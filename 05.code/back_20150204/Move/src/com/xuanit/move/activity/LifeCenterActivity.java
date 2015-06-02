package com.xuanit.move.activity;

import io.rong.imkit.RongIM;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.james.asynhttp.AsynHttpClient;
import com.james.asynhttp.HttpNetWorkDataHandler;
import com.xuanit.move.R;
import com.xuanit.move.adapter.ErShouAdapter;
import com.xuanit.move.adapter.JianZhiAdapter;
import com.xuanit.move.adapter.KuaiDiAdapter;
import com.xuanit.move.adapter.WaiMaiAdapter;
import com.xuanit.move.app.AppConfig;
import com.xuanit.move.app.MoveApplication;
import com.xuanit.move.base.BaseActivity;
import com.xuanit.move.model.ErShouInfo;
import com.xuanit.move.model.FriendInfo;
import com.xuanit.move.model.JianZhiInfo;
import com.xuanit.move.model.KuaiDiInfo;
import com.xuanit.move.model.ResultInfo;
import com.xuanit.move.model.WaiMainInfo;
import com.xuanit.move.util.CloseAllActivityManager;
import com.xuanit.move.util.StringUtils;
import com.xuanit.move.view.CustomProgressDialog;
import com.xuanit.move.view.PullToRefreshListView;
public class LifeCenterActivity extends BaseActivity {
	private Button btn_waimai;
	private Button btn_kuaidi;
	private Button btn_jianzhi;
	private Button btn_ershou;
	private PullToRefreshListView pulltorefrshListView_life;
	private int flag = 0;
	private ArrayList<WaiMainInfo> list_waimai = new ArrayList<WaiMainInfo>();
	private ArrayList<JianZhiInfo> list_jianzhi = new ArrayList<JianZhiInfo>();

	private ArrayList<KuaiDiInfo> list_kuaidi = new ArrayList<KuaiDiInfo>();
	private ArrayList<ErShouInfo> list_ershou = new ArrayList<ErShouInfo>();
	private CustomProgressDialog customProgressDialog;
	private final String WAIMAI = "PhoneTakeAway/GetList";// 外卖
	private final String JIANZHI = "PhonePartTimeJob/GetList";// 兼职 1
	private final String KUAIDI = "PhoneLogisticsCompany/GetList";// 快递 2
	private final String ERSHOU = "PhoneHandInformation/GetList";// 二手 3
	private String[] LIFE = { WAIMAI, JIANZHI, KUAIDI, ERSHOU };
	Handler mHandler = new Handler();
	private MoveApplication application;
	private String userUserId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		CloseAllActivityManager.getInstance().addActivity(LifeCenterActivity.this);
		super.onCreate(savedInstanceState);
		application = (MoveApplication) getApplication();
		userUserId = application.appConfig.USER_ID;
		setView(R.layout.activity_life_center);
		setTitle("", "生活中心", "");

		initView();
		
		//加载数据
		loadData(flag);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	private ResultInfo<WaiMainInfo> jsonParser(String result) {
		if (!StringUtils.isNullOrEmpty(result)) {
			try {
				JSONObject jsonObject = new JSONObject(result);
				String Code = jsonObject.getString("Code");
				ResultInfo<WaiMainInfo> resultInfo = new ResultInfo<WaiMainInfo>();
				resultInfo.Code = Code;
				ArrayList<WaiMainInfo> list_waimai = resultInfo.getList();
				JSONObject obj = jsonObject.getJSONObject("Data");
				String Item2 = obj.getString("Item2");
				resultInfo.Item2 = Item2;
				JSONArray jsonArray = obj.getJSONArray("Item1");

				String arr[] = new String[jsonArray.length()];
				for (int i = 0; i < jsonArray.length(); i++) {
					arr[i] = jsonArray.getString(i);
					JSONObject o = new JSONObject(arr[i]);
					WaiMainInfo info = new WaiMainInfo();
					info.TakeAwayId = o.getString("TakeAwayId");
					info.Name = o.getString("Name");
					info.Contents = o.getString("Contents");
					info.Status = o.getString("Status");
					info.CreateTime = o.getString("CreateTime");
					info.CreateUser = o.getString("CreateUser");
					info.LastChangeTime = o.getString("LastChangeTime");
					info.LastChangeUser = o.getString("LastChangeUser");
					info.Phone = o.getString("Phone");
					info.Distance = o.getString("Distance");
					info.Img = o.getString("Img");
					info.Speed = o.getString("Speed");

					list_waimai.add(info);
				}
				return resultInfo;

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	// 兼职
	private ResultInfo<JianZhiInfo> jsonParser1(String result) {
		if (!StringUtils.isNullOrEmpty(result)) {
			try {
				JSONObject jsonObject = new JSONObject(result);
				String Code = jsonObject.getString("Code");
				ResultInfo<JianZhiInfo> resultInfo = new ResultInfo<JianZhiInfo>();
				resultInfo.Code = Code;
				ArrayList<JianZhiInfo> list_jianzhi = resultInfo.getList();
				JSONObject obj = jsonObject.getJSONObject("Data");
				String Item2 = obj.getString("Item2");
				resultInfo.Item2 = Item2;
				JSONArray jsonArray = obj.getJSONArray("Item1");

				String arr[] = new String[jsonArray.length()];
				for (int i = 0; i < jsonArray.length(); i++) {
					arr[i] = jsonArray.getString(i);
					JSONObject o = new JSONObject(arr[i]);
					JianZhiInfo info = new JianZhiInfo();
					info.PartTimeId = o.getString("PartTimeId");
					info.Name = o.getString("Name");
					info.Contents = o.getString("Contents");
					info.Status = o.getString("Status");
					info.WorkPeriod = o.getString("WorkPeriod");
					info.WorkArea = o.getString("WorkArea");
					info.WorkTime = o.getString("WorkTime");
					info.WorkPay = o.getString("WorkPay");
					info.Img = o.getString("Img");
					info.ContactName = o.getString("ContactName");
					info.ContactPhone = o.getString("ContactPhone");
					info.CreateTime = o.getString("CreateTime");
					info.CreateUser = o.getString("CreateUser");
					info.LastChangeTime = o.getString("LastChangeTime");
					info.LastChangeUser = o.getString("LastChangeUser");

					list_jianzhi.add(info);
				}
				return resultInfo;

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	// 快递
	private ResultInfo<KuaiDiInfo> jsonParser2(String result) {
		if (!StringUtils.isNullOrEmpty(result)) {
			try {
				JSONObject jsonObject = new JSONObject(result);
				String Code = jsonObject.getString("Code");
				ResultInfo<KuaiDiInfo> resultInfo = new ResultInfo<KuaiDiInfo>();
				resultInfo.Code = Code;
				ArrayList<KuaiDiInfo> list_kuaidi = resultInfo.getList();
				JSONObject obj = jsonObject.getJSONObject("Data");
				String Item2 = obj.getString("Item2");
				resultInfo.Item2 = Item2;
				JSONArray jsonArray = obj.getJSONArray("Item1");

				String arr[] = new String[jsonArray.length()];
				for (int i = 0; i < jsonArray.length(); i++) {
					arr[i] = jsonArray.getString(i);
					JSONObject o = new JSONObject(arr[i]);
					KuaiDiInfo info = new KuaiDiInfo();
					info.CompanyId = o.getString("CompanyId");
					info.Prefix = o.getString("Prefix");
					info.CompanyCode = o.getString("CompanyCode");
					info.LinkMan = o.getString("LinkMan");
					info.Phone = o.getString("Phone");
					info.CompanyName = o.getString("CompanyName");
					info.Img = o.getString("Img");
					info.Status = o.getString("Status");
					info.CreateTime = o.getString("CreateTime");
					info.CreateUser = o.getString("CreateUser");
					info.LastChangeTime = o.getString("LastChangeTime");
					info.LastChangeUser = o.getString("LastChangeUser");

					list_kuaidi.add(info);
				}
				return resultInfo;

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	// 二手
	private ResultInfo<ErShouInfo> jsonParser3(String result) {
		if (!StringUtils.isNullOrEmpty(result)) {
			try {
				JSONObject jsonObject = new JSONObject(result);
				String Code = jsonObject.getString("Code");
				ResultInfo<ErShouInfo> resultInfo = new ResultInfo<ErShouInfo>();
				resultInfo.Code = Code;
				ArrayList<ErShouInfo> list_ershou = resultInfo.getList();
				JSONObject obj = jsonObject.getJSONObject("Data");
				String Item2 = obj.getString("Item2");
				resultInfo.Item2 = Item2;
				JSONArray jsonArray = obj.getJSONArray("Item1");

				String arr[] = new String[jsonArray.length()];
				for (int i = 0; i < jsonArray.length(); i++) {
					arr[i] = jsonArray.getString(i);
					JSONObject o = new JSONObject(arr[i]);
					ErShouInfo info = new ErShouInfo();
					info.InformationId = o.getString("InformationId");
					info.Name = o.getString("Name");
					info.Contents = o.getString("Contents");
					info.Status = o.getString("Status");
					info.CreateTime = o.getString("CreateTime");
					info.CreateUser = o.getString("CreateUser");
					info.LastChangeTime = o.getString("LastChangeTime");
					info.LastChangeUser = o.getString("LastChangeUser");
					info.Img = o.getString("Img");

					list_ershou.add(info);
				}
				return resultInfo;

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	private String getJson(int flag) {
		JSONObject o = new JSONObject();
		switch (flag) {
		case 0:
			try {
				o.put("PageNo", "1");
				o.put("PageSize", "10");
				if (!StringUtils.isNullOrEmpty(userUserId)) {
					o.put("UserId", userUserId);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;
		case 1:
			try {
				o.put("PageNo", "1");
				o.put("PageSize", "10");
				if (!StringUtils.isNullOrEmpty(userUserId)) {
					o.put("UserId", userUserId);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 2:
			try {
				o.put("PageNo", "1");
				o.put("PageSize", "10");
				if (!StringUtils.isNullOrEmpty(userUserId)) {
					o.put("UserId", userUserId);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;
		case 3:
			try {
				o.put("PageNo", "1");
				o.put("PageSize", "10");
				if (!StringUtils.isNullOrEmpty(userUserId)) {
					o.put("UserId", userUserId);
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
		btn_waimai = (Button) findViewById(R.id.btn_waimai);
		btn_jianzhi = (Button) findViewById(R.id.btn_jianzhi);
		btn_kuaidi = (Button) findViewById(R.id.btn_kuaidi);
		btn_ershou = (Button) findViewById(R.id.btn_ershou);
		pulltorefrshListView_life = (PullToRefreshListView) findViewById(R.id.pulltorefrshListView_life);

		customProgressDialog = CustomProgressDialog.createDialog(this);
		btn_waimai.setOnClickListener(this);
		btn_jianzhi.setOnClickListener(this);
		btn_kuaidi.setOnClickListener(this);
		btn_ershou.setOnClickListener(this);

		// 处理Footer
//		LinearLayout footer_dongtan = (LinearLayout) findViewById(R.id.footer_ll_dongtan);
//		LinearLayout footer_huodong = (LinearLayout) findViewById(R.id.footer_ll_huodong);
//		LinearLayout footer_renwu = (LinearLayout) findViewById(R.id.footer_ll_renwu);
//		LinearLayout footer_tanlun = (LinearLayout) findViewById(R.id.footer_ll_tanlun);
//
//		footer_dongtan.setOnClickListener(this);
//		footer_huodong.setOnClickListener(this);
//		footer_renwu.setOnClickListener(this);
//		footer_tanlun.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		/*ImageView footer_iv_dongtan = (ImageView) findViewById(R.id.footer_iv_dongtan);
		ImageView footer_iv_huodong = (ImageView) findViewById(R.id.footer_iv_huodong);
		ImageView footer_iv_renwu = (ImageView) findViewById(R.id.footer_iv_renwu);
		ImageView footer_iv_tanlun = (ImageView) findViewById(R.id.footer_iv_tanlun);*/
		switch (v.getId()) {
		case R.id.btn_waimai:
			flag = 0;
			checkButton(flag);
			loadData(flag);
			break;
		case R.id.btn_jianzhi:
			flag = 1;
			checkButton(flag);
			loadData(flag);
			break;
		case R.id.btn_kuaidi:
			flag = 2;
			checkButton(flag);
			loadData(flag);
			break;
		case R.id.btn_ershou:
			flag = 3;
			checkButton(flag);
			loadData(flag);
			break;
		case R.id.footer_ll_dongtan:
			/*footer_iv_dongtan.setImageResource(R.drawable.ic_main_dontan);
			footer_iv_huodong.setImageResource(R.drawable.ic_main_acticity_1);
			footer_iv_renwu.setImageResource(R.drawable.ic_detail_people_1);
			footer_iv_tanlun.setImageResource(R.drawable.ic_main_chat_1);*/
			LifeCenterActivity.this.finish();
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

			break;
		case R.id.footer_ll_huodong:
			/*footer_iv_dongtan.setImageResource(R.drawable.ic_main_dontan_1);
			footer_iv_huodong.setImageResource(R.drawable.ic_main_acticity);
			footer_iv_renwu.setImageResource(R.drawable.ic_detail_people_1);
			footer_iv_tanlun.setImageResource(R.drawable.ic_main_chat_1);*/
			startActivity(new Intent(LifeCenterActivity.this, HuoDongActivity.class));
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			LifeCenterActivity.this.finish();
			break;
		case R.id.footer_ll_renwu:
			/*footer_iv_dongtan.setImageResource(R.drawable.ic_main_dontan_1);
			footer_iv_huodong.setImageResource(R.drawable.ic_main_acticity_1);
			footer_iv_renwu.setImageResource(R.drawable.ic_detail_people);
			footer_iv_tanlun.setImageResource(R.drawable.ic_main_chat_1);*/
			startActivity(new Intent(LifeCenterActivity.this, DongtanActivity.class));
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			LifeCenterActivity.this.finish();
			break;
		case R.id.footer_ll_tanlun:

			RongIM.getInstance().startConversationList(LifeCenterActivity.this);
			/*mHandler.post(new Runnable() {
				@Override
				public void run() {
					RongIM.getInstance().startConversationList(LifeCenterActivity.this);
				}
			});*/

			// startActivity(new Intent(LifeCenterActivity.this,
			// CommuncationActivity.class));
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			/*
			 * Toast.makeText(LifeCenterActivity.this, "敬请期待",
			 * Toast.LENGTH_LONG).show();
			 */
			LifeCenterActivity.this.finish();
			break;

		default:
			break;
		}
	}

	public ResultInfo<FriendInfo> JsonParse(String jsonStr) {
		if (!StringUtils.isNullOrEmpty(jsonStr)) {

			try {
				ResultInfo<FriendInfo> result = new ResultInfo<FriendInfo>();
				JSONObject jsonObject = new JSONObject(jsonStr);
				String code = jsonObject.getString("Code");
				result.Code = code;
				ArrayList<FriendInfo> list = result.getList();

				JSONArray jsonArray = jsonObject.getJSONArray("Data");
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject2 = jsonArray.getJSONObject(i);
					FriendInfo friendInfo = new FriendInfo();
					friendInfo.UserId = jsonObject2.getString("UserId");
					friendInfo.StudentCard = jsonObject2.getString("StudentCard");
					friendInfo.UserName = jsonObject2.getString("UserName");
					friendInfo.Head = jsonObject2.getString("Head");
					friendInfo.PersonalDescription = jsonObject2.getString("PersonalDescription");
					friendInfo.FriendCount = jsonObject2.getString("FriendCount");
					friendInfo.FansCount = jsonObject2.getString("FansCount");
					friendInfo.CommonFriends = jsonObject2.getString("CommonFriends");
					friendInfo.ToUserId = jsonObject2.getString("ToUserId");
					friendInfo.AttentionType = jsonObject2.getString("AttentionType");
					list.add(friendInfo);
				}
				return result;

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private void loadData(final int flag) {
		customProgressDialog.show();
		new AsynHttpClient().post(AppConfig.HOSTURL + LIFE[flag],

		"data=" + getJson(flag), new HttpNetWorkDataHandler() {

			@Override
			public void success(int statusCode, Object obj) {
				if (customProgressDialog != null && customProgressDialog.isShowing() && LifeCenterActivity.this != null
						&& !LifeCenterActivity.this.isFinishing()) {

					customProgressDialog.dismiss();
				}
				System.out.println("==flag" + flag + "===" + obj.toString());
				switch (flag) {
				case 0:
					ResultInfo<WaiMainInfo> resultInfo = jsonParser(obj.toString());
					if (resultInfo != null) {
						list_waimai = resultInfo.getList();
						WaiMaiAdapter waiMaiAdapter = new WaiMaiAdapter(LifeCenterActivity.this, list_waimai);
						pulltorefrshListView_life.setAdapter(waiMaiAdapter);
						waiMaiAdapter.notifyDataSetChanged();
					}

					break;
				case 1:
					ResultInfo<JianZhiInfo> resultInfo1 = jsonParser1(obj.toString());
					if (resultInfo1 != null) {
						list_jianzhi = resultInfo1.getList();
						JianZhiAdapter jianzhiAdapter = new JianZhiAdapter(LifeCenterActivity.this, list_jianzhi);
						pulltorefrshListView_life.setAdapter(jianzhiAdapter);
						jianzhiAdapter.notifyDataSetChanged();
					}

					break;
				case 2:
					ResultInfo<KuaiDiInfo> resultInfo2 = jsonParser2(obj.toString());
					list_kuaidi = resultInfo2.getList();
					KuaiDiAdapter kuaidiAdapter = new KuaiDiAdapter(LifeCenterActivity.this, list_kuaidi);
					pulltorefrshListView_life.setAdapter(kuaidiAdapter);
					kuaidiAdapter.notifyDataSetChanged();
					break;
				case 3:
					ResultInfo<ErShouInfo> resultInfo3 = jsonParser3(obj.toString());
					list_ershou = resultInfo3.getList();
					ErShouAdapter ershouAdapter = new ErShouAdapter(LifeCenterActivity.this, list_ershou);
					pulltorefrshListView_life.setAdapter(ershouAdapter);
					ershouAdapter.notifyDataSetChanged();
					break;
				default:
					break;
				}
			}

			@Override
			public void failure(int statusCode, Object obj) {
				if (customProgressDialog != null && customProgressDialog.isShowing() && LifeCenterActivity.this != null
						&& !LifeCenterActivity.this.isFinishing()) {
					customProgressDialog.dismiss();
				}
				Toast.makeText(LifeCenterActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void checkButton(int flag) {
		btn_waimai.setBackgroundResource(R.drawable.bt_left_shape);
		btn_waimai.setTextColor(getResources().getColor(R.color.white));
		btn_jianzhi.setBackgroundResource(R.drawable.bt_center_shape);
		btn_jianzhi.setTextColor(getResources().getColor(R.color.white));
		btn_kuaidi.setBackgroundResource(R.drawable.bt_center_shape);
		btn_kuaidi.setTextColor(getResources().getColor(R.color.white));
		btn_ershou.setBackgroundResource(R.drawable.bt_right_shape);
		btn_ershou.setTextColor(getResources().getColor(R.color.white));

		switch (flag) {
		case 0:
			btn_waimai.setBackgroundResource(R.drawable.bt_left_click_shape);
			btn_waimai.setTextColor(getResources().getColor(R.color.baseColor));
			break;
		case 1:
			btn_jianzhi.setBackgroundResource(R.drawable.bt_center_click_shape);
			btn_jianzhi.setTextColor(getResources().getColor(R.color.baseColor));
			break;
		case 2:
			btn_kuaidi.setBackgroundResource(R.drawable.bt_center_click_shape);
			btn_kuaidi.setTextColor(getResources().getColor(R.color.baseColor));
			break;
		case 3:
			btn_ershou.setBackgroundResource(R.drawable.bt_right_click_shape);
			btn_ershou.setTextColor(getResources().getColor(R.color.baseColor));
			break;
		}
	}
}
