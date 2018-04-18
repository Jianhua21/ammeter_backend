package com.kashuo.kcp.dao;

import com.kashuo.kcp.domain.AmmeterRule;

import java.util.List;

public interface AmmeterRuleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AmmeterRule record);

    int insertSelective(AmmeterRule record);

    AmmeterRule selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AmmeterRule record);

    int updateByPrimaryKey(AmmeterRule record);

    List<AmmeterRule>  getNetWorkRules();
}