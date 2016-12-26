package win.yulongsun.talents.common;

/**
 * Create By: yulongsun
 * Date at: 2016/9/1
 * Desc :
 */
public interface Constant {

    String URL = "http://192.168.0.109:8081/talents/";
//    String URL = "http://192.168.1.133:8081/talents/";

    //闪屏时间
    Integer SPLASH_TIME = 2000;

    String MODE_NAME = "mode_name";

    String isLogin = "is_login";//是否已经登录

    interface LEARN_PROGRESS {
        int UN_START   = 0;
        int PROGESSING = 1;
        int COMMPLATE  = 2;
    }


    //角色
    interface ROLE {
        int HR       = 1;
        int REFERRER = 2;
        int STU      = 3;
    }

    //模式
    interface MODE_VALUE {
        int EDIT   = 0;//编辑
        int ADD    = 1;//添加
        int SELECT = 2;//选择学习计划
        int LEARN  = 3;//学习此计划
        int CHECK  = 4;//审查通过
        int SEND   = 5;//发送
        int RE      =6;//回复
    }

    //http返回状态码
    interface CODE {
        Integer SUCCESS = 200;
        Integer ERROR   = 400;
    }

    interface TOAST_MSG {
        String CONNECT_ERROR = "连接服务器失败";
    }

    interface APPLY_STATUS {
        int UN_COMMIT       = 0;//未投递
        int COMMIT          = 1;//已提交
        int REFERRER_REJECT = 2;//推荐人拒绝
        int REFERRER_PASS   = 3;//推荐人已推荐
        int HR_REJECT       = 4;//已谢绝
        int HR_PASS         = 5;//面试邀请
    }

}
