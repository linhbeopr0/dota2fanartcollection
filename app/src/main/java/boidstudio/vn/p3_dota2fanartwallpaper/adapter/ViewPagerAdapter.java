/*
package boidstudio.vn.p3_dota2fanartwallpaper.adapter;

import java.util.ArrayList;

import vn.boidstudio.dota2fanart.activity.MainActivity;
import vn.boidstudio.dota2fanart.activity.ViewPagerFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

	ViewPagerFragment mFragment;
	int[] mData;
	Context mContext;

	public ViewPagerAdapter(FragmentManager fm, int[] data,
			Context context) {
		super(fm);
		for (int i = 0; i < data.length; i++) {
			this.mData[i] = data[i];
		}
		this.mContext = context;
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int pos) {
		Log.d("LINH", "getItem: pos = " + pos);
		mFragment = new ViewPagerFragment(mContext);
		Bundle info = new Bundle();
		info.putInt("pos", pos);
		mFragment.setArguments(info);
		return mFragment;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mData.length;
	}

}
*/
