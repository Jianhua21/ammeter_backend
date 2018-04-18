package com.kashuo.kcp.dao.condition;

import io.swagger.annotations.ApiModel;

/**
 * 角色列表查询
 * Created by Mr.ZHAO on 2016/5/17.
 */
@ApiModel(value = "角色")
public class RoleCondition extends BaseCondition {

    private Integer id;

    private String name;

    private Integer channelId;

    private String description;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId == null ? 0 : channelId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
