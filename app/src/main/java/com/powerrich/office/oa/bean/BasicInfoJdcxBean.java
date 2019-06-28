package com.powerrich.office.oa.bean;

import java.util.List;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/11/21
 * 版权：
 */

public class BasicInfoJdcxBean {

    /**
     * DATA : {"CURRENTROWS":1,"DATA":[{"applyDate":"20180808","applyType":"1","bankName":"中国工商银行","cardBatchMessage":"制卡完成",
     * "cardBussinessStatus":"4","cardDataStatus":"1","idNumber":"340123198209242591","makeCardType":"3","name":"许",
     * "photoDate":"20180815","processStatus":"2"}]}
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
         * DATA : [{"applyDate":"20180808","applyType":"1","bankName":"中国工商银行","cardBatchMessage":"制卡完成",
         * "cardBussinessStatus":"4","cardDataStatus":"1","idNumber":"340123198209242591","makeCardType":"3","name":"许",
         * "photoDate":"20180815","processStatus":"2"}]
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
             * applyDate : 20180808
             * applyType : 1
             * bankName : 中国工商银行
             * cardBatchMessage : 制卡完成
             * cardBussinessStatus : 4
             * cardDataStatus : 1
             * idNumber : 340123198209242591
             * makeCardType : 3
             * name : 许
             * photoDate : 20180815
             * processStatus : 2
             */

            private String applyDate;
            private String applyType;
            private String bankName;
            private String cardBatchMessage;
            private String cardBussinessStatus;
            private String cardDataStatus;
            private String idNumber;
            private String makeCardType;
            private String name;
            private String photoDate;
            private String processStatus;

            public String getApplyDate() {
                return applyDate;
            }

            public void setApplyDate(String applyDate) {
                this.applyDate = applyDate;
            }

            public String getApplyType() {
                return applyType;
            }

            public void setApplyType(String applyType) {
                this.applyType = applyType;
            }

            public String getBankName() {
                return bankName;
            }

            public void setBankName(String bankName) {
                this.bankName = bankName;
            }

            public String getCardBatchMessage() {
                return cardBatchMessage;
            }

            public void setCardBatchMessage(String cardBatchMessage) {
                this.cardBatchMessage = cardBatchMessage;
            }

            public String getCardBussinessStatus() {
                return cardBussinessStatus;
            }

            public void setCardBussinessStatus(String cardBussinessStatus) {
                this.cardBussinessStatus = cardBussinessStatus;
            }

            public String getCardDataStatus() {
                return cardDataStatus;
            }

            public void setCardDataStatus(String cardDataStatus) {
                this.cardDataStatus = cardDataStatus;
            }

            public String getIdNumber() {
                return idNumber;
            }

            public void setIdNumber(String idNumber) {
                this.idNumber = idNumber;
            }

            public String getMakeCardType() {
                return makeCardType;
            }

            public void setMakeCardType(String makeCardType) {
                this.makeCardType = makeCardType;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPhotoDate() {
                return photoDate;
            }

            public void setPhotoDate(String photoDate) {
                this.photoDate = photoDate;
            }

            public String getProcessStatus() {
                return processStatus;
            }

            public void setProcessStatus(String processStatus) {
                this.processStatus = processStatus;
            }
        }
    }
}
