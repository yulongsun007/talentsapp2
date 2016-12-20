package win.yulongsun.talents.common;

/**
 * Create By: yulongsun
 * Date at: 2016/9/1
 * Desc :
 */
public interface Constant {

    String URL = "http://192.168.0.106:8081/talents/";

    //闪屏时间
    Integer SPLASH_TIME = 2000;

    String MODE_NAME = "mode_name";

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
