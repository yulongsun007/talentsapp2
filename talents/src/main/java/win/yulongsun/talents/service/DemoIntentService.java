package win.yulongsun.talents.service;

import android.content.Context;

import com.gexin.fastjson.JSON;
import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTTransmitMessage;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import win.yulongsun.talents.entity.Msg;
import win.yulongsun.talents.event.MsgEvent;

/**
 * 继承 GTIntentService 接收来自个推的消息, 所有消息在线程中回调, 如果注册了该服务, 则务必要在 AndroidManifest中声明, 否则无法接受消息<br>
 * onReceiveMessageData 处理透传消息<br>
 * onReceiveClientId 接收 cid <br>
 * onReceiveOnlineState cid 离线上线通知 <br>
 * onReceiveCommandResult 各种事件处理回执 <br>
 */
public class DemoIntentService extends GTIntentService {

    public DemoIntentService() {

    }

    @Override
    public void onReceiveServicePid(Context context, int pid) {
    }

    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage msg) {
        Logger.d("onReceiveMessageData -> " + "msg = " + new String(msg.getPayload()));
        String msgJson = new String(msg.getPayload());
        Msg msgEntity = JSON.parseObject(msgJson, Msg.class);
        msgEntity.save();
        EventBus.getDefault().post(new MsgEvent(msgEntity));
    }

    @Override
    public void onReceiveClientId(Context context, String clientid) {
        Logger.d( "onReceiveClientId -> " + "clientid = " + clientid);
    }

    @Override
    public void onReceiveOnlineState(Context context, boolean online) {
    }

    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage cmdMessage) {
    }
}
