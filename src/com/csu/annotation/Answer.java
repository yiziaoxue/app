package com.csu.annotation;

import java.io.Serializable;

public class Answer implements Serializable{
	/** 
	 * 2017下午10:04:49 
	 * Server-Android
	 * asus
	 */ 
	private static final long serialVersionUID = -7356203687192223194L;

	private Integer id;
	
	private String answer;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
}
