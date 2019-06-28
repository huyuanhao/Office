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

public class BasicInfoJbxxBean implements Serializable{

    /**
     * DATA : {"CURRENTROWS":1,"DATA":[{"aaa027":"贵溪市","aac003":"李红","aac004":"女","aac006":"19850109","aac010":"罗河镇新田村新田五组40号",
     * "aac147":"360423198501092025"}]}
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
         * DATA : [{"aaa027":"贵溪市","aac003":"李红","aac004":"女","aac006":"19850109","aac010":"罗河镇新田村新田五组40号",
         * "aac147":"360423198501092025"}]
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
             * aac003 : 李红
             * aac004 : 女
             * aac006 : 19850109
             * aac010 : 罗河镇新田村新田五组40号
             * aac147 : 360423198501092025
             */

            private String aaa027;
            private String aac003;
            private String aac004;
            private String aac006;
            private String aac010;
            private String aac147;

            public String getAaa027() {
                return aaa027;
            }

            public void setAaa027(String aaa027) {
                this.aaa027 = aaa027;
            }

            public String getAac003() {
                return aac003;
            }

            public void setAac003(String aac003) {
                this.aac003 = aac003;
            }

            public String getAac004() {
                return aac004;
            }

            public void setAac004(String aac004) {
                this.aac004 = aac004;
            }

            public String getAac006() {
                return aac006;
            }

            public void setAac006(String aac006) {
                this.aac006 = aac006;
            }

            public String getAac010() {
                return aac010;
            }

            public void setAac010(String aac010) {
                this.aac010 = aac010;
            }

            public String getAac147() {
                return aac147;
            }

            public void setAac147(String aac147) {
                this.aac147 = aac147;
            }
        }
    }
}
