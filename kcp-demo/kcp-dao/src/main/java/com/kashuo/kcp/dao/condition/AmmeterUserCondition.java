package com.kashuo.kcp.dao.condition;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 用户
 * Created by Mr.fangzhong on 2016/5/17.
 */
@ApiModel("用户条件")
public class AmmeterUserCondition extends BaseCondition {

    /**
     * 用户ID
     */
    @ApiModelProperty("用户ID")
    private Integer userId;
    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    private String loginName;
    /**
     * 用户姓名
     */
    @ApiModelProperty("用户姓名")
    private String realName;
    /**
     * 性别 1:男 2:女
     */
    @ApiModelProperty(value = "性别 1:男 2:女", allowableValues = "1,2")
    private Byte gender;
    /**
     * 部门名称
     */
    @ApiModelProperty("部门名称")
    private String departMentName;
    /**
     * 工号
     */
    @ApiModelProperty("工号")
    private String jobNumber;
    /**
     * 手机号
     */
    @ApiModelProperty("手机号")
    private String mobilePhone;
    /**
     * 邮箱地址
     */
    @ApiModelProperty("邮箱地址")
    private String email;
    /**
     * 用户类型 0:管理员 1:普通用户 2:商户管理员
     */
    @ApiModelProperty(value = "用户类型", allowableValues = "0,1,2")
    private Byte userType;
    /**
     * 角色ID
     */
    @ApiModelProperty("角色ID")
    private Integer roleId;
    /**
     * 角色名
     */
    @ApiModelProperty("角色名")
    private String roleName;
    /**
     * 渠道商ID
     */
    @ApiModelProperty("渠道商ID")
    private Integer channelId;
    /**
     * 部门ID
     */
    @ApiModelProperty("部门ID")
    private Integer departmentId;
    /**
     * 职位ID
     */
    @ApiModelProperty(value = "职位ID", hidden = true)
    private Integer positionId;
    /**
     * 状态 0:停用 1:启用
     */
    @ApiModelProperty(value = "状态", allowableValues = "0,1")
    private Integer status;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? "" : loginName.trim();
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? "" : realName.trim();
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public String getDepartMentName() {
        return departMentName;
    }

    public void setDepartMentName(String departMentName) {
        this.departMentName = departMentName == null ? "" : departMentName.trim();
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber == null ? "" : jobNumber.trim();
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone == null ? "" : mobilePhone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? "" : email.trim();
    }

    public Byte getUserType() {
        return userType;
    }

    public void setUserType(Byte userType) {
        this.userType = userType;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? "" : roleName.trim();
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
