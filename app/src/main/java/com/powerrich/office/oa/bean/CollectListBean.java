package com.powerrich.office.oa.bean;

import java.util.List;

/**
 * 文件名：CollectListBean
 * 描述：
 * 作者：白煜
 * 时间：2018/1/12 0012
 * 版权：
 */

public class CollectListBean {

    /**
     * DATA : {"CURRENTROWS":3,"DATA":[{"ITEMID":"201712211724213455090726653","ITEMNAME":"个人所得税完税证明开具",
     * "SCID":"201801101847112061587780315","SCTIME":"2018-01-10 18:47","SXBM":"360600-011",
     * "SXCODE":"360600-0115747516620171221","USERID":"201801090919526443893616085"},{"ITEMID":"201712220940512220159615115",
     * "ITEMNAME":"专业技术资格考核认定","SCID":"201801102256561070303089978","SCTIME":"2018-01-10 22:56","SXBM":"360600-014",
     * "SXCODE":"360600-0143022227520171222","USERID":"201801090919526443893616085"},{"ITEMID":"201801091033561202868114950",
     * "ITEMNAME":"《就业失业登记证》发证与年检","SCID":"201801101847037565013471234","SCTIME":"2018-01-10 18:47","SXBM":"360600-023",
     * "SXCODE":"360600-0236506032320171222","USERID":"201801090919526443893616085"}],"ROWS":"3"}
     * code : 0
     * message : 操作成功
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
         * CURRENTROWS : 3
         * DATA : [{"ITEMID":"201712211724213455090726653","ITEMNAME":"个人所得税完税证明开具","SCID":"201801101847112061587780315",
         * "SCTIME":"2018-01-10 18:47","SXBM":"360600-011","SXCODE":"360600-0115747516620171221",
         * "USERID":"201801090919526443893616085"},{"ITEMID":"201712220940512220159615115","ITEMNAME":"专业技术资格考核认定",
         * "SCID":"201801102256561070303089978","SCTIME":"2018-01-10 22:56","SXBM":"360600-014",
         * "SXCODE":"360600-0143022227520171222","USERID":"201801090919526443893616085"},
         * {"ITEMID":"201801091033561202868114950","ITEMNAME":"《就业失业登记证》发证与年检","SCID":"201801101847037565013471234",
         * "SCTIME":"2018-01-10 18:47","SXBM":"360600-023","SXCODE":"360600-0236506032320171222",
         * "USERID":"201801090919526443893616085"}]
         * ROWS : 3
         */

        private int CURRENTROWS;
        private String ROWS;
        private List<DATABean> DATA;

        public int getCURRENTROWS() {
            return CURRENTROWS;
        }

        public void setCURRENTROWS(int CURRENTROWS) {
            this.CURRENTROWS = CURRENTROWS;
        }

        public String getROWS() {
            return ROWS;
        }

        public void setROWS(String ROWS) {
            this.ROWS = ROWS;
        }

        public List<DATABean> getDATA() {
            return DATA;
        }

        public void setDATA(List<DATABean> DATA) {
            this.DATA = DATA;
        }

        public static class DATABean {
            /**
             * ITEMID : 201712211724213455090726653
             * ITEMNAME : 个人所得税完税证明开具
             * SCID : 201801101847112061587780315
             * SCTIME : 2018-01-10 18:47
             * SXBM : 360600-011
             * SXCODE : 360600-0115747516620171221
             * USERID : 201801090919526443893616085
             */

            private String ITEMID;
            private String ITEMNAME;
            private String SCID;
            private String SCTIME;
            private String SXBM;
            private String SXCODE;
            private String USERID;
            private String NORMACCEPTDEPART;
            private String NORMACCEPTDEPARTID;

            public String getITEMID() {
                return ITEMID;
            }

            public void setITEMID(String ITEMID) {
                this.ITEMID = ITEMID;
            }

            public String getITEMNAME() {
                return ITEMNAME;
            }

            public void setITEMNAME(String ITEMNAME) {
                this.ITEMNAME = ITEMNAME;
            }

            public String getSCID() {
                return SCID;
            }

            public void setSCID(String SCID) {
                this.SCID = SCID;
            }

            public String getSCTIME() {
                return SCTIME;
            }

            public void setSCTIME(String SCTIME) {
                this.SCTIME = SCTIME;
            }

            public String getSXBM() {
                return SXBM;
            }

            public void setSXBM(String SXBM) {
                this.SXBM = SXBM;
            }

            public String getSXCODE() {
                return SXCODE;
            }

            public void setSXCODE(String SXCODE) {
                this.SXCODE = SXCODE;
            }

            public String getUSERID() {
                return USERID;
            }

            public void setUSERID(String USERID) {
                this.USERID = USERID;
            }

            public String getNORMACCEPTDEPART() {
                return NORMACCEPTDEPART;
            }

            public void setNORMACCEPTDEPART(String NORMACCEPTDEPART) {
                this.NORMACCEPTDEPART = NORMACCEPTDEPART;
            }

            public String getNORMACCEPTDEPARTID() {
                return NORMACCEPTDEPARTID;
            }

            public void setNORMACCEPTDEPARTID(String NORMACCEPTDEPARTID) {
                this.NORMACCEPTDEPARTID = NORMACCEPTDEPARTID;
            }
        }
    }
}
