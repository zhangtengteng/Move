package com.xuanit.move.activity;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.james.asynhttp.AsynHttpClient;
import com.james.asynhttp.HttpNetWorkDataHandler;
import com.xuanit.move.R;
import com.xuanit.move.adapter.FensiAdapter;
import com.xuanit.move.app.AppConfig;
import com.xuanit.move.app.MoveApplication;
import com.xuanit.move.base.BaseActivity;
import com.xuanit.move.model.FriendInfo;
import com.xuanit.move.model.ResultInfo;
import com.xuanit.move.util.CloseAllActivityManager;
import com.xuanit.move.util.StringUtils;
import com.xuanit.move.view.CustomProgressDialog;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

public class MyselfFensictivity extends BaseActivity {

	private PullToRefreshListView pulltorefrshListView;
	private static final String GETCONTACTPERSON = "PhoneFriendsFans/GetContactPerson";
	private ArrayList<FriendInfo> list_fensi = new ArrayList<FriendInfo>();
	private CustomProgressDialog customProgressDialog;
	private int PageNo = 1;
	private final int PageSize = 10;
	private int count;
	private boolean isLoadOver = false;
	private MoveApplication application;
	private String userUserId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		CloseAllActivityManager.getInstance().addActivity(MyselfFensictivity.this);
		super.onCreate(savedInstanceState);
		application = (MoveApplication) getApplication();
		userUserId = application.appConfig.USER_ID;
		setView(R.layout.activity_myself_fensi);
		setTitle("", "粉丝", "");
		initView();
		//loadData();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		isLoadOver = false;
		list_fensi.clear();
		customProgressDialog.show();
		loadData();
	}

	// 粉丝
	private ResultInfo<FriendInfo> jsonParser(String result) {

		if (!StringUtils.isNullOrEmpty(result)) {
			JSONObject jsonObject;
			try {
				jsonObject = new JSONObject(result);
				String code = jsonObject.getString("Code");
				ResultInfo<FriendInfo> resultInfo = new ResultInfo<FriendInfo>();
				resultInfo.Code = code;
				ArrayList<FriendInfo> list_haoyou = resultInfo.getList();
				if (list_haoyou != null) {

					String data = jsonObject.getString("Data");
					if (code.equals("1")) {
						JSONObject object = new JSONObject(data);

						application.appConfig.GameIntegral = object.getString("GameIntegral");
						application.appConfig.StudentCard = object.getString("StudentCard");
						application.appConfig.Head = object.getString("Head");
						application.appConfig.StarLevel = object.getString("StarLevel");
						application.appConfig.MoveCount = object.getString("MoveCount");
						application.appConfig.FriendCount = object.getString("FriendCount");
						application.appConfig.FansCount = object.getString("FansCount");
						application.appConfig.TotalCount = object.getString("TotalCount");
						count =  Integer.valueOf(object.getString("TotalCount"));
						System.out.println("$$$$$$$$$$$$$$" + object.getString("TotalCount"));
						String ContactUsers = object.getString("ContactUsers");
						JSONArray jsonArray = new JSONArray(ContactUsers);
						String arr[] = new String[jsonArray.length()];

						for (int i = 0; i < jsonArray.length(); i++) {
							arr[i] = jsonArray.getString(i);
							JSONObject o = new JSONObject(arr[i]);
							FriendInfo info = new FriendInfo();

							info.UserId = o.getString("UserId");
							info.UserName = o.getString("UserName");
							info.StudentCard = o.getString("StudentCard");
							info.Head = o.getString("Head");
							info.PersonalDescription = o.getString("PersonalDescription");
							info.FriendCount = o.getString("FriendCount");
							info.CollegeName = o.getString("CollegeName");
							info.FansCount = o.getString("FansCount");
							info.CommonFriends = o.getString("CommonFriends");
							info.ToUserId = o.getString("ToUserId");
							info.AttentionType = o.getString("AttentionType");

							list_haoyou.add(info);
						}
						return resultInfo;
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	private String getJson() {
		JSONObject o = new JSONObject();
		try {
			if (!StringUtils.isNullOrEmpty(userUserId)) {
				o.put("PageNo", PageNo + "");
				o.put("PageSize", PageSize + "");
				o.put("UserId", userUserId);
				o.put("AttentionType", "2");
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return o.toString();
	}

	private void initView() {
		pulltorefrshListView = (PullToRefreshListView) findViewById(R.id.pulltorefrshListView_dongtan);
		customProgressDialog = CustomProgressDialog.createDialog(this);
		
		pulltorefrshListView.setMode(Mode.PULL_FROM_END);
		pulltorefrshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				if (!isLoadOver) {
					PageNo++;
				}
				loadData();
			}
		});

	}

	private void loadData() {
		customProgressDialog.show();

		new AsynHttpClient().post(AppConfig.HOSTURL + GETCONTACTPERSON, "data=" + getJson(), new HttpNetWorkDataHandler() {
			@Override
			public void success(int statusCode, Object obj) {
				pulltorefrshListView.onRefreshComplete();
				if (customProgressDialog != null && customProgressDialog.isShowing() && MyselfFensictivity.this != null
						&& !MyselfFensictivity.this.isFinishing()) {
					customProgressDialog.dismiss();
				}
				ResultInfo<FriendInfo> fensiResultInfo = jsonParser(obj.toString());
				
//				list_fensi = fensiResultInfo.getList();
				
				if (list_fensi!=null && fensiResultInfo != null && !StringUtils.isNullOrEmpty(fensiResultInfo.Code)
						&& "1".equals(fensiResultInfo.Code)) {
					
					FensiAdapter fensiAdapter = new FensiAdapter(MyselfFensictivity.this, list_fensi);
					pulltorefrshListView.setAdapter(fensiAdapter);
					
					if (PageNo == 1 && count == 0) {
						Toast.makeText(MyselfFensictivity.this, "木有数据", Toast.LENGTH_SHORT).show();
					}
					if (!isLoadOver) {
						list_fensi.addAll(fensiResultInfo.getList());
					}
					if ((PageNo * PageSize) >= count && count != 0) {
						//Toast.makeText(MyselfFensictivity.this, "已经全部加载", 1).show();
						isLoadOver = true;
					}
					fensiAdapter.notifyDataSetChanged();
				}
			}
			@Override
			public void failure(int statusCode, Object obj) {
				pulltorefrshListView.onRefreshComplete();
				if (customProgressDialog != null && customProgressDialog.isShowing() && MyselfFensictivity.this != null
						&& !MyselfFensictivity.this.isFinishing()) {
					customProgressDialog.dismiss();
				}
				Toast.makeText(MyselfFensictivity.this, "网络错误", Toast.LENGTH_SHORT).show();
			}
		});
	}
}
