package com.kashuo.kcp.core;

import com.kashuo.common.base.domain.Page;
import com.kashuo.common.mybatis.helper.PageHelper;
import com.kashuo.kcp.dao.AmmeterPositionMapper;
import com.kashuo.kcp.dao.condition.AmmeterPositionCondition;
import com.kashuo.kcp.domain.AmmeterPosition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by dell-pc on 2018/3/20.
 */
@Service
public class AmmeterPositionService {

    private final static Logger logger = LoggerFactory.getLogger(AmmeterPositionService.class);

    @Autowired
    private AmmeterPositionMapper ammeterPositionMapper;

    public void insert(AmmeterPosition ammeterPosition){
        ammeterPositionMapper.insert(ammeterPosition);
    }

    public void updateByPrimaryKeySelective(AmmeterPosition ammeterPosition){
        ammeterPositionMapper.updateByPrimaryKey(ammeterPosition);
    }
    public void deleteAmmeterPosition(Integer id){
        ammeterPositionMapper.deleteByPrimaryKey(id);
    }

    public AmmeterPosition getAmmeterPositionInfo(Integer id){
        return ammeterPositionMapper.selectByPrimaryKey(id);
    }

    public AmmeterPosition selectByDeviceId(String deviceId){
        return ammeterPositionMapper.selectByDeviceId(deviceId);
    }

    public Page<AmmeterPosition> getPositionList(AmmeterPositionCondition condition){
        PageHelper.startPage(condition.getPageIndex(),condition.getPageSize());
        return ammeterPositionMapper.getPositionList(condition);
    }
}
