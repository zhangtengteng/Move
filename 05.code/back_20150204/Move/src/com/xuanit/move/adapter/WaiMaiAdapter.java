package com.xuanit.move.adapter;

import java.util.ArrayList;
import java.util.List;

import com.ab.bitmap.AbImageDownloader;
import com.xuanit.move.R;
import com.xuanit.move.model.WaiMainInfo;
import com.xuanit.move.util.StringUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xuanit.move.view.CircleImageView;

public class WaiMaiAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;
	private List<WaiMainInfo> list = new ArrayList<WaiMainInfo>();
	private AbImageDownloader mAbImageDownloader;
	
	public WaiMaiAdapter(Context context, List<WaiMainInfo> list) {
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
		WaiMaiViewHolder holder;
		if (mAbImageDownloader == null) {
			mAbImageDownloader = new AbImageDownloader(context);
		}
		
		if (convertView == null) {
			holder = new WaiMaiViewHolder();
			convertView = inflater.inflate(R.layout.item_waimai, null);
			holder.waimai_mingcheng = (TextView)convertView.findViewById(R.id.waimai_mingcheng);
			holder.waimai_neirong = (TextView) convertView.findViewById(R.id.waimai_neirong);
			holder.waimai_dianhua = (TextView) convertView.findViewById(R.id.waimai_dianhua);
			holder.waimai_send_time = (TextView) convertView.findViewById(R.id.waimai_send_time);
			holder.waimai_juli = (TextView) convertView.findViewById(R.id.waimai_juli);
			holder.waimai_head = (CircleImageView) convertView.findViewById(R.id.waimai_head);
			convertView.setTag(holder);
		}else{
			holder = (WaiMaiViewHolder) convertView.getTag();
		}
			
		if(list != null){
			WaiMainInfo waiMainInfo = list.get(position);
			if(waiMainInfo!=null){
				if(!StringUtils.isNullOrEmpty(waiMainInfo.Name)){
					holder.waimai_mingcheng.setText(waiMainInfo.Name);
				}
				if(!StringUtils.isNullOrEmpty(waiMainInfo.Contents)){
					holder.waimai_neirong.setText(waiMainInfo.Contents);
				}
				if(!StringUtils.isNullOrEmpty(waiMainInfo.Phone)){
					holder.waimai_dianhua.setText(waiMainInfo.Phone);
				}
				if(!StringUtils.isNullOrEmpty(waiMainInfo.Distance)){
					holder.waimai_juli.setText("距离:" + waiMainInfo.Distance + "M");
				}
				if(!StringUtils.isNullOrEmpty(waiMainInfo.Speed)){
					holder.waimai_send_time.setText("送餐速度："+waiMainInfo.Speed + "分钟");
				}
				if(!StringUtils.isNullOrEmpty(waiMainInfo.Img)){
					
					mAbImageDownloader.display(holder.waimai_head, waiMainInfo.Img);
					//holder.waimai_head.setUrl(waiMainInfo.Img);
				}
			}
		}else{
			holder.waimai_mingcheng.setText("");
			holder.waimai_neirong.setText("");
			holder.waimai_dianhua.setText("");
			holder.waimai_juli.setText("");
			holder.waimai_send_time.setText("");
		}
		return convertView;
	}

	class WaiMaiViewHolder {
		private TextView waimai_mingcheng;
		private TextView waimai_neirong;
		private TextView waimai_dianhua;
		private TextView waimai_juli;
		private TextView waimai_send_time;
		private CircleImageView waimai_head;
	}

}
