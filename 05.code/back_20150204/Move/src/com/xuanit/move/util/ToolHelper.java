package com.xuanit.move.util;

import java.io.File;
import java.math.BigDecimal;

import com.xuanit.move.model.ListViewImageModel;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.location.Location;
import android.location.LocationManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

public class ToolHelper {

	private Context context;

	public ToolHelper(Context _context) {
		context = _context;
	}

	public int Dp2Px(float dp) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dp * scale + 0.5f);
	}

	public int Px2Dp(Context context, float px) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (px / scale + 0.5f);
	}
	
	public int Px2Dp(float px) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (px / scale + 0.5f);
	}

	public String FormatZan(int number) {
		String ret = "";
		if (number < 1000)
			ret = String.valueOf(number);
		else if (number >= 1000 && number < 10000) {
			Double newnumDouble = number + 0.0;
			Double factor = Math.pow(10, 1);
			Double st = Math.floor(newnumDouble / 1000 * factor + 0.5) / factor;
			ret = String.valueOf(st) + "K";
		} else if (number >= 10000) {
			Double newnumDouble = number + 0.0;
			Double factor = Math.pow(10, 1);
			Double st = Math.floor(newnumDouble / 10000 * factor + 0.5) / factor;
			ret = String.valueOf(st) + "W";
		}
		return ret;
	}

	/**
	 * 获取控件宽度
	 * 
	 * @param view
	 * @return
	 */
	public int getControlWidth(View view) {
		int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		view.measure(w, h);
		int width = view.getMeasuredWidth();
		return width;
	}

	/**
	 * 是否下载图片
	 * 
	 * @param context
	 * @return
	 */
	public Boolean DownPic() {
		if (get2G3G()) {
			return true;
		} else {
			if (JudgePhoneStateUtil.isWifiConnected(context)) {
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * 截取字符串
	 * 
	 * @param str
	 * @param len
	 * @return
	 */
	public String SubStringHelper(String str, int len) {
		String ret = "";
		if (str.length() > len)
			ret = str.substring(0, len);
		else
			ret = str;
		return ret;
	}

	/**
	 * 获取当前2G3G是否开启
	 * 
	 * @param context
	 * @return
	 */
	public Boolean get2G3G() {
		Boolean isOpen = false;
		File file = new File(context.getFilesDir() + "/2g.txt");
		if (!file.exists()) {
			FileHelper.writeFileData(context, "2g.txt", "0");
			isOpen = false;
		} else {
			String reString = FileHelper.readFileData(context, "2g.txt");
			if (reString.equals("1"))
				isOpen = true;
			else
				isOpen = false;
		}
		return isOpen;
	}

	/**
	 * 设置2g3g
	 * 
	 * @param isOpen
	 * @param context
	 */
	public void set2G3G(Boolean isOpen) {
		if (isOpen)
			FileHelper.writeFileData(context, "2g.txt", "1");
		else
			FileHelper.writeFileData(context, "2g.txt", "0");
	}

	/**
	 * 获取经纬度
	 * 
	 * @return
	 */
	public Location GetLocation() {
		LocationManager manager;
		String contextService = Context.LOCATION_SERVICE;
		manager = (LocationManager) context.getSystemService(contextService);
		String providerString = LocationManager.GPS_PROVIDER;
		Location location = manager.getLastKnownLocation(providerString);
		return location;
	}

	/**
	 * 获取显示版本
	 * 
	 * @param context
	 * @return
	 * @throws NameNotFoundException
	 */
	public String getVersionName() throws NameNotFoundException {
		PackageManager packageManager = context.getPackageManager();
		PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
		String version = packageInfo.versionName;
		return version;
	}

	/**
	 * 获取程序版本
	 * 
	 * @throws NameNotFoundException
	 */
	public int getVersionCode() throws NameNotFoundException {
		PackageManager packageManager = context.getPackageManager();
		PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
		int version = packageInfo.versionCode;
		return version;
	}

	/**
	 * 获取屏幕宽度px高度
	 * 
	 * @return
	 */
	public DisplayMetrics getScreenDisplayMetrics() {
		return context.getResources().getDisplayMetrics();
	}

	/**
	 * 获取对应分辨率图片因该显示的宽度
	 * 
	 * @param context
	 * @return
	 */
	public String getDisplayImageWidth() {
		int width = getScreenDisplayMetrics().widthPixels;
		if (width <= 240)
			width = 240;
		else if (width > 240 && width <= 320)
			width = 320;
		else if (width > 320 && width <= 480)
			width = 480;
		else if (width > 480 && width <= 540)
			width = 540;
		else if (width > 540 && width <= 600)
			width = 600;
		else if (width > 600 && width <= 720)
			width = 720;
		else if (width > 720 && width <= 800)
			width = 800;
		else if (width > 800 && width <= 1080)
			width = 1080;
		else if (width > 1080)
			width = 1080;
		return String.valueOf(width);
	}

	/**
	 * 获取列表页面的图片宽度和高度px
	 * 
	 * @param context
	 * @return164 120
	 */
	public ListViewImageModel getScreenModel(int widthp, int heightp) {
		DisplayMetrics metrics = getScreenDisplayMetrics();
		int width = new BigDecimal(metrics.widthPixels * widthp / 720).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
		int height = new BigDecimal(metrics.heightPixels * heightp / 1280).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
		ListViewImageModel model = new ListViewImageModel();
		model.setWidth(width);
		model.setHeight(height);
		return model;
	}

	/**
	 * 解决listview+scrollview嵌套listview没有高度的问题
	 * 
	 * @param listView
	 */
	public void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

	public void Destroy() {
		context = null;
	}
}
