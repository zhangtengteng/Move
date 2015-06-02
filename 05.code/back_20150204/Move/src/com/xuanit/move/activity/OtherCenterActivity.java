package com.xuanit.move.activity;

import org.json.JSONException;
import org.json.JSONObject;

import io.rong.imkit.RongIM;

import com.ab.bitmap.AbImageDownloader;
import com.james.asynhttp.AsynHttpClient;
import com.james.asynhttp.HttpNetWorkDataHandler;
import com.xuanit.move.R;
import com.xuanit.move.app.AppConfig;
import com.xuanit.move.app.MoveApplication;
import com.xuanit.move.base.BaseActivity;
import com.xuanit.move.model.AddPersonInfo;
import com.xuanit.move.model.FriendInfo;
import com.xuanit.move.model.ResultInfo;
import com.xuanit.move.util.CloseAllActivityManager;
import com.xuanit.move.util.StringUtils;
import com.xuanit.move.view.CircleImageView;
import com.xuanit.move.view.CustomProgressDialog;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class OtherCenterActivity extends BaseActivity implements OnClickListener {
	private String otherUserId;
	private AddPersonInfo other = new AddPersonInfo();
	private CircleImageView iv_user_heade;
	private TextView tv_guanzhu_count;
	private TextView tv_haoyou_count;
	private TextView tv_start_count;
	private TextView tv_user_name;
	private TextView tv_user_desc;
	private TextView tv_look_dongtan;
	private TextView tv_look_houdong;
	private TextView tv_chat_haoyou;
	private TextView tv_guanzhu;
	private String GETFRIENDS = "PhoneFriendsFans/Attention";
	private TextView tv_user_school;
	private TextView tv_user_dream;
	private TextView tv_user_favor;
	private TextView tv_user_brithday;
	private AbImageDownloader mAbImageDownloader;
	private CustomProgressDialog customProgressDialog;
	private Toast toast;
	private MoveApplication application;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		CloseAllActivityManager.getInstance().addActivity(OtherCenterActivity.this);
		super.onCreate(savedInstanceState);
		application = (MoveApplication) getApplication();
		setView(R.layout.activity_other_center);
		setTitle("", "人物信息", "");

		initView();
		setListener();
		otherUserId = getIntent().getExtras().getString("OTHER_USER_ID");

		loadData();
	}

	private void initView() {
		customProgressDialog = CustomProgressDialog.createDialog(this);

		iv_user_heade = (CircleImageView) findViewById(R.id.iv_user_heade);
		tv_guanzhu_count = (TextView) findViewById(R.id.tv_guanzhu_count);
		tv_haoyou_count = (TextView) findViewById(R.id.tv_haoyou_count);
		tv_start_count = (TextView) findViewById(R.id.tv_start_count);

		tv_user_name = (TextView) findViewById(R.id.tv_user_name);
		tv_user_desc = (TextView) findViewById(R.id.tv_user_desc);

		tv_look_dongtan = (TextView) findViewById(R.id.tv_look_dongtan);
		tv_look_houdong = (TextView) findViewById(R.id.tv_look_houdong);
		tv_chat_haoyou = (TextView) findViewById(R.id.tv_chat_haoyou);
		tv_guanzhu = (TextView) findViewById(R.id.tv_guanzhu);

		tv_user_school = (TextView) findViewById(R.id.tv_user_school);
		tv_user_dream = (TextView) findViewById(R.id.tv_user_dream);
		tv_user_favor = (TextView) findViewById(R.id.tv_user_favor);
		tv_user_brithday = (TextView) findViewById(R.id.tv_user_brithday);

	}

	private void setListener() {
		tv_look_dongtan.setOnClickListener(this);
		tv_look_houdong.setOnClickListener(this);
		tv_chat_haoyou.setOnClickListener(this);
		tv_guanzhu.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.tv_chat_haoyou:
			if (!StringUtils.isNullOrEmpty(other.UserId) && !StringUtils.isNullOrEmpty(other.UserName)) {
				RongIM.getInstance().startPrivateChat(OtherCenterActivity.this, other.UserId, other.UserName);
			}
			break;
		case R.id.tv_look_dongtan:
			Intent intent_dongtan = new Intent(this, MyselfDongtanActivity.class);
			Bundle data_dongtan = new Bundle();
			data_dongtan.putInt("FLAG", 2);
			data_dongtan.putSerializable("OTHER_USER_INFO", other);

			// data_dongtan.putString("OTHER_USER_HEAD", other.Head);
			intent_dongtan.putExtras(data_dongtan);
			startActivityForAnima(intent_dongtan);
			break;
		case R.id.tv_look_houdong:
			Intent intent_huodong = new Intent(this, OtherCenterDetailActivity.class);
			Bundle data_huodong = new Bundle();
			data_huodong.putInt("FLAG", 1);
			data_huodong.putString("OTHER_USER_ID", otherUserId);
			data_huodong.putString("OTHER_USER_NAME", other.UserName);
			// data_huodong.putString("OTHER_USER_HEAD", other.Head);
			intent_huodong.putExtras(data_huodong);
			startActivityForAnima(intent_huodong);
			break;
		case R.id.tv_guanzhu:
			try {
				loadData(otherUserId);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;
		default:
			break;
		}
	}

	private void loadData(String toUserId) throws JSONException {

		new AsynHttpClient().post(AppConfig.HOSTURL + GETFRIENDS, "data=[" + getJson(toUserId) + "]&ExecutorID="
				+ application.appConfig.USER_ID, new HttpNetWorkDataHandler() {

			@Override
			public void success(int statusCode, Object obj) {
				if (customProgressDialog != null && customProgressDialog.isShowing()
						&& OtherCenterActivity.this != null && !OtherCenterActivity.this.isFinishing()) {
					customProgressDialog.dismiss();
				}

				JsonParseState(obj.toString());

				System.out.println("==obj.toString()" + "===" + obj.toString());

			}

			@Override
			public void failure(int statusCode, Object obj) {
				if (customProgressDialog != null && customProgressDialog.isShowing()
						&& OtherCenterActivity.this != null && !OtherCenterActivity.this.isFinishing()) {
					customProgressDialog.dismiss();
				}
				Toast.makeText(OtherCenterActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
			}
		});
	}

	private String getJson(String toUserId) throws JSONException {

		JSONObject o = new JSONObject();
		o.put("UserId", application.appConfig.USER_ID);
		o.put("ToUserId", toUserId);
		System.out.println("otherCenterActivity=============" + o.toString());
		return o.toString();
	}

	public ResultInfo<FriendInfo> JsonParseState(String jsonStr) {
		if (!StringUtils.isNullOrEmpty(jsonStr)) {

			try {
				ResultInfo<FriendInfo> result = new ResultInfo<FriendInfo>();
				JSONObject jsonObject = new JSONObject(jsonStr);
				String code = jsonObject.getString("Code");
				result.Code = code;

				if (code.equals("1")) {
					Toast.makeText(OtherCenterActivity.this, "关注成功", Toast.LENGTH_SHORT).show();
					tv_guanzhu.setText("已关注TA");
					tv_guanzhu.setClickable(false);
				} else {
					Toast.makeText(OtherCenterActivity.this, "关注失败", Toast.LENGTH_SHORT).show();
				}

				return result;

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private void initData() {
		mAbImageDownloader = new AbImageDownloader(this);
		if (!StringUtils.isNullOrEmpty(other.Head)) {
			mAbImageDownloader.display(iv_user_heade, other.Head);
		}

		if (!StringUtils.isNullOrEmpty(other.FansCount)) {
			tv_guanzhu_count.setText("关注:" + other.FansCount);
		} else {
			tv_guanzhu_count.setText("关注:" + "0");
		}

		if (!StringUtils.isNullOrEmpty(other.StarLevel)) {
			tv_haoyou_count.setText("好友:" + other.StarLevel);
		} else {
			tv_haoyou_count.setText("好友:" + "0");
		}

		if (!StringUtils.isNullOrEmpty(other.NumberStars)) {
			tv_start_count.setText(other.NumberStars);
		} else {
			tv_start_count.setText("0");
		}

		if (!StringUtils.isNullOrEmpty(other.UserName)) {
			tv_user_name.setText(other.UserName);
		} else {
			tv_user_name.setText("");
		}

		if (!StringUtils.isNullOrEmpty(other.Description) && !"null".equals(other.Description)) {
			tv_user_desc.setText(other.Description);
		} else {
			tv_user_desc.setText("这个家伙很懒，没有留下任何信息");
		}

		if (!StringUtils.isNullOrEmpty(other.SchoolName)) {
			tv_user_school.setText(" " + other.SchoolName);
		} else {
			tv_user_school.setText("");
		}

		if (!StringUtils.isNullOrEmpty(other.Dream)) {
			tv_user_dream.setText(" " + other.Dream);
		} else {
			tv_user_dream.setText("");
		}

		if (!StringUtils.isNullOrEmpty(other.Hobby)) {
			tv_user_favor.setText(" " + other.Hobby);
		} else {
			tv_user_favor.setText("");
		}

		if (!StringUtils.isNullOrEmpty(other.Birthday)) {
			tv_user_brithday.setText(other.Birthday.substring(0, 10));
		} else {
			tv_user_brithday.setText("");
		}
		if (other.IsAssociated) {

			tv_guanzhu.setClickable(false);
			tv_guanzhu.setText("已关注TA");

		} else {
			tv_guanzhu.setClickable(true);
			tv_guanzhu.setText("未关注TA");
		}

	}

	private void loadData() {
		new AsynHttpClient().post(AppConfig.HOSTURL + "PhoneUser/Get", "data=" + getJson(),
				new HttpNetWorkDataHandler() {

					@Override
					public void success(int statusCode, Object obj) {
						if (customProgressDialog != null && customProgressDialog.isShowing()) {
							customProgressDialog.dismiss();
						}

						if (!StringUtils.isNullOrEmpty(obj.toString())) {
							try {
								JSONObject jsonObject = new JSONObject(obj.toString());
								String code = jsonObject.getString("Code");
								if ("1".equals(code)) {

									JSONObject o = new JSONObject(jsonObject.getString("Data"));

									other.UserId = o.getString("UserId");// 用户ID
									other.Head = o.getString("Head");// 头像
									other.NumberStars = o.getString("NumberStars");// 星星数量
									other.Description = o.getString("Description");// 用户描述
									other.UserName = o.getString("UserName");// 用户昵称
									other.MoveCount = o.getString("MoveCount");// 动弹数
									other.FansCount = o.getString("FansCount");// 粉丝数量
									other.Hobby = o.getString("Hobby");// 兴趣爱好
									other.Birthday = o.getString("Birthday");// 生日
									other.Dream = o.getString("Dream");// 梦想
									other.StarLevel = o.getString("StarLevel");// 星级
									other.SchoolName = o.getString("SchoolName");//
									other.IsAssociated = o.getBoolean("IsAssociated");
									System.out.println("OtherCenterActivity--306" + o.toString());

									// other.Pwd = o.getString("Pwd");
									// other.Salt = o.getString("Salt");
									// other.StudentNum =
									// o.getString("StudentNum");
									// other.SchoolId = o.getString("SchoolId");
									// other.StudentCard =
									// o.getString("StudentCard");// 学生证
									// other.AuditStatus =
									// o.getString("AuditStatus");
									// other.LevelMax = o.getString("LevelMax");
									// other.GameIntegral =
									// o.getString("GameIntegral");// 游戏积分
									// other.StarLevelSum =
									// o.getString("StarLevelSum");
									//
									// other.Status = o.getString("Status");
									// other.CreateTime =
									// o.getString("CreateTime");
									// other.CreateUser =
									// o.getString("CreateUser");
									// other.LastChangeTime =
									// o.getString("LastChangeTime");
									// other.LastChangeUser =
									// o.getString("LastChangeUser");
									// other.ActId = o.getString("ActId");
									// other.IsAssociated =
									// o.getString("IsAssociated");
									// other.PersonalCenter =
									// o.getString("PersonalCenter");

									initData();
								} else {
									getToast("数据取得失败，请重试").show();
									return;
								}
							} catch (JSONException e) {
								e.printStackTrace();
								Log.i("OtherCenterActivity", "JSON解析异常");
							}

						} else {
							getToast("数据取得失败，请重试").show();
						}
					}

					@Override
					public void failure(int statusCode, Object obj) {
						// TODO Auto-generated method stub
						if (customProgressDialog != null && customProgressDialog.isShowing()) {
							customProgressDialog.dismiss();
						}
						getToast("服务器连接失败").show();
					}
				});

	}

	private String getJson() {
		JSONObject o = new JSONObject();

		try {
			o.put("PageNo", 1 + "");
			o.put("PageSize", 10 + "");
			o.put("UserId", otherUserId);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return o.toString();
	}

	private Toast getToast(String msg) {
		if (toast == null) {
			toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
			;
		} else {
			toast.setText(msg);
		}

		return toast;
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		if (toast != null) {
			toast.cancel();
		}
	}

}
