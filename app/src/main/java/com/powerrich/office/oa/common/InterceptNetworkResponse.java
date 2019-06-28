package com.powerrich.office.oa.common;

import android.content.Context;

import com.powerrich.office.oa.network.http.HttpResponse;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/17.
 * 拦截请求响应信息，可以根据返回状态码处理自己的逻辑
 */

public interface InterceptNetworkResponse {

    boolean intercept(Context context, HttpResponse response);

    void addInterceptor(Interceptor interceptor);

    void addInterceptors(ArrayList<Interceptor> interceptors);
}
