package com.xuanit.move.activity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;

import com.ab.activity.AbActivity;
import com.ab.bitmap.AbImageDownloader;
import com.ab.util.AbDateUtil;
import com.james.asynhttp.AsynHttpClient;
import com.james.asynhttp.HttpNetWorkDataHandler;
import com.xuanit.move.R;
import com.xuanit.move.app.AppConfig;
import com.xuanit.move.app.MoveApplication;
import com.xuanit.move.base.BaseActivity;
import com.xuanit.move.util.CloseAllActivityManager;
import com.xuanit.move.util.ImageComPressUtil;
import com.xuanit.move.util.MessageBox;
import com.xuanit.move.util.StringUtils;
import com.xuanit.move.view.CircleImageView;
import com.xuanit.move.view.CustomProgressDialog;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class UpdatePersonMsgActivity extends AbActivity implements OnClickListener {
	final int RESULT_OK = -1;
	private CircleImageView update_user_head;
	private TextView update_description;
	private TextView update_dream;
	private TextView update_hobby;
	private TextView update_birthday;
	//private Spinner update_school;
	private LinearLayout update_commit;
	private LinearLayout ll_header_left;
	private TextView tv_header_middle;
	private AbImageDownloader abImageDownloader;
	private String description;
	private String dream;
	private String hobby;
	private String birthday;
	private String user_header_loacle_path;// 上传图片的本地路径
	private String user_header_network_path;// 已上传图片的網絡路径
	private String user_head = "";// 上传图片的路径
	private MoveApplication application;
	//private List<School> schoolList = new ArrayList<School>();
	//private List<String> schoolNameList = new ArrayList<String>();
	//private SchoolAdapter schoolAdapter;
	private CustomProgressDialog customProgressDialog;
	//private String schoolId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		CloseAllActivityManager.getInstance().addActivity(UpdatePersonMsgActivity.this);
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		application = (MoveApplication) getApplication();
		setAbContentView(R.layout.activity_update_person_msg);
		getTitleBar().setVisibility(View.GONE);

		initView();
		setListener();
		//getSchooldata();
		// 创建首选项
		/*School tempSchool = new School();
		tempSchool.Id = "";
		tempSchool.Name = AppConfig.SchoolName;
		schoolList.add(tempSchool);
		schoolNameList.add(tempSchool.Name);
		schoolAdapter = new SchoolAdapter(UpdatePersonMsgActivity.this, schoolNameList);
		update_school.setAdapter(schoolAdapter);*/

	}

	public void initView() {
		// 获取页面组件
		update_user_head = (CircleImageView) findViewById(R.id.update_user_head);
		update_description = (TextView) findViewById(R.id.update_description);
		update_dream = (TextView) findViewById(R.id.update_dream);
		update_hobby = (TextView) findViewById(R.id.update_hobby);
		update_birthday = (TextView) findViewById(R.id.update_birthday);
		update_commit = (LinearLayout) findViewById(R.id.update_commit);
		tv_header_middle = (TextView) findViewById(R.id.tv_header_middle);
		tv_header_middle.setText("更新信息");
		ll_header_left = (LinearLayout) findViewById(R.id.ll_header_left);
		ll_header_left.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				UpdatePersonMsgActivity.this.finish();
			}
		});
		
		//update_school = (Spinner) findViewById(R.id.update_school);
		customProgressDialog = CustomProgressDialog.createDialog(UpdatePersonMsgActivity.this);
		if (abImageDownloader == null) {
			abImageDownloader = new AbImageDownloader(UpdatePersonMsgActivity.this);
		}
		// 设置页面显示的内容
		abImageDownloader.display(update_user_head, application.appConfig.Head);
		if(StringUtils.isNullOrEmpty(application.appConfig.PersonalDescription)){
			update_description.setText("");
			application.appConfig.PersonalDescription = "";
		}else{
			update_description.setText(application.appConfig.PersonalDescription);
		}
		update_dream.setText(application.appConfig.Dream);
		update_hobby.setText(application.appConfig.Hobby);
		if(!StringUtils.isNullOrEmpty(application.appConfig.Birthday)){
			update_birthday.setText(application.appConfig.Birthday);
		}else{
			update_birthday.setText("");
		}
		
	}

	private void getValueFromPage() {
		// TODO Auto-generated method stub
		description = update_description.getText().toString().trim();
		dream = update_dream.getText().toString().trim();
		hobby = update_hobby.getText().toString().trim();
		birthday = update_birthday.getText().toString().trim();
		
		//String类型的日期时间转化为Date类型.
		Date dateBirthday = AbDateUtil.getDateByFormat(birthday, AbDateUtil.dateFormatYMD);
		Date dateMax = AbDateUtil.getDateByFormat(AbDateUtil.getCurrentDate(AbDateUtil.dateFormatYMD), AbDateUtil.dateFormatYMD);
		Date dateMin = AbDateUtil.getDateByFormat("1940-01-01", AbDateUtil.dateFormatYMD);
		//如果出生日期大于现在的日期
		if(dateBirthday.after(dateMax) || dateBirthday.before(dateMin)){
			Toast.makeText(getApplicationContext(), "请选择正确的生日时间！", Toast.LENGTH_SHORT).show();
			return;
		}
		
		if (StringUtils.isNullOrEmpty(birthday)) {
			Toast.makeText(getApplicationContext(), "出生日期必须填写！", Toast.LENGTH_SHORT).show();
			return;
		}
		if(description.equals(application.appConfig.PersonalDescription)
				&&dream.equals(application.appConfig.Dream)
				&&hobby.equals(application.appConfig.Hobby)
				&&birthday.equals(application.appConfig.Birthday)){
			Toast.makeText(getApplicationContext(), "请修改内容后进行更新！", Toast.LENGTH_SHORT).show();
			return;
		}
		
		updateUserMsg();

	}

	public void setListener() {
		// 设置监听器
		update_user_head.setOnClickListener(this);
		update_commit.setOnClickListener(this);
		update_description.setOnClickListener(this);
		update_dream.setOnClickListener(this);
		update_hobby.setOnClickListener(this);
		update_birthday.setOnClickListener(this);
		/*update_school.setOnItemSelectedListener(new OnItemSelectedListener() {
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
		});*/
	}

	@Override
	public void onClick(View view) {
		Calendar calendar = Calendar.getInstance();
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.update_commit:
			/*DoHeadImageUpLoad();*/
			getValueFromPage();
			break;
		case R.id.update_user_head:
			uptoclod(view);
			break;
		case R.id.update_description:
			description = update_description.getText().toString().trim();
			Intent intentDes = new Intent(UpdatePersonMsgActivity.this, EditTextActivity.class);
			intentDes.putExtra("maxLenth", 50);
			intentDes.putExtra("content", description);
			intentDes.putExtra("title", "签名");
			intentDes.putExtra("code", 0X111);
			startActivityForResult(intentDes, 0X111);
			break;
		case R.id.update_dream:
			dream = update_dream.getText().toString().trim();
			Intent intentDre = new Intent(UpdatePersonMsgActivity.this, EditTextActivity.class);
			intentDre.putExtra("maxLenth", 120);
			intentDre.putExtra("content", dream);
			intentDre.putExtra("title", "梦想");
			intentDre.putExtra("code", 0X112);
			startActivityForResult(intentDre, 0X112);
			break;
		case R.id.update_hobby:
			hobby = update_hobby.getText().toString().trim();
			Intent intentHob = new Intent(UpdatePersonMsgActivity.this, EditTextActivity.class);
			intentHob.putExtra("maxLenth", 150);
			intentHob.putExtra("content", hobby);
			intentHob.putExtra("title", "爱好");
			intentHob.putExtra("code", 0X113);
			startActivityForResult(intentHob, 0X113);
			break;
		case R.id.update_birthday:
			new DatePickerDialog(UpdatePersonMsgActivity.this, new DatePickerDialog.OnDateSetListener() {
				@Override
				public void onDateSet(DatePicker datePicker, int year, int month, int day) {
					update_birthday.setText(year + "-" + StringUtils.addZerodata(month + 1) + "-"
							+ StringUtils.addZerodata(day));
				}
			}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
		default:
			break;
		}
	}
	
	
	
	
	/*public void getSchooldata() {
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
						//Toast.makeText(getActivity(), jsonObject.getString("Item2"), Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void failure(int statusCode, Object obj) {
				Toast.makeText(UpdatePersonMsgActivity.this, "访问服务器失败，请稍微再试!" + statusCode, Toast.LENGTH_SHORT).show();
			}
		});
	}*/

	private void updateUserMsg() {
		// TODO Auto-generated method stub

		new AsynHttpClient().post(AppConfig.HOSTURL + "PhoneUser/UpdatePersonCenterUser", "data=" + getJson() + "&ExecutorID=" + application.appConfig.USER_ID,
				new HttpNetWorkDataHandler() {

					@Override
					public void success(int statusCode, Object obj) {
						if (customProgressDialog != null && customProgressDialog.isShowing() && UpdatePersonMsgActivity.this != null
								&& !UpdatePersonMsgActivity.this.isFinishing()) {
							customProgressDialog.dismiss();
						}
						System.out.println("@@@@@@@!!!!!!!!!" + obj.toString());
						JsonParseState(obj.toString());
					}

					@Override
					public void failure(int statusCode, Object obj) {
						if (customProgressDialog != null && customProgressDialog.isShowing() && UpdatePersonMsgActivity.this != null
								&& !UpdatePersonMsgActivity.this.isFinishing()) {
							customProgressDialog.dismiss();
						}
						Toast.makeText(UpdatePersonMsgActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
					}
				});
		
	}

	private void JsonParseState(String jsonStr) {
		// TODO Auto-generated method stub
		
		if (!StringUtils.isNullOrEmpty(jsonStr)) {
			try {
				JSONObject jsonObject = new JSONObject(jsonStr);
				String Code = jsonObject.getString("Code");
				//String Data = jsonObject.getString("Data");
				
				System.out.println("***********" + Code == "1");
				System.out.println("***********" + Code.equals("1"));
				if(Code.equals("1")){
					Toast.makeText(UpdatePersonMsgActivity.this, "更新成功！", Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(UpdatePersonMsgActivity.this, "更新失败！请重新设置！", Toast.LENGTH_SHORT).show();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
	}

	private String getJson() {
		JSONObject o = new JSONObject();
		try {
			if (!StringUtils.isNullOrEmpty(application.appConfig.USER_ID)) {
				
				o.put("UserId", application.appConfig.USER_ID);
				
				if(user_header_network_path==null){
					o.put("Head", application.appConfig.Head.substring(26));
					System.out.println("o.put(AppConfig.Head.substring(26));" + application.appConfig.Head.substring(26));
				}else{
					o.put("Head", user_header_network_path);
				}
				o.put("Description", description);
				o.put("Dream", dream);
				o.put("Hobby", hobby);
				o.put("Birthday", birthday);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return o.toString();
	}

	public void uptoclod(View view) {
		AlertDialog dialog = new AlertDialog.Builder(UpdatePersonMsgActivity.this)
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
		if (resultCode == RESULT_OK) {
			if (requestCode == 150) {
				Uri uri = data.getData();
				try {
					String[] pojo = { MediaStore.Images.Media.DATA };
					@SuppressWarnings("deprecation")
					Cursor cursor = UpdatePersonMsgActivity.this.managedQuery(uri, pojo, null, null, null);
					if (cursor != null) {
						int colunm_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
						cursor.moveToFirst();
						String path = cursor.getString(colunm_index);
						if (path.endsWith("jpg")) {
							File file = new File(path);
							Log.i("image_loacle_path", "size" + file.length() + "");
							if (file.length() < 5 * 1024 * 1024) {
								user_head = path;
								user_header_loacle_path = path;
								Log.i("image_loacle_path", "header" + user_header_loacle_path);
								getImage(user_header_loacle_path, update_user_head);				
							
							} else {
								MessageBox.Instance(UpdatePersonMsgActivity.this).ShowToast("你选择的图片太大了，请重新选择大小在5M以内的图片");
							}

						} else {
							
							MessageBox.Instance(UpdatePersonMsgActivity.this).ShowToast("请选择jpg图片");
						}
					} else {
						
						MessageBox.Instance(UpdatePersonMsgActivity.this).ShowToast("请选择jpg图片");
					}
				} catch (Exception e) {
					
					MessageBox.Instance(UpdatePersonMsgActivity.this).ShowToast("请选择jpg图片");
					e.printStackTrace();
				}
			} else if (requestCode == 120) {
				if (user_head != null) {
					user_header_loacle_path = user_head;
					Log.i("image_loacle_path", "header" + user_header_loacle_path);
					getImage(user_header_loacle_path, update_user_head);

				}
			 
			}
			DoHeadImageUpLoad();
		}
		
		if(resultCode == 0X111){
			description = data.getStringExtra("content");
			update_description.setText(description);
		}
		
		if(resultCode == 0X112){
			dream = data.getStringExtra("content");
			update_dream.setText(dream);
		}
		
		if(resultCode == 0X113){
			hobby = data.getStringExtra("content");
			update_hobby.setText(hobby);
		}
		
		super.onActivityResult(requestCode, resultCode, data);
	}

	@SuppressLint("SimpleDateFormat")
	private void takePhoto() {
		String SDState = Environment.getExternalStorageState();
		if (SDState.equals(Environment.MEDIA_MOUNTED)) {
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
			
			user_head = Environment.getExternalStorageDirectory() + "/image" + "/" + formatter.format(date) + ".jpg"; 
			File file = new File(Environment.getExternalStorageDirectory() + "/image");
			
			if (!file.exists()) {
				file.mkdir();
			}
			
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(user_head)));
			startActivityForResult(intent, 120);
			
		} else {
			MessageBox.Instance(UpdatePersonMsgActivity.this).ShowToast("内存卡不存在");
		}
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
		}
		System.out.println("~~~~~~~~~~~~~~~~~~359" + userimg);
		System.out.println("~~~~~~~~~~~~~~~~~360" + posturl);
		System.out.println("~~~~~~~~~~~~~~~~~361" + application.appConfig.Head);
		new AsynHttpClient().upload(AppConfig.HOSTURL + posturl, userimg, new HttpNetWorkDataHandler() {
			@Override
			public void success(int statusCode, Object obj) {
				try {
					JSONObject jObject = new JSONObject(obj.toString());
					if ("1".equals(jObject.getString("Code"))) {
						user_header_network_path = new JSONObject(jObject.getString("Data")).getString("Item2");
						System.out.println("~~~~~~~~~~~~~~~~369" + user_header_network_path);
						application.appConfig.Head  = user_header_network_path;
						getValueFromPage();
						updateUserMsg();
					} else {
						MessageBox.Instance(UpdatePersonMsgActivity.this).ShowToast(jObject.getString("Data"));
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					customProgressDialog.dismiss();
				}

			}

			@Override
			public void failure(int statusCode, Object obj) {
				MessageBox.Instance(UpdatePersonMsgActivity.this).ShowToast("上传失败！");
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
//		img.setScaleType(ScaleType.CENTER_INSIDE);
		BitmapDrawable bitmapDrawable = (BitmapDrawable) img.getDrawable();
		if (bitmapDrawable != null && !bitmapDrawable.getBitmap().isRecycled()) {
			bitmapDrawable.getBitmap().recycle();
			
		}
		img.setImageBitmap(ImageComPressUtil.getimage(img_url));
	}

}
