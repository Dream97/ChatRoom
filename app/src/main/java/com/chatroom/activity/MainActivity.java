package com.chatroom.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.chatroom.R;

/**
 * Created by asus on 2017/10/8.
 */

public class MainActivity extends Activity implements OnClickListener{
	private Button adminBtn;
	private RadioButton male,female;
	private EditText nameEt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); //设置无标题
		setContentView(R.layout.activity_main);
		SysApplication.getInstance().addActivity(this);
		initView();
	}
	private void initView() {
		adminBtn = (Button)findViewById(R.id.btn_login);
		nameEt = (EditText)findViewById(R.id.editText1);
		male = (RadioButton)findViewById(R.id.rbtn_male);
		female = (RadioButton)findViewById(R.id.rbtn_female);

		adminBtn.setOnClickListener(this);
		male.setOnClickListener(this);
		female.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.btn_login:
				if(nameEt.length() == 0){
					myToast("昵称不能为空!");
					return;
				}
				else if(nameEt.length()>10){
					myToast("建议昵称不超过10个字节");
					return;
				}
				Intent intent = new Intent(MainActivity.this,ChatActivity.class);
				intent.putExtra("name",nameEt.getText().toString());
				if(male.isChecked()){
					intent.putExtra("sex","male");
				}
				else{
					intent.putExtra("sex", "female");
				}

				startActivity(intent);
				finish();
				break;
			case R.id.rbtn_male:
				male.setChecked(true);
				female.setChecked(false);
				break;
			case R.id.rbtn_female:
				male.setChecked(false);
				female.setChecked(true);
				break;

			default:break;
		}
	}
	private void myToast(String s){
		Toast toast = Toast.makeText(this, s, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.BOTTOM, 0, 220);
		toast.show();
	}


}  