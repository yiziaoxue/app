package com.csu.bean;

import java.util.ArrayList;
import java.util.List;

public class FriendGroup {
	public static List<String> list = new ArrayList<String>();
	
	
	public void addGroup(String name){
		list.add(name);
	}
	
	public void removeGroup(String name){
		list.remove(name);
	}
	
	public static String get(int i){
		return list.get(i);
	}
	
	public static int size(){
		return list.size();
	}
}
