package com.csu.annotation;

import java.io.Serializable;

public class Question implements Serializable{
	/** 
	 * 2017下午10:05:01 
	 * Server-Android
	 * asus
	 */ 
	private static final long serialVersionUID = 3844341981723699970L;

	/**
	 * ���췽��
	 * @param title ����
	 * @param author ����
	 * @param pubDate ��������
	 * @param commentCount ������
	 * @param body ��������
	 * @param answer;
	 */
	private Integer id;
	
	private String title;
	
	private User user;
	
	private String putData;
	
	private int commentCount;
	
	private String body;
	
	private Answer answer;

	private Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getPutData() {
		return putData;
	}

	public void setPutData(String putData) {
		this.putData = putData;
	}

	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Answer getAnswer() {
		return answer;
	}

	public void setAnswer(Answer answer) {
		this.answer = answer;
	}
	
	
}
