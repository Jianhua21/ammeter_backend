package com.kashuo.kcp.eums;

/**
 * Created by dell-pc on 2019/3/23.
 */
public enum ThirdPartyDeviceStatus {

    NORMAL("0","设备正常"),
    SELF_CHECKING("1","自检"),
    OFFLINE("2","设备不在线"),
    NO_ELECT("3","设备未上电"),
    STATE_04("4","底座未安装告警"),
    STATE_05("5","电量告警"),
    STATE_06("6","火灾告警"),
    STATE_11("11","位移告警"),
    STATE_12("12","水位告警"),
    STATE_13("13","溢满告警"),
    STATE_14("14","光感告警"),
    STATE_22("22","电流告警"),
    STATE_09("23","跳闸告警"),
    STATES_150("4,","脱离底座故障"),
    STATES_151("4,","脱离底座故障恢复"),

    STATES_30("1","火警（可燃气体、电气火灾报警）"),
    STATES_31("2,","低限报警"),
    STATES_32("3,","高限报警"),
    STATES_33("4,","超量程报警"),
    STATES_34("5,","剩余电流报警"),
    STATES_35("6,","温度报警"),
    STATES_36("7,","电弧报警"),
    STATES_37("8,","感烟探测器报警"),
    STATES_38("9,","感温探测器报警"),
    STATES_39("10,","手动报警按钮报警"),
    STATES_40("11,","火焰探测器报警");

    private String code;

    private String name;

     ThirdPartyDeviceStatus(String code,String name){
        this.code =code;
        this.name=name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String parseCode(String name) {
        for (ThirdPartyDeviceStatus types : ThirdPartyDeviceStatus.values()) {
            if (types.name.equals(name)) {
                return types.code;
            }
        }
        return "";
    }
}
