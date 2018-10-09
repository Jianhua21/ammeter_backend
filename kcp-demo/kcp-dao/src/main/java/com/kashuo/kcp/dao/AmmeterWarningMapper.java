package com.kashuo.kcp.dao;

import com.kashuo.common.base.domain.Page;
import com.kashuo.kcp.dao.condition.WarningCondition;
import com.kashuo.kcp.dao.result.WarningDeviceHome;
import com.kashuo.kcp.dao.result.WarningHome;
import com.kashuo.kcp.dao.result.WarningWellCover;
import com.kashuo.kcp.domain.AmmeterWarning;
import com.kashuo.kcp.domain.AmmeterWarningResult;

import java.util.List;
import java.util.Map;

public interface AmmeterWarningMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AmmeterWarning record);

    int insertSelective(AmmeterWarning record);

    AmmeterWarning selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AmmeterWarning record);

    int updateByRuleKey(AmmeterWarning record);

    int updateByPrimaryKey(AmmeterWarning record);

    int updateStatusByType(AmmeterWarning record);

    WarningHome getStatisticsDevices();


    Map<String,Object> reportWarningDevices();

    Map<String,Object> reportWarningSmartDevices();

    Map<String,Object> reportWarningSmokeDevices();

    Map<String,Object> reportWarningCount(Integer deviceType);

    Page<AmmeterWarningResult> queryWarningList(WarningCondition condition);

    List<WarningWellCover> wellCoverWarningList(WarningCondition condition);
}