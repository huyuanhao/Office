package com.yt.simpleframe.http.bean;

import com.yt.simpleframe.http.bean.entity.CollectionInfo;
import com.yt.simpleframe.http.bean.entity.PagingInfo;

/**
 * Created by fanliang on 18/6/9.
 */

public class CollectionListBean extends BaseBean{
    private PagingInfo<CollectionInfo> DATA;

    public PagingInfo<CollectionInfo> getDATA() {
        return DATA;
    }

    public void setDATA(PagingInfo<CollectionInfo> DATA) {
        this.DATA = DATA;
    }
}
