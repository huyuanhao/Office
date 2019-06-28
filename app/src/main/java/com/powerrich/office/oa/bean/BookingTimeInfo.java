package com.powerrich.office.oa.bean;

import java.util.List;

/**
 * 文 件 名：BookingTimeInfo
 * 描    述：
 * 作    者：chenhao
 * 时    间：2018/1/12
 * 版    权：v1.0
 */

public class BookingTimeInfo {

    /**
     * DATA : {"ITEMID":"201806242122116539786139043","YYSJ":[{"count":"0","time":"2018-07-27 10:40-12:00"},{"count":"0","time":"2018-07-27 14:40-15:00"},{"count":"0","time":"2018-07-27 15:30-17:30"}]}
     * code : 0
     * message : 操作成功！
     */

    private DATABean DATA;
    private String code;
    private String message;

    public DATABean getDATA() {
        return DATA;
    }

    public void setDATA(DATABean DATA) {
        this.DATA = DATA;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DATABean {
        /**
         * ITEMID : 201806242122116539786139043
         * YYSJ : [{"count":"0","time":"2018-07-27 10:40-12:00"},{"count":"0","time":"2018-07-27 14:40-15:00"},{"count":"0","time":"2018-07-27 15:30-17:30"}]
         */

        private String ITEMID;
        private List<YYSJBean> YYSJ;

        public String getITEMID() {
            return ITEMID;
        }

        public void setITEMID(String ITEMID) {
            this.ITEMID = ITEMID;
        }

        public List<YYSJBean> getYYSJ() {
            return YYSJ;
        }

        public void setYYSJ(List<YYSJBean> YYSJ) {
            this.YYSJ = YYSJ;
        }

        public static class YYSJBean {
            /**
             * count : 0
             * time : 2018-07-27 10:40-12:00
             */

            private String count;
            private String time;

            public String getCount() {
                return count;
            }

            public void setCount(String count) {
                this.count = count;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }
    }
}
