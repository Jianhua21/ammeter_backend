package com.kashuo.kcp.domain;

import java.util.Date;

public class AmmeterWarning {
    private Integer id;

    private Integer ammeterId;

    private String warningStatus;

    private Date warningDate;

    private Date createDate;

    private String createBy;

    private String warningDesc;

    private Integer warningType;

    public Integer getWarningType() {
        return warningType;
    }

    public void setWarningType(Integer warningType) {
        this.warningType = warningType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAmmeterId() {
        return ammeterId;
    }

    public void setAmmeterId(Integer ammeterId) {
        this.ammeterId = ammeterId;
    }

    public String getWarningStatus() {
        return warningStatus;
    }

    public void setWarningStatus(String warningStatus) {
        this.warningStatus = warningStatus == null ? null : warningStatus.trim();
    }

    public Date getWarningDate() {
        return warningDate;
    }

    public void setWarningDate(Date warningDate) {
        this.warningDate = warningDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public String getWarningDesc() {
        return warningDesc;
    }

    public void setWarningDesc(String warningDesc) {
        this.warningDesc = warningDesc == null ? null : warningDesc.trim();
    }
}