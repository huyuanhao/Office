package com.powerrich.office.oa.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 文 件 名：Items
 * 描   述：个人、企业、部门事项实体类
 * 作   者：Wangzheng
 * 时   间：2017/11/21
 * 版   权：v1.0
 */
public class Items implements Serializable {
    private String ITEMID;
    private String ITEMNAME;
    private String NORMACCEPTDEPART;
    private String SITENO;
    private String SSTYPE;
    private String SXBM;
    private String VERSION;
    private String SXLX;
    private String ISAPPOINTMENT;//可否预约
    private String ISAPP;
    private List<SonItem> sonItems;

    public String getSITENO() {
        return SITENO;
    }

    public void setSITENO(String SITENO) {
        this.SITENO = SITENO;
    }

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

    public String getNORMACCEPTDEPART() {
        return NORMACCEPTDEPART;
    }

    public void setNORMACCEPTDEPART(String NORMACCEPTDEPART) {
        this.NORMACCEPTDEPART = NORMACCEPTDEPART;
    }

    public String getSSTYPE() {
        return SSTYPE;
    }

    public void setSSTYPE(String SSTYPE) {
        this.SSTYPE = SSTYPE;
    }

    public String getSXBM() {
        return SXBM;
    }

    public void setSXBM(String SXBM) {
        this.SXBM = SXBM;
    }

    public String getVERSION() {
        return VERSION;
    }

    public void setVERSION(String VERSION) {
        this.VERSION = VERSION;
    }

    public String getSXLX() {
        return SXLX;
    }

    public void setSXLX(String SXLX) {
        this.SXLX = SXLX;
    }

    public String getISAPPOINTMENT() {
        return ISAPPOINTMENT;
    }

    public void setISAPPOINTMENT(String ISAPPOINTMENT) {
        this.ISAPPOINTMENT = ISAPPOINTMENT;
    }

    public String getISAPP() {
        return ISAPP;
    }

    public void setISAPP(String ISAPP) {
        this.ISAPP = ISAPP;
    }

    public List<SonItem> getSonItems() {
        return sonItems;
    }

    public void setSonItems(List<SonItem> sonItems) {
        this.sonItems = sonItems;
    }

    public class SonItem implements Serializable{
        private String ITEMID;
        private String ITEMNAME;
        private String NORMACCEPTDEPART;
        private String SITENO;
        private String SSTYPE;
        private String SXBM;
        private String VERSION;
        private String SXLX;
        private String ISAPPOINTMENT;//可否预约
        private String ISAPP;

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

        public String getNORMACCEPTDEPART() {
            return NORMACCEPTDEPART;
        }

        public void setNORMACCEPTDEPART(String NORMACCEPTDEPART) {
            this.NORMACCEPTDEPART = NORMACCEPTDEPART;
        }

        public String getSITENO() {
            return SITENO;
        }

        public void setSITENO(String SITENO) {
            this.SITENO = SITENO;
        }

        public String getSSTYPE() {
            return SSTYPE;
        }

        public void setSSTYPE(String SSTYPE) {
            this.SSTYPE = SSTYPE;
        }

        public String getSXBM() {
            return SXBM;
        }

        public void setSXBM(String SXBM) {
            this.SXBM = SXBM;
        }

        public String getVERSION() {
            return VERSION;
        }

        public void setVERSION(String VERSION) {
            this.VERSION = VERSION;
        }

        public String getSXLX() {
            return SXLX;
        }

        public void setSXLX(String SXLX) {
            this.SXLX = SXLX;
        }

        public String getISAPPOINTMENT() {
            return ISAPPOINTMENT;
        }

        public void setISAPPOINTMENT(String ISAPPOINTMENT) {
            this.ISAPPOINTMENT = ISAPPOINTMENT;
        }

        public String getISAPP() {
            return ISAPP;
        }

        public void setISAPP(String ISAPP) {
            this.ISAPP = ISAPP;
        }
    }
}
