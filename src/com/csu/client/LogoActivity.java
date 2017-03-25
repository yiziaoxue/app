package com.csu.client;

import java.util.Random;

import com.csu.service.MainService;
import com.example.client.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

public class LogoActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȡ�����⣬Ҫж��setContentView��ǰ��
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_logo);
		
		//ȡ��״̬��
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		ImageView iv=(ImageView)this.findViewById(R.id.logoimage);		
		Random random = new Random();
		switch(random.nextInt(3)){
		case 1:
			iv.setImageResource(R.drawable.kobe);
			break;
		case 2:
			iv.setImageResource(R.drawable.iverson);
			break;
		case 3:
			iv.setImageResource(R.drawable.lin);
			break;
		};
		//��������
		AlphaAnimation aa=new AlphaAnimation(0.1f,1f);
		aa.setDuration(4000);
		iv.startAnimation(aa);
		aa.setAnimationListener(new AnimationListener(){//��������������

			public void onAnimationEnd(Animation arg0) {
				Log.i("Mytest", "����service");
				Intent intent = new Intent(LogoActivity.this,MainService.class);
				startService(intent);
				Intent it=new Intent(LogoActivity.this,LoginActivity.class);
				startActivity(it);
				finish();
			}

			public void onAnimationRepeat(Animation arg0) {
				
			}

			public void onAnimationStart(Animation arg0) {
				
			}
			
		});
		
		
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.logo, menu);
		return true;
	}
}
