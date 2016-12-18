package win.yulongsun.talents.ui.hr.referrer;

import android.support.v7.widget.Toolbar;

import butterknife.Bind;
import win.yulongsun.talents.base.BaseSwipeBackFragment;
import win.yulongsun.talents.R;

/**
 * @author sunyulong on 2016/12/2.
 *         添加推荐人
 */
public class ReferrerAddFragment extends BaseSwipeBackFragment {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    public static ReferrerAddFragment newInstance() {
        return new ReferrerAddFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_referrer_add;
    }

    @Override
    protected Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    protected String getToolbarTitle() {
        return "添加推荐人";
    }

}
