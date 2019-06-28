package com.powerrich.office.oa.dynamic;

import java.util.List;

import android.content.Context;
import android.graphics.Paint;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class ViewUtils {

	public static final int TEXT_SIZE = 12;
	public static final int TEXT_UNIT = TypedValue.COMPLEX_UNIT_SP;
	public static final int WIDTH = 10;

	public static int[] getViewSize(View view) {
		int width = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		int height = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		view.measure(width, height);
		int fHeight = view.getMeasuredHeight();
		int fWidth = view.getMeasuredWidth();
		return new int[] { fWidth, fHeight };
	}

	public static int getViewsWidth(List<View> views) {
		int sum = 0;
		if (null != views) {
			int width = View.MeasureSpec.makeMeasureSpec(0,
					View.MeasureSpec.UNSPECIFIED);
			int height = View.MeasureSpec.makeMeasureSpec(0,
					View.MeasureSpec.UNSPECIFIED);
			for (View view : views) {
				view.measure(width, height);
				// int fHeight=view.getMeasuredHeight();
				int fWidth = view.getMeasuredWidth();
				sum += fWidth;
			}
		}
		return sum;
	}

	public static int getScreenWidth(Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		int width = wm.getDefaultDisplay().getWidth();
		int height = wm.getDefaultDisplay().getHeight();
		return width;
	}

	public static float getCharacterWidth(String text, TextView view) {
		if (null == text || "".equals(text))
			return 0;
//		float width = 0;
		Paint paint = new Paint();
		paint.setTextSize(view.getTextSize());
		float text_width = paint.measureText(text) + WIDTH;// 得到总体长度
//		width = text_width / text.length();// 每一个字符的长度
//		return width * view.getScaleX();
		return text_width;
	}
}
