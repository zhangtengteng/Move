package com.xuanit.move.fragment;

import com.xuanit.move.R;
import com.xuanit.move.activity.LoginRegistActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class StartFragment extends Fragment{
	public static final String ARG_PAGE = "page_num";  
	private int currentPageNum;
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onActivityCreated(android.os.Bundle)
	 */
	
	  public static StartFragment create(int pagerNum) {  
		    
		  StartFragment myPageFrament = new StartFragment();  
		          Bundle arg = new Bundle();  
		          arg.putInt(ARG_PAGE, pagerNum);  
		           myPageFrament.setArguments(arg);  
		 
		          return myPageFrament;  
		    
		  } 

	   @Override  
	      public void onCreate(Bundle savedInstanceState) {  
	           super.onCreate(savedInstanceState);  
	         Log.i("INFO", "onCreate..");  
	         currentPageNum = getArguments().getInt(ARG_PAGE);  
	      }  


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View root = null;
		ImageView iv = null;
		if(currentPageNum==2){
			root=inflater.inflate(R.layout.fragment_start_two, null);
		}else{
			root=inflater.inflate(R.layout.fragment_start_one, null);
			iv=(ImageView) root.findViewById(R.id.iv_start_pic);
		}
		
		switch (currentPageNum) {
		case 0:
			iv.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.start_001));
			break;
		case 1:
			iv.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.start_002));
			break;
		case 2:
			Button but_start=(Button) root.findViewById(R.id.but_start);
			but_start.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					Intent intent=new Intent(); 
					intent.setClass(getActivity(), LoginRegistActivity.class);
					getActivity().startActivity(intent);
					getActivity().finish();
					getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
				}
			});
			break;
		default:
			break;
		}
		return root;
	}
	

	
}
