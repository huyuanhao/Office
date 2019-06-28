package com.powerrich.office.oa.activity.Interaction;

import android.view.View;

/**
 * @author AlienCc
 * @date 2018/09/27 15:03
 */
public abstract class OnMultiClickListener implements View.OnClickListener {
    //最小限制的时间
    private static final int MIN_CLICK_DELAY_TIME = 1000;

    private static long lastClickTime = 0;

    protected abstract void onMultiClick(View v);

    @Override
    public void onClick(View v) {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - lastClickTime >= MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTimeMillis;
            onMultiClick(v);
        }
    }
}
