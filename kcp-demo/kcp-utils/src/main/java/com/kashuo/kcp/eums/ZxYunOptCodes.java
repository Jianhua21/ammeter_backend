package com.kashuo.kcp.eums;

/**
 * Created by dell-pc on 2019/3/5.
 */
public enum ZxYunOptCodes {

    OPT_01("1","复位"),
    OPT_02("2","消音"),
    OPT_03("3","手动报警"),
    OPT_04("4","屏蔽"),
    OPT_05("5","屏蔽解除"),
    OPT_06("6","隔离"),
    OPT_07("7","隔离解除"),
    OPT_08("8","测试"),
    OPT_09("9","巡检"),
    OPT_10("10","确认"),
    OPT_11("11","自检"),
    OPT_12("12","启动"),
    OPT_13("13","延时启动")
    ;
    private String code;

    private String desc;

    ZxYunOptCodes(String code,String desc){
        this.code=code;
        this.desc=desc;
    }
    public static String parseDesc(String code) {
        for (ZxYunOptCodes types : ZxYunOptCodes.values()) {
            if (types.code.equals(code)) {
                return types.desc;
            }
        }
        return "";
    }
}
