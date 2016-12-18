package win.yulongsun.talents.app;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Create By: yulongsun
 * Date at: 2016/10/8
 * Desc :
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //config dbfow
        FlowConfig flowConfig = new FlowConfig.Builder(this).openDatabasesOnInit(true).build();
        FlowManager.init(flowConfig);
    }
}
