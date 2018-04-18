package com.kashuo.kcp.dao;


import com.kashuo.kcp.domain.AmmeterMonthlyReport;

import java.util.List;

public interface AmmeterMonthlyReportMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AmmeterMonthlyReport record);

    int insertSelective(AmmeterMonthlyReport record);

    AmmeterMonthlyReport selectByPrimaryKey(Integer id);

    List<AmmeterMonthlyReport> queryByParams(AmmeterMonthlyReport record);

    int updateByPrimaryKeySelective(AmmeterMonthlyReport record);

    int updateByPrimaryKey(AmmeterMonthlyReport record);
}