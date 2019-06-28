package com.powerrich.office.oa.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/6/22
 * 版权：
 */

public class EthnicBean implements Serializable{
   private List<EthnicInfo> data;

    public List<EthnicInfo> getData() {
        return data;
    }

    public void setData(List<EthnicInfo> data) {
        this.data = data;
    }
}
