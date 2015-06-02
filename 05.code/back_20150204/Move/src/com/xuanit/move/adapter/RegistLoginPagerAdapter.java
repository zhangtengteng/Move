package com.xuanit.move.adapter;

import com.xuanit.move.fragment.LoginFragment;
import com.xuanit.move.fragment.RegistFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class RegistLoginPagerAdapter extends FragmentPagerAdapter {

    public RegistLoginPagerAdapter(FragmentManager fm) {
	super(fm);
	// TODO Auto-generated constructor stub
    }

    @Override
    public Fragment getItem(int position) {
	// TODO Auto-generated method stub
	if (position == 0) {
	    LoginFragment fragment = new LoginFragment();
	    return fragment;
	} else if (position == 1) {
	    RegistFragment fragment = new RegistFragment();
	    return fragment;
	} else {
	    return null;
	}

    }

    @Override
    public int getCount() {
	// TODO Auto-generated method stub
	return 2;
    }

}
