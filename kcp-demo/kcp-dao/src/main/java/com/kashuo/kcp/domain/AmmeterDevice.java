package com.kashuo.kcp.domain;

import com.kashuo.kcp.utils.DateUtils;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class AmmeterDevice {

    /***
     * 根据数据库数据返回想要的发送数据字符串Id
     * @param ammeterDevice
     * @return
     */
    public int returnParamIndex(AmmeterDevice ammeterDevice){
        if(ammeterDevice == null){
            return -1;
        }else if(ammeterDevice.getAddress() == null){
            return 13;
        }else if(ammeterDevice.getMeterNo() == null){
            return 14;
        }else if(ammeterDevice.getAssManageCode() == null){
            return 15;
        }else if(ammeterDevice.getRatedVoltage()== null){
            return 16;
        }else if(ammeterDevice.getRatedCurrent()== null){
            return 17;
        }else if(ammeterDevice.getMaxCurrent() == null){
            return 18;
        }else if(ammeterDevice.getActiveAccLevel() == null){
            return 19;
        }else if(ammeterDevice.getReactiveAccLevel() == null){
            return 20;
        }else if(ammeterDevice.getActiveConstant() == null){
            return 21;
        }else if(ammeterDevice.getReactiveConstant() == null){
            return 22;
        }else if(ammeterDevice.getType() == null){
            return 23;
        }else if(ammeterDevice.getProductDate() == null){
            return 24;
        }else if(ammeterDevice.getProtocolNo() == null){
            return 25;
        }else if(ammeterDevice.getSoftwareNo() == null){
            return 26;
        }else if (ammeterDevice.getHardwareNo() == null){
            return 27;
        }
        return -1;
    }

    public void setParams(String result,Integer index,AmmeterDevice ammeterDevice){
        if(index == 13){
            ammeterDevice.setAddress(result);
        }
        if(index == 14){
            ammeterDevice.setMeterNo(result);
        }
        if(index == 15 ){
            ammeterDevice.setAssManageCode(result);
        }
        if(index == 16 ){
            ammeterDevice.setRatedVoltage(result);
        }
        if(index == 17 ){
            ammeterDevice.setRatedCurrent(result);
        }
        if(index == 18 ){
            ammeterDevice.setMaxCurrent(result);
        }
        if(index == 19 ){
           ammeterDevice.setActiveAccLevel(result);
        }
        if(index == 20 ){
            ammeterDevice.setReactiveAccLevel(result);
        }
        if(index == 21 ){
            ammeterDevice.setActiveConstant(result);
        }
        if(index == 22 ){
            ammeterDevice.setReactiveConstant(result);
        }
        if(index ==23 ){
            ammeterDevice.setType(result);
        }
        if(index ==24 ){
            if(DateUtils.stringToDate(result) == null){
                ammeterDevice.setProductDate(new Date());
            }else{
                ammeterDevice.setProductDate(DateUtils.stringToDate(result));
            }

        }
        if(index ==25 ){
            ammeterDevice.setProtocolNo(result);
        }
        if(index ==26 ){
            ammeterDevice.setSoftwareNo(result);
        }
        if(index ==27 ){
            ammeterDevice.setHardwareNo(result);
        }

    };
    private Integer id;

    private String imsi;

    private String address;
    @ApiModelProperty(value = "表号")
    private String meterNo;

    private String ammeterNumber;

    private String assManageCode;

    private String ratedVoltage;

    private String ratedCurrent;

    private String maxCurrent;

    private String activeAccLevel;

    private String reactiveAccLevel;

    private String activeConstant;

    private String reactiveConstant;

    private String type;

    private Date productDate;

    private String protocolNo;

    private String softwareNo;

    private String hardwareNo;

    private Integer sendStatus;

    private String inputMsg;

    private String descMsg;

    private Integer channelId;

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public String getDescMsg() {
        return descMsg;
    }

    public void setDescMsg(String descMsg) {
        this.descMsg = descMsg;
    }

    public String getAmmeterNumber() {
        return ammeterNumber;
    }

    public void setAmmeterNumber(String ammeterNumber) {
        this.ammeterNumber = ammeterNumber;
    }

    public String getInputMsg() {
        return inputMsg;
    }

    public void setInputMsg(String inputMsg) {
        this.inputMsg = inputMsg;
    }

    public Integer getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(Integer sendStatus) {
        this.sendStatus = sendStatus;
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
        this.imsi = imsi == null ? null : imsi.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getMeterNo() {
        return meterNo;
    }

    public void setMeterNo(String meterNo) {
        this.meterNo = meterNo == null ? null : meterNo.trim();
    }

    public String getAssManageCode() {
        return assManageCode;
    }

    public void setAssManageCode(String assManageCode) {
        this.assManageCode = assManageCode == null ? null : assManageCode.trim();
    }

    public String getRatedVoltage() {
        return ratedVoltage;
    }

    public void setRatedVoltage(String ratedVoltage) {
        this.ratedVoltage = ratedVoltage == null ? null : ratedVoltage.trim();
    }

    public String getRatedCurrent() {
        return ratedCurrent;
    }

    public void setRatedCurrent(String ratedCurrent) {
        this.ratedCurrent = ratedCurrent == null ? null : ratedCurrent.trim();
    }

    public String getMaxCurrent() {
        return maxCurrent;
    }

    public void setMaxCurrent(String maxCurrent) {
        this.maxCurrent = maxCurrent == null ? null : maxCurrent.trim();
    }

    public String getActiveAccLevel() {
        return activeAccLevel;
    }

    public void setActiveAccLevel(String activeAccLevel) {
        this.activeAccLevel = activeAccLevel == null ? null : activeAccLevel.trim();
    }

    public String getReactiveAccLevel() {
        return reactiveAccLevel;
    }

    public void setReactiveAccLevel(String reactiveAccLevel) {
        this.reactiveAccLevel = reactiveAccLevel == null ? null : reactiveAccLevel.trim();
    }

    public String getActiveConstant() {
        return activeConstant;
    }

    public void setActiveConstant(String activeConstant) {
        this.activeConstant = activeConstant;
    }

    public String getReactiveConstant() {
        return reactiveConstant;
    }

    public void setReactiveConstant(String reactiveConstant) {
        this.reactiveConstant = reactiveConstant;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
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
        this.protocolNo = protocolNo == null ? null : protocolNo.trim();
    }

    public String getSoftwareNo() {
        return softwareNo;
    }

    public void setSoftwareNo(String softwareNo) {
        this.softwareNo = softwareNo == null ? null : softwareNo.trim();
    }

    public String getHardwareNo() {
        return hardwareNo;
    }

    public void setHardwareNo(String hardwareNo) {
        this.hardwareNo = hardwareNo == null ? null : hardwareNo.trim();
    }
}