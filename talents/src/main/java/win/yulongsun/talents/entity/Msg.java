package win.yulongsun.talents.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ModelContainer;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;
import java.util.Date;

import win.yulongsun.talents.config.DBConfig;

//推送消息
@ModelContainer
@Table(database = DBConfig.class)
public class Msg extends BaseModel implements Serializable{
    @PrimaryKey
    public Integer msg_id;
    @Column
    @JSONField(name = "msgFromId")
    public Integer msg_from_id;
    @Column
    @JSONField(name = "msgToId")
    public Integer msg_to_id;
    @Column
    @JSONField(name = "msgTitle")
    public String  msg_title;
    @Column
    @JSONField(name = "msgContent")
    public String  msg_content;
    @Column
    @JSONField(name = "msgType")
    public String  msg_type;
    @Column
    @JSONField(name = "createAt")
    public Date    create_at;
}