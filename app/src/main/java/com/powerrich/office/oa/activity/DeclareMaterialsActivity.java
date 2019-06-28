package com.powerrich.office.oa.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.activity.mine.CertificateImageActivity;
import com.powerrich.office.oa.activity.mine.CertificateListActivity;
import com.powerrich.office.oa.adapter.MyExpandableListViewAdapter;
import com.powerrich.office.oa.api.ApiRequest;
import com.powerrich.office.oa.api.OAInterface;
import com.powerrich.office.oa.base.BaseActivity;
import com.powerrich.office.oa.base.BaseRequestCallBack;
import com.powerrich.office.oa.base.IRequestCallBack;
import com.powerrich.office.oa.bean.FileInfo;
import com.powerrich.office.oa.bean.MaterialsDepotBean;
import com.powerrich.office.oa.common.ResultItem;
import com.powerrich.office.oa.enums.CertificateType;
import com.powerrich.office.oa.network.http.HttpResponse;
import com.powerrich.office.oa.tools.BeanUtils;
import com.powerrich.office.oa.tools.Constants;
import com.powerrich.office.oa.tools.DialogUtils;
import com.powerrich.office.oa.tools.DownloadUtils;
import com.powerrich.office.oa.tools.FileSizeUtil;
import com.powerrich.office.oa.tools.FileUtils;
import com.powerrich.office.oa.tools.LoginUtils;
import com.powerrich.office.oa.tools.NetWorkUtil;
import com.powerrich.office.oa.tools.progressutils.listener.impl.UIProgressListener;
import com.powerrich.office.oa.view.CircleProgressBar;
import com.powerrich.office.oa.view.MyDialog;
import com.powerrich.office.oa.view.ProgressBarDialog;
import com.yt.simpleframe.http.bean.entity.CertificateInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static com.powerrich.office.oa.activity.SDCardFileExplorerActivity.REQUEST_CODE_FILE;


/**
 * 文 件 名：DeclareMaterialsActivity
 * 描   述：个人、企业办事申报材料
 * 作   者：Wangzheng
 * 时   间：2018-6-13 11:05:36
 * 版   权：v1.0
 */
public class DeclareMaterialsActivity extends BaseActivity implements View.OnClickListener, DownloadUtils.InvokeData, ExpandableListView.OnChildClickListener {

    private static final int ITEM_FILE_LIST_CODE = 0;
    private static final int GET_UPLOAD_FILE_CODE = 1;
    private static final int SYNC_ITEM_ELECTRON_LICENSE_CODE = 2;
    private static final int GET_ELECTRON_LICENSE_FILE_CODE = 3;
    private static final int SUBMIT_ITEM_AUDIT_CODE = 4;
    private static final int DELETE_FILE_CODE = 5;
    private static final int SAVE_RECORD_FILE_CODE = 6;
    //使用照相机拍照获取图片
    public static final int TAKE_PHOTO_CODE = 1;
    //使用相册中的图片
    public static final int SELECT_PIC_CODE = 2;
    private static final int MATERIALS_DEPOT_CODE = 3;
    private static final int ELECTRONIC_CERTIFICATION_CODE = 4;
    private ImageView iv_progress_bar;
    private TextView tv_item_name, tv_item_code;
    private ExpandableListView expand_lv;
    private Button btn_next;
    private String itemId, itemName, itemCode;
    private String proKeyId;
    private String position;
    private List<FileInfo> fileInfoList = new ArrayList<>();
    private List<List<FileInfo>> childList = new ArrayList<>();

    private MyExpandableListViewAdapter expandAdapter;
    private int mPosition = 0;
    private Dialog mCameraDialog;
    private Uri photoUri;
    private String fileName, filePath;
    private String dynamicForm;

    private int groupPos;
    private int childPos;

    private ProgressBarDialog progressBarDialog;
    private CircleProgressBar circleProgressBar;
    private MyDialog builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemId = getIntent().getStringExtra("itemId");
        itemName = getIntent().getStringExtra("itemName");
        itemCode = getIntent().getStringExtra("itemCode");
        proKeyId = getIntent().getStringExtra("proKeyId");
        dynamicForm = getIntent().getStringExtra("dynamicForm");
        position = getIntent().getStringExtra("position");
        initView();
        initData();
        if (!"0".equals(position)) {
            getUploadFile();
        } else {
            getItemFileList();
        }
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_declare_materials;
    }

    private void initView() {
        initTitleBar(R.string.declare_materials, this, null);
        iv_progress_bar = (ImageView) findViewById(R.id.iv_progress_bar);
        tv_item_name = (TextView) findViewById(R.id.tv_item_name);
        tv_item_code = (TextView) findViewById(R.id.tv_item_code);
        expand_lv = (ExpandableListView) findViewById(R.id.expand_lv);
        btn_next = (Button) findViewById(R.id.btn_next);
    }

    private void initData() {
        tv_item_name.setText(itemName);
        tv_item_code.setText(itemCode);
        btn_next.setOnClickListener(this);
        expand_lv.setOnChildClickListener(this);
        if ("0".equals(dynamicForm)) {
            iv_progress_bar.setImageResource(R.drawable.per_circle_four_4);
        } else {
            iv_progress_bar.setImageResource(R.drawable.per_circle_four);
        }
    }

    /**
     * 通过事项id获取该事项所需上传的材料请求
     */
    private void getItemFileList() {
        ApiRequest request = OAInterface.getItemFileList(itemId);
        invoke.invokeWidthDialog(request, callBack, ITEM_FILE_LIST_CODE);
    }

    /**
     * 暂存件-获取申请材料和已上传申请材料信息请求
     */
    private void getUploadFile() {
        ApiRequest request = OAInterface.getUploadedFile(proKeyId, itemId);
        invoke.invokeWidthDialog(request, callBack, GET_UPLOAD_FILE_CODE);
    }

    /**
     * 删除已上传的附件请求
     *
     * @param fileId
     */
    private void deleteFileById(String fileId) {
        ApiRequest request = OAInterface.deleteFileById("1", fileId);
        invoke.invokeWidthDialog(request, callBack, DELETE_FILE_CODE);
    }

    /**
     * 网上办事-事项申报提交审核请求
     */
    private void submitItemAudit() {
        ApiRequest request = OAInterface.submitItemAudit(itemId, proKeyId);
        invoke.invokeWidthDialog(request, callBack, SUBMIT_ITEM_AUDIT_CODE);
    }

    /**
     * 同步事项电子证照请求
     */
    private void syncItemElectronLicense() {
        ApiRequest request = OAInterface.syncItemElectronLicense(proKeyId);
        invoke.invokeWidthDialog(request, callBack, SYNC_ITEM_ELECTRON_LICENSE_CODE);
    }

    /**
     * 同步回来的电子证照信息请求
     */
    private void getElectronLicenseFile(String materialId) {
        ApiRequest request = OAInterface.getElectronLicenseFile(proKeyId, materialId);
        invoke.invoke(request, callBack, GET_ELECTRON_LICENSE_FILE_CODE);
    }

    /**
     * 事项申报时保存附件材料库信息请求
     */
    private void saveRecordFile(String materialName, String materialId) {
        ApiRequest request = OAInterface.saveRecordFile(proKeyId, materialName, materialId);
        invoke.invokeWidthDialog(request, callBack, SAVE_RECORD_FILE_CODE);
    }

    private IRequestCallBack callBack = new BaseRequestCallBack() {

        @Override
        public void process(HttpResponse response, int what) {
            ResultItem item = response.getResultItem(ResultItem.class);
            String code = item.getString("code");
            String message = item.getString("message");
            if (Constants.SUCCESS_CODE.equals(code)) {
                if (what == ITEM_FILE_LIST_CODE) {
                    List<ResultItem> data = item.getItems("DATA");
                    parseData(data);
                    syncItemElectronLicense();
                } else if (what == GET_UPLOAD_FILE_CODE) {
                    List<ResultItem> materials = item.getItems("SQCLDATA");
                    parseMaterialData(materials);
                    List<ResultItem> files = item.getItems("YSCCLDATA");
                    parseFileData(files);
                    syncItemElectronLicense();
                } else if (what == SYNC_ITEM_ELECTRON_LICENSE_CODE) {
                    ResultItem result = (ResultItem) item.get("DATA");
                    List<String> items = (List<String>) result.get("MIDS");
                    for (String s : items) {
                        getElectronLicenseFile(s);
                    }
                } else if (what == GET_ELECTRON_LICENSE_FILE_CODE) {
                    List<ResultItem> items = item.getItems("DATA");
                    parseElectronLicenseFile(items);
                } else if (what == SUBMIT_ITEM_AUDIT_CODE) {
                    ResultItem data = (ResultItem) item.get("DATA");
                    String trackId = data.getString("TRACKID");
                    Intent intent = new Intent(DeclareMaterialsActivity.this, DeclareNotifyActivity.class);
                    intent.putExtra("itemName", itemName);
                    intent.putExtra("itemCode", itemCode);
                    intent.putExtra("dynamicForm", dynamicForm);
                    intent.putExtra("proKeyId", proKeyId);
                    intent.putExtra("trackId", trackId);
                    startActivity(intent);
                } else if (what == DELETE_FILE_CODE) {
                    delete(groupPos, childPos);
                    DialogUtils.showToast(DeclareMaterialsActivity.this, message);
                } else if (what == SAVE_RECORD_FILE_CODE) {
                    DialogUtils.showToast(DeclareMaterialsActivity.this, message);
                }
            } else {
                DialogUtils.showToast(DeclareMaterialsActivity.this, message);
            }
        }
    };

    private void parseData(List<ResultItem> items) {
        if (BeanUtils.isEmpty(items)) {
            return;
        }
        for (ResultItem item : items) {
            FileInfo fileInfo = new FileInfo();
            List<FileInfo> child = new ArrayList<>();
            String asyncJhpt = item.getString("ASYNC_JHPT");
            String materialName = item.getString("CLNAME");
            String materialId = item.getString("ID");
            fileInfo.setAsyncJhpt(asyncJhpt);
            fileInfo.setMaterialName(materialName);
            fileInfo.setMaterialId(materialId);
            if (!BeanUtils.isEmptyStr(asyncJhpt)) {
                if (BeanUtils.isEmptyStr(LoginUtils.getInstance().getUserInfo().getDATA().getAPPEIDCODE())) {
                    FileInfo f = new FileInfo();
                    f.setFileName("已同步人口库信息");
                    child.add(f);
                }
            }
            childList.add(child);
            fileInfoList.add(fileInfo);
        }
        setAdapterData();
        expand();
    }

    private void parseElectronLicenseFile(List<ResultItem> items) {
        if (!BeanUtils.isEmpty(items)) {
            if (!BeanUtils.isEmpty(fileInfoList)) {
                for (int i = 0; i < fileInfoList.size(); i++) {
                    List<FileInfo> child = new ArrayList<>();
                    for (ResultItem item : items) {
                        String clId = item.getString("CLID");
                        String hdfsFileName = item.getString("HDFSFILENAME");
                        String path = item.getString("PATH");
                        String preName = item.getString("PRENAME");
                        String id = fileInfoList.get(i).getMaterialId();
                        if (id.equals(clId)) {
                            FileInfo fileInfo = new FileInfo();
                            fileInfo.setFileName(hdfsFileName);
                            fileInfo.setPath(path);
                            fileInfo.setPreName(preName);
                            child.add(fileInfo);
                            childList.get(i).add(fileInfo);
                        }
                    }
                }
            }
        } else {
            for (int i = 0; i < fileInfoList.size(); i++) {
                childList.add(new ArrayList<FileInfo>());
            }
        }
        expand();
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
        setAdapterData();
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
                    if (parent.getMaterialId().equals(fileId)) {
                        FileInfo fileInfo = new FileInfo();
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
        expand();

    }

    private void expand() {
        if (expandAdapter != null) {
            for (int i = 0; i < expandAdapter.getGroupCount(); i++) {
                expand_lv.expandGroup(i);
            }
            expandAdapter.notifyDataSetChanged();
        }
    }

    private void setAdapterData() {
        if (expandAdapter == null) {
            expandAdapter = new MyExpandableListViewAdapter(DeclareMaterialsActivity.this, fileInfoList, childList);
            expand_lv.setAdapter(expandAdapter);
            expandAdapter.setOnClickListener(listener);
            expandAdapter.setOnDeleteListener(deleteListener);
        } else {
            expandAdapter.notifyDataSetChanged();
        }
    }

    private MyExpandableListViewAdapter.IDownloadListener listener = new MyExpandableListViewAdapter.IDownloadListener() {

        @Override
        public void onClick(int position) {
            mPosition = position;
            showSelectPhotoDialog();
        }
    };

    /**
     * 删除已上传的材料信息
     */
    private MyExpandableListViewAdapter.IDeleteListener deleteListener = new MyExpandableListViewAdapter.IDeleteListener() {
        @Override
        public void onDelete(int groupPosition, int childPosition) {
            groupPos = groupPosition;
            childPos = childPosition;
            String fileId = childList.get(groupPosition).get(childPosition).getFileId();
            String fileMaterialId = childList.get(groupPosition).get(childPosition).getFileMaterialId();
            deleteDialog("0".equals(position) ? fileId : fileMaterialId, groupPosition, childPosition);
        }
    };

    private void showSelectPhotoDialog() {
        if (mCameraDialog != null && mCameraDialog.isShowing()) {
            return;
        }
        mCameraDialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        LinearLayout root = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.layout_camera_control, null);
        root.findViewById(R.id.btn_history_attachment).setOnClickListener(this);
        root.findViewById(R.id.btn_electronic_certification).setOnClickListener(this);
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
        root.measure(0, 0);
//        lp.height = root.getMeasuredHeight();
        lp.alpha = 9f; // 透明度
        dialogWindow.setAttributes(lp);
        mCameraDialog.setCanceledOnTouchOutside(true);
        mCameraDialog.show();
    }

    private void fileChoose() {
        doPermissionRW("存储", new PermissionCallBack() {
            @Override
            public void accept() {
                Intent intent = new Intent(DeclareMaterialsActivity.this, SDCardFileExplorerActivity.class);
                startActivityForResult(intent, REQUEST_CODE_FILE);
            }
        });
    }

    /**
     * 拍照获取图片
     */
    private void picTyTakePhoto() {
        doPermission("相机、存储", new PermissionCallBack() {
            @Override
            public void accept() {
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
                    DialogUtils.showToast(DeclareMaterialsActivity.this, "内存卡不存在");
                }
            }
        }, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            final String materialId = fileInfoList.get(mPosition).getMaterialId();
            final String materialName = fileInfoList.get(mPosition).getMaterialName();
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
                    DialogUtils.showToast(DeclareMaterialsActivity.this, "图片选择失败");
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
                    DialogUtils.showToast(DeclareMaterialsActivity.this, "图片选择失败");
                }
            } else if (requestCode == MATERIALS_DEPOT_CODE) {
                List<MaterialsDepotBean> beanList = (List<MaterialsDepotBean>) data.getSerializableExtra("materialsDepotBeanList");
                for (MaterialsDepotBean bean : beanList) {
                    FileInfo info = new FileInfo();
                    info.setFileName(bean.getNAME());
                    info.setFileId(bean.getID());
                    childList.get(mPosition).add(info);
                    saveRecordFile(materialName, bean.getID());
                }
            } else if (requestCode == ELECTRONIC_CERTIFICATION_CODE) {
                ArrayList<CertificateInfo> beanList = (ArrayList<CertificateInfo>) data.getSerializableExtra("sendList");
                for (CertificateInfo bean : beanList) {
                    FileInfo info = new FileInfo();
                    info.setFileName(bean.getFILENAME());
                    info.setFileId(bean.getID());
                    childList.get(mPosition).add(info);
                    saveRecordFile(materialName, bean.getID());
                }
            }
            if (expandAdapter != null) {
                expandAdapter.notifyDataSetChanged();
            }
            expand_lv.expandGroup(mPosition);
            if (requestCode == REQUEST_CODE_FILE || requestCode == TAKE_PHOTO_CODE || requestCode == SELECT_PIC_CODE) {
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
                        uploadFile(materialId, materialName);
                    }
                } else {
                    DialogUtils.showToast(DeclareMaterialsActivity.this, "该文件不存在");
                }
            }
        }

    }

    private void uploadFile(final String materialId, final String materialName) {
        String extend = FileUtils.getFileExtends(fileName);
        if ("pdf".equalsIgnoreCase(extend) || "bmp".equalsIgnoreCase(extend) || "gif".equalsIgnoreCase(extend)
                || "jpg".equalsIgnoreCase(extend) || "jpeg".equalsIgnoreCase(extend) || "png".equalsIgnoreCase(extend)) {
            String saveDir = Environment.getExternalStorageDirectory() + "/download";
            Luban.with(this)
                    .load(filePath)
                    .ignoreBy(100)
                    .setTargetDir(saveDir)
                    .filter(new CompressionPredicate() {
                        @Override
                        public boolean apply(String path) {
                            return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".pdf"));
                        }
                    })
                    .setCompressListener(new OnCompressListener() {
                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onSuccess(File file) {
                            if (!NetWorkUtil.checkedNetWork(DeclareMaterialsActivity.this)) {
                                DialogUtils.showToast(DeclareMaterialsActivity.this, "请检查网络连接是否正常！");
                                return;
                            }
                            DownloadUtils.uploadMultiFile(uiProgressRequestListener, DeclareMaterialsActivity.this, Constants.UPLOAD_URL, file.getAbsolutePath(), fileName, itemId, proKeyId, materialId, materialName);
                            showProgressBarDialog();
                        }

                        @Override
                        public void onError(Throwable e) {
                            DialogUtils.showToast(DeclareMaterialsActivity.this, "压缩失败：" + e.getMessage());
                        }
                    }).launch();
        } else {
            DialogUtils.showToast(DeclareMaterialsActivity.this, "你上传的格式不正确，请重新选择！");
        }
    }

    //这个是ui线程回调，可直接操作UI
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

    private void showProgressBarDialog() {
        progressBarDialog = ProgressBarDialog.createDialog(DeclareMaterialsActivity.this);
        circleProgressBar = (CircleProgressBar) progressBarDialog.findViewById(R.id.progressBar);
        circleProgressBar.setCurProgress(0);
        if (progressBarDialog != null) {
            progressBarDialog.show();
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

    @Override
    public void success(String result) {
        if (progressBarDialog != null) {
            progressBarDialog.cancel();
        }
        Toast.makeText(this, "上传成功", Toast.LENGTH_SHORT).show();
        parseJson(result);
    }

    /**
     * 图片上传成功数据解析
     * @param result
     */
    private void parseJson(String result) {
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
                DialogUtils.showToast(DeclareMaterialsActivity.this, message);
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
        if (progressBarDialog != null) {
            progressBarDialog.cancel();
        }
        Toast.makeText(this, "上传失败,请重新操作", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, final int groupPosition, final int childPosition, long id) {
        if (!BeanUtils.isEmptyStr(childList.get(groupPosition).get(childPosition).getPreName())) {
            Intent intent = new Intent(DeclareMaterialsActivity.this, CertificateImageActivity.class);
            intent.putExtra("CARD_FILE", childList.get(groupPosition).get(childPosition).getPath());
            intent.putExtra("HDFSFILENAME", childList.get(groupPosition).get(childPosition).getFileName());
            intent.putExtra("FILENAME", childList.get(groupPosition).get(childPosition).getPreName());
            startActivity(intent);
        }
        if (!BeanUtils.isEmptyStr(fileInfoList.get(groupPosition).getAsyncJhpt())) {
            if (BeanUtils.isEmptyStr(LoginUtils.getInstance().getUserInfo().getDATA().getAPPEIDCODE())) {
                if (BeanUtils.isEmpty(childList.get(groupPosition).get(childPosition).getFileId())) {
                    Intent intent = new Intent(DeclareMaterialsActivity.this, ExchangeInformationActivity.class);
                    intent.putExtra("materialId", fileInfoList.get(groupPosition).getMaterialId());
                    intent.putExtra("materialName", fileInfoList.get(groupPosition).getMaterialName());
                    intent.putExtra("asyncJhpt", fileInfoList.get(groupPosition).getAsyncJhpt());
                    intent.putExtra("proKeyId", proKeyId);
                    startActivity(intent);
                }
            }
        }
        return true;
    }

    private void deleteDialog(final String fileId, final int groupPosition, final int childPosition) {
        if (builder != null && builder.isShowing()) {
            return;
        }
        builder = new MyDialog(DeclareMaterialsActivity.this).builder();
        builder
                .setMessage("是否删除材料附件")
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (!BeanUtils.isEmptyStr(fileId)) {
                            deleteFileById(fileId);
                        } else {
                            delete(groupPosition, childPosition);
                        }
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

    private void delete(int groupPosition, int childPosition) {
        childList.get(groupPosition).remove(childPosition);
        if (expandAdapter != null) {
            expandAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 关闭对话框
     */
    private void dismiss() {
        if (mCameraDialog != null) {
            mCameraDialog.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                DeclareMaterialsActivity.this.finish();
                break;
            case R.id.btn_next:
                if (!BeanUtils.isEmpty(childList)) {
                    for (int i = 0; i < childList.size(); i++) {
                        List<FileInfo> child = childList.get(i);
                        if (BeanUtils.isEmpty(child)) {
                            DialogUtils.showToast(DeclareMaterialsActivity.this, "请选择相关的材料附件依次上传");
                            return;
                        }
                    }
                }
                // 申请提交审核请求
                submitItemAudit();
                break;
            case R.id.btn_history_attachment:
                startActivityForResult(new Intent(DeclareMaterialsActivity.this, MaterialsDepotActivity.class), MATERIALS_DEPOT_CODE);
                dismiss();
                break;
            case R.id.btn_electronic_certification:
                Intent intent = new Intent(DeclareMaterialsActivity.this, CertificateListActivity.class);
                intent.putExtra("type", CertificateType.选择);
                startActivityForResult(intent, ELECTRONIC_CERTIFICATION_CODE);
                dismiss();
                break;
            case R.id.btn_file_choose:
                fileChoose();
                dismiss();
                break;
            case R.id.btn_open_camera:
                // 打开照相机
                picTyTakePhoto();
                dismiss();
                break;
            case R.id.btn_choose_img:
                // 打开相册
                pickPhoto();
                dismiss();
                break;
            case R.id.btn_cancel:
                // 取消
                dismiss();
                break;
        }
    }

}
