package com.powerrich.office.oa.tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.adapter.WardenMailListAdapter.WidgetClick;
import com.powerrich.office.oa.view.wheel.ScreenInfo;
import com.powerrich.office.oa.view.wheel.WheelTime;

import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * 日期时间工具类
 **/

public class DateUtils {

    public static final String DATE_UTC = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSS";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_FORMAT_CUSTOM = "yyyy/MM/dd";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm";
    public static final String TIME_FORMAT = "HH:mm:ss";
    private static AlertDialog alertDialog;

    public static final String DATE_M_D = "MM-dd";


    /**
     * 将时间戳转换为时间
     */
    public static String longToDate(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 日期转换
     *
     * @param date
     * @param format
     * @return
     */
    public static String formatDate(Date date, String format) {
        String result = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            result = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    public static String UtcToStr(String utcStr) {
        SimpleDateFormat sdf1 = new SimpleDateFormat(DATE_UTC);
        SimpleDateFormat sdf2 = new SimpleDateFormat(DATE_TIME_FORMAT);
        try {
            Date date = sdf1.parse(utcStr);//拿到Date对象
            String str = sdf2.format(date);//输出格式：2017-01-22 09:28:33
            return str;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String DateToMdStr(String utcStr) {
        SimpleDateFormat sdf1 = new SimpleDateFormat(DATE_TIME_FORMAT);
        SimpleDateFormat sdf2 = new SimpleDateFormat(DATE_M_D);
        try {
            Date date = sdf1.parse(utcStr);//拿到Date对象
            String str = sdf2.format(date);//输出格式：2017-01-22 09:28:33
            return str;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 字符串转换日期
     *
     * @param dateStr
     * @param formatStr
     * @return
     */
    public static Date parseDate(String dateStr, String formatStr) {
        Date result = null;
        try {
            if (dateStr.length() < formatStr.length()) {
                dateStr = "0" + dateStr;
            }
            SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
            result = sdf.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 判断是否为合法的日期时间字符串
     *
     * @param str_input
     * @param str_input
     * @return boolean;符合为true,不符合为false
     */
    public static boolean isDate(String str_input, String rDateFormat) {
        if (!BeanUtils.isEmpty(str_input)) {
            SimpleDateFormat formatter = new SimpleDateFormat(rDateFormat);
            formatter.setLenient(false);
            try {
                formatter.format(formatter.parse(str_input));
            } catch (Exception e) {
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * 返回当前时间的年月日，格式：yyyy-MM-dd
     *
     * @return yyyy-MM-dd
     */
    public static String getDateStr() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    /**
     * 返回指定时间的年月日，格式：yyyy-MM-dd
     *
     * @param date 指定时间
     * @return yyyy-MM-dd
     */
    public static String getDateStr(Date date) {
        if (date == null) {
            return "";
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
    }

    /**
     * 按照指定的文本格式返回当前时间
     *
     * @param format 默认格式：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getDateStr(String format) {
        if (format == null || "".equals(format)) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        return new SimpleDateFormat(format).format(new Date());
    }

    /**
     * 按照指定的文本格式返回指定时间
     *
     * @param date   指定时间
     * @param format 默认格式：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getDateStr(Date date, String format) {
        if (date == null) {
            return "";
        }
        if (format == null || "".equals(format)) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * 把String类型的日期yyyy-MM-dd HH:mm:ss 转换成需要的日期
     *
     * @param dateStr 指定时间
     * @param format  默认格式：yyyy-MM-dd
     * @return
     */
    public static String getDateStr(String dateStr, String format) {
        if (dateStr == null) {
            return "";
        }
        if (format == null || "".equals(format)) {
            format = "yyyy-MM-dd";
        }
        try {
            return new SimpleDateFormat(format).format(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(dateStr));
        } catch (ParseException e) {
            return dateStr;
        }
    }


    /**
     * 把String类型的日期转换成需要的日期
     *
     * @param dateStr 指定时间
     * @param format  默认格式：yyyy-MM-dd
     * @return
     */
    public static String getDateStr(String dateStr, String oldformat, String format) {
        if (dateStr == null) {
            return "";
        }
        if (format == null || "".equals(format)) {
            format = "yyyy-MM-dd";
        }
        try {
            return new SimpleDateFormat(format).format(new SimpleDateFormat(oldformat).parse(dateStr));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateStr;
    }

    /**
     * 返回当前时间的年月日时分秒，格式：yyyy-MM-dd HH:mm:ss
     *
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getDateTimeStr() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    /**
     * 弹出选择时间
     *
     * @param context
     * @param et      EditText
     * @param format  时间选择格式默认 yyyy-MM-dd
     */
    public static void showTimeSelect(final Context context, final EditText et, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        LayoutInflater inflater = LayoutInflater.from(context);
        final View timepickerview = inflater.inflate(R.layout.common_datetime, null);
        ScreenInfo screenInfo = new ScreenInfo(((Activity) context));
        final WheelTime wheelMain;
        if (DateUtils.DATE_TIME_FORMAT.equals(format) || DateUtils.DATETIME_FORMAT.equals(format)) {
            wheelMain = new WheelTime(timepickerview, true);
        } else {
            wheelMain = new WheelTime(timepickerview);
        }
        wheelMain.screenheight = screenInfo.getHeight();
        String time = et.getText().toString();
        Calendar calendar = Calendar.getInstance();
        if (DateUtils.isDate(time, format)) {
            try {
                calendar.setTime(dateFormat.parse(time));
            } catch (ParseException e) {
                calendar.setTime(new Date());
            }
        }
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        if (DateUtils.DATE_TIME_FORMAT.equals(format)) {
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int min = calendar.get(Calendar.MINUTE);
            wheelMain.initDateTimePicker(year, month, day, hour, min);
        } else {
            wheelMain.initDateTimePicker(year, month, day);
        }
        new AlertDialog.Builder(context).setTitle("选择时间")
                .setView(timepickerview)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        et.setText(wheelMain.getTime());
                        if (null != context && context instanceof WidgetClick) {
                            ((WidgetClick) context).click(0);
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
    }

    public static void showTimeSelectTextView(final Context context, final TextView et, String format) {
        if (alertDialog != null && alertDialog.isShowing()) return;//防止弹框连续出现
        DateFormat dateFormat = new SimpleDateFormat(format);
        LayoutInflater inflater = LayoutInflater.from(context);
        View timepickerview = inflater.inflate(R.layout.common_datetime, null);
        ScreenInfo screenInfo = new ScreenInfo(((Activity) context));
        final WheelTime wheelMain;
        if (DateUtils.DATE_TIME_FORMAT.equals(format) || DateUtils.DATETIME_FORMAT.equals(format)) {
            wheelMain = new WheelTime(timepickerview, true);
        } else {
            wheelMain = new WheelTime(timepickerview);
        }
        wheelMain.screenheight = screenInfo.getHeight();
        String time = et.getText().toString();
        Calendar calendar = Calendar.getInstance();
        if (DateUtils.isDate(time, format)) {
            try {
                calendar.setTime(dateFormat.parse(time));
            } catch (ParseException e) {
                calendar.setTime(new Date());
            }
        }
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        if (DateUtils.DATE_TIME_FORMAT.equals(format)) {
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int min = calendar.get(Calendar.MINUTE);
            wheelMain.initDateTimePicker(year, month, day, hour, min);
        } else {
            wheelMain.initDateTimePicker(year, month, day);
        }
        wheelMain.getConfirmButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et.setText(wheelMain.getTime());
                if (null != context && context instanceof WidgetClick) {
                    ((WidgetClick) context).click(0);
                }
                if (alertDialog != null) {
                    alertDialog.dismiss();
                }
            }
        });
        wheelMain.getCancelButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (alertDialog != null) {
                    alertDialog.dismiss();
                }
            }
        });
        View view = ((Activity) context).getLayoutInflater().inflate(R.layout.select_date_custom_title, null);
        alertDialog = new AlertDialog.Builder(context).setCustomTitle(view)
                .setView(timepickerview)
                .show();
    }

    /**
     * 获取今天往后一周的日期（年-月-日(星期几)）
     */
    public static List<String> get7date() {
        List<String> dates = new ArrayList<>();
        String week;
        try {
            URL url = new URL("http://www.baidu.com");//取得资源对象
            URLConnection uc = url.openConnection();//生成连接对象
            uc.connect(); //发出连接
            long ld = uc.getDate(); //取得网站日期时间
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(ld);
            String date = formatter.format(calendar.getTime());
            dates.add(date + "(今天)");
            for (int i = 0; i < 6; i++) {
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                date = formatter.format(calendar.getTime());
                week = getWeek(date);
                dates.add(date + "(" + week + ")");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dates;
    }

    /**
     * 根据当前日期获得是星期几
     *
     * @return
     */
    public static String getWeek(String time) {
        String Week = "";

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {

            c.setTime(format.parse(time));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            Week += "星期日";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
            Week += "星期一";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
            Week += "星期二";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
            Week += "星期三";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
            Week += "星期四";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
            Week += "星期五";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            Week += "星期六";
        }
        return Week;
    }

    /**
     * 返回指定时间的年月日，格式：yyyy/MM/dd
     *
     * @param date 指定时间
     * @return yyyy-MM-dd
     */
    public static String getCustomDateStr(Date date) {
        if (date == null) {
            return "";
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return new SimpleDateFormat("yyyy/MM/dd").format(calendar.getTime());
    }

    public static String getCustomDateYM(Date date) {
        if (date == null) {
            return "";
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return new SimpleDateFormat("yyyy/MM").format(calendar.getTime());
    }

    public static Calendar dataToCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }


    /**
     * 获取一个月前的日期
     *
     * @param date 传入的日期
     * @return
     */
    public static String getMonthAgo(Date date, int month, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, month);
        String monthAgo = simpleDateFormat.format(calendar.getTime());
        return monthAgo;
    }


    /**
     * 获取年的日期
     *
     * @param date 传入的日期
     * @return
     */
    public static String getYearAgo(Date date, int year, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, year);
        String monthAgo = simpleDateFormat.format(calendar.getTime());
        return monthAgo;
    }


    public static String getMonthAgo(Date date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String monthAgo = simpleDateFormat.format(calendar.getTime());
        return monthAgo;
    }

    public static final String YEAR = "year";
    public static final String MONTH = "month";
    public static final String HALF_YEAR = "halfYear";

    /**
     * 获取过去的某个日期
     *
     * @param tag
     * @return
     */
    public static String getLastDateFormat(String tag) {
        SimpleDateFormat format = new SimpleDateFormat(DateUtils.DATE_FORMAT_CUSTOM);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        if ("year".equals(tag)) {//过去一年
            calendar.add(Calendar.YEAR, -1);
        } else if ("month".equals(tag)) {//过去一月
            calendar.add(Calendar.MONTH, -1);
        } else if ("halfYear".equals(tag)) {
            calendar.add(Calendar.MONTH, -6);
        }
        Date d = calendar.getTime();
        String dateStr = format.format(d);
        return dateStr;
    }

}
