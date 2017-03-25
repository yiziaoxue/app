package com.csu.util;

public interface Constant {
	
	static final int Tag_Int = 0;//tag标签int类型
	static final int Tag_String = 1;//tag标签string类型
	static final int Tag_Bytes = 2;//tag标签byte[]类型
	
	static final int Fail = -200;
	static final int success = 200;
	
	  //Btn的标识  
    public static final int BTN_FLAG_MESSAGE = 0x01;  
    public static final int BTN_FLAG_CONTACTS = 0x01 << 1;  
    public static final int BTN_FLAG_NEWS = 0x01 << 2;  
    public static final int BTN_FLAG_SETTING = 0x01 << 3;  
      
    //Fragment的标识  
    public static final String FRAGMENT_FLAG_MESSAGE = "消息";   
    public static final String FRAGMENT_FLAG_CONTACTS = "联系人";   
    public static final String FRAGMENT_FLAG_NEWS = "新闻";   
    public static final String FRAGMENT_FLAG_SETTING = "设置";   
    public static final String FRAGMENT_FLAG_SIMPLE = "simple"; 
    
    static final int Task_Regist = 0;//注册任务常量
	static final int Task_Login = 1;//登陆任务常量
	static final int Task_friend_Refresh = 2;//好友列表刷新任务
	static final int Task_Chat_Msg = 3;//聊天发送任务
	static final int Task_Check_Regist = 4;//注册用户名重复查询
	static final int Task_Video_Chat = 5;//視頻聊天任務
	static final int Task_Question_Post = 6;//发布问题任务
	static final int Task_Question_Ask = 7;//请求疑问列表任务
	
	
}
