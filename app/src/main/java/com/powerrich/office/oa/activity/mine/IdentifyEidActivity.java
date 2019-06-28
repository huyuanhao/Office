package com.powerrich.office.oa.activity.mine;

import android.app.FragmentManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.powerrich.office.oa.R;
import com.powerrich.office.oa.SIMeID.ResultParams;
import com.powerrich.office.oa.SIMeID.SIMeIDApplication;
import com.powerrich.office.oa.SIMeID.URLContextSMS;
import com.powerrich.office.oa.activity.mine.base.YTBaseActivity;
import com.powerrich.office.oa.enums.IdentifyType;
import com.powerrich.office.oa.fragment.mine.IdentifyEidFragment;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.ToastUtils;

import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/6/11
 * 版权：
 */

public class IdentifyEidActivity extends YTBaseActivity {

    //UI线程Handler
    private Handler mUIHandler = null;
    //Background线程Handler
    private Handler mBKHandler = null;
    //Background线程
    private HandlerThread handlerThread = null;
    private static final int MSG_SIGN_BEGIN = 1;
    private static final int MSG_SIGN_END = 2;
    private String reqURL;
    private SIMeIDApplication app;
    private OkHttpClient client;

    public static  String phoneNumber = "";
    public static String cardNumber = "";
    public static String name = "";
    public static String group = "";
    public static String selectCity = "";

    private IdentifyEidFragment fragment1, fragment2, fragment3;




    protected View onCreateContentView() {
        fragment1 = IdentifyEidFragment.getInstance(IdentifyType.页面1);
        fragment2 = IdentifyEidFragment.getInstance(IdentifyType.页面2);
        fragment3 = IdentifyEidFragment.getInstance(IdentifyType.页面3);
        replaceFragment(R.id.root_flt_content, fragment1);
        return null;
    }

    public void replaceFragment(IdentifyType type) {
        FragmentManager fm = getFragmentManager();
        if (type == IdentifyType.页面1) {
            replaceFragmentAnimation(R.id.root_flt_content, fragment1);
        } else if (type == IdentifyType.页面2) {
            replaceFragmentAnimation(R.id.root_flt_content, fragment2);
        } else {
            replaceFragmentAnimation(R.id.root_flt_content, fragment3);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("eId验证", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getSupportFragmentManager().getBackStackEntryCount() > 0 ){
                    getSupportFragmentManager().popBackStack();
                } else {
                    finish();
                }
            }
        });
        app = ((SIMeIDApplication) getApplication());
        initOkHttp();
        initializeHandler();
        initStatic();
    }


    private void initOkHttp() {
        reqURL = Constants.SIM_EID_AUTH_URL;
        if (null == client) {

            client = new OkHttpClient.Builder()
                    .connectTimeout(120, TimeUnit.SECONDS)
                    .readTimeout(120, TimeUnit.SECONDS)
                    .build();

        }

    }



    private void initializeHandler() {

        Looper looper;
        if (null != (looper = Looper.myLooper())) {
            mUIHandler = new EventHandler(this, looper);
        } else if (null != (looper = Looper.getMainLooper())) {
            mUIHandler = new EventHandler(this, looper);
        } else {
            mUIHandler = null;
        }
    }


    private static class EventHandler extends Handler {

        private IdentifyEidActivity curAT = null;

        public EventHandler(IdentifyEidActivity curAT, Looper looper) {
            super(looper);

            this.curAT = curAT;

        }

        public void handleMessage(Message msg) {

            if (null != curAT) {

                curAT.processMsg(msg);
                return;

            }

            super.handleMessage(msg);

        }

    }


    /**
     * 开启线程验证
     */
    public void startDoRealNameThread() {

        //创建一个HandlerThread并启动它
        handlerThread = new HandlerThread("DoRealNameThread");
        handlerThread.start();

        //使用HandlerThread的looper对象创建Handler，如果使用默认的构造方法，会阻塞UI线程。
        mBKHandler = new Handler(handlerThread.getLooper(), new DoRealNameCallback());
        mBKHandler.sendEmptyMessage(MSG_SIGN_BEGIN);

    }


    class DoRealNameCallback implements Handler.Callback {

        @Override
        public boolean handleMessage(Message msg) {

            //在子线程中进行耗时操作
            mUIHandler.sendEmptyMessage(MSG_SIGN_BEGIN);

            //执行网络请求
            String URL = reqURL + URLContextSMS.REAL_NAME;
//            ResultParams result = doSign(URL, strMobileNo, strNation, strUserName, strUserId);
            ResultParams result = doSign(URL, phoneNumber, group, name, cardNumber);
            Message msgToUI = mUIHandler.obtainMessage(MSG_SIGN_END, result);
            mUIHandler.sendMessage(msgToUI);
            return false;

        }

    }

    private void processMsg(Message msg) {

        switch (msg.what) {

            case MSG_SIGN_BEGIN: {
                String title = phoneNumber + " - 提示";
                String message = "正在请求身份识别";
                showProgressDlg(title, message);
            }
            break;
            case MSG_SIGN_END: {
                hideProgressDlg();
                ResultParams resultParams = (ResultParams) msg.obj;
                parseResp(resultParams.more);
            }
            break;
            default:
                break;
        }
    }

    private void parseResp(String resp) {

        try {
            JSONObject root = JSONObject.parseObject(resp);
            String result = root.getString(SECTION.RESULT);
            String resultDetails = root.getString(SECTION.RESULT_DETAILS);

            if (result.length() == 4) {
                // 认证失败
                ToastUtils.showMessage(this, resultDetails);
            } else {

                if (result.equals("00")) {
                    String userInfoForEID = root.getString(SECTION.USER_INFO);
                    JSONObject object = JSONObject.parseObject(userInfoForEID);
                    String appEIDCode = object.getString(SECTION.APP_EID_CODE);
                    // 认证成功，调用后台实名认证接口保存实名信息
//                    invoke.invokeWidthDialog(OAInterface.saveUserInfoPersonal(registUserName, registUserduty, strMobileNo,
//                            strUserName, strUserId, StringUtils.getGenderByIdCard(strUserId), strNation, appEIDCode), callBack);
                    fragment2.saveUserInfo(appEIDCode);
                } else {
                    // 认证失败
                    ToastUtils.showMessage(this, resultDetails);
                }
            }
            Log.e(TAG, result);
            Log.e(TAG, resultDetails);
        } catch (JSONException e) {
            ToastUtils.showMessage(this, e.getMessage());
        }

    }



    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");


    /**
     * 请求签名
     *
     * @param url
     * @param mobileNo
     * @param dataToSign
     * @param userName
     * @param userId
     * @return
     */
    private ResultParams doSign(
            String url,
            String mobileNo,
            String dataToSign,
            String userName,
            String userId) {


        ResultParams resultParams = new ResultParams("test");

        JSONObject obj = new JSONObject();

        try {

            obj.put("mobile_no", mobileNo);
            obj.put("data_to_sign", dataToSign);

            if (null != userName && userName.length() > 0) {

                obj.put("name", userName);

            }

            if (null != userId && userId.length() > 0) {

                obj.put("idnum", userId);

            }
            //涉及到中文字段提交的，需要再进行一次UTF-8编码，此处userName为中文
            RequestBody body = RequestBody.create(JSON, URLEncoder.encode(obj.toString(), "UTF-8"));

            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();
            int statusCode = response.code();
            Log.d(TAG, "HTTP Code : " + statusCode);
            if (response.isSuccessful()) {

                resultParams.build(true, response.body().string());

            } else {

                String result = "服务器返回错误：statusCode = " + statusCode;
                resultParams.build(false, result);
                Log.e(TAG, response.toString());

            }


        } catch (Exception e) {

            Log.e(TAG, e.toString());

            String result = "请求异常，url = \"" + url + "\"[" + e.toString() + "]";
            resultParams.build(false, result);

        }

        return resultParams;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (null != handlerThread) {

            handlerThread.quit();

        }
        app.removeActivity(this);
    }

    public class SECTION {

        static final String ENV_TAG = "env_tag";

        static final String BIZ_ID = "biz_id";
        static final String RESULT = "result";
        static final String RESULT_DETAILS = "result_details";
        static final String REQ_TIME = "req_time";
        static final String RESP_TIME = "resp_time";
        static final String EID_SIGN = "eid_sign";
        static final String USER_INFO = "user_info";
        static final String APP_EID_CODE = "appeidcode";

    }

    void initStatic(){
        phoneNumber = "";
        cardNumber = "";
        name = "";
        group = "";
        selectCity = "";
    }
}
