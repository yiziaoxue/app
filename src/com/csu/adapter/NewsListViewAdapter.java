package com.csu.adapter;

import java.util.List;

import com.csu.annotation.Question;
import com.example.client.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsListViewAdapter extends BaseAdapter {
	
	// ����������
	private Context context;
	// ���б���ͼ����
	private LayoutInflater listContainer;
	// �������ݼ���
	private List<Question> newsDataList;
	
	// �б����
	private int itemViewResource;
	// �б����������(title��author��count��date��flag)
	static class ListItemView { 
		public TextView title;
		public TextView author;
		public TextView date;
		public TextView count;
		public ImageView flag;
	}

	/**
	 * ���ݸ��б����б����������������б�����桢��ȡ�������б�������
	 * @param context ���б�
	 * @param newsDataList �б�������
	 * @param itemViewResource �б�����沼��
	 */
	public NewsListViewAdapter(int itemViewResource,LayoutInflater mInflater) {
		this.listContainer = mInflater;
		this.itemViewResource = itemViewResource;
	}

	/**
	 * ��������������Ŀ�����б�����Ŀ
	 */
	public void setList(List<Question> newsDataList){
		this.newsDataList = newsDataList;
	}
	public int getCount() {
		return newsDataList.size();
	}

	public Object getItem(int arg0) {
		return null;
	}

	public long getItemId(int arg0) {
		return 0;
	}

	/**
	 * ����λ�ú�����趨�����б���
	 * @param position �б�����ʼλ��
	 * @param convertView ���ص�ÿһ���б���
	 * @param parent �б������
	 */

	public View getView(int position, View convertView, ViewGroup parent) {

		// �����Զ����б������
		ListItemView listItemView = null;

		if (convertView == null) {
			
			// ��ȡ�б���֣�this.itemViewResource--��
			convertView = listContainer.inflate(this.itemViewResource, null);
			//��ʼ���б�������(title��author��count��date��flag)
			listItemView = new ListItemView();
			listItemView.title = (TextView) convertView.findViewById(R.id.news_listitem_title);
			listItemView.author = (TextView) convertView.findViewById(R.id.news_listitem_author);
			listItemView.count = (TextView) convertView.findViewById(R.id.news_listitem_commentCount);
			listItemView.date = (TextView) convertView.findViewById(R.id.news_listitem_date);
			listItemView.flag = (ImageView) convertView.findViewById(R.id.news_listitem_flag);
			// ���ÿؼ�����convertView
			convertView.setTag(listItemView);
			
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}

		//���ζ�ȡ�б��������еĵ�position�����ݸ���news����
		Question news = newsDataList.get(position);
		listItemView.title.setText(news.getTitle());
		listItemView.author.setText(news.getUser().getUsername());
		listItemView.date.setText(news.getPutData());
		listItemView.count.setText(news.getCommentCount() + "");
		listItemView.flag.setVisibility(View.VISIBLE);
		return convertView;
	}
}