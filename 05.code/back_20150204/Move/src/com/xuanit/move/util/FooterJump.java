package com.xuanit.move.util;

import io.rong.imkit.RongIM;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.xuanit.move.R;
import com.xuanit.move.activity.DongtanActivity;
import com.xuanit.move.activity.HuoDongActivity;
import com.xuanit.move.activity.PhotoAddActivity;
public class FooterJump implements OnClickListener {

/*	private Context from;
	Handler mHandler = new Handler();

	public void Jump(int resource, Context from) {

		this.from = from;

		View footerView = LayoutInflater.from(from).inflate(resource, null);
		// 处理Footer
		TextView footer_dongtan = (TextView) footerView.findViewById(R.id.footer_dongtan);
		TextView footer_huodong = (TextView) footerView.findViewById(R.id.footer_huodong);
		TextView footer_renwu = (TextView) footerView.findViewById(R.id.footer_renwu);
		TextView footer_tanlun = (TextView) footerView.findViewById(R.id.footer_tanlun);

		footer_dongtan.setOnClickListener(this);
		footer_huodong.setOnClickListener(this);
		footer_renwu.setOnClickListener(this);
		footer_tanlun.setOnClickListener(this);
	}
*/
	@Override
	public void onClick(View view) {
/*		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.footer_dongtan:
			from.startActivity(new Intent(from,MainActivity.class));
			System.out.println("from.startActivity(new Intent(from,MainActivity.class));");
			break;
		case R.id.footer_huodong:
			from.startActivity(new Intent(from,HuoDongActivity.class));
			System.out.println("from.startActivity(new Intent(from,HuoDongActivity.class));");
			break;
		case R.id.footer_renwu:
			from.startActivity(new Intent(from,DongtanActivity.class));
			System.out.println("from.startActivity(new Intent(from,DongtanActivity.class));");
			break;
		case R.id.footer_tanlun:
			from.startActivity(new Intent(from,CommuncationActivity.class));
			System.out.println("from.startActivity(new Intent(from,CommuncationActivity.class));");
			RongIM.getInstance().startConversationList(from);
			mHandler.post(new Runnable() {
				@Override
				public void run() {
					RongIM.getInstance().startConversationList(from);
				}
			});
			
			break;
		default:
			break;
		}*/
	}

}
