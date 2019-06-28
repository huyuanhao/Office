package com.powerrich.office.oa.enums;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/6/7/007
 * 版权：
 */
public enum SearchActivityType {
    我的办件(0),我的预约(1),我的咨询(2),我的收藏(3);

    int code;

    SearchActivityType(int code) {
        this.code = code;
    }
    public int getCode(){
        return code;
    }
}
