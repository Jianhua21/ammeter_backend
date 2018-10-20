package com.kashuo.kcp.dao.result;

/**
 * Created by dell-pc on 2018/10/9.
 */
public class WarningSmokeDetectorCategory {

    private String warningName ="烟感告警饼状图";
    //低电量告警数
    private Integer batteryWarningDevices =0;
    //设备总数
    private Integer totalDevices =0;
    //正常设备数
    private Integer normalDevices=0;
    //离线设备数
    private Integer offlineDevices =0;
    //温度告警数
    private Integer temperatureWarningDevices =0;
    //湿度告警
    private Integer humidityWarningDevices=0;
    //烟雾告警
    private Integer smokeWarningDevices=0;

    private Integer weakSignalDevices =0;

    public Integer getWeakSignalDevices() {
        return weakSignalDevices;
    }

    public void setWeakSignalDevices(Integer weakSignalDevices) {
        this.weakSignalDevices = weakSignalDevices;
    }

    public String getWarningName() {
        return warningName;
    }

    public void setWarningName(String warningName) {
        this.warningName = warningName;
    }

    public Integer getBatteryWarningDevices() {
        return batteryWarningDevices;
    }

    public void setBatteryWarningDevices(Integer batteryWarningDevices) {
        this.batteryWarningDevices = batteryWarningDevices;
    }

    public Integer getTotalDevices() {
        return totalDevices;
    }

    public void setTotalDevices(Integer totalDevices) {
        this.totalDevices = totalDevices;
    }

    public Integer getNormalDevices() {
        return normalDevices;
    }

    public void setNormalDevices(Integer normalDevices) {
        this.normalDevices = normalDevices;
    }

    public Integer getOfflineDevices() {
        return offlineDevices;
    }

    public void setOfflineDevices(Integer offlineDevices) {
        this.offlineDevices = offlineDevices;
    }

    public Integer getTemperatureWarningDevices() {
        return temperatureWarningDevices;
    }

    public void setTemperatureWarningDevices(Integer temperatureWarningDevices) {
        this.temperatureWarningDevices = temperatureWarningDevices;
    }

    public Integer getHumidityWarningDevices() {
        return humidityWarningDevices;
    }

    public void setHumidityWarningDevices(Integer humidityWarningDevices) {
        this.humidityWarningDevices = humidityWarningDevices;
    }

    public Integer getSmokeWarningDevices() {
        return smokeWarningDevices;
    }

    public void setSmokeWarningDevices(Integer smokeWarningDevices) {
        this.smokeWarningDevices = smokeWarningDevices;
    }
}
