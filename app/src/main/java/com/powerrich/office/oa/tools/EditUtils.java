package com.powerrich.office.oa.tools;

import android.text.InputFilter;
import android.text.Spanned;
import android.widget.EditText;

/**
 * @author Administrator
 * @date 2018/11/07 09:29
 */
public class EditUtils {

    public static void isEdit(EditText editText, boolean value) {

        if (value) {

            editText.setFocusable(true);

            editText.setFocusableInTouchMode(true);

            editText.setFilters(new InputFilter[]{new InputFilter() {

                @Override

                public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

                    return null;

                }

            }});

        } else {

//设置不可获取焦点

            editText.setFocusable(false);

            editText.setFocusableInTouchMode(false);

//输入框无法输入新的内容

            editText.setFilters(new InputFilter[]{new InputFilter() {

                @Override

                public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

                    return source.length() < 1 ? dest.subSequence(dstart, dend) : "";

                }

            }});

        }

    }

}
