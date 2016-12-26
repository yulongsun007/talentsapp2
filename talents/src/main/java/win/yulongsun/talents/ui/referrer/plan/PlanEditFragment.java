package win.yulongsun.talents.ui.referrer.plan;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

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
import win.yulongsun.framework.util.android.app.DialogUtil;
import win.yulongsun.framework.util.android.widget.ToastUtils;
import win.yulongsun.framework.util.java.lang.StringUtils;
import win.yulongsun.framework.widget.RecyView.DividerItemDecoration;
import win.yulongsun.talents.R;
import win.yulongsun.talents.adapter.PlanClazzRVAdapter;
import win.yulongsun.talents.base.BaseSwipeBackFragment;
import win.yulongsun.talents.common.Constant;
import win.yulongsun.talents.entity.Clazz;
import win.yulongsun.talents.entity.JobTemplate;
import win.yulongsun.talents.entity.Plan;
import win.yulongsun.talents.http.resp.ResponseList;
import win.yulongsun.talents.http.resp.biz.ClazzResponse;
import win.yulongsun.talents.http.resp.biz.PlanResponse;
import win.yulongsun.talents.ui.msg.MsgDetailFragment;

import static win.yulongsun.framework.util.JsonUtil.fromJson;
import static win.yulongsun.framework.widget.RecyView.DividerItemDecoration.VERTICAL_LIST;
import static win.yulongsun.talents.R.array.clazz_status;

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
    @Bind(R.id.btn_plan_edit_learn_plan)
    Button       mBtnPlanEditLearnPlan;
    @Bind(R.id.et_plan_tmp_id)
    EditText     mEtPlanTmpId;
    @Bind(R.id.et_plan_edit_log)
    EditText     mEtPlanLog;
    private int mMode = Constant.MODE_VALUE.EDIT;
    private Plan               mPlan;
    private PlanClazzRVAdapter mAdapter;
    private List<Clazz> mClazzList = new ArrayList<>();
    private JobTemplate mJobTemplate;

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
        return newInstance(mode, plan, null);
    }

    public static PlanEditFragment newInstance(int mode, Plan plan, JobTemplate jobTemplate) {
        PlanEditFragment fragment = new PlanEditFragment();
        Bundle args = new Bundle();
        args.putInt(Constant.MODE_NAME, mode);
        if (mode == Constant.MODE_VALUE.EDIT || mode == Constant.MODE_VALUE.SELECT || mode == Constant.MODE_VALUE.LEARN) {
            args.putSerializable(PLAN_LIST_EDIT_KEY, plan);
        }
        if (mode == Constant.MODE_VALUE.ADD) {
            args.putSerializable(PLAN_LIST_EDIT_KEY, jobTemplate);
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getMenuResId() {
        if (mMode == Constant.MODE_VALUE.SELECT) {
            return 0;
        } else if (mMode == Constant.MODE_VALUE.LEARN) {
            return R.menu.menu_stu_contact;
        } else if (mMode == Constant.MODE_VALUE.EDIT) {
            return R.menu.menu_plan_save;
        }
        return 0;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (mMode == Constant.MODE_VALUE.EDIT && menu != null) {
            MenuItem item = menu.findItem(R.id.action_plan_stu);
            if (item != null) {
                item.setVisible(true);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_plan_save) {
            toSavePlan();
        }
        if (item.getItemId() == R.id.action_contact_referrer) {
            start(MsgDetailFragment.newInstance());
        }
        if (item.getItemId() == R.id.action_plan_stu) {
            start(ReferrerStuListFragment.newInstance(mPlan));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initView() {
        Bundle bundle = getArguments();
        mMode = bundle.getInt(Constant.MODE_NAME);
        super.initView();
        if (mMode == Constant.MODE_VALUE.EDIT) {
            mPlan = (Plan) bundle.getSerializable(PLAN_LIST_EDIT_KEY);
            //传递数据，初始化页面数据
            mEtPlanTmpId.setText(mPlan.job_template_id + "");
            mEtPlanEditName.setText(mPlan.plan_name);
            mEtPlanLog.setText(mPlan.plan_img);
            mEtPlanEditDesc.setText(mPlan.plan_desc);
            mEtPlanEditContent.setText(mPlan.plan_content);
            mClazzList = mPlan.clazz;
            mBtnPlanEditAddClazz.setVisibility(View.VISIBLE);
            mBtnPlanEditLearnPlan.setVisibility(View.GONE);
        } else if (mMode == Constant.MODE_VALUE.ADD) {
            mJobTemplate = (JobTemplate) bundle.getSerializable(PLAN_LIST_EDIT_KEY);
            //显示"添加课程"按钮
            mBtnPlanEditAddClazz.setVisibility(View.GONE);
            mBtnPlanEditLearnPlan.setVisibility(View.GONE);
        } else if (mMode == Constant.MODE_VALUE.SELECT) {
            mPlan = (Plan) bundle.getSerializable(PLAN_LIST_EDIT_KEY);
            //隐藏"添加课程"按钮、显示"学习计划"按钮
            mEtPlanTmpId.setText(mPlan.job_template_id + "");
            mEtPlanLog.setText(mPlan.plan_img);
            mEtPlanEditName.setText(mPlan.plan_name);
            mEtPlanEditDesc.setText(mPlan.plan_desc);
            mEtPlanEditContent.setText(mPlan.plan_content);
            mEtPlanTmpId.setEnabled(false);
            mEtPlanTmpId.setFocusable(false);
            mEtPlanLog.setEnabled(false);
            mEtPlanLog.setFocusable(false);
            mEtPlanEditDesc.setEnabled(false);
            mEtPlanEditDesc.setFocusable(false);
            mEtPlanEditContent.setEnabled(false);
            mEtPlanEditContent.setFocusable(false);
            mClazzList = mPlan.clazz;
            mBtnPlanEditAddClazz.setVisibility(View.GONE);
            mBtnPlanEditLearnPlan.setVisibility(View.VISIBLE);
        } else if (mMode == Constant.MODE_VALUE.LEARN) {
            mPlan = (Plan) bundle.getSerializable(PLAN_LIST_EDIT_KEY);
            //隐藏"添加课程"按钮、隐藏"学习计划"按钮
            mEtPlanTmpId.setText(mPlan.job_template_id + "");
            mEtPlanLog.setText(mPlan.plan_img);
            mEtPlanEditName.setText(mPlan.plan_name);
            mEtPlanEditDesc.setText(mPlan.plan_desc);
            mEtPlanEditContent.setText(mPlan.plan_content);
            mEtPlanTmpId.setEnabled(false);
            mEtPlanTmpId.setFocusable(false);
            mEtPlanLog.setEnabled(false);
            mEtPlanLog.setFocusable(false);
            mEtPlanEditDesc.setEnabled(false);
            mEtPlanEditDesc.setFocusable(false);
            mEtPlanEditContent.setEnabled(false);
            mEtPlanEditContent.setFocusable(false);
            mClazzList = mPlan.clazz;
            mBtnPlanEditAddClazz.setVisibility(View.GONE);
            mBtnPlanEditLearnPlan.setVisibility(View.GONE);
        }
        //adapter
        mAdapter = new PlanClazzRVAdapter(_mActivity, mClazzList, R.layout.item_plan_clazz_list);
        mRecyPlanEditClazz.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRecyPlanEditClazz.addItemDecoration(new DividerItemDecoration(_mActivity, VERTICAL_LIST));
        mRecyPlanEditClazz.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);

    }


    @OnClick({R.id.btn_plan_edit_add_clazz, R.id.btn_plan_edit_learn_plan})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_plan_edit_add_clazz:
                toAddClazz();
                break;
            case R.id.btn_plan_edit_learn_plan:
                toLearnClazz();
                break;
        }
    }


    //添加培养计划:HR用
    private void toAddClazz() {
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
                                        ClazzResponse resp = (ClazzResponse) fromJson(response, ClazzResponse.class);
                                        ToastUtils.toastL(_mActivity, resp.msg);
                                        if (resp.code == Constant.CODE.SUCCESS) {
                                            //success
                                            dialog.dismiss();
                                            Logger.json(response);
                                            mClazzList = resp.data;
                                            mAdapter.replaceAll(mClazzList);
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
        String plan_img = mEtPlanLog.getText().toString();
        if (StringUtils.isEmpty(plan_name)) {
            ToastUtils.toastL(_mActivity, "培养计划名称不能为空");
            return;
        }
        if (StringUtils.isEmpty(plan_img)) {
            ToastUtils.toastL(_mActivity, "培养计划标签不能为空");
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

        if (mMode == Constant.MODE_VALUE.EDIT) {
            OkHttpUtils.post()
                    .url(Constant.URL + "plan/update")
                    .addParams("plan_id", String.valueOf(mPlan.plan_id))
                    .addParams("plan_name", plan_name)
                    .addParams("plan_img", plan_img)
                    .addParams("plan_desc", plan_desc)
                    .addParams("plan_content", plan_content)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {

                        }

                        @Override
                        public void onResponse(String response, int id) {
                            PlanResponse resp = (PlanResponse) fromJson(response, PlanResponse.class);
                            ToastUtils.toastL(_mActivity, resp.msg);
                            if (resp.code == Constant.CODE.SUCCESS) {
                                pop();
                            }
                        }
                    });
        } else if (mMode == Constant.MODE_VALUE.ADD) {
            //add
            OkHttpUtils.post()
                    .url(Constant.URL + "plan/add")
                    .addParams("plan_name", plan_name)
                    .addParams("plan_desc", plan_desc)
                    .addParams("plan_content", plan_content)
                    .addParams("job_template_id", String.valueOf(mJobTemplate.tmp_id))
                    .addParams("create_by", String.valueOf(_User.user_id))
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {

                        }

                        @Override
                        public void onResponse(String response, int id) {
                            PlanResponse resp = (PlanResponse) fromJson(response, PlanResponse.class);
                            ToastUtils.toastL(_mActivity, resp.msg);
                            if (resp.code == Constant.CODE.SUCCESS) {
                                //更新mPlan
                                mPlan = resp.data.get(0);
                                mBtnPlanEditAddClazz.setVisibility(View.VISIBLE);
                                mMode = Constant.MODE_VALUE.EDIT;
                            }
                        }
                    });
        }


    }


    @Override
    public void onItemClick(View itemView, int viewType, final int position) {
        switch (mMode) {
            case Constant.MODE_VALUE.SELECT:
                break;
            case Constant.MODE_VALUE.LEARN:
                toUpdateClazzProgress(position);
                break;
            case Constant.MODE_VALUE.EDIT:
            case Constant.MODE_VALUE.ADD:
                toDeleteClazz(position);
                break;
        }
    }

    //删除课程：推荐人用
    private void toDeleteClazz(final int position) {
        new AlertDialog.Builder(_mActivity)
                .setTitle("删除提示")
                .setMessage("您确定删除" + mClazzList.get(position).clazz_name + "吗?")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        OkHttpUtils.post()
                                .url(Constant.URL + "clazz/delete")
                                .addParams("plan_id", String.valueOf(mPlan.plan_id))
                                .addParams("clazz_id", String.valueOf(mClazzList.get(position).clazz_id))
                                .build()
                                .execute(new StringCallback() {
                                    @Override
                                    public void onError(Call call, Exception e, int id) {

                                    }

                                    @Override
                                    public void onResponse(String response, int id) {
                                        ClazzResponse resp = (ClazzResponse) fromJson(response, ClazzResponse.class);
                                        ToastUtils.toastL(_mActivity, resp.msg);
                                        if (resp.code == Constant.CODE.SUCCESS) {
                                            Logger.json(response);
                                            mClazzList = resp.data;
                                            mAdapter.replaceAll(mClazzList);
                                        }
                                    }
                                });
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

    //更新课程进度：学生用
    private void toUpdateClazzProgress(int position) {
        final Clazz clazz = mClazzList.get(position);
        View dialogView = LayoutInflater.from(_mActivity).inflate(R.layout.dialog_clazz_status, null);
        Spinner sp_clazz_status = (Spinner) dialogView.findViewById(R.id.sp_dialog_clazz_status);
        final EditText clazz_grade = (EditText) dialogView.findViewById(R.id.et_dialog_clazz_grade);
        //init data
        if ("未开始".equals(clazz.clazz_status)) {
            sp_clazz_status.setSelection(0);
        } else if ("进行中".equals(clazz.clazz_status)) {
            sp_clazz_status.setSelection(1);
        } else if ("已完成".equals(clazz.clazz_status)) {
            sp_clazz_status.setSelection(2);
        }
        clazz_grade.setText(clazz.clazz_grade + "");

        //get data
        final String[] clazzStatus = {"未开始"};
        sp_clazz_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                clazzStatus[0] = getResources().getStringArray(clazz_status)[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        new AlertDialog.Builder(_mActivity)
                .setMessage("设置课程状态")
                .setView(dialogView)
                .setCancelable(false)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        final int clazzGrade = Integer.parseInt(clazz_grade.getText().toString());
                        if (clazzGrade > 100) {
                            ToastUtils.toastL(_mActivity, "满分100分，请输入小于等于100的分数");
                            return;
                        }
                        DialogUtil.showLoading(_mActivity, "更新中...");
                        OkHttpUtils.post()
                                .url(Constant.URL + "user_plan_clazz_r/update")
                                .addParams("_id", String.valueOf(clazz._id))
                                .addParams("user_id", String.valueOf(_User.user_id))
                                .addParams("plan_id", String.valueOf(mPlan.plan_id))
                                .addParams("clazz_status", clazzStatus[0])
                                .addParams("clazz_grade", String.valueOf(clazzGrade))
                                .build()
                                .execute(new StringCallback() {
                                    @Override
                                    public void onError(Call call, Exception e, int id) {

                                    }

                                    @Override
                                    public void onResponse(String response, int id) {
                                        DialogUtil.dismissLoading();
                                        ClazzResponse resp = (ClazzResponse) JsonUtil.fromJson(response, ClazzResponse.class);
                                        ToastUtils.toastL(_mActivity, resp.msg);
                                        Logger.json(response);
                                        if (resp.code == Constant.CODE.SUCCESS) {
                                            dialog.dismiss();
                                            mClazzList = resp.data;
                                            mAdapter.replaceAll(mClazzList);
                                        }
                                    }
                                });

                    }
                }).setNegativeButton("取消", null)
                .show();


    }

    //学习此培养计划:学生用
    private void toLearnClazz() {
        OkHttpUtils.post()
                .url(Constant.URL + "user_plan_r/add")
                .addParams("user_id", String.valueOf(_User.user_id))
                .addParams("plan_id", String.valueOf(mPlan.plan_id))
                .addParams("apply_status", String.valueOf(Constant.LEARN_PROGRESS.PROGESSING))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        ResponseList resp = (ResponseList) JsonUtil.fromJson(response, ResponseList.class);
                        ToastUtils.toastL(_mActivity, resp.msg);
                        if (resp.code == Constant.CODE.SUCCESS) {
                            pop();
                        }
                    }
                });

    }
}
