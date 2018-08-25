package com.kashuo.kcp.dao.result;

/**
 * Created by dell-pc on 2018/8/22.
 */
public class WarningDeviceHome {

    private Integer warningNumbers=0;

    private Integer currentWarnings=0;

    private Integer historyWarnings=0;

    private WarningDeviceCategory  warningCategories;

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
