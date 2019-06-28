package com.powerrich.office.oa.dynamic;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class CheckBoxGroup extends LinearLayout {
	
	private int horizontalSpacing = 20;
	private int verticalSpacing = 10;
	private Context mContext;
	
	public CheckBoxGroup(Context context) {
		this(context, null);
	}

	public CheckBoxGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
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
    	int width = MeasureSpec.getSize(widthMeasureSpec);
    	int noPaddingWidth = width - getPaddingLeft() - getPaddingRight();
    	int sumWidth = 0;
    	int rowHeight = 0;
    	int rows = 0;
    	int count = getChildCount();
    	for (int i = 0; i < count; i++) {
    		View childView = getChildAt(i);
    		if (0 == i) {
    			rowHeight = childView.getMeasuredHeight();
    		}
    		measureChild(childView, widthMeasureSpec, heightMeasureSpec);
    		if (sumWidth + childView.getMeasuredWidth() > noPaddingWidth) {
    			rows++;
    			sumWidth = childView.getMeasuredWidth();
    		} else {
    			sumWidth += childView.getMeasuredWidth() + horizontalSpacing;
    		}
    		if (i == count - 1) {
    			sumWidth += childView.getMeasuredWidth() + horizontalSpacing;
    		}
    	}
    	setMeasuredDimension(width, rows * (rowHeight + verticalSpacing) + rowHeight);
    }
    
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//    	super.onLayout(changed, l, t, r, b);
    	System.out.println("group: " + l + "  " + t + "  " + r);
    	int count = getChildCount();
    	int row = 0;
    	int sumWidth = l;
    	int sumHeight = t;
    	int rowHeight = 0;
    	for (int i = 0; i < count; i++) {
    		View childView = getChildAt(i);
    		if (i == 0) {
    			rowHeight = childView.getMeasuredHeight();
    		}
    		int width = childView.getMeasuredWidth();
    		sumWidth += width;
    		if (sumWidth > r) {
    			sumWidth = width + l;
    			row++;
    		}
    		sumHeight = (row + 1) * rowHeight + verticalSpacing + t;
    		childView.layout(sumWidth - width - l, sumHeight - rowHeight, sumWidth - l, sumHeight);
    		sumWidth += horizontalSpacing;
    	}
    }

}
