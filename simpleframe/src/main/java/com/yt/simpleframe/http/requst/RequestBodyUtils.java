package com.yt.simpleframe.http.requst;

import android.provider.SyncStateContract;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.yt.simpleframe.http.ApiManager;
import com.yt.simpleframe.http.RxSchedulers;
import com.yt.simpleframe.http.bean.entity.XmlInfo;
import com.yt.simpleframe.http.bean.xmlentity.BaseXmlInfo;
import com.yt.simpleframe.http.bean.xmlentity.CompanyInfo;
import com.yt.simpleframe.http.bean.xmlentity.NewsInfoBean;
import com.yt.simpleframe.http.bean.xmlentity.XmlAddressInfo;
import com.yt.simpleframe.http.bean.xmlentity.XmlCompanyInfo;
import com.yt.simpleframe.http.bean.xmlentity.XmlEidUserInfo;
import com.yt.simpleframe.http.ksoap2.transport.SoapHelper;

import java.io.Reader;
import java.io.Writer;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by fanliang on 18/5/27.
 */

public class RequestBodyUtils {
    /*登录**/
    public static int pageSize = 15;
    public static final String LOGIN = "login";

    //社保个人基本信息
    public static final String personBasicInfo = "personBasicInfo";

    public static final String GET_HOME_NEWS_IMG = "getHomeNewsImg";


    //    public static final String SAVE_COMPANY_INFO = "saveUserInfo";
    /*办件**/
    public static final String QUERY_BUSSINESS_LIST = "queryBussinessList";


//    public static final String SAVE_COMPANY_INFO = "saveUserInfo";
    /*查询当前用户的历史材料信息接口**/
    public static final String QUERYPROCESSLIST = "getAllfileListByUser";
    /**
     * 咨询
     */
    public static final String GET_EXCHANGE_LIST = "getExchangeList";
    /**
     * 我的收藏
     */
    public static final String COLLECTITEMLIST = "collectItemList";
    /**
     * 我的预约
     */
    public static final String RESERVATION_LIST = "getMyAppointmentList";
    /**
     * 获取验证码
     */
    public static final String PHONE_VALICODE = "getPhoneValiCode";
    /**
     * 重新设置密码
     */
    public static final String RESET_PWD = "resetPwd";
    /**
     * 获取物流列表
     */
    public static final String GET_EXPRESS_LIST = "getExpressList";
    /**
     * 获取最新的消息列表公告
     */
    public static final String GET_NEW_TITLE = "getNewsTitle";
    /**
     * 退出登录
     */
    public static final String LOGOUT = "loginOut";
    /**
     * 修改手机号码
     */
    public static final String MODIFY_PHONE = "modifyPhone";
    /**
     * 修改邮箱
     */
    public static final String MODIFY_EMAIL = "modifyEmail";
    /**
     * 获取收货地址
     */
    public static final String GET_ADRESS_MANAGER = "getAddressManager";
    /**
     * 添加地址
     */
    public static final String ADD_ADDRESS = "modifyAddress";

    /**
     * 添加地址
     */
    public static final String DEL_ADDRESS = "deleteAddress";
    /**
     * 注册
     */
    public static final String REGISTER = "register";

    /**
     * 实名对比人口库
     */
    public static final String COMPARE_USERINFO = "comparisonIdentityInfo";

    /**
     * 根据手机号码，查找是否有改联系人
     */
    public static final String QUERY_USER = "getUserByIdInfo";

    /**
     * 保存用户 企业信息
     */
    public static final String SAVE_COMPANY_INFO = "authenticationUserByQY";

    /**
     * 个人 eid
     */
    public static final String IDENTIFY_EID = "authenticationUserEID";
    /**
     * 批量删除办件
     */
    public static final String DEL_PROCESS = "deleteProcessOrderinfo";
    /**
     * 批量删除办件
     */
    public static final String DEL_COLLECTES = "batchCancelCollectItem";

    /**
     * 批量删除办件
     */
    public static final String GET_PUSH_MESSAGE_LIST = "getPushMessageList";
    /**
     * 获取单个咨询
     */
    public static final String GET_EXCHANGE_INFO = "getExchangeInfo";

    /**
     * 读取单个消息
     */
    public static final String GET_INFO_MSG = "getInformById";

    /**
     * 评价
     */
    public static final String EVALUATE_FLAG = "evaluateItem";


    /**
     * 获取电子证件照列表
     * 108.（附件材料库）查询当前用户的电子证照信息
     */
    public static final String GET_ALL_LICENSE_LIST = "getAllLicenseListByUser";

    /**
     * 获取电子证件照新的列表
     */
    public static final String GET_ALL_OWNERLICENSELIST = "ownerLicenseList";


    /**
     * 查看评价
     */
    public static final String GET_EVALUATEINFO = "getEvaluateInfo";

    /**
     * 获取自愿者资讯列表
     */
    public static final String GET_VOLUNTEER_LIST = "queryVolunteerList";

    /**
     * 获取自愿者资讯
     */
    public static final String GET_VOLUNTEER_INFO = "getVolunteerInfoById";

    /**
     * 获取志愿者资讯轮播图片
     */
    public static final String GET_VOLUNTEER_IMGS = "getHomeVolunteerImg";


    public static final String LOGISTICS_INFO = "getExpressFollowInfo";




    public static final String GET_NEWSTITLE = "getNewsTitle";
    public final static String NAME_SPACE = "http://xzspos.webservices.platform.powerrich.com";


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

    public static MediaType parse() {
        return MediaType.parse("application/xml;charset=UTF-8");
    }
    public static final MediaType JSON=MediaType.parse("application/json; charset=utf-8");


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
     * 对象转XML
     *
     * @param bean
     * @return
     */
    public static String beanToXml(BaseXmlInfo bean) {
        XStream xStream = initXStream();
        xStream.alias(bean.getType(), bean.getClass());
        String str = HEAD_SPACE + xStream.toXML(bean);
        return str.replaceAll("<", "&lt;").replaceAll(">", "&gt;");

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
        return xmlStr;
    }


    static Map<String, String> soapHeaderMap = new HashMap<>();
    static String mBody = "";

    public static Observable getNewsLs(String titleName, int currentPage) {
        HashMap<String, Object> properties = new HashMap<String, Object>();
        properties.put("titleName", titleName);
        properties.put("currentPage", currentPage + "");
        properties.put("pagesize", 10 + "");
        List<Object> getParamters = SoapHelper.getInstance().getParams("getNewsTitle", NAME_SPACE, properties);
        if (getParamters != null) {
            soapHeaderMap = (Map<String, String>) getParamters.get(0);
            mBody = new String((byte[]) getParamters.get(1));
        }
        Observable<NewsInfoBean> observable = ApiManager.getApi().getNews(soapHeaderMap, mBody)
                .subscribeOn(Schedulers.io())
                //指定回调在哪执行
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxSchedulers.<NewsInfoBean>io_main());
        return observable;
    }


    public static void main(String[] args) {
        System.out.println(beanToXml(new CompanyInfo("registerUserName", "registerUserduty",
                "socialCreditCode", "legalName", "legalIdNum", "legalIdNum", "legalIdNum")));
    }


    /**
     * 登录
     *
     * @param type
     * @param name
     * @param password
     * @return
     */
    public static RequestBody login(String type, String name, String password) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("type", type);
        map.put("name", name);
        map.put("password", password);
        String str = getHttpXmlStr(LOGIN, map);
        return RequestBody.create(parse(), str);
    }

    //社保个人基本信息
    public static RequestBody SocialInfo(String aac002) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("aac002", aac002);
        String str = getHttpXmlStr(personBasicInfo, map);
        return RequestBody.create(parse(), str);
    }

    //社保查询
    public static RequestBody QuerySocialInfo(String aac002,String aae140,String beginAae003,String endAae003) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("aac002", aac002);
        map.put("aae140", aae140);
        map.put("beginAae003", beginAae003);
        map.put("endAae003", endAae003);
        String str = getHttpXmlStr("findOldContributions", map);
        return RequestBody.create(parse(), str);
    }


    /**
     * 还款明细
     */
    public static RequestBody queryDkhkmxcx(String in0,String in1,String in2,String in3,String in4,String in5) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("in0", in0);
        map.put("in1", in1);
        map.put("in2", in2);
        map.put("in3", in3);
        map.put("in4", in4);
        map.put("in5", in5);
        String str = getHttpXmlStr("dkhkmxcx", map);
        return RequestBody.create(parse(), str);
    }


    /**
     * 还款计划
     */
    public static RequestBody queryHkjhcx(String in0,String in1,String in2,String in3,String in4,String in5) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("in0", in0);
        map.put("in1", in1);
        map.put("in2", in2);
        map.put("in3", in3);
        map.put("in4", in4);
        map.put("in5", in5);
        String str = getHttpXmlStr("dkhkjhcx", map);
        return RequestBody.create(parse(), str);
    }
    /**
     * 逾期未还款
     */
    public static RequestBody queryYqwhk(String in0,String in1) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("in0", in0);
        map.put("in1", in1);
        String str = getHttpXmlStr("yqwhkmxcx", map);
        return RequestBody.create(parse(), str);
    }


    //异地就医备案查询
    public static RequestBody QueryMedicalrecords(String aac002) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("aac002", aac002);
        String str = getHttpXmlStr("personEcdemicMedicalInfo", map);
        return RequestBody.create(parse(), str);
    }

    //参保缴费证明
    public static RequestBody SocialPayInfo(String aac002) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("aac002", aac002);
        String str = getHttpXmlStr("pensionSocialSecurityProof", map);
        return RequestBody.create(parse(), str);
    }

    //养老金收入证明
    public static RequestBody Pensionincome(String aac002) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("aac002", aac002);
        String str = getHttpXmlStr("pensionIncomeProof", map);
        return RequestBody.create(parse(), str);
    }

    //供水服务
    public static RequestBody WaterInfo(String account) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("account", account);
        String str = getHttpXmlStr("getWaterInfo", map);
        return RequestBody.create(parse(), str);
    }
//    /**
//     * 保存公司信息
//     *
//     * @param type
//     * @param info
//     * @return
//     */
//    public static RequestBody saveCompanyInfo(String type, BaseXmlInfo info) {
//        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
//        String xmlStr = beanToXml(info);
//        map.put("type", type);
//        map.put("xmlInfo", xmlStr);
//        String str = getHttpXmlStr(SAVE_COMPANY_INFO, map);
//        return RequestBody.create(parse(), str);
//    }


    public static RequestBody getHomeNewsImg() {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        String str = getHttpXmlStr(GET_HOME_NEWS_IMG, map);
        return RequestBody.create(parse(), str);
    }

    /**
     * 查询办件
     *
     * @param token
     * @param state
     * @param itemName    模糊匹配
     * @param currentPage
     * @param pageSize
     * @return
     */
    public static RequestBody queryProcesslist(String token, String state, String itemName, int currentPage, int pageSize) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("token", token);
        map.put("state", state);
        map.put("itemName", itemName);
        map.put("currentPage", currentPage);
        map.put("pageSize", pageSize);
        String str = getHttpXmlStr(QUERY_BUSSINESS_LIST, map);
        return RequestBody.create(parse(), str);
    }

    /**
     * 查询当前用户的历史材料信息接口
     *
     * @return
     */
    public static RequestBody queryHistoryProcesslist(String token,String fileName, String currentPage) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("token", token); //LoginUtils.getInstance().getUserInfo().getAuthtoken()
        map.put("filename", fileName);
        map.put("currentpage", currentPage);
        map.put("pagesize", "15");
        String str = getHttpXmlStr(QUERY_BUSSINESS_LIST, map);
        return RequestBody.create(parse(), str);
    }

    //获取新闻列表
    public static RequestBody getNews(String type, String titleName, int currentpage) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("TYPE", type);
        map.put("titleName", titleName);
        map.put("currentpage", currentpage + "");
        map.put("pagesize", pageSize + "");
        String str = getHttpXmlStr(GET_NEWSTITLE, map);
        return RequestBody.create(parse(), str);
    }

    /**
     * 获取个人中心个人建议，咨询，投诉列表接口
     */
    public static RequestBody getExchangeList(String token, String type, String bizCode, int currentPage, int pageSize) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("token", token);
        map.put("type", type);
        map.put("bizCode", bizCode);
        map.put("currentPage", currentPage);
        map.put("pageSize", pageSize);
        String str = getHttpXmlStr(GET_EXCHANGE_LIST, map);
        return RequestBody.create(parse(), str);
    }

    /**
     * 获取个人中心个人建议，咨询，投诉 详情
     */
    public static RequestBody getExchangeInfo(String token, String type, String jl_id) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("token", token);
        map.put("type", type);
        map.put("jl_id", jl_id);
        String str = getHttpXmlStr(GET_EXCHANGE_INFO, map);
        return RequestBody.create(parse(), str);
    }

    /**
     * 我的收藏列表接口
     *
     * @return
     */
    public static RequestBody collectItemList(String token, String itemName, int currentPage, int pageSize) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("token", token);
        map.put("itemName", itemName);
        map.put("currentPage", currentPage);
        map.put("pageSize", pageSize);
        String str = getHttpXmlStr(COLLECTITEMLIST, map);
        return RequestBody.create(parse(), str);
    }

    /**
     * 我的预约列表接口
     *
     * @return
     */
    public static RequestBody getMyAppointmentList(String token, String itemName, int currentPage, int pageSize) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("token", token);
        map.put("itemName", itemName);
        map.put("currentPage", currentPage);
        map.put("pageSize", pageSize);
        String str = getHttpXmlStr(RESERVATION_LIST, map);
        return RequestBody.create(parse(), str);
    }

    /**
     * 获取验证码接口
     */
    public static RequestBody getPhoneValiCodeList(String phone) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("phone", phone);
        String str = getHttpXmlStr(PHONE_VALICODE, map);
        return RequestBody.create(parse(), str);
    }

    /**
     * 重新设置密码
     */
    public static RequestBody resetPwd(String username, String newPassword) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("username", username);
        map.put("newPassword", newPassword);
        String str = getHttpXmlStr(RESET_PWD, map);
        return RequestBody.create(parse(), str);
    }

    /**
     * 获取物流列表
     *
     * @return
     */
    public static RequestBody getExpressList(String token, String order_status, String expressNum, int currentPage, int
            pageSize) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("token", token);
        map.put("order_status", order_status);
        map.put("expressNum", expressNum);
        map.put("currentPage", currentPage);
        map.put("pageSize", pageSize);
        String str = getHttpXmlStr(GET_EXPRESS_LIST, map);
        return RequestBody.create(parse(), str);
    }

    /**
     * 获取消息列表
     */
    public static RequestBody getNewsTitle(String titleName, int currentPage, int pageSize) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("titleName", titleName);
        map.put("currentPage", currentPage);
        map.put("pageSize", pageSize);
        String str = getHttpXmlStr(GET_NEW_TITLE, map);
        return RequestBody.create(parse(), str);
    }

    /**
     * 退出登录
     */
    public static RequestBody logout(String token) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("authToken", token);
        String str = getHttpXmlStr(LOGOUT, map);
        return RequestBody.create(parse(), str);
    }

    /**
     * 修改手机号码
     *
     * @param token
     * @param phone
     * @return
     */
    public static RequestBody modifyPhone(String token, String phone) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("authToken", token);
        map.put("phone", phone);
        String str = getHttpXmlStr(MODIFY_PHONE, map);
        return RequestBody.create(parse(), str);
    }

    /**
     * 修改手机邮箱
     *
     * @param token
     * @return
     */
    public static RequestBody modifyEmail(String token, String email, String password) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("authToken", token);
        map.put("email", email);
        map.put("password", password);
        String str = getHttpXmlStr(MODIFY_EMAIL, map);
        return RequestBody.create(parse(), str);
    }

    /**
     * 注册
     */
    public static RequestBody register( String phone,  String password) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("phone", phone);
        map.put("password", password);
        String str = getHttpXmlStr(REGISTER, map);
        return RequestBody.create(parse(), str);
    }

    /**
     * 注册
     */
    public static RequestBody comparisonIdentityInfo(String userId, String realname,  String idcard) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("userId", userId);
        map.put("realname", realname);
        map.put("idcard", idcard);
        String str = getHttpXmlStr(COMPARE_USERINFO, map);
        return RequestBody.create(parse(), str);
    }



    /**
     * 获取地址列表
     *
     * @param token
     * @return
     */
    public static RequestBody getAddressManager(String token) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("authToken", token);
        String str = getHttpXmlStr(GET_ADRESS_MANAGER, map);
        return RequestBody.create(parse(), str);
    }

    /**
     * 查询用户
     *
     * @param phone
     * @return
     */
    public static RequestBody queryUser(String phone) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("username", phone);
        String str = getHttpXmlStr(QUERY_USER, map);
        return RequestBody.create(parse(), str);
    }


    /**
     * 修改地址
     *
     * @param token
     * @param addressId
     * @param info
     * @return
     */
    public static RequestBody modifyAddress(String token, String addressId, XmlAddressInfo info) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("token", token);
        map.put("addressId", addressId);
        String xmlStr = beanToXml(info);
        map.put("xmlInfo", xmlStr);
        String str = getHttpXmlStr(ADD_ADDRESS, map);
        return RequestBody.create(parse(), str);
    }

    /**
     * 删除地址
     *
     */
    public static RequestBody delAddress(String token, String userId, String addressId) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("token", token);
        map.put("userId", userId);
        map.put("addressId", addressId);
        String str = getHttpXmlStr(DEL_ADDRESS, map);
        return RequestBody.create(parse(), str);
    }

    /**
     * 批量删除暂存办件
     *
     */
    public static RequestBody deleteProcess(String token, String json) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("token", token);
        map.put("prokeyidarray", json);
        String str = getHttpXmlStr(DEL_PROCESS, map);
        return RequestBody.create(parse(), str);
    }

    /**
     * 批量删除暂存收藏
     *
     */
    public static RequestBody deleteCollectes(String token, String json) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("TOKEN", token);
        map.put("SCIDARR", json);
        String str = getHttpXmlStr(DEL_COLLECTES, map);
        return RequestBody.create(parse(), str);
    }
    /**
     * 获取激光推送未读消息列表
     */
    public static RequestBody getPushMessageList(String token, String currentpage,String pagesize) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("token", token);
        map.put("currentpage", currentpage);
        map.put("pagesize", pagesize);
        String str = getHttpXmlStr(GET_PUSH_MESSAGE_LIST, map);
        return RequestBody.create(parse(), str);
    }

    /**
     * 读取单条消息
     */
    public static RequestBody getMessageInfo(String prokeyid) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("recordid", prokeyid);
        String str = getHttpXmlStr(GET_INFO_MSG, map);
        return RequestBody.create(parse(), str);
    }

    /**
     * 单条评价
     */
    public static RequestBody evaluate(String prokeyid, String satisfaction,String remark) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("prokeyid", prokeyid);
        map.put("satisfaction", satisfaction);
        map.put("remark", remark);
        String str = getHttpXmlStr(EVALUATE_FLAG, map);
        return RequestBody.create(parse(), str);
    }


    /**
     * 获取历史附件
     * 107-附件材料库）查询当前用户的历史材料信息
     */
    public static RequestBody getHistoryCertificateList(String token, String filename,String currentpage,String pagesize) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("token", token);
        map.put("filename", filename);
        map.put("currentpage", currentpage);
        map.put("pagesize", pagesize);
        String str = getHttpXmlStr(QUERYPROCESSLIST, map);
        return RequestBody.create(parse(), str);
    }


    /**
     * 单条评价
     */
    public static RequestBody getCertificateList(String token, String filename,String currentpage,String pagesize) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("token", token);
        map.put("filename", filename);
        map.put("currentpage", currentpage);
        map.put("pagesize", pagesize);
        String str = getHttpXmlStr(GET_ALL_LICENSE_LIST, map);
        return RequestBody.create(parse(), str);
    }

    /**
     * 新的电子证件
     * 用户id
     * 证照名称，用于模糊查询
     */
    public static RequestBody getCertificateListNew(String userid, String name,String currentpage,String pagesize) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("userid", userid);
        map.put("name", name);
        map.put("currentpage", currentpage);
        map.put("pagesize", pagesize);
        String str = getHttpXmlStr(GET_ALL_OWNERLICENSELIST, map);
        return RequestBody.create(parse(), str);
    }

    /**
     * 查看评价
     */
    public static RequestBody getEvaluateInfo(String token, String prokeyid) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("token", token);
        map.put("prokeyid", prokeyid);
        String str = getHttpXmlStr(GET_EVALUATEINFO, map);
        return RequestBody.create(parse(), str);
    }


    /**
     * 获取物流详情
     */
    public static RequestBody getLoginsticsInfo(String token, String order_num) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("token", token);
        map.put("order_num", order_num);
        String str = getHttpXmlStr(LOGISTICS_INFO, map);
        return RequestBody.create(parse(), str);
    }

    /**
     * 获取自愿者资讯列表
     */
    public static RequestBody queryVolunteerList(String titleName,String currentpage,String pagesize) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("titleNameName",titleName);
        map.put("currentpage",currentpage);
        map.put("pagesize",pagesize);
        String str = getHttpXmlStr(GET_VOLUNTEER_LIST, map);
        return RequestBody.create(parse(), str);
    }
    /**
     * 获取自愿者资讯列表
     */
    public static RequestBody queryVolunteerInfo(String getVolunteerInfoById) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("getVolunteerInfoById",getVolunteerInfoById);
        String str = getHttpXmlStr(GET_VOLUNTEER_INFO, map);
        return RequestBody.create(parse(), str);
    }

    /**
     *获取志愿者资讯轮播图片
     */
    public static RequestBody getHomeVolunteerImg() {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        String str = getHttpXmlStr(GET_VOLUNTEER_IMGS, map);
        return RequestBody.create(parse(), str);
    }

    /**
     * 保存企业信息
     * <auditRegInfo>
    <name>用户登录名</name>
    <userduty>用户类型：0个人，1企业</userduty>
    <businesslicence>社会信用代码</businesslicence>
    <idcard>法人身份证号码</idcard>
    <enterprisePerson>法人姓名</enterprisePerson>
    <phone>法人手机号</phone>
    <address>企业地址</address>
    </auditRegInfo>
     *
     */
    public static RequestBody saveCompanyInfo(String token,XmlCompanyInfo info) {

        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        String xmlStr = beanToXml(info);
        map.put("token", token);
        map.put("xmlparam", xmlStr);
        String str = getHttpXmlStr(SAVE_COMPANY_INFO, map);
        return RequestBody.create(parse(), str);
    }


    /**
     * 保存个人 eid
     * @return
     */
    public static RequestBody identifyEid(String phone ,String realname,String idcard,String eidcode) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("phone", phone);
        map.put("realname", realname);
        map.put("idcard", idcard);
        map.put("eidcode", eidcode);
        String str = getHttpXmlStr(IDENTIFY_EID, map);
        return RequestBody.create(parse(), str);
    }


    /**
     * 实名认证信息提交保存接口（个人人工认证）
     */
    public static RequestBody saveEID(XmlEidUserInfo info) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        String xmlStr = beanToXml(info);
        map.put("type", "3");
        map.put("xmlInfo", xmlStr);
        String str = getHttpXmlStr(IDENTIFY_EID, map);
        return RequestBody.create(parse(), str);
    }


    //投诉，咨询，建议
    public static RequestBody iWant(String type, XmlInfo xmlInfo) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
        buffer.append("<auditIwantInfo>");
        buffer.append("<type>" + type + "</type>");
//		buffer.append("<loginTime>" + xmlInfo.getLoginTime() + "</loginTime>");
        buffer.append("<userName>" + xmlInfo.getUserName() + "</userName>");
        buffer.append("<phone>" + xmlInfo.getPhone() + "</phone>");
//		buffer.append("<phoneNumber>" + xmlInfo.getPhoneNumber() + "</phoneNumber>");
        buffer.append("<email>" + xmlInfo.getEmail() + "</email>");
        buffer.append("<isNote>" + (xmlInfo.isNote() ? "1" : "0") + "</isNote>");
        buffer.append("<isMail>" + (xmlInfo.isMail() ? "1" : "0") + "</isMail>");
        buffer.append("<postnumber>" + xmlInfo.getPostNumber() + "</postnumber>");
        buffer.append("<address>" + xmlInfo.getAddress() + "</address>");
        buffer.append("<dept>" + xmlInfo.getDept() + "</dept>");
        buffer.append("<title>" + xmlInfo.getTitle() + "</title>");
        buffer.append("<content>" + xmlInfo.getContent() + "</content>");
//		buffer.append("<loca>" + xmlInfo.getLocal() + "</loca>");
        buffer.append("<isOpen>" + xmlInfo.getIsOpen() + "</isOpen>");
        buffer.append("</auditIwantInfo>");
        return RequestBody.create(parse(), buffer.toString().toString());
    }

}
