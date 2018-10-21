package com.kashuo.kcp.domain;

public class AmmeterWellcover {
    private Integer id;

    private Integer positionId;

    private String deviceType;

    private String batteryStatus;

    private String sensor;

    private String surfaceDistance;

    private String tiltSensor;

    private String waterLevelSensor;

    private String enTemperature;

    private String enHumidity;

    private String smokeWarning;

    private String rsrq;

    private String currentLimit;

    private String electricLimit;

    public String getRsrq() {
        return rsrq;
    }

    public void setRsrq(String rsrq) {
        this.rsrq = rsrq;
    }

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

    public String getSmokeWarning() {
        return smokeWarning;
    }

    public void setSmokeWarning(String smokeWarning) {
        this.smokeWarning = smokeWarning;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType == null ? null : deviceType.trim();
    }

    public String getBatteryStatus() {
        return batteryStatus;
    }

    public void setBatteryStatus(String batteryStatus) {
        this.batteryStatus = batteryStatus == null ? null : batteryStatus.trim();
    }

    public String getSensor() {
        return sensor;
    }

    public void setSensor(String sensor) {
        this.sensor = sensor == null ? null : sensor.trim();
    }

    public String getSurfaceDistance() {
        return surfaceDistance;
    }

    public void setSurfaceDistance(String surfaceDistance) {
        this.surfaceDistance = surfaceDistance == null ? null : surfaceDistance.trim();
    }

    public String getTiltSensor() {
        return tiltSensor;
    }

    public void setTiltSensor(String tiltSensor) {
        this.tiltSensor = tiltSensor == null ? null : tiltSensor.trim();
    }

    public String getWaterLevelSensor() {
        return waterLevelSensor;
    }

    public void setWaterLevelSensor(String waterLevelSensor) {
        this.waterLevelSensor = waterLevelSensor == null ? null : waterLevelSensor.trim();
    }
}