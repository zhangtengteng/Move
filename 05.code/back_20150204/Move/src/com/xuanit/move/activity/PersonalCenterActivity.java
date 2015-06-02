package com.xuanit.move.activity;

import org.json.JSONException;
import org.json.JSONObject;
import com.ab.bitmap.AbImageDownloader;
import com.james.asynhttp.AsynHttpClient;
import com.james.asynhttp.HttpNetWorkDataHandler;
import com.xuanit.move.R;
import com.xuanit.move.app.AppConfig;
import com.xuanit.move.app.MoveApplication;
import com.xuanit.move.base.BaseActivity;
import com.xuanit.move.model.PersonCenterInfo;
import com.xuanit.move.model.ResultInfo;
import com.xuanit.move.util.CloseAllActivityManager;
import com.xuanit.move.util.StringUtils;
import com.xuanit.move.view.CircleImageView;
import com.xuanit.move.view.CustomProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PersonalCenterActivity extends BaseActivity {

	private CustomProgressDialog customProgressDialog;
	private String GETPERSONALMSG = "PhoneUser/Get";
	private CircleImageView add_person_head;
	private TextView add_person_username;
	private TextView add_person_sign;
	private TextView tv_dongtanzhishu;
	private TextView tv_guanzhu;
	private TextView tv_fensi;
	private TextView setting_person_msg;
	private TextView myself_dongtan;
	private TextView myself_fensi;
	private AbImageDownloader mAbImageDownloader;
	private MoveApplication application;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		CloseAllActivityManager.getInstance().addActivity(PersonalCenterActivity.this);
		super.onCreate(savedInstanceState);
		application = (MoveApplication) getApplication();
		setView(R.layout.activity_own_center);
		setTitle("", "个人中心", "");
		initView();
		loadUserIdData();
	}

	private String getJson() {
		JSONObject o = new JSONObject();

		try {
			if (!StringUtils.isNullOrEmpty(application.appConfig.USER_ID)) {
				o.put("UserId", application.appConfig.USER_ID);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return o.toString();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		loadUserIdData();
	}

	private void initView() {

		add_person_head = (CircleImageView) findViewById(R.id.add_person_head);
		add_person_username = (TextView) findViewById(R.id.add_person_username);
		add_person_sign = (TextView) findViewById(R.id.add_person_sign);
		tv_dongtanzhishu = (TextView) findViewById(R.id.tv_dongtanzhishu);
		tv_guanzhu = (TextView) findViewById(R.id.tv_guanzhu);
		tv_fensi = (TextView) findViewById(R.id.tv_fensi);
		setting_person_msg = (TextView) findViewById(R.id.tv_person_ziliao);
		myself_dongtan = (TextView) findViewById(R.id.tv_person_dongtan);
		myself_fensi = (TextView) findViewById(R.id.tv_wodefensi);

		if (mAbImageDownloader == null) {
			mAbImageDownloader = new AbImageDownloader(PersonalCenterActivity.this);
		}
		customProgressDialog = CustomProgressDialog.createDialog(this);

		setting_person_msg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(PersonalCenterActivity.this, UpdatePersonMsgActivity.class));
				overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			}
		});

		myself_dongtan.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(PersonalCenterActivity.this, MyselfDongtanActivity.class));
				overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			}
		});

		myself_fensi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(PersonalCenterActivity.this, MyselfFensictivity.class));
				overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			}
		});

	}

	public ResultInfo<PersonCenterInfo> JsonParseState(String jsonStr) {

		ResultInfo<PersonCenterInfo> resultInfo = new ResultInfo<PersonCenterInfo>();

		if (!StringUtils.isNullOrEmpty(jsonStr)) {
			try {
				JSONObject jsonObject = new JSONObject(jsonStr);
				String Code = jsonObject.getString("Code");

				resultInfo.Code = Code;

				JSONObject o = jsonObject.getJSONObject("Data");

				application.appConfig.Head = o.getString("Head");// 头像
				application.appConfig.MoveCount = o.getString("MoveCount");// 动弹数
				application.appConfig.PersonalDescription = o.getString("Description");
				application.appConfig.SchoolName = o.getString("SchoolName");
				application.appConfig.FansCount = o.getString("FansCount");
				application.appConfig.Dream = o.getString("Dream");
				application.appConfig.Hobby = o.getString("Hobby");
				application.appConfig.StarLevel = o.getString("StarLevel");
				if (!StringUtils.isNullOrEmpty(o.getString("Birthday"))) {
					application.appConfig.Birthday = o.getString("Birthday").substring(0, 10);
				} else {
					application.appConfig.Birthday = "";
				}
				mAbImageDownloader.display(add_person_head, application.appConfig.Head);
				add_person_username.setText(application.appConfig.UserName);
				if (StringUtils.isNullOrEmpty(application.appConfig.PersonalDescription)) {
					add_person_sign.setText("您还没有属于自己的个性签名！");
				} else {
					add_person_sign.setText(application.appConfig.PersonalDescription);
				}
				tv_dongtanzhishu.setText(application.appConfig.MoveCount);
				tv_fensi.setText(application.appConfig.FansCount);

				System.out.println("*************"
						+ application.appConfig.StarLevel.substring(0, application.appConfig.StarLevel.indexOf(".")));
				tv_guanzhu.setText(Integer.valueOf(application.appConfig.StarLevel.substring(0,
						application.appConfig.StarLevel.indexOf(".")))
						+ Integer.valueOf(application.appConfig.FansCount) + "");

			} catch (JSONException e) {
				e.printStackTrace();
			}
			return resultInfo;
		}
		return null;
	}

	private void loadUserIdData() {
		customProgressDialog.show();
		new AsynHttpClient().post(AppConfig.HOSTURL + GETPERSONALMSG, "data=" + getJson(),
				new HttpNetWorkDataHandler() {

					@Override
					public void success(int statusCode, Object obj) {
						if (customProgressDialog != null && customProgressDialog.isShowing()
								&& PersonalCenterActivity.this != null && !PersonalCenterActivity.this.isFinishing()) {
							customProgressDialog.dismiss();
						}

						ResultInfo<PersonCenterInfo> reslutInfo = JsonParseState(obj.toString());
						if (reslutInfo != null) {

						}

					}

					@Override
					public void failure(int statusCode, Object obj) {
						if (customProgressDialog != null && customProgressDialog.isShowing()
								&& PersonalCenterActivity.this != null && !PersonalCenterActivity.this.isFinishing()) {
							customProgressDialog.dismiss();
						}
						Toast.makeText(PersonalCenterActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
					}
				});
	}
}
