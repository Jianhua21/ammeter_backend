package com.kashuo.kcp.core;

import com.alibaba.fastjson.JSONObject;
import com.kashuo.common.base.domain.Page;
import com.kashuo.common.mybatis.helper.PageHelper;
import com.kashuo.kcp.dao.AmmeterNetworkMapper;
import com.kashuo.kcp.dao.AmmeterPositionMapper;
import com.kashuo.kcp.dao.AmmeterWarningMapper;
import com.kashuo.kcp.dao.AmmeterWellcoverMapper;
import com.kashuo.kcp.dao.condition.WarningCondition;
import com.kashuo.kcp.dao.result.*;
import com.kashuo.kcp.domain.*;
import com.kashuo.kcp.utils.BeanUtils;
import com.kashuo.kcp.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by dell-pc on 2018/4/9.
 */
@Service
public class AmmeterWarningService {

    @Autowired
    private AmmeterNetworkMapper networkMapper;
    @Autowired
    private AmmeterRuleService ruleService;
    @Autowired
    private AmmeterWarningMapper warningMapper;

    @Autowired
    private AmmeterPositionMapper ammeterPositionMapper;
    @Autowired
    private AmmeterWellcoverMapper wellcoverMapper;

    public void updateWarningInfo(){
        List<AmmeterNetwork> networks = networkMapper.selectForWarningReport();
        for (AmmeterNetwork network:networks){
            if(network.getAmmeterId() != null ) {
                ruleService.generateNetWorkWarningInfo(network);
            }
        }

        List<AmmeterNetwork> offline_networks = networkMapper.selectOfflineDevice();
        offline_networks.forEach(n-> {
            AmmeterPosition p = ammeterPositionMapper.selectByDeviceId(n.getDeviceId());
                boolean messageFlag = MessageUtils.sendMessage(p.getImei(),"未上电",p.getContactInfo());

                ruleService.offLineDeviceWarning(n,messageFlag);
                AmmeterPosition position = new AmmeterPosition();
                //设备不在线处理
                position.setStatus(7);
                position.setDeviceId(n.getDeviceId());
                ammeterPositionMapper.updateStatusByDeviceId(position);
            }
        );

        List<AmmeterNetwork> online_networks = networkMapper.selectOnlineDeviceWithWarning();
        online_networks.forEach(o->{
                AmmeterWarning warning = new AmmeterWarning();
                warning.setWarningType(1);
                warning.setAmmeterId(o.getAmmeterId());
                warning.setWarningStatus("1");
                warningMapper.updateStatusByType(warning);

                AmmeterPosition position = new AmmeterPosition();
                //设备在线处理
                position.setStatus(6);
                position.setDeviceId(o.getDeviceId());
                ammeterPositionMapper.updateStatusByDeviceId(position);
        });
    }

    public void genereateWellCoverWarning(){
        List<WarningWellCover> list = warningMapper.wellCoverWarningList(null);
        if(list != null){
            for (WarningWellCover warningWellCover :list){
                AmmeterWellcover wellcover = wellcoverMapper.selectByPositionId(warningWellCover.getId());

                if(warningWellCover.getBatteryWarning() == null){
                    ruleService.checkWellCoverWarning(wellcover,"batteryStatus",warningWellCover.getAmmeterId());
                }
                if(warningWellCover.getSensorWarning() == null){
                    ruleService.checkWellCoverWarning(wellcover,"sensor",warningWellCover.getAmmeterId());
                }
                if(warningWellCover.getSurfaceDistanceWarning() == null){
                    ruleService.checkWellCoverWarning(wellcover,"surfaceDistance",warningWellCover.getAmmeterId());
                }
                if(warningWellCover.getTiltSensorWarning() == null){
                    wellcover.setTiltSensor(wellcover.getTiltSensor().substring(1,2));
                    ruleService.checkWellCoverWarning(wellcover,"tiltSensor",warningWellCover.getAmmeterId());
                }
                if(warningWellCover.getWaterLevelSensorWarning() == null){
                    wellcover.setWaterLevelSensor(wellcover.getWaterLevelSensor().substring(1,2));
                    ruleService.checkWellCoverWarning(wellcover,"waterLevelSensor",warningWellCover.getAmmeterId());
                }
            }
        }
    }

    public void cancelWellCoverWarning(Integer positionId){
        WarningCondition condition = new WarningCondition();
        condition.setPositionId(positionId);
        List<WarningWellCover> list = warningMapper.wellCoverWarningList(condition);
        if(list != null){
            for (WarningWellCover warningWellCover :list){
                AmmeterWellcover wellcover = wellcoverMapper.selectByPositionId(warningWellCover.getId());
                if(warningWellCover.getBatteryWarning() != null){
                    ruleService.cancelWellCoverWarning(wellcover,"batteryStatus",warningWellCover.getAmmeterId());
                }
                if(warningWellCover.getSensorWarning() != null){
                    ruleService.cancelWellCoverWarning(wellcover,"sensor",warningWellCover.getAmmeterId());
                }
                if(warningWellCover.getSurfaceDistanceWarning() != null){
                    ruleService.cancelWellCoverWarning(wellcover,"surfaceDistance",warningWellCover.getAmmeterId());
                }
                if(warningWellCover.getTiltSensorWarning() != null){
                    wellcover.setTiltSensor(wellcover.getTiltSensor().substring(1,2));
                    ruleService.cancelWellCoverWarning(wellcover,"tiltSensor",warningWellCover.getAmmeterId());
                }
                if(warningWellCover.getWaterLevelSensorWarning() != null){
                    wellcover.setWaterLevelSensor(wellcover.getWaterLevelSensor().substring(1,2));
                    ruleService.cancelWellCoverWarning(wellcover,"waterLevelSensor",warningWellCover.getAmmeterId());
                }
            }
        }
    }

    public Page<AmmeterWarningResult> queryWarningList(WarningCondition condition){
        PageHelper.startPage(condition.getPageIndex(),condition.getPageSize());
        return warningMapper.queryWarningList(condition);
    }

    public Integer updateWarning(AmmeterWarning record){
        return warningMapper.updateByPrimaryKeySelective(record);
    }

    public AmmeterWarning selectWarningByKey(Integer warningId){
        return warningMapper.selectByPrimaryKey(warningId);
    }

    public WarningHome reportWarningInfo() throws Exception {
        WarningHome home = warningMapper.getStatisticsDevices();
        Map<String,Object> warningInfo = warningMapper.reportWarningCount();
        home.setCurrentWarnings(Integer.parseInt(warningInfo.get("currentWarnings").toString()) );
        home.setHistoryWarnings(Integer.parseInt(warningInfo.get("historyWarnings").toString()));
        home.setWarningNumbers(Integer.parseInt(warningInfo.get("warningNumbers").toString()));
        Map<String,Object> warningDevices = warningMapper.reportWarningDevices();
        WarningCategory warningCategories = new WarningCategory();
        warningCategories.setTotalDevices(Integer.parseInt(warningDevices.get("totalDevices").toString()));
        warningCategories.setNormalDevices(Integer.parseInt(warningDevices.get("normalDevices").toString()));
        warningCategories.setWarningRsrqDevices(Integer.parseInt(warningDevices.get("warningRsrqDevices").toString()));
        warningCategories.setWarningOfflineDevices(Integer.parseInt(warningDevices.get("warningOfflineDevices").toString()));
        home.setWarningCategories(warningCategories);
        return home;

    }

    public WarningDeviceHome reportWarningDeviceInfo() throws Exception{
        WarningDeviceHome  home = new WarningDeviceHome();
        Map<String,Object> warningInfo = warningMapper.reportWarningCount();
        home.setCurrentWarnings(Integer.parseInt(warningInfo.get("currentWarnings").toString()) );
        home.setHistoryWarnings(Integer.parseInt(warningInfo.get("historyWarnings").toString()));
        home.setWarningNumbers(Integer.parseInt(warningInfo.get("warningNumbers").toString()));
        Map<String,Object> warningDevices = warningMapper.reportWarningSmartDevices();
        WarningDeviceCategory category = new WarningDeviceCategory();
        home.setTotalDevices(Integer.parseInt(warningDevices.get("totalDevices").toString()));
        home.setOnlineDevices(Integer.parseInt(warningDevices.get("onlineDevices").toString()));
        category.setTotalDevices(Integer.parseInt(warningDevices.get("totalDevices").toString()));
        category.setNormalDevices(Integer.parseInt(warningDevices.get("normalDevices").toString()));
        category.setBatteryWarningDevices(Integer.parseInt(warningDevices.get("batteryWarningDevices").toString()));
        category.setSensorWarningDevices(Integer.parseInt(warningDevices.get("sensorWarningDevices").toString()));
        category.setSurfaceDistanceWarningDevices(Integer.parseInt(warningDevices.get("surfaceDistanceWarningDevices").toString()));
        category.setTiltSensorWarningDevices(Integer.parseInt(warningDevices.get("tiltSensorWarningDevices").toString()));
        category.setWaterLevelSensorWarningDevices(Integer.parseInt(warningDevices.get("waterLevelSensorWarningDevices").toString()));
        home.setWarningCategories(category);
        return home;
    }

}
