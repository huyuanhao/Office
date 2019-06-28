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

public class BasicInfoBean implements Serializable{
    /**
     * DATA : {"CURRENTROWS":1,"DATA":[{"aaa027":"贵溪市","aaa119":"城乡居民个人账户","aae206":"201409","aae238":"860元","aae239":"16.95元",
     * "aae240":"876.95元"}]}
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
         * CURRENTROWS : 1
         * DATA : [{"aaa027":"贵溪市","aaa119":"城乡居民个人账户","aae206":"201409","aae238":"860元","aae239":"16.95元","aae240":"876.95元"}]
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
             * aaa119 : 城乡居民个人账户
             * aae206 : 201409
             * aae238 : 860元
             * aae239 : 16.95元
             * aae240 : 876.95元
             */

            private String aaa027;
            private String aaa119;
            private String aae206;
            private String aae238;
            private String aae239;
            private String aae240;

            public String getAaa027() {
                return aaa027;
            }

            public void setAaa027(String aaa027) {
                this.aaa027 = aaa027;
            }

            public String getAaa119() {
                return aaa119;
            }

            public void setAaa119(String aaa119) {
                this.aaa119 = aaa119;
            }

            public String getAae206() {
                return aae206;
            }

            public void setAae206(String aae206) {
                this.aae206 = aae206;
            }

            public String getAae238() {
                return aae238;
            }

            public void setAae238(String aae238) {
                this.aae238 = aae238;
            }

            public String getAae239() {
                return aae239;
            }

            public void setAae239(String aae239) {
                this.aae239 = aae239;
            }

            public String getAae240() {
                return aae240;
            }

            public void setAae240(String aae240) {
                this.aae240 = aae240;
            }
        }
    }
}
