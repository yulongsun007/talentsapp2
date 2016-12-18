package win.yulongsun.talents.ui.hr.job.lib;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.byteam.superadapter.OnItemClickListener;
import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.Bind;
import win.yulongsun.talents.base.BaseSwipeBackFragment;
import win.yulongsun.talents.common.Constant;
import win.yulongsun.talents.entity.JobTemplate;
import win.yulongsun.talents.event.StartBrotherEvent;
import win.yulongsun.talents.R;
import win.yulongsun.talents.adapter.JobTempLibListRVAdapter;

/**
 * @author sunyulong on 2016/12/2.
 *         招聘模板 列表
 */
public class JobTempLibListFragment extends BaseSwipeBackFragment {

    @Bind(R.id.toolbar)
    Toolbar      mToolbar;
    @Bind(R.id.recy_job_temp_lib)
    RecyclerView mRecyJobTempLib;

    public static JobTempLibListFragment newInstance() {
        return new JobTempLibListFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_job_temp_lib_list;
    }

    @Override
    protected Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    protected String getToolbarTitle() {
        return "招聘模板";
    }

    @Override
    protected void initView() {
        super.initView();
        ArrayList<JobTemplate> mJobTemplates = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mJobTemplates.add(new JobTemplate());
        }
        JobTempLibListRVAdapter mAdapter = new JobTempLibListRVAdapter(_mActivity, mJobTemplates, R.layout.item_job_temp_lib);
        mRecyJobTempLib.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRecyJobTempLib.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {
                EventBus.getDefault().post(new StartBrotherEvent(JobTempLibEditFragment.newInstance(Constant.MODE_VALUE.EDIT)));
            }
        });
    }
}
