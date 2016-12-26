package win.yulongsun.talents.adapter;

import android.content.Context;
import android.view.View;

import java.util.List;

import win.yulongsun.framework.adapter.SuperAdapter;
import win.yulongsun.framework.adapter.SuperViewHolder;
import win.yulongsun.talents.R;
import win.yulongsun.talents.entity.UserPlanR;

/**
 * @author sunyulong on 2016/12/25.
 */
public class ReferrerResumeListRVAdapter extends SuperAdapter<UserPlanR> {
    private Context context;

    public ReferrerResumeListRVAdapter(Context context, List<UserPlanR> items, int layoutResId) {
        super(context, items, layoutResId);
        this.context = context;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, UserPlanR item) {
        holder.setImage(context, R.id.iv_item_resume_img, item.resume.resume_img);
        holder.setText(R.id.tv_item_resume_name, "简历#" + item.resume_id + "." + item.resume.resume_name);
        holder.setText(R.id.tv_item_resume_is_study, item.resume.resume_is_study);
        holder.setText(R.id.tv_item_resume_academy, item.resume.resume_academy);
        holder.setText(R.id.tv_item_resume_update_at, item.update_at);
        holder.setVisibility(R.id.ll_plan_grade, View.VISIBLE);
        holder.setText(R.id.tv_item_plan_already_hour, item.plan_already_hour + "小时");
        holder.setText(R.id.tv_item_plan_already_score, item.plan_already_score + "分");

    }
}
