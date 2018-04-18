package com.kashuo.kcp.utils;

/**
 * Created by dell-pc on 2018/4/14.
 */
public class AmmeterUtils {


    public static String exchangeCommand(String param){
//        Integer x = Integer.parseInt(param,16);

        return "02800001";
//        return x.toString();
    }

    public static void main(String[] args) {
        System.out.println(AmmeterUtils.exchangeCommand("89"));
    }
}
