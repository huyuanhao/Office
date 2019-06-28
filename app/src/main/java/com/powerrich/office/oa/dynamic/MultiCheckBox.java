package com.powerrich.office.oa.dynamic;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;

import com.powerrich.office.oa.bean.DynamicBean;
import com.powerrich.office.oa.bean.DynamicInfo;
import com.powerrich.office.oa.tools.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class MultiCheckBox extends LinearLayout implements DynamicListener {
	
	private Context mContext;
	private ArrayList<Integer> array = new ArrayList<>();
	private DynamicInfo mInfo;
	
	public MultiCheckBox(Context context) {
		this(context, null);
	}

	public MultiCheckBox(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
	}

	public void createView(int width, DynamicInfo info, ArrayList<String> checkbox, boolean isEnable) {
		mInfo = info;
		createView(info.getFieldCName(), width, checkbox, isEnable);
	}
	
	public void createView(String tips, int width, ArrayList<String> checkbox, boolean isEnable) {
		LimitLabel text = new LimitLabel(mContext);
		text.createView(width, tips);
//		text.setLayoutParams(new LayoutParams(width, LayoutParams.WRAP_CONTENT));
		final CheckBoxGroup group = new CheckBoxGroup(mContext);
		group.setOrientation(HORIZONTAL);
		group.setPadding(10, 0, 10, 0);
		for (int i = 0; i < checkbox.size(); i++) {
			CheckBox button = new CheckBox(mContext);
            button.setEnabled(isEnable);
			String value = mInfo.getDefaultValue();
			List<DynamicInfo.SourceInfo> sourceList = mInfo.getSourceInfoList();
			if (!BeanUtils.isNullOrEmpty(value)) {
				if (!value.contains(",")) {
					if (!isEnable) {
						for (int j = 0; j < sourceList.size(); j++) {
							if (value.equals(sourceList.get(j).getId())) {
								button.setChecked(true);
							}
						}
					} else {
						if (value.equals(mInfo.getSourceInfoList().get(i).getId())) {
							button.setChecked(true);
							array.add(Integer.parseInt(value) - 1);
						}
					}
				} else {
					String[] values = value.split(",");
					if (!isEnable) {
						for (int j = 0; j < values.length; j++) {
							String str = values[j];
							for (int k = 0; k < sourceList.size(); k++) {
								if (str.equals(sourceList.get(k).getId())) {
									button.setChecked(true);
								}
							}
						}
					} else {
						for (int j = 0; j < values.length; j++) {
							String str = values[j];
							if (str.equals(mInfo.getSourceInfoList().get(i).getId())) {
								button.setChecked(true);
								array.add(Integer.parseInt(str) - 1);
							}
						}
					}
				}
			}
			button.setText(checkbox.get(i));
			button.setTextSize(ViewUtils.TEXT_UNIT, ViewUtils.TEXT_SIZE);
			group.addView(button);
			button.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					if (isChecked) {
						array.add(group.indexOfChild(buttonView));
					} else {
						array.remove((Integer) group.indexOfChild(buttonView));
					}
				}
				
			});
		}
		group.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		addView(text);
		addView(group);
		setGravity(Gravity.CENTER_VERTICAL);
	}
	
	public DynamicBean.ParamMap getValue() {
		StringBuffer string = new StringBuffer();
		boolean flag = false;
		for (int i : array) {
			if (flag) {
				string.append(",");
			}
			if (!BeanUtils.isEmpty(mInfo.getSourceInfoList())) {
				string.append(mInfo.getSourceInfoList().get(i).getId());
				flag = true;
			}
		}
		return new DynamicBean.ParamMap(mInfo.getFieldEName(), string.toString());
	}

}
