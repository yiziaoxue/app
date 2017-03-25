package com.test.chat;

/**
 * 聊天消息实体类
 * @author daobo.yuan
 */
public class ChatMessage {
	public int userID;// 发送消息人ID
	public String chatMsg;// 消息内容
	public String nickName;//昵称
	public ChatMessage() {
		super();
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public String getChatMsg() {
		return chatMsg;
	}
	public void setChatMsg(String chatMsg) {
		this.chatMsg = chatMsg;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
}