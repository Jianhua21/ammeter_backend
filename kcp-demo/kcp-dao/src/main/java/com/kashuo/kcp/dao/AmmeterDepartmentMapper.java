package com.kashuo.kcp.dao;

import com.kashuo.kcp.domain.AmmeterDepartment;

import java.util.List;
import java.util.Map;

public interface AmmeterDepartmentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AmmeterDepartment record);

    int insertSelective(AmmeterDepartment record);

    AmmeterDepartment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AmmeterDepartment record);

    int updateByPrimaryKey(AmmeterDepartment record);

    int updateSubDepartMent(Map map);

    int updateSubDepartmentStatus(String levelCode);

    AmmeterDepartment selectDepartmentByNameAndChannelId(String name, Integer channelId);

    /**
     * 根据用户ID和渠道商ID查询部门列表
     *
     * @param channelId 渠道商ID
     * @return 部门列表
     */
    List<AmmeterDepartment> selectDepartmentByChannelId(Integer channelId);
}