package com.powerrich.office.oa.dynamic;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.bean.DynamicInfo;
import com.powerrich.office.oa.tools.BeanUtils;

public class RadioBox extends LinearLayout {
	
	private Context mContext;
	private int checkedIndex;
	private DynamicInfo info;
	
	public RadioBox(Context context) {
		this(context, null);
	}

	public RadioBox(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
	}

	public void createView(int width, int def, DynamicInfo info) {
		this.info = info;
		createView(width, def);
	}


	public void createView(int width, int def) {
		checkedIndex = def;
		LimitLabel text = new LimitLabel(mContext);
		text.createView(width, info.getFieldCName());
		MyRadioGroup group = new MyRadioGroup(mContext);
		group.setPadding(5, 30, 0, 30);
		group.setBackground(ContextCompat.getDrawable(mContext, R.drawable.gray_border_white_bg));
		group.setOrientation(HORIZONTAL);
		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				checkedIndex = checkedId;
			}
		});
		List<DynamicInfo.SourceInfo> sourceInfoList = info.getSourceInfoList();
		if (!BeanUtils.isEmpty(sourceInfoList)) {
			for (int i = 0; i < sourceInfoList.size(); i++) {
				RadioButton button = new RadioButton(mContext);
				button.setText(sourceInfoList.get(i).getVal());
				button.setButtonDrawable(ContextCompat.getDrawable(mContext, R.drawable.rb_selector));
				button.setTextSize(ViewUtils.TEXT_UNIT, ViewUtils.TEXT_SIZE);
				group.addView(button);
			}
		}
		if (def >= 0)
			((RadioButton) group.getChildAt(def)).setChecked(true);
		text.setPadding(0, 5, 0, 5);
		group.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		addView(text);
		addView(group);
		setGravity(Gravity.CENTER_VERTICAL);
	}

	public void createView(String tips, int width, ArrayList<String> radio, int def) {
		checkedIndex = def;
		LimitLabel text = new LimitLabel(mContext);
		text.createView(width, tips);
//		RadioGroup group = new RadioGroup(mContext);
		MyRadioGroup group = new MyRadioGroup(mContext);
		group.setOrientation(HORIZONTAL);
		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
//				Toast.makeText(mContext, "currentChecked " + checkedId, Toast.LENGTH_SHORT).show();
				checkedIndex = checkedId;
			}
		});
		for (int i = 0; i < radio.size(); i++) {
			RadioButton button = new RadioButton(mContext);
			button.setText(radio.get(i));
			button.setTextSize(ViewUtils.TEXT_UNIT, ViewUtils.TEXT_SIZE);
			group.addView(button);
		}
		if (def >= 0)
			((RadioButton) group.getChildAt(def)).setChecked(true);
		text.setPadding(0, 5, 0, 5);
		group.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		addView(text);
		addView(group);
		setGravity(Gravity.CENTER_VERTICAL);
	}
	
	public int getValue() {
		return checkedIndex;
	}

}
