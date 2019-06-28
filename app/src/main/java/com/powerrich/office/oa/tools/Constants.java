package com.powerrich.office.oa.tools;

import android.os.Environment;

/**
 * 常量定义工具类
 **/

public class Constants {

    /**
     * 成功编码
     */
    public static final String SUCCESS_CODE = "0";
    public static final String CODE = "200";
    public static final boolean SUCCESS = true;

    /**
     * 失败编码
     */
    public static final String LOGIN_FAIL_CODE = "-1";

    /**
     * SIMeID认证  三所地址
     */
//    public static final String SIM_EID_AUTH_URL = "http://dev.m-eid.cn/simeidas_test";
    /**
     * SIMeID认证  地址
     */
    public static final String SIM_EID_AUTH_URL = "http://111.72.252.163:18080/SIMeIDWebServer/";

    /**
     * 返回XML的error code key
     */
    public static final String XML_ERROR_CODE_KEY = "Code";
    /**
     * 返回XML的error message key
     */
    public static final String XML_ERROR_MESSAGE_KEY = "Descr";
    /**
     * 返回XML的 tag
     */
    public static final String XML_TAG_KEY = "TAG_NAME";
    /**
     * 返回XML的error tag key
     */
    public static final String XML_ERROR_TAG_KEY = "Error";
    /**
     * 返回XML的textContent
     */
    public static final String XML_TEXT_CONTENT = "TEXT_CONTENT";
    /**
     * 返回XML的children
     */
    public static final String XML_CHILDREN = "CHILDREN";

    /**
     * 返回XML的decode key
     */
    public static final String XML_BASE64_KEY = "decode";
    /**
     * 返回XML的base64
     */
    public static final String XML_BASE64_VALUE = "base64";

    /**
     * 系统请求数据源类型
     */
    public static final String DATASOURCE_HTTP = "HTTP";
    public static final String DATASOURCE_HTTPS = "HTTPS";
    public static final String DATASOURCE_SOCKET = "SOCKET";
    public static final String DATASOURCE_SQLITE = "SQLITE";
    public static final String DATASOURCE_WEBSERVICE = "WEBSERVICE";
    public static final String DATASOURCE_IO = "IO";
    /**
     * vpn是否打开 1，打开
     */
    public static final String VPN_OPEN = "1";

    /**
     * type类型1.个人办事
     */
    public static final String PERSONAL_WORK_TYPE = "2";
    /**
     * type类型2.企业办事
     */
    public static final String COMPANY_WORK_TYPE = "1";
    /**
     * type类型3.部门办事
     */
    public static final String DEPARTMENT_WORK_TYPE = "3";

    /**
     * 程序数据保存的根路径
     */
    public static final String SDString = Environment.getExternalStorageDirectory() + "";
    /**
     * SDCARD 路径
     */
    public final static String SDCARD_DIR = SDString.concat("/GTOA");
    /**
     * 新建文档的路径
     */
    public final static String SDCARD_DIR_NEW_FILE = SDCARD_DIR.concat("/filecache/");
    /**
     * 网络下载公文的文件夹
     */
    public final static String SDCARD_DIR_DOCUMENT_DOWNLOAD = SDCARD_DIR.concat("/doc_download/");
    /**
     * 下载的附件图片文件夹
     */
    public final static String SDCARD_DIR_IMAGE_DOWNLOAD = SDCARD_DIR.concat("/img_download/");
    /**
     * 下载的附件文本文件夹
     */
    public final static String SDCARD_DIR_TXT_DOWNLOAD = SDCARD_DIR.concat("/txt_download/");
    /**
     * 创建临时文件
     */
    public final static String SDCARD_TEMP_FILE = "temp.doc";

    public final static String NAME_SPACE = "http://xzspos.webservices.platform.powerrich.com";
    public final static int TIME_OUT = 10 * 1000;

//    public final static String URL_IP = "http://218.17.204.4:8066/";
    /**
     * 现场
     */
//    public final static String URL_IP = "http://122.97.217.181:80/";
//    public final static String URL_IP = "http://58.61.30.198:8099/";
    public final static String URL_IP = "http://218.87.176.156:80/";

    //江西省政务服务的域名
    public final static String URL_USER= "https://login.jxzwfww.gov.cn/";


    //江西博览会 域名
    public final static String URL_QB_USER= "http://openapi.31huiyi.com/";

    //------------------------------------   江西博览会   ------------------------------------
    //会议id
    public final static String eventId = "1655847707";
    //通道id
    public final static String ChannelId = "1664735536";
    //门票id
    public final static String TicketslId = "1664735537";

    //根据姓名和手机号获取报名用户的完整信息
    public final static String QR_OBTAIN_INFO = URL_QB_USER + "rest/event/"+eventId+"/getjoinuser";    //江西省的发送验证码

   // 提交接口
    public final static String QR_send = URL_QB_USER + "BuyTicket/"+eventId+"/BuyTicketOrderSubmit/"+ChannelId;



    //------------------------------------    江西政务服务   ------------------------------------

    //江西省的发送验证码
    public final static String SEND_CODE_URL = URL_USER + "user/sendCheckCode.do";

    //江西省的验证手机验证码
    public final static String VERIFY_CODE_URL = URL_USER + "user/checkCode.do";

    //江西省的-表14　获取用户是否存在接口表
    public final static String USEREXIST_URL = URL_USER + "user/getUserExist.do";

    //江西省的注册
    public final static String REGISTER_URL = URL_USER + "user/register.do";

    //江西省-获取 授权码
    public final static String GET_AUTHORIZATION_CODE_URL = URL_USER + "auth2/getAuthorizationCode.do";

    //江西省-获取 访问令牌
    public final static String GET_ACCESS_TOKEN_URL = URL_USER + "auth2/getAccessToken.do";





    //    public final static String SERVICE_URL = "http://218.17.204.4:8066/platform/services/XzsposServices";
    public final static String SERVICE_URL = URL_IP + "platform/services/XzsposServices";

    //    public final static String UPLOAD_URL = "http://218.17.204.4:8066/platform/servlet/APPuploadFile";
    public final static String UPLOAD_URL = URL_IP + "platform/UploadItemFileServlet?type=1";
    public final static String UPLOAD_URL4 = URL_IP + "platform/UploadItemFileServlet?type=4";
    public final static String UPLOAD_ID_CARD_URL = URL_IP + "platform/UploadItemFileServlet?type=2";
    public final static String UPLOAD_HEAD_IMG_URL = URL_IP + "platform/UploadItemFileServlet?type=5";
    public final static String UPLOAD_HEAD_FACE_URL = URL_IP + "platform/UploadItemFileServlet?type=6";
    public final static String UPLOAD_HEAD_COMPANY_URL = URL_IP + "platform/UploadItemFileServlet?type=7";
    //    public final static String URL = "http://222.184.59.8:8099/ipgs_ha/w";
    public final static String URL = "http://58.61.30.198:8099/ipgs_ha/w";
    public final static String DOWNLOAD_URL = URL_IP + "platform/DownFileServlet";
    /**鹰潭市生物识别综合服务平台*/
//    public final static String SOCIAL_SECURITY_URL = "http://117.40.214.211:7001/ws/services/MainServlet?wsdl";
    public final static String SOCIAL_SECURITY_URL = "http://111.72.252.164:7001/ws/services/MainServlet?wsdl";
    public final static String SOCIAL_SECURITY_NAME_SPACE = "http://webservice.ws.mbr.aeye.com/";

    //移动政商
    public final static String MB_URL = "https://cmswebv3.aheading.com//api/GovWindowLinkApi/";


    /**
     * 临时文件打开标识
     */
    public final static String TAG_TEMP_FILE = "temp";
    /**
     * 当前页数
     */
    public final static int CURRENT_PAGE = 1;
    /**
     * 总页数
     */
    public final static int COMMON_PAGE = 15;

    /**
     * 手势密码登录
     */
    public static final int GESTURES_OPTTYPE_LOGIN = 123;
    /**
     * 手势密码设置
     */
    public static final int GESTURES_OPTTYPE_SETPWD = 456;
    /**
     * 手势密码修改
     */
    public static final int GESTURES_OPTTYPE_MODPWD = 789;
    /**
     * 行政性规范文件
     */
    public static final String ADMINISTRATIVE_NORM_FILE = "1";
    /**
     * 地方性法规
     */
    public static final String ENDEMICITY_POLICY = "2";
    /**
     * 政府规章
     */
    public static final String GOVERNMENT_REGULATIONS = "3";
    /**
     * 政府文件
     */
    public static final String GOVERNMENT_FILE = "4";
    /**
     * 部门文件
     */
    public static final String DEPARTMENT_FILE = "5";
    /**
     * 政策解读
     */
    public static final String POLICY_UNSCRAMBLE = "6";
    /**
     * 我要咨询
     */
    public static final String CONSULTING_TYPE = "1";
    /**
     * 我要投诉
     */
    public static final String COMPLAIN_TYPE = "2";
    /**
     * 我要建议
     */
    public static final String SUGGEST_TYPE = "3";
    /**
     * 我要举报
     */
//    public static final String REPORT_TYPE = "4";

    /**
     * 市长信箱
     */
    public static final String LETTER_TYPE = "4";

    /**
     * 获取移动政商左边分类列表
     */
    public static final String GET_SERVICEARITICLETYPELISTNEW = "GetServiceAriticleTypeListNew";

    /**
     * 获取移动政商右边分类列表
     */
    public static final String GET_CLASSIFYNEW = "GetClassifyNew";
    /**
     * 是否使用同一认证
     */
    public static final boolean IS_CERTIFY = false;
    /**
     * 江西政务服务网业务系统授权密钥
     */
    public static final String CLIENT_ID = "c2e580fd36a147bdbb6519e206985a52";
    /**
     * 江西政务服务网业务系统私钥
     */
    public static final String CLIENT_SECRET = "0eee82";
}
