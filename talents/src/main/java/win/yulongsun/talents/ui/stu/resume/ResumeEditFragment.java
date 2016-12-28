package win.yulongsun.talents.ui.stu.resume;

import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.orhanobut.logger.Logger;
import com.raizlabs.android.dbflow.annotation.NotNull;
import com.sw926.imagefileselector.ErrorResult;
import com.sw926.imagefileselector.ImageFileSelector;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Call;
import win.yulongsun.framework.adapter.OnItemClickListener;
import win.yulongsun.framework.image.ImageLoadManager;
import win.yulongsun.framework.util.JsonUtil;
import win.yulongsun.framework.util.android.app.DialogUtil;
import win.yulongsun.framework.util.android.widget.ToastUtils;
import win.yulongsun.framework.util.java.lang.StringUtils;
import win.yulongsun.talents.R;
import win.yulongsun.talents.adapter.ResumeExperListRVAdapter;
import win.yulongsun.talents.base.BaseSwipeBackFragment;
import win.yulongsun.talents.common.Constant;
import win.yulongsun.talents.entity.Exper;
import win.yulongsun.talents.entity.Resume;
import win.yulongsun.talents.event.ActionEvent;
import win.yulongsun.talents.http.resp.biz.ExperResponse;
import win.yulongsun.talents.http.resp.biz.ResumeResponse;

import static win.yulongsun.framework.util.JsonUtil.fromJson;

/**
 * @author sunyulong on 2016/12/25.
 */
public class ResumeEditFragment extends BaseSwipeBackFragment implements OnItemClickListener {

    public static final  String RESUME_DETAIL_KEY = "resume_detail_key";
    private static final int    REQ_CODE          = 0x001;
    @Bind(R.id.tv_resume_detail_img)
    ImageView    mIvResumeDetailImg;
    @Bind(R.id.et_resume_detail_name)
    EditText     mEtResumeDetailName;
    @Bind(R.id.et_resume_detail_gender)
    EditText     mEtResumeDetailGender;
    @Bind(R.id.et_resume_detail_is_study)
    EditText     mEtResumeDetailIsStudy;
    @Bind(R.id.et_resume_detail_academy)
    EditText     mEtResumeDetailAcademy;
    @Bind(R.id.et_resume_detail_graduate_at)
    EditText     mEtResumeDetailGraduateAt;
    @Bind(R.id.et_resume_detail_mobile)
    EditText     mEtResumeDetailMobile;
    @Bind(R.id.et_resume_detail_email)
    EditText     mEtResumeDetailEmail;
    @Bind(R.id.et_resume_detail_desc)
    EditText     mEtResumeDetailDesc;
    @Bind(R.id.btn_resume_detail_add_exper)
    Button       mBtnResumeDetailAddExper;
    @Bind(R.id.recy_resume_exper)
    RecyclerView mRecyResumeExper;
    @Bind(R.id.toolbar)
    Toolbar      mToolbar;
    @Bind(R.id.ll_resume_container)
    LinearLayout mLlResumeContainer;
    @Bind(R.id.et_resume_detail_major)
    EditText     mEtResumeDetailMajor;

    private Resume mResume;
    private int    mMode;
    private List<Exper> mExperList = new ArrayList<>();
    private ResumeExperListRVAdapter mAdapter;
    private ImageFileSelector        mImageFileSelector;
    private String                   resume_img;
    private String                   resume_name;
    private String                   resume_gender;
    private String                   resume_desc;
    private String                   resume_email;
    private String                   resume_is_study;
    private String                   resume_major;
    private String                   resume_academy;
    private String                   resume_graduate_at;
    private String                   resume_mobile;
    private Bundle                   mBundle;


    public static ResumeEditFragment newInstance(int mode, Resume resume) {
        ResumeEditFragment fragment = new ResumeEditFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.MODE_NAME, mode);
        if (mode == Constant.MODE_VALUE.EDIT || mode == Constant.MODE_VALUE.CHECK) {
            bundle.putSerializable(RESUME_DETAIL_KEY, resume);
        }
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_resume_edit;
    }

    @Override
    protected Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    protected String getToolbarTitle() {
        return "简历详情";
    }

    @Override
    protected void initView() {
        mBundle = getArguments();
        mMode = mBundle.getInt(Constant.MODE_NAME);
        super.initView();
        //adapter
        mAdapter = new ResumeExperListRVAdapter(_mActivity, mExperList, R.layout.item_resume_exper_list);
        mRecyResumeExper.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRecyResumeExper.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        //pick
        mImageFileSelector = new ImageFileSelector(_mActivity);
        mImageFileSelector.setOutPutImageSize(600, 600);
        mImageFileSelector.setCallback(new ImageFileSelector.Callback() {
            @Override
            public void onError(@NotNull ErrorResult errorResult) {
                Logger.e(errorResult.toString());
                switch (errorResult) {
                    case permissionDenied:
                        break;
                    case canceled:
                        break;
                    case error:
                        ToastUtils.toastL(_mActivity, "获取图片失败");
                        break;
                }
            }

            @Override
            public void onSuccess(@NotNull String file) {
                resume_img = file;
                Logger.d("onSuccess: " + file);
                ImageLoadManager.getInstance().load(file).into(mIvResumeDetailImg);
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        //edit
        if (mMode == Constant.MODE_VALUE.EDIT) {
            mResume = (Resume) mBundle.getSerializable(RESUME_DETAIL_KEY);
            ImageLoadManager.getInstance().load(mResume.resume_img).into(mIvResumeDetailImg);
            mEtResumeDetailName.setText(mResume.resume_name);
            mEtResumeDetailGender.setText(mResume.resume_gender);
            mEtResumeDetailIsStudy.setText(mResume.resume_is_study);
            mEtResumeDetailAcademy.setText(mResume.resume_academy);
            mEtResumeDetailMajor.setText(mResume.resume_major);
            mEtResumeDetailGraduateAt.setText(mResume.resume_graduate_at);
            mEtResumeDetailMobile.setText(mResume.resume_mobile);
            mEtResumeDetailEmail.setText(mResume.resume_email);
            mEtResumeDetailDesc.setText(mResume.resume_desc);
            mAdapter.replaceAll(mResume.experList);
        } else if (mMode == Constant.MODE_VALUE.ADD) {
            mBtnResumeDetailAddExper.setVisibility(View.GONE);
        } else if (mMode == Constant.MODE_VALUE.CHECK) {
            mResume = (Resume) mBundle.getSerializable(RESUME_DETAIL_KEY);
            ImageLoadManager.getInstance().load(mResume.resume_img).into(mIvResumeDetailImg);
            mEtResumeDetailName.setText(mResume.resume_name);
            mEtResumeDetailGender.setText(mResume.resume_gender);
            mEtResumeDetailIsStudy.setText(mResume.resume_is_study);
            mEtResumeDetailAcademy.setText(mResume.resume_academy);
            mEtResumeDetailMajor.setText(mResume.resume_major);
            mEtResumeDetailGraduateAt.setText(mResume.resume_graduate_at);
            mEtResumeDetailMobile.setText(mResume.resume_mobile);
            mEtResumeDetailEmail.setText(mResume.resume_email);
            mEtResumeDetailDesc.setText(mResume.resume_desc);
            mAdapter.replaceAll(mResume.experList);
            //disable
            mEtResumeDetailName.setEnabled(false);
            mIvResumeDetailImg.setEnabled(false);
            mEtResumeDetailGender.setEnabled(false);
            mEtResumeDetailIsStudy.setEnabled(false);
            mEtResumeDetailAcademy.setEnabled(false);
            mEtResumeDetailMajor.setEnabled(false);
            mEtResumeDetailGraduateAt.setEnabled(false);
            mEtResumeDetailMobile.setEnabled(false);
            mEtResumeDetailEmail.setEnabled(false);
            mEtResumeDetailDesc.setEnabled(false);
            mAdapter.setOnItemClickListener(null);
            mBtnResumeDetailAddExper.setVisibility(View.GONE);
        }

    }

    @Override
    protected int getMenuResId() {
        if (mMode == Constant.MODE_VALUE.ADD || mMode == Constant.MODE_VALUE.EDIT) {
            return R.menu.menu_common_save;
        } else {
            return 0;
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_common_save) {
            if (mMode == Constant.MODE_VALUE.ADD) {
                toSaveResume();
            } else if (mMode == Constant.MODE_VALUE.EDIT) {
                toUpdateResume();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    //保存简历
    private void toSaveResume() {
        if (!checkParam()) {
            return;
        }
        if (StringUtils.isEmpty(resume_img)) {
            ToastUtils.toastL(_mActivity, "请选择头像");
            return;
        }
        DialogUtil.showLoading(_mActivity, "保存中...");
        OkHttpUtils.post()
                .url(Constant.URL + "resume/add")
                .addFile("resume_img", String.valueOf(_User.user_id) + ".jpg", new File(resume_img))
                .addParams("resume_name", resume_name)
                .addParams("resume_gender", resume_gender)
                .addParams("resume_is_study", resume_is_study)
                .addParams("resume_major", resume_major)
                .addParams("resume_academy", resume_academy)
                .addParams("resume_graduate_at", resume_graduate_at)
                .addParams("resume_mobile", resume_mobile)
                .addParams("resume_email", resume_email)
                .addParams("resume_desc", resume_desc)
                .addParams("create_by", String.valueOf(_User.user_id))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        DialogUtil.dismissLoading();
                        ResumeResponse resp = (ResumeResponse) JsonUtil.fromJson(response, ResumeResponse.class);
                        ToastUtils.toastL(_mActivity, resp.msg);
                        if (resp.code == Constant.CODE.SUCCESS) {
                            mBtnResumeDetailAddExper.setVisibility(View.VISIBLE);
                            pop();
                        }
                    }
                });

    }

    private boolean checkParam() {
        resume_name = mEtResumeDetailName.getText().toString();
        if (StringUtils.isEmpty(resume_name)) {
            ToastUtils.toastL(_mActivity, "请填写姓名");
            return false;
        }
        resume_gender = mEtResumeDetailGender.getText().toString();
        if (StringUtils.isEmpty(resume_gender)) {
            ToastUtils.toastL(_mActivity, "请填写性别");
            return false;
        }
        resume_is_study = mEtResumeDetailIsStudy.getText().toString();
        if (StringUtils.isEmpty(resume_is_study)) {
            ToastUtils.toastL(_mActivity, "请填写学历");
            return false;
        }
        resume_major = mEtResumeDetailMajor.getText().toString();
        if (StringUtils.isEmpty(resume_major)) {
            ToastUtils.toastL(_mActivity, "请填写专业");
            return false;
        }
        resume_academy = mEtResumeDetailAcademy.getText().toString();
        if (StringUtils.isEmpty(resume_academy)) {
            ToastUtils.toastL(_mActivity, "请填写毕业院校");
            return false;
        }
        resume_graduate_at = mEtResumeDetailGraduateAt.getText().toString();
        if (StringUtils.isEmpty(resume_graduate_at)) {
            ToastUtils.toastL(_mActivity, "请填写毕业时间");
            return false;
        }
        resume_mobile = mEtResumeDetailMobile.getText().toString();
        if (StringUtils.isEmpty(resume_mobile)) {
            ToastUtils.toastL(_mActivity, "请填写联系电话");
            return false;
        }
        resume_email = mEtResumeDetailEmail.getText().toString();
        if (StringUtils.isEmpty(resume_gender)) {
            ToastUtils.toastL(_mActivity, "请填写联系邮箱");
            return false;
        }
        resume_desc = mEtResumeDetailDesc.getText().toString();
        if (StringUtils.isEmpty(resume_gender)) {
            ToastUtils.toastL(_mActivity, "请填写自我介绍");
            return false;
        }
        return true;
    }

    private void toUpdateResume() {
        if (!checkParam()) {
            return;
        }
        DialogUtil.showLoading(_mActivity, "更新中...");
        PostFormBuilder builder = OkHttpUtils.post();
        if (StringUtils.isNotEmpty(resume_img)) {
            builder.url(Constant.URL + "resume/updateImg");
            builder.addFile("resume_img", String.valueOf(_User.user_id) + ".jpg", new File(resume_img));
        } else {
            builder.url(Constant.URL + "resume/update");
        }
        builder.addParams("resume_id", String.valueOf(mResume.resume_id))
                .addParams("resume_name", resume_name)
                .addParams("resume_gender", resume_gender)
                .addParams("resume_is_study", resume_is_study)
                .addParams("resume_major", resume_major)
                .addParams("resume_academy", resume_academy)
                .addParams("resume_graduate_at", resume_graduate_at)
                .addParams("resume_mobile", resume_mobile)
                .addParams("resume_email", resume_email)
                .addParams("resume_desc", resume_desc)
                .addParams("create_by", String.valueOf(_User.user_id))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        DialogUtil.dismissLoading();
                        ResumeResponse resp = (ResumeResponse) JsonUtil.fromJson(response, ResumeResponse.class);
                        ToastUtils.toastL(_mActivity, resp.msg);
                        if (resp.code == Constant.CODE.SUCCESS) {
                            EventBus.getDefault().post(new ActionEvent(ActionEvent.UPDATE));
                            pop();
                        }
                    }
                });
    }

    @Override
    public void onItemClick(View itemView, int viewType, int position) {
        final Exper exper = mExperList.get(position);
        DialogUtil.showLoading(_mActivity, "删除中...");
        new AlertDialog.Builder(_mActivity)
                .setTitle("删除提示")
                .setMessage("您确定删除" + exper.exper_name + "吗?")
                .setCancelable(false)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        OkHttpUtils.post()
                                .url(Constant.URL + "resume_exper/delete")
                                .addParams("exper_id", String.valueOf(exper.exper_id))
                                .addParams("resume_id", String.valueOf(exper.resume_id))
                                .build()
                                .execute(new StringCallback() {
                                    @Override
                                    public void onError(Call call, Exception e, int id) {

                                    }

                                    @Override
                                    public void onResponse(String response, int id) {
                                        DialogUtil.dismissLoading();
                                        ExperResponse resp = (ExperResponse) fromJson(response, ExperResponse.class);
                                        ToastUtils.toastL(_mActivity, resp.msg);
                                        if (resp.code == Constant.CODE.SUCCESS) {
                                            Logger.json(response);
                                            mExperList = resp.data;
                                            mAdapter.replaceAll(mExperList);
                                        }
                                    }
                                });
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DialogUtil.dismissLoading();
                        dialog.dismiss();
                    }
                })
                .show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mImageFileSelector.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mImageFileSelector.onSaveInstanceState(outState);
    }

//    @Override
//    public void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        mImageFileSelector.onRestoreInstanceState(savedInstanceState);
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mImageFileSelector.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @OnClick({R.id.tv_resume_detail_img, R.id.btn_resume_detail_add_exper})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_resume_detail_img:
                // 从文件选取
                mImageFileSelector.selectImage(this, REQ_CODE);
                break;
            case R.id.btn_resume_detail_add_exper:
                View dialogView = LayoutInflater.from(_mActivity).inflate(R.layout.dialog_resume_exper_add, null);

                final EditText exper_time = (EditText) dialogView.findViewById(R.id.et_dialog_exper_time);
                final EditText exper_name = (EditText) dialogView.findViewById(R.id.et_dialog_exper_name);
                final EditText exper_job = (EditText) dialogView.findViewById(R.id.et_dialog_exper_job);
                final EditText exper_job_desc = (EditText) dialogView.findViewById(R.id.et_dialog_exper_job_desc);
                final EditText exper_desc = (EditText) dialogView.findViewById(R.id.et_dialog_exper_desc);

                new AlertDialog.Builder(_mActivity)
                        .setCancelable(false)
                        .setTitle("添加项目经验")
                        .setView(dialogView)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, int which) {
                                String experTime = exper_time.getText().toString();
                                if (StringUtils.isEmpty(experTime)) {
                                    ToastUtils.toastL(_mActivity, "请填写项目时间");
                                    return;
                                }
                                String experName = exper_name.getText().toString();
                                if (StringUtils.isEmpty(experName)) {
                                    ToastUtils.toastL(_mActivity, "请填写项目名称");
                                    return;
                                }
                                String experJob = exper_job.getText().toString();
                                if (StringUtils.isEmpty(experJob)) {
                                    ToastUtils.toastL(_mActivity, "请填写项目岗位");
                                    return;
                                }
                                String experJobDesc = exper_job_desc.getText().toString();
                                if (StringUtils.isEmpty(experJobDesc)) {
                                    ToastUtils.toastL(_mActivity, "请填写职责简述");
                                    return;
                                }
                                String experDesc = exper_desc.getText().toString();
                                if (StringUtils.isEmpty(experDesc)) {
                                    ToastUtils.toastL(_mActivity, "请填写项目简述");
                                    return;
                                }
                                DialogUtil.showLoading(_mActivity, "添加中...");
                                OkHttpUtils.post()
                                        .url(Constant.URL + "resume_exper/add")
                                        .addParams("exper_time", experTime)
                                        .addParams("exper_name", experName)
                                        .addParams("exper_job", experJob)
                                        .addParams("exper_job_desc", experJobDesc)
                                        .addParams("exper_desc", experDesc)
                                        .addParams("resume_id", String.valueOf(mResume.resume_id))
                                        .build()
                                        .execute(new StringCallback() {
                                            @Override
                                            public void onError(Call call, Exception e, int id) {

                                            }

                                            @Override
                                            public void onResponse(String response, int id) {
                                                DialogUtil.dismissLoading();
                                                ExperResponse resp = (ExperResponse) JsonUtil.fromJson(response, ExperResponse.class);
                                                ToastUtils.toastL(_mActivity, resp.msg);
                                                if (resp.code == Constant.CODE.SUCCESS) {
                                                    mExperList = resp.data;
                                                    mAdapter.replaceAll(mExperList);
                                                    dialog.dismiss();
                                                }
                                            }
                                        });


                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DialogUtil.dismissLoading();
                                dialog.dismiss();
                            }
                        })
                        .show();
                break;
        }
    }


}
