package com.xuanit.move.adapter;

import java.util.ArrayList;
import java.util.List;

import com.ab.bitmap.AbImageDownloader;
import com.xuanit.move.R;
import com.xuanit.move.activity.OtherCenterActivity;
import com.xuanit.move.model.TuiJianPersonInfo;
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

public class TuiJianAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;
	private List<TuiJianPersonInfo> list = new ArrayList<TuiJianPersonInfo>();

	private AbImageDownloader mAbImageDownloader;
	
	public TuiJianAdapter(Context context, List<TuiJianPersonInfo> list) {
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
		JianZhiViewHolder holder;
		if (mAbImageDownloader ==null){
			mAbImageDownloader = new AbImageDownloader(context);
		}

		if (convertView == null) {
			holder = new JianZhiViewHolder();
			convertView = inflater.inflate(R.layout.item_tuijian, null);
			
			holder.item_tuijian_name = (TextView)convertView.findViewById(R.id.item_tuijian_name);
			holder.item_tuijian_heade= (CircleImageView) convertView.findViewById(R.id.item_tuijian_heade); 
			//holder.item_tuijian_qianming = (TextView)convertView.findViewById(R.id.item_tuijian_qianming);
			//holder.item_tuijian_school = (TextView)convertView.findViewById(R.id.item_tuijian_school);
			holder.item_tuijian_dongtanshu = (TextView)convertView.findViewById(R.id.item_tuijian_dongtanshu);
			holder.item_tuijian_youxijifen = (TextView)convertView.findViewById(R.id.item_tuijian_youxijifen);
			//holder.item_friend_distance = (TextView)convertView.findViewById(R.id.item_friend_distance);
			//holder.item_friend_create_time = (TextView)convertView.findViewById(R.id.item_friend_create_time);
			convertView.setTag(holder);
		}else{
			holder = (JianZhiViewHolder) convertView.getTag();
		}
			
		if(list != null){
			final TuiJianPersonInfo tuijian = list.get(position);
			if(tuijian!=null){
				if(!StringUtils.isNullOrEmpty(tuijian.UserName)){
					holder.item_tuijian_name.setText(tuijian.UserName);
				}else{
					holder.item_tuijian_name.setText("");
				}
				if(!StringUtils.isNullOrEmpty(tuijian.Head)){
					mAbImageDownloader.display(holder.item_tuijian_heade, tuijian.Head);
					//holder.item_tuijian_heade.setUrl(tuijian.Head);
				}
				/*if(!StringUtils.isNullOrEmpty(tuijian.PersonalDescription)){
					holder.item_tuijian_qianming.setText(tuijian.PersonalDescription);
				}else{
					holder.item_tuijian_qianming.setText("");
				}
				if(!StringUtils.isNullOrEmpty(tuijian.CollegeName)){
					holder.item_tuijian_school.setText(tuijian.CollegeName);				
				}else{
					holder.item_tuijian_school.setText("");
				}*/
				if(!StringUtils.isNullOrEmpty(tuijian.MoveCount)){
					holder.item_tuijian_dongtanshu.setText(tuijian.MoveCount);
				}else{
					holder.item_tuijian_dongtanshu.setText("");
				}
				if(!StringUtils.isNullOrEmpty(tuijian.GameIntegral)){
					holder.item_tuijian_youxijifen.setText(tuijian.GameIntegral);
				}else{
					holder.item_tuijian_youxijifen.setText("");
				}
				
				holder.item_tuijian_heade.setOnClickListener(new OnClickListener() {		
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						Intent intent=new Intent();
						intent.setClass(context, OtherCenterActivity.class);
						Bundle data=new Bundle();
						data.putString("OTHER_USER_ID", tuijian.UserId);
						intent.putExtras(data);
						context.startActivity(intent);
						
					}
				});
			}
		}else{
			holder.item_tuijian_name.setText("");
			//holder.item_tuijian_qianming.setText("");
			//holder.item_tuijian_school.setText("");
			holder.item_tuijian_dongtanshu.setText("");
			holder.item_tuijian_youxijifen.setText("");
			
			//holder.item_tuijian_heade.setUrl(null);
			//holder.item_friend_distance.setText("");
			//holder.item_friend_create_time.setText("");
		}
		return convertView;
	}

	class JianZhiViewHolder {
		private TextView item_tuijian_name;
		//private TextView item_tuijian_qianming;
		//private TextView item_tuijian_school;
		private TextView item_tuijian_dongtanshu;
		private TextView item_tuijian_youxijifen;
		private CircleImageView item_tuijian_heade;
		
		//private TextView item_friend_distance;
		//private TextView item_friend_create_time;
	}

}
