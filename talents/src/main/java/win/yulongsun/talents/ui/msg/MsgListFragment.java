package win.yulongsun.talents.ui.msg;

import android.view.View;

import com.orhanobut.logger.Logger;

import org.byteam.superadapter.SuperAdapter;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import win.yulongsun.talents.R;
import win.yulongsun.talents.adapter.MsgListAdapter;
import win.yulongsun.talents.base.CommonListFragment;
import win.yulongsun.talents.event.MsgEvent;
import win.yulongsun.talents.event.StartBrotherEvent;

/**
 * Create By : yulongsun
 * Date At : 16/8/30
 * Desc : 消息
 */
public class MsgListFragment extends CommonListFragment {

    public static MsgListFragment newInstance() {
        return new MsgListFragment();
    }


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

    @Override
    public void onItemClick(View itemView, int viewType, int position) {
        super.onItemClick(itemView, viewType, position);
        EventBus.getDefault().post(new StartBrotherEvent(MsgDetailFragment.newInstance()));
    }

    @Subscribe
    public void onMsgEvent(MsgEvent msgEvent) {
        Logger.d(msgEvent.getMsgEntity().toString());
        _mAdapter.add(0, msgEvent.getMsgEntity());
    }


}
