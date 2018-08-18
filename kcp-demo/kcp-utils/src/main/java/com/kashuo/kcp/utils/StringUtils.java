package com.kashuo.kcp.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dell-pc on 2017/9/4.
 */
public class StringUtils {

    /**
     * @Title:string2HexString
     * @Description:字符串转16进制字符串
     * @param strPart
     *            字符串
     * @return 16进制字符串
     * @throws
     */
    public static String string2HexString(String strPart) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < strPart.length(); i++) {
            int ch = (int) strPart.charAt(i);
            String strHex = Integer.toHexString(ch);
            hexString.append(strHex);
        }
        return hexString.toString();
    }

    public static Map<String,Object> initDailyReportMap(Integer initValue){
        Map<String,Object> dailyReportMap = new HashMap<>();
        for (int i = 1;i<=initValue;i++){
            dailyReportMap.put(String.valueOf(i),null);
        }
        return dailyReportMap;
    }

    public static Map<String,Object> initMonthReportMap(int month){
        Integer[] days = {31,28,31,30,31,30,31,31,30,31,30,31};
        Map<String,Object> monthReportMap = new HashMap<>();
        for (int i=1;i<=days[month-1];i++){
            monthReportMap.put(String.valueOf(i),null);
        }
        return monthReportMap;
    }

    /**
     * @Title:hexString2String
     * @Description:16进制字符串转字符串
     * @param src
     *            16进制字符串
     * @return 字节数组
     * @throws
     */
    public static String hexString2String(String src) {
        String temp = "";
        for (int i = 0; i < src.length() / 2; i++) {
            temp = temp
                    + (char) Integer.valueOf(src.substring(i * 2, i * 2 + 2),
                    16).byteValue();
        }
        return temp;
    }
}
