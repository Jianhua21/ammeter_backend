package com.kashuo.kcp.dao.condition;

import java.io.Serializable;

/**
 * Created by dell-pc on 2018/4/14.
 */
public class ServicesParams implements Serializable {
    private String serviceId;

    private String serviceType;

    private String eventTime;

    public DataParams getData() {
        return data;
    }

    public void setData(DataParams data) {
        this.data = data;
    }

    private DataParams data;

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }
}

