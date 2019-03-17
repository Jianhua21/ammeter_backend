package com.kashuo.kcp.domain;

import com.kashuo.kcp.eums.PlateTypes;
import com.kashuo.kcp.utils.DateUtils;
import com.kashuo.kcp.utils.StringUtil;
import com.kashuo.kcp.utils.StringUtils;
import io.swagger.annotations.ApiModelProperty;

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
    //电表型号
    private String deviceModel;
    //用电单位
    private String companyName;
    //合同状态
    private String agreementStatus;
    //联系人信息
    private String contactInfo;

    @ApiModelProperty("电池状态")
    private String batteryStatus;
    @ApiModelProperty("亮度传感器信息")
    private String sensor;
    @ApiModelProperty("水面距离")
    private String surfaceDistance;
    @ApiModelProperty("倾斜传感器信息")
    private String tiltSensor;
    @ApiModelProperty("水位满溢传感器信息")
    private String waterLevelSensor;
    @ApiModelProperty("环境温度")
    private String enTemperature;
    @ApiModelProperty("环境湿度")
    private String enHumidity;
    @ApiModelProperty("烟雾告警信息")
    private String smokeWarning;

    private Integer platform;

    private String riData;

    private String deviceImei;

    private String devicePlatform;

    public String getDeviceImei() {
        return StringUtil.pageViewToEmpty(deviceImei);
    }

    public void setDeviceImei(String deviceImei) {
        this.deviceImei = deviceImei;
    }

    public String getDevicePlatform() {
        try {
            return PlateTypes.parseName(Integer.parseInt(devicePlatform));
        }catch (Exception e){
            return "-";
        }
    }

    public void setDevicePlatform(String devicePlatform) {
        this.devicePlatform = devicePlatform;
    }

    public String getRiData() {
        return riData;
    }

    public void setRiData(String riData) {
        this.riData = riData;
    }

    public String getEnTemperature() {
        if(enTemperature != null){
            enTemperature = enTemperature+" ℃";
        }else{
            enTemperature = "-";
        }
        return enTemperature;
    }

    public void setEnTemperature(String enTemperature) {
        this.enTemperature = enTemperature;
    }

    public String getEnHumidity() {
        if(enHumidity != null){
            enHumidity = enHumidity+" %";
        }else{
            enHumidity = "-";
        }
        return enHumidity;
    }

    public void setEnHumidity(String enHumidity) {
        this.enHumidity = enHumidity;
    }

    public String getSmokeWarning() {
        if(smokeWarning == null){
            return "-";
        }else if("A0".equals(smokeWarning)){
            return " 正常 ";
        }else{
            return " 有告警发生 ";
        }
    }

    public void setSmokeWarning(String smokeWarning) {
        this.smokeWarning = smokeWarning;
    }

    public Integer getPlatform() {
        return platform;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }

    public String getBatteryStatus() {
        try {
            batteryStatus = String.valueOf(Integer.parseInt(batteryStatus) * 1d / 100) + "V";
        }catch (Exception e){
            batteryStatus ="0 V";
        }
        return batteryStatus ;
    }

    public void setBatteryStatus(String batteryStatus) {
        this.batteryStatus = batteryStatus;
    }

    public String getSensor() {
        return sensor;
    }

    public void setSensor(String sensor) {
        this.sensor = sensor;
    }

    public String getSurfaceDistance() {
        try {
            surfaceDistance = String.valueOf(Integer.parseInt(surfaceDistance) * 1d / 100) + "M";
        }catch (Exception e){
            surfaceDistance = "0 M";
        }
        return surfaceDistance;
    }

    public void setSurfaceDistance(String surfaceDistance) {
        this.surfaceDistance = surfaceDistance;
    }

    public String getTiltSensor() {
        if(tiltSensor == null){
            return "-";
        }else if("A0".equals(tiltSensor)){
            return " 正常 ";
        }else{
            return " 倾斜 ";
        }

    }

    public void setTiltSensor(String tiltSensor) {
        this.tiltSensor = tiltSensor;
    }

    public String getWaterLevelSensor() {
        if(waterLevelSensor == null){
            return "-";
        }
        else if("W0".equals(waterLevelSensor)){
            return " 正常水位 ";
        }else{
            return  " 水位已漫出 ";
        }
    }

    public void setWaterLevelSensor(String waterLevelSensor) {
        this.waterLevelSensor = waterLevelSensor;
    }


    public String getDeviceModel() {
        return StringUtil.pageViewToEmpty(deviceModel);
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getCompanyName() {
        return StringUtil.pageViewToEmpty(companyName);
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAgreementStatus() {
        return StringUtil.pageViewToEmpty(agreementStatus);
    }

    public void setAgreementStatus(String agreementStatus) {
        this.agreementStatus = agreementStatus;
    }

    public String getContactInfo() {
        return StringUtil.pageViewToEmpty(contactInfo);
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getName() {
        return StringUtil.pageViewToEmpty(name);
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
        return StringUtil.pageViewToEmpty(gisAmap);
    }

    public void setGisAmap(String gisAmap) {
        this.gisAmap = gisAmap;
    }

    public String getInstaller() {
        return StringUtil.pageViewToEmpty(installer);
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
        return StringUtil.pageViewToEmpty(remark);
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOnlineStatus() {
        return StringUtil.pageViewToEmpty(onlineStatus);
    }

    public void setOnlineStatus(String onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public String getAmmeterNumber() {
        return StringUtil.pageViewToEmpty(ammeterNumber);
    }

    public void setAmmeterNumber(String ammeterNumber) {
        this.ammeterNumber = ammeterNumber;
    }

    public String getAddress() {
        return StringUtil.pageViewToEmpty(address);
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
        return StringUtil.pageViewToEmpty(meterNo);
    }

    public void setMeterNo(String meterNo) {
        this.meterNo = meterNo;
    }

    public String getAssManageCode() {
        return StringUtil.pageViewToEmpty(assManageCode);
    }

    public void setAssManageCode(String assManageCode) {
        this.assManageCode = assManageCode;
    }


    public String getType() {
        return StringUtil.pageViewToEmpty(type);
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProductDate() {
        return DateUtils.dateDetailToString(productDate);
    }

    public void setProductDate(Date productDate) {
        this.productDate = productDate;
    }

    public String getProtocolNo() {
        return StringUtil.pageViewToEmpty(protocolNo);
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
        return StringUtil.pageViewToEmpty(activePower);
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
