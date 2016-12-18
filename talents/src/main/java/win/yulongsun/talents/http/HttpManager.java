package win.yulongsun.talents.http;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author sunyulong on 2016/12/4.
 *         封装Http相关请求
 *         1.Retrofit+Rxjava+okhttp基本使用方法
 *         2.统一处理请求数据格式
 *         3.统一的ProgressDialog和回调Subscriber处理
 *         4.取消http请求
 *         5.预处理http请求
 *         6.返回数据的统一判断
 *         7.失败后的retry封装处理
 *         8.RxLifecycle管理生命周期，防止泄露
 *         9.文件上传和文件下载（支持多文件断点续传）
 *         10.数据持久化
 */
public class HttpManager {

    private static final String BASE_URL = "";
    private final           HttpService mHttpService;
    private volatile static HttpManager INSTANCE;

    public static HttpManager newInstance() {
        if (INSTANCE == null) {
            synchronized (HttpManager.class) {
                INSTANCE = new HttpManager();
            }
        }
        return INSTANCE;
    }


    private HttpManager() {
        Retrofit mRetrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(HttpManager.BASE_URL)
                .build();
        mHttpService = mRetrofit.create(HttpService.class);
    }

}
