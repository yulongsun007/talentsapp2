package win.yulongsun.talents.ui.hr.job;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Call;
import win.yulongsun.framework.util.JsonUtil;
import win.yulongsun.framework.util.android.widget.ToastUtils;
import win.yulongsun.framework.util.java.lang.StringUtils;
import win.yulongsun.talents.R;
import win.yulongsun.talents.base.BaseChildFragment;
import win.yulongsun.talents.common.Constant;
import win.yulongsun.talents.entity.JobTemplate;
import win.yulongsun.talents.http.resp.ResponseList;
import win.yulongsun.talents.http.resp.biz.JobTemplateResponse;

/**
 * Create By : yulongsun
 * Date At : 16/8/30
 * Desc : 发布职位
 */
public class JobTempAddFragment extends BaseChildFragment {

    @Bind(R.id.toolbar)
    Toolbar  mToolbar;
    @Bind(R.id.tv_job_temp_add_job_name)
    TextView mTvJobTempAddJobName;
    @Bind(R.id.tv_job_temp_add_start)
    TextView mTvJobTempAddStart;
    @Bind(R.id.tv_job_temp_add_end)
    TextView mTvJobTempAddEnd;
    @Bind(R.id.tv_job_temp_is_push)
    TextView mTvJobTempIsPush;
    @Bind(R.id.btn_job_temp_add_deploy)
    Button   mBtnJobTempAddDeploy;

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

    @Override
    protected void initData() {
        super.initData();
        //2.从服务器加载数据
        JobTemplate jobTemplate = new JobTemplate();
        jobTemplate.create_by = _mUser.user_id;
        loadDataFromServer("job_temp/hrListUnDeploy", jobTemplate, JobTemplate.class, JobTemplateResponse.class);

    }

    @OnClick({R.id.ll_job_temp_add_job_name, R.id.ll_job_temp_add_start, R.id.ll_job_temp_add_end, R.id.tv_job_temp_is_push, R.id.btn_job_temp_add_deploy})
    public void onClick(View view) {
        Date mDate = Calendar.getInstance().getTime();
        switch (view.getId()) {
            case R.id.ll_job_temp_add_job_name:
                List<JobTemplate> mDatas = (List<JobTemplate>) _mDatas;
                final String[] temps = new String[mDatas.size()];
                for (int i = 0; i < mDatas.size(); i++) {
                    JobTemplate template = mDatas.get(i);
                    temps[i] = template.tmp_id + "-" + template.tmp_job_name;
                }
                new AlertDialog.Builder(_mActivity)
                        .setSingleChoiceItems(temps, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mTvJobTempAddJobName.setText(temps[which]);
                                dialog.dismiss();
                            }
                        }).show();
                break;
            case R.id.ll_job_temp_add_start:
                new DatePickerDialog(_mActivity, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        mTvJobTempAddStart.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
                    }
                }, mDate.getYear(), mDate.getMonth(), mDate.getDay()).show();
                break;
            case R.id.ll_job_temp_add_end:
                new DatePickerDialog(_mActivity, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        mTvJobTempAddEnd.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
                    }
                }, mDate.getYear(), mDate.getMonth(), mDate.getDay()).show();
                break;
            case R.id.tv_job_temp_is_push:
                String[] choiceStr = {"是", "否"};
                new AlertDialog.Builder(_mActivity)
                        .setSingleChoiceItems(choiceStr, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (0 == which) {
                                    mTvJobTempIsPush.setText("是");
                                } else {
                                    mTvJobTempIsPush.setText("否");
                                }
                            }
                        }).show();
                break;
            case R.id.btn_job_temp_add_deploy:
                //发布
                toDeploy();
                break;
        }
    }

    private void toDeploy() {
        String job_name = mTvJobTempAddJobName.getText().toString();
        String tmp_id = job_name.split("-")[0];
        if (StringUtils.isEmpty(job_name) || StringUtils.isEmpty(tmp_id)) {
            ToastUtils.toastL(_mActivity, "请选择招聘模板");
            return;
        }
        String start_at = mTvJobTempAddStart.getText().toString();
        if (StringUtils.isEmpty(start_at)) {
            ToastUtils.toastL(_mActivity, "请选择开始时间");
            return;
        }
        String end_at = mTvJobTempAddEnd.getText().toString();
        if (StringUtils.isEmpty(end_at)) {
            ToastUtils.toastL(_mActivity, "请选择结束时间");
            return;
        }
        OkHttpUtils.post()
                .url(Constant.URL + "job_temp/deploy")
                .addParams("tmp_id", tmp_id)
                .addParams("start_at", start_at)
                .addParams("end_at", end_at)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        ResponseList resp = (ResponseList) JsonUtil.fromJson(response, ResponseList.class);
                        ToastUtils.toastL(_mActivity, resp.msg);
                        if (resp.code == Constant.CODE.SUCCESS) {
                            pop();
                        }
                    }
                });

    }
}
