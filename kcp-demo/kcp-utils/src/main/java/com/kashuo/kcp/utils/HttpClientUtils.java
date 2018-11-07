package com.kashuo.kcp.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * Created by enjun.zhu
 * 2017/5/17.
 * →→→→→→如果有问题请联系→→QQ:727865942
 */
public class HttpClientUtils {

    public static JSONObject getDataFromGetMethod(String url) throws IOException {
        //开启一个HttpClient
        HttpClient httpClient = new HttpClient();
        //根据一个url创建一个method对象
        GetMethod method = new GetMethod(url);
        //执行method
        httpClient.executeMethod(method);
        //获取返回的参数
        String responseJsonString = method.getResponseBodyAsString();
        //解析返回的数据格式
        JSONObject json = JSON.parseObject(responseJsonString);
        //关闭连接
        method.releaseConnection();
        return json;
    }

    public static String getDataFromPostMethod(String url,String params) throws IOException {
        return getDataFromPostMethod(url,params,"text/json");
    }

    public static String getDataFromPostMethod(String url,String params,String applciationType) throws IOException{

        //开启一个HttpClient
        HttpClient httpClient =new HttpClient();

        //根据一个url创建一个method对象
        PostMethod method = new PostMethod(url);
        if(params != null && !"".equals(params.trim())) {
            RequestEntity requestEntity = new StringRequestEntity(params,applciationType,"UTF-8");
            method.setRequestEntity(requestEntity);
        }
        //执行method
        httpClient.executeMethod(method);
        //获取返回的参数
//        String responseJsonString = method.getResponseBodyAsString();
        InputStream inputStream = method.getResponseBodyAsStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
        StringBuilder responseJsonString = new StringBuilder();
        String str;
        while((str = br.readLine()) != null){
            responseJsonString .append(str );
        }
        //解析返回的数据格式
        //Map map = JSONObject.parseObject(responseJsonString, Map.class);
        //关闭连接
        method.releaseConnection();
        if (method.getStatusCode() == 200) {
            System.out.println("send success!");
        } else {
            System.out.println("send error!");
        }
        return responseJsonString.toString();
    }
}
