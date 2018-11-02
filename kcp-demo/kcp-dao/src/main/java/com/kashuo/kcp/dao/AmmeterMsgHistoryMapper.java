package com.kashuo.kcp.dao;

import com.kashuo.kcp.domain.AmmeterMsgHistory;

public interface AmmeterMsgHistoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AmmeterMsgHistory record);

    int insertSelective(AmmeterMsgHistory record);

    AmmeterMsgHistory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AmmeterMsgHistory record);

    int updateByPrimaryKey(AmmeterMsgHistory record);
}