package win.yulongsun.talents.http.resp;

/**
 * @author yulongsun
 * @Date 2016/4/25
 * @Version 1.0.0
 * @Description
 */
public class BaseResponse {
    public String  msg;
    /**
     * 状态码：200-成功，400-失败
     * {@link win.yulongsun.talents.common.Constant.CODE}
     */
    public int code;
}
