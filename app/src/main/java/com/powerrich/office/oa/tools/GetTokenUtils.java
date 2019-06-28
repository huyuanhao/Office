package com.powerrich.office.oa.tools;

import android.content.Context;

import com.powerrich.office.oa.api.ApiRequest;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.base.InvokeHelper;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.network.http.HttpResponse;

/**
 * 文 件 名：GetTokenUtils
 * 描   述：获取accessToken请求工具类
 * 作   者：Wangzheng
 * 时   间：2018-11-2 15:37:05
 * 版   权：v1.0
 */
public class GetTokenUtils {

    private static final int GET_AUTHORIZATION_CODE = 0;
    private static final int GET_ACCESS_TOKEN_CODE = 1;

    private static ICallback iCallback;
    private static Context mContext;

    public interface ICallback {
        void callback(String token);
    }

    public static void getToken(Context context, ICallback callback) {
        mContext = context;
        iCallback = callback;
        getAuthorizationCode();
    }

    /**
     * 获取授权码请求
     */
    private static void getAuthorizationCode() {
        ApiRequest request = OAInterface.getAuthorizationCode();
        new InvokeHelper(mContext).invoke(request, callBack, GET_AUTHORIZATION_CODE);
    }

    /**
     * 获取accessToken请求
     */
    private static void getAccessToken(String authCode) {
        ApiRequest request = OAInterface.getAccessToken(authCode);
        new InvokeHelper(mContext).invoke(request, callBack, GET_ACCESS_TOKEN_CODE);
    }
    private static IRequestCallBack callBack = new BaseRequestCallBack() {

        @Override
        public void process(HttpResponse response, int what) {
            ResultItem item = response.getResultItem(ResultItem.class);
            String code = item.getString("code");
            String message = item.getString("msg");
            if (Constants.CODE.equals(code)) {
                if (what == GET_AUTHORIZATION_CODE) {
                    ResultItem data = (ResultItem) item.get("data");
                    String authCode = data.getString("AuthorizationCode");
                    getAccessToken(authCode);
                } else if (what == GET_ACCESS_TOKEN_CODE) {
                    ResultItem data = (ResultItem) item.get("data");
                    String accessToken = data.getString("accessToken");
                    iCallback.callback(accessToken);
                }
            } else {
                DialogUtils.showToast(context, message);
            }
        }
    };
}
