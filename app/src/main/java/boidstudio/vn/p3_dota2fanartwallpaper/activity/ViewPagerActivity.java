/*
package boidstudio.vn.p3_dota2fanartwallpaper.activity;

import java.util.ArrayList;

import vn.boidstudio.dota2fanart.adapter.PreviewListAdapter;
import vn.boidstudio.dota2fanart.adapter.ViewPagerAdapter;
import vn.boidstudio.dota2fanart.model.CategoryData;
import vn.boidstudio.dota2fanart.R;

import com.jess.ui.TwoWayAdapterView;
import com.jess.ui.TwoWayAdapterView.OnItemClickListener;
import com.jess.ui.TwoWayGridView;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.startapp.android.publish.Ad;
import com.startapp.android.publish.AdEventListener;
import com.startapp.android.publish.StartAppAd;
import com.startapp.android.publish.StartAppSDK;

public class ViewPagerActivity extends FragmentActivity {

	private ViewPager mViewPager;
	public static TwoWayGridView mHrzGridView;
	private ImageView mBtnNext;
	private ImageView mBtnPrev;
	private PreviewListAdapter mPreviewListAdapter;
	private int[] mReducedLinks;
	private int[] mLinks;
	private Context mContext;
	private ViewPagerAdapter mPagerAdapter;

	public static int UPDATE_MESSAGE = 99;
	public static int SCROLL_TO_LOADED_IMAGE = 100;
	public int size = 0;
	private int mLength;

	private static int count = 0;
	private StartAppAd startAppAd = new StartAppAd(this);

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == UPDATE_MESSAGE) {
				Log.d("LINH", "UPDATE DATA...!");
				new LoadMoreAsyncTask().execute();
			} else if (msg.what == SCROLL_TO_LOADED_IMAGE) {
				*/
/*
				 * Log.d("LINH", "Pager scroll to: " + msg.arg1);
				 * mHrzGridView.smoothScrollToPosition(msg.arg1);
				 *//*

			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_viewpager);
		mContext = this;

		mBtnPrev = (ImageView) findViewById(R.id.btnPrev);
		mBtnNext = (ImageView) findViewById(R.id.btnNext);
		mViewPager = (ViewPager) findViewById(R.id.viewPager);
		mHrzGridView = (TwoWayGridView) findViewById(R.id.vPhrzGrid);

		int pos = getIntent().getIntExtra("index", 0);
		Log.d("LINH", "pos = " + pos);
		mLinks = new int[101];

		for (int i = 0; i < MainActivity.SIZE; i++) {
			mLinks[i] = MainActivity.mData[i];
		}

		mLength = mLinks.length;
		mReducedLinks = new int[101];
		int newSize = size + 20;
		if (newSize > mLength && mLength > 0) {
			newSize = mLength;
		}
		for (int i = size; i < newSize; i++) {
			mReducedLinks[i] = mLinks[i];
		}
		size += 20;
		mPreviewListAdapter = new PreviewListAdapter(mContext, mReducedLinks,
				mHandler);
		mHrzGridView.setAdapter(mPreviewListAdapter);

		// init viewPager
		mPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),
				mLinks, mContext);
		mViewPager.setAdapter(mPagerAdapter);

		mHrzGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(TwoWayAdapterView<?> parent, View view,
					int position, long id) {
				mViewPager.setCurrentItem(position);
			}
		});

		// mViewPager.setOffscreenPageLimit(10);\
		Log.d("LINH", "setCurrentItem = " + pos);
		mViewPager.setCurrentItem(pos - 1);
		mHrzGridView.smoothScrollToPosition(pos - 1);
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int pos) {
				Log.d("LINH", "Pos = " + pos);
				mHrzGridView.smoothScrollToPosition(pos);
			}

			@Override
			public void onPageScrolled(int pos, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int pos) {

			}
		});

		initAd();

	}

	private class LoadMoreAsyncTask extends AsyncTask<Void, Void, Void> {
		Dialog pDialog;

		@Override
		protected void onPreExecute() {
			pDialog = new Dialog(mContext,
					R.style.TransparentProgressDialogWithPngImage);
			View mn = LayoutInflater.from(mContext).inflate(
					R.layout.remove_border_pdialog, null);
			Window window = pDialog.getWindow();
			window.setGravity(Gravity.RIGHT | Gravity.BOTTOM);
			window.setBackgroundDrawableResource(R.color.transparent);

			pDialog.setContentView(mn);
			pDialog.setCancelable(false);
			pDialog.show();
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... params) {
			int newSize = size + 20;
			if (newSize > mLength && mLength > 0) {
				newSize = mLength;
			}
			for (int i = size; i < newSize; i++) {
				mReducedLinks[i] = mLinks[i];
			}
			size += 20;
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			if (pDialog.isShowing()) {
				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {
						pDialog.dismiss();
					}
				}, 1000);

			}
			mPreviewListAdapter.notifyDataSetChanged();
			mPagerAdapter.notifyDataSetChanged();
			// pdBar.setVisibility(View.INVISIBLE);
			super.onPostExecute(result);
		}

	}

	private void initAd() {
		StartAppSDK.init(this, "109866585", "212836627", false);
		this.registerReceiver(this.mConnReceiver, new IntentFilter(
				ConnectivityManager.CONNECTIVITY_ACTION));
	}

	@Override
	protected void onDestroy() {
		Log.d("LINH", "Destroy!");
		count++;
		unregisterReceiver(mConnReceiver);
		super.onDestroy();
	}

	private BroadcastReceiver mConnReceiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			boolean noConnectivity = intent.getBooleanExtra(
					ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
			String reason = intent
					.getStringExtra(ConnectivityManager.EXTRA_REASON);
			boolean isFailover = intent.getBooleanExtra(
					ConnectivityManager.EXTRA_IS_FAILOVER, false);

			NetworkInfo currentNetworkInfo = (NetworkInfo) intent
					.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
			NetworkInfo otherNetworkInfo = (NetworkInfo) intent
					.getParcelableExtra(ConnectivityManager.EXTRA_OTHER_NETWORK_INFO);

			if (currentNetworkInfo.isConnected()) {
				if (count != 0 && (count % 2) == 0) {
					startAppAd.loadAd(new AdEventListener() {

						@Override
						public void onReceiveAd(Ad ad) {
							Log.d("LINH", "Ad received!");
							startAppAd.showAd();
						}

						@Override
						public void onFailedToReceiveAd(Ad ad) {
							Log.d("LINH", "Ad failed");

						}
					});
				}

			} else {
				Toast.makeText(mContext,
						"Please turn on network connection to download data!",
						Toast.LENGTH_LONG).show();
			}
		}
	};

	private ArrayList<String> converTo1280x720(ArrayList<String> links) {
		ArrayList<String> result = new ArrayList<String>();
		for (String str : links) {
			String tmp = str.replace("wide", "1280x720");
			String tmp2 = tmp.replace("walls", "download");
			result.add(tmp2);
		}
		return result;
	}
}
*/
