package com.csu.bean;

import java.util.ArrayList;
import java.util.List;

public class FriendChild {
	public List<FriendUser> list;
	
	public FriendChild(){
		list = new ArrayList<FriendUser>();
	}
	
	public void addChild(FriendUser name){
		list.add(name);
	}
	
	public void removeChild(FriendUser name){
		list.remove(name);
	}
	
	
}
