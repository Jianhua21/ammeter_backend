package com.kashuo.kcp.rpc.controller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.huawei.iotplatform.client.NorthApiException;
import com.huawei.iotplatform.client.dto.AsynCommandDTO;
import com.huawei.iotplatform.client.dto.PostDeviceCommandInDTO;
import com.huawei.iotplatform.client.dto.RegDirectDeviceInDTO;
import com.huawei.iotplatform.client.dto.RegDirectDeviceOutDTO;
import com.huawei.iotplatform.client.invokeapi.DeviceManagement;
import com.kashuo.kcp.api.entity.RegDevice;
import com.kashuo.kcp.core.AmmeterHandleService;
import com.kashuo.kcp.core.AmmeterPositionService;
import com.kashuo.kcp.dao.condition.AmmeterHandleCondition;
import com.kashuo.kcp.dao.condition.AmmeterHandleResult;
import com.kashuo.kcp.dao.condition.AmmeterRegisterDevice;
import com.kashuo.kcp.domain.AmmeterPosition;
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
public class ConnectAppController extends BaseController{
    private Logger logger = LoggerFactory.getLogger(ConnectAppController.class);
    @Value("${app.constant.isDebug}")
    private boolean isDebugFlag;

    @Autowired
    private AmmeterHandleService handleService;

    @Autowired
    private AmmeterPositionService positionService;

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
        DeviceManagement deviceManagement = new DeviceManagement();
        RegDirectDeviceInDTO deviceInDTO = new RegDirectDeviceInDTO();
        deviceInDTO.setDeviceName(ammeterPosition.getName());
        deviceInDTO.setNodeId(ammeterPosition.getImei());
        deviceInDTO.setVerifyCode(ammeterPosition.getImei());
        try {
            RegDirectDeviceOutDTO response = deviceManagement.regDirectDevice(deviceInDTO, "", "");
            AmmeterPosition position = new AmmeterPosition();
            position.setId(ammeterPosition.getId());
            position.setDeviceId(response.getDeviceId());
            position.setPsk(response.getPsk());
            position.setVerifyCode(response.getVerifyCode());
            position.setStatus(1);
            positionService.updateByPrimaryKeySelective(position);
        }catch(NorthApiException e){
            //注册失败
            AmmeterPosition position = new AmmeterPosition();
            position.setId(ammeterPosition.getId());
            position.setStatus(2);
            positionService.updateByPrimaryKeySelective(position);

            return  Results.error("设备注册失败!",registerDevice.getSn());
        }
        return Results.success("设备注册成功!",registerDevice.getSn());
    }


}
