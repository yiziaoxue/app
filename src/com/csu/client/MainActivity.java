package com.csu.client;

import java.util.List;

import com.csu.annotation.Question;
import com.csu.annotation.User;
import com.csu.client.ContactsFragment.FriendListLitener;
import com.csu.client.MessageFragment.QuestListlistener;
import com.csu.service.ImWeiboActivity;
import com.csu.service.MainService;
import com.csu.service.Task;
import com.csu.util.Constant;
import com.csu.util.NewsDetail;
import com.example.client.R;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.ActivityManager;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

/**
 * 项目的主Activity，所有的Fragment都嵌入在这里。
 * 
 * @author guolin
 */
public class MainActivity extends FragmentActivity implements OnClickListener,ImWeiboActivity,FriendListLitener,QuestListlistener{

	public static final int refresh_friend = 1;//刷新好友列表
//	private MessageFragment messageFragment;
	private ContactsFragment contactsFragment;
//	private QustionFragment newsFragment;
	private SettingFragment settingFragment;
//	private View messageLayout;
	private View contactsLayout;
//	private View newsLayout;
	private View settingLayout;
//	private ImageView messageImage;
	private ImageView contactsImage;
//	private ImageView newsImage;
	private ImageView settingImage;
//	private TextView messageText;
	private TextView contactsText;
//	private TextView newsText;
	private TextView settingText;
	private FragmentManager fragmentManager;
	private ImageButton surchBtton;
	private String serviceName = "";
	//保存好友的队列
	private List<User> list; 
	private List<Question> questList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		MainService.allActivity.add(this);
		// 初始化布局元素
		initViews();
		fragmentManager = this.getFragmentManager();
		// 第一次启动时选中第0个tab
		setTabSelection(1);
		Bundle extras = getIntent().getExtras();  
        if (!extras.isEmpty()) {  
        	serviceName =  extras.getString("serviceName");  
        }
        extras.clear();
	}

	/**
	 * 在这里获取到每个需要用到的控件的实例，并给它们设置好必要的点击事件。
	 */
	private void initViews() {
//		messageLayout = findViewById(R.id.message_layout);
		contactsLayout = findViewById(R.id.contacts_layout);
//		newsLayout = findViewById(R.id.news_layout);
		settingLayout = findViewById(R.id.setting_layout);
//		messageImage = (ImageView) findViewById(R.id.message_image);
		contactsImage = (ImageView) findViewById(R.id.contacts_image);
//		newsImage = (ImageView) findViewById(R.id.news_image);
		settingImage = (ImageView) findViewById(R.id.setting_image);
//		messageText = (TextView) findViewById(R.id.message_text);
		contactsText = (TextView) findViewById(R.id.contacts_text);
//		newsText = (TextView) findViewById(R.id.news_text);
		settingText = (TextView) findViewById(R.id.setting_text);
		surchBtton = (ImageButton) findViewById(R.id.header_surc_btn);
		
//		messageLayout.setOnClickListener(this);
		contactsLayout.setOnClickListener(this);
//		newsLayout.setOnClickListener(this);
		settingLayout.setOnClickListener(this);
		surchBtton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
//		case R.id.message_layout:
//			// 当点击了消息tab时，选中第1个tab
//			setTabSelection(0);
//			break;
		case R.id.contacts_layout:
			// 当点击了联系人tab时，选中第2个tab
			setTabSelection(1);
			break;
//		case R.id.news_layout:
//			// 当点击了动态tab时，选中第3个tab
//			setTabSelection(2);
//			break;
		case R.id.setting_layout:
			// 当点击了设置tab时，选中第4个tab
			setTabSelection(3);
			break;
		case R.id.header_surc_btn:
			Intent it=new Intent(MainActivity.this,WebViewActivity.class);
			startActivity(it);
			break;
		default:
			break;
		}
	}

	/**
	 * 根据传入的index参数来设置选中的tab页。
	 * 
	 * @param index
	 *            每个tab页对应的下标。0表示消息，1表示联系人，2表示动态，3表示设置。
	 */
	private void setTabSelection(int index) {
		int flag = -1;
		if(flag == index)
			return;
		Task task = null;
		// 每次选中之前先清楚掉上次的选中状态
		clearSelection();
		// 开启一个Fragment事务
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		// 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
		hideFragments(transaction);
		switch (index) {
		/*case 0:
			flag = 0;
			// 当点击了消息tab时，改变控件的图片和文字颜色
			messageImage.setImageResource(R.drawable.message_selected);
			messageText.setTextColor(Color.WHITE);
			if (messageFragment == null) {
				// 如果MessageFragment为空，则创建一个并添加到界面上
				messageFragment = new MessageFragment();
				transaction.add(R.id.content, messageFragment);
			} 
			task = new Task(Constant.Task_Question_Ask ,serviceName);
			MainService.allTask.add(task);
			// 如果MessageFragment不为空，则直接将它显示出来
			transaction.show(messageFragment);
			//获得mListView的实力，等待数据装载
			break;*/
		case 1:
			flag = 1;
			// 当点击了联系人tab时，改变控件的图片和文字颜色
			contactsImage.setImageResource(R.drawable.contacts_selected);
			contactsText.setTextColor(Color.WHITE);
			if (contactsFragment == null) {
				// 如果ContactsFragment为空，则创建一个并添加到界面上
				contactsFragment = new ContactsFragment();
				transaction.add(R.id.content, contactsFragment);
			}
			task = new Task(Constant.Task_friend_Refresh,serviceName);
			task.addData("no");
			MainService.allTask.add(task);
			// 如果ContactsFragment不为空，则直接将它显示出来
			transaction.show(contactsFragment);
			break;
		/*case 2:
			flag = 2;
			// 当点击了动态tab时，改变控件的图片和文字颜色
			newsImage.setImageResource(R.drawable.news_selected);
			newsText.setTextColor(Color.WHITE);
			if (newsFragment == null) {
				// 如果NewsFragment为空，则创建一个并添加到界面上
				newsFragment = new QustionFragment();
				transaction.add(R.id.content, newsFragment);
			} else {
				// 如果NewsFragment不为空，则直接将它显示出来
				transaction.show(newsFragment);
			}
			break;*/
		case 3:
			flag = 3;
			// 当点击了设置tab时，改变控件的图片和文字颜色
			settingImage.setImageResource(R.drawable.setting_selected);
			settingText.setTextColor(Color.WHITE);
			if (settingFragment == null) {
				// 如果SettingFragment为空，则创建一个并添加到界面上
				settingFragment = new SettingFragment();
				transaction.add(R.id.content, settingFragment);
			} else {
				// 如果SettingFragment不为空，则直接将它显示出来
				transaction.show(settingFragment);
			}
			break;
		}
		transaction.commit();
	}

	/**
	 * 清除掉所有的选中状态。
	 */
	private void clearSelection() {
//		messageImage.setImageResource(R.drawable.message_unselected);
//		messageText.setTextColor(Color.parseColor("#82858b"));
		contactsImage.setImageResource(R.drawable.contacts_unselected);
		contactsText.setTextColor(Color.parseColor("#82858b"));
//		newsImage.setImageResource(R.drawable.news_unselected);
//		newsText.setTextColor(Color.parseColor("#82858b"));
		settingImage.setImageResource(R.drawable.setting_unselected);
		settingText.setTextColor(Color.parseColor("#82858b"));
	}

	/**
	 * 将所有的Fragment都置为隐藏状态。
	 * 
	 * @param transaction
	 *            用于对Fragment执行操作的事务
	 */
	private void hideFragments(FragmentTransaction transaction) {
		/*if (messageFragment != null) {
			transaction.hide(messageFragment);
		}*/
		if (contactsFragment != null) {
			transaction.hide(contactsFragment);
		}
		/*if (newsFragment != null) {
			transaction.hide(newsFragment);
		}*/
		if (settingFragment != null) {
			transaction.hide(settingFragment);
		}
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refresh(Object... param) {
		switch (((Message)param[0]).what){
		case Constant.Task_friend_Refresh:
			list = (List<User>)((Message)param[0]).obj;
			contactsFragment.initData(list);
			break;
		/*case Constant.Task_Question_Ask:
			questList = (List<Question>)((Message)param[0]).obj;
			messageFragment.initNewsData(questList);
			break;*/
		}
	}

	@Override
	public void refreshFriend(String ...param) {
		//子界面执行该方法，触发次方法向chatactivity发送消息，并开启之
		String otherName = param[0];
		Intent it=new Intent(MainActivity.this,ChatTestActivity.class);
		it.putExtra("serviceName", serviceName);
		it.putExtra("otherName", otherName);
		startActivity(it);
	}

	@Override
	public void refreshAnswer(int position) {
		//子界面执行该方法，触发次方法向chatactivity发送消息，并开启之
		Intent it=new Intent(MainActivity.this,NewsDetail.class);
		it.putExtra("news_id", position);
		startActivity(it);
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
