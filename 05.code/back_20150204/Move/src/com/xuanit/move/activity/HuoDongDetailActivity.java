package com.xuanit.move.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.ab.bitmap.AbImageDownloader;
import com.xuanit.move.R;
import com.xuanit.move.app.AppConfig;
import com.xuanit.move.base.BaseActivity;
import com.xuanit.move.model.HuoDongInfo;
import com.xuanit.move.util.CloseAllActivityManager;
import com.xuanit.move.util.StringUtils;

public class HuoDongDetailActivity extends BaseActivity {
	private TextView tv_huodong_title;
	private TextView tv_huodong_address;
	private TextView tv_huodong_renshu;
	private TextView tv_huodong_feiyong;
	private TextView tv_start_date;
	private TextView tv_end_date;
	private TextView tv_huodong_liucheng;
	private ImageView iv_huodong_pic;
	private HuoDongInfo currentHuoDong;// 当前活动信息
	private AbImageDownloader abImageDownloader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		CloseAllActivityManager.getInstance().addActivity(HuoDongDetailActivity.this);
		super.onCreate(savedInstanceState);
		setView(R.layout.activity_huodong_detail);
		setTitle("", "活动详细", "");
		initView();
		setListener();
		

	}

	private void initView() {
		tv_huodong_title = (TextView) findViewById(R.id.tv_huodong_title);
		tv_huodong_address = (TextView) findViewById(R.id.tv_huodong_address);
		tv_huodong_renshu = (TextView) findViewById(R.id.tv_huodong_renshu);
		tv_huodong_feiyong = (TextView) findViewById(R.id.tv_huodong_feiyong);
		tv_start_date = (TextView) findViewById(R.id.tv_start_date);
		tv_end_date = (TextView) findViewById(R.id.tv_end_date);
		tv_huodong_liucheng = (TextView) findViewById(R.id.tv_huodong_liucheng);
		iv_huodong_pic = (ImageView) findViewById(R.id.iv_huodong_pic);

		currentHuoDong = (HuoDongInfo) getIntent().getExtras().get("HuoDongInfo");
		if (currentHuoDong != null) {
			tv_huodong_title.setText(currentHuoDong.Title);
			tv_huodong_address.setText(currentHuoDong.ActivityLocate);
			tv_huodong_renshu.setText(currentHuoDong.CapPeople);
			tv_huodong_feiyong.setText(currentHuoDong.ActivityPrice);
			tv_start_date.setText(currentHuoDong.ApplyTime);
			tv_end_date.setText(currentHuoDong.EndTime);
			tv_huodong_liucheng.setText(currentHuoDong.Detail);
			
			// 设置活动图片
			if (!StringUtils.isNullOrEmpty(currentHuoDong.PreviewImg)) {
				abImageDownloader = new AbImageDownloader(this);
				if (currentHuoDong.PreviewImg.startsWith("~")) {
					String replace = currentHuoDong.PreviewImg.replace("~", AppConfig.HOSTURL);
					abImageDownloader.display(iv_huodong_pic, replace);
				} else {
					abImageDownloader.display(iv_huodong_pic, currentHuoDong.PreviewImg);
				}
			}
			
		}


	}
	private void setListener(){
		iv_huodong_pic.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(HuoDongDetailActivity.this, ImageDetailActivity.class);
				String imageSrc = "";
				if (!StringUtils.isNullOrEmpty(currentHuoDong.PreviewImg)) {
					if (currentHuoDong.PreviewImg.startsWith("~")) {
						imageSrc = currentHuoDong.PreviewImg.replace("~", AppConfig.HOSTURL);

					} else {
						imageSrc = currentHuoDong.PreviewImg;
					}

				}
				intent.putExtra("ImageSrc", imageSrc);
				intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
				startActivity(intent);
				overridePendingTransition(R.anim.image_scale_alpha_in, R.anim.stay_the_same);
			}
		});
	}
}
