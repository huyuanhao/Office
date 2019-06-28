package com.powerrich.office.oa.enums;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/6/7/007
 * 版权：
 */
public enum ModifyPhoneType {
    页面1(0),页面2(1),页面3(2);

    int code;

    ModifyPhoneType(int code) {
        this.code = code;
    }
    public int getCode(){
        return code;
    }
}
