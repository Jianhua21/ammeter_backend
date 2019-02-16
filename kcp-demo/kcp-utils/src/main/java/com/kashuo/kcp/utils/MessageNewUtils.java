package com.kashuo.kcp.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.kashuo.kcp.entity.MessageBody;
import com.kashuo.kcp.entity.MessageResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell-pc on 2019/2/14.
 */
public class MessageNewUtils {

    public static boolean sendNoticesMessage(MessageBody message,String phone){
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAICUQSAwOaqPPu", "WtILoMfs3376WkAOva5fAX26GivC48");
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers",phone);
        request.putQueryParameter("SignName", "契通物联");
        request.putQueryParameter("TemplateCode", "SMS_155245060");
        request.putQueryParameter("TemplateParam", message.toString());
        try {
            CommonResponse response = client.getCommonResponse(request);
            MessageResponse messageResponse = JSONObject.parseObject(response.getData(),MessageResponse.class);
            if("OK".equals(messageResponse.getCode())){
                return true;
            }
        } catch (ServerException e) {
            e.printStackTrace();
            return false;
        } catch (ClientException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }


    public static void main(String[] args) {
        MessageBody message = new MessageBody();
        message.setAddress("燕子山小区南路7号井燕子山小区南路7号井");
        message.setStatus("未上电");
        message.setImei("867725033353313");
        message.setRemark("请登录网站查看");
        String phone = "13773075845,18115690874";
        String str ="{\"imei\":\"867725033353313\",\"address\":\"燕子山小区南路7号井\",\"status\":\"未上电\",\"remark\":\"请登录网站查看\"}";

        MessageNewUtils.sendNoticesMessage(message,phone);
    }
}
