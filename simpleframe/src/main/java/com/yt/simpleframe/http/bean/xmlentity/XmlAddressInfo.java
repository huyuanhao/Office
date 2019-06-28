package com.yt.simpleframe.http.bean.xmlentity;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/6/8
 * 版权：
 */

/**
 buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
 buffer.append("<auditAddressInfo>");
 buffer.append("<address>" + addressData.getADDRESS() + "</address>");
 buffer.append("<companyName>" + addressData.getCOMPANY_NAME() + "</companyName>");
 buffer.append("<handset>" + addressData.getHANDSET() + "</handset>");
 buffer.append("<isDefault>" + addressData.getISDEFAULT() + "</isDefault>");
 buffer.append("<sjrxm>" + addressData.getSJRXM() + "</sjrxm>");
 buffer.append("<telNo>" + addressData.getTEL_NO() + "</telNo>");
 buffer.append("<yzbm>" + addressData.getYZBM() + "</yzbm>");
 buffer.append("<prov>" + addressData.getPROV() + "</prov>");
 buffer.append("<city>" + addressData.getCITY() + "</city>");
 buffer.append("</auditAddressInfo>");
 xml = buffer.toString();
 request.addParam("xmlInfo", xml);
 */



public class XmlAddressInfo extends BaseXmlInfo{
    private static final long serialVersionUID = 1L;

    private String address;
    private String companyName;
    private String handset;
    private String isDefault;
    private String sjrxm;
    private String telNo;
    private String yzbm;
    private String prov;
    private String city;

    @Override
    public String getType() {
        return "auditAddressInfo";
    }

    public XmlAddressInfo(String address, String companyName, String handset, String isDefault, String sjrxm, String telNo,
                          String yzbm, String prov, String city) {
        this.address = address;
        this.companyName = companyName;
        this.handset = handset;
        this.isDefault = isDefault;
        this.sjrxm = sjrxm;
        this.telNo = telNo;
        this.yzbm = yzbm;
        this.prov = prov;
        this.city = city;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getHandset() {
        return handset;
    }

    public void setHandset(String handset) {
        this.handset = handset;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getSjrxm() {
        return sjrxm;
    }

    public void setSjrxm(String sjrxm) {
        this.sjrxm = sjrxm;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getYzbm() {
        return yzbm;
    }

    public void setYzbm(String yzbm) {
        this.yzbm = yzbm;
    }

    public String getProv() {
        return prov;
    }

    public void setProv(String prov) {
        this.prov = prov;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public XmlAddressInfo() {
    }


}
