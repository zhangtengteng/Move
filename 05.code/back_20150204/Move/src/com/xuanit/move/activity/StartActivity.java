package com.xuanit.move.activity;

import java.io.File;

import com.xuanit.move.R;
import com.xuanit.move.adapter.StartPageAdapter;
import com.xuanit.move.comm.PathComm;
import com.xuanit.move.util.CloseAllActivityManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

public class StartActivity extends FragmentActivity {
	private ViewPager vp_start;
	private StartPageAdapter adapter;
	private SharedPreferences sharedPreferences;
	private Editor editor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		CloseAllActivityManager.getInstance().addActivity(StartActivity.this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		createAppFolder();
		sharedPreferences = getSharedPreferences("APP_INFO", MODE_PRIVATE);
		editor = sharedPreferences.edit();
		boolean isFirst = sharedPreferences.getBoolean("IS_FIRST", true);

		if (!isFirst) {
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), LauncherActivity.class);
			startActivity(intent);
			this.finish();
		}

		editor.clear();
		editor.putBoolean("IS_FIRST", false);
		editor.commit();

		vp_start = (ViewPager) findViewById(R.id.vp_start);
		adapter = new StartPageAdapter(getSupportFragmentManager());
		vp_start.setAdapter(adapter);
		vp_start.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				switch (position) {
				case 0:
					vp_start.setCurrentItem(0, true);
					break;
				case 1:
					vp_start.setCurrentItem(1, true);
					break;
				case 2:
					vp_start.setCurrentItem(2, true);
					break;
				default:
					break;
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void createAppFolder() {
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			File file = new File(PathComm.appFolderPath);
			if (!file.exists()) {
				if (file.mkdir()) {
					new File(PathComm.appFolderPath + File.separator + "Images").mkdir();
				}
			} else {
				new File(PathComm.appFolderPath + File.separator + "Images").mkdir();
			}
		}

	}
}
