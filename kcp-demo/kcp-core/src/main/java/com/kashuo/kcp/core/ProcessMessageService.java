package com.kashuo.kcp.core;

import cmcciot.onenet.nbapi.sdk.entity.NbiotCallResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kashuo.kcp.api.entity.CommandDetail;
import com.kashuo.kcp.api.entity.callback.DeviceDataChange;
import com.kashuo.kcp.command.CommandService;
import com.kashuo.kcp.constant.IoTConstant;
import com.kashuo.kcp.domain.AmmeterCallbackHistory;
import com.kashuo.kcp.utils.CRC16x25Utils;
import com.kashuo.kcp.utils.NbiotUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by Mr.Legend on 2019/7/1.
 * →→→→如果有问题请联系→→→→
 * penghuazi@126.com
 * →→→→→→→→→→→→→→→→
 */
@Service
public class ProcessMessageService {


    private Logger logger = LoggerFactory.getLogger(ProcessMessageService.class);

    @Autowired
    private NetWorkService netWorkService;

    @Autowired
    private CommandService commandService;

    @Autowired
    private AmmeterCallBackService callBackService;

    public void processMessage(NbiotUtils.BodyObj obj){
        try {
            NbiotCallResponse response = JSONObject.parseObject(obj.getMsg().toString(), NbiotCallResponse.class);
            DeviceDataChange dataChange = new DeviceDataChange();
            String deviceId = String.valueOf(response.getDevId());
            dataChange.setDeviceId(deviceId);
            dataChange.setNotifyType("Nb_Iot");
            if (response.getType() == 2) {
                //设备上下线
                if (response.getStatus() == 1) {
                    netWorkService.updateDeviceStatusByNb(deviceId, null, true);
                } else {
                    netWorkService.updateDeviceStatusByNb(deviceId, null, false);
                }
            } else {
                //设备数据处理
                String command = response.getValue();
                if (command == "") {
                    return;
                }
                //处理CallBack 命令参数
                CommandDetail detail = commandService.processDeviceCallBackResponse(command);
                String crc_new = CRC16x25Utils.CRC16_Check(detail.getContext().getBytes(), detail.getContext().length());
                if (!crc_new.toUpperCase().equals(detail.getCrcValue())) {
                    logger.info("CRC值 不正确:{}", JSON.toJSONString(detail));
                    return;
                }
                if (IoTConstant.Command.DEVICE_COMMAND_NBM001.equals(detail.getCommand())) {
                    //处理保活数据
                    netWorkService.insertNetWorkInfo(detail.getData(), deviceId);
                    //

                } else if (IoTConstant.Command.DEVICE_COMMAND_NBM002.equals(detail.getCommand())) {
                    //设置CDP服务器IP
                    callBackService.processRunningConfig(detail, deviceId);
                } else if (IoTConstant.Command.DEVICE_COMMAND_NBM003.equals(detail.getCommand())) {
                    //设置APN地址
                    callBackService.processRunningConfig(detail, deviceId);
                } else if (IoTConstant.Command.DEVICE_COMMAND_STM001.equals(detail.getCommand())) {
                    //处理设备软重启CallBack
                } else if (IoTConstant.Command.DEVICE_COMMAND_STM002.equals(detail.getCommand())) {
                    //恢复出厂设置
                } else if (IoTConstant.Command.DEVICE_COMMAND_STM003.equals(detail.getCommand())) {
                    //保存系统配置
                } else if (IoTConstant.Command.DEVICE_COMMAND_STM004.equals(detail.getCommand())) {
                    //配置NB处理流程时间
                    callBackService.processRunningConfig(detail, deviceId);
                } else if (IoTConstant.Command.DEVICE_COMMAND_IEM001.equals(detail.getCommand()) ||
                        IoTConstant.Command.DEVICE_COMMAND_IEM001_2.equals(detail.getCommand())) {
                    //拉闸处理
                    callBackService.processSwitchPower(detail, deviceId, false);
                } else if (IoTConstant.Command.DEVICE_COMMAND_IEM002.equals(detail.getCommand()) ||
                        IoTConstant.Command.DEVICE_COMMAND_IEM002_2.equals(detail.getCommand())) {
                    //合闸处理
                    callBackService.processSwitchPower(detail, deviceId, true);
                } else if (detail.getCommand().startsWith(IoTConstant.Command.DEVICE_COMMAND_FEFEFEFE)) {
                    //电表645命令
                    callBackService.process645dltData(detail, deviceId);
                    logger.info("--------------------------------------------");
                }
                commandService.updateCommandHistoryBySubscrible(dataChange, detail);
            }
            AmmeterCallbackHistory callbackHistory = new AmmeterCallbackHistory();
            callbackHistory.setDeviceId(deviceId);
            callbackHistory.setNotifyType(dataChange.getNotifyType());
            callbackHistory.setCreateTime(new Date());
            callbackHistory.setParams(JSONObject.toJSONString(response));
            callBackService.insertCallBackHistory(callbackHistory);
            logger.info("--------Nbiot subscribe request data End------------------------");
        }catch (Exception e){
            logger.error("error...");
        }
    }
}
