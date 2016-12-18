package win.yulongsun.talents.ui.me;

import android.support.v7.widget.Toolbar;

import butterknife.Bind;
import win.yulongsun.talents.base.BaseSwipeBackFragment;
import win.yulongsun.talents.R;

/**
 * @author sunyulong on 2016/12/11.
 *         钱包
 */
public class WalletFragment extends BaseSwipeBackFragment {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    public static WalletFragment newInstance() {
        return new WalletFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_wallet;
    }

    @Override
    protected Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    protected String getToolbarTitle() {
        return "钱包";
    }

}
