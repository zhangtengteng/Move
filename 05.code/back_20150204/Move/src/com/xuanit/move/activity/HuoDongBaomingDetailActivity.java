package com.xuanit.move.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.james.asynhttp.AsynHttpClient;
import com.james.asynhttp.HttpNetWorkDataHandler;
import com.xuanit.move.R;
import com.xuanit.move.app.AppConfig;
import com.xuanit.move.app.MoveApplication;
import com.xuanit.move.base.BaseActivity;
import com.xuanit.move.comm.StringsComm;
import com.xuanit.move.util.CloseAllActivityManager;
import com.xuanit.move.util.StringUtils;
import com.xuanit.move.view.CustomProgressDialog;

public class HuoDongBaomingDetailActivity extends BaseActivity {
	private String userId;
	private String huoDongId;
	private EditText et_houdong_baoming_content;
	private CustomProgressDialog customProgressDialog;
	private String detail;
	private MoveApplication application;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		CloseAllActivityManager.getInstance().addActivity(HuoDongBaomingDetailActivity.this);
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		application = (MoveApplication) getApplication();
		setView(R.layout.activity_huodong_baoming_detail);
		setTitle("", "活动详细", "发表");

		if (!StringUtils.isNullOrEmpty(application.appConfig.USER_ID)) {
			userId = application.appConfig.USER_ID;
		}
		huoDongId = (String) getIntent().getExtras().get("huoDongId");

		initView();
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.ll_header_right:
			detail = et_houdong_baoming_content.getEditableText().toString().trim();
			if (StringUtils.isNullOrEmpty(detail)){
				Toast.makeText(getApplicationContext(), StringsComm.huodong_baoming_null, Toast.LENGTH_SHORT).show();
				return;
			}else if (StringUtils.getCharCount(detail) > 500){
				Toast.makeText(getApplicationContext(), StringsComm.huodong_baoming_limit, Toast.LENGTH_SHORT).show();
				return;
			}
			addData();
			break;

		default:
			break;
		}
	}

	private void initView() {
		customProgressDialog = CustomProgressDialog.createDialog(this);
		et_houdong_baoming_content = (EditText) findViewById(R.id.et_houdong_baoming_content);
	}

	private void addData() {
		customProgressDialog.show();

		new AsynHttpClient().post(AppConfig.HOSTURL + "PhoneActivityDetail/Create", "data=" + getJson()
				+ "&ExecutorID=" + huoDongId, new HttpNetWorkDataHandler() {

			@Override
			public void success(int statusCode, Object obj) {
				// TODO Auto-generated method stub
				Log.i("HuoDongBaomingDetailActivity", obj.toString());
				customProgressDialog.dismiss();
				if (!StringUtils.isNullOrEmpty(obj.toString())) {
					try {
						JSONObject jsonObject = new JSONObject(obj.toString());
						String code = jsonObject.getString("Code");
						String data = jsonObject.getString("Data");
						if ("1".equals(code)) {
							HuoDongBaomingDetailActivity.this.finish();
						} else {
							Toast.makeText(HuoDongBaomingDetailActivity.this, data, Toast.LENGTH_SHORT).show();
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}

			@Override
			public void failure(int statusCode, Object obj) {
				// TODO Auto-generated method stub
				customProgressDialog.dismiss();
				Toast.makeText(HuoDongBaomingDetailActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
			}

		});
	}

	private String getJson() {
		JSONObject o = new JSONObject();
		try {
			o.put("Detail", detail);
			o.put("ActId", huoDongId);
			if (!StringUtils.isNullOrEmpty(userId)) {
				o.put("UserId", userId);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return o.toString();
	}
}
