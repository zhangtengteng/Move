package com.xuanit.move.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.bitmap.AbImageDownloader;
import com.james.asynhttp.AsynHttpClient;
import com.james.asynhttp.HttpNetWorkDataHandler;
import com.xuanit.move.R;
import com.xuanit.move.adapter.NewsCommentAdapter;
import com.xuanit.move.app.AppConfig;
import com.xuanit.move.app.MoveApplication;
import com.xuanit.move.base.BaseActivity;
import com.xuanit.move.comm.StringsComm;
import com.xuanit.move.model.CommentInfo;
import com.xuanit.move.model.ZhouBianInfo;
import com.xuanit.move.util.CloseAllActivityManager;
import com.xuanit.move.util.StringUtils;
import com.xuanit.move.view.CircleImageView;
import com.xuanit.move.view.CustomProgressDialog;

public class NewscommentActivity extends BaseActivity implements OnClickListener {
	private String userId;
	private String newsId;
	private CustomProgressDialog customProgressDialog;
	private final List<CommentInfo> commentList = new ArrayList<CommentInfo>();
	private AbImageDownloader mAbImageDownloader;
	private NewsCommentAdapter adapter;
	private ZhouBianInfo newsInfo = new ZhouBianInfo();
	private LinearLayout ll_head;
	private CircleImageView iv_user_header;
	private TextView tv_user_name;
	private TextView tv_publish_time;
	private TextView tv_user_content;
	private ImageView tv_contant_pic;
	private ImageView iv_dianzan;
	private ListView lv_user_comment;
	private TextView tv_add_send;
	private EditText et_add_content;
	private String contents;
	private String data;
	private boolean yiZan;
	private MoveApplication application;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		CloseAllActivityManager.getInstance().addActivity(NewscommentActivity.this);
		super.onCreate(savedInstanceState);
		application = (MoveApplication) getApplication();
		setView(R.layout.activity_newscomment);
		setTitle("", "动弹一下", "");
		customProgressDialog = CustomProgressDialog.createDialog(this);
		mAbImageDownloader = new AbImageDownloader(this);
		initView();

		if (!StringUtils.isNullOrEmpty(application.appConfig.USER_ID)) {
			userId = application.appConfig.USER_ID;
		}
		sendReaedData();
		setListener();
		loadData();
	}

	@SuppressLint("CutPasteId")
	public void initView() {
		ll_head = (LinearLayout) getLayoutInflater().inflate(R.layout.activity_newscomment_head, null);

		iv_user_header = (CircleImageView) ll_head.findViewById(R.id.iv_user_header);
		tv_user_name = (TextView) ll_head.findViewById(R.id.tv_user_name);
		tv_publish_time = (TextView) ll_head.findViewById(R.id.tv_publish_time);
		tv_user_content = (TextView) ll_head.findViewById(R.id.tv_user_content);
		tv_contant_pic = (ImageView) ll_head.findViewById(R.id.tv_contant_pic);
		iv_dianzan = (ImageView) ll_head.findViewById(R.id.iv_dianzan);

		lv_user_comment = (ListView) findViewById(R.id.lv_user_comment);

		// iv_add_comment = (ImageView) findViewById(R.id.iv_add_comment);
		// ll_add_comment = (LinearLayout) findViewById(R.id.ll_add_comment);
		tv_add_send = (TextView) findViewById(R.id.tv_add_send);
		et_add_content = (EditText) findViewById(R.id.et_add_content);

		Bundle data = getIntent().getExtras();
		newsId = data.getString("NewsId");

		lv_user_comment.addHeaderView(ll_head);
		adapter = new NewsCommentAdapter(this, commentList,userId);
		lv_user_comment.setAdapter(adapter);

	}

	public void setListener() {
		iv_dianzan.setOnClickListener(this);
		tv_contant_pic.setOnClickListener(this);
		tv_add_send.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		super.onClick(view);
		switch (view.getId()) {
		case R.id.iv_dianzan:
			sendData(view.getId());
			break;

		case R.id.tv_contant_pic:

			Intent intent = new Intent();
			intent.setClass(NewscommentActivity.this, ImageDetailActivity.class);
			String imageSrc = "";
			if (!StringUtils.isNullOrEmpty(newsInfo.ImgSrc)) {
				if (newsInfo.ImgSrc.startsWith("~")) {
					imageSrc = newsInfo.ImgSrc.replace("~", AppConfig.HOSTURL);

				} else {
					imageSrc = newsInfo.ImgSrc;
				}

			}
			intent.putExtra("ImageSrc", imageSrc);
			intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
			startActivity(intent);
			overridePendingTransition(R.anim.image_scale_alpha_in, R.anim.stay_the_same);

		case R.id.tv_add_send:
			sendData(view.getId());
			break;
		default:
			break;
		}

	}

	public void sendReaedData() {
		new AsynHttpClient().get(AppConfig.HOSTURL + "PhoneNewsFeed/Reaed?NewsId=" + newsId + "&ExecutorID=" + userId,
				new HttpNetWorkDataHandler() {

			@Override
			public void success(int statusCode, Object obj) {
				// TODO Auto-generated method stub

				Log.i("NewscommentActivity", "newsData====" + obj.toString());
				try {
					JSONObject o = new JSONObject(obj.toString());
					String code = o.getString("Code");
					String errorData = o.getString("Data");
					if ("1".equals(code)) {
						Log.i("msg", "已阅读信息追加成功");
					} else {
						Log.i("msg", "已阅读信息追加失败----beacuse:" + errorData);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void failure(int statusCode, Object obj) {
				// TODO Auto-generated method stub
				Toast.makeText(NewscommentActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
			}
		});
	}

	public void sendData(int id) {
		if (R.id.tv_add_send == id) {
			contents = et_add_content.getEditableText().toString().trim();

			if (StringUtils.isNullOrEmpty(contents)){
				Toast.makeText(getApplicationContext(), "回复不可以为空！", Toast.LENGTH_SHORT).show();
				return;
			}else if (StringUtils.getCharCount(contents) > 250){
				Toast.makeText(getApplicationContext(), StringsComm.dongtan_reply_limit, Toast.LENGTH_SHORT).show();
				return;
			}else{
				customProgressDialog.show();
				getJson();
				new AsynHttpClient().post(AppConfig.HOSTURL + "PhoneNewComment/Create", "data=" + data,
						new HttpNetWorkDataHandler() {

					@Override
					public void success(int statusCode, Object obj) {
						// TODO Auto-generated method stub
						if(customProgressDialog!=null && customProgressDialog.isShowing()){
							customProgressDialog.dismiss();
						}

						Log.i("NewscommentActivity", "newsData==read==" + obj.toString());
						try {
							JSONObject o = new JSONObject(obj.toString());
							String code = o.getString("Code");
							String errorData = o.getString("Data");
							if ("1".equals(code)) {
								Toast.makeText(NewscommentActivity.this, "回复成功", Toast.LENGTH_SHORT).show();
								et_add_content.setText("");
								loadData();
							} else {
								Toast.makeText(NewscommentActivity.this, errorData, Toast.LENGTH_SHORT).show();
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					@Override
					public void failure(int statusCode, Object obj) {
						// TODO Auto-generated method stub
						if(customProgressDialog!=null && customProgressDialog.isShowing()){
							customProgressDialog.dismiss();
						}
						Toast.makeText(NewscommentActivity.this, "网络错误", 1).show();
					}
				});				
			}
		} else if (R.id.iv_dianzan == id) {
			customProgressDialog.show();
			if (!yiZan) {
				new AsynHttpClient().get(AppConfig.HOSTURL + "PhoneNewsFeed/Praise?NewsId=" + newsId + "&ExecutorID="
						+ userId, new HttpNetWorkDataHandler() {

					@Override
					public void success(int statusCode, Object obj) {
						// TODO Auto-generated method stub
						if(customProgressDialog!=null && customProgressDialog.isShowing()){
							customProgressDialog.dismiss();
						}

						Log.i("NewscommentActivity", "newsData==dianzan==" + obj.toString());
						try {
							JSONObject o = new JSONObject(obj.toString());
							String code = o.getString("Code");
							String errorData = o.getString("Data");
							if ("1".equals(code)) {
								// Toast.makeText(NewscommentActivity.this,
								// "已赞", 1).show();
								iv_dianzan.setImageDrawable(getResources().getDrawable(R.drawable.ic_detail_start));
								yiZan = true;
								// loadData();
							} else {
								Toast.makeText(NewscommentActivity.this, errorData, Toast.LENGTH_SHORT).show();
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					@Override
					public void failure(int statusCode, Object obj) {
						// TODO Auto-generated method stub
						if(customProgressDialog!=null && customProgressDialog.isShowing()){
							customProgressDialog.dismiss();
						}
						Toast.makeText(NewscommentActivity.this, "网络错误", 1).show();
					}
				});
			} else {
				new AsynHttpClient().get(AppConfig.HOSTURL + "PhoneNewsFeed/PraiseCancle?NewsId=" + newsId
						+ "&ExecutorID=" + userId, new HttpNetWorkDataHandler() {

					@Override
					public void success(int statusCode, Object obj) {
						// TODO Auto-generated method stub
						if(customProgressDialog!=null && customProgressDialog.isShowing()){
							customProgressDialog.dismiss();
						}

						Log.i("NewscommentActivity", "newsData==quxiaodianzan==" + obj.toString());
						try {
							JSONObject o = new JSONObject(obj.toString());
							String code = o.getString("Code");
							String errorData = o.getString("Data");
							if ("1".equals(code)) {
								// Toast.makeText(NewscommentActivity.this,
								// "取消赞", 1).show();
								iv_dianzan.setImageDrawable(getResources().getDrawable(R.drawable.ic_detail_start_1));
								yiZan = false;
								// loadData();
							} else {
								Toast.makeText(NewscommentActivity.this, errorData, 1).show();
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					@Override
					public void failure(int statusCode, Object obj) {
						// TODO Auto-generated method stub
						if(customProgressDialog!=null && customProgressDialog.isShowing()){
							customProgressDialog.dismiss();
						}
						Toast.makeText(NewscommentActivity.this, "网络错误", 1).show();
					}
				});
			}

		}
	}

	public void getJson() {
		JSONObject object = new JSONObject();
		try {
			object.put("UserId", userId);
			object.put("NewsId", newsId);
			object.put("Contents", contents);
			data = object.toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void loadData() {
		customProgressDialog.show();
		new AsynHttpClient().get(AppConfig.HOSTURL + "PhoneNewsFeed/Get?NewsId=" + newsId + "&ExecutorID=" + userId,
				new HttpNetWorkDataHandler() {

			@Override
			public void success(int statusCode, Object obj) {
				// TODO Auto-generated method stub
				if(customProgressDialog!=null && customProgressDialog.isShowing()){
					customProgressDialog.dismiss();
				}

				System.out.println("=====" + obj.toString());
				try {
					JSONObject o = new JSONObject(obj.toString());
					String code = o.getString("Code");
					if ("1".equals(code)) {
						JSONObject jObject = new JSONObject(o.getString("Data"));

						newsInfo.NewsId = jObject.getString("NewsId");
						newsInfo.UserId = jObject.getString("UserId");
						newsInfo.Contents = jObject.getString("Contents");
						newsInfo.ReadCount = jObject.getString("ReadCount");
						newsInfo.ReplyCount = jObject.getString("ReplyCount");
						newsInfo.PublishTime = jObject.getString("PublishTime");
						newsInfo.UserName = jObject.getString("UserName");
						newsInfo.Head = jObject.getString("Head");
						newsInfo.ImgSrc = jObject.getString("ImgSrc");
						newsInfo.NewComment = jObject.getString("NewComment");
						newsInfo.IsPraise = jObject.getBoolean("IsPraise");

						initData();
					} else {
						Toast.makeText(NewscommentActivity.this, o.getString("Data"), Toast.LENGTH_SHORT).show();
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void failure(int statusCode, Object obj) {
				// TODO Auto-generated method stub
				if(customProgressDialog!=null && customProgressDialog.isShowing()){
					customProgressDialog.dismiss();
				}

				Toast.makeText(NewscommentActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
			}
		});
	}

	public void initData() {
		if (mAbImageDownloader == null) {
			mAbImageDownloader = new AbImageDownloader(this);
		}
		if (!StringUtils.isNullOrEmpty(newsInfo.Head)) {
			if (newsInfo.Head.startsWith("~")) {
				String replace = newsInfo.Head.replace("~", AppConfig.HOSTURL);
				mAbImageDownloader.display(iv_user_header, replace);
			} else {
				mAbImageDownloader.display(iv_user_header, newsInfo.Head);
			}
		}

		if (!StringUtils.isNullOrEmpty(newsInfo.UserName)) {
			tv_user_name.setText(newsInfo.UserName);
		} else {
			tv_user_name.setText("");
		}

		if (!StringUtils.isNullOrEmpty(newsInfo.PublishTime)) {
			tv_publish_time.setText(newsInfo.PublishTime);
		} else {
			tv_publish_time.setText("");
		}

		if (!StringUtils.isNullOrEmpty(newsInfo.Contents)) {
			tv_user_content.setText(newsInfo.Contents);
		} else {
			tv_user_content.setText("");
		}

		if (!StringUtils.isNullOrEmpty(newsInfo.ImgSrc)) {
			if (newsInfo.ImgSrc.startsWith("~")) {
				String replace = newsInfo.ImgSrc.replace("~", AppConfig.HOSTURL);
				mAbImageDownloader.display(tv_contant_pic, replace);
			} else {
				mAbImageDownloader.display(tv_contant_pic, newsInfo.ImgSrc);
			}

		}
		if (newsInfo.IsPraise) {
			yiZan = true;
			iv_dianzan.setImageDrawable(getResources().getDrawable(R.drawable.ic_detail_start));
		} else {
			yiZan = false;
			iv_dianzan.setImageDrawable(getResources().getDrawable(R.drawable.ic_detail_start_1));
		}

		if (!StringUtils.isNullOrEmpty(newsInfo.NewComment)) {
			JSONArray NewCommentJArray;
			try {
				commentList.clear();
				NewCommentJArray = new JSONArray(newsInfo.NewComment);
				for (int j = 0; j < NewCommentJArray.length(); j++) {
					JSONObject newCommentJObject = NewCommentJArray.optJSONObject(j);
					CommentInfo commentInfo = new CommentInfo();
					commentInfo.CommentId = newCommentJObject.getString("CommentId");
					commentInfo.Contents = newCommentJObject.getString("Contents");
					commentInfo.NewsId = newCommentJObject.getString("NewsId");
					commentInfo.UserId = newCommentJObject.getString("UserId");
					commentInfo.UserName = newCommentJObject.getString("UserName");
					commentInfo.Head = newCommentJObject.getString("Head");
					commentList.add(commentInfo);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			adapter.notifyDataSetChanged();

		}

	}
}
