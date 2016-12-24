package win.yulongsun.talents.ui.referrer;

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
import win.yulongsun.talents.common.Constant;
import win.yulongsun.talents.event.StartBrotherEvent;
import win.yulongsun.talents.ui.hr.job.JobTempListFragment;
import win.yulongsun.talents.ui.hr.job.lib.JobTempLibListFragment;
import win.yulongsun.talents.ui.referrer.plan.PlanEditFragment;
import win.yulongsun.talents.ui.referrer.plan.PlanListFragment;
import win.yulongsun.talents.ui.referrer.stu.ReferrerStuListFragment;

/**
 * @author sunyulong on 2016/12/15.
 *         推荐人主页
 */
public class ReferrerIndexFragment extends BaseRootFragment {
    private static final int POSITION_JOB   = 0;
    private static final int POSITION_STU   = 1;
    private static final int POSITION_PLAN  = 2;
    private static final int POSITION_CLAZZ = 3;
    @Bind(R.id.tab)
    TabLayout mTab;
    @Bind(R.id.viewPager)
    ViewPager mViewPager;
    @Bind(R.id.tl_index)
    Toolbar   mTlIndex;
    private int mPosition = 0;
    private ArrayList<SupportFragment> mFragmentList;
    private String[]                   mTitle;

    public static ReferrerIndexFragment newInstance() {

        Bundle args = new Bundle();

        ReferrerIndexFragment fragment = new ReferrerIndexFragment();
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
        mTitle = new String[]{"招聘", "学生", "培养计划"};
        //Fragment
        mFragmentList = new ArrayList<SupportFragment>();
        mFragmentList.add(JobTempListFragment.newInstance());
        mFragmentList.add(ReferrerStuListFragment.newInstance());
        mFragmentList.add(PlanListFragment.newInstance());
//        mFragmentList.add(ClazzAddFragment.newInstance());


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
                if (POSITION_PLAN == position) {
                    mActionAdd.setVisible(false);
                } else {
                    mActionAdd.setVisible(true);
                }
//                if (POSITION_SCORE == position) {
//                    mActionSearch.setVisible(false);
//                } else {
//                    mActionSearch.setVisible(true);
//                }
//                if (POSITION_JOB == position) {
//                    mActionJobTempLib.setVisible(true);
//                } else {
//                    mActionJobTempLib.setVisible(false);
//                }
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
//            case POSITION_TALENT:
//                targetFragment = TalentSearchFragment.newInstance();
//                break;
//            case POSITION_JOB:
//                targetFragment = JobTempSearchFragment.newInstance();
//                break;
//            case POSITION_REFERRER:
//                targetFragment = ReferrerSearchFragment.newInstance();
//                break;
//            case POSITION_SCORE:
//                //none
//                break;
        }
        EventBus.getDefault().post(new StartBrotherEvent(targetFragment));

    }

    //添加
    private void toAdd() {
        SupportFragment targetFragment = null;
        switch (mPosition) {
            case POSITION_PLAN:
                targetFragment = PlanEditFragment.newInstance(Constant.MODE_VALUE.ADD, null);
                break;
//            case POSITION_JOB:
//                targetFragment = JobTempAddFragment.newInstance();
//                break;
//            case POSITION_REFERRER:
//                targetFragment = ReferrerAddFragment.newInstance();
//                break;
//            case POSITION_SCORE:
//                //none
//                break;
        }
        EventBus.getDefault().post(new StartBrotherEvent(targetFragment));
    }
}
