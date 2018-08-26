package com.kashuo.kcp.api.entity.callback;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by dell-pc on 2018/8/26.
 */
public class DeviceInfoChange {

    @JsonProperty
    private String notifyType;

    private String deviceId;

    private String gatewayId;
}
