package com.kashuo.kcp.rpc.controller.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kashuo.kcp.api.entity.CommandDetail;
import com.kashuo.kcp.api.entity.callback.DeviceCommandCallBack;
import com.kashuo.kcp.api.entity.callback.DeviceDataChange;
import com.kashuo.kcp.api.entity.callback.DeviceZxYunCallBack;
import com.kashuo.kcp.command.CommandService;
import com.kashuo.kcp.command.WellCoverService;
import com.kashuo.kcp.constant.IoTConstant;
import com.kashuo.kcp.core.AmmeterCallBackService;
import com.kashuo.kcp.core.NetWorkService;
import com.kashuo.kcp.domain.AmmeterCallbackHistory;
import com.kashuo.kcp.domain.SysDictionary;
import com.kashuo.kcp.utils.CRC16x25Utils;
import com.kashuo.kcp.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * Created by dell-pc on 2018/4/22.
 */
@RestController
@Api("回调函数接口管理")
@RequestMapping("/api/callback")
public class CallBackController {
    private Logger logger = LoggerFactory.getLogger(CallBackController.class);

    @Autowired
    private AmmeterCallBackService callBackService;

    @Autowired
    private NetWorkService netWorkService;

    @Autowired
    private CommandService commandService;

    @Autowired
    private WellCoverService wellCoverService;

    @PostMapping("/command")
    @ApiOperation("命令下发回调")
    public void CommandCallBack(HttpServletRequest request) throws IOException {
        InputStream inputStream = request.getInputStream();
        String result = org.apache.commons.io.IOUtils.toString(inputStream);
        logger.info("--------IoM request data Start----------------------");
        logger.info(result);
        logger.info("--------IoM request data End------------------------");
        DeviceCommandCallBack params =JSONObject.parseObject(result,DeviceCommandCallBack.class);
        commandService.updateCommandHistory(params);
    }

    @PostMapping("/zxYunSubscribe")
    @ApiOperation("中消云回调")
    public void zxYunSubscribeCallBack(HttpServletRequest request) throws Exception {
        InputStream inputStream = request.getInputStream();
        String result = org.apache.commons.io.IOUtils.toString(inputStream);
        logger.info("--------中消云回调 subscribe request data Start----------------------"+result);
        try {
            DeviceZxYunCallBack callBack = JSON.parseObject(result,DeviceZxYunCallBack.class);

            callBackService.processZxYunCallBack(callBack);

            netWorkService.updateDeviceStatusByNb(callBack.getDeviceId(),null,true);

            logger.info("--------中消云回调 subscribe request data,DeviceId :{}", callBack.getDeviceId());
            AmmeterCallbackHistory callbackHistory = new AmmeterCallbackHistory();
            callbackHistory.setDeviceId(callBack.getDeviceId());
            callbackHistory.setNotifyType("中消云");
            callbackHistory.setCreateTime(new Date());
            callbackHistory.setParams(result);
            callBackService.insertCallBackHistory(callbackHistory);
        }catch (Exception e){
            logger.error("数据格式有问题!中消云");
        }

        logger.info("--------中消云回调 subscribe request data End----------------------");
    }

    @PostMapping("/manholeSubscribe")
    @ApiOperation("订阅回调")
    public void manholeSubscribleCallBack(HttpServletRequest request) throws Exception {
        InputStream inputStream = request.getInputStream();
        String result = org.apache.commons.io.IOUtils.toString(inputStream);
        logger.info("--------IoM manhole cover subscribe request data Start----------------------");
        logger.info(result);
        JSONObject params =JSONObject.parseObject(result);
        if(params == null){
            return;
        }
        String notifyType = String.valueOf(params.get(IoTConstant.IOT_NOTIFY_TYPE));
        String deviceId = String.valueOf(params.get(IoTConstant.IOT_DEVICE_ID));
        logger.info("正在处理通知类型为:{} 的数据",notifyType);
        if(IoTConstant.IOT_NOTIFY_TYPE_DEVICE_DATA_CHANGED.equals(notifyType)){
            DeviceDataChange dataChange = JSON.parseObject(result,DeviceDataChange.class);
            String response = StringUtils.hexString2String(dataChange.getService().getData().getUpmessage());
            logger.info("======实际数据============"+response);
            wellCoverService.processData(response,deviceId);
        }
        netWorkService.updateDeviceStatusByNb(deviceId,null,true);

        AmmeterCallbackHistory callbackHistory = new AmmeterCallbackHistory();
        callbackHistory.setDeviceId(deviceId);
        callbackHistory.setNotifyType(notifyType);
        callbackHistory.setCreateTime(new Date());
        callbackHistory.setParams(result);
        callBackService.insertCallBackHistory(callbackHistory);
        logger.info("--------IoM  manhole cover subscribe request data End----------------------");
    }

    @PostMapping("/subscribe")
    @ApiOperation("订阅回调")
    public void SubscribeCallBack(HttpServletRequest request) throws Exception {
        InputStream inputStream = request.getInputStream();
        String result = org.apache.commons.io.IOUtils.toString(inputStream);
        logger.info("--------IoM subscribe request data Start----------------------");
        logger.info(result);
        JSONObject params =JSONObject.parseObject(result);
        String notifyType = String.valueOf(params.get(IoTConstant.IOT_NOTIFY_TYPE));
        String deviceId = String.valueOf(params.get(IoTConstant.IOT_DEVICE_ID));
        logger.info("正在处理通知类型为:{} 的数据",notifyType);
        try {
            if (IoTConstant.IOT_NOTIFY_TYPE_DEVICE_DATA_CHANGED.equals(notifyType)) {

                DeviceDataChange dataChange = JSON.parseObject(result, DeviceDataChange.class);
                String response = dataChange.getService().getData().getMeter();
                //处理CallBack 命令参数
                CommandDetail detail = commandService.processDeviceCallBackResponse(response);

                String crc_new = CRC16x25Utils.CRC16_Check(detail.getContext().getBytes(), detail.getContext().length());
                if (!crc_new.toUpperCase().equals(detail.getCrcValue())) {
                    logger.info("CRC值 不正确:{}", JSON.toJSONString(detail));
                    return;
                }
                System.out.println("------------------------");
                if (IoTConstant.Command.DEVICE_COMMAND_NBM001.equals(detail.getCommand())) {
                    //处理保活数据
                    netWorkService.insertNetWorkInfo(detail.getData(), deviceId);
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
                } else if (IoTConstant.Command.DEVICE_COMMAND_IEM001.equals(detail.getCommand())) {
                    //拉闸处理
                    callBackService.processSwitchPower(detail, deviceId, false);
                } else if (IoTConstant.Command.DEVICE_COMMAND_IEM002.equals(detail.getCommand())) {
                    //合闸处理
                    callBackService.processSwitchPower(detail, deviceId, true);
                } else if (detail.getCommand().startsWith(IoTConstant.Command.DEVICE_COMMAND_FEFEFEFE)) {
                    //电表645命令
                    callBackService.process645dltData(detail, deviceId);
                }
                commandService.updateCommandHistoryBySubscrible(dataChange, detail);
            } else if (IoTConstant.IOT_NOTIFY_TYPE_DEVICE_INFO_CHANGED.equals(notifyType)) {

            }
            AmmeterCallbackHistory callbackHistory = new AmmeterCallbackHistory();
            callbackHistory.setDeviceId(deviceId);
            callbackHistory.setNotifyType(notifyType);
            callbackHistory.setCreateTime(new Date());
            callbackHistory.setParams(result);
            callBackService.insertCallBackHistory(callbackHistory);
        }catch (Exception e){
            logger.error("电信 -----数据格式有问题:{}",result);
        }
        logger.info("--------IoM subscribe request data End------------------------");
    }


    public static void main(String[] args) {
        String s ="{\n" +
                "\t\"notifyType\": \"deviceDataChanged\",\n" +
                "\t\"deviceId\": \"9f25fafd-61ce-4e91-acdd-e58acc646e7a\",\n" +
                "\t\"gatewayId\": \"9f25fafd-61ce-4e91-acdd-e58acc646e7a\",\n" +
                "\t\"requestId\": null,\n" +
                "\t\"service\": {\n" +
                "\t\t\"serviceId\": \"elect_meter\",\n" +
                "\t\t\"serviceType\": \"elect_meter\",\n" +
                "\t\t\"data\": {\n" +
                "\t\t\t\"meter\": \"89010320FEFEFEFE6806010000000068110433343535bd1646FEFEFEFE6806010000000068910733343535333333D91630D0FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF\"\n" +
                "\t\t},\n" +
                "\t\t\"eventTime\": \"20180520T150030Z\"\n" +
                "\t}\n" +
                "}";
    DeviceDataChange dataChange = JSON.parseObject(s,DeviceDataChange.class);
        System.out.println(JSON.toJSONString(dataChange));
        String response = dataChange.getService().getData().getMeter();
        int commandLength = Integer.parseInt(response.substring(6,8));
        System.out.println(commandLength);
        String command  = response.substring(8,8+commandLength);
        System.out.println(command);

    }

}
