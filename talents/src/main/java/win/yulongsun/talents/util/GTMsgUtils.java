package win.yulongsun.talents.util;

import android.os.Handler;
import android.os.Message;

import com.gexin.fastjson.JSON;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.TransmissionTemplate;

import win.yulongsun.talents.entity.Msg;

/**
 * Created by sunyulong on 2016/12/20.
 */
public class GTMsgUtils {
    static String appId        = "F9PVBw1Oz29uS98WReT6F8";
    static String appKey       = "zvJbaPnYNJ6KmKfYGGuAi6";
    static String masterSecret = "NGI8nPHBK2AKOTHVfv5gU2";
    static String host         = "http://sdk.open.api.igexin.com/apiex.htm";


    private GTMsgUtils() {
    }


    /**
     * 推送通知栏消息
     */
    public static void pushMsgToSingle(final Msg msgEntity) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                IGtPush push = new IGtPush(host, appKey, masterSecret);
                //tmp
        TransmissionTemplate tmp = new TransmissionTemplate();
        tmp.setAppId(appId);
        tmp.setAppkey(appKey);
        tmp.setTransmissionType(2);
        tmp.setTransmissionContent(JSON.toJSONString(msgEntity));
//                NotificationTemplate tmp = new NotificationTemplate();
//                tmp.setTransmissionContent(JSON.toJSONString(msgEntity));
//                tmp.setTransmissionType(2);
//                tmp.setAppId(appId);
//                tmp.setAppkey(appKey);
//                Style1 style1 = new Style1();
//                style1.setTitle(msgEntity.msg_title);
//                style1.setText(msgEntity.msg_type);
//                tmp.setStyle(style1);

                //msg
                SingleMessage msg = new SingleMessage();
                msg.setOffline(true);// 离线有效时间，单位为毫秒，可选
                msg.setOfflineExpireTime(24 * 3600 * 1000);
                msg.setData(tmp);
                msg.setPushNetWorkType(0);// 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
                //target
                Target target = new Target();
                target.setAppId(appId);
                target.setAlias(String.valueOf(msgEntity.msg_to_id));
                //push
                IPushResult ret = null;
                try {
                    ret = push.pushMessageToSingle(msg, target);
                } catch (RequestException e) {
                    e.printStackTrace();
                    ret = push.pushMessageToSingle(msg, target, e.getRequestId());
                }
                Message message = new Message();
                message.obj = ret;
                handler.sendMessage(message);
            }
        }).start();
    }

    private static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

//            //result
//            PushResult pushResult = new PushResult();
//            if (ret != null) {
//                String result = (String) ret.getResponse().get("result");
//                System.out.println(ret.getResponse().toString());
//                if ("ok".equals(result)) {
//                    pushResult.success = true;
//                    pushResult.message = "推送成功";
//                } else {
//                    pushResult.success = false;
//                    pushResult.message = "推送失败";
//                }
//            } else {
//                pushResult.success = false;
//                pushResult.message = "服务器异常";
//            }
        }
    };


    //推送结果
    static class PushResult {
        public boolean success;
        public String  message;
    }

    public static void main(String[] args) {
//        User user = new User();
//        user.setUserName("zhansgan222");
//        user.setUserToken("2222");
        Msg msg = new Msg();
        msg.msg_from_id = 1;
        msg.msg_to_id = 2;
        GTMsgUtils.pushMsgToSingle(msg);
    }
}
