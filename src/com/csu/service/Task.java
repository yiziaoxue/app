package com.csu.service;

import java.util.HashMap;
import java.util.Map;

public class Task {
	public int taskId;//任务编号
	public Map<String,Object> taskParam;//任务参数
	public String name;//发送的对象
	
	public Task(int taskId,String name){
		this.taskParam = new HashMap<String,Object>();
		this.taskId = taskId;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addData(Object ob){
		taskParam.put("data", ob);
	}
	
	public int getTaskId() {
		return taskId;
	}


	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}


	public Map<String,Object> getTaskParam() {
		return taskParam;
	}


	public void setTaskParam(Map<String,Object> taskParam) {
		this.taskParam = taskParam;
	}
}