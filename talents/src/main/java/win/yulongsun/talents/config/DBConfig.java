package win.yulongsun.talents.config;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Create By : yulongsun
 * Date At : 16/10/10
 * Desc :  数据库版配置
 */
@Database(name = DBConfig.NAME, version = DBConfig.VERSION)
public class DBConfig {

    public static final String NAME = "talent_hr"; // we will add the .db extension

    public static final int VERSION = 1;
}
