package com.xuanit.move.adapter;

import java.util.ArrayList;
import java.util.List;

import com.ab.bitmap.AbImageDownloader;
import com.xuanit.move.R;
import com.xuanit.move.app.AppConfig;
import com.xuanit.move.app.MoveApplication;
import com.xuanit.move.model.AddPersonInfo;
import com.xuanit.move.model.PersonCenterInfo;
import com.xuanit.move.util.StringUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PersonCenterInfoAdaper extends BaseAdapter {

	private Context context;
	private LayoutInflater inflater;
	private List<PersonCenterInfo> list = new ArrayList<PersonCenterInfo>();
	private AbImageDownloader mAbImageDownloader;
	private AddPersonInfo personInfo=new AddPersonInfo();

	public PersonCenterInfoAdaper(Context context, List<PersonCenterInfo> list ,AddPersonInfo personInfo) {
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.list = list;
		this.personInfo=personInfo;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size() + 1;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		if (mAbImageDownloader == null) {
			mAbImageDownloader = new AbImageDownloader(context);
		}

		if (position == 0) {
			View headView = inflater.inflate(R.layout.item_person_center_head, null);

			ImageView add_person_head = (ImageView) headView.findViewById(R.id.add_person_head);
			TextView add_person_username = (TextView) headView.findViewById(R.id.add_person_username);
			TextView add_person_starlevel_sum = (TextView) headView.findViewById(R.id.add_person_starlevel_sum);
			TextView add_person_description = (TextView) headView.findViewById(R.id.add_person_description);

			mAbImageDownloader.display(add_person_head, personInfo.Head);
			add_person_username.setText(personInfo.UserName);
			add_person_starlevel_sum.setText(personInfo.StarLevelSum);
			if(!StringUtils.isNullOrEmpty(personInfo.Description)){
				add_person_description.setText(personInfo.Description);
			}else{
				add_person_description.setText("");
			}
			

			return headView;
		} else {
			
			View rootView = inflater.inflate(R.layout.item_person_detail, null);
			TextView person_date_day = (TextView) rootView.findViewById(R.id.person_date_day);
			TextView person_date_month = (TextView) rootView.findViewById(R.id.person_date_month);
			ImageView person_img = (ImageView) rootView.findViewById(R.id.person_img);
			TextView person_content = (TextView) rootView.findViewById(R.id.person_content);
			TextView person_ReplyCount = (TextView) rootView.findViewById(R.id.person_ReplyCount);
			TextView person_PraiseCount = (TextView) rootView.findViewById(R.id.person_PraiseCount);

			if (list != null) {

				PersonCenterInfo personCenterInfo = list.get(position - 1);
				System.out.println("###@@@@####" + personCenterInfo);
				if (personCenterInfo != null) {
					if (!StringUtils.isNullOrEmpty(personCenterInfo.PublishTime)) {
						// 对时间信息进行处理 2015-01-03 19:41:56
						String date = personCenterInfo.PublishTime;
						String dateMonth = date.substring(5, 7);
						String dateDay = date.substring(8, 10);

						if (!StringUtils.isNullOrEmpty(dateDay)) {
							person_date_day.setText(dateDay);
						}
						if (!StringUtils.isNullOrEmpty(dateMonth)) {
							person_date_month.setText(dateMonth + "月");
						}
					}
					if (!StringUtils.isNullOrEmpty(personCenterInfo.ImgSrc)) {
						person_img.setVisibility(View.VISIBLE);
						// 链接网络图片
						//mAbImageDownloader.display(person_img, AppConfig.HOSTURL + personCenterInfo.ImgSrc);
						mAbImageDownloader.display(person_img, personCenterInfo.ImgSrc);
					}else{
						person_img.setVisibility(View.GONE);
					}
					if (!StringUtils.isNullOrEmpty(personCenterInfo.Contents)) {
						person_content.setText(personCenterInfo.Contents);
					}
					if (!StringUtils.isNullOrEmpty(personCenterInfo.ReplyCount)) {
						person_ReplyCount.setText("回复:" + personCenterInfo.ReplyCount);
					}
					if (!StringUtils.isNullOrEmpty(personCenterInfo.PraiseCount)) {
						person_PraiseCount.setText("赞：" + personCenterInfo.PraiseCount);
					}
				}
				
				
				System.out.println("personCenterInfo.PublishTime===" + personCenterInfo.PublishTime);
				System.out.println("personCenterInfo.ImgSrc========" + personCenterInfo.ImgSrc);
				System.out.println("personCenterInfo.Contents======" + personCenterInfo.Contents);
				System.out.println("personCenterInfo.ReplyCount====" + personCenterInfo.ReplyCount);
				System.out.println("personCenterInfo.PraiseCount===" + personCenterInfo.PraiseCount);
				
				
			} else {
				person_date_day.setText("");
				person_date_month.setText("");
				person_img.setVisibility(View.GONE);
				person_content.setText("");
				person_ReplyCount.setText("");
				person_PraiseCount.setText("");
			}
			return rootView;
		}
	}
}
