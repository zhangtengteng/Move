package com.xuanit.move.activity;

import android.R.integer;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.ab.activity.AbActivity;
import com.xuanit.move.R;
import com.xuanit.move.util.CloseAllActivityManager;

public class EditTextActivity extends AbActivity implements OnClickListener {

	private EditText et_content;
	private String oldContent;
	private int maxLenth;
	private String titleContent;
	private String content;
	private TextView current_lenth;
	private int len;
	private int code;
	private LinearLayout ll_header_left;
	private TextView tv_header_middle;
	private LinearLayout ll_header_right;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		CloseAllActivityManager.getInstance().addActivity(EditTextActivity.this);
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.activity_edittext);
		getTitleBar().setVisibility(View.GONE);
		
		Bundle bundle = this.getIntent().getExtras();
		maxLenth = bundle.getInt("maxLenth");
		oldContent = bundle.getString("content");
		titleContent = bundle.getString("title");
		code = bundle.getInt("code");
		current_lenth = (TextView) findViewById(R.id.current_lenth);
		et_content = (EditText) findViewById(R.id.et_content);
		ll_header_left = (LinearLayout) findViewById(R.id.ll_header_left);
		tv_header_middle = (TextView) findViewById(R.id.tv_header_middle);
		ll_header_right = (LinearLayout) findViewById(R.id.ll_header_right);

		ll_header_left.setOnClickListener(this);
		ll_header_right.setOnClickListener(this);

		tv_header_middle.setText(titleContent);
		et_content.setText(oldContent);
		content = et_content.getText().toString();
		len = content.length();
		len = maxLenth - len;
		current_lenth.setText(len + "/" + maxLenth);

		et_content.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				content = et_content.getText().toString();
				len = content.length();
				if (len <= maxLenth) {
					len = maxLenth - len;
					current_lenth.setTextColor(getResources().getColor(R.color.gray));
					current_lenth.setText(String.valueOf(len) + "/" + maxLenth);
				}

				else {
					len = len - maxLenth;
					current_lenth.setTextColor(Color.RED);
					current_lenth.setText("-" + String.valueOf(len) + "/"
							+ maxLenth);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ll_header_left:
				this.finish();
			break;
		case R.id.ll_header_right:
			if (content.length() > maxLenth){
				Toast.makeText(this, "字数超过限制字数", Toast.LENGTH_LONG).show();
				return;
			}else{
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
			    bundle.putString("content", content);							
			    intent.putExtras(bundle);
				this.setResult(code, intent);
				this.finish();
			}
			break;

		default:
			break;
		}
	}
}
