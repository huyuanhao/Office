package com.yt.simpleframe.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 用户账户信息保存工具类
 * 1.不要存放大的key和value！我就不重复三遍了，会引起界面卡、频繁GC、占用内存等等，好自为之！
 * 2.毫不相关的配置项就不要丢在一起了！文件越大读取越慢.
 * 3.读取频繁的key和不易变动的key尽量不要放在一起，影响速度。（如果整个文件很小，那么忽略吧，为了这点性能添加维护成本得不偿失）
 * 4.不要乱edit和apply，尽量批量修改一次提交！
 * 5.尽量不要存放JSON和HTML，这种场景请直接使用json！
 * 6.不要指望用这货进行跨进程通信！！！
 */
public class SharedPreferencesUtils {

    /**
     * 保存在手机里面的文件名
     */
    private static final String FILE_NAME = "share_date";


    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     * @param context
     * @param key
     * @param object
     */
    public static void setParam(Context context , String key, Object object){

        String type = object.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if("String".equals(type)){
            editor.putString(key, (String)object);
        }
        else if("Integer".equals(type)){
            editor.putInt(key, (Integer)object);
        }
        else if("Boolean".equals(type)){
            editor.putBoolean(key, (Boolean)object);
        }
        else if("Float".equals(type)){
            editor.putFloat(key, (Float)object);
        }
        else if("Long".equals(type)){
            editor.putLong(key, (Long)object);
        }

        editor.commit();
    }


    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object getParam(Context context , String key, Object defaultObject){
        String type = defaultObject.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);

        if("String".equals(type)){
            return sp.getString(key, (String)defaultObject);
        }

        else if("Integer".equals(type)){
            return sp.getInt(key, (Integer)defaultObject);
        }

        else if("Boolean".equals(type)){
            return sp.getBoolean(key, (Boolean)defaultObject);
        }

        else if("Float".equals(type)){
            return sp.getFloat(key, (Float)defaultObject);
        }

        else if("Long".equals(type)){
            return sp.getLong(key, (Long)defaultObject);
        }

        return null;
    }

    /**
     * 清除所有数据
     * @param context
     */
    public static void clear(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear().commit();
    }

    /**
     * 清除指定数据
     * @param context
     */
    public static void clearAll(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove("定义的键名");
        editor.commit();
    }


    /**
     * 供水查询历史记录专用
     */
    private static String WaterHistory = "waterhis";
    // 从SharedPreferences中获取历史记录数据
    public static String getHistoryFromSharedPreferences(Context context, String key, String defaultObject) {
        SharedPreferences sp = context.getSharedPreferences(WaterHistory, Context.MODE_PRIVATE);
        return sp.getString(key, defaultObject);
    }

    // 将历史记录数据保存到SharedPreferences中
    public static void saveHistoryToSharedPreferences(Context context,String key, String history) {
        SharedPreferences sp = context.getSharedPreferences(WaterHistory, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, history);
        editor.apply();
    }

    // 清除保存在SharedPreferences中的历史记录数据
    public static void clearHistoryInSharedPreferences(Context context) {
        SharedPreferences sp = context.getSharedPreferences(WaterHistory, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
    }
}
