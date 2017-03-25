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
	
	// 运行上下文
	private Context context;
	// 父列表视图容器
	private LayoutInflater listContainer;
	// 新闻数据集合
	private List<Question> newsDataList;
	
	// 列表项布局
	private int itemViewResource;
	// 列表项组件集合(title、author、count、date、flag)
	static class ListItemView { 
		public TextView title;
		public TextView author;
		public TextView date;
		public TextView count;
		public ImageView flag;
	}

	/**
	 * 根据父列表构造列表项适配器，加载列表项界面、读取并设置列表项数据
	 * @param context 父列表
	 * @param newsDataList 列表项数据
	 * @param itemViewResource 列表项界面布局
	 */
	public NewsListViewAdapter(int itemViewResource,LayoutInflater mInflater) {
		this.listContainer = mInflater;
		this.itemViewResource = itemViewResource;
	}

	/**
	 * 根据新闻数据条目返回列表项数目
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
	 * 根据位置和相关设定返回列表项
	 * @param position 列表项起始位置
	 * @param convertView 返回的每一个列表项
	 * @param parent 列表项父容器
	 */

	public View getView(int position, View convertView, ViewGroup parent) {

		// 创建自定义列表项组件
		ListItemView listItemView = null;

		if (convertView == null) {
			
			// 获取列表项布局（this.itemViewResource--）
			convertView = listContainer.inflate(this.itemViewResource, null);
			//初始化列表项各组件(title、author、count、date、flag)
			listItemView = new ListItemView();
			listItemView.title = (TextView) convertView.findViewById(R.id.news_listitem_title);
			listItemView.author = (TextView) convertView.findViewById(R.id.news_listitem_author);
			listItemView.count = (TextView) convertView.findViewById(R.id.news_listitem_commentCount);
			listItemView.date = (TextView) convertView.findViewById(R.id.news_listitem_date);
			listItemView.flag = (ImageView) convertView.findViewById(R.id.news_listitem_flag);
			// 设置控件集到convertView
			convertView.setTag(listItemView);
			
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}

		//依次读取列表项数据中的第position项数据赋于news对象
		Question news = newsDataList.get(position);
		listItemView.title.setText(news.getTitle());
		listItemView.author.setText(news.getUser().getUsername());
		listItemView.date.setText(news.getPutData());
		listItemView.count.setText(news.getCommentCount() + "");
		listItemView.flag.setVisibility(View.VISIBLE);
		return convertView;
	}
}