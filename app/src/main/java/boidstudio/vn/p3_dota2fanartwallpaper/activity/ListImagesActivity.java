package boidstudio.vn.p3_dota2fanartwallpaper.activity;

import boidstudio.vn.p3_dota2fanartwallpaper.R;
import boidstudio.vn.p3_dota2fanartwallpaper.Utils.Utilities;
import boidstudio.vn.p3_dota2fanartwallpaper.adapter.ListImagesRecyclerAdapter;

import com.startapp.android.publish.Ad;
import com.startapp.android.publish.AdEventListener;
import com.startapp.android.publish.StartAppAd;
import com.startapp.android.publish.StartAppSDK;

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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

public class ListImagesActivity extends Activity {

    private RecyclerView mHorizontalGridview;
    private ListImagesRecyclerAdapter mAdapter;
    private Context mContext;

    private int mFullData[];
    private int mReducedData[];
    private int mLength;


    public int size = 0;
    public int currentPos = 0;

    private static int count = 0;
    private StartAppAd startAppAd = new StartAppAd(this);

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == Utilities.UPDATE_MESSAGE) {
                Log.d("LINH", "UPDATE DATA...!");
                new LoadMoreAsyncTask().execute();
            } else if (msg.what == Utilities.SCROLL_TO_LOADED_IMAGE) {
                Log.d("LINH", "SCROLL_TO_LOADED_IMAGE " + msg.arg1);
                mHorizontalGridview.smoothScrollToPosition(msg.arg1);
            }
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        mContext = this;
        setContentView(R.layout.activity_list_image_recycler_view);
        mFullData = new int[MainActivity.mData.length];
        for (int i = 0; i < MainActivity.SIZE; i++) {
            mFullData[i] = MainActivity.mData[i];
        }

        mLength = mFullData.length;
        mReducedData = new int[mLength];

        int newSize = size + 18;
        if (newSize > mLength && mLength > 0) {
            newSize = mLength;
        }

        for (int i = size; i < newSize; i++) {
            mReducedData[i] = mFullData[i];
        }
        size += 18;

        mHorizontalGridview = (RecyclerView) findViewById(R.id.horizontalListView);
      /*  LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(
                mContext, LinearLayoutManager.HORIZONTAL, true);*/
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3, GridLayoutManager.HORIZONTAL, false);
        mHorizontalGridview.setLayoutManager(gridLayoutManager);
        mAdapter = new ListImagesRecyclerAdapter(mContext, mReducedData, size, mHandler);
        mHorizontalGridview.setAdapter(mAdapter);
//		ListImageRecyclerApdater adapter = new ListImageRecyclerApdater(mContext, mReducedData);
//		mHorizontalGridview.setAdapter(adapter);

/*		mLstHrzGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(TwoWayAdapterView<?> parent, View view,
					int position, long id) {
				Intent i = new Intent(ListImagesActivity.this,
						FullScreenActivity.class);
				i.putExtra("link", mFullData[position]);
				currentPos = position;
				startActivity(i);
			}
		});*/
        initAd();
        Log.d("LINH", "List count = " + count);
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
            window.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
            window.setBackgroundDrawableResource(R.color.transparent);

            pDialog.setContentView(mn);
            pDialog.setCancelable(false);
            pDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            int newSize = size + 18;
            if (newSize > mLength && mLength > 0) {
                newSize = mLength;
            }
            for (int i = size; i < newSize; i++) {
                mReducedData[i] = mFullData[i];
            }
            size += 18;
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
            mAdapter.notifyDataSetChanged();
            // pdBar.setVisibility(View.INVISIBLE);
            super.onPostExecute(result);
        }

    }

    private void initAd() {
        StartAppSDK.init(this, "109866585", "212836627", false);
        this.registerReceiver(this.mConnReceiver, new IntentFilter(
                ConnectivityManager.CONNECTIVITY_ACTION));
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
                if (count != 0 && (count % 3) == 0) {
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

    @Override
    protected void onDestroy() {
        size = 0;
        count++;
        unregisterReceiver(mConnReceiver);
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        Log.d("LINH", "onResume()");
        if (currentPos > 10) {
            mHorizontalGridview.smoothScrollToPosition(currentPos);
        }
        super.onResume();
    }
}
