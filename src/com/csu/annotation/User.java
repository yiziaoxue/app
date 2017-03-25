package com.csu.annotation;

import java.io.Serializable;

public class User implements Serializable{
	/** 
	 * 2017下午10:05:06 
	 * Server-Android
	 * asus
	 */ 
	private static final long serialVersionUID = 2556861328825545318L;

	private Integer id;
	
	private String username;
	
	private Integer sex;
	
	private String password;

	private int state;
	
//	private ArrayList<User> users;
	
	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username; 
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}
	
	
}
