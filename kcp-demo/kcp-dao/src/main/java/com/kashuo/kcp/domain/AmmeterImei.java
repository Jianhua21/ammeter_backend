package com.kashuo.kcp.domain;

import java.util.Date;

public class AmmeterImei extends AmmeterImeiKey {
    private Date createTime;

    private String createBy;

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