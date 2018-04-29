package com.kashuo.kcp.dao.condition;

import io.swagger.annotations.ApiModel;

/**
 * Created by dell-pc on 2018/4/28.
 */
@ApiModel("imei校验")
public class IMEICondition {
    private String IMEI;

    private String sn;

    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }
}
