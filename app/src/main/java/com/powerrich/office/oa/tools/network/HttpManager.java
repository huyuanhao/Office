package com.powerrich.office.oa.tools.network;

import android.os.Looper;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.powerrich.office.oa.SIMeID.SIMeIDApplication;
import com.powerrich.office.oa.bean.QRCommitResult;
import com.powerrich.office.oa.bean.QRQuery;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.StringUtils;
import com.powerrich.office.oa.tools.network.entity.NetStatus;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.lzy.okgo.utils.HttpUtils.runOnUiThread;

/**
 * 暂时网络工具类（等待封装... ）
 *
 * @author Administrator
 * @date 2018/10/31 09:56
 */
public class HttpManager {


    public static String client_id = "c2e580fd36a147bdbb6519e206985a52";
    public static String client_secret = "0eee82";
    private static OkHttpClient client;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static boolean isParams = true;


    static {
        initOkHttp();
    }

    public HttpManager() {

    }

    public static void setParams(boolean isParams2) {
        isParams = isParams2;
    }

    public static void postNetWork(String url, Map<String, Object> reqBody, final INetLister iNetLister) {

        JSONObject jsonObject = new JSONObject(reqBody);
        Log.i("jsc", isParams + "-" + "地址url: " + url + "-参数：" + jsonObject.toString());
        Map<String, Object> stringStringHashMap;

        if (isParams) {
            stringStringHashMap = new HashMap<>();
            stringStringHashMap.put("params", jsonObject.toString());
        } else {
            stringStringHashMap = reqBody;
        }
        isParams = true;
        JSONObject jsonObject1 = new JSONObject(stringStringHashMap);
//        Log.i("jsc", "postNetWork: " + jsonObject1.toString());


        //  RequestBody requestBody1 = RequestBody.create(JSON, "params=" + toURLEncoded(jsonObject.toString()));
        RequestBody requestBody = setRequestBody(stringStringHashMap);


        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        Response response = null;
        // 创建请求对象
        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                Log.i("jsc", "onFailure: " + e.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        iNetLister.onError(e.getMessage());
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {


                final String string = response.body().string();
                Log.i("jsc", "run: " + string);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        NetStatus netStatus = SIMeIDApplication.getmGson().fromJson(string, NetStatus.class);

                        if (iNetLister != null) {


                            if (netStatus.getCode().equals("200")) {
                                //    if (netStatus.getSuccess()) {
                                iNetLister.onSuccess(netStatus);
                                //     } else {
                                //         iNetLister.onError("" + netStatus.getMsg());
                                //       }
                                //accessToken非法 accessToken有效期2小时。
                            } else if (netStatus.getCode().equals("201")) {
                                String errorStr = BeanUtils.isEmptyStr(netStatus.getMsg()) ? CodeUtils.getErrorMsg(netStatus.getCode()) : netStatus.getMsg();
                                iNetLister.onError("" + errorStr);
                            } else {
                                String errorStr = CodeUtils.getErrorMsg(netStatus.getCode());
                                iNetLister.onError("" + errorStr);
                            }
                        }


                    }
                });
            }
        });


    }


    public static String toURLEncoded(String paramString) {
        if (paramString == null || paramString.equals("")) {
            Log.e("jsc", "toURLEncoded error:" + paramString);
            return "";
        }

        try {
            String str = new String(paramString.getBytes(), "UTF-8");
            str = URLEncoder.encode(str, "UTF-8");
            return str;
        } catch (Exception localException) {
            localException.printStackTrace();
            Log.e("jsc", "toURLEncoded error:" + paramString, localException);
        }

        return "";
    }


    public static void getNetWork(String url, Map<String, String> objectMap, final INetLister iNetLister) {
        if (objectMap != null) {
            url = attachHttpGetParams(url, objectMap);
        }

        Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String string = response.body().string();
                            Log.i("jsc", "run: ");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

    }

    public static String attachHttpGetParams(String url, Map<String, String> params) {

        Iterator<String> keys = params.keySet().iterator();
        Iterator<String> values = params.values().iterator();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("?");

        for (int i = 0; i < params.size(); i++) {
            String value = null;
            try {
                value = URLEncoder.encode(values.next(), "utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }

            stringBuffer.append(keys.next() + "=" + value);
            if (i != params.size() - 1) {
                stringBuffer.append("&");
            }
            Log.i("jsc", "stringBuffer" + stringBuffer.toString());
        }

        return url + stringBuffer.toString();
    }

    public static boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }


    /**
     * post的请求参数，构造RequestBody
     *
     * @param BodyParams
     * @return
     */
    private static RequestBody setRequestBody(Map<String, Object> BodyParams) {
        RequestBody body = null;
        okhttp3.FormBody.Builder formEncodingBuilder = new okhttp3.FormBody.Builder();

        if (BodyParams != null) {
            Iterator<String> iterator = BodyParams.keySet().iterator();
            String key = "";
            while (iterator.hasNext()) {
                key = iterator.next().toString();
                formEncodingBuilder.add(key, (String) BodyParams.get(key));
                Log.d("post http", "post_Params===" + key + "====" + BodyParams.get(key));
            }
        }
        body = formEncodingBuilder.build();
        return body;

    }


    //可添加头的 二维码
    public static void postNetQrWork(String url, String X_31HuiYi_AppAuth, Map<String, Object> reqBody, final IQrNetLister iQrNetLister) {

        JSONObject jsonObject = new JSONObject(reqBody);
        Log.i("jsc", isParams + "-" + "postNetWork-url: " + url + "-参数：" + jsonObject.toString());
//        Map<String, Object> stringStringHashMap;
////
////        if (isParams) {
////            stringStringHashMap = new HashMap<>();
////            stringStringHashMap.put("params", jsonObject.toString());
////        } else {
////            stringStringHashMap = reqBody;
////        }
////

        isParams = true;
        JSONObject jsonObject1 = new JSONObject(reqBody);
        Log.i("jsc-net", "postNetWork: " + jsonObject1.toString());


        // RequestBody requestBody1 = RequestBody.create(JSON, "");
        RequestBody requestBody = setRequestBody(reqBody);


        Log.e("jsc-net", "HttpManager-postNetQrWork:" + X_31HuiYi_AppAuth);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/json")
                .addHeader("X_31HuiYi_AppAuth", X_31HuiYi_AppAuth)
                .post(requestBody)
                .build();

        Response response = null;
        // 创建请求对象
        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("jsc", "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                Log.e("jsc-net", "HttpManager-onResponse:" + response.code());
                final String string = response.body().string();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Log.i("jsc-net", "run: " + string);
                        //SignInCode
                        QRQuery netStatus = SIMeIDApplication.getmGson().fromJson(string, QRQuery.class);

                        if (iQrNetLister != null) {
                            if (netStatus.getCode() == 0) {

                                if (null != netStatus.getBody()) {
                                    String signInCode = netStatus.getBody().getSignInCode();
                                    iQrNetLister.onSuccess(signInCode);
                                } else {
                                    iQrNetLister.onSuccess("");
                                }


                            } else {
                                iQrNetLister.onError(netStatus.getDefaultMessage());
                            }
                        }

   /*
                     NetStatus netStatus = SIMeIDApplication.getmGson().fromJson(string, NetStatus.class);

                        if (iNetLister != null) {

                            if (netStatus.getCode().equals("200")) {
                                //    if (netStatus.getSuccess()) {
                                iNetLister.onSuccess(netStatus);
                                //     } else {
                                //         iNetLister.onError("" + netStatus.getMsg());
                                //       }
                                //accessToken非法 accessToken有效期2小时。
                            } else {
                                iNetLister.onError("" + CodeUtils.getErrorMsg(netStatus.getCode()));
                            }
                        }*/


                    }
                });
            }
        });


    }

    //可添加头的 二维码
    public static void postNetQrCommit(String url, String reqBody, final IQrNetLister iQrNetLister) {
        Log.e("jsc-net", "提交的数据HttpManager-postNetQrCommit:" + reqBody);
        //     JSONObject jsonObject = new JSONObject(reqBody);
        //     Log.i("jsc", isParams + "-" + "postNetWork-url: " + url + "-参数：" + jsonObject.toString());
//        Map<String, Object> stringStringHashMap;
////
////        if (isParams) {
////            stringStringHashMap = new HashMap<>();
////            stringStringHashMap.put("params", jsonObject.toString());
////        } else {
////            stringStringHashMap = reqBody;
////        }
////

//        isParams = true;
//        JSONObject jsonObject1 = new JSONObject(reqBody);
//        Log.i("jsc-net", "postNetWork: " + jsonObject1.toString());


        RequestBody requestBody = RequestBody.create(JSON, reqBody);
        //   RequestBody requestBody = setRequestBody(reqBody);


        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/json")
                .post(requestBody)
                .build();

        Response response = null;
        // 创建请求对象
        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("jsc", "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                Log.e("jsc-net", "HttpManager-onResponse:" + response.code());
                final String string = response.body().string();
                final int code = response.code();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Log.i("jsc-net", "run: " + string);
                        //SignInCode
                        QRCommitResult netStatus = SIMeIDApplication.getmGson().fromJson(string, QRCommitResult.class);

                        if (iQrNetLister != null) {
                            if (netStatus.getCode() == 0) {
                                iQrNetLister.onSuccess("");
                            } else {
                                iQrNetLister.onError(netStatus.getDefaultMessage());
                            }
                        }

   /*
                     NetStatus netStatus = SIMeIDApplication.getmGson().fromJson(string, NetStatus.class);

                        if (iNetLister != null) {

                            if (netStatus.getCode().equals("200")) {
                                //    if (netStatus.getSuccess()) {
                                iNetLister.onSuccess(netStatus);
                                //     } else {
                                //         iNetLister.onError("" + netStatus.getMsg());
                                //       }
                                //accessToken非法 accessToken有效期2小时。
                            } else {
                                iNetLister.onError("" + CodeUtils.getErrorMsg(netStatus.getCode()));
                            }
                        }*/


                    }
                });
            }
        });


    }

    public interface INetLister {
        void onSuccess(NetStatus netStatus);

        void onError(String error);
    }


    public interface IQrNetLister {
        void onSuccess(String netStatus);

        void onError(String error);
    }


    private static void initOkHttp() {

        if (null == client) {
            client = new OkHttpClient.Builder()
                    .connectTimeout(120, TimeUnit.SECONDS)
                    .readTimeout(120, TimeUnit.SECONDS)
                    .build();
        }

    }
}
