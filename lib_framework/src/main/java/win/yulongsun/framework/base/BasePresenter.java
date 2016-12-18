package win.yulongsun.framework.base;

import android.content.Context;


/**
 * @Project Purchaser
 * @Packate me.jiudeng.purchase.ui.presenter
 * @Author yulongsun
 * @Email yulongsun@gmail.com
 * @Date 2016/4/18
 * @Version 1.0.0
 * @Description
 */
public abstract class BasePresenter<T extends IBaseView> {

    protected Context context;
    protected T iView;

    public BasePresenter(Context context, T iView) {
        this.context = context;
        this.iView = iView;
    }



}
