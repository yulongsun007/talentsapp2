package win.yulongsun.framework.util;

import com.google.gson.Gson;
import com.google.gson.JsonNull;

import java.lang.reflect.Type;

/**
 * @author sunyulong on 2016/12/14.
 *         Json解析工具类
 */
public class JsonUtil {

    private static Gson gson = new Gson();

    private JsonUtil() {
    }

    /**
     * 将对象转成Json字符串
     *
     * @param object
     * @return
     */
    public static String toJson(Object object) {
        if (object == null) {
            gson.toJson(JsonNull.INSTANCE);
        }
        return gson.toJson(object);
    }

    /**
     * 用来将JSON串转为对象，但此方法不可用来转带泛型的集合
     *
     * @param json
     * @param classOfT
     * @return
     */
    public static <T> Object fromJson(String json, Class<T> classOfT) {
        return gson.fromJson(json, (Type) classOfT);
    }
}
