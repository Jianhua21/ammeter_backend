package com.kashuo.kcp.rpc.controller;

import com.alibaba.fastjson.JSON;
import com.huawei.iotplatform.client.NorthApiException;
import com.huawei.iotplatform.client.invokeapi.DeviceManagement;
import com.kashuo.common.base.domain.Page;
import com.kashuo.kcp.command.CommandService;
import com.kashuo.kcp.constant.AppConstant;
import com.kashuo.kcp.core.AmmeterPositionService;
import com.kashuo.kcp.core.AmmeterService;
import com.kashuo.kcp.dao.condition.AmmeterPositionCondition;
import com.kashuo.kcp.dao.condition.IMEICondition;
import com.kashuo.kcp.dao.result.PosotionHome;
import com.kashuo.kcp.domain.*;
import com.kashuo.kcp.utils.Results;
import com.kashuo.kcp.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static Logger logger = LoggerFactory.getLogger(PositionController.class);
    @Autowired
    private AmmeterPositionService ammeterPositionService;

    @Autowired
    private AmmeterService ammeterService;

    @Autowired
    private CommandService commandService;

    @PostMapping(value = "/create")
    @ApiOperation(value="电表位置信息录入")
    public Results CreateAmmeterPosition(@RequestBody AmmeterPosition ammeterPosition) throws NorthApiException {

        logger.info("录入设备信息参数：{}", JSON.toJSONString(ammeterPosition));
        if(StringUtil.isEmpty(ammeterPosition.getAmapLatitude())){
            return Results.error("高德经度信息不能为空!");
        }
        if(StringUtil.isEmpty(ammeterPosition.getAmapLongitude())){
            return Results.error("高德纬度信息不能为空!");
        }
        if(StringUtil.isEmpty(ammeterPosition.getName())){
            return Results.error("电表名称不能为空!");
        }
        if(StringUtil.isEmpty(ammeterPosition.getNumber())){
            ammeterPosition.setNumber(ammeterPosition.getImei());
//            return Results.error("电表编号不能为空!");
        }
        if(StringUtil.isEmpty(ammeterPosition.getAddress())){
            ammeterPosition.setAddress(ammeterPosition.getRemark());
//            return Results.error("电表位置信息地址不能为空!");
        }
        if(StringUtil.isEmpty(ammeterPosition.getInstaller())){
            ammeterPosition.setInstaller("-");
        }
        AmmeterPosition position = ammeterPositionService.selectByImei(ammeterPosition.getImei());
        boolean updateFlag = false;
        if(position != null && position.getStatus() != 3 &&
                position.getStatus() !=8){
            return Results.error("IMEI号已存在!");
        }else if(position != null && (position.getStatus() == 3||
                position.getStatus() == 8 )){
            updateFlag = true;
            ammeterPosition.setId(position.getId());
        }
        ammeterPosition.setStatus(0);
        ammeterPosition.setCreateTime(new Date());
        ammeterPosition.setCreateBy(getCuruserId());
        try {
            if(updateFlag){
                ammeterPositionService.updateByPrimaryKeySelective(ammeterPosition);
            }else {
                ammeterPositionService.insert(ammeterPosition);
            }
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
    public Results updateAmmeterPosition(@RequestBody AmmeterPositionBaseInfo ammeterPosition){
        AmmeterPosition positionDB = ammeterPositionService.selectByPrimaryKey(ammeterPosition.getId());
        if(positionDB == null){
            return Results.error("电表信息不存在!");
        }
        try {
            AmmeterPosition position  = new AmmeterPosition();
            position.setId(ammeterPosition.getId());
            position.setAddress(ammeterPosition.getAddress());
            position.setRemark(ammeterPosition.getRemark());
            position.setInstaller(ammeterPosition.getInstaller());
            position.setAmapLongitude(ammeterPosition.getAmapLongitude());
            position.setAmapLatitude(ammeterPosition.getAmapLatitude());
            position.setGpsLongitude(ammeterPosition.getGpsLongitude());
            position.setGpsLatitude(ammeterPosition.getGpsLatitude());
            position.setName(ammeterPosition.getName());
            position.setNumber(ammeterPosition.getNumber());
            ammeterPositionService.updateByPrimaryKeySelective(position);

            /***
             * Web删除和 IoM删除的设备不能同步信息给IoM
             * 注册失败的，不需要同步信息给IoM平台
             */
            if(positionDB.getStatus() !=3 && positionDB.getStatus() !=8 &&
                    positionDB.getStatus() != 2) {
                position.setDeviceId(positionDB.getDeviceId());
                commandService.autoSyncDeviceInfo(position);
            }
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
    @PostMapping("/gisList")
    @ApiOperation("首页GIS")
    public Results gisList(@RequestBody AmmeterPositionCondition condition){
        AmmeterUser user = getCuruser();
        if(!isAdmin(user.getChannelId())){
            condition.setChannelId(user.getChannelId());
        }else{
            condition.setChannelId(null);
        }
        condition.setPageSize(10000);
        Page<PosotionHome> positionPage = ammeterPositionService.getGISList(condition);
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

            try {
                commandService.deleteDeviceFromIoM(ammeterPosition.getDeviceId());
            } catch (NorthApiException e) {
                logger.error("删除失败!");
            }

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
