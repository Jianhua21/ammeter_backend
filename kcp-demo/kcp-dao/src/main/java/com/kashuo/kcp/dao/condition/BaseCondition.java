package com.kashuo.kcp.dao.condition;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author zhaoqingping
 *         2016/04/11
 */
@ApiModel
public class BaseCondition {


    /**
     * 当前分页数
     */
    @ApiModelProperty(value = "当前分页数")
    private Integer pageIndex = 1;

    /**
     * 每页显示的记录数
     */
    @ApiModelProperty(value = "每页显示的记录数")
    private Integer pageSize = 15;

    @ApiModelProperty(hidden = true)
    private Integer userID;

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

    public Integer getOffset() {
        return (pageIndex - 1) * pageSize;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }
}
