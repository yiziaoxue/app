package com.csu.client;

import java.util.ArrayList;
import java.util.List;

import com.csu.adapter.ExpandAdapter;
import com.csu.annotation.User;
import com.csu.bean.Item;
import com.example.client.R;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

public class ContactsFragment extends Fragment implements OnChildClickListener{
    private ExpandableListView mListView = null;
    private ExpandAdapter mAdapter = null;
    private List<List<Item>> mData = new ArrayList<List<Item>>();
    private List<User> list;//保存好友的队列
    private FriendListLitener friendList;
	private View contactsLayout;
	private LayoutInflater inflater;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try { 
			friendList = (FriendListLitener)activity; 
			Log.i("ContactsFragment", "执行回调函数");
		  } catch (ClassCastException e) { 
		     throw new ClassCastException(); 
		  } 
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
			this.inflater = inflater;
			contactsLayout = inflater.inflate(R.layout.contacts_layout,container, false);
			list = new ArrayList<User>();
	        mListView = (ExpandableListView)contactsLayout.findViewById(R.id.expendlist);
	        mListView.setGroupIndicator(getResources().getDrawable(R.drawable.expander_floder));
	        mListView.setDescendantFocusability(ExpandableListView.FOCUS_AFTER_DESCENDANTS);
	        mListView.setOnChildClickListener(this);
	        return contactsLayout;
	    }
	
	    /*
	     * ChildView 设置 布局很可能onChildClick进不来，要在 ChildView layout 里加上
	     * android:descendantFocusability="blocksDescendants",
	     * 还有isChildSelectable里返回true
	     */
	    @Override
	    public boolean onChildClick(ExpandableListView parent, View v,int groupPosition, int childPosition, long id) {
	        // TODO Auto-generated method stub
	    	//点击好友开始聊天
	        Item item = mAdapter.getChild(groupPosition, childPosition);
	        friendList.refreshFriend(item.getName());
	        return true;
	    }
	    
		public void initData(List<User> list) {
				this.list = list;
				List<Item> ilist = new ArrayList<Item>();
		        for(User user:list){
		            Item item = new Item(R.drawable.female, user.getUsername(), user.getPassword());
		            ilist.add(item);
	            }
		        mData.clear();
		        mData.add(ilist);
		        mAdapter = new ExpandAdapter(mData, inflater);
			    mListView.setAdapter(mAdapter);
	    }
		
		public interface FriendListLitener{  
	        public void refreshFriend(String ...param);   
	    }  
	}

