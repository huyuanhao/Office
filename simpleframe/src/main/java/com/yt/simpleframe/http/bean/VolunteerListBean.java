package com.yt.simpleframe.http.bean;

import com.yt.simpleframe.http.bean.entity.PagingInfo;
import com.yt.simpleframe.http.bean.entity.VolunteerInfo;

/**
 * Created by fanliang on 18/6/9.
 */

public class VolunteerListBean extends BaseBean {
    private PagingInfo<VolunteerInfo> DATA;

    public PagingInfo<VolunteerInfo> getDATA() {
        return DATA;
    }

    public void setDATA(PagingInfo<VolunteerInfo> DATA) {
        this.DATA = DATA;
    }
}
