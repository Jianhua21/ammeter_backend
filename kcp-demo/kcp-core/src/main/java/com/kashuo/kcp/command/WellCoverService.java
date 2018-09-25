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
            warningService.cancelWellCoverWarning(position.getId());

            //更新上报时间
            deviceMapper.updateProductDateByDeviceId(deviceId,new Date());

            wellcoverMapper.insertHistory(wellcover);
        }


    }

    public AmmeterWellcover analysisResponse(String data){
        AmmeterWellcover wellcover = null;
        if(data.length() ==19){
            wellcover = new AmmeterWellcover();
            wellcover.setDeviceType(data.substring(0,2));
            wellcover.setBatteryStatus(data.substring(4,7));
            wellcover.setSensor(data.substring(8,11));
            wellcover.setSurfaceDistance(data.substring(12,15));
            wellcover.setTiltSensor(data.substring(15,17));
            wellcover.setWaterLevelSensor(data.substring(17,19));
        }
        return wellcover;
    }


    public static void main(String[] args) {
        new WellCoverService().analysisResponse("P7BV342L255D310A0W0");
    }

}
