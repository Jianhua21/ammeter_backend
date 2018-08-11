package com.kashuo.kcp.rpc.controller.api;

import cmcciot.onenet.nbapi.sdk.entity.NbiotCallResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kashuo.kcp.api.entity.CommandDetail;
import com.kashuo.kcp.api.entity.callback.DeviceDataChange;
import com.kashuo.kcp.command.CommandService;
import com.kashuo.kcp.constant.IoTConstant;
import com.kashuo.kcp.core.AmmeterCallBackService;
import com.kashuo.kcp.core.NetWorkService;
import com.kashuo.kcp.domain.AmmeterCallbackHistory;
import com.kashuo.kcp.domain.AmmeterDevice;
import com.kashuo.kcp.utils.CRC16x25Utils;
import com.kashuo.kcp.utils.NbiotUtils;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * Created by dell-pc on 2018/8/3.
 */
@RestController
@RequestMapping("/nbiot")
public class CallNbiotController {

    private Logger logger = LoggerFactory.getLogger(CallNbiotController.class);
    private static String token = "abcdefghijkmlnopqrstuvwxyzlegend";//用户自定义token和OneNet第三方平台配置里的token一致
    private static String aeskey = "whBx2ZwAU5LOHVimPj1MPx56QRe3OsGGWRe4dr17crV";//aeskey和OneNet第三方平台配置里的token一致
//    private static String token = "1234567890";

    @Autowired
    private CommandService commandService;

    @Autowired
    private AmmeterCallBackService callBackService;

    @Autowired
    private NetWorkService netWorkService;

    @RequestMapping(value = "/receive", method = RequestMethod.GET)
    @ApiOperation("回调token验证")
    @ResponseBody
    public String check(@RequestParam(value = "msg") String msg,
                        @RequestParam(value = "nonce") String nonce,
                        @RequestParam(value = "signature") String signature) throws UnsupportedEncodingException {

        logger.info("url&token check: msg:{} nonce{} signature:{}",msg,nonce,signature);
        if (NbiotUtils.checkToken(msg, nonce, signature, token)) {
            return msg;
        } else {
            return "error";
        }

    }

    @RequestMapping(value = "/receive",method = RequestMethod.POST)
    @ApiOperation("回调数据订阅")
    @ResponseBody
    public String receive(@RequestBody String body) throws Exception {
        logger.info("data receive:  body String --- " +body);
        /************************************************
         *  解析数据推送请求，非加密模式。
         *  如果是明文模式使用以下代码
         **************************************************/
        /*************明文模式  start****************/
        NbiotUtils.BodyObj obj = NbiotUtils.resolveBody(body, false);
        if (obj != null){
            boolean dataRight = NbiotUtils.checkSignature(obj, token);
            if (dataRight){
                NbiotCallResponse response = JSONObject.parseObject(obj.getMsg().toString(),NbiotCallResponse.class);
                DeviceDataChange dataChange = new DeviceDataChange();
                String deviceId = String.valueOf(response.getDevId());
                dataChange.setDeviceId(deviceId);
                dataChange.setNotifyType("Nb_Iot");
                if(response.getType() ==2) {
                    //设备上下线
                    if(response.getStatus() == 0){
                        netWorkService.updateDeviceStatusByNb(deviceId,null,true);
                    }else{
                        netWorkService.updateDeviceStatusByNb(deviceId,null,false);
                    }
                }else{
                    //设备数据处理
                    String command = response.getValue();
                    //处理CallBack 命令参数
                    CommandDetail detail = commandService.processDeviceCallBackResponse(command);
                    String crc_new = CRC16x25Utils.CRC16_Check(detail.getContext().getBytes(),detail.getContext().length());
                    if(!crc_new.toUpperCase().equals(detail.getCrcValue())){
                        logger.info("CRC值 不正确:{}", JSON.toJSONString(detail));
                        return "";
                    }
                    if(IoTConstant.Command.DEVICE_COMMAND_NBM001.equals(detail.getCommand())){
                        //处理保活数据
                        netWorkService.insertNetWorkInfo(detail.getData(),deviceId);
                        //

                    }else if(IoTConstant.Command.DEVICE_COMMAND_NBM002.equals(detail.getCommand())){
                        //设置CDP服务器IP
                        callBackService.processRunningConfig(detail,deviceId);
                    }else if(IoTConstant.Command.DEVICE_COMMAND_NBM003.equals(detail.getCommand())){
                        //设置APN地址
                        callBackService.processRunningConfig(detail,deviceId);
                    }else if(IoTConstant.Command.DEVICE_COMMAND_STM001.equals(detail.getCommand())){
                        //处理设备软重启CallBack
                    }else if(IoTConstant.Command.DEVICE_COMMAND_STM002.equals(detail.getCommand())){
                        //恢复出厂设置
                    }else if(IoTConstant.Command.DEVICE_COMMAND_STM003.equals(detail.getCommand())){
                        //保存系统配置
                    }else if(IoTConstant.Command.DEVICE_COMMAND_STM004.equals(detail.getCommand())){
                        //配置NB处理流程时间
                        callBackService.processRunningConfig(detail,deviceId);
                    }else if(IoTConstant.Command.DEVICE_COMMAND_IEM001.equals(detail.getCommand())){
                        //拉闸处理
                        callBackService.processSwitchPower(detail,deviceId,false);
                    }else if(IoTConstant.Command.DEVICE_COMMAND_IEM002.equals(detail.getCommand())){
                        //合闸处理
                        callBackService.processSwitchPower(detail,deviceId,true);
                    }else if(detail.getCommand().startsWith(IoTConstant.Command.DEVICE_COMMAND_FEFEFEFE)){
                        //电表645命令
                        callBackService.process645dltData(detail,deviceId);
                        logger.info("--------------------------------------------");
                    }
                    commandService.updateCommandHistoryBySubscrible(dataChange,detail);
                }
                AmmeterCallbackHistory callbackHistory = new AmmeterCallbackHistory();
                callbackHistory.setDeviceId(deviceId);
                callbackHistory.setNotifyType(dataChange.getNotifyType());
                callbackHistory.setCreateTime(new Date());
                callbackHistory.setParams(obj.toString());
                callBackService.insertCallBackHistory(callbackHistory);
                logger.info("--------Nbiot subscribe request data End------------------------");
            }else {
                logger.info("data receive: signature error");
            }

        }else {
            logger.info("data receive: body empty error");
        }
        /*************明文模式  end****************/


        /********************************************************
         *  解析数据推送请求，加密模式
         *
         *  如果是加密模式使用以下代码
         ********************************************************/
        /*************加密模式  start****************/
//        Util.BodyObj obj1 = Util.resolveBody(body, true);
//        logger.info("data receive:  body Object--- " +obj1);
//        if (obj1 != null){
//            boolean dataRight1 = Util.checkSignature(obj1, token);
//            if (dataRight1){
//                String msg = Util.decryptMsg(obj1, aeskey);
//                logger.info("data receive: content" + msg);
//            }else {
//                logger.info("data receive:  signature error " );
//            }
//        }else {
//            logger.info("data receive: body empty error" );
//        }
        /*************加密模式  end****************/
        return "ok";
    }

    public static void main(String[] args) {
        String str = "{\"msg\":{\"at\":1533357129123,\"" +
                "imei\":\"869405032285448\",\"type\":1,\"ds_id\":\"3311_0_5706\",\"value\":\"89010206NBM00143RSSI:11;RSRQ:-113;RSRP:-985;CELLID:15186012A734\",\"dev_id\":36720497},\"msg_signature\":\"70NfXS" +
                "1U+nOPx/BRE0ONSQ==\",\"nonce\":\"LqHbN7$V\"}";
        NbiotUtils.BodyObj obj = NbiotUtils.resolveBody(str, false);
        NbiotCallResponse response = JSONObject.parseObject(obj.getMsg().toString(),NbiotCallResponse.class);
        if(response != null){
            if(response.getType() ==1) {
                //设备上下线
            }else{

            }
        }else{
            System.out.println("======返回数据异常====");
        }
    }
}