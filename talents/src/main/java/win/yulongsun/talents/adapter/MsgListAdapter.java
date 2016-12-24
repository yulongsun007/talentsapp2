package win.yulongsun.talents.adapter;

import android.content.Context;

import java.util.List;

import win.yulongsun.framework.adapter.SuperAdapter;
import win.yulongsun.framework.adapter.SuperViewHolder;
import win.yulongsun.framework.adapter.animation.BaseAnimation;
import win.yulongsun.talents.R;
import win.yulongsun.talents.entity.Msg;


public class MsgListAdapter extends SuperAdapter<Msg> {

    public MsgListAdapter(Context context, List<Msg> items, int layoutResId) {
        super(context, items, layoutResId);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, Msg item) {
        holder.setText(R.id.tv_msg_title, item.msg_title);
        holder.setText(R.id.tv_msg_title, item.msg_title);
//        holder.setText(R.id.tv_msg_create_at, item.create_at.getHours() + ":" + item.create_at.getMinutes());
    }

    @Override
    public void enableLoadAnimation(long duration, BaseAnimation animation) {

    }
}
