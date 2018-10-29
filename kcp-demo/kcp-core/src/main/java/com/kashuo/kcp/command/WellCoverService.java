package com.kashuo.kcp.command;

import com.kashuo.kcp.core.AmmeterWarningService;
import com.kashuo.kcp.dao.AmmeterDeviceMapper;
import com.kashuo.kcp.dao.AmmeterPositionMapper;
import com.kashuo.kcp.dao.AmmeterWellcoverMapper;
import com.kashuo.kcp.domain.AmmeterPosition;
import com.kashuo.kcp.domain.AmmeterWellcover;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by dell-pc on 2018/8/19.
 */
@Service
public class WellCoverService {

    @Autowired
    private AmmeterWellcoverMapper wellcoverMapper;
    @Autowired
    private AmmeterPositionMapper positionMapper;
    @Autowired
    private AmmeterDeviceMapper deviceMapper;

    @Autowired
    private AmmeterWarningService warningService;

    public boolean avoidWellCoverStatus(String imei,Integer ruleId){
        AmmeterPosition position = positionMapper.selectByImei(imei);
        if(position != null &&"wellcover".equals(position.getProductId())) {
            AmmeterWellcover wellcover = wellcoverMapper.selectByPositionId(position.getId());
            if (ruleId == 7) {
                wellcover.setTiltSensor("A0");
            } else if(ruleId ==8){
                wellcover.setWaterLevelSensor("W0");
            }
            wellcover.setPositionId(position.getId());
            wellcoverMapper.updateByPrimaryKeySelective(wellcover);
        }
        return true;

    }



    public void processData(String response,String deviceId){
        AmmeterPosition position = positionMapper.selectByDeviceId(deviceId);
        AmmeterWellcover wellcover = analysisResponse(response);
        if(wellcover != null && position != null){
            wellcover.setPositionId(position.getId());
            AmmeterWellcover wellcoverDB = wellcoverMapper.selectByPositionId(position.getId());
            if(wellcoverDB != null){
                wellcover.setId(wellcoverDB.getId());
                wellcoverMapper.updateByPrimaryKeySelective(wellcover);
            }else{
                wellcoverMapper.insert(wellcover);
            }
            warningService.cancelWellCoverWarning(position.getId(),position.getDeviceType());

            //更新上报时间
            deviceMapper.updateProductDateByDeviceId(deviceId,new Date());

            wellcoverMapper.insertHistory(wellcover);
        }


    }

    public AmmeterWellcover analysisResponse(String data){
        AmmeterWellcover wellcover = null;
        if(data != null) {
            String deviceType = data.substring(0, 2);
            if (data.length() == 19 && "P7".equals(deviceType)) {
                wellcover = new AmmeterWellcover();
                wellcover.setDeviceType(deviceType);
                wellcover.setBatteryStatus(data.substring(4, 7));
                wellcover.setSensor(data.substring(8, 11));
                wellcover.setSurfaceDistance(data.substring(12, 15));
                wellcover.setTiltSensor(data.substring(15, 17));
                wellcover.setWaterLevelSensor(data.substring(17, 19));
            } else if (data.length() == 15 && "P0".equals(deviceType)) {
                //烟感设备
                wellcover = new AmmeterWellcover();
                wellcover.setDeviceType(deviceType);
                wellcover.setBatteryStatus(data.substring(4, 7));
                wellcover.setEnTemperature(data.substring(8, 10));
                wellcover.setEnHumidity(data.substring(11, 13));
                wellcover.setSmokeWarning(data.substring(13, 15));
            } else if("P0".equals(deviceType) &&data.length() == 9 ) {
                //烟感设备
                wellcover = new AmmeterWellcover();
                wellcover.setDeviceType(deviceType);
                wellcover.setBatteryStatus(data.substring(4, 7));
                wellcover.setEnTemperature("0");
                wellcover.setEnHumidity("0");
                wellcover.setSmokeWarning(data.substring(7,9));
            }else if("P0".equals(deviceType) && data.length()==17){
                //烟感设备  P0BV342T605H405A1  P0BV355T592H538A0
                wellcover = new AmmeterWellcover();
                wellcover.setDeviceType(deviceType);
                wellcover.setBatteryStatus(data.substring(4, 7));
                wellcover.setEnTemperature(data.substring(8, 10));
                wellcover.setEnHumidity(data.substring(12, 14));
                wellcover.setSmokeWarning(data.substring(15,17));
            }
        }
        return wellcover;
    }


    public static void main(String[] args) {
        new WellCoverService().analysisResponse("P0BV361A0");
    }

}
