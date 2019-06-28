package com.powerrich.office.oa.enums;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/6/7/007
 * 版权：
 */
public enum SearchFragmentType {
    暂存(0),已办理(1),已完结(3),咨询(3),投诉(4),建议(5),预约(6),收藏(7);

    int code;

    SearchFragmentType(int code) {
        this.code = code;
    }
    public int getCode(){
        return code;
    }
}
