package win.yulongsun.talents.ui.hr.referrer;

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
import win.yulongsun.talents.adapter.ReferrerListRVAdapter;
import win.yulongsun.talents.base.BaseRootFragment;
import win.yulongsun.talents.common.Constant;
import win.yulongsun.talents.entity.User;
import win.yulongsun.talents.http.resp.biz.UserResponse;

/**
 * @author sunyulong on 2016/11/29.
 *         <p>
 *         推荐人 列表
 */
public class ReferrerListFragment extends BaseRootFragment implements SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.recy_referrer)
    RecyclerView       mRecyReferrer;
    @Bind(R.id.srf_referrer_list)
    SwipeRefreshLayout mSrfReferrerList;
    public static ReferrerListFragment INSTANCE = new ReferrerListFragment();

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_referrer_list;
    }

    @Override
    protected Toolbar getToolbar() {
        return null;
    }

    @Override
    protected String getToolbarTitle() {
        return null;
    }

    public static ReferrerListFragment newInstance() {
        return INSTANCE;
    }

    @Override
    protected void initView() {
        super.initView();
        mSrfReferrerList.setOnRefreshListener(this);
        mRecyReferrer.setLayoutManager(new LinearLayoutManager(_mActivity));

    }

    @Override
    protected void loadDataFromServer() {
        super.loadDataFromServer();
        mSrfReferrerList.setRefreshing(true);
        OkHttpUtils.post()
                .url(Constant.URL + "user/listReferrer")
                .addParams("user_company_id", String.valueOf(_User.user_company_id))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        mSrfReferrerList.setRefreshing(false);
                        ToastUtils.toastL(_mActivity, ToastUtils.CONNECT_SERVER_EXCEPTION);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        UserResponse resp = (UserResponse) JsonUtil.fromJson(response, UserResponse.class);
                        List<User> data = resp.data;
                        ReferrerListRVAdapter mAdapter = new ReferrerListRVAdapter(_mActivity, data, R.layout.item_referrer_list);
                        mRecyReferrer.setAdapter(mAdapter);
                    }
                });
    }

    @Override
    public void onRefresh() {
        loadDataFromServer();
    }
}
