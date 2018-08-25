package com.kashuo.kcp.dao.result;

/**
 * Created by dell-pc on 2018/5/14.
 */
public class WarningDeviceCategory {
    private String warningName ="告警饼状图";

    private Integer warningNumbers=0;

    private Integer batteryWarningDevices =0;

    private Integer sensorWarningDevices =0;

    private Integer surfaceDistanceWarningDevices =0;

    private Integer tiltSensorWarningDevices =0;

    private Integer waterLevelSensorWarningDevices=0;

    private Integer totalDevices =0;

    private Integer normalDevices=0;

    private Integer offlineDevices =0;

    private Integer weakSignalDevices =0;

    public Integer getOfflineDevices() {
        return offlineDevices;
    }

    public void setOfflineDevices(Integer offlineDevices) {
        this.offlineDevices = offlineDevices;
    }

    public Integer getWeakSignalDevices() {
        return weakSignalDevices;
    }

    public void setWeakSignalDevices(Integer weakSignalDevices) {
        this.weakSignalDevices = weakSignalDevices;
    }

    public Integer getBatteryWarningDevices() {
        return batteryWarningDevices;
    }

    public void setBatteryWarningDevices(Integer batteryWarningDevices) {
        this.batteryWarningDevices = batteryWarningDevices;
    }

    public Integer getSensorWarningDevices() {
        return sensorWarningDevices;
    }

    public void setSensorWarningDevices(Integer sensorWarningDevices) {
        this.sensorWarningDevices = sensorWarningDevices;
    }

    public Integer getSurfaceDistanceWarningDevices() {
        return surfaceDistanceWarningDevices;
    }

    public void setSurfaceDistanceWarningDevices(Integer surfaceDistanceWarningDevices) {
        this.surfaceDistanceWarningDevices = surfaceDistanceWarningDevices;
    }

    public Integer getTiltSensorWarningDevices() {
        return tiltSensorWarningDevices;
    }

    public void setTiltSensorWarningDevices(Integer tiltSensorWarningDevices) {
        this.tiltSensorWarningDevices = tiltSensorWarningDevices;
    }

    public Integer getWaterLevelSensorWarningDevices() {
        return waterLevelSensorWarningDevices;
    }

    public void setWaterLevelSensorWarningDevices(Integer waterLevelSensorWarningDevices) {
        this.waterLevelSensorWarningDevices = waterLevelSensorWarningDevices;
    }

    public Integer getNormalDevices() {
        return normalDevices;
    }

    public void setNormalDevices(Integer normalDevices) {
        this.normalDevices = normalDevices;
    }

    public Integer getTotalDevices() {
        return totalDevices;
    }

    public void setTotalDevices(Integer totalDevices) {
        this.totalDevices = totalDevices;
    }

    public String getWarningName() {
        return warningName;
    }

    public void setWarningName(String warningName) {
        this.warningName = warningName;
    }

    public Integer getWarningNumbers() {
        return warningNumbers;
    }

    public void setWarningNumbers(Integer warningNumbers) {
        this.warningNumbers = warningNumbers;
    }

    public static void main(String[] args) {
        System.out.println((double)Math.round((double)4/11*100)/100);
    }
}
