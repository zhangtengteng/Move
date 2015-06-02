package com.xuanit.move.fragment;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import com.james.asynhttp.AsynHttpClient;
import com.james.asynhttp.HttpNetWorkDataHandler;
import com.xuanit.move.R;
import com.xuanit.move.activity.MainActivity;
import com.xuanit.move.app.AppConfig;
import com.xuanit.move.app.MoveApplication;
import com.xuanit.move.util.JudgePhoneStateUtil;
import com.xuanit.move.util.MessageBox;
import com.xuanit.move.util.StringUtils;
import com.xuanit.move.view.CustomProgressDialog;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class LoginFragment extends Fragment implements OnClickListener {
	private CheckBox cb_login_jizhu;
	private View root;
	private EditText input_name, input_pwd;
	private ImageView loadimg;
	private RelativeLayout loadview;// 异步加载动画视图
	private CustomProgressDialog customProgressDialog;
	private Button login;
	private ImageButton clearName;
	private ImageButton clearPass;
	private MoveApplication application;
	private SharedPreferences sharedPreferences;
	private Editor editor;
	public String saveUserName;// 保存在本地的用户名
	public String saveUserPwd;// 保存在本地
	public boolean saveState;// 保存在本地的用户名

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
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		root = inflater.inflate(R.layout.fragment_login, container, false);
		init();

		return root;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	public void init() {
		customProgressDialog = CustomProgressDialog.createDialog(getActivity());
		input_name = (EditText) root.findViewById(R.id.name);
		input_pwd = (EditText) root.findViewById(R.id.pwd);
		loadimg = (ImageView) root.findViewById(R.id.loadimg);
		loadview = (RelativeLayout) root.findViewById(R.id.re_loadview);
		login = (Button) root.findViewById(R.id.login_but_login);
		cb_login_jizhu = (CheckBox) root.findViewById(R.id.cb_login_jizhu);
		clearName = (ImageButton) root.findViewById(R.id.clearName);
		clearPass = (ImageButton) root.findViewById(R.id.clearPass);

		sharedPreferences = getActivity().getSharedPreferences("SAVE_USER", getActivity().MODE_PRIVATE);
		editor = sharedPreferences.edit();

		saveUserName = sharedPreferences.getString("USER_NAME", "");
		saveUserPwd = sharedPreferences.getString("USER_PWD", "");
		saveState = sharedPreferences.getBoolean("CHECK_STATES", false);

		input_pwd.addTextChangedListener(new TextWatcher() {
			// String pass = input_pwd.getText().toString().trim();
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				String pass = input_pwd.getText().toString().trim();
				if (pass.length() > 0) {
					clearPass.setVisibility(View.VISIBLE);
					clearPass.postDelayed(new Runnable() {
						@Override
						public void run() {
							clearPass.setVisibility(View.INVISIBLE);
						}
					}, 5000);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				/*
				 * String reg = "[A-Za-z0-9]{6,12}"; if (!regCheck(reg, pass)) {
				 * Toast.makeText(getActivity(),
				 * " 密码格式不正确!(密码只能是6-12位，其中包括：字母、数字)",
				 * Toast.LENGTH_SHORT).show(); return; }
				 */
			}
		});

		// 当文本改变的时候
		input_name.addTextChangedListener(new TextWatcher() {
			// String name = input_name.getText().toString().trim();
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				String name = input_name.getText().toString().trim();
				if (name.length() > 0) {
					clearName.setVisibility(View.VISIBLE);
					clearName.postDelayed(new Runnable() {
						@Override
						public void run() {
							clearName.setVisibility(View.INVISIBLE);
						}
					}, 5000);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				/*
				 * String nameReg = "[\u4e00-\u9fa5|\\w]{3,12}"; if
				 * (!regCheck(nameReg, name)) { Toast.makeText(getActivity(),
				 * "用户名格式不正确!(用户名只能是3-12位，其中包括：汉字、字母、数字和‘_’)",
				 * Toast.LENGTH_SHORT).show(); return; }
				 */
			}
		});

		clearName.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				input_name.setText("");
			}
		});

		clearPass.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				input_pwd.setText("");
			}
		});

		if (saveState) {
			cb_login_jizhu.setChecked(true);
			input_name.setText(saveUserName);
			input_pwd.setText(saveUserPwd);
		} else {
			cb_login_jizhu.setChecked(false);
			input_name.setText("");
			input_pwd.setText("");
		}

		login.setOnClickListener(this);

	}

	public boolean regCheck(String reg, String str) {
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.login_but_login:
			SaveUserInfo();
			login();
			break;
		case R.id.login_but_forgetPwd:
			break;
		/*
		 * case R.id.clearName: input_name.setText(""); break; case
		 * R.id.clearPass: input_pwd.setText(""); break;
		 */
		default:
			break;
		}

	}

	private void SaveUserInfo() {
		if (cb_login_jizhu.isChecked()) {
			String name = input_name.getText().toString().trim();
			String pwd = input_pwd.getText().toString().trim();
			editor.putString("USER_NAME", name);
			editor.putString("USER_PWD", pwd);
			editor.putBoolean("CHECK_STATES", true);
			editor.commit();
		} else {
			editor.clear();
			editor.commit();
		}
	}

	/**
	 * 登录点击
	 * 
	 * @param view
	 */
	public void login() {
		String name = input_name.getText().toString().trim();
		String pwd = input_pwd.getText().toString().trim();
		if (null == name || name.equals("")) {
			MessageBox.Instance(getActivity()).ShowToast("用户名不能为空");
			return;
		}

		if (name.length() < 3 || name.length() > 12) {
			Toast.makeText(getActivity(), "请输入正确的用户名", Toast.LENGTH_SHORT).show();
			return;
		}

		if (null == pwd || pwd.equals("")) {
			MessageBox.Instance(getActivity()).ShowToast("密码不能为空!");
			return;
		}

		if (pwd.length() < 3 || pwd.length() > 12) {
			Toast.makeText(getActivity(), "请输入3到12位的密码", Toast.LENGTH_SHORT).show();
			return;
		}
		customProgressDialog.show();

		if (StringUtils.isNullOrEmpty(getJson(name, pwd))) {
			return;
		}
		new AsynHttpClient().post(AppConfig.HOSTURL + "PhoneUser", "data=" + getJson(name, pwd),
				new HttpNetWorkDataHandler() {
					@Override
					public void success(int statusCode, Object obj) {
						System.out.println("==Login==" + obj.toString());
						customProgressDialog.dismiss();
						try {
							JSONObject jsonObject = new JSONObject(obj.toString());
							String item1 = jsonObject.getString("Code");
							String item2 = jsonObject.getString("Data");
							if (item1.equals("1")) {
								JSONObject object = new JSONObject(item2);

								Log.i("userinfo", item2);
								application.appConfig.USER_ID = object.getString("UserId");
								application.appConfig.USER_NAME = object.getString("UserName");
								application.appConfig.Head = object.getString("Head");
								application.appConfig.StarLevelSum = object.getString("StarLevelSum");
								application.appConfig.PersonalDescription = object.getString("Description");

								if ("1".equals(object.getString("AuditStatus"))) {
									Toast.makeText(getActivity(), "你的帐号正在审核中，暂时无法登陆", Toast.LENGTH_SHORT).show();
									return;
								}
								if ("2".equals(object.getString("AuditStatus"))) {
									getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
									getActivity().finish();
								}
								if ("3".equals(object.getString("AuditStatus"))) {
									Toast.makeText(getActivity(), "你申请的帐号被拒绝，请重新注册", Toast.LENGTH_SHORT).show();
									return;
								}

							} else {
								Toast.makeText(getActivity(), jsonObject.getString("Item2"), Toast.LENGTH_SHORT).show();
							}

						} catch (JSONException e) {
							Toast.makeText(getActivity(), "用户名或密码错误", Toast.LENGTH_SHORT).show();
						}

					}

					@Override
					public void failure(int statusCode, Object obj) {
						customProgressDialog.dismiss();
						Toast.makeText(getActivity(), "访问服务器失败，请稍微再试!", Toast.LENGTH_SHORT).show();
					}
				});
	}

	public String getJson(String name, String pwd) {
		JSONObject object = new JSONObject();
		try {
			object.put("UserName", name);
			object.put("Pwd", pwd);

			if (((int) application.lontitude == 0 && (int) application.latitude == 0)) {
				object.put("LoginLongitude", 0);
				object.put("LoginLatitude", 0);
			}

			return object.toString();
		} catch (JSONException e) {
			return "";
		}
	}

	public void hideview() {
		loadimg.clearAnimation();
		loadview.setVisibility(View.GONE);
	}

	public void showview() {
		loadview.setVisibility(View.VISIBLE);
		loadview.requestFocus();
		Animation operatingAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.druaction);
		LinearInterpolator lin = new LinearInterpolator();
		operatingAnim.setInterpolator(lin);
		if (operatingAnim != null)
			loadimg.startAnimation(operatingAnim);
	}

}
