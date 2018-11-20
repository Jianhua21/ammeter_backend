package com.kashuo.kcp.rpc.controller.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kashuo.kcp.api.entity.PushBean;
import com.kashuo.kcp.utils.DateUtils;
import com.kashuo.kcp.utils.HttpClientUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wangyang
 * @create 2018-01-16 17:26
 **/
@RestController
@RequestMapping("/na/1.0")
@Api("垃圾回收器回调")
public class NotifyController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    //设置推送URL
    private String appURL = "http://111.85.219.23/smartcity/api/tenpin/updateDeviceDatas";

    /**
     * 接收推送的数据
     * @param object
     * @return
     * @throws Exception
     */
    @ApiOperation("回调接口")
    @RequestMapping(value = {"/updateDeviceDatas"}, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> demo(@RequestBody Object object) throws Exception {
        System.out.println("----------Object object------------");
        System.out.println(object);
        System.out.println(JSON.toJSONString(object));
        /*
            object content:
            {
            "status":"ONLINE",
            "eventTime":"2017-11-22 14:43:35",
            "service":
                {
                    "cap":1,
                    "fall":1,
                    "fire":1,
                    "location":
                    {
                        "lng":"116.34",
                        "lat":"39.92"
                    },
                    "battery":1,
                    "full":1,
                    "height":349
                },
            "imei":"863703032842937"
            }
         */
        //封装推送数据格式
        PushBean pushBean = new PushBean();
        pushBean.setEventTime(DateUtils.convert2String(new Date(),DateUtils.formatPatternDateTime));
        pushBean.setImei("863703032904901");
        pushBean.setStatus("ONLINE");
        pushBean.setDeviceType("P7");
        Map<String, Object> map = new HashMap<>();
        map.put("data","P7BV347L012D120A1W0LG123.23LT31.212");
        pushBean.setService(map);
        System.out.println(JSONObject.toJSONString(pushBean));
        //发送数据
        String response = HttpClientUtils.getDataFromPostMethod(appURL,JSONObject.toJSONString(pushBean),"application/json");
        logger.info("返回结果:{}",JSONObject.toJSONString(response));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
