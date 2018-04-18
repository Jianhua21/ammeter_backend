package com.kashuo.kcp.dao.condition;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by dell-pc on 2017/9/16.
 */
public class AmmeterUpdateCondition implements Serializable {

    @NotNull(message="id 不能为空")
    private Integer id;
    @NotNull(message = "状态 不能为空")
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
