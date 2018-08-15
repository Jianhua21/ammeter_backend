package com.kashuo.kcp.command;

import cmcciot.onenet.nbapi.sdk.api.command.NbiotDeviceManagement;
import cmcciot.onenet.nbapi.sdk.entity.Device;
import cmcciot.onenet.nbapi.sdk.entity.NbiotResult;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.huawei.iotplatform.client.NorthApiException;
import com.huawei.iotplatform.client.dto.*;
import com.huawei.iotplatform.client.invokeapi.DeviceManagement;
import com.huawei.iotplatform.client.invokeapi.SignalDelivery;
import com.huawei.iotplatform.utils.JsonUtil;
import com.kashuo.kcp.api.entity.AmmeterCommand;
import com.kashuo.kcp.api.entity.CommandDetail;
import com.kashuo.kcp.api.entity.CommandParams;
import com.kashuo.kcp.api.entity.callback.DeviceCommandCallBack;
import com.kashuo.kcp.api.entity.callback.DeviceDataChange;
import com.kashuo.kcp.auth.AuthExceptionService;
import com.kashuo.kcp.auth.AuthService;
import com.kashuo.kcp.constant.AppConstant;
import com.kashuo.kcp.constant.IoTConstant;
import com.kashuo.kcp.core.AmmeterPositionService;
import com.kashuo.kcp.core.SysDictionaryService;
import com.kashuo.kcp.dao.AmmeterCommandHistoryMapper;
import com.kashuo.kcp.dao.AmmeterPositionMapper;
import com.kashuo.kcp.dao.result.AmmeterDeviceResult;
import com.kashuo.kcp.domain.AmmeterAuth;
import com.kashuo.kcp.domain.AmmeterCommandHistory;
import com.kashuo.kcp.domain.AmmeterNbiot;
import com.kashuo.kcp.domain.AmmeterPosition;
import com.kashuo.kcp.manage.DeviceManageService;
import com.kashuo.kcp.utils.AmmeterUtils;
import com.kashuo.kcp.utils.CRC16x25Utils;
import com.kashuo.kcp.utils.DateUtils;
import com.kashuo.kcp.utils.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by dell-pc on 2018/4/21.
 */
@Service
public class CommandService {
    private Logger logger = LoggerFactory.getLogger(CommandService.class);
    @Autowired
    private AuthService authService;
    @Autowired
    private SysDictionaryService sysDictionaryService;

    @Autowired
    private AmmeterCommandHistoryMapper commandHistoryMapper;

    @Autowired
    private DeviceManageService deviceManageService;

    @Autowired
    private AuthExceptionService exceptionService;

    @Autowired
    private AmmeterPositionMapper positionMapper;

    @Value("${app.constant.nbiot}")
    private boolean nbiot;

    ExecutorService pool = Executors.newFixedThreadPool(10);

    public PostDeviceCommandOutDTO postDeviceCommand(PostDeviceCommandInDTO deviceCommandInDTO) throws NorthApiException {
        SignalDelivery signalDelivery = new SignalDelivery();
        signalDelivery.setNorthApiClient(authService.getNorthApiClient());
        AmmeterAuth ammeterAuth = authService.getPlatIomAuth();
        return signalDelivery.postDeviceCommand(deviceCommandInDTO,ammeterAuth.getAppId(),ammeterAuth.getAccessToken());
    }

    public PostDeviceCommandInDTO prepareCommand(CommandParams params, PostDeviceCommandInDTO deviceCommandInDTO) throws Exception {
        return  prepareCommand(params,deviceCommandInDTO,false);
    }

    public PostDeviceCommandInDTO prepareCommand(CommandParams params,PostDeviceCommandInDTO deviceCommandInDTO,Boolean testFlag) throws Exception {
        deviceCommandInDTO.setCallbackUrl(sysDictionaryService.getDynamicSystemValue(params.getCallBackKey(), AppConstant.CALLBACK_URLS_TYPE_ID));
        AsynCommandDTO commandDTO = new AsynCommandDTO();
        String command;
        if(testFlag){
            command = params.getCommandKey();
        }else {
            command = sysDictionaryService.getDynamicSystemValue(params.getCommandKey(), AppConstant.CALLBACK_URLS_TYPE_ID);
            //判断是否需要处理645 命令
            if(params.getCommandKey().equals(AppConstant.COMMAND_SWTICH_ON_KEY)){
                command = AmmeterUtils.getSwitchPackageCommand(params.getAddress(),command);
            }else {
                if (params.isDltFlag()) {
                    command = AmmeterUtils.getPackageCommand(params.getAddress(), command);
                }
            }
            logger.info("================"+command);
        }
        commandDTO.setServiceId(AppConstant.COMMAND_SERVICEID_PARAM_NAME);
        commandDTO.setMethod(AppConstant.COMMAND_METHOD_PARAM_NAME);
        AmmeterCommand ammeterCommand = new AmmeterCommand();
        //CRC封装数据
        ammeterCommand.setDown_command(CRCCommand(command,params));
        ObjectNode paras = JsonUtil.convertObject2ObjectNode(ammeterCommand);
        commandDTO.setParas(paras);
        deviceCommandInDTO.setCommand(commandDTO);
        logger.info("Nbiot:{},命令{}下发 参数:{}",nbiot,params.getCommandKey(), JsonUtil.jsonObj2Sting(deviceCommandInDTO));
        return deviceCommandInDTO;
    }

    /***
     * 指令封装与CRC校验
     * @param command
     * @param params
     * @return
     */
    public String CRCCommand(String command,CommandParams params){
        //协议头
        String header = sysDictionaryService.getDynamicValue(params.getCommandType());
        StringBuilder sb = new StringBuilder();
        sb.append(header);
        //指令封装
        if("1".equals(params.getIsChanged())){
            sb.append(params.changedCommandData(command));
        }else {
            sb.append(params.commandData(command));
        }
        if(params.isDataFlag()){
            if("1".equals(params.getIsChanged()) || "2".equals(params.getIsChanged())){
                sb.append("00");
            }else {
            sb.append(params.getDataLength()).append(params.getData());
            }
        }
        String crc = CRC16x25Utils.CRC16_Check(sb.toString().getBytes(),sb.length());
        params.setCommand(sb.toString()+crc.toUpperCase());
        return sb.toString()+crc.toUpperCase();
    }

    public void insertCommandHistory(PostDeviceCommandOutDTO commandOutDTO,String commandName){
        AmmeterCommandHistory commandHistory = new AmmeterCommandHistory();
        commandHistory.setDeviceId(commandOutDTO.getDeviceId());
        commandHistory.setCommand(JsonUtil.jsonObj2Sting(commandOutDTO.getCommand()));
        commandHistory.setCommandId(commandOutDTO.getCommandId());
        commandHistory.setCallbackUrl(commandOutDTO.getCallbackUrl());
        commandHistory.setCreateTime(DateUtils.getDateByIoT(commandOutDTO.getCreationTime()));
        commandHistory.setExpiretime(commandOutDTO.getExpireTime());
        commandHistory.setDeliveredTime(DateUtils.getDateByIoT(commandOutDTO.getDeliveredTime()));
        commandHistory.setExecuteTime(DateUtils.getDateByIoT(commandOutDTO.getExecuteTime()));
        commandHistory.setPlatformIssuedTime(DateUtils.getDateByIoT(commandOutDTO.getPlatformIssuedTime()));
        commandHistory.setStatus(commandOutDTO.getStatus());
        commandHistory.setCommandName(commandName);
        commandHistoryMapper.insert(commandHistory);
    }

    /***
     * 更新call back command的命令状态
     * @param commandCallBack
     */
    public void updateCommandHistory(DeviceCommandCallBack commandCallBack){
        AmmeterCommandHistory commandHistory = new AmmeterCommandHistory();
        commandHistory.setCommandId(commandCallBack.getCommandId());
        commandHistory.setDeviceId(commandCallBack.getDeviceId());
        commandHistory = commandHistoryMapper.selectByCommandIdAndDeviceId(commandHistory);
        commandHistory.setStatus(commandCallBack.getResult().getResultCode());
        commandHistoryMapper.updateStatusByCommandId(commandHistory);
    }

    public void updateCommandHistoryBySubscrible(DeviceDataChange deviceDataChange,CommandDetail detail){
        AmmeterCommandHistory commandHistory = new AmmeterCommandHistory();
        commandHistory.setDeviceId(deviceDataChange.getDeviceId());
        commandHistory.setCommandName(detail.getCommand());
        commandHistory.setStatus("SUCCESS");
        commandHistoryMapper.updateStatusBySubscrible(commandHistory);
    }
    /***
     * 向IoM平台注册和同步设备信息
     * @param ammeterPosition
     * @throws NorthApiException
     */
    public Integer autoRegDevice(AmmeterPosition ammeterPosition) throws NorthApiException {
        AmmeterAuth ammeterAuth = authService.getPlatIomAuth();
        DeviceManagement deviceManagement = new DeviceManagement(authService.getNorthApiClient());
        RegDirectDeviceInDTO deviceInDTO = new RegDirectDeviceInDTO();
        deviceInDTO.setNodeId(ammeterPosition.getImei());
        deviceInDTO.setVerifyCode(ammeterPosition.getImei());
        boolean regFlag = deviceManageService.regDirectDevice(ammeterPosition,deviceManagement,ammeterAuth);
        if(regFlag){
            boolean modifyFlag = deviceManageService.modifyDeviceInfo2IoT(ammeterPosition,deviceManagement,ammeterAuth);
            if(modifyFlag){
                logger.info("IoM平台注册成功!  IMEI:{}",ammeterPosition.getImei());
                return 1;
            }else{
                logger.info("IoM平台同步失败!  IMEI:{}",ammeterPosition.getImei());
                return 4;
            }
        }else {
            logger.info("IoM平台注册失败!  IMEI:{}",ammeterPosition.getImei());
            return 2;
        }
    }

    /***
     *
     */
    public void autoSyncDeviceInfo(AmmeterPosition ammeterPosition) throws NorthApiException {
        AmmeterAuth ammeterAuth = authService.getPlatIomAuth();
        DeviceManagement deviceManagement = new DeviceManagement(authService.getNorthApiClient());
        boolean modifyFlag = deviceManageService.modifyDeviceInfo2IoT(ammeterPosition,deviceManagement,ammeterAuth);
        if(modifyFlag){
            logger.info("IoM平台同步成功!  IMEI:{}",ammeterPosition.getImei());
        }else{
            logger.info("IoM平台同步失败!  IMEI:{}",ammeterPosition.getImei());
        }
    }

    public void deleteDeviceFromIoM(String deviceId) throws NorthApiException {
        AmmeterAuth ammeterAuth = authService.getPlatIomAuth();
        DeviceManagement deviceManagement = new DeviceManagement(authService.getNorthApiClient());
        deviceManagement.deleteDirectDevice(deviceId,ammeterAuth.getAppId(),ammeterAuth.getAccessToken());
    }

    public CommandDetail processDeviceCallBackResponse(String response){
        CommandDetail detail = new CommandDetail();

        //根据版本号确定command的长度
        int version = Integer.parseInt(response.substring(4,6));
        //获取命令长度  2位
        int commandLength = Integer.parseInt(response.substring(6,8));

        if(version ==3) {
            if(response.contains(IoTConstant.Command.DEVICE_COMMAND_IEM001) ||
                    response.contains(IoTConstant.Command.DEVICE_COMMAND_IEM002)){
                detail.setCommandLength(commandLength);
            }else{
                detail.setCommandLength(commandLength*2);
            }
        }else{
            detail.setCommandLength(commandLength);
        }
        //根据命令长度获取  命令值
        String command  = response.substring(8,8+detail.getCommandLength());
        detail.setCommand(command);
        //获取数据长度
        Integer dataLength = Integer.parseInt(response.substring(8+detail.getCommandLength(),8+detail.getCommandLength()+2));
        detail.setDataLength(dataLength);
        //获取数据值
        String data = response.substring(8+detail.getCommandLength()+2,8+detail.getCommandLength()+2+dataLength);
        detail.setData(data);
        //获取CRC值
        String crcValue = response.substring(8+detail.getCommandLength()+2+dataLength,8+detail.getCommandLength()+2+dataLength+4);
        detail.setCrcValue(crcValue);
        String cal_response = response.substring(0,8+detail.getCommandLength()+2+dataLength);
        detail.setContext(cal_response);
        logger.info("命令CallBack  信息>>>>>>>{}",JSON.toJSONString(detail));
        return detail;
    }

    /***
     * 发送电量命令
     * @param result
     * @throws Exception
     */
    public void send645PowerDeviceCommand(AmmeterDeviceResult result) throws Exception {
        send645DeviceCommand(result,AppConstant.COMMAND_AMMETER_POWER_KEY);
    }

    /***
     * 发送电压指令
     * @param result
     * @throws Exception
     */
    public void send645VoltageDeviceCommand(AmmeterDeviceResult result) throws Exception {
        send645DeviceCommand(result,AppConstant.COMMAND_AMMETER_VOLTAGE_KEY);
    }

    /***
     * 发送电流命令
     * @param result
     * @throws Exception
     */
    public void send645CurrentDeviceCommand(AmmeterDeviceResult result) throws Exception {
        send645DeviceCommand(result,AppConstant.COMMAND_AMMETER_CURRENT_KEY);
    }

    /***
     * 发送温度指令
     * @param result
     * @throws Exception
     */
    public void send645TemperatureDeviceCommand(AmmeterDeviceResult result) throws Exception {
        send645DeviceCommand(result,AppConstant.COMMAND_AMMETER_TEMPERATURE_KEY);
    }

    /***
     * 发送645命令
     * @param result
     * @throws Exception
     */
    public void send645DeviceCommand(AmmeterDeviceResult result,String commandKey) throws Exception {
        PostDeviceCommandInDTO deviceCommandInDTO = new PostDeviceCommandInDTO();
        deviceCommandInDTO.setDeviceId(result.getDeviceId());
        CommandParams params = new CommandParams();
        params.setCommandKey(commandKey);
        params.setCallBackKey(AppConstant.COMMAND_COMMON_CALLBACK_URL);
        params.setDataFlag(true);
        params.setCommandType(3);
        params.setDltFlag(true);
        params.setAddress(result.getMeterNo());
        //下发00数据
        params.setIsChanged("1");
        deviceCommandInDTO = prepareCommand(params, deviceCommandInDTO);
        PostDeviceCommandOutDTO deviceCommandOutDTO ;
        if (nbiot) {
            AmmeterPosition position = positionMapper.selectByDeviceId(result.getDeviceId());
            deviceCommandOutDTO = postNbiotDeviceCommand(position,params.getCommand());
        } else{
            deviceCommandOutDTO = postDeviceCommand(deviceCommandInDTO);
        }
        if (deviceCommandOutDTO != null) {
            //插入命令历史记录
            insertCommandHistory(deviceCommandOutDTO,
                    sysDictionaryService.getDynamicSystemValue(params.getCommandKey(), AppConstant.CALLBACK_URLS_TYPE_ID));
        }
    }

    /***
     * 获取电表地址
     * @param deviceId
     * @throws Exception
     */
    public void getAmmeterAddress(String deviceId) throws Exception {
        PostDeviceCommandInDTO deviceCommandInDTO = new PostDeviceCommandInDTO();
        deviceCommandInDTO.setDeviceId(deviceId);
        CommandParams params = new CommandParams();
        params.setCommandKey(AppConstant.COMMAND_AMMETER_ADDRESS_KEY);
        params.setCallBackKey(AppConstant.COMMAND_COMMON_CALLBACK_URL);
        params.setDataFlag(true);
        params.setCommandType(3);
        //下发00数据
        params.setIsChanged("1");

        deviceCommandInDTO = prepareCommand(params, deviceCommandInDTO);
        PostDeviceCommandOutDTO deviceCommandOutDTO;
        if (nbiot) {
            AmmeterPosition position = positionMapper.selectByDeviceId(deviceId);
            deviceCommandOutDTO = postNbiotDeviceCommand(position,params.getCommand());
        } else{
            deviceCommandOutDTO = postDeviceCommand(deviceCommandInDTO);
        }
        if (deviceCommandOutDTO != null) {
            //插入命令历史记录
            insertCommandHistory(deviceCommandOutDTO,
                    sysDictionaryService.getDynamicSystemValue(params.getCommandKey(), AppConstant.CALLBACK_URLS_TYPE_ID));
        }
    }

    public void commonCommandSend(AmmeterPosition position,String commandKey,CommandParams params) throws Exception {
        PostDeviceCommandInDTO deviceCommandInDTO = new PostDeviceCommandInDTO();
        deviceCommandInDTO.setDeviceId(position.getDeviceId());
        params.setCommandKey(commandKey);
        params.setCallBackKey(AppConstant.COMMAND_COMMON_CALLBACK_URL);
        params.setDataFlag(true);
//        if(data != null){
//            params.setData(data);
//        }else {
//            params.setData(sysDictionaryService.getDynamicSystemValue(params.getCommandKey(), AppConstant.CALLBACK_URLS_TYPE_ID));
//        }
        if (params.getCommandType() == null) {
            params.setCommandType(1);
        }
        deviceCommandInDTO = prepareCommand(params, deviceCommandInDTO);

        PostDeviceCommandOutDTO deviceCommandOutDTO;
        if (nbiot) {
            deviceCommandOutDTO = postNbiotDeviceCommand(position,params.getCommand());
        } else{
            deviceCommandOutDTO = postDeviceCommand(deviceCommandInDTO);
        }
        if(deviceCommandOutDTO != null){
            //插入命令历史记录
            insertCommandHistory(deviceCommandOutDTO,
                    sysDictionaryService.getDynamicSystemValue(params.getCommandKey(),
                            sysDictionaryService.getDynamicSystemValue(params.getCommandKey(), AppConstant.CALLBACK_URLS_TYPE_ID)));
        }
    }

    public PostDeviceCommandOutDTO postNbiotDeviceCommand(AmmeterPosition position,String command){
        AmmeterNbiot nbiot = authService.getNbiotInformation(position.getProductId());
        Device device = new Device("",position.getImei(),position.getNumber());
        device.setCommand(command);
        device.setWriteResId(nbiot.getResourceId());
        device.setObjId(nbiot.getObjId());
        device.setObjInstId(nbiot.getObjInstanceId());
        NbiotDeviceManagement deviceManagement = new NbiotDeviceManagement(nbiot.getApiKey());
        Integer times =1;
        NbiotResult result = deviceManagement.sendWriteCommand(device);
        logger.info("nbiot 返回数据:"+ JSONObject.toJSONString(result));
        boolean code = "5106".equals(result.getErrno());
        while (times <=3 && code){
            logger.info("nbiot 尝试第:"+(times+1)+"次重新发送");
            result = deviceManagement.sendWriteCommand(device);
            logger.info("nbiot 返回数据:"+ JSONObject.toJSONString(result));
            if("5106".equals(result.getErrno())){
                times++;
            }else{
                break;
            }
        }

        PostDeviceCommandOutDTO commandOutDTO = new PostDeviceCommandOutDTO();
        commandOutDTO.setDeviceId(position.getDeviceId());
        commandOutDTO.setAppId("Nb_Iot");
        commandOutDTO.setStatus(result.getErrno());
        commandOutDTO.setCommandId(command);
        return commandOutDTO;
    }

    /***
     * 设置CDP IP
     * @param position
     * @param cdpIp
     * @throws Exception
     */
    public void configCdpIP(AmmeterPosition position,String cdpIp) throws Exception {
        CommandParams params = new CommandParams();
        //发送CDP IP配置信息
        params.setData(cdpIp);
        params.setCommandType(2);
        try {
            commonCommandSend(position, AppConstant.COMMAND_CDP_IP_KEY, params);
        }catch (NorthApiException e){
            exceptionService.handleException(e,position.getDeviceId());
        }
    }

    /***
     * 设置APN 地址
     * @param position
     * @param apn
     * @throws Exception
     */
    public void configAPN(AmmeterPosition position,String apn) throws Exception {
        CommandParams params = new CommandParams();
        //发送APN 地址配置信息
        params.setData(apn);
        params.setCommandType(2);
        try{
            commonCommandSend(position, AppConstant.COMMAND_APN_ADDRESS_KEY, params);
        }catch (NorthApiException e){
            exceptionService.handleException(e,position.getDeviceId());
        }
    }

    /***
     * 设置保活时间配置
     * @param position
     * @param keepAliveTime
     * @throws Exception
     */
    public void configKeepAlive(AmmeterPosition position,String keepAliveTime) throws Exception {
        CommandParams params = new CommandParams();
        //发送保活时间配置信息
        params.setData(keepAliveTime);
        try {
            commonCommandSend(position, AppConstant.COMMAND_NB_KEEPALIVE_KEY, params);
        }catch (NorthApiException e){
            exceptionService.handleException(e,position.getDeviceId());
        }
    }

    public static void main(String[] args) {
        String res ="89010316FEFEFEFE68AAAAAAAAAAAA681300DF1644FEFEFEFE6811111111111168930644444444444467160047FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF";
        System.out.println(new CommandService().processDeviceCallBackResponse(res));
    }

}
