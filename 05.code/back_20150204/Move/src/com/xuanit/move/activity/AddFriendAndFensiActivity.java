package com.xuanit.move.activity;

import java.util.ArrayList;
import java.util.List;
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
import com.xuanit.move.adapter.AddFriendAndFensiAdapter;
import com.xuanit.move.app.AppConfig;
import com.xuanit.move.app.MoveApplication;
import com.xuanit.move.base.BaseActivity;
import com.xuanit.move.model.AddPersonInfo;
import com.xuanit.move.model.FriendInfo;
import com.xuanit.move.model.ResultInfo;
import com.xuanit.move.util.CloseAllActivityManager;
import com.xuanit.move.util.StringUtils;
import com.xuanit.move.view.CustomProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

/**
 * 目前保留，以后确定不用可以删除。
 * @author yongchen.yu
 *
 */
public class AddFriendAndFensiActivity extends BaseActivity {

	// private Button bt_dongtan_fenzu;
	private PullToRefreshListView refrshListView;
	private int flag = 0;
	private static final String GETUSERLIST = "PhoneUser/GetUserList";
	private String GETFRIENDS = "PhoneFriendsFans/Attention";
	private ArrayList<AddPersonInfo> listMsg = new ArrayList<AddPersonInfo>();
	private List<String> userIdList = new ArrayList<String>();
	private AddFriendAndFensiAdapter addAdapter;
	private CustomProgressDialog customProgressDialog;
	private int count;
	private int PageNo = 1;
	private final int PageSize = 25;
	private boolean isLoadOver = false;
	private MoveApplication application;
	private String userId;

	// private SharedPreferences sharedPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		CloseAllActivityManager.getInstance().addActivity(AddFriendAndFensiActivity.this);
		super.onCreate(savedInstanceState);
		application = (MoveApplication) getApplication();
		userId = application.appConfig.USER_ID;
		setView(R.layout.activity_add_friend_and_fensi);
		setTitle("", "好友/粉丝", "保存");
		initView();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		loadData();
	}

	// 好友 粉丝
	private ResultInfo<AddPersonInfo> jsonParser(String result) throws JSONException {

		if (!StringUtils.isNullOrEmpty(result)) {
			JSONObject jsonObject = new JSONObject(result);
			String Code = jsonObject.getString("Code");
			ResultInfo<AddPersonInfo> resultInfo = new ResultInfo<AddPersonInfo>();
			if(resultInfo != null){
				
				resultInfo.Code = Code;
				ArrayList<AddPersonInfo> list_waimai = resultInfo.getList();
				JSONObject obj = jsonObject.getJSONObject("Data");
				String Item2 = obj.getString("Item2");
				resultInfo.Item2 = Item2;
				count = Integer.parseInt(Item2);
				JSONArray jsonArray = obj.getJSONArray("Item1");

				String arr[] = new String[jsonArray.length()];
				for (int i = 0; i < jsonArray.length(); i++) {
					arr[i] = jsonArray.getString(i);
					JSONObject o = new JSONObject(arr[i]);
					AddPersonInfo info = new AddPersonInfo();

					info.UserId = o.getString("UserId");// 用户ID
					info.UserName = o.getString("UserName");// 用户昵称
					info.Pwd = o.getString("Pwd");
					info.Salt = o.getString("Salt");
					info.StudentNum = o.getString("StudentNum");
					info.SchoolId = o.getString("SchoolId");
					info.Head = o.getString("Head");// 学生证
					info.StudentCard = o.getString("StudentCard");// 头像
					info.AuditStatus = o.getString("AuditStatus");
					info.StarLevel = o.getString("StarLevel");// 星级
					info.LevelMax = o.getString("LevelMax");
					info.GameIntegral = o.getString("GameIntegral");// 游戏积分
					info.StarLevelSum = o.getString("StarLevelSum");
					info.MoveCount = o.getString("MoveCount");// 动弹数
					info.Status = o.getString("Status");
					info.CreateTime = o.getString("CreateTime");
					info.CreateUser = o.getString("CreateUser");
					info.LastChangeTime = o.getString("LastChangeTime");
					info.LastChangeUser = o.getString("LastChangeUser");
					info.ActId = o.getString("ActId");
					info.IsAssociated = o.getBoolean("IsAssociated");
					info.NumberStars = o.getString("NumberStars");
					info.PersonalCenter = o.getString("PersonalCenter");
					info.Description = o.getString("Description");

					list_waimai.add(info);
				}
				
			}
			
			return resultInfo;

		}
		return null;
	}

	private StringBuffer getJson(List<String> userIdList) throws JSONException {

		StringBuffer dataStr = new StringBuffer();

		for (int i = 0; i < userIdList.size(); i++) {
			JSONObject o = new JSONObject();
			o.put("UserId", userId);
			o.put("ToUserId", userIdList.get(i));

			System.out.println("o.toString()==========================" + o.toString());

			if (i != userIdList.size() - 1) {
				dataStr.append(o.toString() + ",");
			} else {
				dataStr.append(o.toString());
			}
			System.out.println("dataStr==============================" + dataStr);
		}
		return dataStr;
	}

	private String getJson(int flag) {
		JSONObject o = new JSONObject();
		switch (flag) {
		case 0:
			try {
				if (!StringUtils.isNullOrEmpty(userId)) {
					o.put("PageIndex", PageNo + "");
					o.put("PageSize", PageSize + "");
					o.put("UserId", userId);
					o.put("AttentionType", "1");
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case 1:
			try {
				if (!StringUtils.isNullOrEmpty(userId)) {
					o.put("PageNo", PageNo + "");
					o.put("PageSize", PageSize + "");
					o.put("UserId", userId);
					o.put("AttentionType", "2");
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		default:
			break;
		}
		return o.toString();
	}

	private void initView() {

		refrshListView = (PullToRefreshListView) findViewById(R.id.pulltorefrshListView_add_friend_and_fensi);
		refrshListView.setMode(Mode.PULL_FROM_END);
		refrshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				if (!isLoadOver) {
					PageNo++;
				}

				loadData();

			}
		});
		addAdapter = new AddFriendAndFensiAdapter(AddFriendAndFensiActivity.this, listMsg);
		refrshListView.setAdapter(addAdapter);

		customProgressDialog = CustomProgressDialog.createDialog(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {

		case R.id.ll_header_right:

			userIdList = AddFriendAndFensiAdapter.getUserIdList();

			if (userIdList.size() != 0) {
				try {
					loadData(userIdList);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				Toast.makeText(AddFriendAndFensiActivity.this, "请添加新的关注对象", Toast.LENGTH_LONG).show();
			}
			break;

		default:
			break;
		}
	}

	public ResultInfo<FriendInfo> JsonParseState(String jsonStr) {
		if (!StringUtils.isNullOrEmpty(jsonStr)) {

			try {
				ResultInfo<FriendInfo> result = new ResultInfo<FriendInfo>();
				JSONObject jsonObject = new JSONObject(jsonStr);
				String code = jsonObject.getString("Code");
				result.Code = code;

				if (code.equals("1")) {
					Toast.makeText(AddFriendAndFensiActivity.this, "关注成功", Toast.LENGTH_SHORT).show();
					AddFriendAndFensiActivity.this.setResult(0X0002);
					//AddFriendAndFensiActivity.this.finish();
				} else {
					Toast.makeText(AddFriendAndFensiActivity.this, "关注失败", Toast.LENGTH_SHORT).show();
				}

				return result;

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private void loadData(List<String> userIdList) throws JSONException {

		new AsynHttpClient().post(AppConfig.HOSTURL + GETFRIENDS, "data=[" + getJson(userIdList) + "]&ExecutorID="
				+ userId, new HttpNetWorkDataHandler() {

			@Override
			public void success(int statusCode, Object obj) {
				if (customProgressDialog != null && customProgressDialog.isShowing()
						&& AddFriendAndFensiActivity.this != null && !AddFriendAndFensiActivity.this.isFinishing()) {
					customProgressDialog.dismiss();
				}

				JsonParseState(obj.toString());

				System.out.println("==obj.toString()" + "===" + obj.toString());

			}

			@Override
			public void failure(int statusCode, Object obj) {
				if (customProgressDialog != null && customProgressDialog.isShowing()
						&& AddFriendAndFensiActivity.this != null && !AddFriendAndFensiActivity.this.isFinishing()) {
					customProgressDialog.dismiss();
				}
				Toast.makeText(AddFriendAndFensiActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void loadData() {
		customProgressDialog.show();

		new AsynHttpClient().post(AppConfig.HOSTURL + GETUSERLIST, "data=" + getJson(flag),
				new HttpNetWorkDataHandler() {
					@Override
					public void success(int statusCode, Object obj) {
						refrshListView.onRefreshComplete();
						if (customProgressDialog != null && customProgressDialog.isShowing()
								&& AddFriendAndFensiActivity.this != null
								&& !AddFriendAndFensiActivity.this.isFinishing()) {

							customProgressDialog.dismiss();
						}
						System.out.println("==flag" + flag + "===" + obj.toString());

						ResultInfo<AddPersonInfo> resultInfo;
						try {
							resultInfo = jsonParser(obj.toString());

							// listMsg = resultInfo.getList();
							Log.i("AddFriendAndFensiActivity", count+"");

							if (resultInfo != null && !StringUtils.isNullOrEmpty(resultInfo.Code)
									&& "1".equals(resultInfo.Code)) {
								
								if (PageNo == 1 && count == 0) {
									Toast.makeText(AddFriendAndFensiActivity.this, "木有数据", Toast.LENGTH_SHORT).show();
								}
								if (!isLoadOver) {
									listMsg.addAll(resultInfo.getList());
								}
								if ((PageNo * PageSize) >= count && count != 0) {
									Toast.makeText(AddFriendAndFensiActivity.this, "已经全部加载", Toast.LENGTH_SHORT).show();
									isLoadOver = true;
								}
								addAdapter.notifyDataSetChanged();


							}

							System.out.println("listMsg--" + listMsg);

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					@Override
					public void failure(int statusCode, Object obj) {
						if (customProgressDialog != null && customProgressDialog.isShowing()
								&& AddFriendAndFensiActivity.this != null
								&& !AddFriendAndFensiActivity.this.isFinishing()) {
							customProgressDialog.dismiss();
						}
						refrshListView.onRefreshComplete();
						Toast.makeText(AddFriendAndFensiActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
					}
				});
	}

}
