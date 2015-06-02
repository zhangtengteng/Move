package com.xuanit.move.activity;

import io.rong.imkit.RongIM;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.james.asynhttp.AsynHttpClient;
import com.james.asynhttp.HttpNetWorkDataHandler;
import com.xuanit.move.R;
import com.xuanit.move.adapter.FensiAdapter;
import com.xuanit.move.adapter.FriendsAdapter;
import com.xuanit.move.adapter.TuiJianAdapter;
import com.xuanit.move.app.AppConfig;
import com.xuanit.move.app.MoveApplication;
import com.xuanit.move.base.BaseActivity;
import com.xuanit.move.model.FriendInfo;
import com.xuanit.move.model.ResultInfo;
import com.xuanit.move.model.TuiJianPersonInfo;
import com.xuanit.move.util.CloseAllActivityManager;
import com.xuanit.move.util.StringUtils;
import com.xuanit.move.view.CustomProgressDialog;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DongtanActivity extends BaseActivity {

	private Button bt_dongtan_haoyou;
	private Button bt_dongtan_fensi;
	private Button bt_dongtan_tuijian;
	private TextView tv_dongtan_tip;
	private PullToRefreshListView pulltorefrshListView_dongtan;
	private int flag = 0;
	private static final String GETCONTACTPERSON = "PhoneFriendsFans/GetContactPerson";
	private static final String GETBYUSERISREFERRE = "PhoneUser/GetByUserIsReferre";
	private ArrayList<FriendInfo> list_haoyou = new ArrayList<FriendInfo>();
	private ArrayList<FriendInfo> list_fensi = new ArrayList<FriendInfo>();
	private ArrayList<TuiJianPersonInfo> list_tuijian = new ArrayList<TuiJianPersonInfo>();
	private LinearLayout ll_header_right;
	private int PageNo = 1;
	private final int PageSize = 25;
	private int count;
	private boolean isLoadOver = false;
	private MoveApplication application;
	private String userFriendCount;
	private String userUserId;
	private int GoAddFriendAndFensi = 0X0001;

	// private Handler mHandler = new Handler();
	/*
	 * private ArrayList<JianZhiInfo> list_fensi= new ArrayList<JianZhiInfo>();
	 * private ArrayList<KuaiDiInfo> list_tuijian= new ArrayList<KuaiDiInfo>();
	 * private ArrayList<ErShouInfo> list_fenzu= new ArrayList<ErShouInfo>();
	 */
	private CustomProgressDialog customProgressDialog;

	// private SharedPreferences sharedPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		CloseAllActivityManager.getInstance().addActivity(DongtanActivity.this);
		super.onCreate(savedInstanceState);
		application = (MoveApplication) getApplication();
		userFriendCount = application.appConfig.FriendCount;
		userUserId = application.appConfig.USER_ID;
		setView(R.layout.activity_dongtan);
		setTitle("排序", "好友(" + userFriendCount + ")", "");

		// sharedPreferences = getSharedPreferences("User_Message_File",
		// Context.MODE_PRIVATE);
		// AppConfig.USER_ID = sharedPreferences.getString("UserId", "");
		initView();
		// customProgressDialog.show();
		loadData(flag);
	}

	/*@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		isLoadOver = false;
		list_tuijian.clear();
		list_fensi.clear();
		list_haoyou.clear();
		customProgressDialog.show();
	}*/

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		
		if(resultCode == 0X0002){
			
			isLoadOver = false;
			list_tuijian.clear();
			list_fensi.clear();
			list_haoyou.clear();
			customProgressDialog.show();
			
			if (flag != 2) {
				loadData(flag);
			} else {
				loadTuiJianData();
			}
		}	
		super.onActivityResult(requestCode, resultCode, data);
	}

	// 好友 粉丝
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
						userFriendCount = object.getString("FriendCount");
						application.appConfig.FansCount = object.getString("FansCount");
						application.appConfig.TotalCount = object.getString("TotalCount");
						count = Integer.valueOf(object.getString("TotalCount"));
						switch (flag) {
						case 0:
							setTitle("排序", "好友(" + userFriendCount + ")", "添加");
							break;
						case 1:
							setTitle("排序", "粉丝(" + userFriendCount + ")", "添加");
							break;

						default:
							break;
						}

						String ContactUsers = object.getString("ContactUsers");
						JSONArray jsonArray = new JSONArray(ContactUsers);

						System.out.println("jsonArray--------------------" + jsonArray);
						System.out.println("jsonArray.length()--------------------" + jsonArray.length());
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

	// 推荐
	@SuppressLint("NewApi")
	private ResultInfo<TuiJianPersonInfo> jsonParserTuiJian(String result) {

		if (!StringUtils.isNullOrEmpty(result)) {
			JSONObject jsonObject;
			try {
				jsonObject = new JSONObject(result);
				System.out.println("*********!!!!!!!!" + jsonObject.toString());
				String code = jsonObject.getString("Code");
				ResultInfo<TuiJianPersonInfo> resultInfo = new ResultInfo<TuiJianPersonInfo>();
				resultInfo.Code = code;
				ArrayList<TuiJianPersonInfo> list_tuiJian = resultInfo.getList();
				if(list_tuiJian != null ){
					String data = jsonObject.getString("Data");
					
					if (code.equals("1")) {
						// JSONObject object = new JSONObject(data);
						setTitle("", "推荐", "");

						JSONArray jsonArray = new JSONArray(data);
						JSONArray jsData = jsonArray.getJSONArray(0);
						// JSONObject jsObject = jsData.getJSONObject(0);

						System.out.println("jsonArray-- " + jsData);
						System.out.println("jsonArray.length()--- " + jsData.length());

						String arr[] = new String[jsData.length()];
						for (int i = 0; i < jsData.length(); i++) {

							arr[i] = jsData.getString(i);
							JSONObject o = new JSONObject(arr[i]);
							
							TuiJianPersonInfo info = new TuiJianPersonInfo();

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
							info.MoveCount = o.getString("MoveCount");// 动弹数
							info.Status = o.getString("Status");
							info.CreateTime = o.getString("CreateTime");
							info.CreateUser = o.getString("CreateUser");
							info.LastChangeTime = o.getString("LastChangeTime");
							info.LastChangeUser = o.getString("LastChangeUser");
							info.ActId = o.getString("ActId");

							list_tuiJian.add(info);
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

	private String getJson(int flag) {
		JSONObject o = new JSONObject();
		switch (flag) {
		case 0:
			try {
				if (!StringUtils.isNullOrEmpty(userUserId)) {
					o.put("PageNo", PageNo + "");
					o.put("PageSize", PageSize + "");
					o.put("UserId", userUserId);
					o.put("AttentionType", "1");
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case 1:
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
			break;

		default:
			break;
		}
		return o.toString();
	}

	private void initView() {
		customProgressDialog = CustomProgressDialog.createDialog(this);
		bt_dongtan_haoyou = (Button) findViewById(R.id.bt_dongtan_haoyou);
		bt_dongtan_tuijian = (Button) findViewById(R.id.bt_dongtan_tuijian);
		bt_dongtan_fensi = (Button) findViewById(R.id.bt_dongtan_fensi);
		tv_dongtan_tip = (TextView) findViewById(R.id.tv_dongtan_tip);
		ll_header_right = (LinearLayout) findViewById(R.id.ll_header_right);
		pulltorefrshListView_dongtan = (PullToRefreshListView) findViewById(R.id.pulltorefrshListView_dongtan);

		pulltorefrshListView_dongtan.setMode(Mode.PULL_FROM_END);
		pulltorefrshListView_dongtan.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				if (!isLoadOver) {
					PageNo++;
				}
				if (flag != 2) {
					loadData(flag);
				} else {
					loadTuiJianData();
				}
			}
		});

		bt_dongtan_haoyou.setOnClickListener(this);
		bt_dongtan_tuijian.setOnClickListener(this);
		bt_dongtan_fensi.setOnClickListener(this);
	}

	@SuppressLint("ResourceAsColor")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.iv_header_left:
			DongtanActivity.this.finish();
			break;
		case R.id.bt_dongtan_haoyou:
			ll_header_right.setVisibility(View.VISIBLE);
			tv_dongtan_tip.setVisibility(View.GONE);
			flag = 0;
			PageNo = 1;
			count = 0;
			checkButton(flag);
			isLoadOver = false;
			customProgressDialog.show();
			list_haoyou.clear();
			loadData(flag);
			break;
		case R.id.bt_dongtan_fensi:
			ll_header_right.setVisibility(View.VISIBLE);
			tv_dongtan_tip.setVisibility(View.GONE);
			flag = 1;
			PageNo = 1;
			count = 0;
			checkButton(flag);
			isLoadOver = false;
			list_fensi.clear();
			customProgressDialog.show();
			loadData(flag);
			break;
		case R.id.bt_dongtan_tuijian:
			flag = 2;
			PageNo = 1;
			count = 0;
			ll_header_right.setVisibility(View.INVISIBLE);
			tv_dongtan_tip.setVisibility(View.GONE);
			checkButton(flag);
			isLoadOver = false;
			customProgressDialog.show();
			list_tuijian.clear();
			loadTuiJianData();
			break;

		case R.id.ll_header_right:
			//等相关接口改好后，此处需跳转到 SelectUserAndAddActivity 进行相关处理
			//startActivityForResult(new Intent(DongtanActivity.this, SelectUserAndAddActivity.class), 0X0002);
			startActivityForResult(new Intent(DongtanActivity.this, AddFriendAndFensiActivity.class), GoAddFriendAndFensi);
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			break;

		case R.id.footer_ll_dongtan:
			DongtanActivity.this.finish();

			/*
			 * footer_iv_dongtan.setImageResource(R.drawable.ic_main_dontan);
			 * footer_iv_huodong
			 * .setImageResource(R.drawable.ic_main_acticity_1);
			 * footer_iv_renwu.setImageResource(R.drawable.ic_detail_people_1);
			 * footer_iv_tanlun.setImageResource(R.drawable.ic_main_chat_1);
			 * footer_tv_dongtan.setTextColor(R.color.baseColor);
			 * footer_tv_huodong.setTextColor(R.color.footer_text_color);
			 * footer_tv_renwu.setTextColor(R.color.footer_text_color);
			 * footer_tv_tanlun.setTextColor(R.color.footer_text_color);
			 * 
			 * View next =
			 * LayoutInflater.from(DongtanActivity.this).inflate(R.layout
			 * .activity_dongtan, null);
			 * 
			 * ImageView next_iv_dongtan = (ImageView)
			 * next.findViewById(R.id.footer_iv_dongtan); ImageView
			 * next_iv_huodong = (ImageView)
			 * next.findViewById(R.id.footer_iv_huodong); ImageView
			 * next_iv_renwu = (ImageView)
			 * next.findViewById(R.id.footer_iv_renwu); ImageView next_iv_tanlun
			 * = (ImageView) next.findViewById(R.id.footer_iv_tanlun); TextView
			 * next_tv_dongtan = (TextView)
			 * next.findViewById(R.id.footer_tv_dongtan); TextView
			 * next_tv_huodong = (TextView)
			 * next.findViewById(R.id.footer_tv_huodong); TextView next_tv_renwu
			 * = (TextView) next.findViewById(R.id.footer_tv_renwu); TextView
			 * next_tv_tanlun = (TextView)
			 * next.findViewById(R.id.footer_tv_tanlun);
			 * 
			 * next_iv_dongtan.setImageResource(R.drawable.ic_main_dontan);
			 * next_iv_huodong.setImageResource(R.drawable.ic_main_acticity_1);
			 * next_iv_renwu.setImageResource(R.drawable.ic_detail_people_1);
			 * next_iv_tanlun.setImageResource(R.drawable.ic_main_chat_1);
			 * next_tv_dongtan.setTextColor(R.color.baseColor);
			 * next_tv_huodong.setTextColor(R.color.footer_text_color);
			 * next_tv_renwu.setTextColor(R.color.footer_text_color);
			 * next_tv_tanlun.setTextColor(R.color.footer_text_color);
			 */

			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

			break;
		case R.id.footer_ll_huodong:
			startActivity(new Intent(DongtanActivity.this, HuoDongActivity.class));
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			/*
			 * footer_iv_dongtan.setImageResource(R.drawable.ic_main_dontan_1);
			 * footer_iv_huodong.setImageResource(R.drawable.ic_main_acticity);
			 * footer_iv_renwu.setImageResource(R.drawable.ic_detail_people_1);
			 * footer_iv_tanlun.setImageResource(R.drawable.ic_main_chat_1);
			 * footer_tv_dongtan.setTextColor(R.color.footer_text_color);
			 * footer_tv_huodong.setTextColor(R.color.baseColor);
			 * footer_tv_renwu.setTextColor(R.color.footer_text_color);
			 * footer_tv_tanlun.setTextColor(R.color.footer_text_color);
			 */

			DongtanActivity.this.finish();
			break;
		case R.id.footer_ll_renwu:
			startActivity(new Intent(DongtanActivity.this, DongtanActivity.class));

			/*
			 * footer_iv_dongtan.setImageResource(R.drawable.ic_main_dontan_1);
			 * footer_iv_huodong
			 * .setImageResource(R.drawable.ic_main_acticity_1);
			 * footer_iv_renwu.setImageResource(R.drawable.ic_detail_people);
			 * footer_iv_tanlun.setImageResource(R.drawable.ic_main_chat_1);
			 * footer_tv_dongtan.setTextColor(R.color.footer_text_color);
			 * footer_tv_huodong.setTextColor(R.color.footer_text_color);
			 * footer_tv_renwu.setTextColor(R.color.baseColor);
			 * footer_tv_tanlun.setTextColor(R.color.footer_text_color);
			 */

			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			DongtanActivity.this.finish();
			break;
		case R.id.footer_ll_tanlun:
			RongIM.getInstance().startConversationList(DongtanActivity.this);
			/*
			 * mHandler.post(new Runnable() {
			 * 
			 * @Override public void run() {
			 * RongIM.getInstance().startConversationList(DongtanActivity.this);
			 * } });
			 */
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

			DongtanActivity.this.finish();
			break;

		default:
			break;
		}
	}

	private void loadTuiJianData() {

		String addressAndMethord = AppConfig.HOSTURL + GETBYUSERISREFERRE;

		new AsynHttpClient().get(addressAndMethord, new HttpNetWorkDataHandler() {
			@Override
			public void success(int statusCode, Object obj) {
				pulltorefrshListView_dongtan.onRefreshComplete();
				if (customProgressDialog != null && customProgressDialog.isShowing() && DongtanActivity.this != null
						&& !DongtanActivity.this.isFinishing()) {
					customProgressDialog.dismiss();
				}
				System.out.println("==flag" + flag + "===" + obj.toString());

				ResultInfo<TuiJianPersonInfo> tuiJianresultInfo = jsonParserTuiJian(obj.toString());

				if (list_tuijian != null && tuiJianresultInfo != null) {

					TuiJianAdapter tuiJianAdapter = new TuiJianAdapter(DongtanActivity.this, list_tuijian);
					pulltorefrshListView_dongtan.setAdapter(tuiJianAdapter);

					/*if (PageNo == 1) {
						tv_dongtan_tip.setVisibility(View.VISIBLE);
						tv_dongtan_tip.setText("还没有推荐的人物~:)");
					} else {
						tv_dongtan_tip.setVisibility(View.GONE);
					}*/
					
					if (!isLoadOver) {
						list_tuijian.addAll(tuiJianresultInfo.getList());
					}
					if ((PageNo * PageSize) >= count && count != 0) {
						isLoadOver = true;
					}

					tuiJianAdapter.notifyDataSetChanged();
				}

			}

			@Override
			public void failure(int statusCode, Object obj) {
				pulltorefrshListView_dongtan.onRefreshComplete();
				if (customProgressDialog != null && customProgressDialog.isShowing() && DongtanActivity.this != null
						&& !DongtanActivity.this.isFinishing()) {
					customProgressDialog.dismiss();
				}
				Toast.makeText(DongtanActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void loadData(final int flag) {

		String addressAndMethord = null;
		switch (flag) {
		case 0:
			addressAndMethord = AppConfig.HOSTURL + GETCONTACTPERSON;
			break;
		case 1:
			addressAndMethord = AppConfig.HOSTURL + GETCONTACTPERSON;
			break;
		default:
			break;
		}

		new AsynHttpClient().post(addressAndMethord, "data=" + getJson(flag), new HttpNetWorkDataHandler() {
			@Override
			public void success(int statusCode, Object obj) {
				pulltorefrshListView_dongtan.onRefreshComplete();
				if (customProgressDialog != null && customProgressDialog.isShowing() && DongtanActivity.this != null
						&& !DongtanActivity.this.isFinishing()) {
					customProgressDialog.dismiss();
				}
				switch (flag) {
				case 0:
					ResultInfo<FriendInfo> resultInfo = jsonParser(obj.toString());
					if (resultInfo != null && resultInfo != null) {

						if (list_haoyou != null && !StringUtils.isNullOrEmpty(resultInfo.Code)
								&& "1".equals(resultInfo.Code)) {

							FriendsAdapter friendAdapter = new FriendsAdapter(DongtanActivity.this, list_haoyou);
							pulltorefrshListView_dongtan.setAdapter(friendAdapter);

							if (PageNo == 1 && count == 0) {
								tv_dongtan_tip.setVisibility(View.VISIBLE);
								tv_dongtan_tip.setText("还没有好友哟~:)");
							} else {
								tv_dongtan_tip.setVisibility(View.GONE);
							}
							if (!isLoadOver) {
								list_haoyou.addAll(resultInfo.getList());
							}
							if ((PageNo * PageSize) >= count && count != 0) {
								isLoadOver = true;
							}
							friendAdapter.notifyDataSetChanged();
						}
					}

					break;
				case 1:
					ResultInfo<FriendInfo> fensiResultInfo = jsonParser(obj.toString());
					if (list_fensi != null && fensiResultInfo != null
							&& !StringUtils.isNullOrEmpty(fensiResultInfo.Code) && "1".equals(fensiResultInfo.Code)) {

						FensiAdapter fensiAdapter = new FensiAdapter(DongtanActivity.this, list_fensi);
						pulltorefrshListView_dongtan.setAdapter(fensiAdapter);
						if (PageNo == 1 && count == 0) {

							tv_dongtan_tip.setVisibility(View.VISIBLE);
							tv_dongtan_tip.setText("还没有粉丝哟！:)");
						} else {
							tv_dongtan_tip.setVisibility(View.GONE);
						}

						if (!isLoadOver) {
							list_fensi.addAll(fensiResultInfo.getList());
						}
						if ((PageNo * PageSize) >= count && count != 0) {
							// Toast.makeText(DongtanActivity.this, "已经全部加载",
							// 1).show();
							isLoadOver = true;
						}

						fensiAdapter.notifyDataSetChanged();
					}
					// item_friend_chat.setClickable(false);
					break;

				default:
					break;
				}

			}

			@Override
			public void failure(int statusCode, Object obj) {
				pulltorefrshListView_dongtan.onRefreshComplete();
				if (customProgressDialog != null && customProgressDialog.isShowing() && DongtanActivity.this != null
						&& !DongtanActivity.this.isFinishing()) {
					customProgressDialog.dismiss();
				}
				Toast.makeText(DongtanActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void checkButton(int flag) {
		bt_dongtan_haoyou.setBackgroundResource(R.drawable.bt_left_shape);
		bt_dongtan_haoyou.setTextColor(getResources().getColor(R.color.white));
		bt_dongtan_tuijian.setBackgroundResource(R.drawable.bt_right_shape);
		bt_dongtan_tuijian.setTextColor(getResources().getColor(R.color.white));
		bt_dongtan_fensi.setBackgroundResource(R.drawable.bt_center_shape);
		bt_dongtan_fensi.setTextColor(getResources().getColor(R.color.white));

		switch (flag) {
		case 0:
			bt_dongtan_haoyou.setBackgroundResource(R.drawable.bt_left_click_shape);
			bt_dongtan_haoyou.setTextColor(getResources().getColor(R.color.baseColor));
			break;
		case 1:
			bt_dongtan_fensi.setBackgroundResource(R.drawable.bt_center_click_shape);
			bt_dongtan_fensi.setTextColor(getResources().getColor(R.color.baseColor));
			break;
		case 2:
			bt_dongtan_tuijian.setBackgroundResource(R.drawable.bt_right_click_shape);
			bt_dongtan_tuijian.setTextColor(getResources().getColor(R.color.baseColor));
			break;
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		DongtanActivity.this.finish();
	}

}
