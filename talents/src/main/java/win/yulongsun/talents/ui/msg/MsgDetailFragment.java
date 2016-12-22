package win.yulongsun.talents.ui.msg;

import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;

import butterknife.Bind;
import butterknife.OnClick;
import me.yokeyword.fragmentation.SupportFragment;
import win.yulongsun.talents.R;
import win.yulongsun.talents.base.BaseChildFragment;
import win.yulongsun.talents.entity.Msg;
import win.yulongsun.talents.util.GTMsgUtils;

/**
 * @author sunyulong on 2016/12/18.
 *         消息详情
 */
public class MsgDetailFragment extends BaseChildFragment {
    @Bind(R.id.toolbar)
    Toolbar  mToolbar;
    @Bind(R.id.tv_msg_title)
    TextView mTvMsgTitle;
    @Bind(R.id.tv_msg_create_at)
    TextView mTvMsgCreateAt;
    @Bind(R.id.tv_msg_content)
    TextView mTvMsgContent;
    @Bind(R.id.tv_msg_replay_content)
    EditText mTvMsgReplayContent;
    @Bind(R.id.btn_msg_replay)
    Button   mBtnMsgReplay;

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
        return "消息";
    }

    public static SupportFragment newInstance() {
        return new MsgDetailFragment();
    }

    @Override
    protected void initView() {
        super.initView();

    }

    @OnClick(R.id.btn_msg_replay)
    public void onClick() {

        Msg msg = new Msg();
        msg.msg_from_id = 1;
        msg.msg_to_id = 2;
        msg.msg_title = "来自sunylong的消息";
        msg.msg_content = "lasd ";
        msg.msg_type = "聊天消息";
        msg.create_at = new Date();


        GTMsgUtils.pushMsgToSingle(msg);

//        OkHttpUtils.post().url(Constant.URL+"msg/pushMsgSingle")
//                .addParams("msg_from_id","1")
//                .addParams("msg_to_id","2")
//                .addParams("msg_title","来自sunylong的消息")
//                .addParams("msg_content","透传消息")
//                .addParams("msg_type","系统消息")
//                .build()
//                .execute(new StringCallback() {
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//
//                    }
//
//                    @Override
//                    public void onResponse(String response, int id) {
//
//                    }
//                });

    }
}
