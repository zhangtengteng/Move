package com.xuanit.move.adapter;

import java.util.ArrayList;
import java.util.List;

import com.xuanit.move.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SchoolAdapter extends BaseAdapter{
	
	private List<String> list=new ArrayList<String>();
	
	private Context context;
	
	public SchoolAdapter(Context context,List<String> list){
		this.context = context;
		this.list = list;
	}
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View view, ViewGroup arg2) {
		if(view == null){
			view = LayoutInflater.from(context).inflate(R.layout.shooladpter,null);
		}
		TextView txt = (TextView) view.findViewById(R.id.text1);
		txt.setText(list.get(position));
		return view;
	}

}
