package com.kashuo.kcp.dao;

import com.kashuo.common.base.domain.Page;
import com.kashuo.kcp.domain.AmmeterCallbackHistory;

public interface AmmeterCallbackHistoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AmmeterCallbackHistory record);

    int insertSelective(AmmeterCallbackHistory record);

    AmmeterCallbackHistory selectByPrimaryKey(Integer id);

    Page<AmmeterCallbackHistory> getCallBackHistoryByDeviceId(String deviceId);

    int updateByPrimaryKeySelective(AmmeterCallbackHistory record);

    int updateByPrimaryKey(AmmeterCallbackHistory record);
}