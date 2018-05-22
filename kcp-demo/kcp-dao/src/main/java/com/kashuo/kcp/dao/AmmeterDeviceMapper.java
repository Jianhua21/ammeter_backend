package com.kashuo.kcp.dao;


import com.kashuo.common.base.domain.Page;
import com.kashuo.kcp.dao.condition.AmmeterCondition;
import com.kashuo.kcp.dao.result.AmmeterDeviceResult;
import com.kashuo.kcp.domain.AmmeterDevice;
import com.kashuo.kcp.domain.AmmeterInfoResult;
import com.kashuo.kcp.domain.AmmeterResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AmmeterDeviceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AmmeterDevice record);

    int insertSelective(AmmeterDevice record);

    AmmeterDevice selectByPrimaryKey(Integer id);

    AmmeterDevice selectByImsiKey(String imsi);

    AmmeterDevice selectByAmmeterNumber(String ammeterNumber);

    AmmeterDevice selectByDeviceId(String deviceId);

    AmmeterDevice selectByPositionId(Integer positionId);

    Page<AmmeterInfoResult> selectAmmeterInfo(AmmeterCondition condition);

    List<AmmeterDeviceResult> checkAmmeterMeterNo();

    List<AmmeterDeviceResult> validAmmeterDevice();

    int updateByPrimaryKeySelective(AmmeterDevice record);

    int updateByImsiKeySelective(AmmeterDevice record);

    int updateByPrimaryKey(AmmeterDevice record);

    int updateWarningStatusByPrimaryKey(AmmeterDevice record);

    int updateMeterNoByDeviceId(@Param("deviceId")String deviceId,@Param("meterNo")String meterNo);

    Page<AmmeterResult> selectByCondition();
}