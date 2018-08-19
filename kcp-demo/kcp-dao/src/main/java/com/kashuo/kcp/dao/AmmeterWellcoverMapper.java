package com.kashuo.kcp.dao;

import com.kashuo.kcp.domain.AmmeterWellcover;

public interface AmmeterWellcoverMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AmmeterWellcover record);

    int insertHistory(AmmeterWellcover record);

    int insertSelective(AmmeterWellcover record);

    AmmeterWellcover selectByPrimaryKey(Integer id);

    AmmeterWellcover selectByPositionId(Integer id);

    int updateByPrimaryKeySelective(AmmeterWellcover record);

    int updateByPrimaryKey(AmmeterWellcover record);
}