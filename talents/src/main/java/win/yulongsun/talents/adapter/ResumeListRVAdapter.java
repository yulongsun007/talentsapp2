package win.yulongsun.talents.adapter;

import android.content.Context;
import android.net.Uri;

import java.util.List;

import win.yulongsun.framework.adapter.SuperAdapter;
import win.yulongsun.framework.adapter.SuperViewHolder;
import win.yulongsun.talents.R;
import win.yulongsun.talents.entity.Resume;

/**
 * @author sunyulong on 2016/12/25.
 */
public class ResumeListRVAdapter extends SuperAdapter<Resume> {
    private Context context;

    public ResumeListRVAdapter(Context context, List<Resume> items, int layoutResId) {
        super(context, items, layoutResId);
        this.context = context;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, Resume item) {
        holder.setImageUri(context, R.id.iv_item_resume_img, Uri.parse(item.resume_img));
        holder.setText(R.id.tv_item_resume_name, "[简历]#" + item.resume_id + "." + item.resume_name);
        holder.setText(R.id.tv_item_resume_is_study, item.resume_is_study);
        holder.setText(R.id.tv_item_resume_academy, item.resume_academy);
        holder.setText(R.id.tv_item_resume_update_at, item.update_at);


    }
}
