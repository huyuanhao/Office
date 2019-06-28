package com.powerrich.office.oa.bean;

/**
 * 文 件 名：MaterialSendInfo
 * 描   述：办理材料寄送实体类
 * 作   者：Wangzheng
 * 时   间：2018/1/17
 * 版   权：v1.0
 */
public class MaterialSendInfo {
    private String register;
    private String name;
    private String phone;
    private String postcode;
    private String time;
    private String province;
    private String city;
    private String address;
    private String proKeyId;

    public String getRegister() {
        return register;
    }

    public void setRegister(String register) {
        this.register = register;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProKeyId() {
        return proKeyId;
    }

    public void setProKeyId(String proKeyId) {
        this.proKeyId = proKeyId;
    }
}
