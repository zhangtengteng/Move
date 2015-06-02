package com.xuanit.move.activity;

import io.rong.imkit.RongIM;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.james.asynhttp.AsynHttpClient;
import com.james.asynhttp.HttpNetWorkDataHandler;
import com.xuanit.move.R;
import com.xuanit.move.adapter.HuoDongAdapter;
import com.xuanit.move.app.AppConfig;
import com.xuanit.move.app.MoveApplication;
import com.xuanit.move.base.BaseActivity;
import com.xuanit.move.model.HuoDongInfo;
import com.xuanit.move.model.ResultInfo;
import com.xuanit.move.util.CloseAllActivityManager;
import com.xuanit.move.util.StringUtils;
import com.xuanit.move.view.CustomProgressDialog;

public class HuoDongActivity extends BaseActivity {

	private String UserId = "";
	private int ActType = 1;
	private int PageSize = 2;
	private int PageNo = 1;
	private int count;
	private boolean isLoadOver = false;
	private Button btn_guanfang;
	private Button btn_geren;
	private PullToRefreshListView pulltorefreshlistview_huodong;
	private int flag = 0;
	private List<HuoDongInfo> list_huodong = new ArrayList<HuoDongInfo>();
	private HuoDongAdapter huoDongAdapter;
	private MoveApplication application;

	private CustomProgressDialog customProgressDialog;
	Handler mHandler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		CloseAllActivityManager.getInstance().addActivity(HuoDongActivity.this);
		super.onCreate(savedInstanceState);
		application = (MoveApplication) getApplication();
		setView(R.layout.activity_huodong);
		setTitle("", "活动中心", "添加");

		if (!StringUtils.isNullOrEmpty(application.appConfig.USER_ID)) {
			UserId = application.appConfig.USER_ID;
		}

		initView();

		huoDongAdapter = new HuoDongAdapter(this, list_huodong,1);
		pulltorefreshlistview_huodong.setAdapter(huoDongAdapter);

	}

	@Override
	protected void onResume() {
		super.onResume();
		customProgressDialog.show();
		PageNo = 1;
		list_huodong.clear();
		isLoadOver = false;
		loadData();
	}

	private void initView() {
		btn_guanfang = (Button) findViewById(R.id.btn_guanfang);
		btn_geren = (Button) findViewById(R.id.btn_geren);

		customProgressDialog = CustomProgressDialog.createDialog(this);
		pulltorefreshlistview_huodong = (PullToRefreshListView) findViewById(R.id.pulltorefreshlistview_huodong);
		pulltorefreshlistview_huodong.setMode(com.handmark.pulltorefresh.library.PullToRefreshBase.Mode.PULL_FROM_END);
		pulltorefreshlistview_huodong.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				if (!isLoadOver) {
					PageNo++;
				}
				loadData();
			}
		});

		btn_guanfang.setOnClickListener(this);
		btn_geren.setOnClickListener(this);

		// 处理Footer
		// LinearLayout footer_dongtan = (LinearLayout)
		// findViewById(R.id.footer_ll_dongtan);
		// LinearLayout footer_huodong = (LinearLayout)
		// findViewById(R.id.footer_ll_huodong);
		// LinearLayout footer_renwu = (LinearLayout)
		// findViewById(R.id.footer_ll_renwu);
		// LinearLayout footer_tanlun = (LinearLayout)
		// findViewById(R.id.footer_ll_tanlun);
		//
		// footer_dongtan.setOnClickListener(this);
		// footer_huodong.setOnClickListener(this);
		// footer_renwu.setOnClickListener(this);
		// footer_tanlun.setOnClickListener(this);
	}

	private void loadData() {
		new AsynHttpClient().post(AppConfig.HOSTURL + "PhoneActivity/GetByActType", "data=" + getJson(),
				new HttpNetWorkDataHandler() {

					@Override
					public void success(int statusCode, Object obj) {
						// TODO Auto-generated method stub
						if (customProgressDialog != null && customProgressDialog.isShowing()) {
							customProgressDialog.dismiss();
						}

						Log.i("HuoDongActivity", "huodongData" + obj.toString());
						ResultInfo<HuoDongInfo> resultInfo = parseJson(obj.toString());
						if (resultInfo != null) {
							ArrayList<HuoDongInfo> list = resultInfo.getList();
							if (PageNo == 1 && count == 0) {
								// Toast.makeText(HuoDongActivity.this, "木有数据",
								// 1).show();
							}
							if (!isLoadOver) {
								list_huodong.addAll(list);
							}
							if ((PageNo * PageSize) >= count && count != 0) {
								// Toast.makeText(HuoDongActivity.this,
								// "已经全部加载", 1).show();
								isLoadOver = true;
							}
							huoDongAdapter.notifyDataSetChanged();
							pulltorefreshlistview_huodong.onRefreshComplete();
						}
					}

					@Override
					public void failure(int statusCode, Object obj) {
						if (customProgressDialog != null && customProgressDialog.isShowing()) {
							customProgressDialog.dismiss();
						}

						pulltorefreshlistview_huodong.onRefreshComplete();
						Toast.makeText(HuoDongActivity.this, "网络错误", Toast.LENGTH_SHORT).show();

					}
				});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		/*
		 * ImageView footer_iv_dongtan = (ImageView)
		 * findViewById(R.id.footer_iv_dongtan); ImageView footer_iv_huodong =
		 * (ImageView) findViewById(R.id.footer_iv_huodong); ImageView
		 * footer_iv_renwu = (ImageView) findViewById(R.id.footer_iv_renwu);
		 * ImageView footer_iv_tanlun = (ImageView)
		 * findViewById(R.id.footer_iv_tanlun);
		 */
		switch (v.getId()) {
		case R.id.ll_header_right:
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), HuoDongApplyActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_guanfang:
			flag = 0;
			ActType = 1;
			checkButton(flag);
			list_huodong.clear();
			PageNo = 1;
			isLoadOver = false;
			customProgressDialog.show();
			loadData();
			break;
		case R.id.btn_geren:
			flag = 1;
			ActType = 2;
			checkButton(flag);
			list_huodong.clear();
			PageNo = 1;
			isLoadOver = false;
			customProgressDialog.show();
			loadData();
			break;
		case R.id.footer_ll_dongtan:
			/*
			 * footer_iv_dongtan.setImageResource(R.drawable.ic_main_dontan);
			 * footer_iv_huodong
			 * .setImageResource(R.drawable.ic_main_acticity_1);
			 * footer_iv_renwu.setImageResource(R.drawable.ic_detail_people_1);
			 * footer_iv_tanlun.setImageResource(R.drawable.ic_main_chat_1);
			 */
			HuoDongActivity.this.finish();
			// startActivity(new Intent(HuoDongActivity.this,
			// MainActivity.class));
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

			break;
		case R.id.footer_ll_huodong:
			/*
			 * footer_iv_dongtan.setImageResource(R.drawable.ic_main_dontan_1);
			 * footer_iv_huodong.setImageResource(R.drawable.ic_main_acticity);
			 * footer_iv_renwu.setImageResource(R.drawable.ic_detail_people_1);
			 * footer_iv_tanlun.setImageResource(R.drawable.ic_main_chat_1);
			 */
			startActivity(new Intent(HuoDongActivity.this, HuoDongActivity.class));
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			HuoDongActivity.this.finish();
			break;
		case R.id.footer_ll_renwu:
			/*
			 * footer_iv_dongtan.setImageResource(R.drawable.ic_main_dontan_1);
			 * footer_iv_huodong
			 * .setImageResource(R.drawable.ic_main_acticity_1);
			 * footer_iv_renwu.setImageResource(R.drawable.ic_detail_people);
			 * footer_iv_tanlun.setImageResource(R.drawable.ic_main_chat_1);
			 */
			startActivity(new Intent(HuoDongActivity.this, PersonActivity.class));
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			HuoDongActivity.this.finish();
			break;
		case R.id.footer_ll_tanlun:

			RongIM.getInstance().startConversationList(HuoDongActivity.this);
			/*
			 * mHandler.post(new Runnable() {
			 * 
			 * @Override public void run() {
			 * RongIM.getInstance().startConversationList(HuoDongActivity.this);
			 * } });
			 */

			// startActivity(new Intent(HuoDongActivity.this,
			// CommuncationActivity.class));
			// Toast.makeText(HuoDongActivity.this, "敬请期待！",
			// Toast.LENGTH_LONG).show();
			HuoDongActivity.this.finish();
			break;

		default:
			break;
		}
	}

	private void checkButton(int flag) {
		// TODO Auto-generated method stub
		btn_guanfang.setBackgroundResource(R.drawable.bt_left_shape);
		btn_guanfang.setTextColor(getResources().getColor(R.color.white));
		btn_geren.setBackgroundResource(R.drawable.bt_right_shape);
		btn_geren.setTextColor(getResources().getColor(R.color.white));

		switch (flag) {
		case 0:
			btn_guanfang.setBackgroundResource(R.drawable.bt_left_click_shape);
			btn_guanfang.setTextColor(getResources().getColor(R.color.baseColor));
			break;
		case 1:
			btn_geren.setBackgroundResource(R.drawable.bt_right_click_shape);
			btn_geren.setTextColor(getResources().getColor(R.color.baseColor));
			break;
		default:
			break;
		}
	}

	private String getJson() {

		JSONObject o = new JSONObject();
		try {
			o.put("PageNo", PageNo + "");
			o.put("PageSize", PageSize + "");
			o.put("ActType", ActType + "");
			if (!StringUtils.isNullOrEmpty(UserId)) {
				o.put("UserId", UserId);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return o.toString();

	}

	private ResultInfo<HuoDongInfo> parseJson(String jsonStr) {
		if (!StringUtils.isNullOrEmpty(jsonStr)) {

			ResultInfo<HuoDongInfo> resultInfo = new ResultInfo<HuoDongInfo>();
			try {
				JSONObject jsonObject = new JSONObject(jsonStr);
				resultInfo.Code = jsonObject.getString("Code");
				JSONObject jsonObject2 = jsonObject.getJSONObject("Data");
				resultInfo.Item2 = jsonObject2.getString("Item2");
				count = Integer.parseInt(resultInfo.Item2);
				JSONArray jsonArray = jsonObject2.getJSONArray("Item1");

				ArrayList<HuoDongInfo> list = resultInfo.getList();
				for (int i = 0; i < jsonArray.length(); i++) {
					HuoDongInfo info = new HuoDongInfo();
					JSONObject object = (JSONObject) jsonArray.get(i);
					info.ActId = object.getString("ActId");
					info.UserId = object.getString("UserId");
					info.Title = object.getString("Title");
					info.CapPeople = object.getString("CapPeople");
					info.ActivityLocate = object.getString("ActivityLocate");
					info.ActType = object.getString("ActType");
					info.Detail = object.getString("Detail");
					info.PreviewImg = object.getString("PreviewImg");
					info.ApplyTime = object.getString("ApplyTime");
					info.EndTime = object.getString("EndTime");
					info.ParticipateCount = object.getString("ParticipateCount");
					info.ActivityPrice = object.getString("ActivityPrice");
					list.add(info);
				}
				return resultInfo;

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
}