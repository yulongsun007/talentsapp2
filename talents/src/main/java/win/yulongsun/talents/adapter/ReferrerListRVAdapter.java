package win.yulongsun.talents.adapter;

import android.content.Context;
import android.view.View;

import java.util.List;

import win.yulongsun.framework.adapter.SuperAdapter;
import win.yulongsun.framework.adapter.SuperViewHolder;
import win.yulongsun.framework.adapter.animation.BaseAnimation;
import win.yulongsun.talents.R;
import win.yulongsun.talents.entity.User;

/**
 * @author sunyulong on 2016/11/29.
 *         <p>
 *         推荐人适配器
 */
public class ReferrerListRVAdapter extends SuperAdapter<User> {
    public ReferrerListRVAdapter(Context context, List<User> items, int layoutResId) {
        super(context, items, layoutResId);
    }

    @Override
    public void onBind(SuperViewHolder holder, final int viewType, final int layoutPosition, User item) {
        holder.setText(R.id.tv_referrer_name, "#" + item.user_id + "." + item.user_name);
        holder.setText(R.id.tv_referrer_depart_career, item.user_company_depart + "-" + item.user_company_career);
        holder.setOnClickListener(R.id.btn_referrer_contact, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(v,viewType,layoutPosition);
            }
        });
    }

    @Override
    public void enableLoadAnimation(long duration, BaseAnimation animation) {

    }
}
