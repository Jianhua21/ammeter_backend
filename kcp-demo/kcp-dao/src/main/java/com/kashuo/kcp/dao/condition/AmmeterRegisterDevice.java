package com.kashuo.kcp.dao.condition;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * Created by dell-pc on 2018/4/16.
 */
@ApiModel("直连设备注册")
public class AmmeterRegisterDevice {
    @ApiModelProperty("设备定位Id")
    @NotNull
    Integer positionId;
    @ApiModelProperty("消息唯一编号")
    String sn;

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }
}
