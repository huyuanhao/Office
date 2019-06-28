package com.powerrich.office.oa.bean;

import com.google.gson.Gson;

import java.util.List;

/**
 * @author AlienChao
 * @date 2019/06/13 16:10
 */
public class QrCommit {


    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    /**
     * JoinList : [{"TicketIdList":[1664735537],"JoinFieldInfoList":[{"name":"RealName","value":"冷"},{"name":"Mobile","value":"15971498220"},{"name":"Email","value":"649458544@qq.com"},{"name":"Field1664735566","value":"否"},{"name":"Field1664735958","value":"地址"},{"name":"Field1655550497","value":"证件号"}]}]
     * ContactInfo : {"ContactRealName":"冷","ContactMobile":"15971498220","ContactEmail":"649458544@qq.com","ContactCompany":""}
     * DiscountCode :
     * InviteCode :
     * OpenId :
     * AppId :
     */

    private ContactInfoBean ContactInfo;
    private String DiscountCode;
    private String InviteCode;
    private String OpenId;
    private String AppId;
    private List<JoinListBean> JoinList;

    public ContactInfoBean getContactInfo() {
        return ContactInfo;
    }

    public void setContactInfo(ContactInfoBean ContactInfo) {
        this.ContactInfo = ContactInfo;
    }

    public String getDiscountCode() {
        return DiscountCode;
    }

    public void setDiscountCode(String DiscountCode) {
        this.DiscountCode = DiscountCode;
    }

    public String getInviteCode() {
        return InviteCode;
    }

    public void setInviteCode(String InviteCode) {
        this.InviteCode = InviteCode;
    }

    public String getOpenId() {
        return OpenId;
    }

    public void setOpenId(String OpenId) {
        this.OpenId = OpenId;
    }

    public String getAppId() {
        return AppId;
    }

    public void setAppId(String AppId) {
        this.AppId = AppId;
    }

    public List<JoinListBean> getJoinList() {
        return JoinList;
    }

    public void setJoinList(List<JoinListBean> JoinList) {
        this.JoinList = JoinList;
    }

    public static class ContactInfoBean {
        /**
         * ContactRealName : 冷
         * ContactMobile : 15971498220
         * ContactEmail : 649458544@qq.com
         * ContactCompany :
         */

        private String ContactRealName;
        private String ContactMobile;
        private String ContactEmail;
        private String ContactCompany;

        public String getContactRealName() {
            return ContactRealName;
        }

        public void setContactRealName(String ContactRealName) {
            this.ContactRealName = ContactRealName;
        }

        public String getContactMobile() {
            return ContactMobile;
        }

        public void setContactMobile(String ContactMobile) {
            this.ContactMobile = ContactMobile;
        }

        public String getContactEmail() {
            return ContactEmail;
        }

        public void setContactEmail(String ContactEmail) {
            this.ContactEmail = ContactEmail;
        }

        public String getContactCompany() {
            return ContactCompany;
        }

        public void setContactCompany(String ContactCompany) {
            this.ContactCompany = ContactCompany;
        }
    }

    public static class JoinListBean {
        private List<Integer> TicketIdList;
        private List<JoinFieldInfoListBean> JoinFieldInfoList;

        public List<Integer> getTicketIdList() {
            return TicketIdList;
        }

        public void setTicketIdList(List<Integer> TicketIdList) {
            this.TicketIdList = TicketIdList;
        }

        public List<JoinFieldInfoListBean> getJoinFieldInfoList() {
            return JoinFieldInfoList;
        }

        public void setJoinFieldInfoList(List<JoinFieldInfoListBean> JoinFieldInfoList) {
            this.JoinFieldInfoList = JoinFieldInfoList;
        }

        public static class JoinFieldInfoListBean {
            /**
             * name : RealName
             * value : 冷
             */

            private String name;
            private String value;

            public String getName() {
                return name;
            }

            public JoinFieldInfoListBean setName(String name) {
                this.name = name;
                return this;
            }

            public String getValue() {
                return value;
            }

            public JoinFieldInfoListBean setValue(String value) {
                this.value = value;
                return this;
            }
        }
    }
}
