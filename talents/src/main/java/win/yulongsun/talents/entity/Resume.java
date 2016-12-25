package win.yulongsun.talents.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author sunyulong on 2016/12/25.
 */
public class Resume implements Serializable {

    public Integer     resume_id;
    public String      resume_mobile;
    public String      resume_img;
    public String      resume_email;
    public String      resume_desc;
    public Integer     create_by;
    public String      resume_academy;
    public String      resume_gender;
    public String      resume_is_study;
    public String      resume_graduate_at;
    public String      update_at;
    public String      create_at;
    public String      resume_name;
    public String      resume_major;
    public List<Exper> experList;

}
