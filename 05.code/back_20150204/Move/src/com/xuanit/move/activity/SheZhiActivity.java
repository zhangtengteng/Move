package com.xuanit.move.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.xuanit.move.R;
import com.xuanit.move.base.BaseActivity;
import com.xuanit.move.util.CloseAllActivityManager;

public class SheZhiActivity extends BaseActivity {
	private LinearLayout ll_tel_connect; // 手机绑定
	private LinearLayout ll_comment_about; // 关于
	private LinearLayout ll_comment_decide; // 用户协议
	private LinearLayout ll_comment_helpandadvise; // 帮助与反馈
	private LinearLayout ll_comment_checknewversion; // 检查新版本
	private LinearLayout ll_comment_exit; // 退出

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		CloseAllActivityManager.getInstance().addActivity(SheZhiActivity.this);
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setView(R.layout.activity_shezhi);
		setTitle("", "设 置", "");
		initView();
		setListener();
	}
	private void initView() {
		ll_tel_connect = (LinearLayout) findViewById(R.id.ll_tel_connect);
		ll_comment_about = (LinearLayout) findViewById(R.id.ll_comment_about);
		ll_comment_decide = (LinearLayout) findViewById(R.id.ll_comment_decide);
		ll_comment_helpandadvise = (LinearLayout) findViewById(R.id.ll_comment_helpandadvise);
		ll_comment_checknewversion = (LinearLayout) findViewById(R.id.ll_comment_checknewversion);
		ll_comment_exit = (LinearLayout) findViewById(R.id.ll_comment_exit);

	}

	private void setListener() {
		ll_tel_connect.setOnClickListener(this);
		ll_comment_about.setOnClickListener(this);
		ll_comment_decide.setOnClickListener(this);
		ll_comment_helpandadvise.setOnClickListener(this);
		ll_comment_checknewversion.setOnClickListener(this);
		ll_comment_exit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {

		case R.id.ll_comment_exit:
			usedialog(ll_comment_exit);
			break;
		case R.id.ll_comment_decide:
			commentdecide();
			break;
		case R.id.ll_comment_about:
			aboutdongtan();
			break;	
		
		default:
			break;
		}
	}

	//退出
	private void usedialog(final LinearLayout LL) {
		final Dialog dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.popup);
		//dialog.setCancelable(true);
		//dialog.setCanceledOnTouchOutside(true);
		TextView tv_user_exit; // 用户退出
		TextView tv_dongtan_exit; // 动弹退出
		LinearLayout ll_popup;
		tv_user_exit = (TextView) dialog.findViewById(R.id.tv_user_exit);
		tv_dongtan_exit = (TextView) dialog.findViewById(R.id.tv_dongtan_exit);
		ll_popup = (LinearLayout) dialog.findViewById(R.id.ll_popup);
		
		tv_user_exit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				//SheZhiActivity.this.finish();
				CloseAllActivityManager.getInstance().exit();
				startActivity(new Intent(SheZhiActivity.this, LoginRegistActivity.class));
				overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			}
		});
		
		tv_dongtan_exit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CloseAllActivityManager.getInstance().exit();
				dialog.dismiss();
			}
		});
		
		ll_popup.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		dialog.show();
	}

    //用户协议
	private void commentdecide(){
		startActivity(new Intent(SheZhiActivity.this, AgreementActivity.class));
	}
	
    //关于动弹
	private void aboutdongtan(){
		startActivity(new Intent(SheZhiActivity.this, AboutDongtanActivity.class));
	}
}
