package com.legend.test;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.huawei.iotplatform.client.NorthApiClient;
import com.huawei.iotplatform.client.NorthApiException;
import com.huawei.iotplatform.client.dto.*;
import com.huawei.iotplatform.client.invokeapi.Authentication;
import com.huawei.iotplatform.client.invokeapi.SignalDelivery;
import com.huawei.iotplatform.utils.JsonUtil;
import com.kashuo.kcp.auth.AuthService;
import com.kashuo.kcp.rpc.App;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by dell-pc on 2018/9/10.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
public class SignalDeliveryTest {


    @Autowired
    private AuthService authService;

    @Test
    public void getChinaNetIoMInfo() throws NorthApiException {
        System.out.println(JSON.toJSONString(authService.getPlatIomAuth()));
    }



    public static void main(String args[]) throws Exception
    {
        NorthApiClient nac = new NorthApiClient();

        ClientInfo ci = new ClientInfo();

        String appId = "BneCA9AjQg4_uHbA_6anCHfvpbMa";
        String secret = "gc4SrNGIa__eHA5jdQYMPLJ1UwYa";

        ci.setAppId(appId);
        ci.setPlatformIp("180.101.147.89");
        ci.setPlatformPort("8743");
        ci.setSecret(secret);

        nac.setClientInfo(ci);
        nac.initSSLConfig();

        Authentication aaa = new Authentication(nac);

        AuthOutDTO aod ;

        aod = aaa.getAuthToken();

        String accessToken = aod.getAccessToken();
        String deviceId = "4e0536b5-74de-4afc-8d62-f514cfd43cb0";

        SignalDelivery sd = new SignalDelivery();
        sd.setNorthApiClient(nac);

        // 4.4.1 创建设备命令V4


          PostDeviceCommandInDTO pdcid = new PostDeviceCommandInDTO();
          pdcid.setDeviceId(deviceId); AsynCommandDTO acdo = new AsynCommandDTO();
          String serviceId = "NbiotCommonDevice";
          String method = "downcommand";
          ObjectNode paras = JsonUtil.convertObject2ObjectNode("{\"downmessage\":\"89010316FEFEFEFE68AAAAAAAAAAAA681300DF1600937B\"}");
          acdo.setServiceId(serviceId);
          acdo.setMethod(method);
          acdo.setParas(paras);
          pdcid.setCommand(acdo);
//          pdcid.setExpireTime(0);
          PostDeviceCommandOutDTO pdcod = sd.postDeviceCommand(pdcid, appId, accessToken);
          System.out.println(pdcod.toString());

        // 4.4.2 查询设备命令V4

//        QueryDeviceCommandInDTO qdcid = new QueryDeviceCommandInDTO();
//        qdcid.setDeviceId(deviceId);
//        QueryDeviceCommandOutDTO qdcod = sd.queryDeviceCommand(qdcid, appId, accessToken);
//        System.out.println(qdcod.toString());

        // 4.4.3 修改设备命令V4
        /*
         * UpdateDeviceCmdInDTO udcid = new UpdateDeviceCmdInDTO(); String deviceCommandId = "bfeb4ba5c2644aae87b3188beb13539f"; udcid.setDeviceCommandId(deviceCommandId);
         * UpdateDeviceCmdReq udcr = new UpdateDeviceCmdReq(); udcr.setStatus("CANCELED"); udcid.setUpdateDeviceCommandReq(udcr); UpdateDeviceCmdOutDTO udcod =
         * sd.updateDeviceCommand(udcid, appId, accessToken); System.out.println(udcod.toString());
         */

        // 4.4.4 创建设备命令撤销任务V4

        /*
         * DeviceCmdCancelTaskInDTO dcctod = new DeviceCmdCancelTaskInDTO(); dcctod.setDeviceId(deviceId); DeviceCmdCancelTaskOutDTO dcctoud =
         * sd.createDeviceCmdCancelTaskV4(dcctod, appId, accessToken); System.out.println(dcctoud.toString());
         */

        // 4.4.5 查询设备命令撤销任务V4

        /*
         * QueryDeviceCmdCancelTaskInDTO qdcctid = new QueryDeviceCmdCancelTaskInDTO(); qdcctid.setDeviceId(deviceId); QueryDeviceCmdCancelTaskOutDTO qdcctod =
         * sd.queryDeviceCmdCancelTask(qdcctid, appId, accessToken); System.out.println(qdcctod.toString());
         */

        // 4.1.3 注销鉴权Token,无返回值
//        aaa.logoutAuthToken(accessToken);
    }
}
