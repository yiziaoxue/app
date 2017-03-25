package com.csu.client;


import java.util.ArrayList;
import java.util.List;
import com.csu.adapter.NewsListViewAdapter;
import com.csu.annotation.Question;
import com.csu.client.QustionFragment.ClickListener;
import com.csu.service.MainService;
import com.csu.service.Task;
import com.csu.util.Constant;
import com.csu.util.NewsDetail;
import com.csu.util.PullToRefreshListView;
import com.example.client.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;

public class MessageFragment extends Fragment {
	// ����ˢ���б�
	private PullToRefreshListView pullToRefreshListView;
	// ����һ���洢�б������ݵ�ArrayList<>�ṹ
	public static List<Question> newsDataList = new ArrayList<Question>();
	private NewsListViewAdapter newsListViewAdapter;
	private QuestListlistener quest;
	private View messageLayout;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try { 
			quest = (QuestListlistener)activity; 
		  } catch (ClassCastException e) { 
		     throw new ClassCastException(); 
		  } 
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		messageLayout = inflater.inflate(R.layout.news_list,container, false);
		newsListViewAdapter = new NewsListViewAdapter(R.layout.news_list_item,inflater);
		// ���������б�(����ˢ���б�)
		pullToRefreshListView = (PullToRefreshListView) messageLayout.findViewById(R.id.frame_listview_news);
		// �󶨵���б����¼�������(���ݵ�����б��������»��ʾ��Ӧ��������)
		pullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
					quest.refreshAnswer(position);
					}
				});
		return messageLayout;
	}

	/**
	 * ��ʼ����������(����20�����Ų�������)
	 */
	public void initNewsData(List<Question> list ) {
		this.newsDataList = list;
		// ���б���������(Ϊ�б��������)
		newsListViewAdapter.setList(newsDataList);
		pullToRefreshListView.setAdapter(newsListViewAdapter);
	}

	//����һ����������б�Ľӿ�
	public interface QuestListlistener{  
        public void refreshAnswer(int position);   
    }  
}
