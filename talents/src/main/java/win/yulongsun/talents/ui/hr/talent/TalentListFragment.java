package win.yulongsun.talents.ui.hr.talent;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import okhttp3.Call;
import win.yulongsun.framework.adapter.SuperAdapter;
import win.yulongsun.framework.util.JsonUtil;
import win.yulongsun.framework.util.android.widget.ToastUtils;
import win.yulongsun.framework.util.java.lang.StringUtils;
import win.yulongsun.talents.R;
import win.yulongsun.talents.adapter.ReferrerResumeListRVAdapter;
import win.yulongsun.talents.base.CommonListFragment;
import win.yulongsun.talents.common.Constant;
import win.yulongsun.talents.entity.Resume;
import win.yulongsun.talents.entity.UserPlanR;
import win.yulongsun.talents.event.StartBrotherEvent;
import win.yulongsun.talents.http.resp.ResponseList;
import win.yulongsun.talents.http.resp.biz.UserPlanRResponse;
import win.yulongsun.talents.ui.stu.resume.ResumeEditFragment;

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
        return new ReferrerResumeListRVAdapter(_mActivity, _mDatas, R.layout.item_resume_list);
    }

    @Override
    protected String getSubTag() {
        return TalentListFragment.class.getSimpleName();
    }

    @Override
    protected void initData() {
        super.initData();
        _mToolbar.setVisibility(View.GONE);
        loadDataFromServer("user_plan_r/hrListStu", _mUser, UserPlanRResponse.class);
    }

    @Override
    public void onItemClick(View itemView, int viewType, int position) {
        super.onItemClick(itemView, viewType, position);
        UserPlanR userPlanR = (UserPlanR) _mDatas.get(0);
        Resume resume = userPlanR.resume;
        EventBus.getDefault().post(new StartBrotherEvent(ResumeEditFragment.newInstance(Constant.MODE_VALUE.CHECK, resume)));
    }

    @Override
    public void onItemLongClick(View itemView, int viewType, int position) {
        super.onItemLongClick(itemView, viewType, position);
        final UserPlanR userPlanR = (UserPlanR) _mDatas.get(position);
        final View dialogView = LayoutInflater.from(_mActivity).inflate(R.layout.dialog_referrer_edit, null);
        final EditText dialogMsg = (EditText) dialogView.findViewById(R.id.et_dialog_msg);
        new AlertDialog.Builder(_mActivity)
                .setTitle("推荐")
                .setView(dialogView)
                .setPositiveButton("同意", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        String apply_msg = dialogMsg.getText().toString();
                        if (StringUtils.isEmpty(apply_msg)) {
                            ToastUtils.toastL(_mActivity, "请输入操作理由");
                            return;
                        }

                        OkHttpUtils.post()
                                .url(Constant.URL + "user_plan_r/referrerDoResume")
                                .addParams("_id", String.valueOf(userPlanR._id))
                                .addParams("apply_status", String.valueOf(Constant.APPLY_STATUS.HR_PASS))
                                .addParams("apply_msg", apply_msg)
                                .build().execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {

                            }

                            @Override
                            public void onResponse(String response, int id) {
                                ResponseList resp = (ResponseList) JsonUtil.fromJson(response, ResponseList.class);
                                ToastUtils.toastL(_mActivity, resp.msg);
                                dialog.dismiss();
                                initData();
                            }
                        });
                    }
                })
                .setNeutralButton("拒绝", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        String apply_msg = dialogMsg.getText().toString();
                        if (StringUtils.isEmpty(apply_msg)) {
                            ToastUtils.toastL(_mActivity, "请输入操作理由");
                            return;
                        }

                        OkHttpUtils.post()
                                .url(Constant.URL + "user_plan_r/referrerDoResume")
                                .addParams("_id", String.valueOf(userPlanR._id))
                                .addParams("apply_status", String.valueOf(Constant.APPLY_STATUS.HR_REJECT))
                                .addParams("apply_msg", apply_msg)
                                .build().execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {

                            }

                            @Override
                            public void onResponse(String response, int id) {
                                ResponseList resp = (ResponseList) JsonUtil.fromJson(response, ResponseList.class);
                                ToastUtils.toastL(_mActivity, resp.msg);
                                dialog.dismiss();
                                initData();
                            }
                        });
                    }
                }).setNegativeButton("取消", null)
                .show();
    }
}
