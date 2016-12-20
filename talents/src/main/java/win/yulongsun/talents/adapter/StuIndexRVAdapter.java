package win.yulongsun.talents.adapter;

import android.content.Context;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

import win.yulongsun.talents.R;
import win.yulongsun.talents.entity.ItemIndex;

/**
 * @author sunyulong on 2016/12/18.
 */
public class StuIndexRVAdapter extends SuperAdapter<ItemIndex> {

    public StuIndexRVAdapter(Context context, List<ItemIndex> items, int layoutResId) {
        super(context, items, layoutResId);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, ItemIndex item) {
        holder.setImageResource(R.id.item_iv_stu_index, item.resId);
        holder.setText(R.id.item_tv_index_stu_title, item.title);
    }
}
