package com.powerrich.office.oa.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.powerrich.office.oa.activity.mine.MessageDetailActivity;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.notify.NotifyKey;
import com.powerrich.office.oa.tools.Logger;
import com.yt.simpleframe.notification.NotificationCenter;

import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * 
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class JPushReceiver extends BroadcastReceiver {
	private static final String TAG = "JIGUANG-Example";

	@Override
	public void onReceive(Context context, Intent intent) {
		try {
			Bundle bundle = intent.getExtras();
//			Logger.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

			if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
//				String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
//				Logger.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
				//send the Registration Id to your server...

			} else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
//				Logger.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
//				processCustomMessage(context, bundle);
				//自定义的消息内容
//				String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
//
//				//自定义通知Notification:弹出通知,当用户点击通知的时候跳转到浏览器打开页面
//				NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
//				builder.setSmallIcon(R.drawable.ic_launcher);
//				builder.setContentTitle("消息");
//				builder.setContentText(message);
//				//通知对象
//				Notification notification = builder.build();
//				NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//				//发起通知
//				mNotificationManager.notify(0, notification);

			} else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
//				Logger.d(TAG, "[MyReceiver] 接收到推送下来的通知");
//				int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
				String alert = bundle.getString(JPushInterface.EXTRA_ALERT);
				String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);

				Logger.d(TAG, "[MyReceiver] 接收到推送下来的通知alert: " + alert);
				Logger.d(TAG, "[MyReceiver] 接收到推送下来的通知extra: " + extra);

				//接收到通知到后 向YtMineFragment发送消息，提示更新
				NotificationCenter.defaultCenter.postNotification(NotifyKey.UPDATE_MESSAGE_KEY);

			} else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
//				Logger.d(TAG, "[MyReceiver] 用户点击打开了通知");
				String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
				ResultItem resultItem = new ResultItem(new JSONObject(extra));
				String param = resultItem.getString("param");
				ResultItem paramItem = new ResultItem(new JSONObject(param));
				String prokeyId = paramItem.getString("RECORDID");
				Intent msgIntent = new Intent(context, MessageDetailActivity.class);
				msgIntent.putExtra("id", prokeyId);
				msgIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(msgIntent);
			} else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
				//在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

			} else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
//				boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
			} else {
			}
		} catch (Exception e){

		}

	}
}
