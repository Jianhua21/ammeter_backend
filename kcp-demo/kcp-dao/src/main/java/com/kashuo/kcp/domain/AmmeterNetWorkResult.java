package com.kashuo.kcp.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dell-pc on 2017/9/24.
 */
public class AmmeterNetWorkResult implements Serializable {
    private Integer id;

    private String imsi;

    private String celli;

    private String rsrq;

    private String rssi;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getCelli() {
        return celli;
    }

    public void setCelli(String celli) {
        this.celli = celli;
    }

    public String getRsrq() {
        return rsrq;
    }

    public void setRsrq(String rsrq) {
        this.rsrq = rsrq;
    }

    public String getRssi() {
        return rssi;
    }

    public void setRssi(String rssi) {
        this.rssi = rssi;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
