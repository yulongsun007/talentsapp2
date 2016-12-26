package win.yulongsun.talents.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author sunyulong on 2016/12/19.
 */
public class Plan implements Serializable {

    public Integer     plan_id;
    public String      plan_desc;
    public String      plan_content;
    public String      plan_name;
    public String      plan_img;
    public String      plan_hour;
    public String      plan_score;
    public Integer     job_template_id;
    public Integer     create_by;
    public String      create_at;
    public String      update_at;
    public List<Clazz> clazz;

    public Integer hr_id;

    ///////////////
    public Integer _id;
    public Integer plan_already_hour;
    public Integer plan_already_score;


}
