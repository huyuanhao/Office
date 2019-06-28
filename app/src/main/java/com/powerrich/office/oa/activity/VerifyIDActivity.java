package com.powerrich.office.oa.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.api.ApiRequest;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.bean.UserInfo;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DialogUtils;
import com.powerrich.office.oa.tools.DownloadUtils;
import com.powerrich.office.oa.tools.LoginUtils;
import com.powerrich.office.oa.tools.progressutils.listener.impl.UIProgressListener;

import java.io.File;

/**
 * 文件名：VerifyIDActivity
 * 描述：人工实名认证
 * 时间：2018/1/18 0018
 *
 * @author 白煜
 *         <p>
 *         modify by 明朋
 *         时间：2018/1/19
 */

public class VerifyIDActivity extends BaseActivity implements View.OnClickListener, DownloadUtils.InvokeData {

    private static final String ID_CARD_FRONT = "1";
    private static final String ID_CARD_BACK = "2";
    private boolean idCard1UploadSuccess;
    private boolean idCard2UploadSuccess;
    private EditText nameEt;
    private ImageView idCardBack;
    private ImageView idCardFront;
    private EditText sexEt;
    private EditText idCardEt;
    private EditText phoneEt;
    private EditText addressEt;
    private Dialog mCameraDialog;
    private Uri photoUri;
    private static final int REQUEST_TAKE_PHOTO_PERMISSION = 10001;
    private static final int REQUEST_PIC_PHOTO_PERMISSION = 10002;
    /**
     * 使用照相机拍照获取图片
     */
    public static final int TAKE_PHOTO_CODE = 1;
    /**
     * 使用相册中的图片
     */
    public static final int SELECT_PIC_CODE = 2;
    private String filePath;
    private String fileName;
    private Dialog progressDialog;
    private TextView tv_file_name;
    private ProgressBar progress;
    private TextView tv_progress;
    private String idCardFlag;
    private boolean isFromRegister;
    private String userName;
    private String userduty;
    private String address;
    private String phoneNum;
    private String idCardNum;
    private String sex;
    private String realName;
    private String nation;
    private EditText nationEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getUserInfo();
        initView();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_verify_id;
    }

    /**
     * 获取用户相关信息
     */
    private void getUserInfo() {
        Intent intent = getIntent();
        isFromRegister = intent.getBooleanExtra("isFromRegister", false);
        UserInfo.DATABean userInfo = (UserInfo.DATABean) intent.getSerializableExtra("userInfo");
        LoginUtils loginUtils = LoginUtils.getInstance();
        boolean loginSuccess = loginUtils.isLoginSuccess();

        userName = loginSuccess ? loginUtils.getUserInfo().getDATA().getUSERNAME() : userInfo.getUSERNAME();
        userduty = loginSuccess ? loginUtils.getUserInfo().getDATA().getUSERDUTY() : userInfo.getUSERDUTY();
    }

    private void initView() {
        initTitleBar(R.string.verify_id, this, null);
        nameEt = ((EditText) findViewById(R.id.et_name));
        sexEt = ((EditText) findViewById(R.id.et_sex));
        idCardEt = ((EditText) findViewById(R.id.et_id_card));
        phoneEt = ((EditText) findViewById(R.id.et_phone));
        addressEt = ((EditText) findViewById(R.id.et_address));
        nationEt = ((EditText) findViewById(R.id.et_nation));
        idCardFront = ((ImageView) findViewById(R.id.id_card_front));
        idCardBack = ((ImageView) findViewById(R.id.id_card_back));
        idCardFront.setOnClickListener(this);
        idCardBack.setOnClickListener(this);
        findViewById(R.id.bt_submit).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                finish();
                break;
            case R.id.bt_submit:
                //提交信息
                if (canSubmit()) {
                    saveUserInfo();
                }
                break;
            case R.id.id_card_front:
                //选择或拍照
                showSelectPhotoDialog();
                idCardFlag = ID_CARD_FRONT;
                break;
            case R.id.id_card_back:
                //选择或拍照
                showSelectPhotoDialog();
                idCardFlag = ID_CARD_BACK;
                break;
            case R.id.btn_open_camera:
                // 打开照相机
                picTyTakePhoto();
                if (mCameraDialog != null) {
                    mCameraDialog.dismiss();
                }
                break;
            case R.id.btn_choose_img:
                // 打开相册
                pickPhoto();
                if (mCameraDialog != null) {
                    mCameraDialog.dismiss();
                }
                break;
            case R.id.btn_cancel:
                // 取消
                if (mCameraDialog != null) {
                    mCameraDialog.dismiss();
                }
                break;
            default:
                break;
        }
    }

    private boolean canSubmit() {
        realName = nameEt.getEditableText().toString().trim();
        if (BeanUtils.isEmptyStr(realName)) {
            Toast.makeText(this, "真实姓名输入无效！", Toast.LENGTH_SHORT).show();
            return false;
        }
        sex = sexEt.getEditableText().toString().trim();
        if (BeanUtils.isEmptyStr(sex)) {
            Toast.makeText(this, "性别输入无效！", Toast.LENGTH_SHORT).show();
            return false;
        }
        idCardNum = idCardEt.getEditableText().toString().trim();
        if (!BeanUtils.validCard(idCardNum)) {
            Toast.makeText(this, "身份证号码输入无效！", Toast.LENGTH_SHORT).show();
            return false;
        }
        phoneNum = phoneEt.getEditableText().toString().trim();
        if (BeanUtils.isEmptyStr(phoneNum) || !BeanUtils.isMobileNO(phoneNum)) {
            Toast.makeText(this, "手机号码输入无效！", Toast.LENGTH_SHORT).show();
            return false;
        }
        nation = nationEt.getEditableText().toString().trim();
        if (BeanUtils.isEmptyStr(nation)) {
            Toast.makeText(this, "民族输入无效！", Toast.LENGTH_SHORT).show();
            return false;
        }
        address = addressEt.getEditableText().toString().trim();
        if (BeanUtils.isEmptyStr(address)) {
            Toast.makeText(this, "地址输入无效！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!idCard1UploadSuccess) {
            Toast.makeText(this, "请上传身份证正面！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!idCard2UploadSuccess) {
            Toast.makeText(this, "请上传身份证背面！", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * 保存用户实名认证信息
     */
    private void saveUserInfo() {
        ApiRequest request = OAInterface.saveUserInfoNormal(userName, userduty, phoneNum, realName, idCardNum, sex, nation, address);
        invoke.invokeWidthDialog(request, callBack);
    }

    private IRequestCallBack callBack = new BaseRequestCallBack() {

        @Override
        public void process(HttpResponse response, int what) {
            ResultItem item = response.getResultItem(ResultItem.class);
            String message = item.getString("message");
            String code = item.getString("code");
            DialogUtils.showToast(VerifyIDActivity.this, message);
            if (Constants.SUCCESS_CODE.equals(code)) {
                if (isFromRegister) {
                    startActivity(new Intent(context, LoginActivity.class));
                } else {
                    LoginUtils.getInstance().getUserInfo().getDATA().setAUDIT_STATE("0");
                    LoginUtils.getInstance().getUserInfo().getDATA().setREALNAME(realName);
                }
                VerifyIDActivity.this.finish();
            }
        }
    };

    private void showSelectPhotoDialog() {
        mCameraDialog = new Dialog(this, R.style.my_dialog);
        LinearLayout root = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.layout_camera_control, null);
        root.findViewById(R.id.btn_file_choose).setVisibility(View.GONE);
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
        root.measure(0, 0);
        lp.alpha = 9f; // 透明度
        dialogWindow.setAttributes(lp);
        mCameraDialog.setCanceledOnTouchOutside(true);
        mCameraDialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_TAKE_PHOTO_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //申请成功，可以拍照
                picTyTakePhoto();
            } else {
                Toast.makeText(this, "PERMISSION DENIED", Toast.LENGTH_SHORT).show();
            }
            return;
        } else if (requestCode == REQUEST_PIC_PHOTO_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //申请成功，可以从相册取文件
                pickPhoto();
            } else {
                Toast.makeText(this, "PERMISSION DENIED", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 拍照获取图片
     */
    private void picTyTakePhoto() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            //申请权限，REQUEST_TAKE_PHOTO_PERMISSION是自定义的常量
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, REQUEST_TAKE_PHOTO_PERMISSION);
        } else {
            //判断SD卡是否存在
            String SDState = Environment.getExternalStorageState();
            if (SDState.equals(Environment.MEDIA_MOUNTED)) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//"android.media.action.IMAGE_CAPTURE"
                //使用照相机拍照，拍照后的图片会存放在相册中。使用这种方式好处就是：获取的图片是拍照后的原图
                //如果不使用ContentValues存放照片路径的话，拍照后获取的图片为缩略图有可能不清晰
                ContentValues values = new ContentValues();
                photoUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(intent, TAKE_PHOTO_CODE);
            } else {
                Toast.makeText(this, "内存卡不存在", Toast.LENGTH_LONG).show();
            }
        }

    }

    /***
     * 从相册中取图片
     */
    private void pickPhoto() {
        //6.0以上动态获取权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //申请权限，REQUEST_TAKE_PHOTO_PERMISSION是自定义的常量
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, REQUEST_PIC_PHOTO_PERMISSION);
        } else {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            startActivityForResult(intent, SELECT_PIC_CODE);
        }
    }

    /**
     * @param uri 把Uri转换为文件路径
     * @return
     */
    private String uriToFilePath(Uri uri) {
        if (null == uri) {
            return null;
        }
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == TAKE_PHOTO_CODE) {
                String[] pojo = {MediaStore.Images.Media.DATA};
                Cursor cursor = managedQuery(photoUri, pojo, null, null, null);
                if (cursor != null) {
                    int columnIndex = cursor.getColumnIndexOrThrow(pojo[0]);
                    cursor.moveToFirst();
                    filePath = cursor.getString(columnIndex);
                    File file = new File(filePath);
                    fileName = file.getName();
                    if (Build.VERSION.SDK_INT < 14) {
                        cursor.close();
                    }
                }
                if (filePath != null) {
                    photoUri = Uri.fromFile(new File(filePath));
                    Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                    setIdCardImageResource(bitmap);
                } else {
                    DialogUtils.showToast(this, "图片选择失败");
                }
            } else if (requestCode == SELECT_PIC_CODE) {
                if (null != data && null != data.getData()) {
                    photoUri = data.getData();
                    filePath = uriToFilePath(photoUri);
                    File file = new File(filePath);
                    fileName = file.getName();
                    Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                    setIdCardImageResource(bitmap);
                } else {
                    DialogUtils.showToast(this, "图片选择失败");
                }
            }
            // 更改附件选择，然后是选择一个附件就上传
            File file = new File(filePath);
            if (file != null && file.exists()) {
                // 文件上传
                if (!BeanUtils.isNullOrEmpty(userName)) {
                    DownloadUtils.uploadIdCard(uiProgressRequestListener, this, Constants.UPLOAD_ID_CARD_URL, filePath, fileName, userName, idCardFlag);
                }
                showProgressDialog();
            } else {
                DialogUtils.showToast(this, "该文件不存在");
            }

        }

    }

    /**
     * 设置身份证正反面照片
     *
     * @param bitmap
     */
    private void setIdCardImageResource(Bitmap bitmap) {
        if (bitmap != null) {
            if (idCardFlag.equals(ID_CARD_FRONT)) {
                idCardFront.setImageBitmap(bitmap);
            } else {
                idCardBack.setImageBitmap(bitmap);
            }
        }
    }

    /**
     * 弹进度框
     */
    private void showProgressDialog() {
        progressDialog = new Dialog(this, R.style.my_dialog);
        LinearLayout root = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.layout_progress_control, null);
        tv_file_name = (TextView) root.findViewById(R.id.tv_file_name);
        progress = (ProgressBar) root.findViewById(R.id.progress);
        tv_progress = (TextView) root.findViewById(R.id.tv_progress);
        progressDialog.setContentView(root);
        Window dialogWindow = progressDialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setWindowAnimations(R.style.dialogstyle); // 添加动画
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.x = 0; // 新位置X坐标
        lp.y = -20; // 新位置Y坐标
        lp.width = getResources().getDisplayMetrics().widthPixels; // 宽度
        root.measure(0, 0);
        lp.height = root.getMeasuredHeight();
        lp.alpha = 9f; // 透明度
        dialogWindow.setAttributes(lp);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    /**
     * 这个是ui线程回调，可直接操作UI
     */
    final UIProgressListener uiProgressRequestListener = new UIProgressListener() {
        @Override
        public void onUIProgress(long bytesWrite, long contentLength, boolean done) {
            //ui层回调
            tv_file_name.setText(fileName + " 正在上传...");
            progress.setProgress((int) ((100 * bytesWrite) / contentLength));
            tv_progress.setText((int) ((100 * bytesWrite) / contentLength) + "%");
        }

        @Override
        public void onUIStart(long bytesWrite, long contentLength, boolean done) {
            super.onUIStart(bytesWrite, contentLength, done);
        }

        @Override
        public void onUIFinish(long bytesWrite, long contentLength, boolean done) {
            super.onUIFinish(bytesWrite, contentLength, done);
        }
    };


    @Override
    public void success(String result) {
        if (progressDialog != null) {
            progressDialog.cancel();
        }
        if (idCardFlag.equals(ID_CARD_FRONT)) {
            idCard1UploadSuccess = true;
        }
        if (idCardFlag.equals(ID_CARD_BACK)) {
            idCard2UploadSuccess = true;
        }
        DialogUtils.showToast(this, "上传成功");
    }

    @Override
    public void failed() {
        if (progressDialog != null) {
            progressDialog.cancel();
        }
        DialogUtils.showToast(this, "上传失败,请重新操作");
    }
}
