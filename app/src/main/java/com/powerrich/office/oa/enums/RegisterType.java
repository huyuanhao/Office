package com.powerrich.office.oa.enums;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/6/7/007
 * 版权：
 */
public enum RegisterType {
    注册(1),忘记密码(2),注册验证(3),主页认证(4);

    int code;

    RegisterType(int code) {
        this.code = code;
    }
    public int getCode(){
        return code;
    }
}
