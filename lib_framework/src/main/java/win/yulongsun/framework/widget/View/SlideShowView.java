package win.yulongsun.framework.widget.View;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import win.yulongsun.framework.R;

/**
 * Created by sunyulong on 2016/11/24.
 */
//轮播图
public class SlideShowView extends FrameLayout {
    // 轮播图片数量
    private final static int IMAGE_COUNT = 5;
    // 自动轮播的时间间隔
    private final static int TIME_INTERVAL = 5;
    //是否自动播放
    private final boolean isAutoPlay = false;

    private ViewPager mVpSlideShow;
    private ScheduledExecutorService mScheduledExecutorService;

    public SlideShowView(Context context) {
        super(context);
    }

    public SlideShowView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SlideShowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initData(context);

        if (isAutoPlay) {
            startPlay();
        }
    }

    // 开始轮播切换
    private void startPlay() {
        mScheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        mScheduledExecutorService.scheduleAtFixedRate(new SlideShowTask(), 1, 4, TimeUnit.SECONDS);
    }

    // 停止轮播图
    private void stopPlay() {
        mScheduledExecutorService.shutdown();
    }


    // 初始化
    private void initData(Context context) {
        LayoutInflater mInflater = LayoutInflater.from(context);
        View mView = mInflater.inflate(R.layout.layout_slideshow, null);

        mVpSlideShow = (ViewPager) mView.findViewById(R.id.vp_slideshow);


        mVpSlideShow.setFocusable(false);
        mVpSlideShow.setAdapter(new MyPagerAdapter());
        mVpSlideShow.setOnPageChangeListener(new MyPageChangeListener());


    }


    //
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };


    // 执行轮播图片Task
    private class SlideShowTask implements Runnable {

        @Override
        public void run() {
            synchronized (mVpSlideShow) {

            }


        }
    }

    //ViewPager适配器
    private class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }
    }


    // ViewPager状态监听器
    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }


}
