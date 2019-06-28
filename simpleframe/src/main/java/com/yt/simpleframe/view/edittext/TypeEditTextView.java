package com.yt.simpleframe.view.edittext;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.View;

import com.yt.simpleframe.R;
import com.yt.simpleframe.utils.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文件名：
 * 描述：验证邮箱 手机号，如果是验证相等，要将上一个editText的内容传进来  作为判断条件
 * 作者：梁帆
 * 时间：2018/6/14
 * 版权：
 */

public class TypeEditTextView extends AppCompatEditText implements View.OnFocusChangeListener {

    public static final String EMAIL = "email";
    public static final String PHONENUMBER = "phone";
    public static final String EQUALS = "equals";
    public static final String PWD = "pwd";

    private Drawable ok_xD;
    private Drawable fail_xD;

    private int ok_view;
    private int fail_view;
    private String type;
    private String defaultStr = "";

    public String getDefaultStr() {
        return defaultStr;
    }

    public void setDefaultStr(String defaultStr) {
        this.defaultStr = defaultStr;
    }

    public TypeEditTextView(Context context) {
        this(context, null);
    }

    public TypeEditTextView(Context context, AttributeSet attrs) {
        this(context, attrs, android.support.v7.appcompat.R.attr.editTextStyle);
    }

    public TypeEditTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.TypeEditView);
        ok_view = a.getResourceId(R.styleable.TypeEditView_okview, ok_view);
        fail_view = a.getResourceId(R.styleable.TypeEditView_failview, fail_view);
        type = a.getString(R.styleable.TypeEditView_type);
        init();
        a.recycle();
    }


    private InputFocusInterface focus;

    public interface InputFocusInterface {
        void hasFocus();

        void noneFocus();
    }

    public void setInputFocusInterface(InputFocusInterface inputFocusInterface) {
        this.focus = inputFocusInterface;
    }

    public void setCompareStr(String str) {
        this.defaultStr = str;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            //获取焦点的时候将上一个内容传入进来
            if (focus != null)
                focus.hasFocus();
        } else {
            if (focus != null)
                focus.noneFocus();
            //检查邮箱是否合法
            String str = getText().toString();
            boolean b = false;
            if (EMAIL.equals(type)) {
                b = checkEmail(str);
            } else if (PHONENUMBER.equals(type)) {
                b = isMobileNO(str);
            } else if (EQUALS.equals(type)) {
                if (StringUtil.isEmpty(defaultStr)) {
                    return;
                }
                b = str.equals(defaultStr);
            } else if (PWD.equals(type)) {
                String pwdStr = this.getText().toString();
                b = pwdStr.length() >= 6 && pwdStr.length() <= 16;
            }
            if (b) {
                showIcon(ok_xD);
            } else {
                showIcon(fail_xD);
            }
        }
    }


    public void setEmptyView() {
        showIcon(null);
    }


    private void init() {
        ok_xD = getCompoundDrawables()[2];
        fail_xD = getCompoundDrawables()[2];
        if (ok_xD == null) {
            ok_xD = ContextCompat.getDrawable(getContext(), ok_view);
            fail_xD = ContextCompat.getDrawable(getContext(), fail_view);
        }
        ok_xD.setBounds(0, 0, ok_xD.getIntrinsicWidth(), ok_xD.getIntrinsicHeight());
        fail_xD.setBounds(0, 0, ok_xD.getIntrinsicWidth(), ok_xD.getIntrinsicHeight());
        this.setOnFocusChangeListener(this);
    }

    private void setView(int okView, int failView) {
        ok_xD = ContextCompat.getDrawable(getContext(), R.mipmap.ic_launcher);
        fail_xD = ContextCompat.getDrawable(getContext(), R.mipmap.ic_launcher_round);
    }

    public void showIcon(Drawable drawable) {
        Drawable x = drawable;
        setCompoundDrawables(getCompoundDrawables()[0],
                getCompoundDrawables()[1], x, getCompoundDrawables()[3]);
    }

    /**
     * 验证邮箱是否争取
     *
     * @param email
     * @return
     */
    public static boolean checkEmail(String email) {
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 验证手机号码
     *
     * @param mobiles
     * @return [0-9]{5,9}
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern
                .compile("^0{0,1}(13[0-9]|15[0-9]|16[0-9]|18[0-9]|17[0-9]|14[0-9])[0-9]{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }
}
