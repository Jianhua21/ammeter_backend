package com.kashuo.kcp.core.socket;


import com.kashuo.kcp.core.SysDictionaryService;
import com.kashuo.kcp.dao.AmmeterWorkingInfoMapper;
import com.kashuo.kcp.domain.AmmeterDevice;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by dell-pc on 2017/9/7.
 */
public class ClientProcess {

    public class HandlerStatusThread implements Runnable {

        private AmmeterDevice ammeterDevice;

        private AmmeterWorkingInfoMapper ammeterWorkingInfoMapper;


        public HandlerStatusThread(AmmeterDevice device,AmmeterWorkingInfoMapper workMapper) {
            ammeterDevice = device;
            ammeterWorkingInfoMapper = workMapper;
            new Thread(this).start();
        }
        @Override
        public void run() {
            System.out.println("合闸关闸 客户端启动...");
            System.out.println("当接收到服务器端字符为 \"OK\" 的时候, 客户端将终止\n");
            int attemptNumber = 1; //尝试重新连接的次数
            while (true) {
                Socket socket = null;
                try {
                    //创建一个流套接字并将其连接到指定主机上的指定端口号
                    socket = new Socket(ammeterDevice.getAddress(), 20177);
                    System.out.println("**********************开闸合闸 开始会话**************** ");
                    //读取服务器端数据
                    DataInputStream input = new DataInputStream(socket.getInputStream());
                    //向服务器端发送数据
                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                    //拉闸 合闸 数据发送
                    String str = SysDictionaryService.getStaticValue(29);
                    String condition = "ab8967454e";
                    if(ammeterDevice.getSendStatus() == 1) {
                        condition ="ab8967454d";
                    }
                    out.writeUTF(str+condition);

                    String ret = input.readUTF();
                    System.out.println("服务器端返回过来的是: " + ret);

                    ammeterWorkingInfoMapper.updateByAmmeterId(ammeterDevice.getId(),ammeterDevice.getSendStatus());

                    out.close();
                    input.close();
                    attemptNumber = 1;
                    break;

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
                    closeClose(socket);
                }
            }
        }
        }

        public  static void closeClose(Socket socket){
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
