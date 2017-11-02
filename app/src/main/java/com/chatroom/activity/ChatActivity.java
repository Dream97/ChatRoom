package com.chatroom.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.chatroom.R;
import com.chatroom.presenter.Listener;
import com.chatroom.presenter.SendMsg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * Created by asus on 2017/10/8.
 */

public class ChatActivity extends Activity implements OnClickListener,MyView{

    private TextView myTitle;
    private ListView myListView ;
    private String str,sex;
    private EditText sendMsg;
    private Button sendBtn;
    private MyAdapter adapter;
    List<Map<String, String>> data ;
    Map<String,String> map,map1 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //设置无标题
        setContentView(R.layout.activity_chat);

        //SysApplication.getInstance().addActivity(this);//加入到列表中
        Intent intent = getIntent();
        str = intent.getStringExtra("name");
        sex = intent.getStringExtra("sex");
        receiveListen();//开启线程
        initView();

    }
    private void receiveListen() {
        Thread thread = new Thread(new Listener(this,8080,myHandler,str));//接收8080端口的接收信息
        thread.start();
    }
    private void initView() {
        data = new ArrayList<Map<String,String>>();
        sendMsg = (EditText)findViewById(R.id.send_et);
        sendBtn = (Button)findViewById(R.id.send_btn);
        adapter = new MyAdapter(this);
        myTitle=(TextView)findViewById(R.id.title_tv);
        myListView = (ListView)findViewById(R.id.listView1);
        myListView.setSelection(myListView.getBottom());
        myListView.setAdapter(adapter);
        myTitle.setText(R.string.chatroom);
        sendBtn.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.send_btn:
                doSendButton();
                break;
        }

    }
    private void doSendButton() {
        if(sendMsg.length() == 0){
            DisplayToast("发送消息不能为空!");
            return;
        }
        else {
            map = new HashMap<String, String>();
            map.put("myName", str);
            map.put("mySex",sex);
            map.put("myMsg", sendMsg.getText().toString());
            data.add(map);
           Thread thread = new Thread(new SendMsg(map));
            thread.start();
        }
        Message msg = new Message();
        msg.what = 1;
        myHandler.sendMessage(msg);
        sendMsg.setText("");
    }

    private void DisplayToast(String str){
        Toast toast = Toast.makeText(this, str, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 220);
        toast.show();
    }

    Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            Log.d("测试", "handleMessage: "+"收到信息");
            if(msg.what == 1){
                if(sex.equals("male")){
                    adapter.mySex = true;
                }else{
                    adapter.mySex = false;
                }
                adapter.count++;
                adapter.notifyDataSetChanged();
                return;
            }
            else if(msg.what == 0){
                adapter.count++;
                adapter.notifyDataSetChanged();
                return;
            }
        }

    };

    @Override
    public void showData(final List<Map<String, String>> data1) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                data = data1;
                Message msg = new Message();
                msg.what = 0;
                myHandler.sendMessage(msg);
            }
        });
    }

    /**
     * 监听端口类
     *
     */
//    class Listener implements Runnable{
//
//
//        private Response myResponse;
//        private RequestDecode myDecode;
//        private int port;//监听端口
//        private boolean flag =true;//循环标志
//        public Listener(int port){
//            this.port = port;
//        }
//        /**
//         * 循环接收port端口的请求
//         */
//        @Override
//        public void run() {
//            while(flag){
//                initData();
//                try {
//                    myResponse.receive(port);
//                    myDecode.decode(myResponse.data);
//                    responseRun(myDecode.response);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        private void responseRun(ArrayList<String> response) throws IOException, InterruptedException {
//            if(response.get(0).equals(Constant.MESSAGE)&&!response.get(1).equals(str)){
//                map1 = new HashMap<>();
//                map1.put("otherName", response.get(1));
//                map1.put("otherSex", response.get(2));
//                map1.put("otherMsg", response.get(3));
//                data.add(map1);
//                Message msg = new Message();
//                msg.what = 0;
//                myHandler.sendMessage(msg);
//                Log.d("收到信息", "responseRun: ");
//            }
//        }
//        private void initData() {
//
//            myDecode = new RequestDecode();
//            myResponse = new Response();
//        }
//
//    }
    class MyAdapter extends BaseAdapter{
        private LayoutInflater mInflater = null;
        private boolean mySex = true;
        int count = 0;
        public MyAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);

        }
        @Override
        public int getCount() {
            return count;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }
        @Override
        public boolean areAllItemsEnabled() {
            return false;
        }

        @Override
        public boolean isEnabled(int position) {
            return false;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if(data.get(position).get("myName") != null){
                //  if(convertView == null){
                convertView = LayoutInflater.from(getApplicationContext()).inflate(
                        R.layout.send_mode, null);
                holder = new ViewHolder();
                holder.name = (TextView)convertView.findViewById(R.id.chat_person_send);
                holder.content = (TextView) convertView.findViewById(R.id.chat_content_send);
                holder.person = (ImageView)convertView.findViewById(R.id.img_send);
                convertView.setTag(holder);
                if(mySex){
                    holder.person.setImageResource(R.drawable.male);
                }else{
                    holder.person.setImageResource(R.drawable.female);
                }
                holder.name.setText(data.get(position).get("myName"));
                holder.content.setText(data.get(position).get("myMsg"));
//          }  
//          else{  
//                holder = (ViewHolder)convertView.getTag();//取出ViewHolder对象                     
//          }  

            }
            else{
                //  if(convertView == null){
                convertView = LayoutInflater.from(getApplicationContext()).inflate(
                        R.layout.receive_mode, null);
                holder = new ViewHolder();
                holder.name = (TextView)convertView.findViewById(R.id.person_receive);
                holder.content = (TextView) convertView.findViewById(R.id.content_receive);
                holder.person = (ImageView)convertView.findViewById(R.id.img_receive);
                convertView.setTag(holder);
                if(data.get(position).get("otherSex").equals("male")){
                    holder.person.setImageResource(R.drawable.male);
                }else{
                    holder.person.setImageResource(R.drawable.female);
                }
                holder.name.setText(data.get(position).get("otherName"));
                holder.content.setText(data.get(position).get("otherMsg"));
//          }  
//          else{  
//                holder = (ViewHolder)convertView.getTag();//取出ViewHolder对象                     
//          }  

            }
            return convertView;
        }

    }
    @Override
    public void onBackPressed() {
        leave();
    }

    /**
     * 弹窗
     */
    public void leave()
    {
//        AlertDialog.Builder dialog=new AlertDialog.Builder(ChatActivity.this);
//        dialog.setTitle("您真的要离开吗？").setPositiveButton("退出", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                SysApplication.getInstance().exit();
//            }
//        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
//
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();//取消弹出框
//            }
//        }).create().show();
//        AlertDialog.Builder builder = new AlertDialog.Builder(ChatActivity.this);
//        builder.setTitle("提示");
//        builder.setMessage("退出聊天室？");
//        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                SysApplication.getInstance().exit();
//            }
//        })
//        .setNegativeButton("取消",new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();//取消弹出框
//            }
//        });
//        AlertDialog dialog = builder.create();
//        dialog.show();

        finish();
    }

    /**存放控件*/
    public final class ViewHolder{
        private TextView name;
        private ImageView person;
        private TextView content;
    }
}  