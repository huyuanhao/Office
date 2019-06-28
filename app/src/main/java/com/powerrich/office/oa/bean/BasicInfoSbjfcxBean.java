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

public class BasicInfoSbjfcxBean implements Serializable{


    /**
     * DATA : {"CURRENTROWS":15,"DATA":[{"aaa027":"贵溪市","aaa115":"正常应缴","aae003":"2014","aae022":"100元","aae140":"新型农村社会养老保险",
     * "aae341":"个人缴费","aaz289":"100元档次(个人缴费100省补18县补12)"},{"aaa027":"贵溪市","aaa115":"正常应缴","aae003":"2014","aae022":"18元",
     * "aae140":"新型农村社会养老保险","aae341":"省级财政补贴","aaz289":"100元档次(个人缴费100省补18县补12)"},{"aaa027":"贵溪市","aaa115":"正常应缴",
     * "aae003":"2014","aae022":"12元","aae140":"新型农村社会养老保险","aae341":"县级财政补贴","aaz289":"100元档次(个人缴费100省补18县补12)"},
     * {"aaa027":"贵溪市","aaa115":"正常应缴","aae003":"2015","aae022":"100元","aae140":"城乡居民社会养老保险","aae341":"个人缴费","aaz289":"100元档次
     * (个人缴费100省补18市补12)"},{"aaa027":"贵溪市","aaa115":"正常应缴","aae003":"2015","aae022":"18元","aae140":"城乡居民社会养老保险",
     * "aae341":"省级财政补贴","aaz289":"100元档次(个人缴费100省补18市补12)"},{"aaa027":"贵溪市","aaa115":"正常应缴","aae003":"2015","aae022":"12元",
     * "aae140":"城乡居民社会养老保险","aae341":"县级财政补贴","aaz289":"100元档次(个人缴费100省补18市补12)"},{"aaa027":"贵溪市","aaa115":"正常应缴",
     * "aae003":"2016","aae022":"100元","aae140":"城乡居民社会养老保险","aae341":"个人缴费","aaz289":"100元档次(个人缴费100省补18市补12)"},
     * {"aaa027":"贵溪市","aaa115":"正常应缴","aae003":"2016","aae022":"18元","aae140":"城乡居民社会养老保险","aae341":"省级财政补贴","aaz289":"100元档次
     * (个人缴费100省补18市补12)"},{"aaa027":"贵溪市","aaa115":"正常应缴","aae003":"2016","aae022":"12元","aae140":"城乡居民社会养老保险",
     * "aae341":"县级财政补贴","aaz289":"100元档次(个人缴费100省补18市补12)"},{"aaa027":"贵溪市","aaa115":"正常应缴","aae003":"2017","aae022":"100元",
     * "aae140":"城乡居民社会养老保险","aae341":"个人缴费","aaz289":"100元档次(个人缴费100省补18市补12)"},{"aaa027":"贵溪市","aaa115":"正常应缴",
     * "aae003":"2017","aae022":"18元","aae140":"城乡居民社会养老保险","aae341":"省级财政补贴","aaz289":"100元档次(个人缴费100省补18市补12)"},
     * {"aaa027":"贵溪市","aaa115":"正常应缴","aae003":"2017","aae022":"12元","aae140":"城乡居民社会养老保险","aae341":"县级财政补贴","aaz289":"100元档次
     * (个人缴费100省补18市补12)"},{"aaa027":"贵溪市","aaa115":"正常应缴","aae003":"2018","aae022":"300元","aae140":"城乡居民社会养老保险",
     * "aae341":"个人缴费","aaz289":"300元档次(个人缴费300省补24县补16)"},{"aaa027":"贵溪市","aaa115":"正常应缴","aae003":"2018","aae022":"24元",
     * "aae140":"城乡居民社会养老保险","aae341":"省级财政补贴","aaz289":"300元档次(个人缴费300省补24县补16)"},{"aaa027":"贵溪市","aaa115":"正常应缴",
     * "aae003":"2018","aae022":"16元","aae140":"城乡居民社会养老保险","aae341":"县级财政补贴","aaz289":"300元档次(个人缴费300省补24县补16)"}]}
     * code : 0
     * message : 操作成功！
     */

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
        /**
         * CURRENTROWS : 15
         * DATA : [{"aaa027":"贵溪市","aaa115":"正常应缴","aae003":"2014","aae022":"100元","aae140":"新型农村社会养老保险","aae341":"个人缴费",
         * "aaz289":"100元档次(个人缴费100省补18县补12)"},{"aaa027":"贵溪市","aaa115":"正常应缴","aae003":"2014","aae022":"18元",
         * "aae140":"新型农村社会养老保险","aae341":"省级财政补贴","aaz289":"100元档次(个人缴费100省补18县补12)"},{"aaa027":"贵溪市","aaa115":"正常应缴",
         * "aae003":"2014","aae022":"12元","aae140":"新型农村社会养老保险","aae341":"县级财政补贴","aaz289":"100元档次(个人缴费100省补18县补12)"},
         * {"aaa027":"贵溪市","aaa115":"正常应缴","aae003":"2015","aae022":"100元","aae140":"城乡居民社会养老保险","aae341":"个人缴费",
         * "aaz289":"100元档次(个人缴费100省补18市补12)"},{"aaa027":"贵溪市","aaa115":"正常应缴","aae003":"2015","aae022":"18元",
         * "aae140":"城乡居民社会养老保险","aae341":"省级财政补贴","aaz289":"100元档次(个人缴费100省补18市补12)"},{"aaa027":"贵溪市","aaa115":"正常应缴",
         * "aae003":"2015","aae022":"12元","aae140":"城乡居民社会养老保险","aae341":"县级财政补贴","aaz289":"100元档次(个人缴费100省补18市补12)"},
         * {"aaa027":"贵溪市","aaa115":"正常应缴","aae003":"2016","aae022":"100元","aae140":"城乡居民社会养老保险","aae341":"个人缴费",
         * "aaz289":"100元档次(个人缴费100省补18市补12)"},{"aaa027":"贵溪市","aaa115":"正常应缴","aae003":"2016","aae022":"18元",
         * "aae140":"城乡居民社会养老保险","aae341":"省级财政补贴","aaz289":"100元档次(个人缴费100省补18市补12)"},{"aaa027":"贵溪市","aaa115":"正常应缴",
         * "aae003":"2016","aae022":"12元","aae140":"城乡居民社会养老保险","aae341":"县级财政补贴","aaz289":"100元档次(个人缴费100省补18市补12)"},
         * {"aaa027":"贵溪市","aaa115":"正常应缴","aae003":"2017","aae022":"100元","aae140":"城乡居民社会养老保险","aae341":"个人缴费",
         * "aaz289":"100元档次(个人缴费100省补18市补12)"},{"aaa027":"贵溪市","aaa115":"正常应缴","aae003":"2017","aae022":"18元",
         * "aae140":"城乡居民社会养老保险","aae341":"省级财政补贴","aaz289":"100元档次(个人缴费100省补18市补12)"},{"aaa027":"贵溪市","aaa115":"正常应缴",
         * "aae003":"2017","aae022":"12元","aae140":"城乡居民社会养老保险","aae341":"县级财政补贴","aaz289":"100元档次(个人缴费100省补18市补12)"},
         * {"aaa027":"贵溪市","aaa115":"正常应缴","aae003":"2018","aae022":"300元","aae140":"城乡居民社会养老保险","aae341":"个人缴费",
         * "aaz289":"300元档次(个人缴费300省补24县补16)"},{"aaa027":"贵溪市","aaa115":"正常应缴","aae003":"2018","aae022":"24元",
         * "aae140":"城乡居民社会养老保险","aae341":"省级财政补贴","aaz289":"300元档次(个人缴费300省补24县补16)"},{"aaa027":"贵溪市","aaa115":"正常应缴",
         * "aae003":"2018","aae022":"16元","aae140":"城乡居民社会养老保险","aae341":"县级财政补贴","aaz289":"300元档次(个人缴费300省补24县补16)"}]
         */

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
            /**
             * aaa027 : 贵溪市
             * aaa115 : 正常应缴
             * aae003 : 2014
             * aae022 : 100元
             * aae140 : 新型农村社会养老保险
             * aae341 : 个人缴费
             * aaz289 : 100元档次(个人缴费100省补18县补12)
             */

            private String aaa027;
            private String aaa115;
            private String aae003;
            private String aae022;
            private String aae140;
            private String aae341;
            private String aaz289;

            public String getAaa027() {
                return aaa027;
            }

            public void setAaa027(String aaa027) {
                this.aaa027 = aaa027;
            }

            public String getAaa115() {
                return aaa115;
            }

            public void setAaa115(String aaa115) {
                this.aaa115 = aaa115;
            }

            public String getAae003() {
                return aae003;
            }

            public void setAae003(String aae003) {
                this.aae003 = aae003;
            }

            public String getAae022() {
                return aae022;
            }

            public void setAae022(String aae022) {
                this.aae022 = aae022;
            }

            public String getAae140() {
                return aae140;
            }

            public void setAae140(String aae140) {
                this.aae140 = aae140;
            }

            public String getAae341() {
                return aae341;
            }

            public void setAae341(String aae341) {
                this.aae341 = aae341;
            }

            public String getAaz289() {
                return aaz289;
            }

            public void setAaz289(String aaz289) {
                this.aaz289 = aaz289;
            }
        }
    }
}
