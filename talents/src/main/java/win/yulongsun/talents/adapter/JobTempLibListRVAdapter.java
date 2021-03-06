package win.yulongsun.talents.adapter;

import android.content.Context;

import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.List;

import win.yulongsun.framework.adapter.SuperAdapter;
import win.yulongsun.framework.adapter.SuperViewHolder;
import win.yulongsun.framework.adapter.animation.BaseAnimation;
import win.yulongsun.talents.R;
import win.yulongsun.talents.common.Constant;
import win.yulongsun.talents.entity.JobTemplate;
import win.yulongsun.talents.entity.User;

/**
 * @author sunyulong on 2016/12/3.
 *         招聘模板列表适配器
 */
public class JobTempLibListRVAdapter extends SuperAdapter<JobTemplate> {
    public JobTempLibListRVAdapter(Context context, List<JobTemplate> items, int layoutResId) {
        super(context, items, layoutResId);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, JobTemplate item) {
        User user = new Select().from(User.class).querySingle();
//        if (user != null) {
//            holder.setText(R.id.tv_job_temp_lib_job_name, user.company_name);
//            if (user.user_role_id == Constant.ROLE.HR) {
//                holder.setVisibility(R.id.tv_job_temp_lib_job_id, View.VISIBLE);
//                holder.setText(R.id.tv_job_temp_lib_job_id, "#-" + item.tmp_id);
//            } else {
//                holder.setVisibility(R.id.tv_job_temp_lib_job_id, View.GONE);
//            }
//        }
        holder.setTitleText(R.id.civ_job_temp_lib_company_logo, item.tmp_job_depart, 30);
        holder.setText(R.id.tv_job_temp_lib_job_time, item.deploy_at);
        holder.setText(R.id.tv_job_temp_lib_job_salary, item.tmp_job_salary);
        holder.setText(R.id.tv_job_temp_lib_biz_direct, item.tmp_job_biz_direct);
        if (user != null) {
            holder.setText(R.id.tv_job_temp_lib_company_name, user.company_name);
            if (user.user_role_id == Constant.ROLE.HR) {
                holder.setText(R.id.tv_job_temp_lib_job_name, "模板#" + item.tmp_id + "." + item.tmp_job_name);
            } else {
                holder.setText(R.id.tv_job_temp_lib_job_name, item.tmp_job_name);
            }

        }


    }

    @Override
    public void enableLoadAnimation(long duration, BaseAnimation animation) {

    }
}
