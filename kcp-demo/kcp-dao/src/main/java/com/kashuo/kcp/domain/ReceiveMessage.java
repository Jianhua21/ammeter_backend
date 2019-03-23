package com.kashuo.kcp.domain;

import com.kashuo.kcp.utils.DateUtils;

import java.io.Serializable;

/**
 * Created by dell-pc on 2019/3/22.
 */
public class ReceiveMessage implements Serializable{

    private Integer id;

    private String imei;

    private String deviceType;

    private String time;

    private String remark;

    private String deviceName;

    private String status;

    private String operation;

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getImei() {
        return imei;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public static ReceiveMessage getMessageBody(AmmeterPosition position,String warningDesc,String warningType){
        ReceiveMessage message = new ReceiveMessage();
        message.setTime(DateUtils.getCurrentDateTime());
        message.setRemark(position.getAddress());
        message.setId(position.getId());
        message.setDeviceName(position.getName());
        message.setOperation(warningDesc);
        message.setImei(position.getImei());
        message.setStatus(position.getStatusName());
        message.setDeviceType(warningType);
        return message;
    }
}
