package com.kashuo.kcp.rpc.controller;

import com.kashuo.common.base.domain.Page;
import com.kashuo.kcp.core.AmmeterRoleService;
import com.kashuo.kcp.dao.condition.RoleCondition;
import com.kashuo.kcp.domain.AmmeterRole;
import com.kashuo.kcp.utils.Results;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 角色管理
 * Created by Mr.Legend on 2016/5/17.
 */
@RestController
@Api(description = "角色管理")
@RequestMapping(value = "/role", method = RequestMethod.POST)
public class RoleController extends BaseController {

    @Autowired
    private AmmeterRoleService roleService;

    @RequestMapping(value = "list")
    @ApiOperation(value="角色列表")
    public Results list(@RequestBody RoleCondition condition) {
        if (condition.getChannelId() == null) {
            condition.setChannelId(getCuruser().getChannelId());
        }
        Page<?> roleList = roleService.selectListSelective(condition);
        return Results.success(roleList);
    }

    @RequestMapping(value = "add")
    @ApiOperation(value="角色新增")
    public Results add(@RequestBody RoleCondition condition) {
        Integer channelId = getCuruser().getChannelId();
        AmmeterRole exists = roleService.selectByChannelIdAndName(channelId, condition.getName());
        if (exists != null) {
            return Results.error(-2,"角色名已经存在");
        }
        AmmeterRole role = new AmmeterRole();
        role.setRoleName(condition.getName());
        role.setDescription(condition.getDescription());
        role.setOwner(getCuruserId().toString());
        role.setChannelId(channelId);
        roleService.insert(role);
        return Results.success("添加成功");
    }

    @RequestMapping(value = "edit")
    @ApiOperation(value="角色编辑")
    public Results edit(@RequestBody RoleCondition condition) {
        Integer channelId = getCuruser().getChannelId();
        AmmeterRole role = roleService.selectByPrimaryKey(condition.getId());
        if (role != null) {
            AmmeterRole exists = roleService.selectByChannelIdAndName(channelId, condition.getName());
            if (exists != null && !exists.getId().equals(role.getId())) {
                return Results.error(-2,"角色名已经存在");
            }
            role.setRoleName(condition.getName());
            role.setDescription(condition.getDescription());
            role.setOwner(getCuruserId().toString());
            roleService.updateByPrimaryKey(role);
            return Results.success("修改成功");
        } else {
            return Results.error("not found");
        }
    }
}
