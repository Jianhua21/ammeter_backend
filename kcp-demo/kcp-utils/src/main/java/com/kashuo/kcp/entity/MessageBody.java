package com.kashuo.kcp.entity;

import java.util.List;

/**
 * Created by dell-pc on 2019/2/14.
 */
public class MessageBody {

    private String imei;

    private String status;

    private String name;

    private String address;

    private String remark;

    public String getRemark() {
        return "【"+remark+"】";
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getImei() {
        return "【"+imei+"】";
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getStatus() {
        return "【"+status+"】";
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return "【"+name+"】";
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        if(address != null && address.length()>18){
            address = "..."+address.substring(address.length()-15,address.length());
        }
        return "【"+address+"】";
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String toString(){
        return "{\"imei\":\""+this.getImei()+"\"," +
                "\"address\":\"" + this.getAddress()+"\"," +
                "\"status\":\"" +this.getStatus()+"\"," +
                "\"remark\":\"" +this.remark+"\"}";
    }
}
