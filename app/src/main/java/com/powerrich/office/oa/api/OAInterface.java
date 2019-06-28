package com.powerrich.office.oa.api;

import com.google.gson.Gson;
import com.powerrich.office.oa.bean.AddressListBean;
import com.powerrich.office.oa.bean.DeclareInfo;
import com.powerrich.office.oa.bean.FileInfo;
import com.powerrich.office.oa.bean.IwantInfo;
import com.powerrich.office.oa.bean.MaterialSendInfo;
import com.powerrich.office.oa.bean.OnlineBookingInfo;
import com.powerrich.office.oa.bean.XmlInfo;
import com.powerrich.office.oa.fund.bean.FundBean;
import com.powerrich.office.oa.fund.bean.FundTqbpmParam;
import com.powerrich.office.oa.fund.bean.FundTqbusinessParam;
import com.powerrich.office.oa.fund.bean.FundTqjsrule;
import com.powerrich.office.oa.network.base.ResponseDataType;
import com.powerrich.office.oa.tools.Base64Utils;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DateUtils;
import com.powerrich.office.oa.tools.GsonUtil;
import com.powerrich.office.oa.tools.LoginUtils;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.yt.simpleframe.http.bean.xmlentity.BaseXmlInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Reader;
import java.io.Writer;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.powerrich.office.oa.fund.bean.FundBean.dwzh;
import static com.powerrich.office.oa.fund.bean.FundBean.grzh;
import static com.powerrich.office.oa.fund.bean.FundBean.khyh;
import static com.powerrich.office.oa.fund.bean.FundBean.sjhm;
import static com.powerrich.office.oa.fund.bean.FundBean.xingming;
import static com.powerrich.office.oa.fund.bean.FundBean.yhzh;
import static com.powerrich.office.oa.fund.bean.FundBean.zjbzxbm;
import static com.powerrich.office.oa.fund.bean.FundBean.zjhm;


public class OAInterface {


    /**
     * 获取授权码接口
     *
     * @return
     */
    public static ApiRequest getAuthorizationCode() {
        ApiRequest request = new ApiRequest("getAuthorizationCode.do");
        request.addParam("client_id", Constants.CLIENT_ID);
        request.setUrl(Constants.GET_AUTHORIZATION_CODE_URL);
        return request;
    }

    /**
     * 获取accessToken接口
     *
     * @param authCode
     * @return
     */
    public static ApiRequest getAccessToken(String authCode) {
        ApiRequest request = new ApiRequest("getAccessToken.do");
        request.addParam("client_secret", Constants.CLIENT_SECRET);
        request.addParam("client_id", Constants.CLIENT_ID);
        request.addParam("auth_code", authCode);
        request.setUrl(Constants.GET_ACCESS_TOKEN_URL);
        return request;
    }

    /**
     * 获取修改密码接口
     *
     * @param accessToken
     * @param oldPwd
     * @param newPwd
     * @return
     */
    public static ApiRequest updatePassword(String accessToken, String oldPwd, String newPwd) {
        ApiRequest request = new ApiRequest("userUpdatePassword.do");
        try {
            JSONObject object = new JSONObject();
            object.put("accesstoken", accessToken);
            object.put("usertype", LoginUtils.getInstance().getUserInfo().getUserType());
            JSONObject obj = new JSONObject();
            obj.put("oldpassword", oldPwd);
            obj.put("newpassword", newPwd);
            object.put("userInfo", obj);
            object.put("userid", LoginUtils.getInstance().getUserInfo().getDATA().getUSERID());
            request.addParam("params", object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request.setUrl("https://login.jxzwfww.gov.cn/user/userUpdatePassword.do");
        return request;
    }

    /**
     * 获取发送手机验证码接口
     *
     * @param phoneNumber
     * @return
     */
    public static ApiRequest sendCheckCode(String phoneNumber) {
        ApiRequest request = new ApiRequest("sendCheckCode.do");
        StringBuffer buffer = new StringBuffer();
        buffer.append("{");
        buffer.append("\"" + "phonenumber" + "\"" + ":" + "\"" + phoneNumber + "\",");
        buffer.append("\"" + "client_id" + "\"" + ":" + "\"" + Constants.CLIENT_ID + "\"");
        buffer.append("}");
        String paramsJson = buffer.toString();
        request.addParam("params", paramsJson);
        request.setUrl("https://login.jxzwfww.gov.cn/user/sendCheckCode.do");
        return request;
    }

    /**
     * 获取验证手机验证码接口
     *
     * @param phoneNumber
     * @return
     */
    public static ApiRequest checkCode(String phoneNumber, String code) {
        ApiRequest request = new ApiRequest("checkCode.do");
        StringBuffer buffer = new StringBuffer();
        buffer.append("{");
        buffer.append("\"" + "phonenumber" + "\"" + ":" + "\"" + phoneNumber + "\",");
        buffer.append("\"" + "code" + "\"" + ":" + "\"" + code + "\",");
        buffer.append("\"" + "client_id" + "\"" + ":" + "\"" + Constants.CLIENT_ID + "\"");
        buffer.append("}");
        String paramsJson = buffer.toString();
        request.addParam("params", paramsJson);
        request.setUrl("https://login.jxzwfww.gov.cn/user/checkCode.do");
        return request;
    }

    /**
     * 获取修改邮箱接口
     *
     * @param accessToken
     * @param email
     * @return
     */
    public static ApiRequest updateEmail(String accessToken, String email) {
        ApiRequest request = new ApiRequest("updateUserInfo.do");
        try {
            JSONObject object = new JSONObject();
            object.put("accesstoken", accessToken);
            object.put("usertype", LoginUtils.getInstance().getUserInfo().getUserType());
            object.put("userid", LoginUtils.getInstance().getUserInfo().getDATA().getUSERID());
            JSONObject obj = new JSONObject();
            obj.put("email", email);
            object.put("updateInfo", obj);
            request.addParam("params", object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request.setUrl("https://login.jxzwfww.gov.cn/user/updateUserInfo.do");
        return request;
    }

    /**
     * 获取修改手机号接口
     *
     * @param accessToken
     * @param phoneNumber
     * @return
     */
    public static ApiRequest updatePhoneNumber(String accessToken, String phoneNumber) {
        ApiRequest request = new ApiRequest("updateUserInfo.do");
        try {
            JSONObject object = new JSONObject();
            object.put("accesstoken", accessToken);
            object.put("usertype", LoginUtils.getInstance().getUserInfo().getUserType());
            object.put("userid", LoginUtils.getInstance().getUserInfo().getDATA().getUSERID());
            JSONObject obj = new JSONObject();
            obj.put("phoneNumber", phoneNumber);
            object.put("updateInfo", obj);
            request.addParam("params", object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request.setUrl("https://login.jxzwfww.gov.cn/user/updateUserInfo.do");
        return request;
    }

    /**
     * 登录接口
     */
    public static ApiRequest login(String type, String name, String password) {
        ApiRequest request = new ApiRequest("login");
        request.addParam("type", type);
        request.addParam("name", name);
        request.addParam("password", password);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 登录接口for EID
     */
    public static ApiRequest eidLogin(String appEidCode) {
        ApiRequest request = new ApiRequest("login");
        request.addParam("type", 2);
        request.addParam("name", appEidCode);
        request.addParam("password", "");
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 注册接口
     */
    public static ApiRequest register(String name, String password, String enterpriseName, String enterprisePerson, String phone, String userType) {
        ApiRequest request = new ApiRequest("register");
        request.addParam("name", name);
        request.addParam("password", password);
        request.addParam("enterpriseName", enterpriseName);
        request.addParam("enterprisePerson", enterprisePerson);
        request.addParam("phone", phone);
        request.addParam("userType", userType);
        request.setUrl(Constants.SERVICE_URL);
        return request;

    }

    /**
     * 注销接口
     *
     * @return
     */
    public static ApiRequest loginOut() {
        ApiRequest request = new ApiRequest("loginOut");
        request.addParam("authToken", LoginUtils.getInstance().getUserInfo().getAuthtoken());
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 根据用户名查找人员信息接口
     */
    public static ApiRequest getUserByIdInfo(String username) {
        ApiRequest request = new ApiRequest("getUserByIdInfo");
        request.addParam("username", username);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 获取版本信息接口
     */
    public static ApiRequest getVersion() {
        ApiRequest request = new ApiRequest("getVersion");
        request.addParam("TYPE", "1");
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 获取事项接口（通过用户选择, 返回相应的部门或者服务分类，如果填了子类型则查找子类型下的事项集合接口）
     */
    public static ApiRequest getServiceOrItems(String type, String tagName) {
        ApiRequest request = new ApiRequest("getServiceOrItems");
        request.addParam("type", type);
        request.addParam("tagname", tagName);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 获取事项列表接口   预约办事 + 办事指南
     */
    public static ApiRequest getEventList(String type, String content, String region_code, String normacceptdepartid, String tagid, String currentPage) {
        ApiRequest request = new ApiRequest("queryItemByConditionPageInfo");
        request.addParam("type", type);
        request.addParam("content", content);
        request.addParam("region_code", region_code);
        request.addParam("normacceptdepartid", normacceptdepartid);
        request.addParam("tagid", tagid);
        request.addParam("currentpage", currentPage);
        request.addParam("pagesize", String.valueOf(Constants.COMMON_PAGE));
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 获取事项列表接口   在线办事
     */
    public static ApiRequest getOlineEventList(String type, String content, String region_code, String normacceptdepartid, String tagid, String currentPage) {
        ApiRequest request = new ApiRequest("queryAppItemPageInfo");
        request.addParam("type", type);
        request.addParam("seach", content);
        request.addParam("region_code", region_code);
        request.addParam("normacceptdepartid", normacceptdepartid);
        request.addParam("tagid", tagid);
        request.addParam("currentpage", currentPage);
        request.addParam("pagesize", String.valueOf(Constants.COMMON_PAGE));
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }


    /**
     * 获取事项类型获取该类型下的所有事项列表接口
     */
    public static ApiRequest getItemByTagId(String type, String tagId, String itemName, String currentPage) {
        ApiRequest request = new ApiRequest("getItemByTagId");
        request.addParam("type", type);
        request.addParam("tag_id", tagId);
        request.addParam("itemName", itemName);
        request.addParam("currentpage", currentPage);
        request.addParam("pagesize", String.valueOf(Constants.COMMON_PAGE));
        request.addParam("token", LoginUtils.getInstance().getUserInfo().getAuthtoken());
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 根据事项ID获取事项信息接口
     */
    public static ApiRequest getItem(String itemId) {
        ApiRequest request = new ApiRequest("getItem");
        request.addParam("token", LoginUtils.getInstance().getUserInfo().getAuthtoken());
        request.addParam("itemId", itemId);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 咨询，投诉接口(1.我要咨询 2.我要投诉)
     */
    public static ApiRequest iWant(String type, XmlInfo xmlInfo) {
        ApiRequest request = new ApiRequest("saveExchangeInfo");
        request.addParam("token", LoginUtils.getInstance().getUserInfo().getAuthtoken());
        StringBuffer buffer = new StringBuffer();
        buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
        buffer.append("<auditIwantInfo>");
        buffer.append("<type>" + type + "</type>");
        buffer.append("<emailtype>" + xmlInfo.getEmailtype() + "</emailtype>");
        buffer.append("<dept>" + xmlInfo.getDept() + "</dept>");
        buffer.append("<deptid>" + xmlInfo.getDeptId() + "</deptid>");
        buffer.append("<title>" + xmlInfo.getTitle() + "</title>");
        buffer.append("<content>" + xmlInfo.getContent() + "</content>");
        buffer.append("<isOpen>" + xmlInfo.getIsOpen() + "</isOpen>");
        buffer.append("<userName>" + xmlInfo.getUserName() + "</userName>");
        buffer.append("<idcard>" + xmlInfo.getIdCard() + "</idcard>");
        buffer.append("<phone>" + xmlInfo.getPhone() + "</phone>");
        buffer.append("<isNote>" + (xmlInfo.isNote() ? "1" : "0") + "</isNote>");
        buffer.append("<address>" + xmlInfo.getAddress() + "</address>");
//		buffer.append("<loginTime>" + xmlInfo.getLoginTime() + "</loginTime>");
//		buffer.append("<phoneNumber>" + xmlInfo.getPhoneNumber() + "</phoneNumber>");
//        buffer.append("<email>" + xmlInfo.getEmail() + "</email>");
//        buffer.append("<isMail>" + (xmlInfo.isMail() ? "1" : "0") + "</isMail>");
//        buffer.append("<postnumber>" + xmlInfo.getPostNumber() + "</postnumber>");
//		buffer.append("<loca>" + xmlInfo.getLocal() + "</loca>");
        buffer.append("</auditIwantInfo>");
        request.addParam("xmlInfo", buffer.toString());
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 咨询，投诉接口(1.我要咨询 2.我要投诉)
     */
    public static ApiRequest iWants(String type, IwantInfo xmlInfo) {
        ApiRequest request = new ApiRequest("saveExchangeInfo");
        request.addParam("token", LoginUtils.getInstance().getUserInfo().getAuthtoken());
        xmlInfo.setType(type);
        String json = new Gson().toJson(xmlInfo);
        request.addParam("xmlInfo", json.toString());
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 办件查询接口
     */
    public static ApiRequest queryBusinessList(String state, String itemName, String currentPage) {
        ApiRequest request = new ApiRequest("queryBussinessList");
        request.addParam("token", LoginUtils.getInstance().getUserInfo().getAuthtoken());
        request.addParam("state", state);
        request.addParam("itemName", itemName);
        request.addParam("currentpage", currentPage);
        request.addParam("pagesize", Constants.COMMON_PAGE);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 根据类型查找出政策法规集合接口
     */
    public static ApiRequest laws(String type, String childType, String name) {
        ApiRequest request = new ApiRequest("laws");
        request.addParam("type", type);
        request.addParam("childType", childType);
        request.addParam("name", name);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 根据id查询政策法规信息接口
     */
    public static ApiRequest getLawById(String type, String id) {
        ApiRequest request = new ApiRequest("getLawById");
        request.addParam("type", type);
        request.addParam("id", id);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 办件详情接口
     */
    public static ApiRequest doFileDetail(String proKeyId) {
        ApiRequest request = new ApiRequest("getBusinessById");
        request.addParam("prokeyid", proKeyId);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 获取办件详情下表单详情接口
     */
    public static ApiRequest queryTabInfo(String proKeyId, String tabName) {
        ApiRequest request = new ApiRequest("queryTabInfo");
        request.addParam("prokeyid", proKeyId);
        request.addParam("tabName", tabName);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 获取预约部门列表接口
     */
    public static ApiRequest getSiteList() {
        ApiRequest request = new ApiRequest("getSiteList");
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }


    /**
     * 获取地区列表接口
     */
    public static ApiRequest getArea() {
        ApiRequest request = new ApiRequest("getRegionList");
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 发送预约短信验证码接口
     */
    public static ApiRequest getPhoneValidateCode(String phone) {
        ApiRequest request = new ApiRequest("getPhoneValiCode");
        request.addParam("phone", phone);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }



    /**
     *贷款进度信息查询
     */
    public static ApiRequest getloanProgressInformationQuery(String phone) {
        ApiRequest request = new ApiRequest("dkjdxxcx");
        request.addParam("zjbzxbm", "C36060");
        request.addParam("jkhtbh", "20180502030002");
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 预约信息提交保存接口
     */
    public static ApiRequest saveAppointmentInfo(OnlineBookingInfo bookingInfo) {
        ApiRequest request = new ApiRequest("saveAppointmentInfo");
        request.addParam("token", LoginUtils.getInstance().getUserInfo().getAuthtoken());
        StringBuffer buffer = new StringBuffer();
        buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
        buffer.append("<auditRegInfo>");
        buffer.append("<name>" + bookingInfo.getName() + "</name>");
        buffer.append("<phone>" + bookingInfo.getPhone() + "</phone>");
        buffer.append("<idcard>" + bookingInfo.getIdcard() + "</idcard>");
        buffer.append("<time>" + bookingInfo.getOrderDate() + " " + bookingInfo.getOrderTime() + "</time>");
        buffer.append("<itemId>" + bookingInfo.getItemId() + "</itemId>");
        buffer.append("<siteid>" + bookingInfo.getSiteid() + "</siteid>");
        buffer.append("</auditRegInfo>");
        request.addParam("xmlInfo", buffer.toString());
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 实名认证信息提交保存接口（个人eid）
     */
    public static ApiRequest saveUserInfoPersonal(String userName, String userDuty, String phoneNum, String realName, String idCard, String sex, String nation, String appEidCode) {
        ApiRequest request = new ApiRequest("saveUserInfo");
        request.addParam("type", "3");
        StringBuffer buffer = new StringBuffer();
        buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
        buffer.append("<auditRegInfo>");
        buffer.append("<name>" + userName + "</name>");
        buffer.append("<userduty>" + userDuty + "</userduty>");
        buffer.append("<phone>" + phoneNum + "</phone>");
        buffer.append("<realname>" + realName + "</realname>");
        buffer.append("<idcard>" + idCard + "</idcard>");
        buffer.append("<sex>" + sex + "</sex>");
        buffer.append("<ethnic>" + nation + "</ethnic>");
        buffer.append("<appeidcode>" + appEidCode + "</appeidcode>");
        buffer.append("</auditRegInfo>");
        request.addParam("xmlInfo", buffer.toString());
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 实名认证信息提交保存接口（个人人工认证）
     */
    public static ApiRequest saveUserInfoNormal(String userName, String userDuty, String phoneNum, String realName, String idCard, String sex, String nation, String address) {
        ApiRequest request = new ApiRequest("commonUserSaveInfo");
        StringBuffer buffer = new StringBuffer();
        buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
        buffer.append("<auditRegInfo>");
        buffer.append("<userduty>" + userDuty + "</userduty>");
        buffer.append("<name>" + userName + "</name>");
        buffer.append("<phone>" + phoneNum + "</phone>");
        buffer.append("<realname>" + realName + "</realname>");
        buffer.append("<idcard>" + idCard + "</idcard>");
        buffer.append("<sex>" + sex + "</sex>");
        buffer.append("<ethnic>" + nation + "</ethnic>");
        buffer.append("<address>" + address + "</address>");
        buffer.append("</auditRegInfo>");
        request.addParam("xmlInfo", buffer.toString());
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 实名认证信息提交保存接（企业）
     */
    public static ApiRequest saveUserInfo(String userName, String userDuty, String socialCreditCode, String legalName, String idCard, String phone, String address) {
        ApiRequest request = new ApiRequest("saveUserInfo");
        request.addParam("type", "3");
        StringBuffer buffer = new StringBuffer();
        buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
        buffer.append("<auditRegInfo>");
        buffer.append("<name>" + userName + "</name>");
        buffer.append("<userduty>" + userDuty + "</userduty>");
        buffer.append("<businesslicence>" + socialCreditCode + "</businesslicence>");
        buffer.append("<idcard>" + idCard + "</idcard>");
        buffer.append("<enterprisePerson>" + legalName + "</enterprisePerson>");
        buffer.append("<phone>" + phone + "</phone>");
        buffer.append("<address>" + address + "</address>");
        buffer.append("</auditRegInfo>");
        request.addParam("xmlInfo", buffer.toString());
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }


    /**
     * 实名认证信息提交保存接（企业）
     * <p>
     * <?xml version="1.0" encoding="UTF-8" standalone="yes" ?>
     * <auditRegInfo>
     * <userduty>法人类型：0企业，1社会团体，2事业单位，3行政机关</userduty>
     * <enterpriseName>组织机构名称</enterpriseName>
     * <enterpriseCode>统一社会信用代码</enterpriseCode>
     * <enterpriseAddress>组织机构地址</enterpriseAddress>
     * <enterprisePerson>法人姓名</enterprisePerson>
     * <idcard>法人身份证号码</idcard>
     * </auditRegInfo>
     */
    public static ApiRequest saveUserCompany(String token, String userduty, String enterpriseName, String enterpriseCode, String enterpriseAddress, String enterprisePerson, String idcard) {
        ApiRequest request = new ApiRequest("authenticationUserByQY");
        request.addParam("token", token);
        StringBuffer buffer = new StringBuffer();
        buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
        buffer.append("<auditRegInfo>");
        buffer.append("<userduty>" + userduty + "</userduty>");
        buffer.append("<enterpriseName>" + enterpriseName + "</enterpriseName>");
        buffer.append("<enterpriseCode>" + enterpriseCode + "</enterpriseCode>");
        buffer.append("<enterpriseAddress>" + enterpriseAddress + "</enterpriseAddress>");
        buffer.append("<enterprisePerson>" + enterprisePerson + "</enterprisePerson>");
        buffer.append("<idcard>" + idcard + "</idcard>");
        buffer.append("</auditRegInfo>");
        request.addParam("xmlInfo", buffer.toString());
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 获取该日期下所有预约时间段和预约状态接口
     */
    public static ApiRequest getItemAppointmentTime(String itemId, String time) {
        ApiRequest request = new ApiRequest("getItemAppointmentTime");
        request.addParam("itemid", itemId);
        request.addParam("time", time);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 获取个人中心个人建议，咨询，投诉列表接口
     */
    public static ApiRequest getIWantList(String type, String bizCode, String currentPage) {
        ApiRequest request = new ApiRequest("getExchangeList");
        request.addParam("token", LoginUtils.getInstance().getUserInfo().getAuthtoken());
        request.addParam("type", type);
        request.addParam("bizCode", bizCode);
        request.addParam("currentpage", currentPage);
        request.addParam("pagesize", Constants.COMMON_PAGE);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 获取个人中心个人建议，咨询，投诉详细信息接口
     */
    public static ApiRequest getIWantInfo(String type, String itemId) {
        ApiRequest request = new ApiRequest("getExchangeInfo");
        request.addParam("token", LoginUtils.getInstance().getUserInfo().getAuthtoken());
        request.addParam("type", type);
        request.addParam("jl_id", itemId);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 根据事项唯一编号获取办事套餐接口
     */
    public static ApiRequest getPackServiceByItemCode(String itemCode) {
        ApiRequest request = new ApiRequest("getPackservicbyItemCode");
        request.addParam("itemCode", itemCode);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 获取政务要闻标题列表接口
     */
    public static ApiRequest getNewsList(String type, String titleName, String currentPage) {
        ApiRequest request = new ApiRequest("getNewsTitle");
        request.addParam("TYPE", type);
        request.addParam("titleName", titleName);
        request.addParam("currentpage", currentPage);
        request.addParam("pagesize", String.valueOf(Constants.COMMON_PAGE));
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 获取政务要闻具体内容接口
     */
    public static ApiRequest getNewsDetail(String newsId) {
        ApiRequest request = new ApiRequest("getNewsById");
        request.addParam("news_id", newsId);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 获取网上办事-查询当前办理人的【基本信息】接口
     */
    public static ApiRequest getUserOrItem(String itemId, String companyId) {
        ApiRequest request = new ApiRequest("getUserOrItem");
        request.addParam("token", LoginUtils.getInstance().getUserInfo().getAuthtoken());
        request.addParam("itemId", itemId);
        request.addParam("qyid", companyId);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 用户收货地址查询
     *
     * @return
     */
    public static ApiRequest getAddressManager() {
        ApiRequest request = new ApiRequest("getAddressManager");
        request.addParam("token", LoginUtils.getInstance().getUserInfo().getAuthtoken());
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 个人用户事项申报基本信息保存接口
     *
     * @param xmlInfo
     * @return
     */
    public static ApiRequest saveItemInfo(DeclareInfo xmlInfo) {
        ApiRequest request = new ApiRequest("saveItemInfo");
        request.addParam("token", LoginUtils.getInstance().getUserInfo().getAuthtoken());
        StringBuffer buffer = new StringBuffer();
        buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
        buffer.append("<auditRegInfo>");
        buffer.append("<userid>" + xmlInfo.getUserId() + "</userid>");
        buffer.append("<itemId>" + xmlInfo.getItemId() + "</itemId>");
        buffer.append("<itemsxbm>" + xmlInfo.getItemCode() + "</itemsxbm>");
        buffer.append("<register>" + xmlInfo.getItemName() + "</register>");
        if (Constants.PERSONAL_WORK_TYPE.equals(xmlInfo.getUserType())) {
            buffer.append("<sex>" + xmlInfo.getGender() + "</sex>");
            buffer.append("<ethnic>" + xmlInfo.getNation() + "</ethnic>");
        } else {
            buffer.append("<enterpriseName>" + xmlInfo.getEnterpriseName() + "</enterpriseName>");
            buffer.append("<businesslicence>" + xmlInfo.getBusinessLicence() + "</businesslicence>");
        }
        buffer.append("<address>" + xmlInfo.getAddress() + "</address>");
        buffer.append("<realname>" + xmlInfo.getName() + "</realname>");
        buffer.append("<idcard>" + xmlInfo.getIdNumber() + "</idcard>");
        buffer.append("<phone>" + xmlInfo.getPhone() + "</phone>");
        buffer.append("<email>" + xmlInfo.getMailbox() + "</email>");
        buffer.append("<postcode>" + xmlInfo.getPostalCode() + "</postcode>");
        buffer.append("<personneltype>" + xmlInfo.getTransactorType() + "</personneltype>");
        buffer.append("<agentName>" + xmlInfo.getAgentName() + "</agentName>");
        buffer.append("<agentPhone>" + xmlInfo.getAgentPhone() + "</agentPhone>");
        buffer.append("<agentId>" + xmlInfo.getAgentId() + "</agentId>");
        buffer.append("<isExprecss>" + xmlInfo.getIsExpress() + "</isExprecss>");
        buffer.append("<exprecssId>" + xmlInfo.getExpressId() + "</exprecssId>");
        buffer.append("<prokeyid>" + xmlInfo.getProKeyId() + "</prokeyid>");
        buffer.append("<wdid>" + xmlInfo.getBranchId() + "</wdid>");
        buffer.append("</auditRegInfo>");
        request.addParam("xmlInfo", buffer.toString());
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 网上办事-动态加载事项表单接口
     */
    public static ApiRequest queryItemDynamicForm(String itemId, String proKeyId) {
        ApiRequest request = new ApiRequest("queryItemDynamicForm");
        request.addParam("itemId", itemId);
        request.addParam("prokeyid", proKeyId);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 网上办事-保存动态表单的信息接口
     */
    public static ApiRequest saveItemDynamicForm(String params) {
        if (BeanUtils.isNullOrEmpty(params)) {
            return null;
        }
        ApiRequest request = new ApiRequest("saveItemDynamicForm");
        request.addParam("Json", params);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 通过事项id获取该事项所需上传的材料接口
     */
    public static ApiRequest getItemFileList(String itemId) {
        ApiRequest request = new ApiRequest("getItemFileList");
        request.addParam("itemId", itemId);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 网上办事-保存用户上传的材料附件并提交审核接口
     */
    public static ApiRequest saveItemFileList(List<FileInfo> fileInfoList) {
        ApiRequest request = new ApiRequest("saveItemFileList");
        request.addParam("token", LoginUtils.getInstance().getUserInfo().getAuthtoken());
        StringBuffer buffer = new StringBuffer();
        String json = "";
        if (!BeanUtils.isEmpty(fileInfoList)) {
            boolean flag = false;
            buffer.append("{");
            buffer.append("\"" + "DATA" + "\"" + ":");
            buffer.append("[");
            for (FileInfo fileInfo : fileInfoList) {
                if (flag) {
                    buffer.append(",");
                }
                flag = true;
                buffer.append("{");
                buffer.append("\"" + "ITEMID" + "\"" + ":" + "\"" + fileInfo.getItemId() + "\",");
                buffer.append("\"" + "PROKEYID" + "\"" + ":" + "\"" + fileInfo.getProKeyId() + "\",");
                buffer.append("\"" + "FILEID" + "\"" + ":" + "\"" + fileInfo.getFileId() + "\",");
                buffer.append("\"" + "FILENAME" + "\"" + ":" + "\"" + fileInfo.getFileName() + "\",");
                buffer.append("\"" + "FILEPATH" + "\"" + ":" + "\"" + fileInfo.getFilePath() + "\",");
                buffer.append("\"" + "FILESIZE" + "\"" + ":" + "\"" + fileInfo.getFileSize() + "\"");
                buffer.append("}");
            }
            buffer.append("]");
            buffer.append("}");
            json = buffer.toString();
        }
        request.addParam("fileJson", json);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 删除地址
     *
     * @param addressId
     * @return
     */
    public static ApiRequest deleteAddress(String addressId) {
        ApiRequest request = new ApiRequest("deleteAddress");
        request.addParam("token", LoginUtils.getInstance().getUserInfo().getAuthtoken());
        request.addParam("userId", LoginUtils.getInstance().getUserInfo().getDATA().getUSERID());
        request.addParam("addressId", addressId);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 地址编辑和添加
     *
     * @param addressData
     * @return
     */
    public static ApiRequest modifyAddress(AddressListBean.DATABean addressData) {
        ApiRequest request = new ApiRequest("modifyAddress");
        request.addParam("token", LoginUtils.getInstance().getUserInfo().getAuthtoken());
        request.addParam("addressId", addressData.getADDRESSID());
        StringBuffer buffer = new StringBuffer();
        String xml;
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
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 网上办事-事项申报提交审核接口
     */
    public static ApiRequest submitItemAudit(String itemId, String proKeyId) {
        ApiRequest request = new ApiRequest("submitItemAudit");
        request.addParam("itemId", itemId);
        request.addParam("prokeyid", proKeyId);
        request.addParam("token", LoginUtils.getInstance().getUserInfo().getAuthtoken());
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 获取全部行政类别接口
     */
    public static ApiRequest getCategoryList() {
        ApiRequest request = new ApiRequest("getXzlbList");
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 查询阳光政务行政权力清单接口
     */
    public static ApiRequest getPowerList(String sitePower, String name, String currentPage) {
        ApiRequest request = new ApiRequest("getPowerList");
        request.addParam("site_power", sitePower);
        request.addParam("name", name);
        request.addParam("currentPage", currentPage);
        request.addParam("pageSize", String.valueOf(Constants.COMMON_PAGE));
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 行政权力清单-根据id查询行政权力清单具体详情接口
     */
    public static ApiRequest getPowerById(String pid) {
        ApiRequest request = new ApiRequest("getPowerById");
        request.addParam("pid", pid);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 市场准入清单-查询类别和领域接口
     */
    public static ApiRequest getApproveCategory() {
        ApiRequest request = new ApiRequest("getApproveCategory");
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 市场准入清单-根据类别、领域查询清单接口
     */
    public static ApiRequest getIntermediaryOrgan(String category, String territory) {
        ApiRequest request = new ApiRequest("getIntermediaryOrgan");
        request.addParam("frinash_link", category);
        request.addParam("approve_enterprise", territory);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 查询阳光政务负面公示接口
     */
    public static ApiRequest getPublicityList(String publicityId, String currentPage) {
        ApiRequest request = new ApiRequest("getFmgsList");
        request.addParam("fmgsId", publicityId);
        request.addParam("currentpage", currentPage);
        request.addParam("pagesize", String.valueOf(Constants.COMMON_PAGE));
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 查询阳光政务政策法规接口
     */
    public static ApiRequest getPolicyStatute(String type, String name, String currentPage) {
        ApiRequest request = new ApiRequest("getZcfgList");
        request.addParam("type", type);
        request.addParam("name", name);
        request.addParam("currentpage", currentPage);
        request.addParam("pagesize", String.valueOf(Constants.COMMON_PAGE));
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 根据政策法规id查询具体政策内容接口
     */
    public static ApiRequest getPolicyStatuteDetail(String type, String policyStatuteId) {
        ApiRequest request = new ApiRequest("getZcfgById");
        request.addParam("type", type);
        request.addParam("zcfgid", policyStatuteId);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 查询阳光政务部门责任清单接口
     */
    public static ApiRequest getDepartmentDutyList(String siteNo, String currentPage) {
        ApiRequest request = new ApiRequest("getBmzzList");
        request.addParam("seitId", siteNo);
        request.addParam("currentpage", currentPage);
        request.addParam("pagesize", String.valueOf(Constants.COMMON_PAGE));
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 查询阳光政务行政事业性收费清单接口
     */
    public static ApiRequest getChargeItemList(String chargeProject, String currentPage) {
        ApiRequest request = new ApiRequest("getChargeItemList");
        request.addParam("sfxm", chargeProject);
        request.addParam("currentpage", currentPage);
        request.addParam("pagesize", String.valueOf(Constants.COMMON_PAGE));
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 查询阳光政务行政事业性收费清单详情信息接口
     */
    public static ApiRequest getChargeDetail(String listId) {
        ApiRequest request = new ApiRequest("getChargeItemById");
        request.addParam("listid", listId);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 查询阳光政务查询统计接口
     */
    public static ApiRequest getQueryStatistics(String type, String year) {
        ApiRequest request = new ApiRequest("getCxtj");
        request.addParam("type", type);
        request.addParam("year", year);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 查询行政审批中介服务清单查询列表接口
     */
    public static ApiRequest getApproveServicesList(String type, String servicesName, String currentPage) {
        ApiRequest request = new ApiRequest("getZjfwqdList");
        request.addParam("type", type);
        request.addParam("zjfwmc", servicesName);
        request.addParam("currentpage", currentPage);
        request.addParam("pagesize", String.valueOf(Constants.COMMON_PAGE));
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 根据id查询行政审批中介服务项目具体详情信息接口
     */
    public static ApiRequest getAdminApproveServicesDetail(String servicesId) {
        ApiRequest request = new ApiRequest("getZjfwqdById");
        request.addParam("zjid", servicesId);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 阳光政务我要查询接口
     */
    public static ApiRequest iWantSearch(String condition) {
        ApiRequest request = new ApiRequest("iWantSearch");
        request.addParam("condition", condition);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 获取支付列表
     *
     * @param status
     * @param itemId
     * @param itemName
     * @return
     */
    public static ApiRequest getItemPays(String status, String itemId, String itemName) {
        ApiRequest request = new ApiRequest("getXzsposItemPays");
        request.addParam("token", LoginUtils.getInstance().getUserInfo().getAuthtoken());
        request.addParam("userId", LoginUtils.getInstance().getUserInfo().getDATA().getUSERID());
        request.addParam("status", status);
        request.addParam("itemId", itemId);
        request.addParam("itemName", itemName);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 事项收藏接口
     */
    public static ApiRequest collectItem(String itemCode) {
        ApiRequest request = new ApiRequest("collectItem");
        request.addParam("token", LoginUtils.getInstance().getUserInfo().getAuthtoken());
        request.addParam("sxbm", itemCode);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }


    /**
     * 新的电子证件
     * 用户id
     * 证照名称，用于模糊查询
     */
    public static ApiRequest getCertificateListNew(String userid, String name, String currentpage, String pagesize) {
        ApiRequest request = new ApiRequest("ownerLicenseList");
        request.addParam("userid", userid);
        request.addParam("name", name);
        request.addParam("currentpage", currentpage);
        request.addParam("pagesize", pagesize);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 事项取消收藏接口
     */
    public static ApiRequest cancelCollectionItem(String itemCode) {
        ApiRequest request = new ApiRequest("cancelCollectionItem");
        request.addParam("token", LoginUtils.getInstance().getUserInfo().getAuthtoken());
        request.addParam("sxbm", itemCode);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 获取部门数据以及对应部门下的所有事项
     *
     * @param siteName
     * @return
     */
    public static ApiRequest getSiteAndItemList(String siteName) {
        ApiRequest request = new ApiRequest("getSiteAndItemList");
        request.addParam("sitename", siteName);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 根据事项id和预约时间段判断当前是否可以预约
     *
     * @param itemId
     * @param time
     * @return
     */
    public static ApiRequest valiAppointmentTime(String itemId, String time) {
        ApiRequest request = new ApiRequest("valiAppointmentTime");
        request.addParam("itemid", itemId);
        request.addParam("time", time);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 获取物流列表
     *
     * @param expressNum
     * @param currentPage order_status 物流状态：0已下单，1物流中，2已签收.为空查全部
     * @return
     */
    public static ApiRequest getExpressList(String expressNum, String currentPage) {
        ApiRequest request = new ApiRequest("getExpressList");
        request.addParam("token", LoginUtils.getInstance().getUserInfo().getAuthtoken());
        request.addParam("order_status", "");
        request.addParam("expressNum", expressNum);
        request.addParam("currentpage", currentPage);
        request.addParam("pagesize", Constants.COMMON_PAGE);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 获取物流详情
     *
     * @param expressNum
     * @return
     */
    public static ApiRequest getExpressFollowInfo(String expressNum) {
        ApiRequest request = new ApiRequest("getExpressFollowInfo");
        request.addParam("token", LoginUtils.getInstance().getUserInfo().getAuthtoken());
        request.addParam("order_num", expressNum);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 获取首页三种图片base64数据接口
     */
    public static ApiRequest getHomeNewsImg() {
        ApiRequest request = new ApiRequest("getHomeNewsImg");
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 查询我的预约列表接口
     *
     * @param itemName
     * @param currentPage
     * @return
     */
    public static ApiRequest getMyAppointmentList(String itemName, String currentPage) {
        ApiRequest request = new ApiRequest("getMyAppointmentList");
        request.addParam("token", LoginUtils.getInstance().getUserInfo().getAuthtoken());
        request.addParam("itemname", itemName);
        request.addParam("currentpage", currentPage);
        request.addParam("pagesize", Constants.COMMON_PAGE);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 根据id查询预约具体的信息详情
     *
     * @param a_id
     * @return
     */
    public static ApiRequest getMyAppointmentById(String a_id) {
        ApiRequest request = new ApiRequest("getMyAppointmentById");
        request.addParam("a_id", a_id);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 收藏列表接口
     *
     * @param itemName
     * @param currentPage
     * @return
     */
    public static ApiRequest collectItemList(String itemName, String currentPage) {
        ApiRequest request = new ApiRequest("collectItemList");
        request.addParam("token", LoginUtils.getInstance().getUserInfo().getAuthtoken());
        request.addParam("itemname", itemName);
        request.addParam("currentpage", currentPage);
        request.addParam("pagesize", Constants.COMMON_PAGE);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 办理材料寄送（线上快递下单，线下快递员上门取件）接口
     *
     * @param sendInfo
     * @return
     */
    public static ApiRequest materialSendEms(MaterialSendInfo sendInfo) {
        ApiRequest request = new ApiRequest("materialOrderEms");
        request.addParam("token", LoginUtils.getInstance().getUserInfo().getAuthtoken());
        StringBuffer buffer = new StringBuffer();
        buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
        buffer.append("<auditRegInfo>");
        buffer.append("<register>" + sendInfo.getRegister() + "</register>");
        buffer.append("<name>" + sendInfo.getName() + "</name>");
        buffer.append("<phone>" + sendInfo.getPhone() + "</phone>");
        buffer.append("<postcode>" + sendInfo.getPostcode() + "</postcode>");
        buffer.append("<time>" + sendInfo.getTime() + "</time>");
        buffer.append("<prov>" + sendInfo.getProvince() + "</prov>");
        buffer.append("<city>" + sendInfo.getCity() + "</city>");
        buffer.append("<address>" + sendInfo.getAddress() + "</address>");
        buffer.append("<prokeyid>" + sendInfo.getProKeyId() + "</prokeyid>");
        buffer.append("</auditRegInfo>");
        request.addParam("xmlInfo", buffer.toString());
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 通过用户名修改密码
     *
     * @param username
     * @param password
     * @return
     */
    public static ApiRequest resetPwd(String username, String password) {
        ApiRequest request = new ApiRequest("resetPwd");
        request.addParam("username", username);
        request.addParam("password", password);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 事项申报寄送办理材料时查询用户揽件地址接口
     *
     * @param proKeyId
     * @return
     */
    public static ApiRequest getExpressAddress(String proKeyId) {
        ApiRequest request = new ApiRequest("getExpressAddress");
        request.addParam("token", LoginUtils.getInstance().getUserInfo().getAuthtoken());
        request.addParam("prokeyid", proKeyId);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 暂存件-获取已办理的步骤信息接口
     *
     * @param proKeyId
     * @return
     */
    public static ApiRequest getPosition(String proKeyId) {
        ApiRequest request = new ApiRequest("getPosition");
        request.addParam("token", LoginUtils.getInstance().getUserInfo().getAuthtoken());
        request.addParam("prokeyid", proKeyId);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 暂存件-根据业务id获取用户基本信息接口
     *
     * @param proKeyId
     * @return
     */
    public static ApiRequest getUserBaseInfo(String proKeyId) {
        ApiRequest request = new ApiRequest("getUserBaseInfoByProkeyId");
        request.addParam("token", LoginUtils.getInstance().getUserInfo().getAuthtoken());
        request.addParam("prokeyid", proKeyId);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 暂存件-获取申请材料和已上传申请材料信息接口
     *
     * @param proKeyId
     * @param itemId
     * @return
     */
    public static ApiRequest getUploadedFile(String proKeyId, String itemId) {
        ApiRequest request = new ApiRequest("getUploadedFile");
        request.addParam("token", LoginUtils.getInstance().getUserInfo().getAuthtoken());
        request.addParam("prokeyid", proKeyId);
        request.addParam("itemId", itemId);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 删除已上传的附件接口
     *
     * @param type
     * @param fileId
     * @return
     */
    public static ApiRequest deleteFileById(String type, String fileId) {
        ApiRequest request = new ApiRequest("deleteFileById");
        request.addParam("token", LoginUtils.getInstance().getUserInfo().getAuthtoken());
        request.addParam("type", type);
        request.addParam("fileid", fileId);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 办件详情-查看该笔件动态表单接口
     *
     * @param proKeyId
     * @return
     */
    public static ApiRequest queryItemDynamicFormInfo(String proKeyId) {
        ApiRequest request = new ApiRequest("queryItemDynamicFormInfo");
        request.addParam("token", LoginUtils.getInstance().getUserInfo().getAuthtoken());
        request.addParam("prokeyid", proKeyId);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 办件详情-查看该笔件已上传的附件列表接口
     *
     * @param proKeyId
     * @return
     */
    public static ApiRequest getBusinessFileList(String proKeyId) {
        ApiRequest request = new ApiRequest("getBusinessFileList");
        request.addParam("token", LoginUtils.getInstance().getUserInfo().getAuthtoken());
        request.addParam("prokeyid", proKeyId);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 综合查询接口
     *
     * @param content
     * @return
     */
    public static ApiRequest searchContent(String content) {
        ApiRequest request = new ApiRequest("searchContent");
        request.addParam("seach", content);
        request.addParam("token", LoginUtils.getInstance().getUserInfo().getAuthtoken());
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 查询更多
     *
     * @param content
     * @return
     */
    public static ApiRequest searchMore(String content, String currentpage) {
        ApiRequest request = new ApiRequest("queryItemListPageInfo");
        request.addParam("seach", content);
        request.addParam("currentpage", currentpage);
        request.addParam("pagesize", "15");
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }


    /**
     * 领导信箱列表
     *
     * @param title
     * @param currentpage
     * @return
     */
    public static ApiRequest getLeadermailList(String title, String currentpage) {
        ApiRequest request = new ApiRequest("getLeadermailList");
        request.addParam("token", LoginUtils.getInstance().getUserInfo().getAuthtoken());
        request.addParam("title", title);
        request.addParam("currentpage", currentpage);
        request.addParam("pagesize", Constants.COMMON_PAGE);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }


    /**
     * 用id获取领导信箱详情
     *
     * @param mid
     * @return
     */
    public static ApiRequest getLeadermailById(String mid) {
        ApiRequest request = new ApiRequest("getLeadermailById");
        request.addParam("mid", mid);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 新增领导信箱
     *
     * @param
     * @return
     */
    public static ApiRequest saveLeadermail(String title, String content, String dept, String deptid) {
        ApiRequest request = new ApiRequest("saveLeadermail");
        request.addParam("token", LoginUtils.getInstance().getUserInfo().getAuthtoken());
        StringBuffer buffer = new StringBuffer();
        buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
        buffer.append("<auditIwantInfo>");
        buffer.append("<title>" + title + "</title>");
        buffer.append("<content>" + content + "</content>");
        buffer.append("<dept>" + dept + "</dept>");
        buffer.append("<deptid>" + deptid + "</deptid>");
        buffer.append("</auditIwantInfo>");
        request.addParam("xmlInfo", buffer.toString());
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 首页获取12条热门事项
     *
     * @return
     */
    public static ApiRequest getHomePageItem() {
        ApiRequest request = new ApiRequest("getHomePageItem");
        request.addParam("token", LoginUtils.getInstance().getUserInfo().getAuthtoken());
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 获取经过排序的事项主题，主题下的事项越多排序越靠前
     */
    public static ApiRequest getTagsListBySort(String type, String tagName) {
        ApiRequest request = new ApiRequest("getTagsListBySort");
        request.addParam("type", type);
        request.addParam("name", tagName);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 获取经过排序的单位数据，单位下的事项越多排序越靠前
     *
     * @param siteName
     * @return
     */
    public static ApiRequest getSiteListBySort(String siteName) {
        ApiRequest request = new ApiRequest("getSiteListBySort");
        request.addParam("sitename", siteName);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 社保 登录
     */
    public static ApiRequest userLogin(String idCard, String name, String password) {
        ApiRequest request = new ApiRequest("userLogin");
        request.addParam("idCard", idCard);
        request.addParam("name", name);
        request.addParam("password", password);
        request.setUrl(Constants.SOCIAL_SECURITY_URL);
        return request;
    }

    /**
     * 社保 查询认证历史
     */
    public static ApiRequest getIdentifyList(String idCard, String name) {
        ApiRequest request = new ApiRequest("getIdentifyList");
        request.addParam("idCard", idCard);
        request.addParam("name", name);
        request.addParam("pageNo", 1);
        request.addParam("pageSize", 10);
        LoginUtils.getInstance().getUserInfo().setSysCode("1");
        request.setUrl(Constants.SOCIAL_SECURITY_URL);
        return request;
    }

    /**
     * 社保 是否能认证
     */
    public static ApiRequest ifCanIdentify(String sysNo) {
        ApiRequest request = new ApiRequest("ifCanIdentify");
        request.addParam("sysNo", sysNo);
        request.addParam("busiType", "CGRZ");
        request.setUrl(Constants.SOCIAL_SECURITY_URL);
        return request;
    }

    /**
     * 社保 比对
     */
    public static ApiRequest backendRecognize(String sysNo, String batchId, String image) {
        ApiRequest request = new ApiRequest("backendRecognize");
        request.addParam("sysNo", sysNo);
        request.addParam("batchId", batchId);
        org.json.JSONObject jsonObject = new org.json.JSONObject();
        try {
            jsonObject.put("bioType", 11);
            jsonObject.put("deviceType", "F110");
            jsonObject.put("modelType", 111);
            jsonObject.put("pics", new org.json.JSONArray().put(image));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        org.json.JSONArray jsonArray = new org.json.JSONArray();
        jsonArray.put(jsonObject);
        request.addParam("compareData", jsonArray);
        request.setUrl(Constants.SOCIAL_SECURITY_URL);
        return request;
    }

    /**
     * 社保 完成认证
     */
    public static ApiRequest completeIdentify(String sysNo, String batchId, int result) {
        ApiRequest request = new ApiRequest("completeIdentify");
        request.addParam("sysNo", sysNo);
        request.addParam("batchId", batchId);
        org.json.JSONObject jsonObject = new org.json.JSONObject();
        try {
            jsonObject.put("bioType", 11);
            jsonObject.put("result", result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        org.json.JSONArray jsonArray = new org.json.JSONArray();
        jsonArray.put(jsonObject);
        request.addParam("aliveData", jsonArray);
        request.setUrl(Constants.SOCIAL_SECURITY_URL);
        return request;
    }

    public static final String HTTP_HEAD_XML = "<v:Envelope xmlns:i=\"http://www.w3.org/1999/XMLSchema-instance\" \n" +
            "xmlns:d=\"http://www.w3.org/1999/XMLSchema\" \n" +
            "xmlns:c=\"http://schemas.xmlsoap.org/soap/encoding/\" \n" +
            "xmlns:v=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
            "<v:Header />\n" +
            "<v:Body>\n" +
            "</v:Body>\n" +
            "</v:Envelope>";
    public static final String ACTION_XML = "xmlns=\"http://xzspos.webservices.platform.powerrich.com\" id=\"o0\" " +
            "c:root=\"1\">\n";
    public static final String BODY_XML = "<v:Body>\n";
    public static final String ROOT_XML = "c:root=\"1\">\n";
    public static final String HEAD_SPACE = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?>\n";

    /**
     * 对象转XML
     *
     * @param bean
     * @return
     */
    public static String beanToXml(BaseXmlInfo bean) {
        XStream xStream = initXStream();
        xStream.alias(bean.getType(), bean.getClass());
        String str = HEAD_SPACE + xStream.toXML(bean);
        return str.replace("<", "").replace(">", "&gt;");

    }

    private static XStream sXStream;

    private static XStream initXStream() {
        if (sXStream == null) {
            sXStream = new XStream(new XppDriver() {

                @Override
                public HierarchicalStreamReader createReader(Reader in) {

                    return super.createReader(in);
                }

                @Override
                public HierarchicalStreamWriter createWriter(Writer out) {
                    return new PrettyPrintWriter(out) {
                        @Override
                        public String encodeNode(String name) {
                            return name;
                        }

                        protected void writeText(QuickWriter writer, String text) {
                            writer.write(text);
                        }
                    };
                }
            });
        }
        return sXStream;
    }

    /**
     * 不要问为什么用LinkedHashMap ，这是服务器留下的坑，只能按照文档要求依次填入数据，因为HahsMap不是依次顺序
     *
     * @param action
     * @param map
     * @return
     */
    public static String getHttpXmlStr(String action, LinkedHashMap<String, Object> map) {
        int indexAction = HTTP_HEAD_XML.indexOf(BODY_XML) + BODY_XML.length();
        StringBuffer sb1 = new StringBuffer(HTTP_HEAD_XML);
        //添加action
        String actionXmlStr = "<" + action + " " + ACTION_XML + "</" + action + ">";
        sb1.insert(indexAction, actionXmlStr);
        int indexRoot = sb1.toString().indexOf(ROOT_XML) + ROOT_XML.length();

        //配置中间值
        StringBuffer sb2 = new StringBuffer();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
//            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            String key = entry.getKey();
            Object value = entry.getValue();
            String type = "string";
            if (value instanceof String) {
            } else if (value instanceof Integer) {
                type = "int";
            } else if (value instanceof Float) {
                type = "float";
            } else if (value instanceof Long) {
                type = "long";
            }
            sb2.append("<" + key + " " + "i:type=\"d:" + type + "\">" + value + "</" + key + ">\n");
        }
        //组合 sb1 sb2
        String xmlStr = sb1.insert(indexRoot, sb2.toString()).toString();
        System.out.println("liangfan......xmlStr = " + xmlStr);
        return xmlStr;
    }

    /**
     * 【申报须知】获取部分事项信息接口
     */
    public static ApiRequest getItemWorkInfo(String itemId) {
        ApiRequest request = new ApiRequest("getItemWorkInfo");
        request.addParam("itemId", itemId);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 根据受理编号查询办事进度接口
     *
     * @param trackNumber
     * @return
     */
    public static ApiRequest getItemSchedule(String trackNumber) {
        ApiRequest request = new ApiRequest("getItemSchedule");
        request.addParam("trackingnumber", trackNumber);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 事项浏览接口
     *
     * @param itemId
     * @return
     */
    public static ApiRequest readerItem(String itemId, String browseType) {
        ApiRequest request = new ApiRequest("readerItem");
        request.addParam("token", LoginUtils.getInstance().getUserInfo().getAuthtoken());
        request.addParam("itemid", itemId);
        request.addParam("browse_type", browseType);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 查询最近浏览的事项信息(4条数据)接口
     *
     * @return
     */
    public static ApiRequest getReaderItem(String browseType) {
        ApiRequest request = new ApiRequest("getReaderItem");
        request.addParam("token", LoginUtils.getInstance().getUserInfo().getAuthtoken());
        request.addParam("browse_type", browseType);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 查询系统办件数量接口
     *
     * @return
     */
    public static ApiRequest getItemStatisticsNumber() {
        ApiRequest request = new ApiRequest("getItemStatisticeNumber");
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 取消已经预约事项（在预约时间24h内不可取消）
     *
     * @return
     */
    public static ApiRequest cancelAppointmentTimeItem(String a_id, String cause) {
        ApiRequest request = new ApiRequest("cancelAppointmentTimeItem");
        request.addParam("token", LoginUtils.getInstance().getUserInfo().getAuthtoken());
        request.addParam("a_id", a_id);
        request.addParam("cause", cause);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * /**
     * 查询公积金
     *
     * @return
     */
    public static ApiRequest getAccumulationFund(String account, String idcard) {
        ApiRequest request = new ApiRequest("getAccumulationFund");
        request.addParam("account", account);
        request.addParam("idcard", idcard);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 同步事项电子证照接口
     *
     * @param proKeyId
     * @return
     */
    public static ApiRequest syncItemElectronLicense(String proKeyId) {
        ApiRequest request = new ApiRequest("syncItemElectronLicense");
        request.addParam("token", LoginUtils.getInstance().getUserInfo().getAuthtoken());
        request.addParam("prokeyid", proKeyId);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }


    /**
     * 社保个人基本信息
     */
    public static ApiRequest getSocialInfo(String aac147) {
        ApiRequest request = new ApiRequest("personBasicInfo");
        request.addParam("aac147", aac147);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 同步同步回来的电子证照信息接口
     *
     * @param proKeyId
     * @param materialId
     * @return
     */
    public static ApiRequest getElectronLicenseFile(String proKeyId, String materialId) {
        ApiRequest request = new ApiRequest("getElectronLicenseFile");
        request.addParam("prokeyid", proKeyId);
        request.addParam("clid", materialId);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 同步事项人口库信息接口
     *
     * @param materialId
     * @param materialName
     * @param asyncJhpt
     * @param proKeyId
     * @return
     */
    public static ApiRequest syncUserInfo(String materialId, String materialName, String asyncJhpt, String proKeyId) {
        ApiRequest request = new ApiRequest("syncUserInfo");
        request.addParam("token", LoginUtils.getInstance().getUserInfo().getAuthtoken());
        request.addParam("clid", materialId);
        request.addParam("clname", materialName);
        request.addParam("async_jhpt", asyncJhpt);
        request.addParam("prokeyid", proKeyId);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 获取政务左边分类列表
     */
    public static ApiRequest GetServiceAriticleTypeListNew(String typeValue) {
        ApiRequest request = new ApiRequest("GetServiceAriticleTypeListNew");
        request.addParam("Nid", "8673");
        request.addParam("TypeValue", typeValue);
        request.setMethod(ResponseDataType.HttpMethod.GET);
        return request;
    }

    /**
     * 获取政务右边分类列表
     */
    public static ApiRequest GetClassifyNew(String pid, String typeValue) {
        ApiRequest request = new ApiRequest("GetClassifyNew");
        request.addParam("Nid", "8673");
        request.addParam("Pid", pid);
        request.addParam("TypeValue", typeValue);
        request.setMethod(ResponseDataType.HttpMethod.GET);
        return request;
    }

    /**
     * 查询当前用户的历史材料信息接口
     *
     * @param fileName
     * @param currentPage
     * @return
     */
    public static ApiRequest getAllFileListByUser(String fileName, String currentPage) {
        ApiRequest request = new ApiRequest("getAllfileListByUser");
        request.addParam("token", LoginUtils.getInstance().getUserInfo().getAuthtoken());
        request.addParam("filename", fileName);
        request.addParam("currentpage", currentPage);
        request.addParam("pagesize", Constants.COMMON_PAGE);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 事项申报时保存附件材料库信息接口
     *
     * @param proKeyId
     * @param materialName
     * @param materialId
     * @return
     */
    public static ApiRequest saveRecordFile(String proKeyId, String materialName, String materialId) {
        ApiRequest request = new ApiRequest("saveRecordFile");
        request.addParam("token", LoginUtils.getInstance().getUserInfo().getAuthtoken());
        request.addParam("prokeyid", proKeyId);
        request.addParam("sxclname", materialName);
        request.addParam("rid", materialId);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 获取办理服务网点接口
     *
     * @param name
     * @param currentPage
     * @return
     */
    public static ApiRequest queryServiceBranchList(String name, String currentPage) {
        ApiRequest request = new ApiRequest("queryFwwdList");
        request.addParam("name", name);
        request.addParam("currentpage", currentPage);
        request.addParam("pagesize", Constants.COMMON_PAGE);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 获取项目审批回执（批注）信息接口
     *
     * @param trackingNumber
     * @return
     */
    public static ApiRequest getReceiptInfo(String trackingNumber) {
        ApiRequest request = new ApiRequest("getReceiptInfo");
        request.addParam("trackingnumber", trackingNumber);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     *上传身份照片
     */
    public static ApiRequest uploadIdcardImg(String filePath, String userId, String pictype) {
        ApiRequest request = new ApiRequest("uploadIdcardImg.do");
        StringBuffer buffer = new StringBuffer();
        buffer.append("{");
        buffer.append("\"" + "file" + "\"" + ":" + "\"" + Base64Utils.file2Base64(filePath) + "\",");
        buffer.append("\"" + "userid" + "\"" + ":" + "\"" + userId + "\",");
        buffer.append("\"" + "pictype" + "\"" + ":" + "\"" + pictype + "\",");
        buffer.append("\"" + "client_id" + "\"" + ":" + "\"" + Constants.CLIENT_ID + "\"");
        buffer.append("}");
        String paramsJson = buffer.toString();
        request.addParam("params", paramsJson);
        request.setUrl("https://login.jxzwfww.gov.cn/user/uploadIdcardImg.do");
        return request;
    }

    /**
     * 没有登录的时候 上传身份照片
     */
    public static ApiRequest uploadIdcardImgCc(String filePath, String pictype,String userId) {
        ApiRequest request = new ApiRequest("uploadIdcardImg.do");
        StringBuffer buffer = new StringBuffer();
        buffer.append("{");
        buffer.append("\"" + "file" + "\"" + ":" + "\"" + Base64Utils.file2Base64(filePath) + "\",");
        buffer.append("\"" + "userid" + "\"" + ":" + "\"" + userId + "\",");
        buffer.append("\"" + "pictype" + "\"" + ":" + "\"" + pictype + "\",");
        buffer.append("\"" + "client_id" + "\"" + ":" + "\"" + Constants.CLIENT_ID + "\"");
        buffer.append("}");
        String paramsJson = buffer.toString();
        request.addParam("params", paramsJson);
        request.setUrl("https://login.jxzwfww.gov.cn/user/uploadIdcardImg.do");
        return request;
    }

    /**
     * 身份证认证
     */
    public static ApiRequest updateIdentification(String userName, String idcard, String realname) {
        ApiRequest request = new ApiRequest("updateIdentification.do");
        StringBuffer buffer = new StringBuffer();
        buffer.append("{");
        buffer.append("\"" + "username" + "\"" + ":" + "\"" + userName + "\",");
        buffer.append("\"" + "idcard" + "\"" + ":" + "\"" + idcard + "\",");
        buffer.append("\"" + "realname" + "\"" + ":" + "\"" + realname + "\",");
        buffer.append("\"" + "client_id" + "\"" + ":" + "\"" + Constants.CLIENT_ID + "\"");
        buffer.append("}");
        String paramsJson = buffer.toString();
        request.addParam("params", paramsJson);
        request.setUrl("https://login.jxzwfww.gov.cn/user/updateIdentification.do");
        return request;
    }
    /**
     * 获取用户是否存在
     */
    public static ApiRequest getUserExist(String username, String usertype) {
        ApiRequest request = new ApiRequest("getUserExist.do");
        StringBuffer buffer = new StringBuffer();
        buffer.append("{");
        buffer.append("\"" + "username" + "\"" + ":" + "\"" + username + "\",");
        buffer.append("\"" + "usertype" + "\"" + ":" + "\"" + usertype + "\",");
        buffer.append("\"" + "client_id" + "\"" + ":" + "\"" + Constants.CLIENT_ID + "\"");
        buffer.append("}");
        String paramsJson = buffer.toString();
        request.addParam("params", paramsJson);
        request.setUrl("https://login.jxzwfww.gov.cn/user/getUserExist.do");
        return request;
    }
    /**
     * 法人信息身份验证
     */
    public static ApiRequest checkFrInfo(String qyname, String qynumber, String frname, String fridcard) {
        ApiRequest request = new ApiRequest("checkFrInfo.do");
        StringBuffer buffer = new StringBuffer();
        buffer.append("{");
        buffer.append("\"" + "qyname" + "\"" + ":" + "\"" + qyname + "\",");
        buffer.append("\"" + "qynumber" + "\"" + ":" + "\"" + qynumber + "\",");
        buffer.append("\"" + "frname" + "\"" + ":" + "\"" + frname + "\",");
        buffer.append("\"" + "fridcard" + "\"" + ":" + "\"" + fridcard + "\",");
        buffer.append("\"" + "client_id" + "\"" + ":" + "\"" + Constants.CLIENT_ID + "\"");
        buffer.append("}");
        String paramsJson = buffer.toString();
        request.addParam("params", paramsJson);
        request.setUrl("https://login.jxzwfww.gov.cn/user/checkFrInfo.do");
        return request;
    }
    /**
     * 重置用户密码
     */
    public static ApiRequest resetPassword(String accessToken, String usertype, String pas, String phoneNumberOrUserid) {
        ApiRequest request = new ApiRequest("resetPassword.do");
        StringBuffer buffer = new StringBuffer();
        buffer.append("{");
        buffer.append("\"" + "accesstoken" + "\"" + ":" + "\"" + accessToken + "\",");
        buffer.append("\"" + "usertype" + "\"" + ":" + "\"" + usertype + "\",");
        buffer.append("\"" + "resetInfo" + "\"" + ":");
        buffer.append("{");
        buffer.append("\"" + "password" + "\"" + ":" + "\"" + pas + "\",");
        if ("0".equals(usertype)) {
            buffer.append("\"" + "phoneNumber" + "\"" + ":" + "\"" + phoneNumberOrUserid + "\"");
        } else {
            buffer.append("\"" + "userid" + "\"" + ":" + "\"" + phoneNumberOrUserid + "\"");
        }
        buffer.append("}");
//        buffer.append("\"" + "client_id" + "\"" + ":" + "\"" + Constants.CLIENT_ID + "\"");
        buffer.append("}");
        String paramsJson = buffer.toString();
        request.addParam("params", paramsJson);
//        request.addParam("usertype", usertype);
//        if ("1".equals(usertype)) {
//            request.addParam("userid", LoginUtils.getInstance().getUserInfo().getDATA().getUSERID());
//        }
//        request.addParam("resetInfo", pas);
        request.setUrl("https://login.jxzwfww.gov.cn/user/resetPassword.do  ");
        return request;
    }

    /**
     * 社保卡密码修改
     */
    public static ApiRequest ciBasicInfo(String idNumber, String name, String idType, String socialSecurityNo, String
            oldPassword, String newPassword) {
        ApiRequest request = new ApiRequest("ciBasicInfo");
        request.addParam("idNumber", idNumber);
        request.addParam("name", name);
        request.addParam("idType", idType);
        request.addParam("socialSecurityNo", socialSecurityNo);
        request.addParam("oldPassword", oldPassword);
        request.addParam("newPassword", newPassword);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 105.社保卡挂失解挂
     */
    public static ApiRequest ciBasicInfoGsjg(String idNumber,String name,String idType,String nation,String cardLossHandleType) {
        ApiRequest request = new ApiRequest("ciBasicInfoGsjg");
        request.addParam("idNumber", idNumber);
        request.addParam("name", name);
        request.addParam("idType", idType);
        request.addParam("nation", nation);
        request.addParam("cardLossHandleType", cardLossHandleType);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 106.【社保】个人卡进度查询
     */
    public static ApiRequest ciBasicInfoJdcx(String idNumber) {
        ApiRequest request = new ApiRequest("ciBasicInfoJdcx");
        request.addParam("idNumber", idNumber);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }
    /**
     * 根据用户名同步省统一身份认证系统用户数据
     */
    public static ApiRequest syncIdcardUserInfo(String type, String username, String password) {
        ApiRequest request = new ApiRequest("syncIdcardUserInfo");
        request.addParam("type", type);
        request.addParam("username", username);
        request.addParam("password", password);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     *
     106.【社保】1.4版本-城乡居民养老账户查询
     */
    public static ApiRequest coBasicInfo(String city,String name,String idcard) {
        ApiRequest request = new ApiRequest("coBasicInfo");
        request.addParam("city", city);
        request.addParam("aac003", name);
        request.addParam("aac147", idcard);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }


    /**
     *
     107.【社保】1.4版本-城乡居民养老发放查询
     */
    public static ApiRequest coBasicInfoFfcx(String name,String idcard,String startTime,String endTime) {
        ApiRequest request = new ApiRequest("coBasicInfoFfcx");
        request.addParam("city", "?");
        request.addParam("aac003", name);
        request.addParam("aac147",idcard);
        request.addParam("timeStart",startTime);
        request.addParam("timeEnd",endTime);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }


    /**
     *
     108.【社保】1.4版本-城乡居民养老个人参保信息查询
     */
    public static ApiRequest coBasicInfoJbxx(String city,String name,String idcard) {
        ApiRequest request = new ApiRequest("coBasicInfoJbxx");
        request.addParam("city", city);
        request.addParam("aac003", name);
        request.addParam("aac147", idcard);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     *
     109.【社保】1.4版本-城乡居民养老个人参保信息查询
     */
    public static ApiRequest coBasicInfoGrcbxx(String city,String name,String idcard) {
        ApiRequest request = new ApiRequest("coBasicInfoGrcbxx");
        request.addParam("city", city);
        request.addParam("aac003", name);
        request.addParam("aac147", idcard);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     *
     110.【社保】1.4版本-城乡居民养老社保缴费信息查询
     */
    public static ApiRequest coBasicInfoSbjfcx(String name,String idcard) {
        ApiRequest request = new ApiRequest("coBasicInfoSbjfcx");
        request.addParam("city", "?");
        request.addParam("aac003", name);
        request.addParam("aac147",idcard);
        request.addParam("aac140","?");
        request.addParam("timeStart","190001");
        request.addParam("timeEnd","205001");

        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 公积金贷款基本信息查询 (登陆入口)
     * @param zjbzxbm
     * @return
     */
    public static ApiRequest getFundLoanInfo(String zjbzxbm,String xingming,String zjhm) {
        ApiRequest request = new ApiRequest("gjjdkjbxxcx");
        request.addParam("zjbzxbm", zjbzxbm);
        request.addParam("xingming", xingming);
        request.addParam("zjhm", zjhm);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 获取公积金账户信息
     * @param zjbzxbm
     * @return
     */
    public static ApiRequest getFundInfo(String zjbzxbm,String grzh) {
        ApiRequest request = new ApiRequest("gjjzhxxcx");
        request.addParam("zjbzxbm", zjbzxbm);
        request.addParam("grzh", grzh);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 还款计划查询
     * @param zjbzxbm
     * @return
     */
    public static ApiRequest RepaymentPlan(String zjbzxbm,String jkhtbh,String ksrq,String jsrq) {
        ApiRequest request = new ApiRequest("dkhkjhcx");
        request.addParam("zjbzxbm", zjbzxbm);
        request.addParam("jkhtbh", jkhtbh);
        request.addParam("ksrq", ksrq);
        request.addParam("jsrq", jsrq);
        request.addParam("currentpage", 1);
        request.addParam("pagesize", 10);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 贷款账户信息查询
     * @param zjbzxbm
     * @return
     */
    public static ApiRequest dkzhxxcx(String zjbzxbm,String jkhtbh) {
        ApiRequest request = new ApiRequest("dkzhxxcx");
        request.addParam("zjbzxbm", zjbzxbm);
        request.addParam("jkhtbh", jkhtbh);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }
    /**
     * 贷款还款明细查询
     */
    public static ApiRequest dkhkmxcx(String zjbzxbm,String jkhtbh, String ksrq, String jsrq) {
        ApiRequest request = new ApiRequest("dkhkmxcx");
        request.addParam("zjbzxbm", zjbzxbm);
        request.addParam("jkhtbh", jkhtbh);
        request.addParam("ksrq", ksrq);
        request.addParam("jsrq", jsrq);
        request.addParam("currentpage", 1);
        request.addParam("pagesize", 10);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }
    /**
     * 计算提前结清利息
     */
    public static ApiRequest jstqjqlx(String jkhtbh, String tqhkbj) {
        ApiRequest request = new ApiRequest("tqjqService");
        request.addParam("type", "_3_4_0_Jstqjqlx");
        StringBuffer buffer = new StringBuffer();
        buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
        buffer.append("<jstqjqlxPojo>");
        buffer.append("<blqd>app</blqd>");
        buffer.append("<ffbm>01</ffbm>");
        buffer.append("<jkhtbh>" + jkhtbh + "</jkhtbh>");
        buffer.append("<ywlx>1</ywlx>");
        buffer.append("<tqhkbj>" + tqhkbj + "</tqhkbj>");
        buffer.append("<citybm>C36060</citybm>");
        buffer.append("<zjbzxbm>C36060</zjbzxbm>");
        buffer.append("</jstqjqlxPojo>");
        request.addParam("xml", buffer.toString());
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 公积金业务明细查询
     * @param zjbzxbm
     * @return
     */
    public static ApiRequest gjjywmxcx(String zjbzxbm,String grzh,String ksrq,String jsrq,int page,int size) {
        ApiRequest request = new ApiRequest("gjjywmxcx");
        request.addParam("zjbzxbm", zjbzxbm);
        request.addParam("grzh", grzh);
        request.addParam("ksrq", ksrq);
        request.addParam("jsrq", jsrq);
        request.addParam("page", page);
        request.addParam("size", size);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 公积金退休提取业务
     * 缴存人业务办理验证
     * @param
     * @return
     */
    public static ApiRequest jcrywblyz() {
        ApiRequest request = new ApiRequest("txtqService");
        request.addParam("type", "_1_1_0_Jcrywblyz");
        StringBuffer buffer = new StringBuffer();
        buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
        buffer.append("<chgjjdktqPojo>");
//        buffer.append("<ffbm>01</ffbm>");
        buffer.append("<grzh>" + grzh + "</grzh>");
        buffer.append("<dwzh>" + dwzh + "</dwzh>");
//        buffer.append("<gjhtqywlx>销户提取</gjhtqywlx>");
//        buffer.append("<userid>0</userid>");
//        buffer.append("<grbh> </grbh>");
//        buffer.append("<blqd>app</blqd>");
//        buffer.append("<zjbzxbm>" + zjbzxbm + "</zjbzxbm>");
//        buffer.append("<ywbm> </ywbm>");
//        buffer.append("<jgbm>0101</jgbm>");
//        buffer.append("<citybm>C36060</citybm>");
        buffer.append("</chgjjdktqPojo>");
        request.addParam("xml", buffer.toString());
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 公积金退休提取业务
     * 缴存人提取申请资格验证
     * @param
     * @return
     */
    public static ApiRequest jcrtqsqzgyz() {
        ApiRequest request = new ApiRequest("txtqService");
        request.addParam("type", "_1_2_0_Jcrtqsqzgyz");
        StringBuffer buffer = new StringBuffer();
        buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
        buffer.append("<chgjjdktqPojo>");
//        buffer.append("<ffbm>01</ffbm>");
        buffer.append("<grzh>" + grzh + "</grzh>");
//        buffer.append("<tqclbh> </tqclbh>");
//        buffer.append("<tqyy>0201</tqyy>");
//        buffer.append("<tqyybm>0201</tqyybm>");
        buffer.append("<tqjehj>0</tqjehj>");
//        buffer.append("<userid>0</userid>");
//        buffer.append("<grbh> </grbh>");
//        buffer.append("<blqd>app</blqd>");
//        buffer.append("<zjbzxbm>" + zjbzxbm + "</zjbzxbm>");
//        buffer.append("<ywbm> </ywbm>");
//        buffer.append("<jgbm>0101</jgbm>");
//        buffer.append("<citybm>C36060</citybm>");
        buffer.append("</chgjjdktqPojo>");
        request.addParam("xml", buffer.toString());
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 公积金退休提取业务
     * 缴存人销户利息查询
     * @param
     * @return
     */
    public static ApiRequest jcrtqxxcx() {
        ApiRequest request = new ApiRequest("txtqService");
        request.addParam("type", "_1_3_1_Jcrxhlxcx");
        StringBuffer buffer = new StringBuffer();
        buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
        buffer.append("<chgjjdktqPojo>");
//        buffer.append("<ffbm>08</ffbm>");
//        buffer.append("<blqd>app</blqd>");
//        buffer.append("<ywfl>02</ywfl>");
//        buffer.append("<ywlb>12</ywlb>");
        buffer.append("<grzh>" + grzh + "</grzh>");
        buffer.append("<dwzh>" + dwzh + "</dwzh>");
//        buffer.append("<zxbm>0101</zxbm>");
//        buffer.append("<userid>0</userid>");
//        buffer.append("<jgbm>0101</jgbm>");
//        buffer.append("<citybm>C36060</citybm>");
        buffer.append("</chgjjdktqPojo>");
        request.addParam("xml", buffer.toString());
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 公积金退休提取业务
     * 收款银行卡信息查询
     * @param
     * @return
     */
    public static ApiRequest jcrtqxxcx1() {
        ApiRequest request = new ApiRequest("txtqService");
        request.addParam("type", "_1_4_0_Jcrskyhkxxcx");
        StringBuffer buffer = new StringBuffer();
        buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
        buffer.append("<chgjjdktqPojo>");
//        buffer.append("<ffbm>03</ffbm>");
        buffer.append("<grzh>" + grzh + "</grzh>");
        buffer.append("<khyh>" + 0 + "</khyh>");
        buffer.append("<yhzh>" + 0 + "</yhzh>");
//        buffer.append("<cxlx>01</cxlx>");
//        buffer.append("<ywfl>02</ywfl>");
//        buffer.append("<ywlb>12</ywlb>");
//        buffer.append("<khbh> </khbh>");
//        buffer.append("<zhbh> </zhbh>");
//        buffer.append("<userid>0</userid>");
//        buffer.append("<jgbm>0101</jgbm>");
//        buffer.append("<citybm>C36060</citybm>");
        buffer.append("</chgjjdktqPojo>");
        request.addParam("xml", buffer.toString());
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 公积金退休提取业务
     * 缴存人添加收款银行
     * @param
     * @return
     */
    public static ApiRequest jcrxxbg(String yhzh,String skyhmc,String lhh) {
        ApiRequest request = new ApiRequest("txtqService");
        request.addParam("type", "_1_4_1_Jcrtjskyhk");
        StringBuffer buffer = new StringBuffer();
        buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
        buffer.append("<chgjjdktqPojo>");
//        buffer.append("<ffbm>03</ffbm>");
//        buffer.append("<ywfl>02</ywfl>");
//        buffer.append("<ywlb>99</ywlb>");
//        buffer.append("<blqd>app</blqd>");
//        buffer.append("<ret>99</ret>");
//        buffer.append("<msg> </msg>");
//        buffer.append("<userid>0</userid>");
        buffer.append("<xingming>" + "曾志英" + "</xingming>");
        buffer.append("<grzh>" + "000000003233" + "</grzh>");
        buffer.append("<lhh>" + lhh + "</lhh>");
        buffer.append("<skyhmc>" + skyhmc + "</skyhmc>");
        buffer.append("<yhzh>" + yhzh + "</yhzh>");
//        buffer.append("<zxbm>0101</zxbm>");
//        buffer.append("<jgbm>0101</jgbm>");
//        buffer.append("<khbh> </khbh>");
//        buffer.append("<zhbh> </zhbh>");
//        buffer.append("<grbh> </grbh>");
//        buffer.append("<citybm>C36060</citybm>");
        buffer.append("</chgjjdktqPojo>");
        request.addParam("xml", buffer.toString());
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 公积金退休提取业务
     * 验证码发送
     * @param
     * @return
     */
//    <yzmfsPojo>
//	<type></type>
//	<sjhm></sjhm>
//	<zjhm></zjhm>
//	<xingming></xingming>
//	<zxbm></zxbm>
//	<jgbm></jgbm>
//</yzmfsPojo>

    public static ApiRequest fund_smssend() {
        ApiRequest request = new ApiRequest("txtqService");
        request.addParam("type", "_1_5_0_Yzmfs");
        StringBuffer buffer = new StringBuffer();
        buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
        buffer.append("<chgjjdktqPojo>");
//        buffer.append("<type>离退休提取</type>");
        buffer.append("<sjhm>" + "13554536332" + "</sjhm>");
        buffer.append("<zjhm>" + zjhm + "</zjhm>");
        buffer.append("<xingming>" + xingming + "</xingming>");
//        buffer.append("<zxbm>0101</zxbm>");
//        buffer.append("<jgbm>0101</jgbm>");
        buffer.append("</chgjjdktqPojo>");
        request.addParam("xml", buffer.toString());
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 公积金退休提取业务
     * 验证码验证
     * @param
     * @return
     */
    public static ApiRequest fund_smsverify(String yzm) {
        ApiRequest request = new ApiRequest("txtqService");
        request.addParam("type", "_1_5_1_Yzmyz");
        StringBuffer buffer = new StringBuffer();
        buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
        buffer.append("<chgjjdktqPojo>");
        buffer.append("<sjhm>" + sjhm + "</sjhm>");
        buffer.append("<yzm>" + yzm + "</yzm>");
//        buffer.append("<yxsc>60</yxsc>");
//        buffer.append("<citybm>C36060</citybm>");
        buffer.append("</chgjjdktqPojo>");
        request.addParam("xml", buffer.toString());
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 公积金退休提取业务
     * 获取联行号
     * @param
     * @return
     */
    public static ApiRequest jcrkhdj(String khyh) {
        ApiRequest request = new ApiRequest("txtqService");
        request.addParam("type", "_1_6_0_Hqlhh");
        StringBuffer buffer = new StringBuffer();
        buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
        buffer.append("<chgjjdktqPojo>");
//        buffer.append("<ffbm>11</ffbm>");
        buffer.append("<page>1</page>");
        buffer.append("<size>20</size>");
//        buffer.append("<ywfl>01</ywfl>");
//        buffer.append("<ywlb>03</ywlb>");
//        buffer.append("<blqd>app</blqd>");
//        buffer.append("<userid>0</userid>");
//        buffer.append("<zxbm>0101</zxbm>");
//        buffer.append("<jgbm>0101</jgbm>");
//        buffer.append("<khbh> </khbh>");
//        buffer.append("<zhbh> </zhbh>");
        buffer.append("<khyh>" + khyh + "</khyh>");
//        buffer.append("<sfky>1</sfky>");
//        buffer.append("<ret>99</ret>");
//        buffer.append("<msg></msg>");
        buffer.append("</chgjjdktqPojo>");
        request.addParam("xml", buffer.toString());
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 公积金退休提取业务
     * 获取业务流水号
     * @param
     * @return
     */
    public static ApiRequest ywlshcx(String yybm) {
        ApiRequest request = new ApiRequest("txtqService");
        request.addParam("type", "_1_7_0_Hqywlsh");
        StringBuffer buffer = new StringBuffer();
        buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
        buffer.append("<chgjjdktqPojo>");
//        buffer.append("<ffbm>01</ffbm>");
//        buffer.append("<yhbm> </yhbm>");
//        buffer.append("<userid>0</userid>");
//        buffer.append("<khbh> </khbh>");
//        buffer.append("<ywfl>99</ywfl>");
//        buffer.append("<ywlb>99</ywlb>");
//        buffer.append("<zhbh> </zhbh>");
        buffer.append("<yybm>" + yybm +"</yybm>");
//        buffer.append("<jgbm>0101</jgbm>");
//        buffer.append("<blqd>app</blqd>");
//        buffer.append("<zxbm>0101</zxbm>");
//        buffer.append("<citybm>C36060</citybm>");
        buffer.append("</chgjjdktqPojo>");
        request.addParam("xml", buffer.toString());
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 公积金退休提取业务
     * 获取业务流程编号
     * @param
     * @return
     */
    public static ApiRequest ywlcbhcx(String yhzh,String skyhmc,String lhh) {
        ApiRequest request = new ApiRequest("txtqService");
        request.addParam("type", "_1_8_0_Hqywlcbh");
        StringBuffer buffer = new StringBuffer();
        buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
        buffer.append("<chgjjdktqPojo>");
//        buffer.append("<ffbm>01</ffbm>");
//        buffer.append("<userid>0</userid>");
//        buffer.append("<khbh> </khbh>");
//        buffer.append("<ywfl>99</ywfl>");
//        buffer.append("<ywlb>99</ywlb>");
//        buffer.append("<zhbh> </zhbh>");
//        buffer.append("<zxbm>0101</zxbm>");
//        buffer.append("<zjbzxbm>C36011</zjbzxbm>");
//        buffer.append("<jgbm>0101</jgbm>");
        buffer.append("</chgjjdktqPojo>");
        request.addParam("xml", buffer.toString());
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }

    /**
     * 公积金退休提取业务
     * 获取业务流程编号
     * @param
     * @return
     */
    public static ApiRequest submitGjjtq(String ywlsh,String bpmid) {
        ApiRequest request = new ApiRequest("txtqService");
        FundTqbusinessParam fundbusinessParam = new FundTqbusinessParam();
        fundbusinessParam.setYwlsh(ywlsh);
        fundbusinessParam.setBpmid(bpmid);
        String ywfsrq = DateUtils.getDateStr();
        fundbusinessParam.setYwfsrq(ywfsrq);
        String fy = FundBean.xingming + "提取" + fundbusinessParam.getTqjehj();
        fundbusinessParam.setFy(fy);

        FundTqbpmParam tqbpmParam = new FundTqbpmParam();
        tqbpmParam.setDescription(xingming + FundBean.zjhm +"退休提取" + tqbpmParam.getTqjehj());
        tqbpmParam.setBusinessKey(bpmid);

        FundTqjsrule fundTqjsrule = new FundTqjsrule();
        fundTqjsrule.setBpmid(bpmid);

        String jsonStr = "businessParam=" + GsonUtil.GsonString(fundbusinessParam) +
                "&bpmParam=" + GsonUtil.GsonString(tqbpmParam) + "&jsrule=" +
                GsonUtil.GsonString(fundTqjsrule);

        Map<String,String> map = new HashMap<>();
        map.put("zxbm","0101");
        map.put("jgbm","0101");
        map.put("ywfl","02");
        map.put("ywlb","99");
        map.put("khbh"," ");
        map.put("zhbh", grzh);
        map.put("blqd","app");
        map.put("userid","0");

        request.addParam("type", "_1_9_0_Submit");
        request.addParam("jsonStr",jsonStr);
        request.addParam("headerMap",map);
        request.setUrl(Constants.SERVICE_URL);
        return request;
    }
}
