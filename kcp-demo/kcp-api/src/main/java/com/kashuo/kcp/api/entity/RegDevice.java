package com.kashuo.kcp.api.entity;

import com.huawei.iotplatform.client.dto.RegDirectDeviceInDTO;

/**
 * Created by dell-pc on 2018/4/15.
 */
public class RegDevice extends RegDirectDeviceInDTO {

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    private Integer positionId;


}
