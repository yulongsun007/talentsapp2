package win.yulongsun.talents.entity;

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
    public Integer msg_from_id;
    @Column
    public Integer msg_to_id;
    @Column
    public String  msg_title;
    @Column
    public String  msg_content;
    @Column
    public String  msg_type;
    @Column
    public Date    create_at;
}