package com.powerrich.office.oa.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.powerrich.office.oa.tools.Logger;

/**
 * 文 件 名：IntentReceiver
 * 描   述：监听网络广播
 * 作   者：Wangzheng
 * 时   间：2018-8-7 16:57:35
 * 版   权：v1.0
 */
public class IntentReceiver extends BroadcastReceiver {
    private NetEvent listener;

    public IntentReceiver(NetEvent listener) {
        this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable()) {
            Logger.e("IntentReceiver", "网络连接成功");
        } else {
            listener.onNetChange();
        }
    }

    // 自定义接口
    public interface NetEvent {
        void onNetChange();
    }
}
