package com.yt.simpleframe.utils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/6/12
 * 版权：
 */

public class TimerUtils {

    public interface TimeCallBack {
        void timeOver();
    }

    private static Timer mTimer = new Timer();

    static class MyTimerTask extends TimerTask {
        TimeCallBack mBack;

        public MyTimerTask(TimeCallBack callBack) {
            this.mBack = callBack;
        }

        @Override
        public void run() {
            mBack.timeOver();
        }
    }

    public static void startTimeCount(int time, TimeCallBack callBack) {
        MyTimerTask task = new MyTimerTask(callBack);
        mTimer.schedule(task, time * 1000);
    }

    public static void startTimeCount(TimeCallBack callBack) {
        startTimeCount(3, callBack);
    }

    public static void main(String[] args) {
        startTimeCount(new TimeCallBack() {
            @Override
            public void timeOver() {
                System.out.println("wahaha");
            }
        });
    }
}
