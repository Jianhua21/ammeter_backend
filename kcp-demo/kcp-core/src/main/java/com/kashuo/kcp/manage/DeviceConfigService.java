package com.kashuo.kcp.manage;

import com.kashuo.kcp.dao.AmmeterConfigMapper;
import com.kashuo.kcp.dao.AmmeterMsgContactMapper;
import com.kashuo.kcp.domain.AmmeterConfig;
import com.kashuo.kcp.domain.AmmeterMsgContact;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by dell-pc on 2018/5/8.
 */
@Service
public class DeviceConfigService {

    @Autowired
    private AmmeterConfigMapper configMapper;

    @Autowired
    private AmmeterMsgContactMapper msgContactMapper;

    public AmmeterConfig selectByPositionId(Integer positionId){
        return configMapper.selectByPositionId(positionId);
    }

    public Integer insertConfig(AmmeterConfig config){
        return configMapper.insertSelective(config);
    }

    public Integer updateConfig(AmmeterConfig config){
        return configMapper.updateByPrimaryKeySelective(config);
    }

    public AmmeterMsgContact getMsgInfoByCondition(Integer channelId,Integer projectId){
        return msgContactMapper.getMsgInfoByCondition(channelId,projectId);
    }

    public Integer updateMsgInfoByCondition(AmmeterMsgContact contact){
        return  msgContactMapper.updateByPrimaryKeySelective(contact);
    }
    public Integer insertMsgInfo(AmmeterMsgContact contact){
        return  msgContactMapper.insert(contact);
    }


}
