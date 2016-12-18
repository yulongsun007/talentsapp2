package win.yulongsun.talents.ui.login;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import butterknife.Bind;
import win.yulongsun.framework.util.java.lang.StringUtils;
import win.yulongsun.framework.widget.EditText.ClearButtonEditText;
import win.yulongsun.talents.base.BaseSwipeBackFragment;
import win.yulongsun.talents.R;

/**
 * Create By : yulongsun
 * Date At : 16/8/30
 * Desc : 注册第一步:填写手机号 短信验证
 */
public class Register01Fragment extends BaseSwipeBackFragment {
    @Bind(R.id.toolbar)
    Toolbar             mToolbar;
    @Bind(R.id.et_mobile)
    ClearButtonEditText mEtMobile;
    @Bind(R.id.et_password)
    ClearButtonEditText mEtPassword;
    @Bind(R.id.et_company_id)
    ClearButtonEditText mEtCompanyId;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_register01;
    }

    @Override
    protected Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    protected String getToolbarTitle() {
        return "注册新用户";
    }

    public static Register01Fragment newInstance() {
        return new Register01Fragment();
    }

    @Override
    protected int getMenuResId() {
        return R.menu.menu_common_next;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (R.id.action_common_next == item.getItemId()) {
            boolean isNewCompany;
            String companyId = mEtCompanyId.getText().toString().trim();
            if(StringUtils.isEmpty(companyId)){
                //新公司注册
                isNewCompany=true;
            }else {
                //绑定公司信息
                isNewCompany=false;
            }
            start(Register02Fragment.newInstance(isNewCompany));
        }

        return super.onOptionsItemSelected(item);
    }


}
