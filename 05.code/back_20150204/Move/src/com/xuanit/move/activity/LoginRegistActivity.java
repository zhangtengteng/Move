package com.xuanit.move.activity;

import com.xuanit.move.R;
import com.xuanit.move.adapter.RegistLoginPagerAdapter;
import com.xuanit.move.util.CloseAllActivityManager;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class LoginRegistActivity extends FragmentActivity implements OnPageChangeListener, OnClickListener {
	private TextView registCursor, loginCursor;

	private ViewPager pager;
	private RegistLoginPagerAdapter pagerAdapter;
	private TextView login;
	private TextView regist;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		CloseAllActivityManager.getInstance().addActivity(LoginRegistActivity.this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_regist_login);
		init();
		pagerAdapter = new RegistLoginPagerAdapter(getSupportFragmentManager());
		pager.setAdapter(pagerAdapter);
		pager.setOnPageChangeListener(this);
		login.setOnClickListener(this);
		regist.setOnClickListener(this);

	}

	public void init() {
		registCursor = (TextView) findViewById(R.id.text_regist_cursor);
		loginCursor = (TextView) findViewById(R.id.text_login_cursor);
		pager = (ViewPager) findViewById(R.id.loginregist_viewpager);
		login = (TextView) findViewById(R.id.regist_login_login);
		regist = (TextView) findViewById(R.id.regist_login_regist);

	}

	@Override
	public void onPageSelected(int position) {
		// TODO Auto-generated method stub
		switch (position) {
		case 0:
			registCursor.setBackgroundColor(Color.GRAY);
			loginCursor.setBackgroundColor(Color.rgb(00, 0X77, 0X99));
			break;

		case 1:
			registCursor.setBackgroundColor(Color.rgb(00, 0X77, 0X99));
			loginCursor.setBackgroundColor(Color.GRAY);
			break;
		default:
			break;
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {

		case R.id.regist_login_login:
			pager.setCurrentItem(0, true);
			break;
		case R.id.regist_login_regist:
			pager.setCurrentItem(1, true);
			break;

		}

	}
	private long exitTime = 0;
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				CloseAllActivityManager.getInstance().exit();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
}
