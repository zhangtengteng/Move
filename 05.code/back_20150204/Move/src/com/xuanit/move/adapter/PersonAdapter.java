package com.xuanit.move.adapter;

import java.util.ArrayList;
import java.util.List;

import com.ab.bitmap.AbImageDownloader;
import com.xuanit.move.R;
import com.xuanit.move.activity.OtherCenterActivity;
import com.xuanit.move.app.AppConfig;
import com.xuanit.move.model.AddPersonInfo;
import com.xuanit.move.util.StringUtils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xuanit.move.view.CircleImageView;

public class PersonAdapter extends BaseAdapter {
	private Context context;
	private List<AddPersonInfo> list = new ArrayList<AddPersonInfo>();
	private LayoutInflater inflater;
	private AbImageDownloader mAbImageDownloader;

	public PersonAdapter(Context context, List<AddPersonInfo> list) {
		System.out.println("=====PersonAdapter====");
		this.context = context;
		this.list = list;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public AddPersonInfo getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (mAbImageDownloader == null) {
			mAbImageDownloader = new AbImageDownloader(context);
		}
		if (convertView == null) {

			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_person, null);

			holder.uiv_person_head = (CircleImageView) convertView.findViewById(R.id.item_person_heade);
			holder.tv_person_name = (TextView) convertView.findViewById(R.id.item_person_name);
			holder.tv_description = (TextView) convertView.findViewById(R.id.item_person_qianming);
			holder.tv_person_school = (TextView) convertView.findViewById(R.id.tv_person_school);
			holder.item_person_distance = (TextView) convertView.findViewById(R.id.item_person_distance);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final AddPersonInfo personInfo = list.get(position);

		if (!StringUtils.isNullOrEmpty(personInfo.Head)) {
			if (personInfo.Head.startsWith("~")) {
				String replace = personInfo.Head.replace("~", AppConfig.HOSTURL);
				mAbImageDownloader.display(holder.uiv_person_head, replace);
				// holder.uiv_person_head.setUrl(replace);
			} else {
				mAbImageDownloader.display(holder.uiv_person_head, personInfo.Head);
				// holder.uiv_person_head.setUrl(personInfo.Head);
			}
		}
		// 名字
		if (!StringUtils.isNullOrEmpty(personInfo.UserName)) {
			holder.tv_person_name.setText(personInfo.UserName);
		} else {
			holder.tv_person_name.setText("不详");
		}
		// 签名
		if (!StringUtils.isNullOrEmpty(personInfo.Description)) {
			holder.tv_description.setText(personInfo.Description);
		} else {
			holder.tv_description.setText("");
		}
		// 学校
		if (!StringUtils.isNullOrEmpty(personInfo.SchoolName)) {
			holder.tv_person_school.setText(personInfo.SchoolName);
		} else {
			holder.tv_person_school.setText("不详");
		}

		// 只有周边的人才显示距离

		if (!StringUtils.isNullOrEmpty(personInfo.Distance))
			if (Double.parseDouble(personInfo.Distance) > (double)0) {
				holder.item_person_distance.setVisibility(View.VISIBLE);
				holder.item_person_distance.setText("距离：" + personInfo.Distance + "KM");
			} else {
				holder.item_person_distance.setVisibility(View.GONE);
			}
		else {
			holder.item_person_distance.setVisibility(View.GONE);
		}

		holder.uiv_person_head.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(context, OtherCenterActivity.class);
				Bundle data=new Bundle();
				data.putString("OTHER_USER_ID", personInfo.UserId);
				intent.putExtras(data);
				context.startActivity(intent);
				
			}
		});
		
		return convertView;
	}

	class ViewHolder {
		private CircleImageView uiv_person_head; // 人物头像
		private TextView tv_person_name; // 名字
		private TextView tv_description; // 签名
		private TextView tv_person_school;
		private TextView item_person_distance;// 距离
	}
}
