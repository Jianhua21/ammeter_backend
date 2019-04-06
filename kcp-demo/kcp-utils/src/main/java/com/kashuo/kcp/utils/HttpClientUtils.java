package com.kashuo.kcp.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);

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

    public static String getDataFromGetMethodByApiToken(String url,String apiToken) throws IOException {
        //开启一个HttpClient
        HttpClient httpClient = new HttpClient();
        //根据一个url创建一个method对象
        GetMethod method = new GetMethod(url);
        if(apiToken != null) {
            method.setRequestHeader("API_TOKEN", apiToken);
        }
        //执行method
        httpClient.executeMethod(method);
        //获取返回的参数
        String responseJsonString = method.getResponseBodyAsString();
        //解析返回的数据格式
        JSONObject json = JSON.parseObject(responseJsonString);
        //关闭连接
        method.releaseConnection();
        return json.toString();
    }

    public static String getDataFromPostMethod(String url,String params) throws IOException {
        return getDataFromPostMethod(url,params,"text/json",null);
    }

    public static String getPostByApiToken(String url,String params,String apiToken)throws IOException{
        return getDataFromPostMethod(url,params,"application/json",apiToken);
    }

    public static String getDataFromPostMethod(String url,String params,String applciationType,String apiToken) throws IOException{

        logger.info("getDataFromPostMethod 请求Url:{},参数:{} ",url,JSON.toJSONString(params));
        //开启一个HttpClient
        HttpClient httpClient =new HttpClient();

        //根据一个url创建一个method对象
        PostMethod method = new PostMethod(url);
        if(params != null && !"".equals(params.trim())) {
            RequestEntity requestEntity = new StringRequestEntity(params,applciationType,"UTF-8");
            method.setRequestEntity(requestEntity);
        }
        if(apiToken != null) {
            method.setRequestHeader("API_TOKEN", apiToken);
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
        logger.info("getDataFromPostMethod 返回结果:{} ",JSON.toJSONString(responseJsonString));
        return responseJsonString.toString();
    }


    public static String sendDataForm(String url,Map<String, String> bodys,String applciationType) throws IOException{

        //开启一个HttpClient
        HttpClient httpClient =new HttpClient();

        //根据一个url创建一个method对象
        PostMethod method = new PostMethod(url);
        if(bodys != null) {
            org.apache.commons.httpclient.NameValuePair[] data = new org.apache.commons.httpclient.NameValuePair[bodys.size()];
            int i =0;
            for (String key : bodys.keySet()) {
                data[i] =new org.apache.commons.httpclient.NameValuePair(key, bodys.get(key));
                i++;
            }
            method.setRequestBody(data);
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
