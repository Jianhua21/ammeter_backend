package com.kashuo.kcp.domain;

import io.swagger.annotations.ApiModelProperty;

public class AmmeterConfig {
    @ApiModelProperty("配置id")
    private Integer id;

    @ApiModelProperty("设备位置Id")
    private Integer positionId;
    @ApiModelProperty("APN地址")
    private String apnAddress;
    @ApiModelProperty("APN 状态  0 - 默认配置   1- 失败   2-执行中 3-需要执行 4-执行成功")
    private Integer apnStatus;
    @ApiModelProperty("CDP")
    private String cdp;
    @ApiModelProperty("CDP状态")
    private Integer cdpStatus;
    @ApiModelProperty("设置NB保活时间  ,30,60,90,120,150  单位秒")
    private Integer nb;
    @ApiModelProperty("NB状态")
    private Integer nbStatus;

    public Integer getApnStatus() {
        return apnStatus;
    }

    public void setApnStatus(Integer apnStatus) {
        this.apnStatus = apnStatus;
    }

    public Integer getCdpStatus() {
        return cdpStatus;
    }

    public void setCdpStatus(Integer cdpStatus) {
        this.cdpStatus = cdpStatus;
    }

    public Integer getNbStatus() {
        return nbStatus;
    }

    public void setNbStatus(Integer nbStatus) {
        this.nbStatus = nbStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    public String getApnAddress() {
        return apnAddress != null ? apnAddress:"";
    }

    public void setApnAddress(String apnAddress) {
        this.apnAddress = apnAddress == null ? null : apnAddress.trim();
    }

    public String getCdp() {
        return cdp != null ? cdp : "";
    }

    public void setCdp(String cdp) {
        this.cdp = cdp == null ? null : cdp.trim();
    }

    public Integer getNb() {
        return nb != null ? nb :90;
    }

    public void setNb(Integer nb) {
        this.nb = nb;
    }
}