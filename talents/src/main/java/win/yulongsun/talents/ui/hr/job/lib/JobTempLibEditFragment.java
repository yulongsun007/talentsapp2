package win.yulongsun.talents.ui.hr.job.lib;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.OnClick;
import win.yulongsun.talents.base.BaseChildFragment;
import win.yulongsun.talents.common.Constant;
import win.yulongsun.talents.R;

/**
 * @author sunyulong on 2016/12/2.
 *         新增/编辑 招聘模板
 */
public class JobTempLibEditFragment extends BaseChildFragment {
    @Bind(R.id.toolbar)
    Toolbar      mToolbar;
    @Bind(R.id.ll_job_temp_job_direct)
    LinearLayout mLlJobTempJobDirect;
    @Bind(R.id.et_job_temp_job_name)
    EditText     mEtJobTempJobName;
    @Bind(R.id.ll_job_temp_job_name)
    LinearLayout mLlJobTempJobName;
    @Bind(R.id.et_job_temp_job_req)
    EditText     mEtJobTempJobReq;
    @Bind(R.id.ll_job_temp_skill_req)
    LinearLayout mLlJobTempSkillReq;
    @Bind(R.id.tv_job_temp_edu_req)
    TextView     mTvJobTempEduReq;
    @Bind(R.id.ll_job_temp_edu_req)
    LinearLayout mLlJobTempEduReq;
    @Bind(R.id.tv_job_temp_job_exp)
    TextView     mTvJobTempJobExp;
    @Bind(R.id.ll_job_temp_job_exp)
    LinearLayout mLlJobTempJobExp;
    @Bind(R.id.tv_job_temp_job_salary)
    TextView     mTvJobTempJobSalary;
    @Bind(R.id.ll_job_temp_job_salary)
    LinearLayout mLlJobTempJobSalary;
    @Bind(R.id.et_job_temp_job_addr)
    EditText     mEtJobTempJobAddr;
    @Bind(R.id.et_job_temp_job_desc)
    EditText     mEtJobTempJobDesc;
    @Bind(R.id.tv_job_temp_job_direct)
    TextView     mTvJobTempJobDirect;
    /*编辑模式*/
    private int mMode = 0;

    public static JobTempLibEditFragment newInstance() {
        return new JobTempLibEditFragment();
    }


    public static JobTempLibEditFragment newInstance(int mode) {
        JobTempLibEditFragment mFragment = new JobTempLibEditFragment();
        Bundle args = new Bundle();
        args.putInt(Constant.MODE_NAME, mode);
        mFragment.setArguments(args);
        return mFragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_job_temp_lib_edit;
    }

    @Override
    protected Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    protected int getMenuResId() {
        return R.menu.menu_common_add;
    }

    @Override
    protected String getToolbarTitle() {
        String type = "";
        switch (mMode) {
            case Constant.MODE_VALUE.ADD:
                type = "新增";
                break;
            case Constant.MODE_VALUE.EDIT:
                type = "编辑";
                break;
        }
        return type + "招聘模板";

    }


    @Override
    protected void initView() {
        super.initView();
        mMode = (int) getArguments().get(Constant.MODE_NAME);

        //设置返回键Logo
        getToolbar().setNavigationIcon(R.drawable.ic_menu_cancel);

    }

    @OnClick({R.id.ll_job_temp_job_direct, R.id.ll_job_temp_edu_req, R.id.ll_job_temp_job_exp, R.id.ll_job_temp_job_salary})
    public void onClick(View view) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(_mActivity);
        switch (view.getId()) {
            case R.id.ll_job_temp_job_direct:
                final String[] jobDirect = {"互联网", "金融", "文化/传媒", "服务业", "教育培训", "通讯电子", "房产建筑", "轻工贸易", "医疗生物", "政府法律", "化工能源", "农林牧鱼"};
                mBuilder.setSingleChoiceItems(jobDirect, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mTvJobTempJobDirect.setText(jobDirect[which]);
                    }
                }).show();
                break;
            case R.id.ll_job_temp_edu_req:
                final String[] eduReq = {"不限", "本科及以上", "硕士及以上"};
                mBuilder.setSingleChoiceItems(eduReq, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mTvJobTempEduReq.setText(eduReq[which]);
                    }
                }).show();
                break;
            case R.id.ll_job_temp_job_exp:
                final String[] jobReq = {"一年以下", "1-3年", "3-5年", "5-10年"};
                mBuilder.setSingleChoiceItems(jobReq, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mTvJobTempJobExp.setText(jobReq[which]);
                    }
                }).show();
                break;
            case R.id.ll_job_temp_job_salary:
                final String[] jobSalary = {"3000及以下/月", "3000-7000/月", "7000-150000/月", "15000以上/月"};
                mBuilder.setSingleChoiceItems(jobSalary, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mTvJobTempJobSalary.setText(jobSalary[which]);
                    }
                }).show();
                break;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int mItemId = item.getItemId();
        if (mItemId == R.id.action_common_add) {
            Toast.makeText(_mActivity, "post", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onBackPressedSupport() {
        new AlertDialog.Builder(_mActivity)
                .setTitle("提示")
                .setMessage("确定放弃当前模板吗？")
                .setPositiveButton("放弃", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        pop();
                    }
                })
                .setNegativeButton("继续编辑", null)
                .show();
        return true;
    }
}
