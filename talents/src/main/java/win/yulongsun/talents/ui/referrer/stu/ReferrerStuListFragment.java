package win.yulongsun.talents.ui.referrer.stu;

import android.support.v7.widget.Toolbar;

import win.yulongsun.talents.R;
import win.yulongsun.talents.base.BaseChildFragment;

/**
 * @author sunyulong on 2016/12/16.
 * 选了某个培养计划的学生
 */
public class ReferrerStuListFragment extends BaseChildFragment{

    public static ReferrerStuListFragment newInstance(){
        return new ReferrerStuListFragment();
    }
    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_referrer_stu_list;
    }

    @Override
    protected Toolbar getToolbar() {
        return null;
    }

    @Override
    protected String getToolbarTitle() {
        return null;
    }
}
