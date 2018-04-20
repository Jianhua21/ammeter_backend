package com.kashuo.kcp.dao;

import com.kashuo.common.base.domain.Page;
import com.kashuo.kcp.dao.condition.AmmeterPositionCondition;
import com.kashuo.kcp.domain.AmmeterPosition;


public interface AmmeterPositionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AmmeterPosition record);

    int insertSelective(AmmeterPosition record);

    AmmeterPosition selectByPrimaryKey(Integer id);

    AmmeterPosition selectByDeviceId(String deviceId);

    int updateByPrimaryKeySelective(AmmeterPosition record);

    int updateByPrimaryKey(AmmeterPosition record);

    Page<AmmeterPosition> getPositionList(AmmeterPositionCondition condition);
}