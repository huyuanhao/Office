package com.powerrich.office.oa.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.aeye.face.AEFaceInterface;
import com.aeye.face.AEFacePack;
import com.aeye.face.AEFaceParam;
import com.powerrich.office.oa.R;
import com.powerrich.office.oa.adapter.CommonAdapter;
import com.powerrich.office.oa.api.ApiRequest;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.bean.UserInfo;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.common.ViewHolder;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.DateUtils;
import com.powerrich.office.oa.tools.LoginUtils;
import com.powerrich.office.oa.tools.MD5Util;
import com.powerrich.office.oa.tools.ToastUtils;
import com.powerrich.office.oa.view.CustomDialog;
import com.powerrich.office.oa.view.MyDialog;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;

import io.reactivex.functions.Consumer;


/**
 * @author MingPeng
 * @date 2018/4/9
 * 社会保险待遇领取资格认证
 */

public class InsuranceReceiveActivity extends BaseActivity implements View.OnClickListener, AEFaceInterface {


    private static final String TAG = InsuranceReceiveActivity.class.getSimpleName();
    private static final int GET_IDENTIFY_LIST = 333;
    private static final int IF_CAN_IDENTIFY = 222;
    private static final int LOGIN = 111;
    private String batchId;
    /**
     * 社保系统内部人员的唯一标识
     */
    private String sessionId;
    private String sysNo;
    private ListView listView;
    private String idCard;
    private String realName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化 人脸识别
        AEFacePack.getInstance().AEYE_Init(this);
        initView();
        UserInfo.DATABean userInfo = LoginUtils.getInstance().getUserInfo().getDATA();
        String userduty = userInfo.getUSERDUTY();
        idCard = userInfo.getIDCARD();
        if ("0".equals(userduty)) {
            realName = userInfo.getREALNAME();
        } else {
            realName = userInfo.getFRDB();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (checkUserInfo()) {
            queryInspectionInfo();
        }
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_insurance_receive;
    }

    private void initView() {
        initTitleBar(getString(R.string.insurance_receive), this, null);
        listView = ((ListView) findViewById(R.id.list_view));
        View emptyView = LayoutInflater.from(this).inflate(R.layout.empty_view, null);
        emptyView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        ((ViewGroup) listView.getParent()).addView(emptyView);
        listView.setEmptyView(emptyView);
        findViewById(R.id.ll_online_declare).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                finish();
                break;
            case R.id.ll_online_declare:
                checkCameraPermission();
                break;
            default:
                break;
        }
    }

    /**
     * 校验用户信息
     *
     * @return
     */
    private boolean checkUserInfo() {
        if (TextUtils.isEmpty(idCard) || idCard.length() < 15) {
            ToastUtils.showMessage(InsuranceReceiveActivity.this, "获取身份证号码失败");
            return false;
        } else if (TextUtils.isEmpty(realName)) {
            ToastUtils.showMessage(InsuranceReceiveActivity.this, "获取姓名失败");
            return false;
        }
        return true;
    }

    @SuppressLint("CheckResult")
    private void checkCameraPermission() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.CAMERA)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            userLogin();
                        } else {
                            showTipDialog("没有相机,请前往设置中打开权限！");
                        }
                    }
                });
    }

    IRequestCallBack callBack = new BaseRequestCallBack() {

        @Override
        public void process(HttpResponse response, int what) {
            ResultItem item = response.getResultItem(ResultItem.class);
            ResultItem data = ((ResultItem) item.get("data"));
            if ("0".equals(data.getString("respCode"))) {
                switch (what) {
                    case LOGIN:
                        sessionId = ((ResultItem) data.get("respData")).getString("sessionId");
                        sysNo = ((ResultItem) data.get("respData")).getString("sysNo");
                        //是否能认证--活体检测--比对--完成认证
                        ifCanIdentify();
                        break;
                    case IF_CAN_IDENTIFY:
                        //获取batchId
                        batchId = ((ResultItem) data.get("respData")).getString("batchId");
                        //respCode为0表示可以认证，开始活体检测
                        startRecog();
                        break;
                    case GET_IDENTIFY_LIST:
                        ResultItem respData = (ResultItem) data.get("respData");
                        List<ResultItem> authInfoList = (List<ResultItem>) respData.get("list");
                        setIdentifyData(authInfoList);
                        break;
                    default:
                        break;
                }
            } else {
                if (what == GET_IDENTIFY_LIST) {

                } else {
                    MyDialog dialog = new MyDialog(InsuranceReceiveActivity.this).builder();
                    dialog.setMessage(data.getString("respMsg"))
                            .setTitle("提示")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setCancelable(false)
                            .setCanceledOnTouchOutside(false);
                    dialog.show();
                }
            }
        }
    };

    /**
     * 设置认证记录数据
     *
     * @param authInfoList
     */
    private void setIdentifyData(List<ResultItem> authInfoList) {
        listView.setAdapter(new CommonAdapter<ResultItem>(InsuranceReceiveActivity.this, authInfoList, R.layout.auth_list_item) {
            @Override
            public void convert(ViewHolder holder, ResultItem item) {
                holder.setTextView(R.id.auth_time, "认证时间：" + DateUtils.longToDate(item.getString("identifyTime")));
                holder.setTextView(R.id.auth_result, "认证结果：" + getResult(item.getInt("identifyResult")));
            }
        });
    }

    private String getResult(int identifyResult) {
        String result = "未知";
        switch (identifyResult) {
            case -1:
                result = "未知";
                break;
            case 0:
                result = "认证失败";
                break;
            case 1:
                result = "认证成功";
                break;
            case 2:
                result = "申诉中";
                break;
            case 3:
                result = "认证驳回";
                break;
            case 4:
                result = "人工审核成功";
                break;
            case 5:
                result = "人工审核失败";
                break;
            case 6:
                result = "认证死亡";
                break;
            default:
                break;
        }
        return result;
    }

    /**
     * 是否可认证
     */
    private void ifCanIdentify() {
        ApiRequest request = OAInterface.ifCanIdentify(sysNo);
        invoke.invokeWidthDialog(request, callBack, IF_CAN_IDENTIFY);
    }

    /**
     * 查询年检信息
     */
    private void queryInspectionInfo() {

//        ApiRequest request = OAInterface.getIdentifyList("36062119580615907X", "吴德才");
        ApiRequest request = OAInterface.getIdentifyList(idCard, realName);
        invoke.invokeWidthDialog(request, callBack, GET_IDENTIFY_LIST);
    }

    /**
     * 登录社保中心
     */
    private void userLogin() {
        String substring = idCard.substring(idCard.length() - 6, idCard.length());
        String password = MD5Util.getMD5(substring);
//        ApiRequest request = OAInterface.userLogin("36062119580615907X", "吴德才", "e10adc3949ba59abbe56e057f20f883e");
//        ApiRequest request = OAInterface.userLogin("360602191711210525", "赵贵莲", password);
        ApiRequest request = OAInterface.userLogin(idCard, realName, password);
        invoke.invokeWidthDialog(request, callBack, LOGIN);
    }

    /**
     * 开始活体检测
     */
    private void startRecog() {
        if (!AEFacePack.getInstance().AEYE_EnvCheck(this, 200 * 1024 * 1024)) {
            Toast.makeText(this, R.string.memory_low, Toast.LENGTH_LONG).show();
            return;
        }

        //设置活体检测参数
        Bundle paras = new Bundle();
        paras.putInt(AEFaceParam.ModelMutiAngleSwitch, 0);//多角度采集（1开启，0关闭，开始是固定采集5张，picnum设置将无效）
        paras.putInt(AEFaceParam.AliveSwitch, 1);//开启活体
        paras.putInt(AEFaceParam.AliveLevel, 1);//活体难度
        paras.putInt(AEFaceParam.VoiceSwitch, 1);//开启语音提示
        paras.putInt(AEFaceParam.AliveMotionNum, 3);//3个随机动作
        paras.putInt(AEFaceParam.FetchImageNum, 1);//采集一张
        paras.putInt(AEFaceParam.QualitySwitch, 1);//开启质量评估
        paras.putInt(AEFaceParam.SingleAliveMotionTime, 10);//单动作10秒超时
        paras.putInt(AEFaceParam.ContinueFailDetectNum, 3);//丢失帧数动作（连续多少帧找不到人脸）
        paras.putInt(AEFaceParam.ContinueSuccessDetectNum, 3);//连续帧数采集（连续多少帧找到人脸）
        //界面类操作
        paras.putInt(AEFaceParam.ColorBottomBarBg, 0x00000000);
        paras.putInt(AEFaceParam.ColorTopBarBg, 0xEE222222);
        paras.putInt(AEFaceParam.ShowBackButton, 1);
        //启动sdkssss
        AEFacePack.getInstance().AEYE_SetListener(this);
        AEFacePack.getInstance().AEYE_SetParameter(paras);
        AEFacePack.getInstance().AEYE_BeginRecog(this);
    }

    @Override
    public void onStart(int i, String s) {

    }

    @Override
    public void onPrompt(int i, String s) {

    }

    @Override
    public void onProcess(int i, String s) {

    }

    @Override
    public void onFinish(int value, String data) {
        Log.d("ZDX", "onFinish code" + value + " data:" + data);
        //检测返回结果，获取图片信息
        Intent recogIntent = new Intent(InsuranceReceiveActivity.this, ResultAliveActivity.class);
        recogIntent.putExtra("VALUE", value);
        recogIntent.putExtra("ERROR_MSG", decodeError(value));
        recogIntent.putExtra("DATA", data);
        recogIntent.putExtra("BATCHID", batchId);
        recogIntent.putExtra("SYSNO", sysNo);
        startActivity(recogIntent);
    }

    private String decodeError(int value) {
        switch (value) {
            default:
            case AEFacePack.SUCCESS:
                if (AEFacePack.getInstance().isAliveOff()) {
                    return getString(R.string.aeye_capture_success);
                }
                return getString(R.string.aeye_alive_success);
            case AEFacePack.ERROR_FAIL:
                return getString(R.string.aeye_capture_fail);
            case AEFacePack.ERROR_TIMEOUT:
                return getString(R.string.aeye_recog_timeout);
            case AEFacePack.ERROR_CANCEL:
                return getString(R.string.aeye_user_cancel);
            case AEFacePack.ERROR_CAMERA:
                return getString(R.string.aeye_camera_error);
        }
    }


    @Override
    protected void onDestroy() {
        //反初始化人脸识别
        AEFacePack.getInstance().AEYE_Destory(this);
        super.onDestroy();
    }
}
