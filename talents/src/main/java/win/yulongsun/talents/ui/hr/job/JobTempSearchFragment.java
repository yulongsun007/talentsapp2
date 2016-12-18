package win.yulongsun.talents.ui.hr.job;

import android.support.v7.widget.Toolbar;

import butterknife.Bind;
import win.yulongsun.talents.base.BaseSwipeBackFragment;
import win.yulongsun.talents.R;

/**
 * @author sunyulong on 2016/12/2.
 *         搜索招聘
 */
public class JobTempSearchFragment extends BaseSwipeBackFragment {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    public static JobTempSearchFragment newInstance() {
        return new JobTempSearchFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_job_temp_search;
    }

    @Override
    protected Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    protected String getToolbarTitle() {
        return "搜索招聘";
    }
}
