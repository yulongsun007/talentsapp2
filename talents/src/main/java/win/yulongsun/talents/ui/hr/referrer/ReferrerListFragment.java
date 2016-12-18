package win.yulongsun.talents.ui.hr.referrer;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

import butterknife.Bind;
import win.yulongsun.talents.base.BaseRootFragment;
import win.yulongsun.talents.entity.Referrer;
import win.yulongsun.talents.R;
import win.yulongsun.talents.adapter.ReferrerRVAdapter;

/**
 * @author sunyulong on 2016/11/29.
 *         <p>
 *         推荐人 列表
 */
public class ReferrerListFragment extends BaseRootFragment {

    @Bind(R.id.recy_referrer)
    RecyclerView mRecyReferrer;
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
        ArrayList<Referrer> mDatas = new ArrayList<>();
        for (int i = 0; i < 35; i++) {
            mDatas.add(new Referrer());
        }
        ReferrerRVAdapter mAdapter = new ReferrerRVAdapter(_mActivity, mDatas, R.layout.item_referrer);
        mRecyReferrer.setAdapter(mAdapter);

        mRecyReferrer.setLayoutManager(new LinearLayoutManager(_mActivity));

    }

}
