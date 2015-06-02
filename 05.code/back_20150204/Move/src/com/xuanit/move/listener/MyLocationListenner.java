package com.xuanit.move.listener;

import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.xuanit.move.app.AppConfig;

public class MyLocationListenner implements BDLocationListener {
	public String latitude;// 纬度
	public String lontitude;// 经度

	@Override
	public void onReceiveLocation(BDLocation location) {
		if (location == null)
			return;
		StringBuffer sb = new StringBuffer(256);
		sb.append("time : ");
		sb.append(location.getTime());
		sb.append("\nerror code : ");
		sb.append(location.getLocType());
		sb.append("\nlatitude : ");
		sb.append(location.getLatitude());
		double latitudeDou = location.getLatitude();
		sb.append("\nlontitude : ");
		sb.append(location.getLongitude());
		double lontitudeDou = location.getLongitude();
		sb.append("\nradius : ");
		sb.append(location.getRadius());
		if (location.getLocType() == BDLocation.TypeGpsLocation) {
			sb.append("\nspeed : ");
			sb.append(location.getSpeed());
			sb.append("\nsatellite : ");
			sb.append(location.getSatelliteNumber());
		} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
			sb.append("\naddr : ");
			sb.append(location.getAddrStr());
		}

		latitude = String.valueOf(latitudeDou);
		lontitude = String.valueOf(lontitudeDou);
		AppConfig.latitude = latitude;
		AppConfig.lontitude = lontitude;

		Log.i("Location", "定位信息(onReceiveLocation)：" + sb.toString());
	}

	public void onReceivePoi(BDLocation poiLocation) {
		if (poiLocation == null) {
			return;
		}
		StringBuffer sb = new StringBuffer(256);
		sb.append("Poi time : ");
		sb.append(poiLocation.getTime());
		sb.append("\nerror code : ");
		sb.append(poiLocation.getLocType());
		sb.append("\nlatitude : ");
		sb.append(poiLocation.getLatitude());
		double latitudeDou = poiLocation.getLatitude();
		sb.append("\nlontitude : ");
		sb.append(poiLocation.getLongitude());
		double lontitudeDou = poiLocation.getLongitude();
		sb.append("\nradius : ");
		sb.append(poiLocation.getRadius());
		if (poiLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
			sb.append("\naddr : ");
			sb.append(poiLocation.getAddrStr());
		}
		
		/*
		 * if (poiLocation.hasPoi()) { sb.append("\nPoi:");
		 * sb.append(poiLocation.getPoi()); } else {
		 * sb.append("noPoi information"); }
		 */
		
		latitude = String.valueOf(latitudeDou);
		lontitude = String.valueOf(lontitudeDou);
		AppConfig.latitude = latitude;
		AppConfig.lontitude = lontitude;
		
		Log.i("Location", "定位信息(onReceivePoi):" + sb.toString());
	}
}
