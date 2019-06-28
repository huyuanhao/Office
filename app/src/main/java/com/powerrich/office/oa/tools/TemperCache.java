package com.powerrich.office.oa.tools;

/**
 * 该类用于临时保存共享数据集合
 * Created by root on 16-5-12.
 */
public class TemperCache {

    private final String TAG = "TemperCache";

    private static class InnerCreator {

        public static TemperCache instance = new TemperCache();
    }

    private TemperCache() {

    }

    public synchronized static TemperCache getInstance(){
        return InnerCreator.instance;
    }

}
