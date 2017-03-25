package com.csu.util;

import com.csu.service.ImWeiboActivity;
import com.csu.service.MainService;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class MHandler extends Handler{
	 public MHandler() {}
	 
	 public MHandler(Looper L) {
		 super(L);
	 }
	 
	 public void handleMessage(Message msg) {
		super.handleMessage(msg);
		switch(msg.what){				
		case Constant.Task_Login:
			ImWeiboActivity iw1 = (ImWeiboActivity)MainService.getActivityName("LoginActivity");
			//����ˢ�����ͺ�ˢ����Ϣ
			iw1.refresh(msg);
			break;
		case Constant.Task_friend_Refresh:
//			ImWeiboActivity iw2 = (ImWeiboActivity)MainService.getActivityName("MainActivity");
//			//����ˢ�����ͺ�ˢ����Ϣ
//			iw2.refresh(MainActivity.refresh_friend,msg);
			break;
			}
		}
}
