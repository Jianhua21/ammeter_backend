package com.kashuo.kcp.domain;


import com.kashuo.kcp.utils.DateUtils;
import com.kashuo.kcp.utils.HttpClientUtils;
import com.kashuo.kcp.utils.StringUtil;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by dell-pc on 2019/3/23.
 */
public class PushMessage implements Serializable {

    //用于约定推送的第三方Id
    private String screctKey;

    private String projectId;

    private String deviceName;

    private String deviceId;

    private String devicePosition;

    private String longitude;

    private String latitude;

    private String deviceType;

    private String deviceState;

    private String happenTime;

    private String data;

    private String pushType;

    private String pushTime;

    public String getScrectKey() {
        return screctKey;
    }

    public void setScrectKey(String screctKey) {
        this.screctKey = screctKey;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDevicePosition() {
        return devicePosition;
    }

    public void setDevicePosition(String devicePosition) {
        this.devicePosition = devicePosition;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceState() {
        return deviceState;
    }

    public void setDeviceState(String deviceState) {
        this.deviceState = deviceState;
    }

    public String getHappenTime() {
        return happenTime;
    }

    public void setHappenTime(String happenTime) {
        this.happenTime = happenTime;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getPushType() {
        return pushType;
    }

    public void setPushType(String pushType) {
        this.pushType = pushType;
    }

    public String getPushTime() {
        return pushTime;
    }

    public void setPushTime(String pushTime) {
        this.pushTime = pushTime;
    }

    public PushMessage getPushMessage(AmmeterPosition position,AmmeterChannel channel,PushMessage message,String status,String data,String pushType){
        message.setScrectKey("63f454550f2342a9985ca8bc447baa5c");
        message.setProjectId(channel.getThirdParty());
        message.setDeviceId(position.getDeviceId());
        message.setDeviceName(position.getName());
        message.setDevicePosition(position.getAddress());
        message.setLongitude(position.getGpsLongitude());
        message.setLatitude(position.getGpsLatitude());
        message.setDeviceType(String.valueOf(position.getDeviceType()));
        message.setDeviceState(status);
        message.setHappenTime(DateUtils.getCurrentDateTime());
        message.setData(data);
        message.setPushType(pushType);
        message.setPushTime(DateUtils.getCurrentDateTime());
        return message;
    }
}
