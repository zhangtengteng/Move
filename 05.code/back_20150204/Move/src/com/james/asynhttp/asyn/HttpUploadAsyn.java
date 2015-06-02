package com.james.asynhttp.asyn;

import android.os.Handler;

import com.james.asynhttp.network.NetWorkService;


/**
 * 
 * post 文件上传 异步操作 (主要 执行文件上传)
 * @author james
 *
 */
public class HttpUploadAsyn implements Runnable {

	private String url_path; // 文件上传地址
	private String filepath; // 本地文件地址
	private Handler handler; // 数据处理
	
	public HttpUploadAsyn(String url_path,String filepath,Handler handler) {
		// TODO Auto-generated constructor stub
		
		this.url_path  =  url_path;
		this.filepath  =  filepath;
		this.handler   =  handler;
	}
	

	/**
	 * 文件上传
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		new NetWorkService().doUploadFile(this.url_path, this.filepath, this.handler);
	}
	
	

}
