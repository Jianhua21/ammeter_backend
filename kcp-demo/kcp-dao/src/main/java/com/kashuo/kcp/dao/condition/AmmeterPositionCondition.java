package com.kashuo.kcp.dao.condition;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by dell-pc on 2018/3/20.
 */
public class AmmeterPositionCondition implements Serializable {
    @ApiModelProperty(value = "当前页")
    private Integer pageIndex =1;
    @ApiModelProperty(value = "页码显示的数")
    private Integer pageSize =10;
    @ApiModelProperty(value = "电表名称")
    private String name;
    @ApiModelProperty(value = "电表imei号")
    private String number;
    @ApiModelProperty(value = "通讯地址")
    private String address;
    @ApiModelProperty
    private String imei;
    @ApiModelProperty("每条消息的唯一编号 ")
    private String sn;
    @ApiModelProperty("机构Id")
    private Integer channelId;

    public String getImei() {
        return imei != null ? imei.trim():"";
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
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

    public String getName() {
        return name != null ? name.trim():null;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number != null ? number.trim(): null;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAddress() {
        return address!= null ? address.trim():null;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
