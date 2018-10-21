package com.kashuo.kcp.dao.condition;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by dell-pc on 2018/8/25.
 */
@ApiModel("井盖修改参数")
public class AmmeterWellCoverSystemParams {

    @ApiModelProperty("电量阀值符号")
    private String batteryStatusKey ="<";
    @ApiModelProperty("电量阀值")
    private String batteryStatusValue ="";
    @ApiModelProperty("亮度传感器信息符号")
    private String sensorKey =">";
    @ApiModelProperty("亮度传感器信息阀值")
    private String sensorValue;
    @ApiModelProperty("水面距离符号")
    private String surfaceDistanceKey ="<";
    @ApiModelProperty("水面距离阀值")
    private String surfaceDistanceValue;
    @ApiModelProperty("设备类型")
    private Integer deviceType;
    @ApiModelProperty("温度阀值")
    private String enTemperature;
    @ApiModelProperty("湿度阀值")
    private String enHumidity;
    @ApiModelProperty("电流阀值")
    private String currentLimit;
    @ApiModelProperty("电量阀值")
    private String electricLimit;

    public String getCurrentLimit() {
        return currentLimit;
    }

    public void setCurrentLimit(String currentLimit) {
        this.currentLimit = currentLimit;
    }

    public String getElectricLimit() {
        return electricLimit;
    }

    public void setElectricLimit(String electricLimit) {
        this.electricLimit = electricLimit;
    }

    public String getEnTemperature() {
        return enTemperature;
    }

    public void setEnTemperature(String enTemperature) {
        this.enTemperature = enTemperature;
    }

    public String getEnHumidity() {
        return enHumidity;
    }

    public void setEnHumidity(String enHumidity) {
        this.enHumidity = enHumidity;
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public String getBatteryStatusKey() {
        return batteryStatusKey;
    }

    public void setBatteryStatusKey(String batteryStatusKey) {
        this.batteryStatusKey = batteryStatusKey;
    }

    public String getBatteryStatusValue() {
        return batteryStatusValue;
    }

    public void setBatteryStatusValue(String batteryStatusValue) {
        this.batteryStatusValue = batteryStatusValue;
    }

    public String getSensorKey() {
        return sensorKey;
    }

    public void setSensorKey(String sensorKey) {
        this.sensorKey = sensorKey;
    }

    public String getSensorValue() {
        return sensorValue;
    }

    public void setSensorValue(String sensorValue) {
        this.sensorValue = sensorValue;
    }

    public String getSurfaceDistanceKey() {
        return surfaceDistanceKey;
    }

    public void setSurfaceDistanceKey(String surfaceDistanceKey) {
        this.surfaceDistanceKey = surfaceDistanceKey;
    }

    public String getSurfaceDistanceValue() {
        return surfaceDistanceValue;
    }

    public void setSurfaceDistanceValue(String surfaceDistanceValue) {
        this.surfaceDistanceValue = surfaceDistanceValue;
    }
}
