/*
package boidstudio.vn.p3_dota2fanartwallpaper.adapter;

import java.util.ArrayList;
import java.util.List;

import vn.boidstudio.dota2fanart.activity.ListImagesActivity;
import vn.boidstudio.dota2fanart.activity.MainActivity;
import vn.boidstudio.dota2fanart.activity.ViewPagerActivity;
import vn.boidstudio.dota2fanart.model.CategoryData;
import vn.boidstudio.dota2fanart.volley.utils.AppController;
import vn.boidstudio.dota2fanart.volley.utils.MyNetworkImage;
import vn.boidstudio.dota2fanart.volley.utils.MyNetworkImageWithResponse;
import vn.boidstudio.dota2fanart.R;

import com.android.volley.toolbox.ImageLoader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;

public class PreviewListAdapter extends BaseAdapter {

	private Context mContext;
	private int[] data;

	// HorzGridView stuff
	private final int childLayoutResourceId = R.layout.activity_viewpager_preview_list_item;
	private int columns;// Used to set childSize in TwoWayGridView
	private int rows;// used with TwoWayGridView
	private int itemPadding;
	private int columnWidth;
	private int rowHeight;
	private ImageLoader imageLoader = AppController.getInstance()
			.getImageLoader();

	private Handler mHandler;
	private int count;
	private int scrolling_count;

	public PreviewListAdapter(Context context, int[] data,
			Handler handler) {
		this.mContext = context;
		for (int i = 0; i < data.length; i++) {
			this.data[i] = data[i];
		}
		this.mHandler = handler;
		this.count = 1;
		this.scrolling_count = 0;
		// Get dimensions from values folders; note that the value will change
		// based on the device size but the dimension mName will remain the same
		Resources res = mContext.getResources();
		itemPadding = (int) res.getDimension(R.dimen.horz_item_padding);
		rows = 1;
		columns = 5;

		// Initialize the layout params
		ViewPagerActivity.mHrzGridView.setNumRows(rows);

		// HorzGridView size not established yet, so need to set it using a
		// viewtreeobserver
		ViewTreeObserver vto = ViewPagerActivity.mHrzGridView
				.getViewTreeObserver();

		OnGlobalLayoutListener onGlobalLayoutListener = new OnGlobalLayoutListener() {

			@SuppressWarnings("deprecation")
			@SuppressLint("NewApi")
			@Override
			public void onGlobalLayout() {
				// First use the gridview height and width to determine child
				// values
				rowHeight = (int) ((float) (ViewPagerActivity.mHrzGridView
						.getHeight() / rows) - 2 * itemPadding);
				columnWidth = (int) ((float) (ViewPagerActivity.mHrzGridView
						.getWidth() / columns) - 2 * itemPadding);

				ViewPagerActivity.mHrzGridView.setRowHeight(rowHeight);

				// Then remove the listener
				ViewTreeObserver vto = ViewPagerActivity.mHrzGridView
						.getViewTreeObserver();

				*/
/*
				 * if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
				 * { vto.removeOnGlobalLayoutListener(this); } else {
				 * vto.removeGlobalOnLayoutListener(this); }
				 *//*

				vto.removeGlobalOnLayoutListener(this);

			}
		};

		vto.addOnGlobalLayoutListener(onGlobalLayoutListener);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// Get the data for the given position in the array
		int thisData = data[position];
		if (position == ((count * 20) - 5)) {
			mHandler.sendEmptyMessage(ListImagesActivity.UPDATE_MESSAGE);
			count++;
		}

		// Use a viewHandler to improve performance
		ViewHolder holder;

		// If reusing a view get the handler info; if view is null, create it
		if (convertView == null) {

			// Only get the inflater when it's needed, then release it-which
			// isn't frequently
			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater
					.inflate(childLayoutResourceId, parent, false);

			// User findViewById only when first creating the child view
			holder = new ViewHolder();
			holder.iv = (MyNetworkImageWithResponse) convertView
					.findViewById(R.id.categoryImgView);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (imageLoader == null) {
			imageLoader = AppController.getInstance().getImageLoader();
		}

		holder.iv.setImageResource(data[position]);
		FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(columnWidth,
				rowHeight);// convertView.getLayoutParams();
		holder.iv.setLayoutParams(lp);
		return convertView;
	}

	private static class ViewHolder {
		MyNetworkImageWithResponse iv;
	}

	@Override
	public int getCount() {

		return data.length;
	}

	@Override
	public Object getItem(int position) {

		return data[position];
	}

	@Override
	public long getItemId(int position) {

		return 0;
	}

}
*/
