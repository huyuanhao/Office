package com.powerrich.office.oa.activity.Interaction;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.imnjh.imagepicker.SImagePicker;
import com.imnjh.imagepicker.activity.PhotoPickerActivity;
import com.powerrich.office.oa.R;
import com.powerrich.office.oa.adapter.ImageSelectAdapter;
import com.powerrich.office.oa.api.ApiRequest;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.bean.IWantBean;
import com.powerrich.office.oa.bean.IwantInfo;
import com.powerrich.office.oa.bean.JsonBean;
import com.powerrich.office.oa.bean.UserInfo;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DialogUtils;
import com.powerrich.office.oa.tools.DownloadUtils;
import com.powerrich.office.oa.tools.LoginUtils;
import com.powerrich.office.oa.tools.PhotoUtils;
import com.powerrich.office.oa.tools.progressutils.listener.impl.UIProgressListener;
import com.powerrich.office.oa.view.LoadingDialog;
import com.powerrich.office.oa.view.MyDialog;
import com.powerrich.office.oa.view.MySpinner;
import com.powerrich.office.oa.view.ZzImageBox;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yt.simpleframe.utils.T;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import top.zibin.luban.Luban;

/**
 * 我要建议、我要咨询、我要投诉、我要举报界面
 *
 * @author Administrator
 */
public class IwantActivity extends BaseActivity implements View.OnClickListener {
    /**
     * 获取部门列表
     */
    private static int GETSITELIST = 000;
    private static int GET_SHOW_DATA = 0001;
    /**
     * 咨询，建议，投诉
     */
    private static int IWANT = 111;
    @BindView(R.id.tv_dep_name)
    TextView tvDepName;
    @BindView(R.id.spinner_dep)
    MySpinner spinnerDep;
    @BindView(R.id.et_dep)
    EditText etDep;
    @BindView(R.id.et_title)
    EditText etTitle;
    @BindView(R.id.cb_no)
    RadioButton cbNo;
    @BindView(R.id.cb_yes)
    RadioButton cbYes;
    @BindView(R.id.gender)
    RadioGroup gender;
    @BindView(R.id.ll_rg)
    LinearLayout llRg;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_id)
    EditText etId;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.cb_short_msg)
    CheckBox cbShortMsg;
    @BindView(R.id.cb_mail)
    CheckBox cbMail;
    @BindView(R.id.ll_cb)
    LinearLayout llCb;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.tv_cacel)
    TextView tvCacel;
    @BindView(R.id.rv_img)
    RecyclerView rvImg;
    @BindView(R.id.tv_more)
    TextView tvMore;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;
    @BindView(R.id.ll_type)
    LinearLayout llType;
    @BindView(R.id.spinner_type)
    MySpinner spinnerType;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_hotline)
    TextView tvHotline;
    @BindView(R.id.bar_back)
    ImageView barBack;
    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.zz_image_box)
    ZzImageBox imageBox;
    @BindView(R.id.cb_ts)
    RadioButton cbTs;
    @BindView(R.id.cb_jy)
    RadioButton cbJy;
    @BindView(R.id.radio_type)
    RadioGroup radioType;
    /**
     * 姓名，电话，手机，电子邮箱，邮政编码，地址，标题，内容
     */
    private IwantInfo info;
    private String iwant_type;
    private String dep_name;
    private String dep_id;
    String[] letterTypes = new String[]{"建议", "咨询", "投诉", "举报", "求决", "其它"};
    /**
     * 单位名称
     */
    private String id;
    LoadingDialog daDialog;

    ImageSelectAdapter imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        daDialog = new LoadingDialog(this);
        daDialog.setMessage(getString(R.string.system_upload_message));
        setUserInfo(LoginUtils.getInstance().getUserInfo());

        //获取城市数据
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);

        iwant_type = getIntent().getStringExtra("iwant_type");
        dep_id = getIntent().getStringExtra("dep_id");
        dep_name = getIntent().getStringExtra("dep_name");
        id = getIntent().getStringExtra("id");
        initView();
        initData();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_iwant;
    }

    private void setUserInfo(UserInfo info) {
        UserInfo.DATABean bean = info.getDATA();
        if (bean != null) {
            etName.setFocusable(false);
            etId.setFocusable(false);
            etPhone.setFocusable(false);
            etEmail.setFocusable(false);
            etAddress.setFocusable(false);
            if (bean.getUSERDUTY().equals("0")) {
                etName.setText(!TextUtils.isEmpty(bean.getREALNAME()) ? bean.getREALNAME() : bean.getUSERNAME());
                etId.setText(bean.getIDCARD());
                etPhone.setText(bean.getMOBILE_NUM());
                etEmail.setText(bean.getEMAIL());
            } else {
                etName.setText(bean.getFRDB());
                etId.setText(bean.getFRDB_SFZHM());
                etPhone.setText(bean.getGR_REALPHONE());
                etEmail.setText(bean.getGR_EMAIL());
            }
            if (TextUtils.isEmpty(bean.getADDR())) {
                etAddress.setFocusableInTouchMode(true);
                etAddress.setFocusable(true);
                etAddress.requestFocus();
            } else {
                etAddress.setText(bean.getADDR());
            }
        }
    }

    private void showData(IWantBean iWantBean) {
        etName.setText(iWantBean.getDATA().getMYNAME());
        etPhone.setText(iWantBean.getDATA().getMOBILE());
        etAddress.setText(iWantBean.getDATA().getADDRESS());
        etTitle.setText(iWantBean.getDATA().getTITLE());
        etContent.setText(iWantBean.getDATA().getQUESTION());
        etDep.setText(iWantBean.getDATA().getQUSTIONTYPE());
    }

    private void initView() {
        tvMore.setVisibility(View.GONE);
        llBottom.setVisibility(View.VISIBLE);
        spinnerDep.setHint("请选择部门");
        spinnerDep.getImageView().setImageResource(R.drawable.arrow_right);
        spinnerDep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IwantActivity.this, DepartmentActivity.class);
                startActivityForResult(intent, 101);
            }
        });
        //根据iwant_type判断显示内容
        if (Constants.CONSULTING_TYPE.equals(iwant_type)) {
            barTitle.setText(R.string.new_advice);
            tvDepName.setText(R.string.admissible_department);
            llType.setVisibility(View.GONE);
        } else if (Constants.COMPLAIN_TYPE.equals(iwant_type)) {
            barTitle.setText(R.string.new_complaint);
            tvDepName.setText(R.string.complaint_department);
            llType.setVisibility(View.GONE);
            tvType.setText("类型");
            cbTs.setChecked(true);
            letterTypes = new String[]{"投诉", "建议"};
        } else if (Constants.SUGGEST_TYPE.equals(iwant_type)) {
            barTitle.setText(R.string.new_proposal);
            tvDepName.setText(R.string.proposal_department);
            llType.setVisibility(View.GONE);
            tvType.setText("类型");
            cbJy.setChecked(true);
            letterTypes = new String[]{"投诉", "建议"};
        } else if (Constants.LETTER_TYPE.equals(iwant_type)) {
            barTitle.setText(R.string.mayoral_mailbox);
            tvDepName.setText(R.string.admissible_department);
            llType.setVisibility(View.GONE);
            tvType.setText("信件类型");
            tvHotline.setVisibility(View.VISIBLE);
        }
        tvRight.setVisibility(View.GONE);
        tvRight.setText(R.string.save);
        tvRight.setOnClickListener(this);
        setImageAdapter();
    }

    private void initData() {
        cbMail.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    etEmail.setHint(getResources().getString(R.string.online_declare_hint_must));
                } else {
                    etEmail.setHint("");
                }
            }
        });
        radioType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == cbTs.getId()) {
                    barTitle.setText(R.string.complaint_department);
                    tvDepName.setText(R.string.complaint_department);
                    iwant_type = Constants.COMPLAIN_TYPE;
                } else if (checkedId == cbJy.getId()) {
                    barTitle.setText(R.string.proposal_department);
                    tvDepName.setText(R.string.proposal_department);
                    iwant_type = Constants.SUGGEST_TYPE;
                }
            }
        });
        gender.setTag("0");
        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == cbNo.getId()) {
                    gender.setTag("0");
                } else if (checkedId == cbYes.getId()) {
                    gender.setTag("1");
                }

            }
        });
    }

    List<IwantInfo.FilelistBean> imgInfos = new ArrayList<>();
    private int uploadPosition = 0;

    private void uploadFile() {
        uploadPosition = 0;
        imgInfos.clear();
        for (int i = 0; i < filelist.size(); i++) {
            if (filelist.get(i).equals("")) {
                filelist.remove(i);
            }
        }
        upLoad();
    }

    public void upLoad() {
        DownloadUtils.upload5(uiProgressListener, Constants.UPLOAD_URL4, filelist.get(uploadPosition), new DownloadUtils.InvokeData() {
            @Override
            public void success(String result) {
                uploadPosition++;
                try {
                    //{"DATA":{"DOWNPATH":"/xzsp/20180706/201807061647207185569463862","FILENAME":"1530866840716_tab_0_selected.png",
                    // "FLAG":"true"},"code":"0","message":"操作成功!"}
                    JSONObject object = new JSONObject(result);
                    JSONObject data = object.optJSONObject("DATA");
                    IwantInfo.FilelistBean filelistBean = new IwantInfo.FilelistBean();
                    filelistBean.setFilename(data.optString("FILENAME"));
                    filelistBean.setDownpath(data.optString("DOWNPATH"));
                    imgInfos.add(filelistBean);
                    info.setFilelist(imgInfos);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (uploadPosition == filelist.size()) {
                    handler.sendEmptyMessage(0);
                    return;
                }
                upLoad();
            }

            @Override
            public void failed() {
                T.showShort(IwantActivity.this, "上传失败,请检查网络");
            }
        });
    }

    UIProgressListener uiProgressListener = new UIProgressListener() {
        @Override
        public void onUIProgress(long currentBytes, long contentLength, boolean done) {

        }
    };

    /**
     * 咨询，建议，投诉接口
     */
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            iWantSuggestion();
        }
    };

    private void iWantSuggestion() {
        ApiRequest request = OAInterface.iWants(iwant_type, info);
        if (null != invoke)
            invoke.invoke(request, callBack, IWANT);
    }

    IRequestCallBack callBack = new BaseRequestCallBack() {

        @Override
        public void process(HttpResponse response, int what) {
            if (daDialog != null && daDialog.isShowing()) {
                daDialog.dismiss();
            }
            ResultItem item = response.getResultItem(ResultItem.class);
            String code = item.getString("code");
            String message = item.getString("message");
            if (Constants.SUCCESS_CODE.equals(code)) {
                if (IWANT == what) {
                    DialogUtils.showToast(IwantActivity.this, message);
                    IwantActivity.this.finish();
                } else if (GETSITELIST == what) {
                    List<ResultItem> data = item.getItems("DATA");
                } else if (GET_SHOW_DATA == what) {
                    String jsonStr = item.getJsonStr();
                    Gson gson = new Gson();
                    IWantBean iWantBean = gson.fromJson(jsonStr, IWantBean.class);
                    showData(iWantBean);
                }
            } else {
                DialogUtils.showToast(IwantActivity.this, message);
            }
        }

    };

    //设置类型，投诉建议只有两种类型
    private void setLetterType() {
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, letterTypes);
//        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setHint("请选择类型");
        int pixels_unit_five = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 300, getResources().getDisplayMetrics());
//        spinnerType.setList(letterTypes, AutoUtils.getDisplayWidthValue(300), LinearLayout.LayoutParams.WRAP_CONTENT);
//        spinnerType.setOnItemSelectedListener(new MySpinner.OnItemSelectedListener() {
//            @Override
//            public void SelectedListener(AdapterView<?> adapterView, View view, int position, long l) {
//                letterType = letterTypes[position];
//                spinnerType.setText(letterType);
//                if (Constants.COMPLAIN_TYPE.equals(iwant_type) || Constants.SUGGEST_TYPE.equals(iwant_type)) {
//                    barTitle.setText("新增" + letterType);
//                    if (position == 0) {
//                        iwant_type = Constants.COMPLAIN_TYPE;
//                        tvDepName.setText(R.string.complaint_department);
//                    } else if (position == 1) {
//                        iwant_type = Constants.SUGGEST_TYPE;
//                        tvDepName.setText(R.string.proposal_department);
//                    }
//                }
//            }
//        });
    }

    private void setImageAdapter() {
        //如果需要加载网络图片，添加此监听。
        imageBox.setOnlineImageLoader(new ZzImageBox.OnlineImageLoader() {
            @Override
            public void onLoadImage(ImageView iv, String url) {
                Glide.with(IwantActivity.this).load(url).fitCenter().dontAnimate().into(iv);
            }
        });
        imageBox.setOnImageClickListener(new ZzImageBox.OnImageClickListener() {

            @Override
            public void onImageClick(int position, String filePath, ImageView iv) {
            }

            @Override
            public void onDeleteClick(int position, String filePath) {
                imageBox.removeImage(position);
                pathList.remove(position);
                filelist.remove(position);
            }

            @Override
            public void onAddClick() {
                RxPermissions rxPermissions = new RxPermissions(IwantActivity.this);
                rxPermissions
                        .request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) throws Exception {
                                if (aBoolean) {
                                    String[] items = new String[]{"拍照", "从相册选择"};
                                    new MyDialog(IwantActivity.this, Gravity.BOTTOM).builder()
                                            .SetTitleGone(true)
                                            .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    dialogInterface.dismiss();
                                                    switch (i){
                                                        case 0:
                                                            if(pathList.size()>= 6){
                                                                new MyDialog(IwantActivity.this).builder().setTitle("最多选择6张图片")
                                                                        .show();
                                                            }else {
                                                                PhotoUtils.takePicture(IwantActivity.this);
                                                            }
                                                            break;
                                                        case 1:
                                                            SImagePicker
                                                                    .from(IwantActivity.this)
                                                                    .setSelected(pathList)
                                                                    .maxCount(6)
                                                                    .rowCount(4)
                                                                    .pickText(R.string.general_ok)
                                                                    .pickMode(SImagePicker.MODE_IMAGE)
//                                            .fileInterceptor(new SingleFileLimitInterceptor())
                                                                    .forResult(PhotoUtils.ALBUM_REQUEST_CODE);
                                                            break;
                                                    }
                                                }
                                            })
                                            .show();
                                } else {
                                    showTipDialog("没有相机权限,请前往设置中打开权限！");
                                }
                            }
                        });
            }
        });
    }

    //弹出城市选择器
    public void showCityPickerView() {
        //条件选择器
        OptionsPickerView pvOptions = new OptionsPickerBuilder(IwantActivity.this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText()
                        + options2Items.get(options1).get(option2)
                        + options3Items.get(options1).get(option2).get(options3);
                tvCity.setText(tx);
            }
        }).build();
        pvOptions.setPicker(options1Items, options2Items, options3Items);
        pvOptions.show();
    }

    private boolean setReturnMsg(String msg) {
        DialogUtils.showToast(IwantActivity.this, msg);
        return false;
    }

    //提交前判断
    private boolean validate() {
        String s = spinnerDep.getText();
        if (spinnerDep.getText() == null || spinnerDep.getText().equals("")) {
            return setReturnMsg(getResources().getString(R.string.please_choose_department));
//        } else if (!Constants.CONSULTING_TYPE.equals(iwant_type) && (spinnerType.getText() == null || spinnerType.getText().equals(""))) {
//            return setReturnMsg(getResources().getString(R.string.please_select_the_type));
        } else if (BeanUtils.isEmptyStr(info.getTitle())) {
            return setReturnMsg(getResources().getString(R.string.title_can_not_be_empty));
        } else if (BeanUtils.isEmptyStr(info.getContent())) {
            return setReturnMsg(getResources().getString(R.string.content_can_not_be_empty));
        } else if (info.getContent().toString().length() < 20) {
            return setReturnMsg(getResources().getString(R.string.The_content_should_not_be_less_than_20_words));
        } else if (BeanUtils.isEmptyStr(info.getName())) {
            return setReturnMsg(getResources().getString(R.string.name_can_not_be_empty));
        } else if (!BeanUtils.isIdCard(info.getIdcard())) {
            return setReturnMsg(getResources().getString(R.string.id_can_not_be_empty));
        } else if (!BeanUtils.isMobileNO(info.getPhone())) {
            return setReturnMsg(getResources().getString(R.string.phone_can_not_be_empty));
        } else if (TextUtils.isEmpty(tvCity.getText().toString())) {
            return setReturnMsg(getResources().getString(R.string.please_choose_the_provincial_city));
        }
//        else if (TextUtils.isEmpty(etAddress.getText().toString())) {
//            return setReturnMsg(getResources().getString(R.string.please_enter_your_address));
//        }
        return true;
    }

    /**
     * 设置信息
     */
    private void setSuggestionInfo() {
        if (null == info) {
            info = new IwantInfo();
        }

        //领导信箱信件类型，领导信箱专用，其它类型请传空值。传值：1、建议；2、咨询；3、投诉；4、举报；5、求决；6、其它
        String emailtype = "";
//        if (Constants.LETTER_TYPE.equals(iwant_type)) {
//            emailtype = letterType;
//        }
        info.setEmailtype(emailtype);//同上
        info.setDept(dep_name);//部门
        info.setDeptid(dep_id);//部门id
        info.setTitle(etTitle.getText().toString());//标题
        info.setContent(etContent.getText().toString());//内容
        info.setIsopen(gender.getTag().toString());//是否公开
        info.setName(etName.getText().toString());//姓名
        info.setIdcard(etId.getText().toString());//身份证
        info.setPhone(etPhone.getText().toString());//手机号码
        info.setIsnote(cbShortMsg.isChecked() ? "1" : "0");//是否短信通知

//        String city = tvCity.getText().toString();
        String address = etAddress.getText().toString();
        info.setAddress(address);//地址
//        info.setLoginTime(DateUtils.getDateStr(new Date()));
//        info.setPhoneNumber(tel);
//        info.setMail(cbMail.isChecked());
//        info.setEmail(email);
//        info.setPostNumber(postalCode);
//        info.setLocal("");
    }

    @OnClick({R.id.bar_back, R.id.tv_right, R.id.tv_submit, R.id.tv_cacel, R.id.tv_more, R.id.tv_city})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.bar_back:
            case R.id.tv_cacel:
                finish();
                break;
            case R.id.tv_right:
            case R.id.tv_submit:
                setSuggestionInfo();
                if (!validate()) {
                    return;
                }
                if (daDialog != null) {
                    daDialog.setCancelable(true);
                    daDialog.show();
                }
                if (filelist != null && filelist.size() > 0) {
                    uploadFile();
                } else {
                    iWantSuggestion();
                }
                break;
            case R.id.tv_more:
                tvMore.setVisibility(View.GONE);
                llBottom.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_city:
                if (isLoaded) {
                    showCityPickerView();
                } else {
                    Toast.makeText(IwantActivity.this, "Please waiting until the data is parsed", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View view) {

    }

    public void yasuo() {
        if (daDialog != null) {
            daDialog.setCancelable(true);
            daDialog.show();
        }
        Flowable.just(pathList)
                .subscribeOn(Schedulers.io())
                .map(new Function<List<String>, List<File>>() {
                    @Override
                    public List<File> apply(@NonNull List<String> list) throws Exception {
                        // 同步方法直接返回压缩后的文件
                        filelist = Luban.with(IwantActivity.this).load(list).get();
                        return filelist;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<File>>() {
            @Override
            public void accept(List<File> files) throws Exception {
                ImageBoxGetDate();
            }
        });

    }

    public void ImageBoxGetDate() {
        List<ZzImageBox.ImageEntity> mDatas = new ArrayList<>();
        for (int i = 0; i < filelist.size(); i++) {
            ZzImageBox.ImageEntity imageEntity = new ZzImageBox.ImageEntity();
            imageEntity.setPicFilePath(filelist.get(i).getAbsolutePath());
            imageEntity.setAdd(true);
            mDatas.add(imageEntity);
        }
        imageBox.setmDatas(mDatas);
        if (daDialog != null && daDialog.isShowing()) {
            daDialog.dismiss();
        }
    }

    ArrayList<String> pathList = new ArrayList<>();
    List<File> filelist = new ArrayList<>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case PhotoUtils.CAMERA_REQUEST_CODE:
                    String contentUri = PhotoUtils.getPictureFile().getAbsolutePath();
                    this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,PhotoUtils.getPictureUrl()));
                    pathList.add(contentUri);
                    yasuo();
                    break;
                case PhotoUtils.ALBUM_REQUEST_CODE:
                    pathList = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT_SELECTION);
                    final boolean original = data.getBooleanExtra(PhotoPickerActivity.EXTRA_RESULT_ORIGINAL, false);
                    yasuo();
                    break;
                case 101:
                    dep_name = data.getStringExtra("dep_name");
                    dep_id = data.getStringExtra("dep_id");
                    spinnerDep.setText(dep_name);
                    break;
            }
        }
    }

    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private boolean isLoaded = false;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread == null) {//如果已创建就不再重新创建子线程了
//                        Toast.makeText(IwantActivity.this, "Begin Parse Data", Toast.LENGTH_SHORT).show();
                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 子线程中解析省市区数据
                                getCityList();
                            }
                        });
                        thread.start();
                    }
                    break;

                case MSG_LOAD_SUCCESS:
//                    Toast.makeText(IwantActivity.this, "Parse Succeed", Toast.LENGTH_SHORT).show();
                    isLoaded = true;
                    break;

                case MSG_LOAD_FAILED:
                    Toast.makeText(IwantActivity.this, "Parse Failed", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    public void getCityList() {
        //Json数据的读写
        try {
//            InputStream is = this.getAssets().open("test.json");//eclipse
            InputStream is = IwantActivity.this.getClass().getClassLoader().getResourceAsStream("assets/" + "CityJson.json");//android studio
            BufferedReader bufr = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = bufr.readLine()) != null) {
                builder.append(line);
            }
            is.close();
            bufr.close();

            //                JSONObject JsonData = new JSONObject(builder.toString());

            ArrayList<JsonBean> jsonBean = parseData(builder.toString());//用Gson 转成实体

            /**
             * 添加省份数据
             *
             * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
             * PickerView会通过getPickerViewText方法获取字符串显示出来。
             */
            options1Items = jsonBean;

            for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
                ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
                ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

                for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                    String CityName = jsonBean.get(i).getCityList().get(c).getName();
                    CityList.add(CityName);//添加城市
                    ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                    //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                    if (jsonBean.get(i).getCityList().get(c).getArea() == null
                            || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                        City_AreaList.add("");
                    } else {
                        City_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                    }
                    Province_AreaList.add(City_AreaList);//添加该省所有地区数据
                }

                /**
                 * 添加城市数据
                 */
                options2Items.add(CityList);

                /**
                 * 添加地区数据
                 */
                options3Items.add(Province_AreaList);
            }

            mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);
//                Log.d("info","cat=" + root.getString("cat"));
//                JSONArray array = root.getJSONArray("languages");
//                for (int i = 0; i < array.length(); i++) {
//                    JSONObject city = array.getJSONObject(i);
//                    Log.d("info","-----------------------");
//                    Log.d("info","id=" + city.getInt("id"));
//                    Log.d("info","ide=" + city.getString("ide"));
//                    Log.d("info","name=" + city.getString("name"));
//                }


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (daDialog != null && daDialog.isShowing()) {
            daDialog.dismiss();
        }
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }

    public String getFileName(String pathandname) {
        int start = pathandname.lastIndexOf("/");
        int end = pathandname.lastIndexOf(".");
        if (start != -1 && end != -1) {
            return pathandname.substring(start + 1, end);
        } else {
            return "newImg" + System.currentTimeMillis();
        }

    }
}

