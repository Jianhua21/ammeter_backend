package com.kashuo.kcp.api.entity.callback;

/**
 * Created by dell-pc on 2019/3/5.
 */
public class DeviceZxYunGateWay {

    private String gatewayName;

    private String gatewayId;

    private boolean connectState;

    private String lastConnectTime;

    private String longitude;

    private String latitude;

    public String getGatewayName() {
        return gatewayName;
    }

    public void setGatewayName(String gatewayName) {
        this.gatewayName = gatewayName;
    }

    public String getGatewayId() {
        return gatewayId;
    }

    public void setGatewayId(String gatewayId) {
        this.gatewayId = gatewayId;
    }

    public boolean isConnectState() {
        return connectState;
    }

    public void setConnectState(boolean connectState) {
        this.connectState = connectState;
    }

    public String getLastConnectTime() {
        return lastConnectTime;
    }

    public void setLastConnectTime(String lastConnectTime) {
        this.lastConnectTime = lastConnectTime;
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
}
