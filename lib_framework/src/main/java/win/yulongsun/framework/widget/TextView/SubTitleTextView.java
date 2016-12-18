package win.yulongsun.framework.widget.TextView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import win.yulongsun.framework.R;
import win.yulongsun.framework.util.DisplayUtil;

/**
 * Create By: yulongsun
 * Date at: 2016/8/18
 * Desc : 带子标题的TextView
 * 水波纹效果：
 * http://blog.csdn.net/singwhatiwanna/article/details/42614953
 */
public class SubTitleTextView extends LinearLayout implements Runnable {
    private int INVALIDATE_DURATION = 25;
    private View  mTouchTarget;
    private float mCenterX;
    private float mCenterY;
    private int   mTargetWidth;
    private int   mTargetHeight;
    private boolean mShouldDoAnimation = false;
    private boolean mIsPressed         = false;
    private int mMinBetweenWidthAndHeight;
    private int mMaxBetweenWidthAndHeight;
    private int mRevealRadius = 0;
    private int mRevealRadiusGap;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int mMaxRevealRadius;
    private DispatchUpTouchEventRunnable mDispatchUpTouchEventRunnable = new DispatchUpTouchEventRunnable();
    private int[]                        mLocationInScreen             = new int[2];

    public SubTitleTextView(Context context) {
        this(context, null);
    }

    public SubTitleTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SubTitleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //设置LinearLayout
//        int padding = 16;
        int padding = DisplayUtil.dip2px(context, 16);
        setPadding(padding, padding, padding, padding);
        setOrientation(VERTICAL);
        setBackgroundColor(Color.WHITE);


        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SubTextView);

        String titleStr = typedArray.getString(R.styleable.SubTextView_stv_title);
        String subTitleStr = typedArray.getString(R.styleable.SubTextView_stv_sub_title);


        typedArray.recycle();
        //设置Title
        TextView title = new TextView(context);
        title.setText(titleStr);
        title.setTextSize(16);
        title.setTextColor(Color.BLACK);
        addView(title);
        //设置SubTitle
        TextView subTitle = new TextView(context);
        subTitle.setText(subTitleStr);
        subTitle.setTextSize(12);
        subTitle.setTextColor(getResources().getColor(R.color.color_text_secondary));
        addView(subTitle);
        //
        isInEditMode();
        setClickable(true);
        initPaint();
    }


    private void initPaint() {
        // TODO: 2016/8/19 ???
        setWillNotDraw(false);

        mPaint.setColor(getResources().getColor(R.color.color_reveal));

    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        getLocationOnScreen(mLocationInScreen);
    }

    //拦截点击事件
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        /**getRawX getRawY和getX getY的区别：
         *
         * getX：相对被点击View的坐标
         * getRowX：相对屏幕的坐标
         * */
        int x = (int) ev.getRawX();
        int y = (int) ev.getRawY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //判断事件坐标是否落在View的范围内
                View touchTarget = getTouchTarget(this, x, y);
                if (touchTarget.isClickable() && touchTarget.isEnabled()) {
                    mTouchTarget = touchTarget;
                    initParamForChild(ev, touchTarget);
                    postInvalidateDelayed(INVALIDATE_DURATION);
                }
                break;
            case MotionEvent.ACTION_UP:
                mIsPressed = false;
                postInvalidateDelayed(INVALIDATE_DURATION);
                mDispatchUpTouchEventRunnable.event = ev;
                postDelayed(mDispatchUpTouchEventRunnable, 40);
                return true;
            case MotionEvent.ACTION_CANCEL:
                mIsPressed = false;
                postInvalidateDelayed(INVALIDATE_DURATION);
                break;
        }

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean performClick() {
        postDelayed(this, 400);
        return true;
    }

    @Override
    public void run() {
        super.performClick();
    }


    class DispatchUpTouchEventRunnable implements Runnable {
        public MotionEvent event;

        @Override
        public void run() {
            if (mTouchTarget == null || !mTouchTarget.isEnabled()) {
                return;
            }

            if (isTouchPointInView(mTouchTarget, (int) event.getRawX(), (int) event.getRawY())) {
                mTouchTarget.performClick();
            }

        }
    }

    /**
     * 初始化Child参数
     *
     * @param ev
     * @param touchTarget
     */
    private void initParamForChild(MotionEvent ev, View touchTarget) {
        //相对被点击View的坐标
        mCenterX = ev.getX();
        mCenterY = ev.getY();

        mTargetWidth = touchTarget.getMeasuredWidth();
        mTargetHeight = touchTarget.getMeasuredHeight();

        mMinBetweenWidthAndHeight = Math.min(mTargetWidth, mTargetHeight);
        mMaxBetweenWidthAndHeight = Math.max(mTargetWidth, mTargetHeight);


        mRevealRadius = 0;
        mShouldDoAnimation = true;
        mRevealRadiusGap = mMinBetweenWidthAndHeight / 8;

        int[] childLocation = new int[2];
        touchTarget.getLocationOnScreen(childLocation);
        /**
         * mLocationInScreen[0]: LinearLayout的x坐标
         * location[0]: 子控件的x坐标
         */
        int left = childLocation[0] - mLocationInScreen[0];
        int transformedCenterX = (int) mCenterX - left;
        // TODO: 2016/8/19  mMaxRevealRadius
        mMaxRevealRadius = Math.max(transformedCenterX, mTargetWidth - transformedCenterX);
    }

    /**
     * View 绘制顺序
     * 先绘制背景，再绘制自己（onDraw），接着绘制子元素（dispatchDraw），最后绘制一些装饰等比如滚动条（onDrawScrollBars）
     *
     * @param canvas
     */
    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (!mShouldDoAnimation || mTargetWidth <= 0 || mTouchTarget == null) {
            return;
        }
        if (mRevealRadius > mMinBetweenWidthAndHeight / 2) {
            mRevealRadius += mRevealRadiusGap * 4;
        } else {
            mRevealRadius += mRevealRadiusGap;
        }

        this.getLocationOnScreen(mLocationInScreen);
        int[] location = new int[2];
        mTouchTarget.getLocationOnScreen(location);
        int left = location[0] - mLocationInScreen[0];
        int top = location[1] - mLocationInScreen[1];
        int right = left + mTouchTarget.getMeasuredWidth();
        int bottom = top + mTouchTarget.getMeasuredHeight();

        canvas.save();
        canvas.clipRect(left, top, right, bottom);
        canvas.drawCircle(mCenterX, mCenterY, mRevealRadius, mPaint);
        canvas.restore();

        if (mRevealRadius <= mMaxRevealRadius) {
            postInvalidateDelayed(INVALIDATE_DURATION, left, top, right, bottom);
        } else if (!mIsPressed) {
            mShouldDoAnimation = false;
            postInvalidateDelayed(INVALIDATE_DURATION, left, top, right, bottom);
        }
    }

    private View getTouchTarget(View view, int x, int y) {
        View target = null;
        ArrayList<View> touchableViews = view.getTouchables();
        for (View child : touchableViews) {
            //判断事件坐标是否落在View的范围内
            if (isTouchPointInView(child, x, y)) {
                target = child;
                break;
            }
        }
        return target;

    }

    /**
     * 如何取得被点击元素的信息 :判断事件坐标是否落在View的范围内
     *
     * @param child 控件View
     * @param x     触点X
     * @param y     触点Y
     * @return 在控件范围内返回true 否则false
     */
    private boolean isTouchPointInView(View child, int x, int y) {
        int[] location = new int[2];
        child.getLocationOnScreen(location);

        int left = location[0];
        int top = location[1];
        int right = left + child.getMeasuredWidth();
        int bottom = top + child.getMeasuredHeight();

        if (child.isClickable() && y >= top && y <= bottom && x >= left && x <= right) {
            return true;
        }

        return false;
    }
}
