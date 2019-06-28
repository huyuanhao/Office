package com.powerrich.office.oa.tools;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.style.ReplacementSpan;

import com.powerrich.office.oa.R;

public class RoundBackgroundColorSpan extends ReplacementSpan {
    private int mRadius;
    private int bgColor;
    private int textColor;
    private int mSize;

    public RoundBackgroundColorSpan(int bgColor,
                                    int textColor,
                                    int radius) {
        super();
        this.bgColor = bgColor;
        this.textColor = textColor;
        this.mRadius = radius;
    }

    /**
     * @param start 第一个字符的下标
     * @param end   最后一个字符的下标
     * @return span的宽度
     */
    @Override
    public int getSize(@NonNull Paint paint,
                       CharSequence text,
                       int start,
                       int end,
                       Paint.FontMetricsInt fm) {
        mSize = (int) (paint.measureText(text, start, end) + mRadius);
        return mSize + 5;//5:距离其他文字的空白
    }

    /**
     * @param y baseline
     */
    @Override
    public void draw(@NonNull Canvas canvas,
                     CharSequence text,
                     int start,
                     int end,
                     float x,
                     int top,
                     int y, int bottom,
                     @NonNull Paint paint) {
        int defaultColor = paint.getColor();//保存文字颜色
        float defaultStrokeWidth = paint.getStrokeWidth();

        //绘制圆角矩形
        paint.setColor(bgColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(AutoUtils.getDisplayWidthValue(2));
        paint.setAntiAlias(true);
        RectF rectF = new RectF(x + 2.5f, 2.5f, x + mSize, y);
        //设置文字背景矩形，x为span其实左上角相对整个TextView的x值，y为span左上角相对整个View的y值。
        // paint.ascent()获得文字上边缘，paint.descent()获得文字下边缘
        //x+2.5f解决线条粗细不一致问题
        canvas.drawRoundRect(rectF, mRadius, mRadius, paint);

        //绘制文字
        paint.setColor(textColor);
        paint.setTextSize(AutoUtils.getDisplayTextSize(30));
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(defaultStrokeWidth);
        paint.setTextAlign(Paint.Align.CENTER);
        float he = rectF.height();
        float as = paint.ascent();
        float de = paint.descent();
        canvas.drawText(text, start, end, rectF.centerX(), (rectF.height() - paint.ascent() - paint.descent()) / 2+2.5f, paint);//此处mRadius为文字右移距离
        paint.setColor(defaultColor);//恢复画笔的文字颜色
    }
}
