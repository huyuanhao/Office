package com.yt.simpleframe.http.bean;

import com.yt.simpleframe.http.bean.entity.LogisticsInfo;
import com.yt.simpleframe.http.bean.entity.PagingInfo;

/**
 * Created by fanliang on 18/6/9.
 */

public class LogisticsListBean extends BaseBean{
    private PagingInfo<LogisticsInfo> DATA;

    public PagingInfo<LogisticsInfo> getDATA() {
        return DATA;
    }

    public void setDATA(PagingInfo<LogisticsInfo> DATA) {
        this.DATA = DATA;
    }
}
