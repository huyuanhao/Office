package com.powerrich.office.oa;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;

import java.util.HashMap;
import java.util.Stack;

/**
 * 应用程序Activity管理类：用于Activity管理和应用程序退出
 */
public class AppManager {

    public static Stack<Activity> activityStack;
    private static AppManager instance;
	private static HashMap<String, Integer> standMemory = new HashMap<String, Integer>();

	public void addMemory(Object o) {
		standMemory.put(o.getClass().getName() + o.hashCode(), 1);
	}

	public void finalize(Object o) {
		standMemory.remove(o.getClass().getName() + o.hashCode());
	}

	/**
	 * 单一实例
	 */
	public static AppManager getAppManager() {
		if (instance == null) {
			instance = new AppManager();
		}
		return instance;
	}

	/**
	 * 添加Activity到堆栈
	 */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
		activityStack.add(activity);
	}

    public void pushActivity(FragmentActivity activity) {
        if(!activityStack.contains(activity)){
			activityStack.add(0,activity);
		}
	}

    public void popActivity(Activity activity) {
        activityStack.remove(activity);
	}
	/**
	 * 获取当前Activity（堆栈中最后一个压入的）
	 */
    public Activity currentActivity() {
        if (activityStack == null) {
			return null;
		}
		Activity activity = activityStack.lastElement();
        return activity;
	}

	@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
	public  <T extends Activity> boolean isActivityExist(Class<T> clz) {
		boolean res;
		Activity activity = currentActivity();
		if (activity == null) {
			res = false;
		} else {
            res = !(activity.isFinishing() || activity.isDestroyed());
		}

		return res;
	}
	@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
	public  static   boolean isActivityExist(Activity activity) {
		boolean res;
		if (activity == null) {
			res = false;
		} else {
            res = !(activity.isFinishing() || activity.isDestroyed());
		}

		return res;
	}


	public String currentActivityName() {
		if (activityStack == null) {
			return null;
		}
		Activity activity = activityStack.lastElement();
		return activity.getClass().getSimpleName();
	}

	public boolean equalsActivity(String name) {
		return currentActivityName().equals(name);

	}

	/**
	 * 结束当前Activity（堆栈中最后一个压入的）
	 */
	public void finishActivity() {
		Activity activity = activityStack.lastElement();
        finishActivity(activity);
	}

	/**
	 * 结束指定的Activity
	 */
    public void finishActivity(Activity activity) {
        if (activity != null) {
			activityStack.remove(activity);
			activity.finish();
			activity = null;
		}
	}

	/**
	 * 结束指定类名的Activity
	 */
	public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
				finishActivity(activity);
				break;
			}
		}
	}

	/**
	 * 结束所有Activity
	 */
	public static void finishAllActivity() {
		if (activityStack == null) {
			return;
		}
		for (int i = 0, size = activityStack.size(); i < size; i++) {
			if (null != activityStack.get(i)) {
				activityStack.get(i).finish();
			}
		}
		activityStack.clear();
	}

	/**
	 * 退出应用程序
	 */
	public void AppExit(Context context) {
		try {
			finishAllActivity();
			/*
			 * Intent intent = new Intent(context, MainActivity.class);
			 * PendingIntent restartIntent = PendingIntent.getActivity( context,
			 * 0, intent, Intent.FLAG_ACTIVITY_NEW_TASK); //退出程序 AlarmManager
			 * mgr =
			 * (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
			 * mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000,
			 * restartIntent); // 1秒钟后重启应用
			 */
			// 杀死该应用进程
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(0);

		} catch (Exception e) {

		}
	}
}