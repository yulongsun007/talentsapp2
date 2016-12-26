package win.yulongsun.talents.ui.referrer.plan;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
import win.yulongsun.framework.adapter.SuperAdapter;
import win.yulongsun.framework.util.JsonUtil;
import win.yulongsun.framework.util.android.widget.ToastUtils;
import win.yulongsun.framework.util.java.lang.StringUtils;
import win.yulongsun.talents.R;
import win.yulongsun.talents.adapter.ReferrerResumeListRVAdapter;
import win.yulongsun.talents.base.CommonListFragment;
import win.yulongsun.talents.common.Constant;
import win.yulongsun.talents.entity.Plan;
import win.yulongsun.talents.entity.Resume;
import win.yulongsun.talents.entity.UserPlanR;
import win.yulongsun.talents.http.resp.ResponseList;
import win.yulongsun.talents.http.resp.biz.UserPlanRResponse;
import win.yulongsun.talents.ui.stu.resume.ResumeEditFragment;

/**
 * @author sunyulong on 2016/12/16.
 *         选了某个培养计划的学生
 */
public class ReferrerStuListFragment extends CommonListFragment {

    public static final String KEY = "plan_id";
    private Plan mPlan;

    public static ReferrerStuListFragment newInstance(Plan plan) {
        ReferrerStuListFragment fragment = new ReferrerStuListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY, plan);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected String getToolbarTitle() {
        return "选课学生";
    }

    @Override
    protected SuperAdapter getAdapter() {
        return new ReferrerResumeListRVAdapter(_mActivity, _mDatas, R.layout.item_resume_list);
    }

    @Override
    protected void initView() {
        super.initView();
        _mTvCommonNoDataTip.setText("没有选课学生");
        mPlan = (Plan) getArguments().getSerializable(KEY);
    }

    @Override
    protected void initData() {
        super.initData();
        loadDataFromServer("user_plan_r/listReferrerSubStu", mPlan, UserPlanRResponse.class);
    }

    @Override
    protected String getSubTag() {
        return ReferrerStuListFragment.class.getSimpleName();
    }

    @Override
    public void onItemClick(View itemView, int viewType, int position) {
        super.onItemClick(itemView, viewType, position);
        UserPlanR userPlanR = (UserPlanR) _mDatas.get(position);
        Resume resume = userPlanR.resume;
        start(ResumeEditFragment.newInstance(Constant.MODE_VALUE.CHECK, resume));
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
                                .addParams("apply_status", String.valueOf(Constant.APPLY_STATUS.REFERRER_PASS))
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
                                .addParams("apply_status", String.valueOf(Constant.APPLY_STATUS.REFERRER_REJECT))
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
