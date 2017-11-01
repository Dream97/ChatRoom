package com.chatroom.presenter;

import com.chatroom.util.Constant;
import com.chatroom.util.Request;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Map;

/**
 * Created by asus on 2017/10/31.
 */

public class SendMsg implements Runnable {
    Request myRequest = new Request();
    Map<String,String> map ;

    public SendMsg(Map<String,String> map) {
        this.map = map;
    }
    @Override
    public void run() {
        try {
            myRequest.send(Constant.MESSAGE+";"+map.get("myName")+";"+map.get("mySex")+";"+map.get("myMsg")+";!",
                    InetAddress.getByName("255.255.255.255"), 8080);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}