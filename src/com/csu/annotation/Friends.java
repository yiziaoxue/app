package com.csu.annotation;

import java.io.Serializable;

public class Friends implements Serializable{
	/** 
	 * 2017下午10:04:55 
	 * Server-Android
	 * asus
	 */ 
	private static final long serialVersionUID = 6067907730964165468L;

	private Integer id;
	
	private User host;
	
	private User friend;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getHost() {
		return host;
	}

	public void setHost(User host) {
		this.host = host;
	}

	public User getFriend() {
		return friend;
	}

	public void setFriend(User friend) {
		this.friend = friend;
	}
	
	
}
