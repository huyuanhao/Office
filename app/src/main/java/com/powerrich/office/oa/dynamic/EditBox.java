package com.powerrich.office.oa.dynamic;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.text.InputFilter;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.bean.DynamicBean;
import com.powerrich.office.oa.bean.DynamicInfo;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.DialogUtils;

/**
 * 在动态配置表单界面，如果一行显示的是一个textview和一个输入框，可以使用这个控件来实现
 */
public class EditBox extends LinearLayout implements DynamicListener {

    private Context mContext;
    private EditText mEditTxt;
    private DynamicInfo mInfo;

    public EditBox(Context context) {
        this(context, null);
    }

    public EditBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public void createView(int width, DynamicInfo info, boolean isEnable) {
        mInfo = info;
        createView(width, isEnable);
    }

    private void createView(int width, boolean isEnable) {
        LimitLabel text = new LimitLabel(mContext);
        text.createView(width, mInfo.getFieldCName());
        mEditTxt = new EditText(mContext);
        mEditTxt.setEnabled(isEnable);
        mEditTxt.setTextSize(ViewUtils.TEXT_UNIT, ViewUtils.TEXT_SIZE);
        mEditTxt.setTextColor(Color.parseColor("#666666"));
        if (!BeanUtils.isNullOrEmpty(mInfo.getDefaultValue())) {
            mEditTxt.setText(mInfo.getDefaultValue());
        }
        if (!BeanUtils.isNullOrEmpty(mInfo.getFieldLength())) {
            mEditTxt.setFilters(new InputFilter[]{new InputFilter.LengthFilter(Integer.parseInt(mInfo.getFieldLength()))});
        }
        if ("001".startsWith(mInfo.getStyleId())) {
            mEditTxt.setKeyListener(DigitsKeyListener.getInstance("123456789x"));
            mEditTxt.setFilters(new InputFilter[]{new InputFilter.LengthFilter(18)});
        } else if ("003".startsWith(mInfo.getStyleId())) {
            mEditTxt.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else if ("002001".equals(mInfo.getStyleId())) {
            mEditTxt.setInputType(InputType.TYPE_CLASS_TEXT);
            if ("person_phone".equals(mInfo.getFieldEName())) {
                mEditTxt.setInputType(InputType.TYPE_CLASS_NUMBER);
                mEditTxt.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
            }
        } else if ("002002".equals(mInfo.getStyleId())) {
            mEditTxt.setTransformationMethod(PasswordTransformationMethod.getInstance());
        } else if ("002004".equals(mInfo.getStyleId())) {
            mEditTxt.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
            // 改变默认的单行模式
            mEditTxt.setSingleLine(false);
            // 水平滚动设置为False
            mEditTxt.setHorizontallyScrolling(false);
        }
        mEditTxt.setBackground(ContextCompat.getDrawable(mContext, R.drawable.gray_stroke_corners_bg));
        mEditTxt.setPadding(20, 20, 20, 20);
        mEditTxt.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(text);
        addView(mEditTxt);
        setPadding(0, 5, 0, 5);
        setGravity(Gravity.CENTER_VERTICAL);
    }

    private boolean validate() {
        boolean flag = true;
        String content = mEditTxt.getText().toString().trim();
		if (BeanUtils.isEmpty(content)) {
            DialogUtils.showToast(mContext, mInfo.getFieldCName() + "不能为空");
            flag = false;
        } else if ("person_phone".equals(mInfo.getFieldEName())) {
            if (!BeanUtils.isMobileNO(content)) {
                DialogUtils.showToast(mContext, "联系电话格式不正确");
                flag = false;
            }
        }
        return flag;
    }

    public DynamicBean.ParamMap getValue() {
        if (!validate()) {
            return null;
        }
        return new DynamicBean.ParamMap(mInfo.getFieldEName(), mEditTxt.getText().toString().trim());
    }
}
