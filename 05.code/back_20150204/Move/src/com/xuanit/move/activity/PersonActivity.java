package com.xuanit.move.activity;

import io.rong.imkit.RongIM;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.baidu.location.LocationClient;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.james.asynhttp.AsynHttpClient;
import com.james.asynhttp.HttpNetWorkDataHandler;
import com.xuanit.move.R;
import com.xuanit.move.adapter.PersonAdapter;
import com.xuanit.move.app.AppConfig;
import com.xuanit.move.app.MoveApplication;
import com.xuanit.move.base.BaseActivity;
import com.xuanit.move.model.AddPersonInfo;
import com.xuanit.move.util.CloseAllActivityManager;
import com.xuanit.move.util.StringUtils;
import com.xuanit.move.view.CustomProgressDialog;

public class PersonActivity extends BaseActivity {

	private PullToRefreshListView refreshListView_person;
	private Button bt_person_round;
	private Button bt_person_classmate;
	private Button bt_person_schoolmate;
	private Button bt_person_introduce;
	private int flag = 0;
	private final List<AddPersonInfo> personList = new ArrayList<AddPersonInfo>();
	private boolean isLoadOver = false;
	private int PageNo = 1;
	private final int PageSize = 10;
	private int count;
	public double latitude;// 纬度
	public double lontitude;// 经度
	public String userid;// 用户名
	public String data;
	private PersonAdapter personAdapter;
	private CustomProgressDialog customProgressDialog;
	private LocationClient mLocationClient;
	private MoveApplication application;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		CloseAllActivityManager.getInstance().addActivity(PersonActivity.this);
		super.onCreate(savedInstanceState);
		application = (MoveApplication) getApplication();
		setView(R.layout.activity_persons);
		setTitle("", "人 物", "");
		initview();
		personAdapter = new PersonAdapter(this, personList);
		refreshListView_person.setAdapter(personAdapter);

		customProgressDialog.show();
		loadData(flag);
	}

	private void initview() {
		customProgressDialog = CustomProgressDialog.createDialog(this);
		bt_person_round = (Button) findViewById(R.id.bt_person_round);
		bt_person_classmate = (Button) findViewById(R.id.bt_person_classmate);
		bt_person_schoolmate = (Button) findViewById(R.id.bt_person_schoolmate);
		bt_person_introduce = (Button) findViewById(R.id.bt_person_introduce);

		bt_person_round.setOnClickListener(this);
		bt_person_classmate.setOnClickListener(this);
		bt_person_schoolmate.setOnClickListener(this);
		bt_person_introduce.setOnClickListener(this);

		// 处理Footer
		LinearLayout footer_dongtan = (LinearLayout) findViewById(R.id.footer_ll_dongtan);
		LinearLayout footer_huodong = (LinearLayout) findViewById(R.id.footer_ll_huodong);
		LinearLayout footer_renwu = (LinearLayout) findViewById(R.id.footer_ll_renwu);
		LinearLayout footer_tanlun = (LinearLayout) findViewById(R.id.footer_ll_tanlun);

		footer_renwu.setClickable(false);
		footer_dongtan.setOnClickListener(this);
		footer_huodong.setOnClickListener(this);
		footer_renwu.setOnClickListener(this);
		footer_tanlun.setOnClickListener(this);

		userid = application.appConfig.USER_ID;

		refreshListView_person = (PullToRefreshListView) findViewById(R.id.pulltorefrshListView_person);
		refreshListView_person.setMode(com.handmark.pulltorefresh.library.PullToRefreshBase.Mode.PULL_FROM_END);
		refreshListView_person.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				if (!isLoadOver) {
					PageNo++;
				}
				loadData(flag);
			}
		});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}

	/*
	 * private void getLoactionInfo() {
	 * 
	 * if (mLocationClient != null && mLocationClient.isStarted()) {
	 * mLocationClient.requestLocation(); } else { mLocationClient =
	 * LoactionClientUtil.getLoactionClient(this); mLocationClient.start();
	 * mLocationClient.requestLocation(); } }
	 */

	private void loadData(final int flag) {

		if (flag == 3) {
			// getLoactionInfo();
			lontitude = application.latitude;
			latitude = application.lontitude;
			if ((lontitude == 0 && latitude == 0)) {
				Toast.makeText(this, "位置获取失败,请重试", Toast.LENGTH_SHORT).show();
				if (customProgressDialog != null && customProgressDialog.isShowing()) {
					customProgressDialog.dismiss();
				}
				return;
			}
			if (mLocationClient != null && mLocationClient.isStarted()) {
				mLocationClient.stop();
			}
		}

		JSONObject o = new JSONObject();
		try {
			if (flag == 3) {
				o.put("PageNo", PageNo + "");
				o.put("PageSize", PageSize + "");
				o.put("Longitude", lontitude);
				o.put("Latitude", latitude);
				o.put("UserId", userid);

			} else {
				o.put("PageNo", PageNo + "");
				o.put("PageSize", PageSize + "");
				o.put("UserId", userid);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		data = o.toString();

		String strlink = "";

		if (0 == flag) {
			strlink = "PhoneUser/GetReferreList";
		} else if (1 == flag) {
			strlink = "PhoneUser/GetSameUser";
		} else if (2 == flag) {
			strlink = "PhoneUser/GetSchoolList";
		} else if (3 == flag) {
			strlink = "PhoneUser/GetPeriphery";
		}
		new AsynHttpClient().post(AppConfig.HOSTURL + strlink, "data=" + data, new HttpNetWorkDataHandler() {
			@Override
			public void success(int statusCode, Object obj) {
				if (customProgressDialog != null && customProgressDialog.isShowing()) {
					customProgressDialog.dismiss();
				}
				String result = obj.toString();
				Log.i("PersonActivity", "personData==" + result);
				if (!StringUtils.isNullOrEmpty(result)) {
					try {
						JSONObject object = new JSONObject(result);
						String Code = object.getString("Code");
						if (!StringUtils.isNullOrEmpty("Code") && "1".equals(Code)) {
							JSONObject jsonObject = object.getJSONObject("Data");
							String Item2 = jsonObject.getString("Item2");
							count = Integer.parseInt(Item2);

							if (!isLoadOver) {
								JSONArray jsonArray = jsonObject.getJSONArray("Item1");
								for (int i = 0; i < jsonArray.length(); i++) {
									JSONObject json = new JSONObject(jsonArray.getString(i));
									AddPersonInfo addPersonInfo = new AddPersonInfo();
									addPersonInfo.UserId = json.getString("UserId");
									addPersonInfo.UserName = json.getString("UserName");
									addPersonInfo.Head = json.getString("Head");
									addPersonInfo.SchoolName = json.getString("SchoolName");
									addPersonInfo.Description = json.getString("Description");
									addPersonInfo.Distance = json.getString("Distance");

									personList.add(addPersonInfo);
								}
							}

							if (PageNo == 1 && count == 0) {
								Log.i("PersonActivity", flag + "木有数据");
								// Toast.makeText(PersonActivity.this,
								// flag+"木有数据", 1).show();
							}

							if ((PageNo * PageSize) >= count && count != 0) {
								Log.i("PersonActivity", flag + "已经全部加载");
								// Toast.makeText(PersonActivity.this, "已经全部加载",
								// 1).show();
								isLoadOver = true;
							}

							refreshListView_person.onRefreshComplete();
							personAdapter.notifyDataSetChanged();

						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Log.i("PersonActivity", "JSON解析出错了");
						refreshListView_person.onRefreshComplete();
					}

				} else {
					refreshListView_person.setVisibility(View.GONE);
					Toast.makeText(PersonActivity.this, "网络错误！", Toast.LENGTH_SHORT).show();
					Log.i("PersonActivity", flag + "网络错误！");
					refreshListView_person.onRefreshComplete();
				}
			}

			@Override
			public void failure(int statusCode, Object obj) {
				if (customProgressDialog != null && customProgressDialog.isShowing()) {
					customProgressDialog.dismiss();
				}

				refreshListView_person.onRefreshComplete();
				Toast.makeText(PersonActivity.this, "访问服务器失败，请稍后再试", Toast.LENGTH_SHORT).show();
				Log.i("PersonActivity", "personData==" + "访问服务器失败，请稍后再试" + "statusCode" + statusCode);
			}
		});
	}

	public void onClick(View v) {
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
		case R.id.bt_person_introduce:
			flag = 0;
			PageNo = 1;
			isLoadOver = false;
			checkButton(flag);
			personList.clear();
			// personAdapter.notifyDataSetChanged();
			customProgressDialog.show();
			loadData(flag);
			break;

		case R.id.bt_person_classmate:
			flag = 1;
			PageNo = 1;
			isLoadOver = false;
			checkButton(flag);
			personList.clear();
			// personAdapter.notifyDataSetChanged();
			customProgressDialog.show();
			loadData(flag);
			break;
		case R.id.bt_person_schoolmate:
			flag = 2;
			PageNo = 1;
			isLoadOver = false;
			checkButton(flag);
			personList.clear();
			// personAdapter.notifyDataSetChanged();
			customProgressDialog.show();
			loadData(flag);
			break;
		case R.id.bt_person_round:
			flag = 3;
			PageNo = 1;
			isLoadOver = false;
			checkButton(flag);
			personList.clear();
			// personAdapter.notifyDataSetChanged();
			customProgressDialog.show();
			loadData(flag);
			break;
		case R.id.footer_ll_dongtan:
			/*
			 * footer_iv_dongtan.setImageResource(R.drawable.ic_main_dontan);
			 * footer_iv_huodong
			 * .setImageResource(R.drawable.ic_main_acticity_1);
			 * footer_iv_renwu.setImageResource(R.drawable.ic_detail_people_1);
			 * footer_iv_tanlun.setImageResource(R.drawable.ic_main_chat_1);
			 */
			startActivity(new Intent(PersonActivity.this, MainActivity.class));
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			break;
		case R.id.footer_ll_huodong:
			/*
			 * footer_iv_dongtan.setImageResource(R.drawable.ic_main_dontan_1);
			 * footer_iv_huodong.setImageResource(R.drawable.ic_main_acticity);
			 * footer_iv_renwu.setImageResource(R.drawable.ic_detail_people_1);
			 * footer_iv_tanlun.setImageResource(R.drawable.ic_main_chat_1);
			 */
			startActivity(new Intent(PersonActivity.this, HuoDongActivity.class));
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			break;
		case R.id.footer_ll_renwu:
			/*
			 * footer_iv_dongtan.setImageResource(R.drawable.ic_main_dontan_1);
			 * footer_iv_huodong
			 * .setImageResource(R.drawable.ic_main_acticity_1);
			 * footer_iv_renwu.setImageResource(R.drawable.ic_detail_people);
			 * footer_iv_tanlun.setImageResource(R.drawable.ic_main_chat_1);
			 */
			break;
		case R.id.footer_ll_tanlun:
			RongIM.getInstance().startConversationList(PersonActivity.this);
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			break;

		default:
			break;
		}
	}

	private void checkButton(int flag) {
		bt_person_round.setBackgroundResource(R.drawable.bt_right_shape);
		bt_person_classmate.setBackgroundResource(R.drawable.bt_center_shape);
		bt_person_schoolmate.setBackgroundResource(R.drawable.bt_center_shape);
		bt_person_introduce.setBackgroundResource(R.drawable.bt_left_click_shape);
		switch (flag) {
		case 0:
			bt_person_introduce.setBackgroundResource(R.drawable.bt_left_shape);

			break;
		case 1:
			bt_person_classmate.setBackgroundResource(R.drawable.bt_center_click_shape);
			break;
		case 2:
			bt_person_schoolmate.setBackgroundResource(R.drawable.bt_center_click_shape);
			break;
		case 3:
			bt_person_round.setBackgroundResource(R.drawable.bt_right_click_shape);
			break;
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (mLocationClient != null && mLocationClient.isStarted()) {
			mLocationClient.stop();
		}
	}

}
