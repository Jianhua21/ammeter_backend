package com.legend.test;


import com.iotplatform.client.NorthApiClient;
import com.iotplatform.client.NorthApiException;
import com.iotplatform.client.dto.AuthOutDTO;
import com.iotplatform.client.dto.ClientInfo;
import com.iotplatform.client.dto.RegDirectDeviceInDTO;
import com.iotplatform.client.dto.RegDirectDeviceOutDTO;
import com.iotplatform.client.invokeapi.Authentication;
import com.iotplatform.client.invokeapi.DeviceManagement;
import net.sf.json.util.PropertyFilter;

/**
 * Created by dell-pc on 2018/4/22.
 */
public class DeviceManagerTest {
    public static void main(String[] args) throws NorthApiException {

        NorthApiClient nac = new NorthApiClient();

        ClientInfo ci = new ClientInfo();


        String appId = "bpfcOrUv5lUvxiSOLeVtUMfxBhMa";
        String secret = "p6m9oSn9FlXZc0nTCIeXPHpm3sYa";

        ci.setAppId(appId);
        ci.setPlatformIp("180.101.147.89");
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

//        String accessToken = aod.getAccessToken();
        String accessToken ="38f221b397288b4b6679ca9f76544d";
        // 4.2.1 设备注册

        RegDirectDeviceInDTO rddid = new RegDirectDeviceInDTO();
        String nodeid = "863703033404885";
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
