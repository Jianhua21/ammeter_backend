package com.kashuo.kcp.dao.result;

/**
 * Created by dell-pc on 2018/8/19.
 */
public class WarningWellCover {

    private Integer id;

    private Integer ammeterId;

    private String batteryWarning;

    private String sensorWarning;

    private String surfaceDistanceWarning;

    private String tiltSensorWarning;

    private String waterLevelSensorWarning;

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
