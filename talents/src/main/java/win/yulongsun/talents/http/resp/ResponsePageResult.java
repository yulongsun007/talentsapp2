package win.yulongsun.talents.http.resp;

import java.util.ArrayList;
import java.util.List;

public class ResponsePageResult<T> {
    //页码
    public int pageNumber = 1;
    //总页数
    public int totalPages;
    //每页大小
    public int pageSize;
    //总共多少条记录
    public int totalRows;
    public List<T> rows = new ArrayList<T>();

    @Override
    public String toString() {
        return "ResponseMessagePageResult [pageNumber=" + pageNumber
                + ", totalPages=" + totalPages + ", pageSize=" + pageSize
                + ", totalRows=" + totalRows + ", rows=" + rows + "]";
    }

}
