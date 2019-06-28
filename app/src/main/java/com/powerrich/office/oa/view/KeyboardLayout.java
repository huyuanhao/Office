package com.powerrich.office.oa.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/7/26
 * 版权：
 */
public class KeyboardLayout extends FrameLayout {

    private KeyboardLayoutListener mListener;
    private boolean mIsKeyboardActive = false; //输入法是否激活
    private int mKeyboardHeight = 0; // 输入法高度

    public KeyboardLayout(Context context) {
        this(context, null, 0);
    }

    public KeyboardLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KeyboardLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 监听布局变化
        getViewTreeObserver().addOnGlobalLayoutListener(new KeyboardOnGlobalChangeListener());
    }

    public void setKeyboardListener(KeyboardLayoutListener listener) {
        mListener = listener;
    }

    public KeyboardLayoutListener getKeyboardListener() {
        return mListener;
    }

    public boolean isKeyboardActive() {
        return mIsKeyboardActive;
    }

    /**
     * 获取输入法高度
     *
     * @return
     */
    public int getKeyboardHeight() {
        return mKeyboardHeight;
    }

    public interface KeyboardLayoutListener {
        /**
         * @param isActive       输入法是否激活
         * @param keyboardHeight 输入法面板高度
         */
        void onKeyboardStateChanged(boolean isActive, int keyboardHeight);
    }

    boolean isActive = false;

    private class KeyboardOnGlobalChangeListener implements ViewTreeObserver.OnGlobalLayoutListener {

        int mScreenHeight = 0;

        private int getScreenHeight() {
            if (mScreenHeight > 0) {
                return mScreenHeight;
            }
            mScreenHeight = ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE))
                    .getDefaultDisplay().getHeight();
            return mScreenHeight;
        }

        @Override
        public void onGlobalLayout() {
            Rect rect = new Rect();
            // 获取当前页面窗口的显示范围
            ((Activity) getContext()).getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
            int screenHeight = getScreenHeight();
            int keyboardHeight = screenHeight - rect.bottom; // 输入法的高度
            //判断监听软键盘是否弹起

            if (!isActive && Math.abs(keyboardHeight) > screenHeight / 4) {//键盘弹起来了
                isActive = true;
                mKeyboardHeight = keyboardHeight;
            } else if (Math.abs(keyboardHeight) == 0 || keyboardHeight < 0) {//键盘隐藏了
                isActive = false;
                mKeyboardHeight  = 0;
            }else if(mKeyboardHeight == keyboardHeight){ //当前是输入字母或者切换键盘第一次执行
                return;
            }else{
                //mKeyboardHeight != keyboardHeight 表示正在切换键盘
                mKeyboardHeight = keyboardHeight;
                isActive = true;
            }

            mIsKeyboardActive = isActive;
            if (mListener != null) {
                mListener.onKeyboardStateChanged(isActive, rect.bottom);
            }
        }
    }

}