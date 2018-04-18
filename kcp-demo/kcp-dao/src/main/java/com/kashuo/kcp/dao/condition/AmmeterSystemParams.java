package com.kashuo.kcp.dao.condition;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by dell-pc on 2018/4/15.
 */
public class AmmeterSystemParams {

    @ApiModelProperty("Iom平台appId")
    private String appId;
    @ApiModelProperty("Iom平台secret")
    private String secret;
    @ApiModelProperty("Iom平台地址")
    private String platformIp;
    @ApiModelProperty("Iom平台端口")
    private String platformPort;
    @ApiModelProperty("消息唯一码")
    private String sn;

    public String getPlatformIp() {
        return platformIp;
    }

    public void setPlatformIp(String platformIp) {
        this.platformIp = platformIp;
    }

    public String getPlatformPort() {
        return platformPort;
    }

    public void setPlatformPort(String platformPort) {
        this.platformPort = platformPort;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }
}
