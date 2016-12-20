package win.yulongsun.talents.ui.hr.score;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.Bind;
import okhttp3.Call;
import win.yulongsun.framework.util.JsonUtil;
import win.yulongsun.framework.util.android.widget.ToastUtils;
import win.yulongsun.talents.R;
import win.yulongsun.talents.adapter.ScoreListRVAdapter;
import win.yulongsun.talents.base.BaseRootFragment;
import win.yulongsun.talents.common.Constant;
import win.yulongsun.talents.entity.User;
import win.yulongsun.talents.http.resp.biz.UserResponse;

/**
 * @author sunyulong on 2016/11/29.
 *         <p>
 *         积分排名
 */
public class ScoreListFragment extends BaseRootFragment implements SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.recy_score)
    RecyclerView       mRecyclerView;
    @Bind(R.id.srf_score_list)
    SwipeRefreshLayout mSrfScoreList;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_score;
    }

    @Override
    protected Toolbar getToolbar() {
        return null;
    }

    @Override
    protected String getToolbarTitle() {
        return null;
    }

    public static final ScoreListFragment INSTANCE = new ScoreListFragment();

    public static ScoreListFragment newInstance() {
        return INSTANCE;
    }

    @Override
    protected void initView() {
        super.initView();
        mSrfScoreList.setOnRefreshListener(this);
    }

    @Override
    protected void loadDataFromServer() {
        super.loadDataFromServer();
        mSrfScoreList.setRefreshing(true);
        OkHttpUtils.post()
                .url(Constant.URL + "user/listScore")
                .addParams("user_company_id", String.valueOf(_User.user_company_id))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        mSrfScoreList.setRefreshing(false);
                        ToastUtils.toastL(_mActivity, "连接服务器失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        mSrfScoreList.setRefreshing(false);
                        UserResponse resp = (UserResponse) JsonUtil.fromJson(response, UserResponse.class);
                        List<User> data = resp.data;
                        ScoreListRVAdapter mAdapter = new ScoreListRVAdapter(_mActivity, data, R.layout.item_score_list);

                        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
                        mRecyclerView.setAdapter(mAdapter);
                    }
                });
    }

    @Override
    public void onRefresh() {
        loadDataFromServer();
    }
}
