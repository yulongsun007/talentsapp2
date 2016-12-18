package win.yulongsun.talents.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.fragmentation.SupportFragment;
import win.yulongsun.talents.ui.hr.job.JobTempListFragment;
import win.yulongsun.talents.ui.hr.referrer.ReferrerListFragment;
import win.yulongsun.talents.ui.hr.score.ScoreFragment;
import win.yulongsun.talents.ui.hr.talent.TalentListFragment;

//首页适配器
public class CommonIndexFragmentAdapter extends FragmentPagerAdapter {
    private String[]              mTab          = new String[]{"人才库", "招聘", "推荐人", "积分排名"};
    private List<SupportFragment> mFragmentList = new ArrayList<>();

    public CommonIndexFragmentAdapter(FragmentManager fm) {
        super(fm);
        mFragmentList.add(TalentListFragment.newInstance());
        mFragmentList.add(JobTempListFragment.newInstance());
        mFragmentList.add(ReferrerListFragment.newInstance());
        mFragmentList.add(ScoreFragment.newInstance());
    }

    @Override
    public Fragment getItem(int position) {
        SupportFragment mFragment = null;
        switch (position) {
            case 0:
                mFragment = mFragmentList.get(0);
                break;
            case 1:
                mFragment = mFragmentList.get(1);
                break;
            case 2:
                mFragment = mFragmentList.get(2);
                break;
            case 3:
                mFragment = mFragmentList.get(3);
                break;
        }
        return mFragment;
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTab[position];
    }
}
