package win.yulongsun.talents.ui.me.child;

import android.support.v7.widget.Toolbar;

import butterknife.Bind;
import win.yulongsun.talents.base.BaseSwipeBackFragment;
import win.yulongsun.talents.R;

/**
 * Create By: yulongsun
 * Date at: 2016/8/31
 * Desc : 选择行业方向
 */
public class ChooseBizDirectFragment extends BaseSwipeBackFragment {
    public static ChooseBizDirectFragment newInstance() {
        return new ChooseBizDirectFragment();
    }

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_choose_biz_direct;
    }

    @Override
    protected Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    protected String getToolbarTitle() {
        return "选择行业方向";
    }

}
