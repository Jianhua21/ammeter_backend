package com.kashuo.kcp.dao;

import com.kashuo.kcp.dao.result.AmmeterIMEIResult;
import com.kashuo.kcp.domain.AmmeterImei;
import com.kashuo.kcp.domain.AmmeterImeiKey;

import java.util.List;

public interface AmmeterImeiMapper {
    int deleteByPrimaryKey(String key);

    int insert(AmmeterImei record);

    int insertSelective(AmmeterImei record);

    AmmeterImei selectByPrimaryKey(String key);

    List<AmmeterIMEIResult> queryImeiByCondition(AmmeterImei condition);

    int updateByPrimaryKeySelective(AmmeterImei record);

    int updateByPrimaryKey(AmmeterImei record);
}