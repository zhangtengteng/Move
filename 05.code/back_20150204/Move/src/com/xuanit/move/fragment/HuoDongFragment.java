package com.xuanit.move.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.james.asynhttp.AsynHttpClient;
import com.james.asynhttp.HttpNetWorkDataHandler;
import com.xuanit.move.R;
import com.xuanit.move.adapter.HuoDongAdapter;
import com.xuanit.move.app.AppConfig;
import com.xuanit.move.app.MoveApplication;
import com.xuanit.move.model.HuoDongInfo;
import com.xuanit.move.model.ResultInfo;
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

public class HuoDongFragment extends Fragment implements View.OnClickListener {
	private String UserId = "";
	private Context context;
	private Button btn_guanfang;
	private Button btn_geren;
	private PullToRefreshListView pulltorefreshlistview_huodong;
	private int ActType = 1;
	private int PageSize = 6;
	private int PageNo = 1;
	private int count;
	private boolean isLoadOver = false;
	private int flag = 0;
	private List<HuoDongInfo> list_huodong = new ArrayList<HuoDongInfo>();
	private HuoDongAdapter huoDongAdapter;
	private CustomProgressDialog customProgressDialog;
	private MoveApplication application;

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
		View rootView = inflater.inflate(R.layout.activity_huodong, container, false);
		btn_guanfang = (Button) rootView.findViewById(R.id.btn_guanfang);
		btn_geren = (Button) rootView.findViewById(R.id.btn_geren);

		pulltorefreshlistview_huodong = (PullToRefreshListView) rootView
				.findViewById(R.id.pulltorefreshlistview_huodong);
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
		
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		context = getActivity();
		customProgressDialog = CustomProgressDialog.createDialog(context);
		
		//创建adapter，设置刷新控件的adapter
		huoDongAdapter = new HuoDongAdapter(context, list_huodong,1);
		pulltorefreshlistview_huodong.setAdapter(huoDongAdapter);
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		//加载数据
		checkButton(flag);
		list_huodong.clear();
		PageNo = 1;
		isLoadOver = false;
		customProgressDialog.show();
		loadData();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
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

		default:
			break;
		}
	}

	/**
	 * 描述:加载数据
	 * 
	 * */
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
							}
							if (!isLoadOver) {
								list_huodong.addAll(list);
							}
							if ((PageNo * PageSize) >= count && count != 0) {
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
						Toast.makeText(context, "网络错误", Toast.LENGTH_SHORT).show();

					}
				});
	}

	/**
	 * 描述:生成请求参数
	 * 
	 * */
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

	/**
	 * 描述:解析返回的数据
	 * 
	 * */
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

	/**
	 * 描述:设置按钮的不同状态
	 * 
	 * */
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
}
