package com.yt.simpleframe.http.bean;

import com.yt.simpleframe.http.bean.entity.MessageInfo;

/**
 * Created by fanliang on 18/6/9.
 */

public class MessageBean extends BaseBean{
    private MessageInfo DATA;


    public MessageInfo getDATA() {
        return DATA;
    }

    public void setDATA(MessageInfo DATA) {
        this.DATA = DATA;
    }
}
