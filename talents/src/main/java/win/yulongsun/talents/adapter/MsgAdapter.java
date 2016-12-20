package win.yulongsun.talents.adapter;

import android.content.Context;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

import win.yulongsun.talents.R;
import win.yulongsun.talents.entity.Msg;


public class MsgAdapter extends SuperAdapter<Msg> {

    public MsgAdapter(Context context, List<Msg> items, int layoutResId) {
        super(context, items, layoutResId);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, Msg item) {
        holder.setText(R.id.tv_msg_title, item.msg_title);
        holder.setText(R.id.tv_msg_title, item.msg_title);
//        holder.setText(R.id.tv_msg_create_at, item.create_at.getHours() + ":" + item.create_at.getMinutes());
    }
}
