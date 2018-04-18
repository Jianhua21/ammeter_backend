package com.kashuo.kcp.core.test;

/**
 * Created by dell-pc on 2017/11/15.
 */
public class testMain {

    public static void main(String[] args)
    {
        boolean result = false;
        try {
            result = ApplicationStartupUtil.checkExternalServices();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("External services validation completed !! Result was :: "+ result);
        System.exit(0);
    }
}
