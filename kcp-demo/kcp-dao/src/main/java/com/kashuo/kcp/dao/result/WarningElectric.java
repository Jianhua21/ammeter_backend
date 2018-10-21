package com.kashuo.kcp.dao.result;

/**
 * Created by dell-pc on 2018/10/21.
 */
public class WarningElectric {

    private Integer id;

    private Integer ammeterId;

    private String rsrqWarning;

    private String currentLimitWarning;

    private String electricLimitWarning;

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

    public String getRsrqWarning() {
        return rsrqWarning;
    }

    public void setRsrqWarning(String rsrqWarning) {
        this.rsrqWarning = rsrqWarning;
    }

    public String getCurrentLimitWarning() {
        return currentLimitWarning;
    }

    public void setCurrentLimitWarning(String currentLimitWarning) {
        this.currentLimitWarning = currentLimitWarning;
    }

    public String getElectricLimitWarning() {
        return electricLimitWarning;
    }

    public void setElectricLimitWarning(String electricLimitWarning) {
        this.electricLimitWarning = electricLimitWarning;
    }
}
