package com.xuanit.move.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.LocationClient;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.james.asynhttp.AsynHttpClient;
import com.james.asynhttp.HttpNetWorkDataHandler;
import com.xuanit.move.R;
import com.xuanit.move.adapter.MainAdapter;
import com.xuanit.move.app.AppConfig;
import com.xuanit.move.app.MoveApplication;
import com.xuanit.move.model.ZhouBianInfo;
import com.xuanit.move.util.StringUtils;
import com.xuanit.move.view.CustomProgressDialog;

@SuppressLint("NewApi")
public class MainFragment extends Fragment implements View.OnClickListener {

	private PullToRefreshListView pullToRefreshListView;
	private TextView tv_main_zhoubian, tv_main_tongban, tv_main_school, tv_main_toutiao;
	private String data;
	private List<ZhouBianInfo> list_zhoubian = new ArrayList<ZhouBianInfo>();
	private MainAdapter adapter;
	private final String HTTP_NEAR = AppConfig.HOSTURL + "PhoneNewsFeed/GetPeriphery";
	private final String HTTP_CLASSMATE = AppConfig.HOSTURL + "PhoneNewsFeed/GetSameClassNews";
	private final String HTTP_SCHOOL = AppConfig.HOSTURL + "PhoneNewsFeed/GetSchoolNews";
	private final String HTTP_TOUTIAO = AppConfig.HOSTURL + "PhoneNewsFeed/GetTopLineNews";
	private final String[] https = { HTTP_TOUTIAO, HTTP_CLASSMATE, HTTP_SCHOOL, HTTP_NEAR };
	private int tag = 0;
	private double Longitude;
	private double Latitude;
	private CustomProgressDialog customProgressDialog;
	private String Item2;// 总条数
	private int PageNo = 1;
	private final int PageSize = 15;
	private boolean refreshOrMore = false; // 刷新false加载true
	private boolean isLoadOver = false;// 是否已经加载全部数据
	private MoveApplication application;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		application = (MoveApplication) getActivity().getApplication();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// 创建进度对话框
		customProgressDialog = CustomProgressDialog.createDialog(getActivity());

		// 获得页面的控件
		View view = inflater.inflate(R.layout.fragment_main, container, false);
		tv_main_zhoubian = (TextView) view.findViewById(R.id.tv_main_zhoubian);
		tv_main_tongban = (TextView) view.findViewById(R.id.tv_main_tongban);
		tv_main_school = (TextView) view.findViewById(R.id.tv_main_school);
		tv_main_toutiao = (TextView) view.findViewById(R.id.tv_main_toutiao);
		pullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.pullToRefreshListView);

		// 设置拉动方式
		pullToRefreshListView.setMode(Mode.BOTH);

		// 设置监听
		pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
			// 下拉刷新
			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				refreshOrMore = false;
				PageNo = 1;
				loadData(https[tag]);
				isLoadOver = false;
			}

			// 上拉加载
			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				refreshOrMore = true;
				if (!isLoadOver) {
					PageNo++;
				}
				loadData(https[tag]);
			}

		});

		tv_main_toutiao.setOnClickListener(this);
		tv_main_tongban.setOnClickListener(this);
		tv_main_school.setOnClickListener(this);
		tv_main_zhoubian.setOnClickListener(this);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		// 创建adapter
		adapter = new MainAdapter(getActivity(), list_zhoubian, application.appConfig.USER_ID);
		pullToRefreshListView.setAdapter(adapter);

	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		// 加载数据
		PageNo = 1;
		refreshOrMore = false;
		isLoadOver = false;
		list_zhoubian.clear();
		checkButton(tag);
		adapter.notifyDataSetChanged();
		customProgressDialog.show();
		loadData(https[tag]);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

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
		case R.id.tv_main_toutiao:
			tag = 0;
			PageNo = 1;
			isLoadOver = false;
			checkButton(tag);
			list_zhoubian.clear();
			adapter.notifyDataSetChanged();
			customProgressDialog.show();
			loadData(https[tag]);
			break;
		case R.id.tv_main_tongban:
			tag = 1;
			PageNo = 1;
			isLoadOver = false;
			checkButton(tag);
			list_zhoubian.clear();
			adapter.notifyDataSetChanged();
			customProgressDialog.show();
			loadData(https[tag]);
			break;
		case R.id.tv_main_school:
			tag = 2;
			PageNo = 1;
			isLoadOver = false;
			checkButton(tag);
			list_zhoubian.clear();
			adapter.notifyDataSetChanged();
			customProgressDialog.show();
			loadData(https[tag]);
			break;
		case R.id.tv_main_zhoubian:
			tag = 3;
			PageNo = 1;
			isLoadOver = false;
			checkButton(tag);
			list_zhoubian.clear();
			adapter.notifyDataSetChanged();
			customProgressDialog.show();
			loadData(https[tag]);
			break;
		default:
			break;
		}
	}

	/**
	 * 描述:设置按钮的不同状态
	 * 
	 * */

	private void checkButton(int tag) {
		tv_main_toutiao.setBackgroundResource(R.drawable.bt_left_shape);
		tv_main_toutiao.setTextColor(getResources().getColor(R.color.white));
		tv_main_tongban.setBackgroundResource(R.drawable.bt_center_shape);
		tv_main_tongban.setTextColor(getResources().getColor(R.color.white));
		tv_main_school.setBackgroundResource(R.drawable.bt_center_shape);
		tv_main_school.setTextColor(getResources().getColor(R.color.white));
		tv_main_zhoubian.setBackgroundResource(R.drawable.bt_right_shape);
		tv_main_zhoubian.setTextColor(getResources().getColor(R.color.white));
		switch (tag) {
		case 0:
			tv_main_toutiao.setBackgroundResource(R.drawable.bt_left_click_shape);
			tv_main_toutiao.setTextColor(getResources().getColor(R.color.baseColor));
			break;
		case 1:
			tv_main_tongban.setBackgroundResource(R.drawable.bt_center_click_shape);
			tv_main_tongban.setTextColor(getResources().getColor(R.color.baseColor));
			break;
		case 2:
			tv_main_school.setBackgroundResource(R.drawable.bt_center_click_shape);
			tv_main_school.setTextColor(getResources().getColor(R.color.baseColor));
			break;
		case 3:
			tv_main_zhoubian.setBackgroundResource(R.drawable.bt_right_click_shape);
			tv_main_zhoubian.setTextColor(getResources().getColor(R.color.baseColor));
			break;
		}
	}

	/**
	 * 描述:加载数据
	 * 
	 * 
	 * */
	private void loadData(String url) {

		getJson(tag);
		if (StringUtils.isNullOrEmpty(data)) {
			return;
		}
		new AsynHttpClient().post(https[tag], "data=" + data, new HttpNetWorkDataHandler() {
			@Override
			public void success(int statusCode, Object obj) {

				if (customProgressDialog != null && customProgressDialog.isShowing()) {
					customProgressDialog.dismiss();
				}
				String result = obj.toString();
				Log.i("MainFragment", "==" + result);
				try {
					if (!StringUtils.isNullOrEmpty(result)) {
						JSONObject object = new JSONObject(result);
						String Code = object.getString("Code");

						if (!StringUtils.isNullOrEmpty("Code") && "1".equals(Code)) {
							JSONObject jsonObject = object.getJSONObject("Data");
							Item2 = jsonObject.getString("Item2");
							JSONArray jsonArray = jsonObject.getJSONArray("Item1");

							if (PageNo == 1 && !refreshOrMore) {
								list_zhoubian.clear();
							}
							// list_zhoubian.clear();
							if (!isLoadOver) {
								for (int i = 0; i < jsonArray.length(); i++) {
									JSONObject json = new JSONObject(jsonArray.getString(i));
									ZhouBianInfo zhouBianInfo = new ZhouBianInfo();
									zhouBianInfo.NewsId = json.getString("NewsId");
									zhouBianInfo.UserId = json.getString("UserId");
									zhouBianInfo.Contents = json.getString("Contents");
									zhouBianInfo.ReadCount = json.getString("ReadCount");
									zhouBianInfo.ReplyCount = json.getString("ReplyCount");
									zhouBianInfo.PublishTime = json.getString("PublishTime");
									zhouBianInfo.UserName = json.getString("UserName");
									zhouBianInfo.Head = json.getString("Head");
									zhouBianInfo.ImgSrc = json.getString("ImgSrc");
									zhouBianInfo.NewComment = json.getString("NewComment");
									zhouBianInfo.PraiseCount = json.getString("PraiseCount");
									zhouBianInfo.IsPraise = json.getBoolean("IsPraise");
									zhouBianInfo.Distance = json.getString("Distance");
									list_zhoubian.add(zhouBianInfo);
								}
							}

							pullToRefreshListView.onRefreshComplete();
							adapter.notifyDataSetChanged();

							if (!StringUtils.isNullOrEmpty(Item2) && !"null".equals(Item2)) {
								int count = Integer.parseInt(Item2);

								if (PageNo == 1 && count == 0) {
									Log.i("FuJinFragment", "FuJinFragment木有数据");
								}

								if ((PageNo * PageSize) > count && count != 0) {
									Log.i("FuJinFragment", "FuJinFragment已经全部加载");
									isLoadOver = true;
								}
							}
						} else {
							pullToRefreshListView.onRefreshComplete();
							Toast.makeText(getActivity(), "网络错误！", 1).show();
						}

					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					pullToRefreshListView.onRefreshComplete();
				}
			}

			@Override
			public void failure(int statusCode, Object obj) {
				if (customProgressDialog != null && customProgressDialog.isShowing()) {
					customProgressDialog.dismiss();
				}

				pullToRefreshListView.onRefreshComplete();
				Toast.makeText(getActivity(), "访问服务器失败，请稍后再试", 1).show();
				Log.i("FuJinFragment", "fujinData==" + "访问服务器失败，请稍后再试" + "statusCode" + statusCode);
			}
		});
	}

	/**
	 * 描述:生成请求参数
	 * 
	 * 
	 * */
	public void getJson(int tag) {
		if (tag == 3) {
			Longitude = application.latitude;
			Latitude = application.lontitude;
			if (((int) Longitude == 0 && (int) Latitude == 0)) {
				Toast.makeText(getActivity(), "位置获取失败,请允许位置获取(设置路径：应用管理→动态一下→权限管理→定位)", Toast.LENGTH_LONG).show();

				list_zhoubian.clear();
				adapter.notifyDataSetChanged();

				if (customProgressDialog != null && customProgressDialog.isShowing()) {
					customProgressDialog.dismiss();

				}
				data = "";
				return;
			}
			JSONObject object3 = new JSONObject();
			try {
				object3.put("Longitude", Longitude + "");
				object3.put("Latitude", Latitude + "");
				object3.put("PageNo", String.valueOf(PageNo));
				object3.put("PageSize", String.valueOf(PageSize));

				if (!StringUtils.isNullOrEmpty(application.appConfig.USER_ID)) {
					object3.put("UserId", application.appConfig.USER_ID);
				}
				data = object3.toString();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else {
			JSONObject object0 = new JSONObject();
			try {
				object0.put("PageNo", PageNo + "");
				object0.put("PageSize", PageSize + "");
				if (!StringUtils.isNullOrEmpty(application.appConfig.USER_ID)) {
					object0.put("UserId", application.appConfig.USER_ID);
				}
				data = object0.toString();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

	}

}
