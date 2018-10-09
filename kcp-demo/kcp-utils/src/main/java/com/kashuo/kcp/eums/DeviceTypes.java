package com.kashuo.kcp.eums;

/**
 * Created by dell-pc on 2018/8/26.
 */
public enum  DeviceTypes {

    WELLCOVER("0","wellcover","智能井盖"),SMOKE_DETECTOR("1","smokeDetector","智能烟感"), ELECTRICALARM("2","electric_alarm","智慧电力");

    String code;

    String name;

    String desc;

    DeviceTypes(String code,String name,String desc){
        this.code = code;
        this.name = name;
        this.desc = desc;
    }

    public static String parseName(String code) {
        for (DeviceTypes types : DeviceTypes.values()) {
            if (types.code.equals(code)) {
                return types.name;
            }
        }
        return "";
    }

    public static String parseDesc(String code) {
        for (DeviceTypes types : DeviceTypes.values()) {
            if (types.code.equals(code)) {
                return types.desc;
            }
        }
        return "-";
    }

}
