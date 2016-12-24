package win.yulongsun.talents.ui.referrer.plan;

import android.view.View;

import org.greenrobot.eventbus.EventBus;

import win.yulongsun.framework.adapter.SuperAdapter;
import win.yulongsun.talents.R;
import win.yulongsun.talents.adapter.PlanListRVAdapter;
import win.yulongsun.talents.base.CommonListFragment;
import win.yulongsun.talents.common.Constant;
import win.yulongsun.talents.entity.Plan;
import win.yulongsun.talents.event.StartBrotherEvent;
import win.yulongsun.talents.http.resp.biz.PlanResponse;

/**
 * @author sunyulong on 2016/12/16.
 *         培养计划
 */
public class PlanListFragment extends CommonListFragment {
    @Override
    protected String getToolbarTitle() {
        return null;
    }

    @Override
    protected SuperAdapter getAdapter() {
        return new PlanListRVAdapter(_mActivity, _mDatas, R.layout.item_plan_list);
    }

    @Override
    protected String getSubTag() {
        return PlanListFragment.class.getSimpleName();
    }

    public static PlanListFragment newInstance() {
        return new PlanListFragment();
    }

    @Override
    protected void initView() {
        super.initView();
        _mToolbar.setVisibility(View.GONE);
    }

    @Override
    protected void initData() {
        super.initData();
        Plan plan = new Plan();
        plan.create_by = _mUser.user_id;
        loadDataFromServer("plan/list", plan, PlanResponse.class);
    }

    @Override
    public void onItemClick(View itemView, int viewType, int position) {
        super.onItemClick(itemView, viewType, position);
        EventBus.getDefault().post(new StartBrotherEvent(PlanEditFragment.newInstance(Constant.MODE_VALUE.EDIT, (Plan) _mDatas.get(position))));
    }
}
