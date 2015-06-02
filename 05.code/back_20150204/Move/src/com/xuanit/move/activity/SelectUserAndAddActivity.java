package com.xuanit.move.activity;

import android.os.Bundle;
import android.widget.EditText;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.xuanit.move.R;
import com.xuanit.move.base.BaseActivity;

public class SelectUserAndAddActivity extends BaseActivity{
	
	private PullToRefreshListView pulltorefrshListView_select_result;
	private EditText select_msg;
	private String selectMsgStr;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setView(R.layout.activity_selectuser_and_add);
		
		select_msg = (EditText) findViewById(R.id.select_msg);
		pulltorefrshListView_select_result = (PullToRefreshListView) findViewById(R.id.pulltorefrshListView_select_result);
		
		selectMsgStr = select_msg.getText().toString().trim();
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
}
