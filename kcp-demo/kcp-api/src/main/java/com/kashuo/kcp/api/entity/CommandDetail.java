package com.kashuo.kcp.api.entity;

/**
 * Created by dell-pc on 2018/4/30.
 */
public class CommandDetail {

    private String  header;

    private Integer commandLength;

    private String command;

    private Integer dataLength;


    private String data;

    private String crcValue;

    private String context;

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public Integer getCommandLength() {
        return commandLength;
    }

    public void setCommandLength(Integer commandLength) {
        this.commandLength = commandLength;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public Integer getDataLength() {
        return dataLength;
    }

    public void setDataLength(Integer dataLength) {
        this.dataLength = dataLength;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCrcValue() {
        return crcValue;
    }

    public void setCrcValue(String crcValue) {
        this.crcValue = crcValue;
    }


}
