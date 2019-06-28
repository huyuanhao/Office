package com.powerrich.office.oa.enums;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/6/7/007
 * 版权：
 */
public enum AdvisoryType {
    咨询(1),投诉(2),建议(3);

    int code;

    AdvisoryType(int code) {
        this.code = code;
    }
    public int getCode(){
        return code;
    }
}
