package com.powerrich.office.oa.network.http;

import android.text.TextUtils;

import com.powerrich.office.oa.api.ApiRequest;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.network.base.BaseRequestTask;
import com.powerrich.office.oa.network.base.IRequestItem;
import com.powerrich.office.oa.network.base.IResponseItem;
import com.powerrich.office.oa.network.base.ProtocolType.ResponseEvent;
import com.powerrich.office.oa.network.base.ResponseDataType;
import com.powerrich.office.oa.network.base.ResponseDataType.Encrypt;
import com.powerrich.office.oa.network.base.ResponseDataType.HttpMethod;
import com.powerrich.office.oa.network.base.ResponseDataType.RequestType;
import com.powerrich.office.oa.tools.AppLogCc;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.FileUtils;
import com.powerrich.office.oa.tools.LogUtils;
import com.powerrich.office.oa.tools.Logger;
import com.powerrich.office.oa.tools.LoginUtils;
import com.powerrich.office.oa.tools.StringUtils;

import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


/**
 * FileName    : HttpConnection.java
 * Description : http请求管理
 *
 * @author : 刘剑
 * @version : 1.0
 *          Create Date : 2014-4-9 上午9:37:05
 **/
public class HttpRequestTask extends BaseRequestTask {

    private static final String TAG = "HttpRequestTask";
    /**
     * 缓存区大小
     */
    protected static int DEFAULT_BUFFER_SIZE = 8192;
    /**
     * http请求成功code
     */
    protected static int SUCCESS = 200;
    /**
     * 当前的httpConnection
     */
    private HttpURLConnection conn = null;
    /**
     * 表单请求的分隔符
     */
    private String boundary = "-----------------------------7da2e536604c8";

    public HttpRequestTask() {

    }

    @Override
    public void init(IRequestItem request) {
        super.request = request;
        this.response = new HttpResponse();
    }


    /**
     * 处理httpRequest
     */
    @Override
    public void exec() {
        InputStream is = null;
        OutputStream os = null;
        String actionName = ((ApiRequest) request).getActionName();
        if (BeanUtils.isNullOrEmpty(actionName)) {
            LogUtils.e(TAG, "send request actionName is null or empty!");
            doCallBack(ResponseEvent.ERROR);
            return;
        }
        if (Constants.GET_SERVICEARITICLETYPELISTNEW.equals(actionName) || Constants.GET_CLASSIFYNEW.equals(actionName)) {
            try {
                HttpRequest request = (HttpRequest) this.request;
                request.setUrl(Constants.MB_URL + actionName);
                request.setEncode("UTF-8"); // 门户设置UTF-8，否则是乱码返回
                Logger.e(TAG, "请求的地址：" + request.getUrl());
                //请求开始
                doCallBack(ResponseEvent.START);

				if (HttpMethod.POST == request.getMethod()) {
					if (RequestType.UPLOAD == request.getRequestType()) {
						doForm(os, request);
					} else {
						doPost(os, request);
					}
				} else {
					doGet(os, request);
				}
//                doPost(os, request);
                Logger.e(TAG, "onRequest requestId " + request.getRequestId() + " " + request.getUrl());
                // 获取返回的responseCode
                int responseCode = conn.getResponseCode();
                Logger.e(TAG, "onResponse requestId " + request.getRequestId() + " code:" + responseCode);

                // 根据返回的responseCode进行处理
                if (responseCode == SUCCESS) {
                    //总的响应大小
                    response.setContentSize(conn.getContentLength());
                    //处理包头响应数据
                    processHeaderData(conn.getHeaderFields());
                    Logger.e(TAG, "onResponse requestId " + request.getRequestId() + " totalSize:" + response.getContentSize());

                    //响应的流
                    is = conn.getInputStream();
                    //数据流进行重构或者切换
                    buildGzipInputStream(conn, request, conn.getInputStream());
                    //数据统一处理
                    processResponseData(is, request, response);
                    //请求成功
                    doCallBack(ResponseEvent.SUCCESS);
                } else {
                    // 错误信息
                    Logger.e(TAG, "requestId " + request.getRequestId() + " event:" + ResponseEvent.ERROR + " http connection error:" + responseCode);
                    response.setError(String.valueOf(responseCode), "http connection error:" + responseCode);
                    doCallBack(ResponseEvent.ERROR);
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.setError("", "http connection error:" + e.getMessage());
                doCallBack(ResponseEvent.ERROR);
            } finally {
                BeanUtils.close(os);
                if (conn != null) {
                    conn.disconnect();
                }
            }
        } else if (actionName.contains(".do")) {
            try {
                HttpRequest request = (HttpRequest) this.request;
//                request.setUrl(Constants.JXZWFWW_URL + actionName);
                request.setEncode("UTF-8"); // 门户设置UTF-8，否则是乱码返回
                Logger.e(TAG, "请求的地址：" + request.getUrl());
                //请求开始
                doCallBack(ResponseEvent.START);

                if (HttpMethod.POST == request.getMethod()) {
                    if (RequestType.UPLOAD == request.getRequestType()) {
                        doForm(os, request);
                    } else {
                        doPost(os, request);
                    }
                } else {
                    doGet(os, request);
                }
//                doPost(os, request);
                Logger.e(TAG, "onRequest requestId " + request.getRequestId() + " " + request.getUrl());
                // 获取返回的responseCode
                int responseCode = conn.getResponseCode();
                Logger.e(TAG, "onResponse requestId " + request.getRequestId() + " code:" + responseCode);

                // 根据返回的responseCode进行处理
                if (responseCode == SUCCESS) {
                    //总的响应大小
                    response.setContentSize(conn.getContentLength());
                    //处理包头响应数据
                    processHeaderData(conn.getHeaderFields());
                    Logger.e(TAG, "onResponse requestId " + request.getRequestId() + " totalSize:" + response.getContentSize());

                    //响应的流
                    is = conn.getInputStream();
                    //数据流进行重构或者切换
                    buildGzipInputStream(conn, request, conn.getInputStream());
                    //数据统一处理
                    processResponseData(is, request, response);
                    //请求成功
                    doCallBack(ResponseEvent.SUCCESS);
                } else {
                    // 错误信息
                    Logger.e(TAG, "requestId " + request.getRequestId() + " event:" + ResponseEvent.ERROR + " http connection error:" + responseCode);
                    response.setError(String.valueOf(responseCode), "http connection error:" + responseCode);
                    doCallBack(ResponseEvent.ERROR);
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.setError("", "http connection error:" + e.getMessage());
                doCallBack(ResponseEvent.ERROR);
            } finally {
                BeanUtils.close(os);
                if (conn != null) {
                    conn.disconnect();
                }
            }
        } else {
            try {
                ApiRequest request = (ApiRequest) this.request;
                String methodName = request.getActionName();
                String soapAction = "";
                //请求开始
                doCallBack(ResponseEvent.START);

                HttpTransportSE ths = new HttpTransportSE(request.getUrl(), Constants.TIME_OUT);
                ths.debug = true;
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
                envelope.version = SoapEnvelope.VER12;
                //如果是社保接口，则传递json串
                if (Constants.SOCIAL_SECURITY_URL.equals(request.getUrl())) {
                    soapAction = Constants.SOCIAL_SECURITY_NAME_SPACE + "/" + methodName;

                    org.json.JSONObject jsonObject = new org.json.JSONObject();
                    org.json.JSONObject headJson = new org.json.JSONObject();
                    headJson.put("authcode", "87a21d54799e87d192b905b6619d907c");
                    headJson.put("invokeTime", System.currentTimeMillis());
                    headJson.put("portalVersion", "1.0");
                    headJson.put("sessionId", LoginUtils.getInstance().getUserInfo().getSessionId());
                    headJson.put("sysType", "android");
                    headJson.put("sysVersion", "19");
                    headJson.put("terminalId", "868323024154558");
                    headJson.put("terminalType", "20010");
                    headJson.put("terminalVersion", "1.1.1");
                    headJson.put("userId", "005");

                    org.json.JSONObject dataJson = new org.json.JSONObject();
                    for (int i = 0; i < request.getParamNames().size(); i++) {
                        dataJson.put(request.getParamNames().get(i), request.getParamValues().get(i));
                    }

                    jsonObject.put("head", headJson);
                    jsonObject.put("data", dataJson);
                    jsonObject.put("fnId", methodName);
                    jsonObject.put("sign", "AASDFASDF");
                    jsonObject.put("sysCode", LoginUtils.getInstance().getUserInfo().getSysCode());

                    String json = jsonObject.toString();
                    envelope.bodyOut = json;
                    envelope.setOutputSoapObject(json);
                    LogUtils.d(TAG, "sendRequest : " + json);
                    LogUtils.d(TAG, "request ip: : " + request.getUrl());
                } else {
                    soapAction = Constants.NAME_SPACE + "/" + methodName;
                    SoapObject rpc = new SoapObject(Constants.NAME_SPACE, methodName);
                    for (int i = 0; i < request.getParamNames().size(); i++) {
                        rpc.addProperty(request.getParamNames().get(i), request.getParamValues().get(i));
                    }
                    envelope.bodyOut = rpc;
                    envelope.setOutputSoapObject(rpc);
                    LogUtils.d(TAG, "sendRequest : " + rpc.toString());
                    LogUtils.d(TAG, "request ip: : " + request.getUrl());
                }

                envelope.dotNet = true;
                ths.call(soapAction, envelope);
                String responseData = envelope.bodyIn.toString();
                if (-1 == responseData.indexOf("SoapFault")) {
                    responseData = envelope.getResponse().toString();
                }

	            // 根据返回的responseCode进行处理
	            if (!StringUtils.isNullOrEmpty(responseData)) {
	                //数据统一处理 {out={"code":"0","data":{"AUTHTOKEN"
            	LogUtils.d(TAG, "responseData : " + responseData);
//	            	LogUtils.d(TAG, "body : " + ths.requestDump);
                    AppLogCc.e("responseData : " + responseData);
                    AppLogCc.e("body : " + ths.requestDump);

	            	JSONObject json = new JSONObject(responseData);
	            	ResultItem item = new ResultItem(json);
	            	item.setJsonStr(responseData);
	                response.setResultData(item);
	                //请求成功
	                doCallBack(ResponseEvent.SUCCESS);
	            } else {
	                // 错误信息
	            	LogUtils.e(TAG, "requestId " + request.getRequestId() + " event:" + ResponseEvent.ERROR + " http connection error:");
	                response.setError("", "http connection error:");
	                doCallBack(ResponseEvent.ERROR);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            response.setError("", "http connection error:" + e.getMessage());
	            doCallBack(ResponseEvent.ERROR);
	        }
		}
		
	}

    /**
     * 做post请求
     *
     * @param os
     * @param request
     */
    protected void doPost(OutputStream os, HttpRequest request) {
        try {
            // 创建http连接
            conn = getConnection(request, request.getUrl());
            // 生成请求体（补充连接的头信息）
            byte[] body = buildRequestBody(request);
            // 进行连接
            conn.connect();

            if (body != null) {
                // 获取请求输出对象,提交请求的对应的参数
                os = new BufferedOutputStream(conn.getOutputStream(), DEFAULT_BUFFER_SIZE);
                // 重新创建新的数据输出方式（压缩或者其他的）
                buidOutputStream(request, os);
                // 请求内容输入
                os.write(body);
                os.flush();
                os.close();
            }
        } catch (Exception e) {
            Logger.w(TAG, "requestId doPost error:", e);
        }
    }

    /**
     * 做get请求
     *
     * @param os
     * @param request
     */
    protected void doGet(OutputStream os, HttpRequest request) {
        try {
            // 创建http连接
            conn = getConnection(request, buildURL(request));
            // 进行连接
            conn.connect();
        } catch (Exception e) {
            Logger.w(TAG, "requestId doPost error:", e);
        }
    }

    /**
     * 做表单提交
     *
     * @param os
     * @param request
     */
    protected void doForm(OutputStream os, HttpRequest request) {
        try {
            // 创建http连接
            conn = getConnection(request, request.getUrl());
            // 进行连接
            conn.connect();
            os = new BufferedOutputStream(conn.getOutputStream(), DEFAULT_BUFFER_SIZE);
            //创建表单内容
            buildFormBody(request, os);
            //添加表单信息
            StringBuffer endMsg = new StringBuffer();
            endMsg.append("--" + boundary + "--" + "\r\n");
            endMsg.append("\r\n");
            os.write(endMsg.toString().getBytes());
            os.flush();
        } catch (Exception e) {
            Logger.w(TAG, "requestId doPost error:", e);
        }
    }

    /**
     * 获取HttpURLConnection 包括 http 和 https
     *
     * @param request
     * @return
     */
    public HttpURLConnection getConnection(HttpRequest request, String url) {
        HttpURLConnection conn = null;
        try {
            URL realUrl = new URL(url);
            if (isHttps(realUrl)) {
                conn = getHttpsConnection(realUrl);
            } else {
                conn = (HttpURLConnection) realUrl.openConnection();
            }
            // URLConnection.setInstanceFollowRedirects是成员函数，仅作用于当前函数
            conn.setInstanceFollowRedirects(true);
            // 超时时间
            conn.setReadTimeout(request.getReadtime());
            conn.setConnectTimeout(request.getTimeout());
            // Read from the connection. Default is true
            conn.setDoInput(true);// 发送输入流
            // http正文内，因此需要设为true
            if (HttpMethod.POST == request.getMethod()) {
                conn.setDoOutput(true);// 发送POST请求必须设置允许输出
            }
            // Post cannot use caches
            // Post 请求不能使用缓存
            conn.setUseCaches(false);
            // Set the post method. Default is GET
            conn.setRequestMethod(request.getMethod().name());
            conn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
            conn.setRequestProperty("Charset", request.getEncode()); //编码
            // conn.setRequestProperty("Content-Length",
            // String.valueOf(request.length));
            // conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            //请求的公用参数,请求前实时去获取最新的token,防止时间间隔造成token不是最新的
            //添加请求自定义的头域
            if (request.hasHttpHeaders()) {
                for (Entry<String, String> header : request.getHttpHeaders().entrySet()) {
                    conn.setRequestProperty(header.getKey(), header.getValue());
                }
            }
            if (request.getRequestType() == RequestType.UPLOAD) {
                conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Logger.w(TAG, "getConnection error:", e);
        }
        return conn;
    }

    /**
     * 是否是https请求
     *
     * @param url
     * @return
     */
    protected boolean isHttps(URL url) {
        return url.getProtocol().toLowerCase(Locale.US).equals("https");
    }

    /**
     * 创建输出流
     *
     * @param request
     * @param os
     */
    protected void buidOutputStream(HttpRequest request, OutputStream os) {
        if (Encrypt.GZIP == request.getRequestEncrypt()) {
            try {
                os = new GZIPOutputStream(os);
            } catch (IOException e) {
                e.printStackTrace();
                Logger.e(TAG, "buidOutputStream GZIP error:", e);
            }
        }
    }

    /**
     * 创建输入流
     *
     * @param connection
     * @param request
     */
    protected void buildGzipInputStream(HttpURLConnection connection, HttpRequest request, InputStream ins) {
        try {
            if ("gzip".equalsIgnoreCase(connection.getContentEncoding()) || Encrypt.GZIP == request.getResponseEncrypt()) {
                ins = new GZIPInputStream(ins);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Logger.e(TAG, "buildGzipInputStream GZIP error:", e);
        }
    }

    /**
     * 创建参数body
     *
     * @param request
     * @return
     */
    protected String buildURL(HttpRequest request) {
        StringBuffer data = new StringBuffer(request.getUrl());
        if (request.hasParams()) {
            try {
                int length = request.getParamNames().size();
                StringBuilder params = new StringBuilder();
                for (int i = 0; i < length; i++) {
                    Object value = request.getParamValues().get(i);
                    params.append(request.getParamNames().get(i));
                    params.append("=");
                    params.append(value == null ? "" : (URLEncoder.encode(value.toString(), request.getEncode())));
                    if (i != length - 1) {
                        params.append("&");
                    }
                }
                String appendChar = request.getUrl().indexOf("?") > 0 ? "" : "?";
                data.append(appendChar + params.toString());
                Logger.e(TAG, "doget url:" + data);
            } catch (Exception e) {
                Logger.e(TAG, "buildURL error:", e);
            }

        }
        return data.toString();
    }

    /**
     * 创建参数body
     *
     * @param request
     * @return
     */
    protected byte[] buildRequestBody(HttpRequest request) {
        byte[] data = null;
        if (request.hasParams()) {
            try {
                int length = request.getParamNames().size();
                StringBuilder params = new StringBuilder();
                for (int i = 0; i < length; i++) {
                    Object value = request.getParamValues().get(i);
                    params.append(request.getParamNames().get(i));
                    params.append("=");
//                    params.append(value == null ? "" : (URLEncoder.encode(value.toString(), request.getEncode())));
                    params.append(value == null ? "" : value.toString());
                    if (i != length - 1) {
                        params.append("&");
                    }
                }
                Logger.e(TAG, request.getRequestId() + " params " + params.toString());
                data = params.toString().getBytes(request.getEncode());
            } catch (Exception e) {
                Logger.e(TAG, "buildRequestBody error:", e);
            }

        }
        return data;
    }

    /**
     * 创建表单请求
     *
     * @param request
     * @param outStream
     */
    protected void buildFormBody(HttpRequest request, OutputStream outStream) {
        if (request.hasParams()) {
            try {
                //总数
                response.setTotalSize(request.getFileTotalSize());
                int length = request.getParamNames().size();
                for (int i = 0; i < length; i++) {
                    String name = request.getParamNames().get(i);
                    Object value = request.getParamValues().get(i);
                    if (value instanceof File) {
                        writeFileParams(name, (File) value, request.getEncode(), outStream);
                    } else {
                        writeStringParams(name, value == null ? "" : value.toString(), request.getEncode(), outStream);
                    }
                }
            } catch (Exception e) {
                Logger.e(TAG, "buildFormBody error:", e);
            }

        }
    }

    /**
     * 基础参数
     *
     * @param name
     * @param value
     * @param chartset
     * @param outStream
     * @throws Exception
     */
    protected void writeStringParams(String name, String value, String chartset, OutputStream outStream) throws Exception {
        StringBuffer buffer = new StringBuffer();
        buffer.append("--" + boundary + "\r\n");
        buffer.append("Content-Disposition: form-data; name=\"" + name + "\"\r\n");
        buffer.append("\r\n");
        buffer.append(value.toString() + "\r\n");
        outStream.write(buffer.toString().getBytes(chartset));
    }

    /**
     * 写文件
     *
     * @param name
     * @param file
     * @param chartset
     * @param outStream
     * @throws Exception
     */
    protected void writeFileParams(String name, File file, String chartset, OutputStream outStream) throws Exception {
        StringBuffer buffer = new StringBuffer();
        buffer.append("--" + boundary + "\r\n");
        buffer.append("Content-Disposition: form-data; name=\"" + name + "\"; filename=\"" + URLEncoder.encode(file.getName(), chartset) + "\"\r\n");

        // 添加Content-Type
        String contentType = FileUtils.getFileMIME(file.getName());
        if (contentType == null || contentType.length() == 0) {
            contentType = "application/octet-stream";
        }

        buffer.append("Content-Type: " + contentType + "\r\n");
        buffer.append("\r\n");
        outStream.write(buffer.toString().getBytes());
        uploadFile(file, outStream);
        outStream.write("\r\n".getBytes());
    }

    /***
     * 获取https连接
     *
     * @param url
     * @return
     */
    protected HttpsURLConnection getHttpsConnection(URL url) {
        HttpsURLConnection https = null;
        try {
            // 采用X509的证书信息机制SSLContext
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new MyTrustManager()}, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            https = (HttpsURLConnection) url.openConnection();

            // 域名主机验证
            https.setHostnameVerifier(new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Logger.e(TAG, "getHttpsConnection error:", e);
        }
        return https;
    }

    /**
     * 采用X509的证书信息机制
     *
     * @author 刘剑
     */
    private class MyTrustManager implements X509TrustManager {
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }

        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
    }

    /**
     * 解析包头的数据
     */
    protected void processHeaderData(Map<String, List<String>> headers) {
        try {
            if (headers != null) {
                ResultItem header = new ResultItem();
                for (Entry<String, List<String>> entry : headers.entrySet()) {
                    String key = entry.getKey();
                    List<String> values = entry.getValue();
                    if (!TextUtils.isEmpty(key)) {
                        header.put(entry.getKey(), (values != null && values.size() == 1) ? values.get(0) : values);
                        Logger.e(TAG, "header key:" + entry.getKey() + " value:" + header.getString(entry.getKey()));
                    }
                }
                response.setHeaderData(header);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Logger.e(TAG, "processHeaderData error:", e);
        }
    }

    /**
     * 处理服务器响应对象
     *
     * @param responseData
     * @param request
     * @param response
     */
    protected void processResponseData(Object responseData, IRequestItem request, IResponseItem response) {
        if (responseData != null) {
            InputStream stream = null;
            HttpRequest httpRequest = (HttpRequest) request;
            if ((responseData instanceof InputStream)) {
                stream = (InputStream) responseData;
            } else if ((responseData instanceof String)) {
                stream = new ByteArrayInputStream(((String) responseData).getBytes());
            }

            if (stream != null) {
                Object result = null;
                try {
                    if (httpRequest.getDataType() == ResponseDataType.JSON)
                        result = processJson(response, httpRequest, stream);
                    else if (httpRequest.getDataType() == ResponseDataType.CONTENT) {
                        result = processContent(httpRequest, stream);
                        Logger.e(TAG, "onResponse requestId " + request.getRequestId() + " result:" + result);
                    } else if (httpRequest.getDataType() == ResponseDataType.FILE) {
                        processFile(response, stream, httpRequest);
                        result = httpRequest.getSavePath();

                    } else if (httpRequest.getDataType() == ResponseDataType.XML) {
                        // result = processXml(httpRequest, stream);
                    }
                    response.setResultData(result);
                } catch (Exception e) {
                    e.printStackTrace();
                    Logger.e(TAG, "processResponseData error:", e);
                }
            }
        }
    }

    /**
     * 直接返回字符串
     *
     * @param httpRequest
     * @param dataSoure
     * @return
     */
    protected String processContent(HttpRequest httpRequest, InputStream dataSoure) {
        return FileUtils.getContent(dataSoure, httpRequest.getEncode());
    }

    /**
     * 处理文件
     *
     * @param stream
     */
    protected void processFile(IResponseItem response, InputStream stream, HttpRequest httpRequest) {
        if (ResponseDataType.FILE == httpRequest.getDataType()) {
            if (!BeanUtils.isEmpty(httpRequest.getSavePath())) {
                //总数
                response.setTotalSize(response.getContentSize());
                downloadFile(stream, httpRequest.getSavePath());
            } else {
                response.setError("1004", "not set save path");
            }
        }
    }

    /**
     * @param response
     * @param httpRequest
     * @param dataSoure
     * @return
     */
    protected ResultItem processJson(IResponseItem response, HttpRequest httpRequest, InputStream dataSoure) {
        ResultItem item = new ResultItem();
        try {
//			String jsonContent = FileUtils.getContent(dataSoure, httpRequest.getEncode());
//          String jsonContent = FileUtils.getContent(dataSoure, "GB2312");
            String charset = httpRequest.getEncode();
            ResultItem result = this.response.getHeaderData();
            if (null != result && null != result.getString("Content-Type")) {
                if (result.getString("Content-Type").contains("=")) {
                    int i = result.getString("Content-Type").indexOf("=");
                    charset = result.getString("Content-Type").substring(i + 1);
                }
            }

            String jsonContent = FileUtils.getContent(dataSoure, charset);

            Logger.e(TAG, "onResponse requestId " + httpRequest.getRequestId() + " result:" + jsonContent + "  charset:" + charset);
            JSONObject json = new JSONObject(jsonContent);
            item = new ResultItem(json);
        } catch (Exception e) {
            e.printStackTrace();
            response.setError("1023", "processJson error:" + e.getMessage());
        } finally {
            BeanUtils.close(dataSoure);
        }
        return item;
    }

    /**
     * 获取文件内容
     *
     * @param file
     * @return
     * @throws Exception
     */
    protected void uploadFile(File file, OutputStream out) throws Exception {
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            byte[] b = new byte[DEFAULT_BUFFER_SIZE / 2];
            int n;
            while ((n = in.read(b)) != -1) {
                out.write(b, 0, n);
                //上报进度
                response.setCompleteSize(response.getCompleteSize() + n);
                doCallBack(ResponseEvent.PROGRESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BeanUtils.close(in);
        }
    }

    /**
     * 保存文件
     *
     * @param inps
     * @param filePath
     */
    protected void downloadFile(InputStream inps, String filePath) {
        OutputStream out = null;
        try {
            out = new FileOutputStream(filePath);
            int len = 0;
            byte[] data = new byte[DEFAULT_BUFFER_SIZE / 2];
            while ((len = inps.read(data)) != -1) {
                out.write(data, 0, len);
                //上报进度
                response.setCompleteSize(response.getCompleteSize() + len);
                doCallBack(ResponseEvent.PROGRESS);
            }
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BeanUtils.close(inps);
            BeanUtils.close(out);
        }
    }

    @Override
    public void cancel() {
        //上报取消时间
        doCallBack(ResponseEvent.CANCEL);
        try {
            BeanUtils.close(conn.getOutputStream());
            BeanUtils.close(conn.getInputStream());
            conn.disconnect();
        } catch (Exception e) {
            //			e.printStackTrace();
        }
    }
}
