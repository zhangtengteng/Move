package com.xuanit.move.adapter;

import java.util.ArrayList;
import java.util.List;

import com.ab.bitmap.AbImageDownloader;
import com.xuanit.move.R;
import com.xuanit.move.model.KuaiDiInfo;
import com.xuanit.move.util.StringUtils;
import com.xuanit.move.view.CircleImageView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class KuaiDiAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;
	private List<KuaiDiInfo> list = new ArrayList<KuaiDiInfo>();
	private AbImageDownloader mAbImageDownloader;
	
	public KuaiDiAdapter(Context context, List<KuaiDiInfo> list) {
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
		KuaiDiViewHolder holder;
		
		if (mAbImageDownloader == null) {
			mAbImageDownloader = new AbImageDownloader(context);
		}
		
		if (convertView == null) {
			holder = new KuaiDiViewHolder();
			convertView = inflater.inflate(R.layout.item_waimai, null);
			holder.tv_name = (TextView)convertView.findViewById(R.id.waimai_mingcheng);
			holder.tv_content = (TextView) convertView.findViewById(R.id.waimai_neirong);
			holder.waimai_head = (CircleImageView) convertView.findViewById(R.id.waimai_head);
			convertView.setTag(holder);
		}else{
			holder = (KuaiDiViewHolder) convertView.getTag();
		}
			
		if(list != null){
			KuaiDiInfo kuaidiInfo = list.get(position);
			if(kuaidiInfo!=null){
				if(!StringUtils.isNullOrEmpty(kuaidiInfo.Prefix)){
					holder.tv_name.setText(kuaidiInfo.Prefix);
				}
				if(!StringUtils.isNullOrEmpty(kuaidiInfo.Img)){
					mAbImageDownloader.display(holder.waimai_head, kuaidiInfo.Img);
				}
				holder.tv_content.setText("");
			}
		}else{
			holder.tv_name.setText("");
			holder.tv_content.setText("");
		}
		return convertView;
	}

	class KuaiDiViewHolder {
		private TextView tv_name;
		private TextView tv_content;
		private CircleImageView waimai_head;
	}

}
