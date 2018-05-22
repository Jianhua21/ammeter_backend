package com.kashuo.kcp.dao.result;

import java.util.List;

/**
 * Created by dell-pc on 2018/5/9.
 */
public class WarningHome {

    private Integer onlineDevices =0;

    private Integer offlineDevices =0;

    private Integer  problemDevices=0;

    private Integer totalDevices = 0;

    private Integer warningNumbers=0;

    private Integer currentWarnings=0;

    private Integer historyWarnings=0;

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

    private WarningCategory warningCategories;


    public Integer getTotalDevices() {
        return totalDevices;
    }

    public void setTotalDevices(Integer totalDevices) {
        this.totalDevices = totalDevices;
    }

    public Integer getOnlineDevices() {
        return onlineDevices;
    }

    public void setOnlineDevices(Integer onlineDevices) {
        this.onlineDevices = onlineDevices;
    }

    public Integer getOfflineDevices() {
        return offlineDevices;
    }

    public void setOfflineDevices(Integer offlineDevices) {
        this.offlineDevices = offlineDevices;
    }

    public Integer getProblemDevices() {
        return problemDevices;
    }

    public void setProblemDevices(Integer problemDevices) {
        this.problemDevices = problemDevices;
    }

    public WarningCategory getWarningCategories() {
        return warningCategories;
    }

    public void setWarningCategories(WarningCategory warningCategories) {
        this.warningCategories = warningCategories;
    }

}
