package win.yulongsun.talents.entity;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ModelContainer;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;
import java.util.Date;

import win.yulongsun.talents.config.DBConfig;

/**
 * Create By: yulongsun
 * Date at: 2016/8/30
 * Desc : 招聘模板
 */
@ModelContainer
@Table(database = DBConfig.class)
public class JobTemplate extends BaseModel implements Serializable{
    @PrimaryKey
    public Integer tmp_id;
    @Column
    public String  tmp_job_biz_direct;          //行业方向
    @Column
    public String  tmp_job_name;                //职位名
    @Column
    public String  tmp_job_depart;              //招聘部门
    @Column
    public Integer tmp_job_num;                 //招聘人数
    @Column
    public String  tmp_job_edu_require;         //学历要求
    @Column
    public String  tmp_job_exper_year;           //工作经验
    @Column
    public String  tmp_job_salary;              //工作薪资
    @Column
    public String  tmp_job_addr;                //工作地点
    @Column
    public String  tmp_job_skill_require;        //技能要求
    @Column
    public String  tmp_job_desc;                //职位描述
    @Column
    public Integer create_by;                   //创建者
    @Column
    public Date    start_at;                    //开始时间
    @Column
    public Date    end_at;                      //结束时间
    @Column
    public String  create_at;                   //创建时间
}
