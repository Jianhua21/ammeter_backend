package com.kashuo.kcp.core;

import com.alibaba.fastjson.JSONObject;
import com.kashuo.common.base.domain.Page;
import com.kashuo.common.mybatis.helper.PageHelper;
import com.kashuo.kcp.dao.*;
import com.kashuo.kcp.dao.condition.WarningCondition;
import com.kashuo.kcp.dao.result.*;
import com.kashuo.kcp.domain.*;
import com.kashuo.kcp.manage.DeviceConfigService;
import com.kashuo.kcp.utils.BeanUtils;
import com.kashuo.kcp.utils.MessageUtils;
import com.kashuo.kcp.utils.StringUtil;
import com.kashuo.kcp.utils.StringUtils;
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
    private AmmeterDeviceMapper deviceMapper;
    @Autowired
    private DeviceConfigService deviceConfigService;


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
                //发送短信提醒
                deviceConfigService.sendMsgInfoBySMS(p,"未上电",1);

                ruleService.offLineDeviceWarning(n,true);
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

//    public void genereateWellCoverWarning(){
//        List<WarningElectric> list = warningMapper.wellCoverWarningList(null);
//        if(list != null){
//            for (WarningElectric warningElectric :list){
//                AmmeterWellcover wellcover = wellcoverMapper.selectByPositionId(warningWellCover.getId());
//
//                if(warningElectric.getRsrqWarning() == null){
//                    ruleService.checkWellCoverWarning(wellcover,"rsrq",warningElectric.getAmmeterId());
//                }
//                if(warningElectric.getCurrentLimitWarning() == null){
//                    ruleService.checkWellCoverWarning(wellcover,"currentLimit",warningElectric.getAmmeterId());
//                }
//                if(warningElectric.getElectricLimitWarning() == null){
//                    ruleService.checkWellCoverWarning(wellcover,"electricLimit",warningElectric.getAmmeterId());
//                }
//
//            }
//        }
//    }
    public void cancelWellCoverWarning(Integer positionId,AmmeterWellcover wellcover){
        cancelWellCoverWarning(positionId,0, wellcover);
    }


    public void cancelWellCoverWarning(Integer positionId,Integer deviceType,AmmeterWellcover wellcover){
        WarningCondition condition = new WarningCondition();
        condition.setPositionId(positionId);
        List<WarningElectric> list = warningMapper.wellCoverWarningList(condition);
        if(list != null){
            for (WarningElectric warningElectric :list){
                if(warningElectric.getRsrqWarning() != null){
                    if(wellcover.getRsrq() != null) {
                        ruleService.cancelWellCoverWarning(wellcover, "rsrq", warningElectric.getAmmeterId());
                    }
                }else{
                    if(wellcover.getRsrq() != null) {
                        ruleService.checkWellCoverWarning(wellcover, "rsrq", warningElectric.getAmmeterId());
                    }
                }
                if(warningElectric.getCurrentLimitWarning() != null){
//                    ruleService.cancelWellCoverWarning(wellcover,"sensor",warningWellCover.getAmmeterId());
                }else{
                    if(wellcover.getCurrentLimit() != null) {
                        ruleService.checkWellCoverWarning(wellcover, "currentLimit", warningElectric.getAmmeterId());
                    }
                }
                if(warningElectric.getElectricLimitWarning() != null){
//                    ruleService.cancelWellCoverWarning(wellcover,"surfaceDistance",warningWellCover.getAmmeterId());
                }else{
                    if(wellcover.getElectricLimit() != null) {
                        ruleService.checkWellCoverWarning(wellcover, "electricLimit", warningElectric.getAmmeterId());
                    }
                }
            }
        }
    }

    public void updateOfflineDeviceStatus(){
        List<AmmeterDeviceResult> results = deviceMapper.queryOfflineDevice();
        if(results != null){
            results.forEach(r->{
                AmmeterPosition position = ammeterPositionMapper.selectByDeviceId(r.getDeviceId());
                position.setStatus(7);
                ammeterPositionMapper.updateByPrimaryKeySelective(position);
            });
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
        if(home != null) {
            home.setCurrentWarnings(Integer.parseInt(warningInfo.get("currentWarnings").toString()));
            home.setHistoryWarnings(Integer.parseInt(warningInfo.get("historyWarnings").toString()));
            home.setWarningNumbers(Integer.parseInt(warningInfo.get("warningNumbers").toString()));

            Map<String,Object> warningDevices = warningMapper.reportWarningDevices();
            WarningCategory warningCategories = new WarningCategory();
            if(warningDevices != null) {
                warningCategories.setTotalDevices(Integer.parseInt(warningDevices.get("totalDevices").toString()));
                warningCategories.setNormalDevices(Integer.parseInt(warningDevices.get("normalDevices").toString()));
                warningCategories.setWarningRsrqDevices(Integer.parseInt(warningDevices.get("warningRsrqDevices").toString()));
                warningCategories.setWarningOfflineDevices(Integer.parseInt(warningDevices.get("warningOfflineDevices").toString()));
                warningCategories.setCurrentLimitDevices(Integer.parseInt(warningDevices.get("currentLimitDevices").toString()));
                warningCategories.setElectricLimitDevices(Integer.parseInt(warningDevices.get("electricLimitDevices").toString()));
            }
            home.setWarningCategories(warningCategories);
        }
        return home;

    }

}
