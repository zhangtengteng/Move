package com.xuanit.move.adapter;

import java.util.List;

import com.ab.bitmap.AbImageDownloader;
import com.xuanit.move.R;
import com.xuanit.move.model.ErShouInfo;
import com.xuanit.move.util.StringUtils;
import com.xuanit.move.view.CircleImageView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ErShouAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;
	private List<ErShouInfo> list = null;
	private AbImageDownloader mAbImageDownloader;
	public ErShouAdapter(Context context, List<ErShouInfo> list) {
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
		ErShouViewHolder holder;
		if (convertView == null) {
			holder = new ErShouViewHolder();
			convertView = inflater.inflate(R.layout.item_waimai, null);
			holder.tv_name = (TextView)convertView.findViewById(R.id.waimai_mingcheng);
			holder.tv_content = (TextView) convertView.findViewById(R.id.waimai_neirong);
			holder.waimai_head = (CircleImageView) convertView.findViewById(R.id.waimai_head);
			convertView.setTag(holder);
		}else{
			holder = (ErShouViewHolder) convertView.getTag();
		}
			
		if(list != null){
			ErShouInfo ershouInfo = list.get(position);
			if(ershouInfo!=null){
				if(!StringUtils.isNullOrEmpty(ershouInfo.Name)){
					holder.tv_name.setText(ershouInfo.Name);
				}
				if(!StringUtils.isNullOrEmpty(ershouInfo.Contents)){
					holder.tv_content.setText(ershouInfo.Contents);
				}
				if(!StringUtils.isNullOrEmpty(ershouInfo.Img)){
//					holder.waimai_head.setUrl(ershouInfo.Img);
					mAbImageDownloader.display(holder.waimai_head, ershouInfo.Img);
				}
				holder.tv_content.setText("");
			}
		}else{
			holder.tv_name.setText("");
			holder.tv_content.setText("");
		}
		return convertView;
	}

	class ErShouViewHolder {
		private TextView tv_name;
		private TextView tv_content;
		private CircleImageView waimai_head;
	}

}
