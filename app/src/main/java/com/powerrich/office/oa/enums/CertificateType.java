package com.powerrich.office.oa.enums;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/6/7/007
 * 版权：
 */
public enum CertificateType {
    查看(1),选择(2);

    int code;

    CertificateType(int code) {
        this.code = code;
    }
    public int getCode(){
        return code;
    }
}
