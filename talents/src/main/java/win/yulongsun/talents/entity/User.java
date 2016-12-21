package win.yulongsun.talents.entity;


import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ModelContainer;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import win.yulongsun.talents.config.DBConfig;

/**
 * @author sunyulong on 2016/12/11.
 */
@ModelContainer
@Table(database = DBConfig.class)
public class User extends BaseModel {
    @PrimaryKey
    public Integer user_id;         //用户编号
    @Column
    public String  user_mobile;     //用户手机号
    @Column
    public String  user_name;       //用户姓名
    @Column
    public String  user_gender;     //性别
    @Column
    public String  user_email;      //邮箱
    @Column
    public String  user_img;        //用户头像
    @Column
    public String  user_token;      //用户密码
    @Column
    public Integer user_role_id;        //用户角色
    @Column
    public Integer user_score;       //用户积分
    @Column
    public Integer user_company_id; //用户公司Id
    @Column
    public String  user_company_career;      //职位
    @Column
    public String  user_company_depart;  //部门

    ////////////////////////// 公司表 ///////////////////////
    @Column
    public String company_logo;        //公司Logo
    @Column
    public String  company_name;     //公司名
    @Column
    public String  company_addr;      //公司地址
    @Column
    public String  company_contact;   //公司联系人


}
