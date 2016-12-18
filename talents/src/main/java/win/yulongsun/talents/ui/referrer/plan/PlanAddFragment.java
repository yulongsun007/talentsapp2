package win.yulongsun.talents.ui.referrer.plan;

import android.support.v7.widget.Toolbar;

import win.yulongsun.talents.R;
import win.yulongsun.talents.base.BaseChildFragment;

/**
 * @author sunyulong on 2016/12/16.
 */
public class PlanAddFragment extends BaseChildFragment {
    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_plan_list;
    }

    @Override
    protected Toolbar getToolbar() {
        return null;
    }

    @Override
    protected String getToolbarTitle() {
        return null;
    }

    public static PlanAddFragment newInstance(){
        return new PlanAddFragment();
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void initData() {
        super.initData();
    }
}
