package com.xuanit.move.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.baidu.location.LocationClient;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.james.asynhttp.AsynHttpClient;
import com.james.asynhttp.HttpNetWorkDataHandler;
import com.xuanit.move.R;

import com.xuanit.move.adapter.PersonAdapter;
import com.xuanit.move.app.AppConfig;
import com.xuanit.move.app.MoveApplication;
import com.xuanit.move.model.AddPersonInfo;
import com.xuanit.move.util.StringUtils;
import com.xuanit.move.view.CustomProgressDialog;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class RenWuFragment extends Fragment implements View.OnClickListener {
	private Context context;
	public String UserId;// 用户名
	private PullToRefreshListView refreshListView_person;
	private Button bt_person_round;
	private Button bt_person_classmate;
	private Button bt_person_schoolmate;
	private Button bt_person_introduce;
	private int flag = 0;
	private final List<AddPersonInfo> personList = new ArrayList<AddPersonInfo>();
	private boolean isLoadOver = false;
	private int PageNo = 1;
	private final int PageSize = 20;
	private int count;
	public double latitude;// 纬度
	public double lontitude;// 经度
	public String data;
	private PersonAdapter personAdapter;
	private CustomProgressDialog customProgressDialog;
	private MoveApplication application;
	private boolean refreshOrMore = false; // 刷新false加载true

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		application = (MoveApplication) getActivity().getApplication();
		if (!StringUtils.isNullOrEmpty(application.appConfig.USER_ID)) {
			UserId = application.appConfig.USER_ID;
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.activity_persons, container, false);

		bt_person_round = (Button) rootView.findViewById(R.id.bt_person_round);
		bt_person_classmate = (Button) rootView.findViewById(R.id.bt_person_classmate);
		bt_person_schoolmate = (Button) rootView.findViewById(R.id.bt_person_schoolmate);
		bt_person_introduce = (Button) rootView.findViewById(R.id.bt_person_introduce);

		bt_person_round.setOnClickListener(this);
		bt_person_classmate.setOnClickListener(this);
		bt_person_schoolmate.setOnClickListener(this);
		bt_person_introduce.setOnClickListener(this);

		refreshListView_person = (PullToRefreshListView) rootView.findViewById(R.id.pulltorefrshListView_person);
		refreshListView_person.setMode(com.handmark.pulltorefresh.library.PullToRefreshBase.Mode.BOTH);

		refreshListView_person.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
			// 下拉刷新
			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				PageNo = 1;
				refreshOrMore = false;
				loadData(flag);
				isLoadOver = false;
			}

			// 上拉加载
			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				refreshOrMore = true;
				if (!isLoadOver) {
					PageNo++;
				}
				loadData(flag);
			}
		});
		return rootView;

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		context = getActivity();

		customProgressDialog = CustomProgressDialog.createDialog(context);
		personAdapter = new PersonAdapter(context, personList);
		refreshListView_person.setAdapter(personAdapter);

	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		PageNo = 1;
		isLoadOver = false;
		refreshOrMore = false;
		checkButton(flag);
		personList.clear();
		customProgressDialog.show();
		loadData(flag);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
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

		default:
			break;
		}
	}

	private void loadData(final int flag) {

		if (flag == 3) {
			lontitude = application.latitude;
			latitude = application.lontitude;
			if ((int)lontitude == 0 && (int)latitude == 0) {
				Toast.makeText(context, "位置获取失败,请允许位置获取(设置路径：应用管理→动态一下→权限管理→定位)", Toast.LENGTH_LONG).show();
				personList.clear();
				personAdapter.notifyDataSetChanged();
				
				if (customProgressDialog != null && customProgressDialog.isShowing()) {
					customProgressDialog.dismiss();
					
				}
				return;
			}
		}

		JSONObject o = new JSONObject();
		try {
			if (flag == 3) {
				o.put("PageNo", PageNo + "");
				o.put("PageSize", PageSize + "");
				o.put("Longitude", lontitude);
				o.put("Latitude", latitude);
				o.put("UserId", UserId);

			} else {
				o.put("PageNo", PageNo + "");
				o.put("PageSize", PageSize + "");
				o.put("UserId", UserId);
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
				refreshListView_person.onRefreshComplete();

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

							if (PageNo == 1 && !refreshOrMore) {
								personList.clear();
							}

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
							}

							if ((PageNo * PageSize) >= count && count != 0) {
								Log.i("PersonActivity", flag + "已经全部加载");
								isLoadOver = true;
							}

							personAdapter.notifyDataSetChanged();

						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Log.i("PersonActivity", "JSON解析出错了");

					}

				} else {
					refreshListView_person.setVisibility(View.GONE);
					Toast.makeText(context, "网络错误！", Toast.LENGTH_SHORT).show();
					Log.i("PersonActivity", flag + "网络错误！");
				}
			}

			@Override
			public void failure(int statusCode, Object obj) {
				if (customProgressDialog != null && customProgressDialog.isShowing()) {
					customProgressDialog.dismiss();
				}

				refreshListView_person.onRefreshComplete();
				Toast.makeText(context, "访问服务器失败，请稍后再试", Toast.LENGTH_SHORT).show();
				Log.i("PersonActivity", "personData==" + "访问服务器失败，请稍后再试" + "statusCode" + statusCode);
			}
		});
	}

	private void checkButton(int flag) {
		bt_person_introduce.setBackgroundResource(R.drawable.bt_left_shape);
		bt_person_introduce.setTextColor(getResources().getColor(R.color.white));
		bt_person_classmate.setBackgroundResource(R.drawable.bt_center_shape);
		bt_person_classmate.setTextColor(getResources().getColor(R.color.white));
		bt_person_schoolmate.setBackgroundResource(R.drawable.bt_center_shape);
		bt_person_schoolmate.setTextColor(getResources().getColor(R.color.white));
		bt_person_round.setBackgroundResource(R.drawable.bt_right_shape);
		bt_person_round.setTextColor(getResources().getColor(R.color.white));

		switch (flag) {
		case 0:
			bt_person_introduce.setBackgroundResource(R.drawable.bt_left_click_shape);
			bt_person_introduce.setTextColor(getResources().getColor(R.color.baseColor));
			break;
		case 1:
			bt_person_classmate.setBackgroundResource(R.drawable.bt_center_click_shape);
			bt_person_classmate.setTextColor(getResources().getColor(R.color.baseColor));
			break;
		case 2:
			bt_person_schoolmate.setBackgroundResource(R.drawable.bt_center_click_shape);
			bt_person_schoolmate.setTextColor(getResources().getColor(R.color.baseColor));
			break;
		case 3:
			bt_person_round.setBackgroundResource(R.drawable.bt_right_click_shape);
			bt_person_round.setTextColor(getResources().getColor(R.color.baseColor));
			break;
		}
	}

}
