package com.kashuo.kcp.utils;

import java.lang.reflect.Method;

/**
 * Created by dell-pc on 2018/4/10.
 */
public class CompareUtils {


    public static boolean compareParams(String param1,String param2,String operator){
        if(param1 == null||param2 ==null ||operator == null
                || "".equals(param1.trim())|| "".equals(param2.trim()) || "".equals(operator.trim())){
            return false;
        }
        try{
            Double.parseDouble(param1);
        }catch (NumberFormatException e){
            param1 ="0";
        }
        if(">".equals(operator)){
             return Double.parseDouble(param1)>Double.parseDouble(param2) ;
        }else if(">=".equals(operator)){
            return Double.parseDouble(param1)>=Double.parseDouble(param2) ;
        }else if("<".equals(operator)){
            return Double.parseDouble(param1)<Double.parseDouble(param2) ;
        }else if("<=".equals(operator)){
            return Double.parseDouble(param1)<=Double.parseDouble(param2) ;
        }else if("=".equals(operator)){
            return Double.parseDouble(param1)==Double.parseDouble(param2) ;
        }
        return false;
    }


}
