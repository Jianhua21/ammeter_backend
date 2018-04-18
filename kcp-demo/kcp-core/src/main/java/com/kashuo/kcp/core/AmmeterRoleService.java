package com.kashuo.kcp.core;

import com.kashuo.common.base.domain.Page;
import com.kashuo.common.mybatis.helper.PageHelper;
import com.kashuo.kcp.dao.AmmeterRoleMapper;
import com.kashuo.kcp.dao.condition.RoleCondition;
import com.kashuo.kcp.domain.AmmeterRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色
 * Created by Mr.ZHAO on 2016/5/17.
 */
@Service
public class AmmeterRoleService {

    @Autowired
    private AmmeterRoleMapper roleMapper;

    public int deleteByPrimaryKey(Integer id) {
        return roleMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(AmmeterRole record) {
        return roleMapper.updateByPrimaryKeySelective(record);
    }

    public AmmeterRole selectByChannelIdAndName(Integer channelId, String name) {
        return roleMapper.selectByChannelIdAndName(channelId, name);
    }

    public int insert(AmmeterRole record) {
        return roleMapper.insert(record);
    }

    public int insertSelective(AmmeterRole record) {
        return roleMapper.insertSelective(record);
    }

    public AmmeterRole selectByPrimaryKey(Integer id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKey(AmmeterRole record) {
        return roleMapper.updateByPrimaryKey(record);
    }

    /**
     * 分页列表
     */
    public Page<?> selectListSelective(RoleCondition condition) {
        PageHelper.startPage(condition.getPageIndex(), condition.getPageSize());
        return roleMapper.selectListSelective(condition);
    }

    /**
     * 渠道商管理角色列表
     */
    public Page<?> selectRoleByChannel(RoleCondition condition) {
        PageHelper.startPage(condition.getPageIndex(), condition.getPageSize());
        return roleMapper.selectRoleByChannel(condition);
    }

    /**
     * 根据实体条件查询角色列表
     */
    public List<AmmeterRole> selectRoleListByChannelId(Integer channelId) {
        channelId = channelId == null ? 0 : channelId;
        return roleMapper.selectRoleListByChannelId(channelId);
    }
}
