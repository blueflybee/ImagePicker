package io.github.changjiashuai.adapter;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

import java.util.ArrayList;

import io.github.changjiashuai.ImagePicker;
import io.github.changjiashuai.Utils;
import io.github.changjiashuai.bean.ImageItem;

/**
 * Email: changjiashuai@gmail.com
 *
 * Created by CJS on 2017/2/23 22:52.
 */

public class ImagePageAdapter extends PagerAdapter {

    private static final String TAG = "ImagePageAdapter";
    private int screenWidth;
    private int screenHeight;
    private ArrayList<ImageItem> images = new ArrayList<>();
    private Activity mActivity;
    private PhotoViewClickListener listener;
    private View.OnLongClickListener mOnLongClickListener;

    public void setOnLongClickListener(View.OnLongClickListener onLongClickListener) {
        mOnLongClickListener = onLongClickListener;
    }

    public ImagePageAdapter(Activity activity, ArrayList<ImageItem> images) {
        this.mActivity = activity;
        this.images = images;
        DisplayMetrics dm = Utils.getScreenPix(activity);
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
    }

    public void setImages(ArrayList<ImageItem> images) {
        this.images = images;
    }

    public void setPhotoViewClickListener(PhotoViewClickListener listener) {
        this.listener = listener;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        PhotoView photoView = new PhotoView(mActivity);
        ImageItem imageItem = images.get(position);
        ImagePicker.getInstance().getImageLoader()
                .displayImage(mActivity, imageItem.path, photoView, screenWidth, screenHeight);
        photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                if (listener != null) {
                    listener.OnPhotoTapListener(view, x, y);
                }
            }

            @Override
            public void onOutsidePhotoTap() {

            }
        });
        photoView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnLongClickListener != null) {
                    mOnLongClickListener.onLongClick(v);
                    return true;
                }
                return false;
            }
        });
        container.addView(photoView);
        return photoView;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public interface PhotoViewClickListener {
        void OnPhotoTapListener(View view, float v, float v1);
    }
}