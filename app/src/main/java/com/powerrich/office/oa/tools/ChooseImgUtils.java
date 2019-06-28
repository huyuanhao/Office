package com.powerrich.office.oa.tools;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.activity.SDCardFileExplorerActivity;
import com.powerrich.office.oa.bean.UserInfo;
import com.powerrich.office.oa.tools.progressutils.listener.impl.UIProgressListener;
import com.powerrich.office.oa.view.CircleProgressBar;
import com.powerrich.office.oa.view.ProgressBarDialog;
import com.yt.simpleframe.utils.BitmapUtils;
import com.yt.simpleframe.utils.StringUtil;

import java.io.File;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/7/2/002
 * 版权：
 */
public class ChooseImgUtils implements View.OnClickListener, DownloadUtils.InvokeData {


    public static final int ITEM_FILE_LIST_REQ = 0;
    public static final int SUBMIT_ITEM_AUDIT_REQ = 1;
    public static final int REQUEST_FILE_CHOOSE_PERMISSION = 10000;
    public static final int REQUEST_TAKE_PHOTO_PERMISSION = 10001;
    public static final int REQUEST_PIC_PHOTO_PERMISSION = 10002;

    //使用照相机拍照获取图片
    public static final int TAKE_PHOTO_CODE = 1;
    //使用相册中的图片
    public static final int SELECT_PIC_CODE = 2;
    //图片裁剪
    private static final int PHOTO_CROP_CODE = 3;
    //定义图片的Uri
    public Uri photoUri;
    //图片文件路径
    private String picPath;
    private Dialog mCameraDialog;

    private Activity mActivity;
    private Fragment mFragment;
    private int type;

    /**
     * @param activity
     * @param type     1 activity 2 fragment
     */
    public ChooseImgUtils(Object activity, int type) {
        this.type = type;
        if (type == 1) {
            this.mActivity = (Activity) activity;
        } else if (type == 2) {
            this.mFragment = (Fragment) activity;
            this.mActivity = this.mFragment.getActivity();
        }

    }

    /**
     * 拍照获取图片
     */
    public void picTyTakePhoto() {
        //判断SD卡是否存在
        String SDState = Environment.getExternalStorageState();
        if (SDState.equals(Environment.MEDIA_MOUNTED)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//"android.media.action.IMAGE_CAPTURE"
            /***
             * 使用照相机拍照，拍照后的图片会存放在相册中。使用这种方式好处就是：获取的图片是拍照后的原图，
             * 如果不实用ContentValues存放照片路径的话，拍照后获取的图片为缩略图有可能不清晰
             */
            ContentValues values = new ContentValues();
            photoUri = mActivity.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityResult(intent, TAKE_PHOTO_CODE);
        } else {
            Toast.makeText(mActivity, "内存卡不存在", Toast.LENGTH_LONG).show();
        }
    }

    public void startActivityResult(Intent intent, int code) {
        if (type == 1 && mActivity != null) {
            mActivity.startActivityForResult(intent, code);
        } else if (type == 2 && mFragment != null) {
            mFragment.startActivityForResult(intent, code);
        }
    }

    /***
     * 从相册中取图片
     */
    public void pickPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//        mActivity.startActivityForResult(intent, SELECT_PIC_CODE);
        startActivityResult(intent, SELECT_PIC_CODE);
    }


    /**
     * @param
     * @description 把Uri转换为文件路径
     */
    public static String uriToFilePath(Uri uri, Activity activity) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = activity.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null,
                    null,
                    null);
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


    public void showSelectPhotoDialog() {
        mCameraDialog = new Dialog(mActivity, R.style.my_dialog);
        LinearLayout root = (LinearLayout) LayoutInflater.from(mActivity).inflate(R.layout.layout_camera_control, null);
        root.findViewById(R.id.btn_file_choose).setOnClickListener(this);
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
        lp.width = mActivity.getResources().getDisplayMetrics().widthPixels; // 宽度
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT; // 高度
//      lp.alpha = 9f; // 透明度
        root.measure(0, 0);
//        lp.height = root.getMeasuredHeight();
        lp.alpha = 9f; // 透明度
        dialogWindow.setAttributes(lp);
        mCameraDialog.setCanceledOnTouchOutside(true);
        mCameraDialog.show();
    }

    private void fileChoose() {
        if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager
                .PERMISSION_GRANTED) {
            //申请权限，REQUEST_TAKE_PHOTO_PERMISSION是自定义的常量
            ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest
                    .permission.CAMERA}, REQUEST_FILE_CHOOSE_PERMISSION);
        } else {
            Intent intent = new Intent(mActivity, SDCardFileExplorerActivity.class);
//            mActivity.startActivityForResult(intent, SDCardFileExplorerActivity.REQUEST_CODE_FILE);
            startActivityResult(intent, SDCardFileExplorerActivity.REQUEST_CODE_FILE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_file_choose:
                fileChoose();
                if (mCameraDialog != null) {
                    mCameraDialog.dismiss();
                }
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


//    private Dialog progressDialog;
    private ProgressBarDialog progressDialog;
    private CircleProgressBar circleProgressBar;
//    private TextView tv_file_name;
//    private ProgressBar progress;
//    private TextView tv_progress;

    /**
     * 弹进度框
     */
    private void showProgressDialog(Context context) {
//        progressDialog = new Dialog(context, R.style.my_dialog);
//        LinearLayout root = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.layout_progress_control, null);
//        tv_file_name = (TextView) root.findViewById(R.id.tv_file_name);
//        progress = (ProgressBar) root.findViewById(R.id.progress);
//        tv_progress = (TextView) root.findViewById(R.id.tv_progress);
//        progressDialog.setContentView(root);
//        Window dialogWindow = progressDialog.getWindow();
//        dialogWindow.setGravity(Gravity.CENTER);
//        dialogWindow.setWindowAnimations(R.style.dialogstyle); // 添加动画
//        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
//        lp.x = 0; // 新位置X坐标
//        lp.y = -20; // 新位置Y坐标
//        lp.width = context.getResources().getDisplayMetrics().widthPixels; // 宽度
//        root.measure(0, 0);
//        lp.height = root.getMeasuredHeight();
//        lp.alpha = 9f; // 透明度
//        dialogWindow.setAttributes(lp);
//        progressDialog.setCancelable(false);
//        progressDialog.show();

        progressDialog = ProgressBarDialog.createDialog(context);
        circleProgressBar = (CircleProgressBar) progressDialog.findViewById(R.id.progressBar);
        circleProgressBar.setCurProgress(0);
        if (progressDialog != null) {
            progressDialog.show();
        }

    }

    /**
     * 这个是ui线程回调，可直接操作UI
     */
    final UIProgressListener uiProgressRequestListener = new UIProgressListener() {
        @Override
        public void onUIProgress(long bytesWrite, long contentLength, boolean done) {
            //ui层回调
            circleProgressBar.setCurProgress((int) ((100 * bytesWrite) / contentLength));
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

    private static UIProgressListener uiListener = new UIProgressListener() {
        @Override
        public void onUIProgress(long currentBytes, long contentLength, boolean done) {

        }
    };


    @Override
    public void success(String result) {
        if (progressDialog != null) {
            progressDialog.cancel();
        }
        DialogUtils.showToast(mActivity, "上传成功");
    }

    @Override
    public void failed() {
        if (progressDialog != null) {
            progressDialog.cancel();
        }
        DialogUtils.showToast(mActivity, "上传失败,请重新操作");
    }

    public interface ExeCallBack {
        void exeCallBack();
    }


    public void upload(final ExeCallBack callBack, String fileName, String path, String userName,String url) {
        showProgressDialog(mActivity);
        DownloadUtils.uploadIdCard(uiProgressRequestListener, new DownloadUtils.InvokeData() {
            @Override
            public void success(String result) {
                if (progressDialog != null) {
                    progressDialog.cancel();
                }
                DialogUtils.showToast(mActivity, "上传成功");
                callBack.exeCallBack();
            }

            @Override
            public void failed() {
                if (progressDialog != null) {
                    progressDialog.cancel();
                }
            }
        }, url, path, fileName, userName, "");
    }


    public void downLoadCard(final ExeCallBack callBack, String fileName, String path, String userName,String url,String flag) {
        showProgressDialog(mActivity);
        DownloadUtils.uploadIdCard(uiProgressRequestListener, new DownloadUtils.InvokeData() {
            @Override
            public void success(String result) {
                if (progressDialog != null) {
                    progressDialog.cancel();
                }
                DialogUtils.showToast(mActivity, "上传成功");
                callBack.exeCallBack();
            }

            @Override
            public void failed() {
                DialogUtils.showToast(mActivity, "上传失败");
                if (progressDialog != null) {
                    progressDialog.cancel();
                }
            }
        }, url, path, fileName, userName, flag);
    }

    //首先查看本地有没有头像这个文件，如果没有那么网络下载，保存到本地，如果有直接显示

    public static void downLoadImg(DownloadUtils.InvokeData context, String url, String fileName) {
        DownloadUtils.downloadFileImage(uiListener, context, url, fileName);
    }


    /**
     * @param context
     * @param iv
     * @param data
     */
    public static void showImage(Context context, final ImageView iv, UserInfo.DATABean data) {


        String filePath = data.getHEADPORTRAIT_DOWNPATH();
        final String hdfsFileName = data.getHEADPORTRAIT_FILENAME();

        if (StringUtil.isEmpty(hdfsFileName) && iv != null) {
            iv.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.pic_mine_head));
            return;
        }
        //先查看本地地址是否有这个头像
        String localDir = new File(Environment.getExternalStorageDirectory() + "/download").getPath();
        final String localPath = localDir + "/" + hdfsFileName;

        final Handler h = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (iv != null)
                    iv.setImageBitmap(BitmapUtils.sampleBitmap(localPath));
            }
        };

        //查看是否有这个文件
        if (fileIsExists(localPath) && iv != null) {
            //将文件转换为bitmap
            iv.setImageBitmap(BitmapUtils.sampleBitmap(localPath));

        } else {
            //开始下载图片
            String url = "http://218.87.176.156:80/platform/DownFileServlet?" + "type=1" + "&DOWNPATH=" + filePath +
                    "&HDFSFILENAME=" + hdfsFileName + "&FILENAME=" + "head.jpg";
            downLoadImg(new DownloadUtils.InvokeData() {

                @Override
                public void success(String result) {
                    h.sendEmptyMessage(1);
                }

                @Override
                public void failed() {
                }
            }, url, hdfsFileName);
            if (iv != null)
                iv.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.pic_mine_head));
        }


    }

    //判断文件是否存在
    public static boolean fileIsExists(String strFile) {
        try {
            File f = new File(strFile);
            if (!f.exists()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
