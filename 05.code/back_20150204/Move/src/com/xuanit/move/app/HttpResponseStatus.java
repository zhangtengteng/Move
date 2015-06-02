package com.xuanit.move.app;

import org.apache.http.HttpStatus;

import com.xuanit.move.util.MessageBox;


import android.app.Activity;

public class HttpResponseStatus {
	public static int httpCode = 200;

	/**
	 * 验证是否请求成功
	 * 
	 * @return
	 */
	public static Boolean httpSucess() {
		if (httpCode == 200)
			return true;
		else
			return false;
	}

	public static void ShowMessage(Activity context) {
		if (httpCode == 0)
			MessageBox.Instance(context).ShowToast("网络请求失败,请稍后再试");
		else if (httpCode == HttpStatus.SC_GATEWAY_TIMEOUT)
			MessageBox.Instance(context).ShowToast("服务器没有响应,请稍后再试");
		else if (httpCode == HttpStatus.SC_REQUEST_TIMEOUT)
			MessageBox.Instance(context).ShowToast("连接已超时,请稍后再试");
		else
			MessageBox.Instance(context).ShowToast("发生未知错误,错误代码" + httpCode);
		httpCode = 200;
	}
	
	public static String getMessage() {
		String str="";
		if (httpCode == 0)
			str= "网络请求失败,请稍后再试";
		else if (httpCode == HttpStatus.SC_GATEWAY_TIMEOUT)
			str="服务器没有响应,请稍后再试";
		else if (httpCode == HttpStatus.SC_REQUEST_TIMEOUT)
			str="连接已超时,请稍后再试";
		else
			str="发生未知错误,错误代码" + httpCode;
		
		httpCode = 200;
		return str;
	}

}
