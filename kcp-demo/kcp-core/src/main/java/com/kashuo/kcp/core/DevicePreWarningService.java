package com.kashuo.kcp.core;

import com.kashuo.kcp.dao.DevicePreWarningMapper;
import com.kashuo.kcp.domain.AmmeterPosition;
import com.kashuo.kcp.domain.AmmeterRule;
import com.kashuo.kcp.domain.DevicePreWarning;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by dell-pc on 2019/4/2.
 */
@Service
public class DevicePreWarningService {

    @Autowired
    private DevicePreWarningMapper devicePreWarningMapper;

    public void updateDevicePreWarning(AmmeterPosition position, AmmeterRule rule){
        DevicePreWarning devicePreWarning = new DevicePreWarning();
        devicePreWarning.setImei(position.getImei());
        devicePreWarning.setRuleId(rule.getId());
        devicePreWarning = devicePreWarningMapper.queryDevicePreWarning(devicePreWarning);
        if(devicePreWarning != null){
            devicePreWarning.setUpdateTime(new Date());
            devicePreWarningMapper.updateByPrimaryKeySelective(devicePreWarning);
        }else{
            devicePreWarning = new DevicePreWarning();
            devicePreWarning.setImei(position.getImei());
            devicePreWarning.setRuleId(rule.getId());
            devicePreWarning.setCreateBy("system");
            devicePreWarning.setCreateTime(new Date());
            devicePreWarning.setUpdateBy("system");
            devicePreWarning.setUpdateTime(new Date());
            devicePreWarning.setRemark(rule.getRuleDesc());
            devicePreWarningMapper.insert(devicePreWarning);
        }
    }

    public void deleteDevicePreWarning(String imei,Integer ruleId){
        DevicePreWarning devicePreWarning = new DevicePreWarning();
        devicePreWarning.setImei(imei);
        devicePreWarning.setRuleId(ruleId);
        devicePreWarningMapper.deleteByCondition(devicePreWarning);
    }

    public List<DevicePreWarning> queryDevicePreWarningList(){
        return devicePreWarningMapper.queryDevicePreWarningList();
    }
}
