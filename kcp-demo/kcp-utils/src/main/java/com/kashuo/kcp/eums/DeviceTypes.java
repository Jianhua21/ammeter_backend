package com.kashuo.kcp.eums;

/**
 * Created by dell-pc on 2018/8/26.
 */
public enum  DeviceTypes {

    WELLCOVER("1","wellcover"),ELECTRICALARM("2","electric_alarm");

    String code;

    String name;

    DeviceTypes(String code,String name){
        this.code = code;
        this.name = name;
    }

    public static String parseName(String code) {
        for (DeviceTypes types : DeviceTypes.values()) {
            if (types.code.equals(code)) {
                return types.name;
            }
        }
        return "";
    }

}
