package win.yulongsun.talents.entity;


import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ModelContainer;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

import win.yulongsun.talents.config.DBConfig;

/**
 * @author sunyulong on 2016/12/11.
 */
@ModelContainer
@Table(database = DBConfig.class)
public class User {
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
    public Integer user_company_id; //用户公司Id
    @Column
    public String  user_role;        //用户角色
    @Column
    public String  company_name;     //公司名
    @Column
    public String  company_addr;      //公司地址
    @Column
    public String  company_contact;   //公司联系人
    @Column
    public Integer user_score;       //用户积分
}
