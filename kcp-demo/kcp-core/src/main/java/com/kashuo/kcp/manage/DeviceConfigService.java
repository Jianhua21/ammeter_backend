package com.kashuo.kcp.manage;

import com.kashuo.kcp.dao.AmmeterConfigMapper;
import com.kashuo.kcp.domain.AmmeterConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by dell-pc on 2018/5/8.
 */
@Service
public class DeviceConfigService {

    @Autowired
    private AmmeterConfigMapper configMapper;

    public AmmeterConfig selectByPositionId(Integer positionId){
        return configMapper.selectByPositionId(positionId);
    }

    public Integer insertConfig(AmmeterConfig config){
        return configMapper.insertSelective(config);
    }

    public Integer updateConfig(AmmeterConfig config){
        return configMapper.updateByPrimaryKeySelective(config);
    }


}
