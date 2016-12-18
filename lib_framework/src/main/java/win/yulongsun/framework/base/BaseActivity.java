package win.yulongsun.framework.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import butterknife.ButterKnife;

/**
 * USER : yulongsun on 2016/4/11
 * NOTE :
 */
public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG = BaseActivity.class.getSimpleName();
    private ProgressDialog mProgDialog;
    private long beginMillis;
    private long endMillis;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layoutResId = getLayoutResId();
        setContentView(layoutResId);
        ButterKnife.bind(this);
        initViews();
        initListeners();
        initDatas();
    }

    protected void initViews() {
        if (mProgDialog == null) {
            mProgDialog = new ProgressDialog(this);
        }
        mProgDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgDialog.setIndeterminate(false);
        mProgDialog.setCancelable(false);
    }

    protected void initListeners() {

    }

    protected void initDatas() {

    }


    /*获取布局*/
    public abstract int getLayoutResId();


    /*显示对话框*/
    public void showLoading(String tip) {
        mProgDialog.setMessage(tip);
        beginMillis = System.currentTimeMillis();
        mProgDialog.show();
    }

    public void showLoading(String tip, boolean cancelable) {
        mProgDialog.setMessage(tip);
        beginMillis = System.currentTimeMillis();
        mProgDialog.setCancelable(true);
        mProgDialog.show();
    }

    /*隐藏对话框*/
    public void hideLoading() {
        if (mProgDialog != null) {
            endMillis = System.currentTimeMillis();
            long time = endMillis - beginMillis;
            Log.d(TAG, "hideLoading: time:" + time);
            mProgDialog.dismiss();
        }
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart");

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
