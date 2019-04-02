package com.kashuo.kcp.dao;

import com.kashuo.kcp.domain.DevicePreWarning;

import java.util.List;

public interface DevicePreWarningMapper {
    int deleteByPrimaryKey(Integer id);

    int deleteByCondition(DevicePreWarning record);

    int insert(DevicePreWarning record);

    int insertSelective(DevicePreWarning record);

    DevicePreWarning selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DevicePreWarning record);

    int updateByPrimaryKey(DevicePreWarning record);

    DevicePreWarning queryDevicePreWarning(DevicePreWarning record);

    List<DevicePreWarning> queryDevicePreWarningList();
}