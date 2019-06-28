package com.yt.simpleframe.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lzy.okgo.https.HttpsUtils;
import com.yt.simpleframe.http.intercepter.MyConverter;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/5/21 0021
 * 版权：
 */


public class ApiManager {

    public static final String base_url = "http://218.87.176.156:80/platform/";

    public static final int TIME_OUT = 30;
    private OkHttpClient okHttpClient;
    private Retrofit retrofit;
    public ApiService api;
    private static ApiManager manager;

    private static class Utils {
        public static ApiManager createManager() {
            manager = new ApiManager();
            return manager;
        }
    }


    public static ApiManager getInstance() {


        if (manager == null) {
            manager = Utils.createManager();
        }
        return manager;
    }

    public static ApiService getApi(){
        getInstance();
        return manager.api;
    }


    private ApiManager() {
        initHttpRequest();
    }

    HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory();

    void initHttpRequest() {
        if (okHttpClient == null) {
//            if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();
            okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(logging)
//                    .addInterceptor(new CommonInterceptor())
//                    .addInterceptor(new RequestIntercetper())
                    .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                    .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
//                    .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
//                    .hostnameVerifier(new ApiHelper.SafeHostnameVerifier())
                    .build();


        }

        /**
         * HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory();
         builder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);
         builder.hostnameVerifier(new SafeHostnameVerifier());
         */
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory();
        if (retrofit == null)
            retrofit = new Retrofit.Builder()
                    .baseUrl(base_url)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 添加Rx适配器
                   .addConverterFactory(MyConverter.create()) // 添加Gson转换器
//                    .addConverterFactory(SoapConverterFactory.create())
//                    .addConverterFactory(GsonConverterFactory.create()) // 添加Gson转换器
                    .client(okHttpClient)
                    .build();
        if (api == null) {
            api = retrofit.create(ApiService.class);
        }
    }
}