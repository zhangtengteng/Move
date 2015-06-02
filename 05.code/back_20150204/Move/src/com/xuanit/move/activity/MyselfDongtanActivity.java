package com.xuanit.move.activity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ab.bitmap.AbImageDownloader;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.james.asynhttp.AsynHttpClient;
import com.james.asynhttp.HttpNetWorkDataHandler;
import com.xuanit.move.R;
import com.xuanit.move.adapter.PersonCenterInfoAdaper;
import com.xuanit.move.app.AppConfig;
import com.xuanit.move.app.MoveApplication;
import com.xuanit.move.base.BaseActivity;
import com.xuanit.move.model.AddPersonInfo;
import com.xuanit.move.model.PersonCenterInfo;
import com.xuanit.move.model.ResultInfo;
import com.xuanit.move.util.CloseAllActivityManager;
import com.xuanit.move.util.StringUtils;
import com.xuanit.move.view.CustomProgressDialog;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

public class MyselfDongtanActivity extends BaseActivity {

	private CustomProgressDialog customProgressDialog;
	private String GETPERSONALCENTER = "PhoneNewsFeed/GetPersonalCenter";
	private int PageNo = 1;
	private int PageSize = 20;
	private boolean isLoadOver = false;
	private int count;
	private PullToRefreshListView listView_personCenter;
	private AbImageDownloader mAbImageDownloader;
	private MoveApplication application;
	private String userUserId;
	private AddPersonInfo other;
	private PersonCenterInfoAdaper personCenterInfoAdaper;
	private ArrayList<PersonCenterInfo> listPerson = new ArrayList<PersonCenterInfo>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		CloseAllActivityManager.getInstance().addActivity(MyselfDongtanActivity.this);
		super.onCreate(savedInstanceState);
		setView(R.layout.add_person_detail);

		application = (MoveApplication) getApplication();

		Bundle data = getIntent().getExtras();
		String userName = "";
		if (data != null && data.getInt("FLAG", 0) == 2) {
			other = (AddPersonInfo) data.getSerializable("OTHER_USER_INFO");
			userUserId = other.UserId;
			setTitle("", other.UserName + "的动弹", "");
		} else {
			setTitle("", "我的动弹", "");
			userUserId = application.appConfig.USER_ID;
			other = new AddPersonInfo();
			other.Head = application.appConfig.Head;
			other.UserName = application.appConfig.USER_NAME;
			other.StarLevel = application.appConfig.StarLevel;
			other.Description = application.appConfig.PersonalDescription;
		}

		mAbImageDownloader = new AbImageDownloader(this);
		initView();
		customProgressDialog.show();
		loadUserIdData();

	}

	private String getJson() {
		JSONObject o = new JSONObject();

		try {
			if (!StringUtils.isNullOrEmpty(userUserId)) {
				o.put("PageIndex", PageNo + "");
				o.put("PageSize", PageSize + "");
				o.put("UserId", userUserId);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return o.toString();
	}

	private void initView() {

		if (mAbImageDownloader == null) {
			mAbImageDownloader = new AbImageDownloader(MyselfDongtanActivity.this);
		}
		customProgressDialog = CustomProgressDialog.createDialog(this);
		listView_personCenter = (PullToRefreshListView) findViewById(R.id.pulltorefrshListView_personCenter);
		listView_personCenter.setMode(com.handmark.pulltorefresh.library.PullToRefreshBase.Mode.PULL_FROM_END);
		listView_personCenter.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				if (!isLoadOver) {
					PageNo++;
				}
				loadUserIdData();
			}
		});

		personCenterInfoAdaper = new PersonCenterInfoAdaper(MyselfDongtanActivity.this, listPerson, other);
		listView_personCenter.setAdapter(personCenterInfoAdaper);

	}

	private ResultInfo<PersonCenterInfo> parseMoveJson(String jsonStr) {
		if (!StringUtils.isNullOrEmpty(jsonStr)) {

			ResultInfo<PersonCenterInfo> resultInfo = new ResultInfo<PersonCenterInfo>();
			try {
				JSONObject jsonObject = new JSONObject(jsonStr);
				resultInfo.Code = jsonObject.getString("Code");
				JSONObject jsonObject2 = jsonObject.getJSONObject("Data");
				resultInfo.Item2 = jsonObject2.getString("Item2");
				count = Integer.parseInt(resultInfo.Item2);
				JSONArray jsonArray = jsonObject2.getJSONArray("Item1");

				if ("1".equals(resultInfo.Code)) {
					ArrayList<PersonCenterInfo> list = resultInfo.getList();
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject object = (JSONObject) jsonArray.get(i);
						PersonCenterInfo info = new PersonCenterInfo();

						info.NewsId = object.getString("NewsId");

						info.UserId = object.getString("UserId");
						info.Contents = object.getString("Contents");
						info.ReadCount = object.getString("ReadCount");
						info.ReplyCount = object.getString("ReplyCount");
						info.PublishTime = object.getString("PublishTime");
						info.UserName = object.getString("UserName");
						info.Head = object.getString("Head");
						info.ImgSrc = object.getString("ImgSrc");
						info.NewComment = object.getString("NewComment");
						info.PraiseCount = object.getString("PraiseCount");
						// info.IsPraise = object.getBoolean("IsPraise");
						list.add(info);
					}
					return resultInfo;
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.i("OtherCenterDetailActivity", "JSON转换异常");
			}
		}
		return null;
	}

	private void loadUserIdData() {
		new AsynHttpClient().post(AppConfig.HOSTURL + GETPERSONALCENTER, "data=" + getJson(),
				new HttpNetWorkDataHandler() {

					@Override
					public void success(int statusCode, Object obj) {
						// TODO Auto-generated method stub
						listView_personCenter.onRefreshComplete();

						if (customProgressDialog != null && customProgressDialog.isShowing()) {
							customProgressDialog.dismiss();
						}
						ResultInfo<PersonCenterInfo> resultInfo = parseMoveJson(obj.toString());
						System.out.println("MyselfDongtanActivity" + obj.toString());
						if (resultInfo != null && resultInfo.getList() != null) {

							System.out.println("listPersonlistPersonlistPerson++++" + listPerson);
							if (!isLoadOver) {
								listPerson.addAll(resultInfo.getList());
							}

							if ((PageNo * PageSize) >= count && count != 0) {
								isLoadOver = true;
							}
							personCenterInfoAdaper.notifyDataSetChanged();
						}
					}

					@Override
					public void failure(int statusCode, Object obj) {
						// TODO Auto-generated method stub
						listView_personCenter.onRefreshComplete();
						if (customProgressDialog != null && customProgressDialog.isShowing()) {
							customProgressDialog.dismiss();
						}
						Toast.makeText(MyselfDongtanActivity.this, "服务器响应异常，请稍后重试", Toast.LENGTH_SHORT).show();
					}
				});

	}
}
