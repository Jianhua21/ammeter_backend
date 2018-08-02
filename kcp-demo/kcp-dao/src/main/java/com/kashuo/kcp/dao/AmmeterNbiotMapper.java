package com.kashuo.kcp.dao;

import com.kashuo.kcp.domain.AmmeterNbiot;

import java.util.List;

public interface AmmeterNbiotMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AmmeterNbiot record);

    int insertSelective(AmmeterNbiot record);

    AmmeterNbiot selectByPrimaryKey(Integer id);

    List<AmmeterNbiot> queryAllNbiot();

    int updateByPrimaryKeySelective(AmmeterNbiot record);

    int updateByPrimaryKey(AmmeterNbiot record);
}