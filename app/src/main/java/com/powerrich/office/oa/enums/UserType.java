package com.powerrich.office.oa.enums;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/6/7/007
 * 版权：
 */
public enum UserType {
    个人(1),法人(2),企业(3);

    int code;

    UserType(int code) {
        this.code = code;
    }
    public int getCode(){
        return code;
    }
}
