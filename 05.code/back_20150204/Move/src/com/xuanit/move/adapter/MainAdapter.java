package com.xuanit.move.adapter;

import java.util.ArrayList;
import java.util.List;

import com.ab.bitmap.AbImageDownloader;
import com.tencent.mm.sdk.constants.ConstantsAPI.WXApp;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.xuanit.move.R;
import com.xuanit.move.activity.ImageDetailActivity;
import com.xuanit.move.activity.NewscommentActivity;
import com.xuanit.move.activity.OtherCenterActivity;
import com.xuanit.move.activity.PersonalCenterActivity;
import com.xuanit.move.app.AppConfig;
import com.xuanit.move.app.MoveApplication;
import com.xuanit.move.comm.StringsComm;
import com.xuanit.move.model.ZhouBianInfo;
import com.xuanit.move.util.StringUtils;
import com.xuanit.move.view.CircleImageView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;
	private List<ZhouBianInfo> list = new ArrayList<ZhouBianInfo>();
	private AbImageDownloader mAbImageDownloader;
	private String userUserId;
	private IWXAPI WXapi = null;

	public MainAdapter(Context context, List<ZhouBianInfo> list, String userUserId) {
		this.list = list;
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.userUserId = userUserId;

		WXapi = WXAPIFactory.createWXAPI(context, StringsComm.WXAPP_ID);
		WXapi.registerApp(StringsComm.WXAPP_ID);
	}

	/*
	 * public MainAdapter(Context context, List<ZhouBianInfo> list_zhoubian) {
	 * // TODO Auto-generated constructor stub this.list = list_zhoubian;
	 * this.context = context; this.inflater = LayoutInflater.from(context); }
	 */

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public ZhouBianInfo getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (mAbImageDownloader == null) {
			mAbImageDownloader = new AbImageDownloader(context);
		}

		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_main, null);

			holder.iv_user_heade = (CircleImageView) convertView.findViewById(R.id.iv_user_heade);
			holder.tv_contant = (TextView) convertView.findViewById(R.id.tv_user_shuoshuo);
			holder.tv_mes_zanshu = (TextView) convertView.findViewById(R.id.tv_mes_zanshu);
			holder.tv_user_readcount = (TextView) convertView.findViewById(R.id.tv_user_readcount);
			holder.tv_reply = (TextView) convertView.findViewById(R.id.tv_user_reply);
			holder.tv_name = (TextView) convertView.findViewById(R.id.tv_user_name);
			holder.uiv_userpic_mainfrag = (ImageView) convertView.findViewById(R.id.tv_user_shuoshuo_pic);
			holder.ll_reply = (LinearLayout) convertView.findViewById(R.id.ll_reply);
			holder.iv_user_zan = (ImageView) convertView.findViewById(R.id.iv_user_zan);
			holder.item_mes_distance = (TextView) convertView.findViewById(R.id.item_mes_distance);
			holder.item_mes_assit = (TextView) convertView.findViewById(R.id.item_mes_assit);
			holder.tv_share = (TextView) convertView.findViewById(R.id.tv_share);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (!StringUtils.isNullOrEmpty(list.get(position).Contents)) {
			holder.tv_contant.setText(list.get(position).Contents);
		} else {
			holder.tv_contant.setText("");
		}
		if (!StringUtils.isNullOrEmpty(list.get(position).UserName)) {
			holder.tv_name.setText(list.get(position).UserName);
		} else {
			holder.tv_name.setText("");
		}

		if (list.get(position).IsPraise) {
			holder.iv_user_zan.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_detail_start));
		} else {
			holder.iv_user_zan.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_detail_start_1));
		}

		if (!StringUtils.isNullOrEmpty(list.get(position).PraiseCount)) {
			holder.tv_mes_zanshu.setText("赞:" + list.get(position).PraiseCount);
		} else {
			holder.tv_mes_zanshu.setText("");
		}

		if (!StringUtils.isNullOrEmpty(list.get(position).ReadCount)) {
			holder.tv_user_readcount.setText("已读(" + list.get(position).ReadCount + ")");
		} else {
			holder.tv_user_readcount.setText("");
		}

		if (!StringUtils.isNullOrEmpty(list.get(position).ReplyCount)) {
			holder.tv_reply.setText("回复(" + list.get(position).ReplyCount + ")");
		} else {
			holder.tv_reply.setText("");
		}
		if (!StringUtils.isNullOrEmpty(list.get(position).Head)) {
			if (list.get(position).Head.startsWith("~")) {
				String replace = list.get(position).Head.replace("~", AppConfig.HOSTURL);
				mAbImageDownloader.display(holder.iv_user_heade, replace);
			} else {
				mAbImageDownloader.display(holder.iv_user_heade, list.get(position).Head);
			}
		}

		if (!StringUtils.isNullOrEmpty(list.get(position).ImgSrc)) {
			if (list.get(position).ImgSrc.startsWith("~")) {
				String replace = list.get(position).ImgSrc.replace("~", AppConfig.HOSTURL);
				// uiv_userpic_mainfrag.setUrl(replace);

				mAbImageDownloader.display(holder.uiv_userpic_mainfrag, replace);
			} else {
				mAbImageDownloader.display(holder.uiv_userpic_mainfrag, list.get(position).ImgSrc);
				// uiv_userpic_mainfrag.setUrl(list.get(position).ImgSrc);
			}

		}

		// 只有周边才显示距离
		if (!StringUtils.isNullOrEmpty(list.get(position).Distance)) {
			if (Double.parseDouble(list.get(position).Distance) > (double) 0.0) {
				holder.item_mes_distance.setVisibility(View.VISIBLE);
				holder.item_mes_assit.setVisibility(View.VISIBLE);
				holder.item_mes_distance.setText("距离：" + list.get(position).Distance + "KM");
			} else {
				holder.item_mes_distance.setVisibility(View.GONE);
				holder.item_mes_assit.setVisibility(View.GONE);
			}
		} else {
			holder.item_mes_distance.setVisibility(View.GONE);
			holder.item_mes_assit.setVisibility(View.GONE);
		}

		holder.iv_user_heade.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				if (getItem(position).UserId.equals(userUserId)) {
					context.startActivity(new Intent(context, PersonalCenterActivity.class));
				} else {
					Intent intent = new Intent();
					intent.setClass(context, OtherCenterActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					Bundle data = new Bundle();
					data.putString("OTHER_USER_ID", getItem(position).UserId);
					intent.putExtras(data);
					context.startActivity(intent);
				}
			}
		});

		holder.uiv_userpic_mainfrag.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent intent = new Intent();
				intent.setClass(context, ImageDetailActivity.class);
				String imageSrc = "";
				if (!StringUtils.isNullOrEmpty(list.get(position).ImgSrc)) {
					if (list.get(position).ImgSrc.startsWith("~")) {
						imageSrc = list.get(position).ImgSrc.replace("~", AppConfig.HOSTURL);

					} else {
						imageSrc = list.get(position).ImgSrc;
					}

				}
				intent.putExtra("ImageSrc", imageSrc);
				// intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
				context.startActivity(intent);

			}
		});

		holder.tv_contant.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(context, NewscommentActivity.class);
				// intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
				intent.putExtra("NewsId", getItem(position).NewsId);
				context.startActivity(intent);
			}
		});

		holder.ll_reply.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent();
				intent.setClass(context, NewscommentActivity.class);
				// intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
				intent.putExtra("NewsId", getItem(position).NewsId);
				context.startActivity(intent);

			}
		});

		holder.tv_share.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				final String text = getItem(position).Contents;

				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setTitle("分享到微信");
				builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						arg0.dismiss();

					}
				});
				builder.setPositiveButton("分享", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub

						if (text == null || text.length() == 0) {
							return;
						}

						// 初始化一个WXTextObject对象
						WXTextObject textObj = new WXTextObject();
						textObj.text = text;

						// 用WXTextObject对象初始化一个WXMediaMessage对象
						WXMediaMessage msg = new WXMediaMessage();
						msg.mediaObject = textObj;
						// 发送文本类型的消息时，title字段不起作用
						// msg.title = "Will be ignored";
						msg.description = text;

						// 构造一个Req
						SendMessageToWX.Req req = new SendMessageToWX.Req();
						req.transaction = String.valueOf(System.currentTimeMillis()); // transaction字段用于唯一标识一个请求
						req.message = msg;
						// req.scene = isTimelineCb.isChecked() ?
						// SendMessageToWX.Req.WXSceneTimeline :
						// SendMessageToWX.Req.WXSceneSession;

						// WXSceneTimeline朋友圈，WXSceneSession分享给好友
						req.scene = SendMessageToWX.Req.WXSceneSession;

						// 调用api接口发送数据到微信
						WXapi.sendReq(req);
					}
				});
				builder.setMessage(text);
				builder.create();
				builder.show();

			}
		});

		return convertView;

	}

	public static class ViewHolder {
		// private TextView tv_time;
		public CircleImageView iv_user_heade;
		public TextView tv_contant;
		public TextView tv_reply;
		public TextView tv_name;
		public TextView tv_user_readcount;
		public TextView tv_mes_zanshu;
		public ImageView uiv_userpic_mainfrag;
		public LinearLayout ll_reply;
		public ImageView iv_user_zan;
		public TextView item_mes_distance;
		public TextView item_mes_assit;
		public TextView tv_share;

	}

}
