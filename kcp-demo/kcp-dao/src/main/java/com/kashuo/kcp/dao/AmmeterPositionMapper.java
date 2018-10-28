package com.kashuo.kcp.dao;

import com.kashuo.common.base.domain.Page;
import com.kashuo.kcp.dao.condition.AmmeterPositionCondition;
import com.kashuo.kcp.dao.result.PosotionHome;
import com.kashuo.kcp.domain.AmmeterPosition;

import java.util.List;


public interface AmmeterPositionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AmmeterPosition record);

    int insertSelective(AmmeterPosition record);

    AmmeterPosition selectByPrimaryKey(Integer id);

    AmmeterPosition selectByImei(String imei);

    AmmeterPosition selectActiveByImei(String imei);

    AmmeterPosition selectByDeviceId(String deviceId);

    List<AmmeterPosition> selectPositionByStatus(Integer status);

    int updateByPrimaryKeySelective(AmmeterPosition record);

    int updateByPrimaryKey(AmmeterPosition record);

    int updateStatusByDeviceId(AmmeterPosition record);

    int updateStatusByImei(AmmeterPosition record);

    Page<AmmeterPosition> getPositionList(AmmeterPositionCondition condition);

    Page<PosotionHome> getGISList(AmmeterPositionCondition condition);
}