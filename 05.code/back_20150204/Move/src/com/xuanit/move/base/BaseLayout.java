package com.xuanit.move.base;


import com.xuanit.move.R;
import com.xuanit.move.util.StringUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BaseLayout extends RelativeLayout{
	private View header_bar;
	public ImageView iv_header_left;
	public LinearLayout ll_header_right;
	private TextView tv_header_middle;
	private TextView tv_header_right;
	public BaseLayout(Context context, int layoutResourceId) {
		super(context);
		LayoutInflater layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		header_bar = layoutInflater.inflate(R.layout.header, null);
		iv_header_left = (ImageView) header_bar.findViewById(R.id.iv_header_left);
		ll_header_right = (LinearLayout) header_bar.findViewById(R.id.ll_header_right);
		tv_header_middle = (TextView) header_bar.findViewById(R.id.tv_header_middle);
		tv_header_right = (TextView)header_bar.findViewById(R.id.tv_header_right);
		
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		addView(header_bar, params);
		
		View view = layoutInflater.inflate(layoutResourceId, null);
		RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		params2.addRule(RelativeLayout.BELOW, R.id.header_bar);
		addView(view, params2);
	}
	
	public void setTitle(String left,String title,String right){
		if(!StringUtils.isNullOrEmpty(title)){
			tv_header_middle.setText(title);
		}
		if(!StringUtils.isNullOrEmpty(right)){
			tv_header_right.setText(right);
		}
	}
}
