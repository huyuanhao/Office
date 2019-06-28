package com.powerrich.office.oa.tools;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.view.MyDialog;
import com.powerrich.office.oa.view.pull.PullToRefreshListView;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * FileName : ResourceUtils Description : 对象数据处理对象
 * 
 * @author : 刘剑
 * @version : 1.0 Create Date : 2011-8-3 下午04:10:24
 **/
@SuppressLint("DefaultLocale")
public class BeanUtils {

	/**
	 * 通用资源的关闭操作
	 * 
	 * @param closeable
	 */
	public static void close(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 反射对象
	 * 
	 * @param className
	 * @return
	 */
	public static Object loadClass(String className) {
		Object obj = null;
		try {
			@SuppressWarnings("rawtypes")
			Class classs = Class.forName(className);
			obj = classs.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("加载【" + className + "】失败!");
		}
		return obj;
	}

	/**
	 * 判断数据是否为空
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("all")
	public static boolean isEmpty(Object obj) {
		boolean flag = true;
		if (obj != null) {
			if (obj instanceof String) {
				flag = (obj.toString().trim().length() == 0);
			} else if (obj instanceof Collection<?>) {
				flag = ((Collection) obj).size() == 0;
			} else {
				flag = false;
			}
		}
		return flag;
	}

	/**
	 * 方法说明：<br>
	 * 判断字符串
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmptyStr(String str) {
        return str == null || "null".equals(str.toLowerCase()) || str.trim().length() == 0;
    }

	/**
	 * 日期转换
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String formatDate(Date date, String format) {
		String result = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			result = sdf.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 字符串转换日期
	 * 
	 * @param dateStr
	 * @param formatStr
	 * @return
	 */
	public static Date parseDate(String dateStr, String formatStr) {
		Date result = null;
		try {
			if (dateStr.length() < formatStr.length()) {
				dateStr = "0" + dateStr;
			}
			SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
			result = sdf.parse(dateStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 文件保存
	 * 
	 * @param filePath
	 */
	public static void saveFile(InputStream inps, String filePath) {
		OutputStream out = null;
		try {
			out = new FileOutputStream(filePath);
			int i = 0;
			while ((i = inps.read()) != -1) {
				out.write(i);
			}
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(inps);
			close(out);
		}
	}

	/**
	 * 文件保存
	 * 
	 * @param fileContent
	 */
	public static void saveFile(String fileContent, String filePath) {
		if (!BeanUtils.isEmpty(fileContent)) {
			saveFile(fileContent.getBytes(), filePath);
		}
	}

	/** 解析文件名称 */
	public static String parseFileName(String url) {
		String fileName = "";
		int lastSplit = url.lastIndexOf("/");
		int lastSplit1 = url.lastIndexOf("\\");
		lastSplit = lastSplit > lastSplit1 ? lastSplit : lastSplit1;
		fileName = url.substring(lastSplit + 1);
		return fileName;
	}

	/** 删除除什么路径之外的文件 */
	public static void deleteFile(String filePath, String excludePath) {
		try {
			File file = new File(filePath);
			if (file.exists()) {
				boolean isExclude = !file.getAbsolutePath().endsWith(
						excludePath)
						|| BeanUtils.isEmpty(excludePath);
				if (file.isDirectory() && isExclude) {
					String[] tempList = file.list();
					File temp = null;
					for (int i = 0; i < tempList.length; i++) {
						if (filePath.endsWith(File.separator)) {
							temp = new File(filePath + tempList[i]);
						} else {
							temp = new File(filePath + File.separator
									+ tempList[i]);
						}
						if (temp.isFile()) {
							temp.delete();
						}
						if (temp.isDirectory()) {
							deleteFile(filePath + File.separator + tempList[i],
									excludePath);
						}
					}
				}
				if (isExclude) {
					file.delete();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 删除文件 */
	public static void deleteFile(String filePath) {
		deleteFile(filePath, "");
	}

	/**
	 * 文件保存
	 * 
	 * @param byes
	 */
	public static void saveFile(byte[] byes, String filePath) {
		OutputStream output = null;
		try {
			output = new FileOutputStream(filePath);
			output.write(byes);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(output);
		}
	}

	/**
	 * 将Base64编码解析
	 * 
	 * @param value
	 * @return
	 */
	public static String decodeBase64(String value) {
		if (!BeanUtils.isEmpty(value)) {
			try {
				byte[] srcbyties = Base64.decode(value, Base64.DEFAULT);
				value = new String(srcbyties);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return value;
	}

	/**
	 * 将Base64编码解析
	 * 
	 * @param value
	 * @return
	 */
	public static byte[] decodeBase64Data(String value) {
		byte[] srcbyties = null;
		try {
			srcbyties = Base64.decode(value, Base64.DEFAULT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return srcbyties;
	}

	/** 获取文件路径 */
	public static String getFileContext(String filePath) {
		String message = "";
		try {
			message = getFileContext(new FileInputStream(filePath));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return message;
	}

	/** 获取文件内容 */
	public static String getFileContext(InputStream input) {
		String mesage = "";
		ByteArrayOutputStream outStrem = null;
		try {
			outStrem = new ByteArrayOutputStream();
			int i = 0;
			while ((i = input.read()) != -1) {
				outStrem.write(i);
			}
			mesage = new String(outStrem.toByteArray());
			return mesage;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("");
		} finally {
			try {
				input.close();
				outStrem.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * URl的拼接
	 * 
	 * @param url
	 * @param parts
	 * @return
	 */
	public static String urlAppend(String url, String parts) {
		url = url.trim();
		if (!BeanUtils.isEmpty(parts)) {
			if (url.indexOf("?") < 0) {
				url += "?";
			} else {
				url += "&";
			}
		}
		return url + parts;
	}

	/**
	 * 验证文件路径是否存在，不存在则进行创建操作
	 * 
	 * @param filePath
	 */
	public static void checkFileExist(String filePath) {
		File file = new File(filePath);
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	public static boolean isFileExist(String filePath) {
		boolean hasFile = false;
		try {
			File file = new File(filePath);
			hasFile = file.exists();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hasFile;
	}


	/***
	 * 生成32位的MD5加密内容
	 * 
	 * @param source
	 * @return
	 */
	public static String md532(String source) {
		char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f'};
		try {
			byte[] strTemp = source.getBytes();
			// 使用MD5创建MessageDigest对象
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte b = md[i];
				// 将没个数(int)b进行双字节加密
				str[k++] = hexDigits[b >> 4 & 0xf];
				str[k++] = hexDigits[b & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}

	/***
	 * 对称加密
	 * 
	 * @param key
	 * @param desConstants
	 * @return
	 */
	public static String desEncrypt(String key, String desConstants) {
		byte[] values = new byte[10];
		try {
			SecureRandom sr = new SecureRandom();
			DESKeySpec dks = new DESKeySpec(desConstants.getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey securekey = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
			values = cipher.doFinal(key.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Base64.encodeToString(values, Base64.DEFAULT);
	}

	/***
	 * 对称解密
	 * 
	 * @param key
	 * @param desConstants
	 * @return
	 */
	public static String desDecrypt(String key, String desConstants) {
		byte[] values = new byte[10];
		try {
			SecureRandom sr = new SecureRandom();
			DESKeySpec dks = new DESKeySpec(desConstants.getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey securekey = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
			values = cipher.doFinal(Base64.decode(key, Base64.DEFAULT));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new String(values, 0, values.length);
	}

	/** 根据流获取文本内容 */
	public static String getContent(InputStream in, String encode) {
		StringBuffer mesage = new StringBuffer();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(in, encode));
			int i = 0;
			while ((i = reader.read()) != -1) {
				mesage.append((char) i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			BeanUtils.close(in);
		}
		return mesage.toString();
	}

	/**
	 * 检查指定的字符串列表是否不为空。
	 */
	public static boolean areNotEmpty(String... values) {
		boolean result = true;
		if (values == null || values.length == 0) {
			result = false;
		} else {
			for (String value : values) {
				result &= !isEmpty(value);
			}
		}
		return result;
	}

	/**
	 * 从URL中提取所有的参数。
	 * 
	 * @param query
	 *            URL地址
	 * @return 参数映射
	 */
	public static Map<String, String> splitUrlQuery(String query) {
		Map<String, String> result = new HashMap<String, String>();
		String[] pairs = query.split("&");
		if (pairs != null && pairs.length > 0) {
			for (String pair : pairs) {
				String[] param = pair.split("=", 2);
				if (param != null && param.length == 2) {
					result.put(param[0], param[1]);
				}
			}
		}
		return result;
	}

	/** 判断是否是Https请求 */
	public static boolean isHttps(String url) {
		return !BeanUtils.isEmpty(url) && url.toLowerCase().startsWith("https");
	}

	private static final Map<String, String> MIME_MAP = new HashMap<String, String>();

	static {
		MIME_MAP.put(".3gp", "video/3gpp");
		MIME_MAP.put(".apk", "application/vnd.android.package-archive");
		MIME_MAP.put(".asf", "video/x-ms-asf");
		MIME_MAP.put(".avi", "video/x-msvideo");
		MIME_MAP.put(".bin", "application/octet-stream");
		MIME_MAP.put(".bmp", "image/bmp");
		MIME_MAP.put(".c", "text/plain");
		MIME_MAP.put(".class", "application/octet-stream");
		MIME_MAP.put(".conf", "text/plain");
		MIME_MAP.put(".cpp", "text/plain");
		MIME_MAP.put(".doc", "application/msword");
		MIME_MAP.put(".docx", "application/msword");
		MIME_MAP.put(".exe", "application/octet-stream");
		MIME_MAP.put(".gif", "image/gif");
		MIME_MAP.put(".gtar", "application/x-gtar");
		MIME_MAP.put(".gz", "application/x-gzip");
		MIME_MAP.put(".h", "text/plain");
		MIME_MAP.put(".htm", "text/html");
		MIME_MAP.put(".html", "text/html");
		MIME_MAP.put(".jar", "application/java-archive");
		MIME_MAP.put(".java", "text/plain");
		MIME_MAP.put(".jpeg", "image/jpeg");
		MIME_MAP.put(".jpg", "image/jpeg");
		MIME_MAP.put(".js", "application/x-javascript");
		MIME_MAP.put(".log", "text/plain");
		MIME_MAP.put(".m3u", "audio/x-mpegurl");
		MIME_MAP.put(".m4a", "audio/mp4a-latm");
		MIME_MAP.put(".m4b", "audio/mp4a-latm");
		MIME_MAP.put(".m4p", "audio/mp4a-latm");
		MIME_MAP.put(".m4u", "video/vnd.mpegurl");
		MIME_MAP.put(".m4v", "video/x-m4v");
		MIME_MAP.put(".mov", "video/quicktime");
		MIME_MAP.put(".mp2", "audio/x-mpeg");
		MIME_MAP.put(".mp3", "audio/x-mpeg");
		MIME_MAP.put(".mp4", "video/mp4");
		MIME_MAP.put(".mpc", "application/vnd.mpohun.certificate");
		MIME_MAP.put(".mpe", "video/mpeg");
		MIME_MAP.put(".mpeg", "video/mpeg");
		MIME_MAP.put(".mpg", "video/mpeg");
		MIME_MAP.put(".mpg4", "video/mp4");
		MIME_MAP.put(".mpga", "audio/mpeg");
		MIME_MAP.put(".msg", "application/vnd.ms-outlook");
		MIME_MAP.put(".ogg", "audio/ogg");
		MIME_MAP.put(".pdf", "application/pdf");
		MIME_MAP.put(".png", "image/png");
		MIME_MAP.put(".pps", "application/vnd.ms-powerpoint");
		MIME_MAP.put(".ppt", "application/vnd.ms-powerpoint");
		MIME_MAP.put(".pptx", "application/vnd.ms-powerpoint");
		MIME_MAP.put(".prop", "text/plain");
		MIME_MAP.put(".rar", "application/x-rar-compressed");
		MIME_MAP.put(".rc", "text/plain");
		MIME_MAP.put(".rmvb", "audio/x-pn-realaudio");
		MIME_MAP.put(".rtf", "application/rtf");
		MIME_MAP.put(".sh", "text/plain");
		MIME_MAP.put(".tar", "application/x-tar");
		MIME_MAP.put(".tgz", "application/x-compressed");
		MIME_MAP.put(".txt", "text/plain");
		MIME_MAP.put(".wav", "audio/x-wav");
		MIME_MAP.put(".wma", "audio/x-ms-wma");
		MIME_MAP.put(".wmv", "audio/x-ms-wmv");
		MIME_MAP.put(".wps", "application/vnd.ms-works");
		MIME_MAP.put(".xml", "text/xml");
		MIME_MAP.put(".xml", "text/plain");
		MIME_MAP.put(".xls", "application/vnd.ms-excel");
		MIME_MAP.put(".xlsx", "application/vnd.ms-excel");
		MIME_MAP.put(".z", "application/x-compress");
		MIME_MAP.put(".zip", "application/zip");
		MIME_MAP.put(".vsd", "application/vnd.visio");
	}

	/**
	 * 获取对应的mimetype
	 */
	public static String getMimeType(String key) {
		String obj = MIME_MAP.get(key);
		return obj != null ? obj : "*/*";
	}

	/**
	 * 判断手机号码
	 * 
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern
//				.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
				.compile("(13|14|15|17|18|19)[0-9]{9}");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}
	
	/** 
     * 电话号码验证 
     *  
     * @param  str 
     * @return 验证通过返回true 
     */  
    public static boolean isPhone(String str) {   
        Pattern p1 = null,p2 = null;  
        Matcher m = null;  
        boolean b = false;    
        p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$");  // 验证带区号的  
        p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");         // 验证没有区号的  
        if(str.length() >9)  
        {   m = p1.matcher(str);  
            b = m.matches();    
        }else{  
            m = p2.matcher(str);  
            b = m.matches();   
        }    
        return b;  
    }

	/**
	 * 校验身份证号
	 * @param idCard
	 * @return
	 */
	public static boolean isIdCard(String idCard){
		String id = idCard.trim();
		Pattern p = Pattern.compile("(^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}[0-9Xx]$)") ;
		Matcher matcher = p.matcher(id);
		return matcher.matches();
	}

	/**
	 * 身份证验证
	 * @param card 身份编号
	 * @return 验证成功返回true，验证失败返回false
	 * */
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
	 * 匹配中国邮政编码
	 * @param postCode 邮政编码
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean isPostCode(String postCode){
		String reg = "[1-9]\\d{5}";
		return Pattern.matches(reg, postCode);
	}

	/**
	 * 校验邮箱
	 *
	 * @param email
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isEmail(String email) {
		String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		return Pattern.matches(REGEX_EMAIL, email);
	}

	/**
	 * 去除重复的
	 * 
	 * @param str
	 * @return
	 */
	public static String clearSameData(String origistr, String str) {
		if (isEmpty(origistr)) {
			return str;
		}
		if (isEmpty(str)) {
			return "";
		}

		String[] origistrs = origistr.split(",");
		String[] strs = str.split(",");
		for (int i = 0; i < strs.length; i++) {
			boolean isSame = false;
			for (int j = 0; j < origistrs.length; j++) {
				if (origistrs[j].equals(strs[i])) {
					isSame = true;
					break;
				}
			}
			if (!isSame) {
				if (isEmpty(origistr)) {
					origistr = strs[i];
				} else {
					origistr = origistr + "," + strs[i];
				}
			}
		}

		return origistr;
	}

	/**
	 * 验证是否整数：采用正则表达式进行验证
	 * 
	 * @param num
	 * @param type
	 *            整数类型："0+":非负整数 "+":正整数 "-0":非正整数 "-":负整数 "":整数
	 * @return
	 */
	public static boolean checkNumber(String num, String type) {
		if (isEmpty(num)) {// 空
			return false;
		}
		String eL = "";
		if ("0+".equals(type)) {
			eL = "^\\d+$";// 非负整数
		} else if ("+".equals(type)) {
			eL = "^\\d*[1-9]\\d*$";// 正整数
		} else if ("-0".equals(type)) {
			eL = "^((-\\d+)|(0+))$";// 非正整数
		} else if ("-".equals(type)) {
			eL = "^-\\d*[1-9]\\d*$";// 负整数
		} else {
			eL = "^-?\\d+$";// 整数
		}
		// Pattern p = Pattern.compile(eL);
		// Matcher m = p.matcher(num);
		// boolean b = m.matches();
		// return b;
		return Pattern.compile(eL).matcher(num).matches();
	}

	/**
	 * 验证是否整数：采用正则表达式进行验证
	 * 
	 * @param num
	 * @return
	 */
	public static boolean checkNumber(String num) {
		if (isEmpty(num)) {// 空
			return false;
		}
		return checkNumber(num, null);
	}

	/**
	 * 验证是否浮点数：采用正则表达式进行验证
	 * 
	 * @param num
	 * @param type
	 *            浮点数类型："0+":非负浮点数 "+":正浮点数 "-0":非正浮点数 "-":负浮点数 "":浮点数
	 * @return
	 */
	public static boolean checkFloat(String num, String type) {
		if (isEmpty(num)) {// 空
			return false;
		}
		String eL = "";
		if ("0+".equals(type)) {
			eL = "^\\d+(\\.\\d+)?$";// 非负浮点数
		} else if ("+".equals(type)) {
			eL = "^((\\d+\\.\\d*[1-9]\\d*)|(\\d*[1-9]\\d*\\.\\d+)|(\\d*[1-9]\\d*))$";// 正浮点数
		} else if ("-0".equals(type)) {
			eL = "^((-\\d+(\\.\\d+)?)|(0+(\\.0+)?))$";// 非正浮点数
		} else if ("-".equals(type)) {
			eL = "^(-((\\d+\\.\\d*[1-9]\\d*)|(\\d*[1-9]\\d*\\.\\d+)|(\\d*[1-9]\\d*)))$";// 负浮点数
		} else {
			eL = "^(-?\\d+)(\\.\\d+)?$";// 浮点数
		}
		// Pattern p = Pattern.compile(eL);
		// Matcher m = p.matcher(num);
		// boolean b = m.matches();
		// return b;
		return Pattern.compile(eL).matcher(num).matches();
	}

	/**
	 * 验证是否浮点数：采用正则表达式进行验证
	 * 
	 * @param num
	 * @return
	 */
	public static boolean checkFloat(String num) {
		if (isEmpty(num)) {// 空
			return false;
		}
		return checkFloat(num, null);
	}

	/**
	 * 验证是否浮点数，如果不是，将返回默认值
	 * 
	 * @param num
	 * @param defaultValue
	 * @return
	 */
	public static Float checkFloat4Get(String num, Float defaultValue) {
		if (checkFloat(num, null)) {
			return Float.valueOf(num);
		} else {
			return defaultValue;
		}
	}

	/**
	 * 验证是否整数，如果不是，将返回默认值
	 * 
	 * @param num
	 * @param defaultValue
	 * @return
	 */
	public static Integer checkNumber4Get(String num, Integer defaultValue) {
		if (checkNumber(num, null)) {
			return Integer.valueOf(num);
		} else {
			return defaultValue;
		}
	}

	/**
	 * 浮点数转换为整数，如果转换失败，将返回默认值
	 * 
	 * @param num
	 * @param defaultValue
	 * @return
	 */
	public static Integer floatToInt(String num, Integer defaultValue) {
		if (checkFloat(num, null)) {
			return Float.valueOf(num).intValue();// 数据的准确性无法保证
		} else {
			return defaultValue;
		}
	}

	/**
	 * 浮点数转换为整数，如果转换失败，将返回0
	 * 
	 * @param num
	 * @return
	 */
	public static Integer floatToInt(String num) {
		return floatToInt(num, 0);
	}

	/**
	 * 浮点数转换为整数，如果转换失败，将返回默认值
	 * 
	 * @param num
	 * @param defaultValue
	 * @return
	 */
	public static String floatToInt4Str(String num, String defaultValue) {
		if (checkFloat(num, null)) {
			// 方法一：数据的准确性无法保证
			// Integer result = Float.valueOf(num).intValue();//数据的准确性无法保证
			// return result.toString();
			// 方法二：数据的准确性可以保证
			if (num.indexOf('.') != -1) {
				return num.substring(0, num.indexOf('.'));// 数据的准确性可以保证
			} else {
				return num;
			}
		} else {
			return defaultValue;
		}
	}

	/**
	 * 浮点数转换为整数，如果转换失败，将返回空字符串""
	 * 
	 * @param num
	 * @return
	 */
	public static String floatToInt4Str(String num) {
		return floatToInt4Str(num, "");
	}

	/**
	 * 验证是否为空，如果是，将返回默认值
	 * 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static String isEmpty4Get(String value, String defaultValue) {
		if (isEmpty(value)) {
			return defaultValue;
		} else {
			return value;
		}
	}

	/**
	 * 验证是否为空，如果是，将返回空字符串""
	 * 
	 * @param value
	 * @return
	 */
	public static String isEmpty4Get(String value) {
		if (isEmpty(value)) {
			return "";
		} else {
			return value;
		}
	}
	
	
	/**
	 * 判断当前是否是平板设备
	 * @param context
	 * @return
	 */
	public static boolean isTabletDevice(Context context) {
        TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        int type = telephony.getPhoneType();
        /**
         * 返回移动终端的类型
         * 
         * PHONE_TYPE_CDMA 手机制式为CDMA，电信  2
         * PHONE_TYPE_GSM 手机制式为GSM，移动和联通 1
         * PHONE_TYPE_NONE 手机制式未知  0
         */
        if (type == TelephonyManager.PHONE_TYPE_NONE) {
            return true;
        } else {
        	DisplayMetrics dm = new DisplayMetrics();
        	((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
            double diagonalPixels = Math.sqrt(Math.pow(dm.widthPixels, 2.0)
                    + Math.pow(dm.heightPixels, 2.0));
            double screenSize = diagonalPixels / (160 * dm.density);
            //如果大于7寸的手机，则试用pad版本
            return screenSize >= 7;
        }
	}
	
	/**
	 * 设置横竖屏幕
	 * @param context
	 * @return
	 */
	public static void setPortraitAndLandscape(Context context) {
		//如果不是pad版本，横竖屏都可以
        if (!isTabletDevice(context)) {
        	//竖屏
        	((Activity) context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
	}

    public static boolean isNullOrEmpty(String content) {
        return (null == content || content.length() == 0);
    }

	/**
	 * 获取受理范围（通办范围--0：全国、1：跨省、2：跨市、3：跨县）
	 */
	public static String getAcceptScope(Context context,String type) {
		String[] typeKeys = context.getResources().getStringArray(R.array.accept_scope);
		if(!BeanUtils.isNullOrEmpty(type)) {
			int index = 0;
			try {
				index = type.contains(".") ? Integer.parseInt(type.substring(0, type.indexOf("."))) : Integer.valueOf(type);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return typeKeys[index];
		}
		return "";
	}

	/**
	 * 取件方式 弹出选择类型 1.邮寄 2.自取
	 * @param context
	 * @param tvType
	 */
	public static void showExpressWays(final Context context, final TextView tvType, final LinearLayout layout1, final LinearLayout layout2) {
		MyDialog builder = new MyDialog(context).builder();
		final String[] expressWaysText = context.getResources().getStringArray(R.array.express_ways);
		final int[] expressWaysTypeValue = {0, 1};
		int checkedItem = -1;
		if (!BeanUtils.isEmpty(tvType.getTag())) {
			checkedItem = Integer.parseInt(String.valueOf(tvType.getTag()));
		}
		// 设置一个下拉的列表选择项
		builder.setSingleChoiceItems(expressWaysText, checkedItem,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						tvType.setText(expressWaysText[which]);
						tvType.setTag(String.valueOf(expressWaysTypeValue[which]));
						dialog.dismiss();
						if (which == 1) {
							// 邮寄方式的显示邮寄地址
							layout1.setVisibility(View.VISIBLE);
							layout2.setVisibility(View.GONE);
						} else {
							// 自取方式的显示通讯地址
							layout1.setVisibility(View.GONE);
							layout2.setVisibility(View.VISIBLE);
						}
					}
				});
		builder.show();
	}

	/**
	 * 取件方式 弹出选择类型 1.邮寄 2.自取
	 * @param context
	 * @param tvType
	 */
	public static void showExpressWays(final Context context, final TextView tvType) {
		MyDialog builder = new MyDialog(context).builder();
		final String[] expressWaysText = context.getResources().getStringArray(R.array.express_ways);
		final int[] expressWaysTypeValue = {0, 1};
		int checkedItem = -1;
		if (!BeanUtils.isEmpty(tvType.getTag())) {
			checkedItem = Integer.parseInt(String.valueOf(tvType.getTag()));
		}
		// 设置一个下拉的列表选择项
		builder.setSingleChoiceItems(expressWaysText, checkedItem,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						tvType.setText(expressWaysText[which]);
						tvType.setTag(String.valueOf(expressWaysTypeValue[which]));
						dialog.dismiss();
					}
				});
		builder.show();
	}

	/**
	 * 获取取件方式 1.邮寄 2.自取
	 * @param context
	 * @param type
	 * @return
	 */
	public static String getExpressWays(Context context,String type) {
		final String[] typeKeys = context.getResources().getStringArray(R.array.express_ways);
		if (!BeanUtils.isNullOrEmpty(type)) {
			int index = 0;
			try {
				index = type.contains(".") ? Integer.parseInt(type.substring(0, type.indexOf("."))) : Integer.valueOf(type);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return typeKeys[index];
		}
		return "";
	}

	/**
	 * 获取材料形式：1.电子件2.原件3.复印件4.纸质材料
	 */
	public static String getMaterialForm(Context context,String type) {
		String[] typeKeys = context.getResources().getStringArray(R.array.material_form);
		if(!BeanUtils.isNullOrEmpty(type)) {
			int index = 1;
			try {
				index = type.contains(".") ? Integer.parseInt(type.substring(0, type.indexOf("."))) : Integer.valueOf(type);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return typeKeys[index];
		}
		return "";
	}

	/**
	 * 性别 弹出选择类型 0.男 1.女
	 * @param context
	 * @param tvType
	 */
	public static void showGender(final Context context, final TextView tvType) {
		MyDialog builder = new MyDialog(context).builder();
		final String[] expressWaysText = context.getResources().getStringArray(R.array.gender);
		final int[] expressWaysTypeValue = {0, 1};
		int checkedItem = -1;
		if (!BeanUtils.isEmpty(tvType.getTag())) {
			checkedItem = Integer.parseInt(String.valueOf(tvType.getTag()));
		}
		// 设置一个下拉的列表选择项
		builder.setSingleChoiceItems(expressWaysText, checkedItem,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						tvType.setText(expressWaysText[which]);
						tvType.setTag(String.valueOf(expressWaysTypeValue[which]));
						dialog.dismiss();
					}
				});
		builder.show();
	}

	/**
	 * 获取性别 0.男 1.女
	 * @param context
	 * @param type
	 * @return
	 */
	public static String getGender(Context context,String type) {
		final String[] typeKeys = context.getResources().getStringArray(R.array.gender);
		if (!BeanUtils.isNullOrEmpty(type)) {
			int index = 0;
			try {
				index = type.contains(".") ? Integer.parseInt(type.substring(0, type.indexOf("."))) : Integer.valueOf(type);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return typeKeys[index];
		}
		return "";
	}

	/**
	 * 设置ListView分割线
	 * @param pTListView
	 * @return
	 */
	public static ListView setProperty(PullToRefreshListView pTListView) {
		pTListView.setPullRefreshEnabled(true);
		pTListView.setPullLoadEnabled(false);
		pTListView.setScrollLoadEnabled(true);
		ListView mListView = pTListView.getRefreshableView();
		mListView.setDivider(null);//不设置会角标越界
		mListView.setDividerHeight(1);//每个条目的分界线
		mListView.setCacheColorHint(Color.TRANSPARENT);
		mListView.setFadingEdgeLength(0);
		mListView.setSelector(android.R.color.transparent);
		return mListView;
	}

	/**
	 * 获取办件进度
	 * @param context
	 * @param type
	 * @return
	 */
	public static String getProcessState(Context context,String type) {
		final String[] typeKeys = context.getResources().getStringArray(R.array.process_state);
		if (!BeanUtils.isNullOrEmpty(type)) {
			int index = 0;
			try {
				index = type.contains(".") ? Integer.parseInt(type.substring(0, type.indexOf("."))) : Integer.valueOf(type);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return typeKeys[index];
		}
		return "";
	}

	/**
	 * 经纬度校验 只校验正数 0-90.000000 0-180.000000 范围内
	 * 经度longitude: (?:[0-9]|[1-9][0-9]|1[0-7][0-9]|180)\\.([0-9]{6})
	 * 纬度latitude：  (?:[0-9]|[1-8][0-9]|90)\\.([0-9]{6})
	 * @return
	 */
	public static boolean checkItude(String longitude,String latitude){
		String reglo = "((?:[0-9]|[1-9][0-9]|1[0-7][0-9])\\.([0-9]{0,6}))|((?:180)\\.([0]{0,6}))";
		String regla = "((?:[0-9]|[1-8][0-9])\\.([0-9]{0,6}))|((?:90)\\.([0]{0,6}))";
		longitude = longitude.trim();
		latitude = latitude.trim();
		return longitude.matches(reglo) == true ? latitude.matches(regla) : false;
	}

	/**
	 * 根据身份证的号码算出当前身份证持有者的性别和年龄 18位身份证
	 */
	public static Map<String, Object> getCarInfo18(String CardCode) {
		Map<String, Object> map = new HashMap();
		String year = CardCode.substring(6).substring(0, 4);// 得到年份
		String yue = CardCode.substring(10).substring(0, 2);// 得到月份
		// String day=CardCode.substring(12).substring(0,2);//得到日
		String sex;
		if (Integer.parseInt(CardCode.substring(16).substring(0, 1)) % 2 == 0) {// 判断性别
			sex = "女";
		} else {
			sex = "男";
		}
		Date date = new Date();// 得到当前的系统时间
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String fyear = format.format(date).substring(0, 4);// 当前年份
		String fyue = format.format(date).substring(5, 7);// 月份
		// String fday=format.format(date).substring(8,10);
		int age = 0;
		if (Integer.parseInt(yue) <= Integer.parseInt(fyue)) { // 当前月份大于用户出身的月份表示已过生
			age = Integer.parseInt(fyear) - Integer.parseInt(year) + 1;
		} else {// 当前用户还没过生
			age = Integer.parseInt(fyear) - Integer.parseInt(year);
		}
		map.put("sex", sex);
		map.put("age", age);
		return map;
	}

	/**
	 * 15位身份证的验证
	 */
	public static Map<String, Object> getCarInfo15(String card) {
		Map<String, Object> map = new HashMap();
		String uyear = "19" + card.substring(6, 8);// 年份
		String uyue = card.substring(8, 10);// 月份
		// String uday=card.substring(10, 12);//日
		String usex = card.substring(14, 15);// 用户的性别
		String sex;
		if (Integer.parseInt(usex) % 2 == 0) {
			sex = "女";
		} else {
			sex = "男";
		}
		Date date = new Date();// 得到当前的系统时间
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String fyear = format.format(date).substring(0, 4);// 当前年份
		String fyue = format.format(date).substring(5, 7);// 月份
		// String fday=format.format(date).substring(8,10);
		int age = 0;
		if (Integer.parseInt(uyue) <= Integer.parseInt(fyue)) { // 当前月份大于用户出身的月份表示已过生
			age = Integer.parseInt(fyear) - Integer.parseInt(uyear) + 1;
		} else {// 当前用户还没过生
			age = Integer.parseInt(fyear) - Integer.parseInt(uyear);
		}
		map.put("sex", sex);
		map.put("age", age);
		return map;
	}

}
