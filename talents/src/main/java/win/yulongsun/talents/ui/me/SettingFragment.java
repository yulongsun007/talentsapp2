package win.yulongsun.talents.ui.me;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.igexin.sdk.PushManager;
import com.raizlabs.android.dbflow.sql.language.Select;

import butterknife.Bind;
import butterknife.OnClick;
import win.yulongsun.framework.util.android.app.DialogUtil;
import win.yulongsun.talents.R;
import win.yulongsun.talents.base.BaseSwipeBackFragment;
import win.yulongsun.talents.entity.User;
import win.yulongsun.talents.ui.login.LoginActivity;

/**
 * @author sunyulong on 2016/12/11.
 *         设置
 */
public class SettingFragment extends BaseSwipeBackFragment {

    @Bind(R.id.toolbar)
    Toolbar      mToolbar;
    @Bind(R.id.ll_setting_logout)
    LinearLayout mLlSettingLogout;

    public static SettingFragment newInstance() {
        return new SettingFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_setting;
    }

    @Override
    protected Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    protected String getToolbarTitle() {
        return "设置";
    }

    @OnClick(R.id.ll_setting_logout)
    public void onClick() {
        DialogUtil.showAlert(_mActivity, "退出", "您确定退出当前账户吗", "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                User user = new Select().from(User.class).querySingle();
                if (user != null) {
                    PushManager.getInstance().unBindAlias(_mActivity, String.valueOf(user.user_id), false);
                    user.delete();
                }
                Intent intent = new Intent(_mActivity, LoginActivity.class);
                startActivity(intent);
                _mActivity.finish();
            }
        }, "取消", null);
    }
}
