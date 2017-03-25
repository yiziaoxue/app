package com.csu.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.util.Log;

public class ChatMessageLst {
	private static Map<String,List<String>> chatMsgLst = new HashMap<String,List<String>>();
	
	public static void addMsg(String name,String msg){
		Log.i("chatMessageLst", "保存消息的队列名字" + name);
		if(chatMsgLst.containsKey(name)){
			chatMsgLst.get(name).add(msg);
		}else{
			List<String> lst = new ArrayList<String>();
			lst.add(msg);
			chatMsgLst.put(name, lst);
		}
	}
	
	public static List<String> getMsg(String name){
		if(chatMsgLst.containsKey(name)){
			return chatMsgLst.get(name);
		}
		return new ArrayList<String>();
	}
	
	public static boolean existOffLineMsg(String name){
		if(chatMsgLst.containsKey(name) && chatMsgLst.get(name).size() > 0){
			return true;
		}else{
			return false;
		}
	}
	
	public static int getOffLineMsgSize(String name){
		Log.i("chatMessageLst", "收消息的队列名字" + name);
		if(chatMsgLst.containsKey(name) && chatMsgLst.get(name).size() > 0){
			return chatMsgLst.get(name).size();
		}else{
			return 0;
		}
	}
}
