package com.xuanit.move.adapter;

import java.util.ArrayList;
import java.util.List;



import com.ab.bitmap.AbImageDownloader;
import com.xuanit.move.R;
import com.xuanit.move.activity.HuoDongBaomingActivity;
import com.xuanit.move.activity.HuoDongDetailActivity;
import com.xuanit.move.app.AppConfig;
import com.xuanit.move.model.HuoDongInfo;
import com.xuanit.move.util.StringUtils;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HuoDongAdapter extends BaseAdapter{
	private List<HuoDongInfo> list = new ArrayList<HuoDongInfo>();
	private Context context;
	private LayoutInflater inflater;
	private int flag=1;//报名信息显示flag
	
	private AbImageDownloader mAbImageDownloader;
	
	public HuoDongAdapter(Context context,List<HuoDongInfo> list,int flag){
		this.list = list;
		this.context = context;
		inflater = LayoutInflater.from(context);
		this.flag=flag;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public HuoDongInfo getItem(int position) {
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
		if(mAbImageDownloader==null){
			mAbImageDownloader=new AbImageDownloader(context);
		}
		ViewHolder holder;
		if(convertView == null){
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_huodong, null);
			holder.uri_item_huodong = (ImageView) convertView.findViewById(R.id.uiv_item_huodong);
			holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);		
			holder.tv_detail = (TextView) convertView.findViewById(R.id.tv_detail);
			holder.rl_detail_head=(RelativeLayout)convertView.findViewById(R.id.rl_detail_head);
			holder.tv_canyu = (ImageView) convertView.findViewById(R.id.tv_canyu);
			holder.people_num = (TextView) convertView.findViewById(R.id.people_num);
			
			convertView.setTag(holder);
			
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		if(flag==1){
			holder.rl_detail_head.setVisibility(View.VISIBLE);
		}else{
			holder.rl_detail_head.setVisibility(View.GONE);
		}
		
		HuoDongInfo huoDongInfo = list.get(position);
		if(!StringUtils.isNullOrEmpty(huoDongInfo.PreviewImg)){
			if(huoDongInfo.PreviewImg.startsWith("~")){
				mAbImageDownloader.display(holder.uri_item_huodong, huoDongInfo.PreviewImg.replace("~", AppConfig.HOSTURL));
//				holder.uri_item_huodong.setUrl(huoDongInfo.PreviewImg.replace("~", ""));
			}else{
				mAbImageDownloader.display(holder.uri_item_huodong,huoDongInfo.PreviewImg);
//				holder.uri_item_huodong.setUrl(huoDongInfo.PreviewImg);
			}
		}
		
		if(!StringUtils.isNullOrEmpty(huoDongInfo.Title)){
			holder.tv_title.setText(huoDongInfo.Title);
		}else{
			holder.tv_title.setText("");
		}
		
		if(!StringUtils.isNullOrEmpty(huoDongInfo.Detail)){
			holder.tv_detail.setText(huoDongInfo.Detail);
		}else{
			holder.tv_detail.setText("");
		}
		
		if(!StringUtils.isNullOrEmpty(huoDongInfo.ParticipateCount)){
			holder.people_num.setText(huoDongInfo.ParticipateCount);
		}else{
			holder.people_num.setText("0");
		}
		
		holder.tv_canyu.setOnClickListener(new View.OnClickListener() {
		    
		    @Override
		    public void onClick(View arg0) {
			Intent intent=new Intent();
			intent.setClass(context, HuoDongBaomingActivity.class);
			intent.putExtra("huoDongId", getItem(position).ActId);
			context.startActivity(intent);
		    }
		});
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(getItem(position)!=null){
					Intent intent = new Intent();
					intent.setClass(context, HuoDongDetailActivity.class);
					intent.putExtra("HuoDongInfo", getItem(position));		
					context.startActivity(intent);
				}
			}
		});

		return convertView;
	}
	class ViewHolder{
		RelativeLayout rl_detail_head;
		ImageView uri_item_huodong;
		TextView tv_title;
		TextView tv_detail;
		ImageView tv_canyu;
		TextView people_num;
	}
}
