package com.kashuo.kcp.dao.result;

/**
 * Created by dell-pc on 2018/8/22.
 */
public class WarningDeviceHome {

    private Integer warningNumbers=0;

    private Integer currentWarnings=0;

    private Integer historyWarnings=0;

    private Integer onlineDevices =0;

    private Integer totalDevices = 0;

    private WarningDeviceCategory  warningCategories;

    private WarningSmokeDetectorCategory smokeDetectorCategory;

    public WarningSmokeDetectorCategory getSmokeDetectorCategory() {
        return smokeDetectorCategory;
    }

    public void setSmokeDetectorCategory(WarningSmokeDetectorCategory smokeDetectorCategory) {
        this.smokeDetectorCategory = smokeDetectorCategory;
    }

    public Integer getOnlineDevices() {
        return onlineDevices;
    }

    public void setOnlineDevices(Integer onlineDevices) {
        this.onlineDevices = onlineDevices;
    }

    public Integer getTotalDevices() {
        return totalDevices;
    }

    public void setTotalDevices(Integer totalDevices) {
        this.totalDevices = totalDevices;
    }



    public WarningDeviceCategory getWarningCategories() {
        return warningCategories;
    }

    public void setWarningCategories(WarningDeviceCategory warningCategories) {
        this.warningCategories = warningCategories;
    }

    public Integer getWarningNumbers() {
        return warningNumbers;
    }

    public void setWarningNumbers(Integer warningNumbers) {
        this.warningNumbers = warningNumbers;
    }

    public Integer getCurrentWarnings() {
        return currentWarnings;
    }

    public void setCurrentWarnings(Integer currentWarnings) {
        this.currentWarnings = currentWarnings;
    }

    public Integer getHistoryWarnings() {
        return historyWarnings;
    }

    public void setHistoryWarnings(Integer historyWarnings) {
        this.historyWarnings = historyWarnings;
    }


}
