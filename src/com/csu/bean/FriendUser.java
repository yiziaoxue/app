package com.csu.bean;

import com.example.client.R;


public class FriendUser {
	private String name;
	private String detail;
	private int img;
	
	public FriendUser(String name,String detail,String sex){
		this.name = name;
		this.detail = detail;
		if(sex.equals("ÄÐ"))
		this.img = R.drawable.male;
		if(sex.equals("Å®"))
		this.img = R.drawable.female;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public int getImg() {
		return img;
	}

	public void setImg(int img) {
		this.img = img;
	}
	
	
}
