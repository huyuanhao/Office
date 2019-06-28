package com.powerrich.office.oa.activity.mine;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.powerrich.office.oa.R;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.bean.UserInfo;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.AESUtil;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DialogUtils;
import com.powerrich.office.oa.tools.GsonUtil;
import com.powerrich.office.oa.tools.LoginUtils;
import com.powerrich.office.oa.tools.ToastUtils;
import com.powerrich.office.oa.view.GlideRoundImage;
import com.powerrich.office.oa.view.NoEmojiEditText;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.io.IOException;

import io.reactivex.functions.Consumer;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * 认证
 */
public class Identify5Activity extends BaseActivity implements View.OnClickListener {

    private NoEmojiEditText et_name;
    private NoEmojiEditText et_id_card;
    private ImageView iv_card1;
    private ImageView iv_card2;
    private ImageView iv_card3;
    private TextView tv_commit;
    private Dialog mCameraDialog;
    //使用相册中的图片
    public static final int TAKE_PHOTO = 101;
    public static final int CROP_PHOTO = 102;
    public static final int SELECT_PIC_CODE = 103;
    public static final String CONTENT_TYPE_GENERAL = "general";
    public static final String CONTENT_TYPE_ID_CARD_FRONT = "IDCardFront";
    public static final String CONTENT_TYPE_ID_CARD_BACK = "IDCardBack";
    public static final int UPDATEIDENTIFICATION = 000;
    public static final int UPLOADIDCARDIMG = 111;
    //没有登录的时候上传图片  获取用户名
    public static final int UPLOADIDCARD_NOLOGIN = 222;
    //根据用户名同步省统一身份认证系统用户数据
    public static final int SYNCIDCARDUSERINFO = 333;
    //校验用户是否存在
    private static int GETUSEREXIST = 555;
    private String cameraType;
    private String mFilePath;
    private boolean ID_CARD_BACK = false;
    private boolean ID_CARD_FRONT = false;
    private boolean ID_CARD_GENERAL = false;
    //从注册界面传入的
    private String userName;
    private String password;

    private SharedPreferences sp;
    //全局保存姓名和身份证号码
    private String realName;
    private String idCard;
    private Uri imageUri;
    private Uri cropImgUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        //屏蔽7.0中使用 Uri.fromFile爆出的FileUriExposureException
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (Build.VERSION.SDK_INT >= 24) {
            builder.detectFileUriExposure();
        }
        initView();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_identify5;
    }

    private void initView() {
        userName = getIntent().getStringExtra("userName");
        password = getIntent().getStringExtra("password");
        initTitleBar("实名认证", this, null);
        et_name = findViewById(R.id.et_name);
        et_id_card = findViewById(R.id.et_id_card);
        iv_card1 = findViewById(R.id.iv_card1);
        iv_card2 = findViewById(R.id.iv_card2);
        iv_card3 = findViewById(R.id.iv_card3);
        tv_commit = findViewById(R.id.tv_commit);
        initData();
    }

    private void initData() {
        iv_card1.setOnClickListener(this);
        iv_card2.setOnClickListener(this);
        iv_card3.setOnClickListener(this);
        tv_commit.setOnClickListener(this);
    }

    private void showSelectPhotoDialog() {
        if (mCameraDialog == null) {
            mCameraDialog = new Dialog(this, R.style.ActionSheetDialogStyle);
            LinearLayout root = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.layout_camera_control1, null);
            root.findViewById(R.id.btn_open_camera).setOnClickListener(this);
            root.findViewById(R.id.btn_choose_img).setOnClickListener(this);
            root.findViewById(R.id.btn_cancel).setOnClickListener(this);
            mCameraDialog.setContentView(root);
            Window dialogWindow = mCameraDialog.getWindow();
            dialogWindow.setGravity(Gravity.BOTTOM);
            dialogWindow.setWindowAnimations(R.style.dialogstyle); // 添加动画
            WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
            lp.x = 0; // 新位置X坐标
            lp.y = -20; // 新位置Y坐标
            lp.width = getResources().getDisplayMetrics().widthPixels; // 宽度
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT; // 高度
//      lp.alpha = 9f; // 透明度
            root.measure(0, 0);
//        lp.height = root.getMeasuredHeight();
            lp.alpha = 9f; // 透明度
            dialogWindow.setAttributes(lp);
            mCameraDialog.setCanceledOnTouchOutside(true);
            mCameraDialog.show();
        } else {
            if (!mCameraDialog.isShowing()) {
                mCameraDialog.show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == TAKE_PHOTO) {//处理拍照返回结果
                startPhotoCrop();
            } else if (requestCode == CROP_PHOTO) {//处理裁剪返回结果
                mFilePath = uriToFilePath(cropImgUri);
                uploadImg(mFilePath);
            } else if (requestCode == SELECT_PIC_CODE) {
                if (data != null) {
                    mFilePath = uriToFilePath(data.getData());
                    String saveDir = Environment.getExternalStorageDirectory() + "/download";
                    Luban.with(this)
                            .load(mFilePath)
                            .ignoreBy(100)
                            .setTargetDir(saveDir)
                            .setCompressListener(new OnCompressListener() {


                                @Override
                                public void onStart() {

                                }

                                @Override
                                public void onSuccess(File file) {
                                    uploadImg(file.getAbsolutePath());
                                }

                                @Override
                                public void onError(Throwable e) {
                                    DialogUtils.showToast(Identify5Activity.this, "压缩失败：" + e.getMessage());
                                }
                            }).launch();
                }
            }
        }
    }

    private void uploadImg(String filePath) {
        //是否登录
        if (LoginUtils.getInstance().isLoginSuccess()) {
            uploadIdcardImg(filePath, LoginUtils.getInstance().getUserInfo().getDATA().getUSERID());
        } else {
            //从注册界面进入  先根据真实姓名获取用户信息得到UserId
            if (!TextUtils.isEmpty(userName)) {
                invoke.invokeWidthDialog(OAInterface.syncIdcardUserInfo("0",userName, password), callBack, UPLOADIDCARD_NOLOGIN);
            } else {
                ToastUtils.showMessage(Identify5Activity.this, "当前用户名为空！");
            }
        }
    }

    private void uploadIdcardImg(String filePath, String userId) {
        if (CONTENT_TYPE_ID_CARD_BACK.equals(cameraType)) {
            invoke.invokeWidthDialog(OAInterface.uploadIdcardImg(filePath, userId, "1"), callBack, UPLOADIDCARDIMG);
        } else if (CONTENT_TYPE_ID_CARD_FRONT.equals(cameraType)) {
            invoke.invokeWidthDialog(OAInterface.uploadIdcardImg(filePath, userId, "2"), callBack, UPLOADIDCARDIMG);
        } else if (CONTENT_TYPE_GENERAL.equals(cameraType)) {
            invoke.invokeWidthDialog(OAInterface.uploadIdcardImg(filePath, userId, "3"), callBack, UPLOADIDCARDIMG);
        }
    }

    private IRequestCallBack callBack = new BaseRequestCallBack() {

        @Override
        public void process(HttpResponse response, int what) {
            if (!BeanUtils.isEmpty(response)) {
                ResultItem item = response.getResultItem(ResultItem.class);
                String code = item.getString("code");
                if (what == UPLOADIDCARDIMG) {
                    String message = item.getString("msg");
                    if (Constants.CODE.equals(code)) {
                        DialogUtils.showToast(Identify5Activity.this, "上传成功");
                        if (CONTENT_TYPE_ID_CARD_BACK.equals(cameraType)) {
                            Glide.with(Identify5Activity.this).load(mFilePath).
                                    transform(new GlideRoundImage(Identify5Activity.this))
                                    .skipMemoryCache(true)
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .crossFade().override(492, 301).into(iv_card1);
                            ID_CARD_FRONT = true;
                        } else if (CONTENT_TYPE_ID_CARD_FRONT.equals(cameraType)) {
                            Glide.with(Identify5Activity.this).load(mFilePath).
                                    transform(new GlideRoundImage(Identify5Activity.this))
                                    .skipMemoryCache(true)
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .crossFade().override(492, 301).into(iv_card2);
                            ID_CARD_BACK = true;
                        } else if (CONTENT_TYPE_GENERAL.equals(cameraType)) {
                            Glide.with(Identify5Activity.this).load(mFilePath).
                                    transform(new GlideRoundImage(Identify5Activity.this))
                                    .skipMemoryCache(true)
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .crossFade().override(618, 634).into(iv_card3);
                            ID_CARD_GENERAL = true;
                        }
                    } else {
                        DialogUtils.showToast(Identify5Activity.this, message);
                    }
                } else if (what == UPDATEIDENTIFICATION) {//身份证认证
                    String message = item.getString("msg");
                    if (Constants.CODE.equals(code)) {
                        boolean success = item.getBoolean("success", false);
                        if (success) {
                            if ("认证成功".equals(message)) {
                                DialogUtils.showToast(Identify5Activity.this, message);
                                //是否登录,未登录说明从注册界面进来，则不用同步用户数据
                                if (LoginUtils.getInstance().isLoginSuccess()) {
                                    String userPsw = sp.getString("userPsw", "");
                                    try {
                                        invoke.invokeWidthDialog(OAInterface.syncIdcardUserInfo("0",
                                                LoginUtils.getInstance().getUserInfo().getDATA().getUSERNAME(),
                                                AESUtil.decrypt("98563258", userPsw)), callBack, SYNCIDCARDUSERINFO);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    Identify5Activity.this.finish();
                                }
                            } else {
                                DialogUtils.showToast(Identify5Activity.this, message);
                            }
                        }
                    } else {
                        DialogUtils.showToast(Identify5Activity.this, message);
                    }
                } else if (what == UPLOADIDCARD_NOLOGIN) {
                    String message = item.getString("message");
                    if (Constants.SUCCESS_CODE.equals(code)) {
                        ResultItem data = (ResultItem) item.get("DATA");
                        String userId = data.getString("USERID", "");
                        uploadIdcardImg(mFilePath, userId);
                    } else {
                        DialogUtils.showToast(Identify5Activity.this, message);
                    }
                } else if (what == SYNCIDCARDUSERINFO) {//根据用户名同步省统一身份认证系统用户数据
                    String message = item.getString("message");
                    if (Constants.SUCCESS_CODE.equals(code)) {
                        String jsonStr = item.getJsonStr();
                        Gson gson = new Gson();
                        UserInfo userInfo = gson.fromJson(jsonStr, UserInfo.class);
                        LoginUtils.getInstance().getUserInfo().setDATA(userInfo.getDATA());
                        SharedPreferences.Editor edit = sp.edit();
                        edit.putString("userInfoBean", GsonUtil.GsonString(LoginUtils.getInstance().getUserInfo()));
                        edit.commit();
                        Identify5Activity.this.finish();
                    } else {
                        DialogUtils.showToast(Identify5Activity.this, message);
                    }
                } else if (what == GETUSEREXIST){//获取用户是否存在
                    Boolean data = item.getBoolean("data", false);
                    if (data) {//用户存在
                        DialogUtils.showToast(Identify5Activity.this, "当前身份证号码已被使用");
                    } else {//用户不存在
                        //身份认证
                        invoke.invokeWidthDialog(OAInterface.updateIdentification(BeanUtils.isEmptyStr(userName) ?
                                LoginUtils.getInstance().getUserInfo().getDATA().getUSERNAME() : userName, idCard.toUpperCase(), realName), callBack, UPDATEIDENTIFICATION);
                    }
                }

            }
        }
    };

    /**
     * @param uri
     * @return
     * @description 把Uri转换为文件路径
     */
    private String uriToFilePath(Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = this.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    private void doPermissionType(final int type) {
        dismiss();
        doPermissionCamera(new BaseActivity.PermissionCallBack() {
            @Override
            public void accept() {
                if (type == 1) {
                    openCamera();
                } else {
                    // 打开相册
                    pickPhoto();
                }
            }
        });
    }

    public void doPermissionCamera(BaseActivity.PermissionCallBack callBack) {
        doPermission("读写、相机", callBack, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.CAMERA);
    }

    public void doPermission(final String msg, final BaseActivity.PermissionCallBack callBack, String... permissions) {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(permissions)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            callBack.accept();
                        } else {
                            showTipDialog("没有 " + msg + " 权限，请打开相关权限");
                        }
                    }
                });
    }

    private void openCamera() {
//        File outputImage = new File(Environment.getExternalStorageDirectory(), "output_image.jpg");
        File outputImage = new File(getExternalCacheDir(), "output_image.jpg");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
                outputImage.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageUri = Uri.fromFile(outputImage);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PHOTO);
    }

    /**
     * 开启裁剪相片
     */
    public void startPhotoCrop() {
        //创建file文件，用于存储剪裁后的照片
        File cropImage = new File(getExternalCacheDir(), "crop_image.jpg");
        try {
            if (cropImage.exists()) {
                cropImage.delete();
            }
            cropImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        cropImgUri = Uri.fromFile(cropImage);
        Intent intent = new Intent("com.android.camera.action.CROP");
        //设置源地址uri
        intent.setDataAndType(imageUri, "image/*");
        intent.putExtra("crop", "true");

        if (CONTENT_TYPE_ID_CARD_BACK.equals(cameraType) || CONTENT_TYPE_ID_CARD_FRONT.equals(cameraType)) {
            //证件尺寸设计为85.6mm×54.0mm×1.0mm
            intent.putExtra("aspectX", 86);
            intent.putExtra("aspectY", 54);
            intent.putExtra("outputX", 360);
            intent.putExtra("outputY", 226);
        } else if (CONTENT_TYPE_GENERAL.equals(cameraType)) {
            intent.putExtra("aspectX", 25);
            intent.putExtra("aspectY", 36);
            intent.putExtra("outputX", 250);
            intent.putExtra("outputY", 360);
        }
        intent.putExtra("scale", true);
        //设置目的地址uri
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cropImgUri);
        //设置图片格式
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("return-data", false);//data不需要返回,避免图片太大异常
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, CROP_PHOTO);
    }

    /***
     * 从相册中取图片
     */
    private void pickPhoto() {
        doPermissionRW("存储", new PermissionCallBack() {
            @Override
            public void accept() {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, SELECT_PIC_CODE);
            }
        });
    }

    /**
     * 关闭对话框
     */
    private void dismiss() {
        if (mCameraDialog != null) {
            mCameraDialog.dismiss();
        }
    }

    private void submitIdentificationInfo() {
        realName = et_name.getText().toString().trim();
        idCard = et_id_card.getText().toString().trim();
        if (BeanUtils.isEmptyStr(realName)) {
            DialogUtils.showToast(this, "姓名不能为空");
            return;
        } else if (BeanUtils.isEmptyStr(idCard)) {
            DialogUtils.showToast(this, "身份证号码不能为空");
            return;
        } else if (!BeanUtils.validCard(idCard)) {
            DialogUtils.showToast(this, "身份证号码格式错误");
            return;
        } else if (!ID_CARD_FRONT) {
            DialogUtils.showToast(this, "请上传身份证正面照片");
            return;
        } else if (!ID_CARD_BACK) {
            DialogUtils.showToast(this, "请上传身份证反面照片");
            return;
        } else if (!ID_CARD_GENERAL) {
            DialogUtils.showToast(this, "请上传手持身份证照片");
            return;
        }
        //获取用户是否存在
        invoke.invokeWidthDialog(OAInterface.getUserExist(idCard.toUpperCase(), "0"), callBack, GETUSEREXIST);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.system_back:
                finish();
                break;
            case R.id.iv_card1:

                if (!TextUtils.isEmpty(userName)) {
                    if (BeanUtils.isEmptyStr(et_name.getText().toString().trim())) {
                        DialogUtils.showToast(this, "姓名不能为空");
                        return;
                    } else {
                        cameraType = CONTENT_TYPE_ID_CARD_BACK;
                        showSelectPhotoDialog();
                    }
                } else {
                    cameraType = CONTENT_TYPE_ID_CARD_BACK;
                    showSelectPhotoDialog();
                }


                break;
            case R.id.iv_card2:

                if (!TextUtils.isEmpty(userName)) {
                    if (BeanUtils.isEmptyStr(et_name.getText().toString().trim())) {
                        DialogUtils.showToast(this, "姓名不能为空");
                        return;
                    } else {
                        cameraType = CONTENT_TYPE_ID_CARD_FRONT;
                        showSelectPhotoDialog();
                    }
                } else {
                    cameraType = CONTENT_TYPE_ID_CARD_FRONT;
                    showSelectPhotoDialog();
                }


                break;
            case R.id.iv_card3:
                if (!TextUtils.isEmpty(userName)) {
                    if (BeanUtils.isEmptyStr(et_name.getText().toString().trim())) {
                        DialogUtils.showToast(this, "姓名不能为空");
                        return;
                    } else {
                        cameraType = CONTENT_TYPE_GENERAL;
                        showSelectPhotoDialog();
                    }
                } else {
                    cameraType = CONTENT_TYPE_GENERAL;
                    showSelectPhotoDialog();
                }

                break;
            case R.id.tv_commit:
                submitIdentificationInfo();
                break;
            case R.id.btn_open_camera:
                doPermissionType(1);
                break;
            case R.id.btn_choose_img:

                doPermissionType(2);


                break;
            case R.id.btn_cancel:
                dismiss();
                break;
        }
    }

}
