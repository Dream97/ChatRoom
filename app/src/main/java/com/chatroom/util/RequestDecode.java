package com.chatroom.util;

import java.util.ArrayList;

/**
 * 制定信息格式
 * Created by asus on 2017/10/8.
 */

public class RequestDecode {
    public String content ;
    public ArrayList<String> response = new ArrayList<String>();
    public static final char ENDCOMMAND = '!';
    public static final char NODECOMMAND = ';';
    public void decode(String data) throws Exception{
        int beginIndex = 0;
        int endIndex = 0;
        while(endIndex <= data.length() && data.charAt(endIndex) != ENDCOMMAND){
            if(data.charAt(endIndex) == NODECOMMAND){
                content = data.substring(beginIndex, endIndex);
                System.out.println("content="+content);
                response.add(content);
                beginIndex = endIndex+1;
            }
            endIndex++;
        }
        System.out.println("response = "+response);
    }
}
