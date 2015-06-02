package com.xuanit.move.activity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;

import com.james.asynhttp.AsynHttpClient;
import com.james.asynhttp.HttpNetWorkDataHandler;
import com.xuanit.move.R;
import com.xuanit.move.app.AppConfig;
import com.xuanit.move.app.MoveApplication;
import com.xuanit.move.base.BaseActivity;
import com.xuanit.move.view.CustomProgressDialog;
import com.xuanit.move.util.CloseAllActivityManager;
import com.xuanit.move.util.ImageComPressUtil;
import com.xuanit.move.util.StringUtils;
import com.xuanit.move.util.MessageBox;
import com.xuanit.move.comm.PathComm;
import com.xuanit.move.comm.StringsComm;

public class HuoDongApplyActivity extends BaseActivity implements OnClickListener {
	private String userId;// 用户ID

	private EditText et_huodong_title;
	private EditText et_huodong_address;
	private NumberPicker np_huodong_renshu;
	private NumberPicker np_huodong_feiyong;
	private TextView tv_start_data;
	private TextView tv_start_time;
	private TextView tv_end_data;
	private TextView tv_end_time;
	private EditText et_huodong_liucheng;
	private ImageView iv_huodong_pic;
	private Button but_huodong_shenqing;

	private String titleStr;// 活动标题
	private String addressStr;// 活动地点
	private String liuchengStr;// 活动流程
	private String renshuStr;
	private String feiyongStr;
	private String dateStartStr;
	private String dateEndStr;
	private String timeStartStr;
	private String timeEndStr;
	private String picNetWorkPath;
	private String picPath;
	private MoveApplication application;
	private CustomProgressDialog customProgressDialog;

	PopupWindow popupWindow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		CloseAllActivityManager.getInstance().addActivity(HuoDongApplyActivity.this);
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		application = (MoveApplication) getApplication();
		setView(R.layout.activity_huodong_apply);
		setTitle("", "活动申请", "");
		if (!StringUtils.isNullOrEmpty(application.appConfig.USER_ID)) {
			userId = application.appConfig.USER_ID;
		}

		initView();
		setListener();

	}

	@Override
	public void onClick(View v) {
		Calendar calendar = Calendar.getInstance();
		super.onClick(v);
		switch (v.getId()) {
		case R.id.but_huodong_shenqing:
			getdata();

			break;
		case R.id.iv_huodong_pic:
			AlertDialog dialog = new AlertDialog.Builder(HuoDongApplyActivity.this)
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
			break;
		case R.id.tv_start_data:
			new DatePickerDialog(HuoDongApplyActivity.this, new DatePickerDialog.OnDateSetListener() {
				@Override
				public void onDateSet(DatePicker datePicker, int year, int month, int day) {
					tv_start_data.setText(year + "-" + StringUtils.addZerodata(month + 1) + "-"
							+ StringUtils.addZerodata(day));
				}
			}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
			break;
		case R.id.tv_start_time:
			new TimePickerDialog(HuoDongApplyActivity.this, new TimePickerDialog.OnTimeSetListener() {
				@Override
				public void onTimeSet(TimePicker timePicker, int hour, int minute) {
					tv_start_time.setText(StringUtils.addZerodata(hour) + ":" + StringUtils.addZerodata(minute));
				}
			}, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
			break;
		case R.id.tv_end_data:
			new DatePickerDialog(HuoDongApplyActivity.this, new DatePickerDialog.OnDateSetListener() {
				@Override
				public void onDateSet(DatePicker datePicker, int year, int month, int day) {
					tv_end_data.setText(year + "-" + StringUtils.addZerodata(month + 1) + "-"
							+ StringUtils.addZerodata(day));
				}
			}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();

			break;
		case R.id.tv_end_time:
			new TimePickerDialog(HuoDongApplyActivity.this, new TimePickerDialog.OnTimeSetListener() {
				@Override
				public void onTimeSet(TimePicker timePicker, int hour, int minute) {
					tv_end_time.setText(StringUtils.addZerodata(hour) + ":" + StringUtils.addZerodata(minute));
				}
			}, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
			break;
		default:
			break;
		}
	}

	private void initView() {
		customProgressDialog = CustomProgressDialog.createDialog(this);
		et_huodong_title = (EditText) findViewById(R.id.et_huodong_title);
		et_huodong_address = (EditText) findViewById(R.id.et_huodong_address);
		np_huodong_renshu = (NumberPicker) findViewById(R.id.np_huodong_renshu);
		np_huodong_feiyong = (NumberPicker) findViewById(R.id.np_huodong_feiyong);

		et_huodong_liucheng = (EditText) findViewById(R.id.et_huodong_liucheng);
		iv_huodong_pic = (ImageView) findViewById(R.id.iv_huodong_pic);
		but_huodong_shenqing = (Button) findViewById(R.id.but_huodong_shenqing);

		tv_start_data = (TextView) findViewById(R.id.tv_start_data);
		tv_end_data = (TextView) findViewById(R.id.tv_end_data);
		tv_start_time = (TextView) findViewById(R.id.tv_start_time);
		tv_end_time = (TextView) findViewById(R.id.tv_end_time);

		np_huodong_renshu.setMinValue(1);
		np_huodong_renshu.setMaxValue(500);
		np_huodong_renshu.setValue(100);
		np_huodong_feiyong.setMinValue(0);
		np_huodong_feiyong.setMaxValue(500);
		np_huodong_feiyong.setValue(20);

	}

	private void setListener() {
		tv_start_data.setOnClickListener(this);
		tv_end_data.setOnClickListener(this);
		tv_start_time.setOnClickListener(this);
		tv_end_time.setOnClickListener(this);
		iv_huodong_pic.setOnClickListener(this);
		but_huodong_shenqing.setOnClickListener(this);
	}

	private void getdata() {
		titleStr = et_huodong_title.getText().toString().trim();
		addressStr = et_huodong_address.getText().toString().trim();
		renshuStr = String.valueOf(np_huodong_renshu.getValue());
		feiyongStr = String.valueOf(np_huodong_feiyong.getValue());
		dateStartStr = tv_start_data.getText().toString().trim();
		dateEndStr = tv_end_data.getText().toString().trim();
		timeStartStr = tv_start_time.getText().toString().trim();
		timeEndStr = tv_end_time.getText().toString().trim();
		liuchengStr = et_huodong_liucheng.getText().toString().trim();

		if (StringUtils.isNullOrEmpty(titleStr)) {
			Toast.makeText(getApplicationContext(), "活动标题必须填写", Toast.LENGTH_SHORT).show();
			return;
		} else if (StringUtils.getCharCount(titleStr) > 100) {
			Toast.makeText(getApplicationContext(), StringsComm.huodong_title_limit, Toast.LENGTH_SHORT).show();
			return;
		}

		if (StringUtils.isNullOrEmpty(addressStr)) {
			Toast.makeText(getApplicationContext(), "活动地点必须填写", Toast.LENGTH_SHORT).show();
			return;
		} else if (StringUtils.getCharCount(addressStr) > 100) {
			Toast.makeText(getApplicationContext(), StringsComm.huodong_place_limit, Toast.LENGTH_SHORT).show();
			return;
		}

		if (StringUtils.isNullOrEmpty(renshuStr)) {
			Toast.makeText(getApplicationContext(), "活动人数必须填写", Toast.LENGTH_SHORT).show();
			return;
		}
		if (StringUtils.isNullOrEmpty(feiyongStr)) {
			Toast.makeText(getApplicationContext(), "活动费用必须填写", Toast.LENGTH_SHORT).show();
			return;
		}

		if (StringUtils.isNullOrEmpty(dateStartStr)) {
			Toast.makeText(getApplicationContext(), "活动开始日期必须填写", Toast.LENGTH_SHORT).show();
			return;
		}

		if (StringUtils.isNullOrEmpty(timeStartStr)) {
			Toast.makeText(getApplicationContext(), "活动开始时间必须填写", Toast.LENGTH_SHORT).show();
			return;
		}

		if (StringUtils.isNullOrEmpty(dateEndStr)) {
			Toast.makeText(getApplicationContext(), "活动结束日期必须填写", Toast.LENGTH_SHORT).show();
			return;
		}
		if (StringUtils.isNullOrEmpty(timeEndStr)) {
			Toast.makeText(getApplicationContext(), "活动结束时间必须填写", Toast.LENGTH_SHORT).show();
			return;
		}

		// 日期大小比较
		if (((dateStartStr.concat(timeStartStr)).compareTo(dateEndStr.concat(timeEndStr))) > 0) {
			Toast.makeText(getApplicationContext(), StringsComm.huodong_time_check, Toast.LENGTH_SHORT).show();
			return;
		}

		if (StringUtils.isNullOrEmpty(liuchengStr)) {
			Toast.makeText(getApplicationContext(), "活动流程必须填写", Toast.LENGTH_SHORT).show();
			return;
		} else if (StringUtils.getCharCount(liuchengStr) > 2000) {
			Toast.makeText(getApplicationContext(), StringsComm.huodong_liucheng_limit, Toast.LENGTH_SHORT).show();
			return;
		}

		if (StringUtils.isNullOrEmpty(picPath)) {
			Toast.makeText(getApplicationContext(), "请选择活动宣传图片", Toast.LENGTH_SHORT).show();
			return;
		}
		DoImageUpLoad();
	}

	private String getJson() {

		JSONObject o = new JSONObject();
		try {
			o.put("UserId", Integer.parseInt(userId));
			o.put("Title", titleStr);
			o.put("CapPeople", Integer.parseInt(renshuStr));
			o.put("ActivityLocate", addressStr);
			o.put("ActType", 2);
			o.put("Detail", liuchengStr);
			o.put("PreviewImg", picNetWorkPath);
			o.put("ApplyTime", dateStartStr + " " + timeStartStr + ":00");
			o.put("EndTime", dateEndStr + " " + timeEndStr + ":00");
			o.put("ActivityPrice", Integer.parseInt(feiyongStr));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return o.toString();
	}

	public void DoTextUpLoad() {

		new AsynHttpClient().post(AppConfig.HOSTURL + "PhoneActivity/Create", "data=" + getJson() + "&ExecutorID="
				+ userId, new HttpNetWorkDataHandler() {
			@Override
			public void success(int statusCode, Object obj) {
				customProgressDialog.dismiss();

				Log.i("HuoDongApplyActivity", obj.toString());
				try {
					JSONObject object = new JSONObject(obj.toString());
					if ("1".equals(object.getString("Code"))) {
						MessageBox.Instance(HuoDongApplyActivity.this).ShowToast("发表成功");
						HuoDongApplyActivity.this.finish();
					} else {

						Toast.makeText(HuoDongApplyActivity.this, object.getString("Data") + statusCode,
								Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {

				}
			}

			@Override
			public void failure(int statusCode, Object obj) {
				Log.i("HuoDongApplyActivity", obj.toString());
				customProgressDialog.dismiss();
				MessageBox.Instance(HuoDongApplyActivity.this).ShowToast("访问服务器失败，请稍微再试!" + statusCode);
			}
		});
	}

	/**
	 * 图片上传
	 */
	public void DoImageUpLoad() {

		customProgressDialog.show();

		// 如果图片大于100KB用压缩图片后在上传
		if (new File(picPath).length() > (long) 100 * 1024) {
			ImageComPressUtil.saveMyBitmap(picPath);
			picPath = PathComm.picPath + picPath.substring(picPath.lastIndexOf("/") + 1);
		}

		new AsynHttpClient().upload(AppConfig.HOSTURL + "PhoneNewsFeed/UpNewsFeedImg", picPath,
				new HttpNetWorkDataHandler() {
					@Override
					public void success(int statusCode, Object obj) {
						Log.i("HuoDongApplyActivity", obj.toString());
						try {
							JSONObject jObject = new JSONObject(obj.toString());
							if ("1".equals(jObject.getString("Code"))) {
								picNetWorkPath = new JSONObject(jObject.getString("Data")).getString("Item2");
								DoTextUpLoad();
							} else {
								MessageBox.Instance(HuoDongApplyActivity.this).ShowToast(jObject.getString("Data"));
								customProgressDialog.dismiss();
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							customProgressDialog.dismiss();
						}
					}

					@Override
					public void failure(int statusCode, Object obj) {
						Log.i("HuoDongApplyActivity", obj.toString());
						MessageBox.Instance(HuoDongApplyActivity.this).ShowToast("上传失败！");
						customProgressDialog.dismiss();
					}
				});
	}

	public void uptoclod(View view) {
		AlertDialog dialog = new AlertDialog.Builder(this)
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
		AlertDialog dialog = new AlertDialog.Builder(this)
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
					Cursor cursor = this.managedQuery(uri, pojo, null, null, null);
					if (cursor != null) {
						int colunm_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
						cursor.moveToFirst();
						picPath = cursor.getString(colunm_index);
						if (picPath.endsWith("jpg")) {
							File file = new File(picPath);
							Log.i("image_loacle_path", "size" + file.length() + "");
							if (file.length() < 5 * 1024 * 1024) {
								getImage(picPath, iv_huodong_pic);
							}
						} else {
							MessageBox.Instance(HuoDongApplyActivity.this).ShowToast("你选择的图片太大了，请重新选择5M以下的图片");
						}
					} else {
						MessageBox.Instance(HuoDongApplyActivity.this).ShowToast("请选择jpg图片");
					}
				} catch (Exception e) {
					e.printStackTrace();
					MessageBox.Instance(HuoDongApplyActivity.this).ShowToast("图片读取失败");
				}
			} else if (requestCode == 120) {
				if (picPath != null) {
					// MessageBox.Instance(HuoDongApplyActivity.this).ShowToast("正在处理...");
					getImage(picPath, iv_huodong_pic);
				}
			}
		}
	}

	@SuppressLint("SimpleDateFormat")
	private void takePhoto() {
		String SDState = Environment.getExternalStorageState();
		if (SDState.equals(Environment.MEDIA_MOUNTED)) {
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
			picPath = PathComm.picPath + formatter.format(date) + ".jpg";
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(picPath)));
			startActivityForResult(intent, 120);
		} else {
			MessageBox.Instance(HuoDongApplyActivity.this).ShowToast("内存卡不存在");
		}
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

}
