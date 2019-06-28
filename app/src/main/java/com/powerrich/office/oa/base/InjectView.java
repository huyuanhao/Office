package com.powerrich.office.oa.base;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 使用标注来代替findViewById()
 * 使用方法：
 * //标注TextView的id
 * @InjectView(id=R.id.tv_img)
 * private TextView mText;
 * */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectView
{
	/** View的ID */
    int id() default -1;
}
