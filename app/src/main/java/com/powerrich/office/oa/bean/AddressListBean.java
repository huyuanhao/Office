package com.powerrich.office.oa.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 文件名：
 * 描述：
 * 作者：白煜
 * 时间：2017/11/30 0030
 * 版权：
 */

public class AddressListBean {

    /**
     * DATA : [{"ADDRESS":"湖北省武汉市东湖高新区光谷科技港2B栋8楼","ADDRESSID":"201711291023425452032085389","COMPANY_NAME":"湖北广通智诚",
     * "HANDSET":"18871099138","ISDEFAULT":"1","SJRXM":"周小权","TEL_NO":"027-12344322","USERID":"2017060115434771793843065",
     * "YZBM":"432100"},{"ADDRESS":"湖北省武汉市光谷科技港","ADDRESSID":"201711291010036982080799771","COMPANY_NAME":"湖北广通智诚软件有限公司",
     * "HANDSET":"156123345685","ISDEFAULT":"0","SJRXM":"戴希先","TEL_NO":"021-11111111","USERID":"2017060115434771793843065",
     * "YZBM":"432100"},{"ADDRESS":"深圳市南山区南山科技园","ADDRESSID":"201711291008508728827625809","COMPANY_NAME":"深圳广通软件有限公司",
     * "HANDSET":"18571689123","ISDEFAULT":"0","SJRXM":"胡越","TEL_NO":"0755-22222222","USERID":"2017060115434771793843065",
     * "YZBM":"123456"}]
     * code : 0
     * message : 操作成功
     */

    private String code;
    private String message;
    private List<DATABean> DATA;

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

    public List<DATABean> getDATA() {
        return DATA;
    }

    public void setDATA(List<DATABean> DATA) {
        this.DATA = DATA;
    }

    public static class DATABean implements Serializable {
        /**
         * ADDRESS : 湖北省武汉市东湖高新区光谷科技港2B栋8楼
         * ADDRESSID : 201711291023425452032085389
         * COMPANY_NAME : 湖北广通智诚
         * HANDSET : 18871099138
         * ISDEFAULT : 1
         * SJRXM : 周小权
         * TEL_NO : 027-12344322
         * USERID : 2017060115434771793843065
         * YZBM : 432100
         */

        private String ADDRESS;
        private String ADDRESSID;
        private String COMPANY_NAME;
        private String HANDSET;
        private String ISDEFAULT;
        private String SJRXM;
        private String TEL_NO;
        private String USERID;
        private String YZBM;
        private String PROV;
        private String CITY;

        private boolean isDefault = false;

        public void setDefault(boolean aDefault) {
            isDefault = aDefault;
        }

        public boolean isDefault() {
            return isDefault;
        }

        public String getADDRESS() {
            return ADDRESS;
        }

        public void setADDRESS(String ADDRESS) {
            this.ADDRESS = ADDRESS;
        }

        public String getADDRESSID() {
            return ADDRESSID;
        }

        public void setADDRESSID(String ADDRESSID) {
            this.ADDRESSID = ADDRESSID;
        }

        public String getCOMPANY_NAME() {
            return COMPANY_NAME;
        }

        public void setCOMPANY_NAME(String COMPANY_NAME) {
            this.COMPANY_NAME = COMPANY_NAME;
        }

        public String getHANDSET() {
            return HANDSET;
        }

        public void setHANDSET(String HANDSET) {
            this.HANDSET = HANDSET;
        }

        public String getISDEFAULT() {
            return ISDEFAULT;
        }

        public void setISDEFAULT(String ISDEFAULT) {
            this.ISDEFAULT = ISDEFAULT;
        }

        public String getSJRXM() {
            return SJRXM;
        }

        public void setSJRXM(String SJRXM) {
            this.SJRXM = SJRXM;
        }

        public String getTEL_NO() {
            return TEL_NO;
        }

        public void setTEL_NO(String TEL_NO) {
            this.TEL_NO = TEL_NO;
        }

        public String getUSERID() {
            return USERID;
        }

        public void setUSERID(String USERID) {
            this.USERID = USERID;
        }

        public String getYZBM() {
            return YZBM;
        }

        public void setYZBM(String YZBM) {
            this.YZBM = YZBM;
        }

        public String getCITY() {
            return CITY;
        }

        public void setCITY(String CITY) {
            this.CITY = CITY;
        }

        public String getPROV() {
            return PROV;
        }

        public void setPROV(String PROV) {
            this.PROV = PROV;
        }
    }
}
