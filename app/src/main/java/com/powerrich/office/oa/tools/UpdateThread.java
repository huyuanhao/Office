package com.powerrich.office.oa.tools;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;


import com.powerrich.office.oa.activity.MainActivity;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.bean.VersionInfoBean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class UpdateThread extends Thread {

	private static final String TAG = "UpdateThread";

	private volatile boolean stopRequested;
	private Thread runThread;
	private VersionInfoBean versionInfo;
	private String path;
	private String apkName;

	private ProgressDialog progressBar;
	private NotificationUtil notification;
	private Context context;

	public UpdateThread(Context context, VersionInfoBean versionInfo, String path, String apkName, ProgressDialog bar) {
		super();
		this.context = context;
		this.versionInfo = versionInfo;
		this.path = path;
		this.apkName = apkName;
		this.progressBar = bar;
	}
	public UpdateThread(Context context, VersionInfoBean versionInfo, String path, String apkName, NotificationUtil notification) {
		super();
		this.context = context;
		this.versionInfo = versionInfo;
		this.path = path;
		this.apkName = apkName;
		this.notification = notification;
	}

	@Override
	public void run() {
		stopRequested = false;
		runThread = Thread.currentThread();

		URL downloadUrl;
		InputStream inputStream = null;
		FileOutputStream outputStream = null;
		float fileSize;
		try {
			//拼接下载地址
			Uri.Builder builder = Uri.parse(Constants.DOWNLOAD_URL).buildUpon();
			builder.appendQueryParameter("type", "1");
			builder.appendQueryParameter("DOWNPATH", versionInfo.getDOWNPATH());
			builder.appendQueryParameter("HDFSFILENAME", versionInfo.getHDFSFILENAME());
			builder.appendQueryParameter("FILENAME", versionInfo.getFILENAME());
			downloadUrl = new URL(builder.build().toString());
			Logger.e(TAG, "---------- downloadUrl：" + downloadUrl);

			HttpURLConnection connection = (HttpURLConnection) downloadUrl.openConnection();
			connection.setConnectTimeout(28000);
			connection.setRequestMethod("GET");
			if (connection.getResponseCode() == 200) {
				inputStream = connection.getInputStream();
				File file = new File(path, apkName);
				outputStream = new FileOutputStream(file);
				fileSize = versionInfo.getApkByteSize();
				int length;
				byte[] buffer = new byte[1024];
				int downloadSize = 0;
//				while (((ch = is.read(buf)) != -1) && !stopRequested)
				while ((length = inputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, length);
					downloadSize += length;
					int progress = ((int) ((float) downloadSize / fileSize * 100f));
					if (progressBar != null) {
                        progressBar.setProgress(progress);
                    } else if (notification != null) {
					    notification.updateProgress(progress);
                    }
				}
				outputStream.flush();
				outputStream.close();
			}
			String filePath = path + "/" + apkName;
			if (progressBar != null) {//强制更新
				//执行升级安装，兼容android7.0
				if (CheckVersionTools.fileIsExists(filePath)) {
					AndroidFileUtil.openFile(context,new File(filePath));
				}
				progressBar.dismiss();
				if (context instanceof BaseActivity) {
					((BaseActivity) context).finish();
				}
			} else if (notification != null) {//非强制更新
				notification.finish(filePath);
			}
//			CheckVersionTools.openApkFile(context, filePath);
		} catch (MalformedURLException e) {
            if (progressBar != null) {
                progressBar.dismiss();
				if (context instanceof MainActivity) {
					((MainActivity) context).finish();
				}
            } else if (notification != null) {
                notification.cancel();
            }
			e.printStackTrace();
			e.getMessage();
			try {
				if (inputStream != null) {
					inputStream.close();
				}
				if (outputStream != null) {
					outputStream.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (IOException e) {
            if (progressBar != null) {
                progressBar.dismiss();
				if (context instanceof MainActivity) {
					((MainActivity) context).finish();
				}
            } else if (notification != null) {
                notification.cancel();
            }
			e.printStackTrace();
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}

	}

	public void stopRequest() {
		stopRequested = true;
		if (runThread != null) {
			runThread.interrupt();
		}
	}



}
