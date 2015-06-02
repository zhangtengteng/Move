package com.xuanit.move.adapter;

import java.util.ArrayList;
import java.util.List;

import com.ab.bitmap.AbImageDownloader;
import com.xuanit.move.R;
import com.xuanit.move.activity.OtherCenterActivity;
import com.xuanit.move.activity.PersonalCenterActivity;
import com.xuanit.move.app.AppConfig;
import com.xuanit.move.app.MoveApplication;
import com.xuanit.move.model.CommentInfo;
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

public class NewsCommentAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;
	private List<CommentInfo> commentList = new ArrayList<CommentInfo>();
	private AbImageDownloader mAbImageDownloader;
	private String userUserId;
	
	public NewsCommentAdapter(Context context, List<CommentInfo> commentList,String userUserId) {
		this.commentList = commentList;
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.userUserId=userUserId;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return commentList.size();
	}

	@Override
	public CommentInfo getItem(int positiosn) {
		// TODO Auto-generated method stub
		return commentList.get(positiosn);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (mAbImageDownloader == null) {
			mAbImageDownloader = new AbImageDownloader(context);
		}
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_newscomment, null);
			holder.iv_user_header = (CircleImageView) convertView.findViewById(R.id.iv_user_header);
			holder.tv_newscomment_username = (TextView) convertView.findViewById(R.id.tv_newscomment_username);
			holder.tv_newscomment_usercontents = (TextView) convertView.findViewById(R.id.tv_newscomment_usercontents);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (!StringUtils.isNullOrEmpty(getItem(position).UserName)) {
			holder.tv_newscomment_username.setText(getItem(position).UserName + ":");
		}
		if (!StringUtils.isNullOrEmpty(getItem(position).Contents)) {
			holder.tv_newscomment_usercontents.setText(getItem(position).Contents);
		}
		if (!StringUtils.isNullOrEmpty(getItem(position).Head)) {
			if (getItem(position).Head.startsWith("~")) {
				String replace = getItem(position).Head.replace("~", AppConfig.HOSTURL);
				mAbImageDownloader.display(holder.iv_user_header, replace);
				// holder.iv_user_header.setUrl(replace);
			} else {
				mAbImageDownloader.display(holder.iv_user_header, getItem(position).Head);
				// holder.iv_user_header.setUrl(getItem(position).Head);
			}
		}

		holder.iv_user_header.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (getItem(position).UserId.equals(userUserId)) {
					context.startActivity(new Intent(context, PersonalCenterActivity.class));
				} else {
					Intent intent = new Intent();
					intent.setClass(context, OtherCenterActivity.class);
//					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					Bundle data = new Bundle();
					data.putString("OTHER_USER_ID", getItem(position).UserId);
					intent.putExtras(data);
					context.startActivity(intent);
				}
			}
		});

		return convertView;
	}

	class ViewHolder {
		private CircleImageView iv_user_header;
		private TextView tv_newscomment_username;
		private TextView tv_newscomment_usercontents;

	}
}
