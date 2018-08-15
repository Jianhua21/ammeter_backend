package com.kashuo.kcp.domain;

import io.swagger.annotations.ApiModelProperty;

public class AmmeterImeiKey {
    private Integer id;
    @ApiModelProperty(value = "IMEI")
    private String imei;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei == null ? null : imei.trim();
    }
}