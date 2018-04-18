package com.kashuo.kcp.dao;

import com.kashuo.kcp.domain.AmmeterAuth;

public interface AmmeterAuthMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AmmeterAuth record);

    int insertSelective(AmmeterAuth record);

    AmmeterAuth selectByPrimaryKey(Integer id);

    AmmeterAuth selectAuthDetail();

    int updateByPrimaryKeySelective(AmmeterAuth record);

    int updateByPrimaryKey(AmmeterAuth record);
}