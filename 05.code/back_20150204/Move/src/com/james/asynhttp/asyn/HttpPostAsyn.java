package com.james.asynhttp.asyn;

import com.james.asynhttp.network.NetWorkService;

import android.os.Handler;
import android.util.Log;

/**
 *  post 异步操作 (主要 执行post请求的网络操作)
 *  		
 * @author james
 *
 */
public class HttpPostAsyn implements Runnable {

	private Handler handler; // 数据处理
	private String  url_path; //  url 路径
	private String  content_data; // 数据内容
	
	
	
	public HttpPostAsyn(Handler handler,String content_data, String url_path) {
		// TODO Auto-generated constructor stub
		this.handler = handler;
		this.url_path = url_path;
		this.content_data = content_data;
		Log.i("url", url_path+content_data);
		
	}
	

	/**
	 * 异步请求
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		new NetWorkService().doPost(this.url_path, this.content_data, this.handler);
		
		
	}

}
