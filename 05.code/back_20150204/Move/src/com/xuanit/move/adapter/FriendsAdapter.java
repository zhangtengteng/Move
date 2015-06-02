package com.xuanit.move.adapter;

import io.rong.imkit.RongIM;

import java.util.List;

import com.ab.bitmap.AbImageDownloader;
import com.xuanit.move.R;
import com.xuanit.move.activity.OtherCenterActivity;
import com.xuanit.move.model.FriendInfo;
import com.xuanit.move.util.StringUtils;
import com.xuanit.move.view.CircleImageView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FriendsAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;
	private List<FriendInfo> list = null;
	private AbImageDownloader mAbImageDownloader;
	private Handler handler = new Handler();
	public FriendsAdapter(Context context, List<FriendInfo> list) {
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
	public Object getItem(int position) {
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
		// TODO Auto-generated method stub
		if (mAbImageDownloader == null) {
			mAbImageDownloader = new AbImageDownloader(context);
		}
		
		JianZhiViewHolder holder;
		if (convertView == null) {
			holder = new JianZhiViewHolder();
			convertView = inflater.inflate(R.layout.item_friend, null);
			
			holder.item_friend_heade = (CircleImageView) convertView.findViewById(R.id.item_friend_heade);
			holder.item_friends_name = (TextView)convertView.findViewById(R.id.item_friends_name);
			holder.item_friend_qianming = (TextView)convertView.findViewById(R.id.item_friend_qianming);
			holder.item_friend_school = (TextView)convertView.findViewById(R.id.item_friend_school);
			holder.item_friend_num = (TextView)convertView.findViewById(R.id.item_friend_num);
			holder.item_fensi_num = (TextView)convertView.findViewById(R.id.item_fensi_num);
			holder.item_friend_chat = (LinearLayout) convertView.findViewById(R.id.item_friend_chat);
			//holder.item_friend_distance = (TextView)convertView.findViewById(R.id.item_friend_distance);
			//holder.item_friend_create_time = (TextView)convertView.findViewById(R.id.item_friend_create_time);
			convertView.setTag(holder);
		}else{
			holder = (JianZhiViewHolder) convertView.getTag();
		}
			
		if(list != null){
			final FriendInfo friend = list.get(position);
			if(friend!=null){
				
				if(!StringUtils.isNullOrEmpty(friend.Head)){
					mAbImageDownloader.display(holder.item_friend_heade, friend.Head);
				}
				if(!StringUtils.isNullOrEmpty(friend.UserName)){
					holder.item_friends_name.setText(friend.UserName);
				}else{
					holder.item_friends_name.setText("");
				}
				if(!StringUtils.isNullOrEmpty(friend.PersonalDescription)){
					holder.item_friend_qianming.setText("此人很懒什么都没有留下！");
				}else{
					holder.item_friend_qianming.setText("此人很懒什么都没有留下！");
				}
				if(!StringUtils.isNullOrEmpty(friend.CollegeName)){
					holder.item_friend_school.setText(friend.CollegeName);				
				}else{
					holder.item_friend_school.setText("");
				}
				if(!StringUtils.isNullOrEmpty(friend.FriendCount)){
					holder.item_friend_num.setText(friend.FriendCount);
				}else{
					holder.item_friend_num.setText("");
				}
				if(!StringUtils.isNullOrEmpty(friend.FansCount)){
					holder.item_fensi_num.setText(friend.FansCount);
				}else{
					holder.item_fensi_num.setText("");
				}
/*				
				holder.item_friend_chat.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						handler.post(new Runnable() {
							@Override
							public void run() {
								// TODO Auto-generated method stub
								RongIM.getInstance().startPrivateChat(context, friend.UserId, friend.UserName);
							}
						});
					
					}
				});
				*/
				holder.item_friend_heade.setOnClickListener(new OnClickListener() {					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						Intent intent=new Intent();
						intent.setClass(context, OtherCenterActivity.class);
						Bundle data=new Bundle();
						data.putString("OTHER_USER_ID", friend.UserId);
						intent.putExtras(data);
						context.startActivity(intent);					
					}
				});
			
			}
			
		}else{
			holder.item_friends_name.setText("");
			holder.item_friend_qianming.setText("");
			holder.item_friend_school.setText("");
			holder.item_friend_num.setText("");
			holder.item_fensi_num.setText("");

		}
		return convertView;
	}

	class JianZhiViewHolder {
		private CircleImageView item_friend_heade;
		private TextView item_friends_name;
		private TextView item_friend_qianming;
		private TextView item_friend_school;
		private TextView item_friend_num;
		private TextView item_fensi_num;
		private LinearLayout item_friend_chat;
		//private TextView item_friend_distance;
		//private TextView item_friend_create_time;
	}

}
