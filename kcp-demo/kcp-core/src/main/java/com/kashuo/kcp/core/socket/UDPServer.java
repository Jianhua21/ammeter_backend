package com.kashuo.kcp.core.socket;

/**
 * Created by dell-pc on 2017/9/3.
 */

import com.kashuo.kcp.core.AmmeterService;
import com.kashuo.kcp.domain.AmmeterDevice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Component
public class UDPServer {
    public static final int SOCKET_PORT  =5688;//监听的端口号
    private final static Logger logger = LoggerFactory.getLogger(UDPServer.class);
    private static final Integer maxThreadNumber =20;


    @Autowired
    private AmmeterService ammeterService;

//    @Autowired
//    private AmmeterWorkingInfoMapper ammeterWorkingInfoMapper;
//    @Autowired
//    private AmmeterDeviceMapper ammeterDeviceMapper;

//    static {
//        System.out.println("Socket 服务器启动...\n"+SOCKET_PORT);
//        new Thread(()->
//        {
//                Server server = new Server();
//                server.init(ammeterService);
//
//        }).start();
//    }
    @Autowired
    private void startServer(){
            System.out.println("Socket 服务器启动...\n"+SOCKET_PORT);
            new Thread(()->
            {
                UDPServer server = new UDPServer();
                server.init(ammeterService);

            }).start();
    }

//    public static void main(String[] args) {
//        System.out.println("服务器启动...\n");
//        Server server = new Server();
//        server.init(new AmmeterService());
//    }

    public void init(AmmeterService ammeterService) {
        try {
            DatagramSocket socket = new DatagramSocket(SOCKET_PORT);
            byte[] infoBytes = new byte[1024];
            System.out.println("服务器端启动了·········"+maxThreadNumber);
            ThreadPoolExecutor executor = (ThreadPoolExecutor)Executors.newFixedThreadPool(maxThreadNumber);
            while (true) {
                DatagramPacket packet = new DatagramPacket(infoBytes, infoBytes.length);
                socket.receive(packet);
                UdpServerThread thread = new UdpServerThread(packet, socket, infoBytes,ammeterService);
//                thread.start();
                executor.submit(thread);
                System.out.println(thread);
                System.out.println("访问的客户端数量：" + (executor.getActiveCount()));
            }
        } catch (Exception e) {
            System.out.println("服务器异常: " + e.getMessage());
        }
    }

    private class UdpServerThread extends Thread {
        private AmmeterService service;
        private String info = null;
        private DatagramSocket socket = null;
        private int port = 0;
        private InetAddress address = null;

        public UdpServerThread(DatagramPacket packet2, DatagramSocket socket2, byte[] infoBytes2,AmmeterService ammeterService) {
            socket = socket2;
            info = new String(infoBytes2, 0, packet2.getLength());
            port = packet2.getPort();
            address = packet2.getAddress();
            service = ammeterService;
        }

        @Override
        public void run() {

            // 处理客户端数据
            AmmeterDevice device = new AmmeterDevice();
            String response;
            System.out.println("客户端说：" + info);
            if(info != null && "test".equals(info.toLowerCase())){
                response = "OK";
            }else {
                device.setInputMsg(info);
                device.setAddress(address.getHostAddress());
                //更新客户端的推送信息进数据库
                response = service.updateAmmeterDevice(device);
            }
//            super.run();
            System.out.println("操作结束");
            if("".equals(response)||"-1".equals(response)){
                try{
                    if(socket != null){
                        socket.disconnect();
                    }
                }catch (Exception e){
                    System.out.println(socket.getInetAddress()+"断开失败!");
                }
            }else {
                byte[] infoBytes = response.getBytes();
                DatagramPacket packet = new DatagramPacket(infoBytes, infoBytes.length, address, port);
                try {
                    socket.send(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if(socket != null) {
                        socket.disconnect();
                    }
                }
            }
        }
    }
}
