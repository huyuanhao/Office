package com.powerrich.office.oa.tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 文 件 名：NetWorkUtil
 * 描   述：网络检查
 * 作   者：Wangzheng
 * 时   间：2018-8-6 16:14:58
 * 版   权：v1.0
 */
public class NetWorkUtil {
    /**
     * 没有网络
     */
    public static final int NO_NETWORK = 0;
    /**
     * 当前是wifi连接
     */
    public static final int WIFI = 1;
    /**
     * 不是wifi连接
     */
    public static final int NO_WIFI = 2;


    /**
     * 检测当前网络的类型 是否是wifi
     * @param context
     * @return
     */
    public static int checkedNetWorkType(Context context){
        if(!checkedNetWork(context)){
            return NO_NETWORK;
        }
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting() ){
            return WIFI;
        }else{
            return NO_WIFI;
        }
    }


    /**
     * 检查是否连接网络
     * @param context
     * @return
     */
    public static boolean checkedNetWork(Context context){
        // 1.获得连接设备管理器
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(cm == null) return false;
        /**
         * 获取网络连接对象
         */
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if(networkInfo == null || !networkInfo.isAvailable()){
            return false;
        }
        return true;
    }
}
