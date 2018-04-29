package com.kashuo.kcp.dao.condition;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by dell-pc on 2018/4/21.
 */
@ApiModel("命令下发条件")
public class CommandCondition {

    @ApiModelProperty("设备Id")
    private String deviceId;
    @ApiModelProperty("sn消息唯一码")
    private String sn;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }
}
