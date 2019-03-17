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

    AmmeterPosition selectByNumber(String number);

    List<AmmeterPosition> selectPositionByStatus(Integer status);

    List<AmmeterPosition> queryPositionByPlatform(Integer platform);

    int updateByPrimaryKeySelective(AmmeterPosition record);

    int updateAllByPrimaryKeySelective(AmmeterPosition record);

    int updateByPrimaryKey(AmmeterPosition record);

    int updateStatusByDeviceId(AmmeterPosition record);


    Page<AmmeterPosition> getPositionList(AmmeterPositionCondition condition);

    Page<PosotionHome> getGISList(AmmeterPositionCondition condition);
}