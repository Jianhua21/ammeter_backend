package com.kashuo.kcp.eums;

/**
 * Created by dell-pc on 2019/1/14.
 */
public enum PlateTypes {

    CHINA_TELNET(0,"中国电信"),CHINA_MOBILE(1,"中国移动"),
    CHINA_UNICOM(2,"中国联通"),OTHERS(3,"其他");

    private Integer code;

    private String name;

     PlateTypes(Integer code,String name){
        this.code=code;
        this.name=name;
    }

    public static String parseName(Integer code) {
        for (PlateTypes types : PlateTypes.values()) {
            if (types.code == code) {
                return types.name;
            }
        }
        return "";
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
