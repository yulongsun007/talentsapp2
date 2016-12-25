package win.yulongsun.talents.ui.stu.resume;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;
import android.view.View;

import com.orhanobut.logger.Logger;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.Subscribe;

import okhttp3.Call;
import win.yulongsun.framework.adapter.SuperAdapter;
import win.yulongsun.framework.util.JsonUtil;
import win.yulongsun.framework.util.android.widget.ToastUtils;
import win.yulongsun.talents.R;
import win.yulongsun.talents.adapter.ResumeListRVAdapter;
import win.yulongsun.talents.base.CommonListFragment;
import win.yulongsun.talents.common.Constant;
import win.yulongsun.talents.entity.Resume;
import win.yulongsun.talents.event.ActionEvent;
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
    public void onItemLongClick(View itemView, int viewType, int position) {
        super.onItemLongClick(itemView, viewType, position);
        final Resume resume = (Resume) _mDatas.get(position);
        new AlertDialog.Builder(_mActivity)
                .setMessage("你确定删除" + resume.resume_name + "吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        OkHttpUtils.post()
                                .url(Constant.URL + "resume/delete")
                                .addParams("resume_id", String.valueOf(resume.resume_id))
                                .build()
                                .execute(new StringCallback() {
                                    @Override
                                    public void onError(Call call, Exception e, int id) {

                                    }

                                    @Override
                                    public void onResponse(String response, int id) {
                                        ResumeResponse resp = (ResumeResponse) JsonUtil.fromJson(response, ResumeResponse.class);
                                        ToastUtils.toastL(_mActivity, resp.msg);
                                        if (Constant.CODE.SUCCESS == resp.code) {
                                            initData();
                                        }
                                    }
                                });

                        dialog.dismiss();
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

    @Override
    protected int getMenuResId() {
        return R.menu.menu_common_add;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_common_add) {
            start(ResumeEditFragment.newInstance(Constant.MODE_VALUE.ADD, null));
        }
        return super.onOptionsItemSelected(item);
    }

    @Subscribe
    public void actionType(ActionEvent event) {
        Logger.d("onActionEvent: " + event.actionType);
        if (event.actionType == ActionEvent.UPDATE) {
            onRefresh();
        }
    }
}
