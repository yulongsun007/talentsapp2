package win.yulongsun.talents.ui.hr.referrer;

import android.support.v7.widget.Toolbar;

import butterknife.Bind;
import win.yulongsun.talents.base.BaseSwipeBackFragment;
import win.yulongsun.talents.R;

/**
 * @author sunyulong on 2016/12/2.
 *         推荐人搜索
 */
public class ReferrerSearchFragment extends BaseSwipeBackFragment {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    public static ReferrerSearchFragment newInstance() {
        return new ReferrerSearchFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_referrer_search;
    }

    @Override
    protected Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    protected String getToolbarTitle() {
        return "搜索推荐人";
    }
}
