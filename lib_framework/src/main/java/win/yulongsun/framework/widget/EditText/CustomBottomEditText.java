package win.yulongsun.framework.widget.EditText;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import win.yulongsun.framework.R;
import win.yulongsun.framework.util.DisplayUtil;
import win.yulongsun.framework.util.ImeUtil;

/**
 * Create By: yulongsun
 * Date at: 2016/8/15
 * Desc : 底部输入框
 */
public class CustomBottomEditText extends LinearLayout {
    private boolean flag = false;

    public CustomBottomEditText(Context context) {
        this(context, null);
    }

    public CustomBottomEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomBottomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    //加了下面这句4.4会报错
//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//
//    }

    private void init(final Context context) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setOrientation(LinearLayout.HORIZONTAL);
        setLayoutParams(layoutParams);
        setGravity(Gravity.CENTER_VERTICAL);
        int paddingLeftRight = DisplayUtil.dip2px(context, 12);
        int paddingTopBottom = DisplayUtil.dip2px(context, 7);
        setPadding(paddingLeftRight, paddingTopBottom, paddingLeftRight, paddingTopBottom);
        setBackgroundResource(R.drawable.bg_shape_keyboard);


        ViewGroup.LayoutParams lpWidget = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        final ImageView leftIcon = new ImageView(context);
        leftIcon.setImageResource(R.drawable.ic_assignment_black_24dp);
        final ImageView rightIcon = new ImageView(context);
        rightIcon.setImageResource(R.drawable.ic_mic_black_24dp);


        LayoutParams lpContainer = new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        lpContainer.weight = 1;
        int margin = DisplayUtil.dip2px(context, 10);
        lpContainer.setMargins(margin, 0, margin, 0);


        final EditText etInput = new EditText(context);
        etInput.setTextSize(14);
        etInput.setBackground(null);
        etInput.setHint("准备做什么?");
        etInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    rightIcon.setImageResource(R.drawable.ic_mic_black_24dp);
                } else {
                    rightIcon.setImageResource(R.drawable.ic_send_black_24dp);
                }
            }
        });

        final Button btnSay = new Button(context);
        btnSay.setText("按住说话");
        btnSay.setTextSize(12);
        btnSay.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        btnSay.setVisibility(GONE);
        btnSay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickListener != null) {
                    mOnClickListener.onClick(v);
                }
            }
        });

        //addView
        addView(leftIcon, lpWidget);
        addView(btnSay, lpContainer);
        addView(etInput, lpContainer);
        addView(rightIcon, lpWidget);


        leftIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        rightIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag) {
                    leftIcon.setVisibility(VISIBLE);
                    etInput.setVisibility(VISIBLE);
                    btnSay.setVisibility(GONE);
                    rightIcon.setImageResource(etInput.getText().length() == 0 ? R.drawable.ic_mic_black_24dp : R.drawable.ic_send_black_24dp);
                    flag = false;
                    ImeUtil.showSoftKeyboard(CustomBottomEditText.this);
                } else {
                    //显示 “按住说话”
                    leftIcon.setVisibility(GONE);
                    etInput.setVisibility(GONE);
                    btnSay.setVisibility(VISIBLE);
                    rightIcon.setImageResource(R.drawable.ic_keyboard_hide_black_24dp);
                    flag = true;
                    ImeUtil.hideSoftKeyboard(CustomBottomEditText.this);
                }

            }
        });
    }


    //----------------------------------------
    // Interface
    //----------------------------------------
    interface OnClickListener {
        void onClick(View view);
    }

    OnClickListener mOnClickListener;

    public void setOnBtnClickListener(OnClickListener listener) {
        mOnClickListener = listener;
    }



}
