package com.kashuo.kcp.rpc.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.kashuo.kcp.dao.SysLogErrorMapper;
import com.kashuo.kcp.domain.AmmeterUser;
import com.kashuo.kcp.rpc.controller.BaseController;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dell-pc on 2018/10/29.
 */
@Component
@Aspect
public class ExceptionLogInterceptor extends BaseController{

    @Autowired
    private SysLogErrorMapper sysLogErrorMapper;

    @Pointcut("execution(* com.kashuo.kcp.rpc.controller.*.*(..)) || " +
            "execution(* com.kashuo.kcp.rpc.controller.api.*.*(..))")
    private void exceptionPointCut() {
    }

    @After(value = "exceptionPointCut()")
    public void afterProcess(JoinPoint point){
        Annotation[] annotations = point.getTarget().getClass().getAnnotations();
        for (Annotation annotation :annotations){
            System.out.println(annotation.annotationType());
        }

    }

    @AfterThrowing(value = "exceptionPointCut()", throwing = "e")
    public void afterThrowing(JoinPoint point, Throwable e) {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            AmmeterUser user = getCuruser();
            String str = JSONObject.toJSONString(point.getArgs());
            StackTraceElement[] stackTrace = e.getStackTrace();
            StringBuilder errorCode = new StringBuilder();
            errorCode.append(e.toString() + "\n");
            for (StackTraceElement s : stackTrace) {
                errorCode.append("\t" + s.toString() + "\n");
            }
            Map<String, Object> map = new HashMap<>();
            map.put("createTime", new Date());
            map.put("url", request.getRequestURI());
            map.put("createUser", user == null ? "" : user.getLoginName());
            map.put("parameter", str);
            map.put("exception", errorCode.toString());
            sysLogErrorMapper.insert(map);
        } catch (Exception h) {
            // h.printStackTrace();
        }

    }
}
