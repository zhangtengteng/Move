package com.xuanit.move.activity;

import io.rong.imkit.RongIM;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.james.asynhttp.AsynHttpClient;
import com.james.asynhttp.HttpNetWorkDataHandler;
import com.xuanit.move.R;
import com.xuanit.move.adapter.GameAdapter;
import com.xuanit.move.app.AppConfig;
import com.xuanit.move.base.BaseActivity;
import com.xuanit.move.model.GameInfo;
import com.xuanit.move.model.ResultInfo;
import com.xuanit.move.util.CloseAllActivityManager;
import com.xuanit.move.util.StringUtils;
import com.xuanit.move.view.CustomProgressDialog;
import com.xuanit.move.comm.ImplComm;


public class GameCenterActivity extends BaseActivity {
	private PullToRefreshListView gameRefreshListView;
	private GameAdapter gameAdapter;
	private List<GameInfo> gameList = new ArrayList<GameInfo>();
	private CustomProgressDialog customProgressDialog;
	private int flag = 0;

	private boolean isLoadOver = false;
	private int PageNo = 1;
	private int PageSize = 10;
	private int count;
	Handler mHandler = new Handler();
	private Button btn_main_zuixin;
	private Button btn_main_zuire;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		CloseAllActivityManager.getInstance().addActivity(GameCenterActivity.this);
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setView(R.layout.activity_gamecenter);
		setTitle("", "游戏中心", "");

		initView();
		setListener();
		gameAdapter = new GameAdapter(GameCenterActivity.this, gameList);
		gameRefreshListView.setAdapter(gameAdapter);
		gameAdapter.notifyDataSetChanged();

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//显示数据加载对话框；
		customProgressDialog.show();
		loadData(flag);
	}

	private void loadData(final int flag) {
		String strlink = "";
		if (0 == flag) {
			strlink = ImplComm.PhoneGame_GetListByNew;
		} else if (1 == flag) {
			strlink = ImplComm.PhoneGame_GetListByTop;
		} 
		new AsynHttpClient().post(AppConfig.HOSTURL + strlink, "data=" + getJson(), new HttpNetWorkDataHandler() {
			@Override
			public void success(int statusCode, Object obj) {
				if (customProgressDialog.isShowing()) {
					customProgressDialog.dismiss();
				}
				gameRefreshListView.onRefreshComplete();

				if (!StringUtils.isNullOrEmpty(obj.toString())) {

					ResultInfo<GameInfo> resultInfo = JsonParse(obj.toString());
					if (resultInfo != null && !StringUtils.isNullOrEmpty(resultInfo.Code)
							&& "1".equals(resultInfo.Code)) {
						if (PageNo == 1 && count == 0) {
//							Toast.makeText(GameCenterActivity.this, "木有数据", 1).show();
						}
						if (!isLoadOver) {
							gameList.addAll(resultInfo.getList());
						}
						if ((PageNo * PageSize) >= count && count != 0) {
//							Toast.makeText(GameCenterActivity.this, "已经全部加载", 1).show();
							isLoadOver = true;
						}
						gameAdapter.notifyDataSetChanged();
						gameRefreshListView.onRefreshComplete();

					}
				} else {
					Toast.makeText(GameCenterActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
					gameRefreshListView.onRefreshComplete();
					if (customProgressDialog.isShowing()) {
						customProgressDialog.dismiss();
					}
				}
			}

			@Override
			public void failure(int statusCode, Object obj) {
				// TODO Auto-generated method stub
				gameRefreshListView.onRefreshComplete();
				Toast.makeText(GameCenterActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
				if (customProgressDialog.isShowing()) {
					customProgressDialog.dismiss();
				}
			}
		});
	}

	private ResultInfo<GameInfo> JsonParse(String jsonStr) {
		if (!StringUtils.isNullOrEmpty(jsonStr)) {
			try {
				JSONObject object = new JSONObject(jsonStr);
				String Code = object.getString("Code");
				ResultInfo<GameInfo> result = new ResultInfo<GameInfo>();
				result.Code = Code;
				ArrayList<GameInfo> list_game = result.getList();

				JSONObject obj = object.getJSONObject("Data");
				String Item2 = obj.getString("Item2");
				count = obj.getInt("Item2");
				result.Item2 = Item2;
				JSONArray jsonArray = obj.getJSONArray("Item1");
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject o = new JSONObject(jsonArray.getString(i));
					GameInfo info = new GameInfo();
					info.GameId = o.getString("GameId");
					info.Name = o.getString("Name");
					info.Src = o.getString("Src");
					info.GameSize = o.getString("GameSize"); // 游戏大小M
					info.GameUrl = o.getString("GameUrl");
					info.AllDownTimes = o.getString("AllDownTimes");
					info.WeeKDownTimes = o.getString("WeeKDownTimes"); // 周下载数
					info.GameStar = o.getString("GameStar");
					info.GameType = o.getString("GameType");
					info.GameIntegral = o.getString("GameIntegral");
					info.Status = o.getString("Status");
					info.CreateTime = o.getString("CreateTime");
					info.CreateUser = o.getString("CreateUser");
					info.LastChangeTime = o.getString("LastChangeTime");
					info.LastChangeUser = o.getString("LastChangeUser");

					list_game.add(info);
				}
				return result;

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return null;

	}

	private String getJson() {
		JSONObject o = new JSONObject();
		try {
			o.put("PageNo", PageNo + "");
			o.put("PageSize", PageSize + "");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return o.toString();
	}

	private void setListener() {
		btn_main_zuixin.setOnClickListener(this);
		btn_main_zuire.setOnClickListener(this);
	}

	private void initView() {
		customProgressDialog = CustomProgressDialog.createDialog(this);
		btn_main_zuixin = (Button) findViewById(R.id.btn_zuixin);
		btn_main_zuire = (Button) findViewById(R.id.btn_zuire);

		// 处理Footer
//		LinearLayout footer_dongtan = (LinearLayout) findViewById(R.id.footer_ll_dongtan);
//		LinearLayout footer_huodong = (LinearLayout) findViewById(R.id.footer_ll_huodong);
//		LinearLayout footer_renwu = (LinearLayout) findViewById(R.id.footer_ll_renwu);
//		LinearLayout footer_tanlun = (LinearLayout) findViewById(R.id.footer_ll_tanlun);

//		footer_dongtan.setOnClickListener(this);
//		footer_huodong.setOnClickListener(this);
//		footer_renwu.setOnClickListener(this);
//		footer_tanlun.setOnClickListener(this);

		gameRefreshListView = (PullToRefreshListView) findViewById(R.id.pullToRefreshListView_game);
		gameRefreshListView.setMode(com.handmark.pulltorefresh.library.PullToRefreshBase.Mode.PULL_FROM_START);
		gameRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				if (!isLoadOver) {
					PageNo++;
				}
				loadData(flag);
			}
		});
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		/*ImageView footer_iv_dongtan = (ImageView) findViewById(R.id.footer_iv_dongtan);
		ImageView footer_iv_huodong = (ImageView) findViewById(R.id.footer_iv_huodong);
		ImageView footer_iv_renwu = (ImageView) findViewById(R.id.footer_iv_renwu);
		ImageView footer_iv_tanlun = (ImageView) findViewById(R.id.footer_iv_tanlun);*/
		switch (v.getId()) {
		case R.id.btn_zuixin:
			flag = 0;
			checkButton(flag);
			gameList.clear();
			PageNo = 1;
			isLoadOver = false;
			customProgressDialog.show();
			loadData(flag);
			break;
		case R.id.btn_zuire:
			flag = 1;
			checkButton(flag);
			gameList.clear();
			PageNo = 1;
			isLoadOver = false;
			customProgressDialog.show();
			loadData(flag);
			break;
		case R.id.footer_ll_dongtan:
			GameCenterActivity.this.finish();
			/*footer_iv_dongtan.setImageResource(R.drawable.ic_main_dontan);
			footer_iv_huodong.setImageResource(R.drawable.ic_main_acticity_1);
			footer_iv_renwu.setImageResource(R.drawable.ic_detail_people_1);
			footer_iv_tanlun.setImageResource(R.drawable.ic_main_chat_1);*/
			
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

			break;
		case R.id.footer_ll_huodong:
			
			/*footer_iv_dongtan.setImageResource(R.drawable.ic_main_dontan_1);
			footer_iv_huodong.setImageResource(R.drawable.ic_main_acticity);
			footer_iv_renwu.setImageResource(R.drawable.ic_detail_people_1);
			footer_iv_tanlun.setImageResource(R.drawable.ic_main_chat_1);*/
			startActivity(new Intent(GameCenterActivity.this, HuoDongActivity.class));
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			GameCenterActivity.this.finish();
			break;
		case R.id.footer_ll_renwu:
			/*footer_iv_dongtan.setImageResource(R.drawable.ic_main_dontan_1);
			footer_iv_huodong.setImageResource(R.drawable.ic_main_acticity_1);
			footer_iv_renwu.setImageResource(R.drawable.ic_detail_people);
			footer_iv_tanlun.setImageResource(R.drawable.ic_main_chat_1);*/
			startActivity(new Intent(GameCenterActivity.this, DongtanActivity.class));
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			GameCenterActivity.this.finish();
			break;
		case R.id.footer_ll_tanlun:
			
			RongIM.getInstance().startConversationList(GameCenterActivity.this);
			/*mHandler.post(new Runnable() {
			mHandler.post(new Runnable() {
				@Override
				public void run() {
					RongIM.getInstance().startConversationList(GameCenterActivity.this);
				}
			});*/
			GameCenterActivity.this.finish();
			break;

		default:
			break;
		}
	}

	private void checkButton(int flag) {
		btn_main_zuixin.setBackgroundResource(R.drawable.bt_left_shape);
		btn_main_zuixin.setTextColor(getResources().getColor(R.color.white));
		btn_main_zuire.setBackgroundResource(R.drawable.bt_right_shape);
		btn_main_zuire.setTextColor(getResources().getColor(R.color.white));
		switch (flag) {
		case 0:
			btn_main_zuixin.setBackgroundResource(R.drawable.bt_left_click_shape);
			btn_main_zuixin.setTextColor(getResources().getColor(R.color.baseColor));
			break;
		case 1:
			btn_main_zuire.setBackgroundResource(R.drawable.bt_right_click_shape);
			btn_main_zuire.setTextColor(getResources().getColor(R.color.baseColor));
			break;
		}
	}
}
