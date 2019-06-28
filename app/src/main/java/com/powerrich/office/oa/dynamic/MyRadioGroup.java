package com.powerrich.office.oa.dynamic;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RadioGroup;

public class MyRadioGroup extends RadioGroup {
	
	private List<RowView> rowViews = new ArrayList<RowView>();
	private int horizontalSpacing = 20;
	private int verticalSpacing = 10;
	private Context mContext;
	
	public MyRadioGroup(Context context) {
		this(context, null);
	}

	public MyRadioGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
    public void setHorizontalSpacing(int horizontalSpacing_dp) {
        this.horizontalSpacing = dip2px(mContext, horizontalSpacing_dp);
    }

    public void setVerticalSpacing(int verticalSpacing_dp) {
        this.verticalSpacing = dip2px(mContext, verticalSpacing_dp);
    }

    public int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    	super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    	rowViews.clear();
    	//获取屏幕宽度
    	int width = MeasureSpec.getSize(widthMeasureSpec);
    	int noPaddingWidth = width - getPaddingLeft() - getPaddingRight();
    	RowView rowView = null;
    	for (int i = 0; i < getChildCount(); i++) {
    		View childView = getChildAt(i);
    		childView.measure(0, 0);
    		if (rowView == null) {
    			rowView = new RowView();
    		}
    		if (rowView.getRowViews().size() == 0) {
    			rowView.addChildView(childView);
    		} else if (rowView.getRowWidth() + horizontalSpacing + childView.getMeasuredWidth() > noPaddingWidth) {
    			rowViews.add(rowView);
    			rowView = new RowView();
    			rowView.addChildView(childView);
    		} else {
    			rowView.addChildView(childView);
    		}
    		if (i == getChildCount() - 1) {
    			rowViews.add(rowView);
    		}
    	}
    	int height = getPaddingTop() + getPaddingBottom();
    	for (int i = 0; i < rowViews.size(); i++) {
    		height += rowViews.get(i).getRowHeight();
    	}
    	height += (rowViews.size() - 1) * verticalSpacing;
    	setMeasuredDimension(width, height);
    	if (getChildCount() == 0) {
    		setMeasuredDimension(0, 0);
    	}
    }
    
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
    	super.onLayout(changed, l, t, r, b);
    	int paddingLeft = getPaddingLeft();
    	int paddingTop = getPaddingTop();
    	
    	for (int i = 0; i < rowViews.size(); i++) {
    		RowView rowView = rowViews.get(i);
    		if (i > 0) {
    			paddingTop += rowViews.get(i - 1).getRowHeight() + verticalSpacing;
    		}
    		List<View> viewList = rowView.getRowViews();
    		for (int j = 0; j < viewList.size(); j++) {
    			View childView = viewList.get(j);
    			if (j == 0) {
    				childView.layout(paddingLeft, paddingTop, paddingLeft + childView.getMeasuredWidth(), paddingTop + childView.getMeasuredHeight());
    			} else {
    				View preView = viewList.get(j - 1);
    				int left = preView.getRight() + horizontalSpacing;
    				childView.layout(left, preView.getTop(), left + childView.getMeasuredWidth(), preView.getBottom());
    			}
    		}
    	}
    }
	
	class RowView {
		private ArrayList<View> lineViews = new ArrayList<View>();
		private int rowWidth;
		private int rowHeight;
		
		public List<View> getRowViews() {
			return lineViews;
		}
		
		public int getRowWidth() {
			return rowWidth;
		}
		
		public int getRowHeight() {
			return rowHeight;
		}
		
		public void addChildView(View view) {
			if (!lineViews.contains(view)) {
				if (lineViews.size() == 0) {
					rowWidth = view.getMeasuredWidth();
				} else {
					rowWidth += view.getMeasuredWidth() + horizontalSpacing;
				}
				rowHeight = Math.max(view.getMeasuredHeight(), rowHeight);
				lineViews.add(view);
			}
		}
	}

}
