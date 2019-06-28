package com.yt.simpleframe.http.bean;

import com.yt.simpleframe.http.bean.entity.PagingInfo;
import com.yt.simpleframe.http.bean.entity.ReservationInfo;

/**
 * Created by fanliang on 18/6/9.
 */

public class ReservationListBean extends BaseBean{
    private PagingInfo<ReservationInfo> DATA;

    public PagingInfo<ReservationInfo> getDATA() {
        return DATA;
    }

    public void setDATA(PagingInfo<ReservationInfo> DATA) {
        this.DATA = DATA;
    }
}
