package com.csu.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.csu.service.ImWeiboActivity;
import com.csu.service.MainService;
import com.csu.service.Task;
import com.csu.util.Constant;
import com.example.client.R;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistActivity extends Activity implements OnClickListener,ImWeiboActivity{
	private EditText user,pass1,pass2;
	private Button submit;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		user = (EditText) this.findViewById(R.id.user);
		pass1 = (EditText) this.findViewById(R.id.pass1);
		pass2 = (EditText) this.findViewById(R.id.pass2);
		submit = (Button) this.findViewById(R.id.submit);
		submit.setOnClickListener(this);
		pass1.setOnClickListener(this);
		MainService.allActivity.add(this);
	}

	//实现onTouchEvent触屏函数但点击屏幕时销毁本Activity
	public boolean onTouchEvent(MotionEvent event){
		finish();
		return true;
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.submit:
			if(user.getText().equals("") || pass1.getText().equals("") || pass2.getText().equals("")){
				Toast.makeText(getApplicationContext(), "对不起，您的输入不合法，请重新输入", Toast.LENGTH_SHORT).show();
				return;
			}
			if(!pass1.getText().toString().equals(pass2.getText().toString())){
				Toast.makeText(getApplicationContext(), "对不起，您两次输入的密码不一致，请重新输入", Toast.LENGTH_SHORT).show();
				pass1.setText("");
				pass2.setText("");
				return;
			}
			String str = user.getText().toString()+" "+pass1.getText().toString();
			Task task = new Task(Constant.Task_Regist,"服务器");
			task.addData(str);
			MainService.addTask(task);
			MainService.allActivity.remove(this);
			finish();
			break;
		case R.id.pass1:
			Map<String,String> m = new HashMap<String,String>();
			Task t = new Task(Constant.Task_Check_Regist,"服务器");
			t.addData(user.getText().toString());
			MainService.addTask(t);
			break;
		}
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refresh(Object... param) {
		switch (((Message)param[0]).arg1){
		case Constant.Fail:
			Toast toast = Toast.makeText(getApplicationContext(), "对不起,该用户名已经存在，请重新输入", Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER , 0, 50);
			toast.show();
			user.setText("");
			pass1.setText("");
			break;
		case Constant.success:
			break;
		}
	}
	
	/** 
	    * 判断某个界面是否在前台 
	    *  
	    * @param context 
	    * @param className 
	    *            某个界面名称 
	    */  
	@Override
	public boolean isForeground() {  
		 String packageName= this.getPackageName(); 
		 
		 String topActivityClassName = null;
         ActivityManager activityManager = (ActivityManager)(this.getSystemService(android.content.Context.ACTIVITY_SERVICE )) ;
         List<RunningTaskInfo> runningTaskInfos = activityManager.getRunningTasks(1);
         
         if(runningTaskInfos != null){
             ComponentName f = runningTaskInfos.get(0).topActivity;
             topActivityClassName = f.getClassName();
         }
         
	     System.out.println("packageName="+packageName+",topActivityClassName="+topActivityClassName);
	     if (packageName!=null&&topActivityClassName!=null&&topActivityClassName.startsWith(packageName)) {
	         System.out.println("---> isRunningForeGround");
	         return true;
	     } else {
	         System.out.println("---> isRunningBackGround");
	         return false;
	     }
	}  
}