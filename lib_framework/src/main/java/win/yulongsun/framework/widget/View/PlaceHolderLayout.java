package win.yulongsun.framework.widget.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import win.yulongsun.framework.R;
import win.yulongsun.framework.util.DisplayUtil;
import win.yulongsun.framework.widget.ImageView.CircleImageView;

/**
 * Create By: yulongsun
 * Date at: 2016/8/15
 * Desc : 占位布局
 */
public class PlaceHolderLayout extends LinearLayout {
    private static final float IMG_SIZE = 120;

    public PlaceHolderLayout(Context context) {
        this(context, null);
    }

    public PlaceHolderLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlaceHolderLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    public PlaceHolderLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//        init(context, attrs);
//    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PlaceholderView);
        Drawable tipImg = typedArray.getDrawable(R.styleable.PlaceholderView_phv_tip_img);
        String tipText = typedArray.getString(R.styleable.PlaceholderView_phv_tip_text);
        String tipSubText = typedArray.getString(R.styleable.PlaceholderView_phv_tip_sub_text);


        //设置LinearLayout
        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        setGravity(Gravity.CENTER);
        setOrientation(VERTICAL);
        setLayoutParams(lp);

        CircleImageView logo   = new CircleImageView(context);
        LayoutParams    logoLp = new LayoutParams(DisplayUtil.dip2px(context, IMG_SIZE), DisplayUtil.dip2px(context, IMG_SIZE));
        logo.setLayoutParams(logoLp);
        int padSize = DisplayUtil.dip2px(context, 20);
        logo.setPadding(padSize, padSize, padSize, padSize);
        logo.setBackground(getResources().getDrawable(R.drawable.bg_shape_plache_holder));
        logo.setImageDrawable(tipImg);
        logo.setBorderWidth(0);

//        ImageView logo = new ImageView(context);
//        logo.setImageDrawable(tipImg);


        TextView tipView = new TextView(context);
        LayoutParams tipLp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tipLp.setMargins(0, DisplayUtil.dip2px(context, 20), 0, 0);
        tipView.setText(tipText);
        tipView.setTextSize(14);


        TextView tipSubView = new TextView(context);
        tipSubView.setTextSize(12);
        tipSubView.setText(tipSubText);
        tipSubView.setTextColor(Color.GRAY);


        addView(logo);
        addView(tipView, tipLp);
        addView(tipSubView);

        typedArray.recycle();

    }
}
