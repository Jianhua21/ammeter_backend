package com.kashuo.kcp.core;

import com.kashuo.kcp.dao.AmmeterDepartmentMapper;
import com.kashuo.kcp.domain.AmmeterDepartment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 部门
 * Created by Mr.Legend on 2016/5/17.
 */
@Service
public class AmmeterDepartmentService {

    @Autowired
    private AmmeterDepartmentMapper departmentMapper;

    public int deleteByPrimaryKey(Integer id) {
        return departmentMapper.deleteByPrimaryKey(id);
    }

    public int insertSelective(AmmeterDepartment record) {
        return departmentMapper.insertSelective(record);
    }

    public AmmeterDepartment selectByPrimaryKey(Integer id) {
        return departmentMapper.selectByPrimaryKey(id);
    }

    public int insert(AmmeterDepartment record) {
        return departmentMapper.insert(record);
    }

    public int updateByPrimaryKeySelective(AmmeterDepartment record) {
        return departmentMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(AmmeterDepartment record) {
        return departmentMapper.updateByPrimaryKey(record);
    }

    public int updateSubDepartMent(Map map) {
        return departmentMapper.updateSubDepartMent(map);
    }

    public AmmeterDepartment checkDepartmentName(String departmentName, Integer channelId) {
        return departmentMapper.selectDepartmentByNameAndChannelId(departmentName, channelId);
    }

    public int updateSubDepartmentStatus(String levelCode) {
        return departmentMapper.updateSubDepartmentStatus(levelCode);
    }

    /**
     * 根据渠道商ID查询部门列表
     *
     * @param channelId 渠道商ID
     * @return 部门列表
     */
    public List<AmmeterDepartment> selectDepartmentByChannelId(Integer channelId) {
        channelId = channelId == null ? 0 : channelId;
        return departmentMapper.selectDepartmentByChannelId(channelId);
    }

}
