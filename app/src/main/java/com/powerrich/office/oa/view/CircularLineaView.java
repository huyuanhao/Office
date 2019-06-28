package com.powerrich.office.oa.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/8/1
 * 版权：
 */

public class CircularLineaView extends android.support.v7.widget.AppCompatTextView {

    private float radius = 5;
    private int space = 0;
    private Paint mPaint;
    private int width = 0;
    private int height = 0;
    int color = 0;
    private float size;

    public CircularLineaView(Context context) {
        this(context, null);
    }

    public CircularLineaView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, android.support.v7.appcompat.R.attr.autoCompleteTextViewStyle);
    }

    public CircularLineaView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        color = getCurrentTextColor();
        radius = getTextSize() / 15;
        radius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, radius, this.getResources().getDisplayMetrics());
        space = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, this.getResources().getDisplayMetrics());
        mPaint.setColor(color);
        mPaint.setAntiAlias(true);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        size = width / (radius * 2 + space);
        if (size > 2) {
            size--;
        }
        for (int i = 0; i < size; i++) {
            canvas.drawCircle((radius + space) + i * (radius * 2 + space), height / 2, radius, mPaint);
        }
    }
}
