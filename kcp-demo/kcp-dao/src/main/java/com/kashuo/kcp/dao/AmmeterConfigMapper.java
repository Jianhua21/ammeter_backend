package com.kashuo.kcp.dao;

import com.kashuo.kcp.domain.AmmeterConfig;

public interface AmmeterConfigMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AmmeterConfig record);

    int insertSelective(AmmeterConfig record);

    AmmeterConfig selectByPrimaryKey(Integer id);

    AmmeterConfig selectByPositionId(Integer positionId);

    int updateByPrimaryKeySelective(AmmeterConfig record);

    int updateStatusByPositionKeySelective(AmmeterConfig record);

    int updateByPrimaryKey(AmmeterConfig record);
}