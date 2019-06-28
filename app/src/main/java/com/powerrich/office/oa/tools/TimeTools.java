package com.powerrich.office.oa.tools;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author AlienChao
 * @date 2019/06/14 13:59
 */
public class TimeTools {
    /**
     * 将Date类型转换为日期字符串
     *
     * @param date Date对象
     * @param type 需要的日期格式(yyyy-MM-dd HH:mm:ss)
     * @return 按照需求格式的日期字符串
     */
    public static String formatDate(Date date, String type) {
        try {
            SimpleDateFormat df = new SimpleDateFormat(type);
            return df.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 判断2个时间大小
     * yyyy-MM-dd HH:mm 格式（自己可以修改成想要的时间格式）
     *
     * @param startTime  开始
     * @param endTime    结束
     * @return  开始的时间比结束大
     */
    public static boolean timeCompare(String startTime, String endTime) {
        boolean flag = false;
        //注意：传过来的时间格式必须要和这里填入的时间格式相同
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date1 = dateFormat.parse(startTime);//开始时间
            Date date2 = dateFormat.parse(endTime);//结束时间
            // 1 结束时间小于开始时间 2 开始时间与结束时间相同 3 结束时间大于开始时间
            if (date2.getTime() < date1.getTime()) {
                //结束时间小于开始时间
                flag = true;
            } else if (date2.getTime() == date1.getTime()) {
                //开始时间与结束时间相同
                flag = false;
            } else if (date2.getTime() > date1.getTime()) {
                //结束时间大于开始时间
                flag = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }


    /**
     * 二维码的有限期
     */
    public static   boolean  qrLimited () {
        String s = formatDate(new Date(), "yyyy-MM-dd");
        return timeCompare("2019-07-20", s);
    }


    public static void main(String[] args) {
        String s = formatDate(new Date(), "yyyy-MM-dd");
        System.out.println(s);
        boolean b = timeCompare("2019-06-13", s);
        System.out.println(b);
    }


}
