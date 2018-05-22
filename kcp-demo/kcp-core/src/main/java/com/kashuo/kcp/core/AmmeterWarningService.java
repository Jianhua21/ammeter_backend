package com.kashuo.kcp.core;

import com.alibaba.fastjson.JSONObject;
import com.kashuo.common.base.domain.Page;
import com.kashuo.common.mybatis.helper.PageHelper;
import com.kashuo.kcp.dao.AmmeterNetworkMapper;
import com.kashuo.kcp.dao.AmmeterPositionMapper;
import com.kashuo.kcp.dao.AmmeterWarningMapper;
import com.kashuo.kcp.dao.condition.WarningCondition;
import com.kashuo.kcp.dao.result.WarningCategory;
import com.kashuo.kcp.dao.result.WarningHome;
import com.kashuo.kcp.domain.AmmeterNetwork;
import com.kashuo.kcp.domain.AmmeterPosition;
import com.kashuo.kcp.domain.AmmeterWarning;
import com.kashuo.kcp.domain.AmmeterWarningResult;
import com.kashuo.kcp.utils.BeanUtils;
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

    public void updateWarningInfo(){
        List<AmmeterNetwork> networks = networkMapper.selectForWarningReport();
        for (AmmeterNetwork network:networks){
            if(network.getAmmeterId() != null ) {
                ruleService.generateNetWorkWarningInfo(network);
            }
        }

        List<AmmeterNetwork> offline_networks = networkMapper.selectOfflineDevice();
        offline_networks.forEach(n-> {
                ruleService.offLineDeviceWarning(n);
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

}
