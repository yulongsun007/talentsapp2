package win.yulongsun.talents.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;

import java.util.List;

import win.yulongsun.framework.adapter.SuperAdapter;
import win.yulongsun.framework.adapter.SuperViewHolder;
import win.yulongsun.talents.R;
import win.yulongsun.talents.common.Constant;
import win.yulongsun.talents.entity.UserPlanR;

/**
 * @author sunyulong on 2016/12/26.
 */
public class FeedbackListRVAdapter extends SuperAdapter<UserPlanR> {
    private final Context context;

    public FeedbackListRVAdapter(Context context, List<UserPlanR> items, int layoutResId) {
        super(context, items, layoutResId);
        this.context = context;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, UserPlanR item) {
        holder.setImageUri(context, R.id.iv_feedback_company_logo, Uri.parse(item.company.company_logo));
        holder.setText(R.id.tv_feedback_job_name, item.job.tmp_job_name);
        holder.setText(R.id.tv_feedback_company_name, item.company.company_name);
        holder.setText(R.id.tv_feedback_update_at, item.update_at);
        holder.setText(R.id.tv_feedback_resume_id, "简历# " + item.resume_id);

        String status = null;
        switch (item.apply_status) {
            case Constant.APPLY_STATUS.COMMIT:
                status = "已提交";
                break;
            case Constant.APPLY_STATUS.REFERRER_REJECT:
                status = "推荐人已拒绝";
                holder.setVisibility(R.id.tv_feedback_apply_msg, View.VISIBLE);
                holder.setText(R.id.tv_feedback_apply_msg, item.apply_msg);
                break;
            case Constant.APPLY_STATUS.REFERRER_PASS:
                status = "推荐人已推荐";
                break;
            case Constant.APPLY_STATUS.HR_REJECT:
                status = "HR已拒绝";
                holder.setVisibility(R.id.tv_feedback_apply_msg, View.VISIBLE);
                holder.setText(R.id.tv_feedback_apply_msg, item.apply_msg);
                break;
            case Constant.APPLY_STATUS.HR_PASS:
                status = "HR已通过";
                holder.setVisibility(R.id.tv_feedback_apply_msg, View.VISIBLE);
                holder.setText(R.id.tv_feedback_apply_msg, item.apply_msg);
                break;
        }
        holder.setText(R.id.tv_feedback_apply_status, status);

    }
}
