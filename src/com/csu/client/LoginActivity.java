package com.csu.client;


import java.util.List;

import com.csu.service.ImWeiboActivity;
import com.csu.service.MainService;
import com.csu.service.Task;
import com.csu.util.Constant;
import com.example.client.R;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity implements ImWeiboActivity{  
    public static final int Refresh_Login = 1;//��½ˢ��
    public ProgressDialog pd;//������
    private Button btn_regist;
    private EditText num;
    private EditText pwd;
    private Button login;
	private ClickListener clickListener = new ClickListener();
    /** Called when the activity is first created. */  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.login); 
        btn_regist = (Button)findViewById(R.id.btn_regist);
        btn_regist.setOnClickListener(clickListener);
        num = (EditText)findViewById(R.id.num);
        pwd = (EditText)findViewById(R.id.pwd);
        login = (Button)findViewById(R.id.btn_login);
        login.setOnClickListener(clickListener);
        
        MainService.allActivity.add(this);
    	}
    public void init() {}
    

	@Override
	public void refresh(Object ...param) {
		switch ((Integer)((Message)param[0]).obj){
		case Constant.success:
			pd.cancel();//���ٽ�����
			MainService.allActivity.remove(this);
			Intent it=new Intent(LoginActivity.this,MainActivity.class);
			it.putExtra("serviceName", String.valueOf(num.getText()));
			MainService.serviceName = String.valueOf(num.getText());
			startActivity(it);
			finish();
			break;
		case Constant.Fail:
			pd.cancel();//���ٽ�����
			Toast.makeText(getApplicationContext(), "��½ʧ�ܣ������µ�½", Toast.LENGTH_SHORT).show();
			num.setText("");
			pwd.setText("");
			break;
		}
	}
	  public class ClickListener implements OnClickListener{  
	        public void onClick(View v){  
	        	switch(v.getId()){
				case R.id.btn_regist://ע��	
					startActivity(new Intent(LoginActivity.this,RegistActivity.class));
					break;
				case R.id.btn_login://��½
					if(pd==null){
						pd=new ProgressDialog(LoginActivity.this);
					}
					pd.setMessage("���ڵ�½");
					pd.show();
					String str = num.getText().toString()+" "+pwd.getText().toString();
					System.out.println(num.getText().toString());
					Task task = new Task(Constant.Task_Login,"������");
					task.addData(str);
					MainService.addTask(task);
			};
	        }  
	          
	    }
	  
	  /** 
	    * �ж�ĳ�������Ƿ���ǰ̨ 
	    *  
	    * @param context 
	    * @param className 
	    *            ĳ���������� 
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