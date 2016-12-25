package win.yulongsun.talents.adapter;

import android.content.Context;

import java.util.List;

import win.yulongsun.framework.adapter.SuperAdapter;
import win.yulongsun.framework.adapter.SuperViewHolder;
import win.yulongsun.talents.R;
import win.yulongsun.talents.entity.Exper;

/**
 * @author sunyulong on 2016/12/25.
 */
public class ResumeExperListRVAdapter extends SuperAdapter<Exper> {
    public ResumeExperListRVAdapter(Context context, List<Exper> items, int layoutResId) {
        super(context, items, layoutResId);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, Exper item) {
        holder.setText(R.id.tv_item_resume_exper_time, "#" + (layoutPosition + 1) + "." + item.exper_time);
        holder.setText(R.id.tv_item_resume_exper_name, "【项目名称】" + item.exper_name);
        holder.setText(R.id.tv_item_resume_exper_job, "【项目岗位】" + item.exper_job);
        holder.setText(R.id.tv_item_resume_exper_desc, "项目简述：" + item.exper_desc);
        holder.setText(R.id.tv_item_resume_exper_job_desc, "职责简述：" + item.exper_job_desc);
    }
}
