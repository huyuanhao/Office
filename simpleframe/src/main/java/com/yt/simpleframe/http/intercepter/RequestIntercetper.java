package com.yt.simpleframe.http.intercepter;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/5/25 0025
 * 版权：
 */

public class RequestIntercetper implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();
        //这里拼接字符串

//        oldRequest.newBuilder().method(oldRequest.method(), RequestBody.create(MediaType.parse("text/xml;charset=UTF-8"),str));
//        HttpUrl.Builder builder = oldRequest.url()
//                .newBuilder();
        return chain.proceed(oldRequest);


    }
}
