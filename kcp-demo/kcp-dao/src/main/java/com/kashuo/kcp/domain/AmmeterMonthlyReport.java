package com.kashuo.kcp.domain;

import java.util.Date;

public class AmmeterMonthlyReport {
    private Integer id;

    private Integer ammeterId;

    private Integer day;

    private String  month;

    private Date sendDate;

    private String activeEnergy;

    private String reactiveEnergy;

    private Integer type;

    public Integer getType() {
        return type;
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

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
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
}