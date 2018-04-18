package com.kashuo.kcp.dao.condition;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by dell-pc on 2018/4/10.
 */
@ApiModel("报警查询")
public class WarningCondition extends BaseCondition{
    @ApiModelProperty("查询时间")
    private String dateTime;

    @ApiModelProperty("每条消息的唯一编号，字符串类型，必要")
    private String sn;
    @ApiModelProperty("电表类型")
    private String ammeterType;
    @ApiModelProperty("机构Id")
    private Integer channelId;

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getAmmeterType() {
        return ammeterType;
    }

    public void setAmmeterType(String ammeterType) {
        this.ammeterType = ammeterType;
    }
}
