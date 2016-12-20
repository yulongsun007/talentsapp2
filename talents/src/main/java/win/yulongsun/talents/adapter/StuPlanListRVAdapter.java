package win.yulongsun.talents.adapter;

import android.content.Context;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.ArrayList;

import win.yulongsun.talents.entity.Plan;

/**
 * @author sunyulong on 2016/12/19.
 */
public class StuPlanListRVAdapter extends SuperAdapter<Plan>{
    public StuPlanListRVAdapter(Context context, int items, ArrayList<Plan> layoutResId) {
        super(context, layoutResId, items);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, Plan item) {

    }
}
