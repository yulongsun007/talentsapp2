package win.yulongsun.talents.ui.login;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Call;
import win.yulongsun.framework.util.JsonUtil;
import win.yulongsun.framework.util.RegexUtil;
import win.yulongsun.framework.util.android.app.DialogUtil;
import win.yulongsun.framework.util.android.widget.ToastUtils;
import win.yulongsun.framework.util.java.lang.StringUtils;
import win.yulongsun.framework.widget.EditText.ClearButtonEditText;
import win.yulongsun.framework.widget.ImageView.CharacterView;
import win.yulongsun.talents.R;
import win.yulongsun.talents.base.BaseSwipeBackFragment;
import win.yulongsun.talents.common.Constant;
import win.yulongsun.talents.http.resp.IntegerResponse;
import win.yulongsun.talents.http.resp.biz.UserResponse;

/**
 * Create By : yulongsun
 * Date At : 16/8/30
 * Desc : 注册第一步:填写手机号 短信验证
 */
public class Register01Fragment extends BaseSwipeBackFragment {
    private static final int BUTTON_SEND_SUCCESS = 0;
    private static final int BUTTON_RESET        = 1;
    private              int i                   = 60;
    @Bind(R.id.toolbar)
    Toolbar             mToolbar;
    @Bind(R.id.et_mobile)
    ClearButtonEditText mEtMobile;
    @Bind(R.id.et_password)
    ClearButtonEditText mEtPassword;
    @Bind(R.id.tv_company_title)
    TextView            mTvCompanyTitle;
    @Bind(R.id.et_company_id)
    ClearButtonEditText mEtCompanyId;
    @Bind(R.id.rb_register_stu)
    RadioButton         mRbRegisterStu;
    @Bind(R.id.rb_register_referrer)
    RadioButton         mRbRegisterReferrer;
    @Bind(R.id.rb_register_hr)
    RadioButton         mRbRegisterHr;
    @Bind(R.id.ll_register01_company)
    LinearLayout        mLlRegister01Company;
    @Bind(R.id.rg_register_role)
    RadioGroup          mRgRegisterRole;
    @Bind(R.id.et_verify_code)
    ClearButtonEditText mEtVerifyCode;
    @Bind(R.id.btn_register_send_code)
    Button              mBtnRegisterSendCode;
    @Bind(R.id.cv_register_pg_1)
    CharacterView       mCvRegisterPg1;
    @Bind(R.id.cv_register_pg_2)
    CharacterView       mCvRegisterPg2;
    @Bind(R.id.cv_register_pg_3)
    CharacterView       mCvRegisterPg3;
    private Integer returnCode;

    String user_mobile;

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
            if (StringUtils.isEmpty(companyId)) {
                //新公司注册
                isNewCompany = true;
            } else {
                //绑定公司信息
                isNewCompany = false;
            }
            //code
            String code = mEtVerifyCode.getText().toString();
            if (StringUtils.isEmpty(code)) {
                ToastUtils.toastL(_mActivity, "请填写验证码");
                return false;
            }
            if (!code.equals(returnCode)) {
                ToastUtils.toastL(_mActivity, "验证码不正确");
                return false;
            }
            //token
            String user_token = mEtPassword.getText().toString();
            if (!StringUtils.checkLength(user_token, 3, 16)) {
                ToastUtils.toastS(_mActivity, "密码长度在6，16位之间");
                return false;
            }
            //company
            String company_id = mEtCompanyId.getText().toString();
            String user_role_id = "3";
            switch (mRgRegisterRole.getCheckedRadioButtonId()) {
                case R.id.rb_register_stu:
                    user_role_id = "3";
                    break;
                case R.id.rb_register_referrer:
                    user_role_id = "2";
                    break;
                case R.id.rb_register_hr:
                    user_role_id = "1";
                    break;
            }
            DialogUtil.showLoading(_mActivity, "注册中...");
            OkHttpUtils.post()
                    .url(Constant.URL + "user/add")
                    .addParams("user_mobile", user_mobile)
                    .addParams("user_token", user_token)
                    .addParams("user_role_id", user_role_id)
                    .addParams("user_company_id", company_id)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {

                        }

                        @Override
                        public void onResponse(String response, int id) {
                            DialogUtil.dismissLoading();
                            UserResponse resp = (UserResponse) JsonUtil.fromJson(response, UserResponse.class);
                            ToastUtils.toastL(_mActivity, resp.msg);
                            if (Constant.CODE.SUCCESS == resp.code) {
                                pop();
//                                start(Register02Fragment.newInstance(isNewCompany));
                            }

                        }
                    });


        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initView() {
        super.initView();
        mRgRegisterRole.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_register_stu:
                        mTvCompanyTitle.setText("学校名");
                        mEtCompanyId.setHint("请填写学校名称");
                        break;
                    case R.id.rb_register_referrer:
                        mTvCompanyTitle.setText("公司ID");
                        mEtCompanyId.setHint("请填写公司ID");
                        break;
                    case R.id.rb_register_hr:
                        mTvCompanyTitle.setText("公司ID");
                        mEtCompanyId.setHint("请填写公司ID(新注册公司不用填写)");
                        break;
                }
                mLlRegister01Company.setVisibility(View.VISIBLE);
            }
        });
    }


    //更新btn
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case BUTTON_SEND_SUCCESS:    //通过空消息改变主线程的倒计时
                    if (mBtnRegisterSendCode != null) {
                        mBtnRegisterSendCode.setText("重新发送(" + i + ")");
                    }
                    break;
                case BUTTON_RESET:    //重置Button
                    if (mBtnRegisterSendCode != null) {
                        mBtnRegisterSendCode.setText("获取验证码");
                        mBtnRegisterSendCode.setClickable(true);
                        i = 60;
                    }
                    break;
            }
        }
    };

    @OnClick(R.id.btn_register_send_code)
    public void onClick() {
        user_mobile = mEtMobile.getText().toString();
        if (!RegexUtil.isMobilePhoneNumber(user_mobile)) {
            ToastUtils.toastS(_mActivity, "请输入正确的手机号");
            return;
        }
        DialogUtil.showLoading(_mActivity, "发送中...");
        OkHttpUtils.post()
                .url(Constant.URL + "user/sendVerifyCode")
                .addParams("user_mobile", user_mobile)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        DialogUtil.dismissLoading();
                        Logger.json(response);
                        IntegerResponse resp = (IntegerResponse) JsonUtil.fromJson(response, IntegerResponse.class);
                        if (Constant.CODE.SUCCESS == resp.code) {
                            ToastUtils.toastL(_mActivity, "验证码发送成功");
                            returnCode = resp.data.get(0);
                            //update btn
                            updateBtn();
                        } else {
                            ToastUtils.toastL(_mActivity, resp.msg);
                        }
                    }
                });

    }

    //
    private void updateBtn() {
        mBtnRegisterSendCode.setClickable(false);
        mBtnRegisterSendCode.setText("重新发送(" + i + ")");
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (; i > 0; i--) {
                    mHandler.sendEmptyMessage(BUTTON_SEND_SUCCESS);//改变倒计时
                    if (i <= 0) {
                        break;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                mHandler.sendEmptyMessage(BUTTON_RESET);
            }
        }).start();
    }
}
