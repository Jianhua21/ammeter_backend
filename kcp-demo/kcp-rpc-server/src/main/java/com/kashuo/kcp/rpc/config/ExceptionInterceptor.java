package com.kashuo.kcp.rpc.config;

import com.iotplatform.client.NorthApiException;
import com.kashuo.kcp.auth.AuthExceptionService;
import com.kashuo.kcp.utils.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 异常拦截
 * create by zhuenjun on 2017/09/14 14:24
 *
 * */
@ControllerAdvice
public class ExceptionInterceptor {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AuthExceptionService authExceptionService;

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(Exception.class)
    public Results handlingValidationError(Exception exception) {
        if (exception instanceof HttpRequestMethodNotSupportedException) {
            return Results.error(50001, "不支持的请求方式");
        }
        if (exception instanceof HttpMessageNotReadableException) {
            return Results.error(50002, "请求参数缺失");
        }
        if(exception instanceof NorthApiException){
            return authExceptionService.handleException((NorthApiException)exception,null);

        }
        logger.error("系统错误：" + exception.getMessage(), exception);
        return Results.error("系统内部错误，请稍后再试");
    }
}
