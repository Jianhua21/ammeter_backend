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
    @ApiModelProperty("电表型号")
    private String deviceModel;
    @ApiModelProperty("用电单位")
    private String companyName;
    @ApiModelProperty("合同状态")
    private String agreementStatus;
    @ApiModelProperty("联系人信息")
    private String contactInfo;
    @ApiModelProperty("产品id")
    private String productId;
    @ApiModelProperty("电池状态")
    private String batteryStatus;
    @ApiModelProperty("亮度传感器信息")
    private String sensor;
    @ApiModelProperty("水面距离")
    private String surfaceDistance;
    @ApiModelProperty("倾斜传感器信息")
    private String tiltSensor;
    @ApiModelProperty("水位满溢传感器信息")
    private String waterLevelSensor;
    @ApiModelProperty("设备类型")
    private Integer deviceType=0;
    @ApiModelProperty("环境温度")
    private String enTemperature;
    @ApiModelProperty("环境湿度")
    private String en_humidity;
    @ApiModelProperty("烟雾告警信息")
    private String smokeWarning;

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }
    public String getEnTemperature() {
        return enTemperature;
    }

    public void setEnTemperature(String enTemperature) {
        this.enTemperature = enTemperature;
    }

    public String getEn_humidity() {
        return en_humidity;
    }

    public void setEn_humidity(String en_humidity) {
        this.en_humidity = en_humidity;
    }

    public String getSmokeWarning() {
        return smokeWarning;
    }

    public void setSmokeWarning(String smokeWarning) {
        this.smokeWarning = smokeWarning;
    }

    public String getBatteryStatus() {
        try {
            batteryStatus = String.valueOf(Integer.parseInt(batteryStatus) * 1d / 100) + "V";
        }catch (Exception e){
            batteryStatus ="0 V";
        }
        return batteryStatus ;
    }

    public void setBatteryStatus(String batteryStatus) {
        this.batteryStatus = batteryStatus;
    }

    public String getSensor() {
        return sensor;
    }

    public void setSensor(String sensor) {
        this.sensor = sensor;
    }

    public String getSurfaceDistance() {
        try {
            surfaceDistance = String.valueOf(Integer.parseInt(surfaceDistance) * 1d / 100) + "M";
        }catch (Exception e){
            surfaceDistance = "0 M";
        }
        return surfaceDistance;
    }

    public void setSurfaceDistance(String surfaceDistance) {
        this.surfaceDistance = surfaceDistance;
    }

    public String getTiltSensor() {
        if(tiltSensor == null){
            return "-";
        }else if("A0".equals(tiltSensor)){
            return "正常";
        }else{
            return "倾斜";
        }

    }

    public void setTiltSensor(String tiltSensor) {
        this.tiltSensor = tiltSensor;
    }

    public String getWaterLevelSensor() {
        if(waterLevelSensor == null){
            return "-";
        }
        else if("W0".equals(waterLevelSensor)){
            return "正常水位";
        }else{
            return  "水位已漫出";
        }
    }

    public void setWaterLevelSensor(String waterLevelSensor) {
        this.waterLevelSensor = waterLevelSensor;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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