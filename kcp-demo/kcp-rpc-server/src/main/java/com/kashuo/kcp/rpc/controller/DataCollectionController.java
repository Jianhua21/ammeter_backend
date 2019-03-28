package com.kashuo.kcp.rpc.controller;

import com.alibaba.fastjson.JSON;

import com.iotplatform.client.NorthApiException;
import com.iotplatform.client.dto.SubscribeInDTO;
import com.iotplatform.client.invokeapi.DataCollection;
import com.kashuo.kcp.auth.AuthService;
import com.kashuo.kcp.constant.AppConstant;
import com.kashuo.kcp.core.SysDictionaryService;
import com.kashuo.kcp.dao.condition.SubscribeCondition;
import com.kashuo.kcp.utils.Results;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by dell-pc on 2018/4/21.
 */
@Api("数据采集管理")
@RestController
@RequestMapping("/collection")
public class DataCollectionController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(DataCollectionController.class);

    @Autowired
    private AuthService authService;
    @Autowired
    private SysDictionaryService sysDictionaryService;

    @PostMapping("/subscribe")
    @ApiOperation("Application订阅")
    public Results subscribe(@RequestBody SubscribeCondition condition) throws NorthApiException {
        DataCollection dc = new DataCollection(authService.getNorthApiClient());
        // 4.3.5 订阅平台数据
        SubscribeInDTO sid = new SubscribeInDTO();
//        String notifyType = "deviceAdded";
        sid.setNotifyType(condition.getNotifyType());
//        String callbackurl = "http://172.31.126.82:8080/RESTfulWS/rest/UserInfoService/subscriber1";
        String callBackUrl = sysDictionaryService.getDynamicSystemValue(AppConstant.COMMAND_SUBSCRIBE_CALLBACK_URL,AppConstant.CALLBACK_URLS_TYPE_ID);
        sid.setCallbackurl(callBackUrl);
        dc.subscribeNotify(sid, authService.getPlatIomAuth().getAccessToken());
        log.info("回调函数传参数值:{}", JSON.toJSONString(sid));
        return Results.success("向平台发送订阅成功!",condition.getSn());
    }
}
