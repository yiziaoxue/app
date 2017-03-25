package com.csu.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.test.chat.ChatMessage;

public class ChatMessageMap {
	
	private static Map<String,List<ChatMessage>> map = new HashMap<String,List<ChatMessage>>();
	
	public static List<ChatMessage> getChatMessage(String name){
		if(map.containsKey(name))
			return map.get(name);
		else
			return null;
	}
	
	public static void setChatMessage(String name,ChatMessage msg){
		if(map.containsKey(name)){
			map.get(name).add(msg);
		}else{
			List<ChatMessage> list = new ArrayList<ChatMessage>();
			list.add(msg);
			map.put(name, list);
		}
	}
}
