package win.yulongsun.talents.ui.stu.plan;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

import butterknife.Bind;
import win.yulongsun.framework.util.android.widget.RecyclerViewDivider;
import win.yulongsun.talents.R;
import win.yulongsun.talents.adapter.StuPlanListRVAdapter;
import win.yulongsun.talents.base.BaseChildFragment;
import win.yulongsun.talents.entity.Plan;

/**
 * @author sunyulong on 2016/12/18.
 */
public class StuPlanListFragment extends BaseChildFragment {
    @Bind(R.id.toolbar)
    Toolbar      mToolbar;
    @Bind(R.id.recy_stu_plan_list)
    RecyclerView mRecyStuPlanList;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_stu_plan_list;
    }

    @Override
    protected Toolbar getToolbar() {
        return null;
    }

    @Override
    protected String getToolbarTitle() {
        return "培养计划";
    }

    public static StuPlanListFragment newInstance() {
        return new StuPlanListFragment();
    }

    @Override
    protected void initView() {
        super.initView();
        ArrayList<Plan> plans = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            plans.add(new Plan());
        }
        StuPlanListRVAdapter adpater = new StuPlanListRVAdapter(_mActivity, R.layout.item_stu_plan_list, plans);
        mRecyStuPlanList.addItemDecoration(new RecyclerViewDivider(_mActivity, LinearLayoutManager.VERTICAL, 10, ContextCompat.getColor(_mActivity, R.color.color_rv_divider)));
        mRecyStuPlanList.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRecyStuPlanList.setAdapter(adpater);
    }
}
