package win.yulongsun.talents.ui.me.child;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.OnClick;
import win.yulongsun.talents.base.BaseSwipeBackFragment;
import win.yulongsun.talents.R;

/**
 * Create By: yulongsun
 * Date at: 2016/8/29
 * Desc : 招聘模版
 */
public class JobTempFragment extends BaseSwipeBackFragment {

    @Bind(R.id.tv_job_temp_job_name)
    TextView mTvJobTempJobName;
    @Bind(R.id.tv_job_temp_edu_req)
    TextView mTVJobTempEduReq;
    @Bind(R.id.toolbar)
    Toolbar  mToolbar;
    @Bind(R.id.tv_job_temp_job_exp)
    TextView mTvJobTempJobExp;
    @Bind(R.id.tv_job_temp_job_salary)
    TextView mTvJobTempJobSalary;

    public static JobTempFragment newInstance() {
        return new JobTempFragment();
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_job_temp;
    }

    @Override
    protected Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    protected String getToolbarTitle() {
        return "招聘模版";
    }

    @Override
    protected int getMenuResId() {
        return R.menu.menu_job_temp_done;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_job_temp_done) {
            pop();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);

    }

    @Override
    protected void initView() {
        super.initView();
        getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(_mActivity).setMessage("您确定要退出编辑模板吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                pop();
                            }
                        }).setNegativeButton("取消", null).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @OnClick({R.id.ll_job_temp_edu_req, R.id.ll_job_temp_job_direct, R.id.ll_job_temp_job_name, R.id.ll_job_temp_skill_req, R.id.ll_job_temp_job_exp, R.id.ll_job_temp_job_salary, R.id.ll_job_temp_job_addr, R.id.et_job_temp_job_desc})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_job_temp_job_direct://
                startForResult(ChooseBizDirectFragment.newInstance(), 0x001);
                break;
            case R.id.ll_job_temp_job_name:
                //加载布局
                View dialogView = LayoutInflater.from(_mActivity).inflate(R.layout.common_dialog_edittext, null);
                final EditText etDiaglogJobName = (EditText) dialogView.findViewById(R.id.et_dialog_job_name);
                //设置原值
                String oldValue = mTvJobTempJobName.getText().toString();
                if ("必选".equals(oldValue)) {
                    etDiaglogJobName.setText("");
                } else {
                    etDiaglogJobName.setText(oldValue);
                }
                //修改值
                new AlertDialog.Builder(_mActivity).setMessage("输入职位名称")
                        .setView(dialogView)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String jobName = etDiaglogJobName.getText().toString();
                                mTvJobTempJobName.setText(jobName);
                                mTvJobTempJobName.setTextColor(getResources().getColor(R.color.color_accent));
                            }
                        }).setNegativeButton("取消", null).show();

                break;
            case R.id.ll_job_temp_skill_req://技能要求
                break;
            case R.id.ll_job_temp_edu_req://学历要求
                final String[] eduLevelArray = new String[]{"不限", "大专", "本科", "硕士及以上"};
                String eduReqStr = mTVJobTempEduReq.getText().toString();
                int checkItem = 0;
                if ("不限".equals(eduReqStr)) {
                    checkItem = 0;
                } else if ("大专".equals(eduReqStr)) {
                    checkItem = 1;
                } else if ("本科".equals(eduReqStr)) {
                    checkItem = 2;
                } else if ("硕士及以上".equals(eduReqStr)) {
                    checkItem = 3;
                }
                new AlertDialog.Builder(_mActivity).setSingleChoiceItems(eduLevelArray, checkItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mTVJobTempEduReq.setText(eduLevelArray[i]);
                        mTVJobTempEduReq.setTextColor(getResources().getColor(R.color.color_accent));
                    }
                }).setPositiveButton("确定", null).show();
                break;
            case R.id.ll_job_temp_job_exp://工作经验
                final String[] jobExpArray = new String[]{"不限", "1-3年", "3-5年", "7年以上"};
                String jobExpReqStr = mTvJobTempJobExp.getText().toString();
                int jobExpCheckItem = 0;
                if ("不限".equals(jobExpReqStr)) {
                    jobExpCheckItem = 0;
                } else if ("1-3年".equals(jobExpReqStr)) {
                    jobExpCheckItem = 1;
                } else if ("3-5年".equals(jobExpReqStr)) {
                    jobExpCheckItem = 2;
                } else if ("7年以上".equals(jobExpReqStr)) {
                    jobExpCheckItem = 3;
                }
                new AlertDialog.Builder(_mActivity).setSingleChoiceItems(jobExpArray, jobExpCheckItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mTvJobTempJobExp.setText(jobExpArray[i]);
                        mTvJobTempJobExp.setTextColor(getResources().getColor(R.color.color_accent));
                    }
                }).setPositiveButton("确定", null).show();
                break;
            case R.id.ll_job_temp_job_salary://薪资
                final String[] jobSalaryArray = new String[]{"不限", "3000-5000元/月", "5000-8000元/月", "8000元/月以上"};
                String jobSalaryStr = mTvJobTempJobSalary.getText().toString();
                int jobSalaryCheckItem = 0;
                if ("不限".equals(jobSalaryStr)) {
                    jobSalaryCheckItem = 0;
                } else if ("1-3年".equals(jobSalaryStr)) {
                    jobSalaryCheckItem = 1;
                } else if ("3-5年".equals(jobSalaryStr)) {
                    jobSalaryCheckItem = 2;
                } else if ("7年以上".equals(jobSalaryStr)) {
                    jobSalaryCheckItem = 3;
                }
                new AlertDialog.Builder(_mActivity).setSingleChoiceItems(jobSalaryArray, jobSalaryCheckItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mTvJobTempJobSalary.setText(jobSalaryArray[i]);
                        mTvJobTempJobSalary.setTextColor(getResources().getColor(R.color.color_accent));
                    }
                }).setPositiveButton("确定", null).show();
                break;
            case R.id.ll_job_temp_job_addr://工作地址
                break;
            case R.id.et_job_temp_job_desc://工作描述
                break;
        }
    }

}
