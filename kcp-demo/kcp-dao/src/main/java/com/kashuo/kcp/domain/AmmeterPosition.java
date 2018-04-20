package com.kashuo.kcp.domain;


import com.kashuo.kcp.utils.DateUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(value = "电表位置信息")
public class AmmeterPosition {
    @ApiModelProperty(value = "位置信息Id,创建时不需要传!")
    private Integer id;
    @ApiModelProperty(value = "电表名称")
    private String name;
    @ApiModelProperty(value = "电表编号")
    private String number;
    @ApiModelProperty(value = "电表地址")
    private String address;
    @ApiModelProperty(value = "高德经度")
    private String amapLongitude;
    @ApiModelProperty(value = "高德纬度")
    private String amapLatitude;
    @ApiModelProperty(value = "GPS经度")
    private String gpsLongitude;
    @ApiModelProperty(value = "GPS纬度")
    private String gpsLatitude;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "状态,创建时可不传!默认为0")
    private Integer status;
    @ApiModelProperty("创建人")
    private Integer createBy;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("电表类型")
    private String type;
    @ApiModelProperty("安装人员")
    private String realName;
    @ApiModelProperty("设备的唯一标识为IMEI号")
    private String imei;
    @ApiModelProperty("IoM平台设备Id")
    private String deviceId;
    @ApiModelProperty("申请的临时验证码，设备可以通过验证码获取id和密码")
    private String verifyCode;
    @ApiModelProperty("psk码，用于生成设备鉴权参数")
    private String psk;
    @ApiModelProperty("激活状态")
    private boolean Activated;

    public boolean isActivated() {
        return Activated;
    }

    public void setActivated(boolean activated) {
        Activated = activated;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
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

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getAmapLongitude() {
        return amapLongitude;
    }

    public void setAmapLongitude(String amapLongitude) {
        this.amapLongitude = amapLongitude == null ? null : amapLongitude.trim();
    }

    public String getAmapLatitude() {
        return amapLatitude;
    }

    public void setAmapLatitude(String amapLatitude) {
        this.amapLatitude = amapLatitude == null ? null : amapLatitude.trim();
    }

    public String getGpsLongitude() {
        return gpsLongitude;
    }

    public void setGpsLongitude(String gpsLongitude) {
        this.gpsLongitude = gpsLongitude == null ? null : gpsLongitude.trim();
    }

    public String getGpsLatitude() {
        return gpsLatitude;
    }

    public void setGpsLatitude(String gpsLatitude) {
        this.gpsLatitude = gpsLatitude == null ? null : gpsLatitude.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}