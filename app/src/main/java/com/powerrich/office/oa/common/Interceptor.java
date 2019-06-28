package com.powerrich.office.oa.common;

import android.content.Context;

/**
 * Created by Administrator on 2018/1/17.
 */

public interface Interceptor {

    /** 拦截令牌失效*/
    String INTERCEPT_TOKEN_CODE = "-403";

    /**
     * 处理不同类型的逻辑
     * */
    void doInterceptWork(Context context);

    /**
     * 返回当前拦截器的错误码
     * */
    String getErrorCode();
}
