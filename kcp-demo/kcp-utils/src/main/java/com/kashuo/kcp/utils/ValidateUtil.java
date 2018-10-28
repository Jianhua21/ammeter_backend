package com.kashuo.kcp.utils;

import java.util.regex.Pattern;

/**
 * Created by dell-pc on 2017/7/20.
 */
public class ValidateUtil {

    /***
     * 验证邮箱是否是合法的邮箱，合法的邮箱返回  true,非法的邮箱返回 false
     * @param email
     * @return
     */
    public static boolean validateEmail(String email){
      String reg = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern regex = Pattern.compile(reg);
        return regex.matcher(email).matches();
    }

    public static boolean validatePhoneNumber(String phoneNumber){
        String check = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
        Pattern regex = Pattern.compile(check);
        return regex.matcher(phoneNumber).matches();
    }

    public static void main(String[] args) {
        System.out.print(validatePhoneNumber("18888888888"));
    }

}
