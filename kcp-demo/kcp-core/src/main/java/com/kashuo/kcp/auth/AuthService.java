package com.kashuo.kcp.auth;

import com.huawei.iotplatform.client.NorthApiClient;
import com.huawei.iotplatform.client.NorthApiException;
import com.huawei.iotplatform.client.dto.AuthOutDTO;
import com.huawei.iotplatform.client.dto.AuthRefreshInDTO;
import com.huawei.iotplatform.client.dto.AuthRefreshOutDTO;
import com.huawei.iotplatform.client.dto.ClientInfo;
import com.huawei.iotplatform.client.invokeapi.Authentication;
import com.kashuo.kcp.core.SysDictionaryService;
import com.kashuo.kcp.dao.AmmeterAuthMapper;
import com.kashuo.kcp.domain.AmmeterAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by dell-pc on 2018/4/16.
 */
@Service
public class AuthService {


    @Autowired
    private SysDictionaryService sysDictionaryService;

    @Autowired
    private AmmeterAuthMapper authMapper;

    private static AmmeterAuth auth_init;

    @Autowired
    public void initPlatIomAuth(){
        auth_init = authMapper.selectAuthDetail();
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
        if(auth_init == null){
            authMapper.insert(ammeterAuth);
        }else{
            ammeterAuth.setId(auth_init.getId());
            authMapper.updateByPrimaryKeySelective(ammeterAuth);
        }
        auth_init = authMapper.selectAuthDetail();
    }

    public void refreshAuthInfo() throws NorthApiException {
        //初始化鉴权对象
        Authentication auth = initAuth();
        AuthRefreshInDTO arid = new AuthRefreshInDTO();
        arid.setRefreshToken(auth_init.getRefreshToken());
        arid.setAppId(sysDictionaryService.getDynamicSystemValue("appId"));
        arid.setSecret(sysDictionaryService.getDynamicSystemValue("secret"));
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
        ammeterAuth.setId(auth_init.getId());
        authMapper.updateByPrimaryKeySelective(ammeterAuth);
        auth_init = authMapper.selectAuthDetail();
    }

    /***
     * 注销鉴权
     * @throws NorthApiException
     */
    public void logoutAuth() throws NorthApiException{
        //初始化鉴权对象
        Authentication auth = initAuth();
        auth.logoutAuthToken(auth_init.getAccessToken());
    }

    /***
     * 初始化鉴权对象
     * @return
     * @throws NorthApiException
     */
    private Authentication initAuth() throws NorthApiException {
        //1 新建对象NorthApiClient
        NorthApiClient northApiClient = new NorthApiClient();
        //2初始化ClientInfo
        ClientInfo ci = new ClientInfo();
        ci.setAppId(sysDictionaryService.getDynamicSystemValue("appId"));
        ci.setPlatformIp(sysDictionaryService.getDynamicSystemValue("platformIp"));
        ci.setPlatformPort(sysDictionaryService.getDynamicSystemValue("platformPort"));
        ci.setSecret(sysDictionaryService.getDynamicSystemValue("secret"));
        northApiClient.setClientInfo(ci);
        //证书配置，使用默认调试证书可以如下配置
        northApiClient.initSSLConfig();
        //4 新建服务对象，并初始化，以鉴权为例
        Authentication auth = new Authentication(northApiClient);
        return auth;
    }
}
