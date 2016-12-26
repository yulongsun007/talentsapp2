package win.yulongsun.talents.ui.hr.talent;

import win.yulongsun.framework.adapter.SuperAdapter;
import win.yulongsun.talents.R;
import win.yulongsun.talents.adapter.ReferrerResumeListRVAdapter;
import win.yulongsun.talents.base.CommonListFragment;
import win.yulongsun.talents.http.resp.biz.UserPlanRResponse;

/**
 * @author sunyulong on 2016/12/26.
 */
public class TalentHistoryListFragment extends CommonListFragment {

    public static TalentHistoryListFragment newInstance() {
        return new TalentHistoryListFragment();
    }

    @Override
    protected String getToolbarTitle() {
        return "人才库";
    }

    @Override
    protected SuperAdapter getAdapter() {
        return new ReferrerResumeListRVAdapter(1,_mActivity, _mDatas, R.layout.item_resume_list);
    }

    @Override
    protected String getSubTag() {
        return TalentHistoryListFragment.class.getSimpleName();
    }

    @Override
    protected void initData() {
        super.initData();
        loadDataFromServer("user_plan_r/hrListStuHistory", _mUser, UserPlanRResponse.class);
    }
}
