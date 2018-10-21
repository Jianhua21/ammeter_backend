package com.legend.test;

import com.huawei.iotplatform.client.NorthApiClient;
import com.huawei.iotplatform.client.NorthApiException;
import com.huawei.iotplatform.client.dto.AuthOutDTO;
import com.huawei.iotplatform.client.dto.ClientInfo;
import com.huawei.iotplatform.client.dto.RegDirectDeviceInDTO;
import com.huawei.iotplatform.client.dto.RegDirectDeviceOutDTO;
import com.huawei.iotplatform.client.invokeapi.Authentication;
import com.huawei.iotplatform.client.invokeapi.DeviceManagement;

/**
 * Created by dell-pc on 2018/4/22.
 */
public class DeviceManagerTest {
    public static void main(String[] args) throws NorthApiException {

        NorthApiClient nac = new NorthApiClient();

        ClientInfo ci = new ClientInfo();


        String appId = "5XajQp0O7Cc0BlGLfmHP3bmTCDsa";
        String secret = "zjnQUeXOL6D8D20OX5XvsrfpaMca";

        ci.setAppId(appId);
        ci.setPlatformIp("device.api.ct10649.com");
        ci.setPlatformPort("8743");
        ci.setSecret(secret);


        nac.setClientInfo(ci);
        nac.initSSLConfig();

        DeviceManagement dm = new DeviceManagement(nac);

        // auth
        Authentication aaa = new Authentication(nac);

        // 4.1.1 鑾峰彇閴存潈Token
        AuthOutDTO aod = null;

        aod = aaa.getAuthToken();

        String accessToken = aod.getAccessToken();
//        String accessToken ="38f221b397288b4b6679ca9f76544d";
        // 4.2.1 设备注册

        RegDirectDeviceInDTO rddid = new RegDirectDeviceInDTO();
        String nodeid = "863703033414889";
        String verifyCode = nodeid;
        rddid.setNodeId(nodeid);
        rddid.setVerifyCode(verifyCode);
        rddid.setTimeout(0);
        RegDirectDeviceOutDTO rddod = null;
        rddod = dm.regDirectDevice(rddid, appId, accessToken);
        System.out.println(rddod.toString());
        String deviceId = rddod.getDeviceId();

    }
}
