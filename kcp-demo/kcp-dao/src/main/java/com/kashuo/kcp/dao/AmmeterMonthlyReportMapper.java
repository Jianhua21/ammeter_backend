package com.kashuo.kcp.dao;


import com.kashuo.kcp.domain.AmmeterMonthlyReport;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AmmeterMonthlyReportMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AmmeterMonthlyReport record);

    int batchInsert(@Param("list") List<AmmeterMonthlyReport> list);

    int insertSelective(AmmeterMonthlyReport record);

    AmmeterMonthlyReport selectByPrimaryKey(Integer id);

    List<AmmeterMonthlyReport> queryByParams(AmmeterMonthlyReport record);

    int updateByPrimaryKeySelective(AmmeterMonthlyReport record);

    int updateByPrimaryKey(AmmeterMonthlyReport record);
}