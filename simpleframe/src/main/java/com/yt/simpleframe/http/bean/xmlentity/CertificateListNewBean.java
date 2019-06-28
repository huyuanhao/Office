package com.yt.simpleframe.http.bean.xmlentity;

import com.yt.simpleframe.http.bean.BaseBean;
import com.yt.simpleframe.http.bean.entity.CertificateInfo;
import com.yt.simpleframe.http.bean.entity.CertificateInfoNew;
import com.yt.simpleframe.http.bean.entity.PagingInfo;

/**
 * Created by fanliang on 18/6/9.
 */

public class CertificateListNewBean extends BaseBean {
    private PagingInfo<CertificateInfoNew> DATA;

    public PagingInfo<CertificateInfoNew> getDATA() {
        return DATA;
    }

    public void setDATA(PagingInfo<CertificateInfoNew> DATA) {
        this.DATA = DATA;
    }
}
