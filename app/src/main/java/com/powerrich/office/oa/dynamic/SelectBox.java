package com.powerrich.office.oa.dynamic;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.bean.DynamicBean;
import com.powerrich.office.oa.bean.DynamicInfo;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.view.MyDialog;

import java.util.List;

public class SelectBox extends LinearLayout implements DynamicListener {

    private Context mContext;
    private EditText content;
    private DynamicInfo info;
    private String[] values;
    private int[] positions;
    private MyDialog dialog;

    private OnClickListener listener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            showSpinner();
        }

    };


    public SelectBox(Context context) {
        this(context, null);
    }

    public SelectBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public void createView(int width, DynamicInfo info, boolean isEnable) {
        if (null == info) {
            return;
        }
        this.info = info;
        List<DynamicInfo.SourceInfo> sourceInfoList = info.getSourceInfoList();
        values = new String[sourceInfoList.size()];
        positions = new int[sourceInfoList.size()];
        if (!BeanUtils.isEmpty(sourceInfoList)) {
            for (int i = 0; i < sourceInfoList.size(); i++) {
                positions[i] = i;
                values[i] = sourceInfoList.get(i).getVal();
            }
        }
        createView(width, isEnable);
        if (null != values && values.length > 0) {
            content.setText(values[0]);
        }
        if (null != positions && positions.length > 0) {
            content.setTag(positions[0]);
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

    public void showSpinner() {
        if (dialog != null && dialog.isShowing()) {
            return;
        }
        dialog = new MyDialog(mContext).builder();
        int checkedItem = 0;
        if (!BeanUtils.isEmpty(content.getTag())) {
            try {
                checkedItem = (int) content.getTag();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            checkedItem = 0;
        }
        // 设置一个下拉的列表选择项
        dialog.setSingleChoiceItems(values, checkedItem,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        content.setText(values[which]);
                        content.setTag(positions[which]);
                        dialog.dismiss();
                    }
                });
        dialog.show();
    }

    public DynamicBean.ParamMap getValue() {
        return new DynamicBean.ParamMap(info.getFieldEName(), info.getSourceInfoList().get((Integer) content.getTag()).getId());
    }

}
