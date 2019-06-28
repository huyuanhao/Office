package com.powerrich.office.oa.tools;


import android.util.Log;

public class AppLogCc {
    public static final String DEFAULT_LOG_TAG = "HttpRequestTask";
    private  static boolean isDebug = true;
    // =================== DEBUG =================== //
    public static void d(String msg) {
        if (isDebug) {
            Log.d(DEFAULT_LOG_TAG, msg);
        }
    }

    public static void d(String msg, Throwable t) {
        if (isDebug) {
            Log.d(DEFAULT_LOG_TAG, msg, t);
        }
    }

    public static void d(String format, Object... args) {
        d(String.format(format, args));
    }

    public static void d(String format, Throwable t, Object... args) {
        d(String.format(format, args), t);
    }

    // =================== INFO =================== //
    public static void i(String msg) {
        Log.i(DEFAULT_LOG_TAG, msg);
    }

    public static void i(String msg, Throwable t) {
        Log.i(DEFAULT_LOG_TAG, msg, t);
    }

    public static void i(String format, Object... args) {
        i(String.format(format, args));
    }

    public static void i(String format, Throwable t, Object... args) {
        i(String.format(format, args), t);
    }

    // =================== WARN =================== //
    public static void w(String msg) {
        Log.w(DEFAULT_LOG_TAG, msg);
    }

    public static void w(String msg, Throwable t) {
        Log.w(DEFAULT_LOG_TAG, msg, t);
    }

    public static void w(String format, Object... args) {
        w(String.format(format, args));
    }

    public static void w(String format, Throwable t, Object... args) {
        w(String.format(format, args), t);
    }

    // =================== ERROR =================== //
//    public static void e(String msg) {
//        Log.e(DEFAULT_LOG_TAG, msg);
//    }

    public static void e(String msg, Throwable t) {
        Log.e(DEFAULT_LOG_TAG, msg, t);
    }

    public static void e(String format, Object... args) {
        e(String.format(format, args));
    }

    public static void e(String format, Throwable t, Object... args) {
        e(String.format(format, args), t);
    }

    //规定每段显示的长度
    private static int LOG_MAXLENGTH = 3000;

    public static void e( String msg) {
        int strLength = msg.length();
        int start = 0;
        int end = LOG_MAXLENGTH;
        for (int i = 0; i < 100; i++) {
            //剩下的文本还是大于规定长度则继续重复截取并输出
            if (strLength > end) {
                Log.e(DEFAULT_LOG_TAG + i, msg.substring(start, end));
                start = end;
                end = end + LOG_MAXLENGTH;
            } else {
                Log.e(DEFAULT_LOG_TAG, msg.substring(start, strLength));
                break;
            }
        }
    }
}
