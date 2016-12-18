package win.yulongsun.talents.ui.hr.talent;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.byteam.superadapter.OnItemClickListener;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import win.yulongsun.talents.base.BaseRootFragment;
import win.yulongsun.talents.entity.Talent;
import win.yulongsun.talents.event.StartBrotherEvent;
import win.yulongsun.talents.event.TabSelectedEvent;
import win.yulongsun.talents.R;
import win.yulongsun.talents.adapter.TalentRVAdapter;
import win.yulongsun.talents.ui.main.MainFragment;

/**
 * Created by yulongsun
 * Date at 2016-8-16
 * Desc  人才页
 */
public class TalentListFragment extends BaseRootFragment implements SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.recy)
    RecyclerView       mRecy;
    @Bind(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;

    private TalentRVAdapter mAdapter;

    private boolean mAtTop = true;

    private int mScrollTotal;

    public static TalentListFragment newInstance() {

        Bundle args = new Bundle();

        TalentListFragment fragment = new TalentListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_talent_list;
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
        EventBus.getDefault().register(this);

        mRefreshLayout.setColorSchemeResources(R.color.color_primary);
        mRefreshLayout.setOnRefreshListener(this);

        List<Talent> mList = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            mList.add(new Talent());
        }
        mAdapter = new TalentRVAdapter(_mActivity,mList,R.layout.item_fragment_list);
        LinearLayoutManager manager = new LinearLayoutManager(_mActivity);
        mRecy.setLayoutManager(manager);
        mRecy.setAdapter(mAdapter);


        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {
                EventBus.getDefault().post(new StartBrotherEvent(TalentDetailFragment.newInstance(position)));
            }
        });

//        mAdapter.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(int position, View view, RecyclerView.ViewHolder vh) {
//                // 这里的DetailFragment在flow包里
//                // 这里是父Fragment启动,要注意 栈层级
//                ((SupportFragment) getParentFragment()).start(DetailFragment.newInstance(mAdapter.getItem(position).getTitle()));
//            }
//        });

        // Init Datas

//        mAdapter.setDatas(articleList);

        mRecy.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mScrollTotal += dy;
                if (mScrollTotal <= 0) {
                    mAtTop = true;
                } else {
                    mAtTop = false;
                }
            }
        });

    }

    @Override
    public void onRefresh() {
        mRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(false);
            }
        }, 2000);
    }

    private void scrollToTop() {
        mRecy.smoothScrollToPosition(0);
    }

    /**
     * 选择tab事件
     */
    @Subscribe
    public void onTabSelectedEvent(TabSelectedEvent event) {
        if (event.position != MainFragment.FIRST) return;
        if (mAtTop) {
            mRefreshLayout.setRefreshing(true);
            onRefresh();
        } else {
            scrollToTop();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        ButterKnife.unbind(this);
    }

}
