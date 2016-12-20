package win.yulongsun.talents.ui.login;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.igexin.sdk.PushManager;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Call;
import win.yulongsun.framework.cache.ACache;
import win.yulongsun.framework.util.JsonUtil;
import win.yulongsun.framework.util.RegexUtil;
import win.yulongsun.framework.util.android.app.DialogUtil;
import win.yulongsun.framework.util.android.widget.ToastUtils;
import win.yulongsun.framework.util.java.lang.StringUtils;
import win.yulongsun.framework.widget.EditText.ClearButtonEditText;
import win.yulongsun.talents.R;
import win.yulongsun.talents.base.BaseRootFragment;
import win.yulongsun.talents.common.Constant;
import win.yulongsun.talents.entity.User;
import win.yulongsun.talents.http.resp.biz.UserResponse;
import win.yulongsun.talents.ui.main.MainActivity;

/**
 * Create By : yulongsun
 * Date At : 16/9/1
 * Desc : 登录页
 */
public class LoginFragment extends BaseRootFragment {
    @Bind(R.id.toolbar)
    Toolbar             mToolbar;
    @Bind(R.id.et_mobile)
    ClearButtonEditText mEtMobile;
    @Bind(R.id.et_password)
    ClearButtonEditText mEtPassword;
    @Bind(R.id.btn_login)
    Button              mBtnLogin;
    @Bind(R.id.tv_register)
    TextView            mTvRegister;
    @Bind(R.id.iv_login_weibo)
    ImageView           mIvLoginWeibo;
    @Bind(R.id.iv_login_qq)
    ImageView           mIvLoginQq;
    @Bind(R.id.iv_login_wechat)
    ImageView           mIvLoginWechat;


    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.common_login;
    }

    @Override
    protected Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    protected String getToolbarTitle() {
        return "登录";
    }


    @OnClick({R.id.btn_login, R.id.tv_register, R.id.iv_login_weibo, R.id.iv_login_qq, R.id.iv_login_wechat})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login://登录
                toLogin();
                break;
            case R.id.tv_register://注册
                start(Register01Fragment.newInstance());
                break;
            case R.id.iv_login_weibo:
                break;
            case R.id.iv_login_qq:
                break;
            case R.id.iv_login_wechat:
                break;
        }
    }

    private void toLogin() {
        String mobile = mEtMobile.getText().toString().trim();
        String password = mEtPassword.getText().toString().trim();
        if (!RegexUtil.isMobilePhoneNumber(mobile)) {
            ToastUtils.toastS(_mActivity, "请输入正确的手机号");
            return;
        }
        if (!StringUtils.checkLength(password, 3, 16)) {
            ToastUtils.toastS(_mActivity, "密码长度在6，16位之间");
            return;
        }
        DialogUtil.showLoading(_mActivity, DialogUtil.MSG_LOGINING);
        OkHttpUtils.post()
                .url(Constant.URL + "user/login")
                .addParams("user_mobile", mobile)
                .addParams("user_token", password)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtils.toastL(_mActivity, Constant.TOAST_MSG.CONNECT_ERROR);
                        DialogUtil.dismissLoading();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        UserResponse resp = (UserResponse) JsonUtil.fromJson(response, UserResponse.class);
                        if (resp.code == Constant.CODE.SUCCESS) {
                            User user = resp.data.get(0);
                            if (user != null) {
                                user.save();
                                ACache mCache = ACache.get(_mActivity);
                                mCache.put(Constant.isLogin, "1");
                                //绑定别名
                                PushManager.getInstance().bindAlias(_mActivity, String.valueOf(user.user_id));
                                //toMain
                                _mActivity.startActivity(new Intent(_mActivity, MainActivity.class));
                                _mActivity.finish();
                            } else {
                                ToastUtils.toastL(_mActivity, "没有获取到用户数据");
                            }

                        } else {
                            ToastUtils.toastL(_mActivity, resp.msg);
                        }
                        DialogUtil.dismissLoading();

                    }
                });


    }
}
