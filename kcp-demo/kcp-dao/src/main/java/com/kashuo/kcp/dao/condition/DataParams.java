package com.kashuo.kcp.dao.condition;

import java.io.Serializable;

/**
 * Created by dell-pc on 2018/4/14.
 */
public class DataParams implements Serializable {
    private String voltage="";
    private String current="";
    private String frequency="";
    private String powerfactor="";

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    private String params;

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

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getPowerfactor() {
        return powerfactor;
    }

    public void setPowerfactor(String powerfactor) {
        this.powerfactor = powerfactor;
    }


}
