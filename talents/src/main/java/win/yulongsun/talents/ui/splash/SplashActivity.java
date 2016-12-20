package win.yulongsun.talents.ui.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.igexin.sdk.PushManager;

import win.yulongsun.framework.cache.ACache;
import win.yulongsun.talents.R;
import win.yulongsun.talents.common.Constant;
import win.yulongsun.talents.config.CacheConstant;
import win.yulongsun.talents.service.DemoIntentService;
import win.yulongsun.talents.service.DemoPushService;
import win.yulongsun.talents.ui.login.LoginActivity;
import win.yulongsun.talents.ui.main.MainActivity;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        final String isLogin = ACache.get(SplashActivity.this).getAsString(CacheConstant.isLogin);
        //初始化个推
        PushManager.getInstance().initialize(SplashActivity.this, DemoPushService.class);
        //
        // com.getui.demo.DemoIntentService 为第三方自定义的推送服务事件接收类
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), DemoIntentService.class);

        //
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if ("1".equals(isLogin)) {
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                } else {
                    intent = new Intent(SplashActivity.this, LoginActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, Constant.SPLASH_TIME);
    }
}
