package com.kashuo.kcp.domain;

import com.kashuo.kcp.utils.DateUtils;

import java.util.Date;

/**
 * Created by dell-pc on 2018/4/10.
 */
public class AmmeterWarningResult {
    //电表设备唯一id
    private Integer id;
    //警告Id
    private Integer warningId;
    //Iom设备Id
    private String deviceId;
    //电表设备唯一imsi
    private String imsi;
    //电表编号
    private String number;
    //报警时间
    private Date warningDate;
    //报警描述
    private String warningDesc;
    //电表类型
    private String ammeterType;
    //警告类型
    private Integer warningType;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getWarningId() {
        return warningId;
    }

    public void setWarningId(Integer warningId) {
        this.warningId = warningId;
    }

    public Integer getWarningType() {
        return warningType;
    }

    public void setWarningType(Integer warningType) {
        this.warningType = warningType;
    }

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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getWarningDate() {
        return warningDate != null ? DateUtils.format(warningDate): "";
    }

    public void setWarningDate(Date warningDate) {
        this.warningDate = warningDate;
    }

    public String getWarningDesc() {
        return warningDesc;
    }

    public void setWarningDesc(String warningDesc) {
        this.warningDesc = warningDesc;
    }

    public String getAmmeterType() {
        return ammeterType;
    }

    public void setAmmeterType(String ammeterType) {
        this.ammeterType = ammeterType;
    }
}
