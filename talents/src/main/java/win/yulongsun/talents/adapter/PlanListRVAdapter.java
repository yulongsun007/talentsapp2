package win.yulongsun.talents.adapter;

import android.content.Context;

import java.util.List;

import win.yulongsun.framework.adapter.SuperAdapter;
import win.yulongsun.framework.adapter.SuperViewHolder;
import win.yulongsun.framework.adapter.animation.BaseAnimation;
import win.yulongsun.talents.R;
import win.yulongsun.talents.entity.Plan;

/**
 * @author sunyulong on 2016/12/19.
 */
public class PlanListRVAdapter extends SuperAdapter<Plan> {

    public PlanListRVAdapter(Context context, List<Plan> items, int layoutResId) {
        super(context, items, layoutResId);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, Plan item) {
        holder.setText(R.id.tv_plan_name, item.plan_name);
        holder.setTitleText(R.id.cv_plan_img, item.plan_img);
        holder.setText(R.id.tv_plan_desc, item.plan_desc);
        holder.setText(R.id.tv_plan_hour, item.plan_hour + "小时");
        holder.setText(R.id.tv_plan_score, item.plan_score + "分");
        holder.setText(R.id.tv_plan_content, item.plan_content);
    }

    @Override
    public void enableLoadAnimation(long duration, BaseAnimation animation) {

    }
}
