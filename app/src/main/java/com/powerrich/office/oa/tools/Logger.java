package com.powerrich.office.oa.tools;

import android.util.Log;

public class Logger {

	public static void w(String tAG, String string) {
		LogUtils.w(tAG, string);
	}
	
	public static void e(String tAG, String string) {
		LogUtils.e(tAG, string);
	}
	
	public static void d(String tAG, String string) {
		LogUtils.d(tAG, string);
	}
	
	public static void i(String tAG, String string) {
		LogUtils.i(tAG, string);
	}
	
	public static void v(String tAG, String string) {
		LogUtils.v(tAG, string);
	}

	public static void w(String tag, String string, Exception e) {
		LogUtils.w(tag,string, e);
	}


	public static void e(String tag, String string, Exception e) {
		LogUtils.e(tag,string, e);
	}

}
