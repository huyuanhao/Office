package com.powerrich.office.oa.tools;


import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * base64 加解密工具类
 * */
public class Base64Utils {
	
	/**
	 * Base64 encode
	 * */
	public static String encode(byte[] input) {
		byte[] result = Base64.encode(input);
		return new String(result);
	}

	/**
	 * Base64 decode
	 * */
	public static byte[] decode(String string) {
		byte[] ret = Base64.decode(string);
		return ret;
	}

	public static String file2Base64(String filePath) {
		String encode = encode(getBytes(filePath));
		return encode;
	}

	/**
	 * 获得指定文件的byte数组
	 */
	private static byte[] getBytes(String filePath){
		byte[] buffer = null;
		try {
			File file = new File(filePath);
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
			byte[] b = new byte[1000];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			fis.close();
			bos.close();
			buffer = bos.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer;
	}

	/**
	 * 根据byte数组，生成文件
	 */
	public static void getFile(byte[] bfile, String filePath,String fileName) {
		BufferedOutputStream bos = null;
		FileOutputStream fos = null;
		File file = null;
		try {
			File dir = new File(filePath);
			if(!dir.exists()&&dir.isDirectory()){//判断文件目录是否存在
				dir.mkdirs();
			}
			file = new File(filePath+"\\"+fileName);
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			bos.write(bfile);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

}
