package com.kashuo.kcp.rpc.controller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.huawei.iotplatform.client.NorthApiException;
import com.huawei.iotplatform.client.dto.*;
import com.huawei.iotplatform.client.invokeapi.DeviceManagement;
import com.kashuo.common.base.domain.Page;
import com.kashuo.kcp.auth.AuthService;
import com.kashuo.kcp.constant.AppConstant;
import com.kashuo.kcp.core.AmmeterCallBackService;
import com.kashuo.kcp.core.AmmeterHandleService;
import com.kashuo.kcp.core.AmmeterPositionService;
import com.kashuo.kcp.dao.condition.*;
import com.kashuo.kcp.domain.AmmeterAuth;
import com.kashuo.kcp.domain.AmmeterCallbackHistory;
import com.kashuo.kcp.domain.AmmeterPosition;
import com.kashuo.kcp.manage.DeviceManageService;
import com.kashuo.kcp.utils.Results;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * Created by dell-pc on 2018/4/14.
 */
@RestController
@RequestMapping("/connect")
@Api("交互管理")
public class DeviceManagerController extends BaseController{
    private Logger logger = LoggerFactory.getLogger(DeviceManagerController.class);
    @Value("${app.constant.isDebug}")
    private boolean isDebugFlag;

    @Autowired
    private AmmeterHandleService handleService;

    @Autowired
    private AmmeterPositionService positionService;

    @Autowired
    private AuthService authService;

    @Autowired
    private AmmeterCallBackService callBackService;
    @Autowired
    private DeviceManageService deviceManageService;

    @PostMapping(value = "/handleData")
    @ApiOperation(value = "与IoM数据交互处理",notes = "created by Legend on 2018-Apr-14")
    public PostDeviceCommandInDTO handleIoMData(@RequestBody AmmeterHandleCondition condition){

        logger.info("处理IoM 平台发来的数据 :{}",JSON.toJSONString(condition));
        PostDeviceCommandInDTO  dto = new PostDeviceCommandInDTO();
        AmmeterHandleResult result = handleService.handleIomData(condition,isDebugFlag);
        StringBuffer sb = new StringBuffer();
        sb.append(result.getProtocolHander()).append(result.getProtocolVersion()).append(result.getAmmeterType()).
                append(result.getLength()).append(result.getCommand()).append(result.getData()).append(result.getCrcValue());
        AsynCommandDTO commandDTO = new AsynCommandDTO();
        ObjectMapper mapper = new ObjectMapper();
        //JSON ----> JsonNode
        ObjectNode rootNode = mapper.createObjectNode();
        rootNode.put("data","89010320FEFEFEFE68111111111111681104373733372316007922");
        commandDTO.setParas(rootNode);
        commandDTO.setMethod("send command");
        commandDTO.setServiceId("App Server");
        dto.setCommand(commandDTO);
        dto.setDeviceId("device00001");
        dto.setCallbackUrl("http://localhost:8088/ammeter/connect/test");
        return dto;
    }

    @PostMapping("/registerDevice")
    @ApiOperation(value = "注册直连设备",notes = "Created by Legend on 2018-Apr-15")
    public Results registerDevice(@RequestBody AmmeterRegisterDevice registerDevice) throws NorthApiException {

        AmmeterPosition ammeterPosition = positionService.getAmmeterPositionInfo(registerDevice.getPositionId());
        if(ammeterPosition != null && ammeterPosition.getStatus() ==1){
           return  Results.error("该设备已注册!",registerDevice.getSn());
        }
        //获取平台的token信息
        AmmeterAuth ammeterAuth = authService.getPlatIomAuth();
        DeviceManagement deviceManagement = new DeviceManagement(authService.getNorthApiClient());
        RegDirectDeviceInDTO deviceInDTO = new RegDirectDeviceInDTO();
//        deviceInDTO.setDeviceName(registerDevice.);
        deviceInDTO.setNodeId(ammeterPosition.getImei());
        deviceInDTO.setVerifyCode(ammeterPosition.getImei());
        deviceManageService.regDirectDevice(ammeterPosition,deviceManagement,ammeterAuth);
        return Results.success("设备注册成功!",registerDevice.getSn());
    }

    @GetMapping("/queryDeviceStatus/{deviceId}/{sn}")
    @ApiOperation(value = "查询设备激活状态")
    public Results queryDeviceStatus(@PathVariable("deviceId") String deviceId,@PathVariable("sn") String sn) throws NorthApiException {

        AmmeterPosition ammeterPosition = positionService.selectByDeviceId(deviceId);
        if(ammeterPosition == null){
            return Results.error("设备不存在!",sn);
        }
        DeviceManagement deviceManagement = new DeviceManagement(authService.getNorthApiClient());
        AmmeterAuth ammeterAuth = authService.getPlatIomAuth();
        QueryDeviceStatusOutDTO deviceStatusOutDTO = deviceManagement.queryDeviceStatus(deviceId,ammeterAuth.getAppId(),ammeterAuth.getAccessToken());
        logger.info("设备激活状态返回: {}",JSON.toJSONString(deviceStatusOutDTO));
        AmmeterPosition position = new AmmeterPosition();
        position.setActivated(deviceStatusOutDTO.isActivated());
        position.setId(ammeterPosition.getId());
        positionService.updateByPrimaryKeySelective(position);
        return Results.success(deviceStatusOutDTO.isActivated()?"设备已激活":"设备未激活",sn);
    }

    @PostMapping("/modify")
    @ApiOperation("修改设备信息")
    public Results modifyDeviceInfo(@RequestBody ModifyDeviceInfoInCondition condition) throws NorthApiException {

        AmmeterPosition position = positionService.selectByDeviceId(condition.getDeviceId());
        if(position == null){
            return Results.error("设备不存在!",condition.getSn());
        }
        AmmeterAuth ammeterAuth = authService.getPlatIomAuth();
        DeviceManagement deviceManagement = new DeviceManagement(authService.getNorthApiClient());
        deviceManageService.modifyDeviceInfo2IoT(position,deviceManagement,ammeterAuth);
        return Results.success("更新成功!",condition.getSn());
    }

    @PostMapping("/history")
    @ApiOperation("设备推送消息历史")
    public Results deviceCallBackHistory(@RequestBody CallBackCondition condition){
        Page<AmmeterCallbackHistory> pages = callBackService.getCallBackHistoryByDeviceId(condition);
        if(pages != null){
            Results results = new Results(pages);
            results.setSn(condition.getSn());
            return results;
        }
        return Results.success("未获取到数据!");
    }
    @GetMapping("/delete/{deviceId}/{sn}")
    public Results deleteDevice(@PathVariable("deviceId") String deviceId,@PathVariable("sn") String sn) throws NorthApiException {
        AmmeterPosition position = positionService.selectByDeviceId(deviceId);
        if(position == null){
            return Results.error("设备不存在!",sn);
        }
        AmmeterAuth ammeterAuth = authService.getPlatIomAuth();
        DeviceManagement deviceManagement = new DeviceManagement(authService.getNorthApiClient());
        deviceManagement.deleteDirectDevice(deviceId,ammeterAuth.getAppId(),ammeterAuth.getAccessToken());
        return Results.success("删除设备成功!",sn);
    }


}
