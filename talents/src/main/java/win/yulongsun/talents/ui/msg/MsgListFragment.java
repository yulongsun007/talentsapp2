package win.yulongsun.talents.ui.msg;

import android.view.View;

import com.orhanobut.logger.Logger;
import com.raizlabs.android.dbflow.sql.language.Select;

import org.byteam.superadapter.SuperAdapter;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import win.yulongsun.talents.R;
import win.yulongsun.talents.adapter.MsgListAdapter;
import win.yulongsun.talents.base.CommonListFragment;
import win.yulongsun.talents.entity.Msg;
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

    @Override
    protected void initView() {
        super.initView();
        _mToolbar.setNavigationIcon(null);
        _mToolbar.setNavigationOnClickListener(null);
    }

    @Override
    protected void initData() {
        super.initData();
        _mDatas = new Select().from(Msg.class).queryList();
        msgIsNull();
    }

    private void msgIsNull() {
        if (_mDatas.isEmpty()) {
            _mLlCommonNoData.setVisibility(View.VISIBLE);
        } else {
            _mLlCommonNoData.setVisibility(View.GONE);
        }
        _mSrfCommonList.setRefreshing(false);
    }

    @Subscribe
    public void onMsgEvent(MsgEvent msgEvent) {
        Logger.d(msgEvent.getMsgEntity().toString());
        Msg msg = msgEvent.getMsgEntity();
        msg.save();
        _mAdapter.add(0, msg);
        msgIsNull();
    }


}
