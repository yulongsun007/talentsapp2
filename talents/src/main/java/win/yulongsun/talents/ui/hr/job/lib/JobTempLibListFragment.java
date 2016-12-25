package win.yulongsun.talents.ui.hr.job.lib;

import android.view.MenuItem;
import android.view.View;

import java.util.List;

import win.yulongsun.framework.adapter.SuperAdapter;
import win.yulongsun.talents.R;
import win.yulongsun.talents.adapter.JobTempLibListRVAdapter;
import win.yulongsun.talents.base.CommonListFragment;
import win.yulongsun.talents.common.Constant;
import win.yulongsun.talents.entity.JobTemplate;
import win.yulongsun.talents.http.resp.biz.JobTemplateResponse;

/**
 * @author sunyulong on 2016/12/2.
 *         招聘模板 列表
 */
public class JobTempLibListFragment extends CommonListFragment {


    public static JobTempLibListFragment newInstance() {
        return new JobTempLibListFragment();
    }

    @Override
    protected String getToolbarTitle() {
        return "招聘模板";
    }

    @Override
    protected SuperAdapter getAdapter() {
        return new JobTempLibListRVAdapter(_mActivity, (List<JobTemplate>) _mDatas, R.layout.item_job_temp_lib_list);
    }

    @Override
    protected String getSubTag() {
        return JobTempLibListFragment.class.getSimpleName();
    }

    @Override
    protected int getMenuResId() {
        return R.menu.menu_common_add;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (R.id.action_common_add == item.getItemId()) {
            start(JobTempLibEditFragment.newInstance(Constant.MODE_VALUE.ADD, null));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initData() {
        super.initData();
        JobTemplate jobTemplate = new JobTemplate();
        jobTemplate.create_by = _mUser.user_id;
        loadDataFromServer("job_temp/list", jobTemplate, JobTemplateResponse.class);
    }

    @Override
    public void onItemClick(View itemView, int viewType, int position) {
        super.onItemClick(itemView, viewType, position);
        start(JobTempLibEditFragment.newInstance(Constant.MODE_VALUE.EDIT, (JobTemplate) _mDatas.get(position)));

    }
}
