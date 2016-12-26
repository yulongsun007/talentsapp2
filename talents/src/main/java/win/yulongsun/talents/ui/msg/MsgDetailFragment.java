package win.yulongsun.talents.ui.msg;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.OnClick;
import me.yokeyword.fragmentation.SupportFragment;
import win.yulongsun.talents.R;
import win.yulongsun.talents.base.BaseSwipeBackFragment;
import win.yulongsun.talents.common.Constant;
import win.yulongsun.talents.entity.Msg;
import win.yulongsun.talents.entity.User;
import win.yulongsun.talents.event.StartBrotherEvent;

/**
 * @author sunyulong on 2016/12/18.
 *         消息详情
 */
public class MsgDetailFragment extends BaseSwipeBackFragment {


    @Bind(R.id.toolbar)
    Toolbar  mToolbar;
    @Bind(R.id.et_msg_detail_title)
    TextView mEtMsgDetailTitle;
    @Bind(R.id.tv_msg_detail_create_at)
    TextView mTvMsgDetailCreateAt;
    @Bind(R.id.et_msg_detail_content)
    TextView mEtMsgDetailContent;
    @Bind(R.id.btn_msg_detail_replay)
    Button   mBtnMsgDetailReplay;
    private Msg mMsg;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_msg_detail;
    }

    @Override
    protected Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    protected String getToolbarTitle() {
        return "消息详情";
    }

    public static SupportFragment newInstance(Msg msg) {
        MsgDetailFragment fragment = new MsgDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("msg", msg);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initData() {
        super.initData();
        mMsg = (Msg) getArguments().getSerializable("msg");
        mEtMsgDetailTitle.setText(mMsg.msg_title);
        mTvMsgDetailCreateAt.setText(mMsg.create_at.getHours() + ":" + mMsg.create_at.getMinutes());
        mEtMsgDetailContent.setText(mMsg.msg_content);

    }

    @OnClick(R.id.btn_msg_detail_replay)
    public void onClick() {
        User toUser = new User();
        toUser.user_id = mMsg.msg_from_id;
        EventBus.getDefault().post(new StartBrotherEvent(MsgDetailSendFragment.newInstance(Constant.MODE_VALUE.RE, toUser)));
    }
}
