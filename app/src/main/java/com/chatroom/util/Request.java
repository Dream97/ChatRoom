package com.chatroom.util;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
/**
 * Created by asus on 2017/10/8.
 */

public class Request {
    /**
     * 发送函数  ：参数为 所发命令command，目的ip地址，使用端口号port。
     * @param command
     * @param ip
     * @param port
     * @throws UnsupportedEncodingException
     * @throws SocketException
     * @throws IOException
     */
    public void send(String command, InetAddress ip,int port) throws UnsupportedEncodingException, SocketException {

        DatagramPacket dp = new DatagramPacket(command.getBytes("UTF-8"),
                command.getBytes("UTF-8").length, ip, port);
        System.out.println(ip);

        DatagramSocket ds = new DatagramSocket();
        Thread t = new Thread (new RequestRunnable(dp,ds));
        t.start();
        System.out.println("-->端口 "+port+" 发送命令:"+command);
    }

}
class RequestRunnable implements Runnable{
    DatagramPacket dp;
    DatagramSocket ds;
    public RequestRunnable(DatagramPacket dp,DatagramSocket ds){
        this.dp = dp;
        this.ds = ds;
    }
    @Override
    public void run() {
        try {
            ds.send(dp);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ds.close();

    }

}