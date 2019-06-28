package com.powerrich.office.oa.view;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.adapter.CommonAdapter;
import com.powerrich.office.oa.common.ViewHolder;
import com.powerrich.office.oa.tools.AutoUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MySpinner extends LinearLayout {
    private Context mContext;
    private MyAdpater adapter;
    private ListView popContentView;
    private AdapterView.OnItemClickListener onItemClickListener;
    private PopupWindow mDropView;
    private TextView textView;
    private ImageView img;

    public MySpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        // 加载布局
        LayoutInflater.from(context).inflate(R.layout.spinner_item, this);
        textView = (TextView) findViewById(R.id.tv_spinner);
        img = (ImageView) findViewById(R.id.img);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
//        Paint mPaint = new Paint();
//        mPaint.setTextSize(AutoUtils.getDisplayTextSize(50));
    }

    public void setHint(String hint) {
        textView.setHint(hint);
    }

    public TextView getTextView(){
        return textView;
    }

    public ImageView getImageView(){
        return img;
    }

    public void setList(String[] strings) {
        List<String> stringList = Arrays.asList(strings);
        setList(strings, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    public void setList(String[] strings, int width, int height) {
        List<String> stringList = Arrays.asList(strings);
        setList(stringList, width, height);
    }

    public void setList(List<String> list) {
        setList(list, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    public void setList(List<String> list, int width, int height) {
        if (adapter == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            LinearLayout container = (LinearLayout) inflater.inflate(R.layout.spinner_content, null);
            popContentView = (ListView) container.findViewById(R.id.spinner_content);
            if (width < -2 || height < -2) {
                mDropView = new PopupWindow(container, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            } else {
                mDropView = new PopupWindow(container, width, height);
            }
//            mDropView = new PopupWindow(container, this.getMeasuredWidth(), LinearLayout.LayoutParams.WRAP_CONTENT);
            mDropView.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            mDropView.setFocusable(true);
            mDropView.setOutsideTouchable(true);
            mDropView.setTouchable(true);
            container.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismissPop();
                }
            });
            this.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (mDropView.isShowing()) {
                        dismissPop();
                    } else {
                        showPop();
                    }
                }
            });
            mDropView.update();
            adapter = new MyAdpater(mContext, list,R.layout.spinner_drop_item);
            popContentView.setAdapter(adapter);
            popContentView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    if (mDropView.isShowing()) {
                        dismissPop();
                    }
                    onItemSelectedListener.SelectedListener(adapterView, view, i, l);
                }
            });
        }
    }

    public OnItemSelectedListener onItemSelectedListener;

    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
        this.onItemSelectedListener = listener;
    }

    public interface OnItemSelectedListener {
        void SelectedListener(AdapterView<?> adapterView, View view, int position, long l);
    }

    public void setText(CharSequence charSequence) {
        textView.setText(charSequence);
    }

    public void setText(int resid) {
        textView.setText(resid);
    }

    public String getText() {
        return textView.getText().toString();
    }

    public void dismissPop() {
        if (mDropView.isShowing()) {
            mDropView.dismiss();
        }
    }

    public void showPop() {
//        mDropView.showAsDropDown(textView);
        if (Build.VERSION.SDK_INT < 24) {
            mDropView.showAsDropDown(textView);
        } else {
            int[] location = new int[2];
            textView.getLocationOnScreen(location);
            mDropView.showAtLocation(textView, Gravity.NO_GRAVITY, location[0], location[1] + textView.getHeight());
        }
    }

    public class MyAdpater extends CommonAdapter {
        public MyAdpater(Context mContext, List mData, int mItemLayoutId) {
            super(mContext, mData, mItemLayoutId);
        }

        @Override
        public void convert(ViewHolder holder, Object item) {
            holder.setTextView(R.id.title,item.toString());
        }
    }

}
