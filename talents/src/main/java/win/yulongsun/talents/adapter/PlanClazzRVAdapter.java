package win.yulongsun.talents.adapter;

import android.content.Context;

import java.util.List;

import win.yulongsun.framework.adapter.SuperAdapter;
import win.yulongsun.framework.adapter.SuperViewHolder;
import win.yulongsun.framework.adapter.animation.BaseAnimation;
import win.yulongsun.talents.R;
import win.yulongsun.talents.entity.Clazz;

/**
 * @author sunyulong on 2016/12/16.
 */
public class PlanClazzRVAdapter extends SuperAdapter<Clazz> {
    public PlanClazzRVAdapter(Context context, List<Clazz> items, int layoutResId) {
        super(context, items, layoutResId);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, Clazz item) {
        holder.setText(R.id.tv_item_clazz_name, (layoutPosition + 1) + "." + item.clazz_name + "[" + item.clazz_priority + "]");
        holder.setText(R.id.tv_item_clazz_score_hour, item.clazz_hour + "课时/" + item.clazz_score + "学分");
        holder.setText(R.id.tv_item_clazz_score_books, "推荐书籍：" + item.clazz_books);
    }

    @Override
    public void enableLoadAnimation(long duration, BaseAnimation animation) {

    }
}
