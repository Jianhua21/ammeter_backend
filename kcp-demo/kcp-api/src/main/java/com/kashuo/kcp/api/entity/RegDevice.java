package com.kashuo.kcp.api.entity;


import com.iotplatform.client.dto.RegDirectDeviceInDTO2;

/**
 * Created by dell-pc on 2018/4/15.
 */
public class RegDevice extends RegDirectDeviceInDTO2 {

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    private Integer positionId;


}
