package com.kashuo.kcp.manage;

import com.huawei.iotplatform.client.NorthApiException;
import com.huawei.iotplatform.client.dto.RegDirectDeviceInDTO;
import com.huawei.iotplatform.client.dto.RegDirectDeviceOutDTO;
import com.huawei.iotplatform.client.invokeapi.DeviceManagement;
import com.kashuo.kcp.auth.AuthService;
import com.kashuo.kcp.domain.AmmeterAuth;
import com.kashuo.kcp.utils.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by dell-pc on 2018/4/19.
 */
@Service
public class DeviceManageService {

    @Autowired
    private AuthService authService;

    public Results regDirectDevice(RegDirectDeviceInDTO rddid, String appId) throws NorthApiException {

        DeviceManagement deviceManagement = new DeviceManagement();
        AmmeterAuth ammeterAuth = authService.getPlatIomAuth();
        RegDirectDeviceOutDTO  deviceOutDTO = deviceManagement.regDirectDevice(rddid,appId,ammeterAuth.getAccessToken());
        if(deviceOutDTO != null){

        }
        return null;
    }
}
