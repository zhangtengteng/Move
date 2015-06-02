package com.james.asynhttp.network;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * 网络请求  （主要分为：post请求，get请求，下载文件，上传文件 ,下载文件返回bitmap）
 * 		主要功能：post 数据请求（执行－－返回结果）
 * 				get  数据请求（执行－－返回结果）
 * 				上传文件 数据请求（写出头文件 －－ 写出字节流 －－ 返回结果）
 * 				下载文件 数据请求 (执行－－返回字节流 －－ 返回结果)
 * 				下载文件（返回bitmap） （执行－－ 返回字节流－－ 转bitmap－－返回结果）
 * @author james
 * 
 */
public class NetWorkService {

	private static final String TAG = "james——NetWorkService"; //tag
	public static final int SUCCESS = 200; // 执行成功
	public static final int FAILURE = 400; // 执行失败
	private static final String POST = "POST"; //请求方式为post
	private static final String GET = "GET"; // 请求方式为get
	
	public NetWorkService() {
		super();
	}

	/**
	 * get 请求
	 * @param url_path 需要访问的url 地址
	 * @param handler  消息发送
	 * @return  返回值 200 代表返回成功 400 代表返回不成功
	 */
	public  int doGet(String url_path,Handler handler) {
		URL url;
		HttpURLConnection conn;
		StringBuffer strb = new StringBuffer();
		try {
			url = new URL(url_path);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setRequestMethod(GET);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setInstanceFollowRedirects(true);
			conn.setRequestProperty("Content-Type","application/json , charset=UTF-8");
			conn.connect();
			int code = conn.getResponseCode();
			if (code == NetWorkService.SUCCESS) {
				
				BufferedReader input = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String str;
				
				while ((str = input.readLine()) != null) {

					strb.append(str);
				}
				
				input.close();
				conn.disconnect();
				
				sendMessage(SUCCESS, strb.toString(), handler);
				return SUCCESS;
			} else {
				sendMessage(FAILURE, "get request exception ", handler);
				return FAILURE;
			}
			
		} catch (Exception e) {
			sendMessage(FAILURE, "get request exception ", handler);
			return FAILURE;
		}
	}

	
	/**
	 * post 网络请求
	 * @param url_path url 地址
	 * @param content_data  请求到服务器的内容
	 * @param handler  // 数据处理
	 * @return 返回成功 200 返回失败 400
	 */
	public int doPost(String url_path, String content_data,Handler handler) {
		Log.d(TAG, url_path+content_data);
		URL url;
		HttpURLConnection conn;
		StringBuffer buffers;
		try {
			buffers = new StringBuffer();
			url = new URL(url_path);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setRequestMethod(POST);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setInstanceFollowRedirects(true);
			conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			conn.connect();
			OutputStream out = conn.getOutputStream();
			out.write(content_data.getBytes(), 0,content_data.getBytes().length);
			out.flush();
			out.close();

			int code = conn.getResponseCode();

			if (code == SUCCESS) {

				BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

				String str;

				while ((str = bufferedreader.readLine()) != null) {

					buffers.append(str);

				}
				bufferedreader.close();
				conn.disconnect();
				sendMessage(SUCCESS, buffers.toString(), handler);
				return SUCCESS;
			} else {
				sendMessage(FAILURE, "post request exception", handler);
				return FAILURE;
			}
		} catch (Exception e) {
			sendMessage(FAILURE, "post request exception", handler);
			return FAILURE;
		}

	}

	
	/**
	 * post 上传文件
	 * @param url_path 上传文件的地址
	 * @param filepath 上传文件的文件地址
	 * @param handler  数据处理
	 * @return 返回成功 200 返回成功 400
	 */
	@SuppressWarnings("resource")
	public  int  doUploadFile(String url_path, String filepath, Handler handler){
		try{
			String boundary = "---------------------------7db1c523809b2";
			StringBuffer buffer = new StringBuffer();
			// 分割线
			File file = new File(filepath);
			final String ENCORDING = "UTF-8";
			// 用来解析主机名和端口
			URL url = new URL(url_path);
			// 用来开启连接
			StringBuilder sb = new StringBuilder();
			// 用来拼装请求
			// 文件部分
//			sb.append("--" + boundary + "\r\n");
//			sb.append("Content-Disposition: form-data; name=\"file\"; filename=\""+ file.getName() + "\"" + "\r\n");
//			sb.append("Content-Type: application/octet-stream" + "\r\n");
//			sb.append("\r\n");
			// 将开头和结尾部分转为字节数组，因为设置Content-Type时长度是字节长度
			byte[] before = sb.toString().getBytes(ENCORDING);
			byte[] after = ("\r\n--" + boundary + "--\r\n").getBytes(ENCORDING);
			// 打开连接, 设置请求头
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("POST");
//			conn.setRequestProperty("Content-Type","multipart/form-data; boundary=" + boundary);
//			conn.setRequestProperty("Content-Length",before.length + file.length() + after.length + "");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取输入输出流
			OutputStream out = conn.getOutputStream();
	
			FileInputStream fis = new FileInputStream(file);
			// 将开头部分写出
			out.write(before);
			// 写出文件数据
			byte[] buf = new byte[256];
			int len;
			while ((len = fis.read(buf)) != -1) {
				out.write(buf, 0, len);
			}
			// 将结尾部分写出
			out.write(after, 0, after.length);
			int code  = conn.getResponseCode();
			if(code == SUCCESS){
				BufferedReader bufReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String line = null;
				
				while ((line = bufReader.readLine()) != null){
					buffer.append(line);
				}
				fis.close();
				out.flush();
				out.close();
				conn.disconnect();
				sendMessage(SUCCESS, buffer.toString(), handler);
				return SUCCESS;
			}else {
				sendMessage(FAILURE, "upload file failure", handler);
				return FAILURE;
			}
		} catch (Exception e) {
			sendMessage(FAILURE, "upload file failure", handler);
			return FAILURE;
		}
	}
	
	/**
	 * 下载文件
	 * @param url_path
	 * @param handler
	 * @return 返回成功 200 返回失败 400
	 */
	public int doFileDownload(String url_path, Handler handler){
		try {
			// 如果相等的话表示当前的sdcard挂载在手机上并且是可用的
			if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
				URL url = new URL(url_path);
				final HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setConnectTimeout(5000);
				// 获取到文件的大小
				
				InputStream is = conn.getInputStream();
				File file = new File(Environment.getExternalStorageDirectory(),url_path.substring(url_path.lastIndexOf("/")));
				if(file.exists()){
					sendMessage(SUCCESS, file.getAbsoluteFile().toString(), handler);
					return SUCCESS;
				}
				
				FileOutputStream fos = new FileOutputStream(file);
				BufferedInputStream bis = new BufferedInputStream(is);
				byte[] buffer = new byte[1024];
				int len;
				while ((len = bis.read(buffer)) != -1) {
					fos.write(buffer, 0, len);
				}
				fos.close();
				bis.close();
				is.close();
				sendMessage(SUCCESS, file.getAbsoluteFile().toString(), handler);
				return SUCCESS;
			} else {
				sendMessage(FAILURE, "sdcard not mount ", handler);
				return FAILURE;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			sendMessage(FAILURE, "download file exception  ", handler);
			return FAILURE;
		}
	}

	/**
	 * 请求图片 
	 * @param imgurl 图片地址
	 * @param handler 数据处理
	 * @return 返回成功 200  返回失败 400
	 */
	public  int doReadBitmap(String imgurl, Handler handler) {
		URL url;
		@SuppressWarnings("unused")
		Bitmap bmp = null;
		InputStream is = null;
		BufferedInputStream bis = null;
		try {

			url = new URL(imgurl);
				URLConnection conn = url.openConnection();
				conn.connect();
				is = conn.getInputStream();
				bis = new BufferedInputStream(is);
				bmp = BitmapFactory.decodeStream(bis);
				sendMessage(SUCCESS, bmp, handler);
				return SUCCESS;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			sendMessage(FAILURE, "download images to bitmap exception ", handler);
			return FAILURE;
		} finally {
			try {
				if (is != null)
					is.close();
				if (bis != null)
					bis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	
	/**
	 * 消息发送
	 * @param statusCode  状态code
	 * @param obj  数据对象
	 */
	public void sendMessage(int statusCode, Object obj,Handler handler){
		Message msg = new Message();
		msg.what = statusCode;
		msg.obj  = obj; 
		handler.sendMessage(msg);
	}
	
}
