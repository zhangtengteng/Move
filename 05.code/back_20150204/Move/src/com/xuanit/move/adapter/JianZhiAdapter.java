package com.xuanit.move.adapter;

import java.util.ArrayList;
import java.util.List;

import com.ab.bitmap.AbImageDownloader;
import com.xuanit.move.R;
import com.xuanit.move.model.JianZhiInfo;
import com.xuanit.move.util.StringUtils;
import com.xuanit.move.view.CircleImageView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class JianZhiAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;
	private List<JianZhiInfo> list = new ArrayList<JianZhiInfo>();
	private AbImageDownloader mAbImageDownloader;
	
	public JianZhiAdapter(Context context, List<JianZhiInfo> list) {
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
			convertView = inflater.inflate(R.layout.item_jianzhi, null);
			holder.jianzhi_mingcheng = (TextView)convertView.findViewById(R.id.jianzhi_mingcheng);
			holder.jianzhi_daiyu = (TextView) convertView.findViewById(R.id.jianzhi_daiyu);
			holder.jianzhi_didian = (TextView)convertView.findViewById(R.id.jianzhi_didian);
			holder.jianzhi_gongzuori = (TextView) convertView.findViewById(R.id.jianzhi_gongzuori);
			holder.jianzhi_neirong = (TextView)convertView.findViewById(R.id.jianzhi_neirong);
			holder.jianzhi_fabu_time = (TextView) convertView.findViewById(R.id.jianzhi_fabu_time);
			holder.jianzhi_head = (CircleImageView)convertView.findViewById(R.id.jianzhi_head);
			
			convertView.setTag(holder);
		}else{
			holder = (JianZhiViewHolder) convertView.getTag();
		}
			
		if(list != null){
			JianZhiInfo jianzhiInfo = list.get(position);
			if(jianzhiInfo!=null){
				if(!StringUtils.isNullOrEmpty(jianzhiInfo.Name)){
					holder.jianzhi_mingcheng.setText(jianzhiInfo.Name);
				}
				if(!StringUtils.isNullOrEmpty(jianzhiInfo.Contents)){
					holder.jianzhi_neirong.setText(jianzhiInfo.Contents);
				}
				if(!StringUtils.isNullOrEmpty(jianzhiInfo.WorkPay)){
					holder.jianzhi_daiyu.setText(jianzhiInfo.WorkPay + "元/日");
				}
				if(!StringUtils.isNullOrEmpty(jianzhiInfo.WorkTime)){
					holder.jianzhi_gongzuori.setText(jianzhiInfo.WorkTime + "日");
				}
				if(!StringUtils.isNullOrEmpty(jianzhiInfo.WorkArea)){
					holder.jianzhi_didian.setText(jianzhiInfo.WorkArea);
				}
				if(!StringUtils.isNullOrEmpty(jianzhiInfo.CreateTime)){
					holder.jianzhi_fabu_time.setText(jianzhiInfo.CreateTime + "发布");
				}
				if(!StringUtils.isNullOrEmpty(jianzhiInfo.Img)){
					mAbImageDownloader.display(holder.jianzhi_head, jianzhiInfo.Img);
				}
			}
		}else{
			holder.jianzhi_mingcheng.setText("");
			holder.jianzhi_daiyu.setText("");
			holder.jianzhi_didian.setText("");
			holder.jianzhi_gongzuori.setText("");
			holder.jianzhi_neirong.setText("");
			holder.jianzhi_fabu_time.setText("");
		}
		return convertView;
	}

	class JianZhiViewHolder {
		private TextView jianzhi_mingcheng;
		private TextView jianzhi_daiyu;
		private TextView jianzhi_didian;
		private TextView jianzhi_gongzuori;
		private TextView jianzhi_neirong;
		private TextView jianzhi_fabu_time;
		private CircleImageView jianzhi_head;
	}

}
