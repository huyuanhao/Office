package com.powerrich.office.oa.tools;

import java.math.BigDecimal;

import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

/**
 * 文 件 名：CoodinateCovertor
 * 描    述：百度地图坐标和火星坐标转换
 * 作    者：chenhao
 * 时    间：2018/8/22
 * 版    权：v1.0
 */

public class CoodinateCovertor {


    private static double x_pi = 3.14159265358979324 * 3000.0 / 180.0;

    /**
     * 对double类型数据保留小数点后多少位
     *  高德地图转码返回的就是 小数点后6位，为了统一封装一下
     * @param digit 位数
     * @param in 输入
     * @return 保留小数位后的数
     */
    static double dataDigit(int digit,double in){
        return new BigDecimal(in).setScale(6,   BigDecimal.ROUND_HALF_UP).doubleValue();

    }

    /**
     * 将火星坐标转变成百度坐标
     * @param lngLat_gd 火星坐标（高德、腾讯地图坐标等）
     * @return 百度坐标
     */

    public static LngLat bd_encrypt(LngLat lngLat_gd) {
        double x = lngLat_gd.getLongitude(), y = lngLat_gd.getLantitude();
        double z = sqrt(x * x + y * y) + 0.00002 * sin(y * x_pi);
        double theta = atan2(y, x) + 0.000003 * cos(x *  x_pi);
        return new LngLat(dataDigit(6,z * cos(theta) + 0.0065),dataDigit(6,z * sin(theta) + 0.006));

    }
    /**
     * 将百度坐标转变成火星坐标
     * @param lngLat_bd 百度坐标（百度地图坐标）
     * @return 火星坐标(高德、腾讯地图等)
     */
    public static LngLat bd_decrypt(LngLat lngLat_bd) {
        double x = lngLat_bd.getLongitude() - 0.0065, y = lngLat_bd.getLantitude() - 0.006;
        double z = sqrt(x * x + y * y) - 0.00002 * sin(y * x_pi);
        double theta = atan2(y, x) - 0.000003 * cos(x * x_pi);
        return new LngLat( dataDigit(6,z * cos(theta)),dataDigit(6,z * sin(theta)));

    }

    public static class LngLat {
        private double longitude;//经度
        private double lantitude;//维度

        public LngLat() {
        }

        public LngLat(double longitude, double lantitude) {
            this.longitude = longitude;
            this.lantitude = lantitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public double getLantitude() {
            return lantitude;
        }

        public void setLantitude(double lantitude) {
            this.lantitude = lantitude;
        }

        @Override
        public String toString() {
            return "LngLat{" +
                    "longitude=" + longitude +
                    ", lantitude=" + lantitude +
                    '}';
        }
    }

}
