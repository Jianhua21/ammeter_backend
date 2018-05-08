package com.kashuo.kcp.dao;

import com.kashuo.kcp.domain.AmmeterLoginHistory;

public interface AmmeterLoginHistoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AmmeterLoginHistory record);

    int insertSelective(AmmeterLoginHistory record);

    AmmeterLoginHistory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AmmeterLoginHistory record);

    int updateByPrimaryKey(AmmeterLoginHistory record);
}