package win.yulongsun.talents.ui.referrer.job;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import win.yulongsun.talents.R;
import win.yulongsun.talents.base.BaseSwipeBackFragment;
import win.yulongsun.talents.event.StartBrotherEvent;
import win.yulongsun.talents.ui.referrer.plan.PlanAddFragment;

/**
 * @author sunyulong on 2016/12/16.
 */
public class JobTempDetailFragment extends BaseSwipeBackFragment {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.btn_job_temp_add_plan)
    Button  mBtnJobTempAddPlan;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_job_temp_detail;
    }

    @Override
    protected Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    protected String getToolbarTitle() {
        return "职位详情";
    }

//    @Override
//    protected int getMenuResId() {
//        return R.menu.menu_job_temp_detail;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.action_job_temp_collect) {
//            ToastUtils.toastL(_mActivity, "收藏成功");
//        }
//        return super.onOptionsItemSelected(item);
//    }

    public static JobTempDetailFragment newInstance() {
        return new JobTempDetailFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.btn_job_temp_add_plan)
    public void onClick() {
        EventBus.getDefault().post(new StartBrotherEvent(PlanAddFragment.newInstance()));
    }
}
