package com.kashuo.kcp.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collection;

public class StringUtil {

    /**
     * 验证字符串是否为空
     *
     * @param str 字符串
     * @return true 空 false 非空
     */
    public static boolean isEmpty(String str) {
        return null == str || "".equals(str);
    }

    /**
     * 验证字符串是否为空
     *
     * @param str 字符串
     * @return true 空 false 非空
     */
    public static boolean isNotEmpty(String str) {
        return null != str && !"".equals(str);
    }

    public static String nullToEmpty(String str) {
        return str == null ? "" : str;
    }

    public static String convertEncoding(String value) {
        if (!StringUtils.isEmpty(value)) {
            try {
                return new String(value.getBytes("iso-8859-1"), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String urlEncoding(String str) {
        try {
            if (!StringUtils.isEmpty(str)) {
                return URLEncoder.encode(str, "UTF-8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }


    public static String truncateStr(String str, int len) {
        if (str == null) {
            return "";
        } else if (str.length() < len) {
            return str;
        } else {
            return str.substring(0, len) + "...";
        }
    }

    /**
     * 检测是否存在空字段
     *
     * @param strs 字串参数列表
     * @return true/false
     */
    public static boolean hasEmpty(String... strs) {
        if (strs != null && strs.length != 0) {
            for (String str : strs) {
                if (StringUtils.isEmpty(str)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 不足位数左补零
     *
     * @param src    源数字
     * @param length 总长度
     * @return 返回指定长度字串
     */
    public static String lpad(int src, int length) {
        return lpad(String.valueOf(src), length);
    }

    /**
     * 不足位数左补零
     *
     * @param src    源字串
     * @param length 总长度
     * @return 返回指定长度字串
     */
    public static String lpad(String src, int length) {
        return lpad(src, length, "0");
    }

    /**
     * 不足位数左补对应字串
     *
     * @param src     源数字
     * @param length  总长度
     * @param fillstr 补位字串
     * @return 返回指定长度字串
     */
    public static String lpad(String src, int length, String fillstr) {
        while (src != null && src.length() < length) {
            src = fillstr + src;
        }
        return src;
    }

    /**
     * 不足位数右补零
     *
     * @param src    源数字
     * @param length 总长度
     * @return 返回指定长度字串
     */
    public static String rpad(int src, int length) {
        return rpad(String.valueOf(src), length);
    }

    /**
     * 不足位数右补零
     *
     * @param src    源字串
     * @param length 总长度
     * @return 返回指定长度字串
     */
    public static String rpad(String src, int length) {
        return rpad(src, length, "0");
    }

    /**
     * 不足位数右补字串
     *
     * @param src     源字串
     * @param length  总长度
     * @param fillstr 补位字串
     * @return 返回指定长度字串
     */
    public static String rpad(String src, int length, String fillstr) {
        while (src != null && src.length() < length) {
            src = src + fillstr;
        }
        return src;
    }

    /**
     * 将中文括号`（）`转为英文括号()
     */
    public static String cn2en(String src){
        if(isNotEmpty(src)){
            src = src.replaceAll("（","(");
            src = src.replaceAll("）",")");
            src = src.replaceAll("【","[");
            src = src.replaceAll("】","]");
        }
        return src;
    }

    /**
     * 将中文逗号转为英文逗号
     * @param src 源字符串
     * @return
     */
    public static String commaCn2En(String src) {
        if(isNotEmpty(src)){
            src = src.replaceAll("，",",");
        }
        return src;
    }

    /**
     * 将String集合用分隔符拼接
     * @param collection
     * @param separator 分隔符
     * @return
     */
    public static String collection2String(Collection<String> collection, char separator ) {
        if(collection == null || collection.size() == 0)
            return "";
         return StringUtils.join(collection.toArray(), separator);
    }

    /**
     * 将String集合用逗号分隔符拼接
     * @param collection
     * @return
     */
    public static String collection2String(Collection<String> collection) {
        return collection2String(collection, ',');
    }

    public static boolean isEmpty(String[] str) {
        if(str == null || str.length == 0) {
            return true;
        }
        return false;
    }

    public static String array2String(String[] str) {
        if(isEmpty(str)) {
            return "";
        }
        return StringUtils.join(str, ",");
    }

    public static void main(String[] args) {
        System.out.println(rpad("8", 6));
    }

}

