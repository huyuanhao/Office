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

public class BasicInfoGrcbxxBean implements Serializable{


    /**
     * DATA : {"CURRENTROWS":1,"DATA":[{"aaa027":"贵溪市","aac008":"正常参保","aac031":"参保缴费","aac049":"201107","aae116":"非待遇人员",
     * "aae140":"城乡居民社会养老保险"}]}
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
         * DATA : [{"aaa027":"贵溪市","aac008":"正常参保","aac031":"参保缴费","aac049":"201107","aae116":"非待遇人员","aae140":"城乡居民社会养老保险"}]
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
             * aac008 : 正常参保
             * aac031 : 参保缴费
             * aac049 : 201107
             * aae116 : 非待遇人员
             * aae140 : 城乡居民社会养老保险
             */

            private String aaa027;
            private String aac008;
            private String aac031;
            private String aac049;
            private String aae116;
            private String aae140;

            public String getAaa027() {
                return aaa027;
            }

            public void setAaa027(String aaa027) {
                this.aaa027 = aaa027;
            }

            public String getAac008() {
                return aac008;
            }

            public void setAac008(String aac008) {
                this.aac008 = aac008;
            }

            public String getAac031() {
                return aac031;
            }

            public void setAac031(String aac031) {
                this.aac031 = aac031;
            }

            public String getAac049() {
                return aac049;
            }

            public void setAac049(String aac049) {
                this.aac049 = aac049;
            }

            public String getAae116() {
                return aae116;
            }

            public void setAae116(String aae116) {
                this.aae116 = aae116;
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
