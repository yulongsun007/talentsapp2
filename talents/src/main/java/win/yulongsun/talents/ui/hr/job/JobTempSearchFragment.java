package win.yulongsun.talents.ui.hr.job;


import win.yulongsun.framework.adapter.SuperAdapter;
import win.yulongsun.framework.util.java.lang.NumberUtils;
import win.yulongsun.framework.util.java.lang.StringUtils;
import win.yulongsun.talents.R;
import win.yulongsun.talents.adapter.JobTempLibListRVAdapter;
import win.yulongsun.talents.base.CommonSearchFragment;
import win.yulongsun.talents.entity.JobTemplate;
import win.yulongsun.talents.http.resp.biz.JobTemplateResponse;

/**
 * @author sunyulong on 2016/12/2.
 *         搜索招聘
 */
public class JobTempSearchFragment extends CommonSearchFragment {


    public static JobTempSearchFragment newInstance() {
        return new JobTempSearchFragment();
    }

    @Override
    protected SuperAdapter getAdapter() {
        return new JobTempLibListRVAdapter(_mActivity, _mDatas, R.layout.item_job_temp_lib_list);
    }

    @Override
    protected String getSubTag() {
        return JobTempSearchFragment.class.getSimpleName();
    }

    @Override
    public String getSearchKeyHint() {
        return "模板编号/岗位名";
    }

    @Override
    protected void toSearch() {
        JobTemplate jobTemplate = new JobTemplate();
        jobTemplate.create_by = _mUser.user_id;
        String key = _mEtCommonSearchKey.getText().toString();
        if (!StringUtils.isEmpty(key) && NumberUtils.isInteger(key)) {
            jobTemplate.tmp_id = Integer.parseInt(key);
        } else {
            jobTemplate.tmp_job_name = key;
        }
        loadDataFromServer("job_temp/queryDeploy", jobTemplate, JobTemplateResponse.class);
    }

}
