package com.powerrich.office.oa;


import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 加密
 *
 * @author Administrator
 */
public class EncryptUtil {


    //~ Methods ================================================================

    public static String encode(String text, String algorithm) {
        byte[] unencodedText = text.getBytes();

        MessageDigest md = null;

        try {
            // first create an instance, given the provider
            md = MessageDigest.getInstance(algorithm);
        } catch (Exception e) {


            return text;
        }

        md.reset();

        md.update(unencodedText);

        // now calculate the hash
        byte[] cryptograph = md.digest();

        StringBuffer buf = new StringBuffer();

        for (int i = 0; i < cryptograph.length; i++) {
            if (((int) cryptograph[i] & 0xff) < 0x10) {
                buf.append('0');
            }

            buf.append(Long.toString((int) cryptograph[i] & 0xff, 16));
        }

        return buf.toString();
    }

    /**
     * HMAC加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptHMAC(byte[] data, String key, String algorithm) throws Exception {

        SecretKey secretKey = new SecretKeySpec(key.getBytes(), algorithm);
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);
        return byteArrayToHexString(mac.doFinal(data));

    }
    
    
    /*byte数组转换为HexString*/
    public static String byteArrayToHexString(byte[] b) {
       StringBuffer sb = new StringBuffer(b.length * 2);
       for (int i = 0; i < b.length; i++) {
         int v = b[i] & 0xff;
         if (v < 16) {
           sb.append('0');
         }
         sb.append(Integer.toHexString(v));
       }
       return sb.toString();
     }


    /**
     *  参数排序，拼成字符串
     * @param map
     * @return
     */
    public static String orderString(Map<String,String[]> map) {

        String result = "";
        try {
            List<Map.Entry<String, String[]>> infoIds = new ArrayList<Map.Entry<String, String[]>>(map.entrySet());
            // 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
            Collections.sort(infoIds, new Comparator<Map.Entry<String, String[]>>() {
                public int compare(Map.Entry<String, String[]> o1, Map.Entry<String, String[]> o2) {
                    return (o1.getKey()).toString().compareTo(o2.getKey());
                }
            });
            // 构造签名键值对的格式
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String[]> item : infoIds) {
                if (item.getKey() != null || item.getKey() != "") {
                    if("sign".equals(item.getKey())){
                        continue;
                    }
                    String key = item.getKey();
                    String val="";
                    String[] values =item.getValue();
                    for (int i = 0; i < values.length; i++) {
                        String value = values[i];
                         sb.append(key + "=" +   value   + "&");
                    }
                }

            }
            result =  sb.deleteCharAt(sb.length() - 1).toString();

           // EncryptUtil.
            //result=EncryptUtil.encryptHMAC(result.getBytes("utf-8"),keySecurity,"HmacMD5");
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    /**
     * 正常MD5 加密
     * @param map
     * @return
     */
    public static String getSignMD5(Map<String,String[]> map){
        return md5(orderString(map));
    }

    /**
     * MD5 二次加密，修改前5位
     * @param map
     * @param keySecurity
     * @return
     */
    public static String getSignMD5Second(Map<String,String[]> map, String keySecurity){
        return md5Second(orderString(map),keySecurity);
    }

    public static String md5(String text ){
        //加密后的字符串
        String encodeStr= md5Hex(text );
        System.out.println("MD5第一次加密后的字符串为:encodeStr="+encodeStr);
        return encodeStr;
    }

    public static String md5Second(String text, String key ){
        //加密后的字符串
        //替换掉前5位
        text=key+ md5(text).substring(5);
        String encodeStr2= md5Hex(text);
        System.out.println("MD5第二次加密后的字符串为:encodeStr="+encodeStr2);
        return encodeStr2;
    }

    public static void main(String[] args) {
        md5Second("page=1&size=10","powerrichEeds");
    }






    /**
     * HexUtil类实现Hex(16进制字符串和)和字节数组的互转
     */
    @SuppressWarnings("unused")
    private static String md5Hex(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(str.getBytes());
            return new String(new HexUtil().encode(digest));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.toString());
            return "";
        }
    }

    /**
     * 加盐MD5加密
     */
    public static String getSaltMD5(String password) {
        // 生成一个16位的随机数
        Random random = new Random();
        StringBuilder sBuilder = new StringBuilder(16);
        sBuilder.append(random.nextInt(99999999)).append(random.nextInt(99999999));
        int len = sBuilder.length();
        if (len < 16) {
            for (int i = 0; i < 16 - len; i++) {
                sBuilder.append("0");
            }
        }
        // 生成最终的加密盐
        String Salt = sBuilder.toString();
        password = md5Hex(password + Salt);
        char[] cs = new char[48];
        for (int i = 0; i < 48; i += 3) {
            //在结果中的每三位用中间位保存salt值
            cs[i] = password.charAt(i / 3 * 2);
            char c = Salt.charAt(i / 3);
            cs[i + 1] = c;
            cs[i + 2] = password.charAt(i / 3 * 2 + 1);
        }
        return String.valueOf(cs);
    }

    /**
     * 验证加盐后是否和原文一致
     */
    public static boolean getSaltverifyMD5(String password, String md5str) {
        char[] cs1 = new char[32];
        char[] cs2 = new char[16];
        for (int i = 0; i < 48; i += 3) {
            cs1[i / 3 * 2] = md5str.charAt(i);
            cs1[i / 3 * 2 + 1] = md5str.charAt(i + 2);
            cs2[i / 3] = md5str.charAt(i + 1);
        }
        String Salt = new String(cs2);
        return md5Hex(password + Salt).equals(String.valueOf(cs1));
    }


    public static class HexUtil{
        /**
         * 字节流转成十六进制表示
         */
        public static String encode(byte[] src) {
            String strHex = "";
            StringBuilder sb = new StringBuilder("");
            for (int n = 0; n < src.length; n++) {
                strHex = Integer.toHexString(src[n] & 0xFF);
                // 每个字节由两个字符表示，位数不够，高位补0
                sb.append((strHex.length() == 1) ? "0" + strHex : strHex);
            }
            return sb.toString().trim();
        }

        /**
         * 字符串转成字节流
         */
        public static byte[] decode(String src) {
            int m = 0, n = 0;
            int byteLen = src.length() / 2; // 每两个字符描述一个字节
            byte[] ret = new byte[byteLen];
            for (int i = 0; i < byteLen; i++) {
                m = i * 2 + 1;
                n = m + 1;
                int intVal = Integer.decode("0x" + src.substring(i * 2, m) +
                        src.substring(m, n));
                ret[i] = Byte.valueOf((byte)intVal);
            }
            return ret;
        }
    }

}

