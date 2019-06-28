package com.yt.simpleframe.utils;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.yt.simpleframe.R;


/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/6/11
 * 版权：
 */

public class CountDownHelper {

    // 倒计时timer
    private CountDownTimer countDownTimer;
    // 计时结束的回调接口
    private OnFinishListener listener;

    private TextView button;

    /**
     * @param button        需要显示倒计时的Button
     * @param defaultString 默认显示的字符串
     * @param max           需要进行倒计时的最大值,单位是秒
     * @param interval      倒计时的间隔，单位是秒
     */
    public CountDownHelper(final TextView button,
                           final String defaultString, final String hintString, int max, int interval) {

        this.button = button;
        // 由于CountDownTimer并不是准确计时，在onTick方法调用的时候，time会有1-10ms左右的误差，这会导致最后一秒不会调用onTick()
        // 因此，设置间隔的时候，默认减去了10ms，从而减去误差。
        // 经过以上的微调，最后一秒的显示时间会由于10ms延迟的积累，导致显示时间比1s长max*10ms的时间，其他时间的显示正常,总时间正常
        countDownTimer = new CountDownTimer(max * 1000, interval * 1000 - 10) {

            @Override
            public void onTick(long time) {
                // 第一次调用会有1-10ms的误差，因此需要+15ms，防止第一个数不显示，第二个数显示2s
                String timeStr = ((time+15)/1000)+"";
                String text = timeStr + "秒" + hintString;
                SpannableString spannableString = new SpannableString(text);
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#ea7805")), 0, timeStr.length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                button.setTextColor(button.getResources().getColor(R.color.gray));
                button.setText(spannableString);
            }

            @Override
            public void onFinish() {
                button.setEnabled(true);
                button.setTextColor(button.getResources().getColor(R.color.white));
                button.setText(defaultString);
                if (listener != null) {
                    listener.finish();
                }
            }
        };
    }

    public CountDownHelper(final TextView button,
                           final String defaultString, int max, int interval) {
        this.button = button;
        // 由于CountDownTimer并不是准确计时，在onTick方法调用的时候，time会有1-10ms左右的误差，这会导致最后一秒不会调用onTick()
        // 因此，设置间隔的时候，默认减去了10ms，从而减去误差。
        // 经过以上的微调，最后一秒的显示时间会由于10ms延迟的积累，导致显示时间比1s长max*10ms的时间，其他时间的显示正常,总时间正常
        countDownTimer = new CountDownTimer(max * 1000, interval * 1000 - 10) {

            @Override
            public void onTick(long time) {
                // 第一次调用会有1-10ms的误差，因此需要+15ms，防止第一个数不显示，第二个数显示2s
                String text = (time + 15) / 1000 + "";
                button.setText(text);
                LogUtil.d("CountDownHelper", "time = " + (time) + " text = "
                        + ((time + 15) / 1000));
            }

            @Override
            public void onFinish() {
                button.setEnabled(true);
                button.setText(defaultString);
                if (listener != null) {
                    listener.finish();
                }
            }
        };
    }

    /**
     * 开始倒计时
     */
    public void start() {
        button.setEnabled(false);
        countDownTimer.start();
    }

    public void stop() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    /**
     * 设置倒计时结束的监听器
     *
     * @param listener
     */
    public void setOnFinishListener(OnFinishListener listener) {
        this.listener = listener;
    }

    /**
     * 计时结束的回调接口
     *
     * @author zhaokaiqiang
     */
    public interface OnFinishListener {
        void finish();
    }

}
