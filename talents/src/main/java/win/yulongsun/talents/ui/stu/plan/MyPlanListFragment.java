package win.yulongsun.talents.ui.stu.plan;

import android.view.View;

import win.yulongsun.framework.adapter.SuperAdapter;
import win.yulongsun.talents.R;
import win.yulongsun.talents.adapter.PlanListRVAdapter;
import win.yulongsun.talents.base.CommonListFragment;
import win.yulongsun.talents.common.Constant;
import win.yulongsun.talents.entity.Plan;
import win.yulongsun.talents.http.resp.biz.PlanResponse;
import win.yulongsun.talents.ui.referrer.plan.PlanEditFragment;


/**
 * @author sunyulong on 2016/12/25.
 *         我的培养计划
 */
public class MyPlanListFragment extends CommonListFragment {

    public static MyPlanListFragment newInstance() {
        return new MyPlanListFragment();
    }

    @Override
    protected String getToolbarTitle() {
        return "我的培养计划";
    }

    @Override
    protected SuperAdapter getAdapter() {
        return new PlanListRVAdapter(_mActivity, _mDatas, R.layout.item_plan_list);
    }

    @Override
    protected String getSubTag() {
        return MyPlanListFragment.class.getSimpleName();
    }

    @Override
    protected void initData() {
        super.initData();
        loadDataFromServer("user_plan_r/listStu", _mUser, PlanResponse.class);
    }

    @Override
    public void onItemClick(View itemView, int viewType, int position) {
        super.onItemClick(itemView, viewType, position);
        start(PlanEditFragment.newInstance(Constant.MODE_VALUE.LEARN, (Plan) _mDatas.get(position)));
    }
}
