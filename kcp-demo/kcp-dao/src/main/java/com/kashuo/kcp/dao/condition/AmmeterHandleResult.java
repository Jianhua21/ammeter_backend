package com.kashuo.kcp.dao.condition;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by dell-pc on 2018/4/14.
 */
@ApiModel
public class AmmeterHandleResult {

    @ApiModelProperty("协议头")
    private String protocolHander;

    @ApiModelProperty("协议版本")
    private String protocolVersion;
    @ApiModelProperty("电表类型")
    private String ammeterType;
    @ApiModelProperty("命令数据长度")
    private String data;
    @ApiModelProperty("命令")
    private String command;
    @ApiModelProperty("CRC值")
    private String crcValue;
    @ApiModelProperty("App服务的命令")
    private String realCommnd;

    private String length;

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getRealCommnd() {
        return realCommnd;
    }

    public void setRealCommnd(String realCommnd) {
        this.realCommnd = realCommnd;
    }

    public String getProtocolHander() {
        return protocolHander;
    }

    public void setProtocolHander(String protocolHander) {
        this.protocolHander = protocolHander;
    }

    public String getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(String protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public String getAmmeterType() {
        return ammeterType;
    }

    public void setAmmeterType(String ammeterType) {
        this.ammeterType = ammeterType;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getCrcValue() {
        return crcValue;
    }

    public void setCrcValue(String crcValue) {
        this.crcValue = crcValue;
    }
}
