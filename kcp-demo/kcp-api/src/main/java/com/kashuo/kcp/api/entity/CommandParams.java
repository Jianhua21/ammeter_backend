package com.kashuo.kcp.api.entity;

/**
 * Created by dell-pc on 2018/4/30.
 */
public class CommandParams {
    //发送指令的数据库key
    private String commandKey;
    //发送指令的回调函数url
    private String callBackKey;
    //发送指令所需的数据
    private String data;
    //1 STM32系统  2-NB模块
    private Integer commandType;
    //是否可变命令
    private String isChanged;
    //是否645电表 电压 电流
    private boolean dltFlag = false;
    //电表地址
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isDltFlag() {
        return dltFlag;
    }

    public void setDltFlag(boolean dltFlag) {
        this.dltFlag = dltFlag;
    }

    public String getIsChanged() {
        return isChanged;
    }

    public void setIsChanged(String isChanged) {
        this.isChanged = isChanged;
    }

    public Integer getCommandType() {
        return commandType;
    }

    public void setCommandType(Integer commandType) {
        this.commandType = commandType;
    }

    public boolean isDataFlag() {
        return dataFlag;
    }

    public void setDataFlag(boolean dataFlag) {
        this.dataFlag = dataFlag;
    }

    //是否需要数据
    private boolean dataFlag = false;


    public String getCommandKey() {
        return commandKey;
    }

    public void setCommandKey(String commandKey) {
        this.commandKey = commandKey;
    }

    public String getCallBackKey() {
        return callBackKey;
    }

    public void setCallBackKey(String callBackKey) {
        this.callBackKey = callBackKey;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDataLength() {
        StringBuilder sb = new StringBuilder();
        return data.length() < 10 ?  sb.append("0").append(data.length()).toString():sb.append(data.length()).toString();
    }

    public String commandData(String command){
        StringBuilder sb = new StringBuilder();
        return  command.length() < 10 ? sb.append("0").append(command.length()).append(command).toString()
                :sb.append(command.length()).append(command).toString();
    }

    public String changedCommandData(String command){
        StringBuilder sb = new StringBuilder();
        return  command.length() < 10 ? sb.append("0").append(command.length()/2).append(command).toString()
                :sb.append(command.length()/2).append(command).toString();
    }

    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        sb.append("0").append(6);
        System.out.println(sb.toString());
    }
}
