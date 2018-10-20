package com.kashuo.kcp.rpc.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.kashuo.kcp.api.entity.callback.DeviceDataChange;
import com.kashuo.kcp.api.entity.callback.DeviceUdpData;
import com.kashuo.kcp.command.WellCoverService;
import com.kashuo.kcp.core.AmmeterCallBackService;
import com.kashuo.kcp.core.AmmeterPositionService;
import com.kashuo.kcp.core.NetWorkService;
import com.kashuo.kcp.domain.AmmeterCallbackHistory;
import com.kashuo.kcp.domain.AmmeterPosition;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * Created by dell-pc on 2018/10/11.
 */
@RestController
@RequestMapping("/udpNbiot")
public class CallBackUDPNbiotController {
    private Logger logger = LoggerFactory.getLogger(CallBackUDPNbiotController.class);
    @Autowired
    private AmmeterCallBackService callBackService;

    @Autowired
    private NetWorkService netWorkService;
    @Autowired
    private WellCoverService wellCoverService;
    @Autowired
    private AmmeterPositionService positionService;


    @RequestMapping(value = "/receive",method = RequestMethod.POST)
    @ApiOperation("回调数据订阅")
    @ResponseBody
    public String receive(@RequestBody String body) throws Exception {
        logger.info("data receive:  body String --- " +body);
        DeviceUdpData obj = JSONObject.parseObject(body,DeviceUdpData.class);
        if (obj != null){
                try {
                    String imei = obj.getImei();
                    AmmeterPosition position = positionService.selectByImei(imei);
                    if(position == null){
                        logger.info("IMEI 不存在!");
                        return "ok";
                    }
                    //设备上线
                    netWorkService.updateDeviceStatusByNb(position.getDeviceId(), null, true);
                    //设备数据处理
                    String command = obj.getData();
                    if ("".equals(command)) {
                        return "ok";
                    }
                    //处理CallBack 命令参数
                    logger.info("======UDP 实际数据============" + JSONObject.toJSONString(obj));
                    wellCoverService.processData(command, position.getDeviceId());
                    AmmeterCallbackHistory callbackHistory = new AmmeterCallbackHistory();
                    callbackHistory.setDeviceId(position.getDeviceId());
                    callbackHistory.setNotifyType(obj.getNotifyType());
                    callbackHistory.setCreateTime(new Date());
                    callbackHistory.setParams(JSONObject.toJSONString(obj));
                    callBackService.insertCallBackHistory(callbackHistory);
                    logger.info("--------UDP Nbiot subscribe request data End------------------------");
                }catch (Exception e){
                    logger.error("数据格式有问题:{}",obj);
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

}
