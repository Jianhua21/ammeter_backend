package com.kashuo.kcp.domain;

import java.util.Date;

public class AmmeterReport {
    private Integer id;

    private Integer ammeterId;

    private String activeEnergy;

    private String voltage;

    private String current;

    private String reactiveEnergy;

    private Date sendTime;

    private Integer hour;

    private String dateTime;

    private Integer type;

    public Integer getType() {
        return type;
    }

    public String getVoltage() {
        return voltage;
    }

    public void setVoltage(String voltage) {
        this.voltage = voltage;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAmmeterId() {
        return ammeterId;
    }

    public void setAmmeterId(Integer ammeterId) {
        this.ammeterId = ammeterId;
    }

    public String getActiveEnergy() {
        return activeEnergy;
    }

    public void setActiveEnergy(String activeEnergy) {
        this.activeEnergy = activeEnergy == null ? null : activeEnergy.trim();
    }

    public String getReactiveEnergy() {
        return reactiveEnergy;
    }

    public void setReactiveEnergy(String reactiveEnergy) {
        this.reactiveEnergy = reactiveEnergy == null ? null : reactiveEnergy.trim();
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime == null ? null : dateTime.trim();
    }
}