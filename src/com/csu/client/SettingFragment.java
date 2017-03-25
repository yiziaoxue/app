package com.csu.client;


import com.example.client.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class SettingFragment extends Fragment {

	private View settingLayout;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		settingLayout = inflater.inflate(R.layout.setting_layout,container, false);
		return settingLayout;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		WebView webview = (WebView)settingLayout.findViewById(R.id.webview);  
		webview.getSettings().setJavaScriptEnabled(true);
		webview.loadUrl("http://119.29.171.214:8888/ecan/manager.html");  
//		webview.loadUrl("http://10.0.2.2:8181/WebService/index.jsp");  
		
	}
	
	

}
