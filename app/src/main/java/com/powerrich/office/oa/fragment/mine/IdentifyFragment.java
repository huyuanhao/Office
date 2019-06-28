package com.powerrich.office.oa.fragment.mine;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aeye.android.uitls.BitmapUtils;
import com.aeye.face.AEFaceInterface;
import com.aeye.face.AEFacePack;
import com.aeye.face.AEFaceParam;
import com.alibaba.fastjson.JSONObject;
import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.sdk.model.IDCardParams;
import com.baidu.ocr.sdk.model.IDCardResult;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.baidu.ocr.ui.camera.CameraNativeHelper;
import com.baidu.ocr.ui.camera.CameraView;
import com.powerrich.office.oa.R;
import com.powerrich.office.oa.activity.mine.IdentifyActivity;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.enums.IdentifyType;
import com.powerrich.office.oa.fragment.mine.base.YtBaseFragment;
import com.powerrich.office.oa.tools.ChooseImgUtils;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.FileUtils;
import com.powerrich.office.oa.tools.LoginUtils;
import com.powerrich.office.oa.tools.PickUtils;
import com.powerrich.office.oa.tools.ToastUtils;
import com.yt.simpleframe.http.bean.xmlentity.XmlUserInfo;
import com.yt.simpleframe.utils.LogUtil;
import com.yt.simpleframe.utils.StringUtil;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/6/21
 * 版权：
 */

public class IdentifyFragment extends YtBaseFragment implements View.OnClickListener, AEFaceInterface {

    IdentifyType type;
    @BindView(R.id.iv_process)
    ImageView mIvProcess;
    @BindView(R.id.iv_card1)
    ImageView mIvCard1;
    @BindView(R.id.iv_card2)
    ImageView mIvCard2;


    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.et_idcard)
    EditText mEtIdcard;
    @BindView(R.id.tv_next)
    TextView mTvNext;

    @BindView(R.id.rb_man)
    RadioButton mRbMan;
    @BindView(R.id.rb_women)
    RadioButton mRbWomen;
    @BindView(R.id.tv_group)
    TextView mTvGroup;
    @BindView(R.id.tv_card_type)
    TextView mTvCardType;
    @BindView(R.id.tv_select_city)
    TextView mTvSelectCity;
    @BindView(R.id.et_adrress)
    EditText mEtAdrress;
    @BindView(R.id.et_email)
    EditText mEtEmail;


    @BindView(R.id.lt_page1)
    RelativeLayout mLtPage1;
    @BindView(R.id.iv_dynamic)
    ImageView mIvDynamic;
    @BindView(R.id.tv_start)
    TextView mTvStart;
    @BindView(R.id.lt_page2)
    LinearLayout mLtPage2;
    @BindView(R.id.tv_end)
    TextView mTvEnd;
    @BindView(R.id.lt_page3)
    LinearLayout mLtPage3;
    Unbinder unbinder;

    private ChooseImgUtils utils;

    private static final int REQUEST_CODE_CAMERA = 102;

//    static String name = "";
//    static String idNumber = "";
//    static String selectCity = "";
//    static String group = "";
//    static String email = "";
//    static String address = "";
//    static String sex = "";


    public static IdentifyFragment getInstance(IdentifyType type) {
        IdentifyFragment fragment = new IdentifyFragment();
        Bundle b = new Bundle();
        b.putSerializable("type", type);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = (IdentifyType) getArguments().getSerializable("type");
        if (type == IdentifyType.页面1) {
            initOcr();
        }
        if (utils == null) {
            utils = new ChooseImgUtils(this, 2);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        if (type == IdentifyType.页面1) {
            mLtPage1.setVisibility(View.VISIBLE);
            mLtPage2.setVisibility(View.GONE);
            mLtPage3.setVisibility(View.GONE);
            mIvProcess.setBackgroundResource(R.drawable.realname_1);

            if (IdentifyActivity.card1Bitmap != null) {
                mIvCard1.setImageBitmap(IdentifyActivity.card1Bitmap);
            }
            if (IdentifyActivity.card2Bitmap != null) {
                mIvCard2.setImageBitmap(IdentifyActivity.card2Bitmap);
            }

        } else if (type == IdentifyType.页面2) {
            mLtPage1.setVisibility(View.GONE);
            mLtPage2.setVisibility(View.VISIBLE);
            mLtPage3.setVisibility(View.GONE);
            mIvProcess.setBackgroundResource(R.drawable.realname_2);
            if(IdentifyActivity.faceBitmap != null){
                mIvDynamic.setImageBitmap(IdentifyActivity.faceBitmap);
            }
            AEFacePack.getInstance().AEYE_Init(mActivity);
        } else {
            mLtPage1.setVisibility(View.GONE);
            mLtPage2.setVisibility(View.GONE);
            mLtPage3.setVisibility(View.VISIBLE);
            mIvProcess.setBackgroundResource(R.drawable.realname_3);
        }

        mTvCardType.setText("身份证");
        mTvGroup.setText(IdentifyActivity.group);
        mTvSelectCity.setText(IdentifyActivity.selectCity);
    }


    @Override
    protected View createContentView() {
        View view = inflateContentView(R.layout.activity_identify);
        ButterKnife.bind(this, view);
        return view;
    }

    protected IdentifyActivity mActivity;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (IdentifyActivity) context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (type == IdentifyType.页面2) {
            AEFacePack.getInstance().AEYE_Destory(mActivity);
        }
    }

    @OnClick({R.id.iv_card1, R.id.iv_card2, R.id.tv_next, R.id.tv_start, R.id.tv_end, R.id.rb_man, R.id.rb_women,
            R.id.tv_group, R.id.tv_card_type, R.id.tv_select_city})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rb_man:
                break;
            case R.id.rb_women:
                break;
            case R.id.iv_card1:
                doPremissionType(1);
                break;
            case R.id.iv_card2:
                doPremissionType(2);
                break;
            case R.id.tv_next:
                if (checkData()) {
                    mActivity.replaceFragment(IdentifyType.页面2);
                }
                break;
            case R.id.tv_start:
                if (IdentifyActivity.faceBoo) {
                    saveUserInfo();
                } else {
                    startRecog();
                }
                break;
            case R.id.tv_end:
                //将图片和内容上传到服务器
//                AppManager.getAppManager().finishActivity(ChooseIdentifyTypeActivity.class);
//                mActivity.finish();
                break;

            case R.id.tv_group:
                PickUtils.showEthnicPickerView(this.mContext, new PickUtils.StringCallback() {
                    @Override
                    public void getString(String city) {
                        mTvGroup.setText(city);
                    }
                });
                break;
            case R.id.tv_card_type:
                PickUtils.showCardIdPickerView(mContext, new PickUtils.StringCallback() {
                    @Override
                    public void getString(String city) {
                        mTvCardType.setText(city);
                    }
                });
                break;
            case R.id.tv_select_city:
                PickUtils.showCityPickerView(mContext, new PickUtils.StringCallback() {
                    @Override
                    public void getString(String city) {
                        mTvSelectCity.setText(city);
                    }
                });
                break;
        }
    }


    public boolean checkData() {
        boolean b = false;
        if (!(IdentifyActivity.card1 && IdentifyActivity.card2)) {
            ToastUtils.showMessage(mActivity, "身份证图像上传不完整,请重新上传");
            return b;
        }
        IdentifyActivity.name = mEtName.getText().toString();
        IdentifyActivity.idNumber = mEtIdcard.getText().toString();
        IdentifyActivity.selectCity = mTvSelectCity.getText().toString();
        IdentifyActivity.group = mTvGroup.getText().toString();
        IdentifyActivity.email = mEtEmail.getText().toString();
        IdentifyActivity.address = mEtAdrress.getText().toString();


        if (TextUtils.isEmpty(IdentifyActivity.name)) {
            ToastUtils.showMessage(mActivity, "名字不能为空！");
            return b;
        }


        if (TextUtils.isEmpty(IdentifyActivity.group)) {
            ToastUtils.showMessage(mActivity, "民族不能为空！");
            return b;
        }

        if (!StringUtil.validCard(IdentifyActivity.idNumber)) {
            ToastUtils.showMessage(mActivity, "身份证号格式错误！");
            return b;
        }

//        if (TextUtils.isEmpty(type)) {
//            ToastUtils.showMessage(mActivity, "证件类型不能为空！");
//            return b;
//        }

        if (TextUtils.isEmpty(IdentifyActivity.selectCity)) {
            ToastUtils.showMessage(mActivity, "选择区域地址不能为空！");
            return b;
        }

        if (TextUtils.isEmpty(IdentifyActivity.address)) {
            ToastUtils.showMessage(mActivity, "具体地址不能为空！");
            return b;
        }

        if (!StringUtil.checkEmail(IdentifyActivity.email)) {
            ToastUtils.showMessage(mActivity, "邮箱填写错误！");
            return b;
        }
        b = true;
        return b;
    }


    public void saveUserInfo() {
        String userName = LoginUtils.getInstance().getUserInfo().getDATA().getUSERNAME();
        String phoneNumber = LoginUtils.getInstance().getUserInfo().getDATA().getMOBILE_NUM();
        IdentifyActivity.sex = "男";
        String sexInt = "0";
        if (mRbWomen.isChecked()) {
            IdentifyActivity.sex = "女";
            sexInt = "1";
        }

        /**
         *
         *
         *       	<name>金牵牛</name>
         <idcard>360700199908121566</idcard>
         <address>江西省鹰潭市月湖区依安小区5号楼501</address>
         <time>1999-08-12</time>
         <ethnic>汉族</ethnic>
         <sex>1</sex>  0是男  1是女
         <appeidcode>authentication值若为1则需要eid的appeidcode信息</appeidcode>
         */
        String token = LoginUtils.getInstance().getUserInfo().getAuthtoken();
        XmlUserInfo info = new XmlUserInfo(IdentifyActivity.name, IdentifyActivity.idNumber, IdentifyActivity.selectCity + " "
                + IdentifyActivity.address
                , "", IdentifyActivity.group, sexInt, "");
//        ApiManager.getApi().exeNormal(RequestBodyUtils.saveUserInfo(token, "0", info))
//                .compose(RxSchedulers.<BaseBean>io_main())
//                .subscribe(new BaseSubscriber<BaseBean>() {
//                    @Override
//                    public void result(BaseBean bean) {
//                        mActivity.replaceFragment(IdentifyType.页面3);
//                        //将消息保存到本地
//                        UserInfo.DATABean user = LoginUtils.getInstance().getUserInfo().getDATA();
//                        user.setSEX(IdentifyActivity.sex);
//                        user.setEMAIL(IdentifyActivity.email);
//                        user.setMZ(IdentifyActivity.group);
//                        user.setIDCARD(IdentifyActivity.idNumber);
//                        user.setIDCARD_ADDRESS(IdentifyActivity.address);
//                        user.setREALNAME(IdentifyActivity.name);
//                        user.setAUDIT_STATE("0");
//                        LoginUtils.getInstance().getUserInfo().setDATA(user);
//                        SharedPreferences sp = mActivity.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor edit = sp.edit();
//                        edit.putString("userInfoBean", GsonUtil.GsonString(LoginUtils.getInstance().getUserInfo()));
//                        edit.commit();
//                    }
//                });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_CAMERA && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                String contentType = data.getStringExtra(CameraActivity.KEY_CONTENT_TYPE);
                String filePath = FileUtils.getSaveFile(mActivity.getApplicationContext()).getAbsolutePath();
                if (!TextUtils.isEmpty(contentType)) {
                    File file = new File(filePath);
                    String fileName = file.getName();
                    String userName = LoginUtils.getInstance().getUserInfo().getDATA().getUSERNAME();
                    if (CameraActivity.CONTENT_TYPE_ID_CARD_FRONT.equals(contentType)) {
                        recIDCard(IDCardParams.ID_CARD_SIDE_FRONT, filePath);
                        IdentifyActivity.card1Bitmap = com.yt.simpleframe.utils.BitmapUtils.sampleBitmap(filePath);
                        mIvCard1.setImageBitmap(IdentifyActivity.card1Bitmap);
                        utils.downLoadCard(new ChooseImgUtils.ExeCallBack() {
                            @Override
                            public void exeCallBack() {
                                IdentifyActivity.card1 = true;
                            }
                        }, fileName, filePath, userName, Constants.UPLOAD_ID_CARD_URL, "1");
                    } else if (CameraActivity.CONTENT_TYPE_ID_CARD_BACK.equals(contentType)) {
                        IdentifyActivity.card2Bitmap = com.yt.simpleframe.utils.BitmapUtils.sampleBitmap(filePath);
                        recIDCard(IDCardParams.ID_CARD_SIDE_BACK, filePath);
                        mIvCard2.setImageBitmap(IdentifyActivity.card2Bitmap);
                        utils.downLoadCard(new ChooseImgUtils.ExeCallBack() {
                            @Override
                            public void exeCallBack() {
                                IdentifyActivity.card2 = true;
                            }
                        }, fileName, filePath, userName, Constants.UPLOAD_ID_CARD_URL, "2");
                    }
                }
            }
        }
    }


    private void recIDCard(String idCardSide, String filePath) {
        IDCardParams param = new IDCardParams();
        param.setImageFile(new File(filePath));
        param.setIdCardSide(idCardSide);
        param.setDetectDirection(true);
        OCR.getInstance().recognizeIDCard(param, new OnResultListener<IDCardResult>() {
            @Override
            public void onResult(IDCardResult result) {
                if (result != null) {
                    Log.d("onResult", "result: " + result.toString());
                    try {
                        mEtName.setText(result.getName().toString());
                        mEtIdcard.setText(result.getIdNumber().toString());
                        mRbWomen.setChecked("女".equals(result.getGender().toString()));
                        mRbMan.setChecked("男".equals(result.getGender().toString()));
                        mTvGroup.setText(result.getEthnic().toString());
                        mTvCardType.setText("身份证");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError(OCRError error) {
                Log.d("onError", "error: " + error.getMessage());
//                ToastUtils.showMessage(mActivity, error.getMessage());
            }
        });
    }


    private void initOcr() {
        OCR.getInstance().initAccessTokenWithAkSk(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken result) {
                // 本地自动识别需要初始化
                initLicense();
            }

            @Override
            public void onError(OCRError error) {
                error.printStackTrace();
                Log.e("MainActivity", "onError: " + error.getMessage());
//                ToastUtils.showMessage(mActivity, error.getMessage());
            }
        }, mActivity.getApplicationContext(), "DGVSwGjgKPNGM9yoj2GdXNGX", "doGDh5c4GLmRv2zrryB90hXuUg0ooFM4");
    }

    private void initLicense() {
        CameraNativeHelper.init(mActivity, OCR.getInstance().getLicense(),
                new CameraNativeHelper.CameraNativeInitCallback() {
                    @Override
                    public void onError(int errorCode, Throwable e) {
                        final String msg;
                        switch (errorCode) {
                            case CameraView.NATIVE_SOLOAD_FAIL:
                                msg = "加载so失败，请确保apk中存在ui部分的so";
                                break;
                            case CameraView.NATIVE_AUTH_FAIL:
                                msg = "授权本地质量控制token获取失败";
                                break;
                            case CameraView.NATIVE_INIT_FAIL:
                                msg = "本地质量控制";
                                break;
                            default:
                                msg = String.valueOf(errorCode);
                        }
                        LogUtil.log("IdentifyActivity", msg);
                    }
                });
    }

    /**
     * 开始活体检测
     */
    private void startRecog() {
        if (!AEFacePack.getInstance().AEYE_EnvCheck(mActivity, 200 * 1024 * 1024)) {
            Toast.makeText(mActivity, R.string.memory_low, Toast.LENGTH_LONG).show();
            return;
        }

        //设置活体检测参数
        Bundle paras = new Bundle();
        paras.putInt(AEFaceParam.ModelMutiAngleSwitch, 1);//多角度采集（1开启，0关闭，开始是固定采集5张，picnum设置将无效） 0
        paras.putInt(AEFaceParam.AliveSwitch, 0);//开启活体  1
        paras.putInt(AEFaceParam.AliveLevel, 1);//活体难度
        paras.putInt(AEFaceParam.VoiceSwitch, 1);//开启语音提示
        paras.putInt(AEFaceParam.AliveMotionNum, 3);//3个随机动作
        paras.putInt(AEFaceParam.FetchImageNum, 1);//采集一张
        paras.putInt(AEFaceParam.QualitySwitch, 1);//开启质量评估
        paras.putInt(AEFaceParam.SingleAliveMotionTime, 5);//单动作10秒超时  10
        paras.putInt(AEFaceParam.ContinueFailDetectNum, 3);//丢失帧数动作（连续多少帧找不到人脸）
        paras.putInt(AEFaceParam.ContinueSuccessDetectNum, 3);//连续帧数采集（连续多少帧找到人脸）
        //界面类操作
        paras.putInt(AEFaceParam.ColorBottomBarBg, 0x00000000);
        paras.putInt(AEFaceParam.ColorTopBarBg, 0xEE222222);
        paras.putInt(AEFaceParam.ShowBackButton, 1);
        //启动sdkssss
        AEFacePack.getInstance().AEYE_SetListener(this);
        AEFacePack.getInstance().AEYE_SetParameter(paras);
        AEFacePack.getInstance().AEYE_BeginRecog(mActivity);
    }

    @Override
    public void onClick(View v) {

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


    String localFilePath = "";

    @Override
    public void onFinish(int value, String data) {
        Log.d("ZDX", "onFinish code" + value + " data:" + data);
        if (data != null) {
            JSONObject json = JSONObject.parseObject(data);
            JSONObject jsonImage = json.getJSONObject("images");
            String strImage = jsonImage.getString("0");
            Bitmap bitmap = BitmapUtils.AEYE_Base64Decode(strImage);
            mIvDynamic.setImageBitmap(bitmap);
            IdentifyActivity.faceBitmap = bitmap;
            mTvStart.setText("下一步");
            //将bitmap保存到本地文件中

            String userName = LoginUtils.getInstance().getUserInfo().getDATA().getUSERNAME();
            String localDir = new File(Environment.getExternalStorageDirectory() + "/download").getPath();
            localFilePath = localDir + "/face.png";
            if (com.yt.simpleframe.utils.BitmapUtils.saveBitmap(IdentifyActivity.faceBitmap, localFilePath)) {
                utils.upload(new ChooseImgUtils.ExeCallBack() {
                    @Override
                    public void exeCallBack() {
                        IdentifyActivity.faceBoo = true;
                    }
                }, "face.png", localFilePath, userName, Constants.UPLOAD_HEAD_FACE_URL);
            }
        } else {
            ToastUtils.showMessage(mActivity, "识别失败！在来一次");
        }
    }

    private void startCamera(String type) {

        Intent intent = new Intent(mActivity, CameraActivity.class);
        intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                FileUtils.getSaveFile(mActivity.getApplication()).getAbsolutePath());
        intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, type);
        startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }


    private void doPremissionType(final int type) {
        doPremissionCamera(new BaseActivity.PermissionCallBack() {
            @Override
            public void accept() {
                if (type == 1) {
                    startCamera(CameraActivity.CONTENT_TYPE_ID_CARD_FRONT);
                } else if (type == 2) {
                    startCamera(CameraActivity.CONTENT_TYPE_ID_CARD_BACK);
                }
            }
        });
    }
}
