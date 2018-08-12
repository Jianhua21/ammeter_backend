package com.kashuo.kcp.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dell-pc on 2018/8/12.
 */
public class MessageUtils {

    public static void main(String[] args) {
        String host = "http://smsmsgs.market.alicloudapi.com";
        String path = "/smsmsgs";
        String method = "GET";
        String appcode = "3157cedf8b4e407bacdd3d82f3b72341";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("param", "建华|12|5000");
        querys.put("phone", "13773075845");
        querys.put("sign", "1");
        querys.put("skin", "1002");
        //JDK 1.8示例代码请在这里下载：  http://code.fegine.com/Tools.zip

        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 或者直接下载：
             * http://code.fegine.com/HttpUtils.zip
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             * 相关jar包（非pom）直接下载：
             * http://code.fegine.com/aliyun-jar.zip
             */
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            //System.out.println(response.toString());
            //获取response的body
            System.out.println(JSONObject.toJSONString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
