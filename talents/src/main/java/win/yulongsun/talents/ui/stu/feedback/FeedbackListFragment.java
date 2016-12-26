package win.yulongsun.talents.ui.stu.feedback;

import win.yulongsun.framework.adapter.SuperAdapter;
import win.yulongsun.talents.R;
import win.yulongsun.talents.adapter.FeedbackListRVAdapter;
import win.yulongsun.talents.base.CommonListFragment;
import win.yulongsun.talents.http.resp.biz.UserPlanRResponse;

/**
 * @author sunyulong on 2016/12/25.
 */
public class FeedbackListFragment extends CommonListFragment {
    public static FeedbackListFragment newInstance() {
        return new FeedbackListFragment();
    }

    @Override
    protected String getToolbarTitle() {
        return "投递反馈";
    }

    @Override
    protected SuperAdapter getAdapter() {
        return new FeedbackListRVAdapter(_mActivity,_mDatas, R.layout.item_feedback_list);
    }

    @Override
    protected String getSubTag() {
        return FeedbackListFragment.class.getSimpleName();
    }

    @Override
    protected void initData() {
        super.initData();
        loadDataFromServer("user_plan_r/listCommit", _mUser, UserPlanRResponse.class);
    }
}
