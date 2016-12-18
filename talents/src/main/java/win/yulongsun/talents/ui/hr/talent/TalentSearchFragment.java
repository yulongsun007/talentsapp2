package win.yulongsun.talents.ui.hr.talent;

import android.support.v7.widget.Toolbar;

import butterknife.Bind;
import win.yulongsun.talents.base.BaseSwipeBackFragment;
import win.yulongsun.talents.R;

/**
 * @author sunyulong on 2016/11/28.
 *
 * 人才搜索
 */
public class TalentSearchFragment extends BaseSwipeBackFragment {
    public static TalentSearchFragment newInstance(){
        return new TalentSearchFragment();
    }

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_talent_search;
    }

    @Override
    protected Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    protected String getToolbarTitle() {
        return "人才搜索";
    }

}
