package com.kashuo.kcp.domain;

public class AmmeterWorkingInfo {

    public int returnWorkInfoIndex(AmmeterWorkingInfo ammeterWorkingInfo){

        if(ammeterWorkingInfo == null || ammeterWorkingInfo.getZeroLineCurrent() == null){
            return 1;
        }else if(ammeterWorkingInfo.getStatus() != null &&ammeterWorkingInfo.getStatus() == 3 ){
            return 29;
        }else if(ammeterWorkingInfo.getStatus() != null &&  ammeterWorkingInfo.getStatus() == 4 ){
            return 33;
        }
        else if(ammeterWorkingInfo.getGridFrequency() == null){
            return 2;
        }else if(ammeterWorkingInfo.getAveragePowerMinute() == null){
            return 3;
        }else if(ammeterWorkingInfo.getActiveDemand() == null){
            return 4;
        }else if(ammeterWorkingInfo.getReactiveDemand() == null){
            return 5;
        }else if(ammeterWorkingInfo.getApparentDemand() == null){
            return 6;
        }else if(ammeterWorkingInfo.getTemperature() == null){
            return 7;
        }else if(ammeterWorkingInfo.getClockCellVoltage() == null){
            return 8;
        }else if(ammeterWorkingInfo.getNoPowerCellVoltage() == null){
            return 9;
        }else if(ammeterWorkingInfo.getBatteryWorkingTime() == null){
            return 10;
        }else if(ammeterWorkingInfo.getActivePower() == null){
            return 11;
        }else if(ammeterWorkingInfo.getReverseActivePower() == null){
            return 12;
        }
        return -1;
    }

    public void setParams(String result,Integer index,AmmeterWorkingInfo ammeterWorkingInfo){
        if(index ==1){
            ammeterWorkingInfo.setZeroLineCurrent(result);
        }else if(index ==2 ){
            ammeterWorkingInfo.setGridFrequency(result);
        }else if(index ==3 ){
            ammeterWorkingInfo.setAveragePowerMinute(result);
        }else if(index ==4 ){
            ammeterWorkingInfo.setActiveDemand(result);
        }else if(index ==5 ){
            ammeterWorkingInfo.setReactiveDemand(result);
        }else if(index ==6 ){
            ammeterWorkingInfo.setApparentDemand(result);
        }else if(index ==7 ){
            ammeterWorkingInfo.setTemperature(result);
        }else if(index ==8 ){
            ammeterWorkingInfo.setClockCellVoltage(result);
        }else if(index ==9 ){
            ammeterWorkingInfo.setNoPowerCellVoltage(result);
        }else if(index == 10 ){
            ammeterWorkingInfo.setBatteryWorkingTime(result);
        }else if(index ==11 ){
            ammeterWorkingInfo.setActivePower(result);
        }else if(index ==12 ){
            ammeterWorkingInfo.setReverseActivePower(result);
        }else if(index ==29){
            if("OK".equals(result)) {
                ammeterWorkingInfo.setStatus(1);
            }else if("ERR".equals(result)){
                ammeterWorkingInfo.setStatus(2);
            }
        }else if(index == 33) {
            if ("OK".equals(result)) {
                ammeterWorkingInfo.setStatus(2);
            } else if ("ERR".equals(result)) {
                ammeterWorkingInfo.setStatus(1);
            }
        }
    }

    private Integer id;

    private Integer ammeterId;

    private String zeroLineCurrent;

    private String gridFrequency;

    private String averagePowerMinute;

    private String activeDemand;

    private String reactiveDemand;

    private String apparentDemand;

    private String temperature;

    private String clockCellVoltage;

    private String noPowerCellVoltage;

    private String batteryWorkingTime;

    private String activePower;

    private String reverseActivePower;

    private Integer status;

    private  String descMsg;

    public String getDescMsg() {
        return descMsg;
    }

    public void setDescMsg(String descMsg) {
        this.descMsg = descMsg;
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

    public String getZeroLineCurrent() {
        return zeroLineCurrent;
    }

    public void setZeroLineCurrent(String zeroLineCurrent) {
        this.zeroLineCurrent = zeroLineCurrent == null ? null : zeroLineCurrent.trim();
    }

    public String getGridFrequency() {
        return gridFrequency;
    }

    public void setGridFrequency(String gridFrequency) {
        this.gridFrequency = gridFrequency == null ? null : gridFrequency.trim();
    }

    public String getAveragePowerMinute() {
        return averagePowerMinute;
    }

    public void setAveragePowerMinute(String averagePowerMinute) {
        this.averagePowerMinute = averagePowerMinute == null ? null : averagePowerMinute.trim();
    }

    public String getActiveDemand() {
        return activeDemand;
    }

    public void setActiveDemand(String activeDemand) {
        this.activeDemand = activeDemand == null ? null : activeDemand.trim();
    }

    public String getReactiveDemand() {
        return reactiveDemand;
    }

    public void setReactiveDemand(String reactiveDemand) {
        this.reactiveDemand = reactiveDemand == null ? null : reactiveDemand.trim();
    }

    public String getApparentDemand() {
        return apparentDemand;
    }

    public void setApparentDemand(String apparentDemand) {
        this.apparentDemand = apparentDemand == null ? null : apparentDemand.trim();
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature == null ? null : temperature.trim();
    }

    public String getClockCellVoltage() {
        return clockCellVoltage;
    }

    public void setClockCellVoltage(String clockCellVoltage) {
        this.clockCellVoltage = clockCellVoltage == null ? null : clockCellVoltage.trim();
    }

    public String getNoPowerCellVoltage() {
        return noPowerCellVoltage;
    }

    public void setNoPowerCellVoltage(String noPowerCellVoltage) {
        this.noPowerCellVoltage = noPowerCellVoltage == null ? null : noPowerCellVoltage.trim();
    }

    public String getBatteryWorkingTime() {
        return batteryWorkingTime;
    }

    public void setBatteryWorkingTime(String batteryWorkingTime) {
        this.batteryWorkingTime = batteryWorkingTime == null ? null : batteryWorkingTime.trim();
    }

    public String getActivePower() {
        return activePower;
    }

    public void setActivePower(String activePower) {
        this.activePower = activePower == null ? null : activePower.trim();
    }

    public String getReverseActivePower() {
        return reverseActivePower;
    }

    public void setReverseActivePower(String reverseActivePower) {
        this.reverseActivePower = reverseActivePower == null ? null : reverseActivePower.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}