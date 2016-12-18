package win.yulongsun.talents.http.resp;

import java.util.ArrayList;
import java.util.List;

public class ResponseList<T> extends BaseResponse {

    public List<T> data = new ArrayList<T>();

}
