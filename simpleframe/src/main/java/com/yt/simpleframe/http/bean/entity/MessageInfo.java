package com.yt.simpleframe.http.bean.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/6/8.
 */

public class MessageInfo implements Serializable{
    /**
     * id	string 	id编号
     prokeyid	string 	业务id
     content	string 	消息内容
     status	string 	消息状态：0未读，1已读
     pushtime	string 	推送时间
     pushuserid	string 	推送对象（别名userid），若为空，则表示后台管理员用户手动推送的通知公告
     msgtype	string 	1人工实名认证通过，2项目办理相关信息，3通知公告
     */
    /**
     * {
     "CONTENT": "{\"INFORM\":{\"METHOD\":\"3\",\"NAME\":\"公共场所卫生首次许可\",\"RECORDID\":\"20180719213810424838941137\",\"STATUSMSG\":\"您申报的【公共场所卫生首次许可】项目已通过【受理】环节的审批，您可到我的办件中查询具体详细信息。\",\"TYPE\":\"2\"}}",
     "ID": "20180719213810424838941137",
     "MSGTYPE": "2",
     "PROKEYID": "679ec425aaa64b9abfb23880ef1f56d5",
     "PUSHTIME": "2018-07-19 21:38:10",
     "PUSHUSERID": "201801261631497478967845420",
     "STATUS": "0"
     }
     * */
    private String ID;
    private String PROKEYID;
    private String CONTENT;
    private String STATUS;
    private String PUSHTIME;
    private String PUSHUSERID;
    private String MSGTYPE;


    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPROKEYID() {
        return PROKEYID;
    }

    public void setPROKEYID(String PROKEYID) {
        this.PROKEYID = PROKEYID;
    }

    public String getCONTENT() {
        return CONTENT;
    }

    public void setCONTENT(String CONTENT) {
        this.CONTENT = CONTENT;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getPUSHTIME() {
        return PUSHTIME;
    }

    public void setPUSHTIME(String PUSHTIME) {
        this.PUSHTIME = PUSHTIME;
    }

    public String getPUSHUSERID() {
        return PUSHUSERID;
    }

    public void setPUSHUSERID(String PUSHUSERID) {
        this.PUSHUSERID = PUSHUSERID;
    }

    public String getMSGTYPE() {
        return MSGTYPE;
    }

    public void setMSGTYPE(String MSGTYPE) {
        this.MSGTYPE = MSGTYPE;
    }
}
