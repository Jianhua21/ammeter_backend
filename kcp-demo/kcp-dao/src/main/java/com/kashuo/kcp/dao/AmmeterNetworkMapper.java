package com.kashuo.kcp.dao;

import com.kashuo.common.base.domain.Page;
import com.kashuo.kcp.dao.condition.AmmeterNetWorkCondition;
import com.kashuo.kcp.domain.AmmeterNetWorkResult;
import com.kashuo.kcp.domain.AmmeterNetwork;

import java.util.List;

public interface AmmeterNetworkMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AmmeterNetwork record);

    int insertSelective(AmmeterNetwork record);

    AmmeterNetwork selectByPrimaryKey(Integer id);

    AmmeterNetwork selectByAmmeterId(Integer id);

    List<AmmeterNetwork> queryNetWorkParams(AmmeterNetwork network);

    Page<AmmeterNetWorkResult> networkList(AmmeterNetWorkCondition condition);

    List<AmmeterNetwork> selectForWarningReport();

    int updateByPrimaryKeySelective(AmmeterNetwork record);

    int updateByPrimaryKey(AmmeterNetwork record);

    int updateStatusByPrimaryKey(Integer id);
}