package win.yulongsun.talents.ui.hr.talent;

import android.support.v7.widget.Toolbar;

import butterknife.Bind;
import win.yulongsun.talents.base.BaseSwipeBackFragment;
import win.yulongsun.talents.R;

/**
 * @author sunyulong on 2016/11/28.
 *
 * 人才详情
 */
public class TalentDetailFragment extends BaseSwipeBackFragment {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    static TalentDetailFragment newInstance(int position) {
        return new TalentDetailFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_talent_detail;
    }

    @Override
    protected Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    protected String getToolbarTitle() {
        return "";
    }

}
