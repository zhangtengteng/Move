package com.xuanit.move.activity;

import android.os.Bundle;

import com.xuanit.move.R;
import com.xuanit.move.base.BaseActivity;
import com.xuanit.move.util.CloseAllActivityManager;

public class AgreementActivity extends BaseActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		CloseAllActivityManager.getInstance().addActivity(AgreementActivity.this);
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setView(R.layout.activity_agreement_comment);
		setTitle("", "用户协议", "");
	}
}
