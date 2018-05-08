package com.kashuo.kcp.dao;

import com.kashuo.kcp.domain.AmmeterCommandHistory;

public interface AmmeterCommandHistoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AmmeterCommandHistory record);

    int insertSelective(AmmeterCommandHistory record);

    AmmeterCommandHistory selectByCommandIdAndDeviceId(AmmeterCommandHistory record);

    AmmeterCommandHistory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AmmeterCommandHistory record);

    int updateStatusByCommandId(AmmeterCommandHistory record);

    int updateStatusBySubscrible(AmmeterCommandHistory record);

    int updateByPrimaryKey(AmmeterCommandHistory record);
}