package com.csu.service;


public interface ImWeiboActivity {
	
	public void init();
	
	public void refresh(Object ...param);
	
	public boolean isForeground();
}
