package win.yulongsun.talents.adapter;

import android.content.Context;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

import win.yulongsun.talents.entity.Referrer;

/**
 * @author sunyulong on 2016/11/29.
 *         <p>
 *         推荐人适配器
 */
public class ReferrerRVAdapter extends SuperAdapter<Referrer> {
    public ReferrerRVAdapter(Context context, List<Referrer> items, int layoutResId) {
        super(context, items, layoutResId);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, Referrer item) {

    }
}
