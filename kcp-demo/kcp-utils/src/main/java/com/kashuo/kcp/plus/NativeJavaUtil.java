package com.kashuo.kcp.plus;

/**
 * Created by dell-pc on 2018/4/15.
 */
public class NativeJavaUtil {


    static{
        System.loadLibrary("NativeJavaUtil");
    }

    public native void sayHello();
    public native String decodeCommand(String command);

    public native String encodeCommand(String deviceNumber,String realCommand);

    public static void main(String[] args){
        NativeJavaUtil test = new NativeJavaUtil();
        System.out.println("Java 平台返回数据:"+
                test.decodeCommand("FEFEFEFE6811111111111168910A37373337333389636565C516"));
        System.out.println("Java 封包 平台返回数据:"+
                test.encodeCommand("111111111111","02800007"));
    }
}
