package com.powerrich.office.oa.view;

import android.content.Context;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

/**
 * Created by MingPeng on 2018/7/20.
 */

public class NoEmojiEditText extends android.support.v7.widget.AppCompatEditText {
    //输入表情前的光标位置
    private int cursorPos;
    //输入表情前EditText中的文本
    private String inputAfterText;
    //是否重置了EditText的内容
    private boolean resetText;

    private Context mContext;

    public NoEmojiEditText(Context context) {
        super(context);
        this.mContext = context;
        initEditText();
    }

//    @Override
//    public Editable getText() {
//        return new SpannableStringBuilder(super.getText().toString().trim());
//    }
//


    public NoEmojiEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initEditText();
    }

    public NoEmojiEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initEditText();
    }

    // 初始化edittext 控件
    private void initEditText() {
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int before, int count) {

                if (!resetText) {
                    cursorPos = getSelectionEnd();
                    // 这里用s.toString()而不直接用s是因为如果用s，
                    // 那么，inputAfterText和s在内存中指向的是同一个地址，s改变了，
                    // inputAfterText也就改变了，那么表情过滤就失败了
                    inputAfterText = s.toString();
                }

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!resetText) {
                    if (containsEmoji( s.toString())) {
                        if (count >= 2) {//表情符号的字符长度最小为2

                          //  CharSequence input = s.subSequence(cursorPos, getSelectionEnd());
                            resetText = true;
                            Toast.makeText(mContext, "不支持输入Emoji表情符号", Toast.LENGTH_SHORT).show();
                            //是表情符号就将文本还原为输入表情符号之前的内容
                            setText(inputAfterText);
                            CharSequence text = getText();
                            if (text instanceof Spannable) {
                                Spannable spanText = (Spannable) text;
                                Selection.setSelection(spanText, text.length());
                            }
                        }
                    }
                } else {
                    resetText = false;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });
    }

    /**
     * 包括空格判断
     *
     * @param input
     * @return
     */
    public static boolean containSpace(String input) {
        return Pattern.compile("\\s+").matcher(input).find();
    }


    /**
     * 检测是否有emoji表情
     *
     * @param source
     * @return
     */
    public static boolean containsEmoji(String source) {
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!isEmojiCharacter(codePoint)) { //如果不能匹配,则该字符是Emoji表情
                return true;
            }
        }
        return false;
    }

    // 显示动画
    public void setShakeAnimation() {
        this.startAnimation(shakeAnimation(3));

    }

    // CycleTimes动画重复的次数
    public Animation shakeAnimation(int CycleTimes) {
        //设置偏移动画 其中new TranslateAnimation(0,10,0,10)四个值表示为 X坐标从0-->10,Y坐标从0-->10
        Animation translateAnimation = new TranslateAnimation(0, 10, 0, 10);
        //设置动画次数
        translateAnimation.setInterpolator(new CycleInterpolator(CycleTimes));
        //设置动画间隔
        translateAnimation.setDuration(500);
        return translateAnimation;
    }

    /**
     * 判断是否是Emoji
     *
     * @param codePoint 比较的单个字符
     * @return
     */
    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) ||
                (codePoint == 0xD) || ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || ((codePoint >= 0x10000)
                && (codePoint <= 0x10FFFF));
    }

}
