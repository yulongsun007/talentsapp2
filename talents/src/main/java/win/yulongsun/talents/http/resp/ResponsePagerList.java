package win.yulongsun.talents.http.resp;

public class ResponsePagerList<T> extends BaseResponse {
    public ResponsePageResult<T> result;

    @Override
    public String toString() {
        return "ResponseMessageList [result=" + result + "]";
    }

}
