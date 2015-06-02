package com.xuanit.move.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.bitmap.AbImageDownloader;
import com.james.asynhttp.AsynHttpClient;
import com.james.asynhttp.HttpNetWorkDataHandler;
import com.xuanit.move.R;
import com.xuanit.move.adapter.HuoDongApplyUserAdapter;
import com.xuanit.move.app.AppConfig;
import com.xuanit.move.app.MoveApplication;
import com.xuanit.move.base.BaseActivity;
import com.xuanit.move.model.ApplyUserInfo;
import com.xuanit.move.model.HuoDongInfo;
import com.xuanit.move.model.ResultInfo;
import com.xuanit.move.util.CloseAllActivityManager;
import com.xuanit.move.util.StringUtils;
import com.xuanit.move.view.CustomProgressDialog;

public class HuoDongBaomingActivity extends BaseActivity implements OnClickListener {
	private String userId;
	private String huoDongId;

	private ImageView iv_houdong_pic;
	private TextView tv_houdong_title;
	private TextView tv_canjia_count;
	private ImageView iv_houdong_pic_up;
	private ImageView iv_houdong_pic_view;

	private ListView lv_houdong_peoples;
	private Button tv_houdong_baoming;

	private boolean isApply;
	private HuoDongInfo currentHuoDong;// 当前活动信息
	private CustomProgressDialog customProgressDialog;
	private List<ApplyUserInfo> usersList = new ArrayList<ApplyUserInfo>();
	private HuoDongApplyUserAdapter adapter;
	private AbImageDownloader mAbImageDownloader;
	private MoveApplication application;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		CloseAllActivityManager.getInstance().addActivity(HuoDongBaomingActivity.this);
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		application = (MoveApplication) getApplication();
		setView(R.layout.activity_houdong_baoming);
		setTitle("", "活动报名", "");

		if (!StringUtils.isNullOrEmpty(application.appConfig.USER_ID)) {
			userId = application.appConfig.USER_ID;
		}

		initView();
		huoDongId = (String) getIntent().getExtras().get("huoDongId");
		currentHuoDong = new HuoDongInfo();
		if (usersList != null) {
			adapter = new HuoDongApplyUserAdapter(HuoDongBaomingActivity.this, usersList);
			lv_houdong_peoples.setAdapter(adapter);
			loadData();
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.tv_houdong_baoming:
			if (!isApply) {
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), HuoDongBaomingDetailActivity.class);
				intent.putExtra("huoDongId", currentHuoDong.ActId);
				startActivityForAnima(intent);
			} else {
				// 取消报名处理
				updateDate();
			}
			break;

		case R.id.iv_huodong_pic_up:
			startActivityForAnima(new Intent(HuoDongBaomingActivity.this, PhotoAddActivity.class));
			break;

		case R.id.iv_huodong_pic_view:
			break;
		default:
			break;
		}
	}

	public void initView() {
		customProgressDialog = CustomProgressDialog.createDialog(this);

		View headView = getLayoutInflater().inflate(R.layout.activity_houdong_baoming_head, null);
		
		tv_houdong_title = (TextView) headView.findViewById(R.id.tv_houdong_title);
		tv_canjia_count = (TextView) headView.findViewById(R.id.tv_canjia_count);
		iv_houdong_pic = (ImageView) headView.findViewById(R.id.iv_houdong_pic);
		iv_houdong_pic_up = (ImageView) headView.findViewById(R.id.iv_huodong_pic_up);
		iv_houdong_pic_view = (ImageView) headView.findViewById(R.id.iv_huodong_pic_view);
		
		lv_houdong_peoples = (ListView) findViewById(R.id.lv_houdong_peoples);
		tv_houdong_baoming = (Button) findViewById(R.id.tv_houdong_baoming);
		
		lv_houdong_peoples.addHeaderView(headView);

		iv_houdong_pic_up.setOnClickListener(this);
		iv_houdong_pic_view.setOnClickListener(this);
		tv_houdong_baoming.setOnClickListener(this);
	}

	private void loadData() {
		customProgressDialog.show();
		new AsynHttpClient().get(AppConfig.HOSTURL + "PhoneActivity/GetByActId?ActId=" + huoDongId + "&UserId="
				+ userId, new HttpNetWorkDataHandler() {
			@Override
			public void success(int statusCode, Object obj) {
				System.out.println("=====" + obj.toString());
				customProgressDialog.dismiss();

				if (!StringUtils.isNullOrEmpty(obj.toString())) {
					ResultInfo<HuoDongInfo> resultInfo = new ResultInfo<HuoDongInfo>();
					try {
						JSONObject jsonObject = new JSONObject(obj.toString());
						resultInfo.Code = jsonObject.getString("Code");
						resultInfo.Item2 = jsonObject.getString("Data");
						JSONObject HuoDongJsonObject = new JSONObject(resultInfo.Item2);

						currentHuoDong.ActId = HuoDongJsonObject.getString("ActId");
						currentHuoDong.UserId = HuoDongJsonObject.getString("UserId");
						currentHuoDong.Title = HuoDongJsonObject.getString("Title");
						currentHuoDong.CapPeople = HuoDongJsonObject.getString("CapPeople");
						currentHuoDong.ActivityLocate = HuoDongJsonObject.getString("ActivityLocate");
						currentHuoDong.ActType = HuoDongJsonObject.getString("ActType");
						currentHuoDong.Detail = HuoDongJsonObject.getString("Detail");
						currentHuoDong.PreviewImg = HuoDongJsonObject.getString("PreviewImg");
						currentHuoDong.ApplyTime = HuoDongJsonObject.getString("ApplyTime");
						currentHuoDong.EndTime = HuoDongJsonObject.getString("EndTime");
						currentHuoDong.UserName = HuoDongJsonObject.getString("UserName");
						currentHuoDong.ParticipateCount = HuoDongJsonObject.getString("ParticipateCount");
						currentHuoDong.ActivityPrice = HuoDongJsonObject.getString("ActivityPrice");
						currentHuoDong.IsApply = HuoDongJsonObject.getBoolean("IsApply");
						currentHuoDong.IsShow = HuoDongJsonObject.getBoolean("IsShow");

						initData();
						JSONArray jsonArray = new JSONArray(HuoDongJsonObject.getString("ActivityDetail"));
						usersList.clear();
						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject UserjsonObject = jsonArray.optJSONObject(i);
							ApplyUserInfo info = new ApplyUserInfo();
							info.DetailId = UserjsonObject.getString("DetailId");
							info.UserId = UserjsonObject.getString("UserId");
							info.Detail = UserjsonObject.getString("Detail");
							info.ActId = UserjsonObject.getString("ActId");
							info.UserName = UserjsonObject.getString("UserName");
							info.Head = UserjsonObject.getString("Head");
							usersList.add(info);
						}
						adapter.notifyDataSetChanged();

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			@Override
			public void failure(int statusCode, Object obj) {
				// TODO Auto-generated method stub
				customProgressDialog.dismiss();
				Toast.makeText(HuoDongBaomingActivity.this, "网络错误", 1).show();
			}
		});
	}

	private void updateDate() {
		customProgressDialog.show();
		new AsynHttpClient().get(AppConfig.HOSTURL + "PhoneActivityDetail/Cancel?" + "ActId=" + huoDongId + "&UserId="
				+ userId, new HttpNetWorkDataHandler() {

			@Override
			public void success(int statusCode, Object obj) {
				// TODO Auto-generated method stub
				System.out.println("=====" + obj.toString());
				customProgressDialog.dismiss();
				if (!StringUtils.isNullOrEmpty(obj.toString())) {
					try {
						JSONObject jsonObject = new JSONObject(obj.toString());
						String code = jsonObject.getString("Code");
						String data = jsonObject.getString("Data");
						if ("1".equals(code)) {
							tv_houdong_baoming.setText("我要报名");
							tv_canjia_count.setText(Integer.parseInt(currentHuoDong.ParticipateCount) - 1 + "");
							loadData();
						} else {
							Toast.makeText(HuoDongBaomingActivity.this, data, 1).show();
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			}

			@Override
			public void failure(int statusCode, Object obj) {
				// TODO Auto-generated method stub
				customProgressDialog.dismiss();
				Toast.makeText(HuoDongBaomingActivity.this, "网络错误", 1).show();
			}

		});
	}

	public void initData() {

		if (mAbImageDownloader == null) {
			mAbImageDownloader = new AbImageDownloader(this);
		}

		// 设置活动图片
		if (!StringUtils.isNullOrEmpty(currentHuoDong.PreviewImg)) {
			if (currentHuoDong.PreviewImg.startsWith("~")) {
				String replace = currentHuoDong.PreviewImg.replace("~", AppConfig.HOSTURL);
				mAbImageDownloader.display(iv_houdong_pic, replace);
			} else {
				mAbImageDownloader.display(iv_houdong_pic, currentHuoDong.PreviewImg);
			}
		}
		// 设置活动主题
		if (!StringUtils.isNullOrEmpty(currentHuoDong.Title)) {
			tv_houdong_title.setText(currentHuoDong.Title);
		} else {
			tv_houdong_title.setText("");
		}
		/*
		 * // 设置活动地点 if
		 * (!StringUtils.isNullOrEmpty(currentHuoDong.ActivityLocate)) {
		 * tv_houdong_address.setText(currentHuoDong.ActivityLocate); } else {
		 * tv_houdong_address.setText(""); } // 设置活动开始时间，结束时间 if
		 * (!StringUtils.isNullOrEmpty(currentHuoDong.ApplyTime)) {
		 * tv_houdong_start_time.setText(currentHuoDong.ApplyTime.substring(0,
		 * 10) + " 始"); } else { tv_houdong_start_time.setText(""); } if
		 * (!StringUtils.isNullOrEmpty(currentHuoDong.EndTime)) {
		 * tv_houdong_end_time.setText(currentHuoDong.EndTime.substring(0, 10) +
		 * " 终"); } else { tv_houdong_end_time.setText(""); } // 设置活动组织者 if
		 * (!StringUtils.isNullOrEmpty(currentHuoDong.UserName)) {
		 * tv_houdong_zuzhi.setText(currentHuoDong.UserName); } else {
		 * tv_houdong_zuzhi.setText("不详"); }
		 * 
		 * // 设置活动要求 if
		 * (!StringUtils.isNullOrEmpty(currentHuoDong.ActivityPrice)) {
		 * tv_houdong_yaoqiu.setText("报名费" + currentHuoDong.ActivityPrice +
		 * "元/人"); } else { tv_houdong_yaoqiu.setText("不详"); } // 设置活动参与人数限制 if
		 * (!StringUtils.isNullOrEmpty(currentHuoDong.CapPeople)) {
		 * tv_houdong_renshu.setText(currentHuoDong.CapPeople); } else {
		 * tv_houdong_renshu.setText("不详"); }
		 */
		// 设置已参加活动人数
		if (!StringUtils.isNullOrEmpty(currentHuoDong.ParticipateCount)) {
			tv_canjia_count.setText(currentHuoDong.ParticipateCount);
		} else {
			tv_canjia_count.setText("0");
		}

		// isShow=true 表示活动过期

		// 未过期
		if (!currentHuoDong.IsShow) {
			// 报名按钮
			tv_houdong_baoming.setVisibility(View.VISIBLE);
			// 上传图片按钮
			iv_houdong_pic_up.setVisibility(View.INVISIBLE);
			// 浏览图片按钮
			iv_houdong_pic_view.setVisibility(View.INVISIBLE);

			// 设置已参加活动人的信息列表
			if (currentHuoDong.IsApply) {
				isApply = true;
				tv_houdong_baoming.setText("取消报名");
				// 可以上传图片

			} else {
				isApply = false;
				tv_houdong_baoming.setText("我要报名");
				// 不可以上传图片
			}
			// 过期
		} else {
			// 不可报名
			tv_houdong_baoming.setVisibility(View.INVISIBLE);
			// 浏览图片按钮
			iv_houdong_pic_view.setVisibility(View.VISIBLE);
			// 报名过的人可以上传图片
			if (currentHuoDong.IsApply) {
				// 可以上传图片
				iv_houdong_pic_up.setVisibility(View.VISIBLE);
			} else {
				// 不可以上传图片
				iv_houdong_pic_up.setVisibility(View.INVISIBLE);
			}

		}

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		loadData();
	}

}
