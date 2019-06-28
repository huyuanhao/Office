package com.powerrich.office.oa.enums;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/6/7/007
 * 版权：
 */
public enum  ProcessType {
    暂存(0),已办理(1),已完结(2);

    int code;

    ProcessType(int code) {
        this.code = code;
    }
    public int getCode(){
        return code;
    }
}
