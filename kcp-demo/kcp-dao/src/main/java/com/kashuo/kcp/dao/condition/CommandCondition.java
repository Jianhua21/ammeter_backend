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
    @ApiModelProperty("保活时间设定,按秒来发送60s,90s,120s")
    private String keepTime;
    @ApiModelProperty("sn消息唯一码")
    private String sn;

    public String getKeepTime() {
        return keepTime;
    }

    public void setKeepTime(String keepTime) {
        this.keepTime = keepTime;
    }

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
