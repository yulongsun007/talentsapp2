package win.yulongsun.talents.ui.hr.job;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.OnClick;
import win.yulongsun.talents.R;
import win.yulongsun.talents.base.BaseChildFragment;

/**
 * Create By : yulongsun
 * Date At : 16/8/30
 * Desc : 发布职位
 */
public class JobTempAddFragment extends BaseChildFragment {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.tv_job_temp_add_job_name)
    TextView mTvJobTempAddJobName;
    @Bind(R.id.tv_job_temp_add_start)
    TextView mTvJobTempAddStart;
    @Bind(R.id.tv_job_temp_add_end)
    TextView mTvJobTempAddEnd;
    @Bind(R.id.tv_job_temp_is_push)
    TextView mTvJobTempIsPush;

    public static JobTempAddFragment newInstance() {
        return new JobTempAddFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_job_temp_add;
    }

    @Override
    protected Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    protected String getToolbarTitle() {
        return "发布职位";
    }

    @OnClick({R.id.tv_job_temp_add_job_name, R.id.tv_job_temp_add_start, R.id.tv_job_temp_add_end, R.id.tv_job_temp_is_push})
    public void onClick(View view) {
        Date mDate = Calendar.getInstance().getTime();
        switch (view.getId()) {
            case R.id.tv_job_temp_add_job_name:
                break;
            case R.id.tv_job_temp_add_start:
                new DatePickerDialog(_mActivity, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        mTvJobTempAddStart.setText(year+"-"+monthOfYear+"-"+dayOfMonth);
                    }
                },mDate.getYear(),mDate.getMonth(),mDate.getDay()).show();
                break;
            case R.id.tv_job_temp_add_end:
                new DatePickerDialog(_mActivity, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        mTvJobTempAddEnd.setText(year+"-"+monthOfYear+"-"+dayOfMonth);
                    }
                },mDate.getYear(),mDate.getMonth(),mDate.getDay()).show();
                break;
            case R.id.tv_job_temp_is_push:
                String[] choiceStr={"是","否"};
                new AlertDialog.Builder(_mActivity)
                        .setSingleChoiceItems(choiceStr, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(0==which){
                                    mTvJobTempIsPush.setText("是");
                                }else {
                                    mTvJobTempIsPush.setText("否");
                                }
                            }
                        }).show();
                break;
        }
    }
}
