package com.kashuo.kcp.utils;

import com.alibaba.fastjson.JSONObject;
import com.kashuo.kcp.entity.ZxYunMessage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dell-pc on 2019/2/21.
 */
public class ZxYunUtils {

    public static ZxYunMessage getToken(String appKey,String masterSecret,String url) throws IOException {
        Map<String,String> params = new HashMap<>();
        params.put("appkey",appKey);
        params.put("masterSecret",masterSecret);
        String result = HttpClientUtils.getPostByApiToken(url+"/api/1.0/auth/getToken", JSONObject.toJSONString(params),null);
        return JSONObject.parseObject(result,ZxYunMessage.class);
    }

    public static ZxYunMessage checkToken(String apiToken, String url) throws IOException{
        String result =  HttpClientUtils.getPostByApiToken(url+"/api/1.0/auth/checkToken", null,apiToken);
        return JSONObject.parseObject(result,ZxYunMessage.class);
    }

    public static ZxYunMessage registerDevice(String apiToken,String url,String params)throws IOException{
        String result =  HttpClientUtils.getPostByApiToken(url+"/api/1.0/project/insertGateway", params,apiToken);
        return JSONObject.parseObject(result,ZxYunMessage.class);
    }

    public static ZxYunMessage deviceBind(String apiToken,String url,String params)throws IOException{
        String result =  HttpClientUtils.getPostByApiToken(url+"/api/1.0/deviceBind", params,apiToken);
        return JSONObject.parseObject(result,ZxYunMessage.class);
    }

    public static ZxYunMessage getGateway(String apiToken,String url,String params)throws IOException{
        String result =  HttpClientUtils.getDataFromGetMethodByApiToken(url+"/api/1.0/project/device/gateway/get?gatewayName="+params ,apiToken);
        return JSONObject.parseObject(result,ZxYunMessage.class);
    }

    public static ZxYunMessage getGatewayList(String apiToken,String url,String params)throws IOException{
        String result =  HttpClientUtils.getDataFromGetMethodByApiToken(url+"/api/1.0/project/device/gateway/getDeviceList?gatewayName="+params ,apiToken);
        return JSONObject.parseObject(result,ZxYunMessage.class);
    }

    public static void main(String[] args) throws IOException {
//        ZxYunMessage message = getToken("21dNh035WnLEfbVC6WbjnEpP","EQqTAAr24rQGTfCe3Y0l3B1YgxZhvm",
//                "http://xf.tandatech.com:1080/login");
//        Map<String,String> datas = (Map<String,String>)message.getData();
//       System.out.println(message.getData()+"-------"+datas.get("apiToken"));

        System.out.println(JSONObject.toJSONString(deviceBind("b05afa80-9bb4-4880-bc1e-adccf7c93984",
                "http://www.jzxfyun.net/ThreePlaceREST","{\"gateWayName\":\"01_NB_18120101010103102078\"}")));
    }
}
