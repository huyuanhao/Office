package com.powerrich.office.oa.bean;

import com.powerrich.office.oa.SIMeID.SIMeIDApplication;

import java.util.List;

/**
 * @author Administrator
 * @date 2018/10/18 11:52
 */
public class MyCertificateBean {


    @Override
    public String toString() {
        return SIMeIDApplication.getmGson().toJson(this);
    }

    /**
     * CURRENTROWS : 1
     * DATA : [{"AUTH_CODE":"20181018113453948NA0183125_360600201800003KTV","HOLDER_NAME":"江西鹰南贡米有限公司","ID_CODE":"91360600X12452039M","ISASYNC":"true","ISSUE_DATE":"2017-05-16 00:00:00","ISSUE_ORG_NAME":"鹰潭市市场和质量监督管理局","LICENSE_CODE":"360600201800003KTV","NAME":"营业执照（A类有限责任公司）"}]
     * ROWS : 1
     */



    private int CURRENTROWS;
    private int ROWS;
    private List<DATABean> DATA;

    public int getCURRENTROWS() {
        return CURRENTROWS;
    }

    public void setCURRENTROWS(int CURRENTROWS) {
        this.CURRENTROWS = CURRENTROWS;
    }

    public int getROWS() {
        return ROWS;
    }

    public void setROWS(int ROWS) {
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
         * AUTH_CODE : 20181018113453948NA0183125_360600201800003KTV
         * HOLDER_NAME : 江西鹰南贡米有限公司
         * ID_CODE : 91360600X12452039M
         * ISASYNC : true
         * ISSUE_DATE : 2017-05-16 00:00:00
         * ISSUE_ORG_NAME : 鹰潭市市场和质量监督管理局
         * LICENSE_CODE : 360600201800003KTV
         * NAME : 营业执照（A类有限责任公司）
         */

        private String AUTH_CODE;
        private String HOLDER_NAME;
        private String ID_CODE;
        private String ISASYNC;
        private String ISSUE_DATE;
        private String ISSUE_ORG_NAME;
        private String LICENSE_CODE;
        private String NAME;

        public String getAUTH_CODE() {
            return AUTH_CODE;
        }

        public void setAUTH_CODE(String AUTH_CODE) {
            this.AUTH_CODE = AUTH_CODE;
        }

        public String getHOLDER_NAME() {
            return HOLDER_NAME;
        }

        public void setHOLDER_NAME(String HOLDER_NAME) {
            this.HOLDER_NAME = HOLDER_NAME;
        }

        public String getID_CODE() {
            return ID_CODE;
        }

        public void setID_CODE(String ID_CODE) {
            this.ID_CODE = ID_CODE;
        }

        public String getISASYNC() {
            return ISASYNC;
        }

        public void setISASYNC(String ISASYNC) {
            this.ISASYNC = ISASYNC;
        }

        public String getISSUE_DATE() {
            return ISSUE_DATE;
        }

        public void setISSUE_DATE(String ISSUE_DATE) {
            this.ISSUE_DATE = ISSUE_DATE;
        }

        public String getISSUE_ORG_NAME() {
            return ISSUE_ORG_NAME;
        }

        public void setISSUE_ORG_NAME(String ISSUE_ORG_NAME) {
            this.ISSUE_ORG_NAME = ISSUE_ORG_NAME;
        }

        public String getLICENSE_CODE() {
            return LICENSE_CODE;
        }

        public void setLICENSE_CODE(String LICENSE_CODE) {
            this.LICENSE_CODE = LICENSE_CODE;
        }

        public String getNAME() {
            return NAME;
        }

        public void setNAME(String NAME) {
            this.NAME = NAME;
        }
    }
}
