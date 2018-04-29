package com.kashuo.kcp.auth;

import com.alibaba.fastjson.JSON;
import com.huawei.iotplatform.client.NorthApiException;
import com.kashuo.kcp.constant.ExceptionConstant;
import com.kashuo.kcp.utils.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Created by dell-pc on 2018/4/16.
 */
@Service
public class AuthExceptionService {

    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private AuthService authService;

    public Results handleException(NorthApiException exception){
        try{
            logger.info("NorthApiException 异常信息 :{}", JSON.toJSONString(exception));
            if(ExceptionConstant.ERROR_CODE_1005.getErrorCode().equals(exception.getError_code())||
                    ExceptionConstant.ERROR_CODE_100006.getErrorCode().equals(exception.getError_code())){
                authService.getAuthInfo();
            }else {
                return Results.error(50003,
                        ExceptionConstant.getExceptionConstant(exception.getHttpStatusCode(),
                                exception.getError_code()));
            }
        }catch (Exception e){
            return Results.error(50003,"请求IoM平台失败,请再试一次或稍后再试");
        }
        return Results.error(50003,"请求IoM平台失败,请再试一次或稍后再试");
    }
}
