package com.xuanit.move.fragment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.james.asynhttp.AsynHttpClient;
import com.james.asynhttp.HttpNetWorkDataHandler;
import com.xuanit.move.R;
import com.xuanit.move.adapter.SchoolAdapter;
import com.xuanit.move.app.AppConfig;
import com.xuanit.move.comm.PathComm;
import com.xuanit.move.model.School;
import com.xuanit.move.util.ImageComPressUtil;
import com.xuanit.move.util.MessageBox;
import com.xuanit.move.view.CustomProgressDialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView.ScaleType;

public class RegistFragment extends Fragment implements OnClickListener {
	final int RESULT_OK = -1;
	private String userName;
	private String userPwd;
	private String schoolId;
	private String studentNum;
	private String classId;
	private View root;
	private Button reg;
	private EditText et_registerName, et_password, et_verifyPwd, et_stu;
	private Spinner sp_school;
	// private Spinner sp_class;
	private ImageView user_img, card_img;
	private ImageView loadimg;
	private RelativeLayout loadview;// 异步加载动画视图
	private String data;
	private String user_header_loacle_path, user_card_loacle_path;// 上传图片的本地路径
	private String user_header_network_path, user_card_network_path;// 已上传图片的網絡路径
	private String user_head = "", user_card = "";// 上传图片的路径
	private int selectindex = -1;
	private List<String> schoolNameList = new ArrayList<String>();
	private List<School> schoolList = new ArrayList<School>();
	private SchoolAdapter schoolAdapter;
	private SchoolAdapter classAdapter;

	private CheckBox cb_consent;
	private TextView tv_userimg, tv_cardimg;
	private CustomProgressDialog customProgressDialog;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		root = inflater.inflate(R.layout.fragment_regist, container, false);
		initView();

		setListener();
		customProgressDialog = CustomProgressDialog.createDialog(getActivity());

		// 创建首选项
		School tempSchool = new School();
		tempSchool.Id = "";
		tempSchool.Name = "院校";
		schoolList.add(tempSchool);
		schoolNameList.add(tempSchool.Name);

		/*
		 * ClassGradeInfo tempClassGrade = new ClassGradeInfo();
		 * tempClassGrade.Id = ""; tempClassGrade.Name = "班级";
		 * classList.add(tempClassGrade);
		 * classNameList.add(tempClassGrade.Name);
		 */
		schoolAdapter = new SchoolAdapter(getActivity(), schoolNameList);
		sp_school.setAdapter(schoolAdapter);

		/*
		 * classAdapter = new SchoolAdapter(getActivity(), classNameList);
		 * sp_class.setAdapter(classAdapter);
		 */
		return root;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		getSchooldata();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	public void initView() {
		et_registerName = (EditText) root.findViewById(R.id.et_registerName);
		et_password = (EditText) root.findViewById(R.id.et_password);
		et_verifyPwd = (EditText) root.findViewById(R.id.et_verifyPwd);
		et_stu = (EditText) root.findViewById(R.id.et_stu);
		loadimg = (ImageView) root.findViewById(R.id.loadimg);
		loadview = (RelativeLayout) root.findViewById(R.id.re_loadview);
		user_img = (ImageView) root.findViewById(R.id.user_img);
		card_img = (ImageView) root.findViewById(R.id.card_img);
		sp_school = (Spinner) root.findViewById(R.id.sp_school);
		// sp_class = (Spinner) root.findViewById(R.id.sp_class);
		cb_consent = (CheckBox) root.findViewById(R.id.cb_consent);
		tv_userimg = (TextView) root.findViewById(R.id.tv_userimg);
		tv_cardimg = (TextView) root.findViewById(R.id.tv_cardimg);
		reg = (Button) root.findViewById(R.id.reg);

	}

	public void setListener() {
		reg.setOnClickListener(this);
		user_img.setOnClickListener(this);
		card_img.setOnClickListener(this);

		sp_school.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
				if (position == 0) {
					schoolId = "";
				} else {
					schoolId = schoolList.get(position).Id;
				}
				// getClassData();
			}

			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {
				schoolId = "";
			}
		});
		/*
		 * sp_class.setOnItemSelectedListener(new OnItemSelectedListener() {
		 * 
		 * @Override public void onItemSelected(AdapterView<?> adapterView, View
		 * view, int position, long id) { if (position == 0) { classId = ""; }
		 * else { classId = classList.get(position).Id; } }
		 * 
		 * @Override public void onNothingSelected(AdapterView<?> adapterView) {
		 * classId = ""; } });
		 */
	}

	public void getSchooldata() {
		new AsynHttpClient().post(AppConfig.HOSTURL + "PhoneCollege/GetList", "", new HttpNetWorkDataHandler() {
			@Override
			public void success(int statusCode, Object obj) {
				String result = obj.toString();
				Log.i("RegistFragment", "school_info==" + result);
				try {
					JSONObject jsonObject = new JSONObject(result);
					String item1 = jsonObject.getString("Code");
					String item2 = jsonObject.getString("Data");
					if (item1.equals("1")) {
						JSONArray arrGroup = new JSONArray(item2);
						School school;
						for (int i = 0; i < arrGroup.length(); i++) {
							JSONObject objGroup = arrGroup.getJSONObject(i);
							school = new School();
							String name = objGroup.getString("CollegeName");
							school.Id = objGroup.getString("Id");
							school.Name = name;
							schoolList.add(school);
							schoolNameList.add(name);
						}
						schoolAdapter.notifyDataSetChanged();
					} else {
						Toast.makeText(getActivity(), jsonObject.getString("Item2"), Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void failure(int statusCode, Object obj) {
				if (getActivity() != null) {
					Toast.makeText(getActivity(), "访问服务器失败，请稍微再试!", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	/*
	 * public void getClassData() { customProgressDialog.show(); new
	 * AsynHttpClient().get(AppConfig.HOSTURL +
	 * "PhoneClassGrade/GetListBySchoolId?SchoolId=" + schoolId, new
	 * HttpNetWorkDataHandler() {
	 * 
	 * @Override public void success(int statusCode, Object obj) {
	 * customProgressDialog.dismiss(); String result = obj.toString();
	 * Log.i("RegistFragment", "class_info==" + result); try { JSONObject
	 * jsonObject = new JSONObject(result); String item1 =
	 * jsonObject.getString("Code"); String item2 =
	 * jsonObject.getString("Data"); if (item1.equals("1")) { JSONArray arrGroup
	 * = new JSONArray(item2); for (int i = 0; i < arrGroup.length(); i++) {
	 * JSONObject objGroup = arrGroup.getJSONObject(i); ClassGradeInfo
	 * classGrade = new ClassGradeInfo(); String name =
	 * objGroup.getString("CollegeName"); classGrade.Id =
	 * objGroup.getString("Id"); classGrade.Name = name;
	 * classList.add(classGrade); classNameList.add(name); }
	 * classAdapter.notifyDataSetChanged(); } else {
	 * Toast.makeText(getActivity(), jsonObject.getString("Item2"),
	 * Toast.LENGTH_SHORT).show(); } } catch (JSONException e) {
	 * e.printStackTrace(); } }
	 * 
	 * @Override public void failure(int statusCode, Object obj) {
	 * Toast.makeText(getActivity(), "访问服务器失败，请稍微再试!" + statusCode,
	 * Toast.LENGTH_SHORT).show(); customProgressDialog.dismiss(); } }); }
	 */
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.reg:
			reg(view);
			break;
		case R.id.user_img:
			uptoclod(view);
			break;
		case R.id.card_img:
			upcardimg(view);
			break;
		default:
			break;
		}
	}

	public void reg(View view) {
		userName = et_registerName.getText().toString().trim();
		userPwd = et_password.getText().toString().trim();
		String verifypwd = et_verifyPwd.getText().toString().trim();
		// 获得班级id
		// classId
		studentNum = et_stu.getText().toString().trim();
		if (userName.equals("")) {
			Toast.makeText(getActivity(), "用户名不能为空!", Toast.LENGTH_SHORT).show();
			return;
		}

		if (userPwd.equals("")) {
			Toast.makeText(getActivity(), " 密码不能为空!", Toast.LENGTH_SHORT).show();
			return;
		}

		if (verifypwd.equals("")) {
			Toast.makeText(getActivity(), "确认密码不能为空!", Toast.LENGTH_SHORT).show();
			return;
		}

		if (!userPwd.equals(verifypwd)) {
			Toast.makeText(getActivity(), "两次输入的密码不一致!", Toast.LENGTH_SHORT).show();
			return;
		}
		if (studentNum.equals("")) {
			Toast.makeText(getActivity(), "学号不能为空!", Toast.LENGTH_SHORT).show();
			return;
		}

		if (null == schoolId || schoolId.equals("")) {
			Toast.makeText(getActivity(), "院校不能为空!", Toast.LENGTH_SHORT).show();
			return;
		}
		if (user_head.equals("")) {
			Toast.makeText(getActivity(), "需要上传初始图片，请点击上传图片", Toast.LENGTH_SHORT).show();
			return;
		}
		if (user_card.equals("")) {
			Toast.makeText(getActivity(), "需要上传学生证图片，请点击上传图片", Toast.LENGTH_SHORT).show();
			return;
		}
		if (!cb_consent.isChecked()) {
			Toast.makeText(getActivity(), "请勾选同意服务这块", Toast.LENGTH_SHORT).show();
			return;
		}

		String nameReg = "^[\u4e00-\u9fa50-9a-zA-Z_-]{3,9}$";
		String reg = "[A-Za-z0-9]{6,12}";
		if (regCheck(nameReg, userName)) {

		} else {
			Toast.makeText(getActivity(), "用户名格式不正确!(用户名只能是3-9位，其中包括：汉字、字母、数字、‘_’和‘-’)", Toast.LENGTH_SHORT).show();
			return;
		}
		if (studentNum.length() != 10) {
			Toast.makeText(getActivity(), "学号格式不正确！(学号只能是：10位数字", Toast.LENGTH_SHORT).show();
			return;
		}

		if (!regCheck(reg, userPwd)) {
			Toast.makeText(getActivity(), " 密码格式不正确!(密码只能是6-12位，其中包括：字母、数字)", Toast.LENGTH_SHORT).show();
			return;
		}
		if (!regCheck(reg, verifypwd)) {
			Toast.makeText(getActivity(), " 确认密码格式不正确!(确认密码只能是密码只能是6-12位，其中包括：字母、数字)", Toast.LENGTH_SHORT).show();
			return;
		}

		DoHeadImageUpLoad();

	}

	public boolean regCheck(String reg, String str) {
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

	public void uptoclod(View view) {
		selectindex = 1;
		AlertDialog dialog = new AlertDialog.Builder(getActivity())
				.setItems(new String[] { "相册", "拍照" }, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (which == 0) {
							pickPhoto();
						} else {
							takePhoto();
						}
					}
				}).setTitle("请选择").create();
		if (!dialog.isShowing()) {
			dialog.show();
		}
	}

	public void upcardimg(View view) {
		selectindex = 2;
		AlertDialog dialog = new AlertDialog.Builder(getActivity())
				.setItems(new String[] { "相册", "拍照" }, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (which == 0) {
							pickPhoto();
						} else {
							takePhoto();
						}
					}
				}).setTitle("请选择").create();
		if (!dialog.isShowing()) {
			dialog.show();
		}
	}

	/**
	 * 通过uri获取文件的绝对路径
	 * 
	 * @param uri
	 * @return
	 */
	public String getAbsoluteImagePath(Activity context, Uri uri) {
		String imagePath = "";
		String[] proj = { MediaStore.Images.Media.DATA };
		@SuppressWarnings("deprecation")
		Cursor cursor = context.managedQuery(uri, proj, null, null, null);
		if (cursor != null) {
			int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			if (cursor.getCount() > 0 && cursor.moveToFirst()) {
				imagePath = cursor.getString(column_index);
			}
		}
		return imagePath;
	}

	/***
	 * 从中取图片
	 */
	private void pickPhoto() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("image/*");
		Intent wrapperIntent = Intent.createChooser(intent, null);
		startActivityForResult(wrapperIntent, 150);
	}

	/**
	 * 接收其他页面返回的信息
	 */

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestCode == 150) {
				Uri uri = data.getData();
				try {
					String[] pojo = { MediaStore.Images.Media.DATA };
					@SuppressWarnings("deprecation")
					Cursor cursor = getActivity().managedQuery(uri, pojo, null, null, null);
					if (cursor != null) {
						int colunm_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
						cursor.moveToFirst();
						String path = cursor.getString(colunm_index);
						if (path.endsWith("jpg")) {
							File file = new File(path);
							Log.i("image_loacle_path", "size" + file.length() + "");
							if (file.length() < 5 * 1024 * 1024) {
								if (selectindex == 1) {
									user_head = path;
									user_header_loacle_path = path;
									Log.i("image_loacle_path", "header" + user_header_loacle_path);
									getImage(user_header_loacle_path, user_img);
									tv_userimg.setVisibility(View.INVISIBLE);
								} else {
									user_card = path;
									user_card_loacle_path = path;
									Log.i("image_loacle_path", "card" + user_card_loacle_path);
									getImage(user_card_loacle_path, card_img);
									tv_cardimg.setVisibility(View.INVISIBLE);
								}
							} else {
								MessageBox.Instance(getActivity()).ShowToast("你选择的图片太大了，请重新选择大小在5M以内的图片");
							}

						} else {
							MessageBox.Instance(getActivity()).ShowToast("请选择jpg图片");
						}
					} else {
						MessageBox.Instance(getActivity()).ShowToast("图片读取失败请重试！");
					}
				} catch (Exception e) {
					MessageBox.Instance(getActivity()).ShowToast("图片读取失败请重试！");
					e.printStackTrace();
				}
			} else if (requestCode == 120) {
				if (selectindex == 1) {
					if (user_head != null) {
						user_header_loacle_path = user_head;
						Log.i("image_loacle_path", "header" + user_header_loacle_path);
						getImage(user_header_loacle_path, user_img);
						tv_userimg.setVisibility(View.INVISIBLE);

					}
				} else if (selectindex == 2) {
					if (user_card != null) {
						user_card_loacle_path = user_card;
						Log.i("image_loacle_path", "card" + user_card_loacle_path);
						getImage(user_card_loacle_path, card_img);
						tv_cardimg.setVisibility(View.INVISIBLE);

					}
				}
			}
		} else {
			MessageBox.Instance(getActivity()).ShowToast("读取失败请重试！");
		}

	}

	@SuppressLint("SimpleDateFormat")
	private void takePhoto() {
		String SDState = Environment.getExternalStorageState();
		if (SDState.equals(Environment.MEDIA_MOUNTED)) {
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
			if (selectindex == 1) {
				user_head = PathComm.picPath + formatter.format(date) + ".jpg";
			} else if (selectindex == 2) {
				user_card = PathComm.picPath + formatter.format(date) + ".jpg";
			}
			if (selectindex == 1) {
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(user_head)));
			} else if (selectindex == 2) {
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(user_card)));
			}
			startActivityForResult(intent, 120);
		} else {
			MessageBox.Instance(getActivity()).ShowToast("内存卡不存在");
		}
	}

	public void DoTextUpLoad() {
		JSONObject object = new JSONObject();
		try {
			object.put("UserName", userName);
			object.put("Pwd", userPwd);
			object.put("StudentNum", studentNum);

			object.put("SchoolId", schoolId);
			object.put("Head", user_header_network_path);
			object.put("StudentCard", user_card_network_path);
			data = object.toString();
		} catch (JSONException e) {
			return;
		}
		new AsynHttpClient().post(AppConfig.HOSTURL + "PhoneUser/Create", "data=" + data + "&ExecutorID=1",
				new HttpNetWorkDataHandler() {
					@Override
					public void success(int statusCode, Object obj) {
						customProgressDialog.dismiss();
						try {
							JSONObject object = new JSONObject(obj.toString());
							if ("1".equals(object.getString("Code"))) {
								Toast.makeText(getActivity(), "账号申请成功，正在审核中。。。", Toast.LENGTH_SHORT).show();
							} else {
								Toast.makeText(getActivity(), object.getString("Data") + statusCode, Toast.LENGTH_SHORT)
										.show();
							}

						} catch (JSONException e) {

						}
					}

					@Override
					public void failure(int statusCode, Object obj) {
						customProgressDialog.dismiss();
						Toast.makeText(getActivity(), "访问服务器失败，请稍微再试!" + statusCode, Toast.LENGTH_SHORT).show();
					}
				});
	}

	/**
	 * 头像图片上传类
	 */
	public void DoHeadImageUpLoad() {
		customProgressDialog.show();
		// 上传用户头像
		String userimg = user_head;
		String posturl = "PhoneUser/UpHeadImg";

		// 如果图片大于100KB用压缩图片后在上传
		if (new File(userimg).length() > (long) 100 * 1024) {
			ImageComPressUtil.saveMyBitmap(userimg);
			userimg = PathComm.picPath + userimg.substring(userimg.lastIndexOf("/") + 1);
		}

		new AsynHttpClient().upload(AppConfig.HOSTURL + posturl, userimg, new HttpNetWorkDataHandler() {
			@Override
			public void success(int statusCode, Object obj) {
				try {
					JSONObject jObject = new JSONObject(obj.toString());
					if ("1".equals(jObject.getString("Code"))) {
						user_header_network_path = new JSONObject(jObject.getString("Data")).getString("Item2");
						DoCardImageUpLoad();
					} else {
						MessageBox.Instance(getActivity()).ShowToast(jObject.getString("Data"));
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					customProgressDialog.dismiss();
				}

			}

			@Override
			public void failure(int statusCode, Object obj) {
				MessageBox.Instance(getActivity()).ShowToast("上传失败！");
				customProgressDialog.dismiss();
			}
		});

	}

	public void DoCardImageUpLoad() {
		// 上传用户头像
		String userimg = user_card;
		String posturl = "PhoneUser/UpCardImg";

		// 如果图片大于100KB用压缩图片后在上传
		if (new File(userimg).length() > (long) 100 * 1024) {
			ImageComPressUtil.saveMyBitmap(userimg);
		}

		Log.i("RegistFragment", "img_path---" + userimg);
		new AsynHttpClient().upload(AppConfig.HOSTURL + posturl, userimg, new HttpNetWorkDataHandler() {
			@Override
			public void success(int statusCode, Object obj) {
				Log.i("RegistFragment", "result---" + obj.toString());

				try {
					JSONObject jObject = new JSONObject(obj.toString());
					if ("1".equals(jObject.getString("Code"))) {
						user_card_network_path = new JSONObject(jObject.getString("Data")).getString("Item2");
						DoTextUpLoad();
					} else {
						MessageBox.Instance(getActivity()).ShowToast(jObject.getString("Data"));
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					customProgressDialog.dismiss();
				}

			}

			@Override
			public void failure(int statusCode, Object obj) {
				MessageBox.Instance(getActivity()).ShowToast("上传失败！");
				customProgressDialog.dismiss();
			}
		});
	}

	/**
	 * imageView中加载本地图片
	 * 
	 * @param img_url
	 *            本地图片的路径
	 * @param img
	 *            要显示图片的imageView
	 * @return
	 */
	public void getImage(String img_url, ImageView img) {
		img.setScaleType(ScaleType.CENTER_INSIDE);
		BitmapDrawable bitmapDrawable = (BitmapDrawable) img.getDrawable();
		if (bitmapDrawable != null && !bitmapDrawable.getBitmap().isRecycled()) {
			bitmapDrawable.getBitmap().recycle();
		}
		img.setImageBitmap(ImageComPressUtil.getimage(img_url));
	}

	/*
    *//**
	 * 加载图片动画
	 * 
	 * @param img
	 */
	/*
	 * public void ShowLoadingView(ImageView img) { Animation operatingAnim =
	 * AnimationUtils.loadAnimation(getActivity(), R.anim.druaction);
	 * LinearInterpolator lin = new LinearInterpolator();
	 * operatingAnim.setInterpolator(lin); img.setVisibility(View.VISIBLE); if
	 * (operatingAnim != null) img.startAnimation(operatingAnim); }
	 *//**
	 * 隐藏loadImage
	 * 
	 */
	/*
	 * public void hideview() { loadimg.clearAnimation();
	 * loadview.setVisibility(View.GONE); }
	 *//**
	 * 显示loadImage
	 * 
	 */
	/*
	 * public void showview() { loadview.setVisibility(View.VISIBLE);
	 * loadview.requestFocus(); Animation operatingAnim =
	 * AnimationUtils.loadAnimation(getActivity(), R.anim.druaction);
	 * LinearInterpolator lin = new LinearInterpolator();
	 * operatingAnim.setInterpolator(lin); if (operatingAnim != null)
	 * loadimg.startAnimation(operatingAnim); }
	 */
}
