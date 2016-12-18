package win.yulongsun.framework.widget.Progressbar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Create By: yulongsun
 * Date at: 2016/9/6
 * Desc : 环形进度条
 *
 * https://github.com/hellsam/DoughnutDemo_Android/blob/master/app/src/main/java/com/hellsam/doughnutdemo/DoughnutView.java
 */
public class CircularProgressBarView extends View {

    private Paint mPaint = new Paint();

    public CircularProgressBarView(Context context) {
        super(context);
    }

    public CircularProgressBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircularProgressBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        resetPaint();

    }

    private void resetPaint() {

        mPaint.reset();
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measure(widthMeasureSpec), measure(heightMeasureSpec));
    }

    private int measure(int origin) {

        return 0;
    }


}
