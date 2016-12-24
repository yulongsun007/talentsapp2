package win.yulongsun.talents.ui.hr.talent;

import android.view.View;

import org.byteam.superadapter.SuperAdapter;
import org.greenrobot.eventbus.EventBus;

import win.yulongsun.talents.R;
import win.yulongsun.talents.adapter.TalentRVAdapter;
import win.yulongsun.talents.base.CommonListFragment;
import win.yulongsun.talents.event.StartBrotherEvent;

/**
 * Created by yulongsun
 * Date at 2016-8-16
 * Desc  人才页
 */
public class TalentListFragment extends CommonListFragment {
    public static TalentListFragment newInstance() {
        return new TalentListFragment();
    }

    @Override
    protected String getToolbarTitle() {
        return null;
    }

    @Override
    protected SuperAdapter getAdapter() {
        return new TalentRVAdapter(_mActivity, _mDatas, R.layout.item_fragment_list);
    }

    @Override
    protected String getSubTag() {
        return TalentListFragment.class.getSimpleName();
    }

    @Override
    protected void initData() {
        super.initData();

    }

    @Override
    public void onItemClick(View itemView, int viewType, int position) {
        super.onItemClick(itemView, viewType, position);
        EventBus.getDefault().post(new StartBrotherEvent(TalentDetailFragment.newInstance(position)));
    }
}
