package win.yulongsun.talents.adapter;

import android.content.Context;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

import win.yulongsun.talents.entity.JobTemplate;

/**
 * @author sunyulong on 2016/12/3.
 * 招聘模板列表适配器
 */
public class JobTempLibListRVAdapter extends SuperAdapter<JobTemplate>{
    public JobTempLibListRVAdapter(Context context, List<JobTemplate> items, int layoutResId) {
        super(context, items, layoutResId);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, JobTemplate item) {

    }
}
