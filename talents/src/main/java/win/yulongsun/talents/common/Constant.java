package win.yulongsun.talents.common;

/**
 * Create By: yulongsun
 * Date at: 2016/9/1
 * Desc :
 */
public class Constant {

    //    String URL = "http://192.168.1.105:8081/talents/";
    public static String URL = "http://118.190.173.67:8080/jfinal_talents/";
//    public static String URL = "http://192.168.1.102:8081/talents/";
//    String URL = "http://192.168.1.133:8081/talents/";

    //闪屏时间
    public static Integer SPLASH_TIME = 2000;

    public static String MODE_NAME = "mode_name";

    public static String isLogin = "is_login";//是否已经登录


    //角色
    public interface ROLE {
        int HR       = 1;
        int REFERRER = 2;
        int STU      = 3;
    }

    //模式
    public interface MODE_VALUE {
        int EDIT     = 0;//编辑
        int ADD      = 1;//添加
        int SELECT   = 2;//选择学习计划
        int LEARN    = 3;//学习此计划
        int CHECK    = 4;//审查通过
        int SEND     = 5;//发送
        int RE       = 6;//回复
        int FEEDBACK = 7;
    }

    //http返回状态码
    public interface CODE {
        Integer SUCCESS = 200;
        Integer ERROR   = 400;
    }

    public interface TOAST_MSG {
        String CONNECT_ERROR = "连接服务器失败";
    }

    public interface APPLY_STATUS {
        int UN_COMMIT       = 0;//未投递
        int COMMIT          = 1;//已提交
        int REFERRER_REJECT = 2;//推荐人拒绝
        int REFERRER_PASS   = 3;//推荐人已推荐
        int HR_REJECT       = 4;//已谢绝
        int HR_PASS         = 5;//面试邀请
    }

}
