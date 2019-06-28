package com.yt.simpleframe.http.bean.xmlentity;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/6/8
 * 版权：
 */

/**
 XmlCompanyInfo
 */



public class XmlUserInfo extends BaseXmlInfo{
    private static final long serialVersionUID = 1L;

    /**
     *
     *
     *       	<name>金牵牛</name>
     <idcard>360700199908121566</idcard>
     <address>江西省鹰潭市月湖区依安小区5号楼501</address>
     <time>1999-08-12</time>
     <ethnic>汉族</ethnic>
     <sex>1</sex>  0是男  1是女
     <appeidcode>authentication值若为1则需要eid的appeidcode信息</appeidcode>

     */

    private String name;
    private String idcard;
    private String address;
    private String time;
    private String ethnic;
    private String sex;
    private String appeidcode;

    /**
     * <?xml version="1.0" encoding="UTF-8" standalone="yes" ?>
     <auditRegInfo>
     <name>金牵牛</name>
     <idcard>360700199908121566</idcard>
     <address>江西省鹰潭市月湖区依安小区5号楼501</address>
     <time>1999-08-12</time>
     <ethnic>汉族</ethnic>
     <sex>1</sex>
     <appeidcode>authentication值若为1则需要eid的appeidcode信息</appeidcode>
     </auditRegInfo>
     * @param name
     * @param idcard
     * @param address
     * @param time
     * @param ethnic
     * @param sex
     * @param appeidcode
     */


    public XmlUserInfo(String name, String idcard, String address, String time, String ethnic, String sex, String appeidcode) {
        this.name = name;
        this.idcard = idcard;
        this.address = address;
        this.time = time;
        this.ethnic = ethnic;
        this.sex = sex;
        this.appeidcode = appeidcode;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEthnic() {
        return ethnic;
    }

    public void setEthnic(String ethnic) {
        this.ethnic = ethnic;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAppeidcode() {
        return appeidcode;
    }

    public void setAppeidcode(String appeidcode) {
        this.appeidcode = appeidcode;
    }

    @Override
    public String getType() {
        return "auditRegInfo";
    }


}
