package com.powerrich.office.oa.dynamic;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.powerrich.office.oa.R;
import com.powerrich.office.oa.bean.DynamicBean;
import com.powerrich.office.oa.bean.DynamicInfo;
import com.powerrich.office.oa.tools.DateUtils;

import java.util.Date;

public class SpinnerBox extends LinearLayout implements DynamicListener {

    private Context mContext;
    private EditText content;
    private DynamicInfo info;
    private TimePickerView pvTime;
    private String format;

    private OnClickListener listener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            showTimeSelect();
        }

    };

    public SpinnerBox(Context context) {
        this(context, null);
    }

    public SpinnerBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public void createView(int width, DynamicInfo info, boolean isEnable) {
        this.info = info;
        createView(width, isEnable);
        if ("004001".equals(info.getStyleId())) {
            content.setText(DateUtils.getDateStr());
            initTimePicker(true, true, true, false, false, false);
        } else {
            content.setText(DateUtils.getDateTimeStr());
            initTimePicker(true, true, true, true, true, true);
        }
    }

    public void createView(int width, boolean isEnable) {
        LimitLabel txt = new LimitLabel(mContext);
        txt.createView(width, info.getFieldCName());
        content = new EditText(mContext);
        content.setFocusable(false);
        content.setFocusableInTouchMode(false);
        content.setEnabled(isEnable);
        content.setTextSize(ViewUtils.TEXT_UNIT, ViewUtils.TEXT_SIZE);
        Drawable image = ContextCompat.getDrawable(mContext, R.drawable.btn_expand);
        // 非常重要，必须设置，否则图片不会显示
        image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
        content.setCompoundDrawables(null, null, image, null);
        content.setBackground(ContextCompat.getDrawable(mContext, R.drawable.gray_stroke_corners_bg));
        content.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        content.setOnClickListener(listener);
        content.setPadding(20, 20, 20, 20);
        addView(txt);
        addView(content);
        setPadding(0, 5, 0, 5);
        setGravity(Gravity.CENTER_VERTICAL);
    }

    private void initTimePicker(boolean year, boolean month, boolean day, final boolean h, boolean m, boolean s) {
        //时间选择器
        pvTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View view) {
                content.setText(DateUtils.getDateStr(date, format));
            }
        }).setType(new boolean[]{year, month, day, h, m, s}).build();
    }

    public void showTimeSelect() {
        if ("004001".equals(info.getStyleId())) {
            format = DateUtils.DATE_FORMAT;
        } else {
            format = DateUtils.DATE_TIME_FORMAT;
        }
        pvTime.setDate(DateUtils.dataToCalendar(DateUtils.parseDate(content.getText().toString().trim(), format)));
        pvTime.show();
    }

    public DynamicBean.ParamMap getValue() {
        return new DynamicBean.ParamMap(info.getFieldEName(), content.getText().toString().trim());
    }

}
