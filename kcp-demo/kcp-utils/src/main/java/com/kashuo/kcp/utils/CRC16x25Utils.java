package com.kashuo.kcp.utils;

import java.io.StringBufferInputStream;

/**
 * Created by dell-pc on 2018/4/29.
 */
public class CRC16x25Utils {




    public static String CRC16_Check(byte data[], int length)
    {
        int Reg_CRC=0xffff;
        int temp;
        int i,j;
        for( i = 0; i<length; i ++)
        {
            temp = data[i];
            if(temp < 0) temp += 256;
            temp &= 0xff;
            Reg_CRC^= temp;
            for (j = 0; j<8; j++)
            {
                if ((Reg_CRC & 0x0001) == 0x0001)
                    Reg_CRC=(Reg_CRC>>1)^0xA001;
                else
                    Reg_CRC >>=1;
            }
        }
        String result = Integer.toHexString((Reg_CRC&0xffff));
        if(result.length() ==1) {
            result ="000"+result;
        }else if(result.length() ==2){
            result="00"+result;
        }else if(result.length() ==3){
            result="0"+result;
        }

        return result;
    }
    public static void main(String str[])
    {
//        String s ="89010320FEFEFEFE68AAAAAAAAAAAA68110434373337B616000862";
//        String s ="89010320FEFEFEFE681111111111116811043537333721160092cf";
        String s ="89010320FEFEFEFE68111111111111681104333434351b1644FEFEFEFE681111111111116891063334343567565A16";
        System.out.println("CRC16-CCITT = " + CRC16x25Utils.CRC16_Check(s.getBytes(),s.length()));
    }
}
