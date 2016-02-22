package boidstudio.vn.p3_dota2fanartwallpaper.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;


import boidstudio.vn.p3_dota2fanartwallpaper.R;
import boidstudio.vn.p3_dota2fanartwallpaper.Utils.Utilities;
import boidstudio.vn.p3_dota2fanartwallpaper.activity.MainActivity;

public class ListImagesRecyclerAdapter extends
        RecyclerView.Adapter<ListImagesRecyclerAdapter.ListImageHolder> {

    private Context mContext;
    private int mData[];
    private int mLength;
    private int mCount;
    private Handler mHandler;

    public ListImagesRecyclerAdapter(Context ctx, int[] data, int length, Handler handler) {
        mContext = ctx;
        mData = data;
        mLength = length;
        mHandler = handler;
        mCount = 1;
    }

    public class ListImageHolder extends RecyclerView.ViewHolder {
        private ImageView iv;

        public ListImageHolder(View v) {
            super(v);
            iv = (ImageView) v.findViewById(R.id.lstImgView);
        }
    }

    @Override
    public int getItemCount() {
        return mLength;
    }

    @Override
    public void onBindViewHolder(ListImageHolder holder, int pos) {
        if (pos == ((mCount * 18) - 5)) {
            mHandler.sendEmptyMessage(Utilities.UPDATE_MESSAGE);
            mCount++;
        }

        int screenSize[] = Utilities.getScreenSize(mContext);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(screenSize[0] / 3, screenSize[1] / 3);
        holder.iv.setLayoutParams(lp);
        setPic(holder.iv, mData[pos]);
    }

    @Override
    public ListImageHolder onCreateViewHolder(ViewGroup parent, int arg1) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.activity_list_images_item, parent, false);
        ListImageHolder holder = new ListImageHolder(v);
        return holder;
    }

    private void setPic(ImageView mImageView, int id) {
        // Get the dimensions of the View
        // int targetW = mImageView.getMeasuredWidth();
        // int targetH = mImageView.getMeasuredHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(mContext.getResources(), id, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = /* Math.min(photoW/targetW, photoH/targetH) */5;

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(),
                id, bmOptions);
        mImageView.setImageBitmap(bitmap);
    }

}
