package win.yulongsun.talents.ui.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import butterknife.Bind;
import win.yulongsun.talents.base.BaseSwipeBackFragment;
import win.yulongsun.talents.R;

/**
 * Create By : yulongsun
 * Date At : 16/9/1
 * Desc :
 */
public class Register02Fragment extends BaseSwipeBackFragment {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    private static final String  IS_NEW_COMPANY = "isNewCompany";
    private              boolean isNewCompany   = false;

    public static Register02Fragment newInstance(boolean isNewCompany) {
        Register02Fragment mFragment = new Register02Fragment();
        Bundle mBundle = new Bundle();
        mBundle.putBoolean(IS_NEW_COMPANY, isNewCompany);
        mFragment.setArguments(mBundle);
        return mFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isNewCompany = getArguments().getBoolean(IS_NEW_COMPANY);
    }

    @Override
    protected int getLayoutResId() {
        if (isNewCompany) {
            return R.layout.fragment_register02_add_company;
        } else {
            return R.layout.fragment_register02_status;
        }
    }

    @Override
    protected int getMenuResId() {
        return R.menu.menu_common_save;
    }

    @Override
    protected Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    protected String getToolbarTitle() {
        if (isNewCompany) {
            return "填写公司信息";
        } else {
            return "注册状态";
        }

    }

    @Override
    protected void initView() {
        super.initView();
    }
}
