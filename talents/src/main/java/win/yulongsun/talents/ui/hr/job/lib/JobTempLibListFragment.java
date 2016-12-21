package win.yulongsun.talents.ui.hr.job.lib;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.raizlabs.android.dbflow.sql.language.Select;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.byteam.superadapter.OnItemClickListener;
import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.Bind;
import okhttp3.Call;
import win.yulongsun.framework.util.JsonUtil;
import win.yulongsun.framework.util.android.widget.ToastUtils;
import win.yulongsun.talents.R;
import win.yulongsun.talents.adapter.JobTempLibListRVAdapter;
import win.yulongsun.talents.base.BaseChildFragment;
import win.yulongsun.talents.common.Constant;
import win.yulongsun.talents.entity.JobTemplate;
import win.yulongsun.talents.event.StartBrotherEvent;
import win.yulongsun.talents.http.resp.biz.JobTemplateResponse;

/**
 * @author sunyulong on 2016/12/2.
 *         招聘模板 列表
 */
public class JobTempLibListFragment extends BaseChildFragment implements SwipeRefreshLayout.OnRefreshListener {


    private static final String TAG = JobTempLibListFragment.class.getSimpleName();
    @Bind(R.id.toolbar)
    Toolbar            mToolbar;
    @Bind(R.id.recy_job_temp_lib)
    RecyclerView       mRecyJobTempLib;
    @Bind(R.id.srf_job_temp_lib_list)
    SwipeRefreshLayout mSrfJobTempLibList;
    private List<JobTemplate>       mJobTemplates;
    private JobTempLibListRVAdapter mAdapter;

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
    protected int getMenuResId() {
        return R.menu.menu_common_add;
    }

    @Override
    protected void initView() {
        super.initView();
        mSrfJobTempLibList.setOnRefreshListener(this);
        mJobTemplates = new Select().from(JobTemplate.class).queryList();
        mAdapter = new JobTempLibListRVAdapter(_mActivity, mJobTemplates, R.layout.item_job_temp_lib);
        mRecyJobTempLib.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRecyJobTempLib.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {
                EventBus.getDefault().post(new StartBrotherEvent(JobTempLibEditFragment.newInstance(Constant.MODE_VALUE.EDIT)));
            }
        });
    }

    @Override
    protected void loadDataFromServer() {
        super.loadDataFromServer();
        mSrfJobTempLibList.setRefreshing(true);
        OkHttpUtils.post()
                .url(Constant.URL + "job_temp/list")
                .addParams("create_by", String.valueOf(_User.user_id))
                .tag(TAG)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        mSrfJobTempLibList.setRefreshing(false);
                        ToastUtils.toastL(_mActivity, ToastUtils.CONNECT_SERVER_EXCEPTION);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        mSrfJobTempLibList.setRefreshing(false);
                        JobTemplateResponse resp = (JobTemplateResponse) JsonUtil.fromJson(response, JobTemplateResponse.class);
                        List<JobTemplate> data = resp.data;
                        mAdapter.replaceAll(data);
                    }
                });
    }

    @Override
    public void onRefresh() {
        loadDataFromServer();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        OkHttpUtils.getInstance().cancelTag(TAG);
    }
}
