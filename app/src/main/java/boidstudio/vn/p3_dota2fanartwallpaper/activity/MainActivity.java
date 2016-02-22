package boidstudio.vn.p3_dota2fanartwallpaper.activity;

import java.util.ArrayList;
import java.util.Random;


import com.startapp.android.publish.Ad;
import com.startapp.android.publish.AdEventListener;
import com.startapp.android.publish.StartAppAd;
import com.startapp.android.publish.StartAppSDK;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import boidstudio.vn.p3_dota2fanartwallpaper.R;

public class MainActivity extends Activity {

    private Context mContext;
    private ImageView[] mImgList;
    private RelativeLayout mBtnCategory;
    private TextView mTextBanner;

    public static int SIZE = 100;
    private int ITEM_SIZE = 14;
    private int[] mImgListIdx;
    public static int mData[];
    public static int mCtLoadingIndex = 0;

    private static Typeface Rabanera_shadow_font;

    private Thread mThread;
    private Handler mHandler;

    private StartAppAd startAppAd = new StartAppAd(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        initData();
        initRes();
        initTimer();
        initListener();
        initAd();
        initLoadingIndex();
    }

    private void initAd() {
        StartAppSDK.init(this, "109866585", "212836627", false);
        this.registerReceiver(this.mConnReceiver, new IntentFilter(
                ConnectivityManager.CONNECTIVITY_ACTION));

    }

    private void initListener() {
        mBtnCategory.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(new Intent(MainActivity.this,
                        ListImagesActivity.class));
                startActivity(i);
            }
        });

        for (int i = 0; i < ITEM_SIZE; i++) {
            final int pos = i;
            mImgList[pos].setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                  /*  Intent intent = new Intent(new Intent(MainActivity.this,
                            ViewPagerActivity.class));

                    intent.putExtra("index", mImgListIdx[pos]);
                    startActivity(intent);*/
                }
            });
        }

    }

    private void initTimer() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 99) {
                    // Log.d("LINH", "Animate!");
                    initAmination();
                }
            }
        };

        mThread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    while (true) {
                        Thread.sleep(5000);
                        mHandler.sendEmptyMessage(99);
                    }
                    // stopThread(mThread);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });
        mThread.start();
    }

    private void initAmination() {
        final int[] newIndex = randomAllocate(SIZE);
        for (int i = 0; i < ITEM_SIZE; i++) {
            final int pos = i;
            final int idx = newIndex[pos];
            float xPos = mImgList[pos].getWidth();
            float yPos = mImgList[pos].getHeight();
            AnimationSet am = new AnimationSet(true);
            ScaleAnimation s1 = new ScaleAnimation(0, 1.2f, 0, 1.2f, xPos / 2,
                    yPos / 2);
            s1.setDuration(200);
            ScaleAnimation s2 = new ScaleAnimation(1.2f, 0.8f, 1.2f, 0.8f,
                    xPos / 2, yPos / 2);
            s2.setDuration(200);
            s2.setStartOffset(200);
            ScaleAnimation s3 = new ScaleAnimation(0.8f, 1.04f, 0.8f, 1.04f,
                    xPos / 2, yPos / 2);
            s3.setDuration(200);
            s3.setStartOffset(400);

            am.setFillAfter(true);
            am.addAnimation(s1);
            am.addAnimation(s2);
            am.addAnimation(s3);
            am.setAnimationListener(new AnimationListener() {

                @Override
                public void onAnimationStart(Animation animation) {
                    //mImgList[pos].setImageResource(mData[idx]);
                    setPic(mImgList[pos], mData[idx]);
                    mImgListIdx[pos] = idx;
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                }
            });
            am.setStartOffset(i * 50);
            mImgList[pos].startAnimation(am);
        }

    }

    private void initRes() {
        Rabanera_shadow_font = Typeface.createFromAsset(getAssets(),
                "Rabanera-outline-shadow.ttf");
        mBtnCategory = (RelativeLayout) findViewById(R.id.btnCategory);
        mTextBanner = (TextView) findViewById(R.id.txtBanner);

        mImgListIdx = new int[ITEM_SIZE];
        mImgList = new ImageView[ITEM_SIZE];
        mImgList[0] = (ImageView) findViewById(R.id.layout_1_img1);
        mImgList[1] = (ImageView) findViewById(R.id.layout_1_img2);

        mImgList[2] = (ImageView) findViewById(R.id.layout_2_img1);
        mImgList[3] = (ImageView) findViewById(R.id.layout_2_img2);
        mImgList[4] = (ImageView) findViewById(R.id.layout_2_img3);
        mImgList[5] = (ImageView) findViewById(R.id.layout_2_img4);
        mImgList[6] = (ImageView) findViewById(R.id.layout_2_img5);

        mImgList[7] = (ImageView) findViewById(R.id.layout_3_img1);
        mImgList[8] = (ImageView) findViewById(R.id.layout_3_img2);
        mImgList[9] = (ImageView) findViewById(R.id.layout_3_img3);
        mImgList[10] = (ImageView) findViewById(R.id.layout_3_img4);

        mImgList[11] = (ImageView) findViewById(R.id.layout_4_img1);
        mImgList[12] = (ImageView) findViewById(R.id.layout_4_img2);
        mImgList[13] = (ImageView) findViewById(R.id.layout_4_img3);

        final int[] newIndex = randomAllocate(SIZE);

        Log.d("LINH", "f0 = " + R.drawable.f0 + " - " + mData[0]);
        for (int i = 0; i < ITEM_SIZE; i++) {
//			mImgList[i].setImageResource(mData[1]);
            setPic(mImgList[i], mData[newIndex[i]]);
            mImgListIdx[i] = newIndex[i];
        }

        mTextBanner.setTypeface(Rabanera_shadow_font);
    }

    private int[] randomAllocate(int size) {
        int result[] = new int[size];
        ArrayList<Integer> tmp = new ArrayList<Integer>();
        for (int i = 0; i < size; i++) {
            tmp.add(i);
        }
        for (int i = 0; i < size; i++) {
            int rdNumber = random(tmp);
            result[i] = rdNumber;
        }
        return result;
    }

    private int random(ArrayList<Integer> arr) {
        int realSize = arr.size() - 1;
        int tmpValue = 0;
        if (realSize > 0) {
            int index = new Random().nextInt(realSize);
            tmpValue = arr.get(index);
            arr.remove(index);
        } else if (realSize == 0) {
            tmpValue = arr.get(0);
        }
        return tmpValue;
    }

    @Override
    protected void onDestroy() {
        // stopThread(thread);
        mThread.interrupt();
        unregisterReceiver(mConnReceiver);
        super.onDestroy();
    }

    private void initData() {
        mData = new int[SIZE];
        for (int i = 0; i < SIZE; i++) {
            int id = getResources().getIdentifier("f" + i, "drawable",
                    getPackageName());
            mData[i] = id;
        }
        Log.d("LINH", "SIZE = " + SIZE);
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
            } else {
                Toast.makeText(mContext,
                        "Please turn on network connection to download data!",
                        Toast.LENGTH_LONG).show();
            }
        }
    };

    private void initLoadingIndex() {
        final int[] newIndex = randomAllocate(10);
        mCtLoadingIndex = newIndex[5];
    }

    private void setPic(ImageView mImageView, int id) {
        // Get the dimensions of the View
//	    int targetW = mImageView.getMeasuredWidth();
//	    int targetH = mImageView.getMeasuredHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), id, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = /*Math.min(photoW/targetW, photoH/targetH)*/ 5;

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), id, bmOptions);
        mImageView.setImageBitmap(bitmap);
    }

}
