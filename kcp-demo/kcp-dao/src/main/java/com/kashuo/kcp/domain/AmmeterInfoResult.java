package com.kashuo.kcp.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dell-pc on 2017/9/9.
 */
public class AmmeterInfoResult implements Serializable{

    /***
     * id
     */
    private Integer id;
    /***
     * 电表唯一识别号
     */
    private String imsi;
    /***
     * 通讯地址
     */
    private String address;
    /***
     * 表号
     */
    private String meterNo;

    private String ammeterNumber;
    /***
     * 资产管理编码(ASCII码)
     */
    private String assManageCode;
    /***
     * 额定电压
     */
    private String ratedVoltage;
    //额定电流/基本电流(ASCII码)
    private String ratedCurrent;
    //最大电流(ASCII码)
    private String maxCurrent;
    //有功准确度等级(ASCII码)
    private String activeAccLevel;
    //无功准确度等级(ASCII码)
    private String reactiveAccLevel;
    //电表有功常数
    private Integer activeConstant;
    //电表无功常数
    private Integer reactiveConstant;
    //电表型号(ASCII码)
    private String type;
    //生产日期(ASCII码)
    private Date productDate;
    //协议版本号(ASCII码
    private String protocolNo;
    //厂家软件版本号(ASCII码)
    private String softwareNo;
    //厂家硬件版本号(ASCII码)
    private String hardwareNo;
    //工作状态的Id
    private String infoId;
    //零线电流
    private String zeroLineCurrent;
    //电网频率
    private String gridFrequency;
    //一分钟有功总平均功率
    private String averagePowerMinute;
    //当前有功需量
    private String activeDemand;
    //当前无功需量
    private String reactiveDemand;
    //当前视在需量
    private String apparentDemand;
    //表内温度
    private String temperature;
    //时钟电池电压(内部)
    private String clockCellVoltage;
    //停电抄表电池电压 (外部)
    private String noPowerCellVoltage;
    //内部电池工作时间
    private String batteryWorkingTime;
    //(当前)正向有功总电能
    private String activePower;
    //(当前)反向有功总电能
    private String reverseActivePower;
    //状态


    public String getAmmeterNumber() {
        return ammeterNumber;
    }

    public void setAmmeterNumber(String ammeterNumber) {
        this.ammeterNumber = ammeterNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private Integer status;



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

    public String getMeterNo() {
        return meterNo;
    }

    public void setMeterNo(String meterNo) {
        this.meterNo = meterNo;
    }

    public String getAssManageCode() {
        return assManageCode;
    }

    public void setAssManageCode(String assManageCode) {
        this.assManageCode = assManageCode;
    }

    public String getRatedVoltage() {
        return ratedVoltage;
    }

    public void setRatedVoltage(String ratedVoltage) {
        this.ratedVoltage = ratedVoltage;
    }

    public String getRatedCurrent() {
        return ratedCurrent;
    }

    public void setRatedCurrent(String ratedCurrent) {
        this.ratedCurrent = ratedCurrent;
    }

    public String getMaxCurrent() {
        return maxCurrent;
    }

    public void setMaxCurrent(String maxCurrent) {
        this.maxCurrent = maxCurrent;
    }

    public String getActiveAccLevel() {
        return activeAccLevel;
    }

    public void setActiveAccLevel(String activeAccLevel) {
        this.activeAccLevel = activeAccLevel;
    }

    public String getReactiveAccLevel() {
        return reactiveAccLevel;
    }

    public void setReactiveAccLevel(String reactiveAccLevel) {
        this.reactiveAccLevel = reactiveAccLevel;
    }

    public Integer getActiveConstant() {
        return activeConstant;
    }

    public void setActiveConstant(Integer activeConstant) {
        this.activeConstant = activeConstant;
    }

    public Integer getReactiveConstant() {
        return reactiveConstant;
    }

    public void setReactiveConstant(Integer reactiveConstant) {
        this.reactiveConstant = reactiveConstant;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getProductDate() {
        return productDate;
    }

    public void setProductDate(Date productDate) {
        this.productDate = productDate;
    }

    public String getProtocolNo() {
        return protocolNo;
    }

    public void setProtocolNo(String protocolNo) {
        this.protocolNo = protocolNo;
    }

    public String getSoftwareNo() {
        return softwareNo;
    }

    public void setSoftwareNo(String softwareNo) {
        this.softwareNo = softwareNo;
    }

    public String getHardwareNo() {
        return hardwareNo;
    }

    public void setHardwareNo(String hardwareNo) {
        this.hardwareNo = hardwareNo;
    }

    public String getInfoId() {
        return infoId;
    }

    public void setInfoId(String infoId) {
        this.infoId = infoId;
    }

    public String getZeroLineCurrent() {
        return zeroLineCurrent;
    }

    public void setZeroLineCurrent(String zeroLineCurrent) {
        this.zeroLineCurrent = zeroLineCurrent;
    }

    public String getGridFrequency() {
        return gridFrequency;
    }

    public void setGridFrequency(String gridFrequency) {
        this.gridFrequency = gridFrequency;
    }

    public String getAveragePowerMinute() {
        return averagePowerMinute;
    }

    public void setAveragePowerMinute(String averagePowerMinute) {
        this.averagePowerMinute = averagePowerMinute;
    }

    public String getActiveDemand() {
        return activeDemand;
    }

    public void setActiveDemand(String activeDemand) {
        this.activeDemand = activeDemand;
    }

    public String getReactiveDemand() {
        return reactiveDemand;
    }

    public void setReactiveDemand(String reactiveDemand) {
        this.reactiveDemand = reactiveDemand;
    }

    public String getApparentDemand() {
        return apparentDemand;
    }

    public void setApparentDemand(String apparentDemand) {
        this.apparentDemand = apparentDemand;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getClockCellVoltage() {
        return clockCellVoltage;
    }

    public void setClockCellVoltage(String clockCellVoltage) {
        this.clockCellVoltage = clockCellVoltage;
    }

    public String getNoPowerCellVoltage() {
        return noPowerCellVoltage;
    }

    public void setNoPowerCellVoltage(String noPowerCellVoltage) {
        this.noPowerCellVoltage = noPowerCellVoltage;
    }

    public String getBatteryWorkingTime() {
        return batteryWorkingTime;
    }

    public void setBatteryWorkingTime(String batteryWorkingTime) {
        this.batteryWorkingTime = batteryWorkingTime;
    }

    public String getActivePower() {
        return activePower;
    }

    public void setActivePower(String activePower) {
        this.activePower = activePower;
    }

    public String getReverseActivePower() {
        return reverseActivePower;
    }

    public void setReverseActivePower(String reverseActivePower) {
        this.reverseActivePower = reverseActivePower;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
