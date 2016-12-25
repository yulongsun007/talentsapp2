package win.yulongsun.talents.ui.stu.job;

import android.view.View;

import org.greenrobot.eventbus.EventBus;

import win.yulongsun.framework.adapter.SuperAdapter;
import win.yulongsun.talents.R;
import win.yulongsun.talents.adapter.JobTempLibListRVAdapter;
import win.yulongsun.talents.base.CommonListFragment;
import win.yulongsun.talents.entity.JobTemplate;
import win.yulongsun.talents.event.StartBrotherEvent;
import win.yulongsun.talents.http.resp.biz.JobTemplateResponse;
import win.yulongsun.talents.ui.hr.job.JobTempDetailFragment;

/**
 * @author sunyulong on 2016/12/25.
 * 所有公司的招聘信息
 */
public class StuJobTempListFragment extends CommonListFragment{
    @Override
    protected String getToolbarTitle() {
        return "招聘信息";
    }

    public static StuJobTempListFragment newInstance(){
        return new StuJobTempListFragment();
    }

    @Override
    protected SuperAdapter getAdapter() {
        return new JobTempLibListRVAdapter(_mActivity,_mDatas, R.layout.item_job_temp_lib_list);
    }

    @Override
    protected String getSubTag() {
        return StuJobTempListFragment.class.getSimpleName();
    }

    @Override
    protected void initData() {
        super.initData();

        loadDataFromServer("job_temp/listAllDeploy",new JobTemplate(), JobTemplateResponse.class);
    }

    @Override
    public void onItemClick(View itemView, int viewType, int position) {
        super.onItemClick(itemView, viewType, position);
        EventBus.getDefault().post(new StartBrotherEvent(JobTempDetailFragment.newInstance((JobTemplate) _mDatas.get(position))));
    }
}
