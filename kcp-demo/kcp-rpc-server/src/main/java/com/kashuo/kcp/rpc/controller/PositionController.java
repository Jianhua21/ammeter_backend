package com.kashuo.kcp.rpc.controller;

import com.alibaba.fastjson.JSON;
import com.iotplatform.client.NorthApiException;
import com.kashuo.common.base.domain.Page;
import com.kashuo.kcp.command.CommandService;
import com.kashuo.kcp.command.NbiotCommandService;
import com.kashuo.kcp.constant.AppConstant;
import com.kashuo.kcp.core.AmmeterIMEIService;
import com.kashuo.kcp.core.AmmeterPositionService;
import com.kashuo.kcp.core.AmmeterService;
import com.kashuo.kcp.dao.condition.AmmeterPositionCondition;
import com.kashuo.kcp.dao.condition.AmmeterUpdateCondition;
import com.kashuo.kcp.dao.condition.IMEICondition;
import com.kashuo.kcp.dao.result.AmmeterIMEIResult;
import com.kashuo.kcp.dao.result.PosotionHome;
import com.kashuo.kcp.domain.*;
import com.kashuo.kcp.eums.DeviceTypes;
import com.kashuo.kcp.eums.PlateTypes;
import com.kashuo.kcp.manage.DeviceConfigService;
import com.kashuo.kcp.utils.Results;
import com.kashuo.kcp.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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

    @Autowired
    private DeviceConfigService configService;

    @Autowired
    private NbiotCommandService nbiotCommandService;

    @Autowired
    private AmmeterIMEIService ammeterIMEIService;


    @Value("${app.constant.gaode.appId}")
    private String key;

    @PostMapping(value = "/create")
    @ApiOperation(value="电表位置信息录入")
    public Results createAmmeterPosition(@RequestBody AmmeterPosition ammeterPosition) throws NorthApiException, IOException {

        logger.info("录入设备信息参数：{}", JSON.toJSONString(ammeterPosition));
        if(StringUtil.isEmpty(ammeterPosition.getImei())){
            return Results.error("IMEI不能为空!");
        }
        if(ammeterPosition.getImei().length() != 15){
            return Results.error("IMEI格式不对,请检查下!");
        }
        if(StringUtil.isEmpty(ammeterPosition.getGpsLatitude())&& StringUtil.isEmpty(ammeterPosition.getAmapLatitude())){
            return Results.error("GPS经度信息不能为空!");
        }
        if(StringUtil.isEmpty(ammeterPosition.getGpsLongitude())&&StringUtil.isEmpty(ammeterPosition.getAmapLongitude())){
            return Results.error("GPS纬度信息不能为空!");
        }
        if(StringUtil.isEmpty(ammeterPosition.getName())){
            return Results.error("电表名称不能为空!");
        }
        if(StringUtil.isEmpty(String.valueOf(ammeterPosition.getDeviceType()))){
            return Results.error("请选择设备类型!");
        }
        if(StringUtil.isEmpty(ammeterPosition.getNumber())){
            ammeterPosition.setNumber(ammeterPosition.getImei());
//            return Results.error("电表编号不能为空!");
        }
        if(StringUtil.isEmpty(ammeterPosition.getAddress())){
            if(StringUtil.isEmpty(ammeterPosition.getRemark())){
                return Results.error("设备地址不能为空!");
            }
            ammeterPosition.setAddress(ammeterPosition.getRemark());
        }
        if(StringUtil.isEmpty(ammeterPosition.getInstaller())){
            ammeterPosition.setInstaller("-");
        }
        ammeterPositionService.setAmapLocation(ammeterPosition,key);
        if(StringUtil.isEmpty(String.valueOf(ammeterPosition.getPlatform()))){
            return Results.error("请选择设备注册平台!");
        }
        boolean updateFlag = false;
        if("3".equals(ammeterPosition.getPlatform())){
            AmmeterPosition position = ammeterPositionService.selectByNumber(ammeterPosition.getNumber());
            if (position != null && position.getStatus() != 3 &&
                    position.getStatus() != 8) {
                return Results.error("网关编号已存在!");
            } else if (position != null && (position.getStatus() == 3 ||
                    position.getStatus() == 8)) {
                updateFlag = true;
                ammeterPosition.setId(position.getId());
            }
        }else {
            AmmeterPosition position = ammeterPositionService.selectByImei(ammeterPosition.getImei());
            if (position != null && position.getStatus() != 3 &&
                    position.getStatus() != 8) {
                return Results.error("IMEI号已存在!");
            } else if (position != null && (position.getStatus() == 3 ||
                    position.getStatus() == 8)) {
                updateFlag = true;
                ammeterPosition.setId(position.getId());
            }
        }
        AmmeterUser user = getCuruser();
        ammeterPosition.setProductId(DeviceTypes.parseName(String.valueOf(ammeterPosition.getDeviceType())));
        ammeterPosition.setStatus(0);
        ammeterPosition.setCreateTime(new Date());
        ammeterPosition.setCreateBy(user.getId());
        ammeterPosition.setChannelId(user.getChannelId());
        try {
            if(updateFlag){
                ammeterPositionService.updateAllByPrimaryKeySelective(ammeterPosition);
            }else {
                ammeterPositionService.insert(ammeterPosition);
            }
            AmmeterDevice ammeterDevice = ammeterService.selectByPositionId(ammeterPosition.getId());
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
        if(String.valueOf(PlateTypes.CHINA_MOBILE.getCode()).equals(ammeterPosition.getPlatform())){
             nbiotCommandService.createDevice(positionDB);
        }else if(String.valueOf(PlateTypes.CHINA_TELNET.getCode()).equals(ammeterPosition.getPlatform())){
             commandService.autoRegDevice(positionDB);
        }else if(String.valueOf(PlateTypes.CHINA_ZXYUN.getCode()).equals(ammeterPosition.getPlatform())){
            if(updateFlag){
                commandService.deviceBindZxYun(positionDB);
            }else {
                commandService.autoRegDeviceZxYun(positionDB);
            }
        }else{
            AmmeterPosition update = new AmmeterPosition();
            if(String.valueOf(PlateTypes.CHINA_UNICOM.getCode()).equals(ammeterPosition.getPlatform())){
                update.setDeviceId("un-"+UUID.randomUUID().toString());
            }else{
                update.setDeviceId("sd-"+UUID.randomUUID().toString());
            }
            update.setId(positionDB.getId());
            ammeterPositionService.updateByPrimaryKeySelective(update);
        }
        return Results.success("位置信息录入成功!");
    }
    @GetMapping("/ammeterPositionInfo/{positionId}/{sn}")
    @ApiOperation(value="单个电表位置信息获取")
    public Results getAmmeterPosition(@ApiParam("电表信息位置Id")@PathVariable("positionId") Integer positionId,@PathVariable("sn")String sn){
        AmmeterPositionCondition condition =new AmmeterPositionCondition();
        condition.setPositionId(positionId);
        AmmeterPosition position = ammeterPositionService.selectByPrimaryKey(positionId);
        if(position != null){
            condition.setDeviceType(position.getDeviceType());
            Page<AmmeterPosition> positionPage = ammeterPositionService.getPositionList(condition);
            if (positionPage != null && positionPage.size()>0) {
                return Results.success(positionPage.get(0), sn);
            } else {
                return Results.error("未获取到电表位置信息!", sn);
            }
        }else {
            return Results.error("未获取到电表位置信息!", sn);
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
            position.setGpsLongitude(ammeterPosition.getGpsLongitude());
            position.setGpsLatitude(ammeterPosition.getGpsLatitude());
            ammeterPositionService.setAmapLocation(position,key);
            position.setName(ammeterPosition.getName());
            position.setNumber(ammeterPosition.getNumber());
            position.setDeviceModel(ammeterPosition.getDeviceModel());
            position.setCompanyName(ammeterPosition.getCompanyName());
            position.setContactInfo(ammeterPosition.getContactInfo());
            position.setAgreementStatus(ammeterPosition.getAgreementStatus());

            ammeterPositionService.updateByPrimaryKeySelective(position);

            /***
             * Web删除和 IoM删除的设备不能同步信息给IoM
             * 注册失败的，不需要同步信息给IoM平台
             */
            if(positionDB.getStatus() !=3 && positionDB.getStatus() !=8 &&
                    positionDB.getStatus() != 2) {
                AmmeterPosition update = new AmmeterPosition();
                update.setProductId(positionDB.getProductId());
                update.setName(position.getName());
                update.setDeviceId(positionDB.getDeviceId());
                update.setImei(positionDB.getImei());
                update.setNumber(positionDB.getNumber());
                if("1".equals(ammeterPosition.getPlatform())) {
                logger.info("移动物联网设备更新!");
                commandService.autoSyncDeviceInfoByIot(update);
                }else {
                    position.setProductId(positionDB.getProductId());
                    position.setDeviceId(positionDB.getDeviceId());
                    commandService.autoSyncDeviceInfo(position);
                }

            }else if(positionDB.getStatus() ==8 || positionDB.getStatus() ==2){
                if("1".equals(ammeterPosition.getPlatform())){
                    nbiotCommandService.createDevice(positionDB);
                }else if("0".equals(ammeterPosition.getPlatform())) {
                    commandService.autoRegDevice(position);
                }
            }
        }catch (Exception e){
            logger.error("出错了:{}",e);
            return  Results.error("编辑电表信息位置出错了!");
        }
        return Results.success("编辑电表信息位置成功!");
    }

    @PostMapping("/list")
    @ApiOperation("智能设备位置信息列表")
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
                if("1".equals(ammeterPosition.getPlatform())){
                    commandService.deleteDeviceFromIot(ammeterPosition.getDeviceId(),ammeterPosition.getProductId());
                }else {
                    commandService.deleteDeviceFromIoM(ammeterPosition.getDeviceId());
                }
            } catch (NorthApiException e) {
                logger.error("删除失败!");
            }catch (Exception e){
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

    @PostMapping("/saveIMEI")
    @ApiOperation("IMEI录入")
    public Results saveIMEI(@RequestBody AmmeterImei imei){

        if(StringUtil.isEmpty(imei.getImei())){
            return Results.error("IMEI 不能为空!");
        }
        imei.setCreateTime(new Date());
        imei.setCreateBy(getCuruser().getRealname());
        return  ammeterIMEIService.saveImei(imei);
    }

    @PostMapping("/queryImeiList")
    @ApiOperation("IMEI列表")
    public Results queryImeiList(@RequestBody AmmeterImei imei){
        AmmeterUser user = getCuruser();
        if(!isAdmin(user.getChannelId())){
            imei.setChannelId(user.getChannelId());
        }
        List<AmmeterIMEIResult>  results = ammeterIMEIService.listPages(imei);
        return Results.success(results);
    }
    @GetMapping("/deleteImei/{imei}")
    @ApiOperation("删除IMEI")
    public Results deleteImei(@PathVariable String imei){
        return ammeterIMEIService.deleteImei(imei);
    }



    @GetMapping("/config/{positionId}")
    @ApiOperation("设备的配置信息")
    public Results getConfigInfo(@PathVariable("positionId") Integer positionId){

    AmmeterConfig config = configService.selectByPositionId(positionId);
    if(config == null){
        config = new AmmeterConfig();
        config.setPositionId(positionId);
    }

    return Results.success(config);

    }
    @PostMapping("/config/save")
    @ApiOperation("保存配置修改")
    public Results saveConfig(@RequestBody AmmeterConfig config) throws Exception {

        logger.info("更新配置信息:{}",JSON.toJSONString(config));
        AmmeterPosition position = ammeterPositionService.selectByPrimaryKey(config.getPositionId());
        if(position == null || position.getStatus() == 8){
            return Results.error("设备异常,不可操作!");
        }
        AmmeterConfig configDB = configService.selectByPositionId(config.getPositionId());
        AmmeterConfig configUpdate = new AmmeterConfig();
        if(configDB != null){
            configUpdate.setId(configDB.getId());
            if(!StringUtil.isEmpty(config.getApnAddress()) && !config.getApnAddress().trim().equals(configDB.getApnAddress())
                    && configDB.getApnStatus() != 3 && configDB.getApnStatus() != 2){
                configUpdate.setApnAddress(config.getApnAddress());
                configUpdate.setApnStatus(3);
                commandService.configAPN(position,config.getApnAddress());
            }
            if(!StringUtil.isEmpty(config.getCdp())&&!config.getCdp().trim().equals(configDB.getCdp())
                    && configDB.getCdpStatus() != 3 && configDB.getCdpStatus() != 2){
                configUpdate.setCdp(config.getCdp());
                configUpdate.setCdpStatus(3);
                commandService.configCdpIP(position,config.getCdp());
            }
            if(config.getNb()!= null && !config.getNb().equals(configDB.getNb())
                    && configDB.getNbStatus() != 3 && configDB.getNbStatus() != 2){
                configUpdate.setNb(config.getNb());
                configUpdate.setNbStatus(3);
                commandService.configKeepAlive(position,String.valueOf(config.getNb()));
            }
            configService.updateConfig(configUpdate);
        }else{
            if(!StringUtil.isEmpty(config.getApnAddress())){
                config.setApnStatus(3);
                commandService.configAPN(position,config.getApnAddress());
            }
            if(!StringUtil.isEmpty(config.getCdp())){
                config.setCdpStatus(3);
                commandService.configCdpIP(position,config.getCdp());
            }
            if(config.getNb() != null){
                config.setNbStatus(3);
                commandService.configKeepAlive(position,String.valueOf(config.getNb()));
            }
            configService.insertConfig(config);
        }
        return Results.success("配置保存成功!");
    }

    @PostMapping(value = "/updateStatus")
    @ApiOperation(value="电表开闸合闸操作")
    public Results updateAmmeterStatus(@RequestBody AmmeterUpdateCondition condition) throws Exception {
        return ammeterService.updateAmmeterStatus(condition.getStatus(),condition.getPositionId(),properties.isNbiot());
    }

    @GetMapping("/getPlate")
    @ApiOperation("获取支持的平台")
    public Results getPlate(){
        List<SysPlatform> platforms = ammeterPositionService.queryPlate();
        return Results.success(platforms);
    }


}
