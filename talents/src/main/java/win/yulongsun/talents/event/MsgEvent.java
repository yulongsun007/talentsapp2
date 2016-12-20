package win.yulongsun.talents.event;

import win.yulongsun.talents.entity.Msg;

/**
 * @author sunyulong on 2016/12/20.
 */
public class MsgEvent {
    private Msg msgEntity;

    public MsgEvent(Msg msgEntity) {
        this.msgEntity = msgEntity;
    }

    public Msg getMsgEntity() {
        return msgEntity;
    }

    public void setMsgEntity(Msg msgEntity) {
        this.msgEntity = msgEntity;
    }
}
