package win.yulongsun.talents.ui.stu.clazz;

import android.support.v7.widget.Toolbar;

import butterknife.Bind;
import win.yulongsun.talents.R;
import win.yulongsun.talents.base.BaseChildFragment;

/**
 * @author sunyulong on 2016/12/18.
 */
public class ClazzListFragment extends BaseChildFragment {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_clazz_list;
    }

    @Override
    protected Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    protected String getToolbarTitle() {
        return "课程列表";
    }

    public static ClazzListFragment newInstance() {
        return new ClazzListFragment();
    }

}
