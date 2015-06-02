package com.xuanit.move.activity;


import com.ab.bitmap.AbImageDownloader;
import com.xuanit.move.R;
import com.xuanit.move.util.CloseAllActivityManager;
import com.xuanit.move.util.StringUtils;

import android.app.Activity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class ImageDetailActivity extends Activity {
	private ImageView iv_image_detail;
	private ImageView iv_image_close;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		CloseAllActivityManager.getInstance().addActivity(ImageDetailActivity.this);
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_detail);
		
		int diwWidth=getWindowManager().getDefaultDisplay().getHeight();
		
		
		iv_image_detail=(ImageView) findViewById(R.id.iv_image_detail);
		iv_image_close=(ImageView) findViewById(R.id.iv_image_close);
		
		String imageSrc=getIntent().getExtras().getString("ImageSrc");
		
		AbImageDownloader abImageDownloader=new AbImageDownloader(this);
		if(!StringUtils.isNullOrEmpty(imageSrc)){
			abImageDownloader.display(iv_image_detail, imageSrc);
		}
		
		iv_image_close.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				ImageDetailActivity.this.finish();
				overridePendingTransition(R.anim.stay_the_same, R.anim.image_scale_alpha_out);
			}
		});
	}
	
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		ImageDetailActivity.this.finish();
		overridePendingTransition(R.anim.stay_the_same, R.anim.image_scale_alpha_out);
	}


	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
	}
}
