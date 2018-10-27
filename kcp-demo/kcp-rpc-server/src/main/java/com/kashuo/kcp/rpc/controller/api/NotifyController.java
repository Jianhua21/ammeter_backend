package com.kashuo.kcp.rpc.controller.api;

import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangyang
 * @create 2018-01-16 17:26
 **/
@RestController
@RequestMapping("/na/1.0")
public class NotifyController {
    /**
     * 接收推送的数据
     * @param object
     * @return
     * @throws Exception
     */
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
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
