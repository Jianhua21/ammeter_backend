package com.kashuo.kcp.dao;

import com.kashuo.common.base.domain.Page;
import com.kashuo.kcp.dao.condition.RoleCondition;
import com.kashuo.kcp.domain.AmmeterRole;

import java.util.List;


public interface AmmeterRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AmmeterRole record);

    int insertSelective(AmmeterRole record);

    AmmeterRole selectByPrimaryKey(Integer id);

    AmmeterRole selectByChannelIdAndName(Integer channelId, String name);

    int updateByPrimaryKeySelective(AmmeterRole record);

    int updateByPrimaryKey(AmmeterRole record);

    Page<?> selectListSelective(RoleCondition condition);

    Page<?> selectRoleByChannel(RoleCondition condition);

    /*
    * 根据实体条件查询角色列表
    * */
    List<AmmeterRole> selectRoleListByChannelId(Integer channelId);
}