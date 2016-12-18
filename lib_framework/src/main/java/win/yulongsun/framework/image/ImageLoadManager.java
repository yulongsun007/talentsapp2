package win.yulongsun.framework.image;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

/**
 * CREATE : yulongsun
 * DATE : 16/7/30
 * DESC : 图片加载管理器
 */
public class ImageLoadManager {

    private static volatile ImageLoadManager       sInstance;
    private                 RequestManager         mRequestManager;
    private DrawableRequestBuilder builder;

    public static ImageLoadManager getInstance() {
        if (sInstance == null) {
            synchronized (ImageLoadManager.class) {
                if (sInstance == null) {
                    sInstance = new ImageLoadManager();
                }
            }
        }
        return sInstance;
    }

    //----------------------------------------
    // with
    //----------------------------------------
    public ImageLoadManager with(Context context) {
        mRequestManager = Glide.with(context);
        return this;
    }

    public ImageLoadManager with(Activity activity) {
        mRequestManager = Glide.with(activity);
        return this;
    }

    public ImageLoadManager with(FragmentActivity fragmentActivity) {
        mRequestManager = Glide.with(fragmentActivity);
        return this;
    }

    public ImageLoadManager with(Fragment fragment) {
        mRequestManager = Glide.with(fragment);
        return this;
    }

    public ImageLoadManager with(android.app.Fragment fragment) {
        mRequestManager = Glide.with(fragment);
        return this;
    }

    //----------------------------------------
    // load
    //----------------------------------------
    public ImageLoadManager load(String url) {
        builder = mRequestManager.load(url).diskCacheStrategy(DiskCacheStrategy.RESULT);
        return this;
    }

    public ImageLoadManager load(int resId) {
        builder = mRequestManager.load(resId).diskCacheStrategy(DiskCacheStrategy.RESULT);
        return this;
    }

    public ImageLoadManager load(Uri uri) {
        builder = mRequestManager.load(uri).diskCacheStrategy(DiskCacheStrategy.RESULT);
        return this;
    }

    public ImageLoadManager needLruCache(boolean needLruCache) {
        builder = builder.skipMemoryCache(!needLruCache);
        return this;
    }

    @SuppressWarnings("unchecked")
    @Deprecated
    public ImageLoadManager setListener(RequestListener listener) {
//        builder = builder.listener(listener);
        return this;
    }


    public ImageLoadManager setSize(int width, int height) {
        builder = builder.override(width, height);
        return this;
    }

    public ImageLoadManager fitCenter() {
        builder = builder.fitCenter();
        return this;
    }

    public ImageLoadManager centerCrop() {
        builder = builder.centerCrop();
        return this;
    }

    public ImageLoadManager setLoading(int resId) {
        builder = builder.placeholder(resId);
        return this;
    }

    public ImageLoadManager setError(int resId) {
        builder = builder.error(resId);
        return this;
    }

    public void into(ImageView imageView) {
        builder.into(imageView);
    }

    public void into(GlideDrawableImageViewTarget target) {
        builder.into(target);
    }

//    public void load(String url, ImageView imageView, boolean needLruCache){
//        mRequestManager.load(url).skipMemoryCache(needLruCache).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
//    }
//
//    public void load(String url, ImageView imageView){
//        load(url, imageView, false);
//    }
//
//    public void load(String url, ImageView imageView, int width, int height, boolean needLruCache){
//        mRequestManager.load(url).override(width, height).skipMemoryCache(needLruCache).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
//    }
//
//    public void load(String url, ImageView imageView, int width, int height){
//        load(url, imageView, width, height, false);
//    }

}
