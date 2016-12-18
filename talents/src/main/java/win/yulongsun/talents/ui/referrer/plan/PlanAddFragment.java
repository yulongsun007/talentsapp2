package win.yulongsun.talents.ui.referrer.plan;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import win.yulongsun.talents.R;
import win.yulongsun.talents.adapter.PlanClazzAdapter;
import win.yulongsun.talents.base.BaseChildFragment;
import win.yulongsun.talents.entity.Clazz;

/**
 * @author sunyulong on 2016/12/16.
 *         添加培养计划
 */
public class PlanAddFragment extends BaseChildFragment {
    @Bind(R.id.toolbar)
    Toolbar  mToolbar;
    @Bind(R.id.lv_clazz)
    ListView mLvClazz;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_plan_add;
    }

    @Override
    protected Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    protected String getToolbarTitle() {
        return "发布培养计划";
    }

    public static PlanAddFragment newInstance() {
        return new PlanAddFragment();
    }

    @Override
    protected int getMenuResId() {
        return 0;
    }

    @Override
    protected void initView() {
        super.initView();
        ArrayList<Clazz> clazzs = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            clazzs.add(new Clazz());
        }
        PlanClazzAdapter adapter = new PlanClazzAdapter(_mActivity, clazzs, R.layout.item_plan_clazz_list);
        mLvClazz.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        super.initData();
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
}
