package com.legend.test;

import com.alibaba.fastjson.JSONObject;
import com.kashuo.kcp.api.entity.PushBean;
import com.kashuo.kcp.utils.DateUtils;
import com.kashuo.kcp.utils.HttpClientUtils;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dell-pc on 2018/10/27.
 */
public class PushBeanText {

    public static void main(String[] args) throws IOException {


        //设置推送URL
        String appURL = "http://127.0.0.1:8089/smart_device/na/1.0/updateDeviceDatas";
        //封装推送数据格式
        PushBean pushBean = new PushBean();
        pushBean.setEventTime(DateUtils.convert2String(new Date(),DateUtils.formatPatternDateTime));
        pushBean.setImei("863703032904901");
        pushBean.setStatus("ONLINE");
        Map<String, Object> map = new HashMap<>();
        Map<String, String> location = new HashMap<>();
        map.put("full",1);
        map.put("fire",1);
        map.put("fall",1);
        map.put("cap",1);
        location.put("lng","116.34");
        location.put("lat","39.92");
        map.put("location",location);
        map.put("battery",1);
        map.put("height",349);

        pushBean.setService(map);
        System.out.println(JSONObject.toJSONString(pushBean));
        //发送数据
        HttpClientUtils.getDataFromPostMethod(appURL,JSONObject.toJSONString(pushBean),"application/json");
}
}
