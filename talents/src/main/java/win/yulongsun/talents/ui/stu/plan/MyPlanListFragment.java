package win.yulongsun.talents.ui.stu.plan;

import win.yulongsun.framework.adapter.SuperAdapter;
import win.yulongsun.talents.base.CommonListFragment;

/**
 * @author sunyulong on 2016/12/25.
 * 我的培养计划
 */
public class MyPlanListFragment extends CommonListFragment{
    @Override
    protected String getToolbarTitle() {
        return "我的培养计划";
    }

    @Override
    protected SuperAdapter getAdapter() {
        return null;
    }

    @Override
    protected String getSubTag() {
        return MyPlanListFragment.class.getSimpleName();
    }
}
