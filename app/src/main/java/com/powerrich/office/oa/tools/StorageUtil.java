package com.powerrich.office.oa.tools;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;

import java.io.File;

/**
 * Created by hasee on 2018/9/14.
 */

public class StorageUtil {

    /**
     * 检查SD卡是否有足够的空间
     * @return
     */
    public static boolean checkFreeSpace() {
        int minimum = 10;
        //要求sd卡最少可用空间为10M
        long size = minimum * 1024 * 1024;
        if (getSDFreeSpace() > size) {
            return true;
        } else {
            return false;
        }
    }

    public static long getSDFreeSpace() {
        String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        StatFs stat = new StatFs(absolutePath);
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize;
    }

}
