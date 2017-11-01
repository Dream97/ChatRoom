package com.chatroom.presenter;

import android.os.Handler;
import android.util.Log;

import com.chatroom.activity.MyView;
import com.chatroom.util.Constant;
import com.chatroom.util.RequestDecode;
import com.chatroom.util.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by asus on 2017/10/31.
 * 监听端口类
 */

public class Listener implements Runnable{

    private String myName;
    private List<Map<String, String>> data = new ArrayList<>();
    private Map<String,String> map,map1 ;
    private Response myResponse;
    private RequestDecode myDecode;
    private int port;//监听端口
    private boolean flag =true;//循环标志
    private Handler mHandler;
    MyView myView;
    public Listener(MyView myView,int port, Handler handler, String name){
        this.myView = myView;
        this.port = port;
        this.mHandler = handler;
        this.myName = name;
    }
    /**
     * 循环接收port端口的请求
     */
    @Override
    public void run() {
        while(flag){
            initData();
            try {
                myResponse.receive(port);
                myDecode.decode(myResponse.data);
                responseRun(myDecode.response);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void responseRun(ArrayList<String> response) throws IOException, InterruptedException {
        Log.d("我得名字是", "responseRun: "+myName);
        if(response.get(0).equals(Constant.MESSAGE)&&!response.get(1).equals(myName)){
            map1 = new HashMap<>();
            map1.put("otherName", response.get(1));
            map1.put("otherSex", response.get(2));
            map1.put("otherMsg", response.get(3));
            data.add(map1);
            myView.showData(data);
        }
    }

    /**
     * 初始化
     */
    private void initData() {

        myDecode = new RequestDecode();
        myResponse = new Response();
    }

}