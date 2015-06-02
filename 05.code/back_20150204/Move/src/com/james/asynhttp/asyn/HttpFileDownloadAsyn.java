package com.james.asynhttp.asyn;

import android.os.Handler;

import com.james.asynhttp.network.NetWorkService;

/**
 *  文件异步下载（执行文件下载:返回是文件地址）
 * @author james
 *
 */
public class HttpFileDownloadAsyn implements Runnable {

	private String url_path;
	private Handler handler;
	
	public HttpFileDownloadAsyn(String url_path, Handler handler) {
		// TODO Auto-generated constructor stub
		this.url_path = url_path;
		this.handler  = handler;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		new NetWorkService().doFileDownload(this.url_path, this.handler);
		
	}

}
