package win.yulongsun.talents.adapter;

import android.content.Context;

import com.raizlabs.android.dbflow.sql.language.Select;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

import win.yulongsun.talents.R;
import win.yulongsun.talents.entity.JobTemplate;
import win.yulongsun.talents.entity.User;

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
        User user = new Select().from(User.class).querySingle();
        if(user!=null){
            holder.setText(R.id.tv_job_temp_lib_job_name,user.company_name);
        }
        holder.setText(R.id.tv_job_temp_lib_job_name,item.tmp_job_name);
        holder.setText(R.id.tv_job_temp_lib_job_salary,item.tmp_job_salary);
        holder.setText(R.id.tv_job_temp_lib_biz_direct,item.tmp_job_biz_direct);
        holder.setText(R.id.tv_job_temp_lib_job_id,"模板编号-"+item.tmp_id);

    }
}
