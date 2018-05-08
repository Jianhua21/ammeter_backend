package com.kashuo.kcp.dao;

import com.kashuo.kcp.domain.AmmeterReport;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AmmeterReportMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AmmeterReport record);

    int insertSelective(AmmeterReport record);

    AmmeterReport selectByPrimaryKey(Integer id);

    AmmeterReport queryMaxDailyReportByAmmeterId(@Param("ammeterId") Integer ammeterId, @Param("date") String date);

    AmmeterReport queryMaxDailyReportByCondition(@Param("ammeterId") Integer ammeterId, @Param("date") String date,
    @Param("hour") Integer hour);

    AmmeterReport queryByParams(AmmeterReport record);

    List<AmmeterReport> dailyReportByParams(AmmeterReport record);

    int updateByPrimaryKeySelective(AmmeterReport record);

    int updateByPrimaryKey(AmmeterReport record);
}