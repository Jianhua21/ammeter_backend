package com.kashuo.kcp.domain;


import com.kashuo.kcp.utils.DateUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(value = "电表位置信息")
public class AmmeterPosition extends AmmeterPositionBaseInfo{
    @ApiModelProperty(value = "状态,创建时可不传!默认为0")
    private Integer status;
    @ApiModelProperty
    private String statusName;
    @ApiModelProperty("创建人")
    private Integer createBy;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("电表类型")
    private String type;
    @ApiModelProperty("IoM平台设备Id")
    private String deviceId;
    @ApiModelProperty("申请的临时验证码，设备可以通过验证码获取id和密码")
    private String verifyCode;
    @ApiModelProperty("psk码，用于生成设备鉴权参数")
    private String psk;
    @ApiModelProperty("激活状态")
    private boolean Activated;
    @ApiModelProperty("用于列表查询    警告状态  0 -没有警告  1-有警告")
    private String warningStatus;

    public Integer getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(Integer workStatus) {
        this.workStatus = workStatus;
    }

    @ApiModelProperty("开关闸状态")
    private Integer workStatus;

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getWarningStatus() {
        return warningStatus;
    }

    public void setWarningStatus(String warningStatus) {
        this.warningStatus = warningStatus;
    }

    public String getWarningDesc() {
        return warningDesc;
    }

    public void setWarningDesc(String warningDesc) {
        this.warningDesc = warningDesc;
    }

    @ApiModelProperty("用于列表查询    警告说明")
    private String warningDesc;

    public boolean isActivated() {
        return Activated;
    }

    public void setActivated(boolean activated) {
        Activated = activated;
    }


    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getPsk() {
        return psk;
    }

    public void setPsk(String psk) {
        this.psk = psk;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public String getCreateTime() {
        return createTime != null ? DateUtils.format(createTime):"";
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}