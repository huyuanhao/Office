package com.powerrich.office.oa.tools.network;

import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.network.entity.NetStatus;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 * @date 2018/10/31 15:57
 */
public class AuthorizationUtils {


    /**
     * 【授权码】
     * 需要调用政务服务实名认证系统API接口时，要调用此方法获取authcode，用于后续获取accesstoken，该authcode有效时间为10分钟。
     *
     * @return
     */
    public static void getAuthorizationCode(final IGetCode iGetCode) {
        HttpManager httpManager = new HttpManager();
        httpManager.setParams(false);

        Map map = new HashMap();
        map.put("client_id", HttpManager.client_id);
        httpManager.postNetWork(Constants.GET_AUTHORIZATION_CODE_URL, map, new HttpManager.INetLister() {
            @Override
            public void onSuccess(NetStatus netStatus) {
                try {
                    JSONObject jsonObject = new JSONObject(netStatus.getData().toString());
                    String authorizationCode = jsonObject.optString("AuthorizationCode");
                    iGetCode.getCode(authorizationCode);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error) {

            }
        });

    }

    /**
     * 【访问令牌】
     * 需要调用政务服务实名认证系统API接口时，要调用此方法获取accessToken，根据accessToken可以调用API接口。accessToken有效期2小时。
     *
     * @return
     */
    public static void getAccessToken(final IGetCode iGetCode) {
        getAuthorizationCode(new IGetCode() {
            @Override
            public void getCode(String auth_code) {
                HttpManager httpManager = new HttpManager();
                httpManager.setParams(false);
                Map map = new HashMap();
                map.put("client_secret", HttpManager.client_secret);
                map.put("client_id", HttpManager.client_id);
                map.put("auth_code", auth_code);

                httpManager.postNetWork(Constants.GET_ACCESS_TOKEN_URL, map, new HttpManager.INetLister() {
                    @Override
                    public void onSuccess(NetStatus netStatus) {
                        try {
                            JSONObject jsonObject = new JSONObject(netStatus.getData().toString());
                            String accessToken = jsonObject.optString("accessToken");
                            iGetCode.getCode(accessToken);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(String error) {

                    }
                });

            }
        });

    }

    public interface IGetCode {
        void getCode(String code);
    }


}
