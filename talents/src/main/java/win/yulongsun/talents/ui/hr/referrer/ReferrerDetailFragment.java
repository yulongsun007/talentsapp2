package win.yulongsun.talents.ui.hr.referrer;

import android.support.v7.widget.Toolbar;

import butterknife.Bind;
import win.yulongsun.talents.base.BaseSwipeBackFragment;
import win.yulongsun.talents.R;

/**
 * @author sunyulong on 2016/11/29.
 *         推荐人详情
 */
public class ReferrerDetailFragment extends BaseSwipeBackFragment {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    public static ReferrerDetailFragment newInstance() {
        return new ReferrerDetailFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_referrer_detail;
    }

    @Override
    protected Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    protected String getToolbarTitle() {
        return "联系人详情";
    }

}
