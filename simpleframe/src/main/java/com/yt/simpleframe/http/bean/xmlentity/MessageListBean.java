package com.yt.simpleframe.http.bean.xmlentity;

import com.yt.simpleframe.http.bean.BaseBean;
import com.yt.simpleframe.http.bean.entity.MessageInfo;
import com.yt.simpleframe.http.bean.entity.PagingInfo;

/**
 * Created by fanliang on 18/6/9.
 */

public class MessageListBean extends BaseBean{
    private PagingInfo<MessageInfo> DATA;

    public PagingInfo<MessageInfo> getDATA() {
        return DATA;
    }

    public void setDATA(PagingInfo<MessageInfo> DATA) {
        this.DATA = DATA;
    }
}
