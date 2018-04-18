package com.kashuo.kcp.dao;


import com.kashuo.common.base.domain.Page;
import com.kashuo.kcp.dao.condition.AmmeterCondition;
import com.kashuo.kcp.domain.AmmeterDevice;
import com.kashuo.kcp.domain.AmmeterInfoResult;
import com.kashuo.kcp.domain.AmmeterResult;

public interface AmmeterDeviceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AmmeterDevice record);

    int insertSelective(AmmeterDevice record);

    AmmeterDevice selectByPrimaryKey(Integer id);

    AmmeterDevice selectByImsiKey(String imsi);

    AmmeterDevice selectByAmmeterNumber(String ammeterNumber);

    Page<AmmeterInfoResult> selectAmmeterInfo(AmmeterCondition condition);

    int updateByPrimaryKeySelective(AmmeterDevice record);

    int updateByImsiKeySelective(AmmeterDevice record);

    int updateByPrimaryKey(AmmeterDevice record);

    Page<AmmeterResult> selectByCondition();
}