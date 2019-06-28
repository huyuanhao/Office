package com.powerrich.office.oa.activity.mine;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.powerrich.office.oa.AppManager;
import com.powerrich.office.oa.R;
import com.powerrich.office.oa.SIMeID.ResultParams;
import com.powerrich.office.oa.SIMeID.SIMeIDApplication;
import com.powerrich.office.oa.SIMeID.URLContextSMS;
import com.powerrich.office.oa.activity.mine.base.YTBaseActivity;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.ToastUtils;
import com.powerrich.office.oa.view.NoEmojiEditText;
import com.yt.simpleframe.http.ApiManager;
import com.yt.simpleframe.http.BaseSubscriber;
import com.yt.simpleframe.http.RxSchedulers;
import com.yt.simpleframe.http.bean.BaseBean;
import com.yt.simpleframe.http.bean.LoginBean;
import com.yt.simpleframe.http.requst.RequestBodyUtils;
import com.yt.simpleframe.utils.StringUtil;

import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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

public class Identify3Activity extends YTBaseActivity {

    @BindView(R.id.et_name)
    NoEmojiEditText mEtName;
    @BindView(R.id.et_id_card)
    NoEmojiEditText mEtIdCard;
    @BindView(R.id.tv_commit)
    TextView mTvCommit;


    private String mPhoneNumber;
    private String mUserId;
    private String name;
    private String idCard;

    private String type;

    //eid
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

    @Override
    protected View onCreateContentView() {
        View view = inflateContentView(R.layout.activity_verify);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPhoneNumber = getIntent().getExtras().getString("phoneNumber");
        mUserId = getIntent().getExtras().getString("userId");
        type = getIntent().getExtras().getString("type");
        if ("eid".equals(type)) {
            setTitle("Eid认证");
            app = ((SIMeIDApplication) getApplication());
            initOkHttp();
            initializeHandler();

        } else if ("normal".equals(type)) {
            setTitle("实名认证");
        }

    }

    @OnClick(R.id.tv_commit)
    public void onViewClicked() {
        if (checkData()) {
            if ("eid".equals(type)) {
                startDoRealNameThread();
            } else if ("normal".equals(type)) {
                comparisonIdentityInfo(mUserId, name, idCard);
            }

        }
    }


    private boolean checkData() {
        boolean b = false;
        name = mEtName.getText().toString().trim();
        idCard = mEtIdCard.getText().toString().trim();

        if (StringUtil.isEmpty(name)) {
            ToastUtils.showMessage(this, "名字不能为空！");
            return b;
        }
        if (StringUtil.isEmpty(idCard)) {
            ToastUtils.showMessage(this, "身份证号不能为空！");
            return b;
        }
        if (!StringUtil.validCard(idCard)) {
            ToastUtils.showMessage(this, "身份证号输入不正确！");
            return b;
        }
        return true;
    }

    //人口库对比身份信息
    private void comparisonIdentityInfo(final String userId, final String realname, final String idcard) {
        ApiManager.getApi().comparisonIdentityInfo(RequestBodyUtils.comparisonIdentityInfo(userId, realname, idcard))
                .compose(RxSchedulers.<LoginBean>io_main())
                .compose(this.<LoginBean>loadingDialog())
                .subscribe(new BaseSubscriber<LoginBean>() {
                    @Override
                    public void result(LoginBean baseBean) {
                        //0未通过认证，1已通过认证
                        if (baseBean.getDATA() != null) {
                            String identifyStr = baseBean.getDATA().getSFSMRZ();
                            if ("1".equals(identifyStr)) {
                                AppManager.getAppManager().finishActivity(ChooseIdentifyTypeActivity.class);
                                finish();
                                ToastUtils.showMessage(Identify3Activity.this, "身份认证成功！");
                            } else {
                                ToastUtils.showMessage(Identify3Activity.this, "身份认证失败！");
                            }
                        }else{
                            ToastUtils.showMessage(Identify3Activity.this, baseBean.getMessage());
                        }

                    }
                });

    }

    public void saveUserInfo(String appEid) {
        ApiManager.getApi().exeNormal(RequestBodyUtils.identifyEid(mPhoneNumber,name,idCard,appEid))
                .compose(RxSchedulers.<BaseBean>io_main())
                .subscribe(new BaseSubscriber<BaseBean>() {
                    @Override
                    public void result(BaseBean bean) {
                        ToastUtils.showMessage(Identify3Activity.this, "注册eid认证成功！");
                        finish();
                        AppManager.getAppManager().finishActivity(ChooseIdentifyTypeActivity.class);
                    }
                });
    }


    //eid 认证部分
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

        private Identify3Activity curAT = null;

        public EventHandler(Identify3Activity curAT, Looper looper) {
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
            ResultParams result = doSign(URL, mPhoneNumber, "汉", name, idCard);
            Message msgToUI = mUIHandler.obtainMessage(MSG_SIGN_END, result);
            mUIHandler.sendMessage(msgToUI);
            return false;

        }

    }

    private void processMsg(Message msg) {

        switch (msg.what) {

            case MSG_SIGN_BEGIN: {
                String title = mPhoneNumber + " - 提示";
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
            String result = root.getString(Identify3Activity.SECTION.RESULT);
            String resultDetails = root.getString(Identify3Activity.SECTION.RESULT_DETAILS);

            if (result.length() == 4) {
                // 认证失败
                ToastUtils.showMessage(this, resultDetails);
            } else {

                if (result.equals("00")) {
                    String userInfoForEID = root.getString(Identify3Activity.SECTION.USER_INFO);
                    JSONObject object = JSONObject.parseObject(userInfoForEID);
                    String appEIDCode = object.getString(Identify3Activity.SECTION.APP_EID_CODE);
                    // 认证成功，调用后台实名认证接口保存实名信息
//                    invoke.invokeWidthDialog(OAInterface.saveUserInfoPersonal(registUserName, registUserduty, strMobileNo,
//                            strUserName, strUserId, StringUtils.getGenderByIdCard(strUserId), strNation, appEIDCode), callBack);
                    saveUserInfo(appEIDCode);
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

        if ("eid".equals(type)) {
            if (null != handlerThread) {
                handlerThread.quit();
            }
            app.removeActivity(this);
        }
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


}
