package com.kashuo.kcp.rpc.controller.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kashuo.kcp.constant.AppConstant;
import com.kashuo.kcp.domain.AmmeterAuth;
import com.kashuo.kcp.redis.RedisServiceImpl;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by dell-pc on 2018/4/22.
 */
@RestController
@Api("回调测试")
@RequestMapping("/test")
public class CallBackTestController {

    @Autowired
    private RedisServiceImpl redisService;



    @GetMapping("/redis/get")
    @ResponseBody
    public AmmeterAuth redisGet(){
        String result =
                redisService.get(AppConstant.REDIS_KEY_AUTH_IOM);
        return JSONObject.parseObject(result,AmmeterAuth.class);
    }
    @GetMapping("/redis/set")
    public String redisSet(){
        redisService.set("jianhua","123");
        return "123";
    }

}
