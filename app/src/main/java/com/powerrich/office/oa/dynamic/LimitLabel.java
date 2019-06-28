package com.powerrich.office.oa.dynamic;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

public class LimitLabel extends TextView {
	
	public LimitLabel(Context context) {
		this(context, null);
	}

	public LimitLabel(Context context, AttributeSet attrs) {
		super(context, attrs);
//		paint.setTextSize(textSize);
//		paint.setAntiAlias(true);
		setSingleLine(false);
	}
	
	public void createView(int maxWidth, String text){
		setText(text);
		setTextSize(ViewUtils.TEXT_UNIT, ViewUtils.TEXT_SIZE);
		setPadding(0, 0, 10, 0);
		setLayoutParams(new LayoutParams(maxWidth, LayoutParams.WRAP_CONTENT));
//		setWidth(maxWidth);
	}
	
//	@Override
//	protected void onDraw(Canvas canvas) {
//		int lineCount = 0;
//		content = getText().toString();
//		if (null == content) {
//			return ;
//		}
//		char[] textArray = content.toCharArray();
//		float width = 0;
//		float charWidth = 0;
//		for (int i = 0; i < textArray.length; i++) {
//			charWidth = paint.measureText(textArray, i, 1);
//			if (textArray[i] == '\n') {
//				lineCount++;
//				width = 0;
//				continue;
//			}
//			if (textShowWidth - width < charWidth) {
//				lineCount++;
//				width = 0;
//			}
//			canvas.drawText(textArray, i, 1, getPaddingLeft() + width,  
//                    (lineCount + 1) * textSize, paint);  
//			width += charWidth;  
//        }  
//        setHeight((lineCount + 1) * (int) textSize + 5);  
//	}

}
