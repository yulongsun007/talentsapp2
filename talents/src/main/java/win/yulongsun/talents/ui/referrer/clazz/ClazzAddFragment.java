package win.yulongsun.talents.ui.referrer.clazz;

import android.support.v7.widget.Toolbar;

import win.yulongsun.talents.R;
import win.yulongsun.talents.base.BaseChildFragment;

/**
 * @author sunyulong on 2016/12/16.
 */
public class ClazzAddFragment extends BaseChildFragment{
    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_clazz;
    }

    @Override
    protected Toolbar getToolbar() {
        return null;
    }

    @Override
    protected String getToolbarTitle() {
        return null;
    }

    public static ClazzAddFragment newInstance() {
        return new ClazzAddFragment();
    }
}
