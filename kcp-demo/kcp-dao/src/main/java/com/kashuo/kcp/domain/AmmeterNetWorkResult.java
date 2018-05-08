package com.kashuo.kcp.domain;

import com.kashuo.kcp.utils.StringUtil;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dell-pc on 2017/9/24.
 */
public class AmmeterNetWorkResult implements Serializable {
    private Integer id;

    private String name;

    private String imsi;

    private String celli;

    private String rsrq;

    private String rssi;

    private String rsrp;

    public String getRsrp() {
        return StringUtil.nullToEmpty(rsrp);
    }

    public void setRsrp(String rsrp) {
        this.rsrp = rsrp;
    }

    public String getName() {
        return StringUtil.nullToEmpty(name);
    }

    public void setName(String name) {
        this.name = name;
    }

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
        return StringUtil.nullToEmpty(celli);
    }

    public void setCelli(String celli) {
        this.celli = celli;
    }

    public String getRsrq() {
        return StringUtil.nullToEmpty(rsrq);
    }

    public void setRsrq(String rsrq) {
        this.rsrq = rsrq;
    }

    public String getRssi() {
        return StringUtil.nullToEmpty(rssi);
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
