package win.yulongsun.talents.entity;

/**
 * Created by sunyulong on 2016/11/21.
 */

public class ItemIndex {
    public String[] urls;
    public int resId;
    public String title;

    public ItemIndex(String title) {
        this.title = title;
    }

    public ItemIndex(int resId, String title) {
        this.resId = resId;
        this.title = title;
    }

    public ItemIndex(String[] urls, int resId, String title) {
        this.urls = urls;
        this.resId = resId;
        this.title = title;
    }
}
