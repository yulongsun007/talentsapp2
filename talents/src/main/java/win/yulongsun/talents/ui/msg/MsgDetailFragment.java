package win.yulongsun.talents.ui.msg;

import android.support.v7.widget.Toolbar;

import butterknife.Bind;
import me.yokeyword.fragmentation.SupportFragment;
import win.yulongsun.talents.R;
import win.yulongsun.talents.base.BaseSwipeBackFragment;

/**
 * @author sunyulong on 2016/12/18.
 *         消息详情
 */
public class MsgDetailFragment extends BaseSwipeBackFragment {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_msg_detail;
    }

    @Override
    protected Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    protected String getToolbarTitle() {
        return "消息";
    }

    public static SupportFragment newInstance() {
        return new MsgDetailFragment();
    }

}
