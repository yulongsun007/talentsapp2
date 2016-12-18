package win.yulongsun.talents.ui.me;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.WriterException;
import com.raizlabs.android.dbflow.sql.language.Select;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.OnClick;
import win.yulongsun.framework.util.android.app.DialogUtil;
import win.yulongsun.framework.util.android.app.QrCodeUtils;
import win.yulongsun.talents.R;
import win.yulongsun.talents.base.BaseRootFragment;
import win.yulongsun.talents.config.CacheConstant;
import win.yulongsun.talents.entity.User;
import win.yulongsun.talents.event.StartBrotherEvent;
import win.yulongsun.talents.ui.login.LoginActivity;

/**
 * Create By: yulongsun
 * Date at: 2016/8/30
 * Desc : 我的
 */
public class MeFragment extends BaseRootFragment {


    @Bind(R.id.toolbar)
    Toolbar  mToolbar;
    @Bind(R.id.tv_me_user_name)
    TextView mTvMeUserName;
    @Bind(R.id.tv_me_company_name)
    TextView mTvMeCompanyName;
    @Bind(R.id.tv_me_user_mobile)
    TextView mTvMeUserMobile;
    @Bind(R.id.tv_me_company_id)
    TextView mTvMeCompanyId;
    @Bind(R.id.tv_me_company_addr)
    TextView mTvMeCompanyAddr;
    String userCompanyId;

    public static MeFragment newInstance() {
        return new MeFragment();
    }


    @OnClick({R.id.ll_me_card, R.id.ll_me_info, R.id.ll_me_wallet, R.id.ll_me_setting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_me_card:
                try {
                    Bitmap mBitmap = QrCodeUtils.encode(userCompanyId + "");
                    ImageView mImageView = new ImageView(_mActivity);
                    mImageView.setImageBitmap(mBitmap);
                    new AlertDialog.Builder(_mActivity)
                            .setView(mImageView)
                            .show();
                } catch (WriterException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.ll_me_info:
                EventBus.getDefault().post(new StartBrotherEvent(EditInfoFragment.newInstance()));
                break;
            case R.id.ll_me_wallet:
                EventBus.getDefault().post(new StartBrotherEvent(WalletFragment.newInstance()));
                break;
            case R.id.ll_me_setting:
                EventBus.getDefault().post(new StartBrotherEvent(SettingFragment.newInstance()));
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_me;
    }

    @Override
    protected Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    protected String getToolbarTitle() {
        return "我的";
    }

    @Override
    protected int getMenuResId() {
        return R.menu.menu_common_logout;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_common_logout) {
            DialogUtil.showAlert(_mActivity, "退出", "您确定退出当前账户吗", "确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    User user = new Select().from(User.class).querySingle();
                    user.delete();
                    Intent intent = new Intent(_mActivity, LoginActivity.class);
                    startActivity(intent);
                    _mActivity.finish();
                }
            }, "取消", null);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initView() {
        super.initView();
        User user = new Select().from(User.class)
                .querySingle();

        String userName = _Cache.getAsString(CacheConstant.userName);
        String companyName = _Cache.getAsString(CacheConstant.companyName);
        String comapnyAddr = _Cache.getAsString(CacheConstant.comapnyAddr);
        String userMobile = _Cache.getAsString(CacheConstant.userMobile);
        userCompanyId = _Cache.getAsString(CacheConstant.userCompanyId);

        String userImg = _Cache.getAsString(CacheConstant.userImg);
        mTvMeUserName.setText(userName);
        mTvMeCompanyName.setText(companyName);
        mTvMeUserMobile.setText(userMobile);
        mTvMeCompanyAddr.setText(comapnyAddr);
        mTvMeCompanyId.setText("公司编号：" + user.user_company_id);
    }
}
