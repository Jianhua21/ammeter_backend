package com.kashuo.kcp.auth;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huawei.iotplatform.client.NorthApiClient;
import com.huawei.iotplatform.client.NorthApiException;
import com.huawei.iotplatform.client.dto.AuthOutDTO;
import com.huawei.iotplatform.client.dto.AuthRefreshInDTO;
import com.huawei.iotplatform.client.dto.AuthRefreshOutDTO;
import com.huawei.iotplatform.client.dto.ClientInfo;
import com.huawei.iotplatform.client.invokeapi.Authentication;
import com.kashuo.kcp.constant.AppConstant;
import com.kashuo.kcp.core.SysDictionaryService;
import com.kashuo.kcp.dao.AmmeterAuthMapper;
import com.kashuo.kcp.dao.AmmeterNbiotMapper;
import com.kashuo.kcp.domain.AmmeterAuth;
import com.kashuo.kcp.domain.AmmeterNbiot;
import com.kashuo.kcp.redis.RedisServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by dell-pc on 2018/4/16.
 */
@Service
public class AuthService {

    private static Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private SysDictionaryService sysDictionaryService;

    @Autowired
    private AmmeterAuthMapper authMapper;

    @Autowired
    private AmmeterNbiotMapper nbiotMapper;

    @Autowired
    private RedisServiceImpl redisService;


    @Autowired
    public void initPlatIomAuth(){
        redisService.set(AppConstant.REDIS_KEY_AUTH_IOM_WELLCOVER, JSON.toJSONString(authMapper.selectAuthDetail()));
        logger.info("IoM平台Auth 信息:",JSON.toJSONString(redisService.get(AppConstant.REDIS_KEY_AUTH_IOM_WELLCOVER)));
        //移动平台
//        List<AmmeterNbiot> nbiots = nbiotMapper.queryAllNbiot();
//        if(nbiots != null){
//            nbiots.forEach(nb->
//                redisService.set(AppConstant.REDIS_KEY_AUTH_IOT+"_"+nb.getProductName(), JSON.toJSONString(nb))
//            );
//        }

    }

    /***
     * 鉴权接口
     * @return
     * @throws NorthApiException
     */
    public void getAuthInfo() throws NorthApiException{
        //初始化鉴权对象
        Authentication auth = initAuth();
        //5 调用SDK API
        AuthOutDTO authOutDTO = auth.getAuthToken();
        AmmeterAuth ammeterAuth = new AmmeterAuth();
        ammeterAuth.setAccessToken(authOutDTO.getAccessToken());
        ammeterAuth.setCreateTime(new Date());
        ammeterAuth.setExpiresIn(authOutDTO.getExpiresIn());
        ammeterAuth.setRefreshToken(authOutDTO.getRefreshToken());
        ammeterAuth.setScope(authOutDTO.getScope());
        ammeterAuth.setTokenType(authOutDTO.getTokenType());
        ammeterAuth.setStatus("0");
        String authCache = redisService.get(AppConstant.REDIS_KEY_AUTH_IOM_WELLCOVER);
        if(authCache == null ||"null".equals(authCache) ){
            authMapper.insert(ammeterAuth);
        }else{
            ammeterAuth.setId(JSONObject.parseObject(authCache,AmmeterAuth.class).getId());
            authMapper.updateByPrimaryKeySelective(ammeterAuth);
        }
//        auth_init = authMapper.selectAuthDetail();
        redisService.set(AppConstant.REDIS_KEY_AUTH_IOM_WELLCOVER,JSON.toJSONString(authMapper.selectAuthDetail()));
    }

    public void refreshAuthInfo() throws NorthApiException {
        //初始化鉴权对象
        Authentication auth = initAuth();
        AuthRefreshInDTO arid = new AuthRefreshInDTO();
        String authCache = redisService.get(AppConstant.REDIS_KEY_AUTH_IOM_WELLCOVER);
        AmmeterAuth ammeterAuthCache =JSONObject.parseObject(authCache,AmmeterAuth.class);
        arid.setRefreshToken(ammeterAuthCache.getRefreshToken());
        arid.setAppId(sysDictionaryService.getDynamicSystemValue(AppConstant.IOM_APPID,AppConstant.SYSTEM_PARAMS_TYPE_ID));
        arid.setSecret(sysDictionaryService.getDynamicSystemValue(AppConstant.IOM_SECRET,AppConstant.SYSTEM_PARAMS_TYPE_ID));
        //5 调用SDK API
        AuthRefreshOutDTO authOutDTO = auth.refreshAuthToken(arid);

        AmmeterAuth ammeterAuth = new AmmeterAuth();
        ammeterAuth.setAccessToken(authOutDTO.getAccessToken());
        ammeterAuth.setCreateTime(new Date());
        ammeterAuth.setExpiresIn(authOutDTO.getExpiresIn());
        ammeterAuth.setRefreshToken(authOutDTO.getRefreshToken());
        ammeterAuth.setScope(authOutDTO.getScope());
        ammeterAuth.setTokenType(authOutDTO.getTokenType());
        ammeterAuth.setStatus("1");
        ammeterAuth.setId(ammeterAuthCache.getId());
        authMapper.updateByPrimaryKeySelective(ammeterAuth);
        redisService.set(AppConstant.REDIS_KEY_AUTH_IOM_WELLCOVER,JSON.toJSONString(authMapper.selectAuthDetail()));
//        auth_init = authMapper.selectAuthDetail();
    }

    /***
     * 注销鉴权
     * @throws NorthApiException
     */
    public void logoutAuth() throws NorthApiException{
        //初始化鉴权对象
        String authCache = redisService.get(AppConstant.REDIS_KEY_AUTH_IOM_WELLCOVER);
        AmmeterAuth ammeterAuthCache =JSONObject.parseObject(authCache,AmmeterAuth.class);
        Authentication auth = initAuth();
        auth.logoutAuthToken(ammeterAuthCache.getAccessToken());
    }

    /***
     * 初始化鉴权对象
     * @return
     * @throws NorthApiException
     */
    private Authentication initAuth() throws NorthApiException {
        NorthApiClient northApiClient = getNorthApiClient();
        //4 新建服务对象，并初始化，以鉴权为例
        Authentication auth = new Authentication(northApiClient);
        return auth;
    }

    public NorthApiClient getNorthApiClient() throws NorthApiException {
        //1 新建对象NorthApiClient
        NorthApiClient northApiClient = new NorthApiClient();
        //2初始化ClientInfo
        ClientInfo ci = new ClientInfo();
        ci.setAppId(sysDictionaryService.getDynamicSystemValue(AppConstant.IOM_APPID,AppConstant.SYSTEM_PARAMS_TYPE_ID));
        ci.setPlatformIp(sysDictionaryService.getDynamicSystemValue(AppConstant.IOM_PLATFORM_IP,AppConstant.SYSTEM_PARAMS_TYPE_ID));
        ci.setPlatformPort(sysDictionaryService.getDynamicSystemValue(AppConstant.IOM_PLATFORM_PORT,AppConstant.SYSTEM_PARAMS_TYPE_ID));
        ci.setSecret(sysDictionaryService.getDynamicSystemValue(AppConstant.IOM_SECRET,AppConstant.SYSTEM_PARAMS_TYPE_ID));
        northApiClient.setClientInfo(ci);
        //证书配置，使用默认调试证书可以如下配置
        northApiClient.initSSLConfig();
        return northApiClient;
    }

    public AmmeterAuth getPlatIomAuth() throws NorthApiException {
        String authCache = redisService.get(AppConstant.REDIS_KEY_AUTH_IOM_WELLCOVER);
        AmmeterAuth ammeterAuthCache = new AmmeterAuth();
        if(authCache == null || "null".equals(authCache)){
            getAuthInfo();
        }else{
            ammeterAuthCache = JSONObject.parseObject(authCache,AmmeterAuth.class);
            ammeterAuthCache = checkPlatAccessToken(ammeterAuthCache);
        }
        if(ammeterAuthCache.getAppId() == null) {
            ammeterAuthCache.setAppId(sysDictionaryService.getDynamicSystemValue(AppConstant.IOM_APPID,AppConstant.SYSTEM_PARAMS_TYPE_ID));
        }
        return ammeterAuthCache;
    }
    public AmmeterAuth checkPlatAccessToken(AmmeterAuth auth) throws NorthApiException {
          if((new Date().getTime() - auth.getCreateTime().getTime())/1000 >= auth.getExpiresIn()){
              getAuthInfo();
              String authCache = redisService.get(AppConstant.REDIS_KEY_AUTH_IOM_WELLCOVER);
              auth = JSONObject.parseObject(authCache,AmmeterAuth.class);
          }
          return auth;
    }


    /***
     * 移动物联网平台信息参数
     * @param productName
     * @return
     * @throws Exception
     */
    public AmmeterNbiot getNbiotInformation(String productName) {
        String nbiot = redisService.get(AppConstant.REDIS_KEY_AUTH_IOT+"_"+productName);
        AmmeterNbiot nbIot ;
        if(nbiot != null){
            nbIot = JSONObject.parseObject(nbiot,AmmeterNbiot.class);
        }else{
            List<AmmeterNbiot> nbiots = nbiotMapper.queryAllNbiot();
            if(nbiots != null){
                nbiots.forEach(nb->
                            redisService.set(AppConstant.REDIS_KEY_AUTH_IOT + "_" + nb.getProductName(), JSON.toJSONString(nb))
                );
            }
            nbIot = JSONObject.parseObject(redisService.get(AppConstant.REDIS_KEY_AUTH_IOT+"_"+productName),AmmeterNbiot.class);
        }

        return nbIot;

    }

    public static void main(String[] args) throws NorthApiException {
        NorthApiClient nac = new NorthApiClient();

        ClientInfo ci = new ClientInfo();

//        String appId = "bpfcOrUv5lUvxiSOLeVtUMfxBhMa";
//        String secret = "p6m9oSn9FlXZc0nTCIeXPHpm3sYa";

        String appId = "BneCA9AjQg4_uHbA_6anCHfvpbMa";
        String secret = "gc4SrNGIa__eHA5jdQYMPLJ1UwYa";

        ci.setAppId(appId);
        ci.setPlatformIp("180.101.147.89");
        ci.setPlatformPort("8743");
        ci.setSecret(secret);

        nac.setClientInfo(ci);
        nac.initSSLConfig();

        // auth
        Authentication aaa = new Authentication(nac);

        // 4.1.1 鑾峰彇閴存潈Token
        AuthOutDTO aod = null;

        aod = aaa.getAuthToken();

        System.out.println(aod.toString());
    }
}
