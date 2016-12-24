package win.yulongsun.talents.ui.hr.job;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.OnClick;
import win.yulongsun.talents.R;
import win.yulongsun.talents.base.BaseSwipeBackFragment;
import win.yulongsun.talents.common.Constant;
import win.yulongsun.talents.entity.JobTemplate;
import win.yulongsun.talents.event.StartBrotherEvent;
import win.yulongsun.talents.ui.referrer.plan.PlanEditFragment;

/**
 * @author sunyulong on 2016/12/16.
 *         招聘详情
 */
public class JobTempDetailFragment extends BaseSwipeBackFragment {
    @Bind(R.id.toolbar)
    Toolbar   mToolbar;
    @Bind(R.id.btn_job_temp_add_plan)
    Button    mBtnJobTempAddPlan;
    @Bind(R.id.tv_job_temp_detail_job_name)
    TextView  mTvJobTempDetailJobName;
    @Bind(R.id.tv_job_temp_detail_job_salary)
    TextView  mTvJobTempDetailJobSalary;
    @Bind(R.id.tv_job_temp_detail_job_depart)
    TextView  mTvJobTempDetailJobDepart;
    @Bind(R.id.tv_job_temp_detail_job_exper_year)
    TextView  mTvJobTempDetailJobExperYear;
    @Bind(R.id.tv_job_temp_detail_job_edu_req)
    TextView  mTvJobTempDetailJobEduReq;
    @Bind(R.id.tv_job_temp_detail_job_desc)
    TextView  mTvJobTempDetailJobDesc;
    @Bind(R.id.tv_job_temp_detail_job_addr)
    TextView  mTvJobTempDetailJobAddr;
    @Bind(R.id.tv_job_temp_detail_hr_company_logo)
    ImageView mTvJobTempDetailHrCompanyLogo;
    @Bind(R.id.tv_job_temp_detail_hr_name)
    TextView  mTvJobTempDetailHrName;
    @Bind(R.id.tv_job_temp_detail_hr_company_name)
    TextView  mTvJobTempDetailHrCompanyName;
    @Bind(R.id.tv_job_temp_detail_job_num)
    TextView  mTvJobTempDetailJobNum;
    private JobTemplate jobTemplate;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_job_temp_detail;
    }

    @Override
    protected Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    protected String getToolbarTitle() {
        return "职位详情";
    }

    public static final String JOB_TEMP_DETAIL_KEY = "job_temp_detail_key";

    public static JobTempDetailFragment newInstance(JobTemplate jobTemplate) {
        JobTempDetailFragment fragment = new JobTempDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(JOB_TEMP_DETAIL_KEY, jobTemplate);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initData() {
        super.initData();
        jobTemplate = (JobTemplate) getArguments().getSerializable(JOB_TEMP_DETAIL_KEY);
        if (_User.user_role_id == 1) {
            mBtnJobTempAddPlan.setVisibility(View.GONE);
        }
        if (jobTemplate == null) {
            return;
        }
        //init
        mTvJobTempDetailJobName.setText(jobTemplate.tmp_job_name);
        mTvJobTempDetailJobSalary.setText("【" + jobTemplate.tmp_job_salary + "】");
        mTvJobTempDetailJobDepart.setText(jobTemplate.tmp_job_depart);
        mTvJobTempDetailJobExperYear.setText(jobTemplate.tmp_job_exper_year + "");
        mTvJobTempDetailJobEduReq.setText(jobTemplate.tmp_job_edu_require + "");
        mTvJobTempDetailJobNum.setText(jobTemplate.tmp_job_num + "人");
        String desc = "";
        desc += "行业方向：" + jobTemplate.tmp_job_biz_direct + "\n";
        desc += "技能要求：" + jobTemplate.tmp_job_skill_require + "\n";
        desc += "职位描述：" + jobTemplate.tmp_job_desc;
        mTvJobTempDetailJobDesc.setText(desc);
        mTvJobTempDetailJobAddr.setText(jobTemplate.tmp_job_addr);
        mTvJobTempDetailHrName.setText(_User.user_name);
        mTvJobTempDetailHrCompanyName.setText(_User.company_name);
    }

    @OnClick(R.id.btn_job_temp_add_plan)
    public void onClick() {
        EventBus.getDefault().post(new StartBrotherEvent(PlanEditFragment.newInstance(Constant.MODE_VALUE.ADD, null,jobTemplate)));
    }

}
