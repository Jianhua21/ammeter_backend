package com.kashuo.kcp.dao;

import com.kashuo.common.base.domain.Page;
import com.kashuo.kcp.dao.condition.WarningCondition;
import com.kashuo.kcp.dao.result.WarningHome;
import com.kashuo.kcp.domain.AmmeterWarning;
import com.kashuo.kcp.domain.AmmeterWarningResult;

import java.util.Map;

public interface AmmeterWarningMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AmmeterWarning record);

    int insertSelective(AmmeterWarning record);

    AmmeterWarning selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AmmeterWarning record);

    int updateByPrimaryKey(AmmeterWarning record);

    int updateStatusByType(AmmeterWarning record);

    WarningHome getStatisticsDevices();

    Map<String,Object> reportWarningDevices();

    Map<String,Object> reportWarningCount();

    Page<AmmeterWarningResult> queryWarningList(WarningCondition condition);
}