package com.xuanit.move.fragment;

import com.ab.bitmap.AbImageDownloader;
import com.xuanit.move.R;
import com.xuanit.move.activity.DongtanActivity;
import com.xuanit.move.activity.GameCenterActivity;
import com.xuanit.move.activity.HuoDongActivity;
import com.xuanit.move.activity.LifeCenterActivity;
import com.xuanit.move.activity.PersonalCenterActivity;
import com.xuanit.move.activity.SheZhiActivity;
import com.xuanit.move.app.AppConfig;
import com.xuanit.move.app.MoveApplication;
import com.xuanit.move.util.StringUtils;
import com.xuanit.move.view.CircleImageView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RightFragment extends Fragment implements OnClickListener {
	private LinearLayout ll_right_lift_center;
	private TextView person_center;
//	private LinearLayout person_xi_lie_huo_dong;
	private LinearLayout person_you_xi_zhong_xin;
	private LinearLayout person_ge_ren_she_zhi;
	private LinearLayout dong_tan_fen_si;
	// PopupWindow popupWindow;
	private CircleImageView user_heade;
	private TextView type_userName;
	private TextView star_num;
	private TextView geren_qianming;
	private AbImageDownloader mAbImageDownloader;
	/*
	 * private SharedPreferences sharedPreferences; private
	 * SharedPreferences.Editor editor;
	 */
	private MoveApplication application;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.rightmenu, container, false);
		ll_right_lift_center = (LinearLayout) view.findViewById(R.id.ll_right_lift_center);
		person_center = (TextView) view.findViewById(R.id.person_center);

//		person_xi_lie_huo_dong = (LinearLayout) view.findViewById(R.id.person_xi_lie_huo_dong);
		person_you_xi_zhong_xin = (LinearLayout) view.findViewById(R.id.person_you_xi_zhong_xin);
		person_ge_ren_she_zhi = (LinearLayout) view.findViewById(R.id.person_ge_ren_she_zhi);
		dong_tan_fen_si = (LinearLayout) view.findViewById(R.id.dong_tan_fen_si);

		geren_qianming = (TextView) view.findViewById(R.id.geren_qianming);

		user_heade = (CircleImageView) view.findViewById(R.id.user_heade);
		type_userName = (TextView) view.findViewById(R.id.type_userName);
		star_num = (TextView) view.findViewById(R.id.star_num);

		// 在User_Message_Table里面写入当前用户信息
		/*
		 * sharedPreferences =
		 * getActivity().getSharedPreferences("User_Message_File",
		 * Context.MODE_PRIVATE); editor = sharedPreferences.edit();
		 */
		// 设置页面内容
		// 头像为空暂时设置默认图片
		/*
		 * if(uri.equals("")||uri.equals(null)){
		 * user_heade.setImageResource(R.drawable.image_user_default_header);
		 * }else{ user_heade.setImageURI(uri); }
		 */
		/*
		 * if (!StringUtils.isNullOrEmpty(AppConfig.Head)) { if
		 * (AppConfig.Head.startsWith("~")) { String replace =
		 * AppConfig.Head.replace("~", AppConfig.HOSTURL);
		 * user_heade.setUrl(replace); } else { String replace =
		 * AppConfig.HOSTURL + AppConfig.Head; user_heade.setUrl(replace); } }
		 */
		if (StringUtils.isNullOrEmpty(application.appConfig.PersonalDescription)) {
			geren_qianming.setText("您还没有属于自己的个性签名！");
		} else {
			geren_qianming.setText(application.appConfig.PersonalDescription);
		}

		type_userName.setText(application.appConfig.USER_NAME);
		star_num.setText(application.appConfig.StarLevelSum);

		dong_tan_fen_si.setOnClickListener(this);
		ll_right_lift_center.setOnClickListener(this);
//		person_xi_lie_huo_dong.setOnClickListener(this);
		person_you_xi_zhong_xin.setOnClickListener(this);
		person_ge_ren_she_zhi.setOnClickListener(this);
		person_center.setOnClickListener(this);

		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		application = (MoveApplication) getActivity().getApplication();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (application != null && application.appConfig != null) {
			if (StringUtils.isNullOrEmpty(application.appConfig.PersonalDescription)) {
				geren_qianming.setText("您还没有属于自己的个性签名！");
			} else {
				geren_qianming.setText(application.appConfig.PersonalDescription);
			}

			if (mAbImageDownloader == null) {
				mAbImageDownloader = new AbImageDownloader(getActivity());
			}
			mAbImageDownloader.display(user_heade, application.appConfig.Head);
		}

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		mAbImageDownloader = new AbImageDownloader(getActivity());
		mAbImageDownloader.display(user_heade, application.appConfig.Head);
		// user_heade.setUrl(AppConfig.Head);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.person_center:
			getActivity().startActivity(new Intent(getActivity(), PersonalCenterActivity.class));
			getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			break;
		case R.id.ll_right_lift_center:
			// TODO Auto-generated method stub
			getActivity().startActivity(new Intent(getActivity(), LifeCenterActivity.class));
			getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			break;
//		case R.id.person_xi_lie_huo_dong:
//			getActivity().startActivity(new Intent(getActivity(), HuoDongActivity.class));
//			getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
//			break;
		case R.id.person_you_xi_zhong_xin:
			getActivity().startActivity(new Intent(getActivity(), GameCenterActivity.class));
			getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

			break;
		case R.id.person_ge_ren_she_zhi:
			getActivity().startActivity(new Intent(getActivity(), SheZhiActivity.class));
			getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			break;
		case R.id.dong_tan_fen_si:
			getActivity().startActivity(new Intent(getActivity(), DongtanActivity.class));
			getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			break;
		default:
			break;
		}

	}

	/*
	 * private TextView tv_xieDongTai; private TextView tv_muJuan; private
	 * TextView tv_shenqinghuodong; private TextView tv_yijianfankui;
	 * 
	 * @Override public View onCreateView(LayoutInflater inflater, ViewGroup
	 * container, Bundle savedInstanceState) { // TODO Auto-generated method
	 * stub View view = inflater.inflate(R.layout.rightmenu,container,false);
	 * tv_xieDongTai = (TextView) view.findViewById(R.id.tv_xieDongTai);
	 * tv_muJuan = (TextView) view.findViewById(R.id.tv_muJuan);
	 * tv_shenqinghuodong = (TextView)
	 * view.findViewById(R.id.tv_shenqinghuodong); tv_yijianfankui = (TextView)
	 * view.findViewById(R.id.tv_yijianfankui);
	 * 
	 * tv_xieDongTai.setOnClickListener(new OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { // TODO Auto-generated method
	 * stub getActivity().startActivity(new
	 * Intent(getActivity(),SpeechActivity.class));
	 * getActivity().overridePendingTransition(R.anim.push_left_in,
	 * R.anim.push_left_out); } });
	 * 
	 * tv_muJuan.setOnClickListener(new OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { // TODO Auto-generated method
	 * stub getActivity().startActivity(new
	 * Intent(getActivity(),MuJuanActivity.class));
	 * getActivity().overridePendingTransition(R.anim.push_left_in,
	 * R.anim.push_left_out);
	 * 
	 * } });
	 * 
	 * tv_shenqinghuodong.setOnClickListener(new OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { // TODO Auto-generated method
	 * stub getActivity().startActivity(new
	 * Intent(getActivity(),ShenqingActivity.class));
	 * getActivity().overridePendingTransition(R.anim.push_left_in,
	 * R.anim.push_left_out);
	 * 
	 * } });
	 * 
	 * tv_yijianfankui.setOnClickListener(new OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { // TODO Auto-generated method
	 * stub getActivity().startActivity(new
	 * Intent(getActivity(),FanKuiActivity.class));
	 * getActivity().overridePendingTransition(R.anim.push_left_in,
	 * R.anim.push_left_out); } }); return view; }
	 */
}

/*
 * package com.xuanit.move.fragment;
 * 
 * import com.xuanit.move.R; import com.xuanit.move.activity.FanKuiActivity;
 * import com.xuanit.move.activity.MuJuanActivity; import
 * com.xuanit.move.activity.RecommendActivity; import
 * com.xuanit.move.activity.ShenqingActivity; import
 * com.xuanit.move.activity.SpeechActivity;
 * 
 * import android.content.Intent; import android.os.Bundle; import
 * android.support.v4.app.Fragment; import android.view.LayoutInflater; import
 * android.view.View; import android.view.View.OnClickListener; import
 * android.view.ViewGroup; import android.widget.TextView;
 * 
 * public class RightFragment extends Fragment{ private TextView tv_xieDongTai;
 * private TextView tv_muJuan; private TextView tv_shenqinghuodong; private
 * TextView tv_yijianfankui;
 * 
 * @Override public View onCreateView(LayoutInflater inflater, ViewGroup
 * container, Bundle savedInstanceState) { // TODO Auto-generated method stub
 * View view = inflater.inflate(R.layout.rightmenu,container,false);
 * tv_xieDongTai = (TextView) view.findViewById(R.id.tv_xieDongTai); tv_muJuan =
 * (TextView) view.findViewById(R.id.tv_muJuan); tv_shenqinghuodong = (TextView)
 * view.findViewById(R.id.tv_shenqinghuodong); tv_yijianfankui = (TextView)
 * view.findViewById(R.id.tv_yijianfankui);
 * 
 * tv_xieDongTai.setOnClickListener(new OnClickListener() {
 * 
 * @Override public void onClick(View v) { // TODO Auto-generated method stub
 * getActivity().startActivity(new Intent(getActivity(),SpeechActivity.class));
 * getActivity().overridePendingTransition(R.anim.push_left_in,
 * R.anim.push_left_out); } });
 * 
 * tv_muJuan.setOnClickListener(new OnClickListener() {
 * 
 * @Override public void onClick(View v) { // TODO Auto-generated method stub
 * getActivity().startActivity(new Intent(getActivity(),MuJuanActivity.class));
 * getActivity().overridePendingTransition(R.anim.push_left_in,
 * R.anim.push_left_out);
 * 
 * } });
 * 
 * tv_shenqinghuodong.setOnClickListener(new OnClickListener() {
 * 
 * @Override public void onClick(View v) { // TODO Auto-generated method stub
 * getActivity().startActivity(new
 * Intent(getActivity(),ShenqingActivity.class));
 * getActivity().overridePendingTransition(R.anim.push_left_in,
 * R.anim.push_left_out);
 * 
 * } });
 * 
 * tv_yijianfankui.setOnClickListener(new OnClickListener() {
 * 
 * @Override public void onClick(View v) { // TODO Auto-generated method stub
 * getActivity().startActivity(new Intent(getActivity(),FanKuiActivity.class));
 * getActivity().overridePendingTransition(R.anim.push_left_in,
 * R.anim.push_left_out); } }); return view; } }
 */