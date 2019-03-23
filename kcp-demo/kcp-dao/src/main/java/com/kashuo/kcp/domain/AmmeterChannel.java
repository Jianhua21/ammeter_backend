package com.kashuo.kcp.domain;


import com.kashuo.kcp.utils.StringUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "机构信息条件")
public class AmmeterChannel {
    @ApiModelProperty("机构id,新增不需要")
    private Integer id;
    @ApiModelProperty("机构名称")
    private String channelName;
    @ApiModelProperty("机构类型,0-管理员机构，1-企业，2-其他")
    private Byte channelType;
    @ApiModelProperty("机构代码")
    private String channelCode;
    @ApiModelProperty("机构地址")
    private String address;
    @ApiModelProperty("机构联系人")
    private String contactName;
    @ApiModelProperty("机构联系人电话")
    private String mobilePhone;
    @ApiModelProperty("机构联系人邮箱")
    private String email;
    @ApiModelProperty("机构状态，默认0启用，1 停用")
    private Byte status;
    @ApiModelProperty("机构登记编码，默认 0000")
    private String levelCode;
    @ApiModelProperty("第三方推送的标记")
    private String thirdParty;

    public String getThirdParty() {
        return thirdParty;
    }

    public void setThirdParty(String thirdParty) {
        this.thirdParty = thirdParty;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName == null ? null : StringUtil.cn2en(channelName).trim();
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode == null ? null : channelCode.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName == null ? null : contactName.trim();
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone == null ? null : mobilePhone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getLevelCode() {
        return levelCode;
    }

    public void setLevelCode(String levelCode) {
        this.levelCode = levelCode;
    }

    public Byte getChannelType() {
        return channelType;
    }

    public void setChannelType(Byte channelType) {
        this.channelType = channelType;
    }

}