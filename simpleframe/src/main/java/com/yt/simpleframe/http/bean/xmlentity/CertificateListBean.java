package com.yt.simpleframe.http.bean.xmlentity;

import com.yt.simpleframe.http.bean.BaseBean;
import com.yt.simpleframe.http.bean.entity.CertificateInfo;
import com.yt.simpleframe.http.bean.entity.PagingInfo;

/**
 * Created by fanliang on 18/6/9.
 */

public class CertificateListBean extends BaseBean{
    private PagingInfo<CertificateInfo> DATA;

    public PagingInfo<CertificateInfo> getDATA() {
        return DATA;
    }

    public void setDATA(PagingInfo<CertificateInfo> DATA) {
        this.DATA = DATA;
    }
}
