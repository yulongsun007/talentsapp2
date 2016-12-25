package win.yulongsun.talents.ui.stu.resume;

import android.view.MenuItem;
import android.view.View;

import win.yulongsun.framework.adapter.SuperAdapter;
import win.yulongsun.talents.R;
import win.yulongsun.talents.adapter.ResumeListRVAdapter;
import win.yulongsun.talents.base.CommonListFragment;
import win.yulongsun.talents.common.Constant;
import win.yulongsun.talents.entity.Resume;
import win.yulongsun.talents.http.resp.biz.ResumeResponse;

/**
 * @author sunyulong on 2016/12/25.
 */
public class ResumeListFragment extends CommonListFragment {
    public static ResumeListFragment newInstance() {
        return new ResumeListFragment();
    }

    @Override
    protected String getToolbarTitle() {
        return "简历列表";
    }

    @Override
    protected SuperAdapter getAdapter() {
        return new ResumeListRVAdapter(_mActivity, _mDatas, R.layout.item_resume_list);
    }

    @Override
    protected String getSubTag() {
        return ResumeListFragment.class.getSimpleName();
    }

    @Override
    protected void initData() {
        super.initData();
        Resume resume = new Resume();
        resume.create_by = _mUser.user_id;
        loadDataFromServer("resume/list", resume, ResumeResponse.class);
    }

    @Override
    public void onItemClick(View itemView, int viewType, int position) {
        super.onItemClick(itemView, viewType, position);
        start(ResumeEditFragment.newInstance(Constant.MODE_VALUE.EDIT, (Resume) _mDatas.get(position)));
    }

    @Override
    protected int getMenuResId() {
        return R.menu.menu_common_add;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_common_add) {
            start(ResumeEditFragment.newInstance(Constant.MODE_VALUE.ADD,null));
        }
        return super.onOptionsItemSelected(item);
    }
}
