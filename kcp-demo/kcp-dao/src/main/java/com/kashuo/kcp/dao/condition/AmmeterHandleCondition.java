package com.kashuo.kcp.dao.condition;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dell-pc on 2018/4/14.
 */
public class AmmeterHandleCondition implements Serializable {

    private String notifyType;

    private String requestId;

    private String deviceId;

    private String gatewayId;

    private List<ServicesParams> services;

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

    public String getGatewayId() {
        return gatewayId;
    }

    public void setGatewayId(String gatewayId) {
        this.gatewayId = gatewayId;
    }

    public List<ServicesParams> getServices() {
        return services;
    }

    public void setServices(List<ServicesParams> services) {
        this.services = services;
    }



    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }



}
