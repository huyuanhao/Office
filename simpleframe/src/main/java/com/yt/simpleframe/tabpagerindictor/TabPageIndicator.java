package com.yt.simpleframe.tabpagerindictor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.CheckedTextView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.yt.simpleframe.R;

import java.util.Locale;


public class TabPageIndicator extends HorizontalScrollView {
    private Context mContext;
    private int[] widths;

    private static final int[] ATTRS = new int[]{
            android.R.attr.textSize,
            android.R.attr.textColor
    };
    private int mTabWidth;

    private LinearLayout.LayoutParams wrapTabLayoutParams;
    private LinearLayout.LayoutParams weightTabLayoutParams;

    private final PageListener pageListener = new PageListener();
    public OnPageChangeListener delegatePageListener;

    private LinearLayout tabsContainer;
    private ViewPager pager;

    private int tabCount;


    private int currentPosition = 0;
    private float currentPositionOffset = 0f;

    private Paint rectPaint;
    private Paint dividerPaint;

    private boolean checkedTabWidths = false;

    private int indicatorColor = Color.parseColor("#e0f17c07");//指示线的颜色  #0075E7
    private int underlineColor = 0xFFdcdcdc;// 默认指示线的颜色
    private int dividerColor = 0x00000000;// 分割线的颜色

    private boolean isSameLine;// 设置导航先是否跟文字长度一至
    private boolean textAllCaps;

    private boolean isExpand;// 是否可扩展
    private boolean isExpandSameLine;// 可扩展并且导标一致

    private int scrollOffset = 10;// 当显示多个tab时，底部导航线距离左侧的位移,默认是10，请不要太小
    private int indicatorHeight = 2;//指示线的高度
    private int underlineHeight = 1;// 默认指示线的高度
    private int dividerPadding = 0;//竖线到下面的距离:可以通过设置indicator的高度设置下面横线和文字的距离
    private int tabPadding;// 标题的间距
    private int dividerWidth = 0;// 分割线的宽度

    private int tabBgSelectColor = R.color.colorPrimary;
    private int getTabBgUnselectColor = R.color.white;

    private int indicatorPaddingLeft = 0;// 距离左边的距离
    private int indicatorPaddingRight = 0;// 距离右边的距离

    private int tabTextSize = 18;//标题的字体大小
    private int tabunSelectTextColor = Color.parseColor("#ffffff");// 标题未被选中时字体颜色
    private int tabTextColorSelected = Color.parseColor("#ffffff");// 标题被选中时字体颜色

    private int lastScrollX = 0;

    private int tabBackgroundResId;
    private int drawableLeft = 0;


    private Locale locale;

    /**
     * 定义6种模式
     */
    public enum IndicatorMode {
        // 给枚举传入自定义的int值
        MODE_WEIGHT_NOEXPAND_SAME(0), // 平均分配，导航线跟标题相等
        MODE_WEIGHT_NOEXPAND_NOSAME(1), // 平均分配，导航线跟标题不相等
        MODE_NOWEIGHT_NOEXPAND_SAME(2),// 非平均分配，非扩展，导标相等
        MODE_NOWEIGHT_NOEXPAND_NOSAME(3),// 非平均分配，非扩展，导标不相等
        MODE_NOWEIGHT_EXPAND_SAME(4),// 可扩展，导标相等
        MODE_NOWEIGHT_EXPAND_NOSAME(5);// 不可扩展，导标不相等
        private int value;

        IndicatorMode(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    private IndicatorMode currentIndicatorMode = IndicatorMode.MODE_WEIGHT_NOEXPAND_SAME;


    public TabPageIndicator(Context context) {
        this(context, null);
        this.mContext = context;
    }

    public TabPageIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        this.mContext = context;
    }

    public TabPageIndicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        setFillViewport(true);
        setWillNotDraw(false);

        tabsContainer = new LinearLayout(context);
        tabsContainer.setOrientation(LinearLayout.HORIZONTAL);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        tabsContainer.setLayoutParams(layoutParams);
        addView(tabsContainer);

        DisplayMetrics dm = getResources().getDisplayMetrics();

        scrollOffset = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, scrollOffset, dm);
        indicatorHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, indicatorHeight, dm);
        underlineHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, underlineHeight, dm);
        dividerPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dividerPadding, dm);
        tabPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, tabPadding, dm);
        dividerWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dividerWidth, dm);
        tabTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, tabTextSize, dm);
        indicatorPaddingLeft = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, indicatorPaddingLeft, dm);

        // get system attrs (android:textSize and android:textColor)

        TypedArray a = context.obtainStyledAttributes(attrs, ATTRS);

        tabTextSize = a.getDimensionPixelSize(0, tabTextSize);
        a.recycle();
        // get custom attrs

        a = context.obtainStyledAttributes(attrs, R.styleable.PagerSlidingTab);
        tabunSelectTextColor = a.getColor(R.styleable.PagerSlidingTab_unsetectTextColor, tabunSelectTextColor);
        indicatorColor = a.getColor(R.styleable.PagerSlidingTab_pageindicatorColor, indicatorColor);
        tabTextColorSelected = a.getColor(R.styleable.PagerSlidingTab_setectTextColor, tabTextColorSelected);
        underlineColor = a.getColor(R.styleable.PagerSlidingTab_underlineColor, underlineColor);
        dividerColor = a.getColor(R.styleable.PagerSlidingTab_dividerColor, dividerColor);
        indicatorHeight = a.getDimensionPixelSize(R.styleable.PagerSlidingTab_indicatorHeight, indicatorHeight);
        tabTextSize = a.getDimensionPixelSize(R.styleable.PagerSlidingTab_tabTextSize, tabTextSize);
        indicatorPaddingLeft = a.getDimensionPixelSize(R.styleable.PagerSlidingTab_indicatorPaddingLeft, indicatorPaddingLeft);
        underlineHeight = a.getDimensionPixelSize(R.styleable.PagerSlidingTab_underlineHeight, underlineHeight);
        dividerPadding = a.getDimensionPixelSize(R.styleable.PagerSlidingTab_pst_dividerPadding, dividerPadding);
        tabPadding = a.getDimensionPixelSize(R.styleable.PagerSlidingTab_tabPaddingLeftRight, tabPadding);
        tabBackgroundResId = a.getResourceId(R.styleable.PagerSlidingTab_tabBackgrounds, tabBackgroundResId);
        drawableLeft = a.getResourceId(R.styleable.PagerSlidingTab_tabDrawableLeft, drawableLeft);
//        isSameLine = a.getBoolean(R.styleable.PagerSlidingTab_sameLine, isSameLine);
        scrollOffset = a.getDimensionPixelSize(R.styleable.PagerSlidingTab_scrollOffset, scrollOffset);
        textAllCaps = a.getBoolean(R.styleable.PagerSlidingTab_pst_textAllCaps, textAllCaps);

        a.recycle();

        rectPaint = new Paint();
        rectPaint.setAntiAlias(true);
        rectPaint.setStyle(Style.FILL);

        dividerPaint = new Paint();
        dividerPaint.setAntiAlias(true);
        dividerPaint.setStrokeWidth(dividerWidth);

        wrapTabLayoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        weightTabLayoutParams = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1.0f);

        if (locale == null) {
            locale = getResources().getConfiguration().locale;
        }
    }


    // 根据不同的Mode设置不同的展现效果
    public void setIndicatorMode(IndicatorMode indicatorMode) {
        switch (indicatorMode) {
            case MODE_WEIGHT_NOEXPAND_SAME:
                isExpand = false;
                isSameLine = true;
                break;
            case MODE_WEIGHT_NOEXPAND_NOSAME:
                isExpand = false;
                isSameLine = false;
                break;
            case MODE_NOWEIGHT_NOEXPAND_SAME:
                isExpand = false;
                isSameLine = true;
                isExpandSameLine = true;
                tabPadding = dip2px(10);
                break;
            case MODE_NOWEIGHT_NOEXPAND_NOSAME:
                isExpand = false;
                isSameLine = true;
                isExpandSameLine = true;
                tabPadding = dip2px(10);
                break;
            case MODE_NOWEIGHT_EXPAND_SAME:
                isExpand = true;
                isExpandSameLine = true;
                tabPadding = dip2px(10);
                break;
            case MODE_NOWEIGHT_EXPAND_NOSAME:
                isExpand = true;
                isExpandSameLine = false;
                tabPadding = dip2px(10);
                break;
        }
        this.currentIndicatorMode = indicatorMode;
        notifyDataSetChanged();
    }

    public void setViewPager(ViewPager pager) {
        this.pager = pager;

        if (pager.getAdapter() == null) {
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        }

        pager.addOnPageChangeListener(pageListener);

        notifyDataSetChanged();
    }

    public void setOnPageChangeListener(OnPageChangeListener listener) {
        this.delegatePageListener = listener;
    }

    public void notifyDataSetChanged() {

        tabsContainer.removeAllViews();
        PagerAdapter adapter = pager.getAdapter();
        tabCount = pager.getAdapter().getCount();
        IconPagerAdapter iconAdapter = null;
        if (adapter instanceof IconPagerAdapter) {
            iconAdapter = (IconPagerAdapter) adapter;
        }
        final int count = adapter.getCount();
        for (int i = 0; i < count; i++) {
            CharSequence title = adapter.getPageTitle(i);
            int iconResId = 0;
            if (iconAdapter != null) {
                iconResId = iconAdapter.getIconResId(i);
            }
            addTextTab(i, title.toString(), iconResId);
        }

        updateTabStyles();

        checkedTabWidths = false;

        getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @SuppressLint("NewApi")
            @Override
            public void onGlobalLayout() {

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                currentPosition = pager.getCurrentItem();
                scrollToChild(currentPosition, 0);
            }
        });

    }

    private void addTextTab(final int position, String title, int iconResId) {
        TabView tabView = new TabView(getContext());
        tabView.setText(title);
        tabView.setFocusable(true);
        tabView.mIndex = position;
        if (iconResId > 0) {
            tabView.setIcon(iconResId);
        }
        tabView.setGravity(Gravity.CENTER);
        tabView.setSingleLine();
        tabView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.onClick(position);
                }
            }
        });

        if (isExpand && !isExpandSameLine) {
            tabView.setPadding(tabPadding, 0, tabPadding, 0);
        } else {
            wrapTabLayoutParams.setMargins(tabPadding, 0, tabPadding, 0);
            weightTabLayoutParams.setMargins(tabPadding, 0, tabPadding, 0);
//            wrapTabLayoutParams.setMargins(0, 0, 0, 0);
//            weightTabLayoutParams.setMargins(0, 0, 0, 0);
        }
        tabsContainer.addView(tabView, position, isSameLine ? wrapTabLayoutParams : weightTabLayoutParams);
    }

    @SuppressLint("ResourceAsColor")
    private void updateTabStyles() {
        widths = new int[tabCount];
        for (int i = 0; i < tabCount; i++) {
            View v = tabsContainer.getChildAt(i);
            if (v instanceof CheckedTextView) {
                CheckedTextView tab = (CheckedTextView) v;
                tab.setTextSize(TypedValue.COMPLEX_UNIT_PX, tabTextSize);
                tab.setTextColor(i == 0 ? tabTextColorSelected : tabunSelectTextColor);
                //设置背景颜色
//                tab.setBackgroundResource(i == 0 ? tabBgSelectColor : getTabBgUnselectColor);
                tab.setChecked(i == 0);
                //大小写切换
                if (textAllCaps) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                        tab.setAllCaps(true);
                    } else {
                        tab.setText(tab.getText().toString().toUpperCase(locale));
                    }
                }
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (isExpand) return;
        final int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
        final boolean lockedExpanded = widthMode == View.MeasureSpec.EXACTLY;

        final int childCount = tabsContainer.getChildCount();
        if (childCount > 1 && (widthMode == MeasureSpec.EXACTLY || widthMode == MeasureSpec.AT_MOST)) {
            mTabWidth = MeasureSpec.getSize(widthMeasureSpec) / childCount;
        } else {
            mTabWidth = -1;
        }
        /**
         * 一下这些代码是为了在设置weight的情况下，导航线也能跟标题长度一致
         */
        int myWidth = getMeasuredWidth();// 其实就是屏幕的宽度
        int childWidth = 0;
        for (int i = 0; i < tabCount; i++) {
            childWidth += tabsContainer.getChildAt(i).getMeasuredWidth();
            if (widths[i] == 0) {
                widths[i] = tabsContainer.getChildAt(i).getMeasuredWidth();
            }
        }
        if (currentIndicatorMode == IndicatorMode.MODE_NOWEIGHT_NOEXPAND_SAME) {
            setIndicatorPaddingRight(myWidth - childWidth - tabPadding * 2 * tabCount);
            tabsContainer.setPadding(indicatorPaddingLeft, 0, indicatorPaddingRight, 0);
        }
        if (currentIndicatorMode == IndicatorMode.MODE_NOWEIGHT_NOEXPAND_NOSAME) {
            setIndicatorPaddingRight(myWidth - childWidth - tabPadding * 2 * tabCount);
            tabsContainer.setPadding(indicatorPaddingLeft, 0, indicatorPaddingRight, 0);
        }
        if (!checkedTabWidths && childWidth > 0 && myWidth > 0) {
            // tab标题的长度为超过屏幕的宽度
            if (childWidth <= myWidth) {
                for (int i = 0; i < tabCount; i++) {
                    tabsContainer.getChildAt(i).setLayoutParams(weightTabLayoutParams);
                }
            } else {
                for (int i = 0; i < tabCount; i++) {
                    tabsContainer.getChildAt(i).setLayoutParams(wrapTabLayoutParams);
                }
            }

            checkedTabWidths = true;
        }
    }

    private void scrollToChild(int position, int offset) {
        if (tabCount == 0 || offset == 0) {
            return;
        }

        int newScrollX = tabsContainer.getChildAt(position).getLeft() + offset;

        if (position > 0 || offset > 0) {
            newScrollX -= scrollOffset;
        }

        if (newScrollX != lastScrollX) {
            lastScrollX = newScrollX;
            scrollTo(newScrollX, 0);
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isInEditMode() || tabCount == 0) {
            return;
        }

        final int height = getHeight();

        /**
         * draw底部的导航线
         */

        rectPaint.setColor(indicatorColor);
        // 默认在Tab标题的下面
        View currentTab = tabsContainer.getChildAt(currentPosition);
        float currentOffWid;
        if (isExpand) {
            currentOffWid = 0;
        } else {
            currentOffWid = (currentTab.getWidth() - widths[currentPosition]) / 2;
        }

        float lineLeft = currentTab.getLeft() + currentOffWid;
        float lineRight = currentTab.getRight() - currentOffWid;

        // if there is an offset, start interpolating left and right coordinates between current and next tab
        if (currentPositionOffset > 0f && currentPosition < tabCount - 1) {
            View nextTab = tabsContainer.getChildAt(currentPosition + 1);
            float nextOffWid;
            if (isExpand) {
                nextOffWid = 0;
            } else {
                nextOffWid = (nextTab.getWidth() - widths[currentPosition + 1]) / 2;
            }
            final float nextTabLeft = nextTab.getLeft() + nextOffWid;
            final float nextTabRight = nextTab.getRight() - nextOffWid;

            lineLeft = (currentPositionOffset * nextTabLeft + (1f - currentPositionOffset) * lineLeft);
            lineRight = (currentPositionOffset * nextTabRight + (1f - currentPositionOffset) * lineRight);
        }
        if (currentIndicatorMode == IndicatorMode.MODE_NOWEIGHT_NOEXPAND_NOSAME) {
            canvas.drawRect(lineLeft - tabPadding, height - indicatorHeight, lineRight + tabPadding, height, rectPaint);
        } else {
            canvas.drawRect(lineLeft, height - indicatorHeight, lineRight, height, rectPaint);
        }
//        if(isExpand&&!isExpandSameLine){
//            canvas.drawRect(lineLeft, height - indicatorHeight, lineRight, height, rectPaint);
//        }else{
//            canvas.drawRect(lineLeft, height - indicatorHeight, lineRight, height, rectPaint);
//        }
        //线在顶端的情况
//		canvas.drawRect(lineLeft, 0, lineRight, indicatorHeight, rectPaint);


        //底部线条
        rectPaint.setColor(underlineColor);
        canvas.drawRect(0, height - underlineHeight, tabsContainer.getWidth(), height, rectPaint);

        /**
         * draw divider：分割线
         */
        dividerPaint.setColor(dividerColor);
        for (int i = 0; i < tabCount - 1; i++) {
            View tab = tabsContainer.getChildAt(i);
            if (!isExpandSameLine) {
                canvas.drawLine(tab.getRight(), dividerPadding, tab.getRight(), height - dividerPadding, dividerPaint);
            } else {
                canvas.drawLine(tab.getRight() + tabPadding, dividerPadding, tab.getRight() + tabPadding, height -
                        dividerPadding, dividerPaint);
            }
        }
    }

    private class PageListener implements OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            currentPosition = position;
            currentPositionOffset = positionOffset;
            scrollToChild(position, (int) (positionOffset * tabsContainer.getChildAt(position).getWidth()));

            invalidate();

            if (delegatePageListener != null) {
                delegatePageListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                scrollToChild(pager.getCurrentItem(), 0);
            }

            if (delegatePageListener != null) {
                delegatePageListener.onPageScrollStateChanged(state);
            }
        }

        @SuppressLint("ResourceAsColor")
        @Override
        public void onPageSelected(int position) {
            if (delegatePageListener != null) {
                delegatePageListener.onPageSelected(position);
            }

            //使当前item高亮
            for (int i = 0; i < tabCount; i++) {
                View v = tabsContainer.getChildAt(i);
                if (v instanceof CheckedTextView) {
                    CheckedTextView textView = (CheckedTextView) v;
                    textView.setTextColor(i == pager.getCurrentItem() ? tabTextColorSelected : tabunSelectTextColor);

//                    textView.setBackgroundResource(i == pager.getCurrentItem() ? tabBgSelectColor : getTabBgUnselectColor);
                    textView.setChecked(i == pager.getCurrentItem());
                }
            }
        }
    }


    @Override
    public void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        currentPosition = savedState.currentPosition;
        requestLayout();
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.currentPosition = currentPosition;
        return savedState;
    }

    static class SavedState extends BaseSavedState {
        int currentPosition;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            currentPosition = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(currentPosition);
        }

        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }

    /**
     * dip转化成px
     */
    public int dip2px(float dpValue) {
        float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    OnTabClickListener clickListener;

    public void setOnTabClickListener(OnTabClickListener clickListener) {
        this.clickListener = clickListener;

    }

    /**
     * 点击事件
     */
    public interface OnTabClickListener {
        void onClick(int position);
    }

    private class TabView extends android.support.v7.widget.AppCompatCheckedTextView {
        private int mIndex;
        private int iconWidth;
        private int iconHeight;

        public TabView(Context context) {
            this(context, null, R.attr.tabView);
        }

        public TabView(Context context, AttributeSet attr, int defStyle) {
            super(context, attr, defStyle);
            TypedArray a = context.obtainStyledAttributes(attr, R.styleable.TabView, defStyle, 0);
            iconWidth = a.getDimensionPixelSize(R.styleable.TabView_iconWidth, 0);
            iconHeight = a.getDimensionPixelSize(R.styleable.TabView_iconHeight, 0);
            a.recycle();
        }

        @Override
        public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);

            // Re-measure if we went beyond our maximum size.
//            if (mTabWidth > 0) {
//                super.onMeasure(MeasureSpec.makeMeasureSpec(mTabWidth, MeasureSpec.EXACTLY),
//                        heightMeasureSpec);
//            }
        }

        public void setIcon(int resId) {
            if (resId > 0) {
                final Resources resources = getContext().getResources();
                Drawable icon = resources.getDrawable(resId);
                int width = iconWidth == 0 ? icon.getIntrinsicWidth() : iconWidth;
                int height = iconHeight == 0 ? icon.getIntrinsicHeight() : iconHeight;
                icon.setBounds(0, 0, width, height);
                setCompoundDrawables(icon, null, null, null);
            }
        }

        public int getIndex() {
            return mIndex;
        }
    }


    public void setIndicatorColor(int indicatorColor) {
        this.indicatorColor = indicatorColor;
        invalidate();
    }

    public void setIndicatorColorResource(int resId) {
        this.indicatorColor = getResources().getColor(resId);
        invalidate();
    }

    public int getIndicatorColor() {
        return this.indicatorColor;
    }

    public void setIndicatorHeight(int indicatorLineHeightPx) {
        this.indicatorHeight = indicatorLineHeightPx;
        invalidate();
    }

    public int getIndicatorHeight() {
        return indicatorHeight;
    }

    public void setUnderlineColor(int underlineColor) {
        this.underlineColor = underlineColor;
        invalidate();
    }

    public void setUnderlineColorResource(int resId) {
        this.underlineColor = getResources().getColor(resId);
        invalidate();
    }

    public int getUnderlineColor() {
        return underlineColor;
    }

    public void setDividerColor(int dividerColor) {
        this.dividerColor = dividerColor;
        invalidate();
    }

    public void setDividerColorResource(int resId) {
        this.dividerColor = getResources().getColor(resId);
        invalidate();
    }

    public int getDividerColor() {
        return dividerColor;
    }

    public void setUnderlineHeight(int underlineHeightPx) {
        this.underlineHeight = underlineHeightPx;
        invalidate();
    }

    public int getUnderlineHeight() {
        return underlineHeight;
    }

    public void setDividerPadding(int dividerPaddingPx) {
        this.dividerPadding = dividerPaddingPx;
        invalidate();
    }

    public int getDividerPadding() {
        return dividerPadding;
    }

    public void setScrollOffset(int scrollOffsetPx) {
        this.scrollOffset = scrollOffsetPx;
        invalidate();
    }

    public int getScrollOffset() {
        return scrollOffset;
    }

    public void setSameLine(boolean sameLine) {
        this.isSameLine = sameLine;
        requestLayout();
    }

    public boolean getSameLine() {
        return isSameLine;
    }

    public boolean isTextAllCaps() {
        return textAllCaps;
    }

    public void setAllCaps(boolean textAllCaps) {
        this.textAllCaps = textAllCaps;
    }

    public void setTextSize(int textSizePx) {
        this.tabTextSize = textSizePx;
        updateTabStyles();
    }

    public int getTextSize() {
        return tabTextSize;
    }

    public void setTextColor(int textColor) {
        this.tabunSelectTextColor = textColor;
        updateTabStyles();
    }

    public void setTextColorSelected(int textColorSelected) {
        this.tabTextColorSelected = textColorSelected;
        updateTabStyles();
    }

    public void setTextColorResource(int resId) {
        this.tabunSelectTextColor = getResources().getColor(resId);
        updateTabStyles();
    }

    public int getTextColor() {
        return tabunSelectTextColor;
    }

    public void setTabBackground(int resId) {
        this.tabBackgroundResId = resId;
        updateTabStyles();
    }

    public int getTabBackground() {
        return tabBackgroundResId;
    }

    public void setTabPaddingLeftRight(int paddingPx) {
        this.tabPadding = paddingPx;
        updateTabStyles();
    }

    public int getTabPaddingLeftRight() {
        return tabPadding;
    }

    public int getIndicatorPaddingLeft() {
        return indicatorPaddingLeft;
    }

    public void setIndicatorPaddingLeft(int indicatorPaddingLeft) {
        this.indicatorPaddingLeft = indicatorPaddingLeft;
    }

    public int getIndicatorPaddingRight() {
        return indicatorPaddingRight;
    }

    public void setIndicatorPaddingRight(int indicatorPaddingRight) {
        this.indicatorPaddingRight = indicatorPaddingRight;
    }

}
