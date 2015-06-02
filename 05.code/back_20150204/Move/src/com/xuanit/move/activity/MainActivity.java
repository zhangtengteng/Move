package com.xuanit.move.activity;

import io.rong.imkit.RongIM;
import io.rong.imkit.RongIM.GetUserInfoProvider;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.RongIMClient.UserInfo;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.activity.AbActivity;
import com.ab.adapter.AbFragmentPagerAdapter;
import com.ab.view.slidingmenu.SlidingMenu;
import com.ab.view.titlebar.AbTitleBar;
import com.james.asynhttp.AsynHttpClient;
import com.james.asynhttp.HttpNetWorkDataHandler;
import com.xuanit.move.R;
import com.xuanit.move.app.AppConfig;
import com.xuanit.move.app.MoveApplication;
import com.xuanit.move.fragment.HuoDongFragment;
import com.xuanit.move.fragment.MainFragment;
import com.xuanit.move.fragment.RenWuFragment;
import com.xuanit.move.fragment.RightFragment;
import com.xuanit.move.fragment.TanLunFragment;
import com.xuanit.move.model.FriendInfo;
import com.xuanit.move.model.ResultInfo;
import com.xuanit.move.util.CloseAllActivityManager;
import com.xuanit.move.util.StringUtils;

public class MainActivity extends AbActivity implements OnClickListener, OnPageChangeListener {
	private AbTitleBar mAbTitleBar = null;
	private final int RE_TO_SPEECH = 0X101;
	private final int RE_TO_HUODONGAPPLY = 0X102;

	private SlidingMenu menu;
	private ImageView iv_header_left;
	private TextView tv_header_middle;
	private ImageView tv_header_right;
	private ViewPager vp_main;

	private LinearLayout footer_ll_dongtan;
	private LinearLayout footer_ll_huodong;
	private LinearLayout footer_ll_renwu;
	private LinearLayout footer_ll_tanlun;
	private ImageView footer_iv_dongtan;
	private ImageView footer_iv_huodong;
	private ImageView footer_iv_renwu;
	private ImageView footer_iv_tanlun;
	private TextView footer_tv_dongtan;
	private TextView footer_tv_huodong;
	private TextView footer_tv_renwu;
	private TextView footer_tv_tanlun;
	private MoveApplication application;
	private String userUserId;

	private AbFragmentPagerAdapter pagerAdapter;

	public String UserId;
	Handler mHandler = new Handler();
	public List<FriendInfo> list_friend = new ArrayList<FriendInfo>();
	public String token;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		CloseAllActivityManager.getInstance().addActivity(MainActivity.this);
		super.onCreate(savedInstanceState);
		application = (MoveApplication) getApplication();
		userUserId = application.appConfig.USER_ID;
		getToken();

		if (!StringUtils.isNullOrEmpty(userUserId)) {
			UserId = userUserId;

		}

		setAbContentView(R.layout.activity_main);

		mAbTitleBar = getTitleBar();
		mAbTitleBar.setVisibility(View.GONE);

		initView();
		registListener();

		ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
		fragmentList.add(new MainFragment());
		fragmentList.add(new HuoDongFragment());
		fragmentList.add(new RenWuFragment());
		fragmentList.add(new TanLunFragment());

		pagerAdapter = new AbFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
		vp_main.setAdapter(pagerAdapter);

		// SlidingMenu的配置
		menu = new SlidingMenu(this);
		menu.setMode(SlidingMenu.LEFT);

		// slidingmenu的事件模式，如果里面有可以滑动的请用TOUCHMODE_MARGIN
		// 可解决事件冲突问题
		// menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setShadowDrawable(R.drawable.shadow);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setFadeDegree(0.35f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		menu.setMenu(R.layout.fragment_right);
		getSupportFragmentManager().beginTransaction().replace(R.id.fragment_right, new RightFragment()).commit();
		menu.setSecondaryMenu(R.layout.fragment_right);
		getSupportFragmentManager().beginTransaction().replace(R.id.fragment_right, new RightFragment()).commit();

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		menu.showContent();
	}

	private void initView() {
		iv_header_left = (ImageView) findViewById(R.id.iv_header_left);
		tv_header_right = (ImageView) findViewById(R.id.tv_header_right);
		tv_header_middle = (TextView) findViewById(R.id.tv_header_middle);
		vp_main = (ViewPager) findViewById(R.id.vp_main);

		tv_header_middle.setText("动弹一下");

		// 处理Footer
		footer_ll_dongtan = (LinearLayout) findViewById(R.id.footer_ll_dongtan);
		footer_ll_huodong = (LinearLayout) findViewById(R.id.footer_ll_huodong);
		footer_ll_renwu = (LinearLayout) findViewById(R.id.footer_ll_renwu);
		footer_ll_tanlun = (LinearLayout) findViewById(R.id.footer_ll_tanlun);

		footer_iv_dongtan = (ImageView) findViewById(R.id.footer_iv_dongtan);
		footer_iv_huodong = (ImageView) findViewById(R.id.footer_iv_huodong);
		footer_iv_renwu = (ImageView) findViewById(R.id.footer_iv_renwu);
		footer_iv_tanlun = (ImageView) findViewById(R.id.footer_iv_tanlun);
		footer_tv_dongtan = (TextView) findViewById(R.id.footer_tv_dongtan);
		footer_tv_huodong = (TextView) findViewById(R.id.footer_tv_huodong);
		footer_tv_renwu = (TextView) findViewById(R.id.footer_tv_renwu);
		footer_tv_tanlun = (TextView) findViewById(R.id.footer_tv_tanlun);

		footer_ll_dongtan.setOnClickListener(this);
		footer_ll_huodong.setOnClickListener(this);
		footer_ll_renwu.setOnClickListener(this);
		footer_ll_tanlun.setOnClickListener(this);
	}

	private void registListener() {
		iv_header_left.setOnClickListener(this);
		tv_header_right.setOnClickListener(this);
		vp_main.setOnPageChangeListener(this);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iv_header_left:
			if (menu.isMenuShowing()) {
				menu.showContent();
			} else {
				menu.showMenu();
			}
			break;

		case R.id.tv_header_right:
			if (vp_main.getCurrentItem() == 0) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, SpeechActivity.class);
				startActivityForResult(intent, RE_TO_SPEECH);
			} else if (vp_main.getCurrentItem() == 1) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, HuoDongApplyActivity.class);
				startActivityForResult(intent, RE_TO_HUODONGAPPLY);
			}

			break;
		case R.id.footer_ll_dongtan:
			if (vp_main.getCurrentItem() != 0) {
				vp_main.setCurrentItem(0);
				checkButton(0);
			}
			break;
		case R.id.footer_ll_huodong:
			if (vp_main.getCurrentItem() != 1) {
				vp_main.setCurrentItem(1);
				checkButton(1);
			}
			break;
		case R.id.footer_ll_renwu:
			if (vp_main.getCurrentItem() != 2) {
				vp_main.setCurrentItem(2);
				checkButton(2);
			}
			break;
		case R.id.footer_ll_tanlun:
			if (vp_main.getCurrentItem() != 3) {
				vp_main.setCurrentItem(3);
				checkButton(3);
			}
			break;

		default:
			break;
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if (menu.isMenuShowing()) {
			menu.toggle();
		}

	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);
		// 发表完成后主页刷新

	}

	private long exitTime = 0;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				CloseAllActivityManager.getInstance().exit();
				System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onPageSelected(int position) {
		// TODO Auto-generated method stub
		switch (position) {

		case 0:
			checkButton(position);
			tv_header_right.setVisibility(View.VISIBLE);
			break;
		case 1:
			checkButton(position);
			tv_header_right.setVisibility(View.VISIBLE);
			break;
		case 2:
			checkButton(position);
			tv_header_right.setVisibility(View.GONE);
			break;
		case 3:
			checkButton(position);
			tv_header_right.setVisibility(View.GONE);
			break;
		default:
			break;
		}

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	/**
	 * 描述:设置按钮的不同状态
	 * 
	 * */
	private void checkButton(int tag) {

		footer_iv_dongtan.setImageDrawable(getResources().getDrawable(R.drawable.ic_main_dontan_1));
		footer_iv_huodong.setImageDrawable(getResources().getDrawable(R.drawable.ic_main_acticity_1));
		footer_iv_renwu.setImageDrawable(getResources().getDrawable(R.drawable.ic_detail_people_1));
		footer_iv_tanlun.setImageDrawable(getResources().getDrawable(R.drawable.ic_main_chat_1));
		footer_tv_dongtan.setTextColor(getResources().getColor(R.color.footer_text_color));
		footer_tv_huodong.setTextColor(getResources().getColor(R.color.footer_text_color));
		footer_tv_renwu.setTextColor(getResources().getColor(R.color.footer_text_color));
		footer_tv_tanlun.setTextColor(getResources().getColor(R.color.footer_text_color));

		switch (tag) {
		case 0:
			footer_iv_dongtan.setImageDrawable(getResources().getDrawable(R.drawable.ic_main_dontan));
			footer_tv_dongtan.setTextColor(getResources().getColor(R.color.baseColor));
			break;
		case 1:
			footer_iv_huodong.setImageDrawable(getResources().getDrawable(R.drawable.ic_main_acticity));
			footer_tv_huodong.setTextColor(getResources().getColor(R.color.baseColor));
			break;
		case 2:
			footer_iv_renwu.setImageDrawable(getResources().getDrawable(R.drawable.ic_detail_people));
			footer_tv_renwu.setTextColor(getResources().getColor(R.color.baseColor));
			break;
		case 3:
			footer_iv_tanlun.setImageDrawable(getResources().getDrawable(R.drawable.ic_main_chat));
			footer_tv_tanlun.setTextColor(getResources().getColor(R.color.baseColor));
			break;
		}
	}

	public void getToken() {
		/*
		 * new AsynHttpClient().post(AppConfig.HOSTURL + "PhoneChat/GetToken",
		 * "UserId=" + AppConfig.USER_ID, new HttpNetWorkDataHandler() {
		 */
		System.out.println("PhoneChat/GetToken?UserId=" + userUserId);
		new AsynHttpClient().get(AppConfig.HOSTURL + "PhoneChat/GetToken?UserId=" + userUserId,
				new HttpNetWorkDataHandler() {

					@Override
					public void success(int statusCode, Object obj) {
						try {

							System.out.println("377行++++getToken()--success" + "执行");
							JSONObject object = new JSONObject(obj.toString());
							String code = object.getString("Code");
							if (code.equals("1")) {
								System.out.println("381行++++获取token对象" + "执行开始");
								token = object.getString("Data");
								System.out.println("383行++++获取token对象" + "执行结束");
								System.out.println("384行++++获取friend对象" + "执行开始");
								getFriend();
								System.out.println("386行++++获取friend对象" + "执行结束");
							} else {
								System.out.println("MainActivity.this" + "网络出错");
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
						System.out.println("393行++++getToken()--success" + "执行结束");
					}

					@Override
					public void failure(int statusCode, Object obj) {
					}
				});
	}

	public void getFriend() {
		new AsynHttpClient().get(AppConfig.HOSTURL + "PhoneFriendsFans/GetFriends?UserId=" + userUserId,
				new HttpNetWorkDataHandler() {
					@Override
					public void success(int statusCode, Object obj) {
						System.out.println("407行++++getFriend Success" + "执行开始");
						Log.i("MainActivity", "communcateData==" + obj.toString());
						ResultInfo<FriendInfo> resultInfo = JsonParse(obj.toString());
						if (!StringUtils.isNullOrEmpty(resultInfo.Code) && "1".equals(resultInfo.Code)
								&& resultInfo != null) {
							list_friend = resultInfo.getList();
							System.out.println("413行++++getFriend list_friend" + list_friend);
							System.out.println("414行++++getFriend+++init()开始");

							init();
							System.out.println("416行++++getFriend+++init()结束");
							// btn_haoyou.setEnabled(true);
						}
					}

					@Override
					public void failure(int statusCode, Object obj) {
					}
				});
	}

	public ResultInfo<FriendInfo> JsonParse(String jsonStr) {
		if (!StringUtils.isNullOrEmpty(jsonStr)) {

			try {
				ResultInfo<FriendInfo> result = new ResultInfo<FriendInfo>();
				JSONObject jsonObject = new JSONObject(jsonStr);
				result.Code = jsonObject.getString("Code");
				if ("1".endsWith(result.Code)) {
					ArrayList<FriendInfo> list = new ArrayList<FriendInfo>();
					JSONArray jsonArray = new JSONArray(jsonObject.getString("Data"));
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject2 = jsonArray.getJSONObject(i);
						FriendInfo friendInfo = new FriendInfo();
						friendInfo.UserId = jsonObject2.getString("UserId");
						friendInfo.UserName = jsonObject2.getString("UserName");
						friendInfo.StudentCard = jsonObject2.getString("StudentCard");
						friendInfo.Head = jsonObject2.getString("Head");
						friendInfo.PersonalDescription = jsonObject2.getString("PersonalDescription");
						friendInfo.FriendCount = jsonObject2.getString("FriendCount");
						friendInfo.FansCount = jsonObject2.getString("FansCount");
						friendInfo.CommonFriends = jsonObject2.getString("CommonFriends");
						friendInfo.ToUserId = jsonObject2.getString("ToUserId");
						friendInfo.AttentionType = jsonObject2.getString("AttentionType");
						friendInfo.CollegeName = jsonObject2.getString("CollegeName");

						list.add(friendInfo);

					}
					result.setList(list);
					return result;
				} else {
					return null;
				}

			} catch (JSONException e) {
				e.printStackTrace();
				Log.i("MainActivity", "JSON解析异常");
			}
		}
		return null;
	}

	public void init() {
		if (null == token || token.equals("")) {
			return;
		}

		System.out.println("474行++++RongIM.setGetFriendsProvider+++开始");
		// 设置用户信息提供者。
		RongIM.setGetUserInfoProvider(new GetUserInfoProvider() {

			@Override
			public UserInfo getUserInfo(String userId) {
				// TODO Auto-generated method stub

				for (int i = 0; i < list_friend.size(); i++) {
					FriendInfo friendInfo = list_friend.get(i);
					if (userId == friendInfo.UserId) {
						RongIMClient.UserInfo user = new RongIMClient.UserInfo(friendInfo.UserId, friendInfo.UserName,
								friendInfo.Head);
						return user;
					}

				}
				return null;
			}
		}, false);

		// 设置好友信息提供者。
		RongIM.setGetFriendsProvider(new RongIM.GetFriendsProvider() {

			@Override
			public List<RongIMClient.UserInfo> getFriends() {
				// 返回 App 的好友列表给 IMKit 界面组件，供会话列表页中选择好友时使用。
				List<RongIMClient.UserInfo> list = new ArrayList<RongIMClient.UserInfo>();
				for (int i = 0; i < list_friend.size(); i++) {
					FriendInfo friendInfo = list_friend.get(i);
					if (!StringUtils.isNullOrEmpty(friendInfo.Head)
							&& friendInfo.Head.startsWith("http://121.40.197.169:8085/UpLoadFile/Face/")) {
						RongIMClient.UserInfo user1 = new RongIMClient.UserInfo(friendInfo.UserId, friendInfo.UserName,
								friendInfo.Head);
						list.add(user1);
					}
				}
				return list;
			}
		});
		System.out.println("513行++++RongIM.setGetFriendsProvider+++结束");

		try {
			System.out.println("516行++++RongIM.connect+++开始");
			RongIM.connect(token, new RongIMClient.ConnectCallback() {
				@Override
				public void onSuccess(String s) {
					Log.d("Connect", "Login successfully.");
					System.out.println("520行++++Login successfully.");
				}

				@Override
				public void onError(ErrorCode errorCode) {
					Log.d("Connect", "Login failed.");
					System.out.println("527行++++Login failed.");
				}
			});
			System.out.println("530行++++RongIM.connect+++结束");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
