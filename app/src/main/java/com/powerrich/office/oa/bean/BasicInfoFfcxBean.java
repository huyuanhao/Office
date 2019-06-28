package com.powerrich.office.oa.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/11/14
 * 版权：
 */

public class BasicInfoFfcxBean implements Serializable{




    private DATABeanX DATA;
    private String code;
    private String message;

    public DATABeanX getDATA() {
        return DATA;
    }

    public void setDATA(DATABeanX DATA) {
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

    public static class DATABeanX {


        private int CURRENTROWS;
        private List<DATABean> DATA;

        public int getCURRENTROWS() {
            return CURRENTROWS;
        }

        public void setCURRENTROWS(int CURRENTROWS) {
            this.CURRENTROWS = CURRENTROWS;
        }

        public List<DATABean> getDATA() {
            return DATA;
        }

        public void setDATA(List<DATABean> DATA) {
            this.DATA = DATA;
        }

        public static class DATABean {


            private String aae003;
            private String aaa027;
            private String aaa036;
            private String aae019;
            private String aae140;

            public String getAae003() {
                return aae003;
            }

            public void setAae003(String aae003) {
                this.aae003 = aae003;
            }

            public String getAaa027() {
                return aaa027;
            }

            public void setAaa027(String aaa027) {
                this.aaa027 = aaa027;
            }

            public String getAaa036() {
                return aaa036;
            }

            public void setAaa036(String aaa036) {
                this.aaa036 = aaa036;
            }

            public String getAae019() {
                return aae019;
            }

            public void setAae019(String aae019) {
                this.aae019 = aae019;
            }

            public String getAae140() {
                return aae140;
            }

            public void setAae140(String aae140) {
                this.aae140 = aae140;
            }
        }
    }
}
