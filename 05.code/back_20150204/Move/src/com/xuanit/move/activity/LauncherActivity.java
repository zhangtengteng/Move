package com.xuanit.move.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import com.xuanit.move.R;
import com.xuanit.move.util.CloseAllActivityManager;

public class LauncherActivity extends Activity {
	private RelativeLayout relative_launcher;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		CloseAllActivityManager.getInstance().addActivity(LauncherActivity.this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.launcher);
		init();
	}

	public void init() {
		relative_launcher = (RelativeLayout) findViewById(R.id.relative_launcher);
		Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alphation);
		relative_launcher.startAnimation(animation);
		handler.postDelayed(toMain, 2500);// 2.5秒进入主界面
	}

	/**
	 * 转入不通的界面
	 */
	protected void Redictor() {
		Intent intent = new Intent();
		intent.setClass(this, LoginRegistActivity.class);
		startActivity(intent);
		this.finish();
	}

	Handler handler = new Handler();

	Runnable toMain = new Runnable() {
		@Override
		public void run() {
			Redictor();
		}
	};

}
