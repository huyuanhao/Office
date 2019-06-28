package com.powerrich.office.oa.thirdapp.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lzy.okgo.https.HttpsUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/5/21 0021
 * 版权：
 */


public class ThirdApiManager {


    public static final String ULR = "http://cmsv3.aheading.com/";

    public static final String base_url = "http://cmswebv3.aheading.com/";


    public static final int TIME_OUT = 5;
    private OkHttpClient okHttpClient;
    private Retrofit retrofit;
    public ThirdApiService api;
    private static ThirdApiManager manager;

    private static class Utils {
        public static ThirdApiManager createManager() {
            manager = new ThirdApiManager();
            return manager;
        }
    }


    public static ThirdApiManager getInstance() {
        if (manager == null) {
            manager = Utils.createManager();
        }
        return manager;
    }

    public static ThirdApiService getApi(){
        getInstance();
        return manager.api;
    }


    private ThirdApiManager() {
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
                    .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                    .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
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
//                   .addConverterFactory(MyConverter.create()) // 添加Gson转换器
//                    .addConverterFactory(SoapConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create()) // 添加Gson转换器
                    .client(okHttpClient)
                    .build();
        if (api == null) {
            api = retrofit.create(ThirdApiService.class);
        }
    }
}