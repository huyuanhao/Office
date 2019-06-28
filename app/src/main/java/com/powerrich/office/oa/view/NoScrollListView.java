package com.powerrich.office.oa.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by root on 16-5-23.
 */
public class NoScrollListView extends ListView {

	public NoScrollListView(Context context) {  
        super(context);  
    } 

    public NoScrollListView(Context context, AttributeSet attrs) {  
        super(context, attrs);  
    }  
  
    public NoScrollListView(Context context, AttributeSet attrs, int defStyle) {  
        super(context, attrs, defStyle);  
    } 
    @Override
    protected void onMeasure(int width, int height) {
        int expand = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(width, expand);
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(ev.getAction() == MotionEvent.ACTION_MOVE){
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }
}
