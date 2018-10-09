package com.kashuo.kcp.dao.result;

/**
 * Created by dell-pc on 2018/8/19.
 */
public class WarningWellCover {

    private Integer id;

    private Integer ammeterId;

    private Integer deviceType =0;

    private String batteryWarning;

    private String sensorWarning;

    private String surfaceDistanceWarning;

    private String tiltSensorWarning;

    private String waterLevelSensorWarning;

    private String temperatureWarning;

    private String humidityWarning;

    private String smokeWarning;

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public String getTemperatureWarning() {
        return temperatureWarning;
    }

    public void setTemperatureWarning(String temperatureWarning) {
        this.temperatureWarning = temperatureWarning;
    }

    public String getHumidityWarning() {
        return humidityWarning;
    }

    public void setHumidityWarning(String humidityWarning) {
        this.humidityWarning = humidityWarning;
    }

    public String getSmokeWarning() {
        return smokeWarning;
    }

    public void setSmokeWarning(String smokeWarning) {
        this.smokeWarning = smokeWarning;
    }

    public Integer getAmmeterId() {
        return ammeterId;
    }

    public void setAmmeterId(Integer ammeterId) {
        this.ammeterId = ammeterId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBatteryWarning() {
        return batteryWarning;
    }

    public void setBatteryWarning(String batteryWarning) {
        this.batteryWarning = batteryWarning;
    }

    public String getSensorWarning() {
        return sensorWarning;
    }

    public void setSensorWarning(String sensorWarning) {
        this.sensorWarning = sensorWarning;
    }

    public String getSurfaceDistanceWarning() {
        return surfaceDistanceWarning;
    }

    public void setSurfaceDistanceWarning(String surfaceDistanceWarning) {
        this.surfaceDistanceWarning = surfaceDistanceWarning;
    }

    public String getTiltSensorWarning() {
        return tiltSensorWarning;
    }

    public void setTiltSensorWarning(String tiltSensorWarning) {
        this.tiltSensorWarning = tiltSensorWarning;
    }

    public String getWaterLevelSensorWarning() {
        return waterLevelSensorWarning;
    }

    public void setWaterLevelSensorWarning(String waterLevelSensorWarning) {
        this.waterLevelSensorWarning = waterLevelSensorWarning;
    }
}
