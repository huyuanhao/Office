package com.powerrich.office.oa.common;

import java.util.ArrayList;
import java.util.List;

/**
 * 该类用于临时保存共享数据集合
 * Created by root on 16-5-12.
 */
public class TemperCache {

    private final String TAG = "TemperCache";

    /**
     * 缓存个人任务信息列表
     * */
    private ArrayList<ResultItem> taskList;

    private static class InnerCreator {

        public static TemperCache instance = new TemperCache();
    }

    private TemperCache() {

    }

    public synchronized static TemperCache getInstance(){
        return InnerCreator.instance;
    }

    /**
     * 获取缓存的个人任务列表
     * */
    public ArrayList<ResultItem> getTaskList() {
        return taskList;
    }

    /**
     * 保存个人任务信息列表
     * */
    public void saveTaskList(List<ResultItem> tasks) {
        if (null == tasks || tasks.size() <= 0) {
            return;
        }
        if (null == taskList) {
            taskList = new ArrayList<ResultItem>();
            taskList.addAll(tasks);
        } else {
            String id = null;
            String key = "TASK_ID";
            boolean isContain = false;
            for (ResultItem temp : tasks) {
                isContain = false;
                if (null == temp.get(key)) {
                    continue;
                }
                id = String.valueOf(temp.get(key));
                for (ResultItem item : taskList) {
                    if (id.equals(item.get(key))) {
                        isContain = true;
                        break;
                    }
                }
                if (!isContain) {
                    taskList.add(temp);
                }
            }
        }
    }

    /**
     * 清除个人列表
     * */
    public void clearTaskList() {
        if (null != taskList) {
            taskList.clear();
            taskList = null;
        }
    }
}
