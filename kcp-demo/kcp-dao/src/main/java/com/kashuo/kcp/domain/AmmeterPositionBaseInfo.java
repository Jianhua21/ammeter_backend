package com.kashuo.kcp.domain;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by dell-pc on 2018/5/7.
 */
public class AmmeterPositionBaseInfo {
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
    @ApiModelProperty("安装人员")
    private String installer;
    @ApiModelProperty("设备的唯一标识为IMEI号")
    private String imei;
    @ApiModelProperty("电表型号")
    private String deviceModel;
    @ApiModelProperty("用电单位")
    private String companyName;
    @ApiModelProperty("合同状态")
    private String agreementStatus;
    @ApiModelProperty("联系人信息")
    private String contactInfo;
    @ApiModelProperty("设备所属平台")
    private Integer platform =0;

    public String getPlatform() {
        return String.valueOf(platform);
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAgreementStatus() {
        return agreementStatus;
    }

    public void setAgreementStatus(String agreementStatus) {
        this.agreementStatus = agreementStatus;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
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
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAmapLongitude() {
        return amapLongitude;
    }

    public void setAmapLongitude(String amapLongitude) {
        this.amapLongitude = amapLongitude;
    }

    public String getAmapLatitude() {
        return amapLatitude;
    }

    public void setAmapLatitude(String amapLatitude) {
        this.amapLatitude = amapLatitude;
    }

    public String getGpsLongitude() {
        return gpsLongitude;
    }

    public void setGpsLongitude(String gpsLongitude) {
        this.gpsLongitude = gpsLongitude;
    }

    public String getGpsLatitude() {
        return gpsLatitude;
    }

    public void setGpsLatitude(String gpsLatitude) {
        this.gpsLatitude = gpsLatitude;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getInstaller() {
        return installer;
    }

    public void setInstaller(String installer) {
        this.installer = installer;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }
}
