package com.chatroom.util;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by asus on 2017/10/8.
 */
public class Response {
    public InetAddress ip = null;
    public String data;
    /**
     * 接收函数 ：开启所传参数端口的接收端
     * @param port
     * @throws IOException
     */
    public void receive(int port) throws IOException {
        System.out.println("-->开启接收端口："+port);
        DatagramSocket ds = new DatagramSocket(port);
        byte[] buf = new byte[1024];
        DatagramPacket dp = new DatagramPacket(buf, buf.length);
        ds.receive(dp);
        ip = dp.getAddress();
        data = new String(dp.getData(),0,dp.getLength(),"UTF-8");
        ds.close();
        System.out.println("-->ip:"+ip+"\n-->内容:"+data);
        System.out.println("-->端口"+port+":  接收成功");


    }


}