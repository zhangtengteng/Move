package com.xuanit.move.app;

import io.rong.imkit.RongIM;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.xuanit.move.R;

import android.app.Application;
import android.app.Service;
import android.os.Vibrator;
import android.util.Log;

public class MoveApplication extends Application implements BDLocationListener {

	public Vibrator mVibrator;
	public double latitude;
	public double lontitude;
	public AppConfig appConfig;
	public LocationClient mLocationClient;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		appConfig = new AppConfig();
		mVibrator = (Vibrator) getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
		System.out.println("21行++++RongIM.init+++开始");
		RongIM.init(MoveApplication.this, "qf3d5gbj3asmh", R.drawable.ic_launcher);
		System.out.println("23行++++RongIM.init+++结束");

		
		mLocationClient= new LocationClient(getApplicationContext()); // 声明LocationClient类
		
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);
		option.setCoorType("gcj02");// 设置坐标类型
		option.setScanSpan(5000);// 设置扫面间隔
		mLocationClient.setLocOption(option);
		mLocationClient.registerLocationListener(this); // 注册监听函数
		
		mLocationClient.start();//开启位置获取
		mLocationClient.requestLocation();
	}

	@Override
	public void onReceiveLocation(BDLocation location) {
		// TODO Auto-generated method stub
		if (location == null)
			return;
		StringBuffer sb = new StringBuffer(256);
		sb.append("time : ");
		sb.append(location.getTime());
		sb.append("\nerror code : ");
		sb.append(location.getLocType());
		sb.append("\nlatitude : ");
		sb.append(location.getLatitude());
		latitude = location.getLatitude();
		sb.append("\nlontitude : ");
		sb.append(location.getLongitude());
		lontitude = location.getLongitude();
		sb.append("\nradius : ");
		sb.append(location.getRadius());
		sb.append("\nprevious");
		sb.append(location.getProvince());
		if (location.getLocType() == BDLocation.TypeGpsLocation) {
			sb.append("\nspeed : ");
			sb.append(location.getSpeed());
			sb.append("\nsatellite : ");
			sb.append(location.getSatelliteNumber());
		} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
			sb.append("\naddr : ");
			sb.append(location.getAddrStr());
		}

		Log.i("Location", "定位信息(onReceiveLocation)：" + sb.toString());
	}

}
