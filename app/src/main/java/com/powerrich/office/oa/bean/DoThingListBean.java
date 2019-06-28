package com.powerrich.office.oa.bean;

import java.util.List;

/**
 * 文件名：
 * 描述：
 * 作者：白煜
 * 时间：2017/12/6 0006
 * 版权：
 */

public class DoThingListBean {

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
             * BJLX : 0
             * CNBJSX : 15
             * CSTATE : 暂存
             * DESDATE : 2017-12-05 08:57:21
             * FZSTATUS : 1
             * GZCZ : 1
             * ISDEL :
             * ISWSBS : 2
             * ITEMNAME : 幼师资格证申请
             * ITEM_NAME : 幼师资格证申请申请
             * LQZT : 0
             * NORMACCEPTDEPART :
             * POSITION : 1
             * PROCESS_STATE : 0
             * PROCESS_STATE_ABOUT :
             * PROKEYID : d6d6a4aaad864e5b8d751c5338fc3e5a
             * REP_EXCHANGE_SERIAL_NO :
             * REQ_EXCHANGE_SERIAL_NO :
             * SEARCHCODE : 360722199908141212
             * SFJE :
             * SHSJ :
             * SHYJ :
             * SITENAME : 市教育局
             * SITE_NAME :
             * SITE_NO :
             * SQRTYPE :
             * SSXBM : 347828439
             * STAFF_NAME :
             * STAFF_NO :
             * SWF_NO :
             * SXBM : 347828439
             * SXID : 20171204144630375872342000
             * SXMC : 幼师资格证申请
             * SYSTEM_ID :
             * TBJDATE :
             * TRACKINGNUMBER :
             * TRACKINGNUMBER2 :
             * TSLDATE : 1512435441934
             * USERNAME : 方鸿渐
             * USER_ID : 201711201757024574595625227
             */

            private String BJLX;
            private String CNBJSX;
            private String CSTATE;
            private String DESDATE_F;
            private String FZSTATUS;
            private String GZCZ;
            private String ISDEL;
            private String ISWSBS;
            private String ITEMNAME;
            private String ITEM_NAME;
            private String LQZT;
            private String NORMACCEPTDEPART;
            private String POSITION;
            private String PROCESS_STATE;
            private String PROCESS_STATE_ABOUT;
            private String PROKEYID;
            private String REP_EXCHANGE_SERIAL_NO;
            private String REQ_EXCHANGE_SERIAL_NO;
            private String SEARCHCODE;
            private String SFJE;
            private String SHSJ;
            private String SHYJ;
            private String SITENAME;
            private String SITE_NAME;
            private String SITE_NO;
            private String SQRTYPE;
            private String SSXBM;
            private String STAFF_NAME;
            private String STAFF_NO;
            private String SWF_NO;
            private String SXBM;
            private String SXID;
            private String SXMC;
            private String SYSTEM_ID;
            private String TBJDATE;
            private String TRACKINGNUMBER;
            private String TRACKINGNUMBER2;
            private long TSLDATE;
            private String USERNAME;
            private String USER_ID;

            public String getBJLX() {
                return BJLX;
            }

            public void setBJLX(String BJLX) {
                this.BJLX = BJLX;
            }

            public String getCNBJSX() {
                return CNBJSX;
            }

            public void setCNBJSX(String CNBJSX) {
                this.CNBJSX = CNBJSX;
            }

            public String getCSTATE() {
                return CSTATE;
            }

            public void setCSTATE(String CSTATE) {
                this.CSTATE = CSTATE;
            }

            public String getDESDATE_F() {
                return DESDATE_F;
            }

            public void setDESDATE_F(String DESDATE) {
                this.DESDATE_F = DESDATE;
            }

            public String getFZSTATUS() {
                return FZSTATUS;
            }

            public void setFZSTATUS(String FZSTATUS) {
                this.FZSTATUS = FZSTATUS;
            }

            public String getGZCZ() {
                return GZCZ;
            }

            public void setGZCZ(String GZCZ) {
                this.GZCZ = GZCZ;
            }

            public String getISDEL() {
                return ISDEL;
            }

            public void setISDEL(String ISDEL) {
                this.ISDEL = ISDEL;
            }

            public String getISWSBS() {
                return ISWSBS;
            }

            public void setISWSBS(String ISWSBS) {
                this.ISWSBS = ISWSBS;
            }

            public String getITEMNAME() {
                return ITEMNAME;
            }

            public void setITEMNAME(String ITEMNAME) {
                this.ITEMNAME = ITEMNAME;
            }

            public String getITEM_NAME() {
                return ITEM_NAME;
            }

            public void setITEM_NAME(String ITEM_NAME) {
                this.ITEM_NAME = ITEM_NAME;
            }

            public String getLQZT() {
                return LQZT;
            }

            public void setLQZT(String LQZT) {
                this.LQZT = LQZT;
            }

            public String getNORMACCEPTDEPART() {
                return NORMACCEPTDEPART;
            }

            public void setNORMACCEPTDEPART(String NORMACCEPTDEPART) {
                this.NORMACCEPTDEPART = NORMACCEPTDEPART;
            }

            public String getPOSITION() {
                return POSITION;
            }

            public void setPOSITION(String POSITION) {
                this.POSITION = POSITION;
            }

            public String getPROCESS_STATE() {
                return PROCESS_STATE;
            }

            public void setPROCESS_STATE(String PROCESS_STATE) {
                this.PROCESS_STATE = PROCESS_STATE;
            }

            public String getPROCESS_STATE_ABOUT() {
                return PROCESS_STATE_ABOUT;
            }

            public void setPROCESS_STATE_ABOUT(String PROCESS_STATE_ABOUT) {
                this.PROCESS_STATE_ABOUT = PROCESS_STATE_ABOUT;
            }

            public String getPROKEYID() {
                return PROKEYID;
            }

            public void setPROKEYID(String PROKEYID) {
                this.PROKEYID = PROKEYID;
            }

            public String getREP_EXCHANGE_SERIAL_NO() {
                return REP_EXCHANGE_SERIAL_NO;
            }

            public void setREP_EXCHANGE_SERIAL_NO(String REP_EXCHANGE_SERIAL_NO) {
                this.REP_EXCHANGE_SERIAL_NO = REP_EXCHANGE_SERIAL_NO;
            }

            public String getREQ_EXCHANGE_SERIAL_NO() {
                return REQ_EXCHANGE_SERIAL_NO;
            }

            public void setREQ_EXCHANGE_SERIAL_NO(String REQ_EXCHANGE_SERIAL_NO) {
                this.REQ_EXCHANGE_SERIAL_NO = REQ_EXCHANGE_SERIAL_NO;
            }

            public String getSEARCHCODE() {
                return SEARCHCODE;
            }

            public void setSEARCHCODE(String SEARCHCODE) {
                this.SEARCHCODE = SEARCHCODE;
            }

            public String getSFJE() {
                return SFJE;
            }

            public void setSFJE(String SFJE) {
                this.SFJE = SFJE;
            }

            public String getSHSJ() {
                return SHSJ;
            }

            public void setSHSJ(String SHSJ) {
                this.SHSJ = SHSJ;
            }

            public String getSHYJ() {
                return SHYJ;
            }

            public void setSHYJ(String SHYJ) {
                this.SHYJ = SHYJ;
            }

            public String getSITENAME() {
                return SITENAME;
            }

            public void setSITENAME(String SITENAME) {
                this.SITENAME = SITENAME;
            }

            public String getSITE_NAME() {
                return SITE_NAME;
            }

            public void setSITE_NAME(String SITE_NAME) {
                this.SITE_NAME = SITE_NAME;
            }

            public String getSITE_NO() {
                return SITE_NO;
            }

            public void setSITE_NO(String SITE_NO) {
                this.SITE_NO = SITE_NO;
            }

            public String getSQRTYPE() {
                return SQRTYPE;
            }

            public void setSQRTYPE(String SQRTYPE) {
                this.SQRTYPE = SQRTYPE;
            }

            public String getSSXBM() {
                return SSXBM;
            }

            public void setSSXBM(String SSXBM) {
                this.SSXBM = SSXBM;
            }

            public String getSTAFF_NAME() {
                return STAFF_NAME;
            }

            public void setSTAFF_NAME(String STAFF_NAME) {
                this.STAFF_NAME = STAFF_NAME;
            }

            public String getSTAFF_NO() {
                return STAFF_NO;
            }

            public void setSTAFF_NO(String STAFF_NO) {
                this.STAFF_NO = STAFF_NO;
            }

            public String getSWF_NO() {
                return SWF_NO;
            }

            public void setSWF_NO(String SWF_NO) {
                this.SWF_NO = SWF_NO;
            }

            public String getSXBM() {
                return SXBM;
            }

            public void setSXBM(String SXBM) {
                this.SXBM = SXBM;
            }

            public String getSXID() {
                return SXID;
            }

            public void setSXID(String SXID) {
                this.SXID = SXID;
            }

            public String getSXMC() {
                return SXMC;
            }

            public void setSXMC(String SXMC) {
                this.SXMC = SXMC;
            }

            public String getSYSTEM_ID() {
                return SYSTEM_ID;
            }

            public void setSYSTEM_ID(String SYSTEM_ID) {
                this.SYSTEM_ID = SYSTEM_ID;
            }

            public String getTBJDATE() {
                return TBJDATE;
            }

            public void setTBJDATE(String TBJDATE) {
                this.TBJDATE = TBJDATE;
            }

            public String getTRACKINGNUMBER() {
                return TRACKINGNUMBER;
            }

            public void setTRACKINGNUMBER(String TRACKINGNUMBER) {
                this.TRACKINGNUMBER = TRACKINGNUMBER;
            }

            public String getTRACKINGNUMBER2() {
                return TRACKINGNUMBER2;
            }

            public void setTRACKINGNUMBER2(String TRACKINGNUMBER2) {
                this.TRACKINGNUMBER2 = TRACKINGNUMBER2;
            }

            public long getTSLDATE() {
                return TSLDATE;
            }

            public void setTSLDATE(long TSLDATE) {
                this.TSLDATE = TSLDATE;
            }

            public String getUSERNAME() {
                return USERNAME;
            }

            public void setUSERNAME(String USERNAME) {
                this.USERNAME = USERNAME;
            }

            public String getUSER_ID() {
                return USER_ID;
            }

            public void setUSER_ID(String USER_ID) {
                this.USER_ID = USER_ID;
            }
        }
    }
}
