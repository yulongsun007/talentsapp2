package win.yulongsun.talents.ui.hr.referrer;

import android.view.View;

import org.greenrobot.eventbus.EventBus;

import win.yulongsun.framework.adapter.SuperAdapter;
import win.yulongsun.talents.R;
import win.yulongsun.talents.adapter.ReferrerListRVAdapter;
import win.yulongsun.talents.base.CommonListFragment;
import win.yulongsun.talents.common.Constant;
import win.yulongsun.talents.entity.User;
import win.yulongsun.talents.event.StartBrotherEvent;
import win.yulongsun.talents.http.resp.biz.UserResponse;
import win.yulongsun.talents.ui.msg.MsgDetailSendFragment;

/**
 * @author sunyulong on 2016/11/29.
 *         <p>
 *         推荐人 列表
 */
public class ReferrerListFragment extends CommonListFragment {

    public static ReferrerListFragment INSTANCE = new ReferrerListFragment();

    @Override
    protected String getToolbarTitle() {
        return null;
    }

    @Override
    protected SuperAdapter getAdapter() {
        return new ReferrerListRVAdapter(_mActivity, _mDatas, R.layout.item_referrer_list);
    }

    @Override
    protected String getSubTag() {
        return ReferrerListFragment.class.getSimpleName();
    }

    public static ReferrerListFragment newInstance() {
        return INSTANCE;
    }

    @Override
    protected void initData() {
        super.initData();
        _mToolbar.setVisibility(View.GONE);
        User user = new User();
        user.user_company_id = _mUser.user_company_id;
        loadDataFromServer("user/listReferrer", user, UserResponse.class);
    }

    @Override
    public void onItemClick(View itemView, int viewType, int position) {
        super.onItemClick(itemView, viewType, position);
        User toUser = (User) _mDatas.get(position);
        EventBus.getDefault().post(new StartBrotherEvent(MsgDetailSendFragment.newInstance(Constant.MODE_VALUE.SEND, toUser)));
    }
}
