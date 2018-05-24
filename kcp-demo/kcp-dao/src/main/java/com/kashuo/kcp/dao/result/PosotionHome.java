package com.kashuo.kcp.dao.result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell-pc on 2018/5/5.
 */
public class PosotionHome {

    private Integer id;

    private String deviceId;

    private String name;

    private String iconStyle ="green";

    private List<Float> position =new ArrayList<>();

    //经度
    private Float amapLongitude;
    //纬度
    private Float amapLatitude;

    private String imei;

    private String statusName;

    private String remark;

    public String getWarningStatus() {
        return warningStatus;
    }

    public void setWarningStatus(String warningStatus) {
        this.warningStatus = warningStatus;
    }

    private String warningStatus;

    public String getWarningDesc() {
        return warningDesc;
    }

    public void setWarningDesc(String warningDesc) {
        this.warningDesc = warningDesc;
    }

    private String warningDesc;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconStyle() {
        if("1".equals(warningStatus)){
            iconStyle ="yellow";
        }
        return iconStyle;
    }

    public void setIconStyle(String iconStyle) {
        this.iconStyle = iconStyle;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Float> getPosition() {
        position.add(amapLongitude);
        position.add(amapLatitude);
        return position;
    }

    public void setPosition(List<Float> amap) {
        this.position = amap;
    }

    public Float getAmapLongitude() {
        return amapLongitude;
    }

    public void setAmapLongitude(Float amapLongitude) {
        this.amapLongitude = amapLongitude;
    }

    public Float getAmapLatitude() {
        return amapLatitude;
    }

    public void setAmapLatitude(Float amapLatitude) {
        this.amapLatitude = amapLatitude;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getAddress() {

        address = remark;
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private String address;


}