package com.csu.util;

import com.example.client.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class PullToRefreshListView extends ListView implements OnScrollListener {

	// �������ɿ������ڡ����ˢ�±�־
	private final static int PULL_TO_REFRESH = 0;
	private final static int RELEASE_TO_REFRESH = 1;
	private final static int REFRESHING = 2;
	private final static int DONE = 3;

	// LayoutInflater������res/layout/�µ�xml�����ļ�������ʵ����,����������findViewById()
	// pull_to_refresh_head.xml���ּ��������
	private LayoutInflater inflater;
	private LinearLayout headView;
	private TextView tipsTextview;
	private TextView lastUpdatedTextView;
	private ImageView arrowImageView;
	private ProgressBar progressBar;

	// "�������ɿ�����ˢ��"��ͷͼ�궯��Ч��
	private RotateAnimation animation;
	private RotateAnimation reverseAnimation;

	// ���ڱ�֤startY��ֵ��һ��������touch�¼���ֻ����¼һ��
	private boolean isRecored;

	// headView�Ŀ�ߺ;���top�ľ���
	private int headContentWidth;
	private int headContentHeight;
	private int headContentOriginalTopPadding;

	private int startY;
	private int firstItemIndex;
	private int currentScrollState;

	private int state;

	private boolean isBack;

	//ˢ�½ӿ�
	public OnRefreshListener refreshListener;

	/**
	 * ���췽��1
	 * 
	 * @param context
	 * @param attrs
	 */
	public PullToRefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	/**
	 * ���췽��2
	 * 
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public PullToRefreshListView(Context context, AttributeSet attrs,int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	/**
	 * ��ʼ��:���������෴��ˢ�¼�ͷͼ�껬������
	 * 
	 * @param context
	 */
	private void init(Context context) {
		// ��������ˢ��Ч����0��-180��
		animation = new RotateAnimation(0, -180,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		animation.setInterpolator(new LinearInterpolator());
		animation.setDuration(100);
		animation.setFillAfter(true);

		// �ɿ�����ˢ��Ч����-180��0��
		reverseAnimation = new RotateAnimation(-180, 0,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		reverseAnimation.setInterpolator(new LinearInterpolator());
		reverseAnimation.setDuration(100);
		reverseAnimation.setFillAfter(true);

		// ����"ͷ��ˢ��"���ֶ���headView,����ʼ�����и����
		inflater = LayoutInflater.from(context);
		headView = (LinearLayout) inflater.inflate(
				R.layout.pull_to_refresh_head, null);
		// ���¼�ͷͼ��
		arrowImageView = (ImageView) headView
				.findViewById(R.id.head_arrowImageView);
		arrowImageView.setMinimumWidth(50);
		arrowImageView.setMinimumHeight(50);
		// ԲȦ���ؽ�����
		progressBar = (ProgressBar) headView
				.findViewById(R.id.head_progressBar);
		// �ı���"��������ˢ��"
		tipsTextview = (TextView) headView.findViewById(R.id.head_tipsTextView);
		// �ı���"�������ʱ��"
		lastUpdatedTextView = (TextView) headView
				.findViewById(R.id.head_lastUpdatedTextView);

		headContentOriginalTopPadding = headView.getPaddingTop();
		measureView(headView);
		headContentHeight = headView.getMeasuredHeight();
		headContentWidth = headView.getMeasuredWidth();

		headView.setPadding(headView.getPaddingLeft(), -1 * headContentHeight,
				headView.getPaddingRight(), headView.getPaddingBottom());
		// ˢ��headView
		headView.invalidate();
		addHeaderView(headView);
		setOnScrollListener(this);
	}

	public void onScroll(AbsListView view, int firstVisiableItem,
			int visibleItemCount, int totalItemCount) {
		firstItemIndex = firstVisiableItem;
	}

	public void onScrollStateChanged(AbsListView view, int scrollState) {
		currentScrollState = scrollState;
	}

	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (firstItemIndex == 0 && !isRecored) {
				startY = (int) event.getY();
				isRecored = true;
				// System.out.println("��ǰ-���¸߶�-ACTION_DOWN-Y��"+startY);
			}
			break;

		case MotionEvent.ACTION_CANCEL:// ʧȥ����&ȡ������
		case MotionEvent.ACTION_UP:

			if (state != REFRESHING) {
				if (state == DONE) {
					// System.out.println("��ǰ-̧��-ACTION_UP��DONEʲô������");
				} else if (state == PULL_TO_REFRESH) {
					state = DONE;
					changeHeaderViewByState();
					// System.out.println("��ǰ-̧��-ACTION_UP��PULL_To_REFRESH-->DONE-������ˢ��״̬��ˢ�����״̬");
				} else if (state == RELEASE_TO_REFRESH) {
					state = REFRESHING;
					changeHeaderViewByState();
					onRefresh();
					// System.out.println("��ǰ-̧��-ACTION_UP��RELEASE_To_REFRESH-->REFRESHING-���ɿ�ˢ��״̬����ˢ�����״̬");
				}
			}

			isRecored = false;
			isBack = false;

			break;

		case MotionEvent.ACTION_MOVE:
			int tempY = (int) event.getY();
			// System.out.println("��ǰ-����-ACTION_MOVE Y��"+tempY);
			if (!isRecored && firstItemIndex == 0) {
				// System.out.println("��ǰ-����-��¼��קʱ��λ�� Y��"+tempY);
				isRecored = true;
				startY = tempY;
			}
			if (state != REFRESHING && isRecored) {
				// �����ɿ�ˢ����
				if (state == RELEASE_TO_REFRESH) {
					// �����ƣ��Ƶ���Ļ�㹻�ڸ�head�ĳ̶ȣ�����û��ȫ���ڸ�
					if ((tempY - startY < headContentHeight + 20)
							&& (tempY - startY) > 0) {
						state = PULL_TO_REFRESH;
						changeHeaderViewByState();
						// System.out.println("��ǰ-����-ACTION_MOVE��RELEASE_To_REFRESH--��PULL_To_REFRESH-���ɿ�ˢ��״̬ת�䵽����ˢ��״̬");
					}
					// һ�����Ƶ���
					else if (tempY - startY <= 0) {
						state = DONE;
						changeHeaderViewByState();
						// System.out.println("��ǰ-����-ACTION_MOVE��RELEASE_To_REFRESH--��DONE-���ɿ�ˢ��״̬ת�䵽done״̬");
					}
					// �����������߻�û�����Ƶ���Ļ�����ڸ�head
					else {
						// ���ý����ر�Ĳ�����ֻ�ø���paddingTop��ֵ������
					}
				}
				// ��û�е�����ʾ�ɿ�ˢ�µ�ʱ��,DONE������PULL_To_REFRESH״̬
				else if (state == PULL_TO_REFRESH) {
					// ���������Խ���RELEASE_TO_REFRESH��״̬
					if (tempY - startY >= headContentHeight + 20
							&& currentScrollState == SCROLL_STATE_TOUCH_SCROLL) {
						state = RELEASE_TO_REFRESH;
						isBack = true;
						changeHeaderViewByState();
						// System.out.println("��ǰ-����-PULL_To_REFRESH--��RELEASE_To_REFRESH-��done��������ˢ��״̬ת�䵽�ɿ�ˢ��");
					}
					// ���Ƶ�����
					else if (tempY - startY <= 0) {
						state = DONE;
						changeHeaderViewByState();
						// System.out.println("��ǰ-����-PULL_To_REFRESH--��DONE-��Done��������ˢ��״̬ת�䵽done״̬");
					}
				}
				// done״̬��
				else if (state == DONE) {
					if (tempY - startY > 0) {
						state = PULL_TO_REFRESH;
						changeHeaderViewByState();
						// System.out.println("��ǰ-����-DONE--��PULL_To_REFRESH-��done״̬ת�䵽����ˢ��״̬");
					}
				}

				// ����headView��size
				if (state == PULL_TO_REFRESH) {
					int topPadding = (int) ((-1 * headContentHeight + (tempY - startY)));
					headView.setPadding(headView.getPaddingLeft(), topPadding,
							headView.getPaddingRight(),
							headView.getPaddingBottom());
					headView.invalidate();
					// System.out.println("��ǰ-����ˢ��PULL_To_REFRESH-TopPad��"+topPadding);
				}

				// ����headView��paddingTop
				if (state == RELEASE_TO_REFRESH) {
					int topPadding = (int) ((tempY - startY - headContentHeight));
					headView.setPadding(headView.getPaddingLeft(), topPadding,
							headView.getPaddingRight(),
							headView.getPaddingBottom());
					headView.invalidate();
					// System.out.println("��ǰ-�ͷ�ˢ��RELEASE_To_REFRESH-TopPad��"+topPadding);
				}
			}
			break;
		}
		return super.onTouchEvent(event);
	}

	/**
	 * ״̬�ı�ʱ���ø÷������½���(��ͷͼ�꣬��ʾ�ı�������ʱ�䣬��������)
	 */
	private void changeHeaderViewByState() {
		switch (state) {
		
		// �ɿ�ˢ��
		case RELEASE_TO_REFRESH:

			arrowImageView.setVisibility(View.VISIBLE);
			progressBar.setVisibility(View.GONE);
			tipsTextview.setVisibility(View.VISIBLE);
			lastUpdatedTextView.setVisibility(View.VISIBLE);
			arrowImageView.clearAnimation();
			arrowImageView.startAnimation(animation);
			tipsTextview.setText(R.string.pull_to_refresh_release_label);
			break;
		
		// ����ˢ��
		case PULL_TO_REFRESH:

			progressBar.setVisibility(View.GONE);
			tipsTextview.setVisibility(View.VISIBLE);
			lastUpdatedTextView.setVisibility(View.VISIBLE);
			arrowImageView.clearAnimation();
			arrowImageView.setVisibility(View.VISIBLE);
			if (isBack) {
				isBack = false;
				arrowImageView.clearAnimation();
				arrowImageView.startAnimation(reverseAnimation);
			}
			tipsTextview.setText(R.string.pull_to_refresh_pull_label);
			break;

		//����ˢ��
		case REFRESHING:
			headView.setPadding(headView.getPaddingLeft(),
					headContentOriginalTopPadding, headView.getPaddingRight(),
					headView.getPaddingBottom());
			headView.invalidate();
			progressBar.setVisibility(View.VISIBLE);
			arrowImageView.clearAnimation();
			arrowImageView.setVisibility(View.GONE);
			tipsTextview.setText(R.string.pull_to_refresh_refreshing_label);
			lastUpdatedTextView.setVisibility(View.GONE);
			break;
			
		//ˢ�����
		case DONE:
			headView.setPadding(headView.getPaddingLeft(), -1
					* headContentHeight, headView.getPaddingRight(),
					headView.getPaddingBottom());
			headView.invalidate();
			progressBar.setVisibility(View.GONE);
			arrowImageView.clearAnimation();
			// ����ͼ��
			arrowImageView.setImageResource(R.drawable.ic_pulltorefresh_arrow);
			tipsTextview.setText(R.string.pull_to_refresh_pull_label);
			lastUpdatedTextView.setVisibility(View.VISIBLE);
			break;
		}
	}

	/**
	 * ���ˢ��
	 */
	public void clickRefresh() {
		setSelection(0);
		state = REFRESHING;
		changeHeaderViewByState();
		onRefresh();
	}

	public void setOnRefreshListener(OnRefreshListener refreshListener) {
		this.refreshListener = refreshListener;
	}

	public interface OnRefreshListener {
		public void onRefresh();
	}

	public void onRefreshComplete(String update) {
		lastUpdatedTextView.setText(update);
		onRefreshComplete();
	}

	public void onRefreshComplete() {
		state = DONE;
		changeHeaderViewByState();
	}

	private void onRefresh() {
		if (refreshListener != null) {
			refreshListener.onRefresh();
		}
	}

	/**
	 * ����headView��width��heightֵ
	 * 
	 * @param child
	 */
	private void measureView(View child) {
		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
					MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}

}