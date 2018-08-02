package com.kashuo.kcp.api.entity;

/**
 * Created by dell-pc on 2018/8/1.
 */
public class NbiotDTO {

    private String imei;

    private String prductName;

    private Integer writeMode;

    private Integer commandType;

    public Integer getCommandType() {
        return commandType;
    }

    public void setCommandType(Integer commandType) {
        this.commandType = commandType;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getPrductName() {
        return prductName;
    }

    public void setPrductName(String prductName) {
        this.prductName = prductName;
    }

    public Integer getWriteMode() {
        return writeMode;
    }

    public void setWriteMode(Integer writeMode) {
        this.writeMode = writeMode;
    }
}
