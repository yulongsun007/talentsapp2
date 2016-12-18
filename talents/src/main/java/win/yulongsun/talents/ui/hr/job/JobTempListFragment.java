package win.yulongsun.talents.ui.hr.job;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.byteam.superadapter.OnItemClickListener;
import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.Bind;
import win.yulongsun.talents.base.BaseRootFragment;
import win.yulongsun.talents.entity.JobTemplate;
import win.yulongsun.talents.R;
import win.yulongsun.talents.adapter.JobTempLibListRVAdapter;
import win.yulongsun.talents.event.StartBrotherEvent;
import win.yulongsun.talents.ui.referrer.job.JobTempDetailFragment;

/**
 * Create By: yulongsun
 * Date at: 2016/8/30
 * Desc : 招聘模板列表
 */
public class JobTempListFragment extends BaseRootFragment {
    @Bind(R.id.recy_job_temp_list)
    RecyclerView mRecyJobTempList;
    private JobTempLibListRVAdapter mAdapter;

    public static final JobTempListFragment INSTANCE= new JobTempListFragment();

    public static JobTempListFragment newInstance() {
        return INSTANCE;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_job_temp_list;
    }

    @Override
    protected Toolbar getToolbar() {
        return null;
    }

    @Override
    protected String getToolbarTitle() {
        return null;
    }


    @Override
    protected void initView() {
        super.initView();
        //rv
        ArrayList<JobTemplate> mJobTemplates = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            mJobTemplates.add(new JobTemplate());
        }
        mAdapter = new JobTempLibListRVAdapter(_mActivity, mJobTemplates,R.layout.item_job_temp_lib);
        mRecyJobTempList.setLayoutManager( new LinearLayoutManager(_mActivity));
        mRecyJobTempList.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {
                EventBus.getDefault().post(new StartBrotherEvent(JobTempDetailFragment.newInstance()));
            }
        });


    }


}
