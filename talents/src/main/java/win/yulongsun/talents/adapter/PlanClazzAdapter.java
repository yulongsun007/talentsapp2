package win.yulongsun.talents.adapter;

import android.content.Context;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

import win.yulongsun.talents.entity.Clazz;

/**
 * @author sunyulong on 2016/12/16.
 */
public class PlanClazzAdapter extends SuperAdapter<Clazz> {
    public PlanClazzAdapter(Context context, List<Clazz> items, int layoutResId) {
        super(context, items, layoutResId);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, Clazz item) {

    }
}
