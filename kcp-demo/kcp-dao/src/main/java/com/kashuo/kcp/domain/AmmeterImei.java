package com.kashuo.kcp.domain;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class AmmeterImei extends AmmeterImeiKey {
    private Date createTime;

    private String createBy;
    @ApiModelProperty(value = "当前页")
    private Integer pageIndex =1;
    @ApiModelProperty(value = "当前显示数")
    private Integer pageSize =10;
    @ApiModelProperty("机构Id")
    private Integer channelId ;


    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }
}