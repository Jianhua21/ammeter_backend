package com.kashuo.kcp.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dell-pc on 2018/8/12.
 */
public class MessageUtils {

    private static String MESSAGE_HOST ="http://smsmsgs.market.alicloudapi.com";

    private  static String MESSAGE_LARGE_HOST ="https://largesms.market.alicloudapi.com";

    private static String MESSAGE_APPCODE ="3157cedf8b4e407bacdd3d82f3b72341";

    public static boolean sendMessage(String imei,String status,String phone){
        if(phone == null||"".equals(phone)){
            phone ="13773075845";
        }
        boolean isSuccess = false;
        String path = "/smsmsgs";
        String method = "GET";
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "APPCODE " + MESSAGE_APPCODE);
        Map<String, String> querys = new HashMap<>();
        querys.put("param", "{ "+imei+" }|{ "+status+" }");
        querys.put("phone", phone);
        querys.put("sign", "46613");
        querys.put("skin", "35058");
        try {
            HttpResponse response = HttpUtils.doGet(MESSAGE_HOST, path, method, headers, querys);
            if(200 == response.getStatusLine().getStatusCode()){
                isSuccess = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isSuccess;
    }

    public static boolean sendLargeMessage(String imei,String status,String phone,String name,String address){
        if(phone == null||"".equals(phone)){
            phone ="13773075845";
        }
        boolean isSuccess = false;
        String path = "/largesms";
        String method = "GET";
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "APPCODE " + MESSAGE_APPCODE);
        Map<String, String> querys = new HashMap<>();
        querys.put("param", "{ "+imei+" } {"+name+"}|{"+address+"}|{ "+status+" }|异常");
        querys.put("phone", phone);
        querys.put("sign", "46613");
        querys.put("skin", "43310");
        try {
            HttpResponse response = HttpUtils.doGet(MESSAGE_LARGE_HOST, path, method, headers, querys);
            if(200 == response.getStatusLine().getStatusCode()){
                isSuccess = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isSuccess;
    }

    public static void main(String[] args) {
        System.out.println(MessageUtils.sendLargeMessage("123456789012345","未上电","13773075845","移动光华大道044灯杆微站10电表","光华大道044灯杆配电箱"));
    }
//    public static void main(String[] args) {
//        String host = "http://smsmsgs.market.alicloudapi.com";
//        String path = "/smsmsgs";
//        String method = "GET";
//        String appcode = "3157cedf8b4e407bacdd3d82f3b72341";
//        Map<String, String> headers = new HashMap<String, String>();
//        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
//        headers.put("Authorization", "APPCODE " + appcode);
//        Map<String, String> querys = new HashMap<String, String>();
//        querys.put("param", "{ 123456789012345 }|{ 未上电 }");
//        querys.put("phone", "13917605273");
//        querys.put("sign", "46613");
//        querys.put("skin", "35058");
//        //JDK 1.8示例代码请在这里下载：  http://code.fegine.com/Tools.zip
//
//        try {
//            /**
//             * 重要提示如下:
//             * HttpUtils请从
//             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
//             * 或者直接下载：
//             * http://code.fegine.com/HttpUtils.zip
//             * 下载
//             *
//             * 相应的依赖请参照
//             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
//             * 相关jar包（非pom）直接下载：
//             * http://code.fegine.com/aliyun-jar.zip
//             */
//            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
//            //System.out.println(response.toString());
//            //获取response的body
//            System.out.println(JSONObject.toJSONString(response.getStatusLine().getStatusCode()));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
