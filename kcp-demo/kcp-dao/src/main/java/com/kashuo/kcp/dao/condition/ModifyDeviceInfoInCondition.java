package com.kashuo.kcp.dao.condition;

import com.iotplatform.client.dto.DeviceConfigDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by dell-pc on 2018/4/19.
 */
@ApiModel("修改设备信息")
public class ModifyDeviceInfoInCondition {

    @ApiModelProperty("设备Id")
    private String deviceId;
    @ApiModelProperty("设备名称")
    private String name;
    @ApiModelProperty("终端用户，若设备为网关，则endUser可选；若设备是通过网关接入的，则endUser必须为null")
    private String endUser;
    @ApiModelProperty("表示设备是否处于冻结状态，即是否上报数据（处于冻结状态，则不上报数据），取值有“1”,“0”可选")
    private String mute;
    @ApiModelProperty("厂商ID-可选")
    private String manufacturerId;
    @ApiModelProperty("厂商名")
    private String manufacturerName;
    @ApiModelProperty("设备的位置")
    private String location;
    @ApiModelProperty("设备类型: 大驼峰命名方式\n" +
            "MultiSensor\n" +
            "ContactSensor\n" +
            "Camera\n" +
            "Gateway\n")
    private String deviceType;

    @ApiModelProperty("型号\n" +
            "z-wave: ProductType + ProductId\n" +
            "16 进制： XXXX-XXXX 补0对齐\n" +
            "如：001A-0A12\n" +
            "其他协议再定\n")
    private String model;
    @ApiModelProperty("设备协议类型")
    private String protocolType;
    @ApiModelProperty("设备配置信息，自定义结构体")
    private DeviceConfigDTO deviceConfigDTO;
    @ApiModelProperty("消息唯一码")
    private String sn;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEndUser() {
        return endUser;
    }

    public void setEndUser(String endUser) {
        this.endUser = endUser;
    }

    public String getMute() {
        return mute;
    }

    public void setMute(String mute) {
        this.mute = mute;
    }

    public String getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(String manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getProtocolType() {
        return protocolType;
    }

    public void setProtocolType(String protocolType) {
        this.protocolType = protocolType;
    }

    public DeviceConfigDTO getDeviceConfigDTO() {
        return deviceConfigDTO;
    }

    public void setDeviceConfigDTO(DeviceConfigDTO deviceConfigDTO) {
        this.deviceConfigDTO = deviceConfigDTO;
    }
}
