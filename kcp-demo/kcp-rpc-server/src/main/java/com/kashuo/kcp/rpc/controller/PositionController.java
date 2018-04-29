package com.kashuo.kcp.rpc.controller;

import com.huawei.iotplatform.client.NorthApiException;
import com.kashuo.common.base.domain.Page;
import com.kashuo.kcp.command.CommandService;
import com.kashuo.kcp.constant.AppConstant;
import com.kashuo.kcp.core.AmmeterPositionService;
import com.kashuo.kcp.core.AmmeterService;
import com.kashuo.kcp.dao.condition.AmmeterPositionCondition;
import com.kashuo.kcp.dao.condition.IMEICondition;
import com.kashuo.kcp.domain.AmmeterDevice;
import com.kashuo.kcp.domain.AmmeterImei;
import com.kashuo.kcp.domain.AmmeterPosition;
import com.kashuo.kcp.domain.AmmeterUser;
import com.kashuo.kcp.utils.Results;
import com.kashuo.kcp.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Created by dell-pc on 2018/3/20.
 */
@RestController
@Api(description = "无线信号位置采集")
@RequestMapping(value = "/position")
public class PositionController extends BaseController{

    @Autowired
    private AmmeterPositionService ammeterPositionService;

    @Autowired
    private AmmeterService ammeterService;

    @Autowired
    private CommandService commandService;

    @PostMapping(value = "/create")
    @ApiOperation(value="电表位置信息录入")
    public Results CreateAmmeterPosition(@RequestBody AmmeterPosition ammeterPosition) throws NorthApiException {

        if(StringUtil.isEmpty(ammeterPosition.getAmapLatitude())){
            return Results.error("高德经度信息不能为空!");
        }else if(StringUtil.isEmpty(ammeterPosition.getAmapLongitude())){
            return Results.error("高德纬度信息不能为空!");
        }else if(StringUtil.isEmpty(ammeterPosition.getName())){
            return Results.error("电表名称不能为空!");
        }else if(StringUtil.isEmpty(ammeterPosition.getNumber())){
            return Results.error("电表编号不能为空!");
        }else if(StringUtil.isEmpty(ammeterPosition.getAddress())){
            return Results.error("电表位置信息地址不能为空!");
        }
        AmmeterPosition position = ammeterPositionService.selectByImei(ammeterPosition.getImei());
        if(position != null){
            return Results.error("IMEI号已存在!");
        }
        ammeterPosition.setStatus(0);
        ammeterPosition.setCreateTime(new Date());
        ammeterPosition.setCreateBy(getCuruserId());
        try {
            ammeterPositionService.insert(ammeterPosition);
            AmmeterDevice ammeterDevice = ammeterService.selectDeviceByImsi(ammeterPosition.getImei());
            if(ammeterDevice == null){
                ammeterDevice = new AmmeterDevice();
                ammeterDevice.setAmmeterNumber(ammeterPosition.getNumber());
                ammeterDevice.setImsi(ammeterPosition.getImei());
                ammeterDevice.setAddress(ammeterPosition.getAddress());
                ammeterDevice.setProductDate(new Date());
                ammeterDevice.setProtocolNo(AppConstant.AmmeterBaseInformation.AMMETER_DEVICE_MODEL+"/"+
                        AppConstant.AmmeterBaseInformation.AMMETER_DEVICE_PROTOCOL);
                ammeterDevice.setType(AppConstant.AmmeterBaseInformation.AMMETER_DEVICE_TYPE);
                ammeterDevice.setHardwareNo(AppConstant.AmmeterBaseInformation.AMMETER_DEVICE_MANUFACTURER_ID);
                ammeterDevice.setSoftwareNo(AppConstant.AmmeterBaseInformation.AMMETER_DEVICE_MANUFACTURER_NAME);
                ammeterDevice.setAssManageCode(AppConstant.AmmeterBaseInformation.AMMETER_DEVICE_MODEL);
                ammeterDevice.setChannelId(getCuruser().getChannelId());
                ammeterService.insert(ammeterDevice);
            }else{
                ammeterDevice.setAmmeterNumber(ammeterPosition.getNumber());
                ammeterDevice.setAddress(ammeterPosition.getAddress());
                ammeterDevice.setProductDate(new Date());
                ammeterDevice.setProtocolNo(AppConstant.AmmeterBaseInformation.AMMETER_DEVICE_MODEL+"/"+
                        AppConstant.AmmeterBaseInformation.AMMETER_DEVICE_PROTOCOL);
                ammeterDevice.setType(AppConstant.AmmeterBaseInformation.AMMETER_DEVICE_TYPE);
                ammeterDevice.setHardwareNo(AppConstant.AmmeterBaseInformation.AMMETER_DEVICE_MANUFACTURER_ID);
                ammeterDevice.setSoftwareNo(AppConstant.AmmeterBaseInformation.AMMETER_DEVICE_MANUFACTURER_NAME);
                ammeterDevice.setAssManageCode(AppConstant.AmmeterBaseInformation.AMMETER_DEVICE_MODEL);
                ammeterDevice.setChannelId(getCuruser().getChannelId());
                ammeterService.updateAmmeterDeviceByCondition(ammeterDevice);
            }

        }catch(Exception e){
            return  Results.error("位置信息录入失败!");
        }

        AmmeterPosition positionDB = ammeterPositionService.selectByImei(ammeterPosition.getImei());
        //向IoT平台注册和设备信息同步
        Integer result = commandService.autoRegDevice(positionDB);

        return Results.success("位置信息录入成功!");

    }
    @GetMapping("/ammeterPositionInfo/{positionId}/{sn}")
    @ApiOperation(value="单个电表位置信息获取")
    public Results getAmmeterPosition(@ApiParam("电表信息位置Id")@PathVariable("positionId") Integer positionId,@PathVariable("sn")String sn){
         AmmeterPosition ammeterPosition = ammeterPositionService.getAmmeterPositionInfo(positionId);
         if(ammeterPosition != null){
             return Results.success(ammeterPosition,sn);
         }else{
             return Results.error("未获取到电表位置信息!",sn);
         }
    }
    @PostMapping("/update")
    @ApiOperation("电表位置信息更新")
    public Results updateAmmeterPosition(@RequestBody AmmeterPosition ammeterPosition){
        try {
            ammeterPositionService.updateByPrimaryKeySelective(ammeterPosition);
        }catch (Exception e){
            return  Results.error("编辑电表信息位置出错了!");
        }
        return Results.success("编辑电表信息位置成功!");
    }

    @PostMapping("/list")
    @ApiOperation("电表位置信息列表")
    public Results list(@RequestBody AmmeterPositionCondition condition){
        AmmeterUser user = getCuruser();
        if(!isAdmin(user.getChannelId())){
            condition.setChannelId(user.getChannelId());
        }else{
            condition.setChannelId(null);
        }
        Page<AmmeterPosition> positionPage = ammeterPositionService.getPositionList(condition);
        Results results = Results.success(positionPage,condition.getSn());
        results.setTotal(positionPage.getTotal());
        return results;
    }

    @GetMapping("/delete/{positionId}/{sn}")
    public Results deletePosition(@PathVariable("positionId") Integer positionId,@PathVariable("sn") String sn){
        AmmeterPosition ammeterPosition = ammeterPositionService.getAmmeterPositionInfo(positionId);
        if(ammeterPosition != null){
            AmmeterPosition position = new AmmeterPosition();
            position.setId(ammeterPosition.getId());
            position.setStatus(3);
            ammeterPositionService.updateByPrimaryKeySelective(position);
            return Results.success("当前设备已删除!",sn);
        }
       return Results.error("设备不存在",sn);
    }
    @PostMapping("/checkIMEI")
    @ApiOperation("检查IMEI是否可用")
    public Results  checkIMEI(@RequestBody IMEICondition condition){
        AmmeterImei ammeterImei = ammeterPositionService.selectIMEIbyKey(condition.getIMEI());
        if(ammeterImei != null){
            AmmeterPosition position = ammeterPositionService.selectActiveByImei(condition.getIMEI());
            if(position != null){
                return Results.error("该IMEI已被注册,请重新输入!");
            }
        }else{
            return Results.error("IMEI 不存在,请重新输入!",condition.getSn());
        }
        return Results.success("IMEI正常",condition.getSn());
    }

}
