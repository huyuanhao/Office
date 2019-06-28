package com.powerrich.office.oa.tools.network;

/**
 * @author Administrator
 * @date 2018/11/06 14:20
 */
public class CodeUtils {

    /**
     * 根据code 返回对应的msg
     *
     * @return
     */
    public static String getErrorMsg(String code) {
        String msg;
        switch (code) {
            case "201":
                msg = "失败";
                break;
            case "10001":
                msg = "缺少client_id";
                break;
            case "10002":
                msg = "Client_id非法";
                break;
            case "10003":
                msg = "缺少ticket";
                break;
            case "10004":
                msg = "Ticket非法";
                break;
            case "10005":
                msg = "系统未授权获取字段属性";
                break;
            case "10006":
                msg = "response_type值非法";
                break;
            case "10007":
                msg = "缺少client_secret";
                break;
            case "10008":
                msg = "Client_id非法或者client_secret非法";
                break;
            case "10009":
                msg = "缺少auth_code";
                break;
            case "10010":
                msg = "Auth_code非法";
                break;
            case "10011":
                msg = "缺少accessToken";
                break;
            case "10012":
                msg = "accessToken非法";
                break;
            case "10013":
                msg = "usertype 用户类型不合法";
                break;
            case "10014":
                msg = "用户名已存在";
                break;
            case "10015":
                msg = "电话号码为空或者已注册或者同一手机号码两分钟内不可频繁发送短信";
                break;
            case "10016":
                msg = "密码为空或者密码不正确";
                break;
            case "10017":
                msg = "电子邮箱为空";
                break;
            case "10018":
                msg = "统一社会信用代码、者企业工商号为空或者已注册";
                break;
            case "10019":
                msg = "用户名或密码错误";
                break;
            case "10020":
                msg = "用户id不合法";
                break;
            case "10021":
                msg = "姓名为空";
                break;
            case "10022":
                msg = "身份证为空";
                break;
            case "10023":
                msg = "电话号码为空";
                break;
            case "10024":
                msg = "密码为空";
                break;
            case "10025":
                msg = "企业类型为空或错误";
                break;
            case "10026":
                msg = "企业名称为空";
                break;
            case "10027":
                msg = "法人姓名为空";
                break;
            case "10028":
                msg = "法人身份证号为空";
                break;
            case "10029":
                msg = "关联个人用户名为空或者不存在";
                break;
            case "10030":
                msg = "无此企业";
                break;
            case "10031":
                msg = "统一社会信用代码不正确";
                break;
            case "10032":
                msg = "法定代表人不正确";
                break;
            case "10033":
                msg = "法定代表人不正确";
                break;
            case "10034":
                msg = "企业/统一社会信用代码/法定代表人有为空";
                break;
            case "10035":
                msg = "授权码不正确";
                break;
            case "10036":
                msg = "上传身份照片为空";
                break;
            case "10037":
                msg = "手机验证码为空";
                break;
            case "10038":
                msg = "手机号码或电子邮箱为空";
                break;
            case "10039":
                msg = "eID参数为空";
                break;
            case "10040":
                msg = "eID调用失败";
                break;
            case "10041":
                msg = "支付宝用户标识为空";
                break;
            case "10042":
                msg = "身份证有效起始日期为空";
                break;
            case "10043":
                msg = "身份证有效结束日期为空";
                break;
            case "10044":
                msg = "tokenSNO值为空，根据tokenSNO获取国家节点自然人和法人";
                break;
            default:
                msg = "code 错误";
                break;
        }
        return msg;
    }

}
