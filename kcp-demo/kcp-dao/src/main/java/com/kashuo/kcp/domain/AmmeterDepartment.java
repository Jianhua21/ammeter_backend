package com.kashuo.kcp.domain;

public class AmmeterDepartment {
    private Integer id;

    private String departmentName;

    private Byte departmentType;

    private String description;

    private Byte status;

    private String levelCode;

    private String createBy;

    private Integer channelId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName == null ? null : departmentName.trim();
    }

    public Byte getDepartmentType() {
        return departmentType;
    }

    public void setDepartmentType(Byte departmentType) {
        this.departmentType = departmentType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? "" : description.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getLevelCode() {
        return levelCode == null ? "" : levelCode.trim();
    }

    public void setLevelCode(String levelCode) {
        this.levelCode = levelCode;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }
}