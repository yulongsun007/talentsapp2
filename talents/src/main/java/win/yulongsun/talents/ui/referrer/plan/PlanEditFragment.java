package win.yulongsun.talents.ui.referrer.plan;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.orhanobut.logger.Logger;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Call;
import win.yulongsun.framework.adapter.OnItemClickListener;
import win.yulongsun.framework.util.JsonUtil;
import win.yulongsun.framework.util.android.widget.ToastUtils;
import win.yulongsun.framework.util.java.lang.StringUtils;
import win.yulongsun.framework.widget.RecyView.DividerItemDecoration;
import win.yulongsun.talents.R;
import win.yulongsun.talents.adapter.PlanClazzRVAdapter;
import win.yulongsun.talents.base.BaseSwipeBackFragment;
import win.yulongsun.talents.common.Constant;
import win.yulongsun.talents.entity.Clazz;
import win.yulongsun.talents.entity.Plan;
import win.yulongsun.talents.http.resp.biz.ClazzResponse;
import win.yulongsun.talents.http.resp.biz.PlanResponse;

import static win.yulongsun.framework.widget.RecyView.DividerItemDecoration.VERTICAL_LIST;

/**
 * @author sunyulong on 2016/12/16.
 *         添加培养计划
 */
public class PlanEditFragment extends BaseSwipeBackFragment implements OnItemClickListener {
    private static final String PLAN_LIST_EDIT_KEY = "plan_list_edit_key";
    @Bind(R.id.toolbar)
    Toolbar      mToolbar;
    @Bind(R.id.et_plan_edit_name)
    EditText     mEtPlanEditName;
    @Bind(R.id.et_plan_edit_desc)
    EditText     mEtPlanEditDesc;
    @Bind(R.id.et_plan_edit_content)
    EditText     mEtPlanEditContent;
    @Bind(R.id.lv_plan_edit_clazz)
    RecyclerView mRecyPlanEditClazz;
    @Bind(R.id.btn_plan_edit_add_clazz)
    Button       mBtnPlanEditAddClazz;
    private int mMode = Constant.MODE_VALUE.EDIT;
    private Plan               mPlan;
    private PlanClazzRVAdapter mAdapter;
    private List<Clazz>        mDatas= new ArrayList<>();

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_plan_edit;
    }

    @Override
    protected Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    protected String getToolbarTitle() {
        String type = "";
        switch (mMode) {
            case Constant.MODE_VALUE.ADD:
                type = "新增";
                break;
            case Constant.MODE_VALUE.EDIT:
                type = "编辑";
                break;
        }
        return type + "培养计划";
    }

    public static PlanEditFragment newInstance(int mode, Plan plan) {
        PlanEditFragment fragment = new PlanEditFragment();
        Bundle args = new Bundle();
        args.putInt(Constant.MODE_NAME, mode);
        if (mode == Constant.MODE_VALUE.EDIT) {
            args.putSerializable(PLAN_LIST_EDIT_KEY, plan);
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getMenuResId() {
        return R.menu.menu_plan_save;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_plan_save) {
            toSavePlan();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initView() {
        Bundle bundle = getArguments();
        mMode = bundle.getInt(Constant.MODE_NAME);
        super.initView();
        mPlan = (Plan) getArguments().getSerializable(PLAN_LIST_EDIT_KEY);

        if (mPlan != null && mMode == Constant.MODE_VALUE.EDIT) {
            mEtPlanEditName.setText(mPlan.plan_name);
            mEtPlanEditDesc.setText(mPlan.plan_desc);
            mEtPlanEditContent.setText(mPlan.plan_content);
            mDatas = mPlan.clazz;
        }
        //adapter
        mAdapter = new PlanClazzRVAdapter(_mActivity, mDatas, R.layout.item_plan_clazz_list);
        mRecyPlanEditClazz.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRecyPlanEditClazz.addItemDecoration(new DividerItemDecoration(_mActivity,VERTICAL_LIST));
        mRecyPlanEditClazz.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);

    }

    @Override
    protected void initData() {
        super.initData();
    }


    @OnClick(R.id.btn_plan_edit_add_clazz)
    public void onClick() {
        View view = LayoutInflater.from(_mActivity).inflate(R.layout.dialog_clazz_add, null);
        final EditText mEtDialogClazzName = (EditText) view.findViewById(R.id.et_dialog_clazz_name);
        final EditText mEtDialogClazzPriority = (EditText) view.findViewById(R.id.et_dialog_clazz_priority);
        final EditText mEtDialogClazzHour = (EditText) view.findViewById(R.id.et_dialog_clazz_hour);
        final EditText mEtDialogClazzScore = (EditText) view.findViewById(R.id.et_dialog_clazz_score);
        final EditText mEtDialogClazzBooks = (EditText) view.findViewById(R.id.et_dialog_clazz_books);

        new AlertDialog.Builder(_mActivity)
                .setTitle("添加课程")
                .setView(view)
                .setPositiveButton("保存", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        //添加课程
                        String clazz_name = mEtDialogClazzName.getText().toString();
                        if (StringUtils.isEmpty(clazz_name)) {
                            ToastUtils.toastL(_mActivity, "课程名称不能为空");
                            return;
                        }
                        String clazz_priority = mEtDialogClazzPriority.getText().toString();
                        if (StringUtils.isEmpty(clazz_priority)) {
                            ToastUtils.toastL(_mActivity, "课程优先级不能为空");
                            return;
                        }
                        String clazz_hour = mEtDialogClazzHour.getText().toString();
                        if (StringUtils.isEmpty(clazz_hour)) {
                            ToastUtils.toastL(_mActivity, "课时不能为空");
                            return;
                        }
                        String clazz_score = mEtDialogClazzScore.getText().toString();
                        if (StringUtils.isEmpty(clazz_score)) {
                            ToastUtils.toastL(_mActivity, "学分不能为空");
                            return;
                        }
                        String clazz_books = mEtDialogClazzBooks.getText().toString();
                        if (StringUtils.isEmpty(clazz_books)) {
                            ToastUtils.toastL(_mActivity, "推荐书籍不能为空");
                            return;
                        }
                        OkHttpUtils.post()
                                .url(Constant.URL + "clazz/add")
                                .addParams("clazz_name", clazz_name)
                                .addParams("clazz_priority", clazz_priority)
                                .addParams("clazz_hour", clazz_hour)
                                .addParams("clazz_score", clazz_score)
                                .addParams("clazz_books", clazz_books)
                                .addParams("plan_id", String.valueOf(mPlan.plan_id))
                                .build()
                                .execute(new StringCallback() {
                                    @Override
                                    public void onError(Call call, Exception e, int id) {

                                    }

                                    @Override
                                    public void onResponse(String response, int id) {
                                        ClazzResponse resp = (ClazzResponse) JsonUtil.fromJson(response, ClazzResponse.class);
                                        ToastUtils.toastL(_mActivity, resp.msg);
                                        if (resp.code == Constant.CODE.SUCCESS) {
                                            //success
                                            dialog.dismiss();
                                            Logger.json(response);
                                            mDatas = resp.data;
                                            mAdapter.replaceAll(mDatas);
                                        }
                                    }
                                });
                    }
                })
                .setCancelable(false)
                .setNegativeButton("取消", null)
                .show();

    }


    //保存培养计划
    private void toSavePlan() {
        String plan_name = mEtPlanEditName.getText().toString();
        String plan_desc = mEtPlanEditDesc.getText().toString();
        String plan_content = mEtPlanEditContent.getText().toString();
        if (StringUtils.isEmpty(plan_name)) {
            ToastUtils.toastL(_mActivity, "培养计划名称不能为空");
            return;
        }
        if (StringUtils.isEmpty(plan_desc)) {
            ToastUtils.toastL(_mActivity, "培养计划标语不能为空");
            return;
        }
        if (StringUtils.isEmpty(plan_content)) {
            ToastUtils.toastL(_mActivity, "培养计划主要内能为空");
            return;
        }

        OkHttpUtils.post()
                .url(Constant.URL + "plan/update")
                .addParams("plan_id", String.valueOf(mPlan.plan_id))
                .addParams("plan_name", plan_name)
                .addParams("plan_desc", plan_desc)
                .addParams("plan_content", plan_content)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        PlanResponse resp = (PlanResponse) JsonUtil.fromJson(response, PlanResponse.class);
                        ToastUtils.toastL(_mActivity, resp.msg);
                        if (resp.code == Constant.CODE.SUCCESS) {
                            pop();
                        }
                    }
                });


    }


    @Override
    public void onItemClick(View itemView, int viewType, final int position) {
        new AlertDialog.Builder(_mActivity)
                .setTitle("删除提示")
                .setMessage("您确定删除" + mDatas.get(position).clazz_name + "吗?")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        OkHttpUtils.post()
                                .url(Constant.URL + "clazz/delete")
                                .addParams("plan_id", String.valueOf(mPlan.plan_id))
                                .addParams("clazz_id", String.valueOf(mDatas.get(position).clazz_id))
                                .build()
                                .execute(new StringCallback() {
                                    @Override
                                    public void onError(Call call, Exception e, int id) {

                                    }

                                    @Override
                                    public void onResponse(String response, int id) {
                                        ClazzResponse resp = (ClazzResponse) JsonUtil.fromJson(response, ClazzResponse.class);
                                        ToastUtils.toastL(_mActivity, resp.msg);
                                        if (resp.code == Constant.CODE.SUCCESS) {
                                            Logger.json(response);
                                            mDatas = resp.data;
                                            mAdapter.replaceAll(mDatas);
                                        }
                                    }
                                });
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }
}
