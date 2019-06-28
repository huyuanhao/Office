package com.powerrich.office.oa.tools;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.activity.GuideActivity;
import com.powerrich.office.oa.activity.LoginActivity;
import com.powerrich.office.oa.activity.MainActivity;
import com.powerrich.office.oa.activity.OnlineWorkActivity;
import com.powerrich.office.oa.activity.RegisterActivity;
import com.powerrich.office.oa.activity.ServiceActivity;
import com.powerrich.office.oa.activity.WelcomeActivity;

import java.lang.reflect.Field;

/**
 * @author MingPeng
 * @date 2018/6/8
 */

public class StatusBarUtils {
    public static void initWindows(Activity activity, int colorResId) {
        //适配4.4启动页面状态栏
        Window window = activity.getWindow();
        int color = activity.getResources().getColor(colorResId);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            if (LoginActivity.class.getName().equals(activity.getClass().getName())) {
                window.setStatusBarColor(activity.getResources().getColor(R.color.transparent));
            } else if (MainActivity.class.getName().equals(activity.getClass().getName())) {
                window.setStatusBarColor(activity.getResources().getColor(R.color.transparent));
            } else if (ServiceActivity.class.getName().equals(activity.getClass().getName())) {
                window.setStatusBarColor(activity.getResources().getColor(R.color.transparent));
            } else if (OnlineWorkActivity.class.getName().equals(activity.getClass().getName())) {
                window.setStatusBarColor(activity.getResources().getColor(R.color.transparent));
            } else if (WelcomeActivity.class.getName().equals(activity.getClass().getName())) {
                window.setStatusBarColor(activity.getResources().getColor(R.color.transparent));
            } else {
                //设置状态栏颜色
                window.setStatusBarColor(color);
                ViewGroup contentView = ((ViewGroup) activity.findViewById(android.R.id.content));
                View childAt = contentView.getChildAt(0);
                if (childAt != null) {
                    childAt.setFitsSystemWindows(true);
                }
            }
            //设置导航栏颜色.
//            window.setNavigationBarColor(activity.getResources().getColor(R.color.transparent));

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (WelcomeActivity.class.getName().equals(activity.getClass().getName())) return;
            if (GuideActivity.class.getName().equals(activity.getClass().getName())) return;

            if (LoginActivity.class.getName().equals(activity.getClass().getName())) {
                //透明状态栏
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                return;
            } else if (MainActivity.class.getName().equals(activity.getClass().getName())) {
                //透明状态栏
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                return;
            } else if (ServiceActivity.class.getName().equals(activity.getClass().getName())) {
                //透明状态栏
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                return;
            } else if (OnlineWorkActivity.class.getName().equals(activity.getClass().getName())) {
                //透明状态栏
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                return;
            }
            //透明状态栏
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

            //设置contentview为fitsSystemWindows
            ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
            View childAt = contentView.getChildAt(0);
            if (childAt != null) {
                childAt.setFitsSystemWindows(true);
            }
            //给statusbar着色
            View view = new View(activity);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity)));
            view.setBackgroundColor(color);
            contentView.addView(view);
        }
    }

    /**
     * 获取状态栏高度
     *
     * @param context context
     * @return 状态栏高度
     */
    private static int getStatusBarHeight(Context context) {
        // 获得状态栏高度
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }
}
