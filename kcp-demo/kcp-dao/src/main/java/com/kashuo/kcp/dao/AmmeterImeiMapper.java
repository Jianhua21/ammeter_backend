package com.kashuo.kcp.dao;

import com.kashuo.kcp.domain.AmmeterImei;
import com.kashuo.kcp.domain.AmmeterImeiKey;

public interface AmmeterImeiMapper {
    int deleteByPrimaryKey(AmmeterImeiKey key);

    int insert(AmmeterImei record);

    int insertSelective(AmmeterImei record);

    AmmeterImei selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(AmmeterImei record);

    int updateByPrimaryKey(AmmeterImei record);
}