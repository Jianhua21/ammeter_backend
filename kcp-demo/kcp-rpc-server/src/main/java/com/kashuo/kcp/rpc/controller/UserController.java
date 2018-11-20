package com.kashuo.kcp.rpc.controller;

import com.kashuo.common.base.domain.Page;
import com.kashuo.kcp.core.AmmeterUserService;
import com.kashuo.kcp.dao.condition.AmmeterUserCondition;
import com.kashuo.kcp.domain.AmmeterUser;
import com.kashuo.kcp.utils.EncryptUtils;
import com.kashuo.kcp.utils.Results;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

/**
 * 用户管理
 * Created by Mr.Legend on 2016/5/18.
 */
@RestController
@Api(description = "用户管理")
@RequestMapping(value = "/user", method = RequestMethod.POST)
public class UserController extends BaseController {

    @Autowired
    private AmmeterUserService userService;

    @RequestMapping(value = "list")
    @ApiOperation(value = "列表查询", notes = "Created by Mr.Legend on 2018/4/3")
    public Results list(@RequestBody AmmeterUserCondition condition) {
        condition.setChannelId(getCuruser().getChannelId());
        Page<?> userList = userService.selectListSelective(condition);
        return Results.success(userList);
    }

    @RequestMapping(value = "add")
    @ApiOperation(value = "用户新增", notes = "Created by Mr.Legend on 2018/4/3")
    public Results add(@RequestBody AmmeterUserCondition condition) {

        // 普通用户使用手机号来登录
        AmmeterUser exists = userService.checkLoginName(condition.getLoginName());
        AmmeterUser existsPhone = userService.checkMobile(condition.getMobilePhone());
        if (exists != null) {
            return Results.error(-3,"用户名已经存在");
        }
        if (existsPhone != null) {
            return Results.error(-2,"手机号已经存在");
        }
        AmmeterUser newuser = new AmmeterUser();
        newuser.setRealname(condition.getRealName());
        newuser.setGender(condition.getGender());
        newuser.setDepartmentId(condition.getDepartmentId());
        newuser.setJobNumber(condition.getJobNumber());
        newuser.setLoginName(condition.getLoginName());
        String defaultPassword = properties.getDefaultPassword();
        newuser.setLoginPasswd(EncryptUtils.encryptMD5(defaultPassword));
        newuser.setMobilePhone(condition.getMobilePhone());
        newuser.setEmail(condition.getEmail());
        Byte userType = condition.getUserType();// 0:管理员 1:普通用户 2:商户管理员
        newuser.setUserType(userType == null ? (byte) 1 : userType);
        newuser.setRoleId(condition.getRoleId());
//        newuser.setCreateBy(getCuruser().getRealname());
        newuser.setCreateBy("system");
        Integer channelId = condition.getChannelId();
        newuser.setChannelId(channelId == null ? getCuruser().getChannelId() : channelId);
        newuser.setCreateTime(new Date());
        newuser.setStatus((byte) 1);
        userService.insert(newuser);
        return Results.success("新增成功");
    }

    @RequestMapping(value = "edit")
    @ApiOperation(value = "用户编辑", notes = "Created by Mr.Legend on 2018/4/3")
    public Results edit(@RequestBody AmmeterUserCondition condition) {
        AmmeterUser exists = userService.selectByPrimaryKey(condition.getUserId());
        if (exists == null) {
            return Results.error("用户不存在");
        }

        AmmeterUser existsName = userService.checkLoginName(condition.getLoginName());
        AmmeterUser existsPhone = userService.checkMobile(condition.getMobilePhone());
        if (existsName != null && !condition.getUserId().equals(existsName.getId())) {
            return Results.error(-3,"用户名已经存在");
        }
        //编辑的时候，如果同一个手机号查到的记录，id不同则表示另一条记录
        if (existsPhone != null && !condition.getUserId().equals(existsPhone.getId())) {
            return Results.error(-2,"手机号已经存在");
        }

        exists.setRealname(condition.getRealName());
        exists.setGender(condition.getGender());
        exists.setDepartmentId(condition.getDepartmentId());
        exists.setJobNumber(condition.getJobNumber());
        exists.setLoginName(condition.getLoginName());
        exists.setMobilePhone(condition.getMobilePhone());
        exists.setEmail(condition.getEmail());
        exists.setRoleId(condition.getRoleId());
        exists.setStatus(condition.getStatus().byteValue());
        userService.updateByPrimaryKey(exists);
        return Results.success("修改成功");
    }

    @RequestMapping(value = "enable")
    @ApiOperation(value = "用户停用/启用", notes = "Created by Mr.Legend on 2018/4/3")
    public Results enable(@RequestBody Map<String, ?> condition) {
        Integer userId = Integer.parseInt(condition.get("userId").toString());
        Boolean enable = Boolean.parseBoolean(condition.get("enable").toString());
        AmmeterUser exists = userService.selectByPrimaryKey(userId);
        if (exists == null) {
            return Results.error("用户不存在");
        }
        if (enable) {
            AmmeterUser existsPhone = userService.checkMobile(exists.getMobilePhone());
            if (existsPhone != null && !condition.get("userId").equals(existsPhone.getId())) {
                return Results.error(-2,"手机号已经存在");
            }
        }
        Integer status = enable ? 1 : 0;
        exists.setStatus(status.byteValue());
        userService.updateByPrimaryKey(exists);
        return Results.success("操作成功");
    }

    @RequestMapping(value = "reset/{userId}")
    @ApiOperation(value = "用户重置", notes = "Created by Mr.Legend on 2018/4/3")
    public Results reset(@PathVariable Integer userId) {
        AmmeterUser exists = userService.selectByPrimaryKey(userId);
        if (exists == null) {
            return Results.error("用户不存在");
        }
        String defaultPassword = properties.getDefaultPassword();
        exists.setLoginPasswd(EncryptUtils.encryptMD5(defaultPassword));
        userService.updateByPrimaryKey(exists);
        return Results.success("密码已重置，重置密码为：" + defaultPassword);
    }

    //@Permission("USER_CHANGEPWD")
    @RequestMapping(value = "changepwd")
    @ApiOperation(value = "修改密码", notes = "Created by Mr.Legend on 2018/4/3")
    public Results changepwd(@RequestBody Map<String, String> condition) {

        String oldPassword = condition.get("oldPassword");
        String newPassword = condition.get("newPassword");
        String confirmPassword = condition.get("confirmPassword");

        if ("".equals(newPassword)) {
            return Results.error("密码不能为空");
        }

        if (!newPassword.equals(confirmPassword)) {
            return Results.error("确认密码不一致");
        }

        oldPassword = EncryptUtils.encryptMD5(oldPassword);

        AmmeterUser curuser = getCuruser();
        if (!oldPassword.equals(curuser.getLoginPasswd())) {
            return Results.error("原密码不正确");
        }

        newPassword = EncryptUtils.encryptMD5(newPassword);
        curuser.setLoginPasswd(newPassword);
        userService.updateByPrimaryKey(curuser);

        return Results.success("修改成功");
    }

}
