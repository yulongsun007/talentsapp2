package win.yulongsun.talents.ui.referrer.plan;

import android.os.Bundle;
import android.view.View;

import org.greenrobot.eventbus.EventBus;

import win.yulongsun.framework.adapter.SuperAdapter;
import win.yulongsun.talents.R;
import win.yulongsun.talents.adapter.PlanListRVAdapter;
import win.yulongsun.talents.base.CommonListFragment;
import win.yulongsun.talents.common.Constant;
import win.yulongsun.talents.entity.JobTemplate;
import win.yulongsun.talents.entity.Plan;
import win.yulongsun.talents.event.StartBrotherEvent;
import win.yulongsun.talents.http.resp.biz.PlanResponse;

/**
 * @author sunyulong on 2016/12/16.
 *         培养计划
 */
public class PlanListFragment extends CommonListFragment {

    public static final String PLAN_LIST_KEY = "plan_list_key";

    public Integer user_role_id;

    @Override
    protected String getToolbarTitle() {
        return "培养计划";
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

    public static PlanListFragment newInstance(JobTemplate jobTemplate) {
        PlanListFragment fragment = new PlanListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(PLAN_LIST_KEY, jobTemplate);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected void initData() {
        super.initData();
        user_role_id = _mUser.user_role_id;
        Plan plan = new Plan();
        if (user_role_id == Constant.ROLE.REFERRER) {
            _mToolbar.setVisibility(View.GONE);
            //查询推荐人的所有培养计划
            plan.create_by = _mUser.user_id;
        } else if (user_role_id == Constant.ROLE.STU) {
            _mToolbar.setVisibility(View.VISIBLE);
            //根据招聘，查询对应的所有培养加护
            JobTemplate jobTemplate = (JobTemplate) getArguments().getSerializable(PLAN_LIST_KEY);
            if (jobTemplate == null) {
                return;
            }
            plan.job_template_id = jobTemplate.tmp_id;
        }
        loadDataFromServer("plan/list", plan, PlanResponse.class);
    }

    @Override
    public void onItemClick(View itemView, int viewType, int position) {
        super.onItemClick(itemView, viewType, position);
        if (user_role_id == Constant.ROLE.REFERRER) {
            EventBus.getDefault().post(new StartBrotherEvent(PlanEditFragment.newInstance(Constant.MODE_VALUE.EDIT, (Plan) _mDatas.get(position), null)));
        }else if (user_role_id == Constant.ROLE.STU) {
            EventBus.getDefault().post(new StartBrotherEvent(PlanEditFragment.newInstance(Constant.MODE_VALUE.SELECT, (Plan) _mDatas.get(position), null)));
        }
    }
}
