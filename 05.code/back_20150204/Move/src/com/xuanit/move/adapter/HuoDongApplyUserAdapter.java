package com.xuanit.move.adapter;

import java.util.ArrayList;
import java.util.List;

import com.ab.bitmap.AbImageDownloader;
import com.xuanit.move.R;
import com.xuanit.move.activity.OtherCenterActivity;
import com.xuanit.move.app.AppConfig;
import com.xuanit.move.model.ApplyUserInfo;
import com.xuanit.move.util.StringUtils;
import com.xuanit.move.view.CircleImageView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HuoDongApplyUserAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;
	private List<ApplyUserInfo> list = new ArrayList<ApplyUserInfo>();
	private AbImageDownloader mAbImageDownloader;

	public HuoDongApplyUserAdapter(Context context, List<ApplyUserInfo> list) {
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public ApplyUserInfo getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (mAbImageDownloader == null) {
			mAbImageDownloader = new AbImageDownloader(context);
		}
		
		JianZhiViewHolder holder;
		if (convertView == null) {
			holder = new JianZhiViewHolder();
			convertView = inflater.inflate(R.layout.item_baoming_user, null);

			holder.tv_apply_name = (TextView) convertView.findViewById(R.id.tv_apply_name);
			holder.iv_apply_user_heade = (CircleImageView) convertView.findViewById(R.id.iv_apply_user_heade);
			holder.tv_apply_user_contents = (TextView) convertView.findViewById(R.id.tv_apply_user_contents);
			convertView.setTag(holder);
		} else {
			holder = (JianZhiViewHolder) convertView.getTag();
		}

		if (!StringUtils.isNullOrEmpty(list.get(position).UserName)) {
			holder.tv_apply_name.setText(list.get(position).UserName);
		} else {
			holder.tv_apply_name.setText("");
		}

		if (!StringUtils.isNullOrEmpty(list.get(position).Head)) {
			if (list.get(position).Head.startsWith("~")) {
				String replace = list.get(position).Head.replace("~", AppConfig.HOSTURL);
				mAbImageDownloader.display(holder.iv_apply_user_heade, replace);
			} else {
				String replace = AppConfig.HOSTURL + list.get(position).Head;
				mAbImageDownloader.display(holder.iv_apply_user_heade, replace);
			}
		}

		if (!StringUtils.isNullOrEmpty(list.get(position).Detail)) {
			holder.tv_apply_user_contents.setText(list.get(position).Detail);
		} else {
			holder.tv_apply_user_contents.setText("");
		}
		
		holder.iv_apply_user_heade.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(context, OtherCenterActivity.class);
				Bundle data=new Bundle();
				data.putString("OTHER_USER_ID", list.get(position).UserId);
				intent.putExtras(data);
				context.startActivity(intent);
			}
		});

		return convertView;
	}
	
	

	class JianZhiViewHolder {
		private TextView tv_apply_name;
		private CircleImageView iv_apply_user_heade;
		private TextView tv_apply_user_contents;
	}

}
