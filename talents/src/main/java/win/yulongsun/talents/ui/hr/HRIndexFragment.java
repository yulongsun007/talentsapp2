package win.yulongsun.talents.ui.hr;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.Bind;
import me.yokeyword.fragmentation.SupportFragment;
import win.yulongsun.talents.R;
import win.yulongsun.talents.adapter.CommonIndexFragmentAdapter;
import win.yulongsun.talents.base.BaseRootFragment;
import win.yulongsun.talents.event.StartBrotherEvent;
import win.yulongsun.talents.ui.hr.job.JobTempAddFragment;
import win.yulongsun.talents.ui.hr.job.JobTempListFragment;
import win.yulongsun.talents.ui.hr.job.JobTempSearchFragment;
import win.yulongsun.talents.ui.hr.job.lib.JobTempLibListFragment;
import win.yulongsun.talents.ui.hr.referrer.ReferrerAddFragment;
import win.yulongsun.talents.ui.hr.referrer.ReferrerListFragment;
import win.yulongsun.talents.ui.hr.referrer.ReferrerSearchFragment;
import win.yulongsun.talents.ui.hr.score.ScoreListFragment;
import win.yulongsun.talents.ui.hr.talent.TalentListFragment;
import win.yulongsun.talents.ui.hr.talent.TalentSearchFragment;

/**
 * Created by yulongsun
 * Date at 2016-8-16
 * Desc  HR主页
 */
public class HRIndexFragment extends BaseRootFragment {
    private static final int POSITION_TALENT   = 0;
    private static final int POSITION_JOB      = 1;
    private static final int POSITION_REFERRER = 2;
    private static final int POSITION_SCORE    = 3;
    @Bind(R.id.tab)
    TabLayout mTab;
    @Bind(R.id.viewPager)
    ViewPager mViewPager;
    @Bind(R.id.tl_index)
    Toolbar   mTlIndex;
    private int mPosition = 0;
    private ArrayList<SupportFragment> mFragmentList;
    private String[]                   mTitle;

    public static HRIndexFragment newInstance() {

        Bundle args = new Bundle();

        HRIndexFragment fragment = new HRIndexFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_index;
    }

    @Override
    protected int getMenuResId() {
        return R.menu.menu_hr_index;
    }

    @Override
    protected Toolbar getToolbar() {
        return mTlIndex;
    }

    @Override
    protected String getToolbarTitle() {
        return "首页";
    }


    @Override
    protected void initView() {
        super.initView();
        mTab.addTab(mTab.newTab());
        mTab.addTab(mTab.newTab());
        //Title
        mTitle = new String[]{"人才库", "招聘", "推荐人", "积分排名"};
        //Fragment
        mFragmentList = new ArrayList<SupportFragment>();
        mFragmentList.add(TalentListFragment.newInstance());
        mFragmentList.add(JobTempListFragment.newInstance());
        mFragmentList.add(ReferrerListFragment.newInstance());
        mFragmentList.add(ScoreListFragment.newInstance());


        mViewPager.setAdapter(new CommonIndexFragmentAdapter(mTitle, mFragmentList, getChildFragmentManager()));
        mTab.setupWithViewPager(mViewPager);

        //menu
        getToolbar().setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_add:
                        toAdd();
                        break;
                    case R.id.action_search:
                        toSearch();
                        break;
                    case R.id.action_job_temp_lib:
                        EventBus.getDefault().post(new StartBrotherEvent(JobTempLibListFragment.newInstance()));
                        break;
                }
                return true;
            }
        });

        //menu icon
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Menu mMenu = getToolbar().getMenu();
                if (mMenu == null) {
                    return;
                }
                mPosition = position;
                MenuItem mActionSearch = mMenu.findItem(R.id.action_search);
                MenuItem mActionAdd = mMenu.findItem(R.id.action_add);
                MenuItem mActionJobTempLib = mMenu.findItem(R.id.action_job_temp_lib);
                if (POSITION_SCORE == position || POSITION_TALENT == position) {
                    mActionAdd.setVisible(false);
                } else {
                    mActionAdd.setVisible(true);
                }
                if (POSITION_SCORE == position) {
                    mActionSearch.setVisible(false);
                } else {
                    mActionSearch.setVisible(true);
                }
                if (POSITION_JOB == position) {
                    mActionJobTempLib.setVisible(true);
                } else {
                    mActionJobTempLib.setVisible(false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    //搜索
    private void toSearch() {
        SupportFragment targetFragment = null;
        switch (mPosition) {
            case POSITION_TALENT:
                targetFragment = TalentSearchFragment.newInstance();
                break;
            case POSITION_JOB:
                targetFragment = JobTempSearchFragment.newInstance();
                break;
            case POSITION_REFERRER:
                targetFragment = ReferrerSearchFragment.newInstance();
                break;
            case POSITION_SCORE:
                //none
                break;
        }
        EventBus.getDefault().post(new StartBrotherEvent(targetFragment));

    }

    //添加
    private void toAdd() {
        SupportFragment targetFragment = null;
        switch (mPosition) {
            case POSITION_TALENT:
                targetFragment = TalentSearchFragment.newInstance();
                break;
            case POSITION_JOB:
                targetFragment = JobTempAddFragment.newInstance();
                break;
            case POSITION_REFERRER:
                targetFragment = ReferrerAddFragment.newInstance();
                break;
            case POSITION_SCORE:
                //none
                break;
        }
        EventBus.getDefault().post(new StartBrotherEvent(targetFragment));
    }

}
