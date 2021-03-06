package win.yulongsun.talents.ui.hr.job;

import android.view.View;

import org.greenrobot.eventbus.EventBus;

import win.yulongsun.framework.adapter.SuperAdapter;
import win.yulongsun.talents.R;
import win.yulongsun.talents.adapter.JobTempLibListRVAdapter;
import win.yulongsun.talents.base.CommonListFragment;
import win.yulongsun.talents.entity.JobTemplate;
import win.yulongsun.talents.entity.User;
import win.yulongsun.talents.event.StartBrotherEvent;
import win.yulongsun.talents.http.resp.biz.JobTemplateResponse;

/**
 * Create By: yulongsun
 * Date at: 2016/8/30
 * Desc : 招聘模板列表
 */
public class JobTempListFragment extends CommonListFragment {

    public static final JobTempListFragment INSTANCE = new JobTempListFragment();

    public static JobTempListFragment newInstance() {
        return INSTANCE;
    }

    @Override
    protected String getToolbarTitle() {
        return null;
    }

    @Override
    protected SuperAdapter getAdapter() {
        return new JobTempLibListRVAdapter(_mActivity, _mDatas, R.layout.item_job_temp_lib_list);
    }

    @Override
    protected String getSubTag() {
        return JobTempListFragment.class.getSimpleName();
    }

    @Override
    protected void initData() {
        super.initData();
        _mToolbar.setVisibility(View.GONE);
        User user = new User();
        user.user_company_id = _mUser.user_company_id;
        loadDataFromServer("job_temp/listDeploy", user, JobTemplateResponse.class);
    }

    @Override
    public void onItemClick(View itemView, int viewType, int position) {
        super.onItemClick(itemView, viewType, position);
        EventBus.getDefault().post(new StartBrotherEvent(JobTempDetailFragment.newInstance((JobTemplate) _mDatas.get(position))));
    }
}
