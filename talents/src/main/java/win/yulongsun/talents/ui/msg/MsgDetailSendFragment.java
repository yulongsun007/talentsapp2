package win.yulongsun.talents.ui.msg;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.Date;

import butterknife.Bind;
import butterknife.OnClick;
import me.yokeyword.fragmentation.SupportFragment;
import okhttp3.Call;
import win.yulongsun.framework.util.android.widget.ToastUtils;
import win.yulongsun.framework.util.java.lang.StringUtils;
import win.yulongsun.framework.util.java.util.DateTimeUtils;
import win.yulongsun.talents.R;
import win.yulongsun.talents.base.BaseSwipeBackFragment;
import win.yulongsun.talents.common.Constant;
import win.yulongsun.talents.entity.Msg;
import win.yulongsun.talents.entity.User;

/**
 * @author sunyulong on 2016/12/18.
 *         消息详情
 */
public class MsgDetailSendFragment extends BaseSwipeBackFragment {

    private static final String KEY_USER = "msg_user_key";
    @Bind(R.id.toolbar)
    Toolbar  mToolbar;
    @Bind(R.id.tv_msg_title)
    TextView mTvMsgTitle;
    @Bind(R.id.et_msg_send_title)
    EditText mEtMsgSendTitle;
    @Bind(R.id.tv_msg_send_create_at)
    TextView mTvMsgSendCreateAt;
    @Bind(R.id.et_msg_send_content)
    EditText mEtMsgSendContent;
    @Bind(R.id.btn_msg_send)
    Button   mBtnMsgSend;

    private User mToUser;
    private int  mMode;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_msg_send;
    }

    @Override
    protected Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    protected String getToolbarTitle() {
        return "消息";
    }

    public static SupportFragment newInstance() {
        return new MsgDetailSendFragment();
    }

    public static SupportFragment newInstance(int mode, User toUser) {
        MsgDetailSendFragment fragment = new MsgDetailSendFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("mode", mode);
        bundle.putSerializable(KEY_USER, toUser);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initView() {
        Bundle bundle = getArguments();
        mMode = bundle.getInt("mode");
        mToUser = (User) bundle.getSerializable(KEY_USER);
        super.initView();
    }

    @Override
    protected void initData() {
        super.initData();
        mTvMsgSendCreateAt.setText(DateTimeUtils.getCurrentTime());
        if (mMode == Constant.MODE_VALUE.RE) {
            mEtMsgSendTitle.setText("回复：来自" + _User.user_name + "的消息");
        } else {
            mEtMsgSendTitle.setText("来自" + _User.user_name + "的消息");
        }
    }

    @OnClick(R.id.btn_msg_send)
    public void onClick() {

        String title = mEtMsgSendTitle.getText().toString();
        if (StringUtils.isEmpty(title)) {
            ToastUtils.toastL(_mActivity, "消息标题不能为空");
            return;
        }
        String content = mEtMsgSendContent.getText().toString();
        if (StringUtils.isEmpty(content)) {
            ToastUtils.toastL(_mActivity, "消息内容不能为空");
            return;
        }

        Msg msg = new Msg();
        msg.msg_from_id = _User.user_id;
        msg.msg_to_id = mToUser.user_id;
        msg.msg_title = title;
        msg.msg_content = content;
        msg.msg_type = "聊天消息";
        msg.create_at = new Date();

//        GTMsgUtils.pushMsgToSingle(msg);


        OkHttpUtils.post().url(Constant.URL+"msg/pushMsgSingle")
                .addParams("msg_from_id",msg.msg_from_id+"")
                .addParams("msg_to_id",msg.msg_to_id+"")
                .addParams("msg_title",msg.msg_title)
                .addParams("msg_content",msg.msg_content)
                .addParams("msg_type",msg.msg_type)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {

                    }
                });
        pop();
    }


}
