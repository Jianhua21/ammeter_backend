package com.kashuo.kcp.dao;

import com.kashuo.kcp.domain.SysPlatform;

import java.util.List;

public interface SysPlatformMapper {
    int deleteByPrimaryKey(String code);

    int insert(SysPlatform record);

    int insertSelective(SysPlatform record);

    SysPlatform selectByPrimaryKey(String code);

    int updateByPrimaryKeySelective(SysPlatform record);

    int updateByPrimaryKey(SysPlatform record);

    List<SysPlatform> queryAll();
}