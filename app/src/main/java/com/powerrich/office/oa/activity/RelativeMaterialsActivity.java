package com.powerrich.office.oa.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
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
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.adapter.MyExpandableListViewAdapter;
import com.powerrich.office.oa.api.ApiRequest;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.bean.FileInfo;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DialogUtils;
import com.powerrich.office.oa.tools.DownloadUtils;
import com.powerrich.office.oa.tools.DownloadUtils.InvokeData;
import com.powerrich.office.oa.tools.FileSizeUtil;
import com.powerrich.office.oa.tools.progressutils.listener.impl.UIProgressListener;
import com.powerrich.office.oa.view.MyDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.powerrich.office.oa.activity.SDCardFileExplorerActivity.REQUEST_CODE_FILE;

/**
 * 在线申报 相关材料界面
 *
 * @author Administrator
 */
public class RelativeMaterialsActivity extends BaseActivity implements OnClickListener, InvokeData, ExpandableListView.OnChildClickListener {

    private static final int ITEM_FILE_LIST_REQ = 0;
    private static final int SUBMIT_ITEM_AUDIT_REQ = 1;
    private static final int REQUEST_FILE_CHOOSE_PERMISSION = 10000;
    private static final int REQUEST_TAKE_PHOTO_PERMISSION = 10001;
    private static final int REQUEST_PIC_PHOTO_PERMISSION = 10002;

    //使用照相机拍照获取图片
    public static final int TAKE_PHOTO_CODE = 1;
    //使用相册中的图片
    public static final int SELECT_PIC_CODE = 2;
    private static final int GET_UPLOAD_FILE_REQ = 2;
    private static final int DELETE_FILE_REQ = 3;

    private ExpandableListView expand_lv;
    private Button btn_next;
    private String proKeyId, fileName, filePath;
    private Dialog progressDialog;
    private ProgressBar progress;
    private TextView tv_progress, tv_file_name;
    private String itemId;
    private String itemName;
    private String position;
    private List<FileInfo> fileInfoList = new ArrayList<>();
    private List<List<FileInfo>> childList = new ArrayList<>();

    private MyExpandableListViewAdapter expandAdapter;
    private int mPosition = 0;
    private Dialog mCameraDialog;
    private Uri photoUri;

    private int groupPos;
    private int childPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitleBar(R.string.title_activity_relative_materials, this, null);
        itemId = getIntent().getStringExtra("itemId");
        itemName = getIntent().getStringExtra("itemName");
        proKeyId = getIntent().getStringExtra("proKeyId");
        position = getIntent().getStringExtra("position");
        initView();
        if (!"0".equals(position)) {
            getUploadFile();
        } else {
            getItemFileList();
        }
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_relative_materials;
    }

    private void initView() {
        btn_next = (Button) findViewById(R.id.btn_next);
        btn_next.setOnClickListener(this);
        expand_lv = (ExpandableListView) findViewById(R.id.expand_lv);
        expand_lv.setOnChildClickListener(this);
    }


    /**
     * 通过事项id获取该事项所需上传的材料请求
     */
    private void getItemFileList() {
        ApiRequest request = OAInterface.getItemFileList(itemId);
        invoke.invokeWidthDialog(request, callBack, ITEM_FILE_LIST_REQ);
    }

    /**
     * 暂存件-获取申请材料和已上传申请材料信息请求
     */
    private void getUploadFile() {
        ApiRequest request = OAInterface.getUploadedFile(proKeyId, itemId);
        invoke.invokeWidthDialog(request, callBack, GET_UPLOAD_FILE_REQ);
    }

    /**
     * 删除已上传的附件请求
     *
     * @param fileId
     */
    private void delete(String fileId) {
        ApiRequest request = OAInterface.deleteFileById("1", fileId);
        invoke.invokeWidthDialog(request, callBack, DELETE_FILE_REQ);
    }

    /**
     * 网上办事-事项申报提交审核请求
     */
    private void submitItemAudit() {
        ApiRequest request = OAInterface.submitItemAudit(itemId, proKeyId);
        invoke.invokeWidthDialog(request, callBack, SUBMIT_ITEM_AUDIT_REQ);
    }

    private void showSelectPhotoDialog() {
        mCameraDialog = new Dialog(this, R.style.my_dialog);
        LinearLayout root = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.layout_camera_control, null);
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
        lp.width = getResources().getDisplayMetrics().widthPixels; // 宽度
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT; // 高度
//      lp.alpha = 9f; // 透明度
        root.measure(0, 0);
//        lp.height = root.getMeasuredHeight();
        lp.alpha = 9f; // 透明度
        dialogWindow.setAttributes(lp);
        mCameraDialog.setCanceledOnTouchOutside(true);
        mCameraDialog.show();
    }

    //弹进度框
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
//        lp.height = WindowManager.LayoutParams.WRAP_CONTENT; // 高度
//      lp.alpha = 9f; // 透明度  
        root.measure(0, 0);
        lp.height = root.getMeasuredHeight();
        lp.alpha = 9f; // 透明度  
        dialogWindow.setAttributes(lp);
//        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    //这个是ui线程回调，可直接操作UI
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_FILE_CHOOSE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //从文件浏览器选择文件
                fileChoose();
            } else {
                Toast.makeText(this, "PERMISSION DENIED", Toast.LENGTH_SHORT).show();
            }
            return;
        } else if (requestCode == REQUEST_TAKE_PHOTO_PERMISSION) {
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

    private void fileChoose() {
        if (ContextCompat.checkSelfPermission(RelativeMaterialsActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //申请权限，REQUEST_TAKE_PHOTO_PERMISSION是自定义的常量
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, REQUEST_FILE_CHOOSE_PERMISSION);
        } else {
            Intent intent = new Intent(RelativeMaterialsActivity.this, SDCardFileExplorerActivity.class);
            startActivityForResult(intent, SDCardFileExplorerActivity.REQUEST_CODE_FILE);
        }
    }

    /**
     * 拍照获取图片
     */
    private void picTyTakePhoto() {
        if (ContextCompat.checkSelfPermission(RelativeMaterialsActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            //申请权限，REQUEST_TAKE_PHOTO_PERMISSION是自定义的常量
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, REQUEST_TAKE_PHOTO_PERMISSION);
        } else {
            //判断SD卡是否存在
            String SDState = Environment.getExternalStorageState();
            if (SDState.equals(Environment.MEDIA_MOUNTED)) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//"android.media.action.IMAGE_CAPTURE"
                /***
                 * 使用照相机拍照，拍照后的图片会存放在相册中。使用这种方式好处就是：获取的图片是拍照后的原图，
                 * 如果不实用ContentValues存放照片路径的话，拍照后获取的图片为缩略图有可能不清晰
                 */
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
        if (ContextCompat.checkSelfPermission(RelativeMaterialsActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //申请权限，REQUEST_TAKE_PHOTO_PERMISSION是自定义的常量
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, REQUEST_PIC_PHOTO_PERMISSION);
        } else {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            startActivityForResult(intent, SELECT_PIC_CODE);
        }
    }

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

    private IRequestCallBack callBack = new BaseRequestCallBack() {

        @Override
        public void process(HttpResponse response, int what) {
            ResultItem item = response.getResultItem(ResultItem.class);
            String code = item.getString("code");
            String message = item.getString("message");
            if (Constants.SUCCESS_CODE.equals(code)) {
                if (what == ITEM_FILE_LIST_REQ) {
                    List<ResultItem> data = item.getItems("DATA");
                    loadData(data);
                } else if (what == DELETE_FILE_REQ) {
                    childList.get(groupPos).remove(childPos);
                    if (expandAdapter != null) {
                        expandAdapter.notifyDataSetChanged();
                    }
                } else if (what == SUBMIT_ITEM_AUDIT_REQ) {
                    ResultItem data = (ResultItem) item.get("DATA");
                    String trackId = data.getString("TRACKID");
                    Intent intent = new Intent(RelativeMaterialsActivity.this, MaterialSendActivity.class);
                    intent.putExtra("itemName", itemName);
                    intent.putExtra("proKeyId", proKeyId);
                    intent.putExtra("trackId", trackId);
                    startActivity(intent);
                } else if (what == GET_UPLOAD_FILE_REQ) {
                    List<ResultItem> materials = item.getItems("SQCLDATA");
                    parseMaterialData(materials);
                    List<ResultItem> files = item.getItems("YSCCLDATA");
                    parseFileData(files);
                }
            } else {
                DialogUtils.showToast(RelativeMaterialsActivity.this, message);
            }
        }
    };

    private void loadData(List<ResultItem> items) {
        if (BeanUtils.isEmpty(items)) {
            return;
        }
        for (ResultItem item : items) {
            FileInfo fileInfo = new FileInfo();
            List<FileInfo> child = new ArrayList<>();
            String materialName = item.getString("CLNAME");
            String materialId = item.getString("ID");
            fileInfo.setMaterialName(materialName);
            fileInfo.setMaterialId(materialId);
            fileInfoList.add(fileInfo);
            childList.add(child);
        }

        if (expandAdapter == null) {
            if (!BeanUtils.isEmpty(fileInfoList) || !BeanUtils.isEmpty(childList)) {
                expandAdapter = new MyExpandableListViewAdapter(RelativeMaterialsActivity.this, fileInfoList, childList);
                expand_lv.setAdapter(expandAdapter);
                expandAdapter.setOnClickListener(listener);
            }
        } else {
            expandAdapter.notifyDataSetChanged();
        }
    }

    private void parseMaterialData(List<ResultItem> items) {
        if (BeanUtils.isEmpty(items)) {
            return;
        }
        for (ResultItem item : items) {
            FileInfo fileInfo = new FileInfo();
            String materialName = item.getString("CLNAME");
            String materialId = item.getString("CLID");
            fileInfo.setMaterialName(materialName);
            fileInfo.setMaterialId(materialId);
            fileInfoList.add(fileInfo);
        }
        if (expandAdapter == null) {
            expandAdapter = new MyExpandableListViewAdapter(RelativeMaterialsActivity.this, fileInfoList, childList);
            expand_lv.setAdapter(expandAdapter);
            expandAdapter.setOnClickListener(listener);
        } else {
            expandAdapter.notifyDataSetChanged();
        }
    }

    private void parseFileData(List<ResultItem> files) {
        if (!BeanUtils.isEmpty(files)) {
            for (FileInfo parent : fileInfoList) {
                List<FileInfo> child = new ArrayList<>();
                for (ResultItem item : files) {
                    String fileId = item.getString("CLID");
                    String fileSize = item.getString("YCLSIZE");
                    String fileMaterialId = item.getString("YSCCLID");
                    String fileName = item.getString("YSCLNAME");
                    FileInfo fileInfo = new FileInfo();
                    if (parent.getMaterialId().equals(fileId)) {
                        fileInfo.setFileId(fileId);
                        fileInfo.setFileSize(fileSize);
                        fileInfo.setFileMaterialId(fileMaterialId);
                        fileInfo.setFileName(fileName);
                        child.add(fileInfo);
                    }
                }
                childList.add(child);
            }
        } else {
            for (int i = 0; i < fileInfoList.size(); i++) {
                childList.add(new ArrayList<FileInfo>());
            }
        }
        if (expandAdapter != null) {
            for (int i = 0; i < expandAdapter.getGroupCount(); i++) {
                expand_lv.expandGroup(i);
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            String fileId = fileInfoList.get(mPosition).getMaterialId();
            String itemFileName = fileInfoList.get(mPosition).getMaterialName();
            if (requestCode == REQUEST_CODE_FILE) {
                fileName = data.getStringExtra("FILE_NAME");
                filePath = data.getStringExtra("FILE_PATH");
            } else if (requestCode == TAKE_PHOTO_CODE) {
                String[] projections = {MediaStore.Images.Media.DATA};
                Cursor cursor = managedQuery(photoUri, projections, null, null, null);
                if (cursor != null) {
                    int columnIndex = cursor.getColumnIndexOrThrow(projections[0]);
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
//                    startPhotoZoom(photoUri, PHOTO_CROP_CODE);
                    Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                    if (bitmap != null) {
                        //这里可以把图片进行上传到服务器操作
                    }
                } else {
                    DialogUtils.showToast(RelativeMaterialsActivity.this, "图片选择失败");
                }
            } else if (requestCode == SELECT_PIC_CODE) {
                if (null != data && null != data.getData()) {
                    photoUri = data.getData();
                    filePath = uriToFilePath(photoUri);
                    File file = new File(filePath);
                    fileName = file.getName();
                    Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                    if (bitmap != null) {
                        //这里可以把图片进行上传到服务器操作
                    }
                } else {
                    DialogUtils.showToast(RelativeMaterialsActivity.this, "图片选择失败");
                }
            }
            fileInfoList.get(mPosition).setFileName(fileName);
            fileInfoList.get(mPosition).setFilePath(filePath);
            fileInfoList.get(mPosition).setFileSize(FileSizeUtil.getAutoFileOrFilesSize(filePath));
            // 更改附件选择，然后是选择一个附件就上传
            List<File> files = new ArrayList<>();
            File file = new File(filePath);
            if (file != null && file.exists()) {
                files.add(file);
                // 文件上传
                if (!BeanUtils.isNullOrEmpty(proKeyId)) {
                    DownloadUtils.uploadMultiFile(uiProgressRequestListener, RelativeMaterialsActivity.this, Constants.UPLOAD_URL, filePath, fileName, itemId, proKeyId, fileId, itemFileName);
                }
                showProgressDialog();
            } else {
                DialogUtils.showToast(RelativeMaterialsActivity.this, "该文件不存在");
            }
        }

    }

    private MyExpandableListViewAdapter.IDownloadListener listener = new MyExpandableListViewAdapter.IDownloadListener() {

        @Override
        public void onClick(int position) {
            mPosition = position;
            showSelectPhotoDialog();
        }
    };


    @Override
    public void success(String result) {
        if (progressDialog != null) {
            progressDialog.cancel();
        }
        DialogUtils.showToast(this, "上传成功");
        try {
            JSONObject jsonObject = new JSONObject(result);
            String code = jsonObject.getString("code");
            String message = jsonObject.getString("message");
            if (Constants.SUCCESS_CODE.equals(code)) {
                JSONObject obj = jsonObject.getJSONObject("DATA");
                String fileId = obj.getString("FILEID");
                FileInfo info = new FileInfo();
                info.setFileName(fileName);
                info.setFileId(fileId);
                childList.get(mPosition).add(info);
            } else {
                DialogUtils.showToast(RelativeMaterialsActivity.this, message);
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (expandAdapter != null) {
                        expandAdapter.notifyDataSetChanged();
                    }
                    expand_lv.expandGroup(mPosition);
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void failed() {
        if (progressDialog != null) {
            progressDialog.cancel();
        }
        DialogUtils.showToast(this, "上传失败,请重新操作");
    }

    private void deleteDialog(final String fileId) {
        MyDialog builder = new MyDialog(RelativeMaterialsActivity.this).builder();
        builder
                .setMessage("是否删除材料附件")
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        delete(fileId);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                finish();
                break;
            case R.id.btn_next:
                if (!BeanUtils.isEmpty(childList)) {
                    for (int i = 0; i < childList.size(); i++) {
                        List<FileInfo> child = childList.get(i);
                        if (BeanUtils.isEmpty(child)) {
                            DialogUtils.showToast(RelativeMaterialsActivity.this, "请选择相关的材料附件依次上传");
                            return;
                        }
                    }
                }
                // 申请提交审核请求
                submitItemAudit();
                break;
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

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        groupPos = groupPosition;
        childPos = childPosition;
        String fileId = childList.get(groupPosition).get(childPosition).getFileId();
        String fileMaterialId = childList.get(groupPosition).get(childPosition).getFileMaterialId();
        if (!"0".equals(position)) {
            deleteDialog(fileMaterialId);
        } else {
            deleteDialog(fileId);
        }
        return true;
    }
}
