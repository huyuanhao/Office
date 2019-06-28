package com.powerrich.office.oa.activity;

import java.io.File;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.powerrich.office.oa.R;
import com.powerrich.office.oa.base.BaseActivity;
/**
 * 给我写信界面
 * @author Administrator
 *
 */
public class WriteToMeActivity extends BaseActivity implements OnClickListener {

	private ImageView iv_photo;
	private Dialog mCameraDialog;
	//使用照相机拍照获取图片
    public static final int TAKE_PHOTO_CODE = 1;
    //使用相册中的图片
    public static final int SELECT_PIC_CODE = 2;
    //图片裁剪
    private static final int PHOTO_CROP_CODE = 3;
    //定义图片的Uri
    private Uri photoUri;
    //图片文件路径
    private String picPath;
	private RadioButton cb_no,cb_yes;
	private CheckBox cb_niming;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initTitleBar(R.string.title_activity_write_to_me, this, null);
		initView();
	}

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_write_to_me;
    }

    private void initView() {
		iv_photo = (ImageView) findViewById(R.id.iv_photo);
		cb_no = (RadioButton) findViewById(R.id.cb_no);
		cb_yes = (RadioButton) findViewById(R.id.cb_yes);
		cb_niming = (CheckBox) findViewById(R.id.cb_niming);
		loadData();
	}

	private void loadData() {
		iv_photo.setOnClickListener(this);
	}

	private void showSelectPhotoDialog() {
		mCameraDialog = new Dialog(this, R.style.my_dialog);  
        LinearLayout root = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.layout_camera_control, null);
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
	
	/**
     * 拍照获取图片
     */
    private void picTyTakePhoto() {
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

    /***
     * 从相册中取图片
     */
    private void pickPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, SELECT_PIC_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            //从相册取图片，有些手机有异常情况，请注意
            if (requestCode == SELECT_PIC_CODE) {
                if (null != data && null != data.getData()) {
                    photoUri = data.getData();
                    picPath = uriToFilePath(photoUri);
//                    startPhotoZoom(photoUri, PHOTO_CROP_CODE);
                    Bitmap bitmap = BitmapFactory.decodeFile(picPath);
                    if (bitmap != null) {
                        //这里可以把图片进行上传到服务器操作
                        iv_photo.setImageBitmap(bitmap);
                    }
                } else {
                    Toast.makeText(this, "图片选择失败", Toast.LENGTH_LONG).show();
                }
            } else if (requestCode == TAKE_PHOTO_CODE) {
                String[] pojo = {MediaStore.Images.Media.DATA};
                Cursor cursor = managedQuery(photoUri, pojo, null, null, null);
                if (cursor != null) {
                    int columnIndex = cursor.getColumnIndexOrThrow(pojo[0]);
                    cursor.moveToFirst();
                    picPath = cursor.getString(columnIndex);
                    File file = new File(picPath);
                    String fileName = file.getName();
                    System.out.println("picPath=" + picPath + "\nfileName=" + fileName);
                    if (Build.VERSION.SDK_INT < 14) {
                        cursor.close();
                    }
                }
                if (picPath != null) {
                    photoUri = Uri.fromFile(new File(picPath));
//                    startPhotoZoom(photoUri, PHOTO_CROP_CODE);
                    Bitmap bitmap = BitmapFactory.decodeFile(picPath);
                    if (bitmap != null) {
                        //这里可以把图片进行上传到服务器操作
                        iv_photo.setImageBitmap(bitmap);
                    }
                } else {
                    Toast.makeText(this, "图片选择失败", Toast.LENGTH_LONG).show();
                }
            } 
        }
    }

    /**
     * @param
     * @description 把Uri转换为文件路径
     */
    private String uriToFilePath(Uri uri) {
        if ( null == uri ) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if ( scheme == null )
            data = uri.getPath();
        else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
            data = uri.getPath();
        } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
            Cursor cursor = this.getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null );
            if ( null != cursor ) {
                if ( cursor.moveToFirst() ) {
                    int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
                    if ( index > -1 ) {
                        data = cursor.getString( index );
                    }
                }
                cursor.close();
            }
        }
        return data;

    }
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.system_back:
			finish();
			break;
		case R.id.iv_photo:
			showSelectPhotoDialog();
			break;
		// 打开照相机  
		case R.id.btn_open_camera: 
			picTyTakePhoto();
			if (mCameraDialog != null) {
				mCameraDialog.dismiss();
			}
            break;  
        // 打开相册  
        case R.id.btn_choose_img:  
        	pickPhoto();
        	if (mCameraDialog != null) {
				mCameraDialog.dismiss();
			}
            break;  
         // 取消  
        case R.id.btn_cancel:  
			if (mCameraDialog != null) {
				mCameraDialog.dismiss();
			}
			break;
		default:
			break;
		}
	}

}
