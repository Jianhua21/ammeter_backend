package com.kashuo.kcp.command;

import com.kashuo.kcp.core.AmmeterRuleService;
import com.kashuo.kcp.core.AmmeterWarningService;
import com.kashuo.kcp.dao.AmmeterDeviceMapper;
import com.kashuo.kcp.dao.AmmeterPositionMapper;
import com.kashuo.kcp.dao.AmmeterWellcoverMapper;
import com.kashuo.kcp.domain.AmmeterPosition;
import com.kashuo.kcp.domain.AmmeterWellcover;
import com.kashuo.kcp.eums.DeviceStatus;
import com.kashuo.kcp.eums.ThirdPartyDeviceStatus;
import com.kashuo.kcp.message.JmsMessageService;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;
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

    @Autowired
    private JmsMessageService jmsMessageService;
    @Autowired
    private AmmeterRuleService ruleService;

    public boolean avoidWellCoverStatus(String imei,Integer ruleId){
        AmmeterPosition position = positionMapper.selectByImei(imei);
        if(position != null &&"wellcover".equals(position.getProductId())) {
            AmmeterWellcover wellcover = wellcoverMapper.selectByPositionId(position.getId());
            //校验设备状态
            wellcover = checkDeviceStatus(wellcover);
//            wellcover.setDeviceStatus(ruleId+100);
            if (ruleId == 7 ) {
                wellcover.setTiltSensor("A0");
            } else if(ruleId == 8 ){
                wellcover.setWaterLevelSensor("W0");
            } else if(ruleId == 5 ){
                wellcover.setSensor("12");
            } else if(ruleId == 6 ){
                wellcover.setSurfaceDistance("55");
            }
            wellcover.setPositionId(position.getId());
            wellcoverMapper.updateByPrimaryKeySelective(wellcover);
        }else if(position != null &&"smokeDetector".equals(position.getProductId())){
            AmmeterWellcover wellcover = wellcoverMapper.selectByPositionId(position.getId());
            //校验设备状态
            wellcover = checkDeviceStatus(wellcover);
            if (ruleId == 7) {
                wellcover.setTiltSensor("A0");
            } else if(ruleId ==8){
                wellcover.setWaterLevelSensor("W0");
            }else if(ruleId ==11){
                wellcover.setSmokeWarning("A0");
            }else if(ruleId ==10){
                wellcover.setSmokeWarning("50");
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
            //校验设备状态
            wellcover = checkDeviceStatus(wellcover);
            //通知第三方
            jmsMessageService.sendThirdPartyNotificationMessage(position, ThirdPartyDeviceStatus.NORMAL.getCode(),response,"2");

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

    public AmmeterWellcover checkDeviceStatus(AmmeterWellcover wellcover){
        AmmeterWellcover check = new AmmeterWellcover();
        BeanUtils.copyProperties(wellcover,check);
        if(check.getTiltSensor()!= null){
            check.setTiltSensor(check.getTiltSensor().substring(1));
        }
        if(check.getWaterLevelSensor()!= null){
            check.setWaterLevelSensor(check.getWaterLevelSensor().substring(1));
        }
        wellcover.setDeviceStatus(DeviceStatus.NORMAL.getCode());
        if(ruleService.checkWellCoverByName(check,"batteryStatus")){
            wellcover.setDeviceStatus(DeviceStatus.LowBattery.getCode());
        }
        if(ruleService.checkWellCoverByName(check,"sensor")){
            wellcover.setDeviceStatus(DeviceStatus.STATE_05.getCode());
        }
        if(ruleService.checkWellCoverByName(check,"surfaceDistance")){
            wellcover.setDeviceStatus(DeviceStatus.STATE_06.getCode());
        }
        if(ruleService.checkWellCoverByName(check,"tiltSensor")){
            wellcover.setDeviceStatus(DeviceStatus.STATE_07.getCode());
        }
        if(ruleService.checkWellCoverByName(check,"waterLevelSensor")){
            wellcover.setDeviceStatus(DeviceStatus.STATE_08.getCode());
        }
        if(ruleService.checkWellCoverByName(check,"enTemperature")){
            wellcover.setDeviceStatus(DeviceStatus.STATE_09.getCode());
        }
        if(ruleService.checkWellCoverByName(check,"enHumidity")){
            wellcover.setDeviceStatus(DeviceStatus.STATE_10.getCode());
        }
        if(ruleService.checkWellCoverByName(check,"smokeWarning")){
            wellcover.setDeviceStatus(DeviceStatus.STATE_11.getCode());
        }
        return wellcover;
    }

    public AmmeterWellcover analysisResponse(String data){
        AmmeterWellcover wellcover = null;
        if(data != null) {
            String deviceType = data.substring(0, 2);
            if ("P7".equals(deviceType)) {
                wellcover = new AmmeterWellcover();
                wellcover.setDeviceType(deviceType);
//                wellcover.setBatteryStatus(data.substring(4, 7));
                wellcover.setBatteryStatus(data.substring(data.indexOf("BV")+2,data.indexOf("L")));
                wellcover.setSensor(data.substring(data.indexOf("L")+1,data.indexOf("D")));
                try {
                    int distance = Integer.parseInt(data.substring(data.indexOf("D")+1,data.indexOf("A")));
                    wellcover.setSurfaceDistance(String.valueOf(distance/2));
                }catch (Exception e){
                    wellcover.setSurfaceDistance(data.substring(data.indexOf("D")+1,data.indexOf("A")));
                }
                wellcover.setTiltSensor(data.substring(data.indexOf("A"),data.indexOf("W")));
                wellcover.setWaterLevelSensor(data.substring(data.indexOf("W"),data.indexOf("W")+2));
                if(data.contains("Ri")){
                    wellcover.setRiData(data.substring(data.indexOf("Ri")+2,data.indexOf("Ri")+4));
                }
                if(data.contains("M")){
                    wellcover.setDeviceImei(data.substring(data.indexOf("M")+1,data.indexOf("O")));
                    wellcover.setDevicePlatform(data.substring(data.indexOf("O")+1,data.indexOf("O")+2));
                }

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
        new WellCoverService().analysisResponse("P7BV16L012D120A1W0Ri27M89861118284007192907O0^@");
//        String data ="P7BV12L012D120A1W0";
//        System.out.println(data.indexOf("L"));
//        System.out.println(data.substring(data.indexOf("BV")+2,data.indexOf("L")));
    }

}
