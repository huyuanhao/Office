package com.powerrich.office.oa.tools;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;  
   
/** 
 * Toast工具类 
 */ 
public class ToastUtils {  
    private static Handler handler = new Handler(Looper.getMainLooper());  
    private static Toast toast = null;  
    private static Object synObj = new Object();  
   
    /** 
     * Toast发送消息，默认Toast.LENGTH_SHORT 
     * @param act 
     * @param msg 
     */ 
    public static void showMessage(final Context act, final String msg) {  
        showMessage(act, msg, Toast.LENGTH_SHORT);  
    }

    public static void showMessage(Activity a,String message){
        Toast.makeText(a,message,Toast.LENGTH_SHORT).show();
    }
    public static void showMessages(Context c,String message){
        Toast.makeText(c,message,Toast.LENGTH_SHORT).show();
    }
    /** 
     * Toast发送消息，默认Toast.LENGTH_LONG 
     * @param act 
     * @param msg 
     */ 
    public static void showMessageLong(final Context act, final String msg) {  
        showMessage(act, msg, Toast.LENGTH_LONG);  
    }  
   
    /** 
     * Toast发送消息，默认Toast.LENGTH_SHORT 
     * @param act 
     * @param msg 
     */ 
    public static void showMessage(final Context act, final int msg) {  
        showMessage(act, msg, Toast.LENGTH_SHORT);  
    }  
       
    /** 
     * Toast发送消息，默认Toast.LENGTH_LONG 
     * @param act 
     * @param msg 
     */ 
    public static void showMessageLong(final Context act, final int msg) {  
        showMessage(act, msg, Toast.LENGTH_LONG);  
    }  
   
    /** 
     * Toast发送消息 
     * @param act 
     * @param msg 
     * @param len 
     */ 
    public static void showMessage(final Context act, final int msg,  
            final int len) {  
        new Thread(new Runnable() {  
            public void run() {  
                handler.post(new Runnable() {  
   
                    @Override 
                    public void run() {  
                        synchronized (synObj) {  
                            if (toast != null) {  
                                toast.cancel();  
                                toast.setText(msg);  
                                toast.setDuration(len);  
                            } else {  
                                toast = Toast.makeText(act, msg, len);  
                            }  
                            toast.show();  
                        }  
                    }  
                });  
            }  
        }).start();  
    }  
       
    /** 
     * Toast发送消息 
     * @param act 
     * @param msg 
     * @param len 
     */ 
    public static void showMessage(final Context act, final String msg,  
            final int len) {  
        new Thread(new Runnable() {  
            public void run() {  
                handler.post(new Runnable() {  
   
                    @Override 
                    public void run() {  
                        synchronized (synObj) {  
                            if (toast != null) {  
                                toast.cancel();  
                                toast.setText(msg);  
                                toast.setDuration(len);  
                            } else {  
                                toast = Toast.makeText(act, msg, len);  
                            }  
                            toast.show();  
                        }  
                    }  
                });  
            }  
        }).start();  
    }  
   
    /** 
     * 关闭当前Toast 
     */ 
    public static void cancelCurrentToast() {  
        if (toast != null) {  
            toast.cancel();  
        }  
    }  
}  
