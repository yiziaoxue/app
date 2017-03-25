package com.csu.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetUtil {
	public static boolean checkNet(Context context){
		try{
			//获得手机的所有连接对象
			ConnectivityManager connectivity = 
					(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
			if(connectivity != null){
				//获得网络管理连接对象
				NetworkInfo info = connectivity.getActiveNetworkInfo(); 
				if(info != null && info.isConnected()){
					//判断当前网络是否连接
					if(info.getState() == NetworkInfo.State.CONNECTED){
						return true;
					}
				}
			}
		}catch(Exception e){}
		return false;
	}
}
