package com.xuanit.move.activity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.james.asynhttp.AsynHttpClient;
import com.james.asynhttp.HttpNetWorkDataHandler;
import com.xuanit.move.R;
import com.xuanit.move.app.AppConfig;
import com.xuanit.move.app.MoveApplication;
import com.xuanit.move.base.BaseActivity;
import com.xuanit.move.comm.PathComm;
import com.xuanit.move.comm.StringsComm;
import com.xuanit.move.util.CloseAllActivityManager;
import com.xuanit.move.util.ImageComPressUtil;
import com.xuanit.move.util.MessageBox;
import com.xuanit.move.util.StringUtils;
import com.xuanit.move.view.CustomProgressDialog;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;

public class SpeechActivity extends BaseActivity implements OnClickListener {

	private ImageView iv_add;
	private EditText et_speech;
	private String userId;
	private final int latitude = 0;
	private final int lontitude = 0;
	private CustomProgressDialog customProgressDialog;

	private String contents;
	private String imagNetWorkPath;
	private String imgLocalPath;
	private String data;
	private MoveApplication application;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		CloseAllActivityManager.getInstance().addActivity(SpeechActivity.this);
		super.onCreate(savedInstanceState);
		application = (MoveApplication) getApplication();
		setView(R.layout.activity_speech);
		setTitle("", "发表动弹", "发表");

		initView();
		customProgressDialog = CustomProgressDialog.createDialog(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		if (!StringUtils.isNullOrEmpty(application.appConfig.USER_ID)) {
			userId = application.appConfig.USER_ID;
		}
	}

	private void initView() {
		iv_add = (ImageView) findViewById(R.id.iv_speech_add);
		et_speech = (EditText) findViewById(R.id.et_speech_content);
		iv_add.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {

		case R.id.ll_header_right:
			contents = et_speech.getText().toString().trim();
			DoImageUpLoad();
			break;

		case R.id.iv_speech_add:
			AlertDialog dialog = new AlertDialog.Builder(SpeechActivity.this)
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

		default:
			break;
		}
	}

	public void check() {

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
						imgLocalPath = cursor.getString(colunm_index);
						if (imgLocalPath.endsWith("jpg")) {
							File file = new File(imgLocalPath);
							Log.i("image_loacle_path", "size" + file.length() + "");
							if (file.length() < 5 * 1024 * 1024) {
								getImage(imgLocalPath, iv_add);
							}
						} else {
							MessageBox.Instance(SpeechActivity.this).ShowToast("你选择的图片太大了，请重新选择5M以下的图片");
						}
					} else {
						MessageBox.Instance(SpeechActivity.this).ShowToast("请选择jpg图片");
					}
				} catch (Exception e) {
					e.printStackTrace();
					MessageBox.Instance(SpeechActivity.this).ShowToast("图片读取失败请重试！");
				}
			} else if (requestCode == 120) {
				if (imgLocalPath != null) {
					// MessageBox.Instance(SpeechActivity.this).ShowToast("正在处理...");
					getImage(imgLocalPath, iv_add);
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
			imgLocalPath = PathComm.picPath + formatter.format(date) + ".jpg";
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(imgLocalPath)));
			startActivityForResult(intent, 120);
		} else {
			MessageBox.Instance(SpeechActivity.this).ShowToast("内存卡不存在");
		}
	}

	private void getJson() {

		JSONObject o = new JSONObject();
		try {
			o.put("UserId", userId);
			o.put("Contents", contents);
			o.put("ImgSrc", imagNetWorkPath);
			if ((int) application.latitude == 0 && (int) application.lontitude == 0) {
				o.put("Latitude", 0);
				o.put("Longitude", 0);
			} else {
				o.put("Latitude", application.latitude);
				o.put("Longitude", application.lontitude);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		data = o.toString();
	}

	public void DoTextUpLoad() {
		getJson();
		new AsynHttpClient().post(AppConfig.HOSTURL + "PhoneNewsFeed/Create", "data=" + data + "&ExecutorID=" + userId,
				new HttpNetWorkDataHandler() {
					@Override
					public void success(int statusCode, Object obj) {
						customProgressDialog.dismiss();
						try {
							JSONObject object = new JSONObject(obj.toString());
							if ("1".equals(object.getString("Code"))) {
								MessageBox.Instance(SpeechActivity.this).ShowToast("发表成功");
								SpeechActivity.this.finish();
							} else {
								Toast.makeText(SpeechActivity.this, object.getString("Data") + statusCode,
										Toast.LENGTH_SHORT).show();
							}
						} catch (JSONException e) {

						}
					}

					@Override
					public void failure(int statusCode, Object obj) {
						customProgressDialog.dismiss();
						MessageBox.Instance(SpeechActivity.this).ShowToast("访问服务器失败，请稍微再试!" + statusCode);
					}
				});
	}

	/**
	 * 图片上传
	 */
	public void DoImageUpLoad() {

		if (StringUtils.isNullOrEmpty(contents)) {
			Toast.makeText(this, "发表的内容不可以为空", 1).show();
			return;
		} else if (StringUtils.getCharCount(contents) > 1000) {
			Toast.makeText(getApplicationContext(), StringsComm.dongtan_content_limit, Toast.LENGTH_SHORT).show();
			return;
		}
		if (StringUtils.isNullOrEmpty(imgLocalPath)) {
			Toast.makeText(this, "请添加图片", 1).show();
			return;
		}
		customProgressDialog.show();

		// 如果图片大于100KB用压缩图片后在上传
		if (new File(imgLocalPath).length() > (long) 100 * 1024) {
			ImageComPressUtil.saveMyBitmap(imgLocalPath);
			imgLocalPath = PathComm.picPath + imgLocalPath.substring(imgLocalPath.lastIndexOf("/") + 1);
		}

		new AsynHttpClient().upload(AppConfig.HOSTURL + "PhoneNewsFeed/UpNewsFeedImg", imgLocalPath,
				new HttpNetWorkDataHandler() {
					@Override
					public void success(int statusCode, Object obj) {
						try {
							JSONObject jObject = new JSONObject(obj.toString());
							if ("1".equals(jObject.getString("Code"))) {
								imagNetWorkPath = new JSONObject(jObject.getString("Data")).getString("Item2");
								DoTextUpLoad();
							} else {
								MessageBox.Instance(SpeechActivity.this).ShowToast(jObject.getString("Data"));
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
						MessageBox.Instance(SpeechActivity.this).ShowToast("上传失败！");
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
		File file = new File(img_url);

		if (file.length() > (long) 100 * 1024) {
			img.setImageBitmap(ImageComPressUtil.getimage(img_url));
		} else {
			img.setImageBitmap(BitmapFactory.decodeFile(img_url));
		}

	}

}
