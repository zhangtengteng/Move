package com.xuanit.move.adapter;

import java.util.ArrayList;

import com.xuanit.move.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.media.ThumbnailUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageShowAdapter extends BaseAdapter {
	private Context context;
	private String[] imagePaths;

	public ImageShowAdapter(Context context, String[] imagePaths) {
		super();
		this.context = context;
		this.imagePaths = imagePaths;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return imagePaths.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return imagePaths[arg0];
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View converView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		if (converView == null) {
			viewHolder = new ViewHolder();
			converView = LayoutInflater.from(context).inflate(R.layout.item_photo, null);
			viewHolder.iv_photo = (ImageView) converView.findViewById(R.id.iv_photo);
			viewHolder.tv_pre_bg = (TextView) converView.findViewById(R.id.tv_pre_bg);
			viewHolder.cbox_check = (CheckBox) converView.findViewById(R.id.cbox_check);
			converView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) converView.getTag();
		}
		Bitmap bitmap = BitmapFactory.decodeFile((String) getItem(position));
		Bitmap tempBitmap = ThumbnailUtils.extractThumbnail(bitmap, 50, 50, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		viewHolder.iv_photo.setImageBitmap(tempBitmap);

		return converView;
	}

	static class ViewHolder {
		ImageView iv_photo;
		TextView tv_pre_bg;
		CheckBox cbox_check;
	}

}
