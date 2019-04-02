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

    private String riData;

    private String deviceImei;

    private String devicePlatform;

    private Integer deviceStatus;

    public Integer getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(Integer deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public String getDeviceImei() {
        return deviceImei;
    }

    public void setDeviceImei(String deviceImei) {
        this.deviceImei = deviceImei;
    }

    public String getDevicePlatform() {
        return devicePlatform;
    }

    public void setDevicePlatform(String devicePlatform) {
        this.devicePlatform = devicePlatform;
    }

    public String getRiData() {
        return riData;
    }

    public void setRiData(String riData) {
        this.riData = riData;
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