package com.csu.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.csu.client.ChatTestActivity;
import com.csu.util.ChatMessageLst;
import com.csu.util.Constant;
import com.csu.util.DataBean;
import com.example.client.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

	public class MainService extends Service implements Runnable{
		public static InetAddress address;//ip地址
		public int port = 8181;  //端口号
		private DatagramSocket socket = null;
		public static String serviceName = "";
		private String comeName = "";
		private Handler hand;
		//所有activity的保存队列
		public static ArrayList<Activity> allActivity= new ArrayList<Activity>();
		//聊天界面的activity
		public static Map<String,Activity> chatActivity = new HashMap<String,Activity>();
		public static int lastActivity;
		//通过名字获取对应的activity
		public static Activity getActivityName(String name){
			for(Activity ai:allActivity){
				if(ai.getClass().getName().indexOf(name)>=0){
					return ai;
				}
			}	
			return null;
		}
		
		//通过名字获取ctivity
		public static Activity getChatActivity(String name){
				return chatActivity.get("name");
		}
		public void log(String message){
			Log.i("MainService", message);
		}
		//任务列表
		public static ArrayList<Task> allTask = new ArrayList<Task>();
		//添加任务
		public static void addTask(Task task){
			allTask.add(task);
		}
		public boolean isrun=true;

		//实例化一个更新界面的方法
		class MHandler extends Handler{
			
			public MHandler(){}
			public MHandler(Looper looper){
				super(looper);
			}
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				ImWeiboActivity iw = null;
				switch(msg.what){			
				case Constant.Task_Login:
					iw = (ImWeiboActivity)MainService.getActivityName("LoginActivity");
					iw.refresh(msg);
					break;
				case Constant.Task_friend_Refresh:
					iw = (ImWeiboActivity)MainService.getActivityName("MainActivity");
					iw.refresh(msg,serviceName);
					break;
				case Constant.Task_Chat_Msg:
					if(!chatActivity.containsKey(comeName)){
						Log.i("MainAcitivity", "不存在该聊天窗口:" + comeName);//保存信息
						ChatMessageLst.addMsg(comeName, msg.obj.toString());
						break;
					}
					iw = (ImWeiboActivity)chatActivity.get(comeName);
					if(iw.isForeground()){
						Log.i("MainAcitivity", "该聊天窗口被隐藏:" + comeName);//保存信息
						ChatMessageLst.addMsg(comeName, msg.obj.toString());
						break;
					}
					iw.refresh(msg);
					break;
				case Constant.Task_Check_Regist:
					iw = (ImWeiboActivity)MainService.getActivityName("RegistActivity");
					if(iw == null){
						Intent it = new Intent(MainService.this,ChatTestActivity.class);
						it.putExtra("msg", msg);
						it.putExtra("otherName", comeName);
						startActivity(it);
					}else{
						iw.refresh(msg);
					}
					break;
				case Constant.Task_Question_Ask:
					iw = (ImWeiboActivity)MainService.getActivityName("MainActivity");
					iw.refresh(msg);
					break;
					
				default:  
                    super.handleMessage(msg);
				}
				super.handleMessage(msg);
			} 
			
		};
		Thread Message = (new Thread(){
			DataBean bean = new DataBean();
			byte[] receive = new byte[20480];
			DatagramPacket backPacket = new DatagramPacket(receive, receive.length); 
			public void run() {
				while(true){
					Message message = hand.obtainMessage();
					try {
						socket.receive(backPacket);
						bean.decode(receive);
						message.what = bean.getTask();
						Log.i("client", "任务ID："+message.what);
						comeName = new String(bean.getName());
						message.obj = bean.getOrg();
						hand.sendMessage(message);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		//执行任务
		public void doTask(Task task){
			try {
				DataBean bean = new DataBean();
				Message message = hand.obtainMessage();
				message.what = task.getTaskId();//任务表示id
				Map<?,?> map = task.getTaskParam();
				send(bean.ecode(task.getTaskId(), task.name, serviceName, map.get("data")));
				allTask.remove(task);
			} catch (UnsupportedEncodingException e) {
				Log.w("MainService","转码错误");
				e.printStackTrace();
			}
		}
	
		public void run() {
			while(isrun){
				Task lastTask = null;
				synchronized(allTask){
				if(allTask.size()>0){
					lastTask = allTask.get(0);
					doTask(lastTask);
				}
				}
				try{Thread.sleep(500);}catch (Exception e){}
			}
		} 
		
		public IBinder onBind(Intent arg0) {
			return null;
		}

		public void onCreate() {
			if(com.csu.util.NetUtil.checkNet(this)){
				try {
//					if(OSinfo.isLinux())
//					address = InetAddress.getByName("119.29.171.214");
//					else if(OSinfo.isWindows())
					address = InetAddress.getByName("192.168.1.101");
					socket = new DatagramSocket(8181);
	               log(address + "连接成功");  
	               hand = new MHandler(Looper.getMainLooper());
	               Message.start();
	    		} catch (UnknownHostException e) {
					e.printStackTrace();
	    		} catch (SocketException e) {
					e.printStackTrace();
				}
			}
			isrun=true;
			Thread thread=new Thread(this);
			thread.start();
		}

		public void onDestroy() {
			super.onDestroy();
			isrun=false;
		}
		public void send(byte[] data){
			DatagramPacket dataGramPacket = new DatagramPacket(data, data.length, address, port);
			try {
				socket.send(dataGramPacket);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public static void alertNeterror(final Context context){
			AlertDialog.Builder ab=new AlertDialog.Builder(context);
			ab.setTitle(R.string.error_network);
			ab.setMessage(R.string.error_network_reset);
			ab.setNegativeButton(R.string.exit_system,
					new OnClickListener(){
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
							exitApp(context);
						}				
			});
			ab.setPositiveButton(R.string.network_reset,
					new OnClickListener(){
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
							context.startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
						}
			});
			ab.create().show();
		}
		
		public static void exitApp(Context con){
			for(Activity ac:allActivity){
				ac.finish();
			}
			Intent it=new Intent("com.csu.service.MainServer");
			con.stopService(it);
		}
}
