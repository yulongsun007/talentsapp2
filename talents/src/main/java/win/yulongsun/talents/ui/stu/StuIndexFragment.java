package win.yulongsun.talents.ui.stu;

import android.support.v7.widget.Toolbar;

import me.yokeyword.fragmentation.SupportFragment;
import win.yulongsun.talents.R;
import win.yulongsun.talents.base.BaseRootFragment;

/**
 * @author sunyulong on 2016/12/15.
 * 学生端首页
 */
public class StuIndexFragment  extends BaseRootFragment{
    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_stu_index;
    }

    @Override
    protected Toolbar getToolbar() {
        return null;
    }

    @Override
    protected String getToolbarTitle() {
        return null;
    }

    public static SupportFragment newInstance() {
        return new SupportFragment();
    }
}
