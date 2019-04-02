package com.kashuo.kcp.dao.condition;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by dell-pc on 2018/4/10.
 */
@ApiModel("报警查询")
public class WarningCondition extends BaseCondition{
    @ApiModelProperty("查询时间")
    private String dateTime;
    @ApiModelProperty("每条消息的唯一编号，字符串类型，必要")
    private String sn;
    @ApiModelProperty("电表类型")
    private String ammeterType;
    @ApiModelProperty("机构Id")
    private Integer channelId;
    @ApiModelProperty("设备Id")
    private String deviceId;
    @ApiModelProperty("设备位置Id")
    private Integer positionId;
    @ApiModelProperty("告警状态 0-未消除告警，1-消除的告警，不传就取所有记录")
    private Integer status;
    @ApiModelProperty("设备IMEI号")
    private String imei;
    @ApiModelProperty("设备名称")
    private String name;
    @ApiModelProperty("设备类型")
    private Integer deviceType;

    private Integer ruleId;

    public Integer getRuleId() {
        return ruleId;
    }

    public void setRuleId(Integer ruleId) {
        this.ruleId = ruleId;
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    public String getImei() {
        return imei != null ? imei.trim():null;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getName() {
        return name != null ? name.trim():null;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getAmmeterType() {
        return ammeterType;
    }

    public void setAmmeterType(String ammeterType) {
        this.ammeterType = ammeterType;
    }
}
