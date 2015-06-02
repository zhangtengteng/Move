package com.xuanit.move.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UploadFile {
	
	// 运行前先设置好以下三个参数
	private static final String BUCKET_NAME = "health-img";
	private static final String USER_NAME = "qichang";
	private static final String USER_PWD = "qichang2014";
	/** 根目录 */
	private static final String DIR_ROOT = "/";
	private static UpYun upyun = null;	
	
	/**
	 * 上传文件
	 * 
	 * @throws IOException
	 */
	public static String  writeFile(String path) throws IOException {
        String name=getSimpleDateFormatName(path);
		// 初始化空间
		upyun = new UpYun(BUCKET_NAME, USER_NAME, USER_PWD);
		// 要传到upyun后的文件路径
		String filePath = DIR_ROOT + name;
		// 本地待上传的图片文件
		File file = new File(path);
		// 设置待上传文件的 Content-MD5 值
		// 如果又拍云服务端收到的文件MD5值与用户设置的不一致，将回报 406 NotAcceptable 错误
		upyun.setContentMD5(UpYun.md5(file));
		// 上传文件，并自动创建父级目录（最多10级）
		boolean result = upyun.writeFile(filePath, file, true);
		String resultString=isSuccess(result);
		return resultString+","+name;
	}

	public static String isSuccess(boolean result) {
		return result ? "成功" : "失败";
	}
	
	/**
	 * 拼接图片名称
	 */
	 public static String getSimpleDateFormatName(String path){
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
			SimpleDateFormat formatter2 = new SimpleDateFormat("yyyyMMdd");
			Date curDate = new Date(System.currentTimeMillis());// 获取当前时间 
			String name=formatter2.format(curDate);
			name += "/"+formatter.format(curDate)+System.currentTimeMillis();
			name += "."+getFileFormat(path);
			return name;
	 }
	 
	/**
	 * 获取文件扩展名
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getFileFormat(String fileName) {
		if (isEmpty(fileName))
			return "";

		int point = fileName.lastIndexOf('.');
		return fileName.substring(point + 1);
	}
	
	/**
	 * 判断给定字符串是否空白串。
	 * 空白串是指由空格、制表符、回车符、换行符组成的字符串
	 * 若输入字符串为null或空字符串，返回true
	 * @param input
	 * @return boolean
	 */
	public static boolean isEmpty( String input ) 
	{
		if ( input == null || "".equals( input ) )
			return true;
		
		for ( int i = 0; i < input.length(); i++ ) 
		{
			char c = input.charAt( i );
			if ( c != ' ' && c != '\t' && c != '\r' && c != '\n' )
			{
				return false;
			}
		}
		return true;
	}	
}
