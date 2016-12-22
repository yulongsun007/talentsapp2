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

    /*模式*/
    interface MODE_VALUE {
        int EDIT = 0;
        int ADD  = 1;
    }

    //http返回状态码
    interface CODE {
        Integer SUCCESS = 200;
        Integer ERROR   = 400;
    }

    interface TOAST_MSG {
        String CONNECT_ERROR = "连接服务器失败";
    }

}
