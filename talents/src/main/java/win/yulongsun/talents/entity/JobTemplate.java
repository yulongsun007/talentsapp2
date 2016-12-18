package win.yulongsun.talents.entity;

/**
 * Create By: yulongsun
 * Date at: 2016/8/30
 * Desc : 招聘模板
 */
public class JobTemplate {
    public Long    _id;
    public String  jobName;          //职位名
    public String  bizDirectId;      //行业方向Id
    public String  bizDirect;        //行业方向
    public String  skillReq;         //技能要求
    public String  jobExp;           //工作经验
    public String  jobDesc;          //职位描述
    public String  jobAddr;          //工作地点
    public String  jobSalary;        //工作薪资
    public String  companyName;      //公司名
    public Integer level;           //紧急程度
    public String  startAt;         //开始时间
    public String  endAt;           //结束时间
    public String  createAt;         //创建时间
    public Boolean isCheck;
}
