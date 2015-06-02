package com.james.asynhttp;

import com.james.asynhttp.asyn.HttpFileDownloadAsyn;
import com.james.asynhttp.asyn.HttpGetAsyn;
import com.james.asynhttp.asyn.HttpImageDownloadAsyn;
import com.james.asynhttp.asyn.HttpPostAsyn;
import com.james.asynhttp.asyn.HttpUploadAsyn;

import android.os.Handler;
import android.util.Log;

/**
 * 
 * 提供http 请求的两种方式： get： 以 get方式上传数据，并请求数据（url 执行－－》 返回参数） post：
 * 以post方式上传数据，并请求数据 （url －－执行－写出数据 － 返回参数） upload 上传文件 （url － 上传头数据 －－ 上传字节流 －－
 * 返回给我数据 ） download 下载文件(url －－ 返回字节流) downloadBitmap 下载图片返回bitmap （url －－
 * 得到字节流－－转bitmap）
 * 
 * @author james
 * 
 */
public class AsynHttpClient {

	public AsynHttpClient() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * get 方式网络请求
	 * 
	 * @param url
	 *            请求地址
	 * @param dataHandler
	 *            数据处理对象
	 */
	public void get(String url_path, HttpNetWorkDataHandler dataHandler) {
		Log.i("Get", "Post请求信息: url_path ：" + url_path);
		Handler handler;
		try {

			handler = dataHandler.handler;// 拿到数据处理对象
			new Thread(new HttpGetAsyn(url_path, handler)).start();// 异步执行

		} catch (Exception e) {

		}

	}

	/**
	 * post 方式 网络请求
	 * 
	 * @param url_path
	 *            请求地址
	 * @param content_data
	 *            请求内容
	 * @param dataHandler
	 *            数据处理对象
	 */
	public void post(String url_path, String content_data, HttpNetWorkDataHandler dataHandler) {

		Log.i("post", "Post请求信息: url_path  ：" + url_path + "--content_data" + content_data);
		Handler handler;
		try {
			handler = dataHandler.handler;// 拿到数据处理对象
			new Thread(new HttpPostAsyn(handler, content_data, url_path)).start();// 异步执行

		} catch (Exception e) {

		}
	}

	/**
	 * 上传文件
	 * 
	 * @param url_path
	 *            请求地址
	 * @param filepath
	 *            文件地址
	 * @param dataHandler
	 *            数据处理对象
	 */
	public void upload(String url_path, String filepath, HttpNetWorkDataHandler dataHandler) {
		Handler handler;
		try {
			handler = dataHandler.handler; // 拿到数据处理对象

			new Thread(new HttpUploadAsyn(url_path, filepath, handler)).start(); // 异步执行

		} catch (Exception e) {

		}
	}

	/**
	 * 文件下载
	 * 
	 * @param url_path
	 *            要下载的文件路径
	 * @param dataHandler
	 *            数据处理对象
	 */
	public void download(String url_path, HttpNetWorkDataHandler dataHandler) {
		Handler handler;
		try {

			handler = dataHandler.handler; // 拿到处理对象
			new Thread(new HttpFileDownloadAsyn(url_path, handler)).start(); // 异步下载

		} catch (Exception e) {

		}

	}

	/**
	 * 图片下载 返回bitmap
	 * 
	 * @param url_path
	 *            下载的文件路径
	 * @param dataHandler
	 *            数据处理对象
	 */
	public void downloadBitMap(String url_path, HttpNetWorkDataHandler dataHandler) {
		Handler handler;
		try {

			handler = dataHandler.handler; // 拿到处理对象
			new Thread(new HttpImageDownloadAsyn(url_path, handler)).start(); // 异步下载

		} catch (Exception e) {

		}

	}

}
