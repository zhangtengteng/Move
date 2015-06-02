package com.xuanit.move.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

public class MessageBox {

	private static Activity context;
	private static MessageBox instance;
	private static Toast toast;

	public MessageBox() {
	}

	public static MessageBox Instance(Activity _context) {
		if (instance == null)
			instance = new MessageBox();
		if (context == null)
			context = _context;
//		if (toast == null)
//			toast = new Toast(context);
		return instance;
		
	}

	public static void ShowToast(String value) {
		if (toast != null)
			toast.cancel();
		toast = Toast.makeText(context, value, Toast.LENGTH_SHORT);
		toast.show();
	}

	public static void CreateToast(Context context,String value){
		Toast.makeText(context, value, Toast.LENGTH_SHORT);
	}
	/**
	 * 提示框
	 * 
	 * @param context
	 * @param title
	 * @param message
	 * @return
	 */
	public static AlertDialog showDialog(Context context, String title, String message) {
		AlertDialog.Builder builder = new Builder(context);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		AlertDialog dialog = builder.create();
		dialog.show();
		return dialog;
	}

	/**
	 * 提示框
	 * 
	 * @param context
	 * @param title
	 * @param message
	 * @return
	 */
	public static AlertDialog showDialog(Context context, String message) {
		AlertDialog.Builder builder = new Builder(context);
		builder.setTitle("温馨提示");
		builder.setMessage(message);
		builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		AlertDialog dialog = builder.create();
		dialog.show();
		return dialog;
	}

}
