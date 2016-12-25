package win.yulongsun.talents.event;

/**
 * @author sunyulong on 2016/12/25.
 */
public class ActionEvent {
    public static int ADD    = 0;
    public static int DELETE = 1;
    public static int UPDATE = 2;
    public static int QUERY  = 3;

    public int actionType;

    public ActionEvent(int action_type) {
        this.actionType = action_type;
    }

}
