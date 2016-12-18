package win.yulongsun.talents.adapter;

import android.content.Context;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

import win.yulongsun.talents.entity.Talent;

/**
 * @author sunyulong on 2016/11/30.
 * 积分排名适配器
 */
public class ScoreRVAdapter extends SuperAdapter<Talent> {
    public ScoreRVAdapter(Context context, List<Talent> items, int layoutResId) {
        super(context, items, layoutResId);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, Talent item) {

    }
}
