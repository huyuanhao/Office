package com.yt.simpleframe.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String Utils
 *
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2011-7-22
 */
public class StringUtil {
    private StringUtil() {
        throw new AssertionError();
    }

    /**
     * is null or its length is 0 or it is made by space
     * <p/>
     * <pre>
     * isBlank(null) = true;
     * isBlank(&quot;&quot;) = true;
     * isBlank(&quot;  &quot;) = true;
     * isBlank(&quot;a&quot;) = false;
     * isBlank(&quot;a &quot;) = false;
     * isBlank(&quot; a&quot;) = false;
     * isBlank(&quot;a b&quot;) = false;
     * </pre>
     *
     * @param str
     * @return if string is null or its size is 0 or it is made by space, return true, else return false.
     */
    public static boolean isBlank(String str) {
        return (str == null || str.trim().length() == 0);
    }

    /**
     * is null or its length is 0
     * <p/>
     * <pre>
     * isEmpty(null) = true;
     * isEmpty(&quot;&quot;) = true;
     * isEmpty(&quot;  &quot;) = false;
     * </pre>
     *
     * @param str
     * @return if string is null or its size is 0, return true, else return false.
     */
    public static boolean isEmpty(CharSequence str) {
        return (str == null || str.equals("null") || str.length() == 0);
    }

    public static void swap(Long i1, Long i2) throws Exception {
        // 这里怎么改，不能在这里输出，使main方法的a,b两值进行交换
        Long temp = new Long(i1.longValue());
        Field i1Value = i1.getClass().getDeclaredField("value");
        i1Value.setAccessible(true);
        i1Value.set(i1, i2.longValue());
        Field i2Value = i2.getClass().getDeclaredField("value");
        i2Value.setAccessible(true);
        i2Value.set(i2, temp);

    }

    /**
     * 替换手机号为星号
     *
     * @param str
     * @return
     */
    public static String replacePhone(String str) {
        String mobile = str.replaceAll("(?<=\\d{3})\\d(?=\\d{4})", "*");
        return mobile;
    }

    /**
     * 替换身份证
     *
     * @param str
     * @return
     */
    public static String replaceIdCard(String str) {
        String idcard = str.replaceAll("(?<=\\d{6})\\d(?=\\d{4})", "*");
        return idcard;
    }


    /**
     * 身份证验证
     *
     * @param card 身份编号
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean validCard(String card) {
        if (null == card) {
            return false;
        }
        card = card.toUpperCase();
        if (card.matches("^\\d{15}$")) {
            return true;
        }
        if (!card.matches("^\\d{15}|\\d{18}|\\d{17}X$")) {
            return false;
        }
        try {
            int[] part1 = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
            String result = "10X98765432";
            int sum = 0;
            for (int i = 0; i < part1.length; i++) {
                sum += part1[i] * Character.getNumericValue(card.charAt(i));
            }
            sum %= 11;
            char index = result.charAt(sum);
            return index == card.charAt(card.length() - 1);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * is null or its length is 0
     * <p/>
     * <pre>
     * isEmpty(null) = true;
     * isEmpty(&quot;&quot;) = true;
     * isEmpty(&quot;  &quot;) = false;
     * </pre>
     *
     * @param str
     * @return if string is null or its size is 0, return true, else return false.
     */
    public static boolean isNotEmpty(CharSequence str) {
        return !isEmpty(str);
    }

    /**
     * is null or its length is 0
     * <p/>
     * <pre>
     * isEmpty(null) = true;
     * isEmpty(&quot;&quot;) = true;
     * isEmpty(&quot;  &quot;) = false;
     * </pre>
     *
     * @param str
     * @return if string is null or its size is 0, return true, else return false.
     */
    public static boolean isEmpty(String str) {
        return (str == null || str.length() == 0 || str.equals("null"));
    }

    /**
     * compare two string
     *
     * @param actual
     * @param expected
     * @return
     * @see ObjectUtils#isEquals(Object, Object)
     */
    public static boolean isEquals(String actual, String expected) {
        return ObjectUtils.isEquals(actual, expected);
    }

    /**
     * 判断一个数组是否包含某元素
     *
     * @param arr
     * @param targetValue
     * @return
     */
    public static boolean useList(String[] arr, String targetValue) {
        return Arrays.asList(arr).contains(targetValue);
    }

    /**
     * get length of CharSequence
     * <p/>
     * <pre>
     * length(null) = 0;
     * length(\"\") = 0;
     * length(\"abc\") = 3;
     * </pre>
     *
     * @param str
     * @return if str is null or empty, return 0, else return {@link CharSequence#length()}.
     */
    public static int length(CharSequence str) {
        return str == null ? 0 : str.length();
    }

    /**
     * null Object to empty string
     * <p/>
     * <pre>
     * nullStrToEmpty(null) = &quot;&quot;;
     * nullStrToEmpty(&quot;&quot;) = &quot;&quot;;
     * nullStrToEmpty(&quot;aa&quot;) = &quot;aa&quot;;
     * </pre>
     *
     * @param str
     * @return
     */
    public static String nullStrToEmpty(Object str) {
        return (str == null ? "" : (str instanceof String ? (String) str : str.toString()));
    }

    /**
     * capitalize first letter
     * <p/>
     * <pre>
     * capitalizeFirstLetter(null)     =   null;
     * capitalizeFirstLetter("")       =   "";
     * capitalizeFirstLetter("2ab")    =   "2ab"
     * capitalizeFirstLetter("a")      =   "A"
     * capitalizeFirstLetter("ab")     =   "Ab"
     * capitalizeFirstLetter("Abc")    =   "Abc"
     * </pre>
     *
     * @param str
     * @return
     */
    public static String capitalizeFirstLetter(String str) {
        if (isEmpty(str)) {
            return str;
        }

        char c = str.charAt(0);
        return (!Character.isLetter(c) || Character.isUpperCase(c)) ? str : new StringBuilder(str.length())
                .append(Character.toUpperCase(c)).append(str.substring(1)).toString();
    }

    /**
     * encoded in utf-8
     * <p/>
     * <pre>
     * utf8Encode(null)        =   null
     * utf8Encode("")          =   "";
     * utf8Encode("aa")        =   "aa";
     * utf8Encode("啊啊啊啊")   = "%E5%95%8A%E5%95%8A%E5%95%8A%E5%95%8A";
     * </pre>
     *
     * @param str
     * @return
     * @throws UnsupportedEncodingException if an error occurs
     */
    public static String utf8Encode(String str) {
        if (!isEmpty(str) && str.getBytes().length != str.length()) {
            try {
                return URLEncoder.encode(str, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("UnsupportedEncodingException occurred. ", e);
            }
        }
        return str;
    }

    /**
     * encoded in utf-8, if exception, return defultReturn
     *
     * @param str
     * @param defultReturn
     * @return
     */
    public static String utf8Encode(String str, String defultReturn) {
        if (!isEmpty(str) && str.getBytes().length != str.length()) {
            try {
                return URLEncoder.encode(str, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                return defultReturn;
            }
        }
        return str;
    }

    /**
     * get innerHtml from href
     * <p/>
     * <pre>
     * getHrefInnerHtml(null)                                  = ""
     * getHrefInnerHtml("")                                    = ""
     * getHrefInnerHtml("mp3")                                 = "mp3";
     * getHrefInnerHtml("&lt;a innerHtml&lt;/a&gt;")                    = "&lt;a innerHtml&lt;/a&gt;";
     * getHrefInnerHtml("&lt;a&gt;innerHtml&lt;/a&gt;")                    = "innerHtml";
     * getHrefInnerHtml("&lt;a&lt;a&gt;innerHtml&lt;/a&gt;")                    = "innerHtml";
     * getHrefInnerHtml("&lt;a href="baidu.com"&gt;innerHtml&lt;/a&gt;")               = "innerHtml";
     * getHrefInnerHtml("&lt;a href="baidu.com" title="baidu"&gt;innerHtml&lt;/a&gt;") = "innerHtml";
     * getHrefInnerHtml("   &lt;a&gt;innerHtml&lt;/a&gt;  ")                           = "innerHtml";
     * getHrefInnerHtml("&lt;a&gt;innerHtml&lt;/a&gt;&lt;/a&gt;")                      = "innerHtml";
     * getHrefInnerHtml("jack&lt;a&gt;innerHtml&lt;/a&gt;&lt;/a&gt;")                  = "innerHtml";
     * getHrefInnerHtml("&lt;a&gt;innerHtml1&lt;/a&gt;&lt;a&gt;innerHtml2&lt;/a&gt;")        = "innerHtml2";
     * </pre>
     *
     * @param href
     * @return <ul>
     * <li>if href is null, return ""</li>
     * <li>if not match regx, return source</li>
     * <li>return the last string that match regx</li>
     * </ul>
     */
    public static String getHrefInnerHtml(String href) {
        if (isEmpty(href)) {
            return "";
        }

        String hrefReg = ".*<[\\s]*a[\\s]*.*>(.+?)<[\\s]*/a[\\s]*>.*";
        Pattern hrefPattern = Pattern.compile(hrefReg, Pattern.CASE_INSENSITIVE);
        Matcher hrefMatcher = hrefPattern.matcher(href);
        if (hrefMatcher.matches()) {
            return hrefMatcher.group(1);
        }
        return href;
    }

    public static String formatDouble(Double d) {
        DecimalFormat df = new DecimalFormat("0.0");
        return df.format(d);
    }

    /**
     * process special char in html
     * <p/>
     * <pre>
     * htmlEscapeCharsToString(null) = null;
     * htmlEscapeCharsToString("") = "";
     * htmlEscapeCharsToString("mp3") = "mp3";
     * htmlEscapeCharsToString("mp3&lt;") = "mp3<";
     * htmlEscapeCharsToString("mp3&gt;") = "mp3\>";
     * htmlEscapeCharsToString("mp3&amp;mp4") = "mp3&mp4";
     * htmlEscapeCharsToString("mp3&quot;mp4") = "mp3\"mp4";
     * htmlEscapeCharsToString("mp3&lt;&gt;&amp;&quot;mp4") = "mp3\<\>&\"mp4";
     * </pre>
     *
     * @param source
     * @return
     */
    public static String htmlEscapeCharsToString(String source) {
        return StringUtil.isEmpty(source) ? source : source.replaceAll("&lt;", "<").replaceAll("&gt;", ">")
                .replaceAll("&amp;", "&").replaceAll("&quot;", "\"");
    }

    /**
     * transform half width char to full width char
     * <p/>
     * <pre>
     * fullWidthToHalfWidth(null) = null;
     * fullWidthToHalfWidth("") = "";
     * fullWidthToHalfWidth(new String(new char[] {12288})) = " ";
     * fullWidthToHalfWidth("！＂＃＄％＆) = "!\"#$%&";
     * </pre>
     *
     * @param s
     * @return
     */
    public static String fullWidthToHalfWidth(String s) {
        if (isEmpty(s)) {
            return s;
        }

        char[] source = s.toCharArray();
        for (int i = 0; i < source.length; i++) {
            if (source[i] == 12288) {
                source[i] = ' ';
                // } else if (source[i] == 12290) {
                // source[i] = '.';
            } else if (source[i] >= 65281 && source[i] <= 65374) {
                source[i] = (char) (source[i] - 65248);
            } else {
                source[i] = source[i];
            }
        }
        return new String(source);
    }

    /**
     * transform full width char to half width char
     * <p/>
     * <pre>
     * halfWidthToFullWidth(null) = null;
     * halfWidthToFullWidth("") = "";
     * halfWidthToFullWidth(" ") = new String(new char[] {12288});
     * halfWidthToFullWidth("!\"#$%&) = "！＂＃＄％＆";
     * </pre>
     *
     * @param s
     * @return
     */
    public static String halfWidthToFullWidth(String s) {
        if (isEmpty(s)) {
            return s;
        }

        char[] source = s.toCharArray();
        for (int i = 0; i < source.length; i++) {
            if (source[i] == ' ') {
                source[i] = (char) 12288;
                // } else if (source[i] == '.') {
                // source[i] = (char)12290;
            } else if (source[i] >= 33 && source[i] <= 126) {
                source[i] = (char) (source[i] + 65248);
            } else {
                source[i] = source[i];
            }
        }
        return new String(source);
    }

    public static String toString(String s) {
        if (isEmpty(s))
            return "";
        return s;
    }

    public static String DecimalFormat(String str) {
        DecimalFormat df = new DecimalFormat("0.00");

        return df.format(Double.parseDouble(str));
    }

    public static String DecimalFormat(Double str) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(str);
    }

    public static List<String> stringToList(String labels) {
        List<String> lableList = new ArrayList<String>();
        if (!TextUtils.isEmpty(labels)) {
            String[] lableStrs = labels.split(",");
            for (int i = 0; i < lableStrs.length; i++) {
                lableList.add(lableStrs[i]);
            }
        }
        return lableList;
    }


    /***
     * 将距离 /1000 保留两位小数
     */
    public static String formatDistance(String distance) {
        String str = "0";
        if (!TextUtils.isEmpty(distance)) {
            try {
                DecimalFormat df = new DecimalFormat("0.0");//格式化小数
                str = df.format(Double.valueOf(distance) / 1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return str + "KM";
    }


    public static String formatStrings(String[] strs) {
        StringBuilder sb = new StringBuilder();
        if (strs != null) {
            for (int i = 0; i < strs.length; i++) {
                if (i == (strs.length - 1)) {
                    sb.append(strs[i]);
                    break;
                }
                sb.append(strs[i]).append(",");
            }
        }
        return sb.toString();

    }


    /**
     * 验证手机号码
     *
     * @param mobiles
     * @return [0-9]{5,9}
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern
                .compile("^0{0,1}(13[0-9]|15[0-9]|16[0-9]|18[0-9]|17[0-9]|14[0-9]|19[0-9])[0-9]{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 验证邮箱是否争取
     *
     * @param email
     * @return
     */
    public static boolean checkEmail(String email) {
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    public static boolean checkZipCode(String zipCode) {
        boolean flag = false;
        try {
            String check = "^[0-9]{6}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(zipCode);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }


    /**
     * （字符 或 数字） || （字符+数字）
     * @param name
     * @return
     */
    public static boolean checkUserName(String name) {
        String regex = "^[A-Za-z0-9-]+$";
        return name.matches(regex);
    }

    /**
     * 验证只能包含数字和字母
     * @param idCard
     * @return
     */
    public static boolean checkStrUserName(String idCard) {
        boolean flag = false;
        if(checkStrLeng(idCard)){
            if(checkStrContent(idCard)){
                flag =true;
            }
        }
        return flag;
    }


    /**
     * 验证只能包含数字和字母
     * @param idCard
     * @return
     */
    public static boolean checkStrContent(String idCard) {
        String regex = "[a-zA-Z0-9]+";
        return Pattern.matches(regex, idCard);
    }

    /**
     * 验证长度在6~18位
     * @param idCard
     * @return
     */
    public static boolean checkStrLeng(String idCard) {
        String pattern = "[\\w]+";
        String regex = "^.{6,18}$";
        return Pattern.matches(regex, idCard);
    }

    /**
     * 验证已字母开头  而且只能包含字母和数字
     * @param idCard
     * @return
     */
    public static boolean checkStrContentCc(String idCard) {
        String regex = "^[a-zA-Z][a-zA-Z0-9]*$";
        return Pattern.matches(regex, idCard);
    }



    /**
     * @param pwd
     * @return 0密码长度不够 1 简单 2中等 3难
     */
    public static int checkPwdComplexity(String pwd) {

        if (pwd.length() < 6) {
            return 0;
        }

        String regexZ = "\\d*";
        String regexS = "[a-zA-Z]+";
        String regexT = "\\W+$";
        String regexZT = "\\D*";
        String regexST = "[\\d\\W]*";
        String regexZS = "\\w*";
        String regexZST = "[\\w\\W]*";

        if (pwd.matches(regexZ)) {
            return 1;
        }
        if (pwd.matches(regexS)) {
            return 1;
        }
        if (pwd.matches(regexT)) {
            return 1;
        }
        if (pwd.matches(regexZT)) {
            return 2;
        }
        if (pwd.matches(regexST)) {
            return 2;
        }
        if (pwd.matches(regexZS)) {
            return 2;
        }
        if (pwd.matches(regexZST)) {
            return 3;
        }
        return 1;
    }
//        int flag = 1;
//        String complexity1 = "^(?:\\d+|[a-zA-Z]+|[!@#$%^&*]+)$";
//        String complexity2 = "^(?![a-zA-z]+$)(?!\\\\d+$)(?![!@#$%^&*]+$)[a-zA-Z\\\\d!@#$%^&*]+$";
//        String complexity3 = "^^(?![a-zA-z]+$)(?!\\\\d+$)(?![!@#$%^&*_-]+$)(?![a-zA-z\\\\d]+$)(?![a-zA-z!@#$%^&*_-]+$)" +
//                "(?![\\\\d!@#$%^&*_-]+$)[a-zA-Z\\\\d!@#$%^&*_-]+$";
//
//        Pattern regex1 = Pattern.compile(complexity1);
//        Pattern regex2 = Pattern.compile(complexity2);
//        Pattern regex3 = Pattern.compile(complexity3);
//
//        Matcher matcher1 = regex1.matcher(pwd);
//        Matcher matcher2 = regex2.matcher(pwd);
//            Matcher matcher3 = regex3.matcher(pwd);
//
//
//        if(matcher3.matches()){
//            return 3;
//        }
//        if(matcher2.matches()){
//            return 2;
//        }
//        if(matcher1.matches()){
//            return 1;
//        }
//        return flag;
//    }


    public static String getLastType(String fileName){
        if(!StringUtil.isEmpty(fileName) && fileName.contains(".")) {
            String filetype = fileName.substring(fileName.lastIndexOf(".") + 1);
            return filetype;
        }
        return "";
    }

    //是否是图片类型
    public static boolean isImg(String type){
        boolean b = false;
        if("png".equalsIgnoreCase(type) || "jepg".equalsIgnoreCase(type) || "jpg".equalsIgnoreCase(type)){
            return true;
        }
        return false;
    }

    //是否是图片类型
    public static boolean isThreeFile(String type){
        boolean b = false;
        if("doc".equalsIgnoreCase(type) || "docx".equalsIgnoreCase(type) || "xls".equalsIgnoreCase(type) || "xlsx".equalsIgnoreCase(type) ){
            return true;
        }
        return false;
    }

    //Android获取一个用于打开Excel文件的intent
    public static Intent getExcelFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/vnd.ms-excel");
        return intent;
    }

    //Android获取一个用于打开Word文件的intent
    public static Intent getWordFileIntent(Context context, String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(context, "com.powerrich.office.oa"+".provider", new File(param));
            // 给目标应用一个临时授权
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }else{
            uri = Uri.fromFile(new File(param));
        }
        intent.setDataAndType(uri, "application/msword");
        return intent;
    }


    /**
     * html内的图片居中
     * @param htmltext
     * @return
     */
    public static String getcenterImg(String htmltext){
        try {
            Document doc= Jsoup.parse(htmltext);
            Elements elements=doc.getElementsByTag("img");
            for (Element element : elements) {
                element.attr("width","100%").attr("height","auto");
            }

            return doc.toString();
        } catch (Exception e) {
            return htmltext;
        }
    }
}
