package com.kashuo.kcp.core.socket;

/**
 * Created by dell-pc on 2017/9/3.
 */

import java.io.*;
import java.net.Socket;

public class Client {
    public static final String IP_ADDR = "localhost";//服务器地址
    public static final int SOCKET_PORT = 5683;//服务器端口号

    public static final String[]  StringArray ={"0x28001","0x28002","0x28003","0x28004","0x18005","0x180006","0x180007","0x180008","0x180009","OK"};

    public static void main(String[] args) {
        System.out.println("客户端启动...");
        System.out.println("当接收到服务器端字符为 \"OK\" 的时候, 客户端将终止\n");
        int attemptNumber = 1; //尝试重新连接的次数
        int number = 0;
        while (true) {
            Socket socket = null;
            try {
                //创建一个流套接字并将其连接到指定主机上的指定端口号
                socket = new Socket(IP_ADDR, SOCKET_PORT);
                System.out.println("开始会话 "+number);
                //读取服务器端数据
                DataInputStream input = new DataInputStream(socket.getInputStream());
                //向服务器端发送数据
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                System.out.print("请输入: \t");
                String str = new BufferedReader(new InputStreamReader(System.in)).readLine();
                if(number >10){
                    break;
                }
//                String str = StringArray[number];
                number ++;
                out.writeUTF(str);
                attemptNumber = 1;
                String ret = input.readUTF();
                System.out.println("服务器端返回过来的是: " + ret);

                // 如接收到 "OK" 则断开连接
                if ("OK".equals(ret)) {
                    System.out.println("客户端将关闭连接");
                    Thread.sleep(500);
                    break;
                }

                out.close();
                input.close();
            } catch (Exception e) {
                if(attemptNumber > 60){
                    System.out.println("尝试连接 30秒,无法连接 ，主动关闭连接!");
                    break;
                }
                System.out.println("客户端异常:" + e.getMessage());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                attemptNumber++;
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        socket = null;
                        System.out.println("客户端 finally 异常:" + e.getMessage());
                    }
                }
            }
        }
    }
}


