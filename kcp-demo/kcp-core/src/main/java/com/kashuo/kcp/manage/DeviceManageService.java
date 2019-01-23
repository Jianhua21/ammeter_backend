package com.kashuo.kcp.manage;

import com.alibaba.fastjson.JSON;
import com.huawei.iotplatform.client.NorthApiException;
import com.huawei.iotplatform.client.dto.ModifyDeviceInfoInDTO;
import com.huawei.iotplatform.client.dto.RegDirectDeviceInDTO;
import com.huawei.iotplatform.client.dto.RegDirectDeviceOutDTO;
import com.huawei.iotplatform.client.invokeapi.DeviceManagement;
import com.kashuo.kcp.auth.AuthExceptionService;
import com.kashuo.kcp.auth.AuthService;
import com.kashuo.kcp.command.CommandService;
import com.kashuo.kcp.constant.AppConstant;
import com.kashuo.kcp.core.AmmeterPositionService;
import com.kashuo.kcp.domain.AmmeterAuth;
import com.kashuo.kcp.domain.AmmeterPosition;
import com.kashuo.kcp.utils.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by dell-pc on 2018/4/19.
 */
@Service
public class DeviceManageService {

    private Logger logger = LoggerFactory.getLogger(DeviceManageService.class);
    @Autowired
    private AmmeterPositionService positionService;

    @Autowired
    private AuthExceptionService authExceptionService;

    public boolean regDirectDevice(AmmeterPosition ammeterPosition, DeviceManagement deviceManagement,AmmeterAuth ammeterAuth) throws NorthApiException {

        boolean result = true;
        RegDirectDeviceInDTO deviceInDTO = new RegDirectDeviceInDTO();
        deviceInDTO.setNodeId(ammeterPosition.getImei());
        deviceInDTO.setVerifyCode(ammeterPosition.getImei());
        deviceInDTO.setTimeout(0);
        deviceInDTO.setDeviceName(ammeterPosition.getName());
        try {
            RegDirectDeviceOutDTO response = deviceManagement.regDirectDevice(deviceInDTO, ammeterAuth.getAppId(), ammeterAuth.getAccessToken());
            logger.info("设备注册状态返回: {}", JSON.toJSONString(response));
            AmmeterPosition position = new AmmeterPosition();
            position.setId(ammeterPosition.getId());
            position.setDeviceId(response.getDeviceId());
            position.setPsk(response.getPsk());
            position.setVerifyCode(response.getVerifyCode());
            position.setStatus(1);
            positionService.updateByPrimaryKeySelective(position);
            //用于同步设备信息使用
            ammeterPosition.setDeviceId(response.getDeviceId());
        }catch(NorthApiException e){
            result =false;
            //注册失败
            AmmeterPosition position = new AmmeterPosition();
            position.setId(ammeterPosition.getId());
            position.setStatus(2);
            positionService.updateByPrimaryKeySelective(position);
            authExceptionService.handleException(e,null);
        }
        return result;
    }

    public boolean modifyDeviceInfo2IoT(AmmeterPosition ammeterPosition, DeviceManagement deviceManagement,AmmeterAuth ammeterAuth) throws NorthApiException {
        ModifyDeviceInfoInDTO deviceInfoInDTO = new ModifyDeviceInfoInDTO();
        deviceInfoInDTO.setDeviceId(ammeterPosition.getDeviceId());
//        deviceInfoInDTO.setDeviceConfig(condition.getDeviceConfigDTO());
        deviceInfoInDTO.setDeviceType(AppConstant.AmmeterBaseInformation.AMMETER_DEVICE_TYPE);
//        deviceInfoInDTO.setEndUser(condition.getEndUser());
        deviceInfoInDTO.setLocation(ammeterPosition.getAddress());
        deviceInfoInDTO.setManufacturerId(AppConstant.AmmeterBaseInformation.AMMETER_DEVICE_MANUFACTURER_ID);
        deviceInfoInDTO.setManufacturerName(AppConstant.AmmeterBaseInformation.AMMETER_DEVICE_MANUFACTURER_NAME);
        deviceInfoInDTO.setModel(AppConstant.AmmeterBaseInformation.AMMETER_DEVICE_MODEL);
//        deviceInfoInDTO.setMute(condition.getMute());
        deviceInfoInDTO.setName(ammeterPosition.getName());
        deviceInfoInDTO.setProtocolType(AppConstant.AmmeterBaseInformation.AMMETER_DEVICE_PROTOCOL);
        boolean result =true;
        try {
            deviceManagement.modifyDeviceInfo(deviceInfoInDTO, ammeterAuth.getAppId(), ammeterAuth.getAccessToken());
            /***
             * 更新状态为 未上电
             */
            AmmeterPosition update = new AmmeterPosition();
            update.setId(ammeterPosition.getId());
            update.setStatus(5);
            positionService.updateByPrimaryKeySelective(update);
        }catch (NorthApiException e){
            result = false;
            AmmeterPosition position_db = new AmmeterPosition();
            position_db.setId(ammeterPosition.getId());
            position_db.setStatus(4);
            positionService.updateByPrimaryKeySelective(position_db);
            authExceptionService.handleException(e,deviceInfoInDTO.getDeviceId());
        }
        return result;

    }
}
