package win.yulongsun.talents.ui.referrer.plan;

import android.os.Bundle;

import win.yulongsun.framework.adapter.SuperAdapter;
import win.yulongsun.talents.R;
import win.yulongsun.talents.adapter.ReferrerStuListRVAdapter;
import win.yulongsun.talents.base.CommonListFragment;

/**
 * @author sunyulong on 2016/12/16.
 *         选了某个培养计划的学生
 */
public class ReferrerStuListFragment extends CommonListFragment {

    public static ReferrerStuListFragment newInstance(Integer plan_id) {
        ReferrerStuListFragment fragment = new ReferrerStuListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("plan_id", plan_id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected String getToolbarTitle() {
        return "选课学生";
    }

    @Override
    protected SuperAdapter getAdapter() {
        return new ReferrerStuListRVAdapter(_mActivity, _mDatas, R.layout.item_referrer_stu_list);
    }

    @Override
    protected void initView() {
        super.initView();

    }

    @Override
    protected String getSubTag() {
        return ReferrerStuListFragment.class.getSimpleName();
    }
}
