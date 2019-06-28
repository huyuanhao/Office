package com.powerrich.office.oa.tools;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.powerrich.office.oa.view.LoadingDialog;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/7/19
 * 版权：
 */

public class FileDownLoadUtils {

    public static final String TAG = "FileDownLoadUtils";
    public static LoadingDialog mDialog;
    public static final int OK = 1;
    public static final int FAIL = 2;

    public static OkHttpClient INSTANCE ;
    public static Object obj = new Object();

    public interface DownLoadCallBack{
        void success(String path);
        void fail();
    }

    public static OkHttpClient getInstance(){
        if(INSTANCE == null){
            synchronized (obj){
                if(INSTANCE == null){
                    okhttp3.OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
                    INSTANCE  = httpBuilder
                            //设置超时
                            .connectTimeout(10, TimeUnit.SECONDS)
                            .writeTimeout(15, TimeUnit.SECONDS)
                            .build();
                }
            }
        }
//        if(mDialog == null){
//            mDialog = new LoadingDialog(SIMeIDApplication.mContex);
//        }
        return INSTANCE;
    }


    /**
     * 判断这个download底下有没有这个文件
     * @return
     */

    public static boolean fileIsExists(String strFile) {
        strFile = Environment.getExternalStorageDirectory() + "/download/"+strFile;
        try {
            File f = new File(strFile);
            if (!f.exists()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 下载文件
     * @param fileName 文件名称
     * @param url 下载地址链接
     * @param callBack 回调
     */
    public static void downloadFile(final String fileName,String url,final DownLoadCallBack callBack){
        if(fileIsExists(fileName)){
            callBack.success(Environment.getExternalStorageDirectory() + "/download/"+fileName);
            return;
        }
        Request request = new Request.Builder().url(url).build();
        final Handler h = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
//                mDialog.dismiss();
                if(msg.what == 1){
                    callBack.success(Environment.getExternalStorageDirectory() + "/download/"+fileName);
                }else if(msg.what == 2){
                    callBack.fail();
                }
            }
        };

        getInstance().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call arg0, IOException arg1) {
                h.sendEmptyMessage(2);
            }
            @Override
            public void onResponse(Call arg0, Response arg1) throws IOException {
                String result = arg1.body().toString();
                try {
                    DownloadUtils.saveFile(arg1,fileName);
                    h.sendEmptyMessage(1);
                    Log.i(TAG, "下载成功");
                } catch (Exception e) {
                    Log.i(TAG, "下载错误");
                    e.printStackTrace();
                    h.sendEmptyMessage(2);
                }
            }
        });
//        mDialog.show();
    }
}
