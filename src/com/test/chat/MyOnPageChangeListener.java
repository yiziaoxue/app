package com.test.chat;

import com.csu.client.ChatTestActivity;
import com.example.client.R;

import android.support.v4.view.ViewPager.OnPageChangeListener;
/**
 * 褰揤iewPager缈婚〉鏃惰Е鍙� * @author daobo.yuan
 *
 */
public class MyOnPageChangeListener implements OnPageChangeListener{

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		updateSelectedIndex(arg0);
	}
	/**
	 * 鏇存柊褰撳墠ViewPager绱㈠紩
	 * @param currentSelectIndex
	 */
	private void updateSelectedIndex(int currentSelectIndex){
		if(null!=ChatTestActivity.self){
			int childCount = ChatTestActivity.self.ll_vp_selected_index.getChildCount();
			for (int i = 0; i < childCount; i++) {
				if(currentSelectIndex==i){
					ChatTestActivity.self.ll_vp_selected_index.getChildAt(i).setBackgroundResource(R.drawable.page_focused);
				}else{
					ChatTestActivity.self.ll_vp_selected_index.getChildAt(i).setBackgroundResource(R.drawable.page_unfocused);
				}
			}
		}
	}
}
