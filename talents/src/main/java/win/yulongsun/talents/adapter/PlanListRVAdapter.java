package win.yulongsun.talents.adapter;

import android.content.Context;
import android.view.View;

import java.util.List;

import win.yulongsun.framework.adapter.SuperAdapter;
import win.yulongsun.framework.adapter.SuperViewHolder;
import win.yulongsun.framework.adapter.animation.BaseAnimation;
import win.yulongsun.talents.R;
import win.yulongsun.talents.common.Constant;
import win.yulongsun.talents.entity.Plan;

/**
 * @author sunyulong on 2016/12/19.
 */
public class PlanListRVAdapter extends SuperAdapter<Plan> {

    private int mode = 0;

    public PlanListRVAdapter(Context context, List<Plan> items, int layoutResId) {
        super(context, items, layoutResId);
    }

    public PlanListRVAdapter(Context context, List<Plan> items, int layoutResId, int mode) {
        super(context, items, layoutResId);
        this.mode = mode;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, Plan item) {
        if (mode == Constant.MODE_VALUE.LEARN) {
            holder.setVisibility(R.id.ll_plan_already, View.VISIBLE);
            holder.setText(R.id.tv_plan_hour_already, item.plan_already_hour + "小时");
            holder.setText(R.id.tv_plan_score_already, item.plan_already_score + "分");
        } else {
            holder.setVisibility(R.id.ll_plan_already, View.GONE);
        }
        holder.setText(R.id.tv_plan_name, item.plan_name);
        holder.setTitleText(R.id.cv_plan_img, item.plan_img, 35);
        holder.setText(R.id.tv_plan_desc, item.plan_desc);
        holder.setText(R.id.tv_plan_hour, item.plan_hour + "小时");
        holder.setText(R.id.tv_plan_score, item.plan_score + "分");
        holder.setText(R.id.tv_plan_content, item.plan_content);
        holder.setText(R.id.tv_plan_update_at, item.update_at);
    }

    @Override
    public void enableLoadAnimation(long duration, BaseAnimation animation) {

    }
}
