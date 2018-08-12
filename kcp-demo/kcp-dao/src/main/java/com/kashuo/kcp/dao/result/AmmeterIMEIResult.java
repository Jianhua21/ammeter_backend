package com.kashuo.kcp.dao.result;

import com.kashuo.kcp.utils.DateUtils;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * Created by dell-pc on 2018/8/11.
 */
public class AmmeterIMEIResult {
    @ApiModelProperty(value = "imei")
    private String imei;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "创建人")
    private String createBy;
    @ApiModelProperty(value = "设备名称")
    private String deviceName;

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getCreateTime() {
        return DateUtils.dateToString(createTime);
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
}
