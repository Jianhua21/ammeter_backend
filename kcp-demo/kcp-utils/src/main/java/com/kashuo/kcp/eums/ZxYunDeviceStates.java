package com.kashuo.kcp.eums;

/**
 * Created by dell-pc on 2019/3/5.
 */
public enum  ZxYunDeviceStates {

    STATES_00("0,","预留"),
    STATES_01("1","火警（可燃气体、电气火灾报警）"),
    STATES_02("2,","低限报警"),
    STATES_03("3,","高限报警"),
    STATES_04("4,","超量程报警"),
    STATES_05("5,","剩余电流报警"),
    STATES_06("6,","温度报警"),
    STATES_07("7,","电弧报警"),
    STATES_08("8,","感烟探测器报警"),
    STATES_09("9,","感温探测器报警"),
    STATES_10("10,","手动报警按钮报警"),
    STATES_11("11,","火焰探测器报警"),
    STATES_150("150,","脱离底座故障"),
    STATES_151("151,","脱离底座故障恢复")
    ;
    private String code;

    private String desc;

    ZxYunDeviceStates(String code,String desc){
        this.code =code;
        this.desc = desc;
    }

    public static String parseDesc(String code) {
        for (ZxYunDeviceStates types : ZxYunDeviceStates.values()) {
            if (types.code.equals(code)) {
                return types.desc;
            }
        }
        return "";
    }

}
