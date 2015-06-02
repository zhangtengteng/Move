package com.james.asynhttp;

import com.james.asynhttp.network.NetWorkService;
import android.os.Handler;

/**
 * 
 * 异步数据处理 并返回（主要：拿到结果并返回结果）
 * 
 * @author james
 *
 */
public abstract class HttpNetWorkDataHandler {
	public HttpNetWorkDataHandler() {
		// TODO Auto-generated constructor stub
		
	}
	
	/**
	 * 数据处理
	 *      success  成功
	 *      failure  失败
	 *      other    失败
	 */
	public Handler handler =  new Handler(){
		
		public void handleMessage(android.os.Message msg) {
			
			switch (msg.what) {
			case NetWorkService.SUCCESS:
					success(NetWorkService.SUCCESS, msg.obj);
				break;
			case NetWorkService.FAILURE:
					failure(NetWorkService.FAILURE, msg.obj);
				break;
			default:
				failure(NetWorkService.FAILURE, "request error");
				break;
			}
			
		};
	};
	
	
	
	/**
	 * 成功所执行的方法
	 * @param statusCode  状态code 
	 * @param obj  数据对象
	 */
	public abstract void  success(int statusCode,Object obj);
	
	/**
	 * 失败所执行的方法
	 * @param statusCode 状态code
	 * @param obj  数据对象
	 */
	public abstract void  failure(int statusCode,Object obj);
	
	
	
	
	
	
	
	
	

}
