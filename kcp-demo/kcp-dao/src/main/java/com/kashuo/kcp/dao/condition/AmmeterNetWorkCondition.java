package com.kashuo.kcp.dao.condition;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by dell-pc on 2017/9/24.
 */
public class AmmeterNetWorkCondition implements Serializable {

    private String imsi;

    @ApiModelProperty(value = "当前页")
    private Integer pageIndex =1;
    @ApiModelProperty(value = "页码显示的数")
    private Integer pageSize =10;

    public String getImsi() {
        return imsi != null ? imsi.trim():"";
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
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
}
