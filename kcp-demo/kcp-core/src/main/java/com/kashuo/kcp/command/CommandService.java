package com.kashuo.kcp.command;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.huawei.iotplatform.client.NorthApiException;
import com.huawei.iotplatform.client.dto.*;
import com.huawei.iotplatform.client.invokeapi.DeviceManagement;
import com.huawei.iotplatform.client.invokeapi.SignalDelivery;
import com.huawei.iotplatform.utils.JsonUtil;
import com.kashuo.kcp.api.entity.AmmeterCommand;
import com.kashuo.kcp.auth.AuthService;
import com.kashuo.kcp.constant.AppConstant;
import com.kashuo.kcp.core.AmmeterPositionService;
import com.kashuo.kcp.core.SysDictionaryService;
import com.kashuo.kcp.dao.AmmeterCommandHistoryMapper;
import com.kashuo.kcp.domain.AmmeterAuth;
import com.kashuo.kcp.domain.AmmeterCommandHistory;
import com.kashuo.kcp.domain.AmmeterPosition;
import com.kashuo.kcp.manage.DeviceManageService;
import com.kashuo.kcp.utils.DateUtils;
import com.kashuo.kcp.utils.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private AmmeterPositionService positionService;

    @Autowired
    private DeviceManageService deviceManageService;

    public PostDeviceCommandOutDTO postDeviceCommand(PostDeviceCommandInDTO deviceCommandInDTO) throws NorthApiException {
        SignalDelivery signalDelivery = new SignalDelivery();
        signalDelivery.setNorthApiClient(authService.getNorthApiClient());
        AmmeterAuth ammeterAuth = authService.getPlatIomAuth();
        return signalDelivery.postDeviceCommand(deviceCommandInDTO,ammeterAuth.getAppId(),ammeterAuth.getAccessToken());
    }

    public PostDeviceCommandInDTO prepareCommand(String commandKey,String callBackUrlKey,PostDeviceCommandInDTO deviceCommandInDTO) throws Exception {
        return  prepareCommand(commandKey,callBackUrlKey,deviceCommandInDTO,false);
    }

    public PostDeviceCommandInDTO prepareCommand(String commandKey,String callBackUrlKey,PostDeviceCommandInDTO deviceCommandInDTO,Boolean testFlag) throws Exception {
        deviceCommandInDTO.setCallbackUrl(sysDictionaryService.getDynamicSystemValue(callBackUrlKey, AppConstant.CALLBACK_URLS_TYPE_ID));
        AsynCommandDTO commandDTO = new AsynCommandDTO();
        String command;
        if(testFlag){
            command = commandKey;
        }else {
            command = sysDictionaryService.getDynamicSystemValue(commandKey, AppConstant.CALLBACK_URLS_TYPE_ID);
        }
        commandDTO.setServiceId(AppConstant.COMMAND_SERVICEID_PARAM_NAME);
        commandDTO.setMethod(AppConstant.COMMAND_METHOD_PARAM_NAME);
        AmmeterCommand ammeterCommand = new AmmeterCommand();
        ammeterCommand.setDown_command(command);
        ObjectNode paras = JsonUtil.convertObject2ObjectNode(ammeterCommand);
        commandDTO.setParas(paras);
        deviceCommandInDTO.setCommand(commandDTO);
        logger.info("命令{}下发 参数:{}",commandKey, JsonUtil.jsonObj2Sting(deviceCommandInDTO));
        return deviceCommandInDTO;
    }

    public void insertCommandHistory(PostDeviceCommandOutDTO commandOutDTO){
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
        commandHistoryMapper.insert(commandHistory);
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
            logger.info("IoM平台注册成功!  IMEI:{}",ammeterPosition.getImei());
        }else{
            logger.info("IoM平台同步失败!  IMEI:{}",ammeterPosition.getImei());
        }

    }

}
