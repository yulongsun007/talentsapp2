package win.yulongsun.talents.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import me.yokeyword.fragmentation.SupportFragment;

//通用首页适配器
public class CommonIndexFragmentAdapter extends FragmentPagerAdapter {
    private String[]              mTab;
    private List<SupportFragment> mFragmentList;

    public CommonIndexFragmentAdapter(String[] tab, List<SupportFragment> fragmentList, FragmentManager fm) {
        super(fm);
        mTab = tab;
        mFragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
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
