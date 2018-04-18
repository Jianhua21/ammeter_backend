package com.kashuo.kcp.dao.condition;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 用户登录
 * Created by Mr.ZHAO on 2016/5/12.
 */
@ApiModel(value = "登录条件")
public class LoginCondition {

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("是否记住密码")
    private Boolean remember;
    @ApiModelProperty("每条消息的唯一编号")
    private String sn;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getUsername() {
        return username == null ? "" : username.trim();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password == null ? "" : password.trim();
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getRemember() {
        return remember == null ? false : remember;
    }

    public void setRemember(Boolean remember) {
        this.remember = remember;
    }
}
