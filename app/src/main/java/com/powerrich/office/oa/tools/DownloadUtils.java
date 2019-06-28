package com.powerrich.office.oa.tools;

import android.content.Context;
import android.os.Environment;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.powerrich.office.oa.tools.progressutils.helper.ProgressHelper;
import com.powerrich.office.oa.tools.progressutils.listener.impl.UIProgressListener;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 上传、下载工具类
 * @author Administrator
 *
 */
public class DownloadUtils {
	
	protected static final String TAG = "test";
	/**
     * 文件上传
     * @param context
     * @param url
     * @param filePath
     * @param fileName
     */
    public static void uploadMultiFile(UIProgressListener uiProgressRequestListener,final Context context,String url,String filePath,String fileName, String itemId, String prokeyId, String materialId, String materialName) {
        Log.e(TAG, url);
        File file = new File(filePath);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("TOKEN", LoginUtils.getInstance().getUserInfo().getAuthtoken())
                .addFormDataPart("ITEMID", itemId)
                .addFormDataPart("PROKEYID", prokeyId)
                .addFormDataPart("FILEID", materialId)
                .addFormDataPart("ITEMFILENAME", materialName)
                .addFormDataPart(fileName, file.getName(), RequestBody.create(null, file))
                // 要去掉addPart这部分避免重复创建文件对象
//                .addPart(Headers.of("Content-Disposition", "form-data; name=\"another\";filename=\"another.dex\""), RequestBody.create(MediaType.parse("application/octet-stream"), file))
//                .addPart(Headers.of("Content-Disposition", "form-data; name=\""+ fileName+"\";filename=\""+ file.getName()), RequestBody.create(MediaType.parse("application/octet-stream"), file))
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(ProgressHelper.addProgressRequestListener(requestBody, uiProgressRequestListener))
                .build();


        okhttp3.OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        OkHttpClient okHttpClient  = httpBuilder
                //设置超时
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, "上传失败");
                Looper.prepare();
                if (null !=  context && context instanceof InvokeData) {
                    ((InvokeData) context).failed();
                }
                Looper.loop();
            }


            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.i(TAG, "上传成功");
                Log.i(TAG, "result=" + result);
                Looper.prepare();
                if (null !=  context && context instanceof InvokeData) {
                    ((InvokeData) context).success(result);
                }
                Looper.loop();
            }

        });
    }

    /**
     * 身份证上传
     *
     * 头像上传公用此接口  idcardFlag 传空
     * @param context
     * @param url
     * @param filePath
     * @param fileName
     */
    public static void uploadIdCard(UIProgressListener uiProgressRequestListener,final Object context,String url,String filePath,String fileName, String userName, String idCardFlag) {
        Log.e(TAG, url);
        File file = new File(filePath);

        MultipartBody.Builder b = new MultipartBody.Builder();
        b.setType(MultipartBody.FORM)
                .addFormDataPart("USERNAME", userName);
        if(!TextUtils.isEmpty(idCardFlag)){
            b.addFormDataPart("IDCARDFLAG", idCardFlag);
        }
        b.addFormDataPart(fileName, file.getName(), RequestBody.create(null, file));
        RequestBody requestBody  = b.build();

//        RequestBody requestBody = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart("USERNAME", userName)
//                .addFormDataPart("IDCARDFLAG", idCardFlag)
//                .addFormDataPart(fileName, file.getName(), RequestBody.create(null, file))
//                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(ProgressHelper.addProgressRequestListener(requestBody, uiProgressRequestListener))
                .build();


        okhttp3.OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        OkHttpClient okHttpClient  = httpBuilder
                //设置超时
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, "上传失败");
                Looper.prepare();
                if (null !=  context && context instanceof InvokeData) {
                    ((InvokeData) context).failed();
                }
                Looper.loop();
            }


            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.i(TAG, "上传成功");
                Log.i(TAG, "result=" + result);
                Looper.prepare();
                if (null !=  context && context instanceof InvokeData) {
                    ((InvokeData) context).success(result);
                }
                Looper.loop();
            }
        });
    }

    /**
     * 文件上传返回hdfs
     * @param url
     * @param uiProgressRequestListener
     */
    public static void upload5(UIProgressListener uiProgressRequestListener,String url, String filePath, final InvokeData invokeData) {
        Log.e(TAG, url);
        File file = new File(filePath);
        upload5(uiProgressRequestListener,url,file,invokeData);
    }

    public static void upload5(UIProgressListener uiProgressRequestListener,String url, File file, final InvokeData invokeData) {
        MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder();
        multipartBodyBuilder.setType(MultipartBody.FORM);
        multipartBodyBuilder.addFormDataPart("FILE", file.getName(), RequestBody.create(MediaType.parse("image/jpg"), file));
        //构建请求体
        RequestBody requestBody = multipartBodyBuilder.build();
//        RequestBody requestBody = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
////                .addFormDataPart("USERNAME", userName)
//                .addFormDataPart("FILE", file.getName(), RequestBody.create(MediaType.parse("image/jpg"), file))
//                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(ProgressHelper.addProgressRequestListener(requestBody, uiProgressRequestListener))
                .build();


        okhttp3.OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        OkHttpClient okHttpClient  = httpBuilder
                //设置超时
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, "上传失败");
                Looper.prepare();
                invokeData.failed();
                Looper.loop();
            }


            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.i(TAG, "上传成功");
                Log.i(TAG, "result=" + result);
                Looper.prepare();
                invokeData.success(result);
                Looper.loop();
            }

        });
    }

	/**
     * 下载文件
     */
//	public static String downloadUrl = "http://d.hiphotos.baidu.com/image/pic/item/5882b2b7d0a20cf482c772bf73094b36acaf997f.jpg";
    public static void downloadFile(UIProgressListener uiProgressRequestListener,final Context context,String url,final String fileName) {
    	Log.e(TAG, url);
        Request request = new Request.Builder().url(url).build();
        okhttp3.OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        OkHttpClient okHttpClient  = httpBuilder
                //设置超时
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();
        ProgressHelper.addProgressResponseListener(okHttpClient, uiProgressRequestListener).newCall(request).enqueue(new Callback() {

			@Override
			public void onFailure(Call arg0, IOException arg1) {
				Log.i(TAG, "下载失败");
				Looper.prepare();
            	if (null !=  context && context instanceof InvokeData) {
	        		((InvokeData) context).failed();
	        	}
            	Looper.loop();
			}

			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {
                String result = arg1.body().toString();
				try {
                    saveFile(arg1,fileName);
                    Looper.prepare();
                	if (null !=  context && context instanceof InvokeData) {
    	        		((InvokeData) context).success(result);
    	        	}
                	Looper.loop();
                	Log.i(TAG, "下载成功");
                } catch (IOException e) {
                    Log.i(TAG, "下载错误");
                    e.printStackTrace();
                }
			}
 
        });
    }

    /**
     * 下载文件
     */
//	public static String downloadUrl = "http://d.hiphotos.baidu.com/image/pic/item/5882b2b7d0a20cf482c772bf73094b36acaf997f.jpg";
    public static void downloadFileImage(UIProgressListener uiProgressRequestListener,final Object context,String url,final String fileName) {
        Log.e(TAG, url);
        Request request = new Request.Builder().url(url).build();
        okhttp3.OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        OkHttpClient okHttpClient  = httpBuilder
                //设置超时
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();
        ProgressHelper.addProgressResponseListener(okHttpClient, uiProgressRequestListener).newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call arg0, IOException arg1) {
                Looper.prepare();
                if (null !=  context && context instanceof InvokeData) {
                    ((InvokeData) context).failed();
                }
                Looper.loop();
            }

            @Override
            public void onResponse(Call arg0, Response arg1) throws IOException {
                String result = arg1.body().toString();
                try {
                    saveFile(arg1,fileName);
                    Looper.prepare();
                    if (null !=  context && context instanceof InvokeData) {
                        ((InvokeData) context).success(result);
                    }
                    Looper.loop();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });
    }
    
    /**
     * 保存文件
     * @param response
     * @param fileName
     * @return
     * @throws IOException
     */
    public static File saveFile(Response response,String fileName) throws IOException {
        InputStream is = null;
        byte[] buf = new byte[2048];
        int len = 0;
        FileOutputStream fos = null;
        try {
            is = response.body().byteStream();
            final long total = response.body().contentLength();
            Log.i("TAG", "total--" + total);
            long sum = 0;
            File dir = new File(Environment.getExternalStorageDirectory() + "/download");
            if (!dir.exists()) dir.mkdirs();
            File file = new File(dir, fileName);
            fos = new FileOutputStream(file);
            while ((len = is.read(buf)) != -1) {
                sum += len;
                fos.write(buf, 0, len);
                final long finalSum = sum;
            }
            fos.flush();
            return file;
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException e) {
            }
            try {
                if (fos != null) fos.close();
            } catch (IOException e) {
            }
        }
    }
    
    /** 
     * 获得指定文件的byte数组 
     */  
    public static byte[] getBytes(String filePath){  
        byte[] buffer = null;  
        try {  
            File file = new File(filePath);  
            FileInputStream fis = new FileInputStream(file);  
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);  
            byte[] b = new byte[1024];  
            int n;  
            while ((n = fis.read(b)) != -1) {  
                bos.write(b, 0, n);  
            }  
            fis.close();  
            bos.close();  
            buffer = bos.toByteArray();  
        } catch (FileNotFoundException e) { 
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return buffer;  
    }
    
    /**
     * 根据byte数组生成文件
     * 
     * @param bytes
     *            生成文件用到的byte数组
     */
    public static void createFileWithByte(String fileName, byte[] bytes) {
        // TODO Auto-generated method stub
        /**
         * 创建File对象，其中包含文件所在的目录以及文件的命名
         */
        File file = new File(Environment.getExternalStorageDirectory()+ "/download",fileName);
        // 创建FileOutputStream对象
        FileOutputStream outputStream = null;
        // 创建BufferedOutputStream对象
        BufferedOutputStream bufferedOutputStream = null;
        try {
            // 如果文件存在则删除
            if (file.exists()) {
                file.delete();
            }
            // 在文件系统中根据路径创建一个新的空文件
            file.createNewFile();
            // 获取FileOutputStream对象
            outputStream = new FileOutputStream(file);
            // 获取BufferedOutputStream对象
            bufferedOutputStream = new BufferedOutputStream(outputStream);
            // 往文件所在的缓冲输出流中写byte数据
            bufferedOutputStream.write(bytes);
            // 刷出缓冲输出流，该步很关键，要是不执行flush()方法，那么文件的内容是空的。
            bufferedOutputStream.flush();
        } catch (Exception e) {
            // 打印异常信息
            e.printStackTrace();
        } finally {
            // 关闭创建的流对象
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bufferedOutputStream != null) {
                try {
                    bufferedOutputStream.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }
    
    
    public interface InvokeData {
		void success(String result);
		void failed();
	}
}
