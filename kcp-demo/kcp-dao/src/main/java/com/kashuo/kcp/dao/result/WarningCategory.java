package com.kashuo.kcp.dao.result;

import java.math.BigDecimal;

/**
 * Created by dell-pc on 2018/5/14.
 */
public class WarningCategory {
    private String warningName ="告警饼状图";

    private Integer warningNumbers=0;

    private Integer warningOfflineDevices =0;

    private Integer warningRsrqDevices =0;

    private Integer normalDevices =0;

    private Integer totalDevices =0;

    private Integer currentLimitDevices =0;

    private Integer electricLimitDevices =0;

    public Integer getCurrentLimitDevices() {
        return currentLimitDevices;
    }

    public void setCurrentLimitDevices(Integer currentLimitDevices) {
        this.currentLimitDevices = currentLimitDevices;
    }

    public Integer getElectricLimitDevices() {
        return electricLimitDevices;
    }

    public void setElectricLimitDevices(Integer electricLimitDevices) {
        this.electricLimitDevices = electricLimitDevices;
    }

    public Integer getWarningOfflineDevices() {
        return warningOfflineDevices;
    }

    public void setWarningOfflineDevices(Integer warningOfflineDevices) {
        this.warningOfflineDevices = warningOfflineDevices;
    }

    public Integer getWarningRsrqDevices() {
        return warningRsrqDevices;
    }

    public void setWarningRsrqDevices(Integer warningRsrqDevices) {
        this.warningRsrqDevices = warningRsrqDevices;
    }

    public Integer getNormalDevices() {
        return normalDevices;
    }

    public void setNormalDevices(Integer normalDevices) {
        this.normalDevices = normalDevices;
    }

    public Integer getTotalDevices() {
        return totalDevices;
    }

    public void setTotalDevices(Integer totalDevices) {
        this.totalDevices = totalDevices;
    }

    public String getWarningName() {
        return warningName;
    }

    public void setWarningName(String warningName) {
        this.warningName = warningName;
    }

    public Integer getWarningNumbers() {
        return warningNumbers;
    }

    public void setWarningNumbers(Integer warningNumbers) {
        this.warningNumbers = warningNumbers;
    }

    public static void main(String[] args) {
        System.out.println((double)Math.round((double)4/11*100)/100);
    }
}
