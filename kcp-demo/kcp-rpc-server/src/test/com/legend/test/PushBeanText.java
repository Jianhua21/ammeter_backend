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
        String appURL = "http://111.85.219.23/smartcity/api/auth/tenpen/updateDeviceDatas";
        //封装推送数据格式
        PushBean pushBean = new PushBean();
        pushBean.setEventTime(DateUtils.convert2String(new Date(),DateUtils.formatPatternDateTime));
        pushBean.setImei("863703032904901");
        pushBean.setStatus("ONLINE");
        pushBean.setDeviceType("P101");
        Map<String, Object> map = new HashMap<>();
        map.put("data","P7BV347L012D120A1W0LG123.23LT31.212");
        pushBean.setService(map);
        System.out.println(JSONObject.toJSONString(pushBean));
        //发送数据
        HttpClientUtils.getDataFromPostMethod(appURL,JSONObject.toJSONString(pushBean),"application/json");
}
}
