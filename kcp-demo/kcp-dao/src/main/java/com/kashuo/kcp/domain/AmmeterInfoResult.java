package com.kashuo.kcp.domain;

import com.kashuo.kcp.utils.DateUtils;
import com.kashuo.kcp.utils.StringUtil;
import com.kashuo.kcp.utils.StringUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dell-pc on 2017/9/9.
 */
public class AmmeterInfoResult implements Serializable{

    /***
     * id
     */
    private Integer id;
    /***
     * 电表唯一识别号
     */
    private String imsi;
    //电表名称
    private String name;
    /***
     * 通讯地址
     */
    private String address;
    /***
     * 表号
     */
    private String meterNo;

    private String ammeterNumber;
    /***
     * 资产管理编码(ASCII码)
     */
    private String assManageCode;
    //电表型号(ASCII码)
    private String type;
    //生产日期(ASCII码)
    private Date productDate;
    //协议版本号(ASCII码
    private String protocolNo;
    //工作状态的Id
    private String infoId;
    //(当前)正向有功总电能
    private String activePower;
    //(当前)反向有功总电能
    private String reverseActivePower;
    //状态
    @Deprecated
    private Integer status;

    //GIS 高德定位
    private String gisAmap;
    //安装人员
    private String installer;
    //安装时间
    private Date  createDate;
    //备注
    private String remark;
    //在线状态
    private String onlineStatus;

    public String getName() {
        return StringUtil.nullToEmpty(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImsi() {

        return StringUtil.nullToEmpty(imsi);
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getGisAmap() {
        return StringUtil.nullToEmpty(gisAmap);
    }

    public void setGisAmap(String gisAmap) {
        this.gisAmap = gisAmap;
    }

    public String getInstaller() {
        return StringUtil.nullToEmpty(installer);
    }

    public void setInstaller(String installer) {
        this.installer = installer;
    }

    public String getCreateDate() {
        return createDate!= null ? DateUtils.dateToString(createDate):"-";
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getRemark() {
        return StringUtil.nullToEmpty(remark);
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOnlineStatus() {
        return StringUtil.nullToEmpty(onlineStatus);
    }

    public void setOnlineStatus(String onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public String getAmmeterNumber() {
        return StringUtil.nullToEmpty(ammeterNumber);
    }

    public void setAmmeterNumber(String ammeterNumber) {
        this.ammeterNumber = ammeterNumber;
    }

    public String getAddress() {
        return StringUtil.nullToEmpty(address);
    }

    public void setAddress(String address) {
        this.address = address;
    }




    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMeterNo() {
        return StringUtil.nullToEmpty(meterNo);
    }

    public void setMeterNo(String meterNo) {
        this.meterNo = meterNo;
    }

    public String getAssManageCode() {
        return StringUtil.nullToEmpty(assManageCode);
    }

    public void setAssManageCode(String assManageCode) {
        this.assManageCode = assManageCode;
    }


    public String getType() {
        return StringUtil.nullToEmpty(type);
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getProductDate() {
        return productDate;
    }

    public void setProductDate(Date productDate) {
        this.productDate = productDate;
    }

    public String getProtocolNo() {
        return StringUtil.nullToEmpty(protocolNo);
    }

    public void setProtocolNo(String protocolNo) {
        this.protocolNo = protocolNo;
    }

    public String getInfoId() {
        return infoId;
    }

    public void setInfoId(String infoId) {
        this.infoId = infoId;
    }

    public String getActivePower() {
        return StringUtil.nullToEmpty(activePower);
    }

    public void setActivePower(String activePower) {
        this.activePower = activePower;
    }

    public String getReverseActivePower() {
        return reverseActivePower;
    }

    public void setReverseActivePower(String reverseActivePower) {
        this.reverseActivePower = reverseActivePower;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
