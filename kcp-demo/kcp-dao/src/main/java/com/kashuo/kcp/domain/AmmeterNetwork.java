package com.kashuo.kcp.domain;

import java.util.Date;

public class AmmeterNetwork {
    private Integer id;

    private Integer ammeterId;

    private String rssi;

    private String rsrq;

    private String celli;

    private Integer recordHour;

    private String recordDay;

    private Date createdTime;

    private Integer warningFlag;

    public Integer getWarningFlag() {
        return warningFlag;
    }

    public void setWarningFlag(Integer warningFlag) {
        this.warningFlag = warningFlag;
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

    public String getRssi() {
        return rssi;
    }

    public void setRssi(String rssi) {
        this.rssi = rssi == null ? null : rssi.trim();
    }

    public String getRsrq() {
        return rsrq;
    }

    public void setRsrq(String rsrq) {
        this.rsrq = rsrq == null ? null : rsrq.trim();
    }

    public String getCelli() {
        return celli;
    }

    public void setCelli(String celli) {
        this.celli = celli == null ? null : celli.trim();
    }

    public Integer getRecordHour() {
        return recordHour;
    }

    public void setRecordHour(Integer recordHour) {
        this.recordHour = recordHour;
    }

    public String getRecordDay() {
        return recordDay;
    }

    public void setRecordDay(String recordDay) {
        this.recordDay = recordDay == null ? null : recordDay.trim();
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}