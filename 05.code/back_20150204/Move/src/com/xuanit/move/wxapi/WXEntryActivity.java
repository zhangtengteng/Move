package com.xuanit.move.wxapi;

import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.xuanit.move.comm.StringsComm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
	// IWXAPI 是第三方app和微信通信的openapi接口
	private IWXAPI WXapi;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		  // 通过WXAPIFactory工厂，获取IWXAPI的实例
		WXapi = WXAPIFactory.createWXAPI(this, StringsComm.WXAPP_ID, false);
		WXapi.registerApp(StringsComm.WXAPP_ID);
		WXapi.handleIntent(getIntent(), this);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);

		setIntent(intent);
		WXapi.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq arg0) {
		// TODO Auto-generated method stub

	}

	// 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
	@Override
	public void onResp(BaseResp resp) {
		// TODO Auto-generated method stub
		String result = "";

		switch (resp.errCode) {
		case BaseResp.ErrCode.ERR_OK:
			result = "分享成功！";
			break;
		case BaseResp.ErrCode.ERR_USER_CANCEL:
			result = "分享取消！";
			break;
		case BaseResp.ErrCode.ERR_AUTH_DENIED:
			result = "分享被拒绝！";
			break;
		default:
			result = "未知错误！";
			break;
		}

		Toast.makeText(this, result, Toast.LENGTH_LONG).show();
		this.finish();
	}

}