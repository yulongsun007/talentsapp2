package win.yulongsun.talents.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.orhanobut.logger.Logger;
import com.raizlabs.android.dbflow.annotation.NotNull;
import com.sw926.imagefileselector.ErrorResult;
import com.sw926.imagefileselector.ImageFileSelector;

import butterknife.Bind;
import win.yulongsun.framework.image.ImageLoadManager;
import win.yulongsun.framework.util.android.widget.ToastUtils;
import win.yulongsun.framework.widget.EditText.ClearButtonEditText;
import win.yulongsun.talents.R;
import win.yulongsun.talents.base.BaseSwipeBackFragment;

/**
 * Create By : yulongsun
 * Date At : 16/9/1
 * Desc :
 */
public class Register02Fragment extends BaseSwipeBackFragment {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    private static final String IS_NEW_COMPANY = "isNewCompany";
    @Bind(R.id.et_company_logo)
    ImageView           mEtCompanyLogo;
    @Bind(R.id.et_company_name)
    ClearButtonEditText mEtCompanyName;
    @Bind(R.id.et_company_addr)
    ClearButtonEditText mEtCompanyAddr;
    @Bind(R.id.et_company_size)
    ClearButtonEditText mEtCompanySize;
    @Bind(R.id.et_company_contact)
    ClearButtonEditText mEtCompanyContact;
    private boolean isNewCompany = false;
    private ImageFileSelector mImageFileSelector;
    String company_logo;

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
        mImageFileSelector = new ImageFileSelector(_mActivity);
        mImageFileSelector.setOutPutImageSize(600, 600);
        mImageFileSelector.setCallback(new ImageFileSelector.Callback() {
            @Override
            public void onError(@NotNull ErrorResult errorResult) {
                Logger.e(errorResult.toString());
                switch (errorResult) {
                    case permissionDenied:
                        break;
                    case canceled:
                        break;
                    case error:
                        ToastUtils.toastL(_mActivity, "获取图片失败");
                        break;
                }
            }

            @Override
            public void onSuccess(@NotNull String file) {
                company_logo = file;
                Logger.d("onSuccess: " + file);
                ImageLoadManager.getInstance().load(file).into(mEtCompanyLogo);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mImageFileSelector.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mImageFileSelector.onSaveInstanceState(outState);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mImageFileSelector.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
