package com.kashuo.kcp.utils;

import com.kashuo.kcp.plus.dlt645_pack;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by dell-pc on 2018/4/14.
 */
public class AmmeterUtils {

    public void analysis(String command,char buff[]) throws Exception{
        analysis(command,buff,null);
    }

    public void analysis(String command,char buff[],Map<String,String> addressDevice) throws Exception {
        String[] commands=command.trim().split(" ");
        List<String> list =new ArrayList<String>();
        for (int i = 0; i <commands.length ; i++) {
            if (!commands[i].equalsIgnoreCase("FE")){
                list.add(commands[i]);
            }
        }
        //解析报文格式
        String[] newCommands=list.toArray(new String[list.size()]);
        /*for (int i = 0; i < newCommands.length; i++) {
            //System.out.println(Integer.parseInt(newCommands[newCommands.length-1]));
            System.out.print(newCommands[i]+" ");
        }*/

        if (newCommands.length<16||newCommands.length>22||Integer.parseInt(newCommands[0])!=68||Integer.parseInt(newCommands[newCommands.length-1])!=16){
            System.err.print("非法帧，无法解析！");
            return;
        }else {

            System.out.println("您的输入：" + command);
            System.out.println("原始地址：" + list);
            System.out.println("帧起始符：" + newCommands[0]);
            if(addressDevice != null) {
                addressDevice.put("address", newCommands[6] + newCommands[5] + newCommands[4] + newCommands[3] + newCommands[2] + newCommands[1]);
                System.out.println("电表地址："+addressDevice);
            }
            System.out.println("电表地址：" + Byte.parseByte(newCommands[6]) +""+ Byte.parseByte(newCommands[5])  +""+  Byte.parseByte(newCommands[4])  +""+  Byte.parseByte(newCommands[3])  +""+  Byte.parseByte(newCommands[2])  +""+  Byte.parseByte(newCommands[1]));
            System.out.println("控制域：" + newCommands[8]);
            System.out.println("数据域长度：" + newCommands[9]);
            System.out.println("校验码：" + newCommands[newCommands.length - 2]);
            System.out.println("停止位：" + newCommands[newCommands.length - 1]);

            //int DTID=newCommands[newCommands.length - 2 - newCommands[9]];
            //解析数据标识
            List<String> list2 = new ArrayList<String>();
            for (int i = 0; i < 4; i++) {
                list2.add(Integer.toHexString(Integer.parseInt(newCommands[newCommands.length - 3 - i-(Integer.parseInt(newCommands[9],16)-4)], 16) - 51));
            }
            String[] DTID = list2.toArray(new String[list2.size()]);
            StringBuffer sbr = new StringBuffer();
            for (int i = 0; i < DTID.length; i++) {
                if (DTID[i].length() == 1) {
                    DTID[i] = String.format("%02d", Integer.parseInt(DTID[i]));
                }else if (DTID[i].length() == 8){
                    DTID[i] = "FF";
                }
                sbr.append(DTID[i]);
            }

            //InputStream is=this.getClass().getClassLoader().getResourceAsStream("resource/config.properties");

            System.out.println(sbr.toString());
//            System.out.println("数据项名称：" + properties.getProperty(sbr.toString()));

            //解析返回数据
            if (newCommands.length>16){
                int DTID0=Integer.parseInt(DTID[0]);
                int DTID1=Integer.parseInt(DTID[1]);
                List<String> list3=new ArrayList();
                for (int i = 0; i < Integer.parseInt(newCommands[9],16)-4; i++) {
                    list3.add(newCommands[newCommands.length - 3 - i]);
                }

                String[] data = list3.toArray(new String[list3.size()]);
                //System.out.println((this.DataFormat(data)).toString());
                long num = Long.parseLong((this.DataFormat(data)).toString());
                //System.out.println((this.DataFormat(data)).toString());
                BigDecimal bigDecimal = new BigDecimal(num);
//                if(sbr.toString().equals("04000404")){
//                    //额定电压	04 00 04 04 //FEFEFEFE6811111111111168910A37373337333389636565C516
//                    trans_d07_data_XX_6(num,buff);
//                }else if(sbr.toString().equals("00010000")){
//                    //正向有功总电能 00 01 00 00 //FEFEFEFE68111111111111689108333334339C353333D316
//                    trans_d07_data_XXXXXX_XX(data_pack,ret_data);
//                }else if(sbr.toString().equals("02800007")){
//                    //表内温度	02 80 00 07 //FEFEFEFE681111111111116891063A33B3356C35C316
//                    trans_d07_data_X_XXX(data_pack,ret_data);
//                }else if(sbr.toString().equals("02010100")){
//                    //A相电压	02 01 01 00 //fefefefe681111111111116891063334343536572a16
//                    trans_d07_data_XXX_X(data_pack,ret_data);
//                }else if(sbr.toString().equals("02010200")){
//                    //B相电压	02 01 02 00 //
//                    trans_d07_data_XXX_X(data_pack,ret_data);
//                }else if(sbr.toString().equals("02010300")){
//                    //C相电压	02 01 03 00 //
//                    trans_d07_data_XXX_X(data_pack,ret_data);
//                }else if(sbr.toString().equals("020210100")){
//                    //A相电流	02 01 01 00 // fefefefe68111111111111689107333435353333333816
//              //      trans_d07_data_XXX_XXX(data_pack,ret_data);
//                }else if(sbr.toString().equals("02020200")){
//                    //B相电流	02 01 02 00 //
//            //        trans_d07_data_XXX_XXX(data_pack,ret_data);
//                }else if(sbr.toString().equals("02020300")){
//                    //C相电流	02 01 03 00 //
//              //      trans_d07_data_XXX_XXX(data_pack,ret_data);
//                }else if(sbr.toString().equals("11111111")){
//                    //地址: 11 11 11 11 //fefefefe681111111111116893064444444444446716
//                 //   sprintf(ret_data,"%s", address);
//                }
                if (DTID0==2&&DTID1==1&&!String.valueOf(DTID[2]).equals("FF")){ //电压0.1v
                    //new BigDecimal()
                    //            System.out.println(properties.getProperty(sbr.toString())+"："+bigDecimal.multiply(new BigDecimal("0.1"))+"v");
                    System.out.println("1："+bigDecimal.multiply(new BigDecimal("0.1"))+"v");
                    String tmp = bigDecimal.multiply(new BigDecimal("0.1")).toString();
                    tmp.getChars(0,tmp.length(),buff,0);
                    System.out.println("1："+buff);
                }else if (DTID0==2&&DTID1==2){ //电流0.001A
//                    System.out.println(properties.getProperty(sbr.toString())+"："+bigDecimal.multiply(new BigDecimal("0.001"))+"A");
                    System.out.println("2："+bigDecimal.multiply(new BigDecimal("0.001"))+"A");
                    String tmp = bigDecimal.multiply(new BigDecimal("0.001")).toString();
                    tmp.getChars(0,tmp.length(),buff,0);
                    System.out.println("2："+buff);

                }else if ((DTID0==2&&DTID1==3)||(DTID0==2&&DTID1==4)||(DTID0==2&&DTID1==5)){ //有无功功率0.0001
//                    System.out.println(properties.getProperty(sbr.toString())+"："+bigDecimal.multiply(new BigDecimal("0.0001")));
                    System.out.println("3："+bigDecimal.multiply(new BigDecimal("0.0001")));

                }else if (DTID0==2&&DTID1==6){ //功率因数0.001
//                    System.out.println(properties.getProperty(sbr.toString())+"："+bigDecimal.multiply(new BigDecimal("0.001")));
                    System.out.println("4："+bigDecimal.multiply(new BigDecimal("0.001")));
                }else if ((DTID0==0&&DTID1==0)||(DTID0==0&&DTID1==1)||(DTID0==0&&DTID1==2)||(DTID0==0&&DTID1==3)||(DTID0==0&&DTID1==4)||(DTID0==0&&DTID1==5)||(DTID0==0&&DTID1==6)||(DTID0==0&&DTID1==7)||(DTID0==0&&DTID1==8)){ //有无功总电能、四象限无功总电能0.01
//                    System.out.println(properties.getProperty(sbr.toString())+"："+bigDecimal.multiply(new BigDecimal("0.01")));
                    System.out.println("5："+bigDecimal.multiply(new BigDecimal("0.01")));
                    String tmp = bigDecimal.multiply(new BigDecimal("0.01")).toString();
                    tmp.getChars(0,tmp.length(),buff,0);
                    System.out.println("5："+buff);
                }else if (DTID0==2&&DTID1==1&&String.valueOf(DTID[2]).equals("FF")){ //电压数据块
                    System.out.println(String.valueOf(num));
                    System.out.println(String.valueOf(num).substring(0,4));
                    System.out.println(String.valueOf(num).substring(4,8));
                    System.out.println(String.valueOf(num).substring(8));

                    System.out.println("C相电压"+new BigDecimal(String.valueOf(num).substring(0,4)).multiply(new BigDecimal("0.1")));
                    System.out.println("B相电压"+new BigDecimal(String.valueOf(num).substring(4,8)).multiply(new BigDecimal("0.1")));
                    System.out.println("A相电压"+new BigDecimal(String.valueOf(num).substring(8)).multiply(new BigDecimal("0.1")));

                }else if(sbr.toString().equals("04000404")){
//                    System.out.println(properties.getProperty(sbr.toString())+"："+num);
                    String temp = String.valueOf(num);
                    char xxxx[] = new char[temp.length()];
                    temp.getChars(0,temp.length(),xxxx,0);
                    char tmp[] = new char[2];
                    int lens = 0;
                    for(int i = 0; i < temp.length()-1; i+=2)
                    {
                        tmp[0] =xxxx[i+1];
                        tmp[1] =xxxx[i];
                        //   strncpy(tmp,&outStr[i],sizeof(tmp));
                        buff[lens++] = charTo16(tmp);
                    }
                    //      System.out.println(buff);
                    //           System.out.println("："+new BigDecimal(String.valueOf(num)).multiply(new BigDecimal("0.1"))+"v");
                }else if(sbr.toString().equals("11111111")){
                    //    Byte.parseByte(newCommands[6]) + Byte.parseByte(newCommands[5]) + Byte.parseByte(newCommands[4]) + Byte.parseByte(newCommands[3]) + Byte.parseByte(newCommands[2]) + Byte.parseByte(newCommands[1])
                    String tmp = newCommands[6]+newCommands[5]+newCommands[4]+newCommands[3]+newCommands[2]+newCommands[1];
                    tmp.getChars(0,tmp.length(),buff,0);
                    System.out.println("7："+tmp);
                }else if(sbr.toString().equals("02800007")){
                    String tmp = String.valueOf(num/10)+"."+String.valueOf(num%10);
                    tmp.getChars(0,tmp.length(),buff,0);
                    System.out.println("9："+num);
                }else {
                    System.out.println("8："+num);
                }

            }

        }
    }
    private void trans_d07_data_XX_6(Long num,char buf[]){
        String temp = String.valueOf(num);
        char xxxx[] = new char[temp.length()];
        temp.getChars(0,temp.length(),xxxx,0);
        char tmp[] = new char[2];
        int lens = 0;
        for(int i = 0; i < temp.length()-1; i+=2)
        {
            tmp[0] =xxxx[i+1];
            tmp[1] =xxxx[i];
            //   strncpy(tmp,&outStr[i],sizeof(tmp));
            buf[lens++] = charTo16(tmp);
        }
    }
    private char charTo16(char src[]){
        char des=0;
        if("0".equals(String.valueOf(src[0]))){
            des = 0x00;
        }else if("1".equals(String.valueOf(src[0]))){
            des = 0x01;
        }else if("2".equals(String.valueOf(src[0]))){
            des = 0x02;
        }else if("3".equals(String.valueOf(src[0]))){
            des = 0x03;
        }else if("4".equals(String.valueOf(src[0]))){
            des = 0x04;
        }else if("5".equals(String.valueOf(src[0]))){
            des = 0x05;
        }else if("6".equals(String.valueOf(src[0]))){
            des = 0x06;
        }else if("7".equals(String.valueOf(src[0]))){
            des = 0x07;
        }else if("8".equals(String.valueOf(src[0]))){
            des = 0x08;
        }else if("9".equals(String.valueOf(src[0]))){
            des = 0x09;
        }else if("A".equals(String.valueOf(src[0]))){
            des = 0x0A;
        }else if("B".equals(String.valueOf(src[0]))){
            des = 0x0B;
        }else if("C".equals(String.valueOf(src[0]))){
            des = 0x0C;
        }else if("D".equals(String.valueOf(src[0]))){
            des = 0x0D;
        }else if("E".equals(String.valueOf(src[0]))){
            des = 0x0E;
        }else if("F".equals(String.valueOf(src[0]))){
            des = 0x0F;
        }

        if("0".equals(String.valueOf(src[1]))){
            des =  (char) (des | 0x00);
        }else if("1".equals(String.valueOf(src[1]))){
            des =  (char) (des | 0x10);
        }else if("2".equals(String.valueOf(src[1]))){
            des =  (char) (des | 0x20);
        }else if("3".equals(String.valueOf(src[1]))){
            des =  (char) (des | 0x30);
        }else if("4".equals(String.valueOf(src[1]))){
            des =  (char) (des | 0x40);
        }else if("5".equals(String.valueOf(src[1]))){
            des =  (char) (des | 0x50);
        }else if("6".equals(String.valueOf(src[1]))){
            des =  (char) (des | 0x60);
        }else if("7".equals(String.valueOf(src[1]))){
            des =  (char) (des | 0x70);
        }else if("8".equals(String.valueOf(src[1]))){
            des =  (char) (des | 0x80);
        }else if("9".equals(String.valueOf(src[1]))){
            des =  (char) (des | 0x90);
        }else if("A".equals(String.valueOf(src[1]))){
            des =  (char) (des | 0xA0);
        }else if("B".equals(String.valueOf(src[1]))){
            des =  (char) (des | 0xB0);
        }else if("C".equals(String.valueOf(src[1]))){
            des =  (char) (des | 0xC0);
        }else if("D".equals(String.valueOf(src[1]))){
            des =  (char) (des | 0xD0);
        }else if("E".equals(String.valueOf(src[1]))){
            des =  (char) (des | 0xE0);
        }else if("F".equals(String.valueOf(src[1]))){
            des =  (char) (des | 0xF0);
        }
        return des;
    }
    public StringBuffer DataFormat(String data[]){
        StringBuffer sbr=new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            String data1=String.valueOf(Integer.parseInt(data[i].substring(0,1),16)-3);
            String data2=String.valueOf(Integer.parseInt(data[i].substring(1),16)-3);
            sbr.append(data1);
            sbr.append(data2);
        }
        return sbr;
    }

    public static String unpackDeviceData(String responseData){
        char buf[] = new char[64];
        StringBuilder sb = new StringBuilder();
        for (int i = 0;i< responseData.length();i++){
            sb.append(responseData.charAt(i));
            if(i%2==1){
                sb.append(" ");
            }
        }
        System.out.println(sb.toString().length()+"-------------");
        return sb.toString().trim();
    }

    public static String commandAddress(String address){
        StringBuilder sb = new StringBuilder();
        sb.append(address.substring(10,12)).append(address.substring(8,10)).append(
                address.substring(6,8)).append(address.substring(4,6)).append(address.substring(2,4)).append(address.substring(0,2));
        return sb.toString();
    }

    public static String getPackageCommand(String address,String commandId){
        byte [] frame = new byte[255];
        dlt645_pack pkt = new dlt645_pack();
        pkt.GetRuleID(commandId);
        pkt.GetContrlCode((byte)0);
        pkt.GetDataLen((byte)4);
        pkt.GetRealLen((byte)4);
        pkt.GetAddrStr(commandAddress(address));
        dlt645_pack.pack_any_frame_by_data(pkt, frame);
        StringBuilder sb = new StringBuilder();
        for(int i = 0;i < frame.length; i++) {
            String data = Integer.toHexString(frame[i]);
            if(!"0".equals(data)){
                if(data.length() ==1){
                    sb.append("0"+data);
                }else {
                    if(data.startsWith("ffffff")){
                        sb.append(data.substring(6,8));
                    }else {
                        sb.append(data);
                    }
                }
            }else if(i<16){
                sb.append("00");
            }
        }
        System.out.println("FEFEFEFE"+sb.toString());
        return "FEFEFEFE"+sb.toString();
    }

    public static String unPackageAnalysis(String data) throws Exception {
        char buf[] = new char[64];
         new AmmeterUtils().analysis(unpackDeviceData(data),buf);
        StringBuilder sb =new StringBuilder();
        for (char c : buf) {
            if('\u0000' != c) {
                sb.append(c);
            }
        }
        return String.valueOf(sb.toString());
    }


    public static String unPackageAnalysis(String data,Map address) throws Exception {
        char buf[] = new char[64];
        new AmmeterUtils().analysis(unpackDeviceData(data),buf,address);
        StringBuilder sb =new StringBuilder();
        for (char c : buf) {
            if('\u0000' != c) {
                sb.append(c);
            }
        }
        return String.valueOf(sb.toString());
    }

    public static Map unPackageAnalysisForAddress(String data) throws Exception {
        char buf[] = new char[64];
        Map<String,String> address = new HashMap<>();
        new AmmeterUtils().analysis(unpackDeviceData(data),buf,address);
        return address;
    }

    public static void main(String[] args) {
        try {
            char buf[] = new char[64];
//            new AmmeterUtils().analysis("FE FE FE FE 68 11 11 11 11 11 11 68 91 0A 37 37 33 37 33 33 89 63 65 65 C5 16",buf);
//
//              new AmmeterUtils().analysis("FE FE FE FE 68 11 11 11 11 11 11 68 91 06 33 34 34 35 85 56 78 16",buf);
//              String s ="FEFEFEFE680801000000006891063334343575550A16";//A箱电压
            String s ="FEFEFEFE681111111111116893064444444444446716";//电表地址
//            String s ="FEFEFEFE6811111111111168D101353D16";
//              String s="FEFEFEFE68111111111111681104333334331816";
//            String s ="FEFEFEFE6806010000000068110433333433ffffffb916";
////            new AmmeterUtils().analysis(unpackDeviceData(s),buf);
////            String address =String.valueOf(AmmeterUtils.unPackageAnalysisForAddress(s).get("address"));
////            System.out.println("返回结果++"+address);
            System.out.println("返回结果++"+AmmeterUtils.unPackageAnalysis(s));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("解析出错！");
        }

//        System.out.println(getPackageCommand("000000000106","00010000")+"==================");
//        System.out.println(getPackageCommand("111111111111","00010000")+"==================");
//        System.out.println(Integer.toHexString(-10));
//        System.out.println(Integer.toHexString(16));
    }

}
