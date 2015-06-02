package com.xuanit.move.activity;

import android.os.Bundle;

import com.xuanit.move.R;
import com.xuanit.move.base.BaseActivity;
import com.xuanit.move.util.CloseAllActivityManager;

public class AboutDongtanActivity extends BaseActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		CloseAllActivityManager.getInstance().addActivity(AboutDongtanActivity.this);
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setView(R.layout.activity_about_dongtan);
		setTitle("", "关于动弹一下", "");
	}
}
