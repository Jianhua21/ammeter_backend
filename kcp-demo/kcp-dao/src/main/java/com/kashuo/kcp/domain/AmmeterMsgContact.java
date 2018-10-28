package com.kashuo.kcp.domain;

public class AmmeterMsgContact {
    private Integer id;

    private String contactName1;

    private Integer contactPhone1;

    private String contactName2;

    private Integer contactPhone2;

    private String contactName3;

    private Integer contactPhone3;

    private Integer channelId;

    private Integer projectId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContactName1() {
        return contactName1;
    }

    public void setContactName1(String contactName1) {
        this.contactName1 = contactName1 == null ? null : contactName1.trim();
    }

    public Integer getContactPhone1() {
        return contactPhone1;
    }

    public void setContactPhone1(Integer contactPhone1) {
        this.contactPhone1 = contactPhone1;
    }

    public String getContactName2() {
        return contactName2;
    }

    public void setContactName2(String contactName2) {
        this.contactName2 = contactName2 == null ? null : contactName2.trim();
    }

    public Integer getContactPhone2() {
        return contactPhone2;
    }

    public void setContactPhone2(Integer contactPhone2) {
        this.contactPhone2 = contactPhone2;
    }

    public String getContactName3() {
        return contactName3;
    }

    public void setContactName3(String contactName3) {
        this.contactName3 = contactName3 == null ? null : contactName3.trim();
    }

    public Integer getContactPhone3() {
        return contactPhone3;
    }

    public void setContactPhone3(Integer contactPhone3) {
        this.contactPhone3 = contactPhone3;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
}