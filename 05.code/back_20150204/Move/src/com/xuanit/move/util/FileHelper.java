package com.xuanit.move.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.util.EncodingUtils;

import android.content.Context;
import android.content.res.AssetManager;

public class FileHelper {
	public static Boolean IsCheckUpdateInit = true;
	public static Boolean IsCanReturn(String resultString) {
		if (resultString != null && !resultString.equals(""))
			return true;
		else {
			return false;
		}
	}
	/*
	 * 读取Asset下的文件为string
	 */
	public static String getAssetFileString(Context context, String url) throws IOException {
		AssetManager assetManager = context.getAssets();
		InputStreamReader stream = new InputStreamReader(assetManager.open(url));
		BufferedReader reader = new BufferedReader(stream);
		String line = "";
		String Result = "";
		while ((line = reader.readLine()) != null)
			Result += line;
		return Result;
	}

	/**
	 * 写入文件，与apk同卸载 data/com.xuanit.health/files/下
	 * 
	 * @param fileName
	 * @param message
	 * @param context
	 */
	public static void writeFileData(Context context, String fileName, String message) {
		try {
			FileOutputStream fout = context.openFileOutput(fileName, Context.MODE_PRIVATE);
			byte[] bytes = message.getBytes();
			fout.write(bytes);
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 读取文件 data/com.xuanit.health/files/下
	 * 
	 * @param fileName
	 * @param context
	 * @return
	 */
	public static String readFileData(Context context, String fileName) {

		String res = "";
		try {

			FileInputStream fin = context.openFileInput(fileName);
			int length = fin.available();
			byte[] buffer = new byte[length];
			fin.read(buffer);
			res = EncodingUtils.getString(buffer, "UTF-8");
			fin.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
}
