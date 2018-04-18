package com.kashuo.kcp.rpc.controller;

import com.kashuo.kcp.domain.AmmeterUser;
import com.kashuo.kcp.rpc.config.AppConstantProperties;
import com.kashuo.kcp.rpc.rest.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;

/**
 * 基础控制器，Action的基类封装
 */
public abstract class BaseController {



    protected Logger log = LoggerFactory.getLogger(getClass());

    private static final ThreadLocal<HttpServletRequest> LOCAL_REQUEST = new ThreadLocal<>();

    private static final ThreadLocal<HttpServletResponse> LOCAL_RESPONSE = new ThreadLocal<>();

    @Autowired
    protected AppConstantProperties properties;
//    @Autowired
//    private ChannelService channelService;

    public BaseController() {
        super();
    }

    @ModelAttribute
    public void setHttp(HttpServletRequest request, HttpServletResponse response) {
        LOCAL_REQUEST.set(request);
        LOCAL_RESPONSE.set(response);
    }

    /**
     * 获取当前request
     */
    protected HttpServletRequest getRequest() {
        return LOCAL_REQUEST.get();
    }

    /**
     * 获取当前response
     */
    protected HttpServletResponse getResponse() {
        return LOCAL_RESPONSE.get();
    }

    /**
     * 获取session
     */
    protected HttpSession getHttpSession() {
        HttpServletRequest request = LOCAL_REQUEST.get();
        return request.getSession();
    }

    /**
     * 获取当前用户
     */
    protected AmmeterUser getCuruser() {
        return RequestUtil.getCuruser();
    }

    /**
     * 获取当前用户ID
     */
    protected Integer getCuruserId() {
        return RequestUtil.getCuruserId();
    }

    /**
     * 获取当前URL
     */
    protected String getCururl() {
        HttpServletRequest request = LOCAL_REQUEST.get();
        String reqStr = request.getRequestURL().toString();
        String queryStr = request.getQueryString();

        if (StringUtils.isEmpty(queryStr)) {
            return reqStr;
        } else {
            return reqStr + "?" + queryStr;
        }
    }

    protected  boolean isAdmin(Integer channelId){
        boolean flag = false;
        String adminIds = properties.getAdminIds();
        String[] ids = adminIds.split(",");
        for (String id :ids){
            if(id.equals(String.valueOf(channelId))){
                flag =true;
            }
        }

        return flag;
    }
}
