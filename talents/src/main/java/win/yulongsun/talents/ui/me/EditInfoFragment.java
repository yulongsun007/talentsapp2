package win.yulongsun.talents.ui.me;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.raizlabs.android.dbflow.sql.language.Select;

import butterknife.Bind;
import win.yulongsun.talents.base.BaseSwipeBackFragment;
import win.yulongsun.talents.R;
import win.yulongsun.talents.config.CacheConstant;
import win.yulongsun.talents.entity.User;

/**
 * Create By: yulongsun
 * Date at: 2016/8/30
 * Desc : 编辑个人信息
 */
public class EditInfoFragment extends BaseSwipeBackFragment {
    @Bind(R.id.toolbar)
    Toolbar   mToolbar;
    @Bind(R.id.iv_info_user_img)
    ImageView mIvInfoUserImg;
    @Bind(R.id.tv_info_user_name)
    TextView  mTvInfoUserName;
    @Bind(R.id.tv_info_user_gender)
    TextView  mTvInfoUserGender;
    @Bind(R.id.tv_info_company_name)
    TextView  mTvInfoCompanyName;
    @Bind(R.id.tv_info_company_addr)
    TextView  mTvInfoCompanyAddr;
    @Bind(R.id.tv_info_user_role)
    TextView  mTvInfoUserRole;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_edit_info;
    }

    @Override
    protected Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    protected String getToolbarTitle() {
        return "编辑个人信息";
    }

    @Override
    protected int getMenuResId() {
        return R.menu.menu_common_save;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (R.id.action_common_save == item.getItemId()) {
            // TODO: 2016/8/30  保存
            pop();
        }
        return super.onOptionsItemSelected(item);
    }

    public static EditInfoFragment newInstance() {
        return new EditInfoFragment();
    }

    @Override
    protected void initView() {
        super.initView();
        User user = new Select().from(User.class)
                .querySingle();
        String userName = _Cache.getAsString(CacheConstant.userName);
        String companyName = _Cache.getAsString(CacheConstant.companyName);
        String userGender = _Cache.getAsString(CacheConstant.userGender);
        String comapnyAddr = _Cache.getAsString(CacheConstant.comapnyAddr);
        String userImg = _Cache.getAsString(CacheConstant.userImg);
        String userRole = _Cache.getAsString(CacheConstant.userRole);
        mTvInfoUserName.setText(userName);
        mTvInfoUserGender.setText(userGender);
        mTvInfoCompanyName.setText(companyName);
        mTvInfoCompanyAddr.setText(comapnyAddr);
        mTvInfoUserRole.setText(user.user_role_id+"");

    }

}
