package com.kashuo.kcp.api.entity.callback;

/**
 * Created by dell-pc on 2018/4/23.
 */
public class DeviceDataChange {

    private String notifyType;

    private String requestId;

    private String deviceId;

    private String gatewayId;

    private BaseService service;

    public String getNotifyType() {
        return notifyType;
    }

    public void setNotifyType(String notifyType) {
        this.notifyType = notifyType;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getGatewayId() {
        return gatewayId;
    }

    public void setGatewayId(String gatewayId) {
        this.gatewayId = gatewayId;
    }

    public BaseService getService() {
        return service;
    }

    public void setService(BaseService service) {
        this.service = service;
    }
}
