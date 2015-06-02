package com.xuanit.move.adapter;

import java.util.ArrayList;
import java.util.List;

import com.ab.bitmap.AbImageDownloader;
import com.xuanit.move.R;
import com.xuanit.move.app.AppConfig;
import com.xuanit.move.model.LianXiRenInfo;
import com.xuanit.move.util.StringUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LianXiRenAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;
	private List<LianXiRenInfo> list = new ArrayList<LianXiRenInfo>();

	public LianXiRenAdapter(Context context, List<LianXiRenInfo> list) {
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.list = list;
	}
	
	private AbImageDownloader mAbImageDownloader;

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
		ViewHolder holder = null;
		
		if (mAbImageDownloader == null) {
			mAbImageDownloader = new AbImageDownloader(context);
		}
		
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_info, null);
			holder.iv_userPic_lx = (ImageView) convertView
					.findViewById(R.id.iv_userPic_lx);
			holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			holder.tv_pubFriends = (TextView) convertView
					.findViewById(R.id.tv_pubFriends);
			holder.tv_count = (TextView) convertView
					.findViewById(R.id.tv_count);
			holder.tv_zhishu = (TextView) convertView
					.findViewById(R.id.tv_zhishu);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (list != null) {
			LianXiRenInfo info = list.get(position);
			if (!StringUtils.isNullOrEmpty(info.UserName)) {
				holder.tv_name.setText(info.UserName);
			} else {
				holder.tv_name.setText("");
			}
			if (!StringUtils.isNullOrEmpty(info.CommonFriends)) {
				holder.tv_pubFriends.setText("共同好友:"+info.CommonFriends);
			} else {
				holder.tv_pubFriends.setText("共同好友: 0");
			}

			if (!StringUtils.isNullOrEmpty(info.FansCount)) {
				holder.tv_count.setText("粉丝数:"+info.FansCount);
			} else {
				holder.tv_count.setText("粉丝数:0");
			}
			if (!StringUtils.isNullOrEmpty(info.FriendCount)) {
				holder.tv_zhishu.setText("好友指数:"+info.FriendCount);
			} else {
				holder.tv_zhishu.setText("好友指数:0");
			}
			if(!StringUtils.isNullOrEmpty(info.StudentCard)){
				
				if(!info.StudentCard.startsWith("~")){
					
					mAbImageDownloader.display(holder.iv_userPic_lx, info.StudentCard);
					//holder.iv_userPic_lx.setUrl(info.StudentCard);
				}else{
					//holder.iv_userPic_lx.setUrl(info.StudentCard.replace("~", AppConfig.HOSTURL));
					mAbImageDownloader.display(holder.iv_userPic_lx, info.StudentCard.replace("~", AppConfig.HOSTURL));
				}
			}else{
				//设置默认的
				holder.iv_userPic_lx.setImageDrawable(context.getResources().getDrawable(R.drawable.image_loading));
				//holder.iv_userPic_lx.getImageView().setImageResource(R.drawable.image_loading);
			}
		}
		return convertView;
	}

	class ViewHolder {
		private ImageView iv_userPic_lx;
		private TextView tv_name;
		private TextView tv_pubFriends;
		private TextView tv_count;
		private TextView tv_zhishu;
	}
}
