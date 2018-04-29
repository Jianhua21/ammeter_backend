package com.kashuo.kcp.rpc.controller;
import com.kashuo.common.base.domain.Page;
import com.kashuo.kcp.core.AmmeterChannelService;
import com.kashuo.kcp.core.AmmeterDepartmentService;
import com.kashuo.kcp.core.AmmeterRoleService;
import com.kashuo.kcp.core.AmmeterUserService;
import com.kashuo.kcp.dao.condition.AmmeterUserCondition;
import com.kashuo.kcp.dao.condition.ChannelTree;
import com.kashuo.kcp.dao.condition.DepartmentTree;
import com.kashuo.kcp.dao.condition.RoleCondition;
import com.kashuo.kcp.domain.AmmeterChannel;
import com.kashuo.kcp.domain.AmmeterDepartment;
import com.kashuo.kcp.domain.AmmeterRole;
import com.kashuo.kcp.domain.AmmeterUser;
import com.kashuo.kcp.utils.Results;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 渠道商管理
 *
 * @author fangzhong
 */
@RestController
@Api(description = "机构管理")
@RequestMapping(value = "/channel", method = RequestMethod.POST)
public class ChannelController extends BaseController {


    @Autowired
    private UserController userController;
    @Autowired
    private AmmeterChannelService channelService;
    @Autowired
    private AmmeterDepartmentService departmentService;
    @Autowired
    private AmmeterRoleService roleService;
    @Autowired
    private AmmeterUserService userService;
//    @Autowired
//    private BaseProviderService baseProviderService;
//    @Autowired
//    private BaseProviderService providerService;
    @Autowired
    private DepartmentController departmentController;
//    @Autowired
//    private CnapsBankCodeService cnapsBankCodeService;


    /**
     * 新增渠道商
     *
     * @return Results
     */
    @RequestMapping(value = "/addChannel", method = RequestMethod.POST)
    @ApiOperation("新增机构")
    public Results addChannel(@RequestBody AmmeterChannel channel) {
        if (channel == null || channel.getChannelName() == null || channel.getContactName() == null
                || channel.getMobilePhone() == null || channel.getChannelType() == null
                ) {
            return Results.error("必填信息不能为空");
        }

        AmmeterChannel c = channelService.checkChannelName(channel.getChannelName());
        if (c != null) {
            return Results.error(-2,"机构名称已经存在");
        }

        if (channel.getChannelType() != null && channel.getChannelType().byteValue() == 100) {
            channel.setChannelType(new Byte("0"));
        }
        //渠道商编号
        channel.setChannelCode(channel.getChannelCode());
        channel.setLevelCode(channel.getChannelCode());
        Map<String, Object> map = channelService.addChannel(channel);
        if ((int) map.get("code") == 0) {
            return Results.success("新增成功");
        } else {
            return Results.error(map.get("message") + "");
        }
    }

    /**
     * 更新渠道商
     *
     * @return Results
     */
    @RequestMapping(value = "/editChannel", method = RequestMethod.POST)
    @ApiOperation("更新机构")
    public Results editChannel(@RequestBody AmmeterChannel condition) {

        if (condition == null || condition.getChannelName() == null || condition.getContactName() == null || condition.getMobilePhone() == null) {
            return Results.error(-2,"渠道商信息不能为空");
        }

        AmmeterChannel channel = channelService.selectByPrimaryKey(condition.getId());
        if (channel == null) {
            return Results.error("not found");
        }

        if (!(channel.getChannelName().equals(condition.getChannelName()))) {
            AmmeterChannel c = channelService.checkChannelName(condition.getChannelName());
            if (c != null) {
                return Results.error(-2,"渠道商名称已经存在");
            }
        }
        if (condition.getChannelType() != null && condition.getChannelType().byteValue() == 100) {
            condition.setChannelType(new Byte("0"));
        }

        String newLevelCode = condition.getLevelCode();
        String oldLevelCode = channel.getLevelCode();

        if (newLevelCode.equals(oldLevelCode.substring(0, oldLevelCode.length() - 3))) {
            newLevelCode = oldLevelCode;
        } else {
            newLevelCode = "0512";
        }

        condition.setLevelCode(newLevelCode);

        // 状态变为停用 子节点状态update
        if (channel.getStatus() != null && !(channel.getStatus().equals(condition.getStatus())) && condition.getStatus() == 0) {
            channelService.updateSubChannelStatus(channel.getLevelCode());
        }
        channelService.editChannel(condition);
        Map<String, String> map = new HashMap<>();
        map.put("levelCode", channel.getLevelCode());
        map.put("newLevelCode", newLevelCode);
        //更新本身和子集渠道商
//        channelService.updateSubChannelList(map);
        return Results.success("更新成功");

    }

    /**
     * 查询渠道商列表
     *
     * @return Results
     */
    @RequestMapping(value = "/channelList", method = RequestMethod.POST)
    @ApiOperation("机构列表")
    public Results channelList(@RequestBody AmmeterChannel channelName) {
        ChannelTree rootTree = channelService.channelList(getCuruser());
        //输入框渠道商名称搜索
        if (channelName.getChannelName() != null && !"".equals(channelName.getChannelName())) {
            rootTree = channelService.getTreeChannelName(rootTree, channelName.getChannelName());
            return Results.success(rootTree);
        }
        return Results.success(rootTree);
    }

    /**
     * 渠道商下拉框列表
     *
     * @return Results
     */
    @RequestMapping(value = "/channelAffiliation", method = RequestMethod.GET)
    public Results channelAffiliation() {
        ChannelTree rootTree = channelService.channelList(getCuruser());
        List<AmmeterChannel> list = channelService.channelAffiliation(rootTree);
        return Results.success(list);
    }

//    /**
//     * 获取上级渠道商
//     */
//    @RequestMapping(value = "parentChannel/{levelCode}", method = RequestMethod.GET)
//    public Results parentChannel(@PathVariable String levelCode) {
//        Map channel = channelService.selectParentChannel(levelCode);
//        return Results.success(channel);
//    }

//    /**
//     * 获取上级渠道商角色列表
//     */
//    @RequestMapping(value = "parentChannelRole", method = RequestMethod.GET)
//    public Results parentChannelRole(@RequestParam(required = false) Integer channelId) {
//        if (channelId == null)
//            channelId = getCuruser() == null ? 0 : getCuruser().getChannelId();
//        List<Map> roleList = channelService.parentChannelRole(channelId);
//        return Results.success(roleList);
//    }


//    /**
//     * 获取渠道商增加时的渠道商编号
//     *
//     * @return Results
//     */
//    @RequestMapping(value = "/getChannelCodeByAdd", method = RequestMethod.GET)
//    public Results getChannelCodeByAdd() {
////        String code = baseProviderService.buildChannelCode();
//        return Results.success("0512");
//    }
//
//    /**
//     * 初始化部门名称下拉框
//     *
//     * @return Results
//     */
//    @RequestMapping(value = "/departMentNameList", method = RequestMethod.GET)
//    public Results departMentNameList() {
//        AmmeterUser user = getCuruser();
//        List<AmmeterDepartment> list = departmentService.selectDepartmentByChannelId(user.getChannelId());
//        return Results.success(list);
//    }
/*
//    *//**
//     * 初始化角色名称下拉框
//     *
//     * @return Results
//     *//*
//    @RequestMapping(value = "/roleNameList", method = RequestMethod.GET)
//    public Results roleNameList() {
//        //获取当前用户
//        AmmeterUser user = getCuruser();
//        Integer channelId = user == null ? 0 : user.getChannelId();
//        List<AmmeterRole> list = roleService.selectRoleListByChannelId(channelId);
//        return Results.success(list);
//    }*/

    /**
     * 渠道商管理用户管理列表
     */
    @RequestMapping(value = "/user/list")
    public Results list(@RequestBody AmmeterUserCondition condition) {
        condition.setUserID(getCuruserId());
        Page<?> userList = userService.selectListSelective(condition);
        return Results.success(userList);
    }

    /**
     * 渠道商管理用户管理添加用户
     */
    @RequestMapping(value = "/user/add")
    public Results add(@RequestBody AmmeterUserCondition condition) {
        condition.setUserType(new Byte("1"));
        return userController.add(condition);
    }

    /**
     * 渠道商管理用户管理编辑
     */
    @RequestMapping(value = "/user/edit")
    public Results edit(@RequestBody AmmeterUserCondition condition) {
        return userController.edit(condition);
    }

    /**
     * 渠道商管理用户管理启用禁用
     */
    @RequestMapping(value = "/user/enable")
    public Results enable(@RequestBody Map<String, ?> condition) {
        return userController.enable(condition);
    }

    /**
     * 渠道商管理用户管理重置密码
     */
    @RequestMapping(value = "/user/reset/{userId}")
    public Results reset(@PathVariable Integer userId) {
        return userController.reset(userId);
    }

    /**
     * 渠道商管理
     * 部门列表
     *
     * @return Results
     */
    @RequestMapping(value = "/department/list", method = RequestMethod.POST)
    public Results departmentList(@RequestBody AmmeterDepartment department) {
        AmmeterUser curuser = getCuruser();
        List<AmmeterDepartment> deptList = departmentService.selectDepartmentByChannelId(department.getChannelId());
        AmmeterChannel channel = channelService.selectByPrimaryKey(department.getChannelId());
        String displayName = channel == null ? "root" : channel.getChannelName();
        DepartmentTree rootTree = new DepartmentTree("", displayName);
        rootTree.setStatus(channel == null ? 1 : channel.getStatus());
        rootTree.setTotalNum(deptList.size() + 1);
        rootTree.buildTree(deptList);
        return Results.success(rootTree);
    }

    /**
     * 渠道商管理
     * 添加部门
     *
     * @return Results
     */
    @SuppressWarnings("all")
    @RequestMapping(value = "/department/add")
    public Results add(@RequestBody AmmeterDepartment condition) {
        String departmentName = condition.getDepartmentName();
        if (departmentName == null || "".equals(departmentName.trim())) {
            return Results.error("部门名称不能为空");
        }
        AmmeterUser curuser = getCuruser();
        AmmeterDepartment department = departmentService.checkDepartmentName(departmentName, condition.getChannelId());
        if (department != null) {
            return Results.error(-2,"部门名称已经存在");
        }
        String levelCode = condition.getLevelCode();
        String newLevelCode = "0512";
        condition.setLevelCode(newLevelCode);
        condition.setCreateBy(curuser.getId().toString());
        if (condition.getChannelId() == null) {
            condition.setChannelId(curuser.getChannelId());
        } else {
            condition.setChannelId(condition.getChannelId());
        }
        departmentService.insert(condition);
        return Results.success("添加成功", condition.getId());
    }

    /**
     * 渠道商管理
     * 编辑部门
     *
     * @return Results
     */
    @RequestMapping(value = "/department/edit")
    public Results edit(@RequestBody AmmeterDepartment condition) {
        return departmentController.edit(condition,true);
    }


    /**
     * 渠道商管理
     * 查看部门
     *
     * @return Results
     */
    @RequestMapping(value = "/department/view/{departmentId}")
    public Results view(@PathVariable Integer departmentId) {
        return departmentController.view(departmentId);
    }


    //@Permission("CHANNEL_RIGHTS_LIST")
    @RequestMapping(value = "/role/list")
    public Results listRole(@RequestBody RoleCondition condition) {
        Page<?> roleList = roleService.selectRoleByChannel(condition);
        return Results.success(roleList);
    }

    //@Permission("CHANNEL_RIGHTS_ADD")
    @RequestMapping(value = "/role/add")
    public Results add(@RequestBody RoleCondition condition) {
        AmmeterRole exists = roleService.selectByChannelIdAndName(condition.getChannelId(), condition.getName());
        if (exists != null) {
            return Results.error(-2,"角色名已经存在");
        }
        AmmeterRole role = new AmmeterRole();
        role.setRoleName(condition.getName());
        role.setDescription(condition.getDescription());
        role.setOwner(getCuruserId().toString());
        role.setChannelId(condition.getChannelId());
        roleService.insert(role);
        return Results.success("添加成功");
    }

    //    @Permission("CHANNEL_RIGHTS_EDIT")
    @RequestMapping(value = "/role/edit")
    public Results edit(@RequestBody RoleCondition condition) {
        AmmeterRole role = roleService.selectByPrimaryKey(condition.getId());
        if (role != null) {
            AmmeterRole exists = roleService.selectByChannelIdAndName(condition.getChannelId(), condition.getName());
            if (exists != null && !exists.getId().equals(role.getId())) {
                return Results.error(-2,"角色名已经存在");
            }
            role.setRoleName(condition.getName());
            role.setDescription(condition.getDescription());
            role.setOwner(getCuruserId().toString());
            role.setChannelId(condition.getChannelId());
            roleService.updateByPrimaryKey(role);
            return Results.success("修改成功");
        } else {
            return Results.error("not found");
        }
    }

//    /**
//     * 查询账户所属银行
//     *
//     * @return Results
//     */
//    @ApiOperation(value = "查询账户所属银行")
//    @RequestMapping(value = "getAccountBank", method = RequestMethod.POST)
//    public Results getAccountBank() {
//        return Results.success(cnapsBankCodeService.getAccountBank());
//    }



//    /**
//     * 查询渠道商列表2,为了不修改原先的接口
//     *
//     * @return Results
//     */
//    @RequestMapping(value = "/channelList2", method = RequestMethod.POST)
//    public Results channelList2(@RequestBody AmmeterChannel channelName) {
//        ChannelTree rootTree = channelService.channelList2(getCuruser());
//        //输入框渠道商名称搜索
//        if (channelName.getChannelName() != null && !"".equals(channelName.getChannelName())) {
//            rootTree = channelService.getTreeChannelName(rootTree, channelName.getChannelName());
//            return Results.success(rootTree);
//        }
//        return Results.success(rootTree);
//    }

}