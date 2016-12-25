package win.yulongsun.talents.ui.stu.plan;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.orhanobut.logger.Logger;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;
import win.yulongsun.framework.adapter.SuperAdapter;
import win.yulongsun.framework.util.JsonUtil;
import win.yulongsun.framework.util.android.app.DialogUtil;
import win.yulongsun.framework.util.android.widget.ToastUtils;
import win.yulongsun.talents.R;
import win.yulongsun.talents.adapter.PlanListRVAdapter;
import win.yulongsun.talents.adapter.ResumeListRVAdapter;
import win.yulongsun.talents.base.CommonListFragment;
import win.yulongsun.talents.common.Constant;
import win.yulongsun.talents.entity.Plan;
import win.yulongsun.talents.entity.Resume;
import win.yulongsun.talents.http.resp.ResponseList;
import win.yulongsun.talents.http.resp.biz.PlanResponse;
import win.yulongsun.talents.http.resp.biz.ResumeResponse;
import win.yulongsun.talents.ui.referrer.plan.PlanEditFragment;


/**
 * @author sunyulong on 2016/12/25.
 *         我的培养计划
 */
public class MyPlanListFragment extends CommonListFragment {

    public static MyPlanListFragment newInstance() {
        return new MyPlanListFragment();
    }

    @Override
    protected String getToolbarTitle() {
        return "我的培养计划";
    }

    @Override
    protected SuperAdapter getAdapter() {
        return new PlanListRVAdapter(_mActivity, _mDatas, R.layout.item_plan_list);
    }

    @Override
    protected String getSubTag() {
        return MyPlanListFragment.class.getSimpleName();
    }

    @Override
    protected void initData() {
        super.initData();
        loadDataFromServer("user_plan_r/listStu", _mUser, PlanResponse.class);
    }

    @Override
    public void onItemClick(View itemView, int viewType, int position) {
        super.onItemClick(itemView, viewType, position);
        start(PlanEditFragment.newInstance(Constant.MODE_VALUE.LEARN, (Plan) _mDatas.get(position)));
    }

    @Override
    public void onItemLongClick(View itemView, int viewType, int position) {
        super.onItemLongClick(itemView, viewType, position);
        //查询所有简历
        OkHttpUtils.post()
                .url(Constant.URL + "resume/list")
                .addParams("create_by", String.valueOf(_mUser.user_id))
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                ResumeResponse resp = (ResumeResponse) JsonUtil.fromJson(response, ResumeResponse.class);
                final List<Resume> resumeList = resp.data;
                ResumeListRVAdapter adapter = new ResumeListRVAdapter(_mActivity, resumeList, R.layout.item_resume_list);
                new AlertDialog.Builder(_mActivity)
                        .setTitle("请选择需要投递的简历")
                        .setSingleChoiceItems(adapter, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Logger.d("onClick: " + which);
                                toCommitResume(resumeList.get(which), (Plan) _mDatas.get(which));
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });
    }

    private void toCommitResume(Resume resume, Plan plan) {
        DialogUtil.showLoading(_mActivity, "投递中...");
        OkHttpUtils.post()
                .url(Constant.URL + "user_plan_r/commitResume")
                .addParams("_id", String.valueOf(plan._id))
                .addParams("resume_id", String.valueOf(resume.resume_id))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        DialogUtil.dismissLoading();
                        ResponseList resp = (ResponseList) JsonUtil.fromJson(response, ResponseList.class);
                        ToastUtils.toastL(_mActivity, resp.msg);
                        if (resp.code == Constant.CODE.SUCCESS) {
                            onRefresh();
                        }
                    }
                });
    }
}
