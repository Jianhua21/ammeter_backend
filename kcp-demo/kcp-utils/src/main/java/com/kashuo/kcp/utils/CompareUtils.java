package com.kashuo.kcp.utils;

import java.lang.reflect.Method;

/**
 * Created by dell-pc on 2018/4/10.
 */
public class CompareUtils {


    public static boolean compareParams(String param1,String param2,String operator){
        if(param1 == null||param2 ==null){
            return false;
        }
        try{
            Integer.parseInt(param1);
        }catch (NumberFormatException e){
            param1 ="0";
        }
        if(">".equals(operator)){
             return Integer.parseInt(param1)>Integer.parseInt(param2) ;
        }else if(">=".equals(operator)){
            return Integer.parseInt(param1)>=Integer.parseInt(param2) ;
        }else if("<".equals(operator)){
            return Integer.parseInt(param1)<Integer.parseInt(param2) ;
        }else if("<=".equals(operator)){
            return Integer.parseInt(param1)<=Integer.parseInt(param2) ;
        }else if("=".equals(operator)){
            return Integer.parseInt(param1)==Integer.parseInt(param2) ;
        }
        return false;
    }


}
