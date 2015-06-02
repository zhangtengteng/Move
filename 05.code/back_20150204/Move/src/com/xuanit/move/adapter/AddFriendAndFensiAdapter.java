package com.xuanit.move.adapter;

import java.util.ArrayList;
import java.util.List;

import com.ab.bitmap.AbImageDownloader;
import com.xuanit.move.R;
import com.xuanit.move.model.AddPersonInfo;
import com.xuanit.move.util.StringUtils;
import com.xuanit.move.view.CircleImageView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class AddFriendAndFensiAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;
	private List<AddPersonInfo> list = new ArrayList<AddPersonInfo>();
	private static List<String> userIdList = new ArrayList<String>();
	private AbImageDownloader mAbImageDownloader;
	
	public AddFriendAndFensiAdapter(Context context, List<AddPersonInfo> list) {
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public AddPersonInfo getItem(int position) {
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
		// TODO Auto-generated method stub
		if(mAbImageDownloader==null){
			mAbImageDownloader=new AbImageDownloader(context);
		}

		final JianZhiViewHolder holder;
		
		holder = new JianZhiViewHolder();
				
		View root =  inflater.inflate(R.layout.item_add_friend_and_fensi, null);
		holder.item_add_heade = (CircleImageView) root.findViewById(R.id.item_add_heade);
		holder.item_add_name = (TextView) root.findViewById(R.id.item_add_name);
		holder.item_add_qianming = (TextView) root.findViewById(R.id.item_add_qianming);
		holder.item_add_school = (TextView) root.findViewById(R.id.item_add_school);
		//holder.rl_add_friend_fensi = (RelativeLayout) convertView.findViewById(R.id.rl_add_friend_fensi);
		holder.item_add_guanzhu = (CheckBox) root.findViewById(R.id.item_add_guanzhu);

		if (list != null) {
			AddPersonInfo addInfo = list.get(position);
			if (addInfo != null) {

				if (!StringUtils.isNullOrEmpty(addInfo.Head)) {
					mAbImageDownloader.display(holder.item_add_heade, addInfo.Head);
				}
				
				if (!StringUtils.isNullOrEmpty(addInfo.UserName)) {
					holder.item_add_name.setText(addInfo.UserName);
				} else {
					holder.item_add_name.setText("");
				}
				if (!StringUtils.isNullOrEmpty(addInfo.Description)) {
					holder.item_add_qianming.setText(addInfo.Description);
				} else {
					holder.item_add_qianming.setText("此人很懒什么都没有留下！");
				}
				
				if (addInfo.IsAssociated) {
					
					holder.item_add_guanzhu.setClickable(false);
					holder.item_add_guanzhu.setChecked(true);
					holder.item_add_guanzhu.setText("已关注");

				} else {
					holder.item_add_guanzhu.setChecked(false);
					holder.item_add_guanzhu.setClickable(true);
					holder.item_add_guanzhu.setText("未关注");
				}
//				userIdList.clear();
				holder.item_add_guanzhu.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						
						if(true == isChecked){
							userIdList.add(getItem(position).UserId);
							System.out.println("getItem(position).UserId" + getItem(position).UserId);
							System.out.println("userIdList+++++++++++" + userIdList);
							holder.item_add_guanzhu.setText("已关注");
						}else{
							holder.item_add_guanzhu.setText("未关注");
							userIdList.remove(getItem(position).UserId);
						}
					}
				});				
				/*holder.rl_add_friend_fensi.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent();
						intent.setClass(context, PersonalCenterActivity.class);

						intent.putExtra("UserId", getItem(position).UserId);//用户ID
						intent.putExtra("UserName", getItem(position).UserName);//用户昵称
						intent.putExtra("Pwd", getItem(position).Pwd);
						intent.putExtra("Salt", getItem(position).Salt);
						intent.putExtra("StudentNum", getItem(position).StudentNum);
						intent.putExtra("SchoolId", getItem(position).SchoolId);
						intent.putExtra("Head", getItem(position).Head);//学生证
						intent.putExtra("StudentCard", getItem(position).StudentCard);//头像
						intent.putExtra("AuditStatus", getItem(position).AuditStatus);
						intent.putExtra("StarLevel", getItem(position).StarLevel);//星级
						intent.putExtra("LevelMax", getItem(position).LevelMax);
						intent.putExtra("GameIntegral", getItem(position).GameIntegral);//游戏积分
						intent.putExtra("StarLevelSum", getItem(position).StarLevelSum);
						intent.putExtra("MoveCount", getItem(position).MoveCount);//动弹数
						intent.putExtra("Status", getItem(position).Status);
						intent.putExtra("CreateTime", getItem(position).CreateTime);
						intent.putExtra("CreateUser", getItem(position).CreateUser);
						intent.putExtra("LastChangeTime", getItem(position).LastChangeTime);
						intent.putExtra("LastChangeUser", getItem(position).LastChangeUser);
						intent.putExtra("ActId", getItem(position).ActId);
						intent.putExtra("IsAssociated", getItem(position).IsAssociated);
						intent.putExtra("NumberStars", getItem(position).NumberStars);
						intent.putExtra("PersonalCenter", getItem(position).PersonalCenter);
						intent.putExtra("Description", getItem(position).Description);
						
						context.startActivity(intent);
					}
				});*/
			}
		} else {
			holder.item_add_name.setText("");
			holder.item_add_qianming.setText("");
			holder.item_add_school.setText("");
//			holder.item_add_heade.setUrl(null);
		}
		return root;
	}

	class JianZhiViewHolder {
		private CircleImageView item_add_heade;
		private TextView item_add_name;
		private TextView item_add_qianming;
		private TextView item_add_school;
		//private RelativeLayout rl_add_friend_fensi;
		private CheckBox item_add_guanzhu;
	}
	
	
	public static List<String> getUserIdList(){
		System.out.println("getUserIdList+++++++++++" + userIdList);
		return userIdList;
	}

}
