package win.yulongsun.talents.ui.msg;

import android.view.View;

import com.raizlabs.android.dbflow.sql.language.Select;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import win.yulongsun.framework.adapter.SuperAdapter;
import win.yulongsun.talents.R;
import win.yulongsun.talents.adapter.MsgListAdapter;
import win.yulongsun.talents.base.CommonListFragment;
import win.yulongsun.talents.entity.Msg;
import win.yulongsun.talents.entity.Msg_Table;
import win.yulongsun.talents.event.MsgEvent;
import win.yulongsun.talents.event.StartBrotherEvent;

/**
 * Create By : yulongsun
 * Date At : 16/8/30
 * Desc : 消息
 */
public class MsgListFragment extends CommonListFragment {

    private Msg mMsg;

    @Override
    protected String getToolbarTitle() {
        return "消息";
    }

    @Override
    protected SuperAdapter getAdapter() {
        return new MsgListAdapter(_mActivity, _mDatas, R.layout.item_msg_list);
    }

    @Override
    protected String getSubTag() {
        return MsgListFragment.class.getSimpleName();
    }

    public static MsgListFragment newInstance() {
        return new MsgListFragment();
    }

    @Override
    protected void initView() {
        super.initView();
        _mTvCommonNoDataTip.setText("没有消息");
        _mToolbar.setNavigationIcon(null);
    }

    @Override
    public void onItemClick(View itemView, int viewType, int position) {
        super.onItemClick(itemView, viewType, position);
        mMsg = (Msg) _mDatas.get(position);
        EventBus.getDefault().post(new StartBrotherEvent(MsgDetailFragment.newInstance(mMsg)));
    }

    @Override
    protected void initData() {
        super.initData();
        List<Msg> msgList = new Select().from(Msg.class).orderBy(Msg_Table.msg_id, false).queryList();
        _mDatas = msgList;
        _mAdapter.replaceAll(_mDatas);
        if (_mDatas.isEmpty()) {
            _mLlCommonNoData.setVisibility(View.VISIBLE);
        } else {
            _mLlCommonNoData.setVisibility(View.GONE);
        }
    }

    @Subscribe
    public void onMsgEvent(MsgEvent event) {
        initData();
    }

}
