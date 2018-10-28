package com.kashuo.kcp.api.entity.callback;

/**
 * Created by dell-pc on 2018/10/28.
 */
public class DataUdpParams {

    //1  总有功电量
    private String activePower;
    //2  有功电能(尖)
    private String strongPower;
    //3  有功电能(峰)
    private String highPower;
    //4  有功电能(平)
    private String normalPower;
    //5  有功电能(谷)
    private String lowerPower;
    //6  单相电压
    private String voltage;
    //7  单相电流
    private String current;
    //8  瞬时总有功功率
    private String instantPower;
    //9  功率因数
    private String powerFactor;
    //10  掉电次数
    private String powerOffTimes;
    //11  近一次掉电记录
    private String record;
    //12  表号
    private String ammeterNumber;
    //13  IMEI
    private String imei;
    //14  RSSI
    private String rssi;

    public String getActivePower() {
        return activePower;
    }

    public void setActivePower(String activePower) {
        this.activePower = activePower;
    }

    public String getStrongPower() {
        return strongPower;
    }

    public void setStrongPower(String strongPower) {
        this.strongPower = strongPower;
    }

    public String getHighPower() {
        return highPower;
    }

    public void setHighPower(String highPower) {
        this.highPower = highPower;
    }

    public String getNormalPower() {
        return normalPower;
    }

    public void setNormalPower(String normalPower) {
        this.normalPower = normalPower;
    }

    public String getLowerPower() {
        return lowerPower;
    }

    public void setLowerPower(String lowerPower) {
        this.lowerPower = lowerPower;
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

    public String getInstantPower() {
        return instantPower;
    }

    public void setInstantPower(String instantPower) {
        this.instantPower = instantPower;
    }

    public String getPowerFactor() {
        return powerFactor;
    }

    public void setPowerFactor(String powerFactor) {
        this.powerFactor = powerFactor;
    }

    public String getPowerOffTimes() {
        return powerOffTimes;
    }

    public void setPowerOffTimes(String powerOffTimes) {
        this.powerOffTimes = powerOffTimes;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public String getAmmeterNumber() {
        return ammeterNumber;
    }

    public void setAmmeterNumber(String ammeterNumber) {
        this.ammeterNumber = ammeterNumber;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getRssi() {
        return rssi;
    }

    public void setRssi(String rssi) {
        this.rssi = rssi;
    }
}
