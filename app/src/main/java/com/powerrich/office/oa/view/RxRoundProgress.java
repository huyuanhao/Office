package com.powerrich.office.oa.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.powerrich.office.oa.R;

/**
 * 弧形进度条
 *
 * @author Vondear
 * @date 2015/12/03
 */
public class RxRoundProgress extends View {
    public static final int STROKE = 0;
    /**
     * 画笔对象的引用
     */
    private Paint paint;
    private Paint textPaint;
    private Paint moneyPaint;
    private Paint moneyDPaint;
    /**
     * 圆环的颜色
     */
    private int roundColor;
    /**
     * 圆环进度的颜色
     */
    private int roundProgressColor;
    /**
     * 中间进度百分比的字符串的颜色
     */
    private int textColor;
    /**
     * 中间进度百分比的字符串的字体
     */
    private float textSize;
    /**
     * 圆环的宽度
     */
    private float roundWidth;
    /**
     * 最大进度
     */
    private double max;
    /**
     * 当前进度
     */
    private double progress;
    /**
     * 是否显示中间的进度
     */
    private boolean textIsDisplayable;
    /**
     * 进度的风格，实心或者空心
     */
    private int style;

    public RxRoundProgress(Context context) {
        this(context, null);
    }

    public RxRoundProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RxRoundProgress(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        //进度条画笔
        paint = new Paint();
        //文字画笔
        textPaint = new Paint();
        //文字画笔
        moneyPaint = new Paint();
        //文字画笔
        moneyDPaint = new Paint();
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs,
                R.styleable.RxRoundProgress);

        //获取自定义属性和默认值
        roundColor = mTypedArray.getColor(R.styleable.RxRoundProgress_roundColor1, Color.WHITE);
        roundProgressColor = mTypedArray.getColor(R.styleable.RxRoundProgress_roundProgressColor1, Color.parseColor("#F6B141"));
        textColor = mTypedArray.getColor(R.styleable.RxRoundProgress_textColor1, Color.GREEN);
        textSize = mTypedArray.getDimension(R.styleable.RxRoundProgress_textSize1, 15);
        roundWidth = mTypedArray.getDimension(R.styleable.RxRoundProgress_roundWidth1, 20);
        max = mTypedArray.getInteger(R.styleable.RxRoundProgress_max1, 100);
        textIsDisplayable = mTypedArray.getBoolean(R.styleable.RxRoundProgress_textIsDisplayable1, true);
        style = mTypedArray.getInt(R.styleable.RxRoundProgress_style1, 0);

        mTypedArray.recycle();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /**
         * 画最外层的大圆环
         */
        //获取圆心的x坐标
        int centre = getWidth() / 2;
        //圆环的半径
        int radius = (int) (centre - roundWidth / 2 - 15);
        //用于定义的圆弧的形状和大小的界限
        RectF oval = new RectF(centre - radius, centre - radius, centre + radius, centre + radius);
        //设置圆环的颜色
        paint.setColor(roundColor);
        //设置空心
        paint.setStyle(Paint.Style.STROKE);
        //设置圆环的宽度
        paint.setStrokeWidth(roundWidth);
        //消除锯齿
        paint.setAntiAlias(true);
        //设置边缘为圆角
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setAlpha(60);
//        BlurMaskFilter bmf=new BlurMaskFilter(100f,BlurMaskFilter.Blur.NORMAL);
//        paint.setMaskFilter(bmf);
        this.setLayerType(View.LAYER_TYPE_SOFTWARE,null);
        paint.setShadowLayer(15,0,15,Color.parseColor("#2011a3fa"));

//		canvas.drawRect(0, 0, getWidth(), getWidth(), paint);// 正方形
        textPaint.setColor(roundColor);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(36);
        moneyPaint.setColor(roundProgressColor);
        moneyPaint.setAntiAlias(true);
        moneyPaint.setTypeface(Typeface.DEFAULT_BOLD);
        moneyPaint.setShadowLayer(4,0,10,Color.parseColor("#3011a3fa"));
        moneyPaint.setTextSize(32);
        moneyDPaint.setColor(roundProgressColor);
        moneyDPaint.setAntiAlias(true);
        moneyDPaint.setShadowLayer(4,0,10,Color.parseColor("#3011a3fa"));
        moneyDPaint.setTextSize(32);
        //左边最小值
        canvas.drawText("0元", (float) (radius - Math.sqrt(2) * (radius / 2) + 10), (float) (2 * radius - Math.sqrt(2) * (radius / 4) + 130), textPaint);
        //右边最大值
        canvas.drawText(getMax() + "元", (float) (radius + Math.sqrt(2) * (radius / 2) + 138), (float) (2 * radius - Math.sqrt(2) * (radius / 4) + 130), textPaint);
		/*if(progress<50){
			double money = progress*1+(Math.floor(Math.random()*getMax()));
			canvas.drawText(money+"", (centre+90) - moneyPaint.measureText(money+"")/2-15, centre+165, moneyPaint);//右边最大值
		}else{*/
        //右边最大值
        canvas.drawText(String.format("%.2f", getProgress()), centre - moneyPaint.measureText(String.format("%.2f", getProgress())) / 2, centre, moneyPaint);
        //}
        //右边最大值
        canvas.drawText("(单位)元", centre - moneyDPaint.measureText("(单位)元") / 2, centre + 50, moneyDPaint);
        //根据进度画圆弧
        canvas.drawArc(oval, 135, 270, false, paint);

        /**
         * 画进度百分比
         */
        paint.setStrokeWidth(0);
        paint.setColor(textColor);
        paint.setTextSize(textSize);
        //设置字体
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        //中间的进度百分比，先转换成float在进行除法运算，不然都为0
        int percent = (int) (((float) progress / (float) max) * 100);
        //测量字体宽度，我们需要根据字体的宽度设置在圆环中间
        float textWidth = paint.measureText(percent + "%");

        if (textIsDisplayable && percent != 0 && style == STROKE) {
            //canvas.drawText(percent + "%", centre+90 - textWidth / 2, centre + 90 + textSize/2, paint); //画出进度百分比
        }

        /**
         * 画圆弧 ，画圆环的进度
         */
        //设置进度是实心还是空心
        //圆环的半径
        float roundWidth2 = roundWidth/2;
        int radius2 = (int) (centre - roundWidth2 / 2 - 15);
        //用于定义的圆弧的形状和大小的界限
        RectF oval2 = new RectF(centre - radius2, centre - radius2, centre + radius2, centre + radius2);
        paint.setStrokeWidth(roundWidth2); //设置圆环的宽度
        paint.setColor(roundProgressColor);  //设置进度的颜色

        switch (style) {
            case STROKE: {
                paint.setStyle(Paint.Style.STROKE);
                if (progress >= 0) {
                    canvas.drawArc(oval2, 135, 270 * ((float) progress / (float) max), false, paint);  //根据进度画圆弧
                }
                break;
            }
            default:
                break;
        }
    }

    public synchronized double getMax() {
        return max;
    }

    /**
     * 设置进度的最大值
     *
     * @param max
     */
    public synchronized void setMax(double max) {
        if (max < 0) {
            throw new IllegalArgumentException("max not less than 0");
        }
        this.max = max;
    }

    /**
     * 获取进度.需要同步
     *
     * @return
     */
    public synchronized double getProgress() {
        return progress;
    }

    /**
     * 设置进度，此为线程安全控件，由于考虑多线的问题，需要同步
     * 刷新界面调用postInvalidate()能在非UI线程刷新
     *
     * @param progress
     */
    public synchronized void setProgress(double progress) {
        if (progress < 0) {
            this.progress = progress;
            //throw new IllegalArgumentException("progress not less than 0");
        }
        if (progress > max) {
            progress = max;
        }
        if (progress <= max) {
            this.progress = progress;
            postInvalidate();
        }

    }


    public int getCricleColor() {
        return roundColor;
    }

    public void setCricleColor(int cricleColor) {
        this.roundColor = cricleColor;
    }

    public int getCricleProgressColor() {
        return roundProgressColor;
    }

    public void setCricleProgressColor(int cricleProgressColor) {
        this.roundProgressColor = cricleProgressColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public float getRoundWidth() {
        return roundWidth;
    }

    public void setRoundWidth(float roundWidth) {
        this.roundWidth = roundWidth;
    }


}
