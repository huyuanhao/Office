package com.powerrich.office.oa.tools;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.powerrich.office.oa.common.InterceptNetworkResponse;
import com.powerrich.office.oa.common.Interceptor;
import com.powerrich.office.oa.common.OffLineInterceptor;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.network.http.HttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * 字符串处理工具类
 * **/

public class StringUtils {
	
	private static final String LINE_SEPARATOR = System.getProperty("line.separator");

	private static InterceptNetworkResponse singleIntercept = new InterceptNetworkResponse() {

	    private ArrayList<Interceptor> interceptors = new ArrayList<>();

        @Override
        public boolean intercept(Context context, HttpResponse response) {
            ResultItem item = response.getResultItem(ResultItem.class);
            if (!BeanUtils.isEmpty(item)) {
                String code = item.getString("code");
                String msg = item.getString("message");
                if (!interceptors.isEmpty()) {
                    for (Interceptor interceptor : interceptors) {
                        if (null != interceptor && interceptor.getErrorCode().equals(code)) {
                            interceptor.doInterceptWork(context);
                            return true;
                        }
                    }
                }
            }
            return false;
        }

        @Override
        public void addInterceptor(Interceptor interceptor) {
            interceptors.add(interceptor);
        }

        @Override
        public void addInterceptors(ArrayList<Interceptor> interceptors) {
            if (null != interceptors) {
                this.interceptors.addAll(interceptors);
            }
        }
    };

	static {
	    singleIntercept.addInterceptor(new OffLineInterceptor(Interceptor.INTERCEPT_TOKEN_CODE));
    }

	/** 判断字符串是否为空 */
	public static boolean isNullOrEmpty(String str) {
        return null == str || str.length() <= 0;
    }
	
    /**
     * 根据流转换成字符串
     * @param [is] 流
     * @param [encodeing] 编码
     * @return
     * @throws Exception 
     */
    public static String convertStreamToString(InputStream is, String encodeing) throws Exception {
		StringBuffer out = new StringBuffer();

		byte[] b = new byte[4096];
		for (int n; (n = is.read(b)) != -1;) {
			out.append(new String(b, 0, n));
		}
		String clob =  new String(out.toString().getBytes(encodeing), "UTF-8");
		return clob; 
    }

    public static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append(LINE_SEPARATOR);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
            	if(null!=reader){
            		reader.close();
            	}
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    /**
     * 根据资源名称获取资源id
     * @param variableName
     * @param c
     * */
    public static int getResId(String variableName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 根据身份编号获取性别
     *
     * @param idCard 身份编号
     * @return 性别(M-男，F-女，N-未知)
     */
    public static String getGenderByIdCard(String idCard) {
        String sGender = "未知";

        String sCardNum = idCard.substring(16, 17);
        if (Integer.parseInt(sCardNum) % 2 != 0) {
            sGender = "0";//男
        } else {
            sGender = "1";//女
        }
        return sGender;
    }

    /**
     * 拦截器实例，可以定义多个拦截
     * */
    public static boolean interceptResponse(Context context, HttpResponse response) {
        return singleIntercept.intercept(context, response);
    }

}
