package com.kashuo.kcp.eums;

/**
 * Created by dell-pc on 2019/4/1.
 */
public enum DeviceStatus {
    NORMAL(0,"设备正常"),
    LowBattery(4,"电量低"),
    STATE_05(5,"光感告警"),
    STATE_06(6,"水位告警"),
    STATE_07(7,"倾斜"),
    STATE_08(8,"水位满溢"),
    STATE_09(9,"温度告警"),
    STATE_10(10,"湿度告警"),
    STATE_11(11,"火灾告警"),
    STATES_12(12,"脱离底座故障恢复"),
    STATE_13(13,"位移告警"),
    STATE_14(14,"电流告警"),
    STATE_15(15,"跳闸告警"),
    STATES_16(16,"脱离底座故障"),


    LowBattery_Cancel(104,"取消电量低告警"),
    STATE_05_Cancel(105,"取消光感告警"),
    STATE_06_Cancel(106,"取消水位告警"),
    STATE_07_Cancel(107,"取消倾斜告警"),
    STATE_08_Cancel(108,"取消水位满溢告警"),
    STATE_09_Cancel(109,"取消温度告警"),
    STATE_10_Cancel(110,"取消湿度告警"),
    STATE_11_Cancel(111,"取消火灾告警"),
    STATES_12_Cancel(112,"取消脱离底座故障恢复"),
    STATE_13_Cancel(113,"取消位移告警"),
    STATE_14_Cancel(114,"取消电流告警"),
    STATE_15_Cancel(115,"取消跳闸告警"),
    STATES_16_Cancel(116,"取消脱离底座故障");

    private Integer  code;

    private String  statusName;

    DeviceStatus(Integer code,String statusName){
        this.code=code;
        this.statusName =statusName;
    }

    public static String parseStatusName(Integer code){
        for (DeviceStatus deviceStatus:DeviceStatus.values()){
            if(deviceStatus.getCode() ==code){
                return deviceStatus.getStatusName();
            }
        }
        return "-";
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
