package com.powerrich.office.oa.bean;

/**
 * 文件名：IWantBean
 * 描述：
 * 作者：白煜
 * 时间：2017/12/7 0007
 * 版权：
 */

public class IWantBean {
    /**
     * DATA : {"ADDRESS":"皇岗花园大厦A座3楼","BIZCODE":"TS2017111310352131","CRETTIME":"2017-11-13 10:35:21",
     * "JL_ID":"20171113103521316536026957","MOBILE":"15986609110","MYNAME":"powerrich",
     * "QUESTION":"powerrich的投诉powerrich的投诉powerrich的投诉powerrich的投诉powerrich的投诉powerrich的投诉","QUSTIONTYPE":"市教育局",
     * "RESERTTIME":"2017-11-13 15:38:19","REVERTCONTENT":"","REVERTER":"","TITLE":"powerrich的投诉"}
     * code : 0
     * message : 操作成功
     */

    private DATABean DATA;
    private String code;
    private String message;

    public DATABean getDATA() {
        return DATA;
    }

    public void setDATA(DATABean DATA) {
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

    public static class DATABean {
        /**
         * ADDRESS : 皇岗花园大厦A座3楼
         * BIZCODE : TS2017111310352131
         * CRETTIME : 2017-11-13 10:35:21
         * JL_ID : 20171113103521316536026957
         * MOBILE : 15986609110
         * MYNAME : powerrich
         * QUESTION : powerrich的投诉powerrich的投诉powerrich的投诉powerrich的投诉powerrich的投诉powerrich的投诉
         * QUSTIONTYPE : 市教育局
         * RESERTTIME : 2017-11-13 15:38:19
         * REVERTCONTENT :
         * REVERTER :
         * TITLE : powerrich的投诉
         */

        private String ADDRESS;
        private String BIZCODE;
        private String CRETTIME;
        private String JL_ID;
        private String MOBILE;
        private String MYNAME;
        private String QUESTION;
        private String QUSTIONTYPE;
        private String RESERTTIME;
        private String REVERTCONTENT;
        private String REVERTER;
        private String TITLE;

        public String getADDRESS() {
            return ADDRESS;
        }

        public void setADDRESS(String ADDRESS) {
            this.ADDRESS = ADDRESS;
        }

        public String getBIZCODE() {
            return BIZCODE;
        }

        public void setBIZCODE(String BIZCODE) {
            this.BIZCODE = BIZCODE;
        }

        public String getCRETTIME() {
            return CRETTIME;
        }

        public void setCRETTIME(String CRETTIME) {
            this.CRETTIME = CRETTIME;
        }

        public String getJL_ID() {
            return JL_ID;
        }

        public void setJL_ID(String JL_ID) {
            this.JL_ID = JL_ID;
        }

        public String getMOBILE() {
            return MOBILE;
        }

        public void setMOBILE(String MOBILE) {
            this.MOBILE = MOBILE;
        }

        public String getMYNAME() {
            return MYNAME;
        }

        public void setMYNAME(String MYNAME) {
            this.MYNAME = MYNAME;
        }

        public String getQUESTION() {
            return QUESTION;
        }

        public void setQUESTION(String QUESTION) {
            this.QUESTION = QUESTION;
        }

        public String getQUSTIONTYPE() {
            return QUSTIONTYPE;
        }

        public void setQUSTIONTYPE(String QUSTIONTYPE) {
            this.QUSTIONTYPE = QUSTIONTYPE;
        }

        public String getRESERTTIME() {
            return RESERTTIME;
        }

        public void setRESERTTIME(String RESERTTIME) {
            this.RESERTTIME = RESERTTIME;
        }

        public String getREVERTCONTENT() {
            return REVERTCONTENT;
        }

        public void setREVERTCONTENT(String REVERTCONTENT) {
            this.REVERTCONTENT = REVERTCONTENT;
        }

        public String getREVERTER() {
            return REVERTER;
        }

        public void setREVERTER(String REVERTER) {
            this.REVERTER = REVERTER;
        }

        public String getTITLE() {
            return TITLE;
        }

        public void setTITLE(String TITLE) {
            this.TITLE = TITLE;
        }
    }

//    /**
//     * DATA : {"ADDRESS":"皇岗花园大厦A座3楼","BIZCODE":"TS2017111310352131","CRETTIME":"2017-11-13 10:35:21",
//     * "EMAIL_ADDRESS":"1016465176@qq.com","EVALUATE_CONTENT":"","ID":"20171113103521316536026957","ISMAIL_NOTIFY":"0",
//     * "ISNOTE_NOTIFY":"0","ISOPEN":"1","ISREVERT":"2","JL_ID":"20171113103521316536026957","MOBILE":"15986609110",
//     * "MYNAME":"powerrich","PHONENUMBER":"0755-965875","POSTNUMBER":"518000",
//     * "QUESTION":"powerrich的投诉powerrich的投诉powerrich的投诉powerrich的投诉powerrich的投诉powerrich的投诉","QUSTIONTYPE":"市教育局","RESERT":"",
//     * "RESERTTIME":"2017-11-13 15:38:19","REVERTCONTENT":"","REVERTER":"","REVERTUNIT":"","SATISFACTION_DEGREE":"",
//     * "STAFFCAD":"441423199307252019","STAFFNAME":"powerrich","SYADDRESS":"","TITLE":"powerrich的投诉","TWTYPE":"1"}
//     * code : 0
//     * message : 操作成功
//     */
//
//    private DATABean DATA;
//    private String code;
//    private String message;
//
//    public DATABean getDATA() {
//        return DATA;
//    }
//
//    public void setDATA(DATABean DATA) {
//        this.DATA = DATA;
//    }
//
//    public String getCode() {
//        return code;
//    }
//
//    public void setCode(String code) {
//        this.code = code;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    public static class DATABean {
//        /**
//         * ADDRESS : 皇岗花园大厦A座3楼
//         * BIZCODE : TS2017111310352131
//         * CRETTIME : 2017-11-13 10:35:21
//         * EMAIL_ADDRESS : 1016465176@qq.com
//         * EVALUATE_CONTENT :
//         * ID : 20171113103521316536026957
//         * ISMAIL_NOTIFY : 0
//         * ISNOTE_NOTIFY : 0
//         * ISOPEN : 1
//         * ISREVERT : 2
//         * JL_ID : 20171113103521316536026957
//         * MOBILE : 15986609110
//         * MYNAME : powerrich
//         * PHONENUMBER : 0755-965875
//         * POSTNUMBER : 518000
//         * QUESTION : powerrich的投诉powerrich的投诉powerrich的投诉powerrich的投诉powerrich的投诉powerrich的投诉
//         * QUSTIONTYPE : 市教育局
//         * RESERT :
//         * RESERTTIME : 2017-11-13 15:38:19
//         * REVERTCONTENT :
//         * REVERTER :
//         * REVERTUNIT :
//         * SATISFACTION_DEGREE :
//         * STAFFCAD : 441423199307252019
//         * STAFFNAME : powerrich
//         * SYADDRESS :
//         * TITLE : powerrich的投诉
//         * TWTYPE : 1
//         */
//
//        private String ADDRESS;
//        private String BIZCODE;
//        private String CRETTIME;
//        private String EMAIL_ADDRESS;
//        private String EVALUATE_CONTENT;
//        private String ID;
//        private String ISMAIL_NOTIFY;
//        private String ISNOTE_NOTIFY;
//        private String ISOPEN;
//        private String ISREVERT;
//        private String JL_ID;
//        private String MOBILE;
//        private String MYNAME;
//        private String PHONENUMBER;
//        private String POSTNUMBER;
//        private String QUESTION;
//        private String QUSTIONTYPE;
//        private String RESERT;
//        private String RESERTTIME;
//        private String REVERTCONTENT;
//        private String REVERTER;
//        private String REVERTUNIT;
//        private String SATISFACTION_DEGREE;
//        private String STAFFCAD;
//        private String STAFFNAME;
//        private String SYADDRESS;
//        private String TITLE;
//        private String TWTYPE;
//
//        public String getADDRESS() {
//            return ADDRESS;
//        }
//
//        public void setADDRESS(String ADDRESS) {
//            this.ADDRESS = ADDRESS;
//        }
//
//        public String getBIZCODE() {
//            return BIZCODE;
//        }
//
//        public void setBIZCODE(String BIZCODE) {
//            this.BIZCODE = BIZCODE;
//        }
//
//        public String getCRETTIME() {
//            return CRETTIME;
//        }
//
//        public void setCRETTIME(String CRETTIME) {
//            this.CRETTIME = CRETTIME;
//        }
//
//        public String getEMAIL_ADDRESS() {
//            return EMAIL_ADDRESS;
//        }
//
//        public void setEMAIL_ADDRESS(String EMAIL_ADDRESS) {
//            this.EMAIL_ADDRESS = EMAIL_ADDRESS;
//        }
//
//        public String getEVALUATE_CONTENT() {
//            return EVALUATE_CONTENT;
//        }
//
//        public void setEVALUATE_CONTENT(String EVALUATE_CONTENT) {
//            this.EVALUATE_CONTENT = EVALUATE_CONTENT;
//        }
//
//        public String getID() {
//            return ID;
//        }
//
//        public void setID(String ID) {
//            this.ID = ID;
//        }
//
//        public String getISMAIL_NOTIFY() {
//            return ISMAIL_NOTIFY;
//        }
//
//        public void setISMAIL_NOTIFY(String ISMAIL_NOTIFY) {
//            this.ISMAIL_NOTIFY = ISMAIL_NOTIFY;
//        }
//
//        public String getISNOTE_NOTIFY() {
//            return ISNOTE_NOTIFY;
//        }
//
//        public void setISNOTE_NOTIFY(String ISNOTE_NOTIFY) {
//            this.ISNOTE_NOTIFY = ISNOTE_NOTIFY;
//        }
//
//        public String getISOPEN() {
//            return ISOPEN;
//        }
//
//        public void setISOPEN(String ISOPEN) {
//            this.ISOPEN = ISOPEN;
//        }
//
//        public String getISREVERT() {
//            return ISREVERT;
//        }
//
//        public void setISREVERT(String ISREVERT) {
//            this.ISREVERT = ISREVERT;
//        }
//
//        public String getJL_ID() {
//            return JL_ID;
//        }
//
//        public void setJL_ID(String JL_ID) {
//            this.JL_ID = JL_ID;
//        }
//
//        public String getMOBILE() {
//            return MOBILE;
//        }
//
//        public void setMOBILE(String MOBILE) {
//            this.MOBILE = MOBILE;
//        }
//
//        public String getMYNAME() {
//            return MYNAME;
//        }
//
//        public void setMYNAME(String MYNAME) {
//            this.MYNAME = MYNAME;
//        }
//
//        public String getPHONENUMBER() {
//            return PHONENUMBER;
//        }
//
//        public void setPHONENUMBER(String PHONENUMBER) {
//            this.PHONENUMBER = PHONENUMBER;
//        }
//
//        public String getPOSTNUMBER() {
//            return POSTNUMBER;
//        }
//
//        public void setPOSTNUMBER(String POSTNUMBER) {
//            this.POSTNUMBER = POSTNUMBER;
//        }
//
//        public String getQUESTION() {
//            return QUESTION;
//        }
//
//        public void setQUESTION(String QUESTION) {
//            this.QUESTION = QUESTION;
//        }
//
//        public String getQUSTIONTYPE() {
//            return QUSTIONTYPE;
//        }
//
//        public void setQUSTIONTYPE(String QUSTIONTYPE) {
//            this.QUSTIONTYPE = QUSTIONTYPE;
//        }
//
//        public String getRESERT() {
//            return RESERT;
//        }
//
//        public void setRESERT(String RESERT) {
//            this.RESERT = RESERT;
//        }
//
//        public String getRESERTTIME() {
//            return RESERTTIME;
//        }
//
//        public void setRESERTTIME(String RESERTTIME) {
//            this.RESERTTIME = RESERTTIME;
//        }
//
//        public String getREVERTCONTENT() {
//            return REVERTCONTENT;
//        }
//
//        public void setREVERTCONTENT(String REVERTCONTENT) {
//            this.REVERTCONTENT = REVERTCONTENT;
//        }
//
//        public String getREVERTER() {
//            return REVERTER;
//        }
//
//        public void setREVERTER(String REVERTER) {
//            this.REVERTER = REVERTER;
//        }
//
//        public String getREVERTUNIT() {
//            return REVERTUNIT;
//        }
//
//        public void setREVERTUNIT(String REVERTUNIT) {
//            this.REVERTUNIT = REVERTUNIT;
//        }
//
//        public String getSATISFACTION_DEGREE() {
//            return SATISFACTION_DEGREE;
//        }
//
//        public void setSATISFACTION_DEGREE(String SATISFACTION_DEGREE) {
//            this.SATISFACTION_DEGREE = SATISFACTION_DEGREE;
//        }
//
//        public String getSTAFFCAD() {
//            return STAFFCAD;
//        }
//
//        public void setSTAFFCAD(String STAFFCAD) {
//            this.STAFFCAD = STAFFCAD;
//        }
//
//        public String getSTAFFNAME() {
//            return STAFFNAME;
//        }
//
//        public void setSTAFFNAME(String STAFFNAME) {
//            this.STAFFNAME = STAFFNAME;
//        }
//
//        public String getSYADDRESS() {
//            return SYADDRESS;
//        }
//
//        public void setSYADDRESS(String SYADDRESS) {
//            this.SYADDRESS = SYADDRESS;
//        }
//
//        public String getTITLE() {
//            return TITLE;
//        }
//
//        public void setTITLE(String TITLE) {
//            this.TITLE = TITLE;
//        }
//
//        public String getTWTYPE() {
//            return TWTYPE;
//        }
//
//        public void setTWTYPE(String TWTYPE) {
//            this.TWTYPE = TWTYPE;
//        }
//    }


}
