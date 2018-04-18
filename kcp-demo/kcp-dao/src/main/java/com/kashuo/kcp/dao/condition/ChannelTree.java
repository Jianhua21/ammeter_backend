package com.kashuo.kcp.dao.condition;


import com.kashuo.kcp.domain.AmmeterChannel;

import java.util.ArrayList;
import java.util.List;

public class ChannelTree {

    private Integer id;

    private String channelName;

    private String channelCode;

    private Byte channelType;

    private String address;

    private String contactName;

    private String mobilePhone;

    private String email;

    private Byte status;

    private String levelCode;

    private Integer depth = 0;

    private Integer realDepth = 0;

    private Integer totalNum;

    private String balanceBank;
    private String accountName;
    private String accountNumber;
    private String bankCode;

    private Integer belongToCompanyId;

    private List<ChannelTree> nodes = new ArrayList<>();

    private ChannelTree() {
        super();
    }

    public ChannelTree(AmmeterChannel channel) {
        this.id = channel.getId();
        this.levelCode = channel.getLevelCode();
        this.channelName = channel.getChannelName();
        this.channelType = channel.getChannelType();
        this.channelCode = channel.getChannelCode();
        this.address = channel.getAddress();
        this.contactName = channel.getContactName();
        this.mobilePhone = channel.getMobilePhone();
        this.email = channel.getEmail();
        this.status = channel.getStatus();
        this.depth = 0;
        if (channel.getLevelCode() != null) {
            this.realDepth = channel.getLevelCode().length() / 3;
        }
    }

    public ChannelTree buildTree(List<AmmeterChannel> allList) {
        this.setNodes(queryNodeList(this.getLevelCode(), allList));
        return this;
    }

    private List<ChannelTree> queryNodeList(String pid, List<AmmeterChannel> allList) {
        List<ChannelTree> treeList = new ArrayList<>();
        for (AmmeterChannel c : allList) {
            if (c.getLevelCode() == null) {
                continue;
            }
            ChannelTree departmentTree = department2Tree(c);
            String levelCode = departmentTree.getLevelCode();
            if (levelCode.startsWith(pid) && levelCode.length() - pid.length() == 3) {
                departmentTree.setNodes(queryNodeList(c.getLevelCode(), allList));
                treeList.add(departmentTree);
            }
        }
        return treeList;
    }

    private ChannelTree department2Tree(AmmeterChannel channel) {
        ChannelTree node = new ChannelTree();
        node.setId(channel.getId());
        node.setChannelName(channel.getChannelName());
        node.setChannelCode(channel.getChannelCode());
        node.setChannelType(channel.getChannelType());
        node.setAddress(channel.getAddress());
        node.setContactName(channel.getContactName());
        node.setEmail(channel.getEmail());
        node.setMobilePhone(channel.getMobilePhone());
        node.setLevelCode(channel.getLevelCode());
        node.setChannelCode(channel.getChannelCode());
        node.setStatus(channel.getStatus());
        return node;
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
        this.channelName = channelName;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public List<ChannelTree> getNodes() {
        return nodes;
    }

    public void setNodes(List<ChannelTree> nodes) {
        this.nodes = nodes;
    }

    public Byte getChannelType() {
        return channelType;
    }

    public void setChannelType(Byte channelType) {
        this.channelType = channelType;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }


    public String getBalanceBank() {
        return balanceBank;
    }

    public void setBalanceBank(String balanceBank) {
        this.balanceBank = balanceBank;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public Integer getBelongToCompanyId() {
        return belongToCompanyId;
    }

    public void setBelongToCompanyId(Integer belongToCompanyId) {
        this.belongToCompanyId = belongToCompanyId;
    }
}
