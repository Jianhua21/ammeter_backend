package com.kashuo.kcp.rpc.controller;

import com.alibaba.fastjson.JSON;
import com.kashuo.kcp.core.AmmeterDepartmentService;
import com.kashuo.kcp.domain.AmmeterDepartment;
import com.kashuo.kcp.domain.AmmeterUser;
import com.kashuo.kcp.utils.Results;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 部门
 * Created by Mr.ZHAO on 2016/5/20.
 */
@RestController
@Api(description = "部门管理")
@RequestMapping(value = "/department", method = RequestMethod.POST)
public class DepartmentController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(DepartmentController.class);

    @Autowired
    private AmmeterDepartmentService service;
//
//    @Autowired
//    private ChannelService channelService;


//    @RequestMapping(value = "list", method = RequestMethod.GET)
//    public Results list() {
//        AmmeterUser curuser = getCuruser();
//        Integer channelId = curuser == null ? 0 : curuser.getChannelId();
//        List<AmmeterDepartment> deptList = service.selectDepartmentByChannelId(channelId);
//        Channel channel = channelService.selectByPrimaryKey(channelId);
//        String displayName = channel == null ? "root" : channel.getChannelName();
//        DepartmentTree rootTree = new DepartmentTree("", displayName);
//        rootTree.setStatus(channel == null ? 1 : channel.getStatus());
//        rootTree.setTotalNum(deptList.size() + 1);
//        rootTree.buildTree(deptList);
//        return Results.success(rootTree);
//    }


    @RequestMapping(value = "add")
    @ApiOperation("部门新增")
    public Results add(@RequestBody AmmeterDepartment condition) {

        String departmentName = condition.getDepartmentName();
        if (departmentName == null || "".equals(departmentName.trim())) {
            return Results.error("部门名称不能为空！");
        }
        AmmeterUser curuser = getCuruser();
        AmmeterDepartment department = service.checkDepartmentName(departmentName, curuser.getChannelId());
        if (department != null) {
            return Results.error(-2,"部门名称已经存在");
        }
        condition.setCreateBy(curuser.getId().toString());
        if (condition.getChannelId() == null) {
            condition.setChannelId(curuser.getChannelId());
        } else {
            condition.setChannelId(condition.getChannelId());
        }
        service.insert(condition);
        return Results.success("添加成功", condition.getId());
    }

    @RequestMapping(value = "edit")
    @ApiOperation("部门编辑")
    public Results edit(@RequestBody AmmeterDepartment condition) {
        return edit(condition,false);
    }

    public Results edit(AmmeterDepartment condition,boolean flag){
        AmmeterDepartment department = service.selectByPrimaryKey(condition.getId());
        if (department == null) {
            return Results.error("not found");
        }
        logger.info("验证部门信息"+ JSON.toJSONString(condition) +" flag:"+ flag);
        AmmeterDepartment exists = service.checkDepartmentName(condition.getDepartmentName(), flag ? condition.getChannelId() : getCuruser().getChannelId());
        if (exists != null && department.getId().intValue() != exists.getId().intValue()) {
            return Results.error(-2,"部门名称已经存在");
        }

        // 状态变为停用 子节点状态update
        if (!(department.getStatus().equals(condition.getStatus())) && condition.getStatus() == 0) {
            service.updateSubDepartmentStatus(department.getLevelCode());
        }
        condition.setCreateBy(department.getCreateBy());
        service.updateByPrimaryKey(condition);
        Map<String, String> map = new HashMap<>();
        map.put("levelCode", department.getLevelCode());
        map.put("newLevelCode", condition.getLevelCode());
        service.updateSubDepartMent(map);
        return Results.success("修改成功");
    }

    @RequestMapping(value = "view/{departmentId}")
    @ApiOperation("部门查看")
    public Results view(@PathVariable Integer departmentId) {
        AmmeterDepartment department = service.selectByPrimaryKey(departmentId);
        return Results.success(department);
    }

}
