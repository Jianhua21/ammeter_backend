package com.kashuo.kcp.api.entity;

import java.io.Serializable;
import java.util.Map;

public class PushBean implements Serializable {

    private String imei;
    private String status;
    private String eventTime;
    private String deviceType;
    private Map<String, Object> service;

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Map<String, Object> getService() {
        return service;
    }

    public void setService(Map<String, Object> service) {
        this.service = service;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    @Override
    public String toString() {
        return "PushBean{" +
                "imei='" + imei + '\'' +
                ", status='" + status + '\'' +
                ", eventTime='" + eventTime + '\'' +
                ", service=" + service +
                '}';
    }
}