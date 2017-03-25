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
			//定义刷新类型和刷新信息
			iw1.refresh(msg);
			break;
		case Constant.Task_friend_Refresh:
//			ImWeiboActivity iw2 = (ImWeiboActivity)MainService.getActivityName("MainActivity");
//			//定义刷新类型和刷新信息
//			iw2.refresh(MainActivity.refresh_friend,msg);
			break;
			}
		}
}
