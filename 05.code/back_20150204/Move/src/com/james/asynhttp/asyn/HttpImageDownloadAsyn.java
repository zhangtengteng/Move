package com.james.asynhttp.asyn;

import android.os.Handler;

import com.james.asynhttp.network.NetWorkService;

/**
 * 图片异步下载（执行图片异步加载 返回bitmap）
 * @author james
 *
 */
public class HttpImageDownloadAsyn implements Runnable {
	
	private String imgurl; // 图片路径
	private Handler handler; // 数据处理
	
	public HttpImageDownloadAsyn(String imgurl,Handler handler) {
		// TODO Auto-generated constructor stub
		this.imgurl = imgurl;
		this.handler = handler;
	}

	/**
	 *  执行图片下载
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		new NetWorkService().doReadBitmap(imgurl, handler);
		
			
	}

}
