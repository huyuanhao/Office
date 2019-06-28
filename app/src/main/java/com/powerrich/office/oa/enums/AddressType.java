package com.powerrich.office.oa.enums;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/6/7/007
 * 版权：
 */
public enum AddressType {
    添加(1),修改(2);

    int code;

    AddressType(int code) {
        this.code = code;
    }
    public int getCode(){
        return code;
    }
}
