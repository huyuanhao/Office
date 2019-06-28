package com.powerrich.office.oa.fragment.mine;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.powerrich.office.oa.activity.mine.base.IdentifyCompanyActivity;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.bean.UserInfo;
import com.powerrich.office.oa.enums.IdentifyType;
import com.powerrich.office.oa.fragment.mine.base.YtBaseFragment;
import com.powerrich.office.oa.tools.ChooseImgUtils;
import com.powerrich.office.oa.tools.FileUtils;
import com.powerrich.office.oa.tools.GsonUtil;
import com.powerrich.office.oa.tools.LoginUtils;
import com.powerrich.office.oa.tools.ToastUtils;
import com.powerrich.office.oa.view.MyDialog;
import com.yt.simpleframe.http.ApiManager;
import com.yt.simpleframe.http.BaseSubscriber;
import com.yt.simpleframe.http.RxSchedulers;
import com.yt.simpleframe.http.bean.BaseBean;
import com.yt.simpleframe.http.bean.xmlentity.XmlCompanyInfo;
import com.yt.simpleframe.http.requst.RequestBodyUtils;
import com.yt.simpleframe.utils.BitmapUtils;
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

public class IdentifyCompanyFragment extends YtBaseFragment {

    IdentifyType type;

    private static final int REQUEST_CODE_CAMERA = 102;
    @BindView(R.id.iv_process)
    ImageView mIvProcess;
    @BindView(R.id.rg_company)
    RadioGroup mRgCompany;

    @BindView(R.id.rb_type1)
    RadioButton mRbType1;
    @BindView(R.id.rb_type2)
    RadioButton mRbType2;
    @BindView(R.id.rb_type3)
    RadioButton mRbType3;
    @BindView(R.id.rb_type4)
    RadioButton mRbType4;
    @BindView(R.id.et_og_name)
    EditText mEtOgName;
    @BindView(R.id.et_og_code)
    EditText mEtOgCode;
    @BindView(R.id.et_og_address)
    EditText mEtOgAddress;
    //    @BindView(R.id.et_name)
//    EditText mEtName;
//    @BindView(R.id.et_card_id)
//    EditText mEtCardId;
//    @BindView(R.id.iv_bsns)
//    ImageView mIvBsns;
    @BindView(R.id.iv_businiess)
    ImageView mIvBusiniess;
    @BindView(R.id.tv_next)
    TextView mTvNext;
    @BindView(R.id.lt_page1)
    RelativeLayout mLtPage1;
    @BindView(R.id.iv_card1)
    ImageView mIvCard1;
    @BindView(R.id.iv_card2)
    ImageView mIvCard2;
    @BindView(R.id.et_card_name)
    EditText mEtCardName;
    @BindView(R.id.et_idcard)
    EditText mEtIdcard;
    @BindView(R.id.et_card_address)
    EditText mEtCardAddress;
    @BindView(R.id.tv_submit)
    TextView mTvSubmit;
    @BindView(R.id.lt_page2)
    LinearLayout mLtPage2;
    @BindView(R.id.tv_end)
    TextView mTvEnd;
    @BindView(R.id.lt_page3)
    LinearLayout mLtPage3;
    Unbinder unbinder;



    String picPath;
    ChooseImgUtils imageUtils;

    public static IdentifyCompanyFragment getInstance(IdentifyType type) {
        IdentifyCompanyFragment fragment = new IdentifyCompanyFragment();
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
        if (imageUtils == null) {
            imageUtils = new ChooseImgUtils(this, 2);
        }
    }

    @Override
    protected View createContentView() {
        View view = inflateContentView(R.layout.activity_identify_company);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    protected IdentifyCompanyActivity mActivity;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (IdentifyCompanyActivity) context;
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

    }

    @OnClick({R.id.iv_card1, R.id.iv_card2, R.id.iv_businiess, R.id.tv_next, R.id.tv_submit, R.id.tv_end})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.iv_card1:
                doPremissionType(1);
                break;
            case R.id.iv_card2:
                doPremissionType(2);
                break;
            case R.id.iv_businiess:
                imageUtils = new ChooseImgUtils(this, 2);
                imageUtils.showSelectPhotoDialog();
                break;
            case R.id.tv_next:
                IdentifyCompanyActivity.ogName = mEtOgName.getText().toString().trim();
                IdentifyCompanyActivity.ogCode = mEtOgCode.getText().toString().trim();
                IdentifyCompanyActivity.ogAddress = mEtOgAddress.getText().toString().trim();
                IdentifyCompanyActivity.cardName = mEtCardName.getText().toString().trim();
                IdentifyCompanyActivity.idCard = mEtIdcard.getText().toString().trim();
                IdentifyCompanyActivity.cardAddress = mEtCardAddress.getText().toString().trim();
                if (checkPage1(IdentifyCompanyActivity.ogName, IdentifyCompanyActivity.ogCode, IdentifyCompanyActivity.ogAddress,
                        IdentifyCompanyActivity.cardName, IdentifyCompanyActivity.idCard, IdentifyCompanyActivity.cardAddress)) {
                    //弹出dialog
                    MyDialog.showDialog(mActivity, "提示信息", "法人信息一旦提交将不允许修改", new MyDialog.InterfaceClick() {
                        @Override
                        public void click() {
                            saveCompanyInfo();
                        }
                    });
                }
                break;
            case R.id.tv_submit:
                if (IdentifyCompanyActivity.companyBoo) {
                    saveCompanyInfo();
                } else {
                    ToastUtils.showMessage(mActivity, "营业执照上传不完整,请重新上传");
                }

                break;
            case R.id.tv_end:
                mActivity.finish();
                break;
        }
    }


    public void saveCompanyInfo() {

        /**
         * <auditRegInfo>
         <userduty>法人类型：0企业，1社会团体，2事业单位，3行政机关</userduty>
         <enterpriseName>组织机构名称</enterpriseName>
         <enterpriseCode>统一社会信用代码</enterpriseCode>
         <enterpriseAddress>组织机构地址</enterpriseAddress>
         <enterprisePerson>法人姓名</enterprisePerson>
         <idcard>法人身份证号码</idcard>
         </auditRegInfo>
         */
        String name = LoginUtils.getInstance().getUserInfo().getDATA().getUSERNAME();

        String token = LoginUtils.getInstance().getUserInfo().getAuthtoken();
        XmlCompanyInfo info = new XmlCompanyInfo(IdentifyCompanyActivity.companyType, IdentifyCompanyActivity.ogName, IdentifyCompanyActivity.ogCode
                , IdentifyCompanyActivity.ogAddress, IdentifyCompanyActivity.cardName, IdentifyCompanyActivity.idCard);
        ApiManager.getApi().exeNormal(RequestBodyUtils.saveCompanyInfo(token, info))
                .compose(RxSchedulers.<BaseBean>io_main())
                .subscribe(new BaseSubscriber<BaseBean>() {
                    @Override
                    public void result(BaseBean bean) {
//                        mActivity.replaceFragment(IdentifyType.页面3);
                        //将消息保存到本地
                        UserInfo.DATABean user = LoginUtils.getInstance().getUserInfo().getDATA();
                        user.setCOMPANYNAME(IdentifyCompanyActivity.ogName);
                        user.setFRDB(IdentifyCompanyActivity.cardName);
                        user.setIDCARD(IdentifyCompanyActivity.idCard);
                        user.setFR_PHONE_NUM(IdentifyCompanyActivity.phone);
                        user.setQYTXDZ(IdentifyCompanyActivity.ogAddress);
                        user.setAUDIT_STATE("0");
                        LoginUtils.getInstance().getUserInfo().setDATA(user);
                        SharedPreferences sp = mActivity.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                        SharedPreferences.Editor edit = sp.edit();
                        edit.putString("userInfoBean", GsonUtil.GsonString(LoginUtils.getInstance().getUserInfo()));
                        edit.commit();
                        mActivity.finish();
                    }
                });
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
        } else if (type == IdentifyType.页面2) {
            mLtPage1.setVisibility(View.GONE);
            mLtPage2.setVisibility(View.VISIBLE);
            mLtPage3.setVisibility(View.GONE);
            mIvProcess.setBackgroundResource(R.drawable.realname_2);
        } else {
            mLtPage1.setVisibility(View.GONE);
            mLtPage2.setVisibility(View.GONE);
            mLtPage3.setVisibility(View.VISIBLE);
            mIvProcess.setBackgroundResource(R.drawable.realname_3);
        }
    }

    private boolean checkPage1(String ogName, String ogCode, String ogAddress, String name, String cardId, String address) {
        boolean b = false;

        if (!mRbType1.isChecked() && !mRbType2.isChecked() && !mRbType3.isChecked() && !mRbType4.isChecked()) {
            ToastUtils.showMessage(mActivity, "请选择法人类型");
            return b;
        }

        if(mRbType1.isChecked()){
            IdentifyCompanyActivity.companyType = "0";
        }else if(mRbType2.isChecked()){
            IdentifyCompanyActivity.companyType = "1";
        }else if(mRbType3.isChecked()){
            IdentifyCompanyActivity.companyType = "2";
        }else if(mRbType4.isChecked()){
            IdentifyCompanyActivity.companyType = "3";
        }


        if (TextUtils.isEmpty(name)) {
            ToastUtils.showMessage(mActivity, "法人姓名不能为空");
            return b;
        }


        if (TextUtils.isEmpty(ogName)) {
            ToastUtils.showMessage(mActivity, "组织机构名称不能为空");
            return b;
        }
        if (TextUtils.isEmpty(ogCode)) {
            ToastUtils.showMessage(mActivity, "统一社会信用代码不能为空");
            return b;
        }
        if (TextUtils.isEmpty(ogAddress)) {
            ToastUtils.showMessage(mActivity, "组织机构地址不能为空");
            return b;
        }

        if (TextUtils.isEmpty(name)) {
            ToastUtils.showMessage(mActivity, "法人姓名不能为空");
            return b;
        }
        if (!StringUtil.validCard(cardId)) {
            ToastUtils.showMessage(mActivity, "身份证号不正确");
            return b;
        }
        if (TextUtils.isEmpty(address)) {
            ToastUtils.showMessage(mActivity, "身份证地址不能为空");
            return b;
        }

        return true;
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
                        IdentifyCompanyActivity.cardBitmap = BitmapUtils.sampleBitmap(filePath);
                        mIvCard1.setImageBitmap(IdentifyCompanyActivity.cardBitmap);

//                        imageUtils.downLoadCard(new ChooseImgUtils.ExeCallBack() {
//                            @Override
//                            public void exeCallBack() {
//                                IdentifyCompanyActivity.card1 = true;
//                            }
//                        }, fileName, filePath, userName, Constants.UPLOAD_ID_CARD_URL, "1");

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
                        mEtCardName.setText(result.getName().toString());
                        mEtIdcard.setText(result.getIdNumber().toString());
                        mEtCardAddress.setText(result.getAddress().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError(OCRError error) {
                Log.d("onError", "error: " + error.getMessage());
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
                ToastUtils.showMessage(mActivity, error.getMessage());
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
