package win.yulongsun.talents.ui.me;

import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.WriterException;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.OnClick;
import win.yulongsun.framework.util.android.app.QrCodeUtils;
import win.yulongsun.talents.base.BaseRootFragment;
import win.yulongsun.talents.event.StartBrotherEvent;
import win.yulongsun.talents.R;
import win.yulongsun.talents.config.CacheConstant;

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
                    Bitmap mBitmap = QrCodeUtils.encode(userCompanyId+"");
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
    protected void initView() {
        super.initView();
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
        mTvMeCompanyId.setText("公司编号：" + userCompanyId);
    }
}
