package com.xuanit.move.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.bitmap.AbImageDownloader;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.james.asynhttp.AsynHttpClient;
import com.james.asynhttp.HttpNetWorkDataHandler;
import com.xuanit.move.R;
import com.xuanit.move.adapter.HuoDongAdapter;
import com.xuanit.move.adapter.MainAdapter;
import com.xuanit.move.app.AppConfig;
import com.xuanit.move.app.MoveApplication;
import com.xuanit.move.base.BaseActivity;
import com.xuanit.move.model.HuoDongInfo;
import com.xuanit.move.model.ResultInfo;
import com.xuanit.move.model.ZhouBianInfo;
import com.xuanit.move.util.CloseAllActivityManager;
import com.xuanit.move.util.StringUtils;
import com.xuanit.move.view.CustomProgressDialog;

public class OtherCenterDetailActivity extends BaseActivity implements OnClickListener {
	final private String ACTIVITY = "PhoneActivity/GetJoinListByUserId";
	final private String MOVE = "PhoneNewsFeed/GetPersonalCenter";

	private int flag = 1;
	private String otherUserId;
	private String otherUserName;
	private PullToRefreshListView prl_other;
	private TextView tv_tip;

	private AbImageDownloader mAbImageDownloader;
	private CustomProgressDialog customProgressDialog;
	private Toast toast;
	
	
	private int PageNo = 1;
	private final int PageSize = 5;
	private boolean isLoadOver = false;
	private int count;

	private List<HuoDongInfo> listHuoDong = new ArrayList<HuoDongInfo>();
	private List<ZhouBianInfo> listZhouBian = new ArrayList<ZhouBianInfo>();
	private BaseAdapter adapter;
	private MoveApplication application;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		CloseAllActivityManager.getInstance().addActivity(OtherCenterDetailActivity.this);
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setView(R.layout.activity_other_info);
		application = (MoveApplication) getApplication();
		
		flag = getIntent().getExtras().getInt("FLAG");
		otherUserId = getIntent().getExtras().getString("OTHER_USER_ID");
		otherUserName = getIntent().getExtras().getString("OTHER_USER_NAME");

		initView();
		initData();
		
		if (flag == 1) {
			setTitle("", otherUserName + "的相关活动", "");
			adapter = new HuoDongAdapter(this, listHuoDong,0);
			prl_other.setAdapter(adapter);
		} else {
			setTitle("", otherUserName + "的动弹", "");
			adapter = new MainAdapter(this, listZhouBian,application.appConfig.USER_ID);
			prl_other.setAdapter(adapter);
		}

		customProgressDialog.show();
		loadData();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);

	}

	private void initView() {
		customProgressDialog = CustomProgressDialog.createDialog(this);
		
//		iv_user_head = (CircleImageView) findViewById(R.id.iv_user_heade);
//		tv_other_name = (TextView) findViewById(R.id.tv_other_name);
		tv_tip=(TextView) findViewById(R.id.tv_tip);
		prl_other = (PullToRefreshListView) findViewById(R.id.prl_other);
		tv_tip.setVisibility(View.GONE);

		prl_other.setMode(com.handmark.pulltorefresh.library.PullToRefreshBase.Mode.PULL_FROM_END);
		prl_other.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				if (!isLoadOver) {
					PageNo++;
				}
				loadData();
			}
		});
	}

	private void initData(){
//		
//		mAbImageDownloader=new AbImageDownloader(this);
//		if(!StringUtils.isNullOrEmpty(otherUserHead)){
//			mAbImageDownloader.display(iv_user_head, otherUserHead);
//		}
//		tv_other_name.setText(otherUserName);
//		
	}
	
	private void loadData() {
		if (flag == 1) {
			new AsynHttpClient().post(AppConfig.HOSTURL + ACTIVITY, "data=" + getJson(), new HttpNetWorkDataHandler() {

				@Override
				public void success(int statusCode, Object obj) {
					// TODO Auto-generated method stub
					prl_other.onRefreshComplete();
					
					if (customProgressDialog != null && customProgressDialog.isShowing()) {
						customProgressDialog.dismiss();
					}

					ResultInfo<HuoDongInfo> resultInfo = parseActivityJson(obj.toString());


					
					if (resultInfo != null) {
						ArrayList<HuoDongInfo> list = resultInfo.getList();
						if (PageNo == 1 && count == 0) {
							Log.i("OtherCenterDetailActivity", "HuoDongInfo木有数据");
						}

						if (!isLoadOver) {
							listHuoDong.addAll(list);
						}
						if ((PageNo * PageSize) >= count && count != 0) {
							Log.i("OtherCenterDetailActivity", "HuoDongInfo已经全部加载");
							isLoadOver = true;
						}

						adapter.notifyDataSetChanged();		
						
						if(count<=0){
							tv_tip.setVisibility(View.VISIBLE);
						}
					}

				}

				@Override
				public void failure(int statusCode, Object obj) {
					// TODO Auto-generated method stub
					prl_other.onRefreshComplete();
					if (customProgressDialog != null && customProgressDialog.isShowing()) {
						customProgressDialog.dismiss();
					}
					getToast("服务器响应异常，请稍后重试").show();
				}
			});
		} else {
			new AsynHttpClient().post(AppConfig.HOSTURL + MOVE, "data=" + getJson(), new HttpNetWorkDataHandler() {

				@Override
				public void success(int statusCode, Object obj) {
					// TODO Auto-generated method stub
					prl_other.onRefreshComplete();
					
					if (customProgressDialog != null && customProgressDialog.isShowing()) {
						customProgressDialog.dismiss();
					}
					ResultInfo<ZhouBianInfo> resultInfo = parseMoveJson(obj.toString());

					if (resultInfo != null) {
						ArrayList<ZhouBianInfo> list = resultInfo.getList();
						if (PageNo == 1 && count == 0) {
							Log.i("OtherCenterDetailActivity", "ZhouBianInfo木有数据");
						}
						if (!isLoadOver) {
							listZhouBian.addAll(list);
						}
						if ((PageNo * PageSize) >= count && count != 0) {
							Log.i("OtherCenterDetailActivity", "ZhouBianInfo已经全部加载");
							isLoadOver = true;
						}
						adapter.notifyDataSetChanged();
						
						if(count<=0){
							tv_tip.setVisibility(View.VISIBLE);
						}
					}

				}

				@Override
				public void failure(int statusCode, Object obj) {
					// TODO Auto-generated method stub
					prl_other.onRefreshComplete();
					if (customProgressDialog != null && customProgressDialog.isShowing()) {
						customProgressDialog.dismiss();
					}
					getToast("服务器响应异常，请稍后重试").show();
				}
			});
		}

	}

	private ResultInfo<HuoDongInfo> parseActivityJson(String jsonStr) {
		if (!StringUtils.isNullOrEmpty(jsonStr)) {

			ResultInfo<HuoDongInfo> resultInfo = new ResultInfo<HuoDongInfo>();
			try {
				JSONObject jsonObject = new JSONObject(jsonStr);
				resultInfo.Code = jsonObject.getString("Code");
				JSONObject jsonObject2 = jsonObject.getJSONObject("Data");
				resultInfo.Item2 = jsonObject2.getString("Item2");
				count = Integer.parseInt(resultInfo.Item2);
				JSONArray jsonArray = jsonObject2.getJSONArray("Item1");

				if ("1".equals(resultInfo.Code)) {
					ArrayList<HuoDongInfo> list = resultInfo.getList();
					for (int i = 0; i < jsonArray.length(); i++) {
						HuoDongInfo info = new HuoDongInfo();
						JSONObject object = (JSONObject) jsonArray.get(i);
						info.ActId = object.getString("ActId");
						info.UserId = object.getString("UserId");
						info.Title = object.getString("Title");
						info.CapPeople = object.getString("CapPeople");
						info.ActivityLocate = object.getString("ActivityLocate");
						info.ActType = object.getString("ActType");
						info.Detail = object.getString("Detail");
						info.PreviewImg = object.getString("PreviewImg");
						info.ApplyTime = object.getString("ApplyTime");
						info.EndTime = object.getString("EndTime");
						info.ParticipateCount = object.getString("ParticipateCount");
						info.ActivityPrice = object.getString("ActivityPrice");
						list.add(info);
					}
					return resultInfo;
				}

			} catch (JSONException e) {
				e.printStackTrace();
				Log.i("OtherCenterDetailActivity", "JSON转换异常");
			}
		}
		return null;
	}

	private ResultInfo<ZhouBianInfo> parseMoveJson(String jsonStr) {
		if (!StringUtils.isNullOrEmpty(jsonStr)) {

			ResultInfo<ZhouBianInfo> resultInfo = new ResultInfo<ZhouBianInfo>();
			try {
				JSONObject jsonObject = new JSONObject(jsonStr);
				resultInfo.Code = jsonObject.getString("Code");
				JSONObject jsonObject2 = jsonObject.getJSONObject("Data");
				resultInfo.Item2 = jsonObject2.getString("Item2");
				count = Integer.parseInt(resultInfo.Item2);
				JSONArray jsonArray = jsonObject2.getJSONArray("Item1");

				if ("1".equals(resultInfo.Code)) {
					ArrayList<ZhouBianInfo> list = resultInfo.getList();
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject object = (JSONObject) jsonArray.get(i);
						ZhouBianInfo zhouBianInfo = new ZhouBianInfo();

						zhouBianInfo.NewsId = object.getString("NewsId");
						zhouBianInfo.UserId = object.getString("UserId");
						zhouBianInfo.Contents = object.getString("Contents");
						zhouBianInfo.ReadCount = object.getString("ReadCount");
						zhouBianInfo.ReplyCount = object.getString("ReplyCount");
						zhouBianInfo.PublishTime = object.getString("PublishTime");
						zhouBianInfo.UserName = object.getString("UserName");
						zhouBianInfo.Head = object.getString("Head");
						zhouBianInfo.ImgSrc = object.getString("ImgSrc");
						zhouBianInfo.NewComment = object.getString("NewComment");
						zhouBianInfo.PraiseCount = object.getString("PraiseCount");
						zhouBianInfo.IsPraise = object.getBoolean("IsPraise");
						list.add(zhouBianInfo);

					}
					return resultInfo;
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.i("OtherCenterDetailActivity", "JSON转换异常");
			}
		}
		return null;
	}

	private String getJson() {
		
		JSONObject object = new JSONObject();
		try {
			if(flag==1){
				object.put("PageNo", PageNo + "");
			}else{
				object.put("PageIndex", PageNo + "");
			}
			object.put("PageSize", PageSize + "");
			object.put("UserId", otherUserId);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return object.toString();
	}

	private Toast getToast(String msg) {
		if (toast == null) {
			toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
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
