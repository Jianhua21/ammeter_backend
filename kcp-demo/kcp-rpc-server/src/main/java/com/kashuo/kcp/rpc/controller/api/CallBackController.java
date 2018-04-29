package com.kashuo.kcp.rpc.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.kashuo.kcp.constant.IoTConstant;
import com.kashuo.kcp.core.AmmeterCallBackService;
import com.kashuo.kcp.domain.AmmeterCallbackHistory;
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

    @PostMapping("/command")
    @ApiOperation("命令下发回调")
    public void CommandCallBack(HttpServletRequest request) throws IOException {
        InputStream inputStream = request.getInputStream();
        String result = org.apache.commons.io.IOUtils.toString(inputStream);
        logger.info("--------IoM request data Start----------------------");
        logger.info(result);
        logger.info("--------IoM request data End------------------------");
        JSONObject params =JSONObject.parseObject(result);
    }
    @PostMapping("/subscribe")
    @ApiOperation("订阅回调")
    public void SubscribeCallBack(HttpServletRequest request) throws IOException {
        InputStream inputStream = request.getInputStream();
        String result = org.apache.commons.io.IOUtils.toString(inputStream);
        logger.info("--------IoM subscribe request data Start----------------------");
        logger.info(result);
        JSONObject params =JSONObject.parseObject(result);
        String notifyType = String.valueOf(params.get(IoTConstant.IOT_NOTIFY_TYPE));
        String deviceId = String.valueOf(params.get(IoTConstant.IOT_DEVICE_ID));
        logger.info("正在处理通知类型为:{} 的数据",notifyType);
        if(IoTConstant.IOT_NOTIFY_TYPE_DEVICE_DATA_CHANGED.equals(notifyType)){

        }
        AmmeterCallbackHistory callbackHistory = new AmmeterCallbackHistory();
        callbackHistory.setDeviceId(deviceId);
        callbackHistory.setNotifyType(notifyType);
        callbackHistory.setCreateTime(new Date());
        callbackHistory.setParams(result);
        callBackService.insertCallBackHistory(callbackHistory);
        logger.info("--------IoM subscribe request data End------------------------");
    }




}
