package com.kashuo.kcp.api.entity.callback;

/**
 * Created by dell-pc on 2018/4/23.
 */
public class BaseService {

    private String serviceId;

    private String serviceType;

    private String eventTime;

    private BaseData data;

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

    public BaseData getData() {
        return data;
    }

    public void setData(BaseData data) {
        this.data = data;
    }

    public class BaseData{
        private String meter;

        private String rawdata;

        public String getRawdata() {
            return rawdata;
        }

        public void setRawdata(String rawdata) {
            this.rawdata = rawdata;
        }

        public String getMeter() {
            return meter;
        }

        public void setMeter(String meter) {
            this.meter = meter;
        }
    }
}
