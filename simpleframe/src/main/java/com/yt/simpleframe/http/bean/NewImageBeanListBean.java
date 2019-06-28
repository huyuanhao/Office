package com.yt.simpleframe.http.bean;


import com.yt.simpleframe.http.bean.entity.NewImageInfo;

import java.util.List;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/5/25 0025
 * 版权：
 */

public class NewImageBeanListBean extends BaseBean {

    private List<NewImageInfo> DATA;

    public List<NewImageInfo> getDATA() {
        return DATA;
    }

    public void setDATA(List<NewImageInfo> DATA) {
        this.DATA = DATA;
    }
}
