package com.chatroom.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by asus on 2017/11/1.
 */

public class MyToast {
    public MyToast(Context context,String s){
        Toast.makeText(context,s,Toast.LENGTH_SHORT).show();

    }
}
