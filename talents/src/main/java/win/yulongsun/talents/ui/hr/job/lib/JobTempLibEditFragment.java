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

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

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
import win.yulongsun.talents.http.resp.biz.JobTemplateResponse;

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
    @Bind(R.id.et_job_temp_job_skill_req)
    EditText     mEtJobTempJobSkillReq;
    @Bind(R.id.tv_job_temp_edu_req)
    TextView     mTvJobTempEduReq;
    @Bind(R.id.ll_job_temp_edu_req)
    LinearLayout mLlJobTempEduReq;
    @Bind(R.id.tv_job_temp_exper_year)
    TextView     mTvJobTempExperYear;
    @Bind(R.id.ll_job_temp_exper_year)
    LinearLayout mLlJobTempExperYear;
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
    @Bind(R.id.et_job_temp_job_num)
    EditText     mEtJobTempJobNum;
    @Bind(R.id.et_job_temp_job_depart)
    EditText     mEtJobTempJobDepart;
    @Bind(R.id.ll_job_temp_job_num)
    LinearLayout mLlJobTempJobNum;
    /*编辑模式*/
    private int mMode = Constant.MODE_VALUE.EDIT;
    private String job_biz_direct;
    private String job_name;
    private String job_depart;
    private String job_num;
    private String job_edu_require;
    private String job_exper_year;
    private String job_salary;
    private String job_addr;
    private String job_skill_require;
    private String job_desc;

    public static final String  JOB_TEMPLATE_KEY = "job_key";
    private             Integer tmpId            = 0;

    public static JobTempLibEditFragment newInstance() {
        return new JobTempLibEditFragment();
    }


    public static JobTempLibEditFragment newInstance(int mode, JobTemplate jobTemplate) {
        JobTempLibEditFragment mFragment = new JobTempLibEditFragment();
        Bundle args = new Bundle();
        args.putInt(Constant.MODE_NAME, mode);
        if (mode == Constant.MODE_VALUE.EDIT) {
            args.putSerializable(JOB_TEMPLATE_KEY, jobTemplate);
        }
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
        int menuResId = 0;
        switch (mMode) {
            case Constant.MODE_VALUE.ADD:
                menuResId = R.menu.menu_common_add;
                break;
            case Constant.MODE_VALUE.EDIT:
                menuResId = R.menu.menu_common_save;
                break;
        }
        return menuResId;

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
        Bundle bundle = getArguments();
        mMode = (int) bundle.get(Constant.MODE_NAME);
        super.initView();

        //设置返回键Logo
        getToolbar().setNavigationIcon(R.drawable.ic_menu_cancel);

        //如果是编辑模式，则初始化View
        JobTemplate job = (JobTemplate) bundle.getSerializable(JOB_TEMPLATE_KEY);
        if (mMode == Constant.MODE_VALUE.EDIT && job != null) {
            tmpId = job.tmp_id;
            mTvJobTempJobDirect.setText(job.tmp_job_biz_direct);
            mEtJobTempJobName.setText(job.tmp_job_name);
            mEtJobTempJobDepart.setText(job.tmp_job_depart);
            mEtJobTempJobNum.setText(String.valueOf(job.tmp_job_num));
            mTvJobTempEduReq.setText(job.tmp_job_edu_require);
            mTvJobTempExperYear.setText(job.tmp_job_exper_year);
            mTvJobTempJobSalary.setText(job.tmp_job_salary);
            mEtJobTempJobAddr.setText(job.tmp_job_addr);
            mEtJobTempJobDesc.setText(job.tmp_job_desc);
            mEtJobTempJobSkillReq.setText(job.tmp_job_skill_require);
        }

    }

    @OnClick({R.id.ll_job_temp_job_direct, R.id.ll_job_temp_edu_req, R.id.ll_job_temp_exper_year, R.id.ll_job_temp_job_salary})
    public void onClick(View view) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(_mActivity);
        switch (view.getId()) {
            case R.id.ll_job_temp_job_direct:
                final String[] jobDirect = {"互联网", "金融", "文化/传媒", "服务业", "教育培训", "通讯电子", "房产建筑", "轻工贸易", "医疗生物", "政府法律", "化工能源", "农林牧鱼"};
                mBuilder.setSingleChoiceItems(jobDirect, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mTvJobTempJobDirect.setText(jobDirect[which]);
                        dialog.dismiss();
                    }
                });
                break;
            case R.id.ll_job_temp_edu_req:
                final String[] eduReq = {"不限", "本科及以上", "硕士及以上"};
                mBuilder.setSingleChoiceItems(eduReq, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mTvJobTempEduReq.setText(eduReq[which]);
                        dialog.dismiss();
                    }
                });
                break;
            case R.id.ll_job_temp_exper_year:
                final String[] jobReq = {"一年以下", "1-3年", "3-5年", "5-10年"};
                mBuilder.setSingleChoiceItems(jobReq, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mTvJobTempExperYear.setText(jobReq[which]);
                        dialog.dismiss();
                    }
                });
                break;
            case R.id.ll_job_temp_job_salary:
                final String[] jobSalary = {"3000及以下/月", "3000-7000/月", "7000-150000/月", "15000以上/月"};
                mBuilder.setSingleChoiceItems(jobSalary, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mTvJobTempJobSalary.setText(jobSalary[which]);
                        dialog.dismiss();
                    }
                });
                break;
        }
        mBuilder.show();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int mItemId = item.getItemId();
        if (mItemId == R.id.action_common_add) {
            toEditJobTemp("job_temp/add");
        }
        if (mItemId == R.id.action_common_save) {
            toEditJobTemp("job_temp/update");
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


    //添加or编辑一个招聘模板
    private void toEditJobTemp(String url) {
        boolean pass = checkData();
        if (!pass) {
            return;
        }
        PostFormBuilder builder = OkHttpUtils.post().url(Constant.URL + url);
        if (tmpId != null && tmpId != 0) {
            builder.addParams("tmp_id", String.valueOf(tmpId));
        }
        builder.addParams("tmp_job_biz_direct", job_biz_direct)
                .addParams("tmp_job_name", job_name)
                .addParams("tmp_job_depart", job_depart)
                .addParams("tmp_job_num", job_num)
                .addParams("tmp_job_skill_require", job_skill_require)
                .addParams("tmp_job_edu_require", job_edu_require)
                .addParams("tmp_job_exper_year", job_exper_year)
                .addParams("tmp_job_addr", job_addr)
                .addParams("tmp_job_salary", job_salary)
                .addParams("tmp_job_desc", job_desc)
                .addParams("create_by", String.valueOf(_User.user_id))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        JobTemplateResponse resp = (JobTemplateResponse) JsonUtil.fromJson(response, JobTemplateResponse.class);
                        ToastUtils.toastL(_mActivity, resp.msg);
                        if (resp.code == Constant.CODE.SUCCESS) {
                            pop();
                        }
                    }
                });

    }

    //检查数据的合法性
    private boolean checkData() {
        job_biz_direct = mTvJobTempJobDirect.getText().toString();
        if (StringUtils.isEmpty(job_biz_direct)) {
            ToastUtils.toastL(_mActivity, "行业方向不能为空");
            return false;
        }
        job_name = mEtJobTempJobName.getText().toString();
        if (StringUtils.isEmpty(job_name)) {
            ToastUtils.toastL(_mActivity, "招聘职位不能为空");
            return false;
        }
        job_depart = mEtJobTempJobDepart.getText().toString();
        if (StringUtils.isEmpty(job_depart)) {
            ToastUtils.toastL(_mActivity, "招聘部门不能为空");
            return false;
        }
        job_num = mEtJobTempJobNum.getText().toString();
        if (StringUtils.isEmpty(job_num)) {
            ToastUtils.toastL(_mActivity, "招聘人数不能为空");
            return false;
        }
        job_edu_require = mTvJobTempEduReq.getText().toString();
        if (StringUtils.isEmpty(job_edu_require)) {
            ToastUtils.toastL(_mActivity, "学历要求不能为空");
            return false;
        }
        job_exper_year = mTvJobTempExperYear.getText().toString();
        if (StringUtils.isEmpty(job_exper_year)) {
            ToastUtils.toastL(_mActivity, "工作经验不能为空");
            return false;
        }
        job_salary = mTvJobTempJobSalary.getText().toString();
        if (StringUtils.isEmpty(job_salary)) {
            ToastUtils.toastL(_mActivity, "薪资范围不能为空");
            return false;
        }
        job_addr = mEtJobTempJobAddr.getText().toString();
        if (StringUtils.isEmpty(job_addr)) {
            ToastUtils.toastL(_mActivity, "工作地点不能为空");
            return false;
        }
        job_skill_require = mEtJobTempJobSkillReq.getText().toString();
        if (StringUtils.isEmpty(job_skill_require)) {
            ToastUtils.toastL(_mActivity, "技能要求不能为空");
            return false;
        }
        job_desc = mEtJobTempJobDesc.getText().toString();
        if (StringUtils.isEmpty(job_desc)) {
            ToastUtils.toastL(_mActivity, "职位描述不能为空");
            return false;
        }
        return true;
    }
}
