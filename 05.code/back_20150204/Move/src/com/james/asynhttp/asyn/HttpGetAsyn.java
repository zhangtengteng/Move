package com.james.asynhttp.asyn;

import com.james.asynhttp.network.NetWorkService;

import android.os.Handler;

/**
 * get 异步操作 (主要 执行get请求的网络操作)
 * @author james
 *
 */
public class HttpGetAsyn implements Runnable {

	private String url_path;
	private Handler handler;
	
	
	public HttpGetAsyn(String url_path ,Handler handler) {
		// TODO Auto-generated constructor stub
		this.url_path = url_path;
		this.handler =  handler;
	}

	/**
	 * get 异步请求
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
	
			new NetWorkService().doGet(url_path, handler);
	}

}
