package com.xuanit.move.base;


import com.xuanit.move.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;


public class BaseActivity extends Activity implements OnClickListener{
	public static final int HEAD_LEFT = 0;
	public static final int HEAD_RIGHT = HEAD_LEFT + 1;
	protected BaseLayout baseLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		
	}
	protected void setView(int layoutResId) {
		baseLayout = new BaseLayout(BaseActivity.this, layoutResId);
		setContentView(baseLayout);
		baseLayout.iv_header_left.setOnClickListener(this);
		baseLayout.ll_header_right.setOnClickListener(this);
	}
	
	protected void setTitle(String left,String title,String right){
		baseLayout.setTitle(left, title,right);
	}
	
	protected void handleHeaderEvent(int mode){
		switch (mode) {
		case HEAD_LEFT:
			finish();
			break;
		case HEAD_RIGHT:
			
			break;

		default:
			break;
		}
	}
	protected void clickHeaderBar(View v){
		switch (v.getId()) {
		case R.id.iv_header_left:
			handleHeaderEvent(HEAD_LEFT);
			break;
		case R.id.ll_header_right:
			handleHeaderEvent(HEAD_RIGHT);
			break;

		default:
			break;
		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iv_header_left:
			finish();
			break;
		case R.id.ll_header_right:
			clickHeaderBar(v);
			break;
		default:
			break;
		}
	}
	/**
	 * 启动activity带有动画切换
	 * 
	 * @param intent
	 * @param parentActivity
	 */
	protected void startActivityForAnima(Intent intent, Activity parentActivity) {
		if (intent != null) {
			parentActivity = getParent();
			if (parentActivity != null) {
				parentActivity.startActivity(intent);
				parentActivity.overridePendingTransition(R.anim.push_left_in,
						R.anim.push_left_out);
			} else {
				startActivity(intent);
				overridePendingTransition(R.anim.push_left_in,
						R.anim.push_left_out);
			}
		}
	}
	/**
	 * 启动activity带有动画切换
	 * 
	 * @param intent
	 */
	protected void startActivityForAnima(Intent intent) {
		startActivityForAnima(intent, null);
	}
	
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
		overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}
}
